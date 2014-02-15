package org.agecraft.core;

import java.util.HashMap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PlayerTradeManager {

	public static class PlayerTrade {
		
		public String player1;
		public String player2;
		public byte currentPlayer;
		
		public int dimensionID;
		
		public boolean accepted1 = false;
		public boolean accepted2 = false;
		
		public PlayerTrade(String player1, String player2, byte currentPlayer, int dimensionID) {
			this.player1 = player1;
			this.player2 = player2;
			this.currentPlayer = currentPlayer;
			
			this.dimensionID = dimensionID;
		}
	}
	
	@SideOnly(Side.CLIENT)
	public static HashMap<String, PlayerTrade> tradesClient = new HashMap<String, PlayerTrade>();
	public static HashMap<String, PlayerTrade> trades = new HashMap<String, PlayerTrade>();
}
