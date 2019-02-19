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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import depends.entity.CandidateTypes;
import depends.entity.ContainerEntity;
import depends.entity.Entity;
import depends.entity.FileEntity;
import depends.entity.FunctionCall;
import depends.entity.MultiDeclareEntities;
import depends.entity.TypeEntity;
import depends.entity.VarEntity;
import depends.entity.repo.BuiltInType;
import depends.entity.repo.EntityRepo;
import depends.entity.repo.NullBuiltInType;
import depends.importtypes.Import;

public class Inferer {
	private static final Logger logger = LoggerFactory.getLogger(Inferer.class);

	static final public TypeEntity buildInType = new TypeEntity("built-in", null, -1);
	static final public TypeEntity externalType = new TypeEntity("external", null, -1);
	static final public TypeEntity genericParameterType = new TypeEntity("T", null, -1);
	private BuiltInType buildInTypeManager = new NullBuiltInType();
	private ImportLookupStrategy importLookupStrategy;
	private HashSet<String> unsolvedSymbols;
	private EntityRepo repo;

	private boolean eagerExpressionResolve = false;

	public Inferer(EntityRepo repo, ImportLookupStrategy importLookupStrategy, BuiltInType buildInTypeManager, boolean eagerExpressionResolve) {
		this.repo = repo;
		this.importLookupStrategy = importLookupStrategy;
		this.buildInTypeManager = buildInTypeManager;
		unsolvedSymbols= new HashSet<>();
		this.eagerExpressionResolve = eagerExpressionResolve;
	}

	/**
	 * Resolve all bindings
	 * - Firstly, we resolve all types from there names.
	 * - Secondly, we resolve all expressions (expression will use type infomation of previous step
	 */
	public  Set<String> resolveAllBindings() {
		resolveTypes();
		resolveExpressoins(); 
		System.out.println("Dependency analaysing....");
		new RelationCounter(repo.getEntities()).computeRelations();
		System.out.println("Dependency done....");
		return unsolvedSymbols;		
	}

	private void resolveTypes() {
		for (Entity entity:repo.getEntities()) {
			if (!(entity instanceof FileEntity)) continue;
			entity.inferEntities(this);
		}
	}
	private void resolveExpressoins() {
		for (Entity entity:repo.getEntities()) {
			if ((entity instanceof ContainerEntity))
				((ContainerEntity)entity).resolveExpressions(this);
		}
	}
	
	/**
	 * For types start with the prefix, it will be treated as built-in type
	 * For example, java.io.* in Java, or __ in C/C++
	 * @param prefix
	 * @return
	 */
	public boolean isBuiltInTypePrefix(String prefix) {
		return buildInTypeManager.isBuiltInTypePrefix(prefix);
	}
	
	/**
	 * Different languages have different strategy on how to compute the imported types
	 * and the imported files.
	 * For example, in C/C++, both imported types (using namespace, using <type>) and imported files exists. 
	 * while in java, only 'import class/function, or import wildcard class.* package.* exists. 
	 */
	public List<Entity> getImportedRelationEntities(List<Import> importedNames) {
		return importLookupStrategy.getImportedRelationEntities(importedNames, repo);
	}

	public List<Entity> getImportedTypes(List<Import> importedNames) {
		return importLookupStrategy.getImportedTypes(importedNames, repo);
	}

	public List<Entity> getImportedFiles(List<Import> importedNames) {
		return importLookupStrategy.getImportedFiles(importedNames, repo);
	}

	/**
	 * By given raw name, to infer the type of the name
	 * for example
	 * (It is just a wrapper of resolve name)
	 *   if it is a class, the class is the type
	 *   if it is a function, the return type is the type
	 *   if it is a variable, type of variable is the type 
	 * @param fromEntity
	 * @param rawName
	 * @return
	 */
	public TypeEntity inferTypeFromName(Entity fromEntity, String rawName) {
		Entity data = resolveName(fromEntity, rawName, true);
		if (data == null)
			return null;
		return data.getType();
	}

	/**
	 * By given raw name, to infer the entity of the name
	 * @param fromEntity
	 * @param rawName
	 * @param searchImport
	 * @return
	 */
	public Entity resolveName(Entity fromEntity, String rawName, boolean searchImport) {
		Entity entity = resolveNameInternal(fromEntity,rawName,searchImport);
		if (logger.isDebugEnabled()) {
			logger.debug("resolve name " + rawName + " from " + fromEntity.getQualifiedName() +" ==> "
						+ (entity==null?"null":entity.getQualifiedName()));
		}
		return entity;
	}

	private Entity resolveNameInternal(Entity fromEntity, String rawName, boolean searchImport) {
		if (rawName == null)
			return null;
		if (buildInTypeManager.isBuiltInType(rawName)) {
			return buildInType;
		}
		if (buildInTypeManager.isBuiltInTypePrefix(rawName)) {
			return buildInType;
		}
		// qualified name will first try global name directly
		if (rawName.startsWith(".")) {
			rawName = rawName.substring(1);
			if (repo.getEntity(rawName) != null)
				return repo.getEntity(rawName);
		}
		
		if (rawName.contains(".")) {
			if (repo.getEntity(rawName) != null)
				return repo.getEntity(rawName);
		}
		// first we lookup the first symbol
		String[] names = rawName.split("\\.");
		if (names.length == 0)
			return null;
		Entity entity = lookupEntity(fromEntity, names[0], searchImport);
		if (entity == null) {
			return null;
		}
		if (names.length == 1) {
			return entity;
		}
		// then find the subsequent symbols
		return findEntitySince(entity, names, 1);
	}
	
