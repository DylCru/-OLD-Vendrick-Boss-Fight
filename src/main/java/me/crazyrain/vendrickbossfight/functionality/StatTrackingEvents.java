package me.crazyrain.vendrickbossfight.functionality;

import me.crazyrain.vendrickbossfight.CustomEvents.VendrickFightStartEvent;
import me.crazyrain.vendrickbossfight.CustomEvents.VendrickFightStopEvent;
import me.crazyrain.vendrickbossfight.CustomEvents.VendrickStartAttackEvent;
import me.crazyrain.vendrickbossfight.VendrickBossFight;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;
import java.util.logging.Level;


public class StatTrackingEvents implements Listener {

    @EventHandler (priority = EventPriority.HIGHEST)
    public void getFinalVendrickDamage(EntityDamageByEntityEvent e){
        if (VendrickBossFight.plugin.venSpawned){
            if (e.getDamager() instanceof Player){
                if (e.getEntity().hasMetadata("Vendrick")){
                    Player player = (Player) e.getDamager();
                    if (VendrickBossFight.plugin.fighting.contains(player.getUniqueId())){
                        StatsTracker.addDamage(player.getUniqueId(), (int) e.getDamage());
                    }
                }
            }
        }
    }

    @EventHandler
    public void onFightStart(VendrickFightStartEvent e){
        VendrickBossFight.plugin.getLogger().log(Level.INFO, "Vendrick spawned! The fight's stats are being tracked.");
        for (UUID id : e.getPlayers()){
            StatsTracker.addPlayer(id);
            StatsTracker.addDamage(id, 0);
        }
        StatsTracker.startTimer();
    }
    @EventHandler
    public void onFightEnd(VendrickFightStopEvent e){
        StatsTracker.stopTimer();
        StatsTracker.setViewExpired(false);

        if (e.getLosers().size() > 0){
            for (UUID id : e.getLosers()){
                StatsTracker.addToLost(id);
            }
        }
        for (UUID pID : e.getPlayers()){
            new BukkitRunnable(){
                @Override
                public void run() {
                    TextComponent message = new TextComponent();
                    message.setText(ChatColor.GREEN + "Click "  + ChatColor.GOLD + "" + ChatColor.BOLD + "HERE"  + ChatColor.GREEN + " to see the stats from that fight!");
                    message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/venfight showstats"));

                    Bukkit.getPlayer(pID).spigot().sendMessage(message);
                }
            }.runTaskLater(VendrickBossFight.plugin, 20 * 5);
        }
        new BukkitRunnable(){
            @Override
            public void run() {
                StatsTracker.setViewExpired(true);
                StatsTracker.reset();
            }
        }.runTaskLater(VendrickBossFight.plugin, 20 * 8);
    }

    @EventHandler
    public void onAttackStart(VendrickStartAttackEvent e){
        StatsTracker.addAttack(e.getPhase());
    }
}
