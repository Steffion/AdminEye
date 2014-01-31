package nl.Steffion.AdminEye.Commands;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import nl.Steffion.AdminEye.AdminEye;
import nl.Steffion.AdminEye.AdminEyeUtils;
import nl.Steffion.AdminEye.StefsAPI;
import nl.Steffion.AdminEye.StefsAPI.ExecutedCommand;

public class GotoCommand extends ExecutedCommand {
	@Override
	public boolean execute(Player player, String playerName, Command cmd,
			String label, String[] args) {
		if (args.length <= 0) {
			StefsAPI.MessageHandler.buildMessage().addSender(playerName)
					.setMessage("error.notEnoughArguments", AdminEye.messages)
					.changeVariable("syntax", "/goto <player name>").build();
		} else {
			gotoPlayer(player, playerName, args[0]);
		}
		return true;
	}

	public static void gotoPlayer(Player player, String playerName,
			String gotoPlayerName) {

		if (player == null) {
			StefsAPI.MessageHandler.buildMessage().addSender("$")
					.setMessage("error.onlyIngame", AdminEye.messages).build();
			return;
		}

		Player gotoPlayer = AdminEyeUtils.requestPlayer(gotoPlayerName);

		if (gotoPlayer == null && gotoPlayerName != null) {
			StefsAPI.MessageHandler.buildMessage().addSender(playerName)
					.setMessage("error.playerNotFound", AdminEye.messages)
					.changeVariable("playername", gotoPlayerName).build();
			return;
		}

		player.teleport(gotoPlayer);

		String desPlayers = "";

		desPlayers += "%A" + gotoPlayer.getName();

		AdminEye.broadcastAdminEyeMessage(playerName, "went", "goto",
				"playernames", desPlayers);
	}
}