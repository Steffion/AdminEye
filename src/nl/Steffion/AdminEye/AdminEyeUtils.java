package nl.Steffion.AdminEye;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class AdminEyeUtils {
	public static String stringBuilder(String[] input, int startArg) {
		if (input.length - startArg <= 0) {
			return null;
		}

		StringBuilder sb = new StringBuilder(input[startArg]);
		for (int i = ++startArg; i < input.length; i++) {
			sb.append(' ').append(input[i]);
		}

		return sb.toString();
	}

	public static Player requestPlayer(String namepart) {
		Player requested = null;
		boolean found = false;
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (namepart.equals("*")) {
				requested = player;
				found = true;
			} else if (player.getName().toLowerCase()
					.contains(namepart.toLowerCase())) {
				requested = player;
				found = true;
			}
		}

		return (found ? requested : null);
	}

	public static ArrayList<Player> requestPlayers(String namepart) {
		ArrayList<Player> requested = new ArrayList<Player>();
		boolean found = false;
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (namepart.equals("*")) {
				requested.add(player);
				found = true;
			} else if (player.getName().toLowerCase()
					.contains(namepart.toLowerCase())) {
				requested.add(player);
				found = true;
			}
		}

		return (found ? requested : null);
	}

	public static ArrayList<OfflinePlayer> requestFilePlayers(String namepart) {
		ArrayList<OfflinePlayer> requested = new ArrayList<OfflinePlayer>();
		;
		boolean found = false;
		File dir = new File("plugins/" + AdminEye.pdfFile.getName()
				+ "/players/");
		for (File file : dir.listFiles()) {
			PlayerFile playerFile = new PlayerFile(file.getName().replaceAll(
					".yml", ""));
			if (namepart.equals("*")) {
				requested
						.add((Bukkit.getOfflinePlayer(playerFile.playerName) != null ? Bukkit
								.getOfflinePlayer(playerFile.playerName) : null));

				found = (requested != null ? true : false);
			} else if (playerFile.playerName.toLowerCase().contains(
					namepart.toLowerCase())) {
				requested
						.add((Bukkit.getOfflinePlayer(playerFile.playerName) != null ? Bukkit
								.getOfflinePlayer(playerFile.playerName) : null));
				found = (requested != null ? true : false);
			}
		}
		return (found ? requested : null);
	}

	public static OfflinePlayer requestFilePlayer(String namepart) {
		OfflinePlayer requested = null;
		boolean found = false;
		File dir = new File("plugins/" + AdminEye.pdfFile.getName()
				+ "/players/");
		for (File file : dir.listFiles()) {
			PlayerFile playerFile = new PlayerFile(file.getName().replaceAll(
					".yml", ""));
			if (namepart.equals("*")) {
				requested = (Bukkit.getOfflinePlayer(playerFile.playerName) != null ? Bukkit
						.getOfflinePlayer(playerFile.playerName) : null);

				found = (requested != null ? true : false);
			} else if (playerFile.playerName.toLowerCase().contains(
					namepart.toLowerCase())) {
				requested = (Bukkit.getOfflinePlayer(playerFile.playerName) != null ? Bukkit
						.getOfflinePlayer(playerFile.playerName) : null);
				found = (requested != null ? true : false);
			}
		}
		return (found ? requested : null);
	}

	public static boolean isNumber(String number) {
		try {
			Integer.valueOf(number);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static boolean isGamemode(String gamemode) {
		if (gamemode.equalsIgnoreCase("survival")
				|| gamemode.equalsIgnoreCase("0")
				|| gamemode.equalsIgnoreCase("creative")
				|| gamemode.equalsIgnoreCase("1")
				|| gamemode.equalsIgnoreCase("adventure")
				|| gamemode.equalsIgnoreCase("2")) {
			return true;
		} else {
			return false;
		}
	}

	public static GameMode getGameMode(String gamemode) {
		if (gamemode.equalsIgnoreCase("survival")
				|| gamemode.equalsIgnoreCase("0")) {
			return GameMode.SURVIVAL;
		} else if (gamemode.equalsIgnoreCase("creative")
				|| gamemode.equalsIgnoreCase("1")) {
			return GameMode.CREATIVE;
		} else if (gamemode.equalsIgnoreCase("adventure")
				|| gamemode.equalsIgnoreCase("2")) {
			return GameMode.ADVENTURE;
		} else {
			return GameMode.SURVIVAL;
		}
	}

	public static int getNumber(String number) {
		try {
			return Integer.valueOf(number);
		} catch (NumberFormatException e) {
			return 0;
		}
	}
}
