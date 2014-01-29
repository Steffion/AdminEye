package nl.Steffion.AdminEye.Commands;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import nl.Steffion.AdminEye.AdminEye;
import nl.Steffion.AdminEye.AdminEyeUtils;
import nl.Steffion.AdminEye.StefsAPI;
import nl.Steffion.AdminEye.StefsAPI.ExecutedCommand;

public class DeopCommand extends ExecutedCommand {
	@Override
	public boolean execute(Player player, String playerName, Command cmd,
			String label, String[] args) {
		if (args.length <= 0) {
			StefsAPI.MessageHandler.buildMessage().addSender(playerName)
					.setMessage("error.notEnoughArguments", AdminEye.messages)
					.changeVariable("syntax", "/deop <player name>").build();
		} else {
			deopPlayer(player, playerName, args[0]);
		}
		return true;
	}

	public static void deopPlayer(Player player, String playerName,
			String deopPlayerName) {
		ArrayList<Player> deopPlayers = AdminEyeUtils
				.requestPlayers(deopPlayerName);

		if (deopPlayers == null && deopPlayerName != null) {
			StefsAPI.MessageHandler.buildMessage().addSender(playerName)
					.setMessage("error.playerNotFound", AdminEye.messages)
					.changeVariable("playername", deopPlayerName).build();
			return;
		}

		String deoppedPlayers = "";

		for (Player deopPlayer : deopPlayers) {
			deopPlayer.setOp(false);
			deoppedPlayers += "%A" + deopPlayer.getName() + "%N, ";
		}

		deoppedPlayers = (deopPlayerName.equals("*") ? deoppedPlayers = AdminEye.config
				.getFile().getString("chat.everyone") + "%N, "
				: deoppedPlayers);

		AdminEye.broadcastAdminEyeMessage(playerName, "deop", "deop",
				"playernames", deoppedPlayers);
	}
}
