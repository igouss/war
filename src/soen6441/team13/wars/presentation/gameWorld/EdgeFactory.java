package soen6441.team13.wars.presentation.gameWorld;

import org.apache.commons.collections15.Factory;

import soen6441.team13.wars.domain.Edge;

/**
 * Create new numberedEdged. New edges are numbered from 0. Used by JUNG.
 */
class EdgeFactory implements Factory<Edge> {
	/**
	 * Create a new edge to be added to the graph
	 */
	@Override
	public Edge create() {
		return new Edge();
	}
}