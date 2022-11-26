package fr.soraxdubbing.saostats;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

public class PotionThread extends BukkitRunnable {

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            ItemStack item = player.getInventory().getItemInMainHand();
            if(!item.getType().isAir()){
                NBTItem nbtItem = new NBTItem(item);
                if(nbtItem.hasKey("ItemInformations")){
                    ItemInformations itemInformations = nbtItem.getObject("ItemInformations",ItemInformations.class);
                    itemInformations.getPotions().forEach((potionType, integer) -> {
                        PotionEffect potionEffect = new PotionEffect(potionType.getEffectType(),20,integer);
                        player.addPotionEffect(potionEffect);
                    });
                }
            }
        });
    }
}
