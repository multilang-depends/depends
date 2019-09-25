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

package depends.format.dot;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

import depends.format.AbstractFormatDependencyDumper;
import depends.matrix.core.DependencyMatrix;
import depends.matrix.core.DependencyPair;

public class DotFullnameDependencyDumper extends  AbstractFormatDependencyDumper{
	ArrayList<String> files  = null;
	@Override
	public String getFormatName() {
		return "dotx";
	}
	public DotFullnameDependencyDumper(DependencyMatrix dependencyMatrix, String projectName, String outputDir) {
		super(dependencyMatrix,projectName,outputDir);
	}
	@Override
	public boolean output() {
		PrintWriter writer;
		try {
			writer = new PrintWriter(composeFilename()+".dot");
			files = matrix.getNodes();
			
			writer.println("digraph");
			writer.println("{");
	        Collection<DependencyPair> dependencyPairs = matrix.getDependencyPairs();

	        addRelations(writer,dependencyPairs); 
			writer.println("}");
			writer.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}

	private void addRelations(PrintWriter writer, Collection<DependencyPair> dependencyPairs) {
		for (DependencyPair dependencyPair:dependencyPairs) {
            int src = dependencyPair.getFrom();
            int dst = dependencyPair.getTo();
        	writer.println("\t\""+files.get(src) + "\" -> \"" + files.get(dst) + "\";");
        }		
	}
}
