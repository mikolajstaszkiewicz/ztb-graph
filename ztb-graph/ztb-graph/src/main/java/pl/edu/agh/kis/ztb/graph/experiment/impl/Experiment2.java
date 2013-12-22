package pl.edu.agh.kis.ztb.graph.experiment.impl;


public class Experiment2 extends AbstractExperiment {
	//vle(p,1,false,true),
	@Override
	protected void setup() {
		transformer.vertexLabelEdit("p", 1, "false", "true");
	}

}
