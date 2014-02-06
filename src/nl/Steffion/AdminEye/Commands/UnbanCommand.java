package nl.Steffion.AdminEye.Commands;

import java.util.ArrayList;

import nl.Steffion.AdminEye.AdminEye;
import nl.Steffion.AdminEye.AdminEyeUtils;
import nl.Steffion.AdminEye.PlayerFile;
import nl.Steffion.AdminEye.StefsAPI;
import nl.Steffion.AdminEye.StefsAPI.ExecutedCommand;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class UnbanCommand extends ExecutedCommand {
	@Override
	public boolean execute(Player player, String playerName, Command cmd,
			String label, String[] args) {
		if (args.length <= 0) {
			StefsAPI.MessageHandler.buildMessage().addSender(playerName)
					.setMessage("error.notEnoughArguments", AdminEye.messages)
					.changeVariable("syntax", "/unban <player name>").build();
		} else {
			unbanPlayer(player, playerName, args[0]);
		}
		return true;
	}

	public static void unbanPlayer(Player player, String playerName,
			String unbanPlayerName) {
		ArrayList<OfflinePlayer> unbanPlayers = AdminEyeUtils
				.requestFilePlayers(unbanPlayerName);
		if (unbanPlayers == null && unbanPlayerName != null) {
			StefsAPI.MessageHandler.buildMessage().addSender(playerName)
					.setMessage("error.playerNotFound", AdminEye.messages)
					.changeVariable("playername", unbanPlayerName).build();
			return;
		}

		String unbannedPlayers = "";
		for (OfflinePlayer unbanPlayer : unbanPlayers) {
			unbannedPlayers += "%A" + unbanPlayer.getName() + "%N, ";
			PlayerFile playerFile = new PlayerFile(unbanPlayer.getName());
			playerFile.banBanned = false;
			playerFile.save();
		}

		unbannedPlayers = (unbanPlayerName.equals("*") ? unbannedPlayers = AdminEye.config
				.getFile().getString("chat.everyone") + "%N, "
				: unbannedPlayers);

		AdminEye.broadcastAdminEyeMessage(playerName, "unbanned", "unban",
				"playernames", unbannedPlayers);
	}
}
