package pl.edu.agh.kis.ztb.graph;

import com.tinkerpop.blueprints.Graph;

public interface RuleExecutor {

	void init();

	void runAll();

	void setGraph(Graph graph);

	void setTransformer(GraphTransformer transformer);
}
