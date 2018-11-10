grammar CElsa;


translationUnit :
   declaration* EOF
;

declaration :
   blockDeclaration|
   functionDefinition|
   macroInvocation |
   templateDeclaration|
   explicitInstantiation|
   explicitSpecialization|
   linkageSpecification|
   namespaceDefinition
;

macroInvocation:
   methodCall ;

// -------------------- a.1 keywords --------------------

typedefName :
   IDENTIFIER
;

namespaceName :
   originalNamespaceName|
   namespaceAlias
;

 originalNamespaceName :
   IDENTIFIER
;

 namespaceAlias :
   IDENTIFIER
;

 className :
   IDENTIFIER|
   templateId
;

 enumName :
   IDENTIFIER
;

 templateName :
   IDENTIFIER
;


literal :
   DECIMAL_LITERAL|
   HEX_LITERAL |
   OCT_LITERAL |
   BINARY_LITERAL |
   CHAR_LITERAL|
   FLOAT_LITERAL|
   HEX_FLOAT_LITERAL|
   StringBlock|
   BOOL_LITERAL |
   NULL_LITERAL 
;
StringBlock:
   STRING_LITERAL+ ;

// -------------------- a.4 expressions --------------------

 idExpression :
   unqualifiedId|
   qualifiedId
;

 unqualifiedId :
   IDENTIFIER|
   operatorFunctionId|
   conversionFunctionId|
   '~' className|
   templateId
;

template :
   'template'
;

 qualifiedId :
   '::'? nestedNameSpecifier template? unqualifiedId|
   '::' IDENTIFIER|
   '::' operatorFunctionId|
   '::' templateId
;

 nestedNameSpecifier :
   classOrNamespaceName '::' nestedNameSpecifier?|
   classOrNamespaceName '::' 'template' nestedNameSpecifier
;

 classOrNamespaceName :
   className|
   namespaceName
;                                                

 primaryExpression :
   literal|
   'this'|
   '(' expression ')'|
   idExpression |
   newExpression|
   deleteExpression
;

expression
    : primaryExpression
    | expression bop='.'
    (
    	template? idExpression
    	|pseudoDestructorName
    	|methodCall
    )
    | expression bop='::'
    (
    	template? idExpression
    	|pseudoDestructorName
    	|methodCall
    )
    |expression bop='->'
    (
    	template? idExpression
    	|pseudoDestructorName
        |methodCall
    )
    | prefix=('+'|'-'|'++'|'--'|'*'|'&'|'!'|'~') expression
    | methodCall
    | expression '[' expressionList ']'
    | 'sizeof' expression
    |  'sizeof' '(' typeId ')'
    | 'typename' '::'? nestedNameSpecifier IDENTIFIER '(' expressionList ')'
    | 'typename' '::'? nestedNameSpecifier template? templateId '(' expressionList ')'
    | simpleTypeSpecifier '(' expressionList ')'    
    | expression postfix=('++' | '--')
    | 'dynamic_cast'     '<' typeId '>' '(' expression ')'
    | 'static_cast'      '<' typeId '>' '(' expression ')'
    | 'reinterpret_cast' '<' typeId '>' '(' expression ')'
    | 'const_cast'       '<' typeId '>' '(' expression ')'
    | 'typeid' '(' expression ')'
    | 'typeid' '(' typeId ')'
    |  '(' typeId ')' expression
    | expression bop=('*'|'/'|'%') expression
    | expression bop=('+'|'-') expression
    | expression ('<<' | '>>' | '>>>' '>') expression
    | expression bop=('<=' | '>=' | '>' | '<') expression
    | expression bop=('==' | '!=') expression
    | expression bop='&' expression
    | expression bop='^' expression
    | expression bop='|' expression
    | expression bop='&&' expression
    | expression bop='||' expression
    | expression bop='?' expression ':' expression
    | <assoc=right> expression
      bop=('=' | '+=' | '-=' | '*=' | '/=' | '&=' | '|=' | '^=' | '>>=' | '>>>=' | '<<=' | '%=')
      expression
	;
	
methodCall: 
	'~'? IDENTIFIER '(' expressionList? ')'
	|'this' '->' '(' expressionList? ')'
	;
	
expressionList :
   expression (',' expression)*
;

 pseudoDestructorName :
   '::'? nestedNameSpecifier? typeName '::' '~' typeName|
   '::'? nestedNameSpecifier 'template' templateId '::' '~' typeName|
   '::'? nestedNameSpecifier? '~' typeName
