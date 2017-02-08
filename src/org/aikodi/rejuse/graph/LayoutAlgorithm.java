package org.aikodi.rejuse.graph;

import java.awt.geom.Point2D;

public interface LayoutAlgorithm<T> {

	Point2D coordinate(T object);
	
}
