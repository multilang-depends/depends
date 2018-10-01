package depends.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.matches;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import depends.entity.repo.EntityRepo;
import depends.entity.types.AnonymousBlock;
import depends.entity.types.FunctionEntity;
import depends.entity.types.PackageEntity;
import depends.entity.types.TypeEntity;
import depends.entity.types.VarEntity;

public class ContainerEntityInferLocalLevelTypeTest {

	private static final String typeRawName = "MyInteger";
	private static final int type1Id = 1;
	EntityRepo repo;
	AnonymousBlock block;
	TypeEntity type1;
	TypeInfer typeInferer;
	
	@Before
	public void setup() {
		repo = new EntityRepo();
	    block = new AnonymousBlock(null, 0);
		repo.add(block);
		type1 = new TypeEntity(typeRawName,null,type1Id);
		repo.add(type1);
		
		typeInferer = mock(TypeInfer.class);

	}
	@Test
	public void testInferDotVarNames() {
		block.addVar(new VarEntity("var",typeRawName,block,2));
		block.addAnnotation(typeRawName);
		block.addTypeParameter(typeRawName);
		
		when(typeInferer.inferType(any(),matches(typeRawName))).thenReturn(type1);
		block.inferLocalLevelTypes(typeInferer);
		
		assertEquals(type1Id, block.getVars().get(0).getType().getId());
		assertEquals(type1Id, block.getResolvedAnnotations().iterator().next().getId());
		assertEquals(type1Id, block.getResolvedTypeParameters().iterator().next().getId());
	}
	
	@Test
	public void testInferExpressions_If_WithRawType_Then_Solve_RawType() {
		Expression expression = new Expression(0, null);
		expression.rawType = typeRawName;
		block.addExpression(expression);
	
		when(typeInferer.inferType(any(),matches(typeRawName))).thenReturn(type1);
		block.inferLocalLevelTypes(typeInferer);
		
		assertEquals(type1Id, block.expressions().get(0).type.getId());
	}
	
	@Test
	public void testInferExpressions_If_WithIdentifier_Then_Find_Identifier_In_Scope() {
		Expression expression = new Expression(0, null);
		expression.identifier = "var";
		block.addExpression(expression);
		block.addVar(new VarEntity("var",typeRawName,block,2));
		
		when(typeInferer.inferType(any(),matches(typeRawName))).thenReturn(type1);
		block.inferLocalLevelTypes(typeInferer);
		
		assertEquals(type1Id, block.expressions().get(0).type.getId());
	}
	
	@Test
	public void testInferExpressions_If_WithoutIdentifier_Then_Not_Find_Identifier_In_Scope() {
		Expression expression = new Expression(0, null);
		expression.identifier = "var_not_exists";
		block.addExpression(expression);
		block.addVar(new VarEntity("var",typeRawName,block,2));
		
		when(typeInferer.inferType(any(),matches(typeRawName))).thenReturn(type1);
		block.inferLocalLevelTypes(typeInferer);
		
		assertNull(block.expressions().get(0).type);
	}
	
	@Test
	public void testInferExpressions_If_WithIdentifier_In_Upper_Block_Then_Found_Identifier_In_Scope() {
		Expression expression = new Expression(0, null);
		expression.identifier = "var_in_parent";
		block.addExpression(expression);
		block.addVar(new VarEntity("var",typeRawName,block,2));
		
		AnonymousBlock parentBlock = new AnonymousBlock(null, 1);
		block.setParent(parentBlock);
		parentBlock.addVar(new VarEntity("var_in_parent",typeRawName,parentBlock,2));
		when(typeInferer.inferType(any(),matches(typeRawName))).thenReturn(type1);

		parentBlock.inferLocalLevelTypes(typeInferer);
		block.inferLocalLevelTypes(typeInferer);
		
		assertEquals(type1Id, block.expressions().get(0).type.getId());
	}
	
