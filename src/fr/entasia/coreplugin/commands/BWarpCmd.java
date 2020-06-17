package fr.entasia.coreplugin.commands;

import fr.entasia.apis.ChatComponent;
import fr.entasia.coreplugin.Main;
import fr.entasia.coreplugin.utils.Warp;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class BWarpCmd implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player))return false;
		Player p = (Player) sender;
		if(p.hasPermission("build.bwarp")){
			if(args.length == 0){
				sendPage(p, 1);
			}else {
				try{
					sendPage(p, Integer.parseInt(args[0]));
				}catch(NumberFormatException ignore){
					List<Warp> f = new ArrayList<>();
					for (Warp w : Main.warps){
						if (w.name.equalsIgnoreCase(args[0])){
							w.teleport(p);
							return true;
						}
						else if (w.name.contains(args[0]))f.add(w);
					}

					if (f.size() == 0){
						p.sendMessage("§cAucun warp nommé ou contenant §4" + args[0] + "§c dans son nom n'a été trouvé ! Fait /bwarp pour la liste des warps.");
					}else if (f.size() == 1){
						f.get(0).teleport(p);
					} else {
						p.sendMessage("§cAucun warp nommé §2" + args[0] + "§a n'a été trouvé. Peut être cherchais-tu l'un de ces warps :");
						f.forEach(w -> sendWarp(p, w));
					}
				}
			}
		}else p.sendMessage("§cTu n'as pas accès à cette commande !");
		return true;
	}

	private static void sendWarp(Player p, Warp w) {
		ChatComponent warp = new ChatComponent("§a- "+w.name);
		ChatComponent hover = new ChatComponent("§aInfos sur le warp §2" + w.name + "§a :\n§aX : " + w.loc.getBlockX() + ", Y : " + w.loc.getBlockY() +
				" & Z : " + w.loc.getBlockZ() + "\n§aMonde : " + w.world + "\n§aCréateur : " + w.creator);
		if(Bukkit.getWorld(w.world)==null){
			warp.setColor(ChatColor.GRAY);
			hover.append(new ChatComponent("Le monde de ce warp est invalide !"));
			hover.setColor(ChatColor.GRAY);
		} else{
			warp.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/bwarp " + w.name));
		}
		warp.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover.create()));
		p.sendMessage(warp.create());
	}

	private static final int PAGE_LEN = 15;

	private static void sendPage(Player p, int page) {

		if(page<1){
			p.sendMessage("§cCette page est invalide !");
			return;
		}

		boolean before = page != 1;
		boolean after = false;

		int min = (page-1) * PAGE_LEN;
		if (min >= Main.warps.size()) {
			p.sendMessage("§cCette page est trop grande !");
			return;
		}

		int max = page * PAGE_LEN;
		if (max > Main.warps.size()) {
			max = Main.warps.size();
		} else if (max != Main.warps.size()) after = true;

		p.sendMessage("§aPage §2"+page+"§a des warps : ");
		for (Warp w : Main.warps.subList(min, max)) {
			sendWarp(p, w);
		}
		ChatComponent beforeC = new ChatComponent("Précédent");
		if(before){
			beforeC.setColor(ChatColor.GREEN);
			beforeC.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, ChatComponent.create("§aClique pour aller à la page "+(page-1)+" !")));
			beforeC.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/bwarp "+(page-1)));
		}else beforeC.setColor(ChatColor.RED);

		ChatComponent afterC = new ChatComponent("Suivant");
		if(after){
			afterC.setColor(ChatColor.GREEN);
			afterC.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, ChatComponent.create("§aClique pour aller à la page "+(page+1)+" !")));
			afterC.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/bwarp "+(page+1)));
		}else afterC.setColor(ChatColor.RED);

		p.sendMessage(ChatComponent.create(beforeC, new ChatComponent("§7  |  "), afterC));

	}
}
