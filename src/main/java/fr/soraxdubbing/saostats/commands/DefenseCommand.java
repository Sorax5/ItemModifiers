package fr.soraxdubbing.saostats.commands;

import app.ashcon.intake.Command;
import app.ashcon.intake.bukkit.parametric.annotation.Sender;
import de.tr7zw.nbtapi.NBTItem;
import fr.soraxdubbing.saostats.ItemInformations;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DefenseCommand {

    @Command(
            aliases = "defense",
            desc = "Set the defense of the item in your hand",
            perms = "SAOStats.defense",
            usage = "[defense]"
    )
    public void defense(@Sender Player player, Double defense) {
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

        if(itemInformations.hasDoubleAttribute("defense")){
            itemInformations.removeDoubleAttribute("defense");
        }
        itemInformations.addDoubleAttribute("defense", defense);
        player.sendMessage("§aLa défense de l'item a été définie à " + defense);

        nbtItem.setObject("ItemInformations", itemInformations);
        nbtItem.getItem();
        player.getInventory().setItemInMainHand(nbtItem.getItem());
    }
}
