package depends.addons;

import java.io.IOException;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.junit.Test;

public class TranslateYacc {
	String keywordsString = "keyword_class:  'class';\n" + 
			"keyword_module:  'module';\n" + 
			"keyword_def:  'def';\n" + 
			"keyword_undef:  'undef';\n" + 
			"keyword_begin:  'begin';\n" + 
			"keyword_rescue:  'rescue';\n" + 
			"keyword_ensure:  'ensure';\n" + 
			"keyword_end:  'end';\n" + 
			"keyword_if:  'if';\n" + 
			"keyword_unless:  'unless';\n" + 
			"keyword_then:  'then';\n" + 
			"keyword_elsif:  'elsif';\n" + 
			"keyword_else:  'else';\n" + 
			"keyword_case:  'case';\n" + 
			"keyword_when:  'when';\n" + 
			"keyword_while:  'while';\n" + 
			"keyword_until:  'until';\n" + 
			"keyword_for:  'for';\n" + 
			"keyword_break:  'break';\n" + 
			"keyword_next:  'next';\n" + 
			"keyword_redo:  'redo';\n" + 
			"keyword_retry:  'retry';\n" + 
			"keyword_in:  'in';\n" + 
			"keyword_do:  'do';\n" + 
			"keyword_return:  'return';\n" + 
			"keyword_yield:  'yield';\n" + 
			"keyword_super:  'super';\n" + 
			"keyword_self:  'self';\n" + 
			"keyword_nil:  'nil';\n" + 
			"keyword_true:  'true';\n" + 
			"keyword_false:  'false';\n" + 
			"keyword_KEYWORD_and:  'and';\n" + 
			"keyword_KEYWORD_or:  'or';\n" + 
			"keyword_KEYWORD_not:  'not';\n" + 
			"keyword_alias:  'alias';\n" + 
			"keyword_defined:  'defined?';\n" + 
			"keyword_KEYWORD_BEGIN:  'BEGIN';\n" + 
			"keyword_KEYWORD_END:  'END';\n" + 
			"keyword_KEYWORD_LINE:  '__LINE__';\n" + 
			"keyword_KEYWORD_FILE:  '__FILE__';\n" + 
			"keyword_KEYWORD_ENCODING:  '__ENCODING__'";
	
	
	String tokensString = "tUPLUS 'unary+'\n" + 
			"tUMINUS 'unary-'\n" + 
			"tPOW '**'\n" + 
			"tCMP '<=>'\n" + 
			"tEQ '=='\n" + 
			"tEQQ '==='\n" + 
			"tNEQ '!='\n" + 
			"tGEQ '>='\n" + 
			"tLEQ '<='\n" + 
			"tANDOP '&&'\n" + 
			"tOROP '||'\n" + 
			"tMATCH '=~'\n" + 
			"tNMATCH '!~'\n" + 
			"tDOT2 '..'\n" + 
			"tDOT3 '...'\n" + 
			"tAREF '[]'\n" + 
			"tASET '[]='\n" + 
			"tLSHFT '<<'\n" + 
			"tRSHFT '>>'\n" + 
			"tANDDOT '&.'\n" + 
			"tCOLON2 '::'\n" + 
			"tCOLON3 '::'\n" + 
			"tOP_ASGN TBC\n" + 
			"tASSOC '=>'\n" + 
			"tLPAREN '('\n" + 
			"tLPAREN_ARG '( arg'\n" + 
			"tRPAREN ')'\n" + 
			"tLBRACK '['\n" + 
			"tLBRACE '{'\n" + 
			"tLBRACE_ARG '{ arg'\n" + 
			"tSTAR '*'\n" + 
			"tDSTAR '**arg'\n" + 
			"tAMPER '&'\n" + 
			"tLAMBDA '->'";
	
	@Test
	public void test() throws IOException {
        CharStream input = CharStreams.fromFileName("./src/main/antlr4/depends/extractor/ruby/parse.y");
        String s = input.toString();
        s = s.replace("'{'", "@$@<");
        s = s.replace("'}'", "@$@>");
        s = s.replace("\"}\"", "$$@>");
        s = s.replace("\"{\"", "$$@<");
        
        int open = 0;
        StringBuffer sb  = new StringBuffer();
        for (int i = 0;i<s.length();i++) {
        	char c = s.charAt(i);
        	if (c=='{') open++;
        	if (open==0)
        		sb.append(c);
        	if (c=='}') open--;
        }
        s = sb.toString();
        s = s.replace("@$@<","'{'");
        s = s.replace("@$@>","'}'");
        s = s.replace("$$@>","\"}\"");
        s = s.replace("$$@<","\"{\"");

        
        String[] keywords = keywordsString.split("\n");
        for (String keyword:keywords) {
        	String[] p = keyword.split(":  '");
        	String a = p[0];
        	String b = "'"+p[1];
        	String renamedA = a.replace("keyword_", "").toUpperCase();
        	s = s.replace(a, renamedA);
        	s = s.replace("k_"+renamedA.toLowerCase(), renamedA);
        	System.out.println(renamedA + ":\t"+b);
        }
        String[] tokens = tokensString.split("\n"); 
        for (String token:tokens) {
        	String[] p = token.split(" ");
        	String a = p[0];
        	String b = p[1];
        	String renamedA = a.replace("t", "TOKEN_").toUpperCase();
        	s = s.replace(a, renamedA);
        	System.out.println(renamedA + ":\t"+b+';');
        }
        String[] lines = s.split("\n");
        for (int i=0;i<lines.length;i++) {
        	if (lines[i].trim().length()>0)
        		System.out.println(lines[i]);
        }
	}

	
}
