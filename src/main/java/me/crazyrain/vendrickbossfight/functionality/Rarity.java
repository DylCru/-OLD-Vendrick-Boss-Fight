package me.crazyrain.vendrickbossfight.functionality;

import org.bukkit.ChatColor;

public enum Rarity {
    RARE("&9Rare &8Tier"),
    EPIC("&5Epic &8Tier"),
    SPECIAL("&dSpecial &8Tier"),
    INSANE("&cInsane &8Tier");

    String text;
    Rarity(String text){
        this.text = text;
    }

    @Override
    public String toString(){
        return ChatColor.translateAlternateColorCodes('&', this.text);
    }
}
