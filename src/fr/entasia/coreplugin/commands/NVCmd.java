package fr.entasia.coreplugin.commands;

import fr.entasia.coreplugin.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class NVCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player))return false;
			Player p = (Player) sender;
			if(p.hasPermission("entasia.nightvision")){
				if(args.length==0){
					if(p.hasPotionEffect(PotionEffectType.NIGHT_VISION)){
						p.removePotionEffect(PotionEffectType.NIGHT_VISION);
						p.sendMessage("§aTu as désactivé ta vision nocturne !");
					} else {
						p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 999999, 255));
						p.sendMessage("§aTu as activé ta vision nocturne !");
					}
				}else{
				if(p.hasPermission("entasia.nightvision.others")){
					Player target = Bukkit.getPlayer(args[0]);
					if(target==null)p.sendMessage("§cErreur :"+args[0]+" n'est pas connecté ou n'existe pas !");
					else{
						if(target.hasPotionEffect(PotionEffectType.NIGHT_VISION)){
							target.removePotionEffect(PotionEffectType.NIGHT_VISION);
							p.sendMessage("§aTu as désactivé la vision nocturne de "+ Utils.formatPlayerSuffix(target)+" §a!");
						} else {
							target.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 999999, 255));
							p.sendMessage("§aTu as activé la vision nocturne de "+ Utils.formatPlayerSuffix(target)+" §a!");
						}
					}
				}else p.sendMessage("§cErreur :Tu n'as pas la permission de changer la vision nocture des autres joueurs !");
			}
		}else p.sendMessage("§cTu n'as pas accès à cette commande !");
		return true;
	}

}
