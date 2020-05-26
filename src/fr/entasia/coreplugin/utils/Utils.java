package fr.entasia.coreplugin.utils;

import org.bukkit.Location;

public class Utils {

	public static Location getRoundedLocation(Location loc){
		return new Location(loc.getWorld(), loc.getBlockX()+0.5, loc.getBlockY()+0.5, loc.getBlockZ()+0.5);
	}

}
