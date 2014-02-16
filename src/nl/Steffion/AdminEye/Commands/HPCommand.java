package nl.Steffion.AdminEye.Commands;

import java.util.ArrayList;

import nl.Steffion.AdminEye.AdminEye;
import nl.Steffion.AdminEye.AdminEyeUtils;
import nl.Steffion.AdminEye.StefsAPI;
import nl.Steffion.AdminEye.StefsAPI.ExecutedCommand;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class HPCommand extends ExecutedCommand {
	@Override
	public boolean execute(Player player, String playerName, Command cmd,
			String label, String[] args) {
		if (args.length <= 0) {
			StefsAPI.MessageHandler
					.buildMessage()
					.addSender(playerName)
					.setMessage("error.notEnoughArguments", AdminEye.messages)
					.changeVariable("syntax",
							"/hp <player name> [amount of hp]").build();
		} else if (args.length <= 1) {
			setHealth(player, playerName, args[0], "20");
		} else {
			setHealth(player, playerName, args[0], args[1]);
		}
		return true;
	}

	@SuppressWarnings("deprecation")
	public static void setHealth(Player player, String playerName,
			String healPlayerName, String amount) {
		ArrayList<Player> healPlayers = AdminEyeUtils
				.requestPlayers(healPlayerName);

		if (healPlayers == null && healPlayerName != null) {
			StefsAPI.MessageHandler.buildMessage().addSender(playerName)
					.setMessage("error.playerNotFound", AdminEye.messages)
					.changeVariable("playername", healPlayerName).build();
			return;
		}

		if (!AdminEyeUtils.isNumber(amount)) {
			StefsAPI.MessageHandler.buildMessage().addSender(playerName)
					.setMessage("error.notANumber", AdminEye.messages)
					.changeVariable("number", amount).build();
			return;
		}

		int health = AdminEyeUtils.getNumber(amount);
		if (health > 20) {
			health = 20;
		} else if (health < 0) {
			health = 0;
		}

		String healedPlayers = "";

		for (Player healPlayer : healPlayers) {
			healPlayer.setHealth(health);
			healedPlayers += "%A" + healPlayer.getName() + "%N, ";
		}

		healedPlayers = (healPlayerName.equals("*") ? healedPlayers = AdminEye.config
				.getFile().getString("chat.everyone") + "%N, "
				: healedPlayers);

		AdminEye.broadcastAdminEyeMessage(playerName, "sethealth", "hp",
				"playernames", healedPlayers, "amount", health + "");
	}

}