;


 newExpression :
   '::'? 'new' newPlacement? newTypeId newInitializer?|
   '::'? 'new' newPlacement? '(' typeId ')' newInitializer?
;

 newPlacement :
   '(' expressionList ')'
;                           

 newTypeId :
   typeSpecifier+ ('[' expression? ']')*
;

 newInitializer :
   '(' expressionList? ')'
;

 deleteExpression :
   '::'? 'delete' expression|
   '::'? 'delete' '[' ']' expression
;


 pmExpression :
   expression|
   pmExpression '.*' expression|
   pmExpression '*' expression
;

// -------------------- a.5 statements --------------------

 statement :
   labeledStatement|
   expressionStatement|
   compoundStatement|
   selectionStatement|
   iterationStatement|
   jumpStatement|
   blockDeclaration|
   tryBlock  |
   macroInvocation
;

 labeledStatement :
   IDENTIFIER ':' statement|
   'case' expression ':' statement|
   'default' ':' statement      // note: linux kernel contains 'switch (..) : ... default: ;' ..
;


 expressionStatement :
   expression ';'
;

compoundStatement
    : '{' statement* '}' 
    ;

 selectionStatement :
   'if' '(' condition ')' statement|
   'if' '(' condition ')' statement 'else' statement|
   'switch' '(' condition ')' statement
;

 condition :
   expression|
   typeSpecifier+ declarator '=' expression 
;
  
 iterationStatement :
   'while' '(' condition ')' statement|
   'do' statement 'while' '(' expression ')' ';'|
   'for' '(' forInitStatement condition? ';' expression? ')' statement
;

 forInitStatement :
   expressionStatement|
   simpleDeclaration
;

 jumpStatement :
   'break' ';'|
   'continue' ';'|
   'return' expression? ';'|
   'goto' IDENTIFIER ';'
;



// -------------------- a.6 declarations --------------------

 blockDeclaration :
   simpleDeclaration|
   asmDefinition|
   namespaceAliasDefinition|
   usingDeclaration|
   usingDirective
;


simpleDeclaration :
   declSpecifier* initDeclaratorList? ';'
;

initDeclaratorList :
   initDeclarator (',' initDeclarator)*
;

initDeclarator :
   declarator initializer?
;


 declSpecifier :
   storageClassSpecifier|
   typeSpecifier |
   functionSpecifier|
   'friend'|
   'typedef'
;


 storageClassSpecifier :
   'auto'|
   'register'|
   'static'|
   'extern'|
   'mutable'
;

 functionSpecifier :
   'inline'|
   'virtual'|
   'explicit'
;

typeSpecifier :
   simpleTypeSpecifier|
   classSpecifier|
   enumSpecifier|
   elaboratedTypeSpecifier|
   cvQualifier |
   ptrOperator
;

 simpleTypeSpecifier :
   '::'? nestedNameSpecifier? typeName|
   '::'? nestedNameSpecifier 'template' templateId|
   'char'|
   'wchar_t'|
   'bool'|
   'short'|
   'int'|
   'long'|
   'signed'|
   'unsigned'|
   'float'|
   'double'|
   'void'
;

 typeName :
   className|
   enumName|
   typedefName
;

 elaboratedTypeSpecifier :
   classKey '::'? nestedNameSpecifier? IDENTIFIER|
   'enum' '::'? nestedNameSpecifier? IDENTIFIER|
   'typename' '::'? nestedNameSpecifier IDENTIFIER|
   'typename' '::'? nestedNameSpecifier template? templateId
;

 enumSpecifier :
   'enum' IDENTIFIER? '{' enumeratorList? ','? '}'
;

 enumeratorList :
   enumeratorDefinition (',' enumeratorDefinition)*
;

 enumeratorDefinition :
   IDENTIFIER |
   IDENTIFIER '=' expression
;

 namespaceDefinition :
   'namespace' IDENTIFIER? '{' declaration* '}'
;

 namespaceAliasDefinition :
   'namespace' IDENTIFIER '=' qualifiedNamespaceSpecifier ';'
;

 qualifiedNamespaceSpecifier :
   '::'? nestedNameSpecifier? namespaceName
;

 usingDeclaration :
   'using' 'typename'? '::'? nestedNameSpecifier unqualifiedId ';'|
   'using' '::' unqualifiedId ';'
;

 usingDirective :
   'using' 'namespace' '::'? nestedNameSpecifier? namespaceName ';'
;

 asmDefinition :
   'asm' '(' StringBlock ')' ';'
