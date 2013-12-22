package pl.edu.agh.kis.ztb.graph.experiment.impl;

import pl.edu.agh.kis.ztb.graph.GraphTransformer;
import pl.edu.agh.kis.ztb.graph.RuleExecutor;
import pl.edu.agh.kis.ztb.graph.experiment.Experiment;
import pl.edu.agh.kis.ztb.graph.impl.GraphTransformerImpl;
import pl.edu.agh.kis.ztb.graph.impl.RuleExecutorImpl;

import com.tinkerpop.blueprints.Graph;

public abstract class AbstractExperiment implements Experiment {
	protected GraphTransformer transformer;
	protected RuleExecutor ruleExecutor;

	@Override
	public void execute(Graph graph) {
		transformer = new GraphTransformerImpl(graph);
		ruleExecutor = new RuleExecutorImpl(graph, transformer);
		setup();
		ruleExecutor.runAll();
	}

	abstract protected void setup();
}
