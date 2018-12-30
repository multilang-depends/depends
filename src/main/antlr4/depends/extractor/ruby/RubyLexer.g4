lexer grammar RubyLexer;

@lexer::members{
	String hereDocSymbol = "";
    String quotedStringStartSymbol = "";
	StringBuilder stringBuffer;
	boolean isHereDocSymbol(String text){
		String theSym =  text.replace("\r","").replace("\n","").replace("\"","");
		hereDocSymbol =  hereDocSymbol.replace("\r","").replace("\n","").replace("<<-","").replace("<<","").replace("\"","");
		return theSym.equals(hereDocSymbol);
	}
	
	boolean isQuotedPair(String text){
		if (quotedStringStartSymbol.equals("(") && text.equals(")")) return true;
		if (quotedStringStartSymbol.equals("[") && text.equals("]")) return true;
		if (quotedStringStartSymbol.equals("{") && text.equals("}")) return true;
		if (quotedStringStartSymbol.equals("<") && text.equals(">")) return true;
		if (quotedStringStartSymbol.equals("|") && text.equals("|")) return true;
		if (quotedStringStartSymbol.equals(text)) return true;
		return false;
	}
	
}

//HereDoc
HereDoc1: '<<' '-'? Identifier  
		{
			hereDocSymbol = getText(); 
			stringBuffer = new StringBuilder();
			mode(HERE_DOC_MODE);
			skip();
		} 
		;
		
HereDoc2: '<<' '-'?  StringFragment 
		{
			hereDocSymbol = getText(); 
			stringBuffer = new StringBuilder();
			mode(HERE_DOC_MODE);
			skip();
		} 
		;

QuotedStringStart: '%' [QqWwxrsi] ~[A-Za-z0-9]
		{
			quotedStringStartSymbol = getText().substring(2); 
			stringBuffer = new StringBuilder();
			skip();
			mode(QUOTED_STR_MODE);
		} 
		;
// Keywords

BEGIN:			'begin';
BEGIN_BLOCK:	'BEGIN';
BREAK:			'break';
CASE:			'case';
CATCH:			'catch';
CLASS:			'class';
DEF:			'def';
DEFINED:		'defined?';
DO:				'do';
ELSE:			'else';
ELSIF:			'elsif';
END_BLOCK:		'END';
END:			'end';
ENSURE:			'ensure';
FALSE:			'false';
FOR:			'for';
IF:				'if';
IN:				'in';
MODULE:			'module';
NEXT:			'next';
NIL:			'nil';
RAISE:			'raise';
REDO:			'redo';
REQUIRE:		'require';
RESCURE:		'rescue';
RETRY:			'retry';
RETURN:			'return';
THEN:			'then';
THROW:			'throw';
TRUE:			'true';
UNLESS:			'unless';
UNTIL:			'until';
WHEN:			'when';
WHILE:			'while';
YIELD:			'yield';
// Literals

Integer
	:
	Sign? Digits ExponentPart?
	| HEX_LITERAL ExponentPart?
	| OCT_LITERAL ExponentPart?
	| BINARY_LITERAL ExponentPart?
	| '?'('\\'? [a-zA-Z_])
;


Float
:
	Sign? [0-9]* '.' [0-9]+ ExponentPart?
;



Regex:
    '/'  ~( '\n' | '\r' | '/' )+ '/'
;
String
:
	StringFragment
	| '%' [QqWwxrsi]  ('{'|'['|'('|'<'|'|') ~( '\n' | '\r' )* (|'}'|']'|')'|'>'|'|') 
;


// Separators
COMMA:	',';
SEMICOLON:	';';
CRLF:	'\r'? '\n';
COLON: ':';
DOT: '.';
LEFT_RBRACKET:	'(';
RIGHT_RBRACKET:	')';
LEFT_SBRACKET:	'[';
RIGHT_SBRACKET:	']';
LEFT_PAREN: '{';
RIGHT_PAREN: '}';
DOT2:	'..';
DOT3:	'...';
COLON2:	'::';
ANDDOT:	'&.';
QUESTION: '?';
// Operators
PLUS:	'+';
MINUS:	'-';
MUL:	'*';
DIV:	'/';
MOD:	'%';
EXP:	'**';
EQUAL:	'==';
NOT_EQUAL2:    '=~';
NOT_EQUAL:	'!=';
GREATER:	'>';
LESS:	'<';
LESS_EQUAL:	'<=';
GREATER_EQUAL:	'>=';
ASSIGN:	'=';
ASSOC:	'=>';
PLUS_ASSIGN:	'+=';
MINUS_ASSIGN:	'-=';
MUL_ASSIGN:	'*=';
DIV_ASSIGN:	'/=';
BIT_OR_ASSIGN:	'|=';
BIT_AND_ASSIGN:	'&=';
OR_ASSIGN:	'||=';
AND_ASSIGN:	'&&=';
MOD_ASSIGN:	'%=';
EXP_ASSIGN:	'**=';
BIT_AND:	'&';
BIT_OR:	'|';
BIT_XOR:	'^';
BIT_NOT:	'~';
BIT_SHL:	'<<';
BIT_SHR:	'>>';
AND:	'and'	| '&&';
OR:	'or'	| '||';
NOT:	'not'	| '!';
Sign: '+'|'-';
DOLLAR: '$';
AT: '@';
SHARP: '#';

