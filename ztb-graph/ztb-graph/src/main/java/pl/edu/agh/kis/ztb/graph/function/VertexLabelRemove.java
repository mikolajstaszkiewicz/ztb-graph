package pl.edu.agh.kis.ztb.graph.function;

import pl.edu.agh.kis.ztb.graph.util.GraphUtil;

import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.pipes.util.PipesFunction;

public class VertexLabelRemove extends PipesFunction<Vertex, Vertex> {

	@Override
	public Vertex compute(Vertex argument) {
		for(String key :GraphUtil.getVertexLabels(argument)){
			argument.removeProperty(key);
		}
		return argument;
	}
}