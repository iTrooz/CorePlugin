package fr.entasia.coreplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AmountCmd implements CommandExecutor {


	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) return false;
		if (sender.hasPermission("entasia.amount")) {
			if(args.length>0){
				short n;
				try{
					n = Short.parseShort(args[0]);
					if(n<1||n>64)throw new NumberFormatException();
				}catch(NumberFormatException e){
					sender.sendMessage("§cCe nombre est invalide ! Tu dois saisir un nombre en 1 et 64");
					return true;
				}
				Player p = (Player) sender;
				ItemStack item = p.getInventory().getItemInMainHand();
				if(item==null)p.sendMessage("§cTu n'as aucun item dans ta main !");
				else {
					item.setAmount(n);
					p.sendMessage("§aLe montant de ton item à été mis à "+n+" !");
					p.getInventory().setItemInMainHand(item);
				}
			}else sender.sendMessage("§cTu dois mettre un nombre !");

		} else sender.sendMessage("§cTu n'as pas accès à cette commande !");
		return true;
	}
}