//Comments
SL_COMMENT:	('#' ~( '\r' | '\n' )* '\r'? '\n') ;

ML_COMMENT:	('=begin' .*? '=end' '\r'? '\n') -> skip;

WS:			(' '| '\t')+ -> skip;

//Identifiers
Identifier:	IdentifierFrag;

// Fragment rules
fragment
HEX_FLOAT_LITERAL
:
	'0' [xX]
	(
		HexDigits '.'?
		| HexDigits? '.' HexDigits
	) [pP] [+-]? Digits [fFdD]?
;

fragment
HEX_LITERAL
:
	'0' [xX] [0-9a-fA-F]
	(
		[0-9a-fA-F_]* [0-9a-fA-F]
	)? [lL]?
;

fragment
OCT_LITERAL
:
	'0' '_'* [0-7]
	(
		[0-7_]* [0-7]
	)? [lL]?
;

fragment
BINARY_LITERAL
:
	'0' [bB] [01]
	(
		[01_]* [01]
	)? [lL]?
;

fragment
HexDigits
:
	HexDigit
	(
		(
			HexDigit
			| '_'
		)* HexDigit
	)?
;

fragment
HexDigit
:
	[0-9a-fA-F]
;

fragment
ESCAPED_QUOTE
:
	'\\"'
	| '\\\''
;

fragment
Digits
:
	[0-9]
	(
		[0-9_]* [0-9]
	)?
;

fragment
ExponentPart
:
	[eE] [+-]? Digits
;    

fragment
StringFragment:
'"'
	(
		ESCAPED_QUOTE
		| ~( '\n' | '\r' )
	)*? '"'
	| '\''
	(
		ESCAPED_QUOTE
		| ~( '\n' | '\r' )
	)*? '\''
	;
IdentifierFrag:[a-zA-Z_] [a-zA-Z0-9_]* ;
	
mode HERE_DOC_MODE;
HereDocEnd1:	IdentifierFrag 
					{ 
						if ( isHereDocSymbol(getText())){ 
							mode(DEFAULT_MODE);
							setType(String); 
							setText(stringBuffer.toString());
					  	}else{
					  		stringBuffer.append(getText());
							skip();
					  	}
					}
					;

HereDocEnd2:	StringFragment 
					{ 
						if ( isHereDocSymbol(getText())){ 
							mode(DEFAULT_MODE);
							setType(String); 
							setText(stringBuffer.toString());
						}else{
					  		stringBuffer.append(getText());
							skip();
						}
					}
					;					
AnyInHere:         (.)+? 
					{
						stringBuffer.append(getText());
						skip();
					}
					;
SL_COMMENT_IN_HEREDOC:	('#' ~( '\r' | '\n' )* '\r'? '\n') -> skip;

ML_COMMENT_IN_HEREDOC:	('=begin' .*? '=end' '\r'? '\n') -> skip;
					
mode QUOTED_STR_MODE;
QuotedStringEnd1:	[\r\n] 
					{ 
						mode(DEFAULT_MODE);
						setType(String); 
						setText(stringBuffer.toString());
					}
					;

AnyInQuotedString1:   [A-Za-z0-9]  |' '
					{
						stringBuffer.append(getText());
						skip();	
					}
					;						
							
AnyInQuotedString2:    ~([A-Za-z0-9]|' ')
					{
						if (isQuotedPair(getText())){
							mode(DEFAULT_MODE);
							setType(String); 
							setText(stringBuffer.toString());
							
						}else{
							stringBuffer.append(getText());
							skip();	
						}
					}
;	
					

