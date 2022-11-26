package fr.soraxdubbing.saostats.commands;

import app.ashcon.intake.Command;
import app.ashcon.intake.bukkit.parametric.annotation.Sender;
import de.tr7zw.nbtapi.NBTItem;
import fr.soraxdubbing.saostats.ItemInformations;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DurabilityCommand {

    @Command(
            aliases = "max",
            desc = "Définir la durabilité maximale de l'item",
            perms = "SAOStats.durability.max",
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

        if(itemInformations.hasDoubleAttribute("durability.max")){
            itemInformations.removeDoubleAttribute("durability.max");
        }
        itemInformations.addDoubleAttribute("durability.max", max);
        player.sendMessage("§aLa durabilité maximale de l'item a été définie à " + max);

        nbtItem.setObject("ItemInformations", itemInformations);
        nbtItem.getItem();
        player.getInventory().setItemInMainHand(nbtItem.getItem());
    }

    @Command(
            aliases = "actual",
            desc = "Définir la durabilité actuelle de l'item",
            perms = "SAOStats.durability.actual",
            usage = "[actual]"
    )
    public void actual(@Sender Player player, Double actual) {
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

        if(itemInformations.hasDoubleAttribute("durability.actual")){
            itemInformations.removeDoubleAttribute("durability.actual");
        }
        itemInformations.addDoubleAttribute("durability.actual", actual);
        player.sendMessage("§aLa durabilité de l'item a été définie à " + actual);

        nbtItem.setObject("ItemInformations", itemInformations);
        nbtItem.getItem();
        player.getInventory().setItemInMainHand(nbtItem.getItem());
    }
}
