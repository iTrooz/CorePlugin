package fr.entasia.coreplugin.commands;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetRawBlockCmd implements CommandExecutor {


	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) return false;
		if(sender.hasPermission("build.setrawblock")){
			Player p = (Player)sender;
			if(args.length>0){

				Material m;
				try {
					m = Material.getMaterial(Integer.parseInt(args[0]));
				} catch (NumberFormatException ignored) {
					m = Material.getMaterial(args[0].toUpperCase());
					if (m == null){
						p.sendMessage("Material "+args[0]+" non valide");
						return true;
					}
				}
				byte meta = 0;
				if(args.length>1){
					try {
						meta = Byte.parseByte(args[1]);
					} catch (NumberFormatException ignored) {
						p.sendMessage("§cMeta "+args[1]+" invalide !");
						return true;
					}
				}

				Block b = p.getTargetBlock(null, 40);
				if(b==null)sender.sendMessage("§cAucun block en visuel !");
				else{
					b.setType(m, false);
					b.setData(meta, false);
					p.sendMessage("§aSuccès !");
				}
			}else p.sendMessage("§cSyntaxe : /setrawblock <id/nom du block> [meta]");
		}else sender.sendMessage("§cTu n'as pas accès à cette commande !");
		return true;
	}
}
