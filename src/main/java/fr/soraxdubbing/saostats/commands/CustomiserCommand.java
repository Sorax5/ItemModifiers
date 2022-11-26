package fr.soraxdubbing.saostats.commands;

import app.ashcon.intake.bukkit.BukkitIntake;
import app.ashcon.intake.bukkit.graph.BasicBukkitCommandGraph;
import de.tr7zw.nbtapi.NBTItem;
import fr.soraxdubbing.saostats.ItemInformations;
import fr.soraxdubbing.saostats.SAOStats;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CustomiserCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player){
            Player player = (Player) sender;
            ItemStack item = player.getInventory().getItemInMainHand();
            if(!item.getType().isAir()){
                NBTItem nbtItem = new NBTItem(item);

                if(!nbtItem.hasKey("ItemInformations")){
                    ItemInformations itemInformations = new ItemInformations();
                    nbtItem.setObject("ItemInformations",itemInformations);
                }

                ItemInformations itemInformations = nbtItem.getObject("ItemInformations",ItemInformations.class);

                itemInformations.addDoubleAttribute("critic.damage",1.5);
                itemInformations.addDoubleAttribute("critic.chance",0.5);

                itemInformations.addDoubleAttribute("damage.max",2.0);
                itemInformations.addDoubleAttribute("damage.min",1.0);

                itemInformations.addIntAttribute("durability.max",2000);
                itemInformations.addIntAttribute("durability.actual",2000);

                itemInformations.addPotion(PotionType.LUCK,1);
                itemInformations.addPotion(PotionType.SPEED,2);

                itemInformations.addDoubleAttribute("defense",1.65);

                nbtItem.setObject("ItemInformations",itemInformations);

                item = nbtItem.getItem();

                player.getInventory().setItemInMainHand(nbtItem.getItem());
                player.sendMessage("§aItem customisé !");
            }



        }
        return true;
    }

    private void Critical(ItemStack item, double damage, double chance) {
        NBTItem nbtItem = new NBTItem(item);
        if (nbtItem.hasKey("CustomAttributes")) {
            nbtItem.removeKey("CustomAttributes");
        }

        /*

        NBTCompoundList attributes = nbtItem.getCompoundList("CustomAttributes");
        NBTListCompound mod1 = attributes.addCompound();
        mod1.setString("Name", "critical");
        mod1.setDouble("Damage", damage);
        mod1.setDouble("Chance", chance);*/
        item = nbtItem.getItem();
    }

    public static String intToRoman(int num)
    {
        System.out.println("Integer: " + num);
        int[] values = {1000,900,500,400,100,90,50,40,10,9,5,4,1};
        String[] romanLetters = {"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};
        StringBuilder roman = new StringBuilder();
        for(int i=0;i<values.length;i++)
        {
            while(num >= values[i])
            {
                num = num - values[i];
                roman.append(romanLetters[i]);
            }
        }
        return roman.toString();
    }
}
