package me.crazyrain.vendrickbossfight.attacks;

import me.crazyrain.vendrickbossfight.VendrickBossFight;
import me.crazyrain.vendrickbossfight.npcs.Vendrick;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Objects;
import java.util.UUID;


public class PigBombs implements Listener {

    VendrickBossFight plugin;

    static Vendrick vendrick;
    List<UUID> players;

    public Pig pigBomb;

    public static Integer pigAmount = 4;
    public static boolean pigsDead = false;
    public Integer endAmount;

    public PigBombs(VendrickBossFight plugin){
        this.plugin = plugin;

    }

    public void init(Vendrick vendrick, List<UUID> players){
        PigBombs.vendrick = vendrick;
        this.players = players;
        pigAmount = 4;
        spawnPigs(PigBombs.vendrick.getVendrick().getLocation().add(0,4,0), this.players);
    }


    public void spawnPigs(Location loc, List<UUID> players){
        new BukkitRunnable(){

            @Override
            public void run() {
                for (int i = 0; i < 4; i++) {
                    pigBomb = (Pig) loc.getWorld().spawnEntity(loc, EntityType.PIG);
                    pigBomb.setHealth(1);
                    pigBomb.setCustomName(ChatColor.GOLD + "" + ChatColor.BOLD + "Pig Bomb");
                    pigBomb.setCustomNameVisible(true);
                    pigBomb.setAdult();
                    pigBomb.addPotionEffect(PotionEffectType.SLOW.createEffect(100000, 20));
                    pigBomb.setMetadata("PigBomb", new FixedMetadataValue(plugin, "pigbomb"));


                    Double rand = Math.random();
                    Vector pv = pigBomb.getLocation().getDirection();

                    switch (i){
                        case 0:
                            pv.setX(1);
                            pv.setZ(rand);
                            break;
                        case 1:
                            pv.setX(-1);
                            pv.setZ(rand);
                            break;
                        case 2:
                            pv.setZ(1);
                            pv.setX(-rand);
                            break;
                        case 3:
                            pv.setZ(-1);
                            pv.setX(-rand);
                            break;
                    }

                    pigBomb.setVelocity(pv);
                    loc.getWorld().playSound(loc, Sound.ENTITY_ENDER_DRAGON_FLAP, 5f, 1.5f);

                    for (UUID p : plugin.fighting){
                        damagePlayer(Bukkit.getPlayer(p), pigBomb);
                    }

                }
                countDown();
                vendrick.setSkipable();

                for (UUID p : plugin.fighting){
                    Bukkit.getPlayer(p).sendMessage(ChatColor.RED + "Vendrick threw " + ChatColor.GOLD + ChatColor.BOLD + "PIG BOMBS! " + ChatColor.RED + "Diffuse them quickly!");
                }

            }
        }.runTaskLater(plugin, 20 * 3);

    }

    public void countDown(){
        new BukkitRunnable(){

            @Override
            public void run() {
                if (!pigsDead){
                    vendrick.stopAttack();
                }
            }
        }.runTaskLater(plugin, 20 * 7);
    }

    @EventHandler
    public void onPigKill(EntityDeathEvent e){
        if (e.getEntity() instanceof Pig){
            if (e.getEntity().hasMetadata("PigBomb")){
                if (e.getEntity().getKiller() == null){
                    return;
                }
                pigAmount -= 1;

                if (pigAmount <= 0){
                    vendrick.stopAttack();
                    for (UUID p : plugin.fighting){
                        Bukkit.getPlayer(p).sendMessage(ChatColor.GREEN + "All the pig bombs were diffused!");
                        Bukkit.getPlayer(p).playSound(Bukkit.getPlayer(p).getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10 , 1f);
                    }
                    pigsDead = true;
                }
            }
        }
    }

    @EventHandler
    public void onArrowHit(ProjectileHitEvent e){
        if (e.getHitEntity() == null){
            return;
        }

        if (e.getHitEntity().hasMetadata("PigBomb")){
            e.getHitEntity().getWorld().spawnParticle(Particle.FIREWORKS_SPARK, e.getHitEntity().getLocation(), 20);
            e.getHitEntity().getWorld().playSound(e.getHitEntity().getLocation(), Sound.BLOCK_ANVIL_LAND, 1.0f, 2.0f);
        }
    }

    public void damagePlayer(Player player, LivingEntity e){
        new BukkitRunnable(){

            @Override
            public void run() {
                if (!pigsDead){
                    if (e.getHealth() > 0){
                        for (UUID id : plugin.fighting){
                            Bukkit.getPlayer(id).getLocation().getWorld().spawnParticle(Particle.EXPLOSION_LARGE, player.getLocation(), 2);
                            Bukkit.getPlayer(id).playSound(Bukkit.getPlayer(id).getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 1);
                            Bukkit.getPlayer(id).sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "BOOM! " + ChatColor.RED + "A pig bomb exploded!");
                            Bukkit.getPlayer(id).damage(5 * pigAmount);
                        }
                        explode(e);
                    }
                }
            }
        }.runTaskLater(plugin, 20 * 7);

    }

    @EventHandler
    public void stopPigAttack(EntityDamageByEntityEvent e){
        if (!plugin.fighting.contains(e.getDamager().getUniqueId()) && !e.getDamager().isOp()){
            if (e.getEntity().hasMetadata("PigBomb")){
                e.setCancelled(true);
                e.getDamager().sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "The defusal process is too confusing for a soul as pure as yours");
            }
        }
    }

    public void explode(LivingEntity e){
        if (e.getHealth() > 0){
                    e.setHealth(0);
                    e.getLocation().getWorld().spawnParticle(Particle.EXPLOSION_LARGE, e.getLocation(), 1);
                }
    }

    @EventHandler
    public void stopFallDmg(EntityDamageEvent e){
        if (e.getEntity() instanceof Pig){
            if (e.getCause().equals(EntityDamageEvent.DamageCause.FALL)){
                if (e.getEntity().hasMetadata("PigBomb")){
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void stopDrops(EntityDeathEvent e){
        if (e.getEntity() instanceof Pig){
            if (e.getEntity().hasMetadata("PigBomb")){
                e.getDrops().clear();
            }
        }
    }

    public void skipAttack(){
        for (Entity e : vendrick.getVendrick().getNearbyEntities(30,30,30)){
            if (e.hasMetadata("PigBomb")){
                e.getWorld().spawnParticle(Particle.SPELL_WITCH, e.getLocation().clone().add(0,0.5,0), 10);
                e.remove();
            }
        }
        pigsDead = true;
        vendrick.stopAttack();
        for (UUID p : plugin.fighting){
            Bukkit.getPlayer(p).sendMessage(ChatColor.GREEN + "All the pig bombs were diffused!");
            Bukkit.getPlayer(p).playSound(Bukkit.getPlayer(p).getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10 , 1f);
        }
        vendrick.setSkipable();
    }
}
