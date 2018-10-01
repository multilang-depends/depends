package depends.entity;

import depends.entity.types.FunctionEntity;
import depends.entity.types.TypeEntity;
import depends.entity.types.VarEntity;
import depends.extractor.BuiltInTypeIdenfier;

public interface TypeInfer {

	TypeEntity inferType(Entity fromEntity, String rawName);

	void setBuiltInTypeIdentifier(BuiltInTypeIdenfier fileParser);
	
	FunctionEntity resolveFunctionBindings(Entity theContainer, String varName);

	VarEntity resolveVarBindings(Entity theContainer, String varName);

}
