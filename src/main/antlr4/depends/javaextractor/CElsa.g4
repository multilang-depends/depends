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
   namespaceDefinition |
   expressionList //only for old style c
;

macroInvocation:
   Identifier ('<' templateArgumentList? '>')? ('(' expressionList? ')')+ ;

// -------------------- a.1 keywords --------------------

typedefName :
   Identifier
;

namespaceName :
   originalNamespaceName|
   namespaceAlias
;

 originalNamespaceName :
   Identifier
;

 namespaceAlias :
   Identifier
;

 className :
   Identifier|
   templateId
;

 enumName :
   Identifier
;

 templateName :
   Identifier
;


stringBlock:
   Stringliteral Stringliteral* ;

// -------------------- a.4 expressions --------------------

 idExpression :
   unqualifiedId|
   qualifiedId
;

 unqualifiedId :
   Identifier
   |'~' className
   |operatorFunctionId
   |conversionFunctionId
   |templateId
;

template :
   'template'
;

 qualifiedId :
   '::'? nestedNameSpecifier template? unqualifiedId  # qualifiedIdOfNameSpace
   |'::' Identifier                                   # qualifiedIdOfGlobalId 
   |'::' operatorFunctionId                           # qualifiedIdOfOperatorFunction
   |'::' templateId                                   # qualifiedIdOfTemplateId
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
   deleteExpression |
   throwExpression
;


expression
    : primaryExpression
    | expression bop='.'
    (
    	template? idExpression
    	|pseudoDestructorName
    )
    | expression bop='::'
    (
    	template? idExpression
    	|pseudoDestructorName
    )
    |expression bop='->'
    (
    	template? idExpression
    	|pseudoDestructorName
    )
    | prefix=('+'|'-'|'++'|'--'|'*'|'&'|'!'|'~') expression
    | expression ('<' templateArgumentList? '>')? (expressionList)+
    | expression '[' expression ']'
    | 'sizeof' expression
    |  'sizeof' '(' typeId ')'
    | 'typename' '::'? nestedNameSpecifier Identifier '(' expressionList ')'
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
expressionList:
expressions 
| '(' expressions? ')'
| '{' expressionList (',' expressionList)* '}'
| '(' expressionList ')'
;
/* 	
methodCall: 
	'~'? Identifier ('<' templateArgumentList? '>')? ('(' expressionList? ')')+
	|'(' '*'? Identifier ')' ('<' templateArgumentList? '>')? ('(' expressionList? ')')+
	|'this' '->' '(' expressionList? ')'
	;
*/
expressions :
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
   Identifier ':' statement|
   'case' expression ':' statement|
   'default' ':' statement      // note: linux kernel contains 'switch (..) : ... default: ;' ..
;


 expressionStatement :
   expressionList ';'
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
   expressionList
   //| typeSpecifier+ declarator '=' expression 
;
  
 iterationStatement :
   'while' '(' condition ')' statement|
   'do' statement 'while' '(' condition ')' ';'|
   'for' '(' forInitStatement condition? ';' expressionList? ')' statement
;

 forInitStatement :
   expressionStatement|
   simpleDeclaration
;

 jumpStatement :
   'break' ';'|
   'continue' ';'|
   'return' expression? ';'|
   'goto' Identifier ';'
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
   functionPtr |
   'friend'|
   'typedef'
;
functionPtr:
    '(' '*' idExpression ')' parameters;



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
   classKey '::'? nestedNameSpecifier? Identifier|
   'enum' '::'? nestedNameSpecifier? Identifier|
   'typename' '::'? nestedNameSpecifier Identifier|
   'typename' '::'? nestedNameSpecifier template? templateId
;

 enumSpecifier :
   'enum' Identifier? '{' enumeratorList? ','? '}'
;

 enumeratorList :
   enumeratorDefinition (',' enumeratorDefinition)*
;

 enumeratorDefinition :
   Identifier |
   Identifier '=' expression
;

 namespaceDefinition :
   'namespace' Identifier? '{' declaration* '}'
;

 namespaceAliasDefinition :
   'namespace' Identifier '=' qualifiedNamespaceSpecifier ';'
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
   'asm' '(' stringBlock ')' ';'
;

 linkageSpecification :
   'extern' Stringliteral '{' declaration* '}'|
   'extern' Stringliteral declaration
;


// -------------------- a.7 declarators --------------------

 declarator :
   declaratorId 
   |ptrOperator declaratorId 
   |declaratorId+ parameters cvQualifierSeq? exceptionSpecification? 
   |declaratorId+ '[' expression? ']'
   |declaratorId+ parameters simpleDeclaration*  
   |'(' declarator ')'
