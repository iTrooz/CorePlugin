package fr.entasia.coreplugin.commands;

import fr.entasia.coreplugin.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCmd implements CommandExecutor {


	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player))return false;
		Player p = (Player) sender;
		if(p.hasPermission("entasia.fly")){
			if(args.length == 0){
				if(p.getAllowFlight()){
					p.setAllowFlight(false);
					p.sendMessage("§aTu ne peux maintenant plus voler !");
				} else {
					p.setAllowFlight(true);
					p.sendMessage("§aTu peux maintenant voler !");
				}
			}else {
				if (p.hasPermission("entasia.fly.others")) {
					Player target = Bukkit.getPlayer(args[0]);
					if (target == null) p.sendMessage("§c4Erreur : §c"+args[0]+" n'est pas connecté ou n'existe pas !");
					else {
						if (target.getAllowFlight()) {
							target.setAllowFlight(false);
							p.sendMessage(Utils.formatPlayerSuffix(target) + " §ane peut désormais plus voler !");
						} else {
							target.setAllowFlight(true);
							p.sendMessage(Utils.formatPlayerSuffix(target) + " §apeut désormais voler !");
						}
					}
				}
			}
		}else p.sendMessage("§cTu n'as pas accès à cette commande !");
		return true;
	}

}
