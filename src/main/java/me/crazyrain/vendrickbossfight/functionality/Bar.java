package me.crazyrain.vendrickbossfight.functionality;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.awt.*;
import java.util.UUID;

public class Bar {

    Player player;
    BossBar bar;

    public Bar(Player player){
        this.player = player;
        bar = Bukkit.createBossBar(ChatColor.DARK_RED + ""  + ChatColor.BOLD + "Vendrick", BarColor.RED, BarStyle.SEGMENTED_10);
    }

    public UUID getPlayer(){
        return player.getUniqueId();
    }

    public void add(){
        bar.addPlayer(player);
    }

    public void remove(){
        bar.removePlayer(player);
    }

    public void fill(Double progress){
        bar.setProgress(progress);
    }



}
