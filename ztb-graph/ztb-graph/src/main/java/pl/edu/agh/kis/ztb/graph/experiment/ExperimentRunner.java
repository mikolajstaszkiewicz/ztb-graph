package pl.edu.agh.kis.ztb.graph.experiment;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.time.StopWatch;

import pl.edu.agh.kis.ztb.graph.GraphTransformer;
import pl.edu.agh.kis.ztb.graph.experiment.impl.Experiment0;
import pl.edu.agh.kis.ztb.graph.experiment.impl.Experiment1;
import pl.edu.agh.kis.ztb.graph.experiment.impl.Experiment2;
import pl.edu.agh.kis.ztb.graph.experiment.impl.Experiment3;
import pl.edu.agh.kis.ztb.graph.experiment.impl.Experiment4;
import pl.edu.agh.kis.ztb.graph.experiment.impl.Experiment5;
import pl.edu.agh.kis.ztb.graph.experiment.impl.Experiment6;
import pl.edu.agh.kis.ztb.graph.experiment.impl.Experiment7;
import pl.edu.agh.kis.ztb.graph.experiment.impl.Experiment8;
import pl.edu.agh.kis.ztb.graph.experiment.impl.Experiment9;
import pl.edu.agh.kis.ztb.graph.impl.GraphTransformerImpl;

import com.tinkerpop.blueprints.Graph;

public class ExperimentRunner {

	private boolean saveResult = false;
	private List<Experiment> experiments;

	public ExperimentRunner() {
		experiments = new ArrayList<Experiment>(10);
		experiments.add(new Experiment0());
		experiments.add(new Experiment1());
		experiments.add(new Experiment2());
		experiments.add(new Experiment3());
		experiments.add(new Experiment4());
		experiments.add(new Experiment5());
		experiments.add(new Experiment6());
		experiments.add(new Experiment7());
		experiments.add(new Experiment8());
		experiments.add(new Experiment9());

	}

	public void run(Graph graph) throws IOException {
		System.out.println(graph.getClass().getCanonicalName());
		GraphTransformer transformer = new GraphTransformerImpl(graph);
		StopWatch allExperimentWatch = new StopWatch();
		allExperimentWatch.start();
		for (int i = 0; i < experiments.size(); i++) {
			StopWatch singleExperimentWatch = new StopWatch();
			singleExperimentWatch.start();
			Experiment experiment = experiments.get(i);
			experiment.execute(graph);
			singleExperimentWatch.stop();
			long experimentTime = singleExperimentWatch.getTime();
			System.out.println(MessageFormat.format("Experiment {0} - {1}[ms] ", i, experimentTime));
			if (saveResult) {
				try (OutputStream os = new FileOutputStream(i + ".txt")) {
					transformer.show(os);
				}
			}
		}
		allExperimentWatch.stop();
		long allExperimentTime = allExperimentWatch.getTime();
		System.out.println(MessageFormat.format("All Experiments - {0}[ms] ", allExperimentTime));
	}

	public boolean isSaveResult() {
		return saveResult;
	}

	public void setSaveResult(boolean saveResult) {
		this.saveResult = saveResult;
	}

	
}
