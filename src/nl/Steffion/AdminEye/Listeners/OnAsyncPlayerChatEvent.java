package nl.Steffion.AdminEye.Listeners;

import java.util.Calendar;

import nl.Steffion.AdminEye.AdminEye;
import nl.Steffion.AdminEye.PlayerFile;
import nl.Steffion.AdminEye.StefsAPI;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class OnAsyncPlayerChatEvent implements Listener {
	@EventHandler(priority = EventPriority.NORMAL)
	public void onASyncOnPlayerChatEvent(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		PlayerFile playerFile = new PlayerFile(player.getName());
		if (playerFile.muteMuted) {
			String finalTime = (playerFile.muteLength.equals(0) ? AdminEye.messages
					.getFile().getString("normal.permanently") : "");
			if (playerFile.muteLength != 0) {
				int time = (int) Calendar.getInstance().getTimeInMillis();

				if (time >= playerFile.muteLength) {
					playerFile.muteMuted = false;
					playerFile.save();

					AdminEye.broadcastAdminEyeMessage("#", "unmuted",
							"unmuted", "playernames", player.getName() + "%N, ");
					return;
				}

				int days = 0;
				int hours = 0;
				int minutes = 0;
				int seconds = 0;

				days = (playerFile.muteLength - time) / 1000 / 60 / 60 / 24;
				hours = ((playerFile.muteLength - time) / 1000 / 60 / 60)
						- (days * 24);
				minutes = ((playerFile.muteLength - time) / 1000 / 60)
						- (hours * 60) - (days * 24 * 60);
				seconds = ((playerFile.muteLength - time) / 1000)
						- (minutes * 60) - (hours * 60 * 60)
						- (days * 24 * 60 * 60);

				if (days != 0) {
					finalTime += " %A"
							+ days
							+ AdminEye.messages.getFile().getString(
									"normal.days");
				}
				if (hours != 0) {
					finalTime += " %A"
							+ hours
							+ AdminEye.messages.getFile().getString(
									"normal.hours");
				}
				if (minutes != 0) {
					finalTime += " %A"
							+ minutes
							+ AdminEye.messages.getFile().getString(
									"normal.minutes");
				}
				if (seconds != 0) {
					finalTime += " %A"
							+ seconds
							+ AdminEye.messages.getFile().getString(
									"normal.seconds");
				}
			}

			StefsAPI.MessageHandler.buildMessage().addSender(player.getName())
					.setMessage("error.muted", AdminEye.messages)
					.changeVariable("time", finalTime).build();
			event.setCancelled(true);
		}
	}
}