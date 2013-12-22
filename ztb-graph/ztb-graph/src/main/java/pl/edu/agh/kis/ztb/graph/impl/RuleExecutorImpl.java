package pl.edu.agh.kis.ztb.graph.impl;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.java.GremlinPipeline;

import pl.edu.agh.kis.ztb.graph.GraphTransformer;
import pl.edu.agh.kis.ztb.graph.RuleExecutor;
import pl.edu.agh.kis.ztb.graph.function.VertexLabelRemove;
import pl.edu.agh.kis.ztb.graph.function.VertexLabelSet;
import pl.edu.agh.kis.ztb.graph.util.GraphConst;

public class RuleExecutorImpl implements RuleExecutor {

	private Graph graph;
	private GraphTransformer transformer;

	public RuleExecutorImpl() {

	}

	public RuleExecutorImpl(Graph graph, GraphTransformer transformer) {
		super();
		this.graph = graph;
		this.transformer = transformer;
	}

	/*
	 * vlc, vla(k,1,false), vla(h,1,day), forall(v(c,J),vla(c,J,off)),
	 * forall(v(p,K),vla(p,K,false)), forall(v(d,L),vla(d,L,false)),!.
	 */
	@Override
	public void init() {
		transformer.vertexLabelClear();
		transformer.vertexLabelAdd("k", 1, "false");
		transformer.vertexLabelAdd("h", 1, "day");
		transformer.vertexLabelAdd("c", "off");
		transformer.vertexLabelAdd("p", "false");
		transformer.vertexLabelAdd("d", "false");
	}

	@Override
	public void runAll() {
		rule1a1b();
		rule2a2b();
		rule3a3b();
		rule4a();
		rule4b();
		rule5a();
		rule5b();
		rule6();
		rule7();
		profileHigh();
		profileLow();
		profileOff();
	}

	@Override
	public void setGraph(Graph graph) {
		this.graph = graph;
	}

	@Override
	public void setTransformer(GraphTransformer transformer) {
		this.transformer = transformer;
	}

	/*
	 * r('1a1b') :- v(k,L,false), e(k,L,s,J), \+ v(s,J,off), e(s,J,c,_,_), then,
	 * vla(s,J,off).
	 */
	private void rule1a1b() {
		new GremlinPipeline<Vertex, Vertex>(graph).V(GraphConst.VERTEX_TYPE, "k").has(GraphConst.VERTEX_LABEL, "false")
				.both().as("s").has(GraphConst.VERTEX_TYPE, "s").hasNot(GraphConst.VERTEX_LABEL, "off").both()
				.has(GraphConst.VERTEX_TYPE, "c")
				.back("s").cast(Vertex.class).transform(new VertexLabelSet("off")).iterate();
	}

	/*
	 * r('2a2b') :- v(k,L,true), v(p,M,false), e(p,M,s,J,in), \+ v(s,J,low),
	 * e(k,L,s,J), e(s,J,c,_,low), then, vla(s,J,low).
	 */
	private void rule2a2b() {
		new GremlinPipeline<>(graph).V(GraphConst.VERTEX_TYPE, "s").as("s").hasNot(GraphConst.VERTEX_LABEL, "low")
				.both("low").has(GraphConst.VERTEX_TYPE, "c")
				.back("s").both("in").has(GraphConst.VERTEX_TYPE, "p").has(GraphConst.VERTEX_LABEL, "false")
				.back("s").both().has(GraphConst.VERTEX_TYPE, "k").has(GraphConst.VERTEX_LABEL,"true")
				.back("s").cast(Vertex.class).transform(new VertexLabelSet("low")).iterate();
	}

	/*
	 * 
	 * r('3a3b') :- v(k,L,true), v(p,M,true), e(p,M,s,J,in), \+ v(s,J,high),
	 * e(k,L,s,J), e(s,J,c,_,high), then, vla(s,J,high).
	 */
	private void rule3a3b() {
		new GremlinPipeline<>(graph).V(GraphConst.VERTEX_TYPE, "s").as("s").hasNot(GraphConst.VERTEX_LABEL, "high")
				.both("high").has(GraphConst.VERTEX_TYPE, "c")
				.back("s").both("in").has(GraphConst.VERTEX_TYPE, "p").has(GraphConst.VERTEX_LABEL, "true")
				.back("s").both().has(GraphConst.VERTEX_TYPE, "k").has(GraphConst.VERTEX_LABEL, "true")
				.back("s").cast(Vertex.class).transform(new VertexLabelSet("high")).iterate();
	}

