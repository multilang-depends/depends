lexer grammar RubyLexer;
@lexer::header{
import java.util.List;	
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
}

@lexer::members{
	
	String hereDocSymbol = "";
    String quotedStringStartSymbol = "";
	StringBuilder stringBuffer;
    Queue<Token> tokenList = new LinkedList<>();
	boolean shouldReleaseBufferedToken = false;
	Token next;
	boolean isHereDocSymbol(String text){
		String theSym =  text.replace("\r","").replace("\n","").replace("\"","").replace("`","");
		hereDocSymbol =  hereDocSymbol.replace("\r","").replace("\n","").replace("<<-","").replace("<<","").replace("\"","").replace("`","");
		return theSym.equals(hereDocSymbol);
	}
	
	void startHereDoc(){
		hereDocSymbol = getText(); 
		stringBuffer = new StringBuilder();
		tokenList = new LinkedList<>();
		shouldReleaseBufferedToken = false;
		while(true) {
			next = nextToken();
			if (next==null) break;
			if (next.getType()==CRLF ||
				next.getType()==SL_COMMENT) {
				break;
			}
			tokenList.add(next);
		}
		mode(HERE_DOC_MODE);
		skip();
	}
	void tryEndHereDoc(){
		if ( isHereDocSymbol(getText())){ 
			mode(DEFAULT_MODE);
			setType(String); 
			setText(stringBuffer.toString());
			shouldReleaseBufferedToken = true;
		}else{
	  		stringBuffer.append(getText());
			skip();
		}
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
	
	@Override
    public Token getToken() { 
    	if (!shouldReleaseBufferedToken) 
    		return super.getToken();
    	if (!tokenList.isEmpty()){
    		return tokenList.peek();
   		}
    	return super.getToken();
    }
    
	
	@Override
	public Token nextToken() {
		if (!shouldReleaseBufferedToken) 
			return super.nextToken();
		if (!tokenList.isEmpty()){
    		Token t = tokenList.remove();
    		return t;
   		}
    	return super.nextToken();
	}
	
}

//HereDoc
HereDoc1: '<<' '-'? Identifier  
		{
			startHereDoc();
		} 
		;
		
HereDoc2: '<<' '-'?  StringFragment 
		{
			startHereDoc();
		} 
		;

HereDoc3:	ShellCommandFrag 
		{
			startHereDoc();
		} 
		;
		
PercentString: '%' [IQRSWXiqrswx] ~[A-Za-z0-9]
		{
			quotedStringStartSymbol = getText().substring(2); 
			stringBuffer = new StringBuilder();
			skip();
			mode(QUOTED_STR_MODE);
		} 
		;
		
// Keywords
ALIAS:			'alias';
BEGIN:			'begin';
BEGIN_BLOCK:	'BEGIN';
BREAK:			'break';
CASE:			'case';
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
NOT:			'not';
RAISE:			'raise';
REDO:			'redo';
RESCUE:			'rescue';
RETRY:			'retry';
RETURN:			'return';
SELF:           'self';
SUPER:          'super';
THEN:			'then';
TRUE:			'true';
UNDEF:          'undef';
UNLESS:			'unless';
UNTIL:			'until';
WHEN:			'when';
WHILE:			'while';
YIELD:			'yield';

//non-keyword but important
REQUIRE:       'require';
// Literals

Integer
	:
	Sign? Digits ExponentPart? NumberTypeSuffix?
	| HEX_LITERAL ExponentPart? NumberTypeSuffix?
	| OCT_LITERAL ExponentPart? NumberTypeSuffix?
	| DEC_LITERAL ExponentPart? NumberTypeSuffix?
	| BINARY_LITERAL ExponentPart? NumberTypeSuffix?
;




Float
:
	Sign? [0-9]* '.' [0-9]+ ExponentPart? NumberTypeSuffix?
;



Regex:
    '/'  ~( '\n' | '\r' | '/' |' ')+ '/' 'i'?
;
String
:
	StringFragment
	| SingleCharacterString
;


DollarSpecial:
    '$' [!?$@;/\\_~&'>*] |
    '$' '.' | '\'';

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
EQUAL3:	'===';
PATTERN_MATCH:    '=~';
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
BIT_XOR_ASSIGN:	'^=';
BIT_NOT_ASSIGN:	'~=';
BIT_SHL_ASSIGN:	'<<=';
BIT_SHR_ASSIGN:	'>>=';
AND:	'and'	| '&&';
OR:	'or'	| '||';
SIGH:   '!';
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
ShellCommand: ShellCommandFrag;

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
DEC_LITERAL
:
	'0' [Dd]? [0-7]
	(
		[0-7_]* [0-7]
	)? [lL]?
;

fragment
OCT_LITERAL
:
	'0' [Oo]? '_'* [0-7]
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

/* i means imaginary(complex) number, r means a rational number */
fragment
NumberTypeSuffix: [ir] | 'ri';

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

fragment
ShellCommandFrag:
'`'
	(
		ESCAPED_QUOTE
		| ~( '\n' | '\r' )
	)*? '`'
;

fragment
IdentifierFrag:[a-zA-Z_] [a-zA-Z0-9_]* ;
	
fragment
SingleCharacterString: 
	'?' ([a-zA-Z0-9_] | EscapedSequenceChar);
	
/*
\nnn           octal bit pattern, where nnn is 1-3 octal digits ([0-7])
\xnn           hexadecimal bit pattern, where nn is 1-2 hexadecimal digits ([0-9a-fA-F])
\unnnn         Unicode character, where nnnn is exactly 4 hexadecimal digits ([0-9a-fA-F])
\u{nnnn ...}   Unicode character(s), where each nnnn is 1-6 hexadecimal digits ([0-9a-fA-F])
\cx or \C-x    control character, where x is an ASCII printable character
\M-x           meta character, where x is an ASCII printable character
\M-\C-x        meta control character, where x is an ASCII printable character
\M-\cx         same as above
\c\M-x         same as above
\c? or \C-?    delete, ASCII 7Fh (DEL)
 */
fragment
EscapedSequenceChar: 
	'\\' [abtnvfres] 
	| '\\' '\\'
	| '\\' (HexDigit) (HexDigit) (HexDigit)
	| '\\' [Xx] (HexDigit) (HexDigit)
	| '\\' [uU] (HexDigit) (HexDigit) (HexDigit) (HexDigit)
	| '\\'  [uU] '{'((HexDigit) (HexDigit) (HexDigit) (HexDigit))+ '}'
	| '\\' [Cc] '-'? PrintableCharacter
	| '\\' [Mm] '-' PrintableCharacter
	| '\\' [Mm] '-' '\\' [Cc] '-'? PrintableCharacter
	| '\\' [Cc] '\\' [Mm] '-'? PrintableCharacter
	| '\\' [Cc] '-'? '?'
;

fragment
PrintableCharacter: [0-9A-Za-z]
                    |[!"#$%&'()*+,-./:;<=>?@[\]^_`~];

	
mode HERE_DOC_MODE;
HereDocEnd1:	IdentifierFrag 
					{ 
						tryEndHereDoc();
					}
					;

HereDocEnd2:	StringFragment 
					{ 
						tryEndHereDoc();
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

/* Make sure the percent string terminated at end of line */
PercentStringLineEnd:	[\r\n] 
					{ 
						mode(DEFAULT_MODE);
						setType(String); 
						setText(stringBuffer.toString());
					}
					;

/* For any character which is alphabetic, add it into string */
AnyInQuotedString1:   ([A-Za-z0-9] |' ')+
					{
						stringBuffer.append(getText());
						skip();	
					}
					;						
/* For string which forms a pair with start symbol, terminated the string
 * Otherwise, add it into string
 */							
AnyInQuotedString2:    ~([A-Za-z0-9]|' ')
					{
						if (isQuotedPair(getText())){
							setType(String); 
							setText(stringBuffer.toString());
							mode(DEFAULT_MODE);
						}else{
							stringBuffer.append(getText());
							skip();	
						}
					}
;	
					

