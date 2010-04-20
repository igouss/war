package soen6441.team13.wars.presentation.gameWorld;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.ImageObserver;

import javax.swing.Icon;

import org.apache.commons.collections15.Transformer;

import soen6441.team13.wars.domain.Edge;
import soen6441.team13.wars.domain.State;
import edu.uci.ics.jung.visualization.VisualizationViewer;

/**
 * Provides visual representation of state vertex.
 */
class VertexIconTransformer implements Transformer<State, Icon> {

	private final VisualizationViewer<State, Edge> vv;

	public VertexIconTransformer(VisualizationViewer<State, Edge> vv) {
		this.vv = vv;

	}

	/*
	 * Implements the Icon interface to draw an Icon with background colour
	 */
	public Icon transform(final State v) {
		return new Icon() {

			private Image cityImage = Toolkit.getDefaultToolkit().getImage(
					getClass().getResource("/city-icon.png"));
			/*
			 * private Image oilImage = Toolkit.getDefaultToolkit().getImage(
			 * getClass().getResource("/oil.jpg"));
			 */
			private Image ironImage = Toolkit.getDefaultToolkit().getImage(
					getClass().getResource("/iron.jpg"));

			private DummyImageObserver observer = new DummyImageObserver();
			private State state = v;

			public int getIconHeight() {
				return 20;
			}

			public int getIconWidth() {
				return 20;
			}

			public void paintIcon(Component c, java.awt.Graphics g, int x, int y) {
				g.setColor(Color.BLACK);

				if (state.getPlayer() != null) {
					Color colo = state.getPlayer().getColor();
					g.setColor(colo);
				}
				if (state.hasIronMine()) {
					g.drawImage(ironImage, x - 30, y, observer);
				}

				if (state.isCity()) {
					g.drawImage(cityImage, x - 10, y - 40, observer);
					g.fillOval(x, y, 20, 20);
				} else {
					g.fillOval(x, y, 20, 20);
				}
				if (vv.getPickedVertexState().isPicked(state)) {
					g.setColor(Color.black);
				} else {
					g.setColor(Color.black);
				}
				// put state Strength into circle
				g.drawString("" + v.getStrength(), x + 1, y + 15);
			}
		};
	}

	/**
	 * Implementation to notify of image update.
	 */
	private final class DummyImageObserver implements ImageObserver {
		@Override
		public boolean imageUpdate(Image arg0, int arg1, int arg2, int arg3,
				int arg4, int arg5) {
			// false to indicate that the image is completely loaded;
			// true otherwise.
			return true;
		}
	}
}
