package pl.edu.agh.kis.ztb.graph;

import java.io.IOException;
import java.io.OutputStream;

import com.tinkerpop.blueprints.Graph;

public interface GraphTransformer {
	void setGraph(Graph graph);

	void vertexLabelClear();

	void vertexLabelEdit(String vertexType, int vertexIndex, String oldLabel, String newLabel);

	void vertexLabelAdd(String vertexType, int vertexNubmer, String label);

	void vertexLabelAdd(String vertexType, String label);

	void show(OutputStream os) throws IOException;
	
}
