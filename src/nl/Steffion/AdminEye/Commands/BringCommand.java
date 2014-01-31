package nl.Steffion.AdminEye.Commands;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import nl.Steffion.AdminEye.AdminEye;
import nl.Steffion.AdminEye.AdminEyeUtils;
import nl.Steffion.AdminEye.StefsAPI;
import nl.Steffion.AdminEye.StefsAPI.ExecutedCommand;

public class BringCommand extends ExecutedCommand {
	@Override
	public boolean execute(Player player, String playerName, Command cmd,
			String label, String[] args) {
		if (args.length <= 0) {
			StefsAPI.MessageHandler.buildMessage().addSender(playerName)
					.setMessage("error.notEnoughArguments", AdminEye.messages)
					.changeVariable("syntax", "/bring <player name>").build();
		} else {
			bringPlayer(player, playerName, args[0]);
		}
		return true;
	}

	public static void bringPlayer(Player player, String playerName,
			String teleportPlayerName) {

		if (player == null) {
			StefsAPI.MessageHandler.buildMessage().addSender("$")
					.setMessage("error.onlyIngame", AdminEye.messages).build();
			return;
		}

		ArrayList<Player> teleportPlayers = AdminEyeUtils
				.requestPlayers(teleportPlayerName);

		if (teleportPlayers == null && teleportPlayerName != null) {
			StefsAPI.MessageHandler.buildMessage().addSender(playerName)
					.setMessage("error.playerNotFound", AdminEye.messages)
					.changeVariable("playername", teleportPlayerName).build();
			return;
		}

		String teleportedPlayers = "";

		for (Player teleportPlayer : teleportPlayers) {
			teleportPlayer.teleport(player.getLocation());
			teleportedPlayers += "%A" + teleportPlayer.getName() + "%N, ";
		}

		teleportedPlayers = (teleportPlayerName.equals("*") ? teleportedPlayers = AdminEye.config
				.getFile().getString("chat.everyone") + "%N, "
				: teleportedPlayers);

		AdminEye.broadcastAdminEyeMessage(playerName, "bring", "bring",
				"playernames", teleportedPlayers);
	}
}