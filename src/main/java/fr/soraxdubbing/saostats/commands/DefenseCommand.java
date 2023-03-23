package fr.soraxdubbing.saostats.commands;

import app.ashcon.intake.Command;
import app.ashcon.intake.bukkit.parametric.annotation.Sender;
import de.tr7zw.nbtapi.NBTItem;
import fr.soraxdubbing.saostats.ItemInformations;
import fr.soraxdubbing.saostats.module.annoted.Hand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DefenseCommand {

    @Command(
            aliases = "set",
            desc = "Set the defense of the item in your hand",
            perms = "SAOStats.defense",
            usage = "[defense] [value]"
    )
    public void set(@Sender Player player, @Hand ItemStack item, Double defense) {
        NBTItem nbtItem = new NBTItem(item);

        ItemInformations itemInformations = new ItemInformations();
        if(nbtItem.hasKey("ItemInformations")){
            itemInformations = nbtItem.getObject("ItemInformations", ItemInformations.class);
        }

        if(itemInformations.hasDoubleAttribute("defense")){
            itemInformations.removeDoubleAttribute("defense");
        }
        itemInformations.addDoubleAttribute("defense", defense);
        player.sendMessage("§aLa défense de l'item a été définie à " + defense);

        nbtItem.setObject("ItemInformations", itemInformations);
        nbtItem.getItem();
        player.getInventory().setItemInMainHand(nbtItem.getItem());

    }

    @Command(
            aliases = "add",
            desc = "Add the defense of the item in your hand",
            perms = "SAOStats.stats.defense.add",
            usage = "[defense]"
    )
    public void add(@Sender Player player, @Hand ItemStack item, Double defense){
        NBTItem nbtItem = new NBTItem(item);

        ItemInformations itemInformations = new ItemInformations();
        if(nbtItem.hasKey("ItemInformations")){
            itemInformations = nbtItem.getObject("ItemInformations", ItemInformations.class);
        }

        if(itemInformations.hasDoubleAttribute("defense")){
            Double actualDefense = itemInformations.getDoubleAttribute("defense");
            defense += actualDefense;
            itemInformations.removeDoubleAttribute("defense");
            itemInformations.addDoubleAttribute("defense", defense);
            player.sendMessage("§aLa défense de l'item a été augmentée de " + defense);
        } else {
            itemInformations.addDoubleAttribute("defense", defense);
            player.sendMessage("§aLa défense de l'item a été définie à " + defense);
        }
    }

    @Command(
            aliases = "remove",
            desc = "Remove the defense of the item in your hand",
            perms = "SAOStats.stats.defense.remove",
            usage = "[defense]"
    )
    public void remove(@Sender Player player, @Hand ItemStack item, Double defense){
        NBTItem nbtItem = new NBTItem(item);

        ItemInformations itemInformations = new ItemInformations();
        if(nbtItem.hasKey("ItemInformations")){
            itemInformations = nbtItem.getObject("ItemInformations", ItemInformations.class);
        }

        if(itemInformations.hasDoubleAttribute("defense")){
            Double actualDefense = itemInformations.getDoubleAttribute("defense");
            defense -= actualDefense;
            itemInformations.removeDoubleAttribute("defense");
            itemInformations.addDoubleAttribute("defense", defense);
            player.sendMessage("§aLa défense de l'item a été diminuée de " + defense);
        } else {
            itemInformations.addDoubleAttribute("defense", defense);
            player.sendMessage("§aLa défense de l'item a été définie à " + defense);
        }
    }

    @Command(
            aliases = "reset",
            desc = "Reset the defense of the item in your hand",
            perms = "SAOStats.stats.defense.reset",
            usage = "reset"
    )
    public void reset(@Sender Player player, @Hand ItemStack item) {
        NBTItem nbtItem = new NBTItem(item);

        ItemInformations itemInformations = new ItemInformations();
        if(nbtItem.hasKey("ItemInformations")){
            itemInformations = nbtItem.getObject("ItemInformations", ItemInformations.class);
        }

        if(itemInformations.hasDoubleAttribute("defense")){
            itemInformations.removeDoubleAttribute("defense");
            player.sendMessage("§aLa défense de l'item a été réinitialisée");
        } else {
            player.sendMessage("§cL'item n'a pas de défense");
        }

        nbtItem.setObject("ItemInformations", itemInformations);
        player.getInventory().setItemInMainHand(nbtItem.getItem());

    }
}
