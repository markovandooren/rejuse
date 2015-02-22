//package be.kuleuven.cs.distrinet.rejuse.graph.adapter;
//
//import java.awt.geom.Point2D;
//
//import edu.uci.ics.jung.algorithms.layout.Layout;
//import be.kuleuven.cs.distrinet.rejuse.graph.Edge;
//import be.kuleuven.cs.distrinet.rejuse.graph.LayoutAlgorithm;
//
//public class JungLayoutAdapter<T> implements LayoutAlgorithm<T> {
//
//	public JungLayoutAdapter(Layout<T, Edge<T>> jungLayout) {
//		_jungLayout = jungLayout;
//	}
//
//	private Layout<T, Edge<T>> _jungLayout;
//	
//	@Override
//	public Point2D coordinate(T object) {
//		return _jungLayout.transform(object);
//	}
//	
//	
//	
//}
