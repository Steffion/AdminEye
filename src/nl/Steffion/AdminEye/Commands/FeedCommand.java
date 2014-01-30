package nl.Steffion.AdminEye.Commands;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import nl.Steffion.AdminEye.AdminEye;
import nl.Steffion.AdminEye.AdminEyeUtils;
import nl.Steffion.AdminEye.StefsAPI;
import nl.Steffion.AdminEye.StefsAPI.ExecutedCommand;

public class FeedCommand extends ExecutedCommand {
	@Override
	public boolean execute(Player player, String playerName, Command cmd,
			String label, String[] args) {
		if (args.length <= 0) {
			StefsAPI.MessageHandler.buildMessage().addSender(playerName)
					.setMessage("error.notEnoughArguments", AdminEye.messages)
					.changeVariable("syntax", "/feed <player name>").build();
		} else {
			feedPlayer(player, playerName, args[0]);
		}
		return true;
	}

	public static void feedPlayer(Player player, String playerName,
			String feedPlayerName) {
		ArrayList<Player> feedPlayers = AdminEyeUtils
				.requestPlayers(feedPlayerName);

		if (feedPlayers == null && feedPlayerName != null) {
			StefsAPI.MessageHandler.buildMessage().addSender(playerName)
					.setMessage("error.playerNotFound", AdminEye.messages)
					.changeVariable("playername", feedPlayerName).build();
			return;
		}

		String fedPlayers = "";

		for (Player feedPlayer : feedPlayers) {
			feedPlayer.setFoodLevel(20);
			fedPlayers += "%A" + feedPlayer.getName() + "%N, ";
		}

		fedPlayers = (feedPlayerName.equals("*") ? fedPlayers = AdminEye.config
				.getFile().getString("chat.everyone") + "%N, " : fedPlayers);

		AdminEye.broadcastAdminEyeMessage(playerName, "fed", "feed",
				"playernames", fedPlayers);
	}
}
