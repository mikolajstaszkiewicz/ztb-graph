package pl.edu.agh.kis.ztb.graph.experiment.impl;


public class Experiment1 extends AbstractExperiment {

	//vle(k,1,false,true),
	@Override
	protected void setup() {
		transformer.vertexLabelEdit("k", 1, "false", "true");
	}

}
