package fr.soraxdubbing.itemmodifiers.commands;

import de.tr7zw.nbtapi.NBTItem;
import fr.soraxdubbing.itemmodifiers.ItemInformations;
import fr.soraxdubbing.itemmodifiers.SAOStats;
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

                itemInformations.setName("§c§lEpée de la mort");
                itemInformations.setDescription("Epée à la puissance inimaginable");

                itemInformations.addDoubleAttribute("critic.damage",1.5);
                itemInformations.addDoubleAttribute("critic.chance",0.5);

                itemInformations.addDoubleAttribute("damage.max",2.0);
                itemInformations.addDoubleAttribute("damage.min",1.0);

                itemInformations.addIntAttribute("durability.max",2000);
                itemInformations.addIntAttribute("durability.actual",2000);

                itemInformations.addPotion(PotionType.LUCK,1);
                itemInformations.addPotion(PotionType.SPEED,2);

                nbtItem.setObject("ItemInformations",itemInformations);

                item = nbtItem.getItem();
                SetLores(item);

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




    public static void SetLores(ItemStack item){
        SAOStats.getInstance().getLogger().info("SetLores");
        NBTItem nbtItem = new NBTItem(item);
        ItemInformations itemInformations = nbtItem.getObject("ItemInformations",ItemInformations.class);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§r§8§l[ §r" + itemInformations.getName() + "§r§8§l ]");

        SAOStats.getInstance().getLogger().info("Description : " + itemInformations.getDescription());
        List<String> lores = new ArrayList<>();
        lores.add("§8§m==========§r§8[§r§2 Desc §8]§m==========");
        lores.add("§2" + itemInformations.getDescription());

        SAOStats.getInstance().getLogger().info("Attributes");
        if (!itemInformations.intAttributesIsEmpty() || !itemInformations.doubleAttributesIsEmpty()){
            lores.add("§8§m==========§r§8[§r§2 Attributs §8]§m==========");
            if(itemInformations.hasDoubleAttribute("damage.min") && itemInformations.hasDoubleAttribute("damage.max")){
                lores.add("§7Dégâts §l§f: §a" + itemInformations.getDoubleAttribute("damage.min") + " §f→ §a" + itemInformations.getDoubleAttribute("damage.max"));
            }
            if(itemInformations.hasDoubleAttribute("critic.chance") && itemInformations.hasDoubleAttribute("critic.damage")){
                lores.add("§7Critic.Chance §l§f: §a" + itemInformations.getDoubleAttribute("critic.chance") + "%");
                lores.add("§7Critic.Damage §l§f: §a" + itemInformations.getDoubleAttribute("critic.damage") + "x");
            }
            if(itemInformations.hasIntAttribute("durability.actual") && itemInformations.hasIntAttribute("durability.max")){
                lores.add("§7Durabilité §l§f: §a" + itemInformations.getIntAttribute("durability.actual") + "§f/§a" + itemInformations.getIntAttribute("durability.max"));
            }
        }

        SAOStats.getInstance().getLogger().info("Buff");
        if(!itemInformations.potionsIsEmpty()){
            lores.add("§8§m==========§r§8[§r§2 Buff §8]§m==========");
            itemInformations.getPotions().forEach((potionType, integer) -> {
                lores.add("§dApply buff " + potionType.name() + " " + intToRoman(integer+1));
            });
        }

        SAOStats.getInstance().getLogger().info("SetLore");
        meta.setLore(lores);
        item.setItemMeta(meta);
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
