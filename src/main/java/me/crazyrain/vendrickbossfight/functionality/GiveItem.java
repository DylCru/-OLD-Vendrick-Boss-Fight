package me.crazyrain.vendrickbossfight.functionality;

import me.crazyrain.vendrickbossfight.inventories.VenItems;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.PlayerInventory;

public class GiveItem implements Listener {

    @EventHandler
    public void giveitem(InventoryClickEvent e){
        if (e.getClickedInventory() == null){
            return;
        }

        if (e.getClickedInventory().getType().equals(InventoryType.PLAYER) && e.getView().getTopInventory().getHolder() instanceof VenItems){
            e.setCancelled(true);
            return;
        }

        if (e.getView().getTopInventory().getHolder() instanceof VenItems){
            if (e.getCurrentItem().getType().equals(Material.RED_STAINED_GLASS_PANE) || e.getCurrentItem().getType().equals(Material.BLUE_STAINED_GLASS_PANE) || e.getCurrentItem().getType().equals(Material.BLACK_STAINED_GLASS_PANE)){
                e.setCancelled(true);
                return;
            }

            if (e.getCurrentItem() != null){
                e.setCancelled(true);
                Player player = (Player) e.getWhoClicked();
                player.getInventory().addItem(e.getCurrentItem());
            }
        }
    }
}
