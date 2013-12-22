package pl.edu.agh.kis.ztb.graph.function;

import pl.edu.agh.kis.ztb.graph.util.GraphConst;

import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.pipes.util.PipesFunction;
import com.tinkerpop.pipes.util.structures.Pair;

public class VertexOrder extends PipesFunction<Pair<Vertex, Vertex>, Integer> {

	@Override
	public Integer compute(Pair<Vertex, Vertex> pair) {
		int a = (Integer) pair.getA().getProperty(GraphConst.VERTEX_INDEX);
		int b = (Integer) pair.getB().getProperty(GraphConst.VERTEX_INDEX);
		return a - b;
	}

}
