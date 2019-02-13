package depends.entity;

import java.util.HashSet;
import java.util.Set;

public class ImportedFileCollector {
	Set<FileEntity> checkedFiles = new HashSet<>();
	Set<FileEntity> files = new HashSet<>();

	public ImportedFileCollector(FileEntity fileEntity) {
		appendImportedFiles(fileEntity);
	}

	private void appendImportedFiles(FileEntity fileEntity) {

		if (checkedFiles.contains(fileEntity)) return;
		checkedFiles.add(fileEntity);
		files.add(fileEntity);
		for (Entity importedFile:fileEntity.getImportedFiles()) {
			if (importedFile instanceof FileEntity) {
				appendImportedFiles((FileEntity)importedFile);
			}
		}
	}

	public Set<FileEntity> getFiles() {
		return files;
	}
}
