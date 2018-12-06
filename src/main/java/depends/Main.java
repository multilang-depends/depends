package depends;

import depends.extractor.AbstractLangWorker;
import depends.extractor.LangWorkers;
import depends.extractor.cpp.CppWorker;
import depends.extractor.java.JavaWorker;

public class Main {

	/**
	 * <executable> <lang> <dir> <include-dir> <project-name> 
	 * <executable> java test-dir not-care test
	 */
	public static void main(String[] args) {
		if (args.length < 4) {
			System.out.println("Usage");
			System.out.println("\t<executable> <lang> <dir> <include-dir> <project-name>"); 
			return;
		}
		String lang = args[0];
        String inputDir = args[1];
        String includeDir = args[2];
        String projectName = args[3];
		new JavaWorker(inputDir,includeDir).register();
		new CppWorker(inputDir,includeDir).register();
		AbstractLangWorker worker = LangWorkers.getRegistry().getWorkerOf(lang);
		if (worker == null){
			System.out.println("Not support this language: " + args[0]);
			return;
		}

		long startTime = System.currentTimeMillis();
		worker.work();
		worker.outputResult(projectName);
		long endTime = System.currentTimeMillis();
		System.out.println("Consumed time: " + (float) ((endTime - startTime) / 1000.00) + " s,  or "
				+ (float) ((endTime - startTime) / 60000.00) + " min.");

	}

}
