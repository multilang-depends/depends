/*
 * [The "BSD license"]
 *  Copyright (c) 2014 Alexander Belov
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions
 *  are met:
 *
 *  1. Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *  2. Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *  3. The name of the author may not be used to endorse or promote products
 *     derived from this software without specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 *  IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 *  OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 *  IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 *  INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 *  NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 *  DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 *  THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 *  THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/*
 *  A grammar for Ruby-like language written in ANTLR v4.
 *  You can find compiler into Parrot VM intermediate representation language
 *  (PIR) here: https://github.com/AlexBelov/corundum
 * 
 * 
 * History:
 *  2018.12.24 derived from antlr/grammarv4 project
 *  2018.12.27 add additional grammar support by gangz (gangz@emergentdesign.cn)
 *             - BEGIN/END block
 *             - support of ".ord"
 *             - supoort of ?a (ascii)
 *             - support of hex/oct/bin literals
 *             - support of ExponentPart in digit literals
 *             - support underscore of number literals
 *             - support \' in string literals
 *             - support %q %Q string literals
 *             - support ASSOC (=>) hash value
 *             - support arrays [,,,]
 *  To be added
 *             - HERE document 
 *             - RANGE (..)
 */
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

expression
:
	function_definition
	| require_block
	| arg
	| pir_inline
	| begin_block
	| end_block
	| RETURN args? 
	| RAISE function_call_params
	| WHEN arg THEN statement_expression_list END
	| expression IF arg 
	| expression UNLESS arg 
	| class_definition
	| module_definition
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


require_block
:
	REQUIRE String
;

pir_inline
:
	PIR CRLF pir_expression_list END
;

pir_expression_list
:
	expression_list
;

function_definition
:
	function_definition_header expression_list? END
;

class_definition: 
   	class_header expression_list? END
;

class_header: 
	CLASS cpath superclass?
;

superclass: '<' arg terminator;

cpath: Identifier;

CLASS: 'class';


function_definition_header
:
	DEF function_name function_definition_params? CRLF
;

function_name
:   Identifier ('.' Identifier)* ('?')?
;

function_definition_params
:
	LEFT_RBRACKET ( arg (COMMA arg)*)? RIGHT_RBRACKET
	| arg (COMMA arg)*
;

function_call
:
	name = function_name LEFT_RBRACKET params = function_call_params 
	RIGHT_RBRACKET
	| name = function_name params = function_call_params
	| name = function_name LEFT_RBRACKET RIGHT_RBRACKET
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
		| function_named_param
	)
;


function_named_param
:
	Identifier op = ASSIGN
	(
		arg
	)
;

function_call_assignment
:
	function_call
;

init_expression
:
	for_init_list
;

for_init_list
:
	for_init_list COMMA arg
	| arg
;

loop_expression
:
	for_loop_list
;

for_loop_list
:
	for_loop_list COMMA arg
	| arg
;

statement_expression_list
:
	expression
	| statement_expression_list terminator expression
;


array_definition
:
	LEFT_SBRACKET array_definition_elements RIGHT_SBRACKET
;

array_definition_elements
:
	(
		arg
	)
	| array_definition_elements COMMA
	(
		arg
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
	Identifier
	|ID_GLOBAL
	|IdClass
	|IdMember
	|IdColon
	| String
	| Float (.)?
	| Integer
	| Regex
	|(TRUE| FALSE)
	| NIL
	| LEFT_RBRACKET arg RIGHT_RBRACKET
	| arg('.'| ANDDOT| COLON2) arg ('?')?
	| MUL arg
	| arg DOT2 arg
	| arg DOT3 arg
	| arg (ASSIGN | PLUS_ASSIGN | MINUS_ASSIGN |MUL_ASSIGN|DIV_ASSIGN|MOD_ASSIGN | EXP_ASSIGN) terminator? arg
	|(NOT| BIT_NOT) arg
	|(PLUS| MINUS) arg
	| arg(LESS|GREATER|LESS_EQUAL| GREATER_EQUAL) terminator? arg
	| arg(MUL|DIV|MOD|PLUS|MINUS|BIT_SHL|BIT_SHR|BIT_AND|BIT_OR|BIT_XOR|EXP) terminator? arg
	| arg(BIT_OR_ASSIGN|BIT_AND_ASSIGN|OR_ASSIGN|AND_ASSIGN) terminator? arg
	| arg(EQUAL| NOT_EQUAL | NOT_EQUAL2) terminator? arg
	| arg(OR| AND) terminator? arg
	| DEFINED arg
	| arg '?' arg ':' arg
	| '{' assoc terminator?(',' terminator? assoc)* ','? terminator? '}'
	| '[' arg terminator? (',' terminator? arg)* ','? terminator? ']'
	| Identifier ASSIGN LEFT_SBRACKET RIGHT_SBRACKET
	| Identifier LEFT_SBRACKET arg RIGHT_SBRACKET
	| ID_GLOBAL LEFT_SBRACKET arg RIGHT_SBRACKET
	| function_call block?
	| arg block
	| IF arg terminator statement_expression_list terminator END
	| CASE arg terminator statement_expression_list terminator END
	| CASE terminator (WHEN arg THEN statement_expression_list terminator)* END
	| UNLESS arg terminator statement_expression_list terminator END
	| ELSE terminator statement_expression_list
	| ELSIF arg terminator statement_expression_list
	| WHEN arg terminator statement_expression_list
	| WHILE arg do_keyword statement_expression_list terminator END
	| UNTIL arg do_keyword statement_expression_list terminator END
	| WHILE arg do_keyword END
	| BEGIN terminator statement_expression_list terminator END (terminator? WHILE arg)?
	| arg terminator? WHILE arg terminator
	| arg terminator? UNTIL arg terminator
	| FOR args IN expression terminator? statement_expression_list terminator END 
	| arg DO terminator? block_params terminator? statement_expression_list? terminator? END 
	| arg DO terminator statement_expression_list terminator END 
	| BREAK
	| NEXT
	| REDO
	| RETRY
	| ENSURE
	| THROW arg (IF arg )? 
	| CATCH args DO expression? terminator? statement_expression_list terminator END 

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

ID_GLOBAL
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