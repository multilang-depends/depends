        keyword_class        "class"
        keyword_module       "module"
        keyword_def          "def"
        keyword_undef        "undef"
        keyword_begin        "begin"
        keyword_rescue       "rescue"
        keyword_ensure       "ensure"
        keyword_end          "end"
        keyword_if           "if"
        keyword_unless       "unless"
        keyword_then         "then"
        keyword_elsif        "elsif"
        keyword_else         "else"
        keyword_case         "case"
        keyword_when         "when"
        keyword_while        "while"
        keyword_until        "until"
        keyword_for          "for"
        keyword_break        "break"
        keyword_next         "next"
        keyword_redo         "redo"
        keyword_retry        "retry"
        keyword_in           "in"
        keyword_do           "do"
        keyword_do_cond      "do (for condition)"
        keyword_do_block     "do (for block)"
        keyword_do_LAMBDA    "do (for lambda)"
        keyword_return       "return"
        keyword_yield        "yield"
        keyword_super        "super"
        keyword_self         "self"
        keyword_nil          "nil"
        keyword_true         "true"
        keyword_false        "false"
        keyword_and          "and"
        keyword_or           "or"
        keyword_not          "not"
        modifier_if          "if (modifier)"
        modifier_unless      "unless (modifier)"
        modifier_while       "while (modifier)"
        modifier_until       "until (modifier)"
        modifier_rescue      "rescue (modifier)"
        keyword_alias        "alias"
        keyword_defined      "defined?"
        keyword_BEGIN        "BEGIN"
        keyword_END          "END"
        keyword__LINE__      "__LINE__"
        keyword__FILE__      "__FILE__"
        keyword__ENCODING__  "__ENCODING__"

%token <id>   tIDENTIFIER tFID tGVAR tIVAR tCONSTANT tCVAR tLABEL
%token <node> tINTEGER tFLOAT tRATIONAL tIMAGINARY tSTRING_CONTENT tCHAR
%token <node> tNTH_REF tBACK_REF
%token <num>  tREGEXP_END

%type <node> singleton strings string string1 xstring regexp
%type <node> string_contents xstring_contents regexp_contents string_content
%type <node> words symbols symbol_list qwords qsymbols word_list qword_list qsym_list word
%type <node> literal numeric simple_numeric dsym cpath
%type <node> top_compstmt top_stmts top_stmt begin_block
%type <node> bodystmt compstmt stmts stmt_or_begin stmt expr arg primary command command_call method_call
%type <node> expr_value expr_value_do arg_value primary_value fcall rel_expr
%type <node> if_tail opt_else case_body cases opt_rescue exc_list exc_var opt_ensure
%type <node> args call_args opt_call_args
%type <node> paren_args opt_paren_args args_tail opt_args_tail block_args_tail opt_block_args_tail
%type <node> command_args aref_args opt_block_arg block_arg var_ref var_lhs
%type <node> command_rhs arg_rhs
%type <node> command_asgn mrhs mrhs_arg superclass block_call block_command
%type <node> f_block_optarg f_block_opt
%type <node> f_arglist f_args f_arg f_arg_item f_optarg f_marg f_marg_list f_margs
%type <node> assoc_list assocs assoc undef_list backref string_dvar for_var
%type <node> block_param opt_block_param block_param_def f_opt
%type <node> f_kwarg f_kw f_block_kwarg f_block_kw
%type <node> bv_decls opt_bv_decl bvar
%type <node> lambda f_larglist lambda_body brace_body do_body
%type <node> brace_block cmd_brace_block do_block lhs none fitem
%type <node> mlhs mlhs_head mlhs_basic mlhs_item mlhs_node mlhs_post mlhs_inner
%type <id>   fsym keyword_variable user_variable sym symbol operation operation2 operation3
%type <id>   cname fname op f_rest_arg f_block_arg opt_f_block_arg f_norm_arg f_bad_arg
%type <id>   f_kwrest f_label f_arg_asgn call_op call_op2 reswords relop dot_or_colon
%token END_OF_INPUT 0	"end-of-input"
%token <id> '.'
/* escaped chars, should be ignored otherwise */
%token <id> '\\'	"backslash"
%token tSP		"escaped space"
%token <id> '\t' 	"escaped horizontal tab"
%token <id> '\f'	"escaped form feed"
%token <id> '\r'	"escaped carriage return"
%token <id> '\13'	"escaped vertical tab"
%token tUPLUS		RUBY_TOKEN(UPLUS)  "unary+"
%token tUMINUS		RUBY_TOKEN(UMINUS) "unary-"
%token tPOW		RUBY_TOKEN(POW)    "**"
%token tCMP		RUBY_TOKEN(CMP)    "<=>"
%token tEQ		RUBY_TOKEN(EQ)     "=="
%token tEQQ		RUBY_TOKEN(EQQ)    "==="
%token tNEQ		RUBY_TOKEN(NEQ)    "!="
%token tGEQ		RUBY_TOKEN(GEQ)    ">="
%token tLEQ		RUBY_TOKEN(LEQ)    "<="
%token tANDOP		RUBY_TOKEN(ANDOP)  "&&"
%token tOROP		RUBY_TOKEN(OROP)   "||"
%token tMATCH		RUBY_TOKEN(MATCH)  "=~"
%token tNMATCH		RUBY_TOKEN(NMATCH) "!~"
%token tDOT2		RUBY_TOKEN(DOT2)   ".."
%token tDOT3		RUBY_TOKEN(DOT3)   "..."
%token tAREF		RUBY_TOKEN(AREF)   "[]"
%token tASET		RUBY_TOKEN(ASET)   "[]="
%token tLSHFT		RUBY_TOKEN(LSHFT)  "<<"
%token tRSHFT		RUBY_TOKEN(RSHFT)  ">>"
%token <id> tANDDOT	RUBY_TOKEN(ANDDOT) "&."
%token <id> tCOLON2	RUBY_TOKEN(COLON2) "::"
%token tCOLON3		":: at EXPR_BEG"
%token <id> tOP_ASGN	/* +=, -=  etc. */
%token tASSOC		"=>"
%token tLPAREN		"("
%token tLPAREN_ARG	"( arg"
%token tRPAREN		")"
%token tLBRACK		"["
%token tLBRACE		"{"
%token tLBRACE_ARG	"{ arg"
%token tSTAR		"*"
%token tDSTAR		"**arg"
%token tAMPER		"&"
%token tLAMBDA		"->"
%token tSYMBEG tSTRING_BEG tXSTRING_BEG tREGEXP_BEG tWORDS_BEG tQWORDS_BEG tSYMBOLS_BEG tQSYMBOLS_BEG
%token tSTRING_DBEG tSTRING_DEND tSTRING_DVAR tSTRING_END tLAMBEG tLABEL_END

/*
 *	precedence table
 */

%nonassoc tLOWEST
%nonassoc tLBRACE_ARG

%nonassoc  modifier_if modifier_unless modifier_while modifier_until
%left  keyword_or keyword_and
%right keyword_not
%nonassoc keyword_defined
%right '=' tOP_ASGN
%left modifier_rescue
%right '?' ':'
%nonassoc tDOT2 tDOT3
%left  tOROP
%left  tANDOP
%nonassoc  tCMP tEQ tEQQ tNEQ tMATCH tNMATCH
%left  '>' tGEQ '<' tLEQ
%left  '|' '^'
%left  '&'
%left  tLSHFT tRSHFT
%left  '+' '-'
%left  '*' '/' '%'
%right tUMINUS_NUM tUMINUS
%right tPOW
%right '!' '~' tUPLUS

