package fr.soraxdubbing.saostats.commands;

import app.ashcon.intake.Command;
import app.ashcon.intake.bukkit.parametric.annotation.Sender;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.w3c.dom.Attr;

import java.util.UUID;

public class AttributesCommands {

        @Command(
                aliases = "add",
                desc = "Ajouter un attribut à l'item",
                perms = "SAOStats.attributes.add",
                usage = "[attribut] [valeur]"
        )
        public void add(@Sender Player sender, Attribute attribute, int value) {
            ItemStack item = sender.getInventory().getItemInMainHand();

            if (item.getType().isAir()) {
                sender.sendMessage("§cVous devez tenir un item en main !");
                return;
            }

            if(!item.hasItemMeta()){
                sender.sendMessage("§cL'item n'a pas de métadonnées !");
                return;
            }

            ItemMeta itemMeta = item.getItemMeta();
            try{
                // if item is armor
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
    public void remove(@Sender Player sender, Attribute attribute) {
        ItemStack item = sender.getInventory().getItemInMainHand();

        if (item.getType().isAir()) {
            sender.sendMessage("§cVous devez tenir un item en main !");
            return;
        }

        if(!item.hasItemMeta()){
            sender.sendMessage("§cL'item n'a pas de métadonnées !");
            return;
        }

        ItemMeta itemMeta = item.getItemMeta();
        try{
            itemMeta.removeAttributeModifier(attribute);
        }
        catch (IllegalArgumentException e){
            sender.sendMessage("§cL'attribut n'existe pas !");
            return;
        }
    }

}
