package nl.Steffion.AdminEye.Commands;

import java.util.ArrayList;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import nl.Steffion.AdminEye.AdminEye;
import nl.Steffion.AdminEye.AdminEyeUtils;
import nl.Steffion.AdminEye.PlayerFile;
import nl.Steffion.AdminEye.StefsAPI;
import nl.Steffion.AdminEye.StefsAPI.ExecutedCommand;

public class UnmuteCommand extends ExecutedCommand {
	@Override
	public boolean execute(Player player, String playerName, Command cmd,
			String label, String[] args) {
		if (args.length <= 0) {
			StefsAPI.MessageHandler.buildMessage().addSender(playerName)
					.setMessage("error.notEnoughArguments", AdminEye.messages)
					.changeVariable("syntax", "/unmute <player name>").build();
		} else {
			unmutePlayer(player, playerName, args[0]);
		}
		return true;
	}

	public static void unmutePlayer(Player player, String playerName,
			String unmutePlayerName) {
		ArrayList<OfflinePlayer> unmutePlayers = AdminEyeUtils
				.requestFilePlayers(unmutePlayerName);

		if (unmutePlayers == null && unmutePlayerName != null) {
			StefsAPI.MessageHandler.buildMessage().addSender(playerName)
					.setMessage("error.playerNotFound", AdminEye.messages)
					.changeVariable("playername", unmutePlayerName).build();
			return;
		}

		String unmutedPlayers = "";
		for (OfflinePlayer unmutePlayer : unmutePlayers) {
			unmutedPlayers += "%A" + unmutePlayer.getName() + "%N, ";
			PlayerFile playerFile = new PlayerFile(unmutePlayer.getName());
			playerFile.muteMuted = false;
			playerFile.save();
		}

		unmutedPlayers = (unmutePlayerName.equals("*") ? unmutedPlayers = AdminEye.config
				.getFile().getString("chat.everyone") + "%N, "
				: unmutedPlayers);

		AdminEye.broadcastAdminEyeMessage(playerName, "unmuted", "unmuted",
				"playernames", unmutedPlayers);
	}
}
