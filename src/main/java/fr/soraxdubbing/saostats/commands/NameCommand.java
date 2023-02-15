package fr.soraxdubbing.saostats.commands;

import app.ashcon.intake.Command;
import app.ashcon.intake.bukkit.parametric.annotation.Sender;
import app.ashcon.intake.parametric.annotation.Text;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class NameCommand {

    @Command(
            aliases = "set",
            desc = "Set the name of the item in your hand",
            perms = "SAOStats.health.set",
            usage = "[value]"
    )
    public void set(@Sender Player player, @Text String name) {
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.getType().isAir()) {
            player.sendMessage("§cVous devez tenir un item en main !");
            return;
        }
        if (name.contains("&")) {
            name = name.replaceAll("&", "§");
        }

        ItemMeta itemMeta = item.getItemMeta();
        try{
            itemMeta.setDisplayName(name);
            itemMeta.setLocalizedName(name);
            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            item.setItemMeta(itemMeta);
            player.sendMessage("§aLe nom de l'item a été définie comme: " + name);
            player.getInventory().setItemInMainHand(item);
        }
        catch (IllegalArgumentException e){
            player.sendMessage("§cErreur dans le changement de nom");
            return;
        }
    }
}
