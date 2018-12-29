
grammar Corundum;

prog
:
	expression_list
;

expression_list
:
	expression terminator
	| expression_list expression terminator
	| terminator
;

statement_expression_list
:
	expression
	| statement_expression_list terminator expression
;

expression
:
	function_definition
	| class_definition
	| module_definition
	| begin_block
	| end_block
	| require_statement
	| return_statement 
	| raise_statement
	| yield_statement
	| arg
	| WHEN arg THEN statement_expression_list END
	| expression IF arg 
	| expression UNLESS arg 
	| arg terminator? WHILE arg 
	| arg terminator? UNTIL arg 
	| ELSE terminator statement_expression_list
	| ELSIF arg terminator statement_expression_list
	| WHEN arg terminator statement_expression_list
	| IF arg terminator expression_list END
	| CASE arg terminator expression_list END
	| CASE terminator (WHEN arg THEN statement_expression_list terminator)* END
	| UNLESS arg terminator expression_list END
	| WHILE arg do_keyword expression_list END
	| UNTIL arg do_keyword expression_list END
	| WHILE arg do_keyword END
	| BEGIN terminator expression_list END (terminator? WHILE arg)?
	| FOR args IN expression terminator? expression_list END 
	| arg DO terminator? block_params terminator? statement_expression_list? terminator? END 
	| arg DO terminator expression_list END 
	| BREAK
	| NEXT
	| REDO
	| RETRY
	| ENSURE
	| THROW arg (IF arg )? 
	| CATCH args DO expression_list END 
;

require_statement
:
	REQUIRE String
;
return_statement: 
	RETURN args?
;

yield_statement: 
	YIELD args?
;
YIELD: 'yield';

raise_statement:
	RAISE args
;

module_definition: MODULE Identifier expression_list END;

begin_block
:
	BEGIN_BLOCK '{' expression_list '}' 
;

end_block
:
	END_BLOCK '{' expression_list '}'
;


function_definition
:
	function_definition_header expression_list? END
;

class_definition: 
   	class_header expression_list? END
;

class_header: 
	CLASS variable_path superclass?
;

superclass: '<' variable_path terminator;

CLASS: 'class';

function_definition_header
:
	DEF function_name function_definition_params? CRLF
;

function_name
:   variable_path ('.' Identifier)* ('?')?
;

function_definition_params
:
	LEFT_RBRACKET ( arg (COMMA arg)*)? RIGHT_RBRACKET
	| arg (COMMA arg)*
;

function_call
:
	function_name LEFT_RBRACKET function_call_params RIGHT_RBRACKET
	| function_name function_call_params
	| function_name LEFT_RBRACKET RIGHT_RBRACKET
	| function_name 
;

function_call_params
:
	function_param
	| function_call_params COMMA function_param
;

function_param
:
	(
		arg
		| Identifier ASSIGN arg
	)
;


args
:
	arg
	(
		',' arg
	)*
;

arg
:
	variable_path
	| DEFINED variable_path
	| LEFT_RBRACKET arg RIGHT_RBRACKET
	| (PLUS| MINUS|MUL) arg
	| (NOT| BIT_NOT) arg
	| arg (ASSIGN | PLUS_ASSIGN | MINUS_ASSIGN |MUL_ASSIGN|DIV_ASSIGN|MOD_ASSIGN | EXP_ASSIGN) terminator? arg
	| arg(LESS|GREATER|LESS_EQUAL| GREATER_EQUAL) terminator? arg
	| arg(MUL|DIV|MOD|PLUS|MINUS|BIT_SHL|BIT_SHR|BIT_AND|BIT_OR|BIT_XOR|EXP) terminator? arg
	| arg(BIT_OR_ASSIGN|BIT_AND_ASSIGN|OR_ASSIGN|AND_ASSIGN) terminator? arg
	| arg(EQUAL| NOT_EQUAL | NOT_EQUAL2) terminator? arg
	| arg(OR| AND) terminator? arg
	| arg '?' arg ':' arg
	| '{' assoc terminator?(',' terminator? assoc)* ','? terminator? '}'
	| '[' arg terminator? (',' terminator? arg)* ','? terminator? ']'
	| variable_path ASSIGN LEFT_SBRACKET RIGHT_SBRACKET
	| variable_path LEFT_SBRACKET arg RIGHT_SBRACKET
	| function_call block?
	| arg '.' function_call
	| arg block
;

variable_path:
	Identifier
	|IdGlobal
	|IdClass
	|IdMember
	|IdColon
	| Float
	| variable_path (ANDDOT| COLON2) variable_path ('?')?
	| variable_path (DOT2|DOT3) variable_path
	| String
	| Integer
	| Regex
	|(TRUE| FALSE)
	| NIL 
	| Float '.'
	;