;

 linkageSpecification :
   'extern' STRING_LITERAL '{' declaration* '}'|
   'extern' STRING_LITERAL declaration
;


// -------------------- a.7 declarators --------------------

 declarator :
   declaratorId |
   declaratorId+ '(' parameterDeclarationClause? ')' cvQualifierSeq? exceptionSpecification?|
   declaratorId+ '[' expression? ']'|
   '(' declarator ')'
;

 ptrOperator :
   '*' cvQualifierSeq?|
   '&'  |
   '::'? nestedNameSpecifier '*' cvQualifierSeq?     // pointer to member
;

 cvQualifierSeq :
   cvQualifier cvQualifier*
;

 cvQualifier :
   'const'|
   'volatile'
;

 declaratorId :
   idExpression|
   '::'? nestedNameSpecifier? typeName
;

 typeId :
   typeSpecifier+ declarator?
;


 parameterDeclarationClause :
   parameterDeclaration (',' parameterDeclaration)*  '...'?
;

 parameterDeclaration :
   declSpecifier+ declarator?|
   declSpecifier+ declarator? '=' expression
;


functionDefinition :
   declSpecifier* declarator ctorInitializer? functionBody|
   declSpecifier* declarator functionTryBlock
;

 functionBody :
   compoundStatement
;

 initializer :
   '=' initializerClause|
   '(' expressionList ')'
;

 initializerClause :
   expression|
   '{' initializerList ','? '}'|
   '{' '}'
;

 initializerList :
   initializerClause|
   initializerList ',' initializerClause
;


// -------------------- a.8 classes --------------------


 classSpecifier :
   classHead '{' memberSpecification* '}'
;

 classHead :
   classKey IDENTIFIER? baseClause?|
   classKey nestedNameSpecifier IDENTIFIER baseClause?|
   classKey nestedNameSpecifier? templateId baseClause?
;

 classKey :
   'class'|
   'struct'|
   'union'
;
  
 memberSpecification :
   memberDeclaration memberDeclaration* |
   accessSpecifier ':' memberSpecification?
;

 memberDeclaration :
   functionDefinition ';'?|
   declSpecifier* memberDeclaratorList? ';'|
   '::'? nestedNameSpecifier template? unqualifiedId ';'|
   usingDeclaration|
   templateDeclaration |
   macroInvocation
;

 memberDeclaratorList :
   memberDeclarator (',' memberDeclarator)*
;

 memberDeclarator :
   declarator pureSpecifier?|           // for when declarator is a function type
   declarator constantInitializer?|     // for when it is any other type
   IDENTIFIER? ':' expression  // bitfield with optional name
;

 pureSpecifier :
   '=' DECIMAL_LITERAL        // standard says '0' here but that's not one of my TOKens..
;

 constantInitializer: 
   '=' expression
;


// -------------------- a.9 derived classes --------------------

 baseClause :
   ':' baseSpecifierList
;

 baseSpecifierList :
   baseSpecifier|
   baseSpecifierList ',' baseSpecifier
;
 

 baseSpecifier :
   '::'? nestedNameSpecifier? className|
   'virtual' accessSpecifier? '::'? nestedNameSpecifier? className|
   accessSpecifier 'virtual'?   '::'? nestedNameSpecifier? className
;

 accessSpecifier :
   'private'|
   'protected'|
   'public'
;


// -------------------- a.10 special member functions --------------------

 conversionFunctionId :
   'operator' typeSpecifier+ 
;

 ctorInitializer :
   ':'  memInitializer (',' memInitializer)*
;

 memInitializer :
   memInitializerId '(' expressionList? ')'
;

 memInitializerId :

   '::'? nestedNameSpecifier? className|
   IDENTIFIER
;


// -------------------- a.11 overloading --------------------

 operatorFunctionId :
   'operator' operatorFunc
;

 operatorFunc :
   'new'|
   'delete'|
   'new' '[' ']'|
   'delete' '[' ']'|
   '+'|
   '-'|
   '*'|
   '/'|
   '%'|
   '^'|
   '&'|
   '|'|
   '~'|
   '!'|
   '='|
   '<'|
   '>'|
   '+='|
   '-='|
   '*='|
   '/='|
   '%='|
   '^='|
   '&='|
   '|='|
   '<<'|
   '>>'|
   '>>='|
   '<<='|
   '=='|
   '!='|
   '<='|
   '>='|
   '&&'|
   '||'|
   '++'|
   '--'|
   ','|
   '*'|
   '->'|
   '(' ')'|
   '[' ']'
;


