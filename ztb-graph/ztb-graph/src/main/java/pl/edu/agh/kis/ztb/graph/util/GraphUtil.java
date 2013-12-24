package pl.edu.agh.kis.ztb.graph.util;

import java.util.Set;

import com.tinkerpop.blueprints.Vertex;

public class GraphUtil {

	public static Set<String> getVertexLabels(Vertex v) {
		Set<String> keys = v.getPropertyKeys();
		keys.remove(GraphConst.VERTEX_INDEX);
		keys.remove(GraphConst.VERTEX_TYPE);
		return keys;
	}
}