	/*
	 * r('4a'):- v(k,L,true), v(h,1,day), v(d,I,true), e(d,I,s,J,dir_day), \+
	 * v(s,J,high), e(k,L,s,J), e(h,1,s,J), e(s,J,c,_,high), then,
	 * vla(s,J,high).
	 */
	private void rule4a() {
		new GremlinPipeline<>(graph).V(GraphConst.VERTEX_TYPE, "s").as("s").hasNot(GraphConst.VERTEX_LABEL, "high")
				.both("high").has(GraphConst.VERTEX_TYPE, "c")
				.back("s").both("dir_day").has(GraphConst.VERTEX_TYPE, "d").has(GraphConst.VERTEX_LABEL, "true")
				.back("s").both().has(GraphConst.VERTEX_TYPE, "k").has(GraphConst.VERTEX_LABEL, "true")
				.back("s").both().has(GraphConst.VERTEX_TYPE, "h").has(GraphConst.VERTEX_INDEX, 1).has(
						GraphConst.VERTEX_LABEL, "day")
				.back("s").cast(Vertex.class).transform(new VertexLabelSet("high")).iterate();
	}

	/*
	 * 
	 * r('4b'):- v(h,1,night), v(k,L,true), v(d,I,true), e(d,I,s,J,dir_night),
	 * \+ v(s,J,high), e(k,L,s,J), e(h,1,s,J), e(s,J,c,_,high), then,
	 * vla(s,J,high).
	 */
	private void rule4b() {
		new GremlinPipeline<>(graph).V(GraphConst.VERTEX_TYPE, "s").as("s").hasNot(GraphConst.VERTEX_LABEL, "high")
				.both("high").has(GraphConst.VERTEX_TYPE, "c")
				.back("s").both("dir_night").has(GraphConst.VERTEX_TYPE, "d").has(GraphConst.VERTEX_LABEL, "true")
				.back("s").both().has(GraphConst.VERTEX_TYPE, "k").has(GraphConst.VERTEX_LABEL, "true")
				.back("s").both().has(GraphConst.VERTEX_TYPE, "h").has(GraphConst.VERTEX_INDEX, 1).has(
						GraphConst.VERTEX_LABEL, "night")
				.back("s").cast(Vertex.class).transform(new VertexLabelSet("high")).iterate();
	}

	/*
	 * r('5a'):- v(k,L,true), v(h,1,day), e(d,I,s,J,dir_day), \+ v(s,J,off),
	 * v(d,I,false), e(k,L,s,J), e(h,1,s,J), e(s,J,c,_,high), then,
	 * vla(s,J,off).
	 */
	private void rule5a() {
		new GremlinPipeline<>(graph).V(GraphConst.VERTEX_TYPE, "s").as("s").hasNot(GraphConst.VERTEX_LABEL, "off")
				.both("high").has(GraphConst.VERTEX_TYPE, "c")
				.back("s").both("dir_day").has(GraphConst.VERTEX_TYPE, "d").has(GraphConst.VERTEX_LABEL, "false")
				.back("s").both().has(GraphConst.VERTEX_TYPE, "k").has(GraphConst.VERTEX_LABEL, "true")
				.back("s").both().has(GraphConst.VERTEX_TYPE, "h").has(GraphConst.VERTEX_INDEX, 1).has(
						GraphConst.VERTEX_LABEL, "day")
				.back("s").cast(Vertex.class).transform(new VertexLabelSet("off")).iterate();
	}

	/*
	 * 
	 * r('5b'):- v(k,L,true), v(h,1,night), e(d,I,s,J,dir_night), \+ v(s,J,off),
	 * v(d,I,false), e(k,L,s,J), e(h,1,s,J), e(s,J,c,_,high), then,
	 * vla(s,J,off).
	 */
	private void rule5b() {
		new GremlinPipeline<>(graph).V(GraphConst.VERTEX_TYPE, "s").as("s").hasNot(GraphConst.VERTEX_LABEL, "off")
				.both("high").has(GraphConst.VERTEX_TYPE, "c")
				.back("s").both("dir_night").has(GraphConst.VERTEX_TYPE, "d").has(GraphConst.VERTEX_LABEL, "false")
				.back("s").both().has(GraphConst.VERTEX_TYPE, "k").has(GraphConst.VERTEX_LABEL, "true")
				.back("s").both().has(GraphConst.VERTEX_TYPE, "h").has(GraphConst.VERTEX_INDEX, 1).has(
						GraphConst.VERTEX_LABEL, "night")
				.back("s").cast(Vertex.class).transform(new VertexLabelSet("off")).iterate();

	}

