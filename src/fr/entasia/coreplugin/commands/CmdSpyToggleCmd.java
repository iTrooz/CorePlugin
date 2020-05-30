package fr.entasia.coreplugin.commands;

import fr.entasia.apis.ChatComponent;
import fr.entasia.coreplugin.Main;
import net.md_5.bungee.api.ChatColor;
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
				if (Main.spyers.contains(p.getName())) {
					Main.removeSpyer(p.getName());
					p.sendMessage("§aTu as désactivé le Commandspy !");
				} else {
					Main.addSpyer(p.getName());
					p.sendMessage("§aTu as activé le Commandspy !");
				}
			}else {
				if (args[0].equals("list")) {
					if (Main.spyers.size() == 0) p.sendMessage("§cPersonne n'a activé le commandspy pour le moment !");
					else {
						p.sendMessage("§aListe des gens en commandspy :");
						for (String s : Main.spyers) {
							Player tp = Bukkit.getPlayer(s);
							ChatComponent spy = new ChatComponent("§2 -§a "+tp.getName());
							spy.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, ChatComponent.create("§cClique pour désactiver le commandspy !")));
							spy.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/commandspy "+tp.getName()));
							p.sendMessage(spy.create());
						}
					}
				} else {
					if (Main.spyers.contains(args[0])){
						Main.removeSpyer(args[0]);
						p.sendMessage("§aTu as désactivé le Commandspy de " + args[0] + "§a !");
					} else {
						Main.addSpyer(args[0]);
						p.sendMessage("§aTu as activé le Commandspy " + args[0] + "§a !");
					}
				}
			}
		}else p.sendMessage("§cTu n'as pas accès à cette commande !");
		return true;
	}

}
