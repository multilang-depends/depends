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
