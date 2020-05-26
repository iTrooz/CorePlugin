package fr.entasia.coreplugin.commands;

import fr.entasia.coreplugin.Main;
import fr.entasia.coreplugin.utils.Utils;
import fr.entasia.coreplugin.utils.Warp;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class SetBWarpCmd implements CommandExecutor {

	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player))return false;
		Player p = (Player) sender;
		if(p.hasPermission("build.setbwarp")){
			if(args.length ==0)p.sendMessage("§cErreur §8» §c Mauvaise utilisation de la commande ! /setbwarp <Nom du warp>");
			else {
				if (Warp.getByName(args[0]) == null) {
					Warp a = new Warp(Utils.getRoundedLocation(p.getLocation()), p.getWorld().getName(), args[0], p.getName());
					try{
						Main.warps.add(a);
						Main.dataconfig.set("warps."+a.name+".location", a.loc.getBlockX()+","+a.loc.getBlockY()+","+a.loc.getBlockZ());
						Main.dataconfig.set("warps."+a.name+".world", a.loc.getWorld().getName());
						Main.dataconfig.set("warps."+a.name+".creator", a.creator);
						Main.dataconfig.save(Main.datafile);
						p.sendMessage("§aLe warp §2"+args[0]+"§a a été créé avec succès !");
					}catch(IOException e){
						e.printStackTrace();
						Main.warps.remove(a);
						p.sendMessage("§cErreur §8» §c Le warp §l"+args[0]+"§c n'a pas été créé suite à une erreur interne (voir console)");
					}
				}else p.sendMessage("§cErreur §8» §c Le warp §l"+args[0]+"§c existe déjà ! Pour le supprimer, faites /delbwarp §l"+args[0]);
			}
		}else p.sendMessage("§cTu n'as pas accès à cette commande !");
		return true;
	}

}
