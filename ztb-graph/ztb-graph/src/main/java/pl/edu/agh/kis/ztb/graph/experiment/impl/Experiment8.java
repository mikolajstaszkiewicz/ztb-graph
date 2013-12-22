package pl.edu.agh.kis.ztb.graph.experiment.impl;

public class Experiment8 extends AbstractExperiment {
	//vle(d,8,true,false),
	@Override
	protected void setup() {
		transformer.vertexLabelEdit("d", 8, "true", "false");
	}

}