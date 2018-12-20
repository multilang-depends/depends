parser grammar RubyParser;

options {
	tokenVocab = RubyLexer;
}

program
:
	top_stmts term*
;

top_stmts : 
    top_stmts* top_stmt term
;

top_stmt
:
	stmt
	| KEYWORD_BEGIN begin_block
;

begin_block
:
	'{' top_stmts '}'
;

bodystmt
:
	compstmt opt_rescue? ELSE compstmt opt_ensure
	| compstmt opt_rescue? opt_ensure
;

compstmt
:
	stmts? term*
;

stmts
:
	| stmt_or_begin
	| stmts terms stmt_or_begin
	| error stmt
;

stmt_or_begin
:
	stmt
	| KEYWORD_BEGIN begin_block
;

stmt
:
	ALIAS fitem fitem
	| ALIAS ID_GLOBAL ID_GLOBAL
	/* | ALIAS ID_GLOBAL tBACK_REF*/
	/*| ALIAS ID_GLOBAL tNTH_REF*/
	| UNDEF undef_list
	| stmt IF expr
	| stmt UNLESS expr
	| stmt WHILE expr
	| stmt UNTIL expr
	| stmt RESCUE stmt
	| KEYWORD_END '{' compstmt '}'
	| command_asgn
	| mlhs '=' command_call
	| lhs '=' mrhs
	| mlhs '=' mrhs_arg
	| expr
;

command_asgn
:
	lhs '=' command_rhs
	| var_lhs TOKEN_OP_ASGN command_rhs
	| primary_value '[' opt_call_args? rbracket TOKEN_OP_ASGN command_rhs
	| primary_value call_op ID TOKEN_OP_ASGN command_rhs
	| primary_value call_op ID TOKEN_OP_ASGN command_rhs
	| primary_value TOKEN_COLON2 ID TOKEN_OP_ASGN command_rhs
	| primary_value TOKEN_COLON2 ID TOKEN_OP_ASGN command_rhs
	| backref TOKEN_OP_ASGN command_rhs
;

command_rhs
:
	command_call /*%prec*/ TOKEN_OP_ASGN
	| command_call RESCUE stmt
	| command_asgn
;

expr
:
	command_call
	| expr KEYWORD_AND expr
	| expr KEYWORD_OR expr
	| KEYWORD_NOT '\n'? expr
	| '!' command_call
	| arg
;

expr_value_do
:
	expr do
;

command_call
:
	command
	| block_command
;

block_command
:
	block_call
	| block_call call_op2 operation2 call_args
;

cmd_brace_block
:
	TOKEN_LBRACE_ARG brace_body '}'
;

fcall
:
	operation
;

command
:
	fcall call_args /*%prec*/ /*tLOWEST*/
	| fcall call_args cmd_brace_block
	| primary_value call_op operation2 call_args /*%prec*/ /*tLOWEST*/
	| primary_value call_op operation2 call_args cmd_brace_block
	| primary_value TOKEN_COLON2 operation2 call_args /*%prec*/ /*tLOWEST*/
	| primary_value TOKEN_COLON2 operation2 call_args cmd_brace_block
	| SUPER call_args
	| YIELD call_args
	| RETURN call_args
	| BREAK call_args
	| NEXT call_args
;

mlhs
:
	mlhs_basic
	| TOKEN_LPAREN mlhs_inner rparen
;

mlhs_inner
:
	mlhs_basic
	| TOKEN_LPAREN mlhs_inner rparen
;

mlhs_basic
:
	mlhs_head
	| mlhs_head mlhs_item
	| mlhs_head TOKEN_STAR mlhs_node
	| mlhs_head TOKEN_STAR mlhs_node ',' mlhs_post
	| mlhs_head TOKEN_STAR
	| mlhs_head TOKEN_STAR ',' mlhs_post
	| TOKEN_STAR mlhs_node
	| TOKEN_STAR mlhs_node ',' mlhs_post
	| TOKEN_STAR
	| TOKEN_STAR ',' mlhs_post
;

mlhs_item
:
	mlhs_node
	| TOKEN_LPAREN mlhs_inner rparen
;

mlhs_head
:
	mlhs_item ','
	| mlhs_head mlhs_item ','
;

mlhs_post
:
	mlhs_item
	| mlhs_post ',' mlhs_item
;

mlhs_node
:
	user_variable
	| keyword_variable
	| primary_value '[' opt_call_args? rbracket
	| primary_value call_op ID
	| primary_value TOKEN_COLON2 ID
	| primary_value call_op ID
	| primary_value TOKEN_COLON2 ID
	| TOKEN_COLON3 ID
	| backref
