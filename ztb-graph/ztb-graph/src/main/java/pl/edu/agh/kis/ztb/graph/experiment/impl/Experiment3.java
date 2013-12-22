package pl.edu.agh.kis.ztb.graph.experiment.impl;

public class Experiment3 extends AbstractExperiment {
	//vle(p,6,false,true),
	@Override
	protected void setup() {
		transformer.vertexLabelEdit("p", 6, "false", "true");
	}

}