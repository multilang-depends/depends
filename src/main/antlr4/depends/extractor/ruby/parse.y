program		:  {
			SET_LEX_STATE(EXPR_BEG);
			local_push(p, ifndef_ripper(1)+0);
		    }
		  top_compstmt
		    {
		    /*%%%*/
			if ($2 && !compile_for_eval) {
			    NODE *node = $2;
			    /* last expression should not be void */
			    if (nd_type(node) == NODE_BLOCK) {
				while (node->nd_next) {
				    node = node->nd_next;
				}
				node = node->nd_head;
			    }
			    node = remove_begin(node);
			    void_expr(p, node);
			}
			p->eval_tree = NEW_SCOPE(0, block_append(p, p->eval_tree, $2), &@$);
		    /*% %*/
		    /*% ripper[final]: program!($2) %*/
			local_pop(p);
		    }
		;

top_compstmt	: top_stmts opt_terms
		    {
			$$ = void_stmts(p, $1);
		    }
		;

top_stmts	: none
                    {
		    /*%%%*/
			$$ = NEW_BEGIN(0, &@$);
		    /*% %*/
		    /*% ripper: stmts_add!(stmts_new!, void_stmt!) %*/
		    }
		| top_stmt
		    {
		    /*%%%*/
			$$ = newline_node($1);
		    /*% %*/
		    /*% ripper: stmts_add!(stmts_new!, $1) %*/
		    }
		| top_stmts terms top_stmt
		    {
		    /*%%%*/
			$$ = block_append(p, $1, newline_node($3));
		    /*% %*/
		    /*% ripper: stmts_add!($1, $3) %*/
		    }
		| error top_stmt
		    {
			$$ = remove_begin($2);
		    }
		;

top_stmt	: stmt
		| keyword_BEGIN begin_block
		    {
			$$ = $2;
		    }
		;

begin_block	: '{' top_compstmt '}'
		    {
		    /*%%%*/
			p->eval_tree_begin = block_append(p, p->eval_tree_begin,
							  NEW_BEGIN($2, &@$));
			$$ = NEW_BEGIN(0, &@$);
		    /*% %*/
		    /*% ripper: BEGIN!($2) %*/
		    }
		;

bodystmt	: compstmt
		  opt_rescue
		  k_else {if (!$2) {yyerror1(&@3, "else without rescue is useless");}}
		  compstmt
		  opt_ensure
		    {
		    /*%%%*/
			$$ = new_bodystmt(p, $1, $2, $5, $6, &@$);
		    /*% %*/
		    /*% ripper: bodystmt!(escape_Qundef($1), escape_Qundef($2), escape_Qundef($5), escape_Qundef($6)) %*/
		    }
		| compstmt
		  opt_rescue
		  opt_ensure
		    {
		    /*%%%*/
			$$ = new_bodystmt(p, $1, $2, 0, $3, &@$);
		    /*% %*/
		    /*% ripper: bodystmt!(escape_Qundef($1), escape_Qundef($2), Qnil, escape_Qundef($3)) %*/
		    }
		;

compstmt	: stmts opt_terms
		    {
			$$ = void_stmts(p, $1);
		    }
		;

stmts		: none
                    {
		    /*%%%*/
			$$ = NEW_BEGIN(0, &@$);
		    /*% %*/
		    /*% ripper: stmts_add!(stmts_new!, void_stmt!) %*/
		    }
		| stmt_or_begin
		    {
		    /*%%%*/
			$$ = newline_node($1);
		    /*% %*/
		    /*% ripper: stmts_add!(stmts_new!, $1) %*/
		    }
		| stmts terms stmt_or_begin
		    {
		    /*%%%*/
			$$ = block_append(p, $1, newline_node($3));
		    /*% %*/
		    /*% ripper: stmts_add!($1, $3) %*/
		    }
		| error stmt
		    {
			$$ = remove_begin($2);
		    }
		;

stmt_or_begin	: stmt
                    {
			$$ = $1;
		    }
                | keyword_BEGIN
		    {
			yyerror1(&@1, "BEGIN is permitted only at toplevel");
		    }
		  begin_block
		    {
			$$ = $3;
		    }

stmt		: keyword_alias fitem {SET_LEX_STATE(EXPR_FNAME|EXPR_FITEM);} fitem
		    {
		    /*%%%*/
			$$ = NEW_ALIAS($2, $4, &@$);
		    /*% %*/
		    /*% ripper: alias!($2, $4) %*/
		    }
		| keyword_alias tGVAR tGVAR
		    {
		    /*%%%*/
			$$ = NEW_VALIAS($2, $3, &@$);
		    /*% %*/
		    /*% ripper: var_alias!($2, $3) %*/
		    }
		| keyword_alias tGVAR tBACK_REF
		    {
		    /*%%%*/
			char buf[2];
			buf[0] = '$';
			buf[1] = (char)$3->nd_nth;
			$$ = NEW_VALIAS($2, rb_intern2(buf, 2), &@$);
		    /*% %*/
		    /*% ripper: var_alias!($2, $3) %*/
		    }
		| keyword_alias tGVAR tNTH_REF
		    {
		    /*%%%*/
			yyerror1(&@3, "can't make alias for the number variables");
			$$ = NEW_BEGIN(0, &@$);
		    /*% %*/
		    /*% ripper[error]: alias_error!(var_alias!($2, $3)) %*/
		    }
		| keyword_undef undef_list
		    {
		    /*%%%*/
			$$ = $2;
		    /*% %*/
		    /*% ripper: undef!($2) %*/
		    }
		| stmt modifier_if expr_value
		    {
		    /*%%%*/
			$$ = new_if(p, $3, remove_begin($1), 0, &@$);
			fixpos($$, $3);
		    /*% %*/
		    /*% ripper: if_mod!($3, $1) %*/
		    }
		| stmt modifier_unless expr_value
		    {
		    /*%%%*/
			$$ = new_unless(p, $3, remove_begin($1), 0, &@$);
			fixpos($$, $3);
		    /*% %*/
		    /*% ripper: unless_mod!($3, $1) %*/
		    }
		| stmt modifier_while expr_value
		    {
		    /*%%%*/
			if ($1 && nd_type($1) == NODE_BEGIN) {
			    $$ = NEW_WHILE(cond(p, $3, &@3), $1->nd_body, 0, &@$);
			}
			else {
			    $$ = NEW_WHILE(cond(p, $3, &@3), $1, 1, &@$);
			}
		    /*% %*/
		    /*% ripper: while_mod!($3, $1) %*/
		    }
		| stmt modifier_until expr_value
		    {
		    /*%%%*/
			if ($1 && nd_type($1) == NODE_BEGIN) {
			    $$ = NEW_UNTIL(cond(p, $3, &@3), $1->nd_body, 0, &@$);
			}
			else {
			    $$ = NEW_UNTIL(cond(p, $3, &@3), $1, 1, &@$);
			}
		    /*% %*/
		    /*% ripper: until_mod!($3, $1) %*/
		    }
		| stmt modifier_rescue stmt
		    {
		    /*%%%*/
			NODE *resq;
			YYLTYPE loc = code_loc_gen(&@2, &@3);
			resq = NEW_RESBODY(0, remove_begin($3), 0, &loc);
			$$ = NEW_RESCUE(remove_begin($1), resq, 0, &@$);
		    /*% %*/
		    /*% ripper: rescue_mod!($1, $3) %*/
		    }
		| keyword_END '{' compstmt '}'
		    {
			if (p->in_def) {
			    rb_warn0("END in method; use at_exit");
			}
		    /*%%%*/
			{
			    NODE *scope = NEW_NODE(
				NODE_SCOPE, 0 /* tbl */, $3 /* body */, 0 /* args */, &@$);
			    $$ = NEW_POSTEXE(scope, &@$);
			}
		    /*% %*/
		    /*% ripper: END!($3) %*/
		    }
		| command_asgn
		| mlhs '=' command_call
		    {
		    /*%%%*/
			value_expr($3);
			$$ = node_assign(p, $1, $3, &@$);
		    /*% %*/
		    /*% ripper: massign!($1, $3) %*/
		    }
		| lhs '=' mrhs
		    {
		    /*%%%*/
			value_expr($3);
			$$ = node_assign(p, $1, $3, &@$);
		    /*% %*/
		    /*% ripper: assign!($1, $3) %*/
		    }
		| mlhs '=' mrhs_arg
		    {
		    /*%%%*/
			$$ = node_assign(p, $1, $3, &@$);
		    /*% %*/
		    /*% ripper: massign!($1, $3) %*/
		    }
		| expr
		;

command_asgn	: lhs '=' command_rhs
		    {
		    /*%%%*/
			value_expr($3);
			$$ = node_assign(p, $1, $3, &@$);
		    /*% %*/
		    /*% ripper: assign!($1, $3) %*/
		    }
		| var_lhs tOP_ASGN command_rhs
		    {
		    /*%%%*/
			value_expr($3);
			$$ = new_op_assign(p, $1, $2, $3, &@$);
		    /*% %*/
		    /*% ripper: opassign!($1, $2, $3) %*/
		    }
		| primary_value '[' opt_call_args rbracket tOP_ASGN command_rhs
		    {
		    /*%%%*/
			value_expr($6);
			$$ = new_ary_op_assign(p, $1, $3, $5, $6, &@3, &@$);
		    /*% %*/
		    /*% ripper: opassign!(aref_field!($1, escape_Qundef($3)), $5, $6) %*/

		    }
		| primary_value call_op tIDENTIFIER tOP_ASGN command_rhs
		    {
		    /*%%%*/
			value_expr($5);
			$$ = new_attr_op_assign(p, $1, $2, $3, $4, $5, &@$);
		    /*% %*/
		    /*% ripper: opassign!(field!($1, $2, $3), $4, $5) %*/
		    }
		| primary_value call_op tCONSTANT tOP_ASGN command_rhs
		    {
		    /*%%%*/
			value_expr($5);
			$$ = new_attr_op_assign(p, $1, $2, $3, $4, $5, &@$);
		    /*% %*/
		    /*% ripper: opassign!(field!($1, $2, $3), $4, $5) %*/
		    }
		| primary_value tCOLON2 tCONSTANT tOP_ASGN command_rhs
		    {
		    /*%%%*/
			YYLTYPE loc = code_loc_gen(&@1, &@3);
			$$ = new_const_op_assign(p, NEW_COLON2($1, $3, &loc), $4, $5, &@$);
		    /*% %*/
		    /*% ripper: opassign!(const_path_field!($1, $3), $4, $5) %*/
		    }
		| primary_value tCOLON2 tIDENTIFIER tOP_ASGN command_rhs
		    {
		    /*%%%*/
			value_expr($5);
			$$ = new_attr_op_assign(p, $1, ID2VAL(idCOLON2), $3, $4, $5, &@$);
		    /*% %*/
		    /*% ripper: opassign!(field!($1, ID2VAL(idCOLON2), $3), $4, $5) %*/
		    }
		| backref tOP_ASGN command_rhs
		    {
		    /*%%%*/
			rb_backref_error(p, $1);
			$$ = NEW_BEGIN(0, &@$);
		    /*% %*/
		    /*% ripper[error]: assign_error!(assign!(var_field(p, $1), $3)) %*/
		    }
		;

command_rhs	: command_call   %prec tOP_ASGN
		    {
			value_expr($1);
			$$ = $1;
		    }
		| command_call modifier_rescue stmt
		    {
		    /*%%%*/
			YYLTYPE loc = code_loc_gen(&@2, &@3);
			value_expr($1);
			$$ = NEW_RESCUE($1, NEW_RESBODY(0, remove_begin($3), 0, &loc), 0, &@$);
		    /*% %*/
		    /*% ripper: rescue_mod!($1, $3) %*/
		    }
		| command_asgn
		;

expr		: command_call
		| expr keyword_and expr
		    {
			$$ = logop(p, idAND, $1, $3, &@2, &@$);
		    }
		| expr keyword_or expr
		    {
			$$ = logop(p, idOR, $1, $3, &@2, &@$);
		    }
		| keyword_not opt_nl expr
		    {
			$$ = call_uni_op(p, method_cond(p, $3, &@3), METHOD_NOT, &@1, &@$);
		    }
		| '!' command_call
		    {
			$$ = call_uni_op(p, method_cond(p, $2, &@2), '!', &@1, &@$);
		    }
		| arg
		;

expr_value	: expr
		    {
			value_expr($1);
			$$ = $1;
		    }
		;

expr_value_do	: {COND_PUSH(1);} expr_value do {COND_POP();}
		    {
			$$ = $2;
		    }


command_call	: command
		| block_command
		;

block_command	: block_call
		| block_call call_op2 operation2 command_args
		    {
		    /*%%%*/
			$$ = new_qcall(p, $2, $1, $3, $4, &@3, &@$);
		    /*% %*/
		    /*% ripper: method_add_arg!(call!($1, $2, $3), $4) %*/
		    }
		;

cmd_brace_block	: tLBRACE_ARG brace_body '}'
		    {
			$$ = $2;
		    /*%%%*/
			$$->nd_body->nd_loc = code_loc_gen(&@1, &@3);
			nd_set_line($$, @1.end_pos.lineno);
		    /*% %*/
		    }
		;

fcall		: operation
		    {
		    /*%%%*/
			$$ = NEW_FCALL($1, 0, &@$);
			nd_set_line($$, p->tokline);
		    /*% %*/
		    /*% ripper: $1 %*/
		    }
		;