;

lhs
:
	user_variable
	| keyword_variable
	| primary_value '[' opt_call_args? rbracket
	| primary_value call_op ID
	| primary_value TOKEN_COLON2 ID
	| primary_value call_op ID
	| primary_value TOKEN_COLON2 ID
	| TOKEN_COLON3 ID
	| backref
;

cname
:
	ID
	| ID
;

cpath
:
	TOKEN_COLON3 cname
	| cname
	| primary_value TOKEN_COLON2 cname
;

fname
:
	ID
	| ID
	| tFID
	| op
	| reswords
;

fsym
:
	fname
	| symbol
;

fitem
:
	fsym
	| dsym
;

undef_list
:
	fitem
	| undef_list ',' fitem
;

op
:
	'|'
	| '^'
	| '&'
	| TOKEN_CMP
	| TOKEN_EQ
	| TOKEN_EQQ
	| TOKEN_MATCH
	| TOKEN_NMATCH
	| '>'
	| TOKEN_GEQ
	| '<'
	| TOKEN_LEQ
	| TOKEN_NEQ
	| TOKEN_LSHFT
	| TOKEN_RSHFT
	| '+'
	| '-'
	| '*'
	| TOKEN_STAR
	| '/'
	| '%'
	| TOKEN_POW
	| TOKEN_DSTAR
	| '!'
	| '~'
	| TOKEN_UPLUS
	| TOKEN_UMINUS
	| TOKEN_AREF
	| TOKEN_ASET
	| '`'
;

reswords
:
	KEYWORD_LINE
	| KEYWORD_FILE
	| KEYWORD_ENCODING
	| KEYWORD_BEGIN
	| KEYWORD_END
	| ALIAS
	| KEYWORD_AND
	| BEGIN
	| BREAK
	| CASE
	| CLASS
	| DEF
	| DEFINED
	| DO
	| ELSE
	| ELSIF
	| END
	| ENSURE
	| FALSE
	| FOR
	| IN
	| MODULE
	| NEXT
	| NIL
	| KEYWORD_NOT
	| KEYWORD_OR
	| REDO
	| RESCUE
	| RETRY
	| RETURN
	| SELF
	| SUPER
	| THEN
	| TRUE
	| UNDEF
	| WHEN
	| YIELD
	| IF
	| UNLESS
	| WHILE
	| UNTIL
;

arg
:
	lhs '=' arg_rhs
	| var_lhs TOKEN_OP_ASGN arg_rhs
	| primary_value '[' opt_call_args? rbracket TOKEN_OP_ASGN arg_rhs
	| primary_value call_op ID TOKEN_OP_ASGN arg_rhs
	| primary_value call_op ID TOKEN_OP_ASGN arg_rhs
	| primary_value TOKEN_COLON2 ID TOKEN_OP_ASGN arg_rhs
	| primary_value TOKEN_COLON2 ID TOKEN_OP_ASGN arg_rhs
	| TOKEN_COLON3 ID TOKEN_OP_ASGN arg_rhs
	| backref TOKEN_OP_ASGN arg_rhs
	| arg TOKEN_DOT2 arg
	| arg TOKEN_DOT3 arg
	| arg TOKEN_DOT2
	| arg TOKEN_DOT3
	| arg '+' arg
	| arg '-' arg
	| arg '*' arg
	| arg '/' arg
	| arg '%' arg
	| arg TOKEN_POW arg
	| TOKEN_UMINUS_NUM simple_numeric TOKEN_POW arg
	| TOKEN_UPLUS arg
	| TOKEN_UMINUS arg
	| arg '|' arg
	| arg '^' arg
	| arg '&' arg
	| arg TOKEN_CMP arg
	| rel_expr /*%prec*/ TOKEN_CMP
	| arg TOKEN_EQ arg
	| arg TOKEN_EQQ arg
	| arg TOKEN_NEQ arg
	| arg TOKEN_MATCH arg
	| arg TOKEN_NMATCH arg
	| '!' arg
	| '~' arg
	| arg TOKEN_LSHFT arg
	| arg TOKEN_RSHFT arg
	| arg TOKEN_ANDOP arg
	| arg TOKEN_OROP arg
	| DEFINED '\n'? arg
	| arg '?' arg '\n'? ':' arg
	| primary
;

relop
:
	'>'
	| '<'
	| TOKEN_GEQ
	| TOKEN_LEQ
;

