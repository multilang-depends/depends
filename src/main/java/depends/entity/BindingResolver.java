package depends.entity;

import depends.entity.types.FunctionEntity;
import depends.entity.types.VarEntity;

public interface BindingResolver {

	FunctionEntity resolveFunctionBindings(Entity theContainer, String varName);

	VarEntity resolveVarBindings(Entity theContainer, String varName);

}
