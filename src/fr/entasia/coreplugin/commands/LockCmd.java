package fr.entasia.coreplugin.commands;

import fr.entasia.apis.utils.ServerUtils;
import fr.entasia.coreplugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class LockCmd implements CommandExecutor {


	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player))return false;
			Player p = (Player) sender;
			if(p.hasPermission("staff.lockdown.use")){

				if (args.length != 0 && (args[0].equals("status") || args[0].equals("statut"))) {
					if (Main.lockdown == null)
						p.sendMessage("§6Lockdown §8» §7Actuellement §cDésactivé §7!");
					else
						p.sendMessage("§6Lockdown §8» §7Actuellement §aActivé §7!");
				}else{
					if(Main.lockdown == null){
						if(args.length==0){
							Main.lockdown = "aucune raison définie";
							p.sendMessage("§6Lockdown §8» §aActivé pour §e"+ ServerUtils.serverName+"§a !");
						}else{
							Main.lockdown = String.join(" ",args);
							p.sendMessage("§6Lockdown §8» §aActivé pour §e"+ ServerUtils.serverName+"§a ! Raison : §6"+ Main.lockdown);
						}
					} else {
						Main.lockdown = null;
						Bukkit.broadcastMessage("§6Lockdown §8» §cDésactivé pour §e"+ServerUtils.serverName+"§c !");
					}
					Main.dataconfig.set("lockdown", Main.lockdown);
					try{
						Main.dataconfig.save(Main.datafile);
					}catch(IOException e){
						e.printStackTrace();
					}
				}
			} else p.sendMessage("§cTu n'as pas accès à cette commande !");
		return true;
	}

}