command		: fcall command_args       %prec tLOWEST
		    {
		    /*%%%*/
			$1->nd_args = $2;
			nd_set_last_loc($1, @2.end_pos);
			$$ = $1;
		    /*% %*/
		    /*% ripper: command!($1, $2) %*/
		    }
		| fcall command_args cmd_brace_block
		    {
		    /*%%%*/
			block_dup_check(p, $2, $3);
			$1->nd_args = $2;
			$$ = method_add_block(p, $1, $3, &@$);
			fixpos($$, $1);
			nd_set_last_loc($1, @2.end_pos);
		    /*% %*/
		    /*% ripper: method_add_block!(command!($1, $2), $3) %*/
		    }
		| primary_value call_op operation2 command_args	%prec tLOWEST
		    {
		    /*%%%*/
			$$ = new_command_qcall(p, $2, $1, $3, $4, Qnull, &@3, &@$);
		    /*% %*/
		    /*% ripper: command_call!($1, $2, $3, $4) %*/
		    }
		| primary_value call_op operation2 command_args cmd_brace_block
		    {
		    /*%%%*/
			$$ = new_command_qcall(p, $2, $1, $3, $4, $5, &@3, &@$);
		    /*% %*/
		    /*% ripper: method_add_block!(command_call!($1, $2, $3, $4), $5) %*/
		    }
		| primary_value tCOLON2 operation2 command_args	%prec tLOWEST
		    {
		    /*%%%*/
			$$ = new_command_qcall(p, ID2VAL(idCOLON2), $1, $3, $4, Qnull, &@3, &@$);
		    /*% %*/
		    /*% ripper: command_call!($1, ID2VAL(idCOLON2), $3, $4) %*/
		    }
		| primary_value tCOLON2 operation2 command_args cmd_brace_block
		    {
		    /*%%%*/
			$$ = new_command_qcall(p, ID2VAL(idCOLON2), $1, $3, $4, $5, &@3, &@$);
		    /*% %*/
		    /*% ripper: method_add_block!(command_call!($1, ID2VAL(idCOLON2), $3, $4), $5) %*/
		   }
		| keyword_super command_args
		    {
		    /*%%%*/
			$$ = NEW_SUPER($2, &@$);
			fixpos($$, $2);
		    /*% %*/
		    /*% ripper: super!($2) %*/
		    }
		| keyword_yield command_args
		    {
		    /*%%%*/
			$$ = new_yield(p, $2, &@$);
			fixpos($$, $2);
		    /*% %*/
		    /*% ripper: yield!($2) %*/
		    }
		| k_return call_args
		    {
		    /*%%%*/
			$$ = NEW_RETURN(ret_args(p, $2), &@$);
		    /*% %*/
		    /*% ripper: return!($2) %*/
		    }
		| keyword_break call_args
		    {
		    /*%%%*/
			$$ = NEW_BREAK(ret_args(p, $2), &@$);
		    /*% %*/
		    /*% ripper: break!($2) %*/
		    }
		| keyword_next call_args
		    {
		    /*%%%*/
			$$ = NEW_NEXT(ret_args(p, $2), &@$);
		    /*% %*/
		    /*% ripper: next!($2) %*/
		    }
		;

mlhs		: mlhs_basic
		| tLPAREN mlhs_inner rparen
		    {
		    /*%%%*/
			$$ = $2;
		    /*% %*/
		    /*% ripper: mlhs_paren!($2) %*/
		    }
		;

mlhs_inner	: mlhs_basic
		| tLPAREN mlhs_inner rparen
		    {
		    /*%%%*/
			$$ = NEW_MASGN(NEW_LIST($2, &@$), 0, &@$);
		    /*% %*/
		    /*% ripper: mlhs_paren!($2) %*/
		    }
		;

mlhs_basic	: mlhs_head
		    {
		    /*%%%*/
			$$ = NEW_MASGN($1, 0, &@$);
		    /*% %*/
		    /*% ripper: $1 %*/
		    }
		| mlhs_head mlhs_item
		    {
		    /*%%%*/
			$$ = NEW_MASGN(list_append(p, $1,$2), 0, &@$);
		    /*% %*/
		    /*% ripper: mlhs_add!($1, $2) %*/
		    }
		| mlhs_head tSTAR mlhs_node
		    {
		    /*%%%*/
			$$ = NEW_MASGN($1, $3, &@$);
		    /*% %*/
		    /*% ripper: mlhs_add_star!($1, $3) %*/
		    }
		| mlhs_head tSTAR mlhs_node ',' mlhs_post
		    {
		    /*%%%*/
			$$ = NEW_MASGN($1, NEW_POSTARG($3,$5,&@$), &@$);
		    /*% %*/
		    /*% ripper: mlhs_add_post!(mlhs_add_star!($1, $3), $5) %*/
		    }
		| mlhs_head tSTAR
		    {
		    /*%%%*/
			$$ = NEW_MASGN($1, NODE_SPECIAL_NO_NAME_REST, &@$);
		    /*% %*/
		    /*% ripper: mlhs_add_star!($1, Qnil) %*/
		    }
		| mlhs_head tSTAR ',' mlhs_post
		    {
		    /*%%%*/
			$$ = NEW_MASGN($1, NEW_POSTARG(NODE_SPECIAL_NO_NAME_REST, $4, &@$), &@$);
		    /*% %*/
		    /*% ripper: mlhs_add_post!(mlhs_add_star!($1, Qnil), $4) %*/
		    }
		| tSTAR mlhs_node
		    {
		    /*%%%*/
			$$ = NEW_MASGN(0, $2, &@$);
		    /*% %*/
		    /*% ripper: mlhs_add_star!(mlhs_new!, $2) %*/
		    }
		| tSTAR mlhs_node ',' mlhs_post
		    {
		    /*%%%*/
			$$ = NEW_MASGN(0, NEW_POSTARG($2,$4,&@$), &@$);
		    /*% %*/
		    /*% ripper: mlhs_add_post!(mlhs_add_star!(mlhs_new!, $2), $4) %*/
		    }
		| tSTAR
		    {
		    /*%%%*/
			$$ = NEW_MASGN(0, NODE_SPECIAL_NO_NAME_REST, &@$);
		    /*% %*/
		    /*% ripper: mlhs_add_star!(mlhs_new!, Qnil) %*/
		    }
		| tSTAR ',' mlhs_post
		    {
		    /*%%%*/
			$$ = NEW_MASGN(0, NEW_POSTARG(NODE_SPECIAL_NO_NAME_REST, $3, &@$), &@$);
		    /*% %*/
		    /*% ripper: mlhs_add_post!(mlhs_add_star!(mlhs_new!, Qnil), $3) %*/
		    }
		;

mlhs_item	: mlhs_node
		| tLPAREN mlhs_inner rparen
		    {
		    /*%%%*/
			$$ = $2;
		    /*% %*/
		    /*% ripper: mlhs_paren!($2) %*/
		    }
		;

mlhs_head	: mlhs_item ','
		    {
		    /*%%%*/
			$$ = NEW_LIST($1, &@1);
		    /*% %*/
		    /*% ripper: mlhs_add!(mlhs_new!, $1) %*/
		    }
		| mlhs_head mlhs_item ','
		    {
		    /*%%%*/
			$$ = list_append(p, $1, $2);
		    /*% %*/
		    /*% ripper: mlhs_add!($1, $2) %*/
		    }
		;

mlhs_post	: mlhs_item
		    {
		    /*%%%*/
			$$ = NEW_LIST($1, &@$);
		    /*% %*/
		    /*% ripper: mlhs_add!(mlhs_new!, $1) %*/
		    }
		| mlhs_post ',' mlhs_item
		    {
		    /*%%%*/
			$$ = list_append(p, $1, $3);
		    /*% %*/
		    /*% ripper: mlhs_add!($1, $3) %*/
		    }
		;

mlhs_node	: user_variable
		    {
		    /*%%%*/
			$$ = assignable(p, $1, 0, &@$);
		    /*% %*/
		    /*% ripper: assignable(p, var_field(p, $1)) %*/
		    }
		| keyword_variable
		    {
		    /*%%%*/
			$$ = assignable(p, $1, 0, &@$);
		    /*% %*/
		    /*% ripper: assignable(p, var_field(p, $1)) %*/
		    }
		| primary_value '[' opt_call_args rbracket
		    {
		    /*%%%*/
			$$ = aryset(p, $1, $3, &@$);
		    /*% %*/
		    /*% ripper: aref_field!($1, escape_Qundef($3)) %*/
		    }
		| primary_value call_op tIDENTIFIER
		    {
		    /*%%%*/
			$$ = attrset(p, $1, $2, $3, &@$);
		    /*% %*/
		    /*% ripper: field!($1, $2, $3) %*/
		    }
		| primary_value tCOLON2 tIDENTIFIER
		    {
		    /*%%%*/
			$$ = attrset(p, $1, idCOLON2, $3, &@$);
		    /*% %*/
		    /*% ripper: const_path_field!($1, $3) %*/
		    }
		| primary_value call_op tCONSTANT
		    {
		    /*%%%*/
			$$ = attrset(p, $1, $2, $3, &@$);
		    /*% %*/
		    /*% ripper: field!($1, $2, $3) %*/
		    }
		| primary_value tCOLON2 tCONSTANT
		    {
		    /*%%%*/
			$$ = const_decl(p, NEW_COLON2($1, $3, &@$), &@$);
		    /*% %*/
		    /*% ripper: const_decl(p, const_path_field!($1, $3)) %*/
		    }
		| tCOLON3 tCONSTANT
		    {
		    /*%%%*/
			$$ = const_decl(p, NEW_COLON3($2, &@$), &@$);
		    /*% %*/
		    /*% ripper: const_decl(p, top_const_field!($2)) %*/
		    }
		| backref
		    {
		    /*%%%*/
			rb_backref_error(p, $1);
			$$ = NEW_BEGIN(0, &@$);
		    /*% %*/
		    /*% ripper[error]: assign_error!(var_field(p, $1)) %*/
		    }
		;

lhs		: user_variable
		    {
		    /*%%%*/
			$$ = assignable(p, $1, 0, &@$);
		    /*% %*/
		    /*% ripper: assignable(p, var_field(p, $1)) %*/
		    }
		| keyword_variable
		    {
		    /*%%%*/
			$$ = assignable(p, $1, 0, &@$);
		    /*% %*/
		    /*% ripper: assignable(p, var_field(p, $1)) %*/
		    }
		| primary_value '[' opt_call_args rbracket
		    {
		    /*%%%*/
			$$ = aryset(p, $1, $3, &@$);
		    /*% %*/
		    /*% ripper: aref_field!($1, escape_Qundef($3)) %*/
		    }
		| primary_value call_op tIDENTIFIER
		    {
		    /*%%%*/
			$$ = attrset(p, $1, $2, $3, &@$);
		    /*% %*/
		    /*% ripper: field!($1, $2, $3) %*/
		    }
		| primary_value tCOLON2 tIDENTIFIER
		    {
		    /*%%%*/
			$$ = attrset(p, $1, idCOLON2, $3, &@$);
		    /*% %*/
		    /*% ripper: field!($1, ID2VAL(idCOLON2), $3) %*/
		    }
		| primary_value call_op tCONSTANT
		    {
		    /*%%%*/
			$$ = attrset(p, $1, $2, $3, &@$);
		    /*% %*/
		    /*% ripper: field!($1, $2, $3) %*/
		    }
		| primary_value tCOLON2 tCONSTANT
		    {
		    /*%%%*/
			$$ = const_decl(p, NEW_COLON2($1, $3, &@$), &@$);
		    /*% %*/
		    /*% ripper: const_decl(p, const_path_field!($1, $3)) %*/
		    }
		| tCOLON3 tCONSTANT
		    {
		    /*%%%*/
			$$ = const_decl(p, NEW_COLON3($2, &@$), &@$);
		    /*% %*/
		    /*% ripper: const_decl(p, top_const_field!($2)) %*/
		    }
		| backref
		    {
		    /*%%%*/
			rb_backref_error(p, $1);
			$$ = NEW_BEGIN(0, &@$);
		    /*% %*/
		    /*% ripper[error]: assign_error!(var_field(p, $1)) %*/
		    }
		;

cname		: tIDENTIFIER
		    {
		    /*%%%*/
			yyerror1(&@1, "class/module name must be CONSTANT");
		    /*% %*/
		    /*% ripper[error]: class_name_error!($1) %*/
		    }
		| tCONSTANT
		;

cpath		: tCOLON3 cname
		    {
		    /*%%%*/
			$$ = NEW_COLON3($2, &@$);
		    /*% %*/
		    /*% ripper: top_const_ref!($2) %*/
		    }
		| cname
		    {
		    /*%%%*/
			$$ = NEW_COLON2(0, $$, &@$);
		    /*% %*/
		    /*% ripper: const_ref!($1) %*/
		    }
		| primary_value tCOLON2 cname
		    {
		    /*%%%*/
			$$ = NEW_COLON2($1, $3, &@$);
		    /*% %*/
		    /*% ripper: const_path_ref!($1, $3) %*/
		    }
		;

fname		: tIDENTIFIER
		| tCONSTANT
		| tFID
		| op
		    {
			SET_LEX_STATE(EXPR_ENDFN);
			$$ = $1;
		    }
		| reswords
		    {
			SET_LEX_STATE(EXPR_ENDFN);
			$$ = $1;
		    }
		;

fsym		: fname
		| symbol
		;

fitem		: fsym
		    {
		    /*%%%*/
			$$ = NEW_LIT(ID2SYM($1), &@$);
		    /*% %*/
		    /*% ripper: symbol_literal!($1) %*/
		    }
		| dsym
		;

