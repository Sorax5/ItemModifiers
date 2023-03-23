package fr.soraxdubbing.saostats.commands;

import app.ashcon.intake.Command;
import app.ashcon.intake.bukkit.parametric.annotation.Sender;
import de.tr7zw.nbtapi.NBTItem;
import fr.soraxdubbing.saostats.SAOStats;
import fr.soraxdubbing.saostats.enchant.Glow;
import fr.soraxdubbing.saostats.module.annoted.Hand;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GlowingCommand {
    @Command(
            aliases = "set",
            desc = "Set the glowing of an item",
            perms = "SAOStats.custom.glowing.set",
            usage = "[true/false]"
    )
    public void set(@Sender Player player, @Hand ItemStack item, Boolean glowing) {
        // faire en sorte que l'item a un effet de glowing comme les enchantement mais sans les enchantement
        ItemMeta itemMeta = getItemMeta(item);

        if(glowing){
            itemMeta.addEnchant(new Glow(new NamespacedKey(SAOStats.getInstance(),"glow")), 1, true);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            player.sendMessage("§aL'item a été défini à " + glowing);
        }
        else{
            itemMeta.removeEnchant(new Glow(new NamespacedKey(SAOStats.getInstance(),"glow")));
        }

        item.setItemMeta(itemMeta);
    }

    private ItemMeta getItemMeta(ItemStack item){
        ItemMeta itemMeta = item.getItemMeta();
        if(itemMeta == null){
            itemMeta = Bukkit.getItemFactory().getItemMeta(item.getType());
        }
        return itemMeta;
    }

}
