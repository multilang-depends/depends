parser grammar RubyParser;

options { tokenVocab= RubyLexer; }


prog
:
	top_compstmt
;

top_compstmt
:
	stmt terms
	| top_compstmt stmt terms
	| terms
;

statement_expression_list
:
	(stmt terms)* stmt
;

stmt
:
	function_definition
	| begin_block
	| end_block
	| alias_statement
	| undef_statement
	| stmt IF arg 
	| stmt UNLESS arg 
	| arg terms? WHILE arg 
	| arg terms? UNTIL arg 
	| arg terms? RESCUE arg 
	| class_definition
	| module_definition
	| require_statement
	| return_statement 
	| raise_statement
	| yield_statement
	| arg
	| WHEN arg THEN statement_expression_list END
	| RESCUE rescure_param?
	| ENSURE
	| ELSE terms statement_expression_list
	| ELSIF arg terms statement_expression_list
	| WHEN arg (',' arg)* terms statement_expression_list
	| CASE arg terms top_compstmt END
	| CASE terms (WHEN arg THEN statement_expression_list terms)* END
	| UNLESS arg terms top_compstmt END
	| WHILE arg do_keyword top_compstmt END
	| UNTIL arg do_keyword top_compstmt END
	| WHILE arg do_keyword END
	| BEGIN terms top_compstmt END (terms? WHILE arg)?
	| FOR args IN stmt terms? top_compstmt END 
	| arg DO terms? block_params terms? statement_expression_list? terms? END 
	| arg DO terms top_compstmt END 
	| BREAK
	| NEXT
	| REDO
	| RETRY
	| ENSURE
	| THROW arg (IF arg )? 
	| CATCH args DO top_compstmt END 
;

undef_statement: UNDEF function_name_or_symbol (COMMA function_name_or_symbol)*;

def_item: function_name;

alias_statement
: 
	ALIAS idGlobal idGlobal
	| ALIAS function_name_or_symbol function_name_or_symbol
;

function_name_or_symbol: 
	function_name
	|symbol
;
	
symbol: 
	COLON identifier 
	|  COLON function_name
;

rescure_param: colon_variable_path | hash_asso |ASSOC identifier;

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

raise_statement:
	RAISE args
;

module_definition: MODULE variable_path top_compstmt END;

begin_block
:
	BEGIN_BLOCK LEFT_PAREN top_compstmt RIGHT_PAREN 
;

end_block
:
	END_BLOCK LEFT_PAREN top_compstmt RIGHT_PAREN
;


function_definition
:
	function_definition_header top_compstmt? END
;

class_definition: 
   	class_header top_compstmt? END
;

class_header: 
	CLASS variable_path superclass? (BIT_SHL SELF)?
;

superclass: '<' colon_variable_path terms;


function_definition_header
:
	DEF function_name function_definition_params? ('=' arg)? terms
;

function_name
:   variable_path (DOT terms? Identifier)? (QUESTION|SIGH)?
	| assignOperator
	| mathOperator
	| bitOperator
	| compareOperator
	| logicalAssignOperator
	| equalsOperator
	| logicalOperator
;


function_definition_params
:
	LEFT_RBRACKET ( function_param (COMMA terms? function_param)*)? RIGHT_RBRACKET
	| function_param (COMMA terms?  function_param)*
;

function_param:
	identifier
	|identifier COLON arg
	|hash_asso
	|arg ASSIGN arg
	|arg 
;

function_call
:
	function_name LEFT_RBRACKET terms? function_param  (COMMA terms? function_param)* terms? RIGHT_RBRACKET
	| function_name LEFT_RBRACKET terms? RIGHT_RBRACKET
	| function_name 
;

function_call_no_bracket:
    function_name function_param  (COMMA terms? function_param)* 
    ;
    
args
:
	arg (',' arg)*
	| LEFT_RBRACKET args RIGHT_RBRACKET
;

arg
:
	variable_path QUESTION?
	| function_call terms? block?
	| variable_path COLON arg
	| arg DOT terms? function_call
	| arg DOT terms? CLASS
	| DEFINED variable_path
	| LEFT_RBRACKET arg RIGHT_RBRACKET
	| LEFT_PAREN terms? hash_asso terms?(',' terms? hash_asso)* ','? terms? RIGHT_PAREN
	| LEFT_PAREN RIGHT_PAREN
	| LEFT_PAREN terms? arg terms? (',' terms? arg)* ','? terms? RIGHT_PAREN
	| LEFT_SBRACKET   RIGHT_SBRACKET 
	| LEFT_SBRACKET  terms? arg terms? (',' terms? arg)* ','? terms?  RIGHT_SBRACKET 
	| (argPrefix) arg
	| (not| BIT_NOT) arg
	| arg (',' arg)* ASSIGN terms? args
	| arg (assignOperator) terms? arg
	| arg (compareOperator) terms? arg
	| arg(mathOperator|bitOperator) terms? arg
	| arg(logicalAssignOperator) terms? arg
	| arg(equalsOperator) terms? arg
	| arg(logicalOperator) terms? arg
	| arg QUESTION arg COLON arg
	| arg LEFT_SBRACKET arg RIGHT_SBRACKET 
	| arg block
	| arg (DOT2|DOT3) arg
	| function_call_no_bracket
	| COLON arg
	| IF arg terms top_compstmt END
;
argPrefix: PLUS| MINUS|MUL|MOD|BIT_AND;

logicalOperator: OR| AND;

equalsOperator: EQUAL| NOT_EQUAL | NOT_EQUAL2;

logicalAssignOperator: BIT_OR_ASSIGN|BIT_AND_ASSIGN|OR_ASSIGN|AND_ASSIGN;

compareOperator: LESS|GREATER|LESS_EQUAL| GREATER_EQUAL;

bitOperator: BIT_SHL|BIT_SHR|BIT_AND|BIT_OR|BIT_XOR;

mathOperator: MUL|DIV|MOD|PLUS|MINUS|EXP;

assignOperator:
	PLUS_ASSIGN | MINUS_ASSIGN |MUL_ASSIGN|DIV_ASSIGN|MOD_ASSIGN | EXP_ASSIGN ;

not: NOT | SIGH;

colon_variable_path:
    COLON? variable_path
   ;
   
variable_path:
	identifier
	| Float
	| String
	| Integer
	| Regex
	|(TRUE| FALSE)
	| NIL 
	| variable_path (ANDDOT| COLON2) variable_path (QUESTION)?
	| Float DOT
	;


block: LEFT_PAREN terms? block_params? statement_expression_list terms? (COMMA statement_expression_list terms?)* RIGHT_PAREN;

block_params: BIT_OR args (COMMA args)*  BIT_OR;


do_keyword: (DO|COLON) terms | terms ;


hash_asso
:
	arg ASSOC arg
;

terms
:
	terms SEMICOLON
	| terms CRLF
	| terms SL_COMMENT
	| SEMICOLON
	| CRLF
	| SL_COMMENT
;

identifier: Identifier | idGlobal | idClass |idMember |idArg | NEXT | BREAK |SELF  ;

idGlobal:	DOLLAR Identifier;
idClass: AT AT Identifier;
idMember: AT Identifier;
idArg: DOLLAR Integer | DOLLAR AT | DOLLAR SHARP;
