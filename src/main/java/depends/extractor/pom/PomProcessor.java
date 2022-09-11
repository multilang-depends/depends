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

package depends.extractor.pom;

import depends.entity.repo.BuiltInType;
import depends.extractor.AbstractLangProcessor;
import depends.extractor.FileParser;
import depends.extractor.empty.EmptyBuiltInType;
import depends.relations.ImportLookupStrategy;

import java.util.ArrayList;
import java.util.List;

import static depends.deptypes.DependencyType.PomParent;
import static depends.deptypes.DependencyType.*;

public class PomProcessor extends AbstractLangProcessor {


	@Override
	public String supportedLanguage() {
		return "pom";
	}

	@Override
	public String[] fileSuffixes() {
		return new String[] {".pom"};
	}

	@Override
	public ImportLookupStrategy getImportLookupStrategy() {
		return new PomImportLookupStategy(entityRepo);
	}

	@Override
	public BuiltInType getBuiltInType() {
		return new EmptyBuiltInType();
	}

	@Override
	public FileParser createFileParser() {
		return new PomFileParser(entityRepo,includePaths(),this, bindingResolver);
	}
	
	@Override
	public List<String> supportedRelations() {
		ArrayList<String> depedencyTypes = new ArrayList<>();
		depedencyTypes.add(PomParent);
		depedencyTypes.add(PomPlugin);
		depedencyTypes.add(PomDependency);
		return depedencyTypes;
	}			
	
	@Override
	public String getRelationMapping(String relation) {
		if (relation.equals(IMPORT)) return PomParent;
		if (relation.equals(USE)) return PomPlugin;
		if (relation.equals(CONTAIN)) return PomDependency;
		return relation;
	}
}
