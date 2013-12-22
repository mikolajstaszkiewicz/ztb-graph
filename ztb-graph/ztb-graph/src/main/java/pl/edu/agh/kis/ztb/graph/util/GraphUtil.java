package pl.edu.agh.kis.ztb.graph.util;

import java.util.List;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.java.GremlinPipeline;

public class GraphUtil {

	public static void printLabels(List<Vertex> vertexes) {
		for (Vertex vertex : vertexes) {
			System.out.println("" + vertex.getProperty(GraphConst.VERTEX_TYPE)
					+ vertex.getProperty(GraphConst.VERTEX_INDEX) + " " + vertex.getProperty(GraphConst.VERTEX_LABEL));
		}
	}

	public static void printVertex(Graph graph, String type) {
		printLabels(new GremlinPipeline<>(graph).V(GraphConst.VERTEX_TYPE, type).toList());
	}

	public static void printGraph(Graph graph) {
		//		System.out.println(new GremlinPipeline<>(graph.getVertices()).toList().toString());
		System.out.println(new GremlinPipeline<>(graph.getEdges()).toList().toString());

	}
}
