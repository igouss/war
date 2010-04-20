package soen6441.team13.wars.presentation;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;

import soen6441.team13.wars.controller.EditorViewController;
import soen6441.team13.wars.controller.SimulationController;
import soen6441.team13.wars.domain.Continent;
import soen6441.team13.wars.domain.Player;
import soen6441.team13.wars.domain.State;
import soen6441.team13.wars.presentation.gameWorld.GameWorldView;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;

/**
 * This contains the main view frame for the game editor
 * 
 *
 */
public class GameEditorView implements EditorView {
	private static String customLookAndFeel = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";

	private JFrame jFrame = null;
	private JPanel jContentPane = null;
	private JPanel jPanel = null;
	private JPanel descPanel;

	private JLabel lblName = null;
	private JTextField edtName = null;
	private JLabel lblPlayer = null;

	// Combobox with player name
	private JComboBox cbPlayer = null;

	//Button to add new player
	private JButton btnAddNewPlayer = null;

	// Combobox with continents
	private JComboBox continents = null;

	private JLabel description;

	private JCheckBox chkIsCity = null;
	private JCheckBox chkHasIron = null;
	private JCheckBox chkHasBarracks = null;
	private JCheckBox chkHasStabels = null;
	private JCheckBox chkDoAttack = null;

	private JCheckBox chkHasArtilleryFactory = null;

	private JLabel lblInfantry = null;
	private JTextField edtInfantry = null;

	private JLabel lblCavalry = null;
	private JTextField edtCavalry = null;

	private JLabel lblArtillery = null;
	private JTextField edtArtillery = null;

	private JComboBox cbGraphMode = null;
	private final JComboBox cbAIStrategy = null;
	private JButton btnUpdateState;

	private JButton btnStartGame;
	private JButton btnStep;
	private JToggleButton btnPauseGame;

	private int gridBagConstraintsCount = 1;
	private EditorViewController worldEditorController;
	private SimulationController simulationController;

	private ActionLogView actionLogView = null;

	GameWorldView gameWorldView;

	/**
	 * @param args
	 */
	public GameEditorView() {
	}