// -------------------- a.12 templates --------------------
templateDeclaration :
   'template' '<' templateParameterList '>' declaration
;

 templateParameterList :
   templateParameter  ( ',' templateParameter)*
;

 templateParameter :
   typeParameter|
   parameterDeclaration
;

 typeParameter :
   'class' IDENTIFIER?|
   'class' IDENTIFIER? '=' typeId|
   'typename' IDENTIFIER? '=' typeId|
   'typename' IDENTIFIER?|
   'template' '<' templateParameterList '>' 'class' IDENTIFIER?|
   'template' '<' templateParameterList '>' 'class' IDENTIFIER? '=' idExpression
;


templateId :
   templateName '<' templateArgumentList? '>'
;
 templateArgumentList :
   templateArgument ( ',' templateArgument)*
;

 templateArgument :
   expression|
   typeId|
   idExpression
;

 explicitInstantiation :
   'template' declaration
;

 explicitSpecialization :
   'template' '<' '>' declaration
;

// -------------------- a.13 exception handling --------------------

 tryBlock :
   'try' compoundStatement handlerSeq
;

 functionTryBlock :
   'try' ctorInitializer? functionBody handlerSeq
;

 handlerSeq :
   handler handler*
;

 handler :
   'catch' '(' exceptionDeclaration ')' compoundStatement
;

 exceptionDeclaration :
   typeSpecifier+ declarator|
   typeSpecifier+|
   '...'
;

 throwExpression :
   'throw' expression?
;

 exceptionSpecification :
   'throw' '(' typeIdList? ')'
;

 typeIdList :
   typeId|
   typeIdList ',' typeId
;


/*Preprocessing directives*/

MultiLineMacro
:
    '#' (~[\n]*? '\\' '\r'? '\n')+ ~[\n]+ -> channel(HIDDEN)
;

Directive
:
    '#' ~[\n]* -> channel(HIDDEN)
;



// Literals

DECIMAL_LITERAL:    ('0' | [1-9] (Digits? | '_'+ Digits)) [lL]?;
HEX_LITERAL:        '0' [xX] [0-9a-fA-F] ([0-9a-fA-F_]* [0-9a-fA-F])? [lL]?;
OCT_LITERAL:        '0' '_'* [0-7] ([0-7_]* [0-7])? [lL]?;
BINARY_LITERAL:     '0' [bB] [01] ([01_]* [01])? [lL]?;
                    
FLOAT_LITERAL:      (Digits '.' Digits? | '.' Digits) ExponentPart? [fFdD]?
             |       Digits (ExponentPart [fFdD]? | [fFdD])
             ;

HEX_FLOAT_LITERAL:  '0' [xX] (HexDigits '.'? | HexDigits? '.' HexDigits) [pP] [+-]? Digits [fFdD]?;

BOOL_LITERAL:       'true'
            |       'false'
            ;

CHAR_LITERAL:       '\'' (~['\\\r\n] | EscapeSequence) '\'';

STRING_LITERAL:     '"' (~["\\\r\n] | EscapeSequence)* '"' ;

NULL_LITERAL:       'null';


// Whitespace and comments

WS:                 [ \t\r\n\u000C]+ -> channel(HIDDEN);
COMMENT:            '/*' .*? '*/'    -> channel(HIDDEN);
LINE_COMMENT:       '//' ~[\r\n]*    -> channel(HIDDEN);

// Identifiers

IDENTIFIER:         Letter LetterOrDigit*;

// Fragment rules

fragment ExponentPart
    : [eE] [+-]? Digits
    ;

fragment EscapeSequence
    : '\\' [btnfr"'\\]
    | '\\' ([0-3]? [0-7])? [0-7]
    | '\\x' ([0-3]? [0-7])? [0-7]
    | '\\' 'u'+ HexDigit HexDigit HexDigit HexDigit
    ;

fragment HexDigits
    : HexDigit ((HexDigit | '_')* HexDigit)?
    ;

fragment HexDigit
    : [0-9a-fA-F]
    ;

fragment Digits
    : [0-9] ([0-9_]* [0-9])?
    ;

fragment LetterOrDigit
    : Letter
    | [0-9]
    ;

fragment Letter
    : [a-zA-Z$_] // these are the "java letters" below 0x7F
    | ~[\u0000-\u007F\uD800-\uDBFF] // covers all characters above 0x7F which are not a surrogate
    | [\uD800-\uDBFF] [\uDC00-\uDFFF] // covers UTF-16 surrogate pairs encodings for U+10000 to U+10FFFF
    ;