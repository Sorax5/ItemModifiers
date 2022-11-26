package fr.soraxdubbing.saostats;

import de.tr7zw.nbtapi.NBTItem;
import fr.soraxdubbing.saostats.commands.CustomiserCommand;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class SAOStats extends JavaPlugin implements Listener {

    private static SAOStats instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        getCommand("customise").setExecutor(new CustomiserCommand());
        getServer().getPluginManager().registerEvents(this, this);
        PotionThread potionThread = new PotionThread();
        potionThread.runTaskTimer(this,0,10);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event){
        Entity damager = event.getDamager();
        Entity damaged = event.getEntity();

        Double damage = event.getDamage();

        if(damager instanceof LivingEntity){
            LivingEntity livingEntity = (LivingEntity) damager;

            ItemStack item = livingEntity.getEquipment().getItemInMainHand();
            NBTItem nbtItem = new NBTItem(item);

            if(nbtItem.hasKey("ItemInformations")){
                ItemInformations itemInformations = nbtItem.getObject("ItemInformations",ItemInformations.class);

                if(itemInformations.hasDoubleAttribute("damage.max") && itemInformations.hasDoubleAttribute("damage.min")){
                    double max = itemInformations.getDoubleAttribute("damage.max");
                    double min = itemInformations.getDoubleAttribute("damage.min");

                    damage = Math.random() * (max - min) + min;
                }

                if(itemInformations.hasDoubleAttribute("critic.chance") && itemInformations.hasDoubleAttribute("critic.damage")){
                    double chances = itemInformations.getDoubleAttribute("critic.chance");
                    double damages = itemInformations.getDoubleAttribute("critic.damage");

                    if(Math.random() < chances){
                        damage *= damages;
                        Component message = Component.text("§cCritical hit !");
                        livingEntity.sendActionBar(message);

                        Location location = damaged.getLocation();
                        livingEntity.getWorld().spawnParticle(org.bukkit.Particle.CRIT, location, 10);
                    }
                }

                if(itemInformations.hasIntAttribute("durability.actual")){
                    int actuel = itemInformations.getIntAttribute("durability.actual");

                    if(actuel > 0){
                        actuel--;
                        itemInformations.addIntAttribute("durability.actual",actuel);
                        nbtItem.setObject("ItemInformations",itemInformations);
                        item = nbtItem.getItem();
                        CustomiserCommand.SetLores(item);
                        livingEntity.getEquipment().setItemInMainHand(item);
                    }else{
                        livingEntity.getEquipment().setItemInMainHand(null);
                    }
                }


            }
        }

        if(damaged instanceof LivingEntity){
            LivingEntity livingEntity = (LivingEntity) damaged;
            ItemStack item = livingEntity.getEquipment().getItemInMainHand();
        }

        event.setDamage(damage);

    }

    public static SAOStats getInstance() {
        return instance;
    }

}
