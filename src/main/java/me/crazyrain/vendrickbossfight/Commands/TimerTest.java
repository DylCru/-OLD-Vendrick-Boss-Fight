package me.crazyrain.vendrickbossfight.Commands;

import me.crazyrain.vendrickbossfight.functionality.StatsTracker;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TimerTest implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        if (command.getLabel().equalsIgnoreCase("ventimer")){
            if (args[0].equalsIgnoreCase("start")){
                player.sendMessage(ChatColor.GREEN + "Timer Started!");
                StatsTracker.startTimer();
            }
            if (args[0].equalsIgnoreCase("stop")){
                player.sendMessage(ChatColor.RED + "Timer Stopped!");
                StatsTracker.stopTimer();
                player.sendMessage(ChatColor.GOLD + "The timer was on for " + StatsTracker.getTimerElapsed() + " seconds");
            }
            if (args[0].equalsIgnoreCase("time")){
                player.sendMessage(ChatColor.GOLD + "The timer has been on for " + StatsTracker.getTimerElapsed() + " seconds");
            }
        }


        return true;
    }
}
