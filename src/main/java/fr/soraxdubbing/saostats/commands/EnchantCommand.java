package fr.soraxdubbing.saostats.commands;

import app.ashcon.intake.Command;
import app.ashcon.intake.bukkit.parametric.annotation.Sender;
import fr.soraxdubbing.saostats.module.annoted.Hand;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EnchantCommand {

    @Command(
            aliases = "add",
            desc = "Add an enchantment to the item in your hand",
            perms = "SAOStats.enchant.add",
            usage = "[enchant] [level]"
    )
    public void add(@Sender Player player, @Hand ItemStack item, Enchantment enchant, Integer level) {
        ItemMeta itemMeta = getItemMeta(item);
        if(itemMeta.addEnchant(enchant,level,true)){
            player.sendMessage("§aL'enchantement a été ajouté à l'item !");
        }
        else{
            player.sendMessage("§cL'enchantement n'a pas pu être ajouté à l'item !");
        }
        item.setItemMeta(itemMeta);
    }

    @Command(
            aliases = "remove",
            desc = "Remove an enchantment to the item in your hand",
            perms = "SAOStats.enchant.remove",
            usage = "[enchant]"
    )
    public void remove(@Sender Player player, @Hand ItemStack item, Enchantment enchant) {
        ItemMeta itemMeta = getItemMeta(item);
        if(itemMeta.removeEnchant(enchant)){
            player.sendMessage("§aL'enchantement a été retiré de l'item !");
        }
        else{
            player.sendMessage("§cL'enchantement n'est pas présent sur l'item !");
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
