package depends.entity;

import java.util.List;

import depends.entity.types.FunctionEntity;
import depends.entity.types.TypeEntity;
import depends.entity.types.VarEntity;
import depends.extractor.BuiltInTypeIdenfier;
import depends.util.Tuple;

public interface TypeInfer {

	TypeEntity inferType(Entity fromEntity, String rawName);

	void setBuiltInTypeIdentifier(BuiltInTypeIdenfier fileParser);
	
	/**
	 * To found the imported types/methods/vars. 
	 * @param fromEntity
	 * @param functionName
	 * @return the Function
	 */
	List<Entity> resolveImportEntity(String item);

	
	/**
	 * To found the function. Must be invoked after all entities function binding solved
	 * @param fromEntity
	 * @param functionName
	 * @return the Function
	 */
	FunctionEntity resolveFunctionBindings(Entity fromEntity, String functionName);

	/**
	 * To found the var. Must be invoked after all entities var binding solved
	 * @param fromEntity
	 * @param varName
	 * @return
	 */
	VarEntity resolveVarBindings(Entity fromEntity, String varName);

	/**
	 * To locate the types in qualified name (match the longest one
	 * For example:
	 *     given package x, class C under x, member variable m under C with type Integer 
	 *     when qualified name = x.C.m, or  x.c.m will return <Integer, null>
	 *     
	 *     given package x, class C under x,  
	 *     when qualified name = x.C.m, or  x.c.m will return <C, "m">
	 *     
	 *     given package x  
	 *     when qualified name = x.C.m, or  x.c.m will return <x, C.m>
	 *     
	 * @param fromEntity
	 * @param qualifiedName
	 * @return
	 */
	Tuple<TypeEntity, String> locateTypeOfQualifiedName(ContainerEntity fromEntity, String qualifiedName);

	/**
	 * To determine whether the prefix is a built-in type
	 * @param prefix - e.g. java. or com.sun.
	 * @return
	 */
	boolean isBuiltInTypePrefix(String prefix);


}
