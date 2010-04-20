package soen6441.team13.wars.presentation;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import soen6441.team13.wars.domain.Action;
import soen6441.team13.wars.logger.ActionLogger;
import soen6441.team13.wars.logger.ActionLoggerFactory;
import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.SortedList;
import ca.odell.glazedlists.gui.TableFormat;
import ca.odell.glazedlists.swing.EventTableModel;
import ca.odell.glazedlists.swing.TableComparatorChooser;

/**
 * Display game action log.
 */
public class ActionLogView extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTable table;
	private final boolean[] editable = { false, false, false, false };
	private final String[] propertyNames = new String[] { "order", "player", "action", "result" };

	private SortedList<Action> eventList;
	
	

	public ActionLogView() {
		add(getActionTable(), BorderLayout.CENTER);
	}

	private void setActionLog(ActionLogger actionLogger) {
		eventList = (SortedList<Action>) actionLogger.getEventList();
			
			

		final TableFormat<Action> tableFormat = GlazedLists.tableFormat(Action.class, propertyNames, propertyNames,
				editable);
		final EventTableModel<Action> model = new EventTableModel<Action>(eventList, tableFormat);

		table = new JTable();
		// uncomment for JDK 6 JTable row sorting
		// table.setAutoCreateRowSorter(true);
		table.setModel(model);
		TableComparatorChooser.install(table, eventList, TableComparatorChooser.SINGLE_COLUMN);
		
	}

	private JScrollPane getActionTable() {
		setActionLog(ActionLoggerFactory.getInstance());
		JScrollPane scrollPane = new JScrollPane(table);
		LayoutUtils.setSize(scrollPane, 600, 150);
		return scrollPane;
	}
}
