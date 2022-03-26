package me.crazyrain.vendrickbossfight.functionality;

import me.crazyrain.vendrickbossfight.VendrickBossFight;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.time.StopWatch;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.sql.Time;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class StatsTracker {

    private static HashMap<UUID, Integer> playerDamage = new HashMap<>();
    private static int attackCount;
    private static StopWatch fightTimer = new StopWatch();
    private static List<String> attackList = new ArrayList<>();

    private static List<UUID> players = new ArrayList<>();
    private static List<UUID> lost = new ArrayList<>();

    private static boolean viewExpired = true;

    /////////////////////////////////////

    public static List<String> getAttackList() {
        return attackList;
    }
    public static void addAttack(int phase){
        // 1: Wraiths, 2: Pig bombs, 3: The horde,
        switch (phase){
            case 1:
                attackList.add(ChatColor.DARK_PURPLE + "Eternal Wraiths");
                break;
            case 2:
                attackList.add(ChatColor.GOLD + "Pig Bombs");
                break;
            case 3:
                attackList.add(ChatColor.DARK_GREEN + "The Horde");
                break;
            case 4:
                attackList.add(ChatColor.GOLD + "Inferno");
                break;
            case 5:
                attackList.add(ChatColor.DARK_BLUE + "Tsunami");
                break;
            case 6:
                attackList.add(ChatColor.DARK_AQUA + "Lightning Storm");
                break;
        }
    }

    public static List<UUID> getPlayers() {
        return players;
    }
    public static void addPlayer(UUID id){
        players.add(id);
    }
    public static int getPlayerCount(){
        return players.size();
    }

    public static List<UUID> getLost() {
        return lost;
    }
    public static void addToLost(UUID id){
        lost.add(id);
    }

    public static void reset(){
        playerDamage.clear();
        players.clear();
        lost.clear();
        attackList.clear();
        fightTimer.reset();

        viewExpired = true;
    }

    public static boolean isViewExpired() {
        return viewExpired;
    }

    public static void setViewExpired(boolean viewInvalid) {
        StatsTracker.viewExpired = viewInvalid;
    }


    public static void addDamage(UUID player, int damage){
        if (!playerDamage.containsKey(player)){
            playerDamage.put(player, damage);
            return;
        }
        int newDamage = damage += playerDamage.get(player);
        playerDamage.replace(player, newDamage);
    }

    public static float getDamageFromPlayer(UUID player){
        try {
            return playerDamage.get(player);
        } catch (NullPointerException e){
            return 0;
        }
    }
    public static HashMap<UUID, Integer> getPlayerDamage(){
        return playerDamage;
    }

    public static void increaseAttackCount(){
        attackCount++;
    }
    public static int getAttackCount() {
        return attackCount;
    }

    public static void startTimer(){
        fightTimer.reset();
        fightTimer.start();
    }
    public static void stopTimer(){
        try {
            fightTimer.stop();
        } catch (IllegalStateException ignored) {}
    }
    public static String getTimerElapsed(){
        int mins = 0;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(fightTimer.getTime());
        while (seconds >= 60){
            seconds -= 60;
            mins += 1;
        }
        String time = mins + " Minute(s) " + seconds + " Seconds";

        return time;
    }

    public static TreeMap<Integer, UUID> sortPlayerDamage(){
        HashMap<Integer, UUID> sorted = new HashMap<>();
        for (UUID id : playerDamage.keySet()){
            sorted.put(playerDamage.get(id), id);
        }
        TreeMap<Integer, UUID> sortedDamage = new TreeMap<>(sorted);
        return sortedDamage;
    }

    public static int getPlayerPosition(UUID pID, int playerSize){
        TreeMap<Integer, UUID> sortedDamage = sortPlayerDamage();
        int i = 0;
        for (UUID id : sortedDamage.values()){
            if (sortedDamage.get(playerDamage.get(id)) == pID){
                return (playerSize - i); //done because Treemap sorts from low to high
            }
            i++;
        }

        return 0;
    }

    public static UUID getPlayerAtPostion(int pos){
        TreeMap<Integer, UUID> sortedDamage = sortPlayerDamage();
        int i = 0;
        for (UUID pID : sortedDamage.values()){
            if (i == sortedDamage.size() - pos){
                return pID;
            }
            i++;
        }
        Bukkit.broadcastMessage(ChatColor.BOLD + "NULL RETURNED");
        return null;
    }

}
