package nl.Steffion.AdminEye;

import nl.Steffion.AdminEye.StefsAPI.Config;

public class PlayerFile {
	public String playerName;
	public Config playerFile;
	public Boolean banBanned;
	public String banReason;
	public Integer banLength;
	public Boolean flyFlying;
	public Boolean frozenFroze;
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
		StefsAPI.ConfigHandler.addDefault(playerFile, "fly.flying", false);
		this.flyFlying = this.playerFile.getFile().getBoolean("fly.flying");
		StefsAPI.ConfigHandler.addDefault(playerFile, "frozen.froze", false);
		this.frozenFroze = this.playerFile.getFile().getBoolean("frozen.froze");
		StefsAPI.ConfigHandler.addDefault(playerFile, "mute.length", 0);
		this.muteLength = this.playerFile.getFile().getInt("mute.length");
		StefsAPI.ConfigHandler.addDefault(playerFile, "mute.muted", false);
		this.muteMuted = this.playerFile.getFile().getBoolean("mute.muted");
	}

	public void save() {
		playerFile.getFile().set("playerName", playerName);
		playerFile.getFile().set("ban.banned", banBanned);
		playerFile.getFile().set("ban.reason", banReason);
		playerFile.getFile().set("ban.length", banLength);
		playerFile.getFile().set("fly.flying", flyFlying);
		playerFile.getFile().set("frozen.froze", frozenFroze);
		playerFile.getFile().set("mute.length", muteLength);
		playerFile.getFile().set("mute.muted", muteMuted);
		this.playerFile.save();
	}
}