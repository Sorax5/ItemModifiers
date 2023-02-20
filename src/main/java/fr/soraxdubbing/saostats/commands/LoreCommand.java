package fr.soraxdubbing.saostats.commands;

import app.ashcon.intake.Command;
import app.ashcon.intake.bukkit.parametric.annotation.Sender;
import app.ashcon.intake.parametric.annotation.Text;
import fr.soraxdubbing.saostats.module.annoted.Hand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class LoreCommand {

    @Command(
            aliases = "set",
            desc = "Set the lore of an item",
            perms = "SAOStats.lore.set",
            usage = "[line] [lore]"
    )
    public void set(@Sender Player player, @Hand ItemStack item ,int a, @Text String b){
        ItemMeta itemMeta = getItemMeta(item);

        List<String> lore = getLore(item);

        try{
            lore.set(a,b);
        }
        catch (IndexOutOfBoundsException e){
            lore.add(b);
        }
        player.sendMessage("§aLa ligne [" + a + "] a été définie à " + b);

        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
    }

    @Command(
            aliases = "add",
            desc = "Add a line to the lore of an item",
            perms = "SAOStats.lore.add",
            usage = "[lore]"
    )
    public void add(@Sender Player player, @Hand ItemStack item, @Text String b) {
        ItemMeta itemMeta = getItemMeta(item);

        List<String> lore = getLore(item);
        lore.add(b);

        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);

        player.sendMessage("§a La ligne [" + b + "] a été ajoutée");
    }

    @Command(
            aliases = "remove",
            desc = "Remove a line from the lore of an item",
            perms = "SAOStats.lore.remove",
            usage = "[line]"
    )
    public void remove(@Sender Player player, @Hand ItemStack item, int b) {
        ItemMeta itemMeta = getItemMeta(item);

        List<String> lore = getLore(item);

        try{
            lore.remove(b);
            player.sendMessage("§aLa ligne " + b + " a été supprimée");
        }
        catch (IndexOutOfBoundsException e){
            player.sendMessage("§cCette ligne n'existe pas");
        }

        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
    }

    @Command(
            aliases = "clear",
            desc = "remove all the lore of an item",
            perms = "SAOStats.lore.clear",
            usage = "[line]"
    )
    public void clear(@Sender Player player, @Hand ItemStack item) {
        ItemMeta itemMeta = getItemMeta(item);

        itemMeta.setLore(new ArrayList<>());
        player.sendMessage("§aLe lore a été supprimée");
        item.setItemMeta(itemMeta);
    }

    private ItemMeta getItemMeta(ItemStack item){
        ItemMeta itemMeta = item.getItemMeta();
        if(itemMeta == null){
            itemMeta = Bukkit.getItemFactory().getItemMeta(item.getType());
        }
        return itemMeta;
    }

    private List<String> getLore(ItemStack item){
        ItemMeta itemMeta = getItemMeta(item);
        List<String> lore = itemMeta.getLore();
        if (lore == null) {
            lore = new ArrayList<>();
        }
        return lore;
    }
}
