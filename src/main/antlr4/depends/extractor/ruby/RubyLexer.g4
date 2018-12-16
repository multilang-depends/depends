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
 *  History:
 *  Created by Alexander Belov 2014
 *  2018.12.16 Gang ZHANG (gangz@emergentdesign.cn)
 *             - Split to separated lexer/parser file
 * 
 */
lexer grammar RubyLexer;

LINE_COMMENT:       '#' ~[\r\n]*    -> channel(HIDDEN);
COMMENT:            '=begin' .*? '=end'    -> channel(HIDDEN);
WS : (' '|'\t')+ -> skip;


CLASS:	'class';
MODULE:	'module';
DEF:	'def';
UNDEF:	'undef';
BEGIN:	'begin';
RESCUE:	'rescue';
ENSURE:	'ensure';
END:	'end';
IF:	'if';
UNLESS:	'unless';
THEN:	'then';
ELSIF:	'elsif';
ELSE:	'else';
CASE:	'case';
WHEN:	'when';
WHILE:	'while';
UNTIL:	'until';
FOR:	'for';
BREAK:	'break';
NEXT:	'next';
REDO:	'redo';
RETRY:	'retry';
IN:	'in';
DO:	'do';
RETURN:	'return';
YIELD:	'yield';
SUPER:	'super';
SELF:	'self';
NIL:	'nil';
TRUE:	'true';
FALSE:	'false';
KEYWORD_AND:	'and';
KEYWORD_OR:	'or';
KEYWORD_NOT:	'not';
ALIAS:	'alias';
DEFINED:	'defined?';
KEYWORD_BEGIN:	'BEGIN';
KEYWORD_END:	'END';
KEYWORD_LINE:	'__LINE__';
KEYWORD_FILE:	'__FILE__';
KEYWORD_ENCODING:	'__ENCODING__';

TOKEN_UPLUS:	'unary+';
TOKEN_UMINUS:	'unary-';
TOKEN_POW:	'**';
TOKEN_CMP:	'<=>';
TOKEN_EQ:	'==';
TOKEN_EQQ:	'===';
TOKEN_NEQ:	'!=';
TOKEN_GEQ:	'>=';
TOKEN_LEQ:	'<=';
TOKEN_ANDOP:	'&&';
TOKEN_OROP:	'||';
TOKEN_MATCH:	'=~';
TOKEN_NMATCH:	'!~';
TOKEN_DOT2:	'..';
TOKEN_DOT3:	'...';
TOKEN_AREF:	'[]';
TOKEN_ASET:	'[]=';
TOKEN_LSHFT:	'<<';
TOKEN_RSHFT:	'>>';
TOKEN_ANDDOT:	'&.';
TOKEN_COLON2:	'::';
TOKEN_COLON3:	'::';
TOKEN_ASSOC:	'=>';
TOKEN_LPAREN:	'(';
TOKEN_LPAREN_ARG:	'(';
TOKEN_RPAREN:	')';
TOKEN_LBRACK:	'[';
TOKEN_LBRACE:	'{';
TOKEN_LBRACE_ARG:	'{';
TOKEN_STAR:	'*';
/*TOKEN_DSTAR:	'**arg';*/
TOKEN_AMPER:	'&';
TOKEN_LAMBDA:	'->';

TOKEN_OP_ASGN: 'TBC';

fragment ESCAPED_QUOTE : '\\"';

LITERAL : '"' ( ESCAPED_QUOTE | ~('\n'|'\r') )*? '"'
        | '\'' ( ESCAPED_QUOTE | ~('\n'|'\r') )*? '\'';

COMMA : ',';
SEMICOLON : ';';
CRLF : '\r'? '\n';

PLUS : '+';
MINUS : '-';
MUL : '*';
DIV : '/';
MOD : '%';
EXP : '**';

EQUAL : '==';
NOT_EQUAL : '!=';
GREATER : '>';
LESS : '<';
LESS_EQUAL : '<=';
GREATER_EQUAL : '>=';

ASSIGN : '=';
PLUS_ASSIGN : '+=';
MINUS_ASSIGN : '-=';
MUL_ASSIGN : '*=';
DIV_ASSIGN : '/=';
MOD_ASSIGN : '%=';
EXP_ASSIGN : '**=';

BIT_AND : '&';
BIT_OR : '|';
BIT_XOR : '^';
BIT_NOT : '~';
BIT_SHL : '<<';
BIT_SHR : '>>';

AND : 'and' | '&&';
OR : 'or' | '||';
NOT : 'not' | '!';

LEFT_RBRACKET : '(';
RIGHT_RBRACKET : ')';
LEFT_SBRACKET : '[';
RIGHT_SBRACKET : ']';



INT : [0-9]+;
FLOAT : [0-9]*'.'[0-9]+;
ID : [a-zA-Z_][a-zA-Z0-9_]*;
ID_GLOBAL : '$'ID;
ID_FUNCTION : ID[?];
