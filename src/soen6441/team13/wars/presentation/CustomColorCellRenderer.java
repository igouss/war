package soen6441.team13.wars.presentation;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import soen6441.team13.wars.domain.Player;

/**
 * Custom JComboBox cell renderer to set the background color of each cell in the
 * list to match the unique color of the player.
 */
class CustomColorCellRenderer implements ListCellRenderer {

	private final DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

	/**
	 * Single method to implement customized rendering. The color property is
	 * retrieved from the player and set as the background for the cell.
	 */
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {

		JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected,
				cellHasFocus);
		if (value instanceof Player) {
			Player player = (Player) value;
			renderer.setBackground(player.getColor());
		}

		return renderer;

	}
}
