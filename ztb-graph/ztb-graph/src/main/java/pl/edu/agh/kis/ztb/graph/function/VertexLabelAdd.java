package pl.edu.agh.kis.ztb.graph.function;

import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.pipes.util.PipesFunction;

public class VertexLabelAdd extends PipesFunction<Vertex, Vertex> {
	private String label;

	public VertexLabelAdd(String label) {
		this.label = label;
	}

	@Override
	public Vertex compute(Vertex argument) {
		argument.setProperty(label,label);
		return argument;
	}

}