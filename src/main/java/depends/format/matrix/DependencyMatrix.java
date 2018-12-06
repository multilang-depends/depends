package depends.format.matrix;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import depends.entity.repo.EntityRepo;

public class DependencyMatrix {
    private HashMap<String, DependencyPair> dependencyPairs = new HashMap<>();
    private ArrayList<String> nodes;
    private ArrayList<String> reMappedNodes;
	private Integer relationCount=0;
    public DependencyMatrix() {
    }
	
	public Collection<DependencyPair> getDependencyPairs() {
        return dependencyPairs.values();
    }

    public void addDependency(String depType, Integer from, Integer to) {
		if(from.equals(to) || from == -1 || to == -1) {
		    return;
		}
		if (dependencyPairs.get(DependencyPair.key(from,to))==null) {
			dependencyPairs.put(DependencyPair.key(from,to),new DependencyPair(from,to));
		}
		DependencyPair dependencyPair = dependencyPairs.get(DependencyPair.key(from,to));
		dependencyPair.addDependency(depType);
		relationCount++;
	}

	public ArrayList<String> getNodes() {
		return reMappedNodes;
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
		for (DependencyPair dependencyPair:dependencyPairs.values()) {
			Integer from = dependencyPair.getFrom();
			Integer to = dependencyPair.getTo();
			dependencyPair.reMap(translateToNewId(repo, nodesMap, from),translateToNewId(repo, nodesMap, to));
		}
	}

	public Integer relationCount() {
		return relationCount;
	}
}