undef_list	: fitem
		    {
		    /*%%%*/
			$$ = NEW_UNDEF($1, &@$);
		    /*% %*/
		    /*% ripper: rb_ary_new3(1, get_value($1)) %*/
		    }
		| undef_list ',' {SET_LEX_STATE(EXPR_FNAME|EXPR_FITEM);} fitem
		    {
		    /*%%%*/
			NODE *undef = NEW_UNDEF($4, &@4);
			$$ = block_append(p, $1, undef);
		    /*% %*/
		    /*% ripper: rb_ary_push($1, get_value($4)) %*/
		    }
		;

op		: '|'		{ ifndef_ripper($$ = '|'); }
		| '^'		{ ifndef_ripper($$ = '^'); }
		| '&'		{ ifndef_ripper($$ = '&'); }
		| tCMP		{ ifndef_ripper($$ = tCMP); }
		| tEQ		{ ifndef_ripper($$ = tEQ); }
		| tEQQ		{ ifndef_ripper($$ = tEQQ); }
		| tMATCH	{ ifndef_ripper($$ = tMATCH); }
		| tNMATCH	{ ifndef_ripper($$ = tNMATCH); }
		| '>'		{ ifndef_ripper($$ = '>'); }
		| tGEQ		{ ifndef_ripper($$ = tGEQ); }
		| '<'		{ ifndef_ripper($$ = '<'); }
		| tLEQ		{ ifndef_ripper($$ = tLEQ); }
		| tNEQ		{ ifndef_ripper($$ = tNEQ); }
		| tLSHFT	{ ifndef_ripper($$ = tLSHFT); }
		| tRSHFT	{ ifndef_ripper($$ = tRSHFT); }
		| '+'		{ ifndef_ripper($$ = '+'); }
		| '-'		{ ifndef_ripper($$ = '-'); }
		| '*'		{ ifndef_ripper($$ = '*'); }
		| tSTAR		{ ifndef_ripper($$ = '*'); }
		| '/'		{ ifndef_ripper($$ = '/'); }
		| '%'		{ ifndef_ripper($$ = '%'); }
		| tPOW		{ ifndef_ripper($$ = tPOW); }
		| tDSTAR	{ ifndef_ripper($$ = tDSTAR); }
		| '!'		{ ifndef_ripper($$ = '!'); }
		| '~'		{ ifndef_ripper($$ = '~'); }
		| tUPLUS	{ ifndef_ripper($$ = tUPLUS); }
		| tUMINUS	{ ifndef_ripper($$ = tUMINUS); }
		| tAREF		{ ifndef_ripper($$ = tAREF); }
		| tASET		{ ifndef_ripper($$ = tASET); }
		| '`'		{ ifndef_ripper($$ = '`'); }
		;

reswords	: keyword__LINE__ | keyword__FILE__ | keyword__ENCODING__
		| keyword_BEGIN | keyword_END
		| keyword_alias | keyword_and | keyword_begin
		| keyword_break | keyword_case | keyword_class | keyword_def
		| keyword_defined | keyword_do | keyword_else | keyword_elsif
		| keyword_end | keyword_ensure | keyword_false
		| keyword_for | keyword_in | keyword_module | keyword_next
		| keyword_nil | keyword_not | keyword_or | keyword_redo
		| keyword_rescue | keyword_retry | keyword_return | keyword_self
		| keyword_super | keyword_then | keyword_true | keyword_undef
		| keyword_when | keyword_yield | keyword_if | keyword_unless
		| keyword_while | keyword_until
		;

arg		: lhs '=' arg_rhs
		    {
		    /*%%%*/
			$$ = node_assign(p, $1, $3, &@$);
		    /*% %*/
		    /*% ripper: assign!($1, $3) %*/
		    }
		| var_lhs tOP_ASGN arg_rhs
		    {
		    /*%%%*/
			$$ = new_op_assign(p, $1, $2, $3, &@$);
		    /*% %*/
		    /*% ripper: opassign!($1, $2, $3) %*/
		    }
		| primary_value '[' opt_call_args rbracket tOP_ASGN arg_rhs
		    {
		    /*%%%*/
			value_expr($6);
			$$ = new_ary_op_assign(p, $1, $3, $5, $6, &@3, &@$);
		    /*% %*/
		    /*% ripper: opassign!(aref_field!($1, escape_Qundef($3)), $5, $6) %*/
		    }
		| primary_value call_op tIDENTIFIER tOP_ASGN arg_rhs
		    {
		    /*%%%*/
			value_expr($5);
			$$ = new_attr_op_assign(p, $1, $2, $3, $4, $5, &@$);
		    /*% %*/
		    /*% ripper: opassign!(field!($1, $2, $3), $4, $5) %*/
		    }
		| primary_value call_op tCONSTANT tOP_ASGN arg_rhs
		    {
		    /*%%%*/
			value_expr($5);
			$$ = new_attr_op_assign(p, $1, $2, $3, $4, $5, &@$);
		    /*% %*/
		    /*% ripper: opassign!(field!($1, $2, $3), $4, $5) %*/
		    }
		| primary_value tCOLON2 tIDENTIFIER tOP_ASGN arg_rhs
		    {
		    /*%%%*/
			value_expr($5);
			$$ = new_attr_op_assign(p, $1, ID2VAL(idCOLON2), $3, $4, $5, &@$);
		    /*% %*/
		    /*% ripper: opassign!(field!($1, ID2VAL(idCOLON2), $3), $4, $5) %*/
		    }
		| primary_value tCOLON2 tCONSTANT tOP_ASGN arg_rhs
		    {
		    /*%%%*/
			YYLTYPE loc = code_loc_gen(&@1, &@3);
			$$ = new_const_op_assign(p, NEW_COLON2($1, $3, &loc), $4, $5, &@$);
		    /*% %*/
		    /*% ripper: opassign!(const_path_field!($1, $3), $4, $5) %*/
		    }
		| tCOLON3 tCONSTANT tOP_ASGN arg_rhs
		    {
		    /*%%%*/
			$$ = new_const_op_assign(p, NEW_COLON3($2, &@$), $3, $4, &@$);
		    /*% %*/
		    /*% ripper: opassign!(top_const_field!($2), $3, $4) %*/
		    }
		| backref tOP_ASGN arg_rhs
		    {
		    /*%%%*/
			rb_backref_error(p, $1);
			$$ = NEW_BEGIN(0, &@$);
		    /*% %*/
		    /*% ripper[error]: assign_error!(opassign!(var_field(p, $1), $2, $3)) %*/
		    }
		| arg tDOT2 arg
		    {
		    /*%%%*/
			value_expr($1);
			value_expr($3);
			$$ = NEW_DOT2($1, $3, &@$);
		    /*% %*/
		    /*% ripper: dot2!($1, $3) %*/
		    }
		| arg tDOT3 arg
		    {
		    /*%%%*/
			value_expr($1);
			value_expr($3);
			$$ = NEW_DOT3($1, $3, &@$);
		    /*% %*/
		    /*% ripper: dot3!($1, $3) %*/
		    }
		| arg tDOT2
		    {
		    /*%%%*/
                        YYLTYPE loc;
                        loc.beg_pos = @2.end_pos;
                        loc.end_pos = @2.end_pos;

			value_expr($1);
			$$ = NEW_DOT2($1, new_nil(&loc), &@$);
		    /*% %*/
		    /*% ripper: dot2!($1, Qnil) %*/
		    }
		| arg tDOT3
		    {
		    /*%%%*/
                        YYLTYPE loc;
                        loc.beg_pos = @2.end_pos;
                        loc.end_pos = @2.end_pos;

			value_expr($1);
			$$ = NEW_DOT3($1, new_nil(&loc), &@$);
		    /*% %*/
		    /*% ripper: dot3!($1, Qnil) %*/
		    }
		| arg '+' arg
		    {
			$$ = call_bin_op(p, $1, '+', $3, &@2, &@$);
		    }
		| arg '-' arg
		    {
			$$ = call_bin_op(p, $1, '-', $3, &@2, &@$);
		    }
		| arg '*' arg
		    {
			$$ = call_bin_op(p, $1, '*', $3, &@2, &@$);
		    }
		| arg '/' arg
		    {
			$$ = call_bin_op(p, $1, '/', $3, &@2, &@$);
		    }
		| arg '%' arg
		    {
			$$ = call_bin_op(p, $1, '%', $3, &@2, &@$);
		    }
		| arg tPOW arg
		    {
			$$ = call_bin_op(p, $1, idPow, $3, &@2, &@$);
		    }
		| tUMINUS_NUM simple_numeric tPOW arg
		    {
			$$ = call_uni_op(p, call_bin_op(p, $2, idPow, $4, &@2, &@$), idUMinus, &@1, &@$);
		    }
		| tUPLUS arg
		    {
			$$ = call_uni_op(p, $2, idUPlus, &@1, &@$);
		    }
		| tUMINUS arg
		    {
			$$ = call_uni_op(p, $2, idUMinus, &@1, &@$);
		    }
		| arg '|' arg
		    {
			$$ = call_bin_op(p, $1, '|', $3, &@2, &@$);
		    }
		| arg '^' arg
		    {
			$$ = call_bin_op(p, $1, '^', $3, &@2, &@$);
		    }
		| arg '&' arg
		    {
			$$ = call_bin_op(p, $1, '&', $3, &@2, &@$);
		    }
		| arg tCMP arg
		    {
			$$ = call_bin_op(p, $1, idCmp, $3, &@2, &@$);
		    }
		| rel_expr   %prec tCMP
		| arg tEQ arg
		    {
			$$ = call_bin_op(p, $1, idEq, $3, &@2, &@$);
		    }
		| arg tEQQ arg
		    {
			$$ = call_bin_op(p, $1, idEqq, $3, &@2, &@$);
		    }
		| arg tNEQ arg
		    {
			$$ = call_bin_op(p, $1, idNeq, $3, &@2, &@$);
		    }
		| arg tMATCH arg
		    {
			$$ = match_op(p, $1, $3, &@2, &@$);
		    }
		| arg tNMATCH arg
		    {
			$$ = call_bin_op(p, $1, idNeqTilde, $3, &@2, &@$);
		    }
		| '!' arg
		    {
			$$ = call_uni_op(p, method_cond(p, $2, &@2), '!', &@1, &@$);
		    }
		| '~' arg
		    {
			$$ = call_uni_op(p, $2, '~', &@1, &@$);
		    }
		| arg tLSHFT arg
		    {
			$$ = call_bin_op(p, $1, idLTLT, $3, &@2, &@$);
		    }
		| arg tRSHFT arg
		    {
			$$ = call_bin_op(p, $1, idGTGT, $3, &@2, &@$);
		    }
		| arg tANDOP arg
		    {
			$$ = logop(p, idANDOP, $1, $3, &@2, &@$);
		    }
		| arg tOROP arg
		    {
			$$ = logop(p, idOROP, $1, $3, &@2, &@$);
		    }
		| keyword_defined opt_nl {p->in_defined = 1;} arg
		    {
			p->in_defined = 0;
			$$ = new_defined(p, $4, &@$);
		    }
		| arg '?' arg opt_nl ':' arg
		    {
		    /*%%%*/
			value_expr($1);
			$$ = new_if(p, $1, $3, $6, &@$);
			fixpos($$, $1);
		    /*% %*/
		    /*% ripper: ifop!($1, $3, $6) %*/
		    }
		| primary
		    {
			$$ = $1;
		    }
		;

relop		: '>'  {$$ = '>';}
		| '<'  {$$ = '<';}
		| tGEQ {$$ = idGE;}
		| tLEQ {$$ = idLE;}
		;

rel_expr	: arg relop arg   %prec '>'
		    {
			$$ = call_bin_op(p, $1, $2, $3, &@2, &@$);
		    }
		| rel_expr relop arg   %prec '>'
		    {
			rb_warning1("comparison '%s' after comparison", WARN_ID($2));
			$$ = call_bin_op(p, $1, $2, $3, &@2, &@$);
		    }
		;

arg_value	: arg
		    {
			value_expr($1);
			$$ = $1;
		    }
		;

aref_args	: none
		| args trailer
		    {
			$$ = $1;
		    }
		| args ',' assocs trailer
		    {
		    /*%%%*/
			$$ = $3 ? arg_append(p, $1, new_hash(p, $3, &@3), &@$) : $1;
		    /*% %*/
		    /*% ripper: args_add!($1, bare_assoc_hash!($3)) %*/
		    }
		| assocs trailer
		    {
		    /*%%%*/
			$$ = $1 ? NEW_LIST(new_hash(p, $1, &@1), &@$) : 0;
		    /*% %*/
		    /*% ripper: args_add!(args_new!, bare_assoc_hash!($1)) %*/
		    }
		;

arg_rhs 	: arg   %prec tOP_ASGN
		    {
			value_expr($1);
			$$ = $1;
		    }
		| arg modifier_rescue arg
		    {
		    /*%%%*/
			YYLTYPE loc = code_loc_gen(&@2, &@3);
			value_expr($1);
			$$ = NEW_RESCUE($1, NEW_RESBODY(0, remove_begin($3), 0, &loc), 0, &@$);
		    /*% %*/
		    /*% ripper: rescue_mod!($1, $3) %*/
		    }
		;

paren_args	: '(' opt_call_args rparen
		    {
		    /*%%%*/
			$$ = $2;
		    /*% %*/
		    /*% ripper: arg_paren!(escape_Qundef($2)) %*/
		    }
		;

opt_paren_args	: none
		| paren_args
		;

