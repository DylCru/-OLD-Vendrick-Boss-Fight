package me.crazyrain.vendrickbossfight.distortions.stormy;

import me.crazyrain.vendrickbossfight.VendrickBossFight;
import me.crazyrain.vendrickbossfight.functionality.AttackCharge;
import me.crazyrain.vendrickbossfight.functionality.ItemManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.UUID;

public class StormyEvents implements Listener {

    VendrickBossFight plugin;

    public StormyEvents(VendrickBossFight plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onVendrickAttack(EntityDamageByEntityEvent e){
        if (e.getEntity().getScoreboardTags().contains("venStorm")){
            int chance = (int) (Math.random() * 17);

            if (chance >= 14){
                plugin.vendrick.startAttack(0);
                plugin.getServer().getPluginManager().callEvent(new VendrickStartAttackEvent(6));
                LightningStorm storm = new LightningStorm(plugin);
                for (UUID p : plugin.fighting){
                    AttackCharge charge = new AttackCharge(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Lightning Storm", Bukkit.getPlayer(p));
                }
                new BukkitRunnable(){
                    @Override
                    public void run() {
                        storm.movement();
                        storm.shootBalls(plugin.vendrick.getVendrick().getLocation().getDirection().normalize(), plugin.vendrick.getVendrick().getLocation());
                    }
                }.runTaskLater(plugin, 20 * 3);
            }
        }
    }

    @EventHandler
    public void shootBallLightning(PlayerInteractEvent e){
        if (!e.getAction().equals(Action.RIGHT_CLICK_AIR)){
            return;
        }
        Player player = e.getPlayer();
        if (e.getPlayer().getEquipment().getItemInMainHand().equals(ItemManager.ballLightning)){
            Vector direction = player.getLocation().getDirection().normalize();
            e.getPlayer().getEquipment().getItemInMainHand().setAmount(0);
            e.getPlayer().updateInventory();

            ArmorStand pulse = (ArmorStand) player.getLocation().getWorld().spawnEntity(player.getLocation().add(direction), EntityType.ARMOR_STAND);
            pulse.setVisible(false);
            pulse.setSmall(true);
            pulse.getEquipment().setHelmet(new ItemStack(Material.BLUE_ICE));
            pulse.setMetadata("venBall", new FixedMetadataValue(plugin, "venball"));
            pulse.setVelocity(direction);


            new BukkitRunnable() {
                int count = 0;

                @Override
                public void run() {
                    if (count == 10) {
                        pulse.remove();
                        cancel();
                    }
                    pulse.setVelocity(direction);

                    for (Entity e : pulse.getNearbyEntities(0.2, 0.2, 0.2)) {
                        if (e instanceof Player || e instanceof ArmorStand) {
                            continue;
                        }
                        try {
                            LivingEntity en = (LivingEntity) e;
                            if (en.hasMetadata("Vendrick")){;
                                en.getWorld().strikeLightningEffect(en.getLocation());
                                player.sendMessage(ChatColor.DARK_GRAY + "You hit Vendrick!");
                                pulse.remove();

                                plugin.hurricane.setDamage(plugin.hurricane.getDamage() - 1);
                                plugin.hurricane.setRadius(plugin.hurricane.getRadius() - 0.5f);
                                for (UUID id : plugin.fighting) {
                                    Bukkit.getPlayer(id).sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "BOOM!" + ChatColor.GREEN +
                                            " Vendrick was hit by ball lightning! The power of his storm has been reduced.");
                                }

                                cancel();
                            }
                        } catch (ClassCastException ignored){}
                    }

                    count++;
                }
            }.runTaskTimer(plugin, 0, 1);
        }
    }
}
