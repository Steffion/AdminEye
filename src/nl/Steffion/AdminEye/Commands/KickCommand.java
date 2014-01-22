package nl.Steffion.AdminEye.Commands;

import java.util.ArrayList;

import nl.Steffion.AdminEye.AdminEye;
import nl.Steffion.AdminEye.AdminEyeUtils;
import nl.Steffion.AdminEye.StefsAPI;
import nl.Steffion.AdminEye.StefsAPI.ExecutedCommand;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class KickCommand extends ExecutedCommand {
	@Override
	public boolean execute(Player player, String playerName, Command cmd,
			String label, String[] args) {
		if (args.length <= 0) {
			StefsAPI.MessageHandler.buildMessage().addSender(playerName)
					.setMessage("error.notEnoughArguments", AdminEye.messages)
					.changeVariable("syntax", "/kick <player name> [reason]")
					.build();
		} else {
			kickPlayer(player, playerName, args[0],
					AdminEyeUtils.stringBuilder(args, 1));

		}
		return true;
	}

	public static void kickPlayer(Player player, String playerName,
			String kickPlayerName, String reason2) {
		ArrayList<Player> kickPlayers = AdminEyeUtils
				.requestPlayers(kickPlayerName);

		if (kickPlayers == null && kickPlayerName != null) {
			StefsAPI.MessageHandler.buildMessage().addSender(playerName)
					.setMessage("error.playerNotFound", AdminEye.messages)
					.changeVariable("playername", kickPlayerName).build();
			return;
		}

		String reason = "%TAG\n%NYou've been kicked! Reason: \n%A";
		String kickedPlayers = "";

		reason = reason
				+ (reason2 == null ? "No reason given%N." : reason2 + "%N.");

		for (Player kickPlayer : kickPlayers) {
			kickPlayer.kickPlayer(StefsAPI.MessageHandler
					.replaceColours(StefsAPI.MessageHandler
							.replacePrefixes(reason)));
			kickedPlayers += "%A" + kickPlayer.getName() + "%N, ";
		}

		kickedPlayers = (kickPlayerName.equals("*") ? kickedPlayers = AdminEye.config
				.getFile().getString("chat.everyone") + "%N, "
				: kickedPlayers);

		AdminEye.broadcastAdminEyeMessage(
				playerName,
				"kicked",
				"kick",
				"playernames",
				kickedPlayers,
				"reason",
				(reason2 == null ? "No reason given" : reason2.replaceAll("&u",
						" ")));
	}
}
