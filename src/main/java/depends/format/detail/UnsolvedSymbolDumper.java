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
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import depends.extractor.UnsolvedBindings;
import multilang.depends.util.file.strip.LeadingNameStripper;

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

	public void output() {
		outputDetail();
		outputGrouped();
	}

	private void outputGrouped() {
		TreeMap<String, Set<String>> grouped = new TreeMap<String, Set<String>>();
		for (UnsolvedBindings symbol: unsolved) {
			String depended = symbol.getRawName();
			String from = leadingNameStripper.stripFilename(symbol.getSourceDisplay());
			Set<String> list = grouped.get(depended);
			if (list==null) {
				list = new HashSet<>();
				grouped.put(depended, list);
			}
			list.add(from);
		}
		ObjectMapper om = new ObjectMapper();
		om.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
		om.configure(SerializationFeature.INDENT_OUTPUT, true);
		om.setSerializationInclusion(Include.NON_NULL);
		try {
			om.writerWithDefaultPrettyPrinter().writeValue(new File(outputDir + File.separator + name +"-PotentialExternalDependencies.json"), grouped);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

	private void outputDetail() {
		PrintWriter writer;
		try {
			writer = new PrintWriter(outputDir + File.separator + name +"-PotentialExternalDependencies.txt");
			for (UnsolvedBindings symbol: unsolved) {
				String source = leadingNameStripper.stripFilename(symbol.getSourceDisplay());
            	writer.println(""+symbol.getRawName()+", "+source);
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
