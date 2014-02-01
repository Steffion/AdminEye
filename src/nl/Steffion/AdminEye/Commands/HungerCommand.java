package nl.Steffion.AdminEye.Commands;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import nl.Steffion.AdminEye.AdminEye;
import nl.Steffion.AdminEye.AdminEyeUtils;
import nl.Steffion.AdminEye.StefsAPI;
import nl.Steffion.AdminEye.StefsAPI.ExecutedCommand;

public class HungerCommand extends ExecutedCommand {
	@Override
	public boolean execute(Player player, String playerName, Command cmd,
			String label, String[] args) {
		if (args.length <= 0) {
			StefsAPI.MessageHandler.buildMessage().addSender(playerName)
					.setMessage("error.notEnoughArguments", AdminEye.messages)
					.changeVariable("syntax", "/hunger <player name> <amount>").build();
		} else if (args.length <= 1) {
			setHunger(player, playerName, args[0], "20");
		} else {
			setHunger(player, playerName, args[0], args[1]);
		}
		return true;
	}

	public static void setHunger(Player player, String playerName,
			String hungerPlayerName, String amount) {
		ArrayList<Player> hungerPlayers = AdminEyeUtils
				.requestPlayers(hungerPlayerName);

		if (hungerPlayers == null && hungerPlayerName != null) {
			StefsAPI.MessageHandler.buildMessage().addSender(playerName)
					.setMessage("error.playerNotFound", AdminEye.messages)
					.changeVariable("playername", hungerPlayerName).build();
			return;
		}

		if (!AdminEyeUtils.isNumber(amount)) {
			StefsAPI.MessageHandler.buildMessage().addSender(playerName)
					.setMessage("error.notANumber", AdminEye.messages)
					.changeVariable("number", amount).build();
			return;
		}

		int food = AdminEyeUtils.getNumber(amount);
		if(food > 20) {
			food = 20;
		}else if(food < 0) {
			food = 0;
		}
		
		String fedPlayers = "";

		for (Player hungerPlayer : hungerPlayers) {
			hungerPlayer.setFoodLevel(food);
			fedPlayers += "%A" + hungerPlayer.getName() + "%N, ";
		}

		fedPlayers = (hungerPlayerName.equals("*") ? fedPlayers = AdminEye.config
				.getFile().getString("chat.everyone") + "%N, " : fedPlayers);

		AdminEye.broadcastAdminEyeMessage(playerName, "fed", "feed",
				"playernames", fedPlayers, "amount", amount);
	}
}
