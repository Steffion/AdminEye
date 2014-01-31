package nl.Steffion.AdminEye.Commands;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import nl.Steffion.AdminEye.AdminEye;
import nl.Steffion.AdminEye.AdminEyeUtils;
import nl.Steffion.AdminEye.StefsAPI;
import nl.Steffion.AdminEye.StefsAPI.ExecutedCommand;

public class OpCommand extends ExecutedCommand {
	@Override
	public boolean execute(Player player, String playerName, Command cmd,
			String label, String[] args) {
		if (args.length <= 0) {
			StefsAPI.MessageHandler.buildMessage().addSender(playerName)
					.setMessage("error.notEnoughArguments", AdminEye.messages)
					.changeVariable("syntax", "/op <player name>").build();
		} else {
			opPlayer(player, playerName, args[0]);
		}
		return true;
	}

	public static void opPlayer(Player player, String playerName,
			String opPlayerName) {
		ArrayList<Player> opPlayers = AdminEyeUtils
				.requestPlayers(opPlayerName);

		if (opPlayers == null && opPlayerName != null) {
			StefsAPI.MessageHandler.buildMessage().addSender(playerName)
					.setMessage("error.playerNotFound", AdminEye.messages)
					.changeVariable("playername", opPlayerName).build();
			return;
		}

		String oppedPlayers = "";

		for (Player opPlayer : opPlayers) {
			opPlayer.setOp(true);
			oppedPlayers += "%A" + opPlayer.getName() + "%N, ";
		}

		oppedPlayers = (opPlayerName.equals("*") ? oppedPlayers = AdminEye.config
				.getFile().getString("chat.everyone") + "%N, "
				: oppedPlayers);

		AdminEye.broadcastAdminEyeMessage(playerName, "op", "op",
				"playernames", oppedPlayers);
	}

}
