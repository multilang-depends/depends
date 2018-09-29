package depends.entity;

import depends.entity.types.TypeEntity;
import depends.extractor.FileParser;

public interface TypeInfer {
	/**
	 * infer the full qualified name of the type
	 * @param theEntity  - from which entity to search
	 * @param rawTypeName - the raw name. ususally without dot.
	 * @return the full qualified name. for example:
	 *          given pacakge x; class A {  class B{} }
	 *          for rawType B, should return x.A.B
	 *          
	 *          given pacakge x; class A {  class B{ void foo(){ class C{}} }}
	 *          for rawType C, should return x.A.B.foo.C
	 */
	String inferQualifiedName(Entity theEntity, String rawTypeName);

	TypeEntity inferType(Entity fromEntity, String rawName);

	void addBuiltIn(FileParser fileParser);
}
