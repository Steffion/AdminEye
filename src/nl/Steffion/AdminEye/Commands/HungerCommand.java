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
			feedPlayer(player, playerName, args[0], "20");
		} else {
			feedPlayer(player, playerName, args[0], args[1]);
		}
		return true;
	}

	public static void feedPlayer(Player player, String playerName,
			String feedPlayerName, String amount) {
		ArrayList<Player> feedPlayers = AdminEyeUtils
				.requestPlayers(feedPlayerName);

		if (feedPlayers == null && feedPlayerName != null) {
			StefsAPI.MessageHandler.buildMessage().addSender(playerName)
					.setMessage("error.playerNotFound", AdminEye.messages)
					.changeVariable("playername", feedPlayerName).build();
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

		for (Player feedPlayer : feedPlayers) {
			feedPlayer.setFoodLevel(food);
			fedPlayers += "%A" + feedPlayer.getName() + "%N, ";
		}

		fedPlayers = (feedPlayerName.equals("*") ? fedPlayers = AdminEye.config
				.getFile().getString("chat.everyone") + "%N, " : fedPlayers);

		AdminEye.broadcastAdminEyeMessage(playerName, "fed", "feed",
				"playernames", fedPlayers, "amount", amount);
	}
}
