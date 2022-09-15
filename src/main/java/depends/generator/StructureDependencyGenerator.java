/*
MIT License

Copyright (c) 2018-2019 Gang ZHANG

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package depends.generator;

import depends.entity.*;
import depends.entity.repo.EntityRepo;

public class StructureDependencyGenerator extends DependencyGenerator{
	@Override
	protected String nameOf(Entity entity) {
		return entity.getQualifiedName() + "|" + entity.getClass().getSimpleName().replace("Entity","");
	}


	@Override
	protected boolean outputLevelMatch(Entity entity) {
		if (entity instanceof FileEntity) return false;
		if (entity instanceof TypeEntity) return true; //package included
		if (entity instanceof VarEntity && entity.getParent() instanceof TypeEntity) return true;
		if (entity instanceof FunctionEntity) return true;
		return false;
	}

	@Override
	protected int upToOutputLevelEntityId(EntityRepo entityRepo, Entity entity) {
		Entity ancestor = getAncestorOfType(entity);
		if (ancestor==null) {
			return -1;
		}
		if (!ancestor.inScope()) return -1;
		return ancestor.getId();
	}

	public Entity getAncestorOfType(Entity fromEntity) {
		while(fromEntity!=null) {
			if (outputLevelMatch(fromEntity))
				return fromEntity;
			if (fromEntity.getParent()==null) return null;
			fromEntity = fromEntity.getParent();
		}
		return null;
	}
}
