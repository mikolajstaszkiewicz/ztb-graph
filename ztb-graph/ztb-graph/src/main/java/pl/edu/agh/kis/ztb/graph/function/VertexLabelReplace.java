package pl.edu.agh.kis.ztb.graph.function;

import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.pipes.util.PipesFunction;

public class VertexLabelReplace extends PipesFunction<Vertex, Vertex> {
	private String oldLabel;
	private String newLabel;

	public VertexLabelReplace(String oldLabel, String newLabel) {
		this.oldLabel = oldLabel;
		this.newLabel = newLabel;
	}

	@Override
	public Vertex compute(Vertex argument) {
		argument.removeProperty(oldLabel);
		argument.setProperty(newLabel, newLabel);
		return argument;
	}
}