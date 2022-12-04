package fr.soraxdubbing.saostats;

import app.ashcon.intake.Intake;
import app.ashcon.intake.bukkit.BukkitIntake;
import app.ashcon.intake.bukkit.graph.BasicBukkitCommandGraph;
import app.ashcon.intake.fluent.DispatcherNode;
import app.ashcon.intake.parametric.Injector;
import de.tr7zw.nbtapi.NBTItem;
import fr.soraxdubbing.saostats.commands.*;
import fr.soraxdubbing.saostats.module.SpigotModule;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionType;

public final class SAOStats extends JavaPlugin implements Listener {

    private static SAOStats instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
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

            if(item.getType().isAir()){
                return;
            }

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
                        damager.spigot().sendMessage(new TextComponent("§c§lCRITIC !"));

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
                        livingEntity.getEquipment().setItemInMainHand(item);
                    }else{
                        livingEntity.getEquipment().setItemInMainHand(null);
                    }
                }
            }
        }

        if(damaged instanceof LivingEntity){
            LivingEntity livingEntity = (LivingEntity) damaged;
            ItemStack[] armors = livingEntity.getEquipment().getArmorContents();

            if (armors != null){
                for (ItemStack armor : armors) {

                    if(armor == null) continue;
                    if(armor.getType().isAir()) continue;
                    NBTItem nbt = new NBTItem(armor);

                    if(nbt.hasKey("ItemInformations")) {
                        ItemInformations itemInformations = nbt.getObject("ItemInformations", ItemInformations.class);

                        if(itemInformations.hasDoubleAttribute("defense")){
                            double defense = itemInformations.getDoubleAttribute("defense");
                            damage -= (damage*defense);
                            if(damage < 0) damage = 0D;
                        }
                    }
                }
            }
        }

        event.setDamage(damage);
    }

    @EventHandler
    public void zdzdd(EntityDamageEvent event){
        Entity damaged = event.getEntity();
        double damage = event.getDamage();

        if(damaged instanceof LivingEntity){
            LivingEntity livingEntity = (LivingEntity) damaged;
            ItemStack[] armors = livingEntity.getEquipment().getArmorContents();

            if (armors != null){
                for (ItemStack armor : armors) {

                    if(armor == null) continue;
                    if(armor.getType().isAir()) continue;
                    NBTItem nbt = new NBTItem(armor);

                    if(nbt.hasKey("ItemInformations")) {
                        ItemInformations itemInformations = nbt.getObject("ItemInformations", ItemInformations.class);

                        if(itemInformations.hasDoubleAttribute("defense")){
                            double defense = itemInformations.getDoubleAttribute("defense");
                            damage -= (damage*defense);
                            if(damage < 0) damage = 0D;
                        }
                    }
                }
            }
        }

        event.setDamage(damage);
    }

    @Override
    public void onLoad() {
        Injector injector = Intake.createInjector();
        injector.install(new SpigotModule());

        BasicBukkitCommandGraph cmdGraph = new BasicBukkitCommandGraph();

        DispatcherNode loreNode = cmdGraph.getRootDispatcherNode().registerNode("lore");
        loreNode.registerCommands(new LoreCommand());

        DispatcherNode attributeNode = cmdGraph.getRootDispatcherNode().registerNode("SAOStats");
        attributeNode.registerNode("damage").registerCommands(new DamageCommand());
        attributeNode.registerNode("critic").registerCommands(new CriticCommand());
        attributeNode.registerNode("durability").registerCommands(new DurabilityCommand());
        attributeNode.registerCommands(new DefenseCommand());
        attributeNode.registerNode("potion").registerCommands(new PotionCommand());
        attributeNode.registerNode("enchant").registerCommands(new EnchantCommand());

        BukkitIntake bukkitIntake = new BukkitIntake(this, cmdGraph);
        bukkitIntake.register();
    }

    public static SAOStats getInstance() {
        return instance;
    }

}
