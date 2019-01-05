parser grammar RubyParser;

options { tokenVocab= RubyLexer; }

prog
:
	statement_list_terms
;

statement_list_terms
:
	statement terms
	| statement_list_terms statement terms
	| terms
;

statement_list_noterms
:
	(statement terms)* statement
;

statement
:
	 begin_block
	| end_block
	| alias_statement
	| undef_statement
	| require_statement
	| ENSURE
	| expr
;
require_statement: REQUIRE String ;

undef_statement: UNDEF function_name_or_symbol (',' function_name_or_symbol)*;

alias_statement
: 
	ALIAS globalVar globalVar
	| ALIAS function_name_or_symbol function_name_or_symbol
;

function_name_or_symbol: 
	function_name
	|symbol
;

return_statement: 
	RETURN expr?
;


raise_statement:
	RAISE expr
;

begin_block
:
	BEGIN_BLOCK LEFT_PAREN statement_list_terms RIGHT_PAREN 
;

end_block
:
	END_BLOCK LEFT_PAREN statement_list_terms RIGHT_PAREN
;

module_definition: 
	MODULE cpath statement_list_terms? END;

class_definition: 
   	class_header statement_list_terms? END
;
superclass: '<' id_symbol terms;

class_header: 
	CLASS cpath superclass? 
	| CLASS BIT_SHL identifier
;

function_definition
:
	function_definition_header statement_list_terms? END
;

function_definition_header
:
	DEF function_name function_definition_params? expr? terms
;

function_name
:   cpath (QUESTION|SIGH|ASSIGN)?
    | literal
	| assignOperator
	| mathOperator AT
	| bitOperator
	| compareOperator
	| equalsOperator
	| logicalOperator
;

function_definition_params
:
	LEFT_RBRACKET ( function_definition_param (',' crlfs? function_definition_param)*)? RIGHT_RBRACKET
	| function_definition_param (',' crlfs?  function_definition_param)*
;

function_definition_param: 
	identifier 
	| MUL identifier
	| MUL MUL identifier
	| BIT_AND identifier
	| hash_asso
	| identifier ASSIGN expr
;

function_call_param:
	identifier
	|hash_asso
	|expr 
	| identifier ASSIGN expr
;


expr
:
	variable_path
	| expr ',' crlfs? expr
	| expr dot_ref terms? expr 
	| expr postfix=QUESTION
	| prefix=(PLUS| MINUS|MUL|MOD|BIT_AND) expr
	| Regex
	| symbol
	| LEFT_RBRACKET expr RIGHT_RBRACKET                                                   	/* (a) */
	| expr LEFT_SBRACKET expr RIGHT_SBRACKET 												/* array access */
	| prefix=DEFINED expr                                                                	        /* identifier definition test */
	| LEFT_PAREN crlfs? hash_asso crlfs?(',' crlfs? hash_asso)* ','? crlfs? RIGHT_PAREN   	/* hash */
	| LEFT_SBRACKET  crlfs? expr crlfs? (',' crlfs? expr)* ','? crlfs?  RIGHT_SBRACKET   	/* array */
	| LEFT_RBRACKET expr (DOT2|DOT3) expr? RIGHT_RBRACKET                               	/* range */
	| expr bop=(DOT2|DOT3) expr? 								                               	/* range */
	| expr ','? MUL? ASSIGN crlfs? expr                                         		/* batch assign */
	| expr assignOperator crlfs? expr														/* assign */
	| expr bop=PATTERN_MATCH expr                                                               /* pattern match */
	| expr bop=BIT_NOT expr                                                                     /* pattern match */
	| expr SIGH BIT_NOT expr                                                                /* pattern match */
	| (not| BIT_NOT) expr                                                               	/* logical not */
	| expr (compareOperator) crlfs? expr                                                	/* compare logical  */
	| expr(logicalOperator) crlfs? expr                                                 	/* logical join */
	| expr(equalsOperator) crlfs? expr                                                  	/* equal test */
	| expr(mathOperator|bitOperator) crlfs? expr                                        	/* calcuation */
	| expr QUESTION expr COLON expr                                                	/* cond?true_part:false_part */
	| expr dot_ref CLASS
	| function_name func_call_parameters
	| expr block
	| block
	| RESCUE rescure_param?
	| YIELD expr?
	| BEGIN terms statement_list_terms? END
	| IF crlfs? expr then_keyword statement_list_terms? if_tail* END
	| WHEN expr then_keyword statement_list_noterms END
	| UNLESS crlfs? expr then_keyword  statement_list_terms? unless_tail? END
	| CASE statement_list_terms? case_body*  END
	| CASE terms case_body*  END
	| WHILE crlfs? expr do_keyword  statement_list_terms? END
	| UNTIL crlfs? expr do_keyword  statement_list_terms?  END
	| FOR crlfs? expr IN when_cond terms?  statement_list_terms? END
	| expr DO block_params? terms? statement_list_terms? END
	| function_name func_call_parameters_no_bracket?
	| class_definition
	| function_definition
	| module_definition
	| BREAK expr?
	| return_statement 
	| raise_statement
	| expr IF crlfs? expr 
	| expr UNLESS crlfs? expr 
	| expr WHILE crlfs? expr 
	| expr UNTIL crlfs? expr 	
	| expr RESCUE statement 
