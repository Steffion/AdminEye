package nl.Steffion.AdminEye.Commands;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import nl.Steffion.AdminEye.AdminEye;
import nl.Steffion.AdminEye.AdminEyeUtils;
import nl.Steffion.AdminEye.StefsAPI;
import nl.Steffion.AdminEye.StefsAPI.ExecutedCommand;

public class SlayCommand extends ExecutedCommand {
	@Override
	public boolean execute(Player player, String playerName, Command cmd,
			String label, String[] args) {
		if (args.length <= 0) {
			StefsAPI.MessageHandler.buildMessage().addSender(playerName)
					.setMessage("error.notEnoughArguments", AdminEye.messages)
					.changeVariable("syntax", "/slay <player name>").build();
		} else {
			slayPlayer(player, playerName, args[0]);
		}
		return true;
	}

	@SuppressWarnings("deprecation")
	public static void slayPlayer(Player player, String playerName,
			String slayPlayerName) {
		ArrayList<Player> slayPlayers = AdminEyeUtils
				.requestPlayers(slayPlayerName);

		if (slayPlayers == null && slayPlayerName != null) {
			StefsAPI.MessageHandler.buildMessage().addSender(slayPlayerName)
					.setMessage("error.playerNotFound", AdminEye.messages)
					.changeVariable("playername", slayPlayerName).build();
			return;
		}

		String slayedPlayers = "";

		for (Player slayPlayer : slayPlayers) {
			slayPlayer.setHealth(0);
			slayedPlayers += "%A" + slayPlayer.getName() + "%N, ";
		}

		slayedPlayers = (slayPlayerName.equals("*") ? slayedPlayers = AdminEye.config
				.getFile().getString("chat.everyone") + "%N, "
				: slayedPlayers);

		AdminEye.broadcastAdminEyeMessage(playerName, "slayed", "slay",
				"playernames", slayedPlayers);
	}
}
