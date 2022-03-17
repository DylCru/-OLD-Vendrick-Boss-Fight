package me.crazyrain.vendrickbossfight.attacks;

import me.crazyrain.vendrickbossfight.VendrickBossFight;
import me.crazyrain.vendrickbossfight.functionality.ItemManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class EnergyRifle implements Listener {

    VendrickBossFight plugin;
    public EnergyRifle(VendrickBossFight plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onRifleRightClick(PlayerInteractEvent e) {
        if (!e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            return;
        }

        if (e.getPlayer().getEquipment().getItemInMainHand().equals(ItemManager.energyRifle)) {

            List<EntityType> safeMobs = new ArrayList<>();
            for (String s : plugin.getConfig().getStringList("rifle-safe")){
                safeMobs.add(EntityType.valueOf(s));
            }

            Player player = e.getPlayer();
            Vector direction = player.getLocation().getDirection().normalize();

            if (player.getFoodLevel() - 2 >= 0) {
                player.setFoodLevel(player.getFoodLevel() - 2);
                String message = ChatColor.GREEN + "Used " + ChatColor.GOLD + "" + ChatColor.BOLD + "Pulse Shot";
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
            } else {
                String message = ChatColor.RED + "" + ChatColor.BOLD + "NOT ENOUGH HUNGER";
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
                return;
            }


            ArmorStand pulse = (ArmorStand) player.getLocation().getWorld().spawnEntity(player.getLocation().add(direction), EntityType.ARMOR_STAND);
            pulse.setVisible(false);
            pulse.setSmall(true);
            pulse.getEquipment().setHelmet(new ItemStack(Material.BEACON));
            pulse.setMetadata("venPulse", new FixedMetadataValue(plugin, "venpulse"));
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
                        if (e instanceof Player || e instanceof ArmorStand || e instanceof ExperienceOrb) {
                            continue;
                        }

                        try {
                            if (safeMobs.contains(e.getType())){
                                continue;
                            }

                            LivingEntity en = (LivingEntity) e;
                            en.damage(20, player);
                            en.getWorld().strikeLightningEffect(en.getLocation());
                            player.sendMessage(ChatColor.DARK_GRAY + "Your Pulse Shot hit " + e.getName() + ChatColor.DARK_GRAY + " for 20 damage");
                            pulse.remove();
                            cancel();
                        } catch (ClassCastException ignored){}
                    }

                    count++;
                }
            }.runTaskTimer(plugin, 0, 1);
        }
    }
}
