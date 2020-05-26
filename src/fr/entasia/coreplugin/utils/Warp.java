package fr.entasia.coreplugin.utils;

import fr.entasia.coreplugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Warp {

	public Location loc;
	public String name, world, creator;
	
	public Warp(Location loc, String world, String name, String creator){
		this.loc = loc;
		this.world = world;
		this.name = name;
		this.creator = creator;
	}

//	public Location getLocation(){
//		loc.setWorld(Bukkit.getWorld(world));
//		if(loc.getWorld()==null)return null;
//		else return loc;
//	}

	public void teleport(Player p){
		loc.setWorld(Bukkit.getWorld(world));
		if(loc.getWorld()==null)p.sendMessage("§cLe monde du warp §4"+name+"§c est invalide !");
		else{
			p.teleport(loc);
			p.sendMessage("§aTu as été téléporté au warp §2"+name+"§a !");
		}
	}



	public static Warp getByName(String name){
		for(Warp warp : Main.warps) if(warp.name.equalsIgnoreCase(name)) return warp;
		return null;
	}

	
}
