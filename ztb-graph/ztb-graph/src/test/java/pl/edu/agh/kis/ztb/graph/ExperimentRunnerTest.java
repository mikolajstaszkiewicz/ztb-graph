package pl.edu.agh.kis.ztb.graph;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import pl.edu.agh.kis.ztb.graph.experiment.ExperimentRunner;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.util.io.graphml.GraphMLReader;

@RunWith(value = Parameterized.class)
public class ExperimentRunnerTest extends AGraphTest {
	private ExperimentRunner experimentRunner = new ExperimentRunner();

	public ExperimentRunnerTest(Graph graph) {
		super(graph);
	}

	@Before
	public void test() throws IOException {
		GraphMLReader.inputGraph(graph, "src/main/resources/graph-ml.xml");
		experimentRunner.setSaveResult(true);
	}

	@Test
	public void experimentTest() throws IOException {
		experimentRunner.run(graph);
		for (int i = 0; i < 10; ++i) {
			System.out.println("experiment " + i);
			List<String> expected = FileUtils.readLines(new File("src/test/resources/" + i + ".txt"));
			List<String> actuals = FileUtils.readLines(new File(i + ".txt"));
			Assert.assertEquals(expected.size(), actuals.size());
			Assert.assertTrue(expected.containsAll(actuals));
		}
	}
}