opt_call_args	: none
		| call_args
		| args ','
		    {
		      $$ = $1;
		    }
		| args ',' assocs ','
		    {
		    /*%%%*/
			$$ = $3 ? arg_append(p, $1, new_hash(p, $3, &@3), &@$) : $1;
		    /*% %*/
		    /*% ripper: args_add!($1, bare_assoc_hash!($3)) %*/
		    }
		| assocs ','
		    {
		    /*%%%*/
			$$ = $1 ? NEW_LIST(new_hash(p, $1, &@1), &@1) : 0;
		    /*% %*/
		    /*% ripper: args_add!(args_new!, bare_assoc_hash!($1)) %*/
		    }
		;

call_args	: command
		    {
		    /*%%%*/
			value_expr($1);
			$$ = NEW_LIST($1, &@$);
		    /*% %*/
		    /*% ripper: args_add!(args_new!, $1) %*/
		    }
		| args opt_block_arg
		    {
		    /*%%%*/
			$$ = arg_blk_pass($1, $2);
		    /*% %*/
		    /*% ripper: args_add_block!($1, $2) %*/
		    }
		| assocs opt_block_arg
		    {
		    /*%%%*/
			$$ = $1 ? NEW_LIST(new_hash(p, $1, &@1), &@1) : 0;
			$$ = arg_blk_pass($$, $2);
		    /*% %*/
		    /*% ripper: args_add_block!(args_add!(args_new!, bare_assoc_hash!($1)), $2) %*/
		    }
		| args ',' assocs opt_block_arg
		    {
		    /*%%%*/
			$$ = $3 ? arg_append(p, $1, new_hash(p, $3, &@3), &@$) : $1;
			$$ = arg_blk_pass($$, $4);
		    /*% %*/
		    /*% ripper: args_add_block!(args_add!($1, bare_assoc_hash!($3)), $4) %*/
		    }
		| block_arg
		    /*% ripper[brace]: args_add_block!(args_new!, $1) %*/
		;

command_args	:   {
			/* If call_args starts with a open paren '(' or '[',
			 * look-ahead reading of the letters calls CMDARG_PUSH(0),
			 * but the push must be done after CMDARG_PUSH(1).
			 * So this code makes them consistent by first cancelling
			 * the premature CMDARG_PUSH(0), doing CMDARG_PUSH(1),
			 * and finally redoing CMDARG_PUSH(0).
			 */
			int lookahead = 0;
			switch (yychar) {
			  case '(': case tLPAREN: case tLPAREN_ARG: case '[': case tLBRACK:
			    lookahead = 1;
			}
			if (lookahead) CMDARG_POP();
			CMDARG_PUSH(1);
			if (lookahead) CMDARG_PUSH(0);
		    }
		  call_args
		    {
			/* call_args can be followed by tLBRACE_ARG (that does CMDARG_PUSH(0) in the lexer)
			 * but the push must be done after CMDARG_POP() in the parser.
			 * So this code does CMDARG_POP() to pop 0 pushed by tLBRACE_ARG,
			 * CMDARG_POP() to pop 1 pushed by command_args,
			 * and CMDARG_PUSH(0) to restore back the flag set by tLBRACE_ARG.
			 */
			int lookahead = 0;
			switch (yychar) {
			  case tLBRACE_ARG:
			    lookahead = 1;
			}
			if (lookahead) CMDARG_POP();
			CMDARG_POP();
			if (lookahead) CMDARG_PUSH(0);
			$$ = $2;
		    }
		;

block_arg	: tAMPER arg_value
		    {
		    /*%%%*/
			$$ = NEW_BLOCK_PASS($2, &@$);
		    /*% %*/
		    /*% ripper: $2 %*/
		    }
		;

opt_block_arg	: ',' block_arg
		    {
			$$ = $2;
		    }
		| none
		    {
			$$ = 0;
		    }
		;

args		: arg_value
		    {
		    /*%%%*/
			$$ = NEW_LIST($1, &@$);
		    /*% %*/
		    /*% ripper: args_add!(args_new!, $1) %*/
		    }
		| tSTAR arg_value
		    {
		    /*%%%*/
			$$ = NEW_SPLAT($2, &@$);
		    /*% %*/
		    /*% ripper: args_add_star!(args_new!, $2) %*/
		    }
		| args ',' arg_value
		    {
		    /*%%%*/
			$$ = last_arg_append(p, $1, $3, &@$);
		    /*% %*/
		    /*% ripper: args_add!($1, $3) %*/
		    }
		| args ',' tSTAR arg_value
		    {
		    /*%%%*/
			$$ = rest_arg_append(p, $1, $4, &@$);
		    /*% %*/
		    /*% ripper: args_add_star!($1, $4) %*/
		    }
		;

mrhs_arg	: mrhs
		| arg_value
		;

mrhs		: args ',' arg_value
		    {
		    /*%%%*/
			$$ = last_arg_append(p, $1, $3, &@$);
		    /*% %*/
		    /*% ripper: mrhs_add!(mrhs_new_from_args!($1), $3) %*/
		    }
		| args ',' tSTAR arg_value
		    {
		    /*%%%*/
			$$ = rest_arg_append(p, $1, $4, &@$);
		    /*% %*/
		    /*% ripper: mrhs_add_star!(mrhs_new_from_args!($1), $4) %*/
		    }
		| tSTAR arg_value
		    {
		    /*%%%*/
			$$ = NEW_SPLAT($2, &@$);
		    /*% %*/
		    /*% ripper: mrhs_add_star!(mrhs_new!, $2) %*/
		    }
		;

primary		: literal
		| strings
		| xstring
		| regexp
		| words
		| qwords
		| symbols
		| qsymbols
		| var_ref
		| backref
		| tFID
		    {
		    /*%%%*/
			$$ = NEW_FCALL($1, 0, &@$);
		    /*% %*/
		    /*% ripper: method_add_arg!(fcall!($1), args_new!) %*/
		    }
		| k_begin
		    {
			CMDARG_PUSH(0);
		    }
		  bodystmt
		  k_end
		    {
			CMDARG_POP();
		    /*%%%*/
			set_line_body($3, @1.end_pos.lineno);
			$$ = NEW_BEGIN($3, &@$);
			nd_set_line($$, @1.end_pos.lineno);
		    /*% %*/
		    /*% ripper: begin!($3) %*/
		    }
		| tLPAREN_ARG {SET_LEX_STATE(EXPR_ENDARG);} rparen
		    {
		    /*%%%*/
			$$ = NEW_BEGIN(0, &@$);
		    /*% %*/
		    /*% ripper: paren!(0) %*/
		    }
		| tLPAREN_ARG stmt {SET_LEX_STATE(EXPR_ENDARG);} rparen
		    {
		    /*%%%*/
			$$ = $2;
		    /*% %*/
		    /*% ripper: paren!($2) %*/
		    }
		| tLPAREN compstmt ')'
		    {
		    /*%%%*/
			$$ = $2;
		    /*% %*/
		    /*% ripper: paren!($2) %*/
		    }
		| primary_value tCOLON2 tCONSTANT
		    {
		    /*%%%*/
			$$ = NEW_COLON2($1, $3, &@$);
		    /*% %*/
		    /*% ripper: const_path_ref!($1, $3) %*/
		    }
		| tCOLON3 tCONSTANT
		    {
		    /*%%%*/
			$$ = NEW_COLON3($2, &@$);
		    /*% %*/
		    /*% ripper: top_const_ref!($2) %*/
		    }
		| tLBRACK aref_args ']'
		    {
		    /*%%%*/
			$$ = make_array($2, &@$);
		    /*% %*/
		    /*% ripper: array!(escape_Qundef($2)) %*/
		    }
		| tLBRACE assoc_list '}'
		    {
		    /*%%%*/
			$$ = new_hash(p, $2, &@$);
			$$->nd_alen = TRUE;
		    /*% %*/
		    /*% ripper: hash!(escape_Qundef($2)) %*/
		    }
		| k_return
		    {
		    /*%%%*/
			$$ = NEW_RETURN(0, &@$);
		    /*% %*/
		    /*% ripper: return0! %*/
		    }
		| keyword_yield '(' call_args rparen
		    {
		    /*%%%*/
			$$ = new_yield(p, $3, &@$);
		    /*% %*/
		    /*% ripper: yield!(paren!($3)) %*/
		    }
		| keyword_yield '(' rparen
		    {
		    /*%%%*/
			$$ = NEW_YIELD(0, &@$);
		    /*% %*/
		    /*% ripper: yield!(paren!(args_new!)) %*/
		    }
		| keyword_yield
		    {
		    /*%%%*/
			$$ = NEW_YIELD(0, &@$);
		    /*% %*/
		    /*% ripper: yield0! %*/
		    }
		| keyword_defined opt_nl '(' {p->in_defined = 1;} expr rparen
		    {
			p->in_defined = 0;
			$$ = new_defined(p, $5, &@$);
		    }
		| keyword_not '(' expr rparen
		    {
			$$ = call_uni_op(p, method_cond(p, $3, &@3), METHOD_NOT, &@1, &@$);
		    }
		| keyword_not '(' rparen
		    {
			$$ = call_uni_op(p, method_cond(p, new_nil(&@2), &@2), METHOD_NOT, &@1, &@$);
		    }
		| fcall brace_block
		    {
		    /*%%%*/
			$$ = method_add_block(p, $1, $2, &@$);
		    /*% %*/
		    /*% ripper: method_add_block!(method_add_arg!(fcall!($1), args_new!), $2) %*/
		    }
		| method_call
		| method_call brace_block
		    {
		    /*%%%*/
			block_dup_check(p, $1->nd_args, $2);
			$$ = method_add_block(p, $1, $2, &@$);
		    /*% %*/
		    /*% ripper: method_add_block!($1, $2) %*/
		    }
		| tLAMBDA
		    {
			token_info_push(p, "->", &@1);
		    }
		  lambda
		    {
			$$ = $3;
                    /*%%%*/
                        nd_set_first_loc($$, @1.beg_pos);
                    /*% %*/
		    }
		| k_if expr_value then
		  compstmt
		  if_tail
		  k_end
		    {
		    /*%%%*/
			$$ = new_if(p, $2, $4, $5, &@$);
			fixpos($$, $2);
		    /*% %*/
		    /*% ripper: if!($2, $4, escape_Qundef($5)) %*/
		    }
		| k_unless expr_value then
		  compstmt
		  opt_else
		  k_end
		    {
		    /*%%%*/
			$$ = new_unless(p, $2, $4, $5, &@$);
			fixpos($$, $2);
		    /*% %*/
		    /*% ripper: unless!($2, $4, escape_Qundef($5)) %*/
		    }
		| k_while expr_value_do
		  compstmt
		  k_end
		    {
		    /*%%%*/
			$$ = NEW_WHILE(cond(p, $2, &@2), $3, 1, &@$);
			fixpos($$, $2);
		    /*% %*/
		    /*% ripper: while!($2, $3) %*/
		    }
		| k_until expr_value_do
		  compstmt
		  k_end
		    {
		    /*%%%*/
			$$ = NEW_UNTIL(cond(p, $2, &@2), $3, 1, &@$);
			fixpos($$, $2);
		    /*% %*/
		    /*% ripper: until!($2, $3) %*/
		    }
		| k_case expr_value opt_terms
		  case_body
		  k_end
		    {
		    /*%%%*/
			$$ = NEW_CASE($2, $4, &@$);
			fixpos($$, $2);
		    /*% %*/
		    /*% ripper: case!($2, $4) %*/
		    }
		| k_case opt_terms case_body k_end
		    {
		    /*%%%*/
			$$ = NEW_CASE2($3, &@$);
		    /*% %*/
		    /*% ripper: case!(Qnil, $3) %*/
		    }
		| k_for for_var keyword_in expr_value_do
		  compstmt
		  k_end
		    {
		    /*%%%*/
			/*
			 *  for a, b, c in e
			 *  #=>
			 *  e.each{|*x| a, b, c = x}
			 *
			 *  for a in e
			 *  #=>
			 *  e.each{|x| a, = x}
			 */
			ID id = internal_id(p);
			NODE *m = NEW_ARGS_AUX(0, 0, &NULL_LOC);
			NODE *args, *scope, *internal_var = NEW_DVAR(id, &@2);
			rb_imemo_tmpbuf_t *tmpbuf = new_tmpbuf();
			ID *tbl = ALLOC_N(ID, 2);
			tbl[0] = 1 /* length of local var table */; tbl[1] = id /* internal id */;
			tmpbuf->ptr = (VALUE *)tbl;

			switch (nd_type($2)) {
			  case NODE_LASGN:
			  case NODE_DASGN:
			  case NODE_DASGN_CURR: /* e.each {|internal_var| a = internal_var; ... } */
			    $2->nd_value = internal_var;
			    id = 0;
			    m->nd_plen = 1;
			    m->nd_next = $2;
			    break;
			  case NODE_MASGN: /* e.each {|*internal_var| a, b, c = (internal_var.length == 1 && Array === (tmp = internal_var[0]) ? tmp : internal_var); ... } */
			    m->nd_next = node_assign(p, $2, NEW_FOR_MASGN(internal_var, &@2), &@2);
			    break;
			  default: /* e.each {|*internal_var| @a, B, c[1], d.attr = internal_val; ... } */
			    m->nd_next = node_assign(p, NEW_MASGN(NEW_LIST($2, &@2), 0, &@2), internal_var, &@2);
			}
			/* {|*internal_id| <m> = internal_id; ... } */
			args = new_args(p, m, 0, id, 0, new_args_tail(p, 0, 0, 0, &@2), &@2);
			scope = NEW_NODE(NODE_SCOPE, tbl, $5, args, &@$);
			$$ = NEW_FOR($4, scope, &@$);
			fixpos($$, $2);
		    /*% %*/
		    /*% ripper: for!($2, $4, $5) %*/
		    }
		| k_class cpath superclass
		    {
			if (p->in_def) {
			    YYLTYPE loc = code_loc_gen(&@1, &@2);
			    yyerror1(&loc, "class definition in method body");
			}
			$<num>1 = p->in_class;
			p->in_class = 1;
			local_push(p, 0);
		    }
		  bodystmt
		  k_end
		    {
		    /*%%%*/
			$$ = NEW_CLASS($2, $5, $3, &@$);
			nd_set_line($$->nd_body, @6.end_pos.lineno);
			set_line_body($5, @3.end_pos.lineno);
			nd_set_line($$, @3.end_pos.lineno);
		    /*% %*/
		    /*% ripper: class!($2, $3, $5) %*/
			local_pop(p);
			p->in_class = $<num>1 & 1;
		    }
		| k_class tLSHFT expr
		    {
			$<num>$ = (p->in_class << 1) | p->in_def;
			p->in_def = 0;
			p->in_class = 0;
			local_push(p, 0);
		    }
		  term
		  bodystmt
		  k_end
		    {
		    /*%%%*/
			$$ = NEW_SCLASS($3, $6, &@$);
			nd_set_line($$->nd_body, @7.end_pos.lineno);
			set_line_body($6, nd_line($3));
			fixpos($$, $3);
		    /*% %*/
		    /*% ripper: sclass!($3, $6) %*/
			local_pop(p);
			p->in_def = $<num>4 & 1;
			p->in_class = ($<num>4 >> 1) & 1;
		    }
		| k_module cpath
		    {
			if (p->in_def) {
			    YYLTYPE loc = code_loc_gen(&@1, &@2);
			    yyerror1(&loc, "module definition in method body");
			}
			$<num>1 = p->in_class;
			p->in_class = 1;
			local_push(p, 0);
		    }
		  bodystmt
		  k_end
		    {
		    /*%%%*/
			$$ = NEW_MODULE($2, $4, &@$);
			nd_set_line($$->nd_body, @5.end_pos.lineno);
			set_line_body($4, @2.end_pos.lineno);
			nd_set_line($$, @2.end_pos.lineno);
		    /*% %*/
		    /*% ripper: module!($2, $4) %*/
			local_pop(p);
			p->in_class = $<num>1 & 1;
		    }
		| k_def fname
		    {
			local_push(p, 0);
			$<id>$ = p->cur_arg;
			p->cur_arg = 0;
		    }
		    {
			$<num>$ = p->in_def;
			p->in_def = 1;
		    }
		  f_arglist
		  bodystmt
		  k_end
		    {
		    /*%%%*/
			NODE *body = remove_begin($6);
			reduce_nodes(p, &body);
			$$ = NEW_DEFN($2, $5, body, &@$);
			nd_set_line($$->nd_defn, @7.end_pos.lineno);
			set_line_body(body, @1.beg_pos.lineno);
		    /*% %*/
		    /*% ripper: def!($2, $5, $6) %*/
			local_pop(p);
			p->in_def = $<num>4 & 1;
			p->cur_arg = $<id>3;
		    }
		| k_def singleton dot_or_colon {SET_LEX_STATE(EXPR_FNAME);} fname
		    {
			$<num>4 = p->in_def;
			p->in_def = 1;
			SET_LEX_STATE(EXPR_ENDFN|EXPR_LABEL); /* force for args */
			local_push(p, 0);
			$<id>$ = p->cur_arg;
			p->cur_arg = 0;
		    }
		  f_arglist
		  bodystmt
		  k_end
		    {
		    /*%%%*/
			NODE *body = remove_begin($8);
			reduce_nodes(p, &body);
			$$ = NEW_DEFS($2, $5, $7, body, &@$);
			nd_set_line($$->nd_defn, @9.end_pos.lineno);
			set_line_body(body, @1.beg_pos.lineno);
		    /*% %*/
		    /*% ripper: defs!($2, $3, $5, $7, $8) %*/
			local_pop(p);
			p->in_def = $<num>4 & 1;
			p->cur_arg = $<id>6;
		    }
		| keyword_break
		    {
		    /*%%%*/
			$$ = NEW_BREAK(0, &@$);
		    /*% %*/
		    /*% ripper: break!(args_new!) %*/
		    }
		| keyword_next
		    {
		    /*%%%*/
			$$ = NEW_NEXT(0, &@$);
		    /*% %*/
		    /*% ripper: next!(args_new!) %*/
		    }
		| keyword_redo
		    {
		    /*%%%*/
			$$ = NEW_REDO(&@$);
		    /*% %*/
		    /*% ripper: redo! %*/
		    }
		| keyword_retry
		    {
		    /*%%%*/
			$$ = NEW_RETRY(&@$);
		    /*% %*/
		    /*% ripper: retry! %*/
		    }
		;

