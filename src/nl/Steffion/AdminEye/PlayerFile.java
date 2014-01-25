package nl.Steffion.AdminEye;

import nl.Steffion.AdminEye.StefsAPI.Config;

public class PlayerFile {
	String playerName;
	Config playerFile;

	public PlayerFile (String playerName) {
		this.playerName = playerName;
		this.playerFile = StefsAPI.ConfigHandler.createConfig(playerName,
				"players");
		StefsAPI.ConfigHandler.displayNewFiles();
	}
}