block: '{' block_params? statement_expression_list '}';

block_params: '|' args '|';

CATCH: 'catch';

IdColon: ':' Identifier;

THROW: 'throw';

IdClass: '@' '@' Identifier;
IdMember: '@' Identifier;

do_keyword: (DO|':') terminator | terminator ;



assoc
:
	arg ASSOC arg
;

terminator
:
	terminator SEMICOLON
	| terminator CRLF
	| terminator SL_COMMENT
	| SEMICOLON
	| CRLF
	| SL_COMMENT
;

MODULE: 'module';

ENSURE: 'ensure';

RAISE: 'raise';

THEN
:
	'then'
;

WHEN
:
	'when'
;

DEFINED
:
	'defined?'
;

DOT2
:
	'..'
;

DOT3
:
	'...'
;

COLON2
:
	'::'
;

ANDDOT
:
	'&.'
;

COMMA
:
	','
;

SEMICOLON
:
	';'
;

CRLF
:
	'\r'? '\n'
;

BEGIN_BLOCK
:
	'BEGIN'
;

END_BLOCK
:
	'END'
;

REQUIRE
:
	'require'
;

DO
:
	'do'
;

FOR
:
	'for'
;

IN:
'in';

WHILE
:
	'while'
;

BEGIN:
'begin';

END
:
	'end'
;

DEF
:
	'def'
;

RETURN
:
	'return'
;

PIR
:
	'pir'
;

IF
:
	'if'
;

ELSE
:
	'else'
;

ELSIF
:
	'elsif'
;

UNLESS
:
	'unless'
;

RETRY
:
	'retry'
;

BREAK
:
	'break'
;

TRUE
:
	'true'
;

FALSE
:
	'false'
;

REDO
:
	'redo'
;

NEXT
:
	'next'
;

CASE
:
	'case'
;

UNTIL
:
	'until'
;

PLUS
:
	'+'
;

MINUS
:
	'-'
;

MUL
:
	'*'
;

DIV
:
	'/'
;

MOD
:
	'%'
;

EXP
:
	'**'
;

EQUAL
:
	'=='
;

NOT_EQUAL2:
    '=~'
;

NOT_EQUAL
:
	'!='
;

GREATER
:
	'>'
;

LESS
:
	'<'
;

LESS_EQUAL
:
	'<='
;

GREATER_EQUAL
:
	'>='
;

ASSIGN
:
	'='
;

ASSOC
:
	'=>'
;

PLUS_ASSIGN
:
	'+='
;

MINUS_ASSIGN
:
	'-='
;

MUL_ASSIGN
:
	'*='
;

DIV_ASSIGN
:
	'/='
;

BIT_OR_ASSIGN
:
	'|='
;

BIT_AND_ASSIGN
:
	'&='
;

OR_ASSIGN
:
	'||='
;

AND_ASSIGN
:
	'&&='
;

MOD_ASSIGN
:
	'%='
;

EXP_ASSIGN
:
	'**='
;

BIT_AND
:
	'&'
;

BIT_OR
:
	'|'
;

BIT_XOR
:
	'^'
;

BIT_NOT
:
	'~'
;

BIT_SHL
:
	'<<'
;

BIT_SHR
:
	'>>'
;

AND
:
	'and'
	| '&&'
;

OR
:
	'or'
	| '||'
;

NOT
:
	'not'
	| '!'
;

LEFT_RBRACKET
:
	'('
;

RIGHT_RBRACKET
:
	')'
;

LEFT_SBRACKET
:
	'['
;

RIGHT_SBRACKET
:
	']'
;

NIL
:
	'nil'
;

SL_COMMENT
:
	(
		'#' ~( '\r' | '\n' )* '\r'? '\n'
	) 
;

ML_COMMENT
:
	(
		'=begin' .*? '=end' '\r'? '\n'
	) -> skip
;

WS
:
	(
		' '
		| '\t'
	)+ -> skip
;

Integer
:
	Digits ExponentPart?
	| HEX_LITERAL ExponentPart?
	| OCT_LITERAL ExponentPart?
	| BINARY_LITERAL ExponentPart?
	| '?'
	(
		'\\'? [a-zA-Z_]
	)
;

Float
:
	[0-9]* '.' [0-9]+ ExponentPart?
;

Identifier
:
	[a-zA-Z_] [a-zA-Z0-9_]*
;

IdGlobal
:
	'$' Identifier
;

Regex:
    '/'  ~( '\n' | '\r' | '/' )+ '/'
;
String
:
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
	| '%' [QqWwxrsi]  ('{'|'['|'('|'<'|'|') ~( '\n' | '\r' )* (|'}'|']'|')'|'>'|'|') 
;

//| '%' [QqWwxrsi] [\{\|\[\(] ~( '\n' | '\r' )* [\}\|\)\]]

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