primary_value	: primary
		    {
			value_expr($1);
			$$ = $1;
		    }
		;

k_begin		: keyword_begin
		    {
			token_info_push(p, "begin", &@$);
		    }
		;

k_if		: keyword_if
		    {
			token_info_push(p, "if", &@$);
		    }
		;

k_unless	: keyword_unless
		    {
			token_info_push(p, "unless", &@$);
		    }
		;

k_while		: keyword_while
		    {
			token_info_push(p, "while", &@$);
		    }
		;

k_until		: keyword_until
		    {
			token_info_push(p, "until", &@$);
		    }
		;

k_case		: keyword_case
		    {
			token_info_push(p, "case", &@$);
		    }
		;

k_for		: keyword_for
		    {
			token_info_push(p, "for", &@$);
		    }
		;

k_class		: keyword_class
		    {
			token_info_push(p, "class", &@$);
		    }
		;

k_module	: keyword_module
		    {
			token_info_push(p, "module", &@$);
		    }
		;

k_def		: keyword_def
		    {
			token_info_push(p, "def", &@$);
		    }
		;

k_do		: keyword_do
		    {
			token_info_push(p, "do", &@$);
		    }
		;

k_do_block	: keyword_do_block
		    {
			token_info_push(p, "do", &@$);
		    }
		;

k_rescue	: keyword_rescue
		    {
			token_info_warn(p, "rescue", p->token_info, 1, &@$);
		    }
		;

k_ensure	: keyword_ensure
		    {
			token_info_warn(p, "ensure", p->token_info, 1, &@$);
		    }
		;

k_when		: keyword_when
		    {
			token_info_warn(p, "when", p->token_info, 0, &@$);
		    }
		;

k_else		: keyword_else
		    {
			token_info *ptinfo_beg = p->token_info;
			int same = ptinfo_beg && strcmp(ptinfo_beg->token, "case") != 0;
			token_info_warn(p, "else", p->token_info, same, &@$);
		    }
		;

k_elsif 	: keyword_elsif
		    {
			token_info_warn(p, "elsif", p->token_info, 1, &@$);
		    }
		;

k_end		: keyword_end
		    {
			token_info_pop(p, "end", &@$);
		    }
		;

k_return	: keyword_return
		    {
			if (p->in_class && !p->in_def && !dyna_in_block(p))
			    yyerror1(&@1, "Invalid return in class/module body");
		    }
		;

then		: term
		| keyword_then
		| term keyword_then
		;

do		: term
		| keyword_do_cond
		;

if_tail		: opt_else
		| k_elsif expr_value then
		  compstmt
		  if_tail
		    {
		    /*%%%*/
			$$ = new_if(p, $2, $4, $5, &@$);
			fixpos($$, $2);
		    /*% %*/
		    /*% ripper: elsif!($2, $4, escape_Qundef($5)) %*/
		    }
		;

opt_else	: none
		| k_else compstmt
		    {
		    /*%%%*/
			$$ = $2;
		    /*% %*/
		    /*% ripper: else!($2) %*/
		    }
		;

for_var		: lhs
		| mlhs
		;

f_marg		: f_norm_arg
		    {
		    /*%%%*/
			$$ = assignable(p, $1, 0, &@$);
		    /*% %*/
		    /*% ripper: assignable(p, $1) %*/
		    }
		| tLPAREN f_margs rparen
		    {
		    /*%%%*/
			$$ = $2;
		    /*% %*/
		    /*% ripper: mlhs_paren!($2) %*/
		    }
		;

f_marg_list	: f_marg
		    {
		    /*%%%*/
			$$ = NEW_LIST($1, &@$);
		    /*% %*/
		    /*% ripper: mlhs_add!(mlhs_new!, $1) %*/
		    }
		| f_marg_list ',' f_marg
		    {
		    /*%%%*/
			$$ = list_append(p, $1, $3);
		    /*% %*/
		    /*% ripper: mlhs_add!($1, $3) %*/
		    }
		;

f_margs		: f_marg_list
		    {
		    /*%%%*/
			$$ = NEW_MASGN($1, 0, &@$);
		    /*% %*/
		    /*% ripper: $1 %*/
		    }
		| f_marg_list ',' tSTAR f_norm_arg
		    {
		    /*%%%*/
			$$ = NEW_MASGN($1, assignable(p, $4, 0, &@$), &@$);
		    /*% %*/
		    /*% ripper: mlhs_add_star!($1, assignable(p, $4)) %*/
		    }
		| f_marg_list ',' tSTAR f_norm_arg ',' f_marg_list
		    {
		    /*%%%*/
			$$ = NEW_MASGN($1, NEW_POSTARG(assignable(p, $4, 0, &@$), $6, &@$), &@$);
		    /*% %*/
		    /*% ripper: mlhs_add_post!(mlhs_add_star!($1, assignable(p, $4)), $6) %*/
		    }
		| f_marg_list ',' tSTAR
		    {
		    /*%%%*/
			$$ = NEW_MASGN($1, NODE_SPECIAL_NO_NAME_REST, &@$);
		    /*% %*/
		    /*% ripper: mlhs_add_star!($1, Qnil) %*/
		    }
		| f_marg_list ',' tSTAR ',' f_marg_list
		    {
		    /*%%%*/
			$$ = NEW_MASGN($1, NEW_POSTARG(NODE_SPECIAL_NO_NAME_REST, $5, &@$), &@$);
		    /*% %*/
		    /*% ripper: mlhs_add_post!(mlhs_add_star!($1, Qnil), $5) %*/
		    }
		| tSTAR f_norm_arg
		    {
		    /*%%%*/
			$$ = NEW_MASGN(0, assignable(p, $2, 0, &@$), &@$);
		    /*% %*/
		    /*% ripper: mlhs_add_star!(mlhs_new!, assignable(p, $2)) %*/
		    }
		| tSTAR f_norm_arg ',' f_marg_list
		    {
		    /*%%%*/
			$$ = NEW_MASGN(0, NEW_POSTARG(assignable(p, $2, 0, &@$), $4, &@$), &@$);
		    /*% %*/
		    /*% ripper: mlhs_add_post!(mlhs_add_star!(mlhs_new!, assignable(p, $2)), $4) %*/
		    }
		| tSTAR
		    {
		    /*%%%*/
			$$ = NEW_MASGN(0, NODE_SPECIAL_NO_NAME_REST, &@$);
		    /*% %*/
		    /*% ripper: mlhs_add_star!(mlhs_new!, Qnil) %*/
		    }
		| tSTAR ',' f_marg_list
		    {
		    /*%%%*/
			$$ = NEW_MASGN(0, NEW_POSTARG(NODE_SPECIAL_NO_NAME_REST, $3, &@$), &@$);
		    /*% %*/
		    /*% ripper: mlhs_add_post!(mlhs_add_star!(mlhs_new!, Qnil), $3) %*/
		    }
		;


block_args_tail	: f_block_kwarg ',' f_kwrest opt_f_block_arg
		    {
			$$ = new_args_tail(p, $1, $3, $4, &@3);
		    }
		| f_block_kwarg opt_f_block_arg
		    {
			$$ = new_args_tail(p, $1, Qnone, $2, &@1);
		    }
		| f_kwrest opt_f_block_arg
		    {
			$$ = new_args_tail(p, Qnone, $1, $2, &@1);
		    }
		| f_block_arg
		    {
			$$ = new_args_tail(p, Qnone, Qnone, $1, &@1);
		    }
		;

opt_block_args_tail : ',' block_args_tail
		    {
			$$ = $2;
		    }
		| /* none */
		    {
			$$ = new_args_tail(p, Qnone, Qnone, Qnone, &@0);
		    }
		;

block_param	: f_arg ',' f_block_optarg ',' f_rest_arg opt_block_args_tail
		    {
			$$ = new_args(p, $1, $3, $5, Qnone, $6, &@$);
		    }
		| f_arg ',' f_block_optarg ',' f_rest_arg ',' f_arg opt_block_args_tail
		    {
			$$ = new_args(p, $1, $3, $5, $7, $8, &@$);
		    }
		| f_arg ',' f_block_optarg opt_block_args_tail
		    {
			$$ = new_args(p, $1, $3, Qnone, Qnone, $4, &@$);
		    }
		| f_arg ',' f_block_optarg ',' f_arg opt_block_args_tail
		    {
			$$ = new_args(p, $1, $3, Qnone, $5, $6, &@$);
		    }
                | f_arg ',' f_rest_arg opt_block_args_tail
		    {
			$$ = new_args(p, $1, Qnone, $3, Qnone, $4, &@$);
		    }
		| f_arg ','
		    {
		    /*%%%*/
			/* magic number for rest_id in iseq_set_arguments() */
			const ID excessed_comma = 1;
			$$ = new_args(p, $1, Qnone, excessed_comma, Qnone, new_args_tail(p, Qnone, Qnone, Qnone, &@1), &@$);
		    /*% %*/
		    /*% ripper: new_args(p, $1, Qnone, excessed_comma!, Qnone, new_args_tail(p, Qnone, Qnone, Qnone, NULL), NULL) %*/
		    }
		| f_arg ',' f_rest_arg ',' f_arg opt_block_args_tail
		    {
			$$ = new_args(p, $1, Qnone, $3, $5, $6, &@$);
		    }
		| f_arg opt_block_args_tail
		    {
			$$ = new_args(p, $1, Qnone, Qnone, Qnone, $2, &@$);
		    }
		| f_block_optarg ',' f_rest_arg opt_block_args_tail
		    {
			$$ = new_args(p, Qnone, $1, $3, Qnone, $4, &@$);
		    }
		| f_block_optarg ',' f_rest_arg ',' f_arg opt_block_args_tail
		    {
			$$ = new_args(p, Qnone, $1, $3, $5, $6, &@$);
		    }
		| f_block_optarg opt_block_args_tail
		    {
			$$ = new_args(p, Qnone, $1, Qnone, Qnone, $2, &@$);
		    }
		| f_block_optarg ',' f_arg opt_block_args_tail
		    {
			$$ = new_args(p, Qnone, $1, Qnone, $3, $4, &@$);
		    }
		| f_rest_arg opt_block_args_tail
		    {
			$$ = new_args(p, Qnone, Qnone, $1, Qnone, $2, &@$);
		    }
		| f_rest_arg ',' f_arg opt_block_args_tail
		    {
			$$ = new_args(p, Qnone, Qnone, $1, $3, $4, &@$);
		    }
		| block_args_tail
		    {
			$$ = new_args(p, Qnone, Qnone, Qnone, Qnone, $1, &@$);
		    }
		;

