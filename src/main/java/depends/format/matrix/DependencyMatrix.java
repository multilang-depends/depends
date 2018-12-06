package depends.format.matrix;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import depends.entity.repo.EntityRepo;

public class DependencyMatrix {
    private Map<Integer, Map<Integer, Map<String, Integer>>> relations = new HashMap<Integer, Map<Integer, Map<String, Integer>>>();
    private Map<Integer, Map<Integer, Map<String, Integer>>> reMappedRelations = new HashMap<Integer, Map<Integer, Map<String, Integer>>>();

    private ArrayList<String> nodes;
    private ArrayList<String> reMappedNodes;
	private Integer relationCount=0;
    public DependencyMatrix() {
    }

    /**
     * 优先返回重映射的Id
     * @return
     */
	public Map<Integer, Map<Integer, Map<String, Integer>>> getRelations() {
		if (reMappedRelations.size()>0) 
			return reMappedRelations;
        return relations;
    }

    public void addDependency(String depType, Integer from, Integer to) {
		if(from.equals(to) || from == -1 || to == -1) {
		    return;
		}
		if(!relations.containsKey(from)) {
		    relations.put(from, new HashMap<Integer, Map<String, Integer>>());
		}
		if(!relations.get(from).containsKey(to)) {
		    relations.get(from).put(to, new HashMap<String, Integer>());
		}
		if(!relations.get(from).get(to).containsKey(depType)) {
		    relations.get(from).get(to).put(depType, 0);
		}

		int weight = relations.get(from).get(to).get(depType) + 1;
		relations.get(from).get(to).put(depType, weight);
		relationCount++;
	}

	public ArrayList<String> getNodes() {
		if (reMappedRelations.size()>0) 
			return reMappedNodes;
		return nodes;
	}

	public void setNodes(ArrayList<String> nodes) {
		this.nodes = nodes;
	}


	private Integer translateToNewId(EntityRepo repo, HashMap<String, Integer> nodesMap, Integer key) {
		return nodesMap.get(repo.getEntity(key).getRawName());
	}

	public void remapIds(EntityRepo repo) {
		HashMap<String, Integer> nodesMap = new HashMap<>();
		reMappedNodes = new ArrayList<>(nodes);
		reMappedNodes.sort(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});

		for (int i=0;i<reMappedNodes.size();i++) {
			nodesMap.put(reMappedNodes.get(i), i);
		}
		
		for (Integer key:relations.keySet()) {
			Map<Integer, Map<String, Integer>> entry = relations.get(key);
			HashMap<Integer, Map<String, Integer>> newEntry = new HashMap<>();
			reMappedRelations.put(translateToNewId(repo, nodesMap, key), newEntry);
			for (Integer idsTo:entry.keySet()) {
				Map<String, Integer> value = entry.get(idsTo);
				newEntry.put(translateToNewId(repo, nodesMap, idsTo), value);
			}
		}		
	}

	public Integer relationCount() {
		return relationCount;
	}


}
