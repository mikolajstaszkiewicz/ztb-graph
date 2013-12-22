package pl.edu.agh.kis.ztb.graph.experiment.impl;

public class Experiment9 extends AbstractExperiment {
	//vle(p,6,true,false),
	@Override
	protected void setup() {
		transformer.vertexLabelEdit("p", 6, "true", "false");
	}

}