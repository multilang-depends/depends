// Generated from ./src/main/antlr4/depends/javaextractor/CElsa.g4 by ANTLR 4.5.3
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CElsaParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36, T__36=37, T__37=38, 
		T__38=39, T__39=40, T__40=41, T__41=42, T__42=43, T__43=44, T__44=45, 
		T__45=46, T__46=47, T__47=48, T__48=49, T__49=50, T__50=51, T__51=52, 
		T__52=53, T__53=54, T__54=55, T__55=56, T__56=57, T__57=58, T__58=59, 
		T__59=60, T__60=61, T__61=62, T__62=63, T__63=64, T__64=65, T__65=66, 
		T__66=67, T__67=68, T__68=69, T__69=70, T__70=71, T__71=72, T__72=73, 
		T__73=74, T__74=75, T__75=76, T__76=77, T__77=78, T__78=79, T__79=80, 
		T__80=81, T__81=82, T__82=83, T__83=84, T__84=85, T__85=86, T__86=87, 
		T__87=88, T__88=89, T__89=90, T__90=91, T__91=92, T__92=93, T__93=94, 
		T__94=95, T__95=96, T__96=97, T__97=98, T__98=99, T__99=100, T__100=101, 
		T__101=102, T__102=103, T__103=104, T__104=105, T__105=106, T__106=107, 
		T__107=108, T__108=109, MultiLineMacro=110, Directive=111, DECIMAL_LITERAL=112, 
		HEX_LITERAL=113, OCT_LITERAL=114, BINARY_LITERAL=115, FLOAT_LITERAL=116, 
		HEX_FLOAT_LITERAL=117, BOOL_LITERAL=118, CHAR_LITERAL=119, STRING_LITERAL=120, 
		NULL_LITERAL=121, WS=122, COMMENT=123, LINE_COMMENT=124, IDENTIFIER=125;
	public static final int
		RULE_translationUnit = 0, RULE_declaration = 1, RULE_typedefName = 2, 
		RULE_namespaceName = 3, RULE_originalNamespaceName = 4, RULE_namespaceAlias = 5, 
		RULE_className = 6, RULE_enumName = 7, RULE_templateName = 8, RULE_literal = 9, 
		RULE_idExpression = 10, RULE_unqualifiedId = 11, RULE_template = 12, RULE_qualifiedId = 13, 
		RULE_nestedNameSpecifier = 14, RULE_classOrNamespaceName = 15, RULE_primaryExpression = 16, 
		RULE_expression = 17, RULE_expressionList = 18, RULE_pseudoDestructorName = 19, 
		RULE_newExpression = 20, RULE_newPlacement = 21, RULE_newTypeId = 22, 
		RULE_newDeclarator = 23, RULE_newInitializer = 24, RULE_deleteExpression = 25, 
		RULE_pmExpression = 26, RULE_statement = 27, RULE_labeledStatement = 28, 
		RULE_expressionStatement = 29, RULE_compoundStatement = 30, RULE_selectionStatement = 31, 
		RULE_condition = 32, RULE_iterationStatement = 33, RULE_forInitStatement = 34, 
		RULE_jumpStatement = 35, RULE_blockDeclaration = 36, RULE_simpleDeclaration = 37, 
		RULE_initDeclaratorList = 38, RULE_initDeclarator = 39, RULE_declSpecifier = 40, 
		RULE_storageClassSpecifier = 41, RULE_functionSpecifier = 42, RULE_typeSpecifier = 43, 
		RULE_simpleTypeSpecifier = 44, RULE_typeName = 45, RULE_elaboratedTypeSpecifier = 46, 
		RULE_enumSpecifier = 47, RULE_enumeratorList = 48, RULE_enumeratorDefinition = 49, 
		RULE_namespaceDefinition = 50, RULE_namedNamespaceDefinition = 51, RULE_originalNamespaceDefinition = 52, 
		RULE_extensionNamespaceDefinition = 53, RULE_unnamedNamespaceDefinition = 54, 
		RULE_namespaceAliasDefinition = 55, RULE_qualifiedNamespaceSpecifier = 56, 
		RULE_usingDeclaration = 57, RULE_usingDirective = 58, RULE_asmDefinition = 59, 
		RULE_linkageSpecification = 60, RULE_declarator = 61, RULE_ptrOperator = 62, 
		RULE_cvQualifierSeq = 63, RULE_cvQualifier = 64, RULE_declaratorId = 65, 
		RULE_typeId = 66, RULE_parameterDeclarationClause = 67, RULE_parameterDeclaration = 68, 
		RULE_functionDefinition = 69, RULE_functionBody = 70, RULE_initializer = 71, 
		RULE_initializerClause = 72, RULE_initializerList = 73, RULE_classSpecifier = 74, 
		RULE_classHead = 75, RULE_classKey = 76, RULE_memberSpecification = 77, 
		RULE_memberDeclaration = 78, RULE_memberDeclaratorList = 79, RULE_memberDeclarator = 80, 
		RULE_pureSpecifier = 81, RULE_constantInitializer = 82, RULE_baseClause = 83, 
		RULE_baseSpecifierList = 84, RULE_baseSpecifier = 85, RULE_accessSpecifier = 86, 
		RULE_conversionFunctionId = 87, RULE_ctorInitializer = 88, RULE_memInitializer = 89, 
		RULE_memInitializerId = 90, RULE_operatorFunctionId = 91, RULE_operatorFunc = 92, 
		RULE_templateDeclaration = 93, RULE_templateParameterList = 94, RULE_templateParameter = 95, 
		RULE_typeParameter = 96, RULE_templateId = 97, RULE_templateArgumentList = 98, 
		RULE_templateArgument = 99, RULE_explicitInstantiation = 100, RULE_explicitSpecialization = 101, 
		RULE_tryBlock = 102, RULE_functionTryBlock = 103, RULE_handlerSeq = 104, 
		RULE_handler = 105, RULE_exceptionDeclaration = 106, RULE_throwExpression = 107, 
		RULE_exceptionSpecification = 108, RULE_typeIdList = 109;
	public static final String[] ruleNames = {
		"translationUnit", "declaration", "typedefName", "namespaceName", "originalNamespaceName", 
		"namespaceAlias", "className", "enumName", "templateName", "literal", 
		"idExpression", "unqualifiedId", "template", "qualifiedId", "nestedNameSpecifier", 
		"classOrNamespaceName", "primaryExpression", "expression", "expressionList", 
		"pseudoDestructorName", "newExpression", "newPlacement", "newTypeId", 
		"newDeclarator", "newInitializer", "deleteExpression", "pmExpression", 
		"statement", "labeledStatement", "expressionStatement", "compoundStatement", 
		"selectionStatement", "condition", "iterationStatement", "forInitStatement", 
		"jumpStatement", "blockDeclaration", "simpleDeclaration", "initDeclaratorList", 
		"initDeclarator", "declSpecifier", "storageClassSpecifier", "functionSpecifier", 
		"typeSpecifier", "simpleTypeSpecifier", "typeName", "elaboratedTypeSpecifier", 
		"enumSpecifier", "enumeratorList", "enumeratorDefinition", "namespaceDefinition", 
		"namedNamespaceDefinition", "originalNamespaceDefinition", "extensionNamespaceDefinition", 
		"unnamedNamespaceDefinition", "namespaceAliasDefinition", "qualifiedNamespaceSpecifier", 
		"usingDeclaration", "usingDirective", "asmDefinition", "linkageSpecification", 
		"declarator", "ptrOperator", "cvQualifierSeq", "cvQualifier", "declaratorId", 
		"typeId", "parameterDeclarationClause", "parameterDeclaration", "functionDefinition", 
		"functionBody", "initializer", "initializerClause", "initializerList", 
		"classSpecifier", "classHead", "classKey", "memberSpecification", "memberDeclaration", 
		"memberDeclaratorList", "memberDeclarator", "pureSpecifier", "constantInitializer", 
		"baseClause", "baseSpecifierList", "baseSpecifier", "accessSpecifier", 
		"conversionFunctionId", "ctorInitializer", "memInitializer", "memInitializerId", 
		"operatorFunctionId", "operatorFunc", "templateDeclaration", "templateParameterList", 
		"templateParameter", "typeParameter", "templateId", "templateArgumentList", 
		"templateArgument", "explicitInstantiation", "explicitSpecialization", 
		"tryBlock", "functionTryBlock", "handlerSeq", "handler", "exceptionDeclaration", 
		"throwExpression", "exceptionSpecification", "typeIdList"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'~'", "'template'", "'::'", "'this'", "'('", "')'", "'.'", "'->'", 
		"'+'", "'-'", "'++'", "'--'", "'*'", "'&'", "'!'", "'['", "']'", "'sizeof'", 
		"'typename'", "'dynamic_cast'", "'<'", "'>'", "'static_cast'", "'reinterpret_cast'", 
		"'const_cast'", "'typeid'", "'/'", "'%'", "'<='", "'>='", "'=='", "'!='", 
		"'^'", "'|'", "'&&'", "'||'", "'?'", "':'", "'='", "'+='", "'-='", "'*='", 
		"'/='", "'&='", "'|='", "'^='", "'>>='", "'>>>='", "'<<='", "'%='", "','", 
		"'new'", "'delete'", "'.*'", "'case'", "'default'", "';'", "'{'", "'}'", 
		"'if'", "'else'", "'switch'", "'while'", "'do'", "'for'", "'break'", "'continue'", 
		"'return'", "'goto'", "'friend'", "'typedef'", "'auto'", "'register'", 
		"'static'", "'extern'", "'mutable'", "'inline'", "'virtual'", "'explicit'", 
		"'char'", "'wchar_t'", "'bool'", "'short'", "'int'", "'long'", "'signed'", 
		"'unsigned'", "'float'", "'double'", "'void'", "'enum'", "'namespace'", 
		"'using'", "'asm'", "'const'", "'volatile'", "'...'", "'class'", "'struct'", 
		"'union'", "'private'", "'protected'", "'public'", "'operator'", "'<<'", 
		"'>>'", "'try'", "'catch'", "'throw'", null, null, null, null, null, null, 
		null, null, null, null, null, "'null'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, "MultiLineMacro", "Directive", "DECIMAL_LITERAL", "HEX_LITERAL", 
		"OCT_LITERAL", "BINARY_LITERAL", "FLOAT_LITERAL", "HEX_FLOAT_LITERAL", 
		"BOOL_LITERAL", "CHAR_LITERAL", "STRING_LITERAL", "NULL_LITERAL", "WS", 
		"COMMENT", "LINE_COMMENT", "IDENTIFIER"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "CElsa.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public CElsaParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class TranslationUnitContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(CElsaParser.EOF, 0); }
		public List<DeclarationContext> declaration() {
			return getRuleContexts(DeclarationContext.class);
		}
		public DeclarationContext declaration(int i) {
			return getRuleContext(DeclarationContext.class,i);
		}
		public TranslationUnitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_translationUnit; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterTranslationUnit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitTranslationUnit(this);
		}
	}

	public final TranslationUnitContext translationUnit() throws RecognitionException {
		TranslationUnitContext _localctx = new TranslationUnitContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_translationUnit);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(223);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << T__2) | (1L << T__4) | (1L << T__18) | (1L << T__56))) != 0) || ((((_la - 70)) & ~0x3f) == 0 && ((1L << (_la - 70)) & ((1L << (T__69 - 70)) | (1L << (T__70 - 70)) | (1L << (T__71 - 70)) | (1L << (T__72 - 70)) | (1L << (T__73 - 70)) | (1L << (T__74 - 70)) | (1L << (T__75 - 70)) | (1L << (T__76 - 70)) | (1L << (T__77 - 70)) | (1L << (T__78 - 70)) | (1L << (T__79 - 70)) | (1L << (T__80 - 70)) | (1L << (T__81 - 70)) | (1L << (T__82 - 70)) | (1L << (T__83 - 70)) | (1L << (T__84 - 70)) | (1L << (T__85 - 70)) | (1L << (T__86 - 70)) | (1L << (T__87 - 70)) | (1L << (T__88 - 70)) | (1L << (T__89 - 70)) | (1L << (T__90 - 70)) | (1L << (T__91 - 70)) | (1L << (T__92 - 70)) | (1L << (T__93 - 70)) | (1L << (T__94 - 70)) | (1L << (T__95 - 70)) | (1L << (T__97 - 70)) | (1L << (T__98 - 70)) | (1L << (T__99 - 70)) | (1L << (T__103 - 70)) | (1L << (IDENTIFIER - 70)))) != 0)) {
				{
				{
				setState(220);
				declaration();
				}
				}
				setState(225);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(226);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclarationContext extends ParserRuleContext {
		public BlockDeclarationContext blockDeclaration() {
			return getRuleContext(BlockDeclarationContext.class,0);
		}
		public FunctionDefinitionContext functionDefinition() {
			return getRuleContext(FunctionDefinitionContext.class,0);
		}
		public TemplateDeclarationContext templateDeclaration() {
			return getRuleContext(TemplateDeclarationContext.class,0);
		}
		public ExplicitInstantiationContext explicitInstantiation() {
			return getRuleContext(ExplicitInstantiationContext.class,0);
		}
		public ExplicitSpecializationContext explicitSpecialization() {
			return getRuleContext(ExplicitSpecializationContext.class,0);
		}
		public LinkageSpecificationContext linkageSpecification() {
			return getRuleContext(LinkageSpecificationContext.class,0);
		}
		public NamespaceDefinitionContext namespaceDefinition() {
			return getRuleContext(NamespaceDefinitionContext.class,0);
		}
		public DeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitDeclaration(this);
		}
	}

	public final DeclarationContext declaration() throws RecognitionException {
		DeclarationContext _localctx = new DeclarationContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_declaration);
		try {
			setState(235);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(228);
				blockDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(229);
				functionDefinition();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(230);
				templateDeclaration();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(231);
				explicitInstantiation();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(232);
				explicitSpecialization();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(233);
				linkageSpecification();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(234);
				namespaceDefinition();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypedefNameContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(CElsaParser.IDENTIFIER, 0); }
		public TypedefNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typedefName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterTypedefName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitTypedefName(this);
		}
	}

	public final TypedefNameContext typedefName() throws RecognitionException {
		TypedefNameContext _localctx = new TypedefNameContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_typedefName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(237);
			match(IDENTIFIER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NamespaceNameContext extends ParserRuleContext {
		public OriginalNamespaceNameContext originalNamespaceName() {
			return getRuleContext(OriginalNamespaceNameContext.class,0);
		}
		public NamespaceAliasContext namespaceAlias() {
			return getRuleContext(NamespaceAliasContext.class,0);
		}
		public NamespaceNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_namespaceName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterNamespaceName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitNamespaceName(this);
		}
	}

	public final NamespaceNameContext namespaceName() throws RecognitionException {
		NamespaceNameContext _localctx = new NamespaceNameContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_namespaceName);
		try {
			setState(241);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(239);
				originalNamespaceName();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(240);
				namespaceAlias();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OriginalNamespaceNameContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(CElsaParser.IDENTIFIER, 0); }
		public OriginalNamespaceNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_originalNamespaceName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterOriginalNamespaceName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitOriginalNamespaceName(this);
		}
	}

	public final OriginalNamespaceNameContext originalNamespaceName() throws RecognitionException {
		OriginalNamespaceNameContext _localctx = new OriginalNamespaceNameContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_originalNamespaceName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(243);
			match(IDENTIFIER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NamespaceAliasContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(CElsaParser.IDENTIFIER, 0); }
		public NamespaceAliasContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_namespaceAlias; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterNamespaceAlias(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitNamespaceAlias(this);
		}
	}

	public final NamespaceAliasContext namespaceAlias() throws RecognitionException {
		NamespaceAliasContext _localctx = new NamespaceAliasContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_namespaceAlias);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(245);
			match(IDENTIFIER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassNameContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(CElsaParser.IDENTIFIER, 0); }
		public TemplateIdContext templateId() {
			return getRuleContext(TemplateIdContext.class,0);
		}
		public ClassNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_className; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterClassName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitClassName(this);
		}
	}

	public final ClassNameContext className() throws RecognitionException {
		ClassNameContext _localctx = new ClassNameContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_className);
		try {
			setState(249);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(247);
				match(IDENTIFIER);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(248);
				templateId();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EnumNameContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(CElsaParser.IDENTIFIER, 0); }
		public EnumNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enumName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterEnumName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitEnumName(this);
		}
	}

	public final EnumNameContext enumName() throws RecognitionException {
		EnumNameContext _localctx = new EnumNameContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_enumName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(251);
			match(IDENTIFIER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TemplateNameContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(CElsaParser.IDENTIFIER, 0); }
		public TemplateNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_templateName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterTemplateName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitTemplateName(this);
		}
	}

	public final TemplateNameContext templateName() throws RecognitionException {
		TemplateNameContext _localctx = new TemplateNameContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_templateName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(253);
			match(IDENTIFIER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LiteralContext extends ParserRuleContext {
		public TerminalNode DECIMAL_LITERAL() { return getToken(CElsaParser.DECIMAL_LITERAL, 0); }
		public TerminalNode HEX_LITERAL() { return getToken(CElsaParser.HEX_LITERAL, 0); }
		public TerminalNode OCT_LITERAL() { return getToken(CElsaParser.OCT_LITERAL, 0); }
		public TerminalNode BINARY_LITERAL() { return getToken(CElsaParser.BINARY_LITERAL, 0); }
		public TerminalNode CHAR_LITERAL() { return getToken(CElsaParser.CHAR_LITERAL, 0); }
		public TerminalNode FLOAT_LITERAL() { return getToken(CElsaParser.FLOAT_LITERAL, 0); }
		public TerminalNode HEX_FLOAT_LITERAL() { return getToken(CElsaParser.HEX_FLOAT_LITERAL, 0); }
		public TerminalNode STRING_LITERAL() { return getToken(CElsaParser.STRING_LITERAL, 0); }
		public TerminalNode BOOL_LITERAL() { return getToken(CElsaParser.BOOL_LITERAL, 0); }
		public TerminalNode NULL_LITERAL() { return getToken(CElsaParser.NULL_LITERAL, 0); }
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitLiteral(this);
		}
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_literal);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(255);
			_la = _input.LA(1);
			if ( !(((((_la - 112)) & ~0x3f) == 0 && ((1L << (_la - 112)) & ((1L << (DECIMAL_LITERAL - 112)) | (1L << (HEX_LITERAL - 112)) | (1L << (OCT_LITERAL - 112)) | (1L << (BINARY_LITERAL - 112)) | (1L << (FLOAT_LITERAL - 112)) | (1L << (HEX_FLOAT_LITERAL - 112)) | (1L << (BOOL_LITERAL - 112)) | (1L << (CHAR_LITERAL - 112)) | (1L << (STRING_LITERAL - 112)) | (1L << (NULL_LITERAL - 112)))) != 0)) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IdExpressionContext extends ParserRuleContext {
		public UnqualifiedIdContext unqualifiedId() {
			return getRuleContext(UnqualifiedIdContext.class,0);
		}
		public QualifiedIdContext qualifiedId() {
			return getRuleContext(QualifiedIdContext.class,0);
		}
		public IdExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_idExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterIdExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitIdExpression(this);
		}
	}

	public final IdExpressionContext idExpression() throws RecognitionException {
		IdExpressionContext _localctx = new IdExpressionContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_idExpression);
		try {
			setState(259);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(257);
				unqualifiedId();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(258);
				qualifiedId();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UnqualifiedIdContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(CElsaParser.IDENTIFIER, 0); }
		public OperatorFunctionIdContext operatorFunctionId() {
			return getRuleContext(OperatorFunctionIdContext.class,0);
		}
		public ConversionFunctionIdContext conversionFunctionId() {
			return getRuleContext(ConversionFunctionIdContext.class,0);
		}
		public ClassNameContext className() {
			return getRuleContext(ClassNameContext.class,0);
		}
		public TemplateIdContext templateId() {
			return getRuleContext(TemplateIdContext.class,0);
		}
		public UnqualifiedIdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unqualifiedId; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterUnqualifiedId(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitUnqualifiedId(this);
		}
	}

	public final UnqualifiedIdContext unqualifiedId() throws RecognitionException {
		UnqualifiedIdContext _localctx = new UnqualifiedIdContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_unqualifiedId);
		try {
			setState(267);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(261);
				match(IDENTIFIER);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(262);
				operatorFunctionId();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(263);
				conversionFunctionId();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(264);
				match(T__0);
				setState(265);
				className();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(266);
				templateId();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TemplateContext extends ParserRuleContext {
		public TemplateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_template; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterTemplate(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitTemplate(this);
		}
	}

	public final TemplateContext template() throws RecognitionException {
		TemplateContext _localctx = new TemplateContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_template);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(269);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class QualifiedIdContext extends ParserRuleContext {
		public NestedNameSpecifierContext nestedNameSpecifier() {
			return getRuleContext(NestedNameSpecifierContext.class,0);
		}
		public UnqualifiedIdContext unqualifiedId() {
			return getRuleContext(UnqualifiedIdContext.class,0);
		}
		public TemplateContext template() {
			return getRuleContext(TemplateContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(CElsaParser.IDENTIFIER, 0); }
		public OperatorFunctionIdContext operatorFunctionId() {
			return getRuleContext(OperatorFunctionIdContext.class,0);
		}
		public TemplateIdContext templateId() {
			return getRuleContext(TemplateIdContext.class,0);
		}
		public QualifiedIdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_qualifiedId; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterQualifiedId(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitQualifiedId(this);
		}
	}

	public final QualifiedIdContext qualifiedId() throws RecognitionException {
		QualifiedIdContext _localctx = new QualifiedIdContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_qualifiedId);
		int _la;
		try {
			setState(286);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(272);
				_la = _input.LA(1);
				if (_la==T__2) {
					{
					setState(271);
					match(T__2);
					}
				}

				setState(274);
				nestedNameSpecifier();
				setState(276);
				_la = _input.LA(1);
				if (_la==T__1) {
					{
					setState(275);
					template();
					}
				}

				setState(278);
				unqualifiedId();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(280);
				match(T__2);
				setState(281);
				match(IDENTIFIER);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(282);
				match(T__2);
				setState(283);
				operatorFunctionId();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(284);
				match(T__2);
				setState(285);
				templateId();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NestedNameSpecifierContext extends ParserRuleContext {
		public ClassOrNamespaceNameContext classOrNamespaceName() {
			return getRuleContext(ClassOrNamespaceNameContext.class,0);
		}
		public NestedNameSpecifierContext nestedNameSpecifier() {
			return getRuleContext(NestedNameSpecifierContext.class,0);
		}
		public NestedNameSpecifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nestedNameSpecifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterNestedNameSpecifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitNestedNameSpecifier(this);
		}
	}

	public final NestedNameSpecifierContext nestedNameSpecifier() throws RecognitionException {
		NestedNameSpecifierContext _localctx = new NestedNameSpecifierContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_nestedNameSpecifier);
		try {
			setState(298);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(288);
				classOrNamespaceName();
				setState(289);
				match(T__2);
				setState(291);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
				case 1:
					{
					setState(290);
					nestedNameSpecifier();
					}
					break;
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(293);
				classOrNamespaceName();
				setState(294);
				match(T__2);
				setState(295);
				match(T__1);
				setState(296);
				nestedNameSpecifier();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassOrNamespaceNameContext extends ParserRuleContext {
		public ClassNameContext className() {
			return getRuleContext(ClassNameContext.class,0);
		}
		public NamespaceNameContext namespaceName() {
			return getRuleContext(NamespaceNameContext.class,0);
		}
		public ClassOrNamespaceNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classOrNamespaceName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterClassOrNamespaceName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitClassOrNamespaceName(this);
		}
	}

	public final ClassOrNamespaceNameContext classOrNamespaceName() throws RecognitionException {
		ClassOrNamespaceNameContext _localctx = new ClassOrNamespaceNameContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_classOrNamespaceName);
		try {
			setState(302);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(300);
				className();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(301);
				namespaceName();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrimaryExpressionContext extends ParserRuleContext {
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public IdExpressionContext idExpression() {
			return getRuleContext(IdExpressionContext.class,0);
		}
		public NewExpressionContext newExpression() {
			return getRuleContext(NewExpressionContext.class,0);
		}
		public DeleteExpressionContext deleteExpression() {
			return getRuleContext(DeleteExpressionContext.class,0);
		}
		public PrimaryExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primaryExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterPrimaryExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitPrimaryExpression(this);
		}
	}

	public final PrimaryExpressionContext primaryExpression() throws RecognitionException {
		PrimaryExpressionContext _localctx = new PrimaryExpressionContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_primaryExpression);
		try {
			setState(313);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(304);
				literal();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(305);
				match(T__3);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(306);
				match(T__4);
				setState(307);
				expression(0);
				setState(308);
				match(T__5);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(310);
				idExpression();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(311);
				newExpression();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(312);
				deleteExpression();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public Token prefix;
		public Token bop;
		public Token postfix;
		public PrimaryExpressionContext primaryExpression() {
			return getRuleContext(PrimaryExpressionContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TypeIdContext typeId() {
			return getRuleContext(TypeIdContext.class,0);
		}
		public NestedNameSpecifierContext nestedNameSpecifier() {
			return getRuleContext(NestedNameSpecifierContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(CElsaParser.IDENTIFIER, 0); }
		public ExpressionListContext expressionList() {
			return getRuleContext(ExpressionListContext.class,0);
		}
		public TemplateIdContext templateId() {
			return getRuleContext(TemplateIdContext.class,0);
		}
		public TemplateContext template() {
			return getRuleContext(TemplateContext.class,0);
		}
		public SimpleTypeSpecifierContext simpleTypeSpecifier() {
			return getRuleContext(SimpleTypeSpecifierContext.class,0);
		}
		public IdExpressionContext idExpression() {
			return getRuleContext(IdExpressionContext.class,0);
		}
		public PseudoDestructorNameContext pseudoDestructorName() {
			return getRuleContext(PseudoDestructorNameContext.class,0);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitExpression(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 34;
		enterRecursionRule(_localctx, 34, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(401);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				{
				setState(316);
				primaryExpression();
				}
				break;
			case 2:
				{
				setState(317);
				((ExpressionContext)_localctx).prefix = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14))) != 0)) ) {
					((ExpressionContext)_localctx).prefix = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(318);
				expression(28);
				}
				break;
			case 3:
				{
				setState(319);
				match(T__17);
				setState(320);
				expression(25);
				}
				break;
			case 4:
				{
				setState(321);
				match(T__17);
				setState(322);
				match(T__4);
				setState(323);
				typeId();
				setState(324);
				match(T__5);
				}
				break;
			case 5:
				{
				setState(326);
				match(T__18);
				setState(328);
				_la = _input.LA(1);
				if (_la==T__2) {
					{
					setState(327);
					match(T__2);
					}
				}

				setState(330);
				nestedNameSpecifier();
				setState(331);
				match(IDENTIFIER);
				setState(332);
				match(T__4);
				setState(333);
				expressionList();
				setState(334);
				match(T__5);
				}
				break;
			case 6:
				{
				setState(336);
				match(T__18);
				setState(338);
				_la = _input.LA(1);
				if (_la==T__2) {
					{
					setState(337);
					match(T__2);
					}
				}

				setState(340);
				nestedNameSpecifier();
				setState(342);
				_la = _input.LA(1);
				if (_la==T__1) {
					{
					setState(341);
					template();
					}
				}

				setState(344);
				templateId();
				setState(345);
				match(T__4);
				setState(346);
				expressionList();
				setState(347);
				match(T__5);
				}
				break;
			case 7:
				{
				setState(349);
				simpleTypeSpecifier();
				setState(350);
				match(T__4);
				setState(351);
				expressionList();
				setState(352);
				match(T__5);
				}
				break;
			case 8:
				{
				setState(354);
				match(T__19);
				setState(355);
				match(T__20);
				setState(356);
				typeId();
				setState(357);
				match(T__21);
				setState(358);
				match(T__4);
				setState(359);
				expression(0);
				setState(360);
				match(T__5);
				}
				break;
			case 9:
				{
				setState(362);
				match(T__22);
				setState(363);
				match(T__20);
				setState(364);
				typeId();
				setState(365);
				match(T__21);
				setState(366);
				match(T__4);
				setState(367);
				expression(0);
				setState(368);
				match(T__5);
				}
				break;
			case 10:
				{
				setState(370);
				match(T__23);
				setState(371);
				match(T__20);
				setState(372);
				typeId();
				setState(373);
				match(T__21);
				setState(374);
				match(T__4);
				setState(375);
				expression(0);
				setState(376);
				match(T__5);
				}
				break;
			case 11:
				{
				setState(378);
				match(T__24);
				setState(379);
				match(T__20);
				setState(380);
				typeId();
				setState(381);
				match(T__21);
				setState(382);
				match(T__4);
				setState(383);
				expression(0);
				setState(384);
				match(T__5);
				}
				break;
			case 12:
				{
				setState(386);
				match(T__25);
				setState(387);
				match(T__4);
				setState(388);
				expression(0);
				setState(389);
				match(T__5);
				}
				break;
			case 13:
				{
				setState(391);
				match(T__25);
				setState(392);
				match(T__4);
				setState(393);
				typeId();
				setState(394);
				match(T__5);
				}
				break;
			case 14:
				{
				setState(396);
				match(T__4);
				setState(397);
				typeId();
				setState(398);
				match(T__5);
				setState(399);
				expression(13);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(491);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(489);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,24,_ctx) ) {
					case 1:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(403);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(404);
						((ExpressionContext)_localctx).bop = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__12) | (1L << T__26) | (1L << T__27))) != 0)) ) {
							((ExpressionContext)_localctx).bop = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(405);
						expression(13);
						}
						break;
					case 2:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(406);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(407);
						((ExpressionContext)_localctx).bop = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__8 || _la==T__9) ) {
							((ExpressionContext)_localctx).bop = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(408);
						expression(12);
						}
						break;
					case 3:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(409);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(417);
						_errHandler.sync(this);
						switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
						case 1:
							{
							setState(410);
							match(T__20);
							setState(411);
							match(T__20);
							}
							break;
						case 2:
							{
							setState(412);
							match(T__21);
							setState(413);
							match(T__21);
							setState(414);
							match(T__21);
							}
							break;
						case 3:
							{
							setState(415);
							match(T__21);
							setState(416);
							match(T__21);
							}
							break;
						}
						setState(419);
						expression(11);
						}
						break;
					case 4:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(420);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(421);
						((ExpressionContext)_localctx).bop = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__20) | (1L << T__21) | (1L << T__28) | (1L << T__29))) != 0)) ) {
							((ExpressionContext)_localctx).bop = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(422);
						expression(10);
						}
						break;
					case 5:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(423);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(424);
						((ExpressionContext)_localctx).bop = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__30 || _la==T__31) ) {
							((ExpressionContext)_localctx).bop = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(425);
						expression(9);
						}
						break;
					case 6:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(426);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(427);
						((ExpressionContext)_localctx).bop = match(T__13);
						setState(428);
						expression(8);
						}
						break;
					case 7:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(429);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(430);
						((ExpressionContext)_localctx).bop = match(T__32);
						setState(431);
						expression(7);
						}
						break;
					case 8:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(432);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(433);
						((ExpressionContext)_localctx).bop = match(T__33);
						setState(434);
						expression(6);
						}
						break;
					case 9:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(435);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(436);
						((ExpressionContext)_localctx).bop = match(T__34);
						setState(437);
						expression(5);
						}
						break;
					case 10:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(438);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(439);
						((ExpressionContext)_localctx).bop = match(T__35);
						setState(440);
						expression(4);
						}
						break;
					case 11:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(441);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(442);
						((ExpressionContext)_localctx).bop = match(T__36);
						setState(443);
						expression(0);
						setState(444);
						match(T__37);
						setState(445);
						expression(3);
						}
						break;
					case 12:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(447);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(448);
						((ExpressionContext)_localctx).bop = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__38) | (1L << T__39) | (1L << T__40) | (1L << T__41) | (1L << T__42) | (1L << T__43) | (1L << T__44) | (1L << T__45) | (1L << T__46) | (1L << T__47) | (1L << T__48) | (1L << T__49))) != 0)) ) {
							((ExpressionContext)_localctx).bop = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(449);
						expression(1);
						}
						break;
					case 13:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(450);
						if (!(precpred(_ctx, 31))) throw new FailedPredicateException(this, "precpred(_ctx, 31)");
						setState(451);
						((ExpressionContext)_localctx).bop = match(T__6);
						setState(457);
						_errHandler.sync(this);
						switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
						case 1:
							{
							setState(453);
							_la = _input.LA(1);
							if (_la==T__1) {
								{
								setState(452);
								template();
								}
							}

							setState(455);
							idExpression();
							}
							break;
						case 2:
							{
							setState(456);
							pseudoDestructorName();
							}
							break;
						}
						}
						break;
					case 14:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(459);
						if (!(precpred(_ctx, 30))) throw new FailedPredicateException(this, "precpred(_ctx, 30)");
						setState(460);
						((ExpressionContext)_localctx).bop = match(T__2);
						setState(466);
						_errHandler.sync(this);
						switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
						case 1:
							{
							setState(462);
							_la = _input.LA(1);
							if (_la==T__1) {
								{
								setState(461);
								template();
								}
							}

							setState(464);
							idExpression();
							}
							break;
						case 2:
							{
							setState(465);
							pseudoDestructorName();
							}
							break;
						}
						}
						break;
					case 15:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(468);
						if (!(precpred(_ctx, 29))) throw new FailedPredicateException(this, "precpred(_ctx, 29)");
						setState(469);
						((ExpressionContext)_localctx).bop = match(T__7);
						setState(475);
						_errHandler.sync(this);
						switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
						case 1:
							{
							setState(471);
							_la = _input.LA(1);
							if (_la==T__1) {
								{
								setState(470);
								template();
								}
							}

							setState(473);
							idExpression();
							}
							break;
						case 2:
							{
							setState(474);
							pseudoDestructorName();
							}
							break;
						}
						}
						break;
					case 16:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(477);
						if (!(precpred(_ctx, 27))) throw new FailedPredicateException(this, "precpred(_ctx, 27)");
						setState(478);
						match(T__15);
						setState(479);
						expressionList();
						setState(480);
						match(T__16);
						}
						break;
					case 17:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(482);
						if (!(precpred(_ctx, 26))) throw new FailedPredicateException(this, "precpred(_ctx, 26)");
						setState(483);
						match(T__4);
						setState(484);
						expressionList();
						setState(485);
						match(T__5);
						}
						break;
					case 18:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(487);
						if (!(precpred(_ctx, 20))) throw new FailedPredicateException(this, "precpred(_ctx, 20)");
						setState(488);
						((ExpressionContext)_localctx).postfix = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__10 || _la==T__11) ) {
							((ExpressionContext)_localctx).postfix = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						}
						break;
					}
					} 
				}
				setState(493);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class ExpressionListContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ExpressionListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterExpressionList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitExpressionList(this);
		}
	}

	public final ExpressionListContext expressionList() throws RecognitionException {
		ExpressionListContext _localctx = new ExpressionListContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_expressionList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(494);
			expression(0);
			setState(499);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__50) {
				{
				{
				setState(495);
				match(T__50);
				setState(496);
				expression(0);
				}
				}
				setState(501);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PseudoDestructorNameContext extends ParserRuleContext {
		public List<TypeNameContext> typeName() {
			return getRuleContexts(TypeNameContext.class);
		}
		public TypeNameContext typeName(int i) {
			return getRuleContext(TypeNameContext.class,i);
		}
		public NestedNameSpecifierContext nestedNameSpecifier() {
			return getRuleContext(NestedNameSpecifierContext.class,0);
		}
		public TemplateIdContext templateId() {
			return getRuleContext(TemplateIdContext.class,0);
		}
		public PseudoDestructorNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pseudoDestructorName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterPseudoDestructorName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitPseudoDestructorName(this);
		}
	}

	public final PseudoDestructorNameContext pseudoDestructorName() throws RecognitionException {
		PseudoDestructorNameContext _localctx = new PseudoDestructorNameContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_pseudoDestructorName);
		int _la;
		try {
			setState(531);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,32,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(503);
				_la = _input.LA(1);
				if (_la==T__2) {
					{
					setState(502);
					match(T__2);
					}
				}

				setState(506);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,28,_ctx) ) {
				case 1:
					{
					setState(505);
					nestedNameSpecifier();
					}
					break;
				}
				setState(508);
				typeName();
				setState(509);
				match(T__2);
				setState(510);
				match(T__0);
				setState(511);
				typeName();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(514);
				_la = _input.LA(1);
				if (_la==T__2) {
					{
					setState(513);
					match(T__2);
					}
				}

				setState(516);
				nestedNameSpecifier();
				setState(517);
				match(T__1);
				setState(518);
				templateId();
				setState(519);
				match(T__2);
				setState(520);
				match(T__0);
				setState(521);
				typeName();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(524);
				_la = _input.LA(1);
				if (_la==T__2) {
					{
					setState(523);
					match(T__2);
					}
				}

				setState(527);
				_la = _input.LA(1);
				if (_la==IDENTIFIER) {
					{
					setState(526);
					nestedNameSpecifier();
					}
				}

				setState(529);
				match(T__0);
				setState(530);
				typeName();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NewExpressionContext extends ParserRuleContext {
		public NewTypeIdContext newTypeId() {
			return getRuleContext(NewTypeIdContext.class,0);
		}
		public NewPlacementContext newPlacement() {
			return getRuleContext(NewPlacementContext.class,0);
		}
		public NewInitializerContext newInitializer() {
			return getRuleContext(NewInitializerContext.class,0);
		}
		public TypeIdContext typeId() {
			return getRuleContext(TypeIdContext.class,0);
		}
		public NewExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_newExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterNewExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitNewExpression(this);
		}
	}

	public final NewExpressionContext newExpression() throws RecognitionException {
		NewExpressionContext _localctx = new NewExpressionContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_newExpression);
		int _la;
		try {
			setState(557);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,39,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(534);
				_la = _input.LA(1);
				if (_la==T__2) {
					{
					setState(533);
					match(T__2);
					}
				}

				setState(536);
				match(T__51);
				setState(538);
				_la = _input.LA(1);
				if (_la==T__4) {
					{
					setState(537);
					newPlacement();
					}
				}

				setState(540);
				newTypeId();
				setState(542);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,35,_ctx) ) {
				case 1:
					{
					setState(541);
					newInitializer();
					}
					break;
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(545);
				_la = _input.LA(1);
				if (_la==T__2) {
					{
					setState(544);
					match(T__2);
					}
				}

				setState(547);
				match(T__51);
				setState(549);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,37,_ctx) ) {
				case 1:
					{
					setState(548);
					newPlacement();
					}
					break;
				}
				setState(551);
				match(T__4);
				setState(552);
				typeId();
				setState(553);
				match(T__5);
				setState(555);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,38,_ctx) ) {
				case 1:
					{
					setState(554);
					newInitializer();
					}
					break;
				}
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NewPlacementContext extends ParserRuleContext {
		public ExpressionListContext expressionList() {
			return getRuleContext(ExpressionListContext.class,0);
		}
		public NewPlacementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_newPlacement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterNewPlacement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitNewPlacement(this);
		}
	}

	public final NewPlacementContext newPlacement() throws RecognitionException {
		NewPlacementContext _localctx = new NewPlacementContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_newPlacement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(559);
			match(T__4);
			setState(560);
			expressionList();
			setState(561);
			match(T__5);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NewTypeIdContext extends ParserRuleContext {
		public List<TypeSpecifierContext> typeSpecifier() {
			return getRuleContexts(TypeSpecifierContext.class);
		}
		public TypeSpecifierContext typeSpecifier(int i) {
			return getRuleContext(TypeSpecifierContext.class,i);
		}
		public NewDeclaratorContext newDeclarator() {
			return getRuleContext(NewDeclaratorContext.class,0);
		}
		public NewTypeIdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_newTypeId; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterNewTypeId(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitNewTypeId(this);
		}
	}

	public final NewTypeIdContext newTypeId() throws RecognitionException {
		NewTypeIdContext _localctx = new NewTypeIdContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_newTypeId);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(564); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(563);
					typeSpecifier();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(566); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,40,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			setState(569);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,41,_ctx) ) {
			case 1:
				{
				setState(568);
				newDeclarator(0);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NewDeclaratorContext extends ParserRuleContext {
		public PtrOperatorContext ptrOperator() {
			return getRuleContext(PtrOperatorContext.class,0);
		}
		public NewDeclaratorContext newDeclarator() {
			return getRuleContext(NewDeclaratorContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public NewDeclaratorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_newDeclarator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterNewDeclarator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitNewDeclarator(this);
		}
	}

	public final NewDeclaratorContext newDeclarator() throws RecognitionException {
		return newDeclarator(0);
	}

	private NewDeclaratorContext newDeclarator(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		NewDeclaratorContext _localctx = new NewDeclaratorContext(_ctx, _parentState);
		NewDeclaratorContext _prevctx = _localctx;
		int _startState = 46;
		enterRecursionRule(_localctx, 46, RULE_newDeclarator, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(579);
			switch (_input.LA(1)) {
			case T__2:
			case T__12:
			case T__13:
			case IDENTIFIER:
				{
				setState(572);
				ptrOperator();
				setState(573);
				newDeclarator(3);
				}
				break;
			case T__15:
				{
				setState(575);
				match(T__15);
				setState(576);
				expression(0);
				setState(577);
				match(T__16);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(588);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,43,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new NewDeclaratorContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_newDeclarator);
					setState(581);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(582);
					match(T__15);
					setState(583);
					expression(0);
					setState(584);
					match(T__16);
					}
					} 
				}
				setState(590);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,43,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class NewInitializerContext extends ParserRuleContext {
		public ExpressionListContext expressionList() {
			return getRuleContext(ExpressionListContext.class,0);
		}
		public NewInitializerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_newInitializer; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterNewInitializer(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitNewInitializer(this);
		}
	}

	public final NewInitializerContext newInitializer() throws RecognitionException {
		NewInitializerContext _localctx = new NewInitializerContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_newInitializer);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(591);
			match(T__4);
			setState(593);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__2) | (1L << T__3) | (1L << T__4) | (1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__51) | (1L << T__52))) != 0) || ((((_la - 80)) & ~0x3f) == 0 && ((1L << (_la - 80)) & ((1L << (T__79 - 80)) | (1L << (T__80 - 80)) | (1L << (T__81 - 80)) | (1L << (T__82 - 80)) | (1L << (T__83 - 80)) | (1L << (T__84 - 80)) | (1L << (T__85 - 80)) | (1L << (T__86 - 80)) | (1L << (T__87 - 80)) | (1L << (T__88 - 80)) | (1L << (T__89 - 80)) | (1L << (T__103 - 80)) | (1L << (DECIMAL_LITERAL - 80)) | (1L << (HEX_LITERAL - 80)) | (1L << (OCT_LITERAL - 80)) | (1L << (BINARY_LITERAL - 80)) | (1L << (FLOAT_LITERAL - 80)) | (1L << (HEX_FLOAT_LITERAL - 80)) | (1L << (BOOL_LITERAL - 80)) | (1L << (CHAR_LITERAL - 80)) | (1L << (STRING_LITERAL - 80)) | (1L << (NULL_LITERAL - 80)) | (1L << (IDENTIFIER - 80)))) != 0)) {
				{
				setState(592);
				expressionList();
				}
			}

			setState(595);
			match(T__5);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeleteExpressionContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public DeleteExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_deleteExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterDeleteExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitDeleteExpression(this);
		}
	}

	public final DeleteExpressionContext deleteExpression() throws RecognitionException {
		DeleteExpressionContext _localctx = new DeleteExpressionContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_deleteExpression);
		int _la;
		try {
			setState(609);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,47,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(598);
				_la = _input.LA(1);
				if (_la==T__2) {
					{
					setState(597);
					match(T__2);
					}
				}

				setState(600);
				match(T__52);
				setState(601);
				expression(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(603);
				_la = _input.LA(1);
				if (_la==T__2) {
					{
					setState(602);
					match(T__2);
					}
				}

				setState(605);
				match(T__52);
				setState(606);
				match(T__15);
				setState(607);
				match(T__16);
				setState(608);
				expression(0);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PmExpressionContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public PmExpressionContext pmExpression() {
			return getRuleContext(PmExpressionContext.class,0);
		}
		public PmExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pmExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterPmExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitPmExpression(this);
		}
	}

	public final PmExpressionContext pmExpression() throws RecognitionException {
		return pmExpression(0);
	}

	private PmExpressionContext pmExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		PmExpressionContext _localctx = new PmExpressionContext(_ctx, _parentState);
		PmExpressionContext _prevctx = _localctx;
		int _startState = 52;
		enterRecursionRule(_localctx, 52, RULE_pmExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(612);
			expression(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(622);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,49,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(620);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,48,_ctx) ) {
					case 1:
						{
						_localctx = new PmExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_pmExpression);
						setState(614);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(615);
						match(T__53);
						setState(616);
						expression(0);
						}
						break;
					case 2:
						{
						_localctx = new PmExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_pmExpression);
						setState(617);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(618);
						match(T__12);
						setState(619);
						expression(0);
						}
						break;
					}
					} 
				}
				setState(624);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,49,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public LabeledStatementContext labeledStatement() {
			return getRuleContext(LabeledStatementContext.class,0);
		}
		public ExpressionStatementContext expressionStatement() {
			return getRuleContext(ExpressionStatementContext.class,0);
		}
		public CompoundStatementContext compoundStatement() {
			return getRuleContext(CompoundStatementContext.class,0);
		}
		public SelectionStatementContext selectionStatement() {
			return getRuleContext(SelectionStatementContext.class,0);
		}
		public IterationStatementContext iterationStatement() {
			return getRuleContext(IterationStatementContext.class,0);
		}
		public JumpStatementContext jumpStatement() {
			return getRuleContext(JumpStatementContext.class,0);
		}
		public BlockDeclarationContext blockDeclaration() {
			return getRuleContext(BlockDeclarationContext.class,0);
		}
		public TryBlockContext tryBlock() {
			return getRuleContext(TryBlockContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitStatement(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_statement);
		try {
			setState(633);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,50,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(625);
				labeledStatement();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(626);
				expressionStatement();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(627);
				compoundStatement();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(628);
				selectionStatement();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(629);
				iterationStatement();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(630);
				jumpStatement();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(631);
				blockDeclaration();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(632);
				tryBlock();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LabeledStatementContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(CElsaParser.IDENTIFIER, 0); }
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public LabeledStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_labeledStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterLabeledStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitLabeledStatement(this);
		}
	}

	public final LabeledStatementContext labeledStatement() throws RecognitionException {
		LabeledStatementContext _localctx = new LabeledStatementContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_labeledStatement);
		try {
			setState(646);
			switch (_input.LA(1)) {
			case IDENTIFIER:
				enterOuterAlt(_localctx, 1);
				{
				setState(635);
				match(IDENTIFIER);
				setState(636);
				match(T__37);
				setState(637);
				statement();
				}
				break;
			case T__54:
				enterOuterAlt(_localctx, 2);
				{
				setState(638);
				match(T__54);
				setState(639);
				expression(0);
				setState(640);
				match(T__37);
				setState(641);
				statement();
				}
				break;
			case T__55:
				enterOuterAlt(_localctx, 3);
				{
				setState(643);
				match(T__55);
				setState(644);
				match(T__37);
				setState(645);
				statement();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionStatementContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ExpressionStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterExpressionStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitExpressionStatement(this);
		}
	}

	public final ExpressionStatementContext expressionStatement() throws RecognitionException {
		ExpressionStatementContext _localctx = new ExpressionStatementContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_expressionStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(648);
			expression(0);
			setState(649);
			match(T__56);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CompoundStatementContext extends ParserRuleContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public CompoundStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compoundStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterCompoundStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitCompoundStatement(this);
		}
	}

	public final CompoundStatementContext compoundStatement() throws RecognitionException {
		CompoundStatementContext _localctx = new CompoundStatementContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_compoundStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(651);
			match(T__57);
			setState(655);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__2) | (1L << T__3) | (1L << T__4) | (1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__51) | (1L << T__52) | (1L << T__54) | (1L << T__55) | (1L << T__56) | (1L << T__57) | (1L << T__59) | (1L << T__61) | (1L << T__62))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (T__63 - 64)) | (1L << (T__64 - 64)) | (1L << (T__65 - 64)) | (1L << (T__66 - 64)) | (1L << (T__67 - 64)) | (1L << (T__68 - 64)) | (1L << (T__69 - 64)) | (1L << (T__70 - 64)) | (1L << (T__71 - 64)) | (1L << (T__72 - 64)) | (1L << (T__73 - 64)) | (1L << (T__74 - 64)) | (1L << (T__75 - 64)) | (1L << (T__76 - 64)) | (1L << (T__77 - 64)) | (1L << (T__78 - 64)) | (1L << (T__79 - 64)) | (1L << (T__80 - 64)) | (1L << (T__81 - 64)) | (1L << (T__82 - 64)) | (1L << (T__83 - 64)) | (1L << (T__84 - 64)) | (1L << (T__85 - 64)) | (1L << (T__86 - 64)) | (1L << (T__87 - 64)) | (1L << (T__88 - 64)) | (1L << (T__89 - 64)) | (1L << (T__90 - 64)) | (1L << (T__91 - 64)) | (1L << (T__92 - 64)) | (1L << (T__93 - 64)) | (1L << (T__94 - 64)) | (1L << (T__95 - 64)) | (1L << (T__97 - 64)) | (1L << (T__98 - 64)) | (1L << (T__99 - 64)) | (1L << (T__103 - 64)) | (1L << (T__106 - 64)) | (1L << (DECIMAL_LITERAL - 64)) | (1L << (HEX_LITERAL - 64)) | (1L << (OCT_LITERAL - 64)) | (1L << (BINARY_LITERAL - 64)) | (1L << (FLOAT_LITERAL - 64)) | (1L << (HEX_FLOAT_LITERAL - 64)) | (1L << (BOOL_LITERAL - 64)) | (1L << (CHAR_LITERAL - 64)) | (1L << (STRING_LITERAL - 64)) | (1L << (NULL_LITERAL - 64)) | (1L << (IDENTIFIER - 64)))) != 0)) {
				{
				{
				setState(652);
				statement();
				}
				}
				setState(657);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(658);
			match(T__58);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SelectionStatementContext extends ParserRuleContext {
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public SelectionStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_selectionStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterSelectionStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitSelectionStatement(this);
		}
	}

	public final SelectionStatementContext selectionStatement() throws RecognitionException {
		SelectionStatementContext _localctx = new SelectionStatementContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_selectionStatement);
		try {
			setState(680);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,53,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(660);
				match(T__59);
				setState(661);
				match(T__4);
				setState(662);
				condition();
				setState(663);
				match(T__5);
				setState(664);
				statement();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(666);
				match(T__59);
				setState(667);
				match(T__4);
				setState(668);
				condition();
				setState(669);
				match(T__5);
				setState(670);
				statement();
				setState(671);
				match(T__60);
				setState(672);
				statement();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(674);
				match(T__61);
				setState(675);
				match(T__4);
				setState(676);
				condition();
				setState(677);
				match(T__5);
				setState(678);
				statement();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConditionContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public DeclaratorContext declarator() {
			return getRuleContext(DeclaratorContext.class,0);
		}
		public List<TypeSpecifierContext> typeSpecifier() {
			return getRuleContexts(TypeSpecifierContext.class);
		}
		public TypeSpecifierContext typeSpecifier(int i) {
			return getRuleContext(TypeSpecifierContext.class,i);
		}
		public ConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_condition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterCondition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitCondition(this);
		}
	}

	public final ConditionContext condition() throws RecognitionException {
		ConditionContext _localctx = new ConditionContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_condition);
		try {
			int _alt;
			setState(692);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,55,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(682);
				expression(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(684); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(683);
						typeSpecifier();
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(686); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,54,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(688);
				declarator();
				setState(689);
				match(T__38);
				setState(690);
				expression(0);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IterationStatementContext extends ParserRuleContext {
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ForInitStatementContext forInitStatement() {
			return getRuleContext(ForInitStatementContext.class,0);
		}
		public IterationStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_iterationStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterIterationStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitIterationStatement(this);
		}
	}

	public final IterationStatementContext iterationStatement() throws RecognitionException {
		IterationStatementContext _localctx = new IterationStatementContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_iterationStatement);
		int _la;
		try {
			setState(721);
			switch (_input.LA(1)) {
			case T__62:
				enterOuterAlt(_localctx, 1);
				{
				setState(694);
				match(T__62);
				setState(695);
				match(T__4);
				setState(696);
				condition();
				setState(697);
				match(T__5);
				setState(698);
				statement();
				}
				break;
			case T__63:
				enterOuterAlt(_localctx, 2);
				{
				setState(700);
				match(T__63);
				setState(701);
				statement();
				setState(702);
				match(T__62);
				setState(703);
				match(T__4);
				setState(704);
				expression(0);
				setState(705);
				match(T__5);
				setState(706);
				match(T__56);
				}
				break;
			case T__64:
				enterOuterAlt(_localctx, 3);
				{
				setState(708);
				match(T__64);
				setState(709);
				match(T__4);
				setState(710);
				forInitStatement();
				setState(712);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__2) | (1L << T__3) | (1L << T__4) | (1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__51) | (1L << T__52))) != 0) || ((((_la - 80)) & ~0x3f) == 0 && ((1L << (_la - 80)) & ((1L << (T__79 - 80)) | (1L << (T__80 - 80)) | (1L << (T__81 - 80)) | (1L << (T__82 - 80)) | (1L << (T__83 - 80)) | (1L << (T__84 - 80)) | (1L << (T__85 - 80)) | (1L << (T__86 - 80)) | (1L << (T__87 - 80)) | (1L << (T__88 - 80)) | (1L << (T__89 - 80)) | (1L << (T__90 - 80)) | (1L << (T__94 - 80)) | (1L << (T__95 - 80)) | (1L << (T__97 - 80)) | (1L << (T__98 - 80)) | (1L << (T__99 - 80)) | (1L << (T__103 - 80)) | (1L << (DECIMAL_LITERAL - 80)) | (1L << (HEX_LITERAL - 80)) | (1L << (OCT_LITERAL - 80)) | (1L << (BINARY_LITERAL - 80)) | (1L << (FLOAT_LITERAL - 80)) | (1L << (HEX_FLOAT_LITERAL - 80)) | (1L << (BOOL_LITERAL - 80)) | (1L << (CHAR_LITERAL - 80)) | (1L << (STRING_LITERAL - 80)) | (1L << (NULL_LITERAL - 80)) | (1L << (IDENTIFIER - 80)))) != 0)) {
					{
					setState(711);
					condition();
					}
				}

				setState(714);
				match(T__56);
				setState(716);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__2) | (1L << T__3) | (1L << T__4) | (1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__51) | (1L << T__52))) != 0) || ((((_la - 80)) & ~0x3f) == 0 && ((1L << (_la - 80)) & ((1L << (T__79 - 80)) | (1L << (T__80 - 80)) | (1L << (T__81 - 80)) | (1L << (T__82 - 80)) | (1L << (T__83 - 80)) | (1L << (T__84 - 80)) | (1L << (T__85 - 80)) | (1L << (T__86 - 80)) | (1L << (T__87 - 80)) | (1L << (T__88 - 80)) | (1L << (T__89 - 80)) | (1L << (T__103 - 80)) | (1L << (DECIMAL_LITERAL - 80)) | (1L << (HEX_LITERAL - 80)) | (1L << (OCT_LITERAL - 80)) | (1L << (BINARY_LITERAL - 80)) | (1L << (FLOAT_LITERAL - 80)) | (1L << (HEX_FLOAT_LITERAL - 80)) | (1L << (BOOL_LITERAL - 80)) | (1L << (CHAR_LITERAL - 80)) | (1L << (STRING_LITERAL - 80)) | (1L << (NULL_LITERAL - 80)) | (1L << (IDENTIFIER - 80)))) != 0)) {
					{
					setState(715);
					expression(0);
					}
				}

				setState(718);
				match(T__5);
				setState(719);
				statement();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ForInitStatementContext extends ParserRuleContext {
		public ExpressionStatementContext expressionStatement() {
			return getRuleContext(ExpressionStatementContext.class,0);
		}
		public SimpleDeclarationContext simpleDeclaration() {
			return getRuleContext(SimpleDeclarationContext.class,0);
		}
		public ForInitStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forInitStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterForInitStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitForInitStatement(this);
		}
	}

	public final ForInitStatementContext forInitStatement() throws RecognitionException {
		ForInitStatementContext _localctx = new ForInitStatementContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_forInitStatement);
		try {
			setState(725);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,59,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(723);
				expressionStatement();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(724);
				simpleDeclaration();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class JumpStatementContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(CElsaParser.IDENTIFIER, 0); }
		public JumpStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jumpStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterJumpStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitJumpStatement(this);
		}
	}

	public final JumpStatementContext jumpStatement() throws RecognitionException {
		JumpStatementContext _localctx = new JumpStatementContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_jumpStatement);
		int _la;
		try {
			setState(739);
			switch (_input.LA(1)) {
			case T__65:
				enterOuterAlt(_localctx, 1);
				{
				setState(727);
				match(T__65);
				setState(728);
				match(T__56);
				}
				break;
			case T__66:
				enterOuterAlt(_localctx, 2);
				{
				setState(729);
				match(T__66);
				setState(730);
				match(T__56);
				}
				break;
			case T__67:
				enterOuterAlt(_localctx, 3);
				{
				setState(731);
				match(T__67);
				setState(733);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__2) | (1L << T__3) | (1L << T__4) | (1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__51) | (1L << T__52))) != 0) || ((((_la - 80)) & ~0x3f) == 0 && ((1L << (_la - 80)) & ((1L << (T__79 - 80)) | (1L << (T__80 - 80)) | (1L << (T__81 - 80)) | (1L << (T__82 - 80)) | (1L << (T__83 - 80)) | (1L << (T__84 - 80)) | (1L << (T__85 - 80)) | (1L << (T__86 - 80)) | (1L << (T__87 - 80)) | (1L << (T__88 - 80)) | (1L << (T__89 - 80)) | (1L << (T__103 - 80)) | (1L << (DECIMAL_LITERAL - 80)) | (1L << (HEX_LITERAL - 80)) | (1L << (OCT_LITERAL - 80)) | (1L << (BINARY_LITERAL - 80)) | (1L << (FLOAT_LITERAL - 80)) | (1L << (HEX_FLOAT_LITERAL - 80)) | (1L << (BOOL_LITERAL - 80)) | (1L << (CHAR_LITERAL - 80)) | (1L << (STRING_LITERAL - 80)) | (1L << (NULL_LITERAL - 80)) | (1L << (IDENTIFIER - 80)))) != 0)) {
					{
					setState(732);
					expression(0);
					}
				}

				setState(735);
				match(T__56);
				}
				break;
			case T__68:
				enterOuterAlt(_localctx, 4);
				{
				setState(736);
				match(T__68);
				setState(737);
				match(IDENTIFIER);
				setState(738);
				match(T__56);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlockDeclarationContext extends ParserRuleContext {
		public SimpleDeclarationContext simpleDeclaration() {
			return getRuleContext(SimpleDeclarationContext.class,0);
		}
		public AsmDefinitionContext asmDefinition() {
			return getRuleContext(AsmDefinitionContext.class,0);
		}
		public NamespaceAliasDefinitionContext namespaceAliasDefinition() {
			return getRuleContext(NamespaceAliasDefinitionContext.class,0);
		}
		public UsingDeclarationContext usingDeclaration() {
			return getRuleContext(UsingDeclarationContext.class,0);
		}
		public UsingDirectiveContext usingDirective() {
			return getRuleContext(UsingDirectiveContext.class,0);
		}
		public BlockDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_blockDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterBlockDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitBlockDeclaration(this);
		}
	}

	public final BlockDeclarationContext blockDeclaration() throws RecognitionException {
		BlockDeclarationContext _localctx = new BlockDeclarationContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_blockDeclaration);
		try {
			setState(746);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,62,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(741);
				simpleDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(742);
				asmDefinition();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(743);
				namespaceAliasDefinition();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(744);
				usingDeclaration();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(745);
				usingDirective();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SimpleDeclarationContext extends ParserRuleContext {
		public List<DeclSpecifierContext> declSpecifier() {
			return getRuleContexts(DeclSpecifierContext.class);
		}
		public DeclSpecifierContext declSpecifier(int i) {
			return getRuleContext(DeclSpecifierContext.class,i);
		}
		public InitDeclaratorListContext initDeclaratorList() {
			return getRuleContext(InitDeclaratorListContext.class,0);
		}
		public SimpleDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simpleDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterSimpleDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitSimpleDeclaration(this);
		}
	}

	public final SimpleDeclarationContext simpleDeclaration() throws RecognitionException {
		SimpleDeclarationContext _localctx = new SimpleDeclarationContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_simpleDeclaration);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(751);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,63,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(748);
					declSpecifier();
					}
					} 
				}
				setState(753);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,63,_ctx);
			}
			setState(755);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__2) | (1L << T__4))) != 0) || _la==T__103 || _la==IDENTIFIER) {
				{
				setState(754);
				initDeclaratorList();
				}
			}

			setState(757);
			match(T__56);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InitDeclaratorListContext extends ParserRuleContext {
		public List<InitDeclaratorContext> initDeclarator() {
			return getRuleContexts(InitDeclaratorContext.class);
		}
		public InitDeclaratorContext initDeclarator(int i) {
			return getRuleContext(InitDeclaratorContext.class,i);
		}
		public InitDeclaratorListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_initDeclaratorList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterInitDeclaratorList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitInitDeclaratorList(this);
		}
	}

	public final InitDeclaratorListContext initDeclaratorList() throws RecognitionException {
		InitDeclaratorListContext _localctx = new InitDeclaratorListContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_initDeclaratorList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(759);
			initDeclarator();
			setState(764);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__50) {
				{
				{
				setState(760);
				match(T__50);
				setState(761);
				initDeclarator();
				}
				}
				setState(766);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InitDeclaratorContext extends ParserRuleContext {
		public DeclaratorContext declarator() {
			return getRuleContext(DeclaratorContext.class,0);
		}
		public InitializerContext initializer() {
			return getRuleContext(InitializerContext.class,0);
		}
		public InitDeclaratorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_initDeclarator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterInitDeclarator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitInitDeclarator(this);
		}
	}

	public final InitDeclaratorContext initDeclarator() throws RecognitionException {
		InitDeclaratorContext _localctx = new InitDeclaratorContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_initDeclarator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(767);
			declarator();
			setState(769);
			_la = _input.LA(1);
			if (_la==T__4 || _la==T__38) {
				{
				setState(768);
				initializer();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclSpecifierContext extends ParserRuleContext {
		public StorageClassSpecifierContext storageClassSpecifier() {
			return getRuleContext(StorageClassSpecifierContext.class,0);
		}
		public TypeSpecifierContext typeSpecifier() {
			return getRuleContext(TypeSpecifierContext.class,0);
		}
		public FunctionSpecifierContext functionSpecifier() {
			return getRuleContext(FunctionSpecifierContext.class,0);
		}
		public DeclSpecifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declSpecifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterDeclSpecifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitDeclSpecifier(this);
		}
	}

	public final DeclSpecifierContext declSpecifier() throws RecognitionException {
		DeclSpecifierContext _localctx = new DeclSpecifierContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_declSpecifier);
		try {
			setState(776);
			switch (_input.LA(1)) {
			case T__71:
			case T__72:
			case T__73:
			case T__74:
			case T__75:
				enterOuterAlt(_localctx, 1);
				{
				setState(771);
				storageClassSpecifier();
				}
				break;
			case T__2:
			case T__18:
			case T__79:
			case T__80:
			case T__81:
			case T__82:
			case T__83:
			case T__84:
			case T__85:
			case T__86:
			case T__87:
			case T__88:
			case T__89:
			case T__90:
			case T__94:
			case T__95:
			case T__97:
			case T__98:
			case T__99:
			case IDENTIFIER:
				enterOuterAlt(_localctx, 2);
				{
				setState(772);
				typeSpecifier();
				}
				break;
			case T__76:
			case T__77:
			case T__78:
				enterOuterAlt(_localctx, 3);
				{
				setState(773);
				functionSpecifier();
				}
				break;
			case T__69:
				enterOuterAlt(_localctx, 4);
				{
				setState(774);
				match(T__69);
				}
				break;
			case T__70:
				enterOuterAlt(_localctx, 5);
				{
				setState(775);
				match(T__70);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StorageClassSpecifierContext extends ParserRuleContext {
		public StorageClassSpecifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_storageClassSpecifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterStorageClassSpecifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitStorageClassSpecifier(this);
		}
	}

	public final StorageClassSpecifierContext storageClassSpecifier() throws RecognitionException {
		StorageClassSpecifierContext _localctx = new StorageClassSpecifierContext(_ctx, getState());
		enterRule(_localctx, 82, RULE_storageClassSpecifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(778);
			_la = _input.LA(1);
			if ( !(((((_la - 72)) & ~0x3f) == 0 && ((1L << (_la - 72)) & ((1L << (T__71 - 72)) | (1L << (T__72 - 72)) | (1L << (T__73 - 72)) | (1L << (T__74 - 72)) | (1L << (T__75 - 72)))) != 0)) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionSpecifierContext extends ParserRuleContext {
		public FunctionSpecifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionSpecifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterFunctionSpecifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitFunctionSpecifier(this);
		}
	}

	public final FunctionSpecifierContext functionSpecifier() throws RecognitionException {
		FunctionSpecifierContext _localctx = new FunctionSpecifierContext(_ctx, getState());
		enterRule(_localctx, 84, RULE_functionSpecifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(780);
			_la = _input.LA(1);
			if ( !(((((_la - 77)) & ~0x3f) == 0 && ((1L << (_la - 77)) & ((1L << (T__76 - 77)) | (1L << (T__77 - 77)) | (1L << (T__78 - 77)))) != 0)) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeSpecifierContext extends ParserRuleContext {
		public SimpleTypeSpecifierContext simpleTypeSpecifier() {
			return getRuleContext(SimpleTypeSpecifierContext.class,0);
		}
		public ClassSpecifierContext classSpecifier() {
			return getRuleContext(ClassSpecifierContext.class,0);
		}
		public EnumSpecifierContext enumSpecifier() {
			return getRuleContext(EnumSpecifierContext.class,0);
		}
		public ElaboratedTypeSpecifierContext elaboratedTypeSpecifier() {
			return getRuleContext(ElaboratedTypeSpecifierContext.class,0);
		}
		public CvQualifierContext cvQualifier() {
			return getRuleContext(CvQualifierContext.class,0);
		}
		public TypeSpecifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeSpecifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterTypeSpecifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitTypeSpecifier(this);
		}
	}

	public final TypeSpecifierContext typeSpecifier() throws RecognitionException {
		TypeSpecifierContext _localctx = new TypeSpecifierContext(_ctx, getState());
		enterRule(_localctx, 86, RULE_typeSpecifier);
		try {
			setState(787);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,68,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(782);
				simpleTypeSpecifier();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(783);
				classSpecifier();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(784);
				enumSpecifier();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(785);
				elaboratedTypeSpecifier();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(786);
				cvQualifier();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SimpleTypeSpecifierContext extends ParserRuleContext {
		public TypeNameContext typeName() {
			return getRuleContext(TypeNameContext.class,0);
		}
		public NestedNameSpecifierContext nestedNameSpecifier() {
			return getRuleContext(NestedNameSpecifierContext.class,0);
		}
		public TemplateIdContext templateId() {
			return getRuleContext(TemplateIdContext.class,0);
		}
		public SimpleTypeSpecifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simpleTypeSpecifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterSimpleTypeSpecifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitSimpleTypeSpecifier(this);
		}
	}

	public final SimpleTypeSpecifierContext simpleTypeSpecifier() throws RecognitionException {
		SimpleTypeSpecifierContext _localctx = new SimpleTypeSpecifierContext(_ctx, getState());
		enterRule(_localctx, 88, RULE_simpleTypeSpecifier);
		int _la;
		try {
			setState(814);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,72,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(790);
				_la = _input.LA(1);
				if (_la==T__2) {
					{
					setState(789);
					match(T__2);
					}
				}

				setState(793);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,70,_ctx) ) {
				case 1:
					{
					setState(792);
					nestedNameSpecifier();
					}
					break;
				}
				setState(795);
				typeName();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(797);
				_la = _input.LA(1);
				if (_la==T__2) {
					{
					setState(796);
					match(T__2);
					}
				}

				setState(799);
				nestedNameSpecifier();
				setState(800);
				match(T__1);
				setState(801);
				templateId();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(803);
				match(T__79);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(804);
				match(T__80);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(805);
				match(T__81);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(806);
				match(T__82);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(807);
				match(T__83);
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(808);
				match(T__84);
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(809);
				match(T__85);
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(810);
				match(T__86);
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(811);
				match(T__87);
				}
				break;
			case 12:
				enterOuterAlt(_localctx, 12);
				{
				setState(812);
				match(T__88);
				}
				break;
			case 13:
				enterOuterAlt(_localctx, 13);
				{
				setState(813);
				match(T__89);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeNameContext extends ParserRuleContext {
		public ClassNameContext className() {
			return getRuleContext(ClassNameContext.class,0);
		}
		public EnumNameContext enumName() {
			return getRuleContext(EnumNameContext.class,0);
		}
		public TypedefNameContext typedefName() {
			return getRuleContext(TypedefNameContext.class,0);
		}
		public TypeNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterTypeName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitTypeName(this);
		}
	}

	public final TypeNameContext typeName() throws RecognitionException {
		TypeNameContext _localctx = new TypeNameContext(_ctx, getState());
		enterRule(_localctx, 90, RULE_typeName);
		try {
			setState(819);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,73,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(816);
				className();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(817);
				enumName();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(818);
				typedefName();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ElaboratedTypeSpecifierContext extends ParserRuleContext {
		public ClassKeyContext classKey() {
			return getRuleContext(ClassKeyContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(CElsaParser.IDENTIFIER, 0); }
		public NestedNameSpecifierContext nestedNameSpecifier() {
			return getRuleContext(NestedNameSpecifierContext.class,0);
		}
		public TemplateIdContext templateId() {
			return getRuleContext(TemplateIdContext.class,0);
		}
		public TemplateContext template() {
			return getRuleContext(TemplateContext.class,0);
		}
		public ElaboratedTypeSpecifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elaboratedTypeSpecifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterElaboratedTypeSpecifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitElaboratedTypeSpecifier(this);
		}
	}

	public final ElaboratedTypeSpecifierContext elaboratedTypeSpecifier() throws RecognitionException {
		ElaboratedTypeSpecifierContext _localctx = new ElaboratedTypeSpecifierContext(_ctx, getState());
		enterRule(_localctx, 92, RULE_elaboratedTypeSpecifier);
		int _la;
		try {
			setState(855);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,81,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(821);
				classKey();
				setState(823);
				_la = _input.LA(1);
				if (_la==T__2) {
					{
					setState(822);
					match(T__2);
					}
				}

				setState(826);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,75,_ctx) ) {
				case 1:
					{
					setState(825);
					nestedNameSpecifier();
					}
					break;
				}
				setState(828);
				match(IDENTIFIER);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(830);
				match(T__90);
				setState(832);
				_la = _input.LA(1);
				if (_la==T__2) {
					{
					setState(831);
					match(T__2);
					}
				}

				setState(835);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,77,_ctx) ) {
				case 1:
					{
					setState(834);
					nestedNameSpecifier();
					}
					break;
				}
				setState(837);
				match(IDENTIFIER);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(838);
				match(T__18);
				setState(840);
				_la = _input.LA(1);
				if (_la==T__2) {
					{
					setState(839);
					match(T__2);
					}
				}

				setState(842);
				nestedNameSpecifier();
				setState(843);
				match(IDENTIFIER);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(845);
				match(T__18);
				setState(847);
				_la = _input.LA(1);
				if (_la==T__2) {
					{
					setState(846);
					match(T__2);
					}
				}

				setState(849);
				nestedNameSpecifier();
				setState(851);
				_la = _input.LA(1);
				if (_la==T__1) {
					{
					setState(850);
					template();
					}
				}

				setState(853);
				templateId();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EnumSpecifierContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(CElsaParser.IDENTIFIER, 0); }
		public EnumeratorListContext enumeratorList() {
			return getRuleContext(EnumeratorListContext.class,0);
		}
		public EnumSpecifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enumSpecifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterEnumSpecifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitEnumSpecifier(this);
		}
	}

	public final EnumSpecifierContext enumSpecifier() throws RecognitionException {
		EnumSpecifierContext _localctx = new EnumSpecifierContext(_ctx, getState());
		enterRule(_localctx, 94, RULE_enumSpecifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(857);
			match(T__90);
			setState(859);
			_la = _input.LA(1);
			if (_la==IDENTIFIER) {
				{
				setState(858);
				match(IDENTIFIER);
				}
			}

			setState(861);
			match(T__57);
			setState(863);
			_la = _input.LA(1);
			if (_la==IDENTIFIER) {
				{
				setState(862);
				enumeratorList();
				}
			}

			setState(865);
			match(T__58);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EnumeratorListContext extends ParserRuleContext {
		public List<EnumeratorDefinitionContext> enumeratorDefinition() {
			return getRuleContexts(EnumeratorDefinitionContext.class);
		}
		public EnumeratorDefinitionContext enumeratorDefinition(int i) {
			return getRuleContext(EnumeratorDefinitionContext.class,i);
		}
		public EnumeratorListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enumeratorList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterEnumeratorList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitEnumeratorList(this);
		}
	}

	public final EnumeratorListContext enumeratorList() throws RecognitionException {
		EnumeratorListContext _localctx = new EnumeratorListContext(_ctx, getState());
		enterRule(_localctx, 96, RULE_enumeratorList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(867);
			enumeratorDefinition();
			setState(872);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__50) {
				{
				{
				setState(868);
				match(T__50);
				setState(869);
				enumeratorDefinition();
				}
				}
				setState(874);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EnumeratorDefinitionContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(CElsaParser.IDENTIFIER, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public EnumeratorDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enumeratorDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterEnumeratorDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitEnumeratorDefinition(this);
		}
	}

	public final EnumeratorDefinitionContext enumeratorDefinition() throws RecognitionException {
		EnumeratorDefinitionContext _localctx = new EnumeratorDefinitionContext(_ctx, getState());
		enterRule(_localctx, 98, RULE_enumeratorDefinition);
		try {
			setState(879);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,85,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(875);
				match(IDENTIFIER);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(876);
				match(IDENTIFIER);
				setState(877);
				match(T__38);
				setState(878);
				expression(0);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NamespaceDefinitionContext extends ParserRuleContext {
		public NamedNamespaceDefinitionContext namedNamespaceDefinition() {
			return getRuleContext(NamedNamespaceDefinitionContext.class,0);
		}
		public UnnamedNamespaceDefinitionContext unnamedNamespaceDefinition() {
			return getRuleContext(UnnamedNamespaceDefinitionContext.class,0);
		}
		public NamespaceDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_namespaceDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterNamespaceDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitNamespaceDefinition(this);
		}
	}

	public final NamespaceDefinitionContext namespaceDefinition() throws RecognitionException {
		NamespaceDefinitionContext _localctx = new NamespaceDefinitionContext(_ctx, getState());
		enterRule(_localctx, 100, RULE_namespaceDefinition);
		try {
			setState(883);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,86,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(881);
				namedNamespaceDefinition();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(882);
				unnamedNamespaceDefinition();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NamedNamespaceDefinitionContext extends ParserRuleContext {
		public OriginalNamespaceDefinitionContext originalNamespaceDefinition() {
			return getRuleContext(OriginalNamespaceDefinitionContext.class,0);
		}
		public ExtensionNamespaceDefinitionContext extensionNamespaceDefinition() {
			return getRuleContext(ExtensionNamespaceDefinitionContext.class,0);
		}
		public NamedNamespaceDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_namedNamespaceDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterNamedNamespaceDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitNamedNamespaceDefinition(this);
		}
	}

	public final NamedNamespaceDefinitionContext namedNamespaceDefinition() throws RecognitionException {
		NamedNamespaceDefinitionContext _localctx = new NamedNamespaceDefinitionContext(_ctx, getState());
		enterRule(_localctx, 102, RULE_namedNamespaceDefinition);
		try {
			setState(887);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,87,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(885);
				originalNamespaceDefinition();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(886);
				extensionNamespaceDefinition();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OriginalNamespaceDefinitionContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(CElsaParser.IDENTIFIER, 0); }
		public List<DeclarationContext> declaration() {
			return getRuleContexts(DeclarationContext.class);
		}
		public DeclarationContext declaration(int i) {
			return getRuleContext(DeclarationContext.class,i);
		}
		public OriginalNamespaceDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_originalNamespaceDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterOriginalNamespaceDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitOriginalNamespaceDefinition(this);
		}
	}

	public final OriginalNamespaceDefinitionContext originalNamespaceDefinition() throws RecognitionException {
		OriginalNamespaceDefinitionContext _localctx = new OriginalNamespaceDefinitionContext(_ctx, getState());
		enterRule(_localctx, 104, RULE_originalNamespaceDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(889);
			match(T__91);
			setState(890);
			match(IDENTIFIER);
			setState(891);
			match(T__57);
			setState(895);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << T__2) | (1L << T__4) | (1L << T__18) | (1L << T__56))) != 0) || ((((_la - 70)) & ~0x3f) == 0 && ((1L << (_la - 70)) & ((1L << (T__69 - 70)) | (1L << (T__70 - 70)) | (1L << (T__71 - 70)) | (1L << (T__72 - 70)) | (1L << (T__73 - 70)) | (1L << (T__74 - 70)) | (1L << (T__75 - 70)) | (1L << (T__76 - 70)) | (1L << (T__77 - 70)) | (1L << (T__78 - 70)) | (1L << (T__79 - 70)) | (1L << (T__80 - 70)) | (1L << (T__81 - 70)) | (1L << (T__82 - 70)) | (1L << (T__83 - 70)) | (1L << (T__84 - 70)) | (1L << (T__85 - 70)) | (1L << (T__86 - 70)) | (1L << (T__87 - 70)) | (1L << (T__88 - 70)) | (1L << (T__89 - 70)) | (1L << (T__90 - 70)) | (1L << (T__91 - 70)) | (1L << (T__92 - 70)) | (1L << (T__93 - 70)) | (1L << (T__94 - 70)) | (1L << (T__95 - 70)) | (1L << (T__97 - 70)) | (1L << (T__98 - 70)) | (1L << (T__99 - 70)) | (1L << (T__103 - 70)) | (1L << (IDENTIFIER - 70)))) != 0)) {
				{
				{
				setState(892);
				declaration();
				}
				}
				setState(897);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(898);
			match(T__58);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExtensionNamespaceDefinitionContext extends ParserRuleContext {
		public OriginalNamespaceNameContext originalNamespaceName() {
			return getRuleContext(OriginalNamespaceNameContext.class,0);
		}
		public List<DeclarationContext> declaration() {
			return getRuleContexts(DeclarationContext.class);
		}
		public DeclarationContext declaration(int i) {
			return getRuleContext(DeclarationContext.class,i);
		}
		public ExtensionNamespaceDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_extensionNamespaceDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterExtensionNamespaceDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitExtensionNamespaceDefinition(this);
		}
	}

	public final ExtensionNamespaceDefinitionContext extensionNamespaceDefinition() throws RecognitionException {
		ExtensionNamespaceDefinitionContext _localctx = new ExtensionNamespaceDefinitionContext(_ctx, getState());
		enterRule(_localctx, 106, RULE_extensionNamespaceDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(900);
			match(T__91);
			setState(901);
			originalNamespaceName();
			setState(902);
			match(T__57);
			setState(906);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << T__2) | (1L << T__4) | (1L << T__18) | (1L << T__56))) != 0) || ((((_la - 70)) & ~0x3f) == 0 && ((1L << (_la - 70)) & ((1L << (T__69 - 70)) | (1L << (T__70 - 70)) | (1L << (T__71 - 70)) | (1L << (T__72 - 70)) | (1L << (T__73 - 70)) | (1L << (T__74 - 70)) | (1L << (T__75 - 70)) | (1L << (T__76 - 70)) | (1L << (T__77 - 70)) | (1L << (T__78 - 70)) | (1L << (T__79 - 70)) | (1L << (T__80 - 70)) | (1L << (T__81 - 70)) | (1L << (T__82 - 70)) | (1L << (T__83 - 70)) | (1L << (T__84 - 70)) | (1L << (T__85 - 70)) | (1L << (T__86 - 70)) | (1L << (T__87 - 70)) | (1L << (T__88 - 70)) | (1L << (T__89 - 70)) | (1L << (T__90 - 70)) | (1L << (T__91 - 70)) | (1L << (T__92 - 70)) | (1L << (T__93 - 70)) | (1L << (T__94 - 70)) | (1L << (T__95 - 70)) | (1L << (T__97 - 70)) | (1L << (T__98 - 70)) | (1L << (T__99 - 70)) | (1L << (T__103 - 70)) | (1L << (IDENTIFIER - 70)))) != 0)) {
				{
				{
				setState(903);
				declaration();
				}
				}
				setState(908);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(909);
			match(T__58);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UnnamedNamespaceDefinitionContext extends ParserRuleContext {
		public List<DeclarationContext> declaration() {
			return getRuleContexts(DeclarationContext.class);
		}
		public DeclarationContext declaration(int i) {
			return getRuleContext(DeclarationContext.class,i);
		}
		public UnnamedNamespaceDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unnamedNamespaceDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterUnnamedNamespaceDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitUnnamedNamespaceDefinition(this);
		}
	}

	public final UnnamedNamespaceDefinitionContext unnamedNamespaceDefinition() throws RecognitionException {
		UnnamedNamespaceDefinitionContext _localctx = new UnnamedNamespaceDefinitionContext(_ctx, getState());
		enterRule(_localctx, 108, RULE_unnamedNamespaceDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(911);
			match(T__91);
			setState(912);
			match(T__57);
			setState(916);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << T__2) | (1L << T__4) | (1L << T__18) | (1L << T__56))) != 0) || ((((_la - 70)) & ~0x3f) == 0 && ((1L << (_la - 70)) & ((1L << (T__69 - 70)) | (1L << (T__70 - 70)) | (1L << (T__71 - 70)) | (1L << (T__72 - 70)) | (1L << (T__73 - 70)) | (1L << (T__74 - 70)) | (1L << (T__75 - 70)) | (1L << (T__76 - 70)) | (1L << (T__77 - 70)) | (1L << (T__78 - 70)) | (1L << (T__79 - 70)) | (1L << (T__80 - 70)) | (1L << (T__81 - 70)) | (1L << (T__82 - 70)) | (1L << (T__83 - 70)) | (1L << (T__84 - 70)) | (1L << (T__85 - 70)) | (1L << (T__86 - 70)) | (1L << (T__87 - 70)) | (1L << (T__88 - 70)) | (1L << (T__89 - 70)) | (1L << (T__90 - 70)) | (1L << (T__91 - 70)) | (1L << (T__92 - 70)) | (1L << (T__93 - 70)) | (1L << (T__94 - 70)) | (1L << (T__95 - 70)) | (1L << (T__97 - 70)) | (1L << (T__98 - 70)) | (1L << (T__99 - 70)) | (1L << (T__103 - 70)) | (1L << (IDENTIFIER - 70)))) != 0)) {
				{
				{
				setState(913);
				declaration();
				}
				}
				setState(918);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(919);
			match(T__58);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NamespaceAliasDefinitionContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(CElsaParser.IDENTIFIER, 0); }
		public QualifiedNamespaceSpecifierContext qualifiedNamespaceSpecifier() {
			return getRuleContext(QualifiedNamespaceSpecifierContext.class,0);
		}
		public NamespaceAliasDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_namespaceAliasDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterNamespaceAliasDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitNamespaceAliasDefinition(this);
		}
	}

	public final NamespaceAliasDefinitionContext namespaceAliasDefinition() throws RecognitionException {
		NamespaceAliasDefinitionContext _localctx = new NamespaceAliasDefinitionContext(_ctx, getState());
		enterRule(_localctx, 110, RULE_namespaceAliasDefinition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(921);
			match(T__91);
			setState(922);
			match(IDENTIFIER);
			setState(923);
			match(T__38);
			setState(924);
			qualifiedNamespaceSpecifier();
			setState(925);
			match(T__56);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class QualifiedNamespaceSpecifierContext extends ParserRuleContext {
		public NamespaceNameContext namespaceName() {
			return getRuleContext(NamespaceNameContext.class,0);
		}
		public NestedNameSpecifierContext nestedNameSpecifier() {
			return getRuleContext(NestedNameSpecifierContext.class,0);
		}
		public QualifiedNamespaceSpecifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_qualifiedNamespaceSpecifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterQualifiedNamespaceSpecifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitQualifiedNamespaceSpecifier(this);
		}
	}

	public final QualifiedNamespaceSpecifierContext qualifiedNamespaceSpecifier() throws RecognitionException {
		QualifiedNamespaceSpecifierContext _localctx = new QualifiedNamespaceSpecifierContext(_ctx, getState());
		enterRule(_localctx, 112, RULE_qualifiedNamespaceSpecifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(928);
			_la = _input.LA(1);
			if (_la==T__2) {
				{
				setState(927);
				match(T__2);
				}
			}

			setState(931);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,92,_ctx) ) {
			case 1:
				{
				setState(930);
				nestedNameSpecifier();
				}
				break;
			}
			setState(933);
			namespaceName();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UsingDeclarationContext extends ParserRuleContext {
		public NestedNameSpecifierContext nestedNameSpecifier() {
			return getRuleContext(NestedNameSpecifierContext.class,0);
		}
		public UnqualifiedIdContext unqualifiedId() {
			return getRuleContext(UnqualifiedIdContext.class,0);
		}
		public UsingDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_usingDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterUsingDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitUsingDeclaration(this);
		}
	}

	public final UsingDeclarationContext usingDeclaration() throws RecognitionException {
		UsingDeclarationContext _localctx = new UsingDeclarationContext(_ctx, getState());
		enterRule(_localctx, 114, RULE_usingDeclaration);
		int _la;
		try {
			setState(951);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,95,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(935);
				match(T__92);
				setState(937);
				_la = _input.LA(1);
				if (_la==T__18) {
					{
					setState(936);
					match(T__18);
					}
				}

				setState(940);
				_la = _input.LA(1);
				if (_la==T__2) {
					{
					setState(939);
					match(T__2);
					}
				}

				setState(942);
				nestedNameSpecifier();
				setState(943);
				unqualifiedId();
				setState(944);
				match(T__56);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(946);
				match(T__92);
				setState(947);
				match(T__2);
				setState(948);
				unqualifiedId();
				setState(949);
				match(T__56);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UsingDirectiveContext extends ParserRuleContext {
		public NamespaceNameContext namespaceName() {
			return getRuleContext(NamespaceNameContext.class,0);
		}
		public NestedNameSpecifierContext nestedNameSpecifier() {
			return getRuleContext(NestedNameSpecifierContext.class,0);
		}
		public UsingDirectiveContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_usingDirective; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterUsingDirective(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitUsingDirective(this);
		}
	}

	public final UsingDirectiveContext usingDirective() throws RecognitionException {
		UsingDirectiveContext _localctx = new UsingDirectiveContext(_ctx, getState());
		enterRule(_localctx, 116, RULE_usingDirective);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(953);
			match(T__92);
			setState(954);
			match(T__91);
			setState(956);
			_la = _input.LA(1);
			if (_la==T__2) {
				{
				setState(955);
				match(T__2);
				}
			}

			setState(959);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,97,_ctx) ) {
			case 1:
				{
				setState(958);
				nestedNameSpecifier();
				}
				break;
			}
			setState(961);
			namespaceName();
			setState(962);
			match(T__56);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AsmDefinitionContext extends ParserRuleContext {
		public TerminalNode STRING_LITERAL() { return getToken(CElsaParser.STRING_LITERAL, 0); }
		public AsmDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_asmDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterAsmDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitAsmDefinition(this);
		}
	}

	public final AsmDefinitionContext asmDefinition() throws RecognitionException {
		AsmDefinitionContext _localctx = new AsmDefinitionContext(_ctx, getState());
		enterRule(_localctx, 118, RULE_asmDefinition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(964);
			match(T__93);
			setState(965);
			match(T__4);
			setState(966);
			match(STRING_LITERAL);
			setState(967);
			match(T__5);
			setState(968);
			match(T__56);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LinkageSpecificationContext extends ParserRuleContext {
		public TerminalNode STRING_LITERAL() { return getToken(CElsaParser.STRING_LITERAL, 0); }
		public List<DeclarationContext> declaration() {
			return getRuleContexts(DeclarationContext.class);
		}
		public DeclarationContext declaration(int i) {
			return getRuleContext(DeclarationContext.class,i);
		}
		public LinkageSpecificationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_linkageSpecification; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterLinkageSpecification(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitLinkageSpecification(this);
		}
	}

	public final LinkageSpecificationContext linkageSpecification() throws RecognitionException {
		LinkageSpecificationContext _localctx = new LinkageSpecificationContext(_ctx, getState());
		enterRule(_localctx, 120, RULE_linkageSpecification);
		int _la;
		try {
			setState(983);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,99,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(970);
				match(T__74);
				setState(971);
				match(STRING_LITERAL);
				setState(972);
				match(T__57);
				setState(976);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << T__2) | (1L << T__4) | (1L << T__18) | (1L << T__56))) != 0) || ((((_la - 70)) & ~0x3f) == 0 && ((1L << (_la - 70)) & ((1L << (T__69 - 70)) | (1L << (T__70 - 70)) | (1L << (T__71 - 70)) | (1L << (T__72 - 70)) | (1L << (T__73 - 70)) | (1L << (T__74 - 70)) | (1L << (T__75 - 70)) | (1L << (T__76 - 70)) | (1L << (T__77 - 70)) | (1L << (T__78 - 70)) | (1L << (T__79 - 70)) | (1L << (T__80 - 70)) | (1L << (T__81 - 70)) | (1L << (T__82 - 70)) | (1L << (T__83 - 70)) | (1L << (T__84 - 70)) | (1L << (T__85 - 70)) | (1L << (T__86 - 70)) | (1L << (T__87 - 70)) | (1L << (T__88 - 70)) | (1L << (T__89 - 70)) | (1L << (T__90 - 70)) | (1L << (T__91 - 70)) | (1L << (T__92 - 70)) | (1L << (T__93 - 70)) | (1L << (T__94 - 70)) | (1L << (T__95 - 70)) | (1L << (T__97 - 70)) | (1L << (T__98 - 70)) | (1L << (T__99 - 70)) | (1L << (T__103 - 70)) | (1L << (IDENTIFIER - 70)))) != 0)) {
					{
					{
					setState(973);
					declaration();
					}
					}
					setState(978);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(979);
				match(T__58);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(980);
				match(T__74);
				setState(981);
				match(STRING_LITERAL);
				setState(982);
				declaration();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclaratorContext extends ParserRuleContext {
		public ParameterDeclarationClauseContext parameterDeclarationClause() {
			return getRuleContext(ParameterDeclarationClauseContext.class,0);
		}
		public List<DeclaratorIdContext> declaratorId() {
			return getRuleContexts(DeclaratorIdContext.class);
		}
		public DeclaratorIdContext declaratorId(int i) {
			return getRuleContext(DeclaratorIdContext.class,i);
		}
		public CvQualifierSeqContext cvQualifierSeq() {
			return getRuleContext(CvQualifierSeqContext.class,0);
		}
		public ExceptionSpecificationContext exceptionSpecification() {
			return getRuleContext(ExceptionSpecificationContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public DeclaratorContext declarator() {
			return getRuleContext(DeclaratorContext.class,0);
		}
		public DeclaratorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declarator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterDeclarator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitDeclarator(this);
		}
	}

	public final DeclaratorContext declarator() throws RecognitionException {
		DeclaratorContext _localctx = new DeclaratorContext(_ctx, getState());
		enterRule(_localctx, 122, RULE_declarator);
		int _la;
		try {
			setState(1014);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,105,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(986); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(985);
					declaratorId();
					}
					}
					setState(988); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__0 || _la==T__2 || _la==T__103 || _la==IDENTIFIER );
				setState(990);
				match(T__4);
				setState(991);
				parameterDeclarationClause();
				setState(992);
				match(T__5);
				setState(994);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,101,_ctx) ) {
				case 1:
					{
					setState(993);
					cvQualifierSeq();
					}
					break;
				}
				setState(997);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,102,_ctx) ) {
				case 1:
					{
					setState(996);
					exceptionSpecification();
					}
					break;
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1000); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(999);
					declaratorId();
					}
					}
					setState(1002); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__0 || _la==T__2 || _la==T__103 || _la==IDENTIFIER );
				setState(1004);
				match(T__15);
				setState(1006);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__2) | (1L << T__3) | (1L << T__4) | (1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__51) | (1L << T__52))) != 0) || ((((_la - 80)) & ~0x3f) == 0 && ((1L << (_la - 80)) & ((1L << (T__79 - 80)) | (1L << (T__80 - 80)) | (1L << (T__81 - 80)) | (1L << (T__82 - 80)) | (1L << (T__83 - 80)) | (1L << (T__84 - 80)) | (1L << (T__85 - 80)) | (1L << (T__86 - 80)) | (1L << (T__87 - 80)) | (1L << (T__88 - 80)) | (1L << (T__89 - 80)) | (1L << (T__103 - 80)) | (1L << (DECIMAL_LITERAL - 80)) | (1L << (HEX_LITERAL - 80)) | (1L << (OCT_LITERAL - 80)) | (1L << (BINARY_LITERAL - 80)) | (1L << (FLOAT_LITERAL - 80)) | (1L << (HEX_FLOAT_LITERAL - 80)) | (1L << (BOOL_LITERAL - 80)) | (1L << (CHAR_LITERAL - 80)) | (1L << (STRING_LITERAL - 80)) | (1L << (NULL_LITERAL - 80)) | (1L << (IDENTIFIER - 80)))) != 0)) {
					{
					setState(1005);
					expression(0);
					}
				}

				setState(1008);
				match(T__16);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1010);
				match(T__4);
				setState(1011);
				declarator();
				setState(1012);
				match(T__5);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PtrOperatorContext extends ParserRuleContext {
		public CvQualifierSeqContext cvQualifierSeq() {
			return getRuleContext(CvQualifierSeqContext.class,0);
		}
		public NestedNameSpecifierContext nestedNameSpecifier() {
			return getRuleContext(NestedNameSpecifierContext.class,0);
		}
		public PtrOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ptrOperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterPtrOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitPtrOperator(this);
		}
	}

	public final PtrOperatorContext ptrOperator() throws RecognitionException {
		PtrOperatorContext _localctx = new PtrOperatorContext(_ctx, getState());
		enterRule(_localctx, 124, RULE_ptrOperator);
		int _la;
		try {
			setState(1029);
			switch (_input.LA(1)) {
			case T__12:
				enterOuterAlt(_localctx, 1);
				{
				setState(1016);
				match(T__12);
				setState(1018);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,106,_ctx) ) {
				case 1:
					{
					setState(1017);
					cvQualifierSeq();
					}
					break;
				}
				}
				break;
			case T__13:
				enterOuterAlt(_localctx, 2);
				{
				setState(1020);
				match(T__13);
				}
				break;
			case T__2:
			case IDENTIFIER:
				enterOuterAlt(_localctx, 3);
				{
				setState(1022);
				_la = _input.LA(1);
				if (_la==T__2) {
					{
					setState(1021);
					match(T__2);
					}
				}

				setState(1024);
				nestedNameSpecifier();
				setState(1025);
				match(T__12);
				setState(1027);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,108,_ctx) ) {
				case 1:
					{
					setState(1026);
					cvQualifierSeq();
					}
					break;
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CvQualifierSeqContext extends ParserRuleContext {
		public List<CvQualifierContext> cvQualifier() {
			return getRuleContexts(CvQualifierContext.class);
		}
		public CvQualifierContext cvQualifier(int i) {
			return getRuleContext(CvQualifierContext.class,i);
		}
		public CvQualifierSeqContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cvQualifierSeq; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterCvQualifierSeq(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitCvQualifierSeq(this);
		}
	}

	public final CvQualifierSeqContext cvQualifierSeq() throws RecognitionException {
		CvQualifierSeqContext _localctx = new CvQualifierSeqContext(_ctx, getState());
		enterRule(_localctx, 126, RULE_cvQualifierSeq);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1031);
			cvQualifier();
			setState(1035);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,110,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(1032);
					cvQualifier();
					}
					} 
				}
				setState(1037);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,110,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CvQualifierContext extends ParserRuleContext {
		public CvQualifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cvQualifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterCvQualifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitCvQualifier(this);
		}
	}

	public final CvQualifierContext cvQualifier() throws RecognitionException {
		CvQualifierContext _localctx = new CvQualifierContext(_ctx, getState());
		enterRule(_localctx, 128, RULE_cvQualifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1038);
			_la = _input.LA(1);
			if ( !(_la==T__94 || _la==T__95) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclaratorIdContext extends ParserRuleContext {
		public IdExpressionContext idExpression() {
			return getRuleContext(IdExpressionContext.class,0);
		}
		public TypeNameContext typeName() {
			return getRuleContext(TypeNameContext.class,0);
		}
		public NestedNameSpecifierContext nestedNameSpecifier() {
			return getRuleContext(NestedNameSpecifierContext.class,0);
		}
		public DeclaratorIdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaratorId; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterDeclaratorId(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitDeclaratorId(this);
		}
	}

	public final DeclaratorIdContext declaratorId() throws RecognitionException {
		DeclaratorIdContext _localctx = new DeclaratorIdContext(_ctx, getState());
		enterRule(_localctx, 130, RULE_declaratorId);
		int _la;
		try {
			setState(1048);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,113,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1040);
				idExpression();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1042);
				_la = _input.LA(1);
				if (_la==T__2) {
					{
					setState(1041);
					match(T__2);
					}
				}

				setState(1045);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,112,_ctx) ) {
				case 1:
					{
					setState(1044);
					nestedNameSpecifier();
					}
					break;
				}
				setState(1047);
				typeName();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeIdContext extends ParserRuleContext {
		public List<TypeSpecifierContext> typeSpecifier() {
			return getRuleContexts(TypeSpecifierContext.class);
		}
		public TypeSpecifierContext typeSpecifier(int i) {
			return getRuleContext(TypeSpecifierContext.class,i);
		}
		public DeclaratorContext declarator() {
			return getRuleContext(DeclaratorContext.class,0);
		}
		public TypeIdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeId; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterTypeId(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitTypeId(this);
		}
	}

	public final TypeIdContext typeId() throws RecognitionException {
		TypeIdContext _localctx = new TypeIdContext(_ctx, getState());
		enterRule(_localctx, 132, RULE_typeId);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1051); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(1050);
					typeSpecifier();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(1053); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,114,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			setState(1056);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,115,_ctx) ) {
			case 1:
				{
				setState(1055);
				declarator();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParameterDeclarationClauseContext extends ParserRuleContext {
		public List<ParameterDeclarationContext> parameterDeclaration() {
			return getRuleContexts(ParameterDeclarationContext.class);
		}
		public ParameterDeclarationContext parameterDeclaration(int i) {
			return getRuleContext(ParameterDeclarationContext.class,i);
		}
		public ParameterDeclarationClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameterDeclarationClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterParameterDeclarationClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitParameterDeclarationClause(this);
		}
	}

	public final ParameterDeclarationClauseContext parameterDeclarationClause() throws RecognitionException {
		ParameterDeclarationClauseContext _localctx = new ParameterDeclarationClauseContext(_ctx, getState());
		enterRule(_localctx, 134, RULE_parameterDeclarationClause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1058);
			parameterDeclaration();
			setState(1063);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__50) {
				{
				{
				setState(1059);
				match(T__50);
				setState(1060);
				parameterDeclaration();
				}
				}
				setState(1065);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(1067);
			_la = _input.LA(1);
			if (_la==T__96) {
				{
				setState(1066);
				match(T__96);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParameterDeclarationContext extends ParserRuleContext {
		public List<DeclSpecifierContext> declSpecifier() {
			return getRuleContexts(DeclSpecifierContext.class);
		}
		public DeclSpecifierContext declSpecifier(int i) {
			return getRuleContext(DeclSpecifierContext.class,i);
		}
		public DeclaratorContext declarator() {
			return getRuleContext(DeclaratorContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ParameterDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameterDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterParameterDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitParameterDeclaration(this);
		}
	}

	public final ParameterDeclarationContext parameterDeclaration() throws RecognitionException {
		ParameterDeclarationContext _localctx = new ParameterDeclarationContext(_ctx, getState());
		enterRule(_localctx, 136, RULE_parameterDeclaration);
		int _la;
		try {
			int _alt;
			setState(1088);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,122,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1070); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(1069);
						declSpecifier();
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(1072); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,118,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(1075);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__2) | (1L << T__4))) != 0) || _la==T__103 || _la==IDENTIFIER) {
					{
					setState(1074);
					declarator();
					}
				}

				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1078); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(1077);
						declSpecifier();
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(1080); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,120,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(1083);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__2) | (1L << T__4))) != 0) || _la==T__103 || _la==IDENTIFIER) {
					{
					setState(1082);
					declarator();
					}
				}

				setState(1085);
				match(T__38);
				setState(1086);
				expression(0);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionDefinitionContext extends ParserRuleContext {
		public DeclaratorContext declarator() {
			return getRuleContext(DeclaratorContext.class,0);
		}
		public FunctionBodyContext functionBody() {
			return getRuleContext(FunctionBodyContext.class,0);
		}
		public List<DeclSpecifierContext> declSpecifier() {
			return getRuleContexts(DeclSpecifierContext.class);
		}
		public DeclSpecifierContext declSpecifier(int i) {
			return getRuleContext(DeclSpecifierContext.class,i);
		}
		public CtorInitializerContext ctorInitializer() {
			return getRuleContext(CtorInitializerContext.class,0);
		}
		public FunctionTryBlockContext functionTryBlock() {
			return getRuleContext(FunctionTryBlockContext.class,0);
		}
		public FunctionDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterFunctionDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitFunctionDefinition(this);
		}
	}

	public final FunctionDefinitionContext functionDefinition() throws RecognitionException {
		FunctionDefinitionContext _localctx = new FunctionDefinitionContext(_ctx, getState());
		enterRule(_localctx, 138, RULE_functionDefinition);
		int _la;
		try {
			int _alt;
			setState(1111);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,126,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1093);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,123,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(1090);
						declSpecifier();
						}
						} 
					}
					setState(1095);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,123,_ctx);
				}
				setState(1096);
				declarator();
				setState(1098);
				_la = _input.LA(1);
				if (_la==T__37) {
					{
					setState(1097);
					ctorInitializer();
					}
				}

				setState(1100);
				functionBody();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1105);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,125,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(1102);
						declSpecifier();
						}
						} 
					}
					setState(1107);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,125,_ctx);
				}
				setState(1108);
				declarator();
				setState(1109);
				functionTryBlock();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionBodyContext extends ParserRuleContext {
		public CompoundStatementContext compoundStatement() {
			return getRuleContext(CompoundStatementContext.class,0);
		}
		public FunctionBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionBody; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterFunctionBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitFunctionBody(this);
		}
	}

	public final FunctionBodyContext functionBody() throws RecognitionException {
		FunctionBodyContext _localctx = new FunctionBodyContext(_ctx, getState());
		enterRule(_localctx, 140, RULE_functionBody);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1113);
			compoundStatement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InitializerContext extends ParserRuleContext {
		public InitializerClauseContext initializerClause() {
			return getRuleContext(InitializerClauseContext.class,0);
		}
		public ExpressionListContext expressionList() {
			return getRuleContext(ExpressionListContext.class,0);
		}
		public InitializerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_initializer; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterInitializer(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitInitializer(this);
		}
	}

	public final InitializerContext initializer() throws RecognitionException {
		InitializerContext _localctx = new InitializerContext(_ctx, getState());
		enterRule(_localctx, 142, RULE_initializer);
		try {
			setState(1121);
			switch (_input.LA(1)) {
			case T__38:
				enterOuterAlt(_localctx, 1);
				{
				setState(1115);
				match(T__38);
				setState(1116);
				initializerClause();
				}
				break;
			case T__4:
				enterOuterAlt(_localctx, 2);
				{
				setState(1117);
				match(T__4);
				setState(1118);
				expressionList();
				setState(1119);
				match(T__5);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InitializerClauseContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public InitializerListContext initializerList() {
			return getRuleContext(InitializerListContext.class,0);
		}
		public InitializerClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_initializerClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterInitializerClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitInitializerClause(this);
		}
	}

	public final InitializerClauseContext initializerClause() throws RecognitionException {
		InitializerClauseContext _localctx = new InitializerClauseContext(_ctx, getState());
		enterRule(_localctx, 144, RULE_initializerClause);
		int _la;
		try {
			setState(1133);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,129,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1123);
				expression(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1124);
				match(T__57);
				setState(1125);
				initializerList(0);
				setState(1127);
				_la = _input.LA(1);
				if (_la==T__50) {
					{
					setState(1126);
					match(T__50);
					}
				}

				setState(1129);
				match(T__58);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1131);
				match(T__57);
				setState(1132);
				match(T__58);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InitializerListContext extends ParserRuleContext {
		public InitializerClauseContext initializerClause() {
			return getRuleContext(InitializerClauseContext.class,0);
		}
		public InitializerListContext initializerList() {
			return getRuleContext(InitializerListContext.class,0);
		}
		public InitializerListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_initializerList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterInitializerList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitInitializerList(this);
		}
	}

	public final InitializerListContext initializerList() throws RecognitionException {
		return initializerList(0);
	}

	private InitializerListContext initializerList(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		InitializerListContext _localctx = new InitializerListContext(_ctx, _parentState);
		InitializerListContext _prevctx = _localctx;
		int _startState = 146;
		enterRecursionRule(_localctx, 146, RULE_initializerList, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(1136);
			initializerClause();
			}
			_ctx.stop = _input.LT(-1);
			setState(1143);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,130,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new InitializerListContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_initializerList);
					setState(1138);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(1139);
					match(T__50);
					setState(1140);
					initializerClause();
					}
					} 
				}
				setState(1145);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,130,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class ClassSpecifierContext extends ParserRuleContext {
		public ClassHeadContext classHead() {
			return getRuleContext(ClassHeadContext.class,0);
		}
		public MemberSpecificationContext memberSpecification() {
			return getRuleContext(MemberSpecificationContext.class,0);
		}
		public ClassSpecifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classSpecifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterClassSpecifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitClassSpecifier(this);
		}
	}

	public final ClassSpecifierContext classSpecifier() throws RecognitionException {
		ClassSpecifierContext _localctx = new ClassSpecifierContext(_ctx, getState());
		enterRule(_localctx, 148, RULE_classSpecifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1146);
			classHead();
			setState(1147);
			match(T__57);
			setState(1149);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << T__2) | (1L << T__4) | (1L << T__18) | (1L << T__37) | (1L << T__56))) != 0) || ((((_la - 70)) & ~0x3f) == 0 && ((1L << (_la - 70)) & ((1L << (T__69 - 70)) | (1L << (T__70 - 70)) | (1L << (T__71 - 70)) | (1L << (T__72 - 70)) | (1L << (T__73 - 70)) | (1L << (T__74 - 70)) | (1L << (T__75 - 70)) | (1L << (T__76 - 70)) | (1L << (T__77 - 70)) | (1L << (T__78 - 70)) | (1L << (T__79 - 70)) | (1L << (T__80 - 70)) | (1L << (T__81 - 70)) | (1L << (T__82 - 70)) | (1L << (T__83 - 70)) | (1L << (T__84 - 70)) | (1L << (T__85 - 70)) | (1L << (T__86 - 70)) | (1L << (T__87 - 70)) | (1L << (T__88 - 70)) | (1L << (T__89 - 70)) | (1L << (T__90 - 70)) | (1L << (T__92 - 70)) | (1L << (T__94 - 70)) | (1L << (T__95 - 70)) | (1L << (T__97 - 70)) | (1L << (T__98 - 70)) | (1L << (T__99 - 70)) | (1L << (T__100 - 70)) | (1L << (T__101 - 70)) | (1L << (T__102 - 70)) | (1L << (T__103 - 70)) | (1L << (IDENTIFIER - 70)))) != 0)) {
				{
				setState(1148);
				memberSpecification();
				}
			}

			setState(1151);
			match(T__58);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassHeadContext extends ParserRuleContext {
		public ClassKeyContext classKey() {
			return getRuleContext(ClassKeyContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(CElsaParser.IDENTIFIER, 0); }
		public BaseClauseContext baseClause() {
			return getRuleContext(BaseClauseContext.class,0);
		}
		public NestedNameSpecifierContext nestedNameSpecifier() {
			return getRuleContext(NestedNameSpecifierContext.class,0);
		}
		public TemplateIdContext templateId() {
			return getRuleContext(TemplateIdContext.class,0);
		}
		public ClassHeadContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classHead; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterClassHead(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitClassHead(this);
		}
	}

	public final ClassHeadContext classHead() throws RecognitionException {
		ClassHeadContext _localctx = new ClassHeadContext(_ctx, getState());
		enterRule(_localctx, 150, RULE_classHead);
		int _la;
		try {
			setState(1174);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,137,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1153);
				classKey();
				setState(1155);
				_la = _input.LA(1);
				if (_la==IDENTIFIER) {
					{
					setState(1154);
					match(IDENTIFIER);
					}
				}

				setState(1158);
				_la = _input.LA(1);
				if (_la==T__37) {
					{
					setState(1157);
					baseClause();
					}
				}

				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1160);
				classKey();
				setState(1161);
				nestedNameSpecifier();
				setState(1162);
				match(IDENTIFIER);
				setState(1164);
				_la = _input.LA(1);
				if (_la==T__37) {
					{
					setState(1163);
					baseClause();
					}
				}

				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1166);
				classKey();
				setState(1168);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,135,_ctx) ) {
				case 1:
					{
					setState(1167);
					nestedNameSpecifier();
					}
					break;
				}
				setState(1170);
				templateId();
				setState(1172);
				_la = _input.LA(1);
				if (_la==T__37) {
					{
					setState(1171);
					baseClause();
					}
				}

				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassKeyContext extends ParserRuleContext {
		public ClassKeyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classKey; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterClassKey(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitClassKey(this);
		}
	}

	public final ClassKeyContext classKey() throws RecognitionException {
		ClassKeyContext _localctx = new ClassKeyContext(_ctx, getState());
		enterRule(_localctx, 152, RULE_classKey);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1176);
			_la = _input.LA(1);
			if ( !(((((_la - 98)) & ~0x3f) == 0 && ((1L << (_la - 98)) & ((1L << (T__97 - 98)) | (1L << (T__98 - 98)) | (1L << (T__99 - 98)))) != 0)) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MemberSpecificationContext extends ParserRuleContext {
		public List<MemberDeclarationContext> memberDeclaration() {
			return getRuleContexts(MemberDeclarationContext.class);
		}
		public MemberDeclarationContext memberDeclaration(int i) {
			return getRuleContext(MemberDeclarationContext.class,i);
		}
		public AccessSpecifierContext accessSpecifier() {
			return getRuleContext(AccessSpecifierContext.class,0);
		}
		public MemberSpecificationContext memberSpecification() {
			return getRuleContext(MemberSpecificationContext.class,0);
		}
		public MemberSpecificationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_memberSpecification; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterMemberSpecification(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitMemberSpecification(this);
		}
	}

	public final MemberSpecificationContext memberSpecification() throws RecognitionException {
		MemberSpecificationContext _localctx = new MemberSpecificationContext(_ctx, getState());
		enterRule(_localctx, 154, RULE_memberSpecification);
		int _la;
		try {
			setState(1190);
			switch (_input.LA(1)) {
			case T__0:
			case T__1:
			case T__2:
			case T__4:
			case T__18:
			case T__37:
			case T__56:
			case T__69:
			case T__70:
			case T__71:
			case T__72:
			case T__73:
			case T__74:
			case T__75:
			case T__76:
			case T__77:
			case T__78:
			case T__79:
			case T__80:
			case T__81:
			case T__82:
			case T__83:
			case T__84:
			case T__85:
			case T__86:
			case T__87:
			case T__88:
			case T__89:
			case T__90:
			case T__92:
			case T__94:
			case T__95:
			case T__97:
			case T__98:
			case T__99:
			case T__103:
			case IDENTIFIER:
				enterOuterAlt(_localctx, 1);
				{
				setState(1178);
				memberDeclaration();
				setState(1182);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << T__2) | (1L << T__4) | (1L << T__18) | (1L << T__37) | (1L << T__56))) != 0) || ((((_la - 70)) & ~0x3f) == 0 && ((1L << (_la - 70)) & ((1L << (T__69 - 70)) | (1L << (T__70 - 70)) | (1L << (T__71 - 70)) | (1L << (T__72 - 70)) | (1L << (T__73 - 70)) | (1L << (T__74 - 70)) | (1L << (T__75 - 70)) | (1L << (T__76 - 70)) | (1L << (T__77 - 70)) | (1L << (T__78 - 70)) | (1L << (T__79 - 70)) | (1L << (T__80 - 70)) | (1L << (T__81 - 70)) | (1L << (T__82 - 70)) | (1L << (T__83 - 70)) | (1L << (T__84 - 70)) | (1L << (T__85 - 70)) | (1L << (T__86 - 70)) | (1L << (T__87 - 70)) | (1L << (T__88 - 70)) | (1L << (T__89 - 70)) | (1L << (T__90 - 70)) | (1L << (T__92 - 70)) | (1L << (T__94 - 70)) | (1L << (T__95 - 70)) | (1L << (T__97 - 70)) | (1L << (T__98 - 70)) | (1L << (T__99 - 70)) | (1L << (T__103 - 70)) | (1L << (IDENTIFIER - 70)))) != 0)) {
					{
					{
					setState(1179);
					memberDeclaration();
					}
					}
					setState(1184);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case T__100:
			case T__101:
			case T__102:
				enterOuterAlt(_localctx, 2);
				{
				setState(1185);
				accessSpecifier();
				setState(1186);
				match(T__37);
				setState(1188);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << T__2) | (1L << T__4) | (1L << T__18) | (1L << T__37) | (1L << T__56))) != 0) || ((((_la - 70)) & ~0x3f) == 0 && ((1L << (_la - 70)) & ((1L << (T__69 - 70)) | (1L << (T__70 - 70)) | (1L << (T__71 - 70)) | (1L << (T__72 - 70)) | (1L << (T__73 - 70)) | (1L << (T__74 - 70)) | (1L << (T__75 - 70)) | (1L << (T__76 - 70)) | (1L << (T__77 - 70)) | (1L << (T__78 - 70)) | (1L << (T__79 - 70)) | (1L << (T__80 - 70)) | (1L << (T__81 - 70)) | (1L << (T__82 - 70)) | (1L << (T__83 - 70)) | (1L << (T__84 - 70)) | (1L << (T__85 - 70)) | (1L << (T__86 - 70)) | (1L << (T__87 - 70)) | (1L << (T__88 - 70)) | (1L << (T__89 - 70)) | (1L << (T__90 - 70)) | (1L << (T__92 - 70)) | (1L << (T__94 - 70)) | (1L << (T__95 - 70)) | (1L << (T__97 - 70)) | (1L << (T__98 - 70)) | (1L << (T__99 - 70)) | (1L << (T__100 - 70)) | (1L << (T__101 - 70)) | (1L << (T__102 - 70)) | (1L << (T__103 - 70)) | (1L << (IDENTIFIER - 70)))) != 0)) {
					{
					setState(1187);
					memberSpecification();
					}
				}

				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MemberDeclarationContext extends ParserRuleContext {
		public FunctionDefinitionContext functionDefinition() {
			return getRuleContext(FunctionDefinitionContext.class,0);
		}
		public List<DeclSpecifierContext> declSpecifier() {
			return getRuleContexts(DeclSpecifierContext.class);
		}
		public DeclSpecifierContext declSpecifier(int i) {
			return getRuleContext(DeclSpecifierContext.class,i);
		}
		public MemberDeclaratorListContext memberDeclaratorList() {
			return getRuleContext(MemberDeclaratorListContext.class,0);
		}
		public NestedNameSpecifierContext nestedNameSpecifier() {
			return getRuleContext(NestedNameSpecifierContext.class,0);
		}
		public UnqualifiedIdContext unqualifiedId() {
			return getRuleContext(UnqualifiedIdContext.class,0);
		}
		public TemplateContext template() {
			return getRuleContext(TemplateContext.class,0);
		}
		public UsingDeclarationContext usingDeclaration() {
			return getRuleContext(UsingDeclarationContext.class,0);
		}
		public TemplateDeclarationContext templateDeclaration() {
			return getRuleContext(TemplateDeclarationContext.class,0);
		}
		public MemberDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_memberDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterMemberDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitMemberDeclaration(this);
		}
	}

	public final MemberDeclarationContext memberDeclaration() throws RecognitionException {
		MemberDeclarationContext _localctx = new MemberDeclarationContext(_ctx, getState());
		enterRule(_localctx, 156, RULE_memberDeclaration);
		int _la;
		try {
			int _alt;
			setState(1218);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,146,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1192);
				functionDefinition();
				setState(1194);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,141,_ctx) ) {
				case 1:
					{
					setState(1193);
					match(T__56);
					}
					break;
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1199);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,142,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(1196);
						declSpecifier();
						}
						} 
					}
					setState(1201);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,142,_ctx);
				}
				setState(1203);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__2) | (1L << T__4) | (1L << T__37))) != 0) || _la==T__103 || _la==IDENTIFIER) {
					{
					setState(1202);
					memberDeclaratorList();
					}
				}

				setState(1205);
				match(T__56);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1207);
				_la = _input.LA(1);
				if (_la==T__2) {
					{
					setState(1206);
					match(T__2);
					}
				}

				setState(1209);
				nestedNameSpecifier();
				setState(1211);
				_la = _input.LA(1);
				if (_la==T__1) {
					{
					setState(1210);
					template();
					}
				}

				setState(1213);
				unqualifiedId();
				setState(1214);
				match(T__56);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(1216);
				usingDeclaration();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(1217);
				templateDeclaration();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MemberDeclaratorListContext extends ParserRuleContext {
		public List<MemberDeclaratorContext> memberDeclarator() {
			return getRuleContexts(MemberDeclaratorContext.class);
		}
		public MemberDeclaratorContext memberDeclarator(int i) {
			return getRuleContext(MemberDeclaratorContext.class,i);
		}
		public MemberDeclaratorListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_memberDeclaratorList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterMemberDeclaratorList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitMemberDeclaratorList(this);
		}
	}

	public final MemberDeclaratorListContext memberDeclaratorList() throws RecognitionException {
		MemberDeclaratorListContext _localctx = new MemberDeclaratorListContext(_ctx, getState());
		enterRule(_localctx, 158, RULE_memberDeclaratorList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1220);
			memberDeclarator();
			setState(1225);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__50) {
				{
				{
				setState(1221);
				match(T__50);
				setState(1222);
				memberDeclarator();
				}
				}
				setState(1227);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MemberDeclaratorContext extends ParserRuleContext {
		public DeclaratorContext declarator() {
			return getRuleContext(DeclaratorContext.class,0);
		}
		public PureSpecifierContext pureSpecifier() {
			return getRuleContext(PureSpecifierContext.class,0);
		}
		public ConstantInitializerContext constantInitializer() {
			return getRuleContext(ConstantInitializerContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(CElsaParser.IDENTIFIER, 0); }
		public MemberDeclaratorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_memberDeclarator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterMemberDeclarator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitMemberDeclarator(this);
		}
	}

	public final MemberDeclaratorContext memberDeclarator() throws RecognitionException {
		MemberDeclaratorContext _localctx = new MemberDeclaratorContext(_ctx, getState());
		enterRule(_localctx, 160, RULE_memberDeclarator);
		int _la;
		try {
			setState(1241);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,151,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1228);
				declarator();
				setState(1230);
				_la = _input.LA(1);
				if (_la==T__38) {
					{
					setState(1229);
					pureSpecifier();
					}
				}

				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1232);
				declarator();
				setState(1234);
				_la = _input.LA(1);
				if (_la==T__38) {
					{
					setState(1233);
					constantInitializer();
					}
				}

				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1237);
				_la = _input.LA(1);
				if (_la==IDENTIFIER) {
					{
					setState(1236);
					match(IDENTIFIER);
					}
				}

				setState(1239);
				match(T__37);
				setState(1240);
				expression(0);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PureSpecifierContext extends ParserRuleContext {
		public TerminalNode DECIMAL_LITERAL() { return getToken(CElsaParser.DECIMAL_LITERAL, 0); }
		public PureSpecifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pureSpecifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterPureSpecifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitPureSpecifier(this);
		}
	}

	public final PureSpecifierContext pureSpecifier() throws RecognitionException {
		PureSpecifierContext _localctx = new PureSpecifierContext(_ctx, getState());
		enterRule(_localctx, 162, RULE_pureSpecifier);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1243);
			match(T__38);
			setState(1244);
			match(DECIMAL_LITERAL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstantInitializerContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ConstantInitializerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constantInitializer; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterConstantInitializer(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitConstantInitializer(this);
		}
	}

	public final ConstantInitializerContext constantInitializer() throws RecognitionException {
		ConstantInitializerContext _localctx = new ConstantInitializerContext(_ctx, getState());
		enterRule(_localctx, 164, RULE_constantInitializer);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1246);
			match(T__38);
			setState(1247);
			expression(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BaseClauseContext extends ParserRuleContext {
		public BaseSpecifierListContext baseSpecifierList() {
			return getRuleContext(BaseSpecifierListContext.class,0);
		}
		public BaseClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_baseClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterBaseClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitBaseClause(this);
		}
	}

	public final BaseClauseContext baseClause() throws RecognitionException {
		BaseClauseContext _localctx = new BaseClauseContext(_ctx, getState());
		enterRule(_localctx, 166, RULE_baseClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1249);
			match(T__37);
			setState(1250);
			baseSpecifierList(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BaseSpecifierListContext extends ParserRuleContext {
		public BaseSpecifierContext baseSpecifier() {
			return getRuleContext(BaseSpecifierContext.class,0);
		}
		public BaseSpecifierListContext baseSpecifierList() {
			return getRuleContext(BaseSpecifierListContext.class,0);
		}
		public BaseSpecifierListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_baseSpecifierList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterBaseSpecifierList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitBaseSpecifierList(this);
		}
	}

	public final BaseSpecifierListContext baseSpecifierList() throws RecognitionException {
		return baseSpecifierList(0);
	}

	private BaseSpecifierListContext baseSpecifierList(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		BaseSpecifierListContext _localctx = new BaseSpecifierListContext(_ctx, _parentState);
		BaseSpecifierListContext _prevctx = _localctx;
		int _startState = 168;
		enterRecursionRule(_localctx, 168, RULE_baseSpecifierList, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(1253);
			baseSpecifier();
			}
			_ctx.stop = _input.LT(-1);
			setState(1260);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,152,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new BaseSpecifierListContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_baseSpecifierList);
					setState(1255);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(1256);
					match(T__50);
					setState(1257);
					baseSpecifier();
					}
					} 
				}
				setState(1262);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,152,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class BaseSpecifierContext extends ParserRuleContext {
		public ClassNameContext className() {
			return getRuleContext(ClassNameContext.class,0);
		}
		public NestedNameSpecifierContext nestedNameSpecifier() {
			return getRuleContext(NestedNameSpecifierContext.class,0);
		}
		public AccessSpecifierContext accessSpecifier() {
			return getRuleContext(AccessSpecifierContext.class,0);
		}
		public BaseSpecifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_baseSpecifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterBaseSpecifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitBaseSpecifier(this);
		}
	}

	public final BaseSpecifierContext baseSpecifier() throws RecognitionException {
		BaseSpecifierContext _localctx = new BaseSpecifierContext(_ctx, getState());
		enterRule(_localctx, 170, RULE_baseSpecifier);
		int _la;
		try {
			setState(1293);
			switch (_input.LA(1)) {
			case T__2:
			case IDENTIFIER:
				enterOuterAlt(_localctx, 1);
				{
				setState(1264);
				_la = _input.LA(1);
				if (_la==T__2) {
					{
					setState(1263);
					match(T__2);
					}
				}

				setState(1267);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,154,_ctx) ) {
				case 1:
					{
					setState(1266);
					nestedNameSpecifier();
					}
					break;
				}
				setState(1269);
				className();
				}
				break;
			case T__77:
				enterOuterAlt(_localctx, 2);
				{
				setState(1270);
				match(T__77);
				setState(1272);
				_la = _input.LA(1);
				if (((((_la - 101)) & ~0x3f) == 0 && ((1L << (_la - 101)) & ((1L << (T__100 - 101)) | (1L << (T__101 - 101)) | (1L << (T__102 - 101)))) != 0)) {
					{
					setState(1271);
					accessSpecifier();
					}
				}

				setState(1275);
				_la = _input.LA(1);
				if (_la==T__2) {
					{
					setState(1274);
					match(T__2);
					}
				}

				setState(1278);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,157,_ctx) ) {
				case 1:
					{
					setState(1277);
					nestedNameSpecifier();
					}
					break;
				}
				setState(1280);
				className();
				}
				break;
			case T__100:
			case T__101:
			case T__102:
				enterOuterAlt(_localctx, 3);
				{
				setState(1281);
				accessSpecifier();
				setState(1283);
				_la = _input.LA(1);
				if (_la==T__77) {
					{
					setState(1282);
					match(T__77);
					}
				}

				setState(1286);
				_la = _input.LA(1);
				if (_la==T__2) {
					{
					setState(1285);
					match(T__2);
					}
				}

				setState(1289);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,160,_ctx) ) {
				case 1:
					{
					setState(1288);
					nestedNameSpecifier();
					}
					break;
				}
				setState(1291);
				className();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AccessSpecifierContext extends ParserRuleContext {
		public AccessSpecifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_accessSpecifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterAccessSpecifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitAccessSpecifier(this);
		}
	}

	public final AccessSpecifierContext accessSpecifier() throws RecognitionException {
		AccessSpecifierContext _localctx = new AccessSpecifierContext(_ctx, getState());
		enterRule(_localctx, 172, RULE_accessSpecifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1295);
			_la = _input.LA(1);
			if ( !(((((_la - 101)) & ~0x3f) == 0 && ((1L << (_la - 101)) & ((1L << (T__100 - 101)) | (1L << (T__101 - 101)) | (1L << (T__102 - 101)))) != 0)) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConversionFunctionIdContext extends ParserRuleContext {
		public List<TypeSpecifierContext> typeSpecifier() {
			return getRuleContexts(TypeSpecifierContext.class);
		}
		public TypeSpecifierContext typeSpecifier(int i) {
			return getRuleContext(TypeSpecifierContext.class,i);
		}
		public List<PtrOperatorContext> ptrOperator() {
			return getRuleContexts(PtrOperatorContext.class);
		}
		public PtrOperatorContext ptrOperator(int i) {
			return getRuleContext(PtrOperatorContext.class,i);
		}
		public ConversionFunctionIdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conversionFunctionId; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterConversionFunctionId(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitConversionFunctionId(this);
		}
	}

	public final ConversionFunctionIdContext conversionFunctionId() throws RecognitionException {
		ConversionFunctionIdContext _localctx = new ConversionFunctionIdContext(_ctx, getState());
		enterRule(_localctx, 174, RULE_conversionFunctionId);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(1297);
			match(T__103);
			setState(1299); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(1298);
					typeSpecifier();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(1301); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,162,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			setState(1304); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(1303);
					ptrOperator();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(1306); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,163,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CtorInitializerContext extends ParserRuleContext {
		public List<MemInitializerContext> memInitializer() {
			return getRuleContexts(MemInitializerContext.class);
		}
		public MemInitializerContext memInitializer(int i) {
			return getRuleContext(MemInitializerContext.class,i);
		}
		public CtorInitializerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ctorInitializer; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterCtorInitializer(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitCtorInitializer(this);
		}
	}

	public final CtorInitializerContext ctorInitializer() throws RecognitionException {
		CtorInitializerContext _localctx = new CtorInitializerContext(_ctx, getState());
		enterRule(_localctx, 176, RULE_ctorInitializer);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1308);
			match(T__37);
			setState(1309);
			memInitializer();
			setState(1314);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__50) {
				{
				{
				setState(1310);
				match(T__50);
				setState(1311);
				memInitializer();
				}
				}
				setState(1316);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MemInitializerContext extends ParserRuleContext {
		public MemInitializerIdContext memInitializerId() {
			return getRuleContext(MemInitializerIdContext.class,0);
		}
		public ExpressionListContext expressionList() {
			return getRuleContext(ExpressionListContext.class,0);
		}
		public MemInitializerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_memInitializer; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterMemInitializer(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitMemInitializer(this);
		}
	}

	public final MemInitializerContext memInitializer() throws RecognitionException {
		MemInitializerContext _localctx = new MemInitializerContext(_ctx, getState());
		enterRule(_localctx, 178, RULE_memInitializer);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1317);
			memInitializerId();
			setState(1318);
			match(T__4);
			setState(1320);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__2) | (1L << T__3) | (1L << T__4) | (1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__51) | (1L << T__52))) != 0) || ((((_la - 80)) & ~0x3f) == 0 && ((1L << (_la - 80)) & ((1L << (T__79 - 80)) | (1L << (T__80 - 80)) | (1L << (T__81 - 80)) | (1L << (T__82 - 80)) | (1L << (T__83 - 80)) | (1L << (T__84 - 80)) | (1L << (T__85 - 80)) | (1L << (T__86 - 80)) | (1L << (T__87 - 80)) | (1L << (T__88 - 80)) | (1L << (T__89 - 80)) | (1L << (T__103 - 80)) | (1L << (DECIMAL_LITERAL - 80)) | (1L << (HEX_LITERAL - 80)) | (1L << (OCT_LITERAL - 80)) | (1L << (BINARY_LITERAL - 80)) | (1L << (FLOAT_LITERAL - 80)) | (1L << (HEX_FLOAT_LITERAL - 80)) | (1L << (BOOL_LITERAL - 80)) | (1L << (CHAR_LITERAL - 80)) | (1L << (STRING_LITERAL - 80)) | (1L << (NULL_LITERAL - 80)) | (1L << (IDENTIFIER - 80)))) != 0)) {
				{
				setState(1319);
				expressionList();
				}
			}

			setState(1322);
			match(T__5);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MemInitializerIdContext extends ParserRuleContext {
		public ClassNameContext className() {
			return getRuleContext(ClassNameContext.class,0);
		}
		public NestedNameSpecifierContext nestedNameSpecifier() {
			return getRuleContext(NestedNameSpecifierContext.class,0);
		}
		public TerminalNode IDENTIFIER() { return getToken(CElsaParser.IDENTIFIER, 0); }
		public MemInitializerIdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_memInitializerId; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterMemInitializerId(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitMemInitializerId(this);
		}
	}

	public final MemInitializerIdContext memInitializerId() throws RecognitionException {
		MemInitializerIdContext _localctx = new MemInitializerIdContext(_ctx, getState());
		enterRule(_localctx, 180, RULE_memInitializerId);
		int _la;
		try {
			setState(1332);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,168,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1325);
				_la = _input.LA(1);
				if (_la==T__2) {
					{
					setState(1324);
					match(T__2);
					}
				}

				setState(1328);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,167,_ctx) ) {
				case 1:
					{
					setState(1327);
					nestedNameSpecifier();
					}
					break;
				}
				setState(1330);
				className();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1331);
				match(IDENTIFIER);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OperatorFunctionIdContext extends ParserRuleContext {
		public OperatorFuncContext operatorFunc() {
			return getRuleContext(OperatorFuncContext.class,0);
		}
		public OperatorFunctionIdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operatorFunctionId; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterOperatorFunctionId(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitOperatorFunctionId(this);
		}
	}

	public final OperatorFunctionIdContext operatorFunctionId() throws RecognitionException {
		OperatorFunctionIdContext _localctx = new OperatorFunctionIdContext(_ctx, getState());
		enterRule(_localctx, 182, RULE_operatorFunctionId);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1334);
			match(T__103);
			setState(1335);
			operatorFunc();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OperatorFuncContext extends ParserRuleContext {
		public OperatorFuncContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operatorFunc; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterOperatorFunc(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitOperatorFunc(this);
		}
	}

	public final OperatorFuncContext operatorFunc() throws RecognitionException {
		OperatorFuncContext _localctx = new OperatorFuncContext(_ctx, getState());
		enterRule(_localctx, 184, RULE_operatorFunc);
		try {
			setState(1385);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,169,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1337);
				match(T__51);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1338);
				match(T__52);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1339);
				match(T__51);
				setState(1340);
				match(T__15);
				setState(1341);
				match(T__16);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(1342);
				match(T__52);
				setState(1343);
				match(T__15);
				setState(1344);
				match(T__16);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(1345);
				match(T__8);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(1346);
				match(T__9);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(1347);
				match(T__12);
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(1348);
				match(T__26);
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(1349);
				match(T__27);
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(1350);
				match(T__32);
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(1351);
				match(T__13);
				}
				break;
			case 12:
				enterOuterAlt(_localctx, 12);
				{
				setState(1352);
				match(T__33);
				}
				break;
			case 13:
				enterOuterAlt(_localctx, 13);
				{
				setState(1353);
				match(T__0);
				}
				break;
			case 14:
				enterOuterAlt(_localctx, 14);
				{
				setState(1354);
				match(T__14);
				}
				break;
			case 15:
				enterOuterAlt(_localctx, 15);
				{
				setState(1355);
				match(T__38);
				}
				break;
			case 16:
				enterOuterAlt(_localctx, 16);
				{
				setState(1356);
				match(T__20);
				}
				break;
			case 17:
				enterOuterAlt(_localctx, 17);
				{
				setState(1357);
				match(T__21);
				}
				break;
			case 18:
				enterOuterAlt(_localctx, 18);
				{
				setState(1358);
				match(T__39);
				}
				break;
			case 19:
				enterOuterAlt(_localctx, 19);
				{
				setState(1359);
				match(T__40);
				}
				break;
			case 20:
				enterOuterAlt(_localctx, 20);
				{
				setState(1360);
				match(T__41);
				}
				break;
			case 21:
				enterOuterAlt(_localctx, 21);
				{
				setState(1361);
				match(T__42);
				}
				break;
			case 22:
				enterOuterAlt(_localctx, 22);
				{
				setState(1362);
				match(T__49);
				}
				break;
			case 23:
				enterOuterAlt(_localctx, 23);
				{
				setState(1363);
				match(T__45);
				}
				break;
			case 24:
				enterOuterAlt(_localctx, 24);
				{
				setState(1364);
				match(T__43);
				}
				break;
			case 25:
				enterOuterAlt(_localctx, 25);
				{
				setState(1365);
				match(T__44);
				}
				break;
			case 26:
				enterOuterAlt(_localctx, 26);
				{
				setState(1366);
				match(T__104);
				}
				break;
			case 27:
				enterOuterAlt(_localctx, 27);
				{
				setState(1367);
				match(T__105);
				}
				break;
			case 28:
				enterOuterAlt(_localctx, 28);
				{
				setState(1368);
				match(T__46);
				}
				break;
			case 29:
				enterOuterAlt(_localctx, 29);
				{
				setState(1369);
				match(T__48);
				}
				break;
			case 30:
				enterOuterAlt(_localctx, 30);
				{
				setState(1370);
				match(T__30);
				}
				break;
			case 31:
				enterOuterAlt(_localctx, 31);
				{
				setState(1371);
				match(T__31);
				}
				break;
			case 32:
				enterOuterAlt(_localctx, 32);
				{
				setState(1372);
				match(T__28);
				}
				break;
			case 33:
				enterOuterAlt(_localctx, 33);
				{
				setState(1373);
				match(T__29);
				}
				break;
			case 34:
				enterOuterAlt(_localctx, 34);
				{
				setState(1374);
				match(T__34);
				}
				break;
			case 35:
				enterOuterAlt(_localctx, 35);
				{
				setState(1375);
				match(T__35);
				}
				break;
			case 36:
				enterOuterAlt(_localctx, 36);
				{
				setState(1376);
				match(T__10);
				}
				break;
			case 37:
				enterOuterAlt(_localctx, 37);
				{
				setState(1377);
				match(T__11);
				}
				break;
			case 38:
				enterOuterAlt(_localctx, 38);
				{
				setState(1378);
				match(T__50);
				}
				break;
			case 39:
				enterOuterAlt(_localctx, 39);
				{
				setState(1379);
				match(T__12);
				}
				break;
			case 40:
				enterOuterAlt(_localctx, 40);
				{
				setState(1380);
				match(T__7);
				}
				break;
			case 41:
				enterOuterAlt(_localctx, 41);
				{
				setState(1381);
				match(T__4);
				setState(1382);
				match(T__5);
				}
				break;
			case 42:
				enterOuterAlt(_localctx, 42);
				{
				setState(1383);
				match(T__15);
				setState(1384);
				match(T__16);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TemplateDeclarationContext extends ParserRuleContext {
		public TemplateParameterListContext templateParameterList() {
			return getRuleContext(TemplateParameterListContext.class,0);
		}
		public DeclarationContext declaration() {
			return getRuleContext(DeclarationContext.class,0);
		}
		public TemplateDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_templateDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterTemplateDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitTemplateDeclaration(this);
		}
	}

	public final TemplateDeclarationContext templateDeclaration() throws RecognitionException {
		TemplateDeclarationContext _localctx = new TemplateDeclarationContext(_ctx, getState());
		enterRule(_localctx, 186, RULE_templateDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1387);
			match(T__1);
			setState(1388);
			match(T__20);
			setState(1389);
			templateParameterList();
			setState(1390);
			match(T__21);
			setState(1391);
			declaration();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TemplateParameterListContext extends ParserRuleContext {
		public List<TemplateParameterContext> templateParameter() {
			return getRuleContexts(TemplateParameterContext.class);
		}
		public TemplateParameterContext templateParameter(int i) {
			return getRuleContext(TemplateParameterContext.class,i);
		}
		public TemplateParameterListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_templateParameterList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterTemplateParameterList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitTemplateParameterList(this);
		}
	}

	public final TemplateParameterListContext templateParameterList() throws RecognitionException {
		TemplateParameterListContext _localctx = new TemplateParameterListContext(_ctx, getState());
		enterRule(_localctx, 188, RULE_templateParameterList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1393);
			templateParameter();
			setState(1398);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__50) {
				{
				{
				setState(1394);
				match(T__50);
				setState(1395);
				templateParameter();
				}
				}
				setState(1400);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TemplateParameterContext extends ParserRuleContext {
		public TypeParameterContext typeParameter() {
			return getRuleContext(TypeParameterContext.class,0);
		}
		public ParameterDeclarationContext parameterDeclaration() {
			return getRuleContext(ParameterDeclarationContext.class,0);
		}
		public TemplateParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_templateParameter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterTemplateParameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitTemplateParameter(this);
		}
	}

	public final TemplateParameterContext templateParameter() throws RecognitionException {
		TemplateParameterContext _localctx = new TemplateParameterContext(_ctx, getState());
		enterRule(_localctx, 190, RULE_templateParameter);
		try {
			setState(1403);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,171,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1401);
				typeParameter();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1402);
				parameterDeclaration();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeParameterContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(CElsaParser.IDENTIFIER, 0); }
		public TypeIdContext typeId() {
			return getRuleContext(TypeIdContext.class,0);
		}
		public TemplateParameterListContext templateParameterList() {
			return getRuleContext(TemplateParameterListContext.class,0);
		}
		public IdExpressionContext idExpression() {
			return getRuleContext(IdExpressionContext.class,0);
		}
		public TypeParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeParameter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterTypeParameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitTypeParameter(this);
		}
	}

	public final TypeParameterContext typeParameter() throws RecognitionException {
		TypeParameterContext _localctx = new TypeParameterContext(_ctx, getState());
		enterRule(_localctx, 192, RULE_typeParameter);
		int _la;
		try {
			setState(1444);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,178,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1405);
				match(T__97);
				setState(1407);
				_la = _input.LA(1);
				if (_la==IDENTIFIER) {
					{
					setState(1406);
					match(IDENTIFIER);
					}
				}

				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1409);
				match(T__97);
				setState(1411);
				_la = _input.LA(1);
				if (_la==IDENTIFIER) {
					{
					setState(1410);
					match(IDENTIFIER);
					}
				}

				setState(1413);
				match(T__38);
				setState(1414);
				typeId();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1415);
				match(T__18);
				setState(1417);
				_la = _input.LA(1);
				if (_la==IDENTIFIER) {
					{
					setState(1416);
					match(IDENTIFIER);
					}
				}

				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(1419);
				match(T__18);
				setState(1421);
				_la = _input.LA(1);
				if (_la==IDENTIFIER) {
					{
					setState(1420);
					match(IDENTIFIER);
					}
				}

				setState(1423);
				match(T__38);
				setState(1424);
				typeId();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(1425);
				match(T__1);
				setState(1426);
				match(T__20);
				setState(1427);
				templateParameterList();
				setState(1428);
				match(T__21);
				setState(1429);
				match(T__97);
				setState(1431);
				_la = _input.LA(1);
				if (_la==IDENTIFIER) {
					{
					setState(1430);
					match(IDENTIFIER);
					}
				}

				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(1433);
				match(T__1);
				setState(1434);
				match(T__20);
				setState(1435);
				templateParameterList();
				setState(1436);
				match(T__21);
				setState(1437);
				match(T__97);
				setState(1439);
				_la = _input.LA(1);
				if (_la==IDENTIFIER) {
					{
					setState(1438);
					match(IDENTIFIER);
					}
				}

				setState(1441);
				match(T__38);
				setState(1442);
				idExpression();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TemplateIdContext extends ParserRuleContext {
		public TemplateNameContext templateName() {
			return getRuleContext(TemplateNameContext.class,0);
		}
		public TemplateArgumentListContext templateArgumentList() {
			return getRuleContext(TemplateArgumentListContext.class,0);
		}
		public TemplateIdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_templateId; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterTemplateId(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitTemplateId(this);
		}
	}

	public final TemplateIdContext templateId() throws RecognitionException {
		TemplateIdContext _localctx = new TemplateIdContext(_ctx, getState());
		enterRule(_localctx, 194, RULE_templateId);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1446);
			templateName();
			setState(1447);
			match(T__20);
			setState(1449);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__2) | (1L << T__3) | (1L << T__4) | (1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__51) | (1L << T__52))) != 0) || ((((_la - 80)) & ~0x3f) == 0 && ((1L << (_la - 80)) & ((1L << (T__79 - 80)) | (1L << (T__80 - 80)) | (1L << (T__81 - 80)) | (1L << (T__82 - 80)) | (1L << (T__83 - 80)) | (1L << (T__84 - 80)) | (1L << (T__85 - 80)) | (1L << (T__86 - 80)) | (1L << (T__87 - 80)) | (1L << (T__88 - 80)) | (1L << (T__89 - 80)) | (1L << (T__90 - 80)) | (1L << (T__94 - 80)) | (1L << (T__95 - 80)) | (1L << (T__97 - 80)) | (1L << (T__98 - 80)) | (1L << (T__99 - 80)) | (1L << (T__103 - 80)) | (1L << (DECIMAL_LITERAL - 80)) | (1L << (HEX_LITERAL - 80)) | (1L << (OCT_LITERAL - 80)) | (1L << (BINARY_LITERAL - 80)) | (1L << (FLOAT_LITERAL - 80)) | (1L << (HEX_FLOAT_LITERAL - 80)) | (1L << (BOOL_LITERAL - 80)) | (1L << (CHAR_LITERAL - 80)) | (1L << (STRING_LITERAL - 80)) | (1L << (NULL_LITERAL - 80)) | (1L << (IDENTIFIER - 80)))) != 0)) {
				{
				setState(1448);
				templateArgumentList();
				}
			}

			setState(1451);
			match(T__21);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TemplateArgumentListContext extends ParserRuleContext {
		public List<TemplateArgumentContext> templateArgument() {
			return getRuleContexts(TemplateArgumentContext.class);
		}
		public TemplateArgumentContext templateArgument(int i) {
			return getRuleContext(TemplateArgumentContext.class,i);
		}
		public TemplateArgumentListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_templateArgumentList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterTemplateArgumentList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitTemplateArgumentList(this);
		}
	}

	public final TemplateArgumentListContext templateArgumentList() throws RecognitionException {
		TemplateArgumentListContext _localctx = new TemplateArgumentListContext(_ctx, getState());
		enterRule(_localctx, 196, RULE_templateArgumentList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1453);
			templateArgument();
			setState(1458);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__50) {
				{
				{
				setState(1454);
				match(T__50);
				setState(1455);
				templateArgument();
				}
				}
				setState(1460);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TemplateArgumentContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TypeIdContext typeId() {
			return getRuleContext(TypeIdContext.class,0);
		}
		public IdExpressionContext idExpression() {
			return getRuleContext(IdExpressionContext.class,0);
		}
		public TemplateArgumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_templateArgument; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterTemplateArgument(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitTemplateArgument(this);
		}
	}

	public final TemplateArgumentContext templateArgument() throws RecognitionException {
		TemplateArgumentContext _localctx = new TemplateArgumentContext(_ctx, getState());
		enterRule(_localctx, 198, RULE_templateArgument);
		try {
			setState(1464);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,181,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1461);
				expression(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1462);
				typeId();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1463);
				idExpression();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExplicitInstantiationContext extends ParserRuleContext {
		public DeclarationContext declaration() {
			return getRuleContext(DeclarationContext.class,0);
		}
		public ExplicitInstantiationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_explicitInstantiation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterExplicitInstantiation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitExplicitInstantiation(this);
		}
	}

	public final ExplicitInstantiationContext explicitInstantiation() throws RecognitionException {
		ExplicitInstantiationContext _localctx = new ExplicitInstantiationContext(_ctx, getState());
		enterRule(_localctx, 200, RULE_explicitInstantiation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1466);
			match(T__1);
			setState(1467);
			declaration();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExplicitSpecializationContext extends ParserRuleContext {
		public DeclarationContext declaration() {
			return getRuleContext(DeclarationContext.class,0);
		}
		public ExplicitSpecializationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_explicitSpecialization; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterExplicitSpecialization(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitExplicitSpecialization(this);
		}
	}

	public final ExplicitSpecializationContext explicitSpecialization() throws RecognitionException {
		ExplicitSpecializationContext _localctx = new ExplicitSpecializationContext(_ctx, getState());
		enterRule(_localctx, 202, RULE_explicitSpecialization);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1469);
			match(T__1);
			setState(1470);
			match(T__20);
			setState(1471);
			match(T__21);
			setState(1472);
			declaration();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TryBlockContext extends ParserRuleContext {
		public CompoundStatementContext compoundStatement() {
			return getRuleContext(CompoundStatementContext.class,0);
		}
		public HandlerSeqContext handlerSeq() {
			return getRuleContext(HandlerSeqContext.class,0);
		}
		public TryBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tryBlock; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterTryBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitTryBlock(this);
		}
	}

	public final TryBlockContext tryBlock() throws RecognitionException {
		TryBlockContext _localctx = new TryBlockContext(_ctx, getState());
		enterRule(_localctx, 204, RULE_tryBlock);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1474);
			match(T__106);
			setState(1475);
			compoundStatement();
			setState(1476);
			handlerSeq();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionTryBlockContext extends ParserRuleContext {
		public FunctionBodyContext functionBody() {
			return getRuleContext(FunctionBodyContext.class,0);
		}
		public HandlerSeqContext handlerSeq() {
			return getRuleContext(HandlerSeqContext.class,0);
		}
		public CtorInitializerContext ctorInitializer() {
			return getRuleContext(CtorInitializerContext.class,0);
		}
		public FunctionTryBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionTryBlock; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterFunctionTryBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitFunctionTryBlock(this);
		}
	}

	public final FunctionTryBlockContext functionTryBlock() throws RecognitionException {
		FunctionTryBlockContext _localctx = new FunctionTryBlockContext(_ctx, getState());
		enterRule(_localctx, 206, RULE_functionTryBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1478);
			match(T__106);
			setState(1480);
			_la = _input.LA(1);
			if (_la==T__37) {
				{
				setState(1479);
				ctorInitializer();
				}
			}

			setState(1482);
			functionBody();
			setState(1483);
			handlerSeq();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class HandlerSeqContext extends ParserRuleContext {
		public List<HandlerContext> handler() {
			return getRuleContexts(HandlerContext.class);
		}
		public HandlerContext handler(int i) {
			return getRuleContext(HandlerContext.class,i);
		}
		public HandlerSeqContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_handlerSeq; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterHandlerSeq(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitHandlerSeq(this);
		}
	}

	public final HandlerSeqContext handlerSeq() throws RecognitionException {
		HandlerSeqContext _localctx = new HandlerSeqContext(_ctx, getState());
		enterRule(_localctx, 208, RULE_handlerSeq);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1485);
			handler();
			setState(1489);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__107) {
				{
				{
				setState(1486);
				handler();
				}
				}
				setState(1491);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class HandlerContext extends ParserRuleContext {
		public ExceptionDeclarationContext exceptionDeclaration() {
			return getRuleContext(ExceptionDeclarationContext.class,0);
		}
		public CompoundStatementContext compoundStatement() {
			return getRuleContext(CompoundStatementContext.class,0);
		}
		public HandlerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_handler; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterHandler(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitHandler(this);
		}
	}

	public final HandlerContext handler() throws RecognitionException {
		HandlerContext _localctx = new HandlerContext(_ctx, getState());
		enterRule(_localctx, 210, RULE_handler);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1492);
			match(T__107);
			setState(1493);
			match(T__4);
			setState(1494);
			exceptionDeclaration();
			setState(1495);
			match(T__5);
			setState(1496);
			compoundStatement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExceptionDeclarationContext extends ParserRuleContext {
		public DeclaratorContext declarator() {
			return getRuleContext(DeclaratorContext.class,0);
		}
		public List<TypeSpecifierContext> typeSpecifier() {
			return getRuleContexts(TypeSpecifierContext.class);
		}
		public TypeSpecifierContext typeSpecifier(int i) {
			return getRuleContext(TypeSpecifierContext.class,i);
		}
		public ExceptionDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exceptionDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterExceptionDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitExceptionDeclaration(this);
		}
	}

	public final ExceptionDeclarationContext exceptionDeclaration() throws RecognitionException {
		ExceptionDeclarationContext _localctx = new ExceptionDeclarationContext(_ctx, getState());
		enterRule(_localctx, 212, RULE_exceptionDeclaration);
		int _la;
		try {
			int _alt;
			setState(1511);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,186,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(1499); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(1498);
						typeSpecifier();
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(1501); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,184,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(1503);
				declarator();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(1506); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(1505);
					typeSpecifier();
					}
					}
					setState(1508); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__2 || _la==T__18 || ((((_la - 80)) & ~0x3f) == 0 && ((1L << (_la - 80)) & ((1L << (T__79 - 80)) | (1L << (T__80 - 80)) | (1L << (T__81 - 80)) | (1L << (T__82 - 80)) | (1L << (T__83 - 80)) | (1L << (T__84 - 80)) | (1L << (T__85 - 80)) | (1L << (T__86 - 80)) | (1L << (T__87 - 80)) | (1L << (T__88 - 80)) | (1L << (T__89 - 80)) | (1L << (T__90 - 80)) | (1L << (T__94 - 80)) | (1L << (T__95 - 80)) | (1L << (T__97 - 80)) | (1L << (T__98 - 80)) | (1L << (T__99 - 80)) | (1L << (IDENTIFIER - 80)))) != 0) );
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(1510);
				match(T__96);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ThrowExpressionContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ThrowExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_throwExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterThrowExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitThrowExpression(this);
		}
	}

	public final ThrowExpressionContext throwExpression() throws RecognitionException {
		ThrowExpressionContext _localctx = new ThrowExpressionContext(_ctx, getState());
		enterRule(_localctx, 214, RULE_throwExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1513);
			match(T__108);
			setState(1515);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__2) | (1L << T__3) | (1L << T__4) | (1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__51) | (1L << T__52))) != 0) || ((((_la - 80)) & ~0x3f) == 0 && ((1L << (_la - 80)) & ((1L << (T__79 - 80)) | (1L << (T__80 - 80)) | (1L << (T__81 - 80)) | (1L << (T__82 - 80)) | (1L << (T__83 - 80)) | (1L << (T__84 - 80)) | (1L << (T__85 - 80)) | (1L << (T__86 - 80)) | (1L << (T__87 - 80)) | (1L << (T__88 - 80)) | (1L << (T__89 - 80)) | (1L << (T__103 - 80)) | (1L << (DECIMAL_LITERAL - 80)) | (1L << (HEX_LITERAL - 80)) | (1L << (OCT_LITERAL - 80)) | (1L << (BINARY_LITERAL - 80)) | (1L << (FLOAT_LITERAL - 80)) | (1L << (HEX_FLOAT_LITERAL - 80)) | (1L << (BOOL_LITERAL - 80)) | (1L << (CHAR_LITERAL - 80)) | (1L << (STRING_LITERAL - 80)) | (1L << (NULL_LITERAL - 80)) | (1L << (IDENTIFIER - 80)))) != 0)) {
				{
				setState(1514);
				expression(0);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExceptionSpecificationContext extends ParserRuleContext {
		public TypeIdListContext typeIdList() {
			return getRuleContext(TypeIdListContext.class,0);
		}
		public ExceptionSpecificationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exceptionSpecification; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterExceptionSpecification(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitExceptionSpecification(this);
		}
	}

	public final ExceptionSpecificationContext exceptionSpecification() throws RecognitionException {
		ExceptionSpecificationContext _localctx = new ExceptionSpecificationContext(_ctx, getState());
		enterRule(_localctx, 216, RULE_exceptionSpecification);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(1517);
			match(T__108);
			setState(1518);
			match(T__4);
			setState(1520);
			_la = _input.LA(1);
			if (_la==T__2 || _la==T__18 || ((((_la - 80)) & ~0x3f) == 0 && ((1L << (_la - 80)) & ((1L << (T__79 - 80)) | (1L << (T__80 - 80)) | (1L << (T__81 - 80)) | (1L << (T__82 - 80)) | (1L << (T__83 - 80)) | (1L << (T__84 - 80)) | (1L << (T__85 - 80)) | (1L << (T__86 - 80)) | (1L << (T__87 - 80)) | (1L << (T__88 - 80)) | (1L << (T__89 - 80)) | (1L << (T__90 - 80)) | (1L << (T__94 - 80)) | (1L << (T__95 - 80)) | (1L << (T__97 - 80)) | (1L << (T__98 - 80)) | (1L << (T__99 - 80)) | (1L << (IDENTIFIER - 80)))) != 0)) {
				{
				setState(1519);
				typeIdList(0);
				}
			}

			setState(1522);
			match(T__5);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeIdListContext extends ParserRuleContext {
		public TypeIdContext typeId() {
			return getRuleContext(TypeIdContext.class,0);
		}
		public TypeIdListContext typeIdList() {
			return getRuleContext(TypeIdListContext.class,0);
		}
		public TypeIdListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeIdList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).enterTypeIdList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CElsaListener ) ((CElsaListener)listener).exitTypeIdList(this);
		}
	}

	public final TypeIdListContext typeIdList() throws RecognitionException {
		return typeIdList(0);
	}

	private TypeIdListContext typeIdList(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		TypeIdListContext _localctx = new TypeIdListContext(_ctx, _parentState);
		TypeIdListContext _prevctx = _localctx;
		int _startState = 218;
		enterRecursionRule(_localctx, 218, RULE_typeIdList, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(1525);
			typeId();
			}
			_ctx.stop = _input.LT(-1);
			setState(1532);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,189,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new TypeIdListContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_typeIdList);
					setState(1527);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(1528);
					match(T__50);
					setState(1529);
					typeId();
					}
					} 
				}
				setState(1534);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,189,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 17:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		case 23:
			return newDeclarator_sempred((NewDeclaratorContext)_localctx, predIndex);
		case 26:
			return pmExpression_sempred((PmExpressionContext)_localctx, predIndex);
		case 73:
			return initializerList_sempred((InitializerListContext)_localctx, predIndex);
		case 84:
			return baseSpecifierList_sempred((BaseSpecifierListContext)_localctx, predIndex);
		case 109:
			return typeIdList_sempred((TypeIdListContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 12);
		case 1:
			return precpred(_ctx, 11);
		case 2:
			return precpred(_ctx, 10);
		case 3:
			return precpred(_ctx, 9);
		case 4:
			return precpred(_ctx, 8);
		case 5:
			return precpred(_ctx, 7);
		case 6:
			return precpred(_ctx, 6);
		case 7:
			return precpred(_ctx, 5);
		case 8:
			return precpred(_ctx, 4);
		case 9:
			return precpred(_ctx, 3);
		case 10:
			return precpred(_ctx, 2);
		case 11:
			return precpred(_ctx, 1);
		case 12:
			return precpred(_ctx, 31);
		case 13:
			return precpred(_ctx, 30);
		case 14:
			return precpred(_ctx, 29);
		case 15:
			return precpred(_ctx, 27);
		case 16:
			return precpred(_ctx, 26);
		case 17:
			return precpred(_ctx, 20);
		}
		return true;
	}
	private boolean newDeclarator_sempred(NewDeclaratorContext _localctx, int predIndex) {
		switch (predIndex) {
		case 18:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean pmExpression_sempred(PmExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 19:
			return precpred(_ctx, 2);
		case 20:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean initializerList_sempred(InitializerListContext _localctx, int predIndex) {
		switch (predIndex) {
		case 21:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean baseSpecifierList_sempred(BaseSpecifierListContext _localctx, int predIndex) {
		switch (predIndex) {
		case 22:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean typeIdList_sempred(TypeIdListContext _localctx, int predIndex) {
		switch (predIndex) {
		case 23:
			return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\177\u0602\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\4I"+
		"\tI\4J\tJ\4K\tK\4L\tL\4M\tM\4N\tN\4O\tO\4P\tP\4Q\tQ\4R\tR\4S\tS\4T\tT"+
		"\4U\tU\4V\tV\4W\tW\4X\tX\4Y\tY\4Z\tZ\4[\t[\4\\\t\\\4]\t]\4^\t^\4_\t_\4"+
		"`\t`\4a\ta\4b\tb\4c\tc\4d\td\4e\te\4f\tf\4g\tg\4h\th\4i\ti\4j\tj\4k\t"+
		"k\4l\tl\4m\tm\4n\tn\4o\to\3\2\7\2\u00e0\n\2\f\2\16\2\u00e3\13\2\3\2\3"+
		"\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3\u00ee\n\3\3\4\3\4\3\5\3\5\5\5\u00f4"+
		"\n\5\3\6\3\6\3\7\3\7\3\b\3\b\5\b\u00fc\n\b\3\t\3\t\3\n\3\n\3\13\3\13\3"+
		"\f\3\f\5\f\u0106\n\f\3\r\3\r\3\r\3\r\3\r\3\r\5\r\u010e\n\r\3\16\3\16\3"+
		"\17\5\17\u0113\n\17\3\17\3\17\5\17\u0117\n\17\3\17\3\17\3\17\3\17\3\17"+
		"\3\17\3\17\3\17\5\17\u0121\n\17\3\20\3\20\3\20\5\20\u0126\n\20\3\20\3"+
		"\20\3\20\3\20\3\20\5\20\u012d\n\20\3\21\3\21\5\21\u0131\n\21\3\22\3\22"+
		"\3\22\3\22\3\22\3\22\3\22\3\22\3\22\5\22\u013c\n\22\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\5\23\u014b\n\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\5\23\u0155\n\23\3\23\3\23\5\23\u0159\n"+
		"\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3"+
		"\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3"+
		"\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3"+
		"\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3"+
		"\23\3\23\5\23\u0194\n\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\5\23\u01a4\n\23\3\23\3\23\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23"+
		"\5\23\u01c8\n\23\3\23\3\23\5\23\u01cc\n\23\3\23\3\23\3\23\5\23\u01d1\n"+
		"\23\3\23\3\23\5\23\u01d5\n\23\3\23\3\23\3\23\5\23\u01da\n\23\3\23\3\23"+
		"\5\23\u01de\n\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23"+
		"\3\23\7\23\u01ec\n\23\f\23\16\23\u01ef\13\23\3\24\3\24\3\24\7\24\u01f4"+
		"\n\24\f\24\16\24\u01f7\13\24\3\25\5\25\u01fa\n\25\3\25\5\25\u01fd\n\25"+
		"\3\25\3\25\3\25\3\25\3\25\3\25\5\25\u0205\n\25\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\3\25\5\25\u020f\n\25\3\25\5\25\u0212\n\25\3\25\3\25\5\25\u0216"+
		"\n\25\3\26\5\26\u0219\n\26\3\26\3\26\5\26\u021d\n\26\3\26\3\26\5\26\u0221"+
		"\n\26\3\26\5\26\u0224\n\26\3\26\3\26\5\26\u0228\n\26\3\26\3\26\3\26\3"+
		"\26\5\26\u022e\n\26\5\26\u0230\n\26\3\27\3\27\3\27\3\27\3\30\6\30\u0237"+
		"\n\30\r\30\16\30\u0238\3\30\5\30\u023c\n\30\3\31\3\31\3\31\3\31\3\31\3"+
		"\31\3\31\3\31\5\31\u0246\n\31\3\31\3\31\3\31\3\31\3\31\7\31\u024d\n\31"+
		"\f\31\16\31\u0250\13\31\3\32\3\32\5\32\u0254\n\32\3\32\3\32\3\33\5\33"+
		"\u0259\n\33\3\33\3\33\3\33\5\33\u025e\n\33\3\33\3\33\3\33\3\33\5\33\u0264"+
		"\n\33\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\7\34\u026f\n\34\f\34"+
		"\16\34\u0272\13\34\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\5\35\u027c"+
		"\n\35\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\5\36\u0289"+
		"\n\36\3\37\3\37\3\37\3 \3 \7 \u0290\n \f \16 \u0293\13 \3 \3 \3!\3!\3"+
		"!\3!\3!\3!\3!\3!\3!\3!\3!\3!\3!\3!\3!\3!\3!\3!\3!\3!\5!\u02ab\n!\3\"\3"+
		"\"\6\"\u02af\n\"\r\"\16\"\u02b0\3\"\3\"\3\"\3\"\5\"\u02b7\n\"\3#\3#\3"+
		"#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\3#\5#\u02cb\n#\3#\3#\5#\u02cf"+
		"\n#\3#\3#\3#\5#\u02d4\n#\3$\3$\5$\u02d8\n$\3%\3%\3%\3%\3%\3%\5%\u02e0"+
		"\n%\3%\3%\3%\3%\5%\u02e6\n%\3&\3&\3&\3&\3&\5&\u02ed\n&\3\'\7\'\u02f0\n"+
		"\'\f\'\16\'\u02f3\13\'\3\'\5\'\u02f6\n\'\3\'\3\'\3(\3(\3(\7(\u02fd\n("+
		"\f(\16(\u0300\13(\3)\3)\5)\u0304\n)\3*\3*\3*\3*\3*\5*\u030b\n*\3+\3+\3"+
		",\3,\3-\3-\3-\3-\3-\5-\u0316\n-\3.\5.\u0319\n.\3.\5.\u031c\n.\3.\3.\5"+
		".\u0320\n.\3.\3.\3.\3.\3.\3.\3.\3.\3.\3.\3.\3.\3.\3.\3.\5.\u0331\n.\3"+
		"/\3/\3/\5/\u0336\n/\3\60\3\60\5\60\u033a\n\60\3\60\5\60\u033d\n\60\3\60"+
		"\3\60\3\60\3\60\5\60\u0343\n\60\3\60\5\60\u0346\n\60\3\60\3\60\3\60\5"+
		"\60\u034b\n\60\3\60\3\60\3\60\3\60\3\60\5\60\u0352\n\60\3\60\3\60\5\60"+
		"\u0356\n\60\3\60\3\60\5\60\u035a\n\60\3\61\3\61\5\61\u035e\n\61\3\61\3"+
		"\61\5\61\u0362\n\61\3\61\3\61\3\62\3\62\3\62\7\62\u0369\n\62\f\62\16\62"+
		"\u036c\13\62\3\63\3\63\3\63\3\63\5\63\u0372\n\63\3\64\3\64\5\64\u0376"+
		"\n\64\3\65\3\65\5\65\u037a\n\65\3\66\3\66\3\66\3\66\7\66\u0380\n\66\f"+
		"\66\16\66\u0383\13\66\3\66\3\66\3\67\3\67\3\67\3\67\7\67\u038b\n\67\f"+
		"\67\16\67\u038e\13\67\3\67\3\67\38\38\38\78\u0395\n8\f8\168\u0398\138"+
		"\38\38\39\39\39\39\39\39\3:\5:\u03a3\n:\3:\5:\u03a6\n:\3:\3:\3;\3;\5;"+
		"\u03ac\n;\3;\5;\u03af\n;\3;\3;\3;\3;\3;\3;\3;\3;\3;\5;\u03ba\n;\3<\3<"+
		"\3<\5<\u03bf\n<\3<\5<\u03c2\n<\3<\3<\3<\3=\3=\3=\3=\3=\3=\3>\3>\3>\3>"+
		"\7>\u03d1\n>\f>\16>\u03d4\13>\3>\3>\3>\3>\5>\u03da\n>\3?\6?\u03dd\n?\r"+
		"?\16?\u03de\3?\3?\3?\3?\5?\u03e5\n?\3?\5?\u03e8\n?\3?\6?\u03eb\n?\r?\16"+
		"?\u03ec\3?\3?\5?\u03f1\n?\3?\3?\3?\3?\3?\3?\5?\u03f9\n?\3@\3@\5@\u03fd"+
		"\n@\3@\3@\5@\u0401\n@\3@\3@\3@\5@\u0406\n@\5@\u0408\n@\3A\3A\7A\u040c"+
		"\nA\fA\16A\u040f\13A\3B\3B\3C\3C\5C\u0415\nC\3C\5C\u0418\nC\3C\5C\u041b"+
		"\nC\3D\6D\u041e\nD\rD\16D\u041f\3D\5D\u0423\nD\3E\3E\3E\7E\u0428\nE\f"+
		"E\16E\u042b\13E\3E\5E\u042e\nE\3F\6F\u0431\nF\rF\16F\u0432\3F\5F\u0436"+
		"\nF\3F\6F\u0439\nF\rF\16F\u043a\3F\5F\u043e\nF\3F\3F\3F\5F\u0443\nF\3"+
		"G\7G\u0446\nG\fG\16G\u0449\13G\3G\3G\5G\u044d\nG\3G\3G\3G\7G\u0452\nG"+
		"\fG\16G\u0455\13G\3G\3G\3G\5G\u045a\nG\3H\3H\3I\3I\3I\3I\3I\3I\5I\u0464"+
		"\nI\3J\3J\3J\3J\5J\u046a\nJ\3J\3J\3J\3J\5J\u0470\nJ\3K\3K\3K\3K\3K\3K"+
		"\7K\u0478\nK\fK\16K\u047b\13K\3L\3L\3L\5L\u0480\nL\3L\3L\3M\3M\5M\u0486"+
		"\nM\3M\5M\u0489\nM\3M\3M\3M\3M\5M\u048f\nM\3M\3M\5M\u0493\nM\3M\3M\5M"+
		"\u0497\nM\5M\u0499\nM\3N\3N\3O\3O\7O\u049f\nO\fO\16O\u04a2\13O\3O\3O\3"+
		"O\5O\u04a7\nO\5O\u04a9\nO\3P\3P\5P\u04ad\nP\3P\7P\u04b0\nP\fP\16P\u04b3"+
		"\13P\3P\5P\u04b6\nP\3P\3P\5P\u04ba\nP\3P\3P\5P\u04be\nP\3P\3P\3P\3P\3"+
		"P\5P\u04c5\nP\3Q\3Q\3Q\7Q\u04ca\nQ\fQ\16Q\u04cd\13Q\3R\3R\5R\u04d1\nR"+
		"\3R\3R\5R\u04d5\nR\3R\5R\u04d8\nR\3R\3R\5R\u04dc\nR\3S\3S\3S\3T\3T\3T"+
		"\3U\3U\3U\3V\3V\3V\3V\3V\3V\7V\u04ed\nV\fV\16V\u04f0\13V\3W\5W\u04f3\n"+
		"W\3W\5W\u04f6\nW\3W\3W\3W\5W\u04fb\nW\3W\5W\u04fe\nW\3W\5W\u0501\nW\3"+
		"W\3W\3W\5W\u0506\nW\3W\5W\u0509\nW\3W\5W\u050c\nW\3W\3W\5W\u0510\nW\3"+
		"X\3X\3Y\3Y\6Y\u0516\nY\rY\16Y\u0517\3Y\6Y\u051b\nY\rY\16Y\u051c\3Z\3Z"+
		"\3Z\3Z\7Z\u0523\nZ\fZ\16Z\u0526\13Z\3[\3[\3[\5[\u052b\n[\3[\3[\3\\\5\\"+
		"\u0530\n\\\3\\\5\\\u0533\n\\\3\\\3\\\5\\\u0537\n\\\3]\3]\3]\3^\3^\3^\3"+
		"^\3^\3^\3^\3^\3^\3^\3^\3^\3^\3^\3^\3^\3^\3^\3^\3^\3^\3^\3^\3^\3^\3^\3"+
		"^\3^\3^\3^\3^\3^\3^\3^\3^\3^\3^\3^\3^\3^\3^\3^\3^\3^\3^\3^\3^\3^\5^\u056c"+
		"\n^\3_\3_\3_\3_\3_\3_\3`\3`\3`\7`\u0577\n`\f`\16`\u057a\13`\3a\3a\5a\u057e"+
		"\na\3b\3b\5b\u0582\nb\3b\3b\5b\u0586\nb\3b\3b\3b\3b\5b\u058c\nb\3b\3b"+
		"\5b\u0590\nb\3b\3b\3b\3b\3b\3b\3b\3b\5b\u059a\nb\3b\3b\3b\3b\3b\3b\5b"+
		"\u05a2\nb\3b\3b\3b\5b\u05a7\nb\3c\3c\3c\5c\u05ac\nc\3c\3c\3d\3d\3d\7d"+
		"\u05b3\nd\fd\16d\u05b6\13d\3e\3e\3e\5e\u05bb\ne\3f\3f\3f\3g\3g\3g\3g\3"+
		"g\3h\3h\3h\3h\3i\3i\5i\u05cb\ni\3i\3i\3i\3j\3j\7j\u05d2\nj\fj\16j\u05d5"+
		"\13j\3k\3k\3k\3k\3k\3k\3l\6l\u05de\nl\rl\16l\u05df\3l\3l\3l\6l\u05e5\n"+
		"l\rl\16l\u05e6\3l\5l\u05ea\nl\3m\3m\5m\u05ee\nm\3n\3n\3n\5n\u05f3\nn\3"+
		"n\3n\3o\3o\3o\3o\3o\3o\7o\u05fd\no\fo\16o\u0600\13o\3o\2\b$\60\66\u0094"+
		"\u00aa\u00dcp\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64"+
		"\668:<>@BDFHJLNPRTVXZ\\^`bdfhjlnprtvxz|~\u0080\u0082\u0084\u0086\u0088"+
		"\u008a\u008c\u008e\u0090\u0092\u0094\u0096\u0098\u009a\u009c\u009e\u00a0"+
		"\u00a2\u00a4\u00a6\u00a8\u00aa\u00ac\u00ae\u00b0\u00b2\u00b4\u00b6\u00b8"+
		"\u00ba\u00bc\u00be\u00c0\u00c2\u00c4\u00c6\u00c8\u00ca\u00cc\u00ce\u00d0"+
		"\u00d2\u00d4\u00d6\u00d8\u00da\u00dc\2\17\3\2r{\4\2\3\3\13\21\4\2\17\17"+
		"\35\36\3\2\13\f\4\2\27\30\37 \3\2!\"\3\2)\64\3\2\r\16\3\2JN\3\2OQ\3\2"+
		"ab\3\2df\3\2gi\u06d6\2\u00e1\3\2\2\2\4\u00ed\3\2\2\2\6\u00ef\3\2\2\2\b"+
		"\u00f3\3\2\2\2\n\u00f5\3\2\2\2\f\u00f7\3\2\2\2\16\u00fb\3\2\2\2\20\u00fd"+
		"\3\2\2\2\22\u00ff\3\2\2\2\24\u0101\3\2\2\2\26\u0105\3\2\2\2\30\u010d\3"+
		"\2\2\2\32\u010f\3\2\2\2\34\u0120\3\2\2\2\36\u012c\3\2\2\2 \u0130\3\2\2"+
		"\2\"\u013b\3\2\2\2$\u0193\3\2\2\2&\u01f0\3\2\2\2(\u0215\3\2\2\2*\u022f"+
		"\3\2\2\2,\u0231\3\2\2\2.\u0236\3\2\2\2\60\u0245\3\2\2\2\62\u0251\3\2\2"+
		"\2\64\u0263\3\2\2\2\66\u0265\3\2\2\28\u027b\3\2\2\2:\u0288\3\2\2\2<\u028a"+
		"\3\2\2\2>\u028d\3\2\2\2@\u02aa\3\2\2\2B\u02b6\3\2\2\2D\u02d3\3\2\2\2F"+
		"\u02d7\3\2\2\2H\u02e5\3\2\2\2J\u02ec\3\2\2\2L\u02f1\3\2\2\2N\u02f9\3\2"+
		"\2\2P\u0301\3\2\2\2R\u030a\3\2\2\2T\u030c\3\2\2\2V\u030e\3\2\2\2X\u0315"+
		"\3\2\2\2Z\u0330\3\2\2\2\\\u0335\3\2\2\2^\u0359\3\2\2\2`\u035b\3\2\2\2"+
		"b\u0365\3\2\2\2d\u0371\3\2\2\2f\u0375\3\2\2\2h\u0379\3\2\2\2j\u037b\3"+
		"\2\2\2l\u0386\3\2\2\2n\u0391\3\2\2\2p\u039b\3\2\2\2r\u03a2\3\2\2\2t\u03b9"+
		"\3\2\2\2v\u03bb\3\2\2\2x\u03c6\3\2\2\2z\u03d9\3\2\2\2|\u03f8\3\2\2\2~"+
		"\u0407\3\2\2\2\u0080\u0409\3\2\2\2\u0082\u0410\3\2\2\2\u0084\u041a\3\2"+
		"\2\2\u0086\u041d\3\2\2\2\u0088\u0424\3\2\2\2\u008a\u0442\3\2\2\2\u008c"+
		"\u0459\3\2\2\2\u008e\u045b\3\2\2\2\u0090\u0463\3\2\2\2\u0092\u046f\3\2"+
		"\2\2\u0094\u0471\3\2\2\2\u0096\u047c\3\2\2\2\u0098\u0498\3\2\2\2\u009a"+
		"\u049a\3\2\2\2\u009c\u04a8\3\2\2\2\u009e\u04c4\3\2\2\2\u00a0\u04c6\3\2"+
		"\2\2\u00a2\u04db\3\2\2\2\u00a4\u04dd\3\2\2\2\u00a6\u04e0\3\2\2\2\u00a8"+
		"\u04e3\3\2\2\2\u00aa\u04e6\3\2\2\2\u00ac\u050f\3\2\2\2\u00ae\u0511\3\2"+
		"\2\2\u00b0\u0513\3\2\2\2\u00b2\u051e\3\2\2\2\u00b4\u0527\3\2\2\2\u00b6"+
		"\u0536\3\2\2\2\u00b8\u0538\3\2\2\2\u00ba\u056b\3\2\2\2\u00bc\u056d\3\2"+
		"\2\2\u00be\u0573\3\2\2\2\u00c0\u057d\3\2\2\2\u00c2\u05a6\3\2\2\2\u00c4"+
		"\u05a8\3\2\2\2\u00c6\u05af\3\2\2\2\u00c8\u05ba\3\2\2\2\u00ca\u05bc\3\2"+
		"\2\2\u00cc\u05bf\3\2\2\2\u00ce\u05c4\3\2\2\2\u00d0\u05c8\3\2\2\2\u00d2"+
		"\u05cf\3\2\2\2\u00d4\u05d6\3\2\2\2\u00d6\u05e9\3\2\2\2\u00d8\u05eb\3\2"+
		"\2\2\u00da\u05ef\3\2\2\2\u00dc\u05f6\3\2\2\2\u00de\u00e0\5\4\3\2\u00df"+
		"\u00de\3\2\2\2\u00e0\u00e3\3\2\2\2\u00e1\u00df\3\2\2\2\u00e1\u00e2\3\2"+
		"\2\2\u00e2\u00e4\3\2\2\2\u00e3\u00e1\3\2\2\2\u00e4\u00e5\7\2\2\3\u00e5"+
		"\3\3\2\2\2\u00e6\u00ee\5J&\2\u00e7\u00ee\5\u008cG\2\u00e8\u00ee\5\u00bc"+
		"_\2\u00e9\u00ee\5\u00caf\2\u00ea\u00ee\5\u00ccg\2\u00eb\u00ee\5z>\2\u00ec"+
		"\u00ee\5f\64\2\u00ed\u00e6\3\2\2\2\u00ed\u00e7\3\2\2\2\u00ed\u00e8\3\2"+
		"\2\2\u00ed\u00e9\3\2\2\2\u00ed\u00ea\3\2\2\2\u00ed\u00eb\3\2\2\2\u00ed"+
		"\u00ec\3\2\2\2\u00ee\5\3\2\2\2\u00ef\u00f0\7\177\2\2\u00f0\7\3\2\2\2\u00f1"+
		"\u00f4\5\n\6\2\u00f2\u00f4\5\f\7\2\u00f3\u00f1\3\2\2\2\u00f3\u00f2\3\2"+
		"\2\2\u00f4\t\3\2\2\2\u00f5\u00f6\7\177\2\2\u00f6\13\3\2\2\2\u00f7\u00f8"+
		"\7\177\2\2\u00f8\r\3\2\2\2\u00f9\u00fc\7\177\2\2\u00fa\u00fc\5\u00c4c"+
		"\2\u00fb\u00f9\3\2\2\2\u00fb\u00fa\3\2\2\2\u00fc\17\3\2\2\2\u00fd\u00fe"+
		"\7\177\2\2\u00fe\21\3\2\2\2\u00ff\u0100\7\177\2\2\u0100\23\3\2\2\2\u0101"+
		"\u0102\t\2\2\2\u0102\25\3\2\2\2\u0103\u0106\5\30\r\2\u0104\u0106\5\34"+
		"\17\2\u0105\u0103\3\2\2\2\u0105\u0104\3\2\2\2\u0106\27\3\2\2\2\u0107\u010e"+
		"\7\177\2\2\u0108\u010e\5\u00b8]\2\u0109\u010e\5\u00b0Y\2\u010a\u010b\7"+
		"\3\2\2\u010b\u010e\5\16\b\2\u010c\u010e\5\u00c4c\2\u010d\u0107\3\2\2\2"+
		"\u010d\u0108\3\2\2\2\u010d\u0109\3\2\2\2\u010d\u010a\3\2\2\2\u010d\u010c"+
		"\3\2\2\2\u010e\31\3\2\2\2\u010f\u0110\7\4\2\2\u0110\33\3\2\2\2\u0111\u0113"+
		"\7\5\2\2\u0112\u0111\3\2\2\2\u0112\u0113\3\2\2\2\u0113\u0114\3\2\2\2\u0114"+
		"\u0116\5\36\20\2\u0115\u0117\5\32\16\2\u0116\u0115\3\2\2\2\u0116\u0117"+
		"\3\2\2\2\u0117\u0118\3\2\2\2\u0118\u0119\5\30\r\2\u0119\u0121\3\2\2\2"+
		"\u011a\u011b\7\5\2\2\u011b\u0121\7\177\2\2\u011c\u011d\7\5\2\2\u011d\u0121"+
		"\5\u00b8]\2\u011e\u011f\7\5\2\2\u011f\u0121\5\u00c4c\2\u0120\u0112\3\2"+
		"\2\2\u0120\u011a\3\2\2\2\u0120\u011c\3\2\2\2\u0120\u011e\3\2\2\2\u0121"+
		"\35\3\2\2\2\u0122\u0123\5 \21\2\u0123\u0125\7\5\2\2\u0124\u0126\5\36\20"+
		"\2\u0125\u0124\3\2\2\2\u0125\u0126\3\2\2\2\u0126\u012d\3\2\2\2\u0127\u0128"+
		"\5 \21\2\u0128\u0129\7\5\2\2\u0129\u012a\7\4\2\2\u012a\u012b\5\36\20\2"+
		"\u012b\u012d\3\2\2\2\u012c\u0122\3\2\2\2\u012c\u0127\3\2\2\2\u012d\37"+
		"\3\2\2\2\u012e\u0131\5\16\b\2\u012f\u0131\5\b\5\2\u0130\u012e\3\2\2\2"+
		"\u0130\u012f\3\2\2\2\u0131!\3\2\2\2\u0132\u013c\5\24\13\2\u0133\u013c"+
		"\7\6\2\2\u0134\u0135\7\7\2\2\u0135\u0136\5$\23\2\u0136\u0137\7\b\2\2\u0137"+
		"\u013c\3\2\2\2\u0138\u013c\5\26\f\2\u0139\u013c\5*\26\2\u013a\u013c\5"+
		"\64\33\2\u013b\u0132\3\2\2\2\u013b\u0133\3\2\2\2\u013b\u0134\3\2\2\2\u013b"+
		"\u0138\3\2\2\2\u013b\u0139\3\2\2\2\u013b\u013a\3\2\2\2\u013c#\3\2\2\2"+
		"\u013d\u013e\b\23\1\2\u013e\u0194\5\"\22\2\u013f\u0140\t\3\2\2\u0140\u0194"+
		"\5$\23\36\u0141\u0142\7\24\2\2\u0142\u0194\5$\23\33\u0143\u0144\7\24\2"+
		"\2\u0144\u0145\7\7\2\2\u0145\u0146\5\u0086D\2\u0146\u0147\7\b\2\2\u0147"+
		"\u0194\3\2\2\2\u0148\u014a\7\25\2\2\u0149\u014b\7\5\2\2\u014a\u0149\3"+
		"\2\2\2\u014a\u014b\3\2\2\2\u014b\u014c\3\2\2\2\u014c\u014d\5\36\20\2\u014d"+
		"\u014e\7\177\2\2\u014e\u014f\7\7\2\2\u014f\u0150\5&\24\2\u0150\u0151\7"+
		"\b\2\2\u0151\u0194\3\2\2\2\u0152\u0154\7\25\2\2\u0153\u0155\7\5\2\2\u0154"+
		"\u0153\3\2\2\2\u0154\u0155\3\2\2\2\u0155\u0156\3\2\2\2\u0156\u0158\5\36"+
		"\20\2\u0157\u0159\5\32\16\2\u0158\u0157\3\2\2\2\u0158\u0159\3\2\2\2\u0159"+
		"\u015a\3\2\2\2\u015a\u015b\5\u00c4c\2\u015b\u015c\7\7\2\2\u015c\u015d"+
		"\5&\24\2\u015d\u015e\7\b\2\2\u015e\u0194\3\2\2\2\u015f\u0160\5Z.\2\u0160"+
		"\u0161\7\7\2\2\u0161\u0162\5&\24\2\u0162\u0163\7\b\2\2\u0163\u0194\3\2"+
		"\2\2\u0164\u0165\7\26\2\2\u0165\u0166\7\27\2\2\u0166\u0167\5\u0086D\2"+
		"\u0167\u0168\7\30\2\2\u0168\u0169\7\7\2\2\u0169\u016a\5$\23\2\u016a\u016b"+
		"\7\b\2\2\u016b\u0194\3\2\2\2\u016c\u016d\7\31\2\2\u016d\u016e\7\27\2\2"+
		"\u016e\u016f\5\u0086D\2\u016f\u0170\7\30\2\2\u0170\u0171\7\7\2\2\u0171"+
		"\u0172\5$\23\2\u0172\u0173\7\b\2\2\u0173\u0194\3\2\2\2\u0174\u0175\7\32"+
		"\2\2\u0175\u0176\7\27\2\2\u0176\u0177\5\u0086D\2\u0177\u0178\7\30\2\2"+
		"\u0178\u0179\7\7\2\2\u0179\u017a\5$\23\2\u017a\u017b\7\b\2\2\u017b\u0194"+
		"\3\2\2\2\u017c\u017d\7\33\2\2\u017d\u017e\7\27\2\2\u017e\u017f\5\u0086"+
		"D\2\u017f\u0180\7\30\2\2\u0180\u0181\7\7\2\2\u0181\u0182\5$\23\2\u0182"+
		"\u0183\7\b\2\2\u0183\u0194\3\2\2\2\u0184\u0185\7\34\2\2\u0185\u0186\7"+
		"\7\2\2\u0186\u0187\5$\23\2\u0187\u0188\7\b\2\2\u0188\u0194\3\2\2\2\u0189"+
		"\u018a\7\34\2\2\u018a\u018b\7\7\2\2\u018b\u018c\5\u0086D\2\u018c\u018d"+
		"\7\b\2\2\u018d\u0194\3\2\2\2\u018e\u018f\7\7\2\2\u018f\u0190\5\u0086D"+
		"\2\u0190\u0191\7\b\2\2\u0191\u0192\5$\23\17\u0192\u0194\3\2\2\2\u0193"+
		"\u013d\3\2\2\2\u0193\u013f\3\2\2\2\u0193\u0141\3\2\2\2\u0193\u0143\3\2"+
		"\2\2\u0193\u0148\3\2\2\2\u0193\u0152\3\2\2\2\u0193\u015f\3\2\2\2\u0193"+
		"\u0164\3\2\2\2\u0193\u016c\3\2\2\2\u0193\u0174\3\2\2\2\u0193\u017c\3\2"+
		"\2\2\u0193\u0184\3\2\2\2\u0193\u0189\3\2\2\2\u0193\u018e\3\2\2\2\u0194"+
		"\u01ed\3\2\2\2\u0195\u0196\f\16\2\2\u0196\u0197\t\4\2\2\u0197\u01ec\5"+
		"$\23\17\u0198\u0199\f\r\2\2\u0199\u019a\t\5\2\2\u019a\u01ec\5$\23\16\u019b"+
		"\u01a3\f\f\2\2\u019c\u019d\7\27\2\2\u019d\u01a4\7\27\2\2\u019e\u019f\7"+
		"\30\2\2\u019f\u01a0\7\30\2\2\u01a0\u01a4\7\30\2\2\u01a1\u01a2\7\30\2\2"+
		"\u01a2\u01a4\7\30\2\2\u01a3\u019c\3\2\2\2\u01a3\u019e\3\2\2\2\u01a3\u01a1"+
		"\3\2\2\2\u01a4\u01a5\3\2\2\2\u01a5\u01ec\5$\23\r\u01a6\u01a7\f\13\2\2"+
		"\u01a7\u01a8\t\6\2\2\u01a8\u01ec\5$\23\f\u01a9\u01aa\f\n\2\2\u01aa\u01ab"+
		"\t\7\2\2\u01ab\u01ec\5$\23\13\u01ac\u01ad\f\t\2\2\u01ad\u01ae\7\20\2\2"+
		"\u01ae\u01ec\5$\23\n\u01af\u01b0\f\b\2\2\u01b0\u01b1\7#\2\2\u01b1\u01ec"+
		"\5$\23\t\u01b2\u01b3\f\7\2\2\u01b3\u01b4\7$\2\2\u01b4\u01ec\5$\23\b\u01b5"+
		"\u01b6\f\6\2\2\u01b6\u01b7\7%\2\2\u01b7\u01ec\5$\23\7\u01b8\u01b9\f\5"+
		"\2\2\u01b9\u01ba\7&\2\2\u01ba\u01ec\5$\23\6\u01bb\u01bc\f\4\2\2\u01bc"+
		"\u01bd\7\'\2\2\u01bd\u01be\5$\23\2\u01be\u01bf\7(\2\2\u01bf\u01c0\5$\23"+
		"\5\u01c0\u01ec\3\2\2\2\u01c1\u01c2\f\3\2\2\u01c2\u01c3\t\b\2\2\u01c3\u01ec"+
		"\5$\23\3\u01c4\u01c5\f!\2\2\u01c5\u01cb\7\t\2\2\u01c6\u01c8\5\32\16\2"+
		"\u01c7\u01c6\3\2\2\2\u01c7\u01c8\3\2\2\2\u01c8\u01c9\3\2\2\2\u01c9\u01cc"+
		"\5\26\f\2\u01ca\u01cc\5(\25\2\u01cb\u01c7\3\2\2\2\u01cb\u01ca\3\2\2\2"+
		"\u01cc\u01ec\3\2\2\2\u01cd\u01ce\f \2\2\u01ce\u01d4\7\5\2\2\u01cf\u01d1"+
		"\5\32\16\2\u01d0\u01cf\3\2\2\2\u01d0\u01d1\3\2\2\2\u01d1\u01d2\3\2\2\2"+
		"\u01d2\u01d5\5\26\f\2\u01d3\u01d5\5(\25\2\u01d4\u01d0\3\2\2\2\u01d4\u01d3"+
		"\3\2\2\2\u01d5\u01ec\3\2\2\2\u01d6\u01d7\f\37\2\2\u01d7\u01dd\7\n\2\2"+
		"\u01d8\u01da\5\32\16\2\u01d9\u01d8\3\2\2\2\u01d9\u01da\3\2\2\2\u01da\u01db"+
		"\3\2\2\2\u01db\u01de\5\26\f\2\u01dc\u01de\5(\25\2\u01dd\u01d9\3\2\2\2"+
		"\u01dd\u01dc\3\2\2\2\u01de\u01ec\3\2\2\2\u01df\u01e0\f\35\2\2\u01e0\u01e1"+
		"\7\22\2\2\u01e1\u01e2\5&\24\2\u01e2\u01e3\7\23\2\2\u01e3\u01ec\3\2\2\2"+
		"\u01e4\u01e5\f\34\2\2\u01e5\u01e6\7\7\2\2\u01e6\u01e7\5&\24\2\u01e7\u01e8"+
		"\7\b\2\2\u01e8\u01ec\3\2\2\2\u01e9\u01ea\f\26\2\2\u01ea\u01ec\t\t\2\2"+
		"\u01eb\u0195\3\2\2\2\u01eb\u0198\3\2\2\2\u01eb\u019b\3\2\2\2\u01eb\u01a6"+
		"\3\2\2\2\u01eb\u01a9\3\2\2\2\u01eb\u01ac\3\2\2\2\u01eb\u01af\3\2\2\2\u01eb"+
		"\u01b2\3\2\2\2\u01eb\u01b5\3\2\2\2\u01eb\u01b8\3\2\2\2\u01eb\u01bb\3\2"+
		"\2\2\u01eb\u01c1\3\2\2\2\u01eb\u01c4\3\2\2\2\u01eb\u01cd\3\2\2\2\u01eb"+
		"\u01d6\3\2\2\2\u01eb\u01df\3\2\2\2\u01eb\u01e4\3\2\2\2\u01eb\u01e9\3\2"+
		"\2\2\u01ec\u01ef\3\2\2\2\u01ed\u01eb\3\2\2\2\u01ed\u01ee\3\2\2\2\u01ee"+
		"%\3\2\2\2\u01ef\u01ed\3\2\2\2\u01f0\u01f5\5$\23\2\u01f1\u01f2\7\65\2\2"+
		"\u01f2\u01f4\5$\23\2\u01f3\u01f1\3\2\2\2\u01f4\u01f7\3\2\2\2\u01f5\u01f3"+
		"\3\2\2\2\u01f5\u01f6\3\2\2\2\u01f6\'\3\2\2\2\u01f7\u01f5\3\2\2\2\u01f8"+
		"\u01fa\7\5\2\2\u01f9\u01f8\3\2\2\2\u01f9\u01fa\3\2\2\2\u01fa\u01fc\3\2"+
		"\2\2\u01fb\u01fd\5\36\20\2\u01fc\u01fb\3\2\2\2\u01fc\u01fd\3\2\2\2\u01fd"+
		"\u01fe\3\2\2\2\u01fe\u01ff\5\\/\2\u01ff\u0200\7\5\2\2\u0200\u0201\7\3"+
		"\2\2\u0201\u0202\5\\/\2\u0202\u0216\3\2\2\2\u0203\u0205\7\5\2\2\u0204"+
		"\u0203\3\2\2\2\u0204\u0205\3\2\2\2\u0205\u0206\3\2\2\2\u0206\u0207\5\36"+
		"\20\2\u0207\u0208\7\4\2\2\u0208\u0209\5\u00c4c\2\u0209\u020a\7\5\2\2\u020a"+
		"\u020b\7\3\2\2\u020b\u020c\5\\/\2\u020c\u0216\3\2\2\2\u020d\u020f\7\5"+
		"\2\2\u020e\u020d\3\2\2\2\u020e\u020f\3\2\2\2\u020f\u0211\3\2\2\2\u0210"+
		"\u0212\5\36\20\2\u0211\u0210\3\2\2\2\u0211\u0212\3\2\2\2\u0212\u0213\3"+
		"\2\2\2\u0213\u0214\7\3\2\2\u0214\u0216\5\\/\2\u0215\u01f9\3\2\2\2\u0215"+
		"\u0204\3\2\2\2\u0215\u020e\3\2\2\2\u0216)\3\2\2\2\u0217\u0219\7\5\2\2"+
		"\u0218\u0217\3\2\2\2\u0218\u0219\3\2\2\2\u0219\u021a\3\2\2\2\u021a\u021c"+
		"\7\66\2\2\u021b\u021d\5,\27\2\u021c\u021b\3\2\2\2\u021c\u021d\3\2\2\2"+
		"\u021d\u021e\3\2\2\2\u021e\u0220\5.\30\2\u021f\u0221\5\62\32\2\u0220\u021f"+
		"\3\2\2\2\u0220\u0221\3\2\2\2\u0221\u0230\3\2\2\2\u0222\u0224\7\5\2\2\u0223"+
		"\u0222\3\2\2\2\u0223\u0224\3\2\2\2\u0224\u0225\3\2\2\2\u0225\u0227\7\66"+
		"\2\2\u0226\u0228\5,\27\2\u0227\u0226\3\2\2\2\u0227\u0228\3\2\2\2\u0228"+
		"\u0229\3\2\2\2\u0229\u022a\7\7\2\2\u022a\u022b\5\u0086D\2\u022b\u022d"+
		"\7\b\2\2\u022c\u022e\5\62\32\2\u022d\u022c\3\2\2\2\u022d\u022e\3\2\2\2"+
		"\u022e\u0230\3\2\2\2\u022f\u0218\3\2\2\2\u022f\u0223\3\2\2\2\u0230+\3"+
		"\2\2\2\u0231\u0232\7\7\2\2\u0232\u0233\5&\24\2\u0233\u0234\7\b\2\2\u0234"+
		"-\3\2\2\2\u0235\u0237\5X-\2\u0236\u0235\3\2\2\2\u0237\u0238\3\2\2\2\u0238"+
		"\u0236\3\2\2\2\u0238\u0239\3\2\2\2\u0239\u023b\3\2\2\2\u023a\u023c\5\60"+
		"\31\2\u023b\u023a\3\2\2\2\u023b\u023c\3\2\2\2\u023c/\3\2\2\2\u023d\u023e"+
		"\b\31\1\2\u023e\u023f\5~@\2\u023f\u0240\5\60\31\5\u0240\u0246\3\2\2\2"+
		"\u0241\u0242\7\22\2\2\u0242\u0243\5$\23\2\u0243\u0244\7\23\2\2\u0244\u0246"+
		"\3\2\2\2\u0245\u023d\3\2\2\2\u0245\u0241\3\2\2\2\u0246\u024e\3\2\2\2\u0247"+
		"\u0248\f\3\2\2\u0248\u0249\7\22\2\2\u0249\u024a\5$\23\2\u024a\u024b\7"+
		"\23\2\2\u024b\u024d\3\2\2\2\u024c\u0247\3\2\2\2\u024d\u0250\3\2\2\2\u024e"+
		"\u024c\3\2\2\2\u024e\u024f\3\2\2\2\u024f\61\3\2\2\2\u0250\u024e\3\2\2"+
		"\2\u0251\u0253\7\7\2\2\u0252\u0254\5&\24\2\u0253\u0252\3\2\2\2\u0253\u0254"+
		"\3\2\2\2\u0254\u0255\3\2\2\2\u0255\u0256\7\b\2\2\u0256\63\3\2\2\2\u0257"+
		"\u0259\7\5\2\2\u0258\u0257\3\2\2\2\u0258\u0259\3\2\2\2\u0259\u025a\3\2"+
		"\2\2\u025a\u025b\7\67\2\2\u025b\u0264\5$\23\2\u025c\u025e\7\5\2\2\u025d"+
		"\u025c\3\2\2\2\u025d\u025e\3\2\2\2\u025e\u025f\3\2\2\2\u025f\u0260\7\67"+
		"\2\2\u0260\u0261\7\22\2\2\u0261\u0262\7\23\2\2\u0262\u0264\5$\23\2\u0263"+
		"\u0258\3\2\2\2\u0263\u025d\3\2\2\2\u0264\65\3\2\2\2\u0265\u0266\b\34\1"+
		"\2\u0266\u0267\5$\23\2\u0267\u0270\3\2\2\2\u0268\u0269\f\4\2\2\u0269\u026a"+
		"\78\2\2\u026a\u026f\5$\23\2\u026b\u026c\f\3\2\2\u026c\u026d\7\17\2\2\u026d"+
		"\u026f\5$\23\2\u026e\u0268\3\2\2\2\u026e\u026b\3\2\2\2\u026f\u0272\3\2"+
		"\2\2\u0270\u026e\3\2\2\2\u0270\u0271\3\2\2\2\u0271\67\3\2\2\2\u0272\u0270"+
		"\3\2\2\2\u0273\u027c\5:\36\2\u0274\u027c\5<\37\2\u0275\u027c\5> \2\u0276"+
		"\u027c\5@!\2\u0277\u027c\5D#\2\u0278\u027c\5H%\2\u0279\u027c\5J&\2\u027a"+
		"\u027c\5\u00ceh\2\u027b\u0273\3\2\2\2\u027b\u0274\3\2\2\2\u027b\u0275"+
		"\3\2\2\2\u027b\u0276\3\2\2\2\u027b\u0277\3\2\2\2\u027b\u0278\3\2\2\2\u027b"+
		"\u0279\3\2\2\2\u027b\u027a\3\2\2\2\u027c9\3\2\2\2\u027d\u027e\7\177\2"+
		"\2\u027e\u027f\7(\2\2\u027f\u0289\58\35\2\u0280\u0281\79\2\2\u0281\u0282"+
		"\5$\23\2\u0282\u0283\7(\2\2\u0283\u0284\58\35\2\u0284\u0289\3\2\2\2\u0285"+
		"\u0286\7:\2\2\u0286\u0287\7(\2\2\u0287\u0289\58\35\2\u0288\u027d\3\2\2"+
		"\2\u0288\u0280\3\2\2\2\u0288\u0285\3\2\2\2\u0289;\3\2\2\2\u028a\u028b"+
		"\5$\23\2\u028b\u028c\7;\2\2\u028c=\3\2\2\2\u028d\u0291\7<\2\2\u028e\u0290"+
		"\58\35\2\u028f\u028e\3\2\2\2\u0290\u0293\3\2\2\2\u0291\u028f\3\2\2\2\u0291"+
		"\u0292\3\2\2\2\u0292\u0294\3\2\2\2\u0293\u0291\3\2\2\2\u0294\u0295\7="+
		"\2\2\u0295?\3\2\2\2\u0296\u0297\7>\2\2\u0297\u0298\7\7\2\2\u0298\u0299"+
		"\5B\"\2\u0299\u029a\7\b\2\2\u029a\u029b\58\35\2\u029b\u02ab\3\2\2\2\u029c"+
		"\u029d\7>\2\2\u029d\u029e\7\7\2\2\u029e\u029f\5B\"\2\u029f\u02a0\7\b\2"+
		"\2\u02a0\u02a1\58\35\2\u02a1\u02a2\7?\2\2\u02a2\u02a3\58\35\2\u02a3\u02ab"+
		"\3\2\2\2\u02a4\u02a5\7@\2\2\u02a5\u02a6\7\7\2\2\u02a6\u02a7\5B\"\2\u02a7"+
		"\u02a8\7\b\2\2\u02a8\u02a9\58\35\2\u02a9\u02ab\3\2\2\2\u02aa\u0296\3\2"+
		"\2\2\u02aa\u029c\3\2\2\2\u02aa\u02a4\3\2\2\2\u02abA\3\2\2\2\u02ac\u02b7"+
		"\5$\23\2\u02ad\u02af\5X-\2\u02ae\u02ad\3\2\2\2\u02af\u02b0\3\2\2\2\u02b0"+
		"\u02ae\3\2\2\2\u02b0\u02b1\3\2\2\2\u02b1\u02b2\3\2\2\2\u02b2\u02b3\5|"+
		"?\2\u02b3\u02b4\7)\2\2\u02b4\u02b5\5$\23\2\u02b5\u02b7\3\2\2\2\u02b6\u02ac"+
		"\3\2\2\2\u02b6\u02ae\3\2\2\2\u02b7C\3\2\2\2\u02b8\u02b9\7A\2\2\u02b9\u02ba"+
		"\7\7\2\2\u02ba\u02bb\5B\"\2\u02bb\u02bc\7\b\2\2\u02bc\u02bd\58\35\2\u02bd"+
		"\u02d4\3\2\2\2\u02be\u02bf\7B\2\2\u02bf\u02c0\58\35\2\u02c0\u02c1\7A\2"+
		"\2\u02c1\u02c2\7\7\2\2\u02c2\u02c3\5$\23\2\u02c3\u02c4\7\b\2\2\u02c4\u02c5"+
		"\7;\2\2\u02c5\u02d4\3\2\2\2\u02c6\u02c7\7C\2\2\u02c7\u02c8\7\7\2\2\u02c8"+
		"\u02ca\5F$\2\u02c9\u02cb\5B\"\2\u02ca\u02c9\3\2\2\2\u02ca\u02cb\3\2\2"+
		"\2\u02cb\u02cc\3\2\2\2\u02cc\u02ce\7;\2\2\u02cd\u02cf\5$\23\2\u02ce\u02cd"+
		"\3\2\2\2\u02ce\u02cf\3\2\2\2\u02cf\u02d0\3\2\2\2\u02d0\u02d1\7\b\2\2\u02d1"+
		"\u02d2\58\35\2\u02d2\u02d4\3\2\2\2\u02d3\u02b8\3\2\2\2\u02d3\u02be\3\2"+
		"\2\2\u02d3\u02c6\3\2\2\2\u02d4E\3\2\2\2\u02d5\u02d8\5<\37\2\u02d6\u02d8"+
		"\5L\'\2\u02d7\u02d5\3\2\2\2\u02d7\u02d6\3\2\2\2\u02d8G\3\2\2\2\u02d9\u02da"+
		"\7D\2\2\u02da\u02e6\7;\2\2\u02db\u02dc\7E\2\2\u02dc\u02e6\7;\2\2\u02dd"+
		"\u02df\7F\2\2\u02de\u02e0\5$\23\2\u02df\u02de\3\2\2\2\u02df\u02e0\3\2"+
		"\2\2\u02e0\u02e1\3\2\2\2\u02e1\u02e6\7;\2\2\u02e2\u02e3\7G\2\2\u02e3\u02e4"+
		"\7\177\2\2\u02e4\u02e6\7;\2\2\u02e5\u02d9\3\2\2\2\u02e5\u02db\3\2\2\2"+
		"\u02e5\u02dd\3\2\2\2\u02e5\u02e2\3\2\2\2\u02e6I\3\2\2\2\u02e7\u02ed\5"+
		"L\'\2\u02e8\u02ed\5x=\2\u02e9\u02ed\5p9\2\u02ea\u02ed\5t;\2\u02eb\u02ed"+
		"\5v<\2\u02ec\u02e7\3\2\2\2\u02ec\u02e8\3\2\2\2\u02ec\u02e9\3\2\2\2\u02ec"+
		"\u02ea\3\2\2\2\u02ec\u02eb\3\2\2\2\u02edK\3\2\2\2\u02ee\u02f0\5R*\2\u02ef"+
		"\u02ee\3\2\2\2\u02f0\u02f3\3\2\2\2\u02f1\u02ef\3\2\2\2\u02f1\u02f2\3\2"+
		"\2\2\u02f2\u02f5\3\2\2\2\u02f3\u02f1\3\2\2\2\u02f4\u02f6\5N(\2\u02f5\u02f4"+
		"\3\2\2\2\u02f5\u02f6\3\2\2\2\u02f6\u02f7\3\2\2\2\u02f7\u02f8\7;\2\2\u02f8"+
		"M\3\2\2\2\u02f9\u02fe\5P)\2\u02fa\u02fb\7\65\2\2\u02fb\u02fd\5P)\2\u02fc"+
		"\u02fa\3\2\2\2\u02fd\u0300\3\2\2\2\u02fe\u02fc\3\2\2\2\u02fe\u02ff\3\2"+
		"\2\2\u02ffO\3\2\2\2\u0300\u02fe\3\2\2\2\u0301\u0303\5|?\2\u0302\u0304"+
		"\5\u0090I\2\u0303\u0302\3\2\2\2\u0303\u0304\3\2\2\2\u0304Q\3\2\2\2\u0305"+
		"\u030b\5T+\2\u0306\u030b\5X-\2\u0307\u030b\5V,\2\u0308\u030b\7H\2\2\u0309"+
		"\u030b\7I\2\2\u030a\u0305\3\2\2\2\u030a\u0306\3\2\2\2\u030a\u0307\3\2"+
		"\2\2\u030a\u0308\3\2\2\2\u030a\u0309\3\2\2\2\u030bS\3\2\2\2\u030c\u030d"+
		"\t\n\2\2\u030dU\3\2\2\2\u030e\u030f\t\13\2\2\u030fW\3\2\2\2\u0310\u0316"+
		"\5Z.\2\u0311\u0316\5\u0096L\2\u0312\u0316\5`\61\2\u0313\u0316\5^\60\2"+
		"\u0314\u0316\5\u0082B\2\u0315\u0310\3\2\2\2\u0315\u0311\3\2\2\2\u0315"+
		"\u0312\3\2\2\2\u0315\u0313\3\2\2\2\u0315\u0314\3\2\2\2\u0316Y\3\2\2\2"+
		"\u0317\u0319\7\5\2\2\u0318\u0317\3\2\2\2\u0318\u0319\3\2\2\2\u0319\u031b"+
		"\3\2\2\2\u031a\u031c\5\36\20\2\u031b\u031a\3\2\2\2\u031b\u031c\3\2\2\2"+
		"\u031c\u031d\3\2\2\2\u031d\u0331\5\\/\2\u031e\u0320\7\5\2\2\u031f\u031e"+
		"\3\2\2\2\u031f\u0320\3\2\2\2\u0320\u0321\3\2\2\2\u0321\u0322\5\36\20\2"+
		"\u0322\u0323\7\4\2\2\u0323\u0324\5\u00c4c\2\u0324\u0331\3\2\2\2\u0325"+
		"\u0331\7R\2\2\u0326\u0331\7S\2\2\u0327\u0331\7T\2\2\u0328\u0331\7U\2\2"+
		"\u0329\u0331\7V\2\2\u032a\u0331\7W\2\2\u032b\u0331\7X\2\2\u032c\u0331"+
		"\7Y\2\2\u032d\u0331\7Z\2\2\u032e\u0331\7[\2\2\u032f\u0331\7\\\2\2\u0330"+
		"\u0318\3\2\2\2\u0330\u031f\3\2\2\2\u0330\u0325\3\2\2\2\u0330\u0326\3\2"+
		"\2\2\u0330\u0327\3\2\2\2\u0330\u0328\3\2\2\2\u0330\u0329\3\2\2\2\u0330"+
		"\u032a\3\2\2\2\u0330\u032b\3\2\2\2\u0330\u032c\3\2\2\2\u0330\u032d\3\2"+
		"\2\2\u0330\u032e\3\2\2\2\u0330\u032f\3\2\2\2\u0331[\3\2\2\2\u0332\u0336"+
		"\5\16\b\2\u0333\u0336\5\20\t\2\u0334\u0336\5\6\4\2\u0335\u0332\3\2\2\2"+
		"\u0335\u0333\3\2\2\2\u0335\u0334\3\2\2\2\u0336]\3\2\2\2\u0337\u0339\5"+
		"\u009aN\2\u0338\u033a\7\5\2\2\u0339\u0338\3\2\2\2\u0339\u033a\3\2\2\2"+
		"\u033a\u033c\3\2\2\2\u033b\u033d\5\36\20\2\u033c\u033b\3\2\2\2\u033c\u033d"+
		"\3\2\2\2\u033d\u033e\3\2\2\2\u033e\u033f\7\177\2\2\u033f\u035a\3\2\2\2"+
		"\u0340\u0342\7]\2\2\u0341\u0343\7\5\2\2\u0342\u0341\3\2\2\2\u0342\u0343"+
		"\3\2\2\2\u0343\u0345\3\2\2\2\u0344\u0346\5\36\20\2\u0345\u0344\3\2\2\2"+
		"\u0345\u0346\3\2\2\2\u0346\u0347\3\2\2\2\u0347\u035a\7\177\2\2\u0348\u034a"+
		"\7\25\2\2\u0349\u034b\7\5\2\2\u034a\u0349\3\2\2\2\u034a\u034b\3\2\2\2"+
		"\u034b\u034c\3\2\2\2\u034c\u034d\5\36\20\2\u034d\u034e\7\177\2\2\u034e"+
		"\u035a\3\2\2\2\u034f\u0351\7\25\2\2\u0350\u0352\7\5\2\2\u0351\u0350\3"+
		"\2\2\2\u0351\u0352\3\2\2\2\u0352\u0353\3\2\2\2\u0353\u0355\5\36\20\2\u0354"+
		"\u0356\5\32\16\2\u0355\u0354\3\2\2\2\u0355\u0356\3\2\2\2\u0356\u0357\3"+
		"\2\2\2\u0357\u0358\5\u00c4c\2\u0358\u035a\3\2\2\2\u0359\u0337\3\2\2\2"+
		"\u0359\u0340\3\2\2\2\u0359\u0348\3\2\2\2\u0359\u034f\3\2\2\2\u035a_\3"+
		"\2\2\2\u035b\u035d\7]\2\2\u035c\u035e\7\177\2\2\u035d\u035c\3\2\2\2\u035d"+
		"\u035e\3\2\2\2\u035e\u035f\3\2\2\2\u035f\u0361\7<\2\2\u0360\u0362\5b\62"+
		"\2\u0361\u0360\3\2\2\2\u0361\u0362\3\2\2\2\u0362\u0363\3\2\2\2\u0363\u0364"+
		"\7=\2\2\u0364a\3\2\2\2\u0365\u036a\5d\63\2\u0366\u0367\7\65\2\2\u0367"+
		"\u0369\5d\63\2\u0368\u0366\3\2\2\2\u0369\u036c\3\2\2\2\u036a\u0368\3\2"+
		"\2\2\u036a\u036b\3\2\2\2\u036bc\3\2\2\2\u036c\u036a\3\2\2\2\u036d\u0372"+
		"\7\177\2\2\u036e\u036f\7\177\2\2\u036f\u0370\7)\2\2\u0370\u0372\5$\23"+
		"\2\u0371\u036d\3\2\2\2\u0371\u036e\3\2\2\2\u0372e\3\2\2\2\u0373\u0376"+
		"\5h\65\2\u0374\u0376\5n8\2\u0375\u0373\3\2\2\2\u0375\u0374\3\2\2\2\u0376"+
		"g\3\2\2\2\u0377\u037a\5j\66\2\u0378\u037a\5l\67\2\u0379\u0377\3\2\2\2"+
		"\u0379\u0378\3\2\2\2\u037ai\3\2\2\2\u037b\u037c\7^\2\2\u037c\u037d\7\177"+
		"\2\2\u037d\u0381\7<\2\2\u037e\u0380\5\4\3\2\u037f\u037e\3\2\2\2\u0380"+
		"\u0383\3\2\2\2\u0381\u037f\3\2\2\2\u0381\u0382\3\2\2\2\u0382\u0384\3\2"+
		"\2\2\u0383\u0381\3\2\2\2\u0384\u0385\7=\2\2\u0385k\3\2\2\2\u0386\u0387"+
		"\7^\2\2\u0387\u0388\5\n\6\2\u0388\u038c\7<\2\2\u0389\u038b\5\4\3\2\u038a"+
		"\u0389\3\2\2\2\u038b\u038e\3\2\2\2\u038c\u038a\3\2\2\2\u038c\u038d\3\2"+
		"\2\2\u038d\u038f\3\2\2\2\u038e\u038c\3\2\2\2\u038f\u0390\7=\2\2\u0390"+
		"m\3\2\2\2\u0391\u0392\7^\2\2\u0392\u0396\7<\2\2\u0393\u0395\5\4\3\2\u0394"+
		"\u0393\3\2\2\2\u0395\u0398\3\2\2\2\u0396\u0394\3\2\2\2\u0396\u0397\3\2"+
		"\2\2\u0397\u0399\3\2\2\2\u0398\u0396\3\2\2\2\u0399\u039a\7=\2\2\u039a"+
		"o\3\2\2\2\u039b\u039c\7^\2\2\u039c\u039d\7\177\2\2\u039d\u039e\7)\2\2"+
		"\u039e\u039f\5r:\2\u039f\u03a0\7;\2\2\u03a0q\3\2\2\2\u03a1\u03a3\7\5\2"+
		"\2\u03a2\u03a1\3\2\2\2\u03a2\u03a3\3\2\2\2\u03a3\u03a5\3\2\2\2\u03a4\u03a6"+
		"\5\36\20\2\u03a5\u03a4\3\2\2\2\u03a5\u03a6\3\2\2\2\u03a6\u03a7\3\2\2\2"+
		"\u03a7\u03a8\5\b\5\2\u03a8s\3\2\2\2\u03a9\u03ab\7_\2\2\u03aa\u03ac\7\25"+
		"\2\2\u03ab\u03aa\3\2\2\2\u03ab\u03ac\3\2\2\2\u03ac\u03ae\3\2\2\2\u03ad"+
		"\u03af\7\5\2\2\u03ae\u03ad\3\2\2\2\u03ae\u03af\3\2\2\2\u03af\u03b0\3\2"+
		"\2\2\u03b0\u03b1\5\36\20\2\u03b1\u03b2\5\30\r\2\u03b2\u03b3\7;\2\2\u03b3"+
		"\u03ba\3\2\2\2\u03b4\u03b5\7_\2\2\u03b5\u03b6\7\5\2\2\u03b6\u03b7\5\30"+
		"\r\2\u03b7\u03b8\7;\2\2\u03b8\u03ba\3\2\2\2\u03b9\u03a9\3\2\2\2\u03b9"+
		"\u03b4\3\2\2\2\u03bau\3\2\2\2\u03bb\u03bc\7_\2\2\u03bc\u03be\7^\2\2\u03bd"+
		"\u03bf\7\5\2\2\u03be\u03bd\3\2\2\2\u03be\u03bf\3\2\2\2\u03bf\u03c1\3\2"+
		"\2\2\u03c0\u03c2\5\36\20\2\u03c1\u03c0\3\2\2\2\u03c1\u03c2\3\2\2\2\u03c2"+
		"\u03c3\3\2\2\2\u03c3\u03c4\5\b\5\2\u03c4\u03c5\7;\2\2\u03c5w\3\2\2\2\u03c6"+
		"\u03c7\7`\2\2\u03c7\u03c8\7\7\2\2\u03c8\u03c9\7z\2\2\u03c9\u03ca\7\b\2"+
		"\2\u03ca\u03cb\7;\2\2\u03cby\3\2\2\2\u03cc\u03cd\7M\2\2\u03cd\u03ce\7"+
		"z\2\2\u03ce\u03d2\7<\2\2\u03cf\u03d1\5\4\3\2\u03d0\u03cf\3\2\2\2\u03d1"+
		"\u03d4\3\2\2\2\u03d2\u03d0\3\2\2\2\u03d2\u03d3\3\2\2\2\u03d3\u03d5\3\2"+
		"\2\2\u03d4\u03d2\3\2\2\2\u03d5\u03da\7=\2\2\u03d6\u03d7\7M\2\2\u03d7\u03d8"+
		"\7z\2\2\u03d8\u03da\5\4\3\2\u03d9\u03cc\3\2\2\2\u03d9\u03d6\3\2\2\2\u03da"+
		"{\3\2\2\2\u03db\u03dd\5\u0084C\2\u03dc\u03db\3\2\2\2\u03dd\u03de\3\2\2"+
		"\2\u03de\u03dc\3\2\2\2\u03de\u03df\3\2\2\2\u03df\u03e0\3\2\2\2\u03e0\u03e1"+
		"\7\7\2\2\u03e1\u03e2\5\u0088E\2\u03e2\u03e4\7\b\2\2\u03e3\u03e5\5\u0080"+
		"A\2\u03e4\u03e3\3\2\2\2\u03e4\u03e5\3\2\2\2\u03e5\u03e7\3\2\2\2\u03e6"+
		"\u03e8\5\u00dan\2\u03e7\u03e6\3\2\2\2\u03e7\u03e8\3\2\2\2\u03e8\u03f9"+
		"\3\2\2\2\u03e9\u03eb\5\u0084C\2\u03ea\u03e9\3\2\2\2\u03eb\u03ec\3\2\2"+
		"\2\u03ec\u03ea\3\2\2\2\u03ec\u03ed\3\2\2\2\u03ed\u03ee\3\2\2\2\u03ee\u03f0"+
		"\7\22\2\2\u03ef\u03f1\5$\23\2\u03f0\u03ef\3\2\2\2\u03f0\u03f1\3\2\2\2"+
		"\u03f1\u03f2\3\2\2\2\u03f2\u03f3\7\23\2\2\u03f3\u03f9\3\2\2\2\u03f4\u03f5"+
		"\7\7\2\2\u03f5\u03f6\5|?\2\u03f6\u03f7\7\b\2\2\u03f7\u03f9\3\2\2\2\u03f8"+
		"\u03dc\3\2\2\2\u03f8\u03ea\3\2\2\2\u03f8\u03f4\3\2\2\2\u03f9}\3\2\2\2"+
		"\u03fa\u03fc\7\17\2\2\u03fb\u03fd\5\u0080A\2\u03fc\u03fb\3\2\2\2\u03fc"+
		"\u03fd\3\2\2\2\u03fd\u0408\3\2\2\2\u03fe\u0408\7\20\2\2\u03ff\u0401\7"+
		"\5\2\2\u0400\u03ff\3\2\2\2\u0400\u0401\3\2\2\2\u0401\u0402\3\2\2\2\u0402"+
		"\u0403\5\36\20\2\u0403\u0405\7\17\2\2\u0404\u0406\5\u0080A\2\u0405\u0404"+
		"\3\2\2\2\u0405\u0406\3\2\2\2\u0406\u0408\3\2\2\2\u0407\u03fa\3\2\2\2\u0407"+
		"\u03fe\3\2\2\2\u0407\u0400\3\2\2\2\u0408\177\3\2\2\2\u0409\u040d\5\u0082"+
		"B\2\u040a\u040c\5\u0082B\2\u040b\u040a\3\2\2\2\u040c\u040f\3\2\2\2\u040d"+
		"\u040b\3\2\2\2\u040d\u040e\3\2\2\2\u040e\u0081\3\2\2\2\u040f\u040d\3\2"+
		"\2\2\u0410\u0411\t\f\2\2\u0411\u0083\3\2\2\2\u0412\u041b\5\26\f\2\u0413"+
		"\u0415\7\5\2\2\u0414\u0413\3\2\2\2\u0414\u0415\3\2\2\2\u0415\u0417\3\2"+
		"\2\2\u0416\u0418\5\36\20\2\u0417\u0416\3\2\2\2\u0417\u0418\3\2\2\2\u0418"+
		"\u0419\3\2\2\2\u0419\u041b\5\\/\2\u041a\u0412\3\2\2\2\u041a\u0414\3\2"+
		"\2\2\u041b\u0085\3\2\2\2\u041c\u041e\5X-\2\u041d\u041c\3\2\2\2\u041e\u041f"+
		"\3\2\2\2\u041f\u041d\3\2\2\2\u041f\u0420\3\2\2\2\u0420\u0422\3\2\2\2\u0421"+
		"\u0423\5|?\2\u0422\u0421\3\2\2\2\u0422\u0423\3\2\2\2\u0423\u0087\3\2\2"+
		"\2\u0424\u0429\5\u008aF\2\u0425\u0426\7\65\2\2\u0426\u0428\5\u008aF\2"+
		"\u0427\u0425\3\2\2\2\u0428\u042b\3\2\2\2\u0429\u0427\3\2\2\2\u0429\u042a"+
		"\3\2\2\2\u042a\u042d\3\2\2\2\u042b\u0429\3\2\2\2\u042c\u042e\7c\2\2\u042d"+
		"\u042c\3\2\2\2\u042d\u042e\3\2\2\2\u042e\u0089\3\2\2\2\u042f\u0431\5R"+
		"*\2\u0430\u042f\3\2\2\2\u0431\u0432\3\2\2\2\u0432\u0430\3\2\2\2\u0432"+
		"\u0433\3\2\2\2\u0433\u0435\3\2\2\2\u0434\u0436\5|?\2\u0435\u0434\3\2\2"+
		"\2\u0435\u0436\3\2\2\2\u0436\u0443\3\2\2\2\u0437\u0439\5R*\2\u0438\u0437"+
		"\3\2\2\2\u0439\u043a\3\2\2\2\u043a\u0438\3\2\2\2\u043a\u043b\3\2\2\2\u043b"+
		"\u043d\3\2\2\2\u043c\u043e\5|?\2\u043d\u043c\3\2\2\2\u043d\u043e\3\2\2"+
		"\2\u043e\u043f\3\2\2\2\u043f\u0440\7)\2\2\u0440\u0441\5$\23\2\u0441\u0443"+
		"\3\2\2\2\u0442\u0430\3\2\2\2\u0442\u0438\3\2\2\2\u0443\u008b\3\2\2\2\u0444"+
		"\u0446\5R*\2\u0445\u0444\3\2\2\2\u0446\u0449\3\2\2\2\u0447\u0445\3\2\2"+
		"\2\u0447\u0448\3\2\2\2\u0448\u044a\3\2\2\2\u0449\u0447\3\2\2\2\u044a\u044c"+
		"\5|?\2\u044b\u044d\5\u00b2Z\2\u044c\u044b\3\2\2\2\u044c\u044d\3\2\2\2"+
		"\u044d\u044e\3\2\2\2\u044e\u044f\5\u008eH\2\u044f\u045a\3\2\2\2\u0450"+
		"\u0452\5R*\2\u0451\u0450\3\2\2\2\u0452\u0455\3\2\2\2\u0453\u0451\3\2\2"+
		"\2\u0453\u0454\3\2\2\2\u0454\u0456\3\2\2\2\u0455\u0453\3\2\2\2\u0456\u0457"+
		"\5|?\2\u0457\u0458\5\u00d0i\2\u0458\u045a\3\2\2\2\u0459\u0447\3\2\2\2"+
		"\u0459\u0453\3\2\2\2\u045a\u008d\3\2\2\2\u045b\u045c\5> \2\u045c\u008f"+
		"\3\2\2\2\u045d\u045e\7)\2\2\u045e\u0464\5\u0092J\2\u045f\u0460\7\7\2\2"+
		"\u0460\u0461\5&\24\2\u0461\u0462\7\b\2\2\u0462\u0464\3\2\2\2\u0463\u045d"+
		"\3\2\2\2\u0463\u045f\3\2\2\2\u0464\u0091\3\2\2\2\u0465\u0470\5$\23\2\u0466"+
		"\u0467\7<\2\2\u0467\u0469\5\u0094K\2\u0468\u046a\7\65\2\2\u0469\u0468"+
		"\3\2\2\2\u0469\u046a\3\2\2\2\u046a\u046b\3\2\2\2\u046b\u046c\7=\2\2\u046c"+
		"\u0470\3\2\2\2\u046d\u046e\7<\2\2\u046e\u0470\7=\2\2\u046f\u0465\3\2\2"+
		"\2\u046f\u0466\3\2\2\2\u046f\u046d\3\2\2\2\u0470\u0093\3\2\2\2\u0471\u0472"+
		"\bK\1\2\u0472\u0473\5\u0092J\2\u0473\u0479\3\2\2\2\u0474\u0475\f\3\2\2"+
		"\u0475\u0476\7\65\2\2\u0476\u0478\5\u0092J\2\u0477\u0474\3\2\2\2\u0478"+
		"\u047b\3\2\2\2\u0479\u0477\3\2\2\2\u0479\u047a\3\2\2\2\u047a\u0095\3\2"+
		"\2\2\u047b\u0479\3\2\2\2\u047c\u047d\5\u0098M\2\u047d\u047f\7<\2\2\u047e"+
		"\u0480\5\u009cO\2\u047f\u047e\3\2\2\2\u047f\u0480\3\2\2\2\u0480\u0481"+
		"\3\2\2\2\u0481\u0482\7=\2\2\u0482\u0097\3\2\2\2\u0483\u0485\5\u009aN\2"+
		"\u0484\u0486\7\177\2\2\u0485\u0484\3\2\2\2\u0485\u0486\3\2\2\2\u0486\u0488"+
		"\3\2\2\2\u0487\u0489\5\u00a8U\2\u0488\u0487\3\2\2\2\u0488\u0489\3\2\2"+
		"\2\u0489\u0499\3\2\2\2\u048a\u048b\5\u009aN\2\u048b\u048c\5\36\20\2\u048c"+
		"\u048e\7\177\2\2\u048d\u048f\5\u00a8U\2\u048e\u048d\3\2\2\2\u048e\u048f"+
		"\3\2\2\2\u048f\u0499\3\2\2\2\u0490\u0492\5\u009aN\2\u0491\u0493\5\36\20"+
		"\2\u0492\u0491\3\2\2\2\u0492\u0493\3\2\2\2\u0493\u0494\3\2\2\2\u0494\u0496"+
		"\5\u00c4c\2\u0495\u0497\5\u00a8U\2\u0496\u0495\3\2\2\2\u0496\u0497\3\2"+
		"\2\2\u0497\u0499\3\2\2\2\u0498\u0483\3\2\2\2\u0498\u048a\3\2\2\2\u0498"+
		"\u0490\3\2\2\2\u0499\u0099\3\2\2\2\u049a\u049b\t\r\2\2\u049b\u009b\3\2"+
		"\2\2\u049c\u04a0\5\u009eP\2\u049d\u049f\5\u009eP\2\u049e\u049d\3\2\2\2"+
		"\u049f\u04a2\3\2\2\2\u04a0\u049e\3\2\2\2\u04a0\u04a1\3\2\2\2\u04a1\u04a9"+
		"\3\2\2\2\u04a2\u04a0\3\2\2\2\u04a3\u04a4\5\u00aeX\2\u04a4\u04a6\7(\2\2"+
		"\u04a5\u04a7\5\u009cO\2\u04a6\u04a5\3\2\2\2\u04a6\u04a7\3\2\2\2\u04a7"+
		"\u04a9\3\2\2\2\u04a8\u049c\3\2\2\2\u04a8\u04a3\3\2\2\2\u04a9\u009d\3\2"+
		"\2\2\u04aa\u04ac\5\u008cG\2\u04ab\u04ad\7;\2\2\u04ac\u04ab\3\2\2\2\u04ac"+
		"\u04ad\3\2\2\2\u04ad\u04c5\3\2\2\2\u04ae\u04b0\5R*\2\u04af\u04ae\3\2\2"+
		"\2\u04b0\u04b3\3\2\2\2\u04b1\u04af\3\2\2\2\u04b1\u04b2\3\2\2\2\u04b2\u04b5"+
		"\3\2\2\2\u04b3\u04b1\3\2\2\2\u04b4\u04b6\5\u00a0Q\2\u04b5\u04b4\3\2\2"+
		"\2\u04b5\u04b6\3\2\2\2\u04b6\u04b7\3\2\2\2\u04b7\u04c5\7;\2\2\u04b8\u04ba"+
		"\7\5\2\2\u04b9\u04b8\3\2\2\2\u04b9\u04ba\3\2\2\2\u04ba\u04bb\3\2\2\2\u04bb"+
		"\u04bd\5\36\20\2\u04bc\u04be\5\32\16\2\u04bd\u04bc\3\2\2\2\u04bd\u04be"+
		"\3\2\2\2\u04be\u04bf\3\2\2\2\u04bf\u04c0\5\30\r\2\u04c0\u04c1\7;\2\2\u04c1"+
		"\u04c5\3\2\2\2\u04c2\u04c5\5t;\2\u04c3\u04c5\5\u00bc_\2\u04c4\u04aa\3"+
		"\2\2\2\u04c4\u04b1\3\2\2\2\u04c4\u04b9\3\2\2\2\u04c4\u04c2\3\2\2\2\u04c4"+
		"\u04c3\3\2\2\2\u04c5\u009f\3\2\2\2\u04c6\u04cb\5\u00a2R\2\u04c7\u04c8"+
		"\7\65\2\2\u04c8\u04ca\5\u00a2R\2\u04c9\u04c7\3\2\2\2\u04ca\u04cd\3\2\2"+
		"\2\u04cb\u04c9\3\2\2\2\u04cb\u04cc\3\2\2\2\u04cc\u00a1\3\2\2\2\u04cd\u04cb"+
		"\3\2\2\2\u04ce\u04d0\5|?\2\u04cf\u04d1\5\u00a4S\2\u04d0\u04cf\3\2\2\2"+
		"\u04d0\u04d1\3\2\2\2\u04d1\u04dc\3\2\2\2\u04d2\u04d4\5|?\2\u04d3\u04d5"+
		"\5\u00a6T\2\u04d4\u04d3\3\2\2\2\u04d4\u04d5\3\2\2\2\u04d5\u04dc\3\2\2"+
		"\2\u04d6\u04d8\7\177\2\2\u04d7\u04d6\3\2\2\2\u04d7\u04d8\3\2\2\2\u04d8"+
		"\u04d9\3\2\2\2\u04d9\u04da\7(\2\2\u04da\u04dc\5$\23\2\u04db\u04ce\3\2"+
		"\2\2\u04db\u04d2\3\2\2\2\u04db\u04d7\3\2\2\2\u04dc\u00a3\3\2\2\2\u04dd"+
		"\u04de\7)\2\2\u04de\u04df\7r\2\2\u04df\u00a5\3\2\2\2\u04e0\u04e1\7)\2"+
		"\2\u04e1\u04e2\5$\23\2\u04e2\u00a7\3\2\2\2\u04e3\u04e4\7(\2\2\u04e4\u04e5"+
		"\5\u00aaV\2\u04e5\u00a9\3\2\2\2\u04e6\u04e7\bV\1\2\u04e7\u04e8\5\u00ac"+
		"W\2\u04e8\u04ee\3\2\2\2\u04e9\u04ea\f\3\2\2\u04ea\u04eb\7\65\2\2\u04eb"+
		"\u04ed\5\u00acW\2\u04ec\u04e9\3\2\2\2\u04ed\u04f0\3\2\2\2\u04ee\u04ec"+
		"\3\2\2\2\u04ee\u04ef\3\2\2\2\u04ef\u00ab\3\2\2\2\u04f0\u04ee\3\2\2\2\u04f1"+
		"\u04f3\7\5\2\2\u04f2\u04f1\3\2\2\2\u04f2\u04f3\3\2\2\2\u04f3\u04f5\3\2"+
		"\2\2\u04f4\u04f6\5\36\20\2\u04f5\u04f4\3\2\2\2\u04f5\u04f6\3\2\2\2\u04f6"+
		"\u04f7\3\2\2\2\u04f7\u0510\5\16\b\2\u04f8\u04fa\7P\2\2\u04f9\u04fb\5\u00ae"+
		"X\2\u04fa\u04f9\3\2\2\2\u04fa\u04fb\3\2\2\2\u04fb\u04fd\3\2\2\2\u04fc"+
		"\u04fe\7\5\2\2\u04fd\u04fc\3\2\2\2\u04fd\u04fe\3\2\2\2\u04fe\u0500\3\2"+
		"\2\2\u04ff\u0501\5\36\20\2\u0500\u04ff\3\2\2\2\u0500\u0501\3\2\2\2\u0501"+
		"\u0502\3\2\2\2\u0502\u0510\5\16\b\2\u0503\u0505\5\u00aeX\2\u0504\u0506"+
		"\7P\2\2\u0505\u0504\3\2\2\2\u0505\u0506\3\2\2\2\u0506\u0508\3\2\2\2\u0507"+
		"\u0509\7\5\2\2\u0508\u0507\3\2\2\2\u0508\u0509\3\2\2\2\u0509\u050b\3\2"+
		"\2\2\u050a\u050c\5\36\20\2\u050b\u050a\3\2\2\2\u050b\u050c\3\2\2\2\u050c"+
		"\u050d\3\2\2\2\u050d\u050e\5\16\b\2\u050e\u0510\3\2\2\2\u050f\u04f2\3"+
		"\2\2\2\u050f\u04f8\3\2\2\2\u050f\u0503\3\2\2\2\u0510\u00ad\3\2\2\2\u0511"+
		"\u0512\t\16\2\2\u0512\u00af\3\2\2\2\u0513\u0515\7j\2\2\u0514\u0516\5X"+
		"-\2\u0515\u0514\3\2\2\2\u0516\u0517\3\2\2\2\u0517\u0515\3\2\2\2\u0517"+
		"\u0518\3\2\2\2\u0518\u051a\3\2\2\2\u0519\u051b\5~@\2\u051a\u0519\3\2\2"+
		"\2\u051b\u051c\3\2\2\2\u051c\u051a\3\2\2\2\u051c\u051d\3\2\2\2\u051d\u00b1"+
		"\3\2\2\2\u051e\u051f\7(\2\2\u051f\u0524\5\u00b4[\2\u0520\u0521\7\65\2"+
		"\2\u0521\u0523\5\u00b4[\2\u0522\u0520\3\2\2\2\u0523\u0526\3\2\2\2\u0524"+
		"\u0522\3\2\2\2\u0524\u0525\3\2\2\2\u0525\u00b3\3\2\2\2\u0526\u0524\3\2"+
		"\2\2\u0527\u0528\5\u00b6\\\2\u0528\u052a\7\7\2\2\u0529\u052b\5&\24\2\u052a"+
		"\u0529\3\2\2\2\u052a\u052b\3\2\2\2\u052b\u052c\3\2\2\2\u052c\u052d\7\b"+
		"\2\2\u052d\u00b5\3\2\2\2\u052e\u0530\7\5\2\2\u052f\u052e\3\2\2\2\u052f"+
		"\u0530\3\2\2\2\u0530\u0532\3\2\2\2\u0531\u0533\5\36\20\2\u0532\u0531\3"+
		"\2\2\2\u0532\u0533\3\2\2\2\u0533\u0534\3\2\2\2\u0534\u0537\5\16\b\2\u0535"+
		"\u0537\7\177\2\2\u0536\u052f\3\2\2\2\u0536\u0535\3\2\2\2\u0537\u00b7\3"+
		"\2\2\2\u0538\u0539\7j\2\2\u0539\u053a\5\u00ba^\2\u053a\u00b9\3\2\2\2\u053b"+
		"\u056c\7\66\2\2\u053c\u056c\7\67\2\2\u053d\u053e\7\66\2\2\u053e\u053f"+
		"\7\22\2\2\u053f\u056c\7\23\2\2\u0540\u0541\7\67\2\2\u0541\u0542\7\22\2"+
		"\2\u0542\u056c\7\23\2\2\u0543\u056c\7\13\2\2\u0544\u056c\7\f\2\2\u0545"+
		"\u056c\7\17\2\2\u0546\u056c\7\35\2\2\u0547\u056c\7\36\2\2\u0548\u056c"+
		"\7#\2\2\u0549\u056c\7\20\2\2\u054a\u056c\7$\2\2\u054b\u056c\7\3\2\2\u054c"+
		"\u056c\7\21\2\2\u054d\u056c\7)\2\2\u054e\u056c\7\27\2\2\u054f\u056c\7"+
		"\30\2\2\u0550\u056c\7*\2\2\u0551\u056c\7+\2\2\u0552\u056c\7,\2\2\u0553"+
		"\u056c\7-\2\2\u0554\u056c\7\64\2\2\u0555\u056c\7\60\2\2\u0556\u056c\7"+
		".\2\2\u0557\u056c\7/\2\2\u0558\u056c\7k\2\2\u0559\u056c\7l\2\2\u055a\u056c"+
		"\7\61\2\2\u055b\u056c\7\63\2\2\u055c\u056c\7!\2\2\u055d\u056c\7\"\2\2"+
		"\u055e\u056c\7\37\2\2\u055f\u056c\7 \2\2\u0560\u056c\7%\2\2\u0561\u056c"+
		"\7&\2\2\u0562\u056c\7\r\2\2\u0563\u056c\7\16\2\2\u0564\u056c\7\65\2\2"+
		"\u0565\u056c\7\17\2\2\u0566\u056c\7\n\2\2\u0567\u0568\7\7\2\2\u0568\u056c"+
		"\7\b\2\2\u0569\u056a\7\22\2\2\u056a\u056c\7\23\2\2\u056b\u053b\3\2\2\2"+
		"\u056b\u053c\3\2\2\2\u056b\u053d\3\2\2\2\u056b\u0540\3\2\2\2\u056b\u0543"+
		"\3\2\2\2\u056b\u0544\3\2\2\2\u056b\u0545\3\2\2\2\u056b\u0546\3\2\2\2\u056b"+
		"\u0547\3\2\2\2\u056b\u0548\3\2\2\2\u056b\u0549\3\2\2\2\u056b\u054a\3\2"+
		"\2\2\u056b\u054b\3\2\2\2\u056b\u054c\3\2\2\2\u056b\u054d\3\2\2\2\u056b"+
		"\u054e\3\2\2\2\u056b\u054f\3\2\2\2\u056b\u0550\3\2\2\2\u056b\u0551\3\2"+
		"\2\2\u056b\u0552\3\2\2\2\u056b\u0553\3\2\2\2\u056b\u0554\3\2\2\2\u056b"+
		"\u0555\3\2\2\2\u056b\u0556\3\2\2\2\u056b\u0557\3\2\2\2\u056b\u0558\3\2"+
		"\2\2\u056b\u0559\3\2\2\2\u056b\u055a\3\2\2\2\u056b\u055b\3\2\2\2\u056b"+
		"\u055c\3\2\2\2\u056b\u055d\3\2\2\2\u056b\u055e\3\2\2\2\u056b\u055f\3\2"+
		"\2\2\u056b\u0560\3\2\2\2\u056b\u0561\3\2\2\2\u056b\u0562\3\2\2\2\u056b"+
		"\u0563\3\2\2\2\u056b\u0564\3\2\2\2\u056b\u0565\3\2\2\2\u056b\u0566\3\2"+
		"\2\2\u056b\u0567\3\2\2\2\u056b\u0569\3\2\2\2\u056c\u00bb\3\2\2\2\u056d"+
		"\u056e\7\4\2\2\u056e\u056f\7\27\2\2\u056f\u0570\5\u00be`\2\u0570\u0571"+
		"\7\30\2\2\u0571\u0572\5\4\3\2\u0572\u00bd\3\2\2\2\u0573\u0578\5\u00c0"+
		"a\2\u0574\u0575\7\65\2\2\u0575\u0577\5\u00c0a\2\u0576\u0574\3\2\2\2\u0577"+
		"\u057a\3\2\2\2\u0578\u0576\3\2\2\2\u0578\u0579\3\2\2\2\u0579\u00bf\3\2"+
		"\2\2\u057a\u0578\3\2\2\2\u057b\u057e\5\u00c2b\2\u057c\u057e\5\u008aF\2"+
		"\u057d\u057b\3\2\2\2\u057d\u057c\3\2\2\2\u057e\u00c1\3\2\2\2\u057f\u0581"+
		"\7d\2\2\u0580\u0582\7\177\2\2\u0581\u0580\3\2\2\2\u0581\u0582\3\2\2\2"+
		"\u0582\u05a7\3\2\2\2\u0583\u0585\7d\2\2\u0584\u0586\7\177\2\2\u0585\u0584"+
		"\3\2\2\2\u0585\u0586\3\2\2\2\u0586\u0587\3\2\2\2\u0587\u0588\7)\2\2\u0588"+
		"\u05a7\5\u0086D\2\u0589\u058b\7\25\2\2\u058a\u058c\7\177\2\2\u058b\u058a"+
		"\3\2\2\2\u058b\u058c\3\2\2\2\u058c\u05a7\3\2\2\2\u058d\u058f\7\25\2\2"+
		"\u058e\u0590\7\177\2\2\u058f\u058e\3\2\2\2\u058f\u0590\3\2\2\2\u0590\u0591"+
		"\3\2\2\2\u0591\u0592\7)\2\2\u0592\u05a7\5\u0086D\2\u0593\u0594\7\4\2\2"+
		"\u0594\u0595\7\27\2\2\u0595\u0596\5\u00be`\2\u0596\u0597\7\30\2\2\u0597"+
		"\u0599\7d\2\2\u0598\u059a\7\177\2\2\u0599\u0598\3\2\2\2\u0599\u059a\3"+
		"\2\2\2\u059a\u05a7\3\2\2\2\u059b\u059c\7\4\2\2\u059c\u059d\7\27\2\2\u059d"+
		"\u059e\5\u00be`\2\u059e\u059f\7\30\2\2\u059f\u05a1\7d\2\2\u05a0\u05a2"+
		"\7\177\2\2\u05a1\u05a0\3\2\2\2\u05a1\u05a2\3\2\2\2\u05a2\u05a3\3\2\2\2"+
		"\u05a3\u05a4\7)\2\2\u05a4\u05a5\5\26\f\2\u05a5\u05a7\3\2\2\2\u05a6\u057f"+
		"\3\2\2\2\u05a6\u0583\3\2\2\2\u05a6\u0589\3\2\2\2\u05a6\u058d\3\2\2\2\u05a6"+
		"\u0593\3\2\2\2\u05a6\u059b\3\2\2\2\u05a7\u00c3\3\2\2\2\u05a8\u05a9\5\22"+
		"\n\2\u05a9\u05ab\7\27\2\2\u05aa\u05ac\5\u00c6d\2\u05ab\u05aa\3\2\2\2\u05ab"+
		"\u05ac\3\2\2\2\u05ac\u05ad\3\2\2\2\u05ad\u05ae\7\30\2\2\u05ae\u00c5\3"+
		"\2\2\2\u05af\u05b4\5\u00c8e\2\u05b0\u05b1\7\65\2\2\u05b1\u05b3\5\u00c8"+
		"e\2\u05b2\u05b0\3\2\2\2\u05b3\u05b6\3\2\2\2\u05b4\u05b2\3\2\2\2\u05b4"+
		"\u05b5\3\2\2\2\u05b5\u00c7\3\2\2\2\u05b6\u05b4\3\2\2\2\u05b7\u05bb\5$"+
		"\23\2\u05b8\u05bb\5\u0086D\2\u05b9\u05bb\5\26\f\2\u05ba\u05b7\3\2\2\2"+
		"\u05ba\u05b8\3\2\2\2\u05ba\u05b9\3\2\2\2\u05bb\u00c9\3\2\2\2\u05bc\u05bd"+
		"\7\4\2\2\u05bd\u05be\5\4\3\2\u05be\u00cb\3\2\2\2\u05bf\u05c0\7\4\2\2\u05c0"+
		"\u05c1\7\27\2\2\u05c1\u05c2\7\30\2\2\u05c2\u05c3\5\4\3\2\u05c3\u00cd\3"+
		"\2\2\2\u05c4\u05c5\7m\2\2\u05c5\u05c6\5> \2\u05c6\u05c7\5\u00d2j\2\u05c7"+
		"\u00cf\3\2\2\2\u05c8\u05ca\7m\2\2\u05c9\u05cb\5\u00b2Z\2\u05ca\u05c9\3"+
		"\2\2\2\u05ca\u05cb\3\2\2\2\u05cb\u05cc\3\2\2\2\u05cc\u05cd\5\u008eH\2"+
		"\u05cd\u05ce\5\u00d2j\2\u05ce\u00d1\3\2\2\2\u05cf\u05d3\5\u00d4k\2\u05d0"+
		"\u05d2\5\u00d4k\2\u05d1\u05d0\3\2\2\2\u05d2\u05d5\3\2\2\2\u05d3\u05d1"+
		"\3\2\2\2\u05d3\u05d4\3\2\2\2\u05d4\u00d3\3\2\2\2\u05d5\u05d3\3\2\2\2\u05d6"+
		"\u05d7\7n\2\2\u05d7\u05d8\7\7\2\2\u05d8\u05d9\5\u00d6l\2\u05d9\u05da\7"+
		"\b\2\2\u05da\u05db\5> \2\u05db\u00d5\3\2\2\2\u05dc\u05de\5X-\2\u05dd\u05dc"+
		"\3\2\2\2\u05de\u05df\3\2\2\2\u05df\u05dd\3\2\2\2\u05df\u05e0\3\2\2\2\u05e0"+
		"\u05e1\3\2\2\2\u05e1\u05e2\5|?\2\u05e2\u05ea\3\2\2\2\u05e3\u05e5\5X-\2"+
		"\u05e4\u05e3\3\2\2\2\u05e5\u05e6\3\2\2\2\u05e6\u05e4\3\2\2\2\u05e6\u05e7"+
		"\3\2\2\2\u05e7\u05ea\3\2\2\2\u05e8\u05ea\7c\2\2\u05e9\u05dd\3\2\2\2\u05e9"+
		"\u05e4\3\2\2\2\u05e9\u05e8\3\2\2\2\u05ea\u00d7\3\2\2\2\u05eb\u05ed\7o"+
		"\2\2\u05ec\u05ee\5$\23\2\u05ed\u05ec\3\2\2\2\u05ed\u05ee\3\2\2\2\u05ee"+
		"\u00d9\3\2\2\2\u05ef\u05f0\7o\2\2\u05f0\u05f2\7\7\2\2\u05f1\u05f3\5\u00dc"+
		"o\2\u05f2\u05f1\3\2\2\2\u05f2\u05f3\3\2\2\2\u05f3\u05f4\3\2\2\2\u05f4"+
		"\u05f5\7\b\2\2\u05f5\u00db\3\2\2\2\u05f6\u05f7\bo\1\2\u05f7\u05f8\5\u0086"+
		"D\2\u05f8\u05fe\3\2\2\2\u05f9\u05fa\f\3\2\2\u05fa\u05fb\7\65\2\2\u05fb"+
		"\u05fd\5\u0086D\2\u05fc\u05f9\3\2\2\2\u05fd\u0600\3\2\2\2\u05fe\u05fc"+
		"\3\2\2\2\u05fe\u05ff\3\2\2\2\u05ff\u00dd\3\2\2\2\u0600\u05fe\3\2\2\2\u00c0"+
		"\u00e1\u00ed\u00f3\u00fb\u0105\u010d\u0112\u0116\u0120\u0125\u012c\u0130"+
		"\u013b\u014a\u0154\u0158\u0193\u01a3\u01c7\u01cb\u01d0\u01d4\u01d9\u01dd"+
		"\u01eb\u01ed\u01f5\u01f9\u01fc\u0204\u020e\u0211\u0215\u0218\u021c\u0220"+
		"\u0223\u0227\u022d\u022f\u0238\u023b\u0245\u024e\u0253\u0258\u025d\u0263"+
		"\u026e\u0270\u027b\u0288\u0291\u02aa\u02b0\u02b6\u02ca\u02ce\u02d3\u02d7"+
		"\u02df\u02e5\u02ec\u02f1\u02f5\u02fe\u0303\u030a\u0315\u0318\u031b\u031f"+
		"\u0330\u0335\u0339\u033c\u0342\u0345\u034a\u0351\u0355\u0359\u035d\u0361"+
		"\u036a\u0371\u0375\u0379\u0381\u038c\u0396\u03a2\u03a5\u03ab\u03ae\u03b9"+
		"\u03be\u03c1\u03d2\u03d9\u03de\u03e4\u03e7\u03ec\u03f0\u03f8\u03fc\u0400"+
		"\u0405\u0407\u040d\u0414\u0417\u041a\u041f\u0422\u0429\u042d\u0432\u0435"+
		"\u043a\u043d\u0442\u0447\u044c\u0453\u0459\u0463\u0469\u046f\u0479\u047f"+
		"\u0485\u0488\u048e\u0492\u0496\u0498\u04a0\u04a6\u04a8\u04ac\u04b1\u04b5"+
		"\u04b9\u04bd\u04c4\u04cb\u04d0\u04d4\u04d7\u04db\u04ee\u04f2\u04f5\u04fa"+
		"\u04fd\u0500\u0505\u0508\u050b\u050f\u0517\u051c\u0524\u052a\u052f\u0532"+
		"\u0536\u056b\u0578\u057d\u0581\u0585\u058b\u058f\u0599\u05a1\u05a6\u05ab"+
		"\u05b4\u05ba\u05ca\u05d3\u05df\u05e6\u05e9\u05ed\u05f2\u05fe";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}