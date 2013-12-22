package pl.edu.agh.kis.ztb.graph.impl;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import pl.edu.agh.kis.ztb.graph.GraphTransformer;
import pl.edu.agh.kis.ztb.graph.function.VertexLabelRemove;
import pl.edu.agh.kis.ztb.graph.function.VertexLabelSet;
import pl.edu.agh.kis.ztb.graph.util.ComparableVertex;
import pl.edu.agh.kis.ztb.graph.util.GraphConst;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.java.GremlinPipeline;
import com.tinkerpop.pipes.util.structures.Row;

public class GraphTransformerImpl implements GraphTransformer {
	

	private Graph graph;

	public GraphTransformerImpl() {
	};

	public GraphTransformerImpl(Graph graph) {
		this.graph = graph;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void show(OutputStream os) throws IOException {
		Iterator<Row> rowsIterator = new GremlinPipeline<>(graph).V(GraphConst.VERTEX_TYPE, "s").as("s").bothE()
				.as("edge").bothV().as("c").has(GraphConst.VERTEX_TYPE, "c").select().iterator();
		List<ComparableVertex> orderedList = new ArrayList<ComparableVertex>();
		while (rowsIterator.hasNext()) {
			Row row = rowsIterator.next();
			int sIndex = ((Vertex) row.getColumn("s")).getProperty(GraphConst.VERTEX_INDEX);
			String edge = ((Edge) row.getColumn("edge")).getLabel();
			Vertex vertexC = (Vertex) row.getColumn("c");
			int cIndex= vertexC.getProperty(GraphConst.VERTEX_INDEX);
			String cLabel= vertexC.getProperty(GraphConst.VERTEX_LABEL);
			orderedList.add(new ComparableVertex("s", sIndex, edge, "c", cIndex, cLabel));
		}
		Collections.sort(orderedList);
		try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os))) {
			for (ComparableVertex vertex : orderedList) {
				writer.write(vertex.getRow());
				writer.newLine();
			}
		}
	}

	public Vertex findVertex(String vertexType, int vertexIndex) {
		return graph.query().has(GraphConst.VERTEX_TYPE, vertexType).has(GraphConst.VERTEX_INDEX, vertexIndex)
				.vertices().iterator().next();
	}

	@Override
	public void vertexLabelAdd(String vertexType, int vertexNubmer, String label) {
		new GremlinPipeline<Vertex, Vertex>(graph).V(GraphConst.VERTEX_TYPE, vertexType).as("result")
				.has(GraphConst.VERTEX_INDEX, vertexNubmer).back("result").cast(Vertex.class)
				.transform(new VertexLabelSet(label)).iterate();
	}

	@Override
	public void vertexLabelAdd(String vertexType, String label) {
		new GremlinPipeline<Vertex, Vertex>(graph).V(GraphConst.VERTEX_TYPE, vertexType)
				.transform(new VertexLabelSet(label)).iterate();
	}

	@Override
	public void vertexLabelClear() {
		new GremlinPipeline<>(graph).V().transform(new VertexLabelRemove()).iterate();
	}

	@Override
	public void vertexLabelEdit(String vertexType, int vertexIndex, String oldLabel, String newLabel) {
		new GremlinPipeline<>(graph).V(GraphConst.VERTEX_TYPE, vertexType).as("vertex")
				.has(GraphConst.VERTEX_INDEX, vertexIndex).has(GraphConst.VERTEX_LABEL, oldLabel).back("vertex")
				.cast(Vertex.class).transform(new VertexLabelSet(newLabel)).iterate();
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}

}
