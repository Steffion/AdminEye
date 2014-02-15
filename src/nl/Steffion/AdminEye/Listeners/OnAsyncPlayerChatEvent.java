package nl.Steffion.AdminEye.Listeners;

import java.util.Calendar;

import nl.Steffion.AdminEye.AdminEye;
import nl.Steffion.AdminEye.PlayerFile;
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
			if (playerFile.muteLength != 0) {
				int time = (int) Calendar.getInstance().getTimeInMillis();

				if (time >= playerFile.muteLength) {
					playerFile.muteMuted = false;
					playerFile.save();

					AdminEye.broadcastAdminEyeMessage("#", "systemUnmute",
							"systemUnmute", "playernames", player.getName());
					return;
				}
			}
			event.setCancelled(true);
		}
	}
}