	/*
	 * r(6) :- v(h,1,day), v(d,I,true), e(d,I,s,J,interior), e(h,1,s,J), \+
	 * v(s,J,high), e(s,J,c,_,high), then, vla(s,J,high).
	 */
	private void rule6() {
		new GremlinPipeline<>(graph).V(GraphConst.VERTEX_TYPE, "s").as("s").hasNot(GraphConst.VERTEX_LABEL, "high")
				.both("high").has(GraphConst.VERTEX_TYPE, "c")
				.back("s").both("interior").has(GraphConst.VERTEX_TYPE, "d").has(GraphConst.VERTEX_LABEL, "true")
				.back("s").both().has(GraphConst.VERTEX_TYPE, "h").has(GraphConst.VERTEX_INDEX, 1).has(
						GraphConst.VERTEX_LABEL, "day")
				.back("s").cast(Vertex.class).transform(new VertexLabelSet("high")).iterate();

	}

	/*
	 * 
	 * r(7) :- v(h,1,day), e(d,I,s,J,interior), \+ v(s,J,off), v(d,I,false),
	 * e(s,J,c,_,high), e(h,1,s,J), then, vla(s,J,off).
	 */
	private void rule7() {
		new GremlinPipeline<>(graph).V(GraphConst.VERTEX_TYPE, "s").as("s").hasNot(GraphConst.VERTEX_LABEL, "off")
				.both("high").has(GraphConst.VERTEX_TYPE, "c")
				.back("s").both("interior").has(GraphConst.VERTEX_TYPE, "d").has(GraphConst.VERTEX_LABEL, "false")
				.back("s").both().has(GraphConst.VERTEX_TYPE, "h").has(GraphConst.VERTEX_INDEX, 1).has(
						GraphConst.VERTEX_LABEL, "day")
				.back("s").cast(Vertex.class).transform(new VertexLabelSet("off")).iterate();
	}

	/*
	 * p(high) :- v(s,I,high), e(s,I,c,K,high), then, vlc(s,I,_),
	 * forall(e(s,I,c,J,_),(vlr(c,J,_),vla(c,J,off))), vlr(c,K,_), vla(c,K,on).
	 */
	private void profileHigh() {
		new GremlinPipeline<>(graph).V(GraphConst.VERTEX_TYPE, "s").as("s").has(GraphConst.VERTEX_LABEL, "high")
				.both("high").as("cHigh").has(GraphConst.VERTEX_TYPE, "c")
				.back("cHigh").cast(Vertex.class).transform(new VertexLabelSet("on"))
				.back("s").bothE().hasNot("label", "high").bothV().as("cNotHigh").has(GraphConst.VERTEX_TYPE, "c")
				.back("cNotHigh").cast(Vertex.class).transform(new VertexLabelSet("off"))
				.back("s").cast(Vertex.class).transform(new VertexLabelRemove()).iterate();

	}

	/*
	 * p(low) :- v(s,I,low), \+ v(s,I,high), e(s,I,c,K,low), then, vlc(s,I,_),
	 * forall(e(s,I,c,J,_),(vlr(c,J,_),vla(c,J,off))), vlr(c,K,_), vla(c,K,on).
	 */
	private void profileLow() {
		new GremlinPipeline<>(graph).V(GraphConst.VERTEX_TYPE, "s").as("s").has(GraphConst.VERTEX_LABEL, "low")
				.hasNot(GraphConst.VERTEX_LABEL, "high").both("low").as("cLow").has(GraphConst.VERTEX_TYPE, "c")
				.back("cLow").cast(Vertex.class).transform(new VertexLabelSet("on"))
				.back("s").bothE().hasNot("label", "low").bothV().as("cNotLow").has(GraphConst.VERTEX_TYPE, "c")
				.back("cNotLow").cast(Vertex.class).transform(new VertexLabelSet("off"))
				.back("s").cast(Vertex.class).transform(new VertexLabelRemove()).iterate();

	}

	/*
	 * p(off) :- v(s,I,off), \+ v(s,I,high), \+ v(s,I,low), then, vlc(s,I,_),
	 * forall(e(s,I,c,J,_),(vlr(c,J,_),vla(c,J,off))).
	 */
	private void profileOff() {
		new GremlinPipeline<>(graph).V(GraphConst.VERTEX_TYPE, "s").as("s").has(GraphConst.VERTEX_LABEL, "off")
				.hasNot(GraphConst.VERTEX_LABEL, "high").hasNot(GraphConst.VERTEX_LABEL, "low")
				.both().as("c").has(GraphConst.VERTEX_TYPE, "c")
				.back("c").cast(Vertex.class).transform(new VertexLabelSet("off"))
				.back("s").cast(Vertex.class).transform(new VertexLabelRemove()).iterate();

	}
}
