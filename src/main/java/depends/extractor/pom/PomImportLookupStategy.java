package depends.extractor.pom;

import java.util.ArrayList;
import java.util.List;

import depends.entity.Entity;
import depends.entity.repo.EntityRepo;
import depends.extractor.empty.EmptyImportLookupStategy;
import depends.importtypes.Import;

public class PomImportLookupStategy extends EmptyImportLookupStategy  {
	@Override
	public List<Entity> getImportedRelationEntities(List<Import> importedList, EntityRepo repo) {
		ArrayList<Entity> result = new ArrayList<>();
		for (Import importedItem:importedList) {
			Entity imported = repo.getEntity(importedItem.getContent());
			if (imported==null) continue;
			result.add(imported);
		}
		return result;
	}
}
