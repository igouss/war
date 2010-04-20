package soen6441.team13.wars.presentation.gameWorld;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections15.Factory;
import org.apache.commons.collections15.Transformer;
import org.apache.commons.collections15.functors.MapTransformer;
import org.apache.commons.collections15.map.LazyMap;

import soen6441.team13.wars.controller.EditorViewController;
import soen6441.team13.wars.domain.Continent;
import soen6441.team13.wars.domain.Edge;
import soen6441.team13.wars.domain.State;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.Vertex;

/**
 * 
 * This class builds the UI components related to visualizing and editing the
 * the world.
 * 
 */
public class GameWorldView {
	private final VisualizationViewer<State, Edge> vv;
	private GraphZoomScrollPane panel = null;
	private EditingModalGraphMouse<State, Edge> graphMouse;
	private final StaticLayout<State, Edge> layout;
	private EditorViewController controller;

	public GameWorldView() {
		Graph<State, Edge> graph = new UndirectedSparseGraph<State, Edge>();
		// graphical layout for drawing
		layout = new StaticLayout<State, Edge>(graph, new Dimension(600, 600));

		// the graph canvas
		vv = new VisualizationViewer<State, Edge>(layout);
		vv.setBackground(Color.white);
		panel = new GraphZoomScrollPane(vv);
	}

	public void setController(EditorViewController controller) {
		this.controller = controller;
	}

	public void init() {

		// Label transformers are needed to represent State and Edges as strings
		Transformer<State, String> vertexLabelTransformer = MapTransformer
				.getInstance(LazyMap.decorate(new HashMap<State, String>(),
				new MyToStringLabeller<State>()));

		Transformer<Edge, String> edgeLabelTransformer = MapTransformer
				.getInstance(LazyMap.decorate(new HashMap<Edge, String>(),
				new MyToStringLabeller<Edge>()));

		RenderContext<State, Edge> renderContext = vv.getRenderContext();
		renderContext.setVertexLabelTransformer(vertexLabelTransformer);
		renderContext.setEdgeLabelTransformer(edgeLabelTransformer);

		// Icon transformers are needed to show graph nodes as icons
		VertexIconTransformer vertexIconTransformer = new VertexIconTransformer(
				vv);
		renderContext.setVertexIconTransformer(vertexIconTransformer);
		vv.setVertexToolTipTransformer(vertexLabelTransformer);

		// graph requires Factory implementation for Vertexes and Edges
		Factory<State> vertexFactory = new VertexFactory(controller);
		Factory<Edge> edgeFactory = new EdgeFactory();

		// predefined mouse class. Used for mouse handling.
		graphMouse = new EditingModalGraphMouse<State, Edge>(renderContext,
				vertexFactory, edgeFactory);

		// there are four modes - set Mode.EDITING as default
		graphMouse.setMode(ModalGraphMouse.Mode.EDITING);

		vv.addGraphMouseListener(new UpdateViewMouseListener(controller));
		vv.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				// start new continent creation, if the current mode is
				// Mode.ANNOTATING
				if ((ModalGraphMouse.Mode) getGraphMouse().getModeComboBox()
						.getSelectedItem() == ModalGraphMouse.Mode.ANNOTATING) {
					if (down != null) {
						// TODO create new continent
						controller.createNewContinent(new Point(down),
								new Dimension(e.getX() - down.x, e.getY()
								- down.y));
					}
					down = null;
				}
			}

			// to store the top-left position for new continent
			Point down;

			@Override
			public void mousePressed(MouseEvent e) {
				if ((ModalGraphMouse.Mode) getGraphMouse().getModeComboBox()
						.getSelectedItem() == ModalGraphMouse.Mode.ANNOTATING) {
					// store the top-left position for new continent
					down = e.getPoint();
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});

		vv.setGraphMouse(graphMouse);
		vv.addKeyListener(graphMouse.getModeKeyListener());
	}

	/**
	 * Retrieve the currently picked State objects.
	 * 
	 * @return
	 */
	public State[] getPickedStates() {
		Set<State> pickedStates = vv.getPickedVertexState().getPicked();
		return pickedStates.toArray(new State[pickedStates.size()]);
	}

	/**
	 * get the layout manager algorithm for the graph
	 * representing this game world view
	 * @return
	 */
	public AbstractLayout<State, Edge> getLayout() {
		return layout;
	}

	/**
	 * Return graph panel.
	 */
	public BasicVisualizationServer<State, Edge> getPanel() {
		return vv;
	}

	/**
	 * Retrieve the graph mouse.
	 */
	public EditingModalGraphMouse<State, Edge> getGraphMouse() {
		return graphMouse;
	}

	/**
	 * Repaint the graph.
	 */
	public void repaint() {
		layout.reset();
		vv.repaint();
	}

	/**
	 * reset the graph
	 */
	public void reset() {
		Graph<State, Edge> graph = controller.getGameWorld().getGraph();
		layout.setGraph(graph);
	}

	/**
	 * 
	 * a custom string labeler
	 * 
	 * @param <V>
	 */
	class MyToStringLabeller<V> extends ToStringLabeller<V> {

		@Override
		public String transform(V v) {
			return super.transform(v);
		}
	}

	/**
	 * Update the State lable after the State object names has been changed
	 * 
	 * @param vertex
	 * @param newLabel
	 */
	public void updateLabel(State vertex, String newLabel) {
		RenderContext<State, Edge> renderContext = vv.getRenderContext();
		Transformer<State, String> vs = renderContext
				.getVertexLabelTransformer();
		Map<State, String> map = ((MapTransformer<State, String>) vs).getMap();
		if (vertex != null && newLabel != null) {
			map.put(vertex, newLabel);
			vv.repaint();
		}
	}

	/**
	 * update the vertex
	 * 
	 * @param vertex
	 */
	public void updateVertex(State vertex) {
		Vertex<State, Edge> vertexRenderer = vv.getRenderer()
				.getVertexRenderer();
		vertexRenderer.paintVertex(vv.getRenderContext(), layout, vertex);
		vv.requestFocusInWindow();
		this.panel.invalidate();
		panel.repaint();
	}

	/**
	 * set the state location
	 * 
	 * @param s
	 * @param location
	 */
	public void setStateLocation(State s, Point2D location) {
		getLayout().setLocation(s, s.getLocation());
		updateVertex(s);
	}

	/**
	 * updates all state locations (before serialization and continent creation)
	 */
	public void updateStateLocations() {
		Collection<State> states = controller.getGameWorld().getGraph()
				.getVertices();
		for (State state : states) {
			state.setLocation(getLayout().transform(state));
			getLayout().setLocation(state, state.getLocation());
		}
	}

	/**
	 * draw this continet on the GUI map
	 * @param continent
	 */
	public void DrawContinents(Continent continent) {
		// drar the continent on the canvas
		vv.addPreRenderPaintable(continent);
	}

}
