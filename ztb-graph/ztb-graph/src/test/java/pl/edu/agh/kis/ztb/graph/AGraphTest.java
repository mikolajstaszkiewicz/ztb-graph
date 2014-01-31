package pl.edu.agh.kis.ztb.graph;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.runners.Parameterized.Parameters;

import pl.edu.agh.kis.ztb.graph.impl.GraphTransformerImpl;

import com.lambdazen.bitsy.BitsyGraph;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.impls.dex.DexGraph;
import com.tinkerpop.blueprints.impls.neo4j.Neo4jGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;

public class AGraphTest {
	private static final String TARGET_GRAPH_PATH = "target/graph/";
	private static final String TINKER_PATH = TARGET_GRAPH_PATH + "tinker/";
	private static final String BITSY_PATH = TARGET_GRAPH_PATH + "bitsy/";
	private static final String ORIENT_PATH = TARGET_GRAPH_PATH + "orient/";
	private static final String NEO4J_PATH = TARGET_GRAPH_PATH + "neo4j/";
	private static final String DEX_PATH = TARGET_GRAPH_PATH + "dex/";
	
	private static final String DEX_URL = DEX_PATH + "dex.db";
	private static final String ORIENT_URL = "local:" + ORIENT_PATH;

	protected GraphTransformer transformer;
	protected Graph graph;

	private static Path bitsyPath;

	@BeforeClass
	public static void setUpFixture() throws IOException {

	}

	@Parameters
	public static Collection<Object[]> data() throws IOException {
		// Titan nie przechodzi testów i raczej nie przejdzie
		// final Configuration titanConfiguration = new BaseConfiguration();
		// titanConfiguration.setProperty("storage.directory",
		// "target/graph/titan");
		// titanConfiguration.setProperty("storage.backend", "persistit");

		FileUtils.deleteDirectory(new File(TARGET_GRAPH_PATH));
		new File(TARGET_GRAPH_PATH).mkdir();

		bitsyPath = FileSystems.getDefault().getPath(BITSY_PATH).toAbsolutePath();
		bitsyPath.toFile().mkdirs();

		new File("target/graph/orient").mkdir();

		Object[][] data = new Object[][] {
				
				{ new BitsyGraph(bitsyPath) },
				{ new BitsyGraph() },  //in-memory
				{ new TinkerGraph(TINKER_PATH) },
				{ new TinkerGraph() }, //in-memory
				{ new OrientGraph(ORIENT_URL) },
				{ new OrientGraph("memory:demo") }, //in-memory
				{ new Neo4jGraph(NEO4J_PATH) },
				{ new DexGraph(DEX_URL) },		
		};
		return Arrays.asList(data);
	}

	public AGraphTest(Graph graph) {
		this.graph = graph;
		transformer = new GraphTransformerImpl(graph);
	}

	@After
	public void clean() {
		graph.shutdown();
	}
}
