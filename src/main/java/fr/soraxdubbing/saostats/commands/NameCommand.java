package fr.soraxdubbing.saostats.commands;

import app.ashcon.intake.Command;
import app.ashcon.intake.bukkit.parametric.annotation.Sender;
import app.ashcon.intake.parametric.annotation.Text;
import fr.soraxdubbing.saostats.module.annoted.Hand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class NameCommand {

    @Command(
            aliases = "set",
            desc = "Change le nom de l'item",
            perms = "SAOStats.custom.name.set",
            usage = "[nom]"
    )
    public void set(@Sender Player sender, @Hand ItemStack item, @Text String name) {
        ItemMeta itemMeta = getItemMeta(item);
        itemMeta.setDisplayName(replaceColor(name));
        item.setItemMeta(itemMeta);
        sender.sendMessage("§aLe nom de l'item a été changé en " + name);
    }

    @Command(
            aliases = "reset",
            desc = "Réinitialise le nom de l'item",
            perms = "SAOStats.custom.name.reset",
            usage = ""
    )
    public void reset(@Sender Player sender, @Hand ItemStack item){
        ItemMeta itemMeta = getItemMeta(item);
        itemMeta.setDisplayName(null);
        item.setItemMeta(itemMeta);
        sender.sendMessage("§aLe nom de l'item a été réinitialisé");
    }

    private ItemMeta getItemMeta(ItemStack item){
        ItemMeta itemMeta = item.getItemMeta();
        if(itemMeta == null){
            itemMeta = Bukkit.getItemFactory().getItemMeta(item.getType());
        }
        return itemMeta;
    }

    private String replaceColor(String message){
        return message.replaceAll("&", "§");
    }
}
