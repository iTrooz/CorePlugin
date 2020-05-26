package fr.entasia.coreplugin.commands;

import fr.entasia.coreplugin.Main;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CmdSpyToggleCmd implements CommandExecutor {

	
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(!(sender instanceof Player))return false;
		Player p = (Player) sender;
		if(p.hasPermission("restricted.commandspy")){
			if(args.length==0) {
				if (Main.spyers.contains(p.getUniqueId())) {
					Main.removeSpyer(p.getUniqueId());
					p.sendMessage("§aTu as désactivé le Commandspy !");
				} else {
					Main.addSpyer(p.getUniqueId());
					p.sendMessage("§aTu as activé le Commandspy !");
				}
			}else {
				if (args[0].equals("list")) {
					if (Main.spyers.size() == 0) p.sendMessage("§cPersonne n'a activé le commandspy pour le moment !");
					else {
						p.sendMessage("§aListe des gens en commandspy :");
						for (UUID i : Main.spyers) {
							Player tp = Bukkit.getPlayer(i);
							String st;
							if(tp==null)st = "UUID="+i;
							else st = tp.getName();
							TextComponent spy = new TextComponent("§2 -§a "+st);
							ComponentBuilder cb = new ComponentBuilder("§aUUID : §2" + i.toString());
							spy.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, cb.create()));
							spy.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/commandspy " + st));
							p.sendMessage(spy);
						}
					}
				} else {
					Player t = Bukkit.getPlayer(args[0]);
					if (t == null) p.sendMessage("§cToggle du commandspy de joeuurs hors-ligne : non implémenté !");
					else {
						if (Main.spyers.contains(t.getUniqueId())) {
							Main.removeSpyer(p.getUniqueId());
							p.sendMessage("§aTu as désactivé le Commandspy de " + Main.formatPlayerSuffix(t) + "§a !");
						} else {
							Main.addSpyer(t.getUniqueId());
							p.sendMessage("§aTu as activé le Commandspy " + Main.formatPlayerSuffix(t) + "§a !");
						}
					}
				}
			}
		}else p.sendMessage("§cTu n'as pas accès à cette commande !");
		return true;
	}

}
