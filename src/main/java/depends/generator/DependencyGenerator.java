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

import java.util.List;

import depends.entity.Entity;
import depends.entity.PackageNamePrefixRemover;
import depends.entity.repo.EntityRepo;
import multilang.depends.util.file.path.EmptyFilenameWritter;
import multilang.depends.util.file.path.FilenameWritter;
import depends.matrix.core.DependencyDetail;
import depends.matrix.core.DependencyMatrix;
import multilang.depends.util.file.strip.EmptyLeadingNameStripper;
import multilang.depends.util.file.strip.ILeadingNameStrippper;

public abstract class DependencyGenerator {
	public abstract DependencyMatrix build(EntityRepo entityRepo,List<String> typeFilter);

	protected ILeadingNameStrippper stripper = new EmptyLeadingNameStripper();
	protected FilenameWritter filenameWritter = new EmptyFilenameWritter();
	private boolean generateDetail = false;
	
	public void setLeadingStripper(ILeadingNameStrippper stripper) {
		this.stripper = stripper;
	}
	protected DependencyDetail buildDescription(Entity fromEntity, Entity toEntity) {
		if (!generateDetail) return null;
		String srcName = PackageNamePrefixRemover.remove(fromEntity);
		String destName = PackageNamePrefixRemover.remove(toEntity);
		return new DependencyDetail(srcName,destName);
	}
	public void setFilenameRewritter(FilenameWritter filenameWritter) {
		this.filenameWritter = filenameWritter;
	}
	public void setGenerateDetail(boolean generateDetail) {
		this.generateDetail = generateDetail;
	}
}
