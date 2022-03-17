package me.crazyrain.vendrickbossfight.inventories;

import com.google.common.math.Stats;
import me.crazyrain.vendrickbossfight.functionality.StatsTracker;
import org.apache.commons.lang.ObjectUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FightStatsGUI implements InventoryHolder {

    private Inventory inv;
    private UUID player;
    private List<String> lore = new ArrayList<>();
    private List<UUID> winners;
    private List<UUID> losers;

    public FightStatsGUI(UUID player, List<UUID> winners, List<UUID> losers){
        inv = Bukkit.createInventory(this, 27, "Your stats from the recent fight");
        this.player = player;
        this.winners = winners;
        this.losers = losers;
        init();
    }

    public void init(){
        for (int i = 0; i < 27; i++){
            inv.setItem(i, createItem(null, Material.BLACK_STAINED_GLASS_PANE, null));
        }

        //General Fight stats (Time, amount of winners and losers)
        lore.add(ChatColor.GRAY + "Fight Duration: " + ChatColor.GOLD + StatsTracker.getTimerElapsed());
        lore.add("" + ChatColor.GREEN + winners.size() + " Players won!");
        lore.add("" + ChatColor.RED + losers.size() + " Players Lost!");
        inv.setItem(4, createItem(ChatColor.GOLD + "" + ChatColor.BOLD + "General Stats", Material.MAP, lore));
        lore.clear();

        //Player's stats (Their damage)
        lore.add(ChatColor.GRAY + "Damage Dealt: " + ChatColor.GOLD + StatsTracker.getDamageFromPlayer(player));
        lore.add(ChatColor.GRAY + "Your position: " + ChatColor.GOLD + StatsTracker.getPlayerPosition(player, (winners.size() + losers.size())));
        inv.setItem(10, createItem( ChatColor.AQUA +  "" + Bukkit.getPlayer(player).getDisplayName() + "'s Stats", Material.PLAYER_HEAD, lore));
        lore.clear();

        //1st place damager (Name, damage)
        UUID pos1 = StatsTracker.getPlayerAtPostion(1);
        try {
            lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + Bukkit.getPlayer(pos1).getDisplayName());
        } catch (NullPointerException e){
            lore.add(ChatColor.RED + "Couldn't get the player in this position!");
        }
        lore.add(ChatColor.GOLD + "Damage Dealt: " + StatsTracker.getDamageFromPlayer(pos1));
        inv.setItem(13, createItem(ChatColor.GOLD + "" + ChatColor.BOLD + "1st Damager", Material.GOLD_BLOCK, lore));
        lore.clear();

        if (winners.size() == 2){

            UUID pos2 = StatsTracker.getPlayerAtPostion(2);
            try {
                lore.add(ChatColor.GRAY + "" + ChatColor.BOLD + Bukkit.getPlayer(pos2).getDisplayName());
            } catch (NullPointerException e){
                lore.add(ChatColor.RED + "Couldn't get the player in this position!");
            }
            lore.add(ChatColor.GRAY + "Damage Dealt: " + StatsTracker.getDamageFromPlayer(pos2));
            inv.setItem(12, createItem(ChatColor.GRAY + "" + ChatColor.BOLD + "2nd Damager", Material.IRON_BLOCK, lore));
            lore.clear();
        }

        if (winners.size() >= 3){
            //2nd place damager (Name, damage)
            UUID pos2 = StatsTracker.getPlayerAtPostion(2);
            try {
                lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + Bukkit.getPlayer(pos2).getDisplayName());
            } catch (NullPointerException e){
                lore.add(ChatColor.RED + "Couldn't get the player in this position!");
            }
            lore.add(ChatColor.GRAY + "Damage Dealt: " + StatsTracker.getDamageFromPlayer(pos2));
            inv.setItem(12, createItem(ChatColor.GRAY + "" + ChatColor.BOLD + "2nd Damager", Material.IRON_BLOCK, lore));
            lore.clear();

            //3rd place damager (Name, damage)
            UUID pos3 = StatsTracker.getPlayerAtPostion(3);
            try {
                lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + Bukkit.getPlayer(pos3).getDisplayName());
            } catch (NullPointerException e){
                lore.add(ChatColor.RED + "Couldn't get the player in this position!");
            }
            lore.add(ChatColor.GRAY + "Damage Dealt: " + StatsTracker.getDamageFromPlayer(pos3));
            inv.setItem(14, createItem(ChatColor.GRAY + "" + ChatColor.BOLD + "3rd Damager", Material.COPPER_BLOCK, lore));
            lore.clear();
        }

        lore.add(ChatColor.GRAY + "Attack Count: " + StatsTracker.getAttackList().size());
        lore.add(ChatColor.GRAY + "Attacks Used: ");
        if (StatsTracker.getAttackList().size() == 0){
            lore.add(ChatColor.RED + "Vendrick didn't use a single attack! Weird.");
        } else {
            lore.addAll(StatsTracker.getAttackList());
        }

        inv.setItem(16, createItem(ChatColor.RED + "" + "Vendrick's Stats", Material.NETHERITE_AXE, lore));
        lore.clear();

    }

    private ItemStack createItem(String name, Material mat, List<String> lore){
        ItemStack item = new ItemStack(mat, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(name);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }
}
