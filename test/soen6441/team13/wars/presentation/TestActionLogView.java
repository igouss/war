package soen6441.team13.wars.presentation;

import static soen6441.team13.wars.domain.ActionType.ATTACK;
import static soen6441.team13.wars.domain.ActionType.MOVE;
import static soen6441.team13.wars.domain.ActionType.PRODUCE;
import static soen6441.team13.wars.domain.ActionType.SCOUT;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Random;

import javax.swing.JFrame;

import soen6441.team13.wars.domain.ActionType;
import soen6441.team13.wars.logger.ActionLogger;
import soen6441.team13.wars.logger.ActionLoggerFactory;

public class TestActionLogView {

	static ActionLogger logger = ActionLoggerFactory.getInstance();

	private static void populate() {
		String players[] = { "Troy", "Iouri", "Justin", "Pavel" };
		ActionType types[] = { ATTACK, MOVE, PRODUCE, SCOUT };
		Random ran = new Random();

		int iterations = 20;
		for (int i = 0; i < iterations; i++) {
			log(players[ran.nextInt(4)], types[ran.nextInt(4)]);
		}

	}

	private static void log(String playerName, ActionType actionType) {
		String result = "";
		switch (actionType) {
		case ATTACK:
			logger.logBattle(playerName, result);
			break;
		case MOVE:
			logger.logMove(playerName, result);
			break;
		case PRODUCE:
			logger.logProduction(playerName, result);
			break;
		case SCOUT:
			logger.logRecon(playerName, result);
			break;

		}

	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Test action log view");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		LogPanel actionLogView = new LogPanel();
		actionLogView.setActionLog(logger);
		populate();
		GridBagConstraints gbc = new GridBagConstraints();
		Container c = frame.getContentPane();
		c.setLayout(new GridBagLayout());
		c.add(actionLogView, gbc);
		frame.pack();
		frame.setVisible(true);
	}
}
