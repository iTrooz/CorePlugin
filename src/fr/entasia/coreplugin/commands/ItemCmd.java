package fr.entasia.coreplugin.commands;

import fr.entasia.coreplugin.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemCmd implements CommandExecutor {

	// item stone
	// item stone 64
	// item stone 64 mortel1211
	// item stone mortel1211


	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) return false;
		Player p = (Player) sender;
		if (p.hasPermission("entasia.item")) {
			if (args.length == 0) p.sendMessage("§cErreur de syntaxe !");
			else {
				ItemStack item;
				try {
					Material a = Material.getMaterial(Integer.parseInt(args[0]));
					item = new ItemStack(a, 64);
				} catch (NumberFormatException e) {
					Material a = Material.getMaterial(args[0]);
					item = new ItemStack(a, 64);
				}
				Player target = null;
				if (args.length > 1) {
					try {
						item.setAmount(Integer.parseInt(args[1]));
						target = Bukkit.getPlayer(args[2]);
					} catch (NumberFormatException e) {
						target = Bukkit.getPlayer(args[1]);
						if (target == null) {
							p.sendMessage("§cErreur de syntaxe !");
							return true;
						}
					}
				}
				if (target == null) {
					p.getInventory().addItem(item);
					p.sendMessage("§aTu t'es give §2" + item.getAmount() + " " + item.getType().getData().getName() + "§a !");
				} else {
					target.getInventory().addItem(item);
					p.sendMessage("§aTu as give §2" + item.getAmount() + " " + item.getType().getData().getName() + "§a a " + Utils.formatPlayerSuffix(target) + "§a !");
				}
			}
		} else p.sendMessage("§cTu n'as pas accès à cette commande !");
		return true;
	}
}