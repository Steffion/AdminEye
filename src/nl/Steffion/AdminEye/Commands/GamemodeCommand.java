package nl.Steffion.AdminEye.Commands;

import java.util.ArrayList;

import nl.Steffion.AdminEye.AdminEye;
import nl.Steffion.AdminEye.AdminEyeUtils;
import nl.Steffion.AdminEye.PlayerFile;
import nl.Steffion.AdminEye.StefsAPI;
import nl.Steffion.AdminEye.StefsAPI.ExecutedCommand;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class GamemodeCommand extends ExecutedCommand {
	@Override
	public boolean execute(Player player, String playerName, Command cmd,
			String label, String[] args) {
		if (args.length <= 1) {
			StefsAPI.MessageHandler
					.buildMessage()
					.addSender(playerName)
					.setMessage("error.notEnoughArguments", AdminEye.messages)
					.changeVariable("syntax",
							"/gamemode <player name> <type gamemode>").build();
		} else {
			setGamemode(player, playerName, args[0], args[1]);
		}
		return true;
	}

	public static void setGamemode(Player player, String playerName,
			String gamemodePlayerName, String gamemode) {
		ArrayList<Player> gamemodePlayers = AdminEyeUtils
				.requestPlayers(gamemodePlayerName);

		if (gamemodePlayers == null && gamemodePlayerName != null) {
			StefsAPI.MessageHandler.buildMessage().addSender(playerName)
					.setMessage("error.playerNotFound", AdminEye.messages)
					.changeVariable("playername", gamemodePlayerName).build();
			return;
		}

		if (!AdminEyeUtils.isGamemode(gamemode)) {
			StefsAPI.MessageHandler.buildMessage().addSender(playerName)
					.setMessage("error.notAGamemode", AdminEye.messages)
					.changeVariable("gamemode", gamemode).build();
			return;
		}

		String gamemodedPlayers = "";

		for (Player gamemodePlayer : gamemodePlayers) {
			gamemodePlayer.setGameMode(AdminEyeUtils.getGameMode(gamemode));

			if (AdminEyeUtils.getGameMode(gamemode) == GameMode.CREATIVE) {
				PlayerFile playerFile = new PlayerFile(gamemodePlayer.getName());
				playerFile.flyFlying = false;
				playerFile.save();
			}

			gamemodedPlayers += "%A" + gamemodePlayer.getName() + "%N, ";
		}

		gamemodedPlayers = (gamemodePlayerName.equals("*") ? gamemodedPlayers = AdminEye.config
				.getFile().getString("chat.everyone") + "%N, "
				: gamemodedPlayers);

		AdminEye.broadcastAdminEyeMessage(playerName, "setgamemode",
				"gamemode", "playernames", gamemodedPlayers, "gamemode",
				AdminEyeUtils.getGameMode(gamemode).name().toLowerCase());
	}

}
