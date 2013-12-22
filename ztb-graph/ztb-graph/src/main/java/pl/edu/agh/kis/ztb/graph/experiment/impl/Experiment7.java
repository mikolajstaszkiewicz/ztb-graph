package pl.edu.agh.kis.ztb.graph.experiment.impl;

public class Experiment7 extends AbstractExperiment {
	//vle(p,1,true,false),
	@Override
	protected void setup() {
		transformer.vertexLabelEdit("p", 1, "true", "false");
	}

}