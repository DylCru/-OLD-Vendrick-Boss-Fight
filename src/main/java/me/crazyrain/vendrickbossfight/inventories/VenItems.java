package me.crazyrain.vendrickbossfight.inventories;

import me.crazyrain.vendrickbossfight.functionality.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class VenItems implements InventoryHolder{

    private Inventory inv;

    public VenItems(){
        inv = Bukkit.createInventory(this, 54 ,"Vendrick items");
        init();
    }

    private void init(){
        for (int i = 0; i < 54; i++){
            inv.setItem(i, createItem(ChatColor.GOLD + "" + ChatColor.BOLD + "Click" + ChatColor.GREEN + " any item to put it in your inventory", Material.BLACK_STAINED_GLASS_PANE, null));
        }

        inv.setItem(10, ItemManager.eternalStar);
        inv.setItem(11, ItemManager.flamingStar);
        inv.setItem(12, ItemManager.tidalStar);
        inv.setItem(13, ItemManager.stormStar);

        inv.setItem(14, ItemManager.eternalFragment);
        inv.setItem(15, ItemManager.vendrickHatchet);
        inv.setItem(16, ItemManager.trueEternalHatchet);

        inv.setItem(19, ItemManager.shatterStick);
        inv.setItem(20, ItemManager.shatterSpine);
        inv.setItem(21, ItemManager.essenceOfEternity);

        inv.setItem(22, ItemManager.nutrimentOfTheInfinite);
        inv.setItem(23, ItemManager.pieCrust);
        inv.setItem(24, ItemManager.lusciousApple);
        inv.setItem(25, ItemManager.oven);
        inv.setItem(28, ItemManager.nutrimentU);

        inv.setItem(29, ItemManager.theCatalyst);
        inv.setItem(30, ItemManager.flameCore);
        inv.setItem(31, ItemManager.waveCore);
        inv.setItem(32, ItemManager.voltaicCore);
        inv.setItem(33, ItemManager.volatileStar);
        inv.setItem(34, ItemManager.fusionChamber);
        inv.setItem(37, ItemManager.unchargedRifle);
        inv.setItem(38, ItemManager.energyRifle);
        inv.setItem(39, ItemManager.catalystPartA);
        inv.setItem(40, ItemManager.catalystPartB);
        inv.setItem(41, ItemManager.infinium);


    }

    private ItemStack createItem(String name, Material mat, List<String> lore){
        ItemStack item = new ItemStack(mat, 1);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Choose" + ChatColor.GREEN + " an item!");
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }


    @Override
    public Inventory getInventory() {
        return inv;
    }
}
