package nl.Steffion.AdminEye;

import nl.Steffion.AdminEye.Commands.BanCommand;
import nl.Steffion.AdminEye.Commands.DeopCommand;
import nl.Steffion.AdminEye.Commands.FlyCommand;
import nl.Steffion.AdminEye.Commands.FreezeCommand;
import nl.Steffion.AdminEye.Commands.HPCommand;
import nl.Steffion.AdminEye.Commands.HungerCommand;
import nl.Steffion.AdminEye.Commands.KickCommand;
import nl.Steffion.AdminEye.Commands.MuteCommand;
import nl.Steffion.AdminEye.Commands.OpCommand;
import nl.Steffion.AdminEye.Commands.SlapCommand;
import nl.Steffion.AdminEye.Commands.SlayCommand;
import nl.Steffion.AdminEye.Commands.UnbanCommand;
import nl.Steffion.AdminEye.Commands.UnflyCommand;
import nl.Steffion.AdminEye.Commands.UnfreezeCommand;
import nl.Steffion.AdminEye.Commands.UnmuteCommand;

import org.bukkit.entity.Player;

public class AdminEyeAPI {
	/**
	 * Bans a player.
	 * 
	 * @param player
	 *            Player who issued. null for unknown.
	 * @param playerName
	 *            Player's name who issued. "$" for console, "#" for 'system',
	 *            or just the player's name.
	 * @param banPlayerName
	 *            Player's name you want to ban. "*" for everyone.
	 * @param time
	 *            Length of ban. Example: "2d1h30m20s" or "0" for a permanently
	 *            ban.
	 * @param reason
	 *            Reason for ban. null for no reason.
	 */
	public static void banPlayer(Player player, String playerName,
			String banPlayerName, String time, String reason) {
		BanCommand.banPlayer(player, playerName, banPlayerName, time, reason);
	}

	/**
	 * DeOP's a player.
	 * 
	 * @param player
	 *            Player who issues. null if unknown
	 * @param playerName
	 *            Player's name who issued. "$" for console, "#" for 'system',
	 *            or just the player's name.
	 * @param teleportPlayerName
	 *            Player's name you want to deOP. "*" for everyone.
	 */
	public static void deopPlayer(Player player, String playerName,
			String deopPlayerName) {
		DeopCommand.deopPlayer(player, playerName, deopPlayerName);
	}

	/**
	 * Makes a player able to fly.
	 * 
	 * @param player
	 *            Player who issued. null for unknown.
	 * @param playerName
	 *            Player's name who issued. "$" for console, "#" for 'system',
	 *            or just the player's name.
	 * @param flyPlayerName
	 *            Player's name you want to fly. "*" for everyone.
	 */

	public static void flyPlayer(Player player, String playerName,
			String flyPlayerName) {
		FlyCommand.flyPlayer(player, playerName, flyPlayerName);
	}

	/**
	 * Stops a player from moving.
	 * 
	 * @param player
	 *            Player who issued. null for unknown.
	 * @param playerName
	 *            Player's name who issued. "$" for console, "#" for 'system',
	 *            or just the player's name.
	 * @param freezePlayerName
	 *            Player's name you want to freeze. "*" for everyone.
	 */

	public static void freezePlayer(Player player, String playerName,
			String freezePlayerName) {
		FreezeCommand.freezePlayer(player, playerName, freezePlayerName);
	}

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
	 * Sets a player's hunger.
	 * 
	 * @param player
	 *            Player who issues. null if unknown
	 * @param playerName
	 *            Player's name who issued. "$" for console, "#" for 'system',
	 *            or just the player's name.
	 * @param teleportPlayerName
	 *            Player's name you want to set the hunger of. "*" for everyone.
	 * @param amount
	 *            Amount of hunger. 0-20. Note: 0 is starving.
	 */
	public static void setHunger(Player player, String playerName,
			String hungerPlayerName, String amount) {
		HungerCommand.setHunger(player, playerName, hungerPlayerName, amount);
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
	 * Mute's a player.
	 * 
	 * @param player
	 *            Player who issues. null if unknown
	 * @param playerName
	 *            Player's name who issued. "$" for console, "#" for 'system',
	 *            or just the player's name.
	 * @param mutePlayerName
	 *            Player's name you want to mute. "*" for everyone.
	 */

	public static void mutePlayer(Player player, String playerName,
			String mutePlayerName, String time) {
		MuteCommand.mutePlayer(player, playerName, mutePlayerName, time);
	}

	/**
	 * Op's a player.
	 * 
	 * @param player
	 *            Player who issues. null if unknown
	 * @param playerName
	 *            Player's name who issued. "$" for console, "#" for 'system',
	 *            or just the player's name.
	 * @param opPlayerName
	 *            Player's name you want to op. "*" for everyone.
	 */
	public static void opPlayer(Player player, String playerName,
			String opPlayerName) {
		OpCommand.opPlayer(player, playerName, opPlayerName);
	}

	/**
	 * Slaps a player.
	 * 
	 * @param player
	 *            Player who issues. null if unknown
	 * @param playerName
	 *            Player's name who issued. "$" for console, "#" for 'system',
	 *            or just the player's name.
	 * @param slapPlayerName
	 *            Player's name you want to slap. "*" for everyone.
	 */
	public static void slapPlayer(Player player, String playerName,
			String slapPlayerName) {
		SlapCommand.slapPlayer(player, playerName, slapPlayerName);
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

	/**
	 * Stops a player from flying.
	 * 
	 * @param player
	 *            Player who issues. null if unknown
	 * @param playerName
	 *            Player's name who issued. "$" for console, "#" for 'system',
	 *            or just the player's name.
	 * @param unflyPlayerName
	 *            Player's name you want to stop flying. "*" for everyone.
	 */

	public static void unflyPlayer(Player player, String playerName,
			String unflyPlayerName) {
		UnflyCommand.unflyPlayer(player, playerName, unflyPlayerName);
	}

	/**
	 * Unfreeze's a player.
	 * 
	 * @param player
	 *            Player who issued. null if unknown.
	 * @param playerName
	 *            Player's name who issued. "$" for console, "#" for 'system',
	 *            or just the player's name.
	 * @param unfreezePlayerName
	 *            Player's name you want to unfreeze. "*" for everyone.
	 */

	public static void unfreezePlayer(Player player, String playerName,
			String unfreezePlayerName) {
		UnfreezeCommand.unfreezePlayer(player, playerName, unfreezePlayerName);
	}

	/**
	 * Unmutes a player.
	 * 
	 * @param player
	 *            Player who issued. null if unknown.
	 * @param playerName
	 *            Player's name who issued. "$" for console, "#" for 'system',
	 *            or just the player's name.
	 * @param unmutePlayerName
	 *            Player's name you want to slay. "*" for everyone.
	 */

	public static void unmutePlayer(Player player, String playerName,
			String unmutePlayerName) {
		UnmuteCommand.unmutePlayer(player, playerName, unmutePlayerName);
	}

	/**
	 * Unbans a player.
	 * 
	 * @param player
	 *            Player who issued. null for unknown.
	 * @param playerName
	 *            Player's name who issued. "$" for console, "#" for 'system',
	 *            or just the player's name.
	 * @param unbanPlayerName
	 *            Player's name you want to unban. "*" for everyone.
	 */
	public static void unbanPlayer(Player player, String playerName,
			String unbanPlayerName) {
		UnbanCommand.unbanPlayer(player, playerName, unbanPlayerName);
	}

}
