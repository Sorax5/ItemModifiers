package fr.soraxdubbing.saostats.commands;

import app.ashcon.intake.Command;
import app.ashcon.intake.bukkit.parametric.annotation.Sender;
import app.ashcon.intake.parametric.annotation.Maybe;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EnchantCommand {

    @Command(
            aliases = "add",
            desc = "Add an enchantment to the item in your hand",
            perms = "SAOStats.enchant.add",
            usage = "[enchants] [level]"
    )
    public void defense(@Sender Player player, String enchantment, Integer level) {
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.getType().isAir()) {
            player.sendMessage("§cVous devez tenir un item en main !");
            return;
        }

        ItemMeta itemMeta = item.getItemMeta();
        try{
            itemMeta.addEnchant(Enchantment.getByName(enchantment),level,true);
            player.sendMessage("§aL'enchante a été ajouté à l'item !");
        }
        catch (Exception e){
            player.sendMessage("§cL'enchante n'existe pas !");
        }
        item.setItemMeta(itemMeta);
    }
}
