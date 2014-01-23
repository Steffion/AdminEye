package nl.Steffion.AdminEye;

import java.util.ArrayList;

import org.bukkit.Bukkit;
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

	public static boolean isNumber(String number) {
		try {
			Integer.valueOf(number);
			return true;
		} catch (NumberFormatException e) {
			return false;
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
