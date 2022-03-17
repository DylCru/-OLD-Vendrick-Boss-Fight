package me.crazyrain.vendrickbossfight.distortions.flaming;


import me.crazyrain.vendrickbossfight.npcs.Vendrick;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class Inferno {

    Vendrick vendrick;
    List<UUID> fighting;

    public Inferno(Vendrick vendrick, List<UUID> fighting){
        this.vendrick = vendrick;
        this.fighting = fighting;
    }

    public void blast(){
        for (Entity e : vendrick.getVendrick().getNearbyEntities(6,4,6)){
            if (fighting.contains(e.getUniqueId())){
                Player player = (Player) e;
                player.damage(8);
                player.setFireTicks(80);
                player.setVelocity((player.getLocation().toVector().subtract(vendrick.getVendrick().getLocation().add(0,-1,0).toVector())).multiply(40).normalize());
            }
        }
    }
}
