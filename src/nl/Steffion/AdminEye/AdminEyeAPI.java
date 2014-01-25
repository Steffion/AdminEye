package nl.Steffion.AdminEye;

import nl.Steffion.AdminEye.Commands.GotoCommand;
import nl.Steffion.AdminEye.Commands.HPCommand;
import nl.Steffion.AdminEye.Commands.KickCommand;
import nl.Steffion.AdminEye.Commands.SlayCommand;

import org.bukkit.entity.Player;

public class AdminEyeAPI {

	/**
	 * Sets a player's health.
	 * 
	 * @param player
	 *            Player who issued. null for unknown.
	 * @param playerName
	 *            Player's name who issued. "$" for console, "#" for 'system',
	 *            or just the player's name.
	 * @param healPlayerName
	 *            Player's name you want to set the health of. "*" for everyone.
	 * @param amount
	 *            Amount of health. 0-20. Note: 0 is dead.
	 */
	public static void setHealth(Player player, String playerName,
			String healPlayerName, String amount) {
		HPCommand.setHealth(player, playerName, healPlayerName, amount);
	}

	/**
	 * Kicks (disconnects) a player from the server.
	 * 
	 * @param player
	 *            Player who issued. null if unknown.
	 * @param playerName
	 *            Player's name who issued. "$" for console, "#" for 'system',
	 *            or just the player's name.
	 * @param kickPlayerName
	 *            Player's name you want to kick. "*" for everyone.
	 * @param reason
	 *            Reason for kick. null makes it "No reason given.". Colours are
	 *            supported and use "&u" for next line.
	 */
	public static void kickPlayer(Player player, String playerName,
			String kickPlayerName, String reason) {
		KickCommand.kickPlayer(player, playerName, kickPlayerName, reason);
	}

	/**
	 * Slays (kills) a player.
	 * 
	 * @param player
	 *            Player who issued. null if unknown.
	 * @param playerName
	 *            Player's name who issued. "$" for console, "#" for 'system',
	 *            or just the player's name.
	 * @param slayPlayerName
	 *            Player's name you want to slay. "*" for everyone.
	 */
	public static void slayPlayer(Player player, String playerName,
			String slayPlayerName) {
		SlayCommand.slayPlayer(player, playerName, slayPlayerName);
	}

	public static void teleportplayer(Player player, String playerName,
			String teleportPlayerName) {
		GotoCommand.gotoPlayer(player, playerName, teleportPlayerName);
	}
}
