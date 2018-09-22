package depends.extractor;

import java.util.HashMap;

public class LangWorkers {
	private static LangWorkers inst = new LangWorkers();
	public HashMap<String, AbstractLangWorker> workers = new HashMap<>();
	public static LangWorkers getRegistry() {
		return inst;
	}
	public AbstractLangWorker getWorkerOf(String lang) {
		return workers.get(lang);
	}
	public void register(AbstractLangWorker worker) {
		if (getWorkerOf(worker.supportedLanguage())!=null) return;
		workers.put(worker.supportedLanguage(), worker);
	}
}
