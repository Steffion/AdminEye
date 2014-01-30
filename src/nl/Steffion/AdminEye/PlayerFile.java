package nl.Steffion.AdminEye;

import nl.Steffion.AdminEye.StefsAPI.Config;

public class PlayerFile {
	public String playerName;
	public Config playerFile;
	public Boolean banBanned;
	public String banReason;
	public Integer banLenght;

	public PlayerFile (String playerName) {
		this.playerName = playerName;
		this.playerFile = StefsAPI.ConfigHandler.createConfig(playerName,
				"players");
		StefsAPI.ConfigHandler.displayNewFiles();
		StefsAPI.ConfigHandler.addDefault(playerFile, "playerName", playerName);
		StefsAPI.ConfigHandler.addDefault(playerFile, "ban.banned", false);
		this.banBanned = this.playerFile.getFile().getBoolean("ban.banned");
		StefsAPI.ConfigHandler.addDefault(playerFile, "ban.reason", "");
		this.banReason = this.playerFile.getFile().getString("ban.reason");
		StefsAPI.ConfigHandler.addDefault(playerFile, "ban.lenght", 0);
		this.banLenght = this.playerFile.getFile().getInt("ban.lenght");
	}

	public void save() {
		playerFile.getFile().set("playerName", playerName);
		playerFile.getFile().set("ban.banned", banBanned);
		playerFile.getFile().set("ban.reason", banReason);
		playerFile.getFile().set("ban.lenght", banLenght);
		this.playerFile.save();
	}
}