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

package depends.extractor;

import depends.entity.FileEntity;
import depends.entity.repo.EntityRepo;
import multilang.depends.util.file.FileUtil;

import java.io.IOException;

public abstract class FileParser {
	protected EntityRepo entityRepo;

	/**
	 * parse files
	 * @param filePath
	 * @throws IOException
	 */
	public final void parse(String filePath) throws IOException{
		filePath = FileUtil.uniqFilePath(filePath);
		/* If file already exist, skip it */
		FileEntity fileEntity = entityRepo.getFileEntity(filePath);
		if (fileEntity!=null) {
			System.out.println("already parsed " + filePath + "...skip");
			if (!fileEntity.isInProjectScope())
				fileEntity.setInProjectScope(true);
		}else {
			System.out.println("parsing " + filePath + "...");
			parseFile(filePath);
			entityRepo.completeFile(filePath);
		}
	}

	/**
	 * The actual file parser - it should put parsed entities into entityRepo;
	 * @param filePath - it is alread unique file path name
	 * @throws IOException
	 */
	protected abstract void parseFile(String filePath) throws IOException;

	protected boolean isPhase2Files(String filePath){
		return false;
	}

}
