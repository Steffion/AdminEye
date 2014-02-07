package nl.Steffion.AdminEye;

import nl.Steffion.AdminEye.StefsAPI.Config;

public class PlayerFile {
	public String playerName;
	public Config playerFile;
	public Boolean banBanned;
	public String banReason;
	public Integer banLength;
	public Boolean muteMuted;
	public Integer muteLength;

	public PlayerFile (String playerName) {
		this.playerName = playerName;
		this.playerFile = StefsAPI.ConfigHandler.createConfig(playerName,
				"players");
		StefsAPI.ConfigHandler.displayNewFiles();
		StefsAPI.ConfigHandler.addDefault(playerFile, "playerName", playerName);
		StefsAPI.ConfigHandler.addDefault(playerFile, "ban.banned", false);
		this.banBanned = this.playerFile.getFile().getBoolean("ban.banned");
		StefsAPI.ConfigHandler.addDefault(playerFile, "ban.length", 0);
		this.banLength = this.playerFile.getFile().getInt("ban.length");
		StefsAPI.ConfigHandler.addDefault(playerFile, "ban.reason", "");
		this.banReason = this.playerFile.getFile().getString("ban.reason");
		StefsAPI.ConfigHandler.addDefault(playerFile, "mute.length", 0);
		this.muteLength = this.playerFile.getFile().getInt("mute.length");
		StefsAPI.ConfigHandler.addDefault(playerFile, "mute.muted", true);
		this.muteMuted = this.playerFile.getFile().getBoolean("mute.muted");
	}

	public void save() {
		playerFile.getFile().set("playerName", playerName);
		playerFile.getFile().set("mute.muted", muteMuted);
		playerFile.getFile().set("ban.banned", banBanned);
		playerFile.getFile().set("ban.reason", banReason);
		playerFile.getFile().set("ban.length", banLength);
		playerFile.getFile().set("mute.length", muteLength);
		this.playerFile.save();
	}
}