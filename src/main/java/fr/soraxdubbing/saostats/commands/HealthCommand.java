package fr.soraxdubbing.saostats.commands;

import app.ashcon.intake.Command;
import app.ashcon.intake.bukkit.parametric.annotation.Sender;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public class HealthCommand {

    @Command(
            aliases = "set",
            desc = "Set the health boost of the item in your hand",
            perms = "SAOStats.health.set",
            usage = "[value]"
    )
    public void set(@Sender Player player, int health) {
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.getType().isAir()) {
            player.sendMessage("§cVous devez tenir un item en main !");
            return;
        }


        ItemMeta itemMeta = item.getItemMeta();
        try{
            AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), Attribute.GENERIC_MAX_HEALTH.name(), health, AttributeModifier.Operation.ADD_NUMBER);
            itemMeta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH,  modifier);
            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            item.setItemMeta(itemMeta);
            player.sendMessage("§aLe bonus de vie de l'item a été définie à " + health);
        }
        catch (IllegalArgumentException e){
            player.sendMessage("§cL'attribut n'existe pas !");
            return;
        }
    }

    @Command(
            aliases = "remove",
            desc = "Retirer le bonus de vie de l'item",
            perms = "SAOStats.health.remove",
            usage = "[value]"
    )
    public void remove(@Sender Player player, int health) {
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.getType().isAir()) {
            player.sendMessage("§cVous devez tenir un item en main !");
            return;
        }

        if(!item.hasItemMeta()){
            player.sendMessage("§cL'item n'a pas de métadonnées !");
            return;
        }

        ItemMeta itemMeta = item.getItemMeta();
        try{
            AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), Attribute.GENERIC_MAX_HEALTH.name(), -health, AttributeModifier.Operation.ADD_NUMBER);
            itemMeta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH,  modifier);
            item.setItemMeta(itemMeta);
            player.sendMessage("§aLe bonus de vie de " + health + " de l'item a été définie à ");
        }
        catch (IllegalArgumentException e){
            player.sendMessage("§cL'attribut n'existe pas !");
            return;
        }
    }
}
