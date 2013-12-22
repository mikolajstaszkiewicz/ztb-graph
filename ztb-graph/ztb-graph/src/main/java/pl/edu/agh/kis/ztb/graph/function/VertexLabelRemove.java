package pl.edu.agh.kis.ztb.graph.function;

import pl.edu.agh.kis.ztb.graph.util.GraphConst;

import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.pipes.util.PipesFunction;

public class VertexLabelRemove extends PipesFunction<Vertex, Vertex> {

	@Override
	public Vertex compute(Vertex argument) {
		argument.removeProperty(GraphConst.VERTEX_LABEL);
		return argument;
	}

}