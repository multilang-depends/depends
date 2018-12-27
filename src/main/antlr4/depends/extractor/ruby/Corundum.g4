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
	| return_statement
	| pir_inline
	| begin_block
	| end_block
	| WHEN arg THEN statement_expression_list END
	| arg IF arg terminator
	| arg UNLESS arg terminator
;

begin_block
:
	BEGIN_BLOCK '{' expression_list '}'
;

end_block
:
	END_BLOCK '{' expression_list '}'
;

global_get
:
	var_name = lvalue op = ASSIGN global_name = ID_GLOBAL
;

global_set
:
	global_name = ID_GLOBAL op = ASSIGN result = arg
;

global_result
:
	ID_GLOBAL
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
	function_definition_header function_definition_body END
;

function_definition_body
:
	expression_list
;

function_definition_header
:
	DEF function_name CRLF
	| DEF function_name function_definition_params CRLF
;

function_name
:
	ID_FUNCTION
	| Identifier
;

function_definition_params
:
	LEFT_RBRACKET RIGHT_RBRACKET
	| LEFT_RBRACKET function_definition_params_list RIGHT_RBRACKET
	| function_definition_params_list
;

function_definition_params_list
:
	function_definition_param_id
	| function_definition_params_list COMMA function_definition_param_id
;

function_definition_param_id
:
	Identifier
;

return_statement
:
	RETURN arg
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

initial_array_assignment
:
	var_id = lvalue op = ASSIGN LEFT_SBRACKET RIGHT_SBRACKET
;

array_assignment
:
	arr_def = array_selector op = ASSIGN arr_val = arg
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

array_selector
:
	Identifier LEFT_SBRACKET
	(
		arg
	) RIGHT_SBRACKET
	| ID_GLOBAL LEFT_SBRACKET
	(
		arg
	) RIGHT_SBRACKET
;

dynamic
:
	Identifier
	| function_call_assignment
	| array_selector
;

comp_var
:
	arg
	| array_selector
	| Identifier
;

lvalue
:
	Identifier
	//| ID_GLOBAL

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
	| String
	| Float
	| Integer
	|
	(
		TRUE
		| FALSE
	)
	| NIL
	| LEFT_RBRACKET arg RIGHT_RBRACKET
	| arg DOT2 arg
	| arg (ASSIGN | PLUS_ASSIGN | MINUS_ASSIGN |MUL_ASSIGN|DIV_ASSIGN|MOD_ASSIGN | EXP_ASSIGN) arg
	|
	(
		NOT
		| BIT_NOT
	) arg
	|
	(
		PLUS
		| MINUS
	) arg
	| MUL arg
	| arg
	(
		LESS
		| GREATER
		| LESS_EQUAL
		| GREATER_EQUAL
	) arg
	| arg
	(
		MUL
		| DIV
		| MOD
		| PLUS
		| MINUS
		| BIT_SHL
		| BIT_SHR
		| BIT_AND
		| BIT_OR
		| BIT_XOR
		| EXP
	) arg
	| arg
	(
		EQUAL
		| NOT_EQUAL
	) arg
	| arg
	(
		OR
		| AND
	) arg
	| DEFINED arg
	| arg '?' arg ':' arg
	| arg
	(
		'.'
		| ANDDOT
		| COLON2
	) arg
	| '{' assoc terminator?
	(
		',' terminator? assoc
	)* ','? terminator? '}'
	| '[' arg terminator?
	(
		',' terminator? arg
	)* ','? terminator? ']'
	| initial_array_assignment
	| array_assignment
	| global_set
	| global_get
	| function_call
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
	| BEGIN terminator statement_expression_list terminator END terminator? WHILE arg
	| arg terminator? WHILE arg terminator
	| arg terminator? UNTIL arg terminator
	| FOR args IN expression terminator? statement_expression_list terminator END 
	| arg DOTEACH DO terminator? '|' args '|' terminator? statement_expression_list terminator? END 
	| BREAK
	| NEXT
	| REDO
	| RETRY
;



do_keyword: (DO|':') terminator | terminator ;



assoc
:
	arg ASSOC arg
;

terminator
:
	terminator SEMICOLON
	| terminator CRLF
	| SEMICOLON
	| CRLF
;

DOTEACH: '.each';

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

EACH
:
	'each'
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
	) -> skip
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
	| [0-9]+ '.' [0-9]* ExponentPart?
;

Identifier
:
	[a-zA-Z_] [a-zA-Z0-9_]*
;

ID_GLOBAL
:
	'$' Identifier
;

ID_FUNCTION
:
	Identifier [?]
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
	| '%' [Qq] [\{\|\[\(] ~( '\n' | '\r' )* [\}\|\)\]]
;

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