	/**
	 * initialize and setup the game world editor UI
	 */
	@Override
	public void init() {

		try {
			UIManager.setLookAndFeel(customLookAndFeel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		gameWorldView = new GameWorldView();

		JPopupMenu.setDefaultLightWeightPopupEnabled(false);
		jFrame = getJFrame();
		cbGraphMode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox) e.getSource();
				Modes mode = (Modes) cb
						.getSelectedItem();
				worldEditorController.graphSelectionChanged(mode.getMode());
			}
		});

		btnUpdateState.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				worldEditorController.currentStateChanged();
			}
		});
		btnAddNewPlayer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				worldEditorController.createNewPlayer();
			}
		});

		btnStartGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				worldEditorController.getGameWorld().setDoBattle(chkDoAttack.isSelected());
				if (!simulationController.isStarted()) {
					simulationController.start(worldEditorController.getGameWorld());
				}
				simulationController.startSimulation();
			}
		});
		btnStep.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				worldEditorController.getGameWorld().setDoBattle(chkDoAttack.isSelected());
				if (!simulationController.isStarted()) {
					simulationController.start(worldEditorController.getGameWorld());
				}
				simulationController.nextTurn();
			}
		});

		btnPauseGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (btnPauseGame.isSelected()) {
					simulationController.pause();
				} else {
					simulationController.resume();
				}
			}
		});
		gameWorldView.setController(worldEditorController);
		gameWorldView.init();
		gameWorldView.reset();
	}

	/**
	 * show the game editor UI
	 */
	public void show() {
		jFrame.pack();
		jFrame.setVisible(true);
	}

	/**
	 * This method initialises jFrame
	 * 
	 * @return javax.swing.JFrame
	 */
	private JFrame getJFrame() {
		if (jFrame == null) {
			jFrame = new JFrame("Team 13. Decisive Battle.");
			jFrame.setSize(new Dimension(384, 240));
			jFrame.setContentPane(getJContentPane());
			jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			jFrame.setJMenuBar(getMenuBar());
			jFrame.setResizable(false);
		}
		return jFrame;
	}

	/**
	 * Initialize menu bar.
	 */
	private JMenuBar getMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);

		JMenuItem openFileMenuItem = new JMenuItem();
		openFileMenuItem.setText("Open...");
		fileMenu.add(openFileMenuItem);

		JMenuItem saveFileMenuItem = new JMenuItem();
		saveFileMenuItem.setText("Save as...");
		fileMenu.add(saveFileMenuItem);

		openFileMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				worldEditorController.loadGame();
			}
		});

		saveFileMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				worldEditorController.saveGame();
			}
		});

		return menuBar;
	}

	/**
	 * This method initialises jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 1;
			gbc.gridy = 0;
			gbc.anchor = GridBagConstraints.PAGE_START;
			jContentPane.add(getJPanel(), gbc);
			gbc.gridx = 0;
			gbc.gridy = 1;
			jContentPane.add(getActionPanel(), gbc);
			gbc.gridx = 1;
			gbc.gridy = 1;
			gbc.insets = new Insets(20, 0, 0, 0);
			gbc.fill = GridBagConstraints.BOTH;
			jContentPane.add(getDescriptionPanel(), gbc);

		}
		return jContentPane;
	}

	private ActionLogView getActionPanel() {
		if (null == actionLogView) {
			actionLogView = new ActionLogView();

		}
		return actionLogView;
	}

	/**
	 * This method initialises jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	GridBagConstraints getNextGridBagConstraints() {
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		// gridBagConstraints.fill = GridBagConstraints.EAST;
		gridBagConstraints.gridy = gridBagConstraintsCount += 2;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.gridx = 0;

		return gridBagConstraints;
	}

	/**
	 * Initialize and return panel.
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			{
				gbc.gridx = 0;
				gbc.gridy = 0;
				gbc.fill = GridBagConstraints.BOTH;
				jContentPane.add(gameWorldView.getPanel(), gbc);
			}

			gbc.fill = GridBagConstraints.HORIZONTAL;
			{
				gbc.gridx = 0;
				gbc.gridy = 0;
				gbc.gridwidth = 2;
				gbc.anchor = GridBagConstraints.WEST;

				jPanel.add(getlblContient(), gbc);

				gbc.gridx = 0;
				gbc.gridy = 1;
				gbc.gridwidth = 2;
				jPanel.add(getContinents(), gbc);
			}

			{
				gbc.gridx = 0;
				gbc.gridy = 3;
				gbc.gridwidth = 2;
				gbc.anchor = GridBagConstraints.WEST;
				jPanel.add(getlblPlayer(), gbc);

				gbc.gridx = 0;
				gbc.gridy = 4;
				jPanel.add(getcbPlayer(), gbc);

				gbc.gridx = 0;
				gbc.gridy = 5;
				gbc.insets = new Insets(0, 0, 20, 0);
				gbc.anchor = GridBagConstraints.NORTHWEST;
				jPanel.add(makeBtnAddNewPlayer(), gbc);
			}

			{
				gbc.gridx = 0;
				gbc.gridy = 6;
				gbc.insets = new Insets(0, 0, 0, 0);
				gbc.anchor = GridBagConstraints.WEST;
				jPanel.add(getlblName(), gbc);

				gbc.gridx = 0;
				gbc.gridy = 7;
				jPanel.add(getedtName(), gbc);
			}

			{
				gbc.gridx = 0;
				gbc.gridy = 8;
				jPanel.add(getchkIsCity(), gbc);

				gbc.gridx = 0;
				gbc.gridy = 10;
				jPanel.add(getchkHasIron(), gbc);

				gbc.gridx = 0;
				gbc.gridy = 11;
				jPanel.add(getchkHasBarraks(), gbc);

				gbc.gridx = 0;
				gbc.gridy = 12;
				jPanel.add(getchkHasStabels(), gbc);

				gbc.gridx = 0;
				gbc.gridy = 13;
				jPanel.add(getchkHasArtilleryFactory(), gbc);
			}

			{
				gbc.gridx = 0;
				gbc.gridy = 14;
				gbc.gridwidth = 1;
				gbc.anchor = GridBagConstraints.WEST;
				jPanel.add(getlblInfanty(), gbc);

				gbc.gridx = 1;
				gbc.gridy = 14;
				gbc.anchor = GridBagConstraints.EAST;
				jPanel.add(getedtInfanty(), gbc);
			}

			{
				gbc.gridx = 0;
				gbc.gridy = 15;
				gbc.anchor = GridBagConstraints.WEST;
				jPanel.add(getlblCavalry(), gbc);

				gbc.gridx = 1;
				gbc.gridy = 15;
				gbc.anchor = GridBagConstraints.EAST;
				jPanel.add(getedtCavalry(), gbc);

			}

			{
				gbc.gridx = 0;
				gbc.gridy = 16;
				gbc.anchor = GridBagConstraints.WEST;
				jPanel.add(getlblArtillery(), gbc);

				gbc.gridx = 1;
				gbc.gridy = 16;
				gbc.anchor = GridBagConstraints.EAST;
				jPanel.add(getedtArtillery(), gbc);

			}
			gbc.anchor = GridBagConstraints.WEST;
			gbc.gridx = 0;
			gbc.gridy = 17;
			gbc.gridwidth = 2;
			jPanel.add(makeCbGraphMode(), gbc);

			gbc.gridx = 0;
			gbc.gridy = 19;
			jPanel.add(makebtnUpdateState(), gbc);

			gbc.gridx = 0;
			gbc.gridy = 20;
			jPanel.add(makeBtnStartGame(), gbc);

			gbc.gridx = 0;
			gbc.gridy = 21;
			jPanel.add(makeBtnPauseGame(), gbc);

			gbc.gridx = 0;
			gbc.gridy = 22;
			jPanel.add(makeBtnStep(), gbc);

			gbc.gridx = 0;
			gbc.gridy = 23;
			jPanel.add(getchkDoAttack(), gbc);

		}
		return jPanel;
	}

	/**
	 * Initialize state description panel.
	 * @return
	 */
	private JScrollPane getDescriptionPanel() {
		descPanel = new JPanel();
		descPanel.setLayout(new BorderLayout());
		descPanel.setBorder(BorderFactory
				.createEtchedBorder(EtchedBorder.LOWERED));
		descPanel.setPreferredSize(new Dimension(150, 200));
		description = new JLabel();
		descPanel.add(description, BorderLayout.NORTH);
		return new JScrollPane(descPanel);
	}

	private JLabel getlblName() {
		if (lblName == null) {
			lblName = new JLabel();
			lblName.setText(" State Name");
		}
		return lblName;
	}

	private JTextField getedtName() {
		if (getEdtName() == null) {
			setEdtName(new JTextField(10));
			getEdtName().setText("");
		}
		return getEdtName();
	}

	private JLabel getlblPlayer() {
		if (lblPlayer == null) {
			lblPlayer = new JLabel();
			lblPlayer.setText(" Player");
		}
		return lblPlayer;
	}

	private JLabel getlblContient() {
		return new JLabel("Continent");
	}

	private JComboBox getcbPlayer() {
		if (getCbPlayer() == null) {
			CustomColorCellRenderer customRenderer = new CustomColorCellRenderer();
			JComboBox cb = new JComboBox();
			cb.setRenderer(customRenderer);
			setCbPlayer(cb);
			getCbPlayer().setPreferredSize(new Dimension(100, 20));
		}
		return getCbPlayer();
	}

	private Component getContinents() {
		continents = new JComboBox();
		continents.setPreferredSize(new Dimension(100, 20));
		return continents;
	}

	private JCheckBox getchkIsCity() {
		if (getChkIsCity() == null) {
			setChkIsCity(new JCheckBox());
			getChkIsCity().setText("Is city");
		}
		return getChkIsCity();
	}

	private JCheckBox getchkHasIron() {
		if (getChkHasIron() == null) {
			setChkHasIron(new JCheckBox());
			getChkHasIron().setText("Has Iron Mine");
		}
		return getChkHasIron();
	}

	private JCheckBox getchkHasBarraks() {
		if (getChkHasBarraks() == null) {
			setChkHasBarraks(new JCheckBox());
			getChkHasBarraks().setEnabled(false);
			getChkHasBarraks().setText("Has Barracks");
		}
		return getChkHasBarraks();
	}

	private JCheckBox getchkHasStabels() {
		if (getChkHasStabels() == null) {
			setChkHasStabels(new JCheckBox());
			getChkHasStabels().setText("Has Stabels");
		}
		return getChkHasStabels();
	}

	private JCheckBox getchkDoAttack() {
		if (getChkDoAttack() == null) {
			setChkDoAttack(new JCheckBox());
			getChkDoAttack().setText("Do attack");
		}
		return getChkDoAttack();
	}

	private JCheckBox getchkHasArtilleryFactory() {
		if (getChkHasArtilleryFactory() == null) {
			setChkHasArtilleryFactory(new JCheckBox());
			getChkHasArtilleryFactory().setText("Has Artillery Factory");
		}
		return getChkHasArtilleryFactory();
	}

	private JLabel getlblInfanty() {
		if (lblInfantry == null) {
			lblInfantry = new JLabel();
			lblInfantry.setText("Infantry");
		}
		return lblInfantry;
	}

	private JTextField getedtInfanty() {
		if (getEdtInfanty() == null) {
			setEdtInfanty(new JTextField(2));
			getEdtInfanty().setText("0");
		}
		return getEdtInfanty();
	}

	private JLabel getlblCavalry() {
		if (lblCavalry == null) {
			lblCavalry = new JLabel();
			lblCavalry.setText("Cavalries");
		}
		return lblCavalry;
	}

	private JTextField getedtCavalry() {
		if (getEdtCavalry() == null) {
			setEdtCavalry(new JTextField(2));
			getEdtCavalry().setText("0");
		}
		return getEdtCavalry();
	}

	private JLabel getlblArtillery() {
		if (lblArtillery == null) {
			lblArtillery = new JLabel();
			lblArtillery.setText("Artillery");
		}
		return lblArtillery;
	}

	private JTextField getedtArtillery() {
		if (getEdtArtillery() == null) {
			setEdtArtillery(new JTextField(2));
			getEdtArtillery().setText("0");
		}
		return getEdtArtillery();
	}

	private JButton makeBtnAddNewPlayer() {
		if (btnAddNewPlayer == null) {
			btnAddNewPlayer = new JButton();
			btnAddNewPlayer.setText("Add New Player");
		}
		return btnAddNewPlayer;
	}

	private JButton makebtnUpdateState() {
		if (btnUpdateState == null) {
			btnUpdateState = new JButton();
			btnUpdateState.setText("Update State");
		}
		return btnUpdateState;
	}

	private JButton makeBtnStartGame() {
		if (btnStartGame == null) {
			btnStartGame = new JButton();
			btnStartGame.setText("Start simulation");
		}
		return btnStartGame;
	}

	private JButton makeBtnStep() {
		if (btnStep == null) {
			btnStep = new JButton();
			btnStep.setText("Step");
		}
		return btnStep;
	}

	private JToggleButton makeBtnPauseGame() {
		if (btnPauseGame == null) {
			btnPauseGame = new JToggleButton("Pause");
		}
		return btnPauseGame;
	}

	/**
	 * The class for mapping ModalGraphMouse Modes into the game slang
	 * @author Pavel
	 *
	 */
	class Modes {
		String name;
		ModalGraphMouse.Mode mode;

		public Modes(String name, ModalGraphMouse.Mode mode) {
			this.mode = mode;
			this.name = name;
		}

		public ModalGraphMouse.Mode getMode() {
			return mode;
		}

		@Override
		public String toString() {
			return name;
		}
	}

	/**
	 * This method initialises jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox makeCbGraphMode() {
		if (cbGraphMode == null) {
			cbGraphMode = new JComboBox();
			cbGraphMode.addItem(new Modes("Create States", Mode.EDITING));
			cbGraphMode.addItem(new Modes("Select States", Mode.PICKING));
			cbGraphMode.addItem(new Modes("Map transforming", Mode.TRANSFORMING));
			cbGraphMode.addItem(new Modes("Set continent", Mode.ANNOTATING));
		}
		return cbGraphMode;
	}

	private void setEdtName(JTextField edtName) {
		this.edtName = edtName;
	}

	private JTextField getEdtName() {
		return edtName;
	}

	private void setEdtInfanty(JTextField edtInfanty) {
		this.edtInfantry = edtInfanty;
	}

	private JTextField getEdtInfanty() {
		return edtInfantry;
	}

	private void setEdtCavalry(JTextField edtCavalry) {
		this.edtCavalry = edtCavalry;
	}

	private JTextField getEdtCavalry() {
		return edtCavalry;
	}

	private void setEdtArtillery(JTextField edtArtillery) {
		this.edtArtillery = edtArtillery;
	}

	private JTextField getEdtArtillery() {
		return edtArtillery;
	}

	private void setChkIsCity(JCheckBox chkIsCity) {
		this.chkIsCity = chkIsCity;
	}

	private JCheckBox getChkIsCity() {
		return chkIsCity;
	}

	private void setChkHasBarraks(JCheckBox chkHasBarraks) {
		this.chkHasBarracks = chkHasBarraks;
	}

	private JCheckBox getChkHasBarraks() {
		return chkHasBarracks;
	}

	private void setChkHasIron(JCheckBox chkHasIron) {
		this.chkHasIron = chkHasIron;
	}

	private JCheckBox getChkHasIron() {
		return chkHasIron;
	}

	private void setChkHasStabels(JCheckBox chkHasStabels) {
		this.chkHasStabels = chkHasStabels;
	}

	private JCheckBox getChkHasStabels() {
		return chkHasStabels;
	}

	private void setChkHasArtilleryFactory(JCheckBox chkHasArtilleryFactory) {
		this.chkHasArtilleryFactory = chkHasArtilleryFactory;
	}

	private JCheckBox getChkHasArtilleryFactory() {
		return chkHasArtilleryFactory;
	}

	private void setCbPlayer(JComboBox cbPlayer) {
		this.cbPlayer = cbPlayer;
	}

	private JComboBox getCbPlayer() {
		return cbPlayer;
	}

	/**
	 * Build description of selected state.
	 * @param state
	 */
	public void setDescription(State state) {
		String props = "";
		if (state.isCity()) {
			props += "<li>Is a city</li>";
		}
		if (state.hasBarracks()) {
			props += "<li>Has barracks</li>";
		}
		if (state.hasFoundry()) {
			props += "<li>Has artillery factory</li>";
		}
		if (state.hasStables()) {
			props += "<li>Has stables</li>";
		}
		if (state.hasIronMine()) {
			props += "<li>Has iron mine</li>";
		}
		if (state.getGarrison().getInfantry() > 0) {
			props += "<li>" + state.getGarrison().getInfantry()
					+ " infantries</li>";
		}
		if (state.getGarrison().getCavalry() > 0) {
			props += "<li>" + state.getGarrison().getCavalry()
					+ " cavalries</li>";
		}
		if (state.getGarrison().getArtillery() > 0) {
			props += "<li>" + state.getGarrison().getArtillery() + " artillery</li>";
		}

		if (null != state.getContinent()) {
			props += "<li> Is part of continent: " + state.getContinent() + " </li>";
		}

		String desc = "<html><b>&nbsp;" + state.getName()
				+ "</b><br/><ul><li>Owned by " + state.getPlayer().getName()
				+ "</li>" + props + "</ul></html>";

		this.description.setText(desc);
	}

	@Override
	public void addPlayer(Player newPlayer) {
		getCbPlayer().addItem(newPlayer);
		getCbPlayer().setSelectedItem(newPlayer);
	}

	@Override
	public void addContinent(Continent continent) {
		continents.addItem(continent);
	}

	@Override
	public boolean isCitySelected() {
		return getChkIsCity().isSelected();
	}

	@Override
	public boolean hasBracksSelected() {
		return getChkHasBarraks().isSelected();
	}

	@Override
	public boolean hasIronSelected() {
		return getChkHasIron().isSelected();
	}

	@Override
	public boolean hasStablesSelected() {
		return getChkHasStabels().isSelected();
	}

	@Override
	public boolean hasArtilleryFactorySelected() {
		return getChkHasArtilleryFactory().isSelected();
	}

	@Override
	public Player getSelectedPlayer() {
		return (Player) getCbPlayer().getSelectedItem();
	}

	@Override
	public String getSelectedStateName() {
		return getEdtName().getText();
	}

	@Override
	public int getNumberOfCavalry() {
		return Integer.valueOf(getEdtCavalry().getText());
	}

	@Override
	public int getNumberOfArtillery() {
		return Integer.valueOf(getEdtArtillery().getText());
	}

	@Override
	public int getNumberOfInfantry() {
		return Integer.valueOf(getEdtInfanty().getText());
	}

	@Override
	public void hasBarracks(boolean hasBarracks) {
		getChkHasBarraks().setSelected(hasBarracks);
	}

	@Override
	public void hasIron(boolean hasIronMine) {
		getChkHasIron().setSelected(hasIronMine);
	}

	@Override
	public void hasStables(boolean hasStables) {
		getChkHasStabels().setSelected(hasStables);
	}

	@Override
	public void hasArtilleryFactory(boolean hasArtilleryFactory) {
		getChkHasArtilleryFactory().setSelected(hasArtilleryFactory);
	}

	@Override
	public void isCity(boolean isCity) {
		getChkIsCity().setSelected(isCity);
	}

	@Override
	public void selectPlayer(Player player) {
		getCbPlayer().setSelectedItem(player);
	}

	@Override
	public void setNumberOfCavalry(int cavalry) {
		getEdtCavalry().setText(String.valueOf(cavalry));
	}

	@Override
	public void setNumberOfInfantry(int infantry) {
		getEdtInfanty().setText(String.valueOf(infantry));
	}

	@Override
	public void setNumberOfArtillery(int artillery) {
		getEdtArtillery().setText(String.valueOf(artillery));
	}

	@Override
	public void setStateName(String name) {
		getEdtName().setText(name);
	}

	/**
	 * Open dialog for a saved game.
	 */
	@Override
	public File openFile() {
		JFrame view = getJFrame();
		JFileChooser fc = new JFileChooser();

		int returnVal = fc.showOpenDialog(view);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			return fc.getSelectedFile();

		} else {
			return null;
		}
	}

	/**
	 * Save dialog to persist a game.
	 */
	@Override
	public File getSaveFile() {
		JFrame view = getJFrame();
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showSaveDialog(view);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			return fc.getSelectedFile();
		} else {
			return null;
		}
	}

	/**
	 * Remove all players
	 */
	@Override
	public void clearPlayerList() {
		((DefaultComboBoxModel) cbPlayer.getModel()).removeAllElements();
	}

	/**
	 * slect the continent in the GUI
	 */
	@Override
	public void selectContinent(Continent continent) {
		continents.setSelectedItem(continent);
	}

	/**
	 * get the continent which is currently selected 
	 * in the GUI
	 */
	@Override
	public Continent getSelectedContinent() {
		return (Continent) continents.getSelectedItem();
	}

	/**
	 * clear all continents from the the GUI
	 */
	@Override
	public void clearContinents() {
		((DefaultComboBoxModel) continents.getModel()).removeAllElements();

	}

	/**
	 * ask for a new users name in a dialog box popup
	 */
	@Override
	public String getNewPlayerName() {
		return JOptionPane.showInputDialog(getJFrame(),
				"Please specify the name for this player", "Enter New Player",
				JOptionPane.QUESTION_MESSAGE);
	}

	/**
	 * ask for a new continent name in a popup dialog
	 * box
	 */
	@Override
	public String getContinentName() {
		return JOptionPane.showInputDialog(getJFrame(),
				"Please specify the name for this continent", "Enter new continent",
				JOptionPane.QUESTION_MESSAGE);
	}

	/**
	 * set the editor controller to link the editor GUI
	 * to the domain model
	 */
	@Override
	public void setEditorController(EditorViewController editorViewController) {
		this.worldEditorController = editorViewController;
	}

	/**
	 * set simulation controller to link the simulation engine
	 * to the GUI
	 */
	@Override
	public void setSimulationController(SimulationController simulationController) {
		this.simulationController = simulationController;
	}

	/**
	 * @param chkDoAttack the chkDoAttack to set
	 */
	public void setChkDoAttack(JCheckBox chkDoAttack) {
		this.chkDoAttack = chkDoAttack;
	}

	/**
	 * @return the chkDoAttack
	 */
	public JCheckBox getChkDoAttack() {
		return chkDoAttack;
	}

	@Override
	public GameWorldView getGameWorldView() {
		return gameWorldView;
	}
}
