package fr.entasia.coreplugin.utils;

import fr.entasia.coreplugin.Main;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.IOException;

public class Utils {

	public static Location getRoundedLocation(Location loc){
		return new Location(loc.getWorld(), loc.getBlockX()+0.5, loc.getBlockY()+0.5, loc.getBlockZ()+0.5);
	}

	public static String formatPlayerSuffix(Player p){
		if(Main.vaultChat==null)return p.getDisplayName();
		else return Main.vaultChat.getPlayerSuffix(p).replace("&", "§")+" "+p.getDisplayName();
	}

	public static void removeSpyer(String name){
		Main.spyers.remove(name);

		String[] a = new String[Main.spyers.size()];
		int i=0;
		for(String s : Main.spyers){
			a[i] = s;
			i++;
		}
		Main.dataconfig.set("spyers", a);

		try{
			Main.dataconfig.save(Main.datafile);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public static void addSpyer(String name){
		Main.spyers.add(name);

		String[] a = new String[Main.spyers.size()];
		int i=0;
		for(String s : Main.spyers){
			a[i] = s;
			i++;
		}
		Main.dataconfig.set("spyers", a);

		try{
			Main.dataconfig.save(Main.datafile);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
