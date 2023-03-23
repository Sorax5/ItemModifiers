package fr.soraxdubbing.saostats.commands;

import app.ashcon.intake.Command;
import app.ashcon.intake.bukkit.parametric.annotation.Sender;
import app.ashcon.intake.parametric.annotation.Maybe;
import de.tr7zw.nbtapi.NBTItem;
import fr.soraxdubbing.saostats.ItemInformations;
import fr.soraxdubbing.saostats.module.annoted.Hand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class DurabilityCommand {

    @Command(
            aliases = "max",
            desc = "Définir la durabilité maximale de l'item",
            perms = "SAOStats.durability.max",
            usage = "[max] [value]"
    )
    public void max(@Sender Player player, @Hand ItemStack item, int max) {

        // Unbreakable
        ItemMeta itemMeta = getItemMeta(item);
        itemMeta.setUnbreakable(true);
        item.setItemMeta(itemMeta);

        NBTItem nbtItem = new NBTItem(item);

        ItemInformations itemInformations = new ItemInformations();
        if(nbtItem.hasKey("ItemInformations")){
            itemInformations = nbtItem.getObject("ItemInformations", ItemInformations.class);
        }

        if(itemInformations.hasIntAttribute("durability.max")){
            itemInformations.removeIntAttribute("durability.max");
        }

        if(itemInformations.hasIntAttribute("durability.actual")){
            itemInformations.removeIntAttribute("durability.actual");
        }

        itemInformations.addIntAttribute("durability.max", max);
        itemInformations.addIntAttribute("durability.actual", max);
        player.sendMessage("§aLa durabilité maximale de l'item a été définie à " + max);

        nbtItem.setObject("ItemInformations", itemInformations);

        ItemStack itemStack = nbtItem.getItem();
        updateDurabilityLore(itemStack);

        player.getInventory().setItemInMainHand(nbtItem.getItem());
    }

    @Command(
            aliases = "actual",
            desc = "Définir la durabilité actuelle de l'item",
            perms = "SAOStats.durability.actual",
            usage = "[actual] [value]"
    )
    public void actual(@Sender Player player, @Hand ItemStack item, int actual) {
        ItemMeta itemMeta = getItemMeta(item);
        itemMeta.setUnbreakable(true);
        item.setItemMeta(itemMeta);

        NBTItem nbtItem = new NBTItem(item);

        ItemInformations itemInformations = new ItemInformations();
        if(nbtItem.hasKey("ItemInformations")){
            itemInformations = nbtItem.getObject("ItemInformations", ItemInformations.class);
        }

        if(itemInformations.hasIntAttribute("durability.actual")){
            itemInformations.removeIntAttribute("durability.actual");
        }
        itemInformations.addIntAttribute("durability.actual", actual);
        player.sendMessage("§aLa durabilité de l'item a été définie à " + actual);

        nbtItem.setObject("ItemInformations", itemInformations);

        ItemStack itemStack = nbtItem.getItem();
        updateDurabilityLore(itemStack);

        player.getInventory().setItemInMainHand(nbtItem.getItem());
    }

    @Command(
            aliases = "repaire",
            desc = "permet de réparer l'item",
            perms = "SAOStats.durability.repare",
            usage = "[value]"
    )
    public void repaire(@Sender Player player, @Hand ItemStack item, @Maybe Integer amount) {
        ItemMeta itemMeta = getItemMeta(item);
        itemMeta.setUnbreakable(true);
        item.setItemMeta(itemMeta);

        NBTItem nbtItem = new NBTItem(item);

        ItemInformations itemInformations = new ItemInformations();
        if(nbtItem.hasKey("ItemInformations")){
            itemInformations = nbtItem.getObject("ItemInformations", ItemInformations.class);
        }

        if(amount != null){
            itemInformations.addIntAttribute("durability.actual", amount + itemInformations.getIntAttribute("durability.actual"));
            player.sendMessage("§a" + amount + " points de durabilité ont été ajoutés à l'item");
        }
        else if(itemInformations.hasIntAttribute("durability.max")){
            itemInformations.addIntAttribute("durability.actual", itemInformations.getIntAttribute("durability.max"));
            player.sendMessage("§aL'item a été réparé");
        }

        nbtItem.setObject("ItemInformations", itemInformations);
        nbtItem.getItem();

        ItemStack itemStack = nbtItem.getItem();
        updateDurabilityLore(itemStack);

        player.getInventory().setItemInMainHand(itemStack);
    }

    public static void updateDurabilityLore(ItemStack item){
        NBTItem nbtItem = new NBTItem(item);
        ItemInformations itemInformations = new ItemInformations();
        int max = 0;
        int actual = 0;

        if(nbtItem.hasKey("ItemInformations")){
            itemInformations = nbtItem.getObject("ItemInformations", ItemInformations.class);
        }

        if(itemInformations.hasIntAttribute("durability.actual")){
            actual = itemInformations.getIntAttribute("durability.actual");
        }

        if(itemInformations.hasIntAttribute("durability.max")){
            max = itemInformations.getIntAttribute("durability.max");
        }

        ItemMeta itemMeta = item.getItemMeta();
        String lore = "§7Durabilité : §a" + actual + "§7/§c" + max;

        if(!itemMeta.hasLore()) {
            List<String> lores = new ArrayList<>();
            lores.add(lore);
            itemMeta.setLore(lores);
        }

        List<String> lores = itemMeta.getLore();
        List<String> newLores = new ArrayList<>(lores);

        for (String s : lores) {
            if(s.contains("Durabilité")){
                int index = lores.indexOf(s);
                newLores.set(index, lore);
            }
        }
        itemMeta.setLore(newLores);

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
