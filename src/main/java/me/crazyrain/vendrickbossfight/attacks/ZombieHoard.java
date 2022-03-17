package me.crazyrain.vendrickbossfight.attacks;

import me.crazyrain.vendrickbossfight.functionality.ItemManager;
import me.crazyrain.vendrickbossfight.VendrickBossFight;
import me.crazyrain.vendrickbossfight.npcs.Vendrick;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;
import java.util.UUID;

public class ZombieHoard implements Listener {

    VendrickBossFight plugin;

    public static Integer zombieAmount = 6;
    public static Integer spawned = 0;

    public Zombie venZombie;

    static Vendrick vendrick;

    public ZombieHoard(VendrickBossFight plugin) {
        this.plugin = plugin;
    }

    public void init(Vendrick vendrick){
        ZombieHoard.vendrick = vendrick;
        startAttack();
    }

    public void startAttack(){
        Location loc = new Location(vendrick.getVendrick().getWorld(), vendrick.getVendrick().getLocation().getBlockX(), vendrick.getVendrick().getLocation().getBlockY() + 4, vendrick.getVendrick().getLocation().getBlockZ());
        for (UUID id : plugin.fighting){
            Bukkit.getPlayer(id).sendMessage(ChatColor.RED + "Vendrick is using the power of " + ChatColor.DARK_GREEN  + "" + ChatColor.BOLD + "THE HORDE" + ChatColor.RED + " to protect himself!");
        }

        zombieAmount = 6;
        spawned = 0;

        spawnZombies(vendrick.getVendrick().getLocation());
    }


    public void spawnZombies(Location loc){
        spawned = 0;
        new BukkitRunnable(){

            @Override
            public void run() {
                    venZombie = (Husk) loc.getWorld().spawnEntity(loc.clone().add(0,3,0), EntityType.HUSK);
                    venZombie.setBaby();
                    loc.getWorld().playSound(loc, Sound.ENTITY_VEX_HURT , 1.0f, 1.0f);
                    venZombie.setCustomName(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Eternal Growth");
                    venZombie.getEquipment().setItemInMainHand(ItemManager.growthSword);
                    venZombie.setMetadata("Growth", new FixedMetadataValue(plugin, "growth"));

                    AttributeModifier modifier = new AttributeModifier(Objects.requireNonNull(venZombie.getCustomName()), 30, AttributeModifier.Operation.ADD_NUMBER);
                    venZombie.getAttribute(Attribute.GENERIC_MAX_HEALTH).addModifier(modifier);
                    venZombie.setHealth(50);

                    spawned += 1;
                if (spawned >= zombieAmount){
                    vendrick.setSkipable();
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0, 15);
    }


    @EventHandler
    public void onVenZombieKill(EntityDeathEvent e){
        if (e.getEntity() instanceof Zombie){
            if (e.getEntity().hasMetadata("Growth")){
                zombieAmount -= 1;

                if (zombieAmount <= 0){
                    vendrick.stopAttack();
                }
            }
        }
    }

    @EventHandler
    public void stopAttack(EntityDamageByEntityEvent e){
        if (e.getDamager() instanceof Projectile){
            return;
        }
        if (!plugin.fighting.contains(e.getDamager().getUniqueId()) && !e.getDamager().isOp()){
            if (e.getEntity().hasMetadata("Growth")){
                e.setCancelled(true);
                e.getDamager().sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "The fumes from this growth make a pure soul like yours be completely powerless against it");
            }
        }
    }

    @EventHandler
    public void stopFallDmg(EntityDamageEvent e){
        if (e.getCause().equals(EntityDamageEvent.DamageCause.FALL)){
            if (e.getEntity() instanceof Husk){
                if (e.getEntity().hasMetadata("Growth")){
                        e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void stopDrops(EntityDeathEvent e){
        if (e.getEntity() instanceof Husk){
            if (e.getEntity().hasMetadata("Growth")){
                e.getDrops().clear();
            }
        }
    }
}
