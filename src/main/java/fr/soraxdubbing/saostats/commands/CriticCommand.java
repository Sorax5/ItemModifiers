package fr.soraxdubbing.saostats.commands;

import app.ashcon.intake.Command;
import app.ashcon.intake.bukkit.parametric.annotation.Sender;
import de.tr7zw.nbtapi.NBTItem;
import fr.soraxdubbing.saostats.ItemInformations;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CriticCommand {

    @Command(
            aliases = "damage",
            desc = "Set the damage of the item if it's a critic hit",
            perms = "SAOStats.critic.damage",
            usage = "[damage]"
    )
    public void damage(@Sender Player player, Double damage) {
        ItemStack item = player.getInventory().getItemInMainHand();
        NBTItem nbtItem = new NBTItem(item);

        ItemInformations itemInformations = new ItemInformations();
        if(nbtItem.hasKey("ItemInformations")){
            itemInformations = nbtItem.getObject("ItemInformations", ItemInformations.class);
        }

        if(itemInformations.hasDoubleAttribute("critic.damage")){
            itemInformations.removeDoubleAttribute("critic.damage");
        }
        itemInformations.addDoubleAttribute("critic.damage", damage);
        player.sendMessage("§aLe damage du coup critique de l'item a été défini à " + damage + " !");

        nbtItem.setObject("ItemInformations", itemInformations);
        nbtItem.getItem();
        player.getInventory().setItemInMainHand(nbtItem.getItem());
    }

    @Command(
            aliases = "chance",
            desc = "Set the chance of the item to do a critic hit",
            perms = "SAOStats.critic.chance",
            usage = "[chance]"
    )
    public void chance(@Sender Player player, Double chance) {
        ItemStack item = player.getInventory().getItemInMainHand();
        NBTItem nbtItem = new NBTItem(item);

        ItemInformations itemInformations = new ItemInformations();
        if(nbtItem.hasKey("ItemInformations")){
            itemInformations = nbtItem.getObject("ItemInformations", ItemInformations.class);
        }

        if(itemInformations.hasDoubleAttribute("critic.chance")){
            itemInformations.removeDoubleAttribute("critic.chance");
        }
        itemInformations.addDoubleAttribute("critic.chance", chance);
        player.sendMessage("§aLa chance du coup critique de l'item a été définie à " + chance + " !");

        nbtItem.setObject("ItemInformations", itemInformations);
        nbtItem.getItem();
        player.getInventory().setItemInMainHand(nbtItem.getItem());
    }
}
