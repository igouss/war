package soen6441.team13.wars.domain;

import java.io.Serializable;


public class Action implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer order;
	private String player;
	private ActionType action;
	private String result;

	public Action(int order, ActionType actionType, String playerTurn, String result) {
		super();
		this.order = order;
		this.action = actionType;
		this.player = playerTurn;
		this.result = result;
	}

	public String getPlayer() {
		return player;
	}

	public void setPlayer(String player) {
		this.player = player;
	}

	public ActionType getAction() {
		return action;
	}

	public void setAction(ActionType action) {
		this.action = action;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getResult() {
		return result;
	}
	
	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + order;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Action other = (Action) obj;
		if (order != other.order)
			return false;
		return true;
	}
}
