// Generated from ./src/main/antlr4/depends/javaextractor/CElsa.g4 by ANTLR 4.5.3
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CElsaParser}.
 */
public interface CElsaListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link CElsaParser#translationUnit}.
	 * @param ctx the parse tree
	 */
	void enterTranslationUnit(CElsaParser.TranslationUnitContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#translationUnit}.
	 * @param ctx the parse tree
	 */
	void exitTranslationUnit(CElsaParser.TranslationUnitContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(CElsaParser.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(CElsaParser.DeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#typedefName}.
	 * @param ctx the parse tree
	 */
	void enterTypedefName(CElsaParser.TypedefNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#typedefName}.
	 * @param ctx the parse tree
	 */
	void exitTypedefName(CElsaParser.TypedefNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#namespaceName}.
	 * @param ctx the parse tree
	 */
	void enterNamespaceName(CElsaParser.NamespaceNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#namespaceName}.
	 * @param ctx the parse tree
	 */
	void exitNamespaceName(CElsaParser.NamespaceNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#originalNamespaceName}.
	 * @param ctx the parse tree
	 */
	void enterOriginalNamespaceName(CElsaParser.OriginalNamespaceNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#originalNamespaceName}.
	 * @param ctx the parse tree
	 */
	void exitOriginalNamespaceName(CElsaParser.OriginalNamespaceNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#namespaceAlias}.
	 * @param ctx the parse tree
	 */
	void enterNamespaceAlias(CElsaParser.NamespaceAliasContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#namespaceAlias}.
	 * @param ctx the parse tree
	 */
	void exitNamespaceAlias(CElsaParser.NamespaceAliasContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#className}.
	 * @param ctx the parse tree
	 */
	void enterClassName(CElsaParser.ClassNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#className}.
	 * @param ctx the parse tree
	 */
	void exitClassName(CElsaParser.ClassNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#enumName}.
	 * @param ctx the parse tree
	 */
	void enterEnumName(CElsaParser.EnumNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#enumName}.
	 * @param ctx the parse tree
	 */
	void exitEnumName(CElsaParser.EnumNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#templateName}.
	 * @param ctx the parse tree
	 */
	void enterTemplateName(CElsaParser.TemplateNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#templateName}.
	 * @param ctx the parse tree
	 */
	void exitTemplateName(CElsaParser.TemplateNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(CElsaParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(CElsaParser.LiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#idExpression}.
	 * @param ctx the parse tree
	 */
	void enterIdExpression(CElsaParser.IdExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#idExpression}.
	 * @param ctx the parse tree
	 */
	void exitIdExpression(CElsaParser.IdExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#unqualifiedId}.
	 * @param ctx the parse tree
	 */
	void enterUnqualifiedId(CElsaParser.UnqualifiedIdContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#unqualifiedId}.
	 * @param ctx the parse tree
	 */
	void exitUnqualifiedId(CElsaParser.UnqualifiedIdContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#template}.
	 * @param ctx the parse tree
	 */
	void enterTemplate(CElsaParser.TemplateContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#template}.
	 * @param ctx the parse tree
	 */
	void exitTemplate(CElsaParser.TemplateContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#qualifiedId}.
	 * @param ctx the parse tree
	 */
	void enterQualifiedId(CElsaParser.QualifiedIdContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#qualifiedId}.
	 * @param ctx the parse tree
	 */
	void exitQualifiedId(CElsaParser.QualifiedIdContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#nestedNameSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterNestedNameSpecifier(CElsaParser.NestedNameSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#nestedNameSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitNestedNameSpecifier(CElsaParser.NestedNameSpecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#classOrNamespaceName}.
	 * @param ctx the parse tree
	 */
	void enterClassOrNamespaceName(CElsaParser.ClassOrNamespaceNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#classOrNamespaceName}.
	 * @param ctx the parse tree
	 */
	void exitClassOrNamespaceName(CElsaParser.ClassOrNamespaceNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryExpression(CElsaParser.PrimaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryExpression(CElsaParser.PrimaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(CElsaParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(CElsaParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void enterExpressionList(CElsaParser.ExpressionListContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void exitExpressionList(CElsaParser.ExpressionListContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#pseudoDestructorName}.
	 * @param ctx the parse tree
	 */
	void enterPseudoDestructorName(CElsaParser.PseudoDestructorNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#pseudoDestructorName}.
	 * @param ctx the parse tree
	 */
	void exitPseudoDestructorName(CElsaParser.PseudoDestructorNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#newExpression}.
	 * @param ctx the parse tree
	 */
	void enterNewExpression(CElsaParser.NewExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#newExpression}.
	 * @param ctx the parse tree
	 */
	void exitNewExpression(CElsaParser.NewExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#newPlacement}.
	 * @param ctx the parse tree
	 */
	void enterNewPlacement(CElsaParser.NewPlacementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#newPlacement}.
	 * @param ctx the parse tree
	 */
	void exitNewPlacement(CElsaParser.NewPlacementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#newTypeId}.
	 * @param ctx the parse tree
	 */
	void enterNewTypeId(CElsaParser.NewTypeIdContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#newTypeId}.
	 * @param ctx the parse tree
	 */
	void exitNewTypeId(CElsaParser.NewTypeIdContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#newDeclarator}.
	 * @param ctx the parse tree
	 */
	void enterNewDeclarator(CElsaParser.NewDeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#newDeclarator}.
	 * @param ctx the parse tree
	 */
	void exitNewDeclarator(CElsaParser.NewDeclaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#newInitializer}.
	 * @param ctx the parse tree
	 */
	void enterNewInitializer(CElsaParser.NewInitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#newInitializer}.
	 * @param ctx the parse tree
	 */
	void exitNewInitializer(CElsaParser.NewInitializerContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#deleteExpression}.
	 * @param ctx the parse tree
	 */
	void enterDeleteExpression(CElsaParser.DeleteExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#deleteExpression}.
	 * @param ctx the parse tree
	 */
	void exitDeleteExpression(CElsaParser.DeleteExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#pmExpression}.
	 * @param ctx the parse tree
	 */
	void enterPmExpression(CElsaParser.PmExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#pmExpression}.
	 * @param ctx the parse tree
	 */
	void exitPmExpression(CElsaParser.PmExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(CElsaParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(CElsaParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#labeledStatement}.
	 * @param ctx the parse tree
	 */
	void enterLabeledStatement(CElsaParser.LabeledStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#labeledStatement}.
	 * @param ctx the parse tree
	 */
	void exitLabeledStatement(CElsaParser.LabeledStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#expressionStatement}.
	 * @param ctx the parse tree
	 */
	void enterExpressionStatement(CElsaParser.ExpressionStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#expressionStatement}.
	 * @param ctx the parse tree
	 */
	void exitExpressionStatement(CElsaParser.ExpressionStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#compoundStatement}.
	 * @param ctx the parse tree
	 */
	void enterCompoundStatement(CElsaParser.CompoundStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#compoundStatement}.
	 * @param ctx the parse tree
	 */
	void exitCompoundStatement(CElsaParser.CompoundStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#selectionStatement}.
	 * @param ctx the parse tree
	 */
	void enterSelectionStatement(CElsaParser.SelectionStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#selectionStatement}.
	 * @param ctx the parse tree
	 */
	void exitSelectionStatement(CElsaParser.SelectionStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#condition}.
	 * @param ctx the parse tree
	 */
	void enterCondition(CElsaParser.ConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#condition}.
	 * @param ctx the parse tree
	 */
	void exitCondition(CElsaParser.ConditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#iterationStatement}.
	 * @param ctx the parse tree
	 */
	void enterIterationStatement(CElsaParser.IterationStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#iterationStatement}.
	 * @param ctx the parse tree
	 */
	void exitIterationStatement(CElsaParser.IterationStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#forInitStatement}.
	 * @param ctx the parse tree
	 */
	void enterForInitStatement(CElsaParser.ForInitStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#forInitStatement}.
	 * @param ctx the parse tree
	 */
	void exitForInitStatement(CElsaParser.ForInitStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void enterJumpStatement(CElsaParser.JumpStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void exitJumpStatement(CElsaParser.JumpStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#blockDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterBlockDeclaration(CElsaParser.BlockDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#blockDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitBlockDeclaration(CElsaParser.BlockDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#simpleDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterSimpleDeclaration(CElsaParser.SimpleDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#simpleDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitSimpleDeclaration(CElsaParser.SimpleDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#initDeclaratorList}.
	 * @param ctx the parse tree
	 */
	void enterInitDeclaratorList(CElsaParser.InitDeclaratorListContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#initDeclaratorList}.
	 * @param ctx the parse tree
	 */
	void exitInitDeclaratorList(CElsaParser.InitDeclaratorListContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#initDeclarator}.
	 * @param ctx the parse tree
	 */
	void enterInitDeclarator(CElsaParser.InitDeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#initDeclarator}.
	 * @param ctx the parse tree
	 */
	void exitInitDeclarator(CElsaParser.InitDeclaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#declSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterDeclSpecifier(CElsaParser.DeclSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#declSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitDeclSpecifier(CElsaParser.DeclSpecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#storageClassSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterStorageClassSpecifier(CElsaParser.StorageClassSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#storageClassSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitStorageClassSpecifier(CElsaParser.StorageClassSpecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#functionSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterFunctionSpecifier(CElsaParser.FunctionSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#functionSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitFunctionSpecifier(CElsaParser.FunctionSpecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#typeSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterTypeSpecifier(CElsaParser.TypeSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#typeSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitTypeSpecifier(CElsaParser.TypeSpecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#simpleTypeSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterSimpleTypeSpecifier(CElsaParser.SimpleTypeSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#simpleTypeSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitSimpleTypeSpecifier(CElsaParser.SimpleTypeSpecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#typeName}.
	 * @param ctx the parse tree
	 */
	void enterTypeName(CElsaParser.TypeNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#typeName}.
	 * @param ctx the parse tree
	 */
	void exitTypeName(CElsaParser.TypeNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#elaboratedTypeSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterElaboratedTypeSpecifier(CElsaParser.ElaboratedTypeSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#elaboratedTypeSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitElaboratedTypeSpecifier(CElsaParser.ElaboratedTypeSpecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#enumSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterEnumSpecifier(CElsaParser.EnumSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#enumSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitEnumSpecifier(CElsaParser.EnumSpecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#enumeratorList}.
	 * @param ctx the parse tree
	 */
	void enterEnumeratorList(CElsaParser.EnumeratorListContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#enumeratorList}.
	 * @param ctx the parse tree
	 */
	void exitEnumeratorList(CElsaParser.EnumeratorListContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#enumeratorDefinition}.
	 * @param ctx the parse tree
	 */
	void enterEnumeratorDefinition(CElsaParser.EnumeratorDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#enumeratorDefinition}.
	 * @param ctx the parse tree
	 */
	void exitEnumeratorDefinition(CElsaParser.EnumeratorDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#namespaceDefinition}.
	 * @param ctx the parse tree
	 */
	void enterNamespaceDefinition(CElsaParser.NamespaceDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#namespaceDefinition}.
	 * @param ctx the parse tree
	 */
	void exitNamespaceDefinition(CElsaParser.NamespaceDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#namedNamespaceDefinition}.
	 * @param ctx the parse tree
	 */
	void enterNamedNamespaceDefinition(CElsaParser.NamedNamespaceDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#namedNamespaceDefinition}.
	 * @param ctx the parse tree
	 */
	void exitNamedNamespaceDefinition(CElsaParser.NamedNamespaceDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#originalNamespaceDefinition}.
	 * @param ctx the parse tree
	 */
	void enterOriginalNamespaceDefinition(CElsaParser.OriginalNamespaceDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#originalNamespaceDefinition}.
	 * @param ctx the parse tree
	 */
	void exitOriginalNamespaceDefinition(CElsaParser.OriginalNamespaceDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#extensionNamespaceDefinition}.
	 * @param ctx the parse tree
	 */
	void enterExtensionNamespaceDefinition(CElsaParser.ExtensionNamespaceDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#extensionNamespaceDefinition}.
	 * @param ctx the parse tree
	 */
	void exitExtensionNamespaceDefinition(CElsaParser.ExtensionNamespaceDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#unnamedNamespaceDefinition}.
	 * @param ctx the parse tree
	 */
	void enterUnnamedNamespaceDefinition(CElsaParser.UnnamedNamespaceDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#unnamedNamespaceDefinition}.
	 * @param ctx the parse tree
	 */
	void exitUnnamedNamespaceDefinition(CElsaParser.UnnamedNamespaceDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#namespaceAliasDefinition}.
	 * @param ctx the parse tree
	 */
	void enterNamespaceAliasDefinition(CElsaParser.NamespaceAliasDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#namespaceAliasDefinition}.
	 * @param ctx the parse tree
	 */
	void exitNamespaceAliasDefinition(CElsaParser.NamespaceAliasDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#qualifiedNamespaceSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterQualifiedNamespaceSpecifier(CElsaParser.QualifiedNamespaceSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#qualifiedNamespaceSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitQualifiedNamespaceSpecifier(CElsaParser.QualifiedNamespaceSpecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#usingDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterUsingDeclaration(CElsaParser.UsingDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#usingDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitUsingDeclaration(CElsaParser.UsingDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#usingDirective}.
	 * @param ctx the parse tree
	 */
	void enterUsingDirective(CElsaParser.UsingDirectiveContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#usingDirective}.
	 * @param ctx the parse tree
	 */
	void exitUsingDirective(CElsaParser.UsingDirectiveContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#asmDefinition}.
	 * @param ctx the parse tree
	 */
	void enterAsmDefinition(CElsaParser.AsmDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#asmDefinition}.
	 * @param ctx the parse tree
	 */
	void exitAsmDefinition(CElsaParser.AsmDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#linkageSpecification}.
	 * @param ctx the parse tree
	 */
	void enterLinkageSpecification(CElsaParser.LinkageSpecificationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#linkageSpecification}.
	 * @param ctx the parse tree
	 */
	void exitLinkageSpecification(CElsaParser.LinkageSpecificationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#declarator}.
	 * @param ctx the parse tree
	 */
	void enterDeclarator(CElsaParser.DeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#declarator}.
	 * @param ctx the parse tree
	 */
	void exitDeclarator(CElsaParser.DeclaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#ptrOperator}.
	 * @param ctx the parse tree
	 */
	void enterPtrOperator(CElsaParser.PtrOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#ptrOperator}.
	 * @param ctx the parse tree
	 */
	void exitPtrOperator(CElsaParser.PtrOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#cvQualifierSeq}.
	 * @param ctx the parse tree
	 */
	void enterCvQualifierSeq(CElsaParser.CvQualifierSeqContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#cvQualifierSeq}.
	 * @param ctx the parse tree
	 */
	void exitCvQualifierSeq(CElsaParser.CvQualifierSeqContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#cvQualifier}.
	 * @param ctx the parse tree
	 */
	void enterCvQualifier(CElsaParser.CvQualifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#cvQualifier}.
	 * @param ctx the parse tree
	 */
	void exitCvQualifier(CElsaParser.CvQualifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#declaratorId}.
	 * @param ctx the parse tree
	 */
	void enterDeclaratorId(CElsaParser.DeclaratorIdContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#declaratorId}.
	 * @param ctx the parse tree
	 */
	void exitDeclaratorId(CElsaParser.DeclaratorIdContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#typeId}.
	 * @param ctx the parse tree
	 */
	void enterTypeId(CElsaParser.TypeIdContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#typeId}.
	 * @param ctx the parse tree
	 */
	void exitTypeId(CElsaParser.TypeIdContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#parameterDeclarationClause}.
	 * @param ctx the parse tree
	 */
	void enterParameterDeclarationClause(CElsaParser.ParameterDeclarationClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#parameterDeclarationClause}.
	 * @param ctx the parse tree
	 */
	void exitParameterDeclarationClause(CElsaParser.ParameterDeclarationClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#parameterDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterParameterDeclaration(CElsaParser.ParameterDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#parameterDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitParameterDeclaration(CElsaParser.ParameterDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#functionDefinition}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDefinition(CElsaParser.FunctionDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#functionDefinition}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDefinition(CElsaParser.FunctionDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#functionBody}.
	 * @param ctx the parse tree
	 */
	void enterFunctionBody(CElsaParser.FunctionBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#functionBody}.
	 * @param ctx the parse tree
	 */
	void exitFunctionBody(CElsaParser.FunctionBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#initializer}.
	 * @param ctx the parse tree
	 */
	void enterInitializer(CElsaParser.InitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#initializer}.
	 * @param ctx the parse tree
	 */
	void exitInitializer(CElsaParser.InitializerContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#initializerClause}.
	 * @param ctx the parse tree
	 */
	void enterInitializerClause(CElsaParser.InitializerClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#initializerClause}.
	 * @param ctx the parse tree
	 */
	void exitInitializerClause(CElsaParser.InitializerClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#initializerList}.
	 * @param ctx the parse tree
	 */
	void enterInitializerList(CElsaParser.InitializerListContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#initializerList}.
	 * @param ctx the parse tree
	 */
	void exitInitializerList(CElsaParser.InitializerListContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#classSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterClassSpecifier(CElsaParser.ClassSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#classSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitClassSpecifier(CElsaParser.ClassSpecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#classHead}.
	 * @param ctx the parse tree
	 */
	void enterClassHead(CElsaParser.ClassHeadContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#classHead}.
	 * @param ctx the parse tree
	 */
	void exitClassHead(CElsaParser.ClassHeadContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#classKey}.
	 * @param ctx the parse tree
	 */
	void enterClassKey(CElsaParser.ClassKeyContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#classKey}.
	 * @param ctx the parse tree
	 */
	void exitClassKey(CElsaParser.ClassKeyContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#memberSpecification}.
	 * @param ctx the parse tree
	 */
	void enterMemberSpecification(CElsaParser.MemberSpecificationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#memberSpecification}.
	 * @param ctx the parse tree
	 */
	void exitMemberSpecification(CElsaParser.MemberSpecificationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#memberDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterMemberDeclaration(CElsaParser.MemberDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#memberDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitMemberDeclaration(CElsaParser.MemberDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#memberDeclaratorList}.
	 * @param ctx the parse tree
	 */
	void enterMemberDeclaratorList(CElsaParser.MemberDeclaratorListContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#memberDeclaratorList}.
	 * @param ctx the parse tree
	 */
	void exitMemberDeclaratorList(CElsaParser.MemberDeclaratorListContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#memberDeclarator}.
	 * @param ctx the parse tree
	 */
	void enterMemberDeclarator(CElsaParser.MemberDeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#memberDeclarator}.
	 * @param ctx the parse tree
	 */
	void exitMemberDeclarator(CElsaParser.MemberDeclaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#pureSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterPureSpecifier(CElsaParser.PureSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#pureSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitPureSpecifier(CElsaParser.PureSpecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#constantInitializer}.
	 * @param ctx the parse tree
	 */
	void enterConstantInitializer(CElsaParser.ConstantInitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#constantInitializer}.
	 * @param ctx the parse tree
	 */
	void exitConstantInitializer(CElsaParser.ConstantInitializerContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#baseClause}.
	 * @param ctx the parse tree
	 */
	void enterBaseClause(CElsaParser.BaseClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#baseClause}.
	 * @param ctx the parse tree
	 */
	void exitBaseClause(CElsaParser.BaseClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#baseSpecifierList}.
	 * @param ctx the parse tree
	 */
	void enterBaseSpecifierList(CElsaParser.BaseSpecifierListContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#baseSpecifierList}.
	 * @param ctx the parse tree
	 */
	void exitBaseSpecifierList(CElsaParser.BaseSpecifierListContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#baseSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterBaseSpecifier(CElsaParser.BaseSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#baseSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitBaseSpecifier(CElsaParser.BaseSpecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#accessSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterAccessSpecifier(CElsaParser.AccessSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#accessSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitAccessSpecifier(CElsaParser.AccessSpecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#conversionFunctionId}.
	 * @param ctx the parse tree
	 */
	void enterConversionFunctionId(CElsaParser.ConversionFunctionIdContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#conversionFunctionId}.
	 * @param ctx the parse tree
	 */
	void exitConversionFunctionId(CElsaParser.ConversionFunctionIdContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#ctorInitializer}.
	 * @param ctx the parse tree
	 */
	void enterCtorInitializer(CElsaParser.CtorInitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#ctorInitializer}.
	 * @param ctx the parse tree
	 */
	void exitCtorInitializer(CElsaParser.CtorInitializerContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#memInitializer}.
	 * @param ctx the parse tree
	 */
	void enterMemInitializer(CElsaParser.MemInitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#memInitializer}.
	 * @param ctx the parse tree
	 */
	void exitMemInitializer(CElsaParser.MemInitializerContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#memInitializerId}.
	 * @param ctx the parse tree
	 */
	void enterMemInitializerId(CElsaParser.MemInitializerIdContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#memInitializerId}.
	 * @param ctx the parse tree
	 */
	void exitMemInitializerId(CElsaParser.MemInitializerIdContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#operatorFunctionId}.
	 * @param ctx the parse tree
	 */
	void enterOperatorFunctionId(CElsaParser.OperatorFunctionIdContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#operatorFunctionId}.
	 * @param ctx the parse tree
	 */
	void exitOperatorFunctionId(CElsaParser.OperatorFunctionIdContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#operatorFunc}.
	 * @param ctx the parse tree
	 */
	void enterOperatorFunc(CElsaParser.OperatorFuncContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#operatorFunc}.
	 * @param ctx the parse tree
	 */
	void exitOperatorFunc(CElsaParser.OperatorFuncContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#templateDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterTemplateDeclaration(CElsaParser.TemplateDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#templateDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitTemplateDeclaration(CElsaParser.TemplateDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#templateParameterList}.
	 * @param ctx the parse tree
	 */
	void enterTemplateParameterList(CElsaParser.TemplateParameterListContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#templateParameterList}.
	 * @param ctx the parse tree
	 */
	void exitTemplateParameterList(CElsaParser.TemplateParameterListContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#templateParameter}.
	 * @param ctx the parse tree
	 */
	void enterTemplateParameter(CElsaParser.TemplateParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#templateParameter}.
	 * @param ctx the parse tree
	 */
	void exitTemplateParameter(CElsaParser.TemplateParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#typeParameter}.
	 * @param ctx the parse tree
	 */
	void enterTypeParameter(CElsaParser.TypeParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#typeParameter}.
	 * @param ctx the parse tree
	 */
	void exitTypeParameter(CElsaParser.TypeParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#templateId}.
	 * @param ctx the parse tree
	 */
	void enterTemplateId(CElsaParser.TemplateIdContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#templateId}.
	 * @param ctx the parse tree
	 */
	void exitTemplateId(CElsaParser.TemplateIdContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#templateArgumentList}.
	 * @param ctx the parse tree
	 */
	void enterTemplateArgumentList(CElsaParser.TemplateArgumentListContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#templateArgumentList}.
	 * @param ctx the parse tree
	 */
	void exitTemplateArgumentList(CElsaParser.TemplateArgumentListContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#templateArgument}.
	 * @param ctx the parse tree
	 */
	void enterTemplateArgument(CElsaParser.TemplateArgumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#templateArgument}.
	 * @param ctx the parse tree
	 */
	void exitTemplateArgument(CElsaParser.TemplateArgumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#explicitInstantiation}.
	 * @param ctx the parse tree
	 */
	void enterExplicitInstantiation(CElsaParser.ExplicitInstantiationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#explicitInstantiation}.
	 * @param ctx the parse tree
	 */
	void exitExplicitInstantiation(CElsaParser.ExplicitInstantiationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#explicitSpecialization}.
	 * @param ctx the parse tree
	 */
	void enterExplicitSpecialization(CElsaParser.ExplicitSpecializationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#explicitSpecialization}.
	 * @param ctx the parse tree
	 */
	void exitExplicitSpecialization(CElsaParser.ExplicitSpecializationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#tryBlock}.
	 * @param ctx the parse tree
	 */
	void enterTryBlock(CElsaParser.TryBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#tryBlock}.
	 * @param ctx the parse tree
	 */
	void exitTryBlock(CElsaParser.TryBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#functionTryBlock}.
	 * @param ctx the parse tree
	 */
	void enterFunctionTryBlock(CElsaParser.FunctionTryBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#functionTryBlock}.
	 * @param ctx the parse tree
	 */
	void exitFunctionTryBlock(CElsaParser.FunctionTryBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#handlerSeq}.
	 * @param ctx the parse tree
	 */
	void enterHandlerSeq(CElsaParser.HandlerSeqContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#handlerSeq}.
	 * @param ctx the parse tree
	 */
	void exitHandlerSeq(CElsaParser.HandlerSeqContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#handler}.
	 * @param ctx the parse tree
	 */
	void enterHandler(CElsaParser.HandlerContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#handler}.
	 * @param ctx the parse tree
	 */
	void exitHandler(CElsaParser.HandlerContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#exceptionDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterExceptionDeclaration(CElsaParser.ExceptionDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#exceptionDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitExceptionDeclaration(CElsaParser.ExceptionDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#throwExpression}.
	 * @param ctx the parse tree
	 */
	void enterThrowExpression(CElsaParser.ThrowExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#throwExpression}.
	 * @param ctx the parse tree
	 */
	void exitThrowExpression(CElsaParser.ThrowExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#exceptionSpecification}.
	 * @param ctx the parse tree
	 */
	void enterExceptionSpecification(CElsaParser.ExceptionSpecificationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#exceptionSpecification}.
	 * @param ctx the parse tree
	 */
	void exitExceptionSpecification(CElsaParser.ExceptionSpecificationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CElsaParser#typeIdList}.
	 * @param ctx the parse tree
	 */
	void enterTypeIdList(CElsaParser.TypeIdListContext ctx);
	/**
	 * Exit a parse tree produced by {@link CElsaParser#typeIdList}.
	 * @param ctx the parse tree
	 */
	void exitTypeIdList(CElsaParser.TypeIdListContext ctx);
}