;

func_call_parameters
:
	LEFT_RBRACKET crlfs? function_call_param  (',' crlfs? function_call_param)* crlfs? RIGHT_RBRACKET
	| LEFT_RBRACKET crlfs? RIGHT_RBRACKET
;
func_call_parameters_no_bracket:
	function_call_param  (',' crlfs? function_call_param)* 
;
rescure_param: id_symbol | hash_asso |ASSOC identifier;

case_body: 
	WHEN when_cond (',' when_cond)* then_keyword statement_list_terms
	|else_tail;
	
when_cond: 
	expr 
	;
unless_tail: 
	else_tail
	;
	
if_tail: 
	ELSIF crlfs? expr terms statement_list_terms
	| else_tail
;
else_tail: 
	ELSE crlfs? statement_list_terms;


dot_ref: DOT | ANDDOT ;


logicalOperator: OR| AND;

equalsOperator: EQUAL| NOT_EQUAL | EQUAL3;

compareOperator: LESS|GREATER|LESS_EQUAL| GREATER_EQUAL;

bitOperator: BIT_SHL|BIT_SHR|BIT_AND|BIT_OR|BIT_XOR;

mathOperator: MUL|DIV|MOD|PLUS|MINUS|EXP;

assignOperator:
	PLUS_ASSIGN | MINUS_ASSIGN |MUL_ASSIGN|DIV_ASSIGN|MOD_ASSIGN | EXP_ASSIGN  |BIT_OR_ASSIGN|BIT_AND_ASSIGN|OR_ASSIGN|AND_ASSIGN;

not: NOT | SIGH;

block: LEFT_PAREN crlfs? block_params? statement_list_noterms crlfs? (',' statement_list_noterms crlfs?)* RIGHT_PAREN;

block_params: BIT_OR expr (',' expr)*  BIT_OR;

id_symbol: 
	cpath
	| 	COLON cpath
	;

symbol: 
	COLON identifier
	COLON string 
	|  COLON function_name
;

hash_asso:
	expr ASSOC expr 
	| expr COLON expr  
;

variable_path:
	identifier
    |literal
	| variable_path COLON2 variable_path
	| COLON2 variable_path
	;
cpath:
    identifier ((COLON2|DOT) identifier)*
    ;	
literal: 
	Float
	| string
	| Integer
	|(TRUE| FALSE)
	| NIL 
	| Float DOT
;

	
identifier: Identifier | globalVar | classVar |instanceVar | idArg | NEXT  |REDO |RETRY | BREAK |SELF | SUPER  |NIL |REQUIRE | empty;

empty: 
	LEFT_PAREN RIGHT_PAREN																/* empty block  */
	| LEFT_SBRACKET  RIGHT_SBRACKET                                                    	/* empty array */
	|LEFT_RBRACKET RIGHT_RBRACKET
	;

globalVar:	DOLLAR Identifier;
classVar: AT AT Identifier;
instanceVar: AT Identifier;
idArg: DOLLAR Integer | DollarSpecial;

do_keyword: (DO|COLON) terms | terms ;
then_keyword: (THEN|COLON) terms? | terms;
string: String+;  /* many string can be concatenated  */

crlfs: (SL_COMMENT|CRLF)+;

terms:
	(term)+
;

term: SEMICOLON | CRLF | SL_COMMENT;



	
