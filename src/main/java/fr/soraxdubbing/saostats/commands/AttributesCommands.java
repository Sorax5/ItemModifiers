package fr.soraxdubbing.saostats.commands;

import app.ashcon.intake.Command;
import app.ashcon.intake.bukkit.parametric.annotation.Sender;
import fr.soraxdubbing.saostats.module.annoted.Hand;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public class AttributesCommands {

        @Command(
                aliases = "add",
                desc = "Ajouter un attribut à l'item",
                perms = "SAOStats.attributes.add",
                usage = "[attribut] [valeur]"
        )
        public void add(@Sender Player sender, @Hand ItemStack item, Attribute attribute, int value) {
            ItemMeta itemMeta = getItemMeta(item);
            try{
                AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), attribute.name(), value, AttributeModifier.Operation.ADD_NUMBER);
                itemMeta.addAttributeModifier(attribute,  modifier);
                item.setItemMeta(itemMeta);
                sender.sendMessage("§aL'attribut " + attribute.name() + " a été ajouté à l'item avec une valeur de " + value);
            }
            catch (IllegalArgumentException e){
                sender.sendMessage("§cL'attribut n'existe pas !");
                return;
            }
        }

        @Command(
                aliases = "remove",
                desc = "Retirer un attribut à l'item",
                perms = "SAOStats.attributes.remove",
                usage = "[attribut]"
        )
    public void remove(@Sender Player sender, @Hand ItemStack item, Attribute attribute) {
        ItemMeta itemMeta = getItemMeta(item);
        try{
            itemMeta.removeAttributeModifier(attribute);
        }
        catch (IllegalArgumentException e){
            sender.sendMessage("§cL'attribut n'existe pas !");
            return;
        }
        item.setItemMeta(itemMeta);
        sender.sendMessage("§aL'attribut " + attribute.name() + " a été retiré de l'item");
    }

    private ItemMeta getItemMeta(ItemStack item){
        ItemMeta itemMeta = item.getItemMeta();
        if(itemMeta == null){
            itemMeta = Bukkit.getItemFactory().getItemMeta(item.getType());
        }
        return itemMeta;
    }
}
