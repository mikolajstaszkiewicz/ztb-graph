package pl.edu.agh.kis.ztb.graph.function;

import pl.edu.agh.kis.ztb.graph.util.GraphConst;

import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.pipes.util.PipesFunction;

public class VertexLabelSet extends PipesFunction<Vertex, Vertex> {
	private String label;

	public VertexLabelSet(String label) {
		this.label = label;
	}

	@Override
	public Vertex compute(Vertex argument) {
		argument.setProperty(GraphConst.VERTEX_LABEL, label);
		return argument;
	}

}