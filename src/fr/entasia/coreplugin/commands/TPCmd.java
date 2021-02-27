package fr.entasia.coreplugin.commands;

import fr.entasia.coreplugin.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TPCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player))return false;
		Player p = (Player) sender;
		if(p.hasPermission("entasia.tp")){
			if(args.length == 1) {
				Player target = Bukkit.getPlayer(args[0]);
				if (target == null) p.sendMessage("§cErreur : "+args[0]+" n'est pas connecté ou n'existe pas !");
				else{
					p.teleport(target.getLocation());
					p.sendMessage("§aTu t'es téléporté à " + Utils.formatPlayerSuffix(target)+ "§a !");
				}

			}else if(args.length == 2){
				if(p.hasPermission("entasia.tp.others")){
					Player target = Bukkit.getPlayer(args[0]);
					if (target == null) p.sendMessage("§cErreur : "+args[0]+" n'est pas connecté ou n'existe pas!");
					else{
						Player to = Bukkit.getPlayer(args[1]);
						if (to == null) p.sendMessage("§cErreur : "+args[1]+" n'est pas connecté ou n'existe pas!");
						else{
							target.teleport(to.getLocation());
							p.sendMessage("§aTu as téléporté "+ Utils.formatPlayerSuffix(target)+"§a a "+ Utils.formatPlayerSuffix(to)+"§a !");
						}

					}

				}else p.sendMessage("§cErreur :Tu n'as pas la permission de te téléporter d'autres joueurs !");

			}else if(args.length >= 3){
				if(p.hasPermission("entasia.tp.coordinates")){
					try {
						Location loc = new Location(p.getWorld(),
								Double.parseDouble(args[0])+0.5,
								Double.parseDouble(args[1])+0.2,
								Double.parseDouble(args[2])+0.5
						);
						if (args.length >= 4) {
							loc.setWorld(Bukkit.getWorld(args[3]));
							if (loc.getWorld() == null) {
								p.sendMessage("§cErreur : Le monde " + args[3] + " n'existe pas !");
								return true;
							}
							p.sendMessage("§aTu as été téléporté en " + args[0] + " " + args[1] + " " + args[2] + "  monde " + args[3] + " !");
						} else{
							p.sendMessage("§aTu as été téléporté en " + args[0] + " " + args[1] + " " + args[2] + " !");
						}
						p.teleport(loc);
					} catch (NumberFormatException e) {
						p.sendMessage("§cErreur : Les coordonnées sont invalides !");
					}
				}else p.sendMessage("§cErreur : Tu n'as pas la permission de te téléporter à des coordonnées !");
			}else p.sendMessage("§cErreur : Utilisations de la commande : /tp <joueur> ou ou /tp <joueur 1> <joueur 2> ou /tp <x> <y> <z> [monde]");
		} else p.sendMessage("§cTu n'as pas accès à cette commande !");
		return true;
	}
}
