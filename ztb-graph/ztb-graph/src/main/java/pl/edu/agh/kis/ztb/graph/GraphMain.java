package pl.edu.agh.kis.ztb.graph;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;

import pl.edu.agh.kis.ztb.graph.experiment.ExperimentRunner;

import com.lambdazen.bitsy.BitsyGraph;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.impls.dex.DexGraph;
import com.tinkerpop.blueprints.impls.neo4j.Neo4jGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;
import com.tinkerpop.blueprints.util.io.graphml.GraphMLReader;

public class GraphMain {

	public static void main(String[] args) throws IOException {
		ExperimentRunner runner = new ExperimentRunner();
		runner.setSaveResult(false);
		Graph graphs[] = new Graph[] { new TinkerGraph(),
				new DexGraph("target/graph/dex/dex.db"),
			new OrientGraph("memory:demo"),
				new Neo4jGraph("target/graph/neo4j"),
				new BitsyGraph()
				};
		
		
		for (Graph graph : graphs) {
			InputStream resourceAsStream = GraphMain.class.getClassLoader().getResourceAsStream("graph-ml.xml");
			GraphMLReader.inputGraph(graph, resourceAsStream);
			runner.run(graph);
			graph.shutdown();
		}
		FileUtils.deleteDirectory(new File("target/graph"));
	}
}
