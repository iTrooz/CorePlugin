package fr.entasia.coreplugin.commands;

import fr.entasia.coreplugin.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HealCmd implements CommandExecutor {


	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player))return false;
		Player p = (Player) sender;
		if(p.hasPermission("entasia.heal")){
			if(args.length == 0){
				p.setHealth(p.getMaxHealth());
				p.setFoodLevel(20);
				p.sendMessage("§aVie regénérée !");
			}else{
				Player target = Bukkit.getPlayer(args[0]);
				if(target == null)p.sendMessage("§c "+args[0]+" n'est pas connecté ou n'existe pas !");
				else {
					target.setHealth(target.getMaxHealth());
					target.setFoodLevel(20);
					p.sendMessage("§aTu as heal "+ Utils.formatPlayerSuffix(target)+"§a !");
				}
			}
		}else p.sendMessage("§cTu n'as pas accès à cette commande !");
		return true;
	}
}
