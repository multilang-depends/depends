package depends.entity;

import java.util.List;

import depends.entity.types.FunctionEntity;
import depends.entity.types.TypeEntity;
import depends.extractor.BuiltInTypeIdenfier;
import depends.importtypes.Import;
import depends.util.Tuple;

public interface TypeInfer {

	static final TypeEntity buildInType = new TypeEntity("built-in", null, -1);
	static final TypeEntity externalType = new TypeEntity("external", null, -1);
	static final TypeEntity genericParameterType = new TypeEntity("T", null, -1);
	public class InferData{
		public InferData(TypeEntity type, Entity entity) {
			this.type = type;
			this.entity = entity;
		}
		public TypeEntity type;
		public Entity entity;
	}
	InferData inferType(Entity fromEntity, String rawName);
	InferData inferTypeWithoutImportSearch(Entity fromEntity, String rawName);
	TypeEntity inferTypeType(Entity fromEntity, String rawName);

	void setBuiltInTypeIdentifier(BuiltInTypeIdenfier fileParser);
	
	
	/**
	 * To found the function. Must be invoked after all entities function binding solved
	 * @param fromEntity
	 * @param functionName
	 * @return the Function
	 */
	FunctionEntity resolveFunctionBindings(Entity fromEntity, String functionName);


	/**
	 * To determine whether the prefix is a built-in type
	 * @param prefix - e.g. java. or com.sun.
	 * @return
	 */
	boolean isBuiltInTypePrefix(String prefix);
	List<Entity> getImportedRelationEntities(List<Import> importedNames);
	List<Entity> getImportedTypes(List<Import> importedNames);
	List<Entity> getImportedFiles(List<Import> importedNames);

}
