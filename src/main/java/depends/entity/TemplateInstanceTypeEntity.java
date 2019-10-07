package depends.entity;

import java.util.ArrayList;
import java.util.List;

import depends.relations.Inferer;

public class TemplateInstanceTypeEntity extends TypeEntity {
	private List<String> paramTypes = new ArrayList<>();
	private List<TypeEntity> resolvedParamTypes = new ArrayList<>();
	@Override
	public void inferLocalLevelEntities(Inferer inferer) {
		super.inferLocalLevelEntities(inferer);
		identiferToEntities(inferer, this.paramTypes).forEach(item -> {
			if (item instanceof TypeEntity) {
				resolvedParamTypes.add((TypeEntity) item);
			}else {
				System.err.println(item.getRawName() + " expected a type, but actually it is "+ item.getClass().getSimpleName());
			}
		});
	}
	
}