rel_expr
:
	arg relop arg /*%prec*/ '>'
	| rel_expr relop arg /*%prec*/ '>'
;


aref_args
:
	args trailer
	| args ',' assocs trailer
	| assocs trailer
;

arg_rhs
:
	arg /*%prec*/ TOKEN_OP_ASGN
	| arg RESCUE arg
;

paren_args
:
	'(' opt_call_args? rparen
;


opt_call_args
:
	call_args
	| args ','
	| args ',' assocs ','
	| assocs ','
;

call_args
:
	command
	| args opt_block_arg?
	| assocs opt_block_arg?
	| args ',' assocs opt_block_arg?
	| block_arg
	/*% ripper[brace]: args_add_block!(args_new!, $1) %*/
;


block_arg
:
	TOKEN_AMPER arg
;

opt_block_arg
:
	',' block_arg
;

args
:
	arg
	| TOKEN_STAR arg
	| args ',' arg
	| args ',' TOKEN_STAR arg
;

mrhs_arg
:
	mrhs
	| arg
;

mrhs
:
	args ',' arg
	| args ',' TOKEN_STAR arg
	| TOKEN_STAR arg
;

primary
:
	LITERAL
	| regexp
	| words
	| qwords
	| symbols
	| qsymbols
	| var_ref
	| backref
	| tFID
	| BEGIN bodystmt END
	| TOKEN_LPAREN_ARG rparen
	| TOKEN_LPAREN_ARG stmt rparen
	| TOKEN_LPAREN compstmt ')'
	| primary_value TOKEN_COLON2 ID
	| TOKEN_COLON3 ID
	| TOKEN_LBRACK aref_args? ']'
	| TOKEN_LBRACE assoc_list '}'
	| RETURN
	| YIELD '(' call_args rparen
	| YIELD '(' rparen
	| YIELD
	| DEFINED '\n'? '(' expr rparen
	| KEYWORD_NOT '(' expr rparen
	| KEYWORD_NOT '(' rparen
	| fcall brace_block
	| method_call
	| method_call brace_block
	| TOKEN_LAMBDA lambda
	| IF expr then compstmt if_tail END
	| UNLESS expr then compstmt (ELSE compstmt)? END
	| WHILE expr_value_do compstmt END
	| UNTIL expr_value_do compstmt END
	| CASE expr term* case_body END
	| CASE term* case_body END
	| FOR for_var IN expr_value_do compstmt END
	| CLASS cpath superclass? bodystmt END
	| CLASS TOKEN_LSHFT expr term bodystmt END
	| MODULE cpath bodystmt END
	| DEF fname f_arglist bodystmt END
	| DEF singleton dot_or_colon fname f_arglist bodystmt END
	| BREAK
	| NEXT
	| REDO
	| RETRY
;

primary_value
:
	primary
;

then
:
	term
	| THEN
	| term THEN
;

do
:
	term
	| DO_cond
;

if_tail
:
	(ELSE compstmt)?
	| ELSIF expr then compstmt if_tail
;


for_var
:
	lhs
	| mlhs
;

f_marg
:
	f_norm_arg
	| TOKEN_LPAREN f_margs rparen
;

f_marg_list
:
	f_marg
	| f_marg_list ',' f_marg
;

f_margs
:
	f_marg_list
	| f_marg_list ',' TOKEN_STAR f_norm_arg
	| f_marg_list ',' TOKEN_STAR f_norm_arg ',' f_marg_list
	| f_marg_list ',' TOKEN_STAR
	| f_marg_list ',' TOKEN_STAR ',' f_marg_list
	| TOKEN_STAR f_norm_arg
	| TOKEN_STAR f_norm_arg ',' f_marg_list
	| TOKEN_STAR
	| TOKEN_STAR ',' f_marg_list
;

block_args_tail
:
	f_block_kwarg ',' f_kwrest opt_f_block_arg
	| f_block_kwarg opt_f_block_arg
	| f_kwrest opt_f_block_arg
	| f_block_arg
;

opt_block_args_tail
:
	',' block_args_tail
	| /* none */
;

