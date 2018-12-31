parser grammar RubyParser;

options { tokenVocab= RubyLexer; }


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
	| RESCURE rescure_param?
	| ENSURE
	| expression IF arg 
	| expression UNLESS arg 
	| arg terminator? WHILE arg 
	| arg terminator? UNTIL arg 
	| ELSE terminator statement_expression_list
	| ELSIF arg terminator statement_expression_list
	| WHEN arg (',' arg)* terminator statement_expression_list
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

module_definition: MODULE variable_path expression_list END;

begin_block
:
	BEGIN_BLOCK LEFT_PAREN expression_list RIGHT_PAREN 
;

end_block
:
	END_BLOCK LEFT_PAREN expression_list RIGHT_PAREN
;


function_definition
:
	function_definition_header expression_list? END
;

class_definition: 
   	class_header expression_list? END
;

class_header: 
	CLASS variable_path superclass? (BIT_SHL SELF)?
;

superclass: '<' colon_variable_path terminator;


function_definition_header
:
	DEF function_name function_definition_params? ('=' arg)? terminator
;

function_name
:   variable_path (DOT terminator? Identifier)? (QUESTION|SIGH)?
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
	LEFT_RBRACKET ( function_param (COMMA terminator? function_param)*)? RIGHT_RBRACKET
	| function_param (COMMA terminator?  function_param)*
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
	function_name LEFT_RBRACKET terminator? function_param  (COMMA terminator? function_param)* terminator? RIGHT_RBRACKET
	| function_name LEFT_RBRACKET terminator? RIGHT_RBRACKET
	| function_name 
;

function_call_no_bracket:
    function_name function_param  (COMMA terminator? function_param)* 
    ;
    
args
:
	arg (',' arg)*
	| LEFT_RBRACKET args RIGHT_RBRACKET
;

arg
:
	variable_path QUESTION?
	| function_call terminator? block?
	| variable_path COLON arg
	| arg DOT terminator? function_call
	| arg DOT terminator? CLASS
	| DEFINED variable_path
	| LEFT_RBRACKET arg RIGHT_RBRACKET
	| LEFT_PAREN terminator? hash_asso terminator?(',' terminator? hash_asso)* ','? terminator? RIGHT_PAREN
	| LEFT_PAREN RIGHT_PAREN
	| LEFT_PAREN terminator? arg terminator? (',' terminator? arg)* ','? terminator? RIGHT_PAREN
	| LEFT_SBRACKET   RIGHT_SBRACKET 
	| LEFT_SBRACKET  terminator? arg terminator? (',' terminator? arg)* ','? terminator?  RIGHT_SBRACKET 
	| (argPrefix) arg
	| (not| BIT_NOT) arg
	| arg (',' arg)* ASSIGN terminator? args
	| arg (assignOperator) terminator? arg
	| arg (compareOperator) terminator? arg
	| arg(mathOperator|bitOperator) terminator? arg
	| arg(logicalAssignOperator) terminator? arg
	| arg(equalsOperator) terminator? arg
	| arg(logicalOperator) terminator? arg
	| arg QUESTION arg COLON arg
	| arg LEFT_SBRACKET arg RIGHT_SBRACKET 
	| arg block
	| arg (DOT2|DOT3) arg
	| function_call_no_bracket
	| COLON arg
	| IF arg terminator expression_list END
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


block: LEFT_PAREN terminator? block_params? statement_expression_list terminator? (COMMA statement_expression_list terminator?)* RIGHT_PAREN;

block_params: BIT_OR args (COMMA args)*  BIT_OR;


do_keyword: (DO|COLON) terminator | terminator ;


hash_asso
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

identifier: Identifier | idGlobal | idClass |idMember |idArg | NEXT | BREAK |SELF  ;

idGlobal:	DOLLAR Identifier;
idClass: AT AT Identifier;
idMember: AT Identifier;
idArg: DOLLAR Integer | DOLLAR AT | DOLLAR SHARP;
