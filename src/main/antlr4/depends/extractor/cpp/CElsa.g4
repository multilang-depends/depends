grammar CElsa;

@lexer::members{
	public static final int CHANNEL_COMMENT = 2;
}

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

 qualifiedId :
   DotDot? nestedNameSpecifier Template? unqualifiedId 
   |NestedName
   |NestedName operatorFunc
   |Identifier DotDot operatorFunctionId
   |DotDot Identifier                                   
   |DotDot operatorFunctionId                          
   |DotDot templateId                                   
;

 nestedNameSpecifier :
   classOrNamespaceName DotDot nestedNameSpecifier?|
   classOrNamespaceName DotDot Template nestedNameSpecifier
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
    	Template? idExpression
    	|pseudoDestructorName
    )
    | expression bop=DotDot
    (
    	Template? idExpression
    	|pseudoDestructorName
    )
    |expression bop='->'
    (
    	Template? idExpression
    	|pseudoDestructorName
    )
    | prefix=('+'|'-'|'++'|'--'|'*'|'&'|'!'|'~') expression
    | expression ('<' templateArgumentList? '>')? (expressionList)+
    | expression '[' expression ']'
    | 'sizeof' expression
    |  'sizeof' '(' typeId ')'
    | 'typename' DotDot? nestedNameSpecifier Identifier '(' expressionList ')'
    | 'typename' DotDot? nestedNameSpecifier Template? templateId '(' expressionList ')'
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
   DotDot? nestedNameSpecifier? typeName DotDot '~' typeName|
   DotDot? nestedNameSpecifier Template templateId DotDot '~' typeName|
   DotDot? nestedNameSpecifier? '~' typeName
;


 newExpression :
   DotDot? 'new' newPlacement? newTypeId newInitializer?|
   DotDot? 'new' newPlacement? '(' typeId ')' newInitializer?
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
   DotDot? 'delete' expression|
   DotDot? 'delete' '[' ']' expression
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
   Auto|
   Register|
   Static|
   Extern|
   Mutable
;

functionSpecifier :
   Inline|
   Virtual|
   Explicit
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
   DotDot? nestedNameSpecifier? typeName|
   NestedName |
   DotDot? nestedNameSpecifier Template templateId|
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
   classKey DotDot? nestedNameSpecifier? Identifier|
   'enum' DotDot? nestedNameSpecifier? Identifier|
   'typename' DotDot? nestedNameSpecifier Identifier|
   'typename' DotDot? nestedNameSpecifier Template? templateId
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
   Namespace Identifier? '{' declaration* '}'
;

 namespaceAliasDefinition :
   Namespace Identifier '=' qualifiedNamespaceSpecifier ';'
;

 qualifiedNamespaceSpecifier :
   DotDot? nestedNameSpecifier? namespaceName
;

 usingDeclaration :
   Using 'typename'? DotDot? nestedNameSpecifier unqualifiedId ';'|
   Using 'typename'? NestedName |
   Using DotDot unqualifiedId ';'
;

 usingDirective :
   Using Namespace DotDot? nestedNameSpecifier? namespaceName ';'
;


 asmDefinition :
   Asm '(' stringBlock ')' ';'
;


 linkageSpecification :
   Extern Stringliteral '{' declaration* '}'|
   Extern Stringliteral declaration
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
   DotDot? nestedNameSpecifier '*' cvQualifierSeq?     // pointer to member
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
   DotDot? nestedNameSpecifier? typeName
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
   functionDefinition ';'?                                    #memberDeclarationFunctionDefine
   |declSpecifier* memberDeclaratorList? ';'                  #memberDeclarationGeneral
   |DotDot? nestedNameSpecifier Template? unqualifiedId ';'   #memberDeclarationDontKnow1
   |NestedName ';'                                            #memberDeclarationDontKnow2
   |usingDeclaration                                          #memberDeclarationUsing
   |templateDeclaration                                       #memberDeclarationTemplate
   |macroInvocation                                           #memberDeclarationMacro
;

 memberDeclaratorList :
   memberDeclarator (',' memberDeclarator)*
;

 memberDeclarator :
   declarator pureSpecifier?            #memberFunction
   |declarator constantInitializer?     #memberOther
   |Identifier? ':' expression          #memberWithBitField
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
   baseSpecifier ( ',' baseSpecifier)*
;
 

 baseSpecifier :
   DotDot? nestedNameSpecifier? className|
   NestedName |
   Virtual accessSpecifier? DotDot? nestedNameSpecifier? className|
   Virtual accessSpecifier? DotDot? NestedName|
   accessSpecifier Virtual?   DotDot? nestedNameSpecifier? className|
   accessSpecifier Virtual?   DotDot? NestedName
;

 accessSpecifier :
   'private'|
   'protected'|
   'public'
;


// -------------------- a.10 special member functions --------------------

 conversionFunctionId :
   Operator typeSpecifier+ 
;

 ctorInitializer :
   ':'  memInitializer (',' memInitializer)*
;

 memInitializer :
   memInitializerId '(' expressionList? ')'
;

 memInitializerId :

   DotDot? nestedNameSpecifier? className|
   Identifier
;




// -------------------- a.11 overloading --------------------

 operatorFunctionId :
   Operator operatorFunc
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
   Template '<' templateParameterList '>' declaration
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
   Template '<' templateParameterList '>' 'class' Identifier?|
   Template '<' templateParameterList '>' 'class' Identifier? '=' idExpression
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
   Template declaration
;

 explicitSpecialization :
   Template '<' '>' declaration
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

Operator: 'operator';  
Template: 'template';
Mutable: 'mutable';
Static: 'static';
Extern: 'extern';
Register: 'register';
Auto: 'auto';
Virtual: 'virtual';
Inline: 'inline';
Explicit: 'explicit';
Namespace: 'namespace';
Using: 'using';
Asm: 'asm';

NestedName:
    DotDot? Identifier (DotDot Identifier)+
;

// Literals

// Whitespace and comments

WS:                 [ \t\r\n\u000C]+ -> channel(HIDDEN);
COMMENT:            '/*' .*? '*/'    -> channel(2);
LINE_COMMENT:       '//' ~[\r\n]*    -> channel(2);
GCC_ATTRIBUTE :     '__attribute__' '((' Identifier '))' ->skip;

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

// symbols
DotDot: '::';


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