block_param
:
	f_arg ',' f_block_optarg ',' f_rest_arg opt_block_args_tail
	| f_arg ',' f_block_optarg ',' f_rest_arg ',' f_arg opt_block_args_tail
	| f_arg ',' f_block_optarg opt_block_args_tail
	| f_arg ',' f_block_optarg ',' f_arg opt_block_args_tail
	| f_arg ',' f_rest_arg opt_block_args_tail
	| f_arg ','
	| f_arg ',' f_rest_arg ',' f_arg opt_block_args_tail
	| f_arg opt_block_args_tail
	| f_block_optarg ',' f_rest_arg opt_block_args_tail
	| f_block_optarg ',' f_rest_arg ',' f_arg opt_block_args_tail
	| f_block_optarg opt_block_args_tail
	| f_block_optarg ',' f_arg opt_block_args_tail
	| f_rest_arg opt_block_args_tail
	| f_rest_arg ',' f_arg opt_block_args_tail
	| block_args_tail
;

opt_block_param
:
	none
	| block_param_def
;

block_param_def
:
	'|' opt_bv_decl '|'
	| TOKEN_OROP
	| '|' block_param opt_bv_decl '|'
;

opt_bv_decl
:
	'\n'?
	| '\n'? ';' bv_decls '\n'?
;

bv_decls
:
	bvar
	/*% ripper[brace]: rb_ary_new3(1, get_value($1)) %*/
	| bv_decls ',' bvar
	/*% ripper[brace]: rb_ary_push($1, get_value($3)) %*/
;

bvar
:
	ID
	| f_bad_arg
;

lambda
:
	f_larglist lambda_body
;

f_larglist
:
	'(' f_args opt_bv_decl ')'
	| f_args
;

lambda_body
:
	tLAMBEG compstmt '}'
	| DO_LAMBDA bodystmt END
;

do_block
:
	DO_block do_body END
;

block_call
:
	command do_block
	| block_call call_op2 operation2 paren_args?
	| block_call call_op2 operation2 paren_args? brace_block
	| block_call call_op2 operation2 call_args do_block
;

method_call
:
	fcall paren_args
	| primary_value call_op operation2 paren_args?
	| primary_value TOKEN_COLON2 operation2 paren_args
	| primary_value TOKEN_COLON2 operation3
	| primary_value call_op paren_args
	| primary_value TOKEN_COLON2 paren_args
	| SUPER paren_args
	| SUPER
	| primary_value '[' opt_call_args? rbracket
;

brace_block
:
	'{' brace_body '}'
	| DO do_body END
;

brace_body
:
	opt_block_param compstmt
;

do_body
:
	opt_block_param bodystmt
;

case_body
:
	WHEN args then compstmt cases
;

cases
:
	(ELSE compstmt)?
	| case_body
;

opt_rescue
:
	RESCUE exc_list exc_var then compstmt opt_rescue?
;

exc_list
:
	arg
	| mrhs
	| none
;

exc_var
:
	TOKEN_ASSOC lhs
	| none
;

opt_ensure
:
	ENSURE compstmt
	| none
;


regexp
:
	tREGEXP_BEG regexp_contents tREGEXP_END
;

words
:
	tWORDS_BEG ' ' word_list tSTRING_END
;

word_list
: /* none */
	| word_list word ' '
;

word
:
	string_content
	/*% ripper[brace]: word_add!(word_new!, $1) %*/
	| word string_content
;

symbols
:
	tSYMBOLS_BEG ' ' symbol_list tSTRING_END
;

symbol_list
: /* none */
	| symbol_list word ' '
;

qwords
:
	tQWORDS_BEG ' ' qword_list tSTRING_END
;

qsymbols
:
	tQSYMBOLS_BEG ' ' qsym_list tSTRING_END
;

qword_list
: /* none */
	| qword_list tSTRING_CONTENT ' '
;

qsym_list
: /* none */
	| qsym_list tSTRING_CONTENT ' '
;

string_contents
: /* none */
	| string_contents string_content
;

xstring_contents
: /* none */
	| xstring_contents string_content
;

regexp_contents
: /* none */
	| regexp_contents string_content
;

string_content
:
	tSTRING_CONTENT
	| tSTRING_DVAR string_dvar
	| tSTRING_DBEG compstmt tSTRING_DEND
;

string_dvar
:
	ID_GLOBAL
	| tIVAR
	| tCVAR
	| backref
;

symbol
:
	tSYMBEG sym
;

sym
:
	fname
	| tIVAR
	| ID_GLOBAL
	| tCVAR
;

dsym
:
	tSYMBEG xstring_contents tSTRING_END
;

numeric
:
	simple_numeric
	| TOKEN_UMINUS_NUM simple_numeric /*%prec*/ /*tLOWEST*/
;

simple_numeric
:
	INT
	| FLOAT
	| tRATIONAL
	| tIMAGINARY
;

user_variable
:
	ID
	| tIVAR
	| ID_GLOBAL
	| ID
	| tCVAR
