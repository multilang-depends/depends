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
rescure_param: variable_path | hash_asso ;



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
	CLASS variable_path superclass?
;

superclass: '<' variable_path terminator;


function_definition_header
:
	DEF function_name function_definition_params? ('=' arg)? terminator
;

function_name
:   variable_path (DOT Identifier)* (QUESTION)?
	| BIT_SHL
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
	| LEFT_PAREN terminator? hash_asso terminator?(',' terminator? hash_asso)* ','? terminator? RIGHT_PAREN
	| LEFT_PAREN RIGHT_PAREN
	| '[' ']'
	| '[' terminator? arg terminator? (',' terminator? arg)* ','? terminator? ']'
	| (PLUS| MINUS|MUL|MOD) arg
	| (NOT| BIT_NOT) arg
	| arg (',' arg)* ASSIGN terminator? args
	| arg (PLUS_ASSIGN | MINUS_ASSIGN |MUL_ASSIGN|DIV_ASSIGN|MOD_ASSIGN | EXP_ASSIGN) terminator? arg
	| arg(LESS|GREATER|LESS_EQUAL| GREATER_EQUAL) terminator? arg
	| arg(MUL|DIV|MOD|PLUS|MINUS|BIT_SHL|BIT_SHR|BIT_AND|BIT_OR|BIT_XOR|EXP) terminator? arg
	| arg(BIT_OR_ASSIGN|BIT_AND_ASSIGN|OR_ASSIGN|AND_ASSIGN) terminator? arg
	| arg(EQUAL| NOT_EQUAL | NOT_EQUAL2) terminator? arg
	| arg(OR| AND) terminator? arg
	| arg QUESTION arg COLON arg
	| function_call block?
	| arg DOT function_call
	| arg '['arg']'
	| arg block
;

variable_path:
	identifier
	| Float
	| variable_path (ANDDOT| COLON2) variable_path (QUESTION)?
	| variable_path (DOT2|DOT3) variable_path
	| COLON variable_path
	| String
	| Integer
	| Regex
	|(TRUE| FALSE)
	| NIL 
	| Float DOT
	;


block: LEFT_PAREN block_params? statement_expression_list RIGHT_PAREN;

block_params: BIT_OR args BIT_OR;


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

identifier: Identifier | idGlobal |idColon | idClass |idMember |idArg;

idGlobal:	DOLLAR Identifier;
idColon: COLON Identifier;
idClass: AT AT Identifier;
idMember: AT Identifier;
idArg: DOLLAR Integer | DOLLAR AT | DOLLAR SHARP;