opt_block_param	: none
		| block_param_def
		    {
			p->command_start = TRUE;
		    }
		;

block_param_def	: '|' opt_bv_decl '|'
		    {
			p->cur_arg = 0;
		    /*%%%*/
			$$ = 0;
		    /*% %*/
		    /*% ripper: block_var!(params_new(Qnil,Qnil,Qnil,Qnil,Qnil,Qnil,Qnil), escape_Qundef($2)) %*/
		    }
		| tOROP
		    {
		    /*%%%*/
			$$ = 0;
		    /*% %*/
		    /*% ripper: block_var!(params_new(Qnil,Qnil,Qnil,Qnil,Qnil,Qnil,Qnil), Qnil) %*/
		    }
		| '|' block_param opt_bv_decl '|'
		    {
			p->cur_arg = 0;
		    /*%%%*/
			$$ = $2;
		    /*% %*/
		    /*% ripper: block_var!(escape_Qundef($2), escape_Qundef($3)) %*/
		    }
		;


opt_bv_decl	: opt_nl
		    {
		      $$ = 0;
		    }
		| opt_nl ';' bv_decls opt_nl
		    {
		    /*%%%*/
			$$ = 0;
		    /*% %*/
		    /*% ripper: $3 %*/
		    }
		;

bv_decls	: bvar
		    /*% ripper[brace]: rb_ary_new3(1, get_value($1)) %*/
		| bv_decls ',' bvar
		    /*% ripper[brace]: rb_ary_push($1, get_value($3)) %*/
		;

bvar		: tIDENTIFIER
		    {
			new_bv(p, get_id($1));
		    /*% ripper: get_value($1) %*/
		    }
		| f_bad_arg
		    {
			$$ = 0;
		    }
		;

lambda		:   {
			$<vars>$ = dyna_push(p);
		    }
		    {
			$<num>$ = p->lex.lpar_beg;
			p->lex.lpar_beg = p->lex.paren_nest;
		    }
		  f_larglist
		    {
			CMDARG_PUSH(0);
		    }
		  lambda_body
		    {
			p->lex.lpar_beg = $<num>2;
			CMDARG_POP();
		    /*%%%*/
                        {
                            YYLTYPE loc = code_loc_gen(&@3, &@5);
                            $$ = NEW_LAMBDA($3, $5, &loc);
                            nd_set_line($$->nd_body, @5.end_pos.lineno);
                            nd_set_line($$, @3.end_pos.lineno);
                        }
		    /*% %*/
		    /*% ripper: lambda!($3, $5) %*/
			dyna_pop(p, $<vars>1);
		    }
		;

f_larglist	: '(' f_args opt_bv_decl ')'
		    {
		    /*%%%*/
			$$ = $2;
		    /*% %*/
		    /*% ripper: paren!($2) %*/
		    }
		| f_args
		    {
			$$ = $1;
		    }
		;

lambda_body	: tLAMBEG compstmt '}'
		    {
			token_info_pop(p, "}", &@3);
			$$ = $2;
		    }
		| keyword_do_LAMBDA bodystmt k_end
		    {
			$$ = $2;
		    }
		;

do_block	: k_do_block do_body k_end
		    {
			$$ = $2;
		    /*%%%*/
			$$->nd_body->nd_loc = code_loc_gen(&@1, &@3);
			nd_set_line($$, @1.end_pos.lineno);
		    /*% %*/
		    }
		;

block_call	: command do_block
		    {
		    /*%%%*/
			if (nd_type($1) == NODE_YIELD) {
			    compile_error(p, "block given to yield");
			}
			else {
			    block_dup_check(p, $1->nd_args, $2);
			}
			$$ = method_add_block(p, $1, $2, &@$);
			fixpos($$, $1);
		    /*% %*/
		    /*% ripper: method_add_block!($1, $2) %*/
		    }
		| block_call call_op2 operation2 opt_paren_args
		    {
		    /*%%%*/
			$$ = new_qcall(p, $2, $1, $3, $4, &@3, &@$);
		    /*% %*/
		    /*% ripper: opt_event(:method_add_arg!, call!($1, $2, $3), $4) %*/
		    }
		| block_call call_op2 operation2 opt_paren_args brace_block
		    {
		    /*%%%*/
			$$ = new_command_qcall(p, $2, $1, $3, $4, $5, &@3, &@$);
		    /*% %*/
		    /*% ripper: opt_event(:method_add_block!, command_call!($1, $2, $3, $4), $5) %*/
		    }
		| block_call call_op2 operation2 command_args do_block
		    {
		    /*%%%*/
			$$ = new_command_qcall(p, $2, $1, $3, $4, $5, &@3, &@$);
		    /*% %*/
		    /*% ripper: method_add_block!(command_call!($1, $2, $3, $4), $5) %*/
		    }
		;

method_call	: fcall paren_args
		    {
		    /*%%%*/
			$$ = $1;
			$$->nd_args = $2;
			nd_set_last_loc($1, @2.end_pos);
		    /*% %*/
		    /*% ripper: method_add_arg!(fcall!($1), $2) %*/
		    }
		| primary_value call_op operation2 opt_paren_args
		    {
		    /*%%%*/
			$$ = new_qcall(p, $2, $1, $3, $4, &@3, &@$);
			nd_set_line($$, @3.end_pos.lineno);
		    /*% %*/
		    /*% ripper: opt_event(:method_add_arg!, call!($1, $2, $3), $4) %*/
		    }
		| primary_value tCOLON2 operation2 paren_args
		    {
		    /*%%%*/
			$$ = new_qcall(p, ID2VAL(idCOLON2), $1, $3, $4, &@3, &@$);
			nd_set_line($$, @3.end_pos.lineno);
		    /*% %*/
		    /*% ripper: method_add_arg!(call!($1, ID2VAL(idCOLON2), $3), $4) %*/
		    }
		| primary_value tCOLON2 operation3
		    {
		    /*%%%*/
			$$ = new_qcall(p, ID2VAL(idCOLON2), $1, $3, Qnull, &@3, &@$);
		    /*% %*/
		    /*% ripper: call!($1, ID2VAL(idCOLON2), $3) %*/
		    }
		| primary_value call_op paren_args
		    {
		    /*%%%*/
			$$ = new_qcall(p, $2, $1, ID2VAL(idCall), $3, &@2, &@$);
			nd_set_line($$, @2.end_pos.lineno);
		    /*% %*/
		    /*% ripper: method_add_arg!(call!($1, $2, ID2VAL(idCall)), $3) %*/
		    }
		| primary_value tCOLON2 paren_args
		    {
		    /*%%%*/
			$$ = new_qcall(p, ID2VAL(idCOLON2), $1, ID2VAL(idCall), $3, &@2, &@$);
			nd_set_line($$, @2.end_pos.lineno);
		    /*% %*/
		    /*% ripper: method_add_arg!(call!($1, ID2VAL(idCOLON2), ID2VAL(idCall)), $3) %*/
		    }
		| keyword_super paren_args
		    {
		    /*%%%*/
			$$ = NEW_SUPER($2, &@$);
		    /*% %*/
		    /*% ripper: super!($2) %*/
		    }
		| keyword_super
		    {
		    /*%%%*/
			$$ = NEW_ZSUPER(&@$);
		    /*% %*/
		    /*% ripper: zsuper! %*/
		    }
		| primary_value '[' opt_call_args rbracket
		    {
		    /*%%%*/
			if ($1 && nd_type($1) == NODE_SELF)
			    $$ = NEW_FCALL(tAREF, $3, &@$);
			else
			    $$ = NEW_CALL($1, tAREF, $3, &@$);
			fixpos($$, $1);
		    /*% %*/
		    /*% ripper: aref!($1, escape_Qundef($3)) %*/
		    }
		;

brace_block	: '{' brace_body '}'
		    {
			$$ = $2;
		    /*%%%*/
			$$->nd_body->nd_loc = code_loc_gen(&@1, &@3);
			nd_set_line($$, @1.end_pos.lineno);
		    /*% %*/
		    }
		| k_do do_body k_end
		    {
			$$ = $2;
		    /*%%%*/
			$$->nd_body->nd_loc = code_loc_gen(&@1, &@3);
			nd_set_line($$, @1.end_pos.lineno);
		    /*% %*/
		    }
		;

brace_body	: {$<vars>$ = dyna_push(p);}
		  opt_block_param compstmt
		    {
		    /*%%%*/
			$$ = NEW_ITER($2, $3, &@$);
		    /*% %*/
		    /*% ripper: brace_block!(escape_Qundef($2), $3) %*/
			dyna_pop(p, $<vars>1);
		    }
		;

do_body 	: {$<vars>$ = dyna_push(p);}
		  {CMDARG_PUSH(0);}
		  opt_block_param bodystmt
		    {
		    /*%%%*/
			$$ = NEW_ITER($3, $4, &@$);
		    /*% %*/
		    /*% ripper: do_block!(escape_Qundef($3), $4) %*/
			CMDARG_POP();
			dyna_pop(p, $<vars>1);
		    }
		;

case_body	: k_when args then
		  compstmt
		  cases
		    {
		    /*%%%*/
			$$ = NEW_WHEN($2, $4, $5, &@$);
			fixpos($$, $2);
		    /*% %*/
		    /*% ripper: when!($2, $4, escape_Qundef($5)) %*/
		    }
		;

cases		: opt_else
		| case_body
		;

opt_rescue	: k_rescue exc_list exc_var then
		  compstmt
		  opt_rescue
		    {
		    /*%%%*/
			$$ = NEW_RESBODY($2,
					 $3 ? block_append(p, node_assign(p, $3, NEW_ERRINFO(&@3), &@3), $5) : $5,
					 $6, &@$);
			fixpos($$, $2?$2:$5);
		    /*% %*/
		    /*% ripper: rescue!(escape_Qundef($2), escape_Qundef($3), escape_Qundef($5), escape_Qundef($6)) %*/
		    }
		| none
		;

exc_list	: arg_value
		    {
		    /*%%%*/
			$$ = NEW_LIST($1, &@$);
		    /*% %*/
		    /*% ripper: rb_ary_new3(1, get_value($1)) %*/
		    }
		| mrhs
		    {
		    /*%%%*/
			if (!($$ = splat_array($1))) $$ = $1;
		    /*% %*/
		    /*% ripper: $1 %*/
		    }
		| none
		;

exc_var		: tASSOC lhs
		    {
			$$ = $2;
		    }
		| none
		;

opt_ensure	: k_ensure compstmt
		    {
		    /*%%%*/
			$$ = $2;
		    /*% %*/
		    /*% ripper: ensure!($2) %*/
		    }
		| none
		;

literal		: numeric
		| symbol
		    {
		    /*%%%*/
			$$ = NEW_LIT(ID2SYM($1), &@$);
		    /*% %*/
		    /*% ripper: symbol_literal!($1) %*/
		    }
		| dsym
		;

strings		: string
		    {
		    /*%%%*/
			NODE *node = $1;
			if (!node) {
			    node = NEW_STR(add_mark_object(p, STR_NEW0()), &@$);
			}
			else {
			    node = evstr2dstr(p, node);
			}
			$$ = node;
		    /*% %*/
		    /*% ripper: $1 %*/
		    }
		;

string		: tCHAR
		| string1
		| string string1
		    {
		    /*%%%*/
			$$ = literal_concat(p, $1, $2, &@$);
		    /*% %*/
		    /*% ripper: string_concat!($1, $2) %*/
		    }
		;

string1		: tSTRING_BEG string_contents tSTRING_END
		    {
		    /*%%%*/
			$$ = heredoc_dedent(p, $2);
			if ($$) nd_set_loc($$, &@$);
		    /*% %*/
		    /*% ripper: string_literal!(heredoc_dedent(p, $2)) %*/
		    }
		;

xstring		: tXSTRING_BEG xstring_contents tSTRING_END
		    {
		    /*%%%*/
			$$ = new_xstring(p, heredoc_dedent(p, $2), &@$);
		    /*% %*/
		    /*% ripper: xstring_literal!(heredoc_dedent(p, $2)) %*/
		    }
		;

regexp		: tREGEXP_BEG regexp_contents tREGEXP_END
		    {
			$$ = new_regexp(p, $2, $3, &@$);
		    }
		;

