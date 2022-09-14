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

package depends.relations;
import depends.entity.*;
import depends.entity.repo.BuiltInType;
import depends.entity.repo.EntityRepo;
import depends.extractor.AbstractLangProcessor;
import depends.extractor.UnsolvedBindings;
import depends.extractor.empty.EmptyBuiltInType;
import depends.importtypes.Import;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.util.*;

public class BindingResolver implements IBindingResolver{

	private BuiltInType buildInTypeManager = new EmptyBuiltInType();
	private ImportLookupStrategy importLookupStrategy;
	private Set<UnsolvedBindings> unsolvedSymbols = new HashSet<>();
	private EntityRepo repo;

	private boolean eagerExpressionResolve = false;
	private boolean isCollectUnsolvedBindings = false;
	private boolean isDuckTypingDeduce = true;
	private static Logger logger = LoggerFactory.getLogger(IBindingResolver.class);

	public BindingResolver(AbstractLangProcessor langProcessor,
							boolean isCollectUnsolvedBindings, boolean isDuckTypingDeduce) {
		this.repo = langProcessor.getEntityRepo();
		this.importLookupStrategy = langProcessor.getImportLookupStrategy();
		this.buildInTypeManager = langProcessor.getBuiltInType();
		this.isCollectUnsolvedBindings = isCollectUnsolvedBindings;
		this.isDuckTypingDeduce = isDuckTypingDeduce;
		unsolvedSymbols= new HashSet<>();
		importLookupStrategy.setBindingResolver(this);
	}


	@Override
	public  Set<UnsolvedBindings> resolveAllBindings(boolean isEagerExpressionResolve) {
		System.out.println("Resolve type bindings....");
		if (logger.isInfoEnabled()) {
			logger.info("Resolve type bindings...");
		}
		resolveTypes(isEagerExpressionResolve);
		System.out.println("Dependency analaysing....");
		if (logger.isInfoEnabled()) {
			logger.info("Dependency analaysing...");
		}
		logger.info("Heap Information: " + ManagementFactory.getMemoryMXBean().getHeapMemoryUsage());
		return unsolvedSymbols;
	}


	private void resolveTypes(boolean eagerExpressionResolve) {
		this.eagerExpressionResolve = eagerExpressionResolve;
		Iterator<Entity> iterator = repo.sortedFileIterator();
		while(iterator.hasNext()) {
			Entity entity= iterator.next();
			entity.inferEntities(this);
		}
	}
	

	@Override
	public Collection<Entity> getImportedRelationEntities(List<Import> importedNames) {
		return importLookupStrategy.getImportedRelationEntities(importedNames);
	}

	@Override
	public Collection<Entity> getImportedTypes(List<Import> importedNames, FileEntity fileEntity) {
		HashSet<UnsolvedBindings> unsolved = new HashSet<UnsolvedBindings>();
		Collection<Entity> result = importLookupStrategy.getImportedTypes(importedNames,unsolved);
		for (UnsolvedBindings item:unsolved) {
			item.setFromEntity(fileEntity);
			addUnsolvedBinding(item);
		}
		return result;
	}

	private void addUnsolvedBinding(UnsolvedBindings item) {
		if (!isCollectUnsolvedBindings) return;
		 	this.unsolvedSymbols.add(item);
	}
	@Override
	public Collection<Entity> getImportedFiles(List<Import> importedNames) {
		return importLookupStrategy.getImportedFiles(importedNames);
	}


	@Override
	public TypeEntity inferTypeFromName(Entity fromEntity, GenericName rawName) {
		Entity data = resolveName(fromEntity, rawName, true);
		if (data == null)
			return null;
		return data.getType();
	}


	@Override
	public Entity resolveName(Entity fromEntity, GenericName rawName, boolean searchImport) {
		if (rawName==null) return null;
		Entity entity = resolveNameInternal(fromEntity,rawName,searchImport);
		if (entity==null ) {
			if (!this.buildInTypeManager.isBuiltInType(rawName.getName())) {
				addUnsolvedBinding(new UnsolvedBindings(rawName.getName(), fromEntity));
			}
		}
		return entity;
	}

