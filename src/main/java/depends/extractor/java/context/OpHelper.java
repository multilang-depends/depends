package depends.extractor.java.context;

//: primary
//| expression bop='.'
//  ( IDENTIFIER
//  | methodCall
//  | THIS
//  | NEW nonWildcardTypeArguments? innerCreator
//  | SUPER superSuffix
//  | explicitGenericInvocation
//  )
//| expression '[' expression ']'
//| methodCall
//| NEW creator
//| '(' typeType ')' expression
//| expression ('<' '<' | '>' '>' '>' | '>' '>') expression
//| expression bop=INSTANCEOF typeType
//| <assoc=right> expression
//  expression
//| lambdaExpression // Java8
public class OpHelper {
	public static boolean isLogic(String op) {
		return op.equals("<") || op.equals(">") || op.equals("<=") || op.equals(">=") 
				|| op.equals("==")
				|| op.equals("!=") || op.equals("&&") || op.equals("||") || op.equals("?");
	}
	public static boolean isArithmetic(String op) {
		return op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/") || op.equals("%")
				|| op.equals("&") || op.equals("|") || op.equals("^") || op.equals(">>") || op.equals(">>>")
				|| op.equals("<<") || op.equals("~") || op.equals("!") ;
	}
	public static boolean isAssigment(String op) {
		return op.equals("=") || op.equals("+=") || op.equals("-=") || op.equals("*=") || op.equals("/=")
				|| op.equals("&=") || op.equals("|=") || op.equals("^=") || op.equals(">>=") || op.equals(">>>=")
				|| op.equals("<<=") || op.equals("%=");
	}

	public static boolean isIncrementalDecremental(String op) {
		return op.equals("++") || op.equals("--");
	}
}
