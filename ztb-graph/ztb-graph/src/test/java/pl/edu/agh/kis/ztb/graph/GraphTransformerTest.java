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
import org.neo4j.helpers.collection.IteratorUtil;

import pl.edu.agh.kis.ztb.graph.experiment.ExperimentRunner;
import pl.edu.agh.kis.ztb.graph.impl.GraphTransformerImpl;
import pl.edu.agh.kis.ztb.graph.util.GraphConst;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.util.io.graphml.GraphMLReader;

@RunWith(value = Parameterized.class)
public class GraphTransformerTest extends AGraphTest {
	GraphTransformerImpl transformer = new GraphTransformerImpl();

	public GraphTransformerTest(Graph graph) {
		super(graph);
	}

	@Before
	public void test() throws IOException {
		GraphMLReader.inputGraph(graph, "src/main/resources/graph-ml.xml");
		transformer.setGraph(graph);
	}

	//	@Test
	public void graphLoadTest() {
		Assert.assertEquals(5102, IteratorUtil.count(graph.query().has(GraphConst.VERTEX_INDEX).vertices()));
		Assert.assertEquals(5102, IteratorUtil.count(graph.query().has(GraphConst.VERTEX_TYPE).vertices()));
		Assert.assertEquals(9300, IteratorUtil.count(graph.query().edges()));
	}

	@Test
	public void experimentTest() throws IOException {
		ExperimentRunner experimentRunner = new ExperimentRunner();
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
