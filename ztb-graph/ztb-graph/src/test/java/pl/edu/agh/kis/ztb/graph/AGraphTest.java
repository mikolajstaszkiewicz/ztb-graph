package pl.edu.agh.kis.ztb.graph;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.configuration.BaseConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import pl.edu.agh.kis.ztb.graph.impl.GraphTransformerImpl;

import com.thinkaurelius.titan.core.TitanFactory;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.impls.dex.DexGraph;
import com.tinkerpop.blueprints.impls.neo4j.Neo4jGraph;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;

@RunWith(value = Parameterized.class)
public class AGraphTest {
	protected GraphTransformer transformer;
	protected Graph graph;

	@Parameters
	public static Collection<Object[]> data() {
		final 	Configuration titanConfiguration = new BaseConfiguration();
		titanConfiguration.setProperty("storage.directory", "target/graph/titan");
		titanConfiguration.setProperty("storage.backend", "persistit");

		Object[][] data = new Object[][] {
				{new TinkerGraph()},
				{TitanFactory.open(titanConfiguration)},
				{new DexGraph("target/graph/dex")},
				{new Neo4jGraph("target/graph/titan")}
		};
		return Arrays.asList(data);
	}

	public AGraphTest(Graph graph) {
		this.graph = graph;
		transformer = new GraphTransformerImpl(graph);
	}
	
	@After
	public void clean() throws IOException{
		graph.shutdown();
		FileUtils.deleteDirectory(new File("target/graph"));
		
	}

}
