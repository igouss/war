package soen6441.team13.wars.presentation;

import java.awt.Component;
import java.awt.Dimension;

public class LayoutUtils {
	public static void setSize(Component component, int x, int y) {
		Dimension d = new Dimension(x, y);
		component.setSize(d);
		component.setPreferredSize(d);
		component.setMaximumSize(d);
		component.setMinimumSize(d);
	}
}