;

keyword_variable
:
	NIL
	| SELF
	| TRUE
	| FALSE
	| KEYWORD_FILE
	| KEYWORD_LINE
	| KEYWORD_ENCODING
;

var_ref
:
	user_variable
	| keyword_variable
;

var_lhs
:
	user_variable
	| keyword_variable
;

backref
:
	tNTH_REF
	| tBACK_REF
;

superclass
:
	'<' expr term
;

f_arglist
:
	'(' f_args rparen
	| f_args term
;

args_tail
:
	f_kwarg ',' f_kwrest opt_f_block_arg
	| f_kwarg opt_f_block_arg
	| f_kwrest opt_f_block_arg
	| f_block_arg
;

opt_args_tail
:
	',' args_tail
	| /* none */
;

f_args
:
	f_arg ',' f_optarg ',' f_rest_arg opt_args_tail
	| f_arg ',' f_optarg ',' f_rest_arg ',' f_arg opt_args_tail
	| f_arg ',' f_optarg opt_args_tail
	| f_arg ',' f_optarg ',' f_arg opt_args_tail
	| f_arg ',' f_rest_arg opt_args_tail
	| f_arg ',' f_rest_arg ',' f_arg opt_args_tail
	| f_arg opt_args_tail
	| f_optarg ',' f_rest_arg opt_args_tail
	| f_optarg ',' f_rest_arg ',' f_arg opt_args_tail
	| f_optarg opt_args_tail
	| f_optarg ',' f_arg opt_args_tail
	| f_rest_arg opt_args_tail
	| f_rest_arg ',' f_arg opt_args_tail
	| args_tail
	| /* none */
;

f_bad_arg
:
	ID
	| tIVAR
	| ID_GLOBAL
	| tCVAR
;

f_norm_arg
:
	f_bad_arg
	| ID
;

f_arg_asgn
:
	f_norm_arg
;

f_arg_item
:
	f_arg_asgn
	| TOKEN_LPAREN f_margs rparen
;

f_arg
:
	f_arg_item
	/*% ripper[brace]: rb_ary_new3(1, get_value($1)) %*/
	| f_arg ',' f_arg_item
;

f_label
:
	tLABEL
;

f_kw
:
	f_label arg
	| f_label
;

f_block_kw
:
	f_label primary_value
	| f_label
;

f_block_kwarg
:
	f_block_kw
	| f_block_kwarg ',' f_block_kw
;

f_kwarg
:
	f_kw
	| f_kwarg ',' f_kw
;

kwrest_mark
:
	TOKEN_POW
	| TOKEN_DSTAR
;

f_kwrest
:
	kwrest_mark ID
	| kwrest_mark
;

f_opt
:
	f_arg_asgn '=' arg
;

f_block_opt
:
	f_arg_asgn '=' primary_value
;

f_block_optarg
:
	f_block_opt
	| f_block_optarg ',' f_block_opt
;

f_optarg
:
	f_opt
	| f_optarg ',' f_opt
;

restarg_mark
:
	'*'
	| TOKEN_STAR
;

f_rest_arg
:
	restarg_mark ID
	| restarg_mark
;

blkarg_mark
:
	'&'
	| TOKEN_AMPER
;

f_block_arg
:
	blkarg_mark ID
;

opt_f_block_arg
:
	',' f_block_arg
	| none
;

singleton
:
	var_ref
	| '(' expr rparen
;

assoc_list
:
	none
	| assocs trailer
;

assocs
:
	assoc
	/*% ripper[brace]: rb_ary_new3(1, get_value($1)) %*/
	| assocs ',' assoc
;

assoc
:
	arg TOKEN_ASSOC arg
	| tLABEL arg
	| tSTRING_BEG string_contents tLABEL_END arg
	| TOKEN_DSTAR arg
;

operation
:
	ID
	| ID
	| tFID
;

operation2
:
	ID
	| ID
	| tFID
	| op
;

operation3
:
	ID
	| tFID
	| op
;

dot_or_colon
:
	'.'
	| TOKEN_COLON2
;

call_op
:
	'.'
	| TOKEN_ANDDOT
;

call_op2
:
	call_op
	| TOKEN_COLON2
;

rparen
:
	'\n'? ')'
;

rbracket
:
	'\n'? ']'
;

trailer
: /* none */
	| '\n'
	| ','
;

term
:
	';'
	| '\n'
;

terms
:
	term
	| terms ';'
;

error
:
	'ERROR_TBC'
;

tFID
:
	'UNKNOWN_TBC'
;