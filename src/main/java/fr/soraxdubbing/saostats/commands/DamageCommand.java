package fr.soraxdubbing.saostats.commands;

import app.ashcon.intake.Command;
import app.ashcon.intake.bukkit.parametric.annotation.Sender;
import de.tr7zw.nbtapi.NBTItem;
import fr.soraxdubbing.saostats.ItemInformations;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DamageCommand {

    @Command(
            aliases = "max",
            desc = "Set the max damage of an item",
            perms = "SAOStats.damage.max",
            usage = "[max]"
    )
    public void max(@Sender Player player, Double max) {
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.getType().isAir()) {
            player.sendMessage("§cVous devez tenir un item en main !");
            return;
        }

        NBTItem nbtItem = new NBTItem(item);

        ItemInformations itemInformations = new ItemInformations();
        if(nbtItem.hasKey("ItemInformations")){
            itemInformations = nbtItem.getObject("ItemInformations", ItemInformations.class);
        }

        if(itemInformations.hasDoubleAttribute("damage.max")){
            itemInformations.removeDoubleAttribute("damage.max");
        }
        itemInformations.addDoubleAttribute("damage.max", max);
        player.sendMessage("§aVous avez défini le max de dégâts de l'item en main à " + max);

        nbtItem.setObject("ItemInformations", itemInformations);
        nbtItem.getItem();
        player.getInventory().setItemInMainHand(nbtItem.getItem());
    }

    @Command(
            aliases = "min",
            desc = "Set the min damage of an item",
            perms = "SAOStats.damage.min",
            usage = "[min]"
    )
    public void min(@Sender Player player, Double min) {
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.getType().isAir()) {
            player.sendMessage("§cVous devez tenir un item en main !");
            return;
        }

        NBTItem nbtItem = new NBTItem(item);

        ItemInformations itemInformations = new ItemInformations();
        if(nbtItem.hasKey("ItemInformations")){
            itemInformations = nbtItem.getObject("ItemInformations", ItemInformations.class);
        }

        if(itemInformations.hasDoubleAttribute("damage.min")){
            itemInformations.removeDoubleAttribute("damage.min");
        }

        itemInformations.addDoubleAttribute("damage.min", min);
        player.sendMessage("§aVous avez défini le min de dégâts de l'item en main à " + min);

        nbtItem.setObject("ItemInformations", itemInformations);
        nbtItem.getItem();
        player.getInventory().setItemInMainHand(nbtItem.getItem());
    }

    @Command(
            aliases = "set",
            desc = "Set the damage of an item",
            perms = "SAOStats.damage.set",
            usage = "[set] [value]"
    )
    public void set(@Sender Player player, Double set) {
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.getType().isAir()) {
            player.sendMessage("§cVous devez tenir un item en main !");
            return;
        }

        NBTItem nbtItem = new NBTItem(item);

        ItemInformations itemInformations = new ItemInformations();
        if(nbtItem.hasKey("ItemInformations")){
            itemInformations = nbtItem.getObject("ItemInformations", ItemInformations.class);
        }

        if(itemInformations.hasDoubleAttribute("damage.set")){
            itemInformations.removeDoubleAttribute("damage.set");
        }

        itemInformations.addDoubleAttribute("damage.set", set);
        player.sendMessage("§aVous avez défini les dégâts de l'item en main à " + set);

        nbtItem.setObject("ItemInformations", itemInformations);
        nbtItem.getItem();
        player.getInventory().setItemInMainHand(nbtItem.getItem());
    }
}