	private Entity resolveNameInternal(Entity fromEntity, GenericName rawName, boolean searchImport) {
		if (rawName==null || rawName.getName()==null)
			return null;
		if (buildInTypeManager.isBuiltInType(rawName.getName())) {
			return TypeEntity.buildInType;
		}
		// qualified name will first try global name directly
		if (rawName.startsWith(".")) {
			rawName = rawName.substring(1);
			if (repo.getEntity(rawName) != null)
				return repo.getEntity(rawName);
		}
		Entity entity = null;
		int indexCount = 0;
		String name = rawName.getName();
		if (fromEntity==null) return null;
		do {
			entity = lookupEntity(fromEntity, name, searchImport);
			if (entity!=null ) {
				break;
			}
			if (importLookupStrategy.supportGlobalNameLookup()) {
				if (repo.getEntity(name) != null) {
					entity = repo.getEntity(name);
					break;
				}
			}
			
			indexCount++;
			if (name.contains("."))
				name = name.substring(0,name.lastIndexOf('.'));
			else
				break;
		}while (true);
		if (entity == null) {
			return null;
		}
		String[] names = rawName.getName().split("\\.");
		if (names.length == 0)
			return null;
		if (names.length == 1) {
			return entity;
		}
		// then find the subsequent symbols
		return findEntitySince(entity, names, names.length-indexCount);
	}
	
	private Entity lookupEntity(Entity fromEntity, String name, boolean searchImport) {
		if (name.equals("this") || name.equals("class") ) {
			TypeEntity entityType = (TypeEntity) (fromEntity.getAncestorOfType(TypeEntity.class));
			return entityType;
		} else if (name.equals("super")) {
			TypeEntity parent = (TypeEntity) (fromEntity.getAncestorOfType(TypeEntity.class));
			if (parent != null) {
				TypeEntity parentType = parent.getInheritedType();
				if (parentType!=null) 
					return parentType;
			}
		}
		Entity inferData = findEntityUnderSamePackage(fromEntity, name);
		if (inferData != null) {
			return inferData;
		}
		if (searchImport)
			inferData = lookupTypeInImported((FileEntity)(fromEntity.getAncestorOfType(FileEntity.class)), name);
		return inferData;
	}
	/**
	 * To lookup entity in case of a.b.c from a;
	 * @param precendenceEntity
	 * @param names
	 * @param nameIndex
	 * @return
	 */
	private Entity findEntitySince(Entity precendenceEntity, String[] names, int nameIndex) {
		if (nameIndex >= names.length) {
			return precendenceEntity;
		}
		if (nameIndex == -1) {
			System.err.println("No expected symbols: names"+Arrays.toString(names) +", index=" + nameIndex);
			return null;
		}
		//If it is not an entity with types (not a type, var, function), fall back to itself
		if (precendenceEntity.getType()==null) 
			return precendenceEntity;
			
		for (Entity child : precendenceEntity.getType().getChildren()) {
			if (child.getRawName().getName().equals(names[nameIndex])) {
				return findEntitySince(child, names, nameIndex + 1);
			}
		}
		return null;
	}

	@Override
	public Entity lookupTypeInImported(FileEntity fileEntity, String name) {
		if (fileEntity == null)
			return null;
		Entity type = importLookupStrategy.lookupImportedType(name, fileEntity);
		if (type != null)
			return type;
		return null;
	}


	/**
	 * In Java/C++ etc, the same package names should take priority of resolving.
	 * the entity lookup is implemented recursively.
	 * @param fromEntity
	 * @param name
	 * @return
	 */
	private Entity findEntityUnderSamePackage(Entity fromEntity, String name) {
		while (true) {
			Entity entity = fromEntity.getByName(name, new HashSet<>());
			if (entity!=null) return entity;
			fromEntity = fromEntity.getParent();
			if (fromEntity == null)
				break;
		}
		return null;
	}
	

	@Override
	public List<TypeEntity> calculateCandidateTypes(VarEntity fromEntity, List<FunctionCall> functionCalls) {
		if (buildInTypeManager.isBuildInTypeMethods(functionCalls)) {
			return new ArrayList<>();
		}
		if (!isDuckTypingDeduce) 
			return new ArrayList<>();
		return searchTypesInRepo(fromEntity, functionCalls);
	}

	private List<TypeEntity> searchTypesInRepo(VarEntity fromEntity, List<FunctionCall> functionCalls) {
		List<TypeEntity> types = new ArrayList<>();
		Iterator<Entity> iterator = repo.sortedFileIterator();
		while(iterator.hasNext()) {
			Entity f = iterator.next();
			if (f instanceof FileEntity) {
				for (TypeEntity type:((FileEntity)f).getDeclaredTypes()) {
					FunctionMatcher functionMatcher = new FunctionMatcher(type.getFunctions());
					if (functionMatcher.containsAll(functionCalls)) {
						types.add(type);
					}
				}
			}
		}
		return types;
	}

	@Override
	public boolean isEagerExpressionResolve() {
		return eagerExpressionResolve;
	}

	@Override
	public EntityRepo getRepo() {
		return repo;
	}

}
