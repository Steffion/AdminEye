package nl.Steffion.AdminEye.Commands;

import java.util.ArrayList;
import java.util.Random;

import nl.Steffion.AdminEye.AdminEye;
import nl.Steffion.AdminEye.AdminEyeUtils;
import nl.Steffion.AdminEye.StefsAPI;
import nl.Steffion.AdminEye.StefsAPI.ExecutedCommand;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class SlapCommand extends ExecutedCommand {
	@Override
	public boolean execute(Player player, String playerName, Command cmd,
			String label, String[] args) {
		if (args.length <= 0) {
			StefsAPI.MessageHandler.buildMessage().addSender(playerName)
					.setMessage("error.notEnoughArguments", AdminEye.messages)
					.changeVariable("syntax", "/slap <player name>").build();
		} else {
			slapPlayer(player, playerName, args[0]);
		}
		return true;
	}

	public static void slapPlayer(Player player, String playerName,
			String slapPlayerName) {
		ArrayList<Player> slapPlayers = AdminEyeUtils
				.requestPlayers(slapPlayerName);

		if (slapPlayers == null && slapPlayerName != null) {
			StefsAPI.MessageHandler.buildMessage().addSender(playerName)
					.setMessage("error.playerNotFound", AdminEye.messages)
					.changeVariable("playername", slapPlayerName).build();
			return;
		}

		String slappedPlayers = "";

		for (Player slapPlayer : slapPlayers) {
			Random random = new Random();
			slapPlayer.setVelocity(new Vector((random.nextInt(4) + 1
					- random.nextInt(9) + 1), (random.nextInt(2) + 1), (random
					.nextInt(4) + 1 - random.nextInt(9) + 1)).multiply(0.5));
			slapPlayer.playSound(slapPlayer.getLocation(), Sound.HURT_FLESH, 1,
					1);
			slappedPlayers += "%A" + slapPlayer.getName() + "%N, ";
		}

		slappedPlayers = (slapPlayerName.equals("*") ? slappedPlayers = AdminEye.config
				.getFile().getString("chat.everyone") + "%N, "
				: slappedPlayers);

		AdminEye.broadcastAdminEyeMessage(playerName, "slapped", "slap",
				"playernames", slappedPlayers);
	}

}
