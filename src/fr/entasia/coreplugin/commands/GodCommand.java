package fr.entasia.coreplugin.commands;

import fr.entasia.coreplugin.Main;
import fr.entasia.coreplugin.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GodCommand implements CommandExecutor {


	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player){
			Player p = (Player) sender;
			if(p.hasPermission("entasia.god")){
				if(args.length == 0){
					if(Main.gods.contains(p.getUniqueId())){
						Main.gods.remove(p.getUniqueId());
						p.sendMessage("§aVous avez désactivé votre mode Dieu !");
					} else {
						Main.gods.add(p.getUniqueId());
						p.sendMessage("§aVous avez activé votre mode Dieu !");
					}

				}else{
					if(p.hasPermission("entasia.god.others")) {
						Player target = Bukkit.getPlayer(args[0]);
						if(target == null)p.sendMessage("§c "+args[0]+" n'est pas connecté ou n'existe pas !");
						else {
							if (Main.gods.contains(target.getUniqueId())) {
								Main.gods.remove(target.getUniqueId());
								p.sendMessage("§aTu as désactivé le mode Dieu de " + Utils.formatPlayerSuffix(target) + " §a!");
							} else {
								Main.gods.add(target.getUniqueId());
								p.sendMessage("§aTu as activé le mode Dieu de " + Utils.formatPlayerSuffix(target) + " §a!");
							}
						}
					}else p.sendMessage("§c Tu n'as pas la permission de changer le mode Dieu des autres joueurs !");
				}
			}else p.sendMessage("§cTu n'as pas accès à cette commande !");
			
		}
		return false;
	}

}
