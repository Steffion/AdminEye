package nl.Steffion.AdminEye.Commands;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import nl.Steffion.AdminEye.AdminEye;
import nl.Steffion.AdminEye.AdminEyeUtils;
import nl.Steffion.AdminEye.PlayerFile;
import nl.Steffion.AdminEye.StefsAPI;
import nl.Steffion.AdminEye.StefsAPI.ExecutedCommand;

public class FlyCommand extends ExecutedCommand {
	@Override
	public boolean execute(Player player, String playerName, Command cmd,
			String label, String[] args) {
		if (args.length <= 0) {
			StefsAPI.MessageHandler.buildMessage().addSender(playerName)
					.setMessage("error.notEnoughArguments", AdminEye.messages)
					.changeVariable("syntax", "/fly <player name>").build();
		} else {
			flyPlayer(player, playerName, args[0]);
		}
		return true;
	}

	public static void flyPlayer(Player player, String playerName,
			String flyPlayerName) {
		ArrayList<Player> flyPlayers = AdminEyeUtils
				.requestPlayers(flyPlayerName);

		if (flyPlayers == null && flyPlayerName != null) {
			StefsAPI.MessageHandler.buildMessage().addSender(playerName)
					.setMessage("error.playerNotFound", AdminEye.messages)
					.changeVariable("playername", flyPlayerName).build();
			return;
		}

		String flyingPlayers = "";

		for (Player flyPlayer : flyPlayers) {
			PlayerFile playerFile = new PlayerFile(flyPlayer.getName());
			playerFile.flyFlying = true;
			playerFile.save();

			flyPlayer.setAllowFlight(true);
			flyPlayer.setFlying(true);

			flyingPlayers += "%A" + flyPlayer.getName() + "%N, ";
		}

		flyingPlayers = (flyPlayerName.equals("*") ? flyingPlayers = AdminEye.config
				.getFile().getString("chat.everyone") + "%N, "
				: flyingPlayers);

		AdminEye.broadcastAdminEyeMessage(playerName, "fly", "fly",
				"playernames", flyingPlayers);
	}
}