package pl.edu.agh.kis.ztb.graph.experiment.impl;

public class Experiment6 extends AbstractExperiment {
	//vle(d,1,true,false),
	@Override
	protected void setup() {
		transformer.vertexLabelEdit("d", 1, "true", "false");
	}

}