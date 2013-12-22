package pl.edu.agh.kis.ztb.graph.experiment.impl;

import com.tinkerpop.blueprints.Graph;

import pl.edu.agh.kis.ztb.graph.GraphTransformer;
import pl.edu.agh.kis.ztb.graph.RuleExecutor;
import pl.edu.agh.kis.ztb.graph.experiment.Experiment;
import pl.edu.agh.kis.ztb.graph.impl.GraphTransformerImpl;
import pl.edu.agh.kis.ztb.graph.impl.RuleExecutorImpl;

public class Experiment0 implements Experiment {
	protected GraphTransformer transformer;
	protected RuleExecutor ruleExecutor;

	@Override
	public void execute(Graph graph) {
		transformer = new GraphTransformerImpl(graph);
		ruleExecutor = new RuleExecutorImpl(graph, transformer);
		ruleExecutor.init();
	}

}
