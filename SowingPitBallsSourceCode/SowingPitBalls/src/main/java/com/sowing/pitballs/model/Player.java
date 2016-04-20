package com.sowing.pitballs.model;

import java.io.Serializable;

public class Player implements Serializable{
	private static final long serialVersionUID = 7541146022890950168L;
	private int playerNo;
	private String name;
	
	public Player(int playerNo, String name) {
		super();
		this.playerNo = playerNo;
		this.name = name;
	}
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (!(obj instanceof Player))
			return false;

		Player other = (Player) obj;
		return this.playerNo == other.playerNo;
	}

	@Override
	public int hashCode() {

		return playerNo;
	}

	@Override
	public String toString() {

		return "Player Number" + " : " + getName() + " :Stones : " + getName();
	}
	
	public int getPlayerNo() {
		return playerNo;
	}

	public void setPlayerNo(int playerNo) {
		this.playerNo = playerNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

}