;
parameters: 
'(' parameterDeclarationClause? ')' |
'(' parameters ')' 
;
 	

 ptrOperator :
   '*' cvQualifierSeq?|
   '&'  |
   '[' ']'|
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
   parameterDeclaration (',' parameterDeclaration)*  (',' '...')?
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
   '{'  expressionList ','? '}'|
   '{' '}' |
   expressionList
;

// -------------------- a.8 classes --------------------


 classSpecifier :
   classHead '{' memberSpecification* '}'
;

 classHead :
   classKey Identifier? baseClause?|
   classKey nestedNameSpecifier Identifier baseClause?|
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
   Identifier? ':' expression  // bitfield with optional name
;

 pureSpecifier :
   '=' Integerliteral        // standard says '0' here but that's not one of my TOKens..
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
   Identifier
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
   'class' Identifier?|
   'class' Identifier? '=' typeId|
   'typename' Identifier? '=' typeId|
   'typename' Identifier?|
   'template' '<' templateParameterList '>' 'class' Identifier?|
   'template' '<' templateParameterList '>' 'class' Identifier? '=' idExpression
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
LINE_ESCAPE:       '\\' ('\r' '\n'? | '\n') -> skip;


MultiLineMacro
:
    '#' (~[\n]*? '\\' '\r'? '\n')+ ~[\n]+ -> channel(HIDDEN)
;

Directive
:
    '#' ~[\n]* -> channel(HIDDEN)
;



// Literals

// Whitespace and comments

WS:                 [ \t\r\n\u000C]+ -> channel(HIDDEN);
COMMENT:            '/*' .*? '*/'    -> channel(HIDDEN);
LINE_COMMENT:       '//' ~[\r\n]*    -> channel(HIDDEN);
GCC_ATTRIBUTE :     '__attribute__' '((' Stringliteral '))' ->skip;

fragment
Universalcharactername
:
	'\\u' [0-9a-fA-F]*
	| '\\U' [0-9a-fA-F]*
;

Identifier
:
/*
	Identifiernondigit
	| Identifier Identifiernondigit
	| Identifier DIGIT
	*/
	Identifiernondigit
	(
		Identifiernondigit
		| DIGIT
	)*
;

fragment
Identifiernondigit
:
	NONDIGIT
	| Universalcharactername
	/* other implementation defined characters*/
;

fragment
NONDIGIT
:
	[a-zA-Z_]
;

fragment
DIGIT
:
	[0-9]
;

literal
:
	stringBlock
	| Integerliteral
	| Characterliteral
	| Floatingliteral
	| Booleanliteral
	| 'nullptr'
	| 'null'
;

Integerliteral
:
	Decimalliteral
	| Octalliteral
	| Hexadecimalliteral 
	| Binaryliteral 
;


Decimalliteral:    ('0' | [1-9] (Digits? | '_'+ Digits)) Integersuffix?;
Hexadecimalliteral:        '0' [xX] [0-9a-fA-F] ([0-9a-fA-F_]* [0-9a-fA-F])? Integersuffix?;
Octalliteral:        '0' '_'* [0-7] ([0-7_]* [0-7])? Integersuffix?;
Binaryliteral:     '0' [bB] [01] ([01_]* [01])? Integersuffix?;
                    
Floatingliteral:      (Digits '.' Digits? | '.' Digits) ExponentPart? [fFdD]?
             |       Digits (ExponentPart [fFdD]? | [fFdD])
             ;

Booleanliteral:       'true'
            |       'false'
            ;

Characterliteral:       '\'' (~['\\\r\n] | EscapeSequence) '\'' |
                        '\'' '\\' [xX] [0-9a-fA-F]+ '\'';

Stringliteral:    ('u8'| 'u'| 'U'| 'L')? '"' (~["\r\n\\] | EscapeSequence | '\\\n' | '\\\r\n' | '\\\r')* '"' ;

//Stringliteral:    ('u8'| 'u'| 'U'| 'L')? '"' SChar* '"' ;


fragment
SChar
    :   ~["\\\r\n]
    |   EscapeSequence
    |   '\\\n'   // Added line
    |   '\\\r\n' // Added line
    ;

IDENTIFIER:         Letter LetterOrDigit*;

// Fragment rules
fragment
Integersuffix
:
	Unsignedsuffix Longsuffix?
	| Unsignedsuffix Longlongsuffix?
	| Longsuffix Unsignedsuffix?
	| Longlongsuffix Unsignedsuffix?
;

fragment
Unsignedsuffix
:
	[uU]
;

fragment
Longsuffix
:
	[lL]
;

fragment
Longlongsuffix
:
	'll'
	| 'LL'
;

fragment ExponentPart
    : [eE] [+-]? Digits
    ;

fragment EscapeSequence
    : '\\' [btnfr"'\\]
    | '\\' ([0-3]? [0-7])? [0-7]
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

