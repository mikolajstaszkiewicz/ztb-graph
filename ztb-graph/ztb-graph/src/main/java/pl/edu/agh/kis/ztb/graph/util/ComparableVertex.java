package pl.edu.agh.kis.ztb.graph.util;

public class ComparableVertex implements Comparable<ComparableVertex> {
	private String aVertexType;
	private int aVertexIndex;
	private String edge;
	private String bVertexType;
	private int bVertexIndex;
	private String label;

	public ComparableVertex(String aVertexType, int aVertexIndex, String edge, String bVertexType, int bVertexIndex,
			String label) {
		super();
		this.aVertexType = aVertexType;
		this.aVertexIndex = aVertexIndex;
		this.edge = edge;
		this.bVertexType = bVertexType;
		this.bVertexIndex = bVertexIndex;
		this.label = label;
	}

	@Override
	public int compareTo(ComparableVertex to) {
		int result = aVertexType.compareTo(to.aVertexType);
		if (result != 0) {
			return result;
		}
		result = aVertexIndex - to.aVertexIndex;
		if (result != 0) {
			return result;
		}
		result = edge.compareTo(to.edge);
		if (result != 0) {
			return result;
		}
		result = bVertexType.compareTo(to.bVertexType);
		if (result != 0) {
			return result;
		}
		return bVertexIndex - to.bVertexIndex;
	}

	public String getRow() {
		return aVertexType + aVertexIndex + "-" + edge + "-" + bVertexType + bVertexIndex + " " + label;
	}

}