	private Entity lookupEntity(Entity fromEntity, String name, boolean searcImport) {
		if (name.equals("this") || name.equals("class")) {
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
		if (searcImport)
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
		//If it is not an entity with types (not a type, var, function), fall back to itself
		if (precendenceEntity.getType()==null) 
			return precendenceEntity;
		
		for (Entity child : precendenceEntity.getType().getChildren()) {
			if (child.getRawName().equals(names[nameIndex])) {
				return findEntitySince(child, names, nameIndex + 1);
			}
		}
		return null;
	}

	private Entity lookupTypeInImported(FileEntity fileEntity, String name) {
		if (fileEntity == null)
			return null;
		Entity type = importLookupStrategy.lookupImportedType(name, fileEntity, repo,this);
		if (type != null)
			return type;
		return externalType;
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
			Entity entity = tryToFindEntityWithName(fromEntity, name);
			if (entity != null)
				return entity;
			entity = findEntityInChild(fromEntity,name);
			if (entity!=null) return entity;
			
			if (fromEntity instanceof TypeEntity) {
				TypeEntity type = (TypeEntity)fromEntity;
				while(true) {
					if (type.getInheritedTypes().size()==0) break;
					for (TypeEntity child:type.getInheritedTypes()) {
						entity = findEntityInChild(child,name);
						if (entity!=null) return entity;
						type = child;
					}
				}
				while(true) {
					if (type.getImplementedTypes().size()==0) break;
					for (TypeEntity child:type.getImplementedTypes()) {
						entity = findEntityInChild(child,name);
						if (entity!=null) return entity;
						type = child;
					}
				}
			}
			
			if (fromEntity instanceof FileEntity) {
				FileEntity file = (FileEntity)fromEntity;
				for (TypeEntity type:file.getDeclaredTypes()) {
					if (type.getRawName().equals(name)||
						suffixMatch(name,type.getQualifiedName())) {
						return type;
					}
				}
			}
			
			for (Entity child : fromEntity.getChildren()) {
				if (child instanceof FileEntity) {
					for (Entity classUnderFile : child.getChildren()) {
						entity = tryToFindEntityWithName(classUnderFile, name);
						if (entity != null)
							return entity;
					}
				}
			}
			fromEntity = fromEntity.getParent();
			if (fromEntity == null)
				break;
		}
		return null;
	}
	
	
	private boolean suffixMatch(String name, String qualifiedName) {
		if (qualifiedName.contains(".")) {
			if (!name.startsWith(".")) name = "." +name;
			return qualifiedName.endsWith(name);
		}
		else {
			return qualifiedName.equals(name);
		}
	}

	private Entity findEntityInChild(Entity fromEntity,String name) {
		Entity entity =null;
		for (Entity child : fromEntity.getChildren()) {
			entity = tryToFindEntityWithName(child, name);
			if (entity != null)
				return entity;
		}
		return entity;
	}
	
	/**
	 * Only used by findEntityUnderSamePackage
	 * @param fromEntity
	 * @param name
	 * @return
	 */
	private Entity tryToFindEntityWithName(Entity fromEntity, String name) {
		if (fromEntity instanceof CandidateTypes) {
			for (TypeEntity type:((CandidateTypes)fromEntity).getCandidateTypes()) {
				Entity e = tryToFindEntityWithNameSureSingleEntity(type,name);
				if (e !=null) return e;
			}
			return null;
		}
		else 
			return tryToFindEntityWithNameSureSingleEntity(fromEntity,name);
	}
	
	private Entity tryToFindEntityWithNameSureSingleEntity(Entity fromEntity, String name) {
		if (!fromEntity.getRawName().equals(name))
			return null;
		if (fromEntity instanceof MultiDeclareEntities) {
			for (Entity declaredEntitiy : ((MultiDeclareEntities) fromEntity).getEntities()) {
				if (declaredEntitiy.getRawName().equals(name) && declaredEntitiy instanceof TypeEntity) {
					return declaredEntitiy;
				}
			}
		}
		return fromEntity;
	}

	/**
	 * Deduce type based on function calls
	 * If the function call is a subset of a type, then the type could be a candidate of the var's type 
	 * @param fromEntity
	 * @param functionCalls
	 * @return
	 */
	public List<TypeEntity> calculateCandidateTypes(VarEntity fromEntity, List<FunctionCall> functionCalls) {
		List<TypeEntity> types = new ArrayList<>();
		FileEntity file = (FileEntity) fromEntity.getAncestorOfType(FileEntity.class);
		searchAllTypesUnder(types,file, functionCalls);
		return types;
	}

	private void searchAllTypesUnder(List<TypeEntity> types, FileEntity file, List<FunctionCall> functionCalls) {
		if (file==null) {
			System.err.println("file  should not been null");
			return;
		}
		Set<FileEntity> files = file.getImportedFilesInAllLevel();
		for(FileEntity f:files) {
			for (TypeEntity type:f.getDeclaredTypes()) {
				FunctionMatcher functionMatcher = new FunctionMatcher(type.getFunctions());
				if (functionMatcher.containsAll(functionCalls)) {
					types.add(type);
				}
			}
		}
	}

	public boolean isEagerExpressionResolve() {
		return eagerExpressionResolve;
	}
}
