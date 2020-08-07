package fr.entasia.coreplugin.listeners;

import fr.entasia.apis.other.ChatComponent;
import fr.entasia.apis.socket.SocketClient;
import fr.entasia.apis.utils.ServerUtils;
import fr.entasia.apis.utils.TextUtils;
import fr.entasia.coreplugin.Main;
import fr.entasia.coreplugin.utils.ConsoleFilter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.event.server.ServerCommandEvent;

import java.util.regex.Pattern;

import static fr.entasia.coreplugin.Main.vaultChat;

public class Others implements Listener {

	@EventHandler
	public void PlayerLogin(PlayerLoginEvent e) {
		if (ServerUtils.isGameServer()) {
			SocketClient.sendData("onlines " + (Bukkit.getOnlinePlayers().size() + 1));
		}
	}

	@EventHandler
	public void PlayerQuit(PlayerQuitEvent e) {
		if (ServerUtils.isGameServer()) {
			SocketClient.sendData("onlines " + (Bukkit.getOnlinePlayers().size() - 1));
		}
	}

	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
		Cmd("§6" + e.getPlayer().getName(), e.getMessage());
	}

	@EventHandler
	public void onConsoleCommand(ServerCommandEvent e) {
		Cmd("§cConsole", e.getCommand());
	}

	private static void Cmd(String by, String tcmd) {
		String lacmd = tcmd.split(" ")[0].toLowerCase();
		for (String i : ConsoleFilter.logincmds) {
			if (lacmd.equals(i)) return;
		}
		TextComponent cmd = new TextComponent("§2S §aCommandSpy§c» " + by + " §6: §6" + tcmd);
		cmd.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, ChatComponent.create("§aClique pour copier la commande !")));
		cmd.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, tcmd));
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (Main.spyers.contains(p.getName())) {
				if (p.hasPermission("restricted.commandspy"))p.sendMessage(cmd);
				else Main.spyers.remove(p.getName());
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onChat(AsyncPlayerChatEvent e) {
		String msg = TextUtils.formatMessage(e.getMessage(), ChatColor.GRAY);
		if (e.getPlayer().hasPermission("chat.color")) msg = TextUtils.setColors(msg);
		boolean everyone = false;
		if (msg.contains("@everyone") && e.getPlayer().hasPermission("staff.everyone")) {
			everyone = true;
			msg = Pattern.compile("@everyone", Pattern.CASE_INSENSITIVE).matcher(msg).replaceAll("§9@everyone§7");
		}
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (msg.contains(p.getDisplayName()) || everyone) {
				Location l = p.getLocation();
				l.setY(l.getY() + 12);
				if (everyone) p.playSound(l, Sound.ENTITY_PLAYER_LEVELUP, 4.5f, 0.9f);
				else {
					p.playSound(l, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 3.5f, 1.1f);
					msg = Pattern.compile(p.getDisplayName(), Pattern.CASE_INSENSITIVE).matcher(msg).replaceAll("§d" + p.getDisplayName() + "§7");
				}
				p.sendMessage("§bTu as été mentionné dans le Chat !");
			}
		}
		e.setMessage(msg);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void chatFormat(AsyncPlayerChatEvent e){
		e.setFormat(format(e.getPlayer()) + "§7 %1$s§8 | §7%2$s");
	}


	public static String format(Player p){
		if(vaultChat==null)return "§7";
		else return vaultChat.getPlayerPrefix(p).replace("&", "§");
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void a(PlayerKickEvent e){
		if(ServerUtils.bungeeMode){
			e.setCancelled(true);
			e.getPlayer().sendMessage("§cVous avez été kick du serveur "+ServerUtils.serverName+" ! Raison : \n§7"+e.getReason());
			SocketClient.sendData("BungeeCord send "+e.getPlayer().getName()+" hub");
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void a(PlayerTeleportEvent e){
		e.getPlayer().setFallDistance(0);
	}
}
