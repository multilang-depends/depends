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

package depends.format.detail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Set;

import depends.extractor.UnsolvedBindings;
import depends.matrix.transform.strip.LeadingNameStripper;

public class UnsolvedSymbolDumper{
	private Set<UnsolvedBindings> unsolved;
	private String name;
	private String outputDir;
	private LeadingNameStripper leadingNameStripper;

	public UnsolvedSymbolDumper(Set<UnsolvedBindings> unsolved, String name, String outputDir, LeadingNameStripper leadingNameStripper) {
		this.unsolved = unsolved;
		this.name = name;
		this.outputDir = outputDir;
		this.leadingNameStripper = leadingNameStripper;
	}

	public boolean output() {
		PrintWriter writer;
		try {
			writer = new PrintWriter(outputDir + File.separator + name +"-unsolved.txt");
			for (UnsolvedBindings symbol: unsolved) {
				String source = leadingNameStripper.stripFilename(symbol.getSourceDisplay());
            	writer.println(""+symbol.getRawName()+", "+source);
			}
			writer.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}

}
