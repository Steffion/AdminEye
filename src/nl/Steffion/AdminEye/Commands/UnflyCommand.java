package nl.Steffion.AdminEye.Commands;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import nl.Steffion.AdminEye.AdminEye;
import nl.Steffion.AdminEye.AdminEyeUtils;
import nl.Steffion.AdminEye.PlayerFile;
import nl.Steffion.AdminEye.StefsAPI;
import nl.Steffion.AdminEye.StefsAPI.ExecutedCommand;

public class UnflyCommand extends ExecutedCommand {
	@Override
	public boolean execute(Player player, String playerName, Command cmd,
			String label, String[] args) {
		if (args.length <= 0) {
			StefsAPI.MessageHandler.buildMessage().addSender(playerName)
					.setMessage("error.notEnoughArguments", AdminEye.messages)
					.changeVariable("syntax", "/unfly <player name>").build();
		} else {
			unflyPlayer(player, playerName, args[0]);
		}
		return true;
	}

	public static void unflyPlayer(Player player, String playerName,
			String unflyPlayerName) {
		ArrayList<Player> unflyPlayers = AdminEyeUtils
				.requestPlayers(unflyPlayerName);

		if (unflyPlayers == null && unflyPlayerName != null) {
			StefsAPI.MessageHandler.buildMessage().addSender(playerName)
					.setMessage("error.playerNotFound", AdminEye.messages)
					.changeVariable("playername", unflyPlayerName).build();
			return;
		}

		String unflyingPlayers = "";

		for (Player unflyPlayer : unflyPlayers) {
			PlayerFile playerFile = new PlayerFile(unflyPlayer.getName());
			playerFile.flyFlying = true;
			playerFile.save();

			unflyPlayer.setFlying(false);
			unflyPlayer.setAllowFlight(false);

			unflyingPlayers += "%A" + unflyPlayer.getName() + "%N, ";
		}

		unflyingPlayers = (unflyPlayerName.equals("*") ? unflyingPlayers = AdminEye.config
				.getFile().getString("chat.everyone") + "%N, "
				: unflyingPlayers);

		AdminEye.broadcastAdminEyeMessage(playerName, "unfly", "unfly",
				"playernames", unflyingPlayers);
	}
}
