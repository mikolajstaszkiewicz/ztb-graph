package pl.edu.agh.kis.ztb.graph.experiment.impl;

public class Experiment4 extends AbstractExperiment {
	//vle(d,1,false,true),
	@Override
	protected void setup() {
		transformer.vertexLabelEdit("d", 1, "false", "true");
	}

}