words		: tWORDS_BEG ' ' word_list tSTRING_END
		    {
		    /*%%%*/
			$$ = make_array($3, &@$);
		    /*% %*/
		    /*% ripper: array!($3) %*/
		    }
		;

word_list	: /* none */
		    {
		    /*%%%*/
			$$ = 0;
		    /*% %*/
		    /*% ripper: words_new! %*/
		    }
		| word_list word ' '
		    {
		    /*%%%*/
			$$ = list_append(p, $1, evstr2dstr(p, $2));
		    /*% %*/
		    /*% ripper: words_add!($1, $2) %*/
		    }
		;

word		: string_content
		    /*% ripper[brace]: word_add!(word_new!, $1) %*/
		| word string_content
		    {
		    /*%%%*/
			$$ = literal_concat(p, $1, $2, &@$);
		    /*% %*/
		    /*% ripper: word_add!($1, $2) %*/
		    }
		;

symbols 	: tSYMBOLS_BEG ' ' symbol_list tSTRING_END
		    {
		    /*%%%*/
			$$ = make_array($3, &@$);
		    /*% %*/
		    /*% ripper: array!($3) %*/
		    }
		;

symbol_list	: /* none */
		    {
		    /*%%%*/
			$$ = 0;
		    /*% %*/
		    /*% ripper: symbols_new! %*/
		    }
		| symbol_list word ' '
		    {
		    /*%%%*/
			$$ = symbol_append(p, $1, evstr2dstr(p, $2));
		    /*% %*/
		    /*% ripper: symbols_add!($1, $2) %*/
		    }
		;

qwords		: tQWORDS_BEG ' ' qword_list tSTRING_END
		    {
		    /*%%%*/
			$$ = make_array($3, &@$);
		    /*% %*/
		    /*% ripper: array!($3) %*/
		    }
		;

qsymbols	: tQSYMBOLS_BEG ' ' qsym_list tSTRING_END
		    {
		    /*%%%*/
			$$ = make_array($3, &@$);
		    /*% %*/
		    /*% ripper: array!($3) %*/
		    }
		;

qword_list	: /* none */
		    {
		    /*%%%*/
			$$ = 0;
		    /*% %*/
		    /*% ripper: qwords_new! %*/
		    }
		| qword_list tSTRING_CONTENT ' '
		    {
		    /*%%%*/
			$$ = list_append(p, $1, $2);
		    /*% %*/
		    /*% ripper: qwords_add!($1, $2) %*/
		    }
		;

qsym_list	: /* none */
		    {
		    /*%%%*/
			$$ = 0;
		    /*% %*/
		    /*% ripper: qsymbols_new! %*/
		    }
		| qsym_list tSTRING_CONTENT ' '
		    {
		    /*%%%*/
			$$ = symbol_append(p, $1, $2);
		    /*% %*/
		    /*% ripper: qsymbols_add!($1, $2) %*/
		    }
		;

string_contents : /* none */
		    {
		    /*%%%*/
			$$ = 0;
		    /*% %*/
		    /*% ripper: string_content! %*/
		    }
		| string_contents string_content
		    {
		    /*%%%*/
			$$ = literal_concat(p, $1, $2, &@$);
		    /*% %*/
		    /*% ripper: string_add!($1, $2) %*/
		    }
		;

xstring_contents: /* none */
		    {
		    /*%%%*/
			$$ = 0;
		    /*% %*/
		    /*% ripper: xstring_new! %*/
		    }
		| xstring_contents string_content
		    {
		    /*%%%*/
			$$ = literal_concat(p, $1, $2, &@$);
		    /*% %*/
		    /*% ripper: xstring_add!($1, $2) %*/
		    }
		;

regexp_contents: /* none */
		    {
		    /*%%%*/
			$$ = 0;
		    /*% %*/
		    /*% ripper: regexp_new! %*/
		    /*%%%*/
		    /*%
			$$ = ripper_new_yylval(p, 0, $$, 0);
		    %*/
		    }
		| regexp_contents string_content
		    {
		    /*%%%*/
			NODE *head = $1, *tail = $2;
			if (!head) {
			    $$ = tail;
			}
			else if (!tail) {
			    $$ = head;
			}
			else {
			    switch (nd_type(head)) {
			      case NODE_STR:
				nd_set_type(head, NODE_DSTR);
				break;
			      case NODE_DSTR:
				break;
			      default:
				head = list_append(p, NEW_DSTR(Qnil, &@$), head);
				break;
			    }
			    $$ = list_append(p, head, tail);
			}
		    /*%
			VALUE s1 = 1, s2 = 0, n1 = $1, n2 = $2;
			if (ripper_is_node_yylval(n1)) {
			    s1 = RNODE(n1)->nd_cval;
			    n1 = RNODE(n1)->nd_rval;
			}
			if (ripper_is_node_yylval(n2)) {
			    s2 = RNODE(n2)->nd_cval;
			    n2 = RNODE(n2)->nd_rval;
			}
			$$ = dispatch2(regexp_add, n1, n2);
			if (!s1 && s2) {
			    $$ = ripper_new_yylval(p, 0, $$, s2);
			}
		    %*/
		    }
		;

string_content	: tSTRING_CONTENT
		| tSTRING_DVAR
		    {
			/* need to backup p->lex.strterm so that a string literal `%&foo,#$&,bar&` can be parsed */
			$<strterm>$ = p->lex.strterm;
			p->lex.strterm = 0;
			SET_LEX_STATE(EXPR_BEG);
		    }
		  string_dvar
		    {
			p->lex.strterm = $<strterm>2;
		    /*%%%*/
			$$ = NEW_EVSTR($3, &@$);
			nd_set_line($$, @3.end_pos.lineno);
		    /*% %*/
		    /*% ripper: string_dvar!($3) %*/
		    }
		| tSTRING_DBEG
		    {
			CMDARG_PUSH(0);
			COND_PUSH(0);
		    }
		    {
			/* need to backup p->lex.strterm so that a string literal `%!foo,#{ !0 },bar!` can be parsed */
			$<strterm>$ = p->lex.strterm;
			p->lex.strterm = 0;
		    }
		    {
			$<num>$ = p->lex.state;
			SET_LEX_STATE(EXPR_BEG);
		    }
		    {
			$<num>$ = p->lex.brace_nest;
			p->lex.brace_nest = 0;
		    }
		    {
			$<num>$ = p->heredoc_indent;
			p->heredoc_indent = 0;
		    }
		  compstmt tSTRING_DEND
		    {
			COND_POP();
			CMDARG_POP();
			p->lex.strterm = $<strterm>3;
			SET_LEX_STATE($<num>4);
			p->lex.brace_nest = $<num>5;
			p->heredoc_indent = $<num>6;
			p->heredoc_line_indent = -1;
		    /*%%%*/
			if ($7) $7->flags &= ~NODE_FL_NEWLINE;
			$$ = new_evstr(p, $7, &@$);
		    /*% %*/
		    /*% ripper: string_embexpr!($7) %*/
		    }
		;

string_dvar	: tGVAR
		    {
		    /*%%%*/
			$$ = NEW_GVAR($1, &@$);
		    /*% %*/
		    /*% ripper: var_ref!($1) %*/
		    }
		| tIVAR
		    {
		    /*%%%*/
			$$ = NEW_IVAR($1, &@$);
		    /*% %*/
		    /*% ripper: var_ref!($1) %*/
		    }
		| tCVAR
		    {
		    /*%%%*/
			$$ = NEW_CVAR($1, &@$);
		    /*% %*/
		    /*% ripper: var_ref!($1) %*/
		    }
		| backref
		;

symbol		: tSYMBEG sym
		    {
			SET_LEX_STATE(EXPR_END);
		    /*%%%*/
			$$ = $2;
		    /*% %*/
		    /*% ripper: symbol!($2) %*/
		    }
		;

sym		: fname
		| tIVAR
		| tGVAR
		| tCVAR
		;

dsym		: tSYMBEG xstring_contents tSTRING_END
		    {
			SET_LEX_STATE(EXPR_END);
		    /*%%%*/
			$$ = dsym_node(p, $2, &@$);
		    /*% %*/
		    /*% ripper: dyna_symbol!($2) %*/
		    }
		;

numeric 	: simple_numeric
		| tUMINUS_NUM simple_numeric   %prec tLOWEST
		    {
		    /*%%%*/
			$$ = $2;
			add_mark_object(p, $$->nd_lit = negate_lit(p, $$->nd_lit));
		    /*% %*/
		    /*% ripper: unary!(ID2VAL(idUMinus), $2) %*/
		    }
		;

simple_numeric	: tINTEGER
		| tFLOAT
		| tRATIONAL
		| tIMAGINARY
		;

user_variable	: tIDENTIFIER
		| tIVAR
		| tGVAR
		| tCONSTANT
		| tCVAR
		;

keyword_variable: keyword_nil {$$ = KWD2EID(nil, $1);}
		| keyword_self {$$ = KWD2EID(self, $1);}
		| keyword_true {$$ = KWD2EID(true, $1);}
		| keyword_false {$$ = KWD2EID(false, $1);}
		| keyword__FILE__ {$$ = KWD2EID(_FILE__, $1);}
		| keyword__LINE__ {$$ = KWD2EID(_LINE__, $1);}
		| keyword__ENCODING__ {$$ = KWD2EID(_ENCODING__, $1);}
		;

var_ref		: user_variable
		    {
		    /*%%%*/
			if (!($$ = gettable(p, $1, &@$))) $$ = NEW_BEGIN(0, &@$);
		    /*%
			if (id_is_var(p, get_id($1))) {
			    $$ = dispatch1(var_ref, $1);
			}
			else {
			    $$ = dispatch1(vcall, $1);
			}
		    %*/
		    }
		| keyword_variable
		    {
		    /*%%%*/
			if (!($$ = gettable(p, $1, &@$))) $$ = NEW_BEGIN(0, &@$);
		    /*% %*/
		    /*% ripper: var_ref!($1) %*/
		    }
		;

var_lhs		: user_variable
		    {
		    /*%%%*/
			$$ = assignable(p, $1, 0, &@$);
		    /*% %*/
		    /*% ripper: assignable(p, var_field(p, $1)) %*/
		    }
		| keyword_variable
		    {
		    /*%%%*/
			$$ = assignable(p, $1, 0, &@$);
		    /*% %*/
		    /*% ripper: assignable(p, var_field(p, $1)) %*/
		    }
		;

backref		: tNTH_REF
		| tBACK_REF
		;

superclass	: '<'
		    {
			SET_LEX_STATE(EXPR_BEG);
			p->command_start = TRUE;
		    }
		  expr_value term
		    {
			$$ = $3;
		    }
		| /* none */
		    {
		    /*%%%*/
			$$ = 0;
		    /*% %*/
		    /*% ripper: Qnil %*/
		    }
		;

f_arglist	: '(' f_args rparen
		    {
		    /*%%%*/
			$$ = $2;
		    /*% %*/
		    /*% ripper: paren!($2) %*/
			SET_LEX_STATE(EXPR_BEG);
			p->command_start = TRUE;
		    }
		|   {
			$<num>$ = p->in_kwarg;
			p->in_kwarg = 1;
			SET_LEX_STATE(p->lex.state|EXPR_LABEL); /* force for args */
		    }
		    f_args term
		    {
			p->in_kwarg = !!$<num>1;
			$$ = $2;
			SET_LEX_STATE(EXPR_BEG);
			p->command_start = TRUE;
		    }
		;

args_tail	: f_kwarg ',' f_kwrest opt_f_block_arg
		    {
			$$ = new_args_tail(p, $1, $3, $4, &@3);
		    }
		| f_kwarg opt_f_block_arg
		    {
			$$ = new_args_tail(p, $1, Qnone, $2, &@1);
		    }
		| f_kwrest opt_f_block_arg
		    {
			$$ = new_args_tail(p, Qnone, $1, $2, &@1);
		    }
		| f_block_arg
		    {
			$$ = new_args_tail(p, Qnone, Qnone, $1, &@1);
		    }
		;

opt_args_tail	: ',' args_tail
		    {
			$$ = $2;
		    }
		| /* none */
		    {
			$$ = new_args_tail(p, Qnone, Qnone, Qnone, &@0);
		    }
		;

f_args		: f_arg ',' f_optarg ',' f_rest_arg opt_args_tail
		    {
			$$ = new_args(p, $1, $3, $5, Qnone, $6, &@$);
		    }
		| f_arg ',' f_optarg ',' f_rest_arg ',' f_arg opt_args_tail
		    {
			$$ = new_args(p, $1, $3, $5, $7, $8, &@$);
		    }
		| f_arg ',' f_optarg opt_args_tail
		    {
			$$ = new_args(p, $1, $3, Qnone, Qnone, $4, &@$);
		    }
		| f_arg ',' f_optarg ',' f_arg opt_args_tail
		    {
			$$ = new_args(p, $1, $3, Qnone, $5, $6, &@$);
		    }
		| f_arg ',' f_rest_arg opt_args_tail
		    {
			$$ = new_args(p, $1, Qnone, $3, Qnone, $4, &@$);
		    }
		| f_arg ',' f_rest_arg ',' f_arg opt_args_tail
		    {
			$$ = new_args(p, $1, Qnone, $3, $5, $6, &@$);
		    }
		| f_arg opt_args_tail
		    {
			$$ = new_args(p, $1, Qnone, Qnone, Qnone, $2, &@$);
		    }
		| f_optarg ',' f_rest_arg opt_args_tail
		    {
			$$ = new_args(p, Qnone, $1, $3, Qnone, $4, &@$);
		    }
		| f_optarg ',' f_rest_arg ',' f_arg opt_args_tail
		    {
			$$ = new_args(p, Qnone, $1, $3, $5, $6, &@$);
		    }
		| f_optarg opt_args_tail
		    {
			$$ = new_args(p, Qnone, $1, Qnone, Qnone, $2, &@$);
		    }
		| f_optarg ',' f_arg opt_args_tail
		    {
			$$ = new_args(p, Qnone, $1, Qnone, $3, $4, &@$);
		    }
		| f_rest_arg opt_args_tail
		    {
			$$ = new_args(p, Qnone, Qnone, $1, Qnone, $2, &@$);
		    }
		| f_rest_arg ',' f_arg opt_args_tail
		    {
			$$ = new_args(p, Qnone, Qnone, $1, $3, $4, &@$);
		    }
		| args_tail
		    {
			$$ = new_args(p, Qnone, Qnone, Qnone, Qnone, $1, &@$);
		    }
		| /* none */
		    {
			$$ = new_args_tail(p, Qnone, Qnone, Qnone, &@0);
			$$ = new_args(p, Qnone, Qnone, Qnone, Qnone, $$, &@0);
		    }
		;