	@Test
	public void testInferExpressions_If_Identifier_WithoutDot_Is_a_Type() {
		Expression expression = new Expression(0, null);
		expression.identifier = typeRawName;
		block.addExpression(expression);
		block.setParent(type1);
		block.inferLocalLevelTypes(repo);
		assertEquals(type1Id, block.expressions().get(0).type.getId());
	}
	
	@Test
	public void testInferExpressions_If_Identifier_WithDot_Is_a_Type() {
		Expression expression = new Expression(0, null);
		expression.identifier = "packagex.MyInteger";
		block.addExpression(expression);
		PackageEntity p = new PackageEntity("packagex",3);
		TypeEntity type2 = new TypeEntity(typeRawName,p,type1Id);
		type2.setQualifiedName("packagex.MyInteger");
		repo.add(type2);
		block.setParent(type2);

		block.inferLocalLevelTypes(repo);
		assertEquals(type1Id, block.expressions().get(0).type.getId());
	}
	
	@Test
	public void testInferExpressions_If_Identifier_WithDot_Is_a_Variable() {
		Expression expression = new Expression(0, null);
		expression.identifier = "packagex.MyInteger2.var";
		block.addExpression(expression);
		PackageEntity p = new PackageEntity("packagex",3);
		TypeEntity type2 = new TypeEntity(typeRawName,p,type1Id);
		type2.setQualifiedName("packagex.MyInteger2");
		repo.add(type2);
		block.setParent(type2);
		type2.addVar(new VarEntity("var",typeRawName,block,2));
		type2.inferLocalLevelTypes(repo);
		block.inferLocalLevelTypes(repo);
		assertEquals(type1Id, block.expressions().get(0).type.getId());
	}
	
	@Test
	public void testInferExpressions_If_Identifier_WithDot_Is_a_Function() {
		Expression expression = new Expression(0, null);
		expression.identifier = "packagex.MyInteger2.foo";
		block.addExpression(expression);
		PackageEntity p = new PackageEntity("packagex",3);
		TypeEntity type2 = new TypeEntity(typeRawName,p,type1Id);
		type2.setQualifiedName("packagex.MyInteger2");
		repo.add(type2);
		block.setParent(type2);
		type2.addFunction(new FunctionEntity("foo",type2, 2,typeRawName,new ArrayList<>()));
		type1.inferLocalLevelTypes(repo);
		type2.inferLocalLevelTypes(repo);
		block.inferLocalLevelTypes(repo);
		assertEquals(type1Id, block.expressions().get(0).type.getId());
	}
	
	@Test
	public void testInferExpressions_If_Identifier_Is_THIS() {
		Expression expression = new Expression(0, null);
		expression.identifier = "this";
		type1.addExpression(expression);
		type1.inferLocalLevelTypes(repo);
		assertEquals(type1Id, type1.expressions().get(0).type.getId());
	}
	
	@Test
	public void testInferExpressions_If_Identifier_Is_SUPER() {
		Expression expression = new Expression(0, null);
		expression.identifier = "super";
		TypeEntity type2 = new TypeEntity("type2",null,100);
		type2.addExpression(expression);
		type2.addExtends(typeRawName);
		repo.add(type1);
		repo.add(type2);
		type2.inferLocalLevelTypes(repo);
		assertEquals(type1Id, type2.expressions().get(0).type.getId());
	}
	
	@Test
	public void testInferExpressions_If_Identifier_Is_SUPER_DOT_Varname() {
		Expression expression = new Expression(0, null);
		expression.identifier = "super.var";
		TypeEntity type2 = new TypeEntity("type2",null,100);
		type2.addExpression(expression);
		type2.addExtends(typeRawName);
		type1.addVar(new VarEntity("var",typeRawName,type1,2));
		repo.add(type1);
		repo.add(type2);
		type1.inferLocalLevelTypes(repo);
		type2.inferLocalLevelTypes(repo);
		assertEquals(type1Id, type2.expressions().get(0).type.getId());
	}

}
