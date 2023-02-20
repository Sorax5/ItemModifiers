package fr.soraxdubbing.saostats.commands;

import app.ashcon.intake.Command;
import app.ashcon.intake.bukkit.parametric.annotation.Sender;
import de.tr7zw.nbtapi.NBTItem;
import fr.soraxdubbing.saostats.ItemInformations;
import fr.soraxdubbing.saostats.module.annoted.Hand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;

public class PotionCommand {

    @Command(
            aliases = "add",
            desc = "Add a potion effect to the item in your hand",
            perms = "SAOStats.potion.add",
            usage = "[potion] [level]"
    )
    public void add(@Sender Player player, @Hand ItemStack item, PotionType potionType, int level) {
        NBTItem nbtItem = new NBTItem(item);

        ItemInformations itemInformations = new ItemInformations();
        if(nbtItem.hasKey("ItemInformations")){
            itemInformations = nbtItem.getObject("ItemInformations", ItemInformations.class);
        }

        try{
            //PotionType potion = PotionType.valueOf(potionType.toUpperCase());
            itemInformations.addPotion(potionType, level);
            player.sendMessage("§aL'effet §e" + potionType.name() + "§a a été ajouté à l'item !");
        }
        catch (IllegalArgumentException e){
            player.sendMessage("§cL'effet §e" + potionType + "§c n'existe pas !");
            return;
        }

        nbtItem.setObject("ItemInformations", itemInformations);
        nbtItem.getItem();
        player.getInventory().setItemInMainHand(nbtItem.getItem());
    }

    @Command(
            aliases = "remove",
            desc = "remove a potion effect to the item in your hand",
            perms = "SAOStats.potion.remove",
            usage = "[potion]"
    )
    public void remove(@Sender Player player, @Hand ItemStack item, String potionType) {
        NBTItem nbtItem = new NBTItem(item);

        ItemInformations itemInformations = new ItemInformations();
        if(nbtItem.hasKey("ItemInformations")){
            itemInformations = nbtItem.getObject("ItemInformations", ItemInformations.class);
        }

        try{
            PotionType potion = PotionType.valueOf(potionType.toUpperCase());
            if(itemInformations.hasPotion(potion)) {
                itemInformations.removePotion(potion);
                player.sendMessage("§aPotion retirée !");
            }
            else {
                player.sendMessage("§cL'item n'a pas cet effet !");
                return;
            }
        }
        catch (IllegalArgumentException e){
            player.sendMessage("§cL'effet §e" + potionType + "§c n'existe pas !");
            return;
        }

        nbtItem.setObject("ItemInformations", itemInformations);
        nbtItem.getItem();
        player.getInventory().setItemInMainHand(nbtItem.getItem());
    }
}