f_bad_arg	: tCONSTANT
		    {
		    /*%%%*/
			yyerror1(&@1, "formal argument cannot be a constant");
			$$ = 0;
		    /*% %*/
		    /*% ripper[error]: param_error!($1) %*/
		    }
		| tIVAR
		    {
		    /*%%%*/
			yyerror1(&@1, "formal argument cannot be an instance variable");
			$$ = 0;
		    /*% %*/
		    /*% ripper[error]: param_error!($1) %*/
		    }
		| tGVAR
		    {
		    /*%%%*/
			yyerror1(&@1, "formal argument cannot be a global variable");
			$$ = 0;
		    /*% %*/
		    /*% ripper[error]: param_error!($1) %*/
		    }
		| tCVAR
		    {
		    /*%%%*/
			yyerror1(&@1, "formal argument cannot be a class variable");
			$$ = 0;
		    /*% %*/
		    /*% ripper[error]: param_error!($1) %*/
		    }
		;

f_norm_arg	: f_bad_arg
		| tIDENTIFIER
		    {
			formal_argument(p, get_id($1));
			$$ = $1;
		    }
		;

f_arg_asgn	: f_norm_arg
		    {
			ID id = get_id($1);
			arg_var(p, id);
			p->cur_arg = id;
			$$ = $1;
		    }
		;

f_arg_item	: f_arg_asgn
		    {
			p->cur_arg = 0;
		    /*%%%*/
			$$ = NEW_ARGS_AUX($1, 1, &NULL_LOC);
		    /*% %*/
		    /*% ripper: get_value($1) %*/
		    }
		| tLPAREN f_margs rparen
		    {
			ID tid = internal_id(p);
		    /*%%%*/
			YYLTYPE loc;
			loc.beg_pos = @2.beg_pos;
			loc.end_pos = @2.beg_pos;
		    /*% %*/
			arg_var(p, tid);
		    /*%%%*/
			if (dyna_in_block(p)) {
			    $2->nd_value = NEW_DVAR(tid, &loc);
			}
			else {
			    $2->nd_value = NEW_LVAR(tid, &loc);
			}
			$$ = NEW_ARGS_AUX(tid, 1, &NULL_LOC);
			$$->nd_next = $2;
		    /*% %*/
		    /*% ripper: mlhs_paren!($2) %*/
		    }
		;

f_arg		: f_arg_item
		    /*% ripper[brace]: rb_ary_new3(1, get_value($1)) %*/
		| f_arg ',' f_arg_item
		    {
		    /*%%%*/
			$$ = $1;
			$$->nd_plen++;
			$$->nd_next = block_append(p, $$->nd_next, $3->nd_next);
			rb_discard_node(p, $3);
		    /*% %*/
		    /*% ripper: rb_ary_push($1, get_value($3)) %*/
		    }
		;


f_label 	: tLABEL
		    {
			ID id = get_id($1);
			arg_var(p, formal_argument(p, id));
			p->cur_arg = id;
			$$ = $1;
		    }
		;

f_kw		: f_label arg_value
		    {
			p->cur_arg = 0;
		    /*%%%*/
			$$ = new_kw_arg(p, assignable(p, $1, $2, &@$), &@$);
		    /*% %*/
		    /*% ripper: rb_assoc_new(get_value(assignable(p, $1)), get_value($2)) %*/
		    }
		| f_label
		    {
			p->cur_arg = 0;
		    /*%%%*/
			$$ = new_kw_arg(p, assignable(p, $1, NODE_SPECIAL_REQUIRED_KEYWORD, &@$), &@$);
		    /*% %*/
		    /*% ripper: rb_assoc_new(get_value(assignable(p, $1)), 0) %*/
		    }
		;

f_block_kw	: f_label primary_value
		    {
		    /*%%%*/
			$$ = new_kw_arg(p, assignable(p, $1, $2, &@$), &@$);
		    /*% %*/
		    /*% ripper: rb_assoc_new(get_value(assignable(p, $1)), get_value($2)) %*/
		    }
		| f_label
		    {
		    /*%%%*/
			$$ = new_kw_arg(p, assignable(p, $1, NODE_SPECIAL_REQUIRED_KEYWORD, &@$), &@$);
		    /*% %*/
		    /*% ripper: rb_assoc_new(get_value(assignable(p, $1)), 0) %*/
		    }
		;

f_block_kwarg	: f_block_kw
		    {
		    /*%%%*/
			$$ = $1;
		    /*% %*/
		    /*% ripper: rb_ary_new3(1, get_value($1)) %*/
		    }
		| f_block_kwarg ',' f_block_kw
		    {
		    /*%%%*/
			$$ = kwd_append($1, $3);
		    /*% %*/
		    /*% ripper: rb_ary_push($1, get_value($3)) %*/
		    }
		;


f_kwarg		: f_kw
		    {
		    /*%%%*/
			$$ = $1;
		    /*% %*/
		    /*% ripper: rb_ary_new3(1, get_value($1)) %*/
		    }
		| f_kwarg ',' f_kw
		    {
		    /*%%%*/
			$$ = kwd_append($1, $3);
		    /*% %*/
		    /*% ripper: rb_ary_push($1, get_value($3)) %*/
		    }
		;

kwrest_mark	: tPOW
		| tDSTAR
		;

f_kwrest	: kwrest_mark tIDENTIFIER
		    {
			arg_var(p, shadowing_lvar(p, get_id($2)));
		    /*%%%*/
			$$ = $2;
		    /*% %*/
		    /*% ripper: kwrest_param!($2) %*/
		    }
		| kwrest_mark
		    {
		    /*%%%*/
			$$ = internal_id(p);
			arg_var(p, $$);
		    /*% %*/
		    /*% ripper: kwrest_param!(Qnil) %*/
		    }
		;

f_opt		: f_arg_asgn '=' arg_value
		    {
			p->cur_arg = 0;
		    /*%%%*/
			$$ = NEW_OPT_ARG(0, assignable(p, $1, $3, &@$), &@$);
		    /*% %*/
		    /*% ripper: rb_assoc_new(get_value(assignable(p, $1)), get_value($3)) %*/
		    }
		;

f_block_opt	: f_arg_asgn '=' primary_value
		    {
			p->cur_arg = 0;
		    /*%%%*/
			$$ = NEW_OPT_ARG(0, assignable(p, $1, $3, &@$), &@$);
		    /*% %*/
		    /*% ripper: rb_assoc_new(get_value(assignable(p, $1)), get_value($3)) %*/
		    }
		;

f_block_optarg	: f_block_opt
		    {
		    /*%%%*/
			$$ = $1;
		    /*% %*/
		    /*% ripper: rb_ary_new3(1, get_value($1)) %*/
		    }
		| f_block_optarg ',' f_block_opt
		    {
		    /*%%%*/
			$$ = opt_arg_append($1, $3);
		    /*% %*/
		    /*% ripper: rb_ary_push($1, get_value($3)) %*/
		    }
		;

f_optarg	: f_opt
		    {
		    /*%%%*/
			$$ = $1;
		    /*% %*/
		    /*% ripper: rb_ary_new3(1, get_value($1)) %*/
		    }
		| f_optarg ',' f_opt
		    {
		    /*%%%*/
			$$ = opt_arg_append($1, $3);
		    /*% %*/
		    /*% ripper: rb_ary_push($1, get_value($3)) %*/
		    }
		;

restarg_mark	: '*'
		| tSTAR
		;

f_rest_arg	: restarg_mark tIDENTIFIER
		    {
			arg_var(p, shadowing_lvar(p, get_id($2)));
		    /*%%%*/
			$$ = $2;
		    /*% %*/
		    /*% ripper: rest_param!($2) %*/
		    }
		| restarg_mark
		    {
		    /*%%%*/
			$$ = internal_id(p);
			arg_var(p, $$);
		    /*% %*/
		    /*% ripper: rest_param!(Qnil) %*/
		    }
		;

blkarg_mark	: '&'
		| tAMPER
		;

f_block_arg	: blkarg_mark tIDENTIFIER
		    {
			arg_var(p, shadowing_lvar(p, get_id($2)));
		    /*%%%*/
			$$ = $2;
		    /*% %*/
		    /*% ripper: blockarg!($2) %*/
		    }
		;

opt_f_block_arg	: ',' f_block_arg
		    {
			$$ = $2;
		    }
		| none
		    {
		    /*%%%*/
			$$ = 0;
		    /*% %*/
		    /*% ripper: Qundef %*/
		    }
		;

singleton	: var_ref
		    {
			value_expr($1);
			$$ = $1;
		    }
		| '(' {SET_LEX_STATE(EXPR_BEG);} expr rparen
		    {
		    /*%%%*/
			switch (nd_type($3)) {
			  case NODE_STR:
			  case NODE_DSTR:
			  case NODE_XSTR:
			  case NODE_DXSTR:
			  case NODE_DREGX:
			  case NODE_LIT:
			  case NODE_ARRAY:
			  case NODE_ZARRAY:
			    yyerror1(&@3, "can't define singleton method for literals");
			    break;
			  default:
			    value_expr($3);
			    break;
			}
			$$ = $3;
		    /*% %*/
		    /*% ripper: paren!($3) %*/
		    }
		;

assoc_list	: none
		| assocs trailer
		    {
		    /*%%%*/
			$$ = $1;
		    /*% %*/
		    /*% ripper: assoclist_from_args!($1) %*/
		    }
		;

assocs		: assoc
		    /*% ripper[brace]: rb_ary_new3(1, get_value($1)) %*/
		| assocs ',' assoc
		    {
		    /*%%%*/
			NODE *assocs = $1;
			NODE *tail = $3;
			if (!assocs) {
			    assocs = tail;
			}
			else if (tail) {
			    if (assocs->nd_head &&
				!tail->nd_head && nd_type(tail->nd_next) == NODE_ARRAY &&
				nd_type(tail->nd_next->nd_head) == NODE_HASH) {
				/* DSTAR */
				tail = tail->nd_next->nd_head->nd_head;
			    }
			    assocs = list_concat(assocs, tail);
			}
			$$ = assocs;
		    /*% %*/
		    /*% ripper: rb_ary_push($1, get_value($3)) %*/
		    }
		;

assoc		: arg_value tASSOC arg_value
		    {
		    /*%%%*/
			if (nd_type($1) == NODE_STR) {
			    nd_set_type($1, NODE_LIT);
			    add_mark_object(p, $1->nd_lit = rb_fstring($1->nd_lit));
			}
			$$ = list_append(p, NEW_LIST($1, &@$), $3);
		    /*% %*/
		    /*% ripper: assoc_new!($1, $3) %*/
		    }
		| tLABEL arg_value
		    {
		    /*%%%*/
			$$ = list_append(p, NEW_LIST(NEW_LIT(ID2SYM($1), &@1), &@$), $2);
		    /*% %*/
		    /*% ripper: assoc_new!($1, $2) %*/
		    }
		| tSTRING_BEG string_contents tLABEL_END arg_value
		    {
		    /*%%%*/
			YYLTYPE loc = code_loc_gen(&@1, &@3);
			$$ = list_append(p, NEW_LIST(dsym_node(p, $2, &loc), &loc), $4);
		    /*% %*/
		    /*% ripper: assoc_new!(dyna_symbol!($2), $4) %*/
		    }
		| tDSTAR arg_value
		    {
		    /*%%%*/
			if (nd_type($2) == NODE_HASH &&
			    !($2->nd_head && $2->nd_head->nd_alen))
			    $$ = 0;
			else
			    $$ = list_append(p, NEW_LIST(0, &@$), $2);
		    /*% %*/
		    /*% ripper: assoc_splat!($2) %*/
		    }
		;

operation	: tIDENTIFIER
		| tCONSTANT
		| tFID
		;

operation2	: tIDENTIFIER
		| tCONSTANT
		| tFID
		| op
		;

operation3	: tIDENTIFIER
		| tFID
		| op
		;

dot_or_colon	: '.'
		| tCOLON2
		;

call_op 	: '.'
		| tANDDOT
		;

call_op2	: call_op
		| tCOLON2
		;

opt_terms	: /* none */
		| terms
		;

opt_nl		: /* none */
		| '\n'
		;

rparen		: opt_nl ')'
		;

rbracket	: opt_nl ']'
		;

trailer		: /* none */
		| '\n'
		| ','
		;

term		: ';' {yyerrok;token_flush(p);}
		| '\n' {token_flush(p);}
		;

terms		: term
		| terms ';' {yyerrok;}
		;

none		: /* none */
		    {
			$$ = Qnull;
		    }
		;
