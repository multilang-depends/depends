package depends.relations;

import depends.entity.*;
import depends.entity.repo.EntityRepo;
import depends.extractor.UnsolvedBindings;
import depends.importtypes.Import;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface IBindingResolver {
    /**
     * Resolve all bindings
     * - Firstly, we resolve all types from there names.
     * - Secondly, we resolve all expressions (expression will use type infomation of previous step
     */
    Set<UnsolvedBindings> resolveAllBindings(boolean isEagerExpressionResolve);

    /**
     * Different languages have different strategy on how to compute the imported types
     * and the imported files.
     * For example, in C/C++, both imported types (using namespace, using <type>) and imported files exists.
     * while in java, only 'import class/function, or import wildcard class.* package.* exists.
     */
    Collection<Entity> getImportedRelationEntities(List<Import> importedNames);

    Collection<Entity> getImportedTypes(List<Import> importedNames, FileEntity fileEntity);

    Collection<Entity> getImportedFiles(List<Import> importedNames);

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
    TypeEntity inferTypeFromName(Entity fromEntity, GenericName rawName);

    /**
     * By given raw name, to infer the entity of the name
     * @param fromEntity
     * @param rawName
     * @param searchImport
     * @return
     */
    Entity resolveName(Entity fromEntity, GenericName rawName, boolean searchImport);

    Entity lookupTypeInImported(FileEntity fileEntity, String name);

    /**
     * Deduce type based on function calls
     * If the function call is a subset of a type, then the type could be a candidate of the var's type
     * @param fromEntity
     * @param functionCalls
     * @return
     */
    List<TypeEntity> calculateCandidateTypes(VarEntity fromEntity, List<FunctionCall> functionCalls);

    boolean isEagerExpressionResolve();

    EntityRepo getRepo();
}
