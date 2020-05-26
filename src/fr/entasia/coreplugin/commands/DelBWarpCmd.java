package fr.entasia.coreplugin.commands;

import fr.entasia.coreplugin.Main;
import fr.entasia.coreplugin.utils.Warp;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class DelBWarpCmd implements CommandExecutor {


	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player))return false;
		Player p = (Player) sender;
		if(p.hasPermission("build.delbwarp")){
			if(args.length ==0)p.sendMessage("§cErreur §8» §c Mauvaise utilisation de la commande ! /delbwarp <Nom du warp>");
			else {
				Warp a = Warp.getByName(args[0]);
				if(a == null)
					p.sendMessage("§cErreur §8» §c Le warp §l" + args[0] + "§c n'existe pas !");
				else{
					try {
						Main.warps.remove(a);
						Main.dataconfig.set("warps."+a.name, null);
						Main.dataconfig.save(Main.datafile);
						p.sendMessage("§aLe warp §2" + args[0] + "§a a été supprimé avec succès !");
					} catch (IOException e) {
						e.printStackTrace();
						Main.warps.add(a);
						p.sendMessage("§cErreur §8» §c Le warp §l" + args[0] + "§c n'a pas été supprimé suite à une erreur interne (voir console)");
					}
				}
			}
		}else p.sendMessage("§cTu n'as pas accès à cette commande !");
		return true;
	}

}
