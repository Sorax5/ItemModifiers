package fr.soraxdubbing.saostats;

import app.ashcon.intake.bukkit.BukkitIntake;
import app.ashcon.intake.bukkit.graph.BasicBukkitCommandGraph;
import app.ashcon.intake.fluent.DispatcherNode;
import de.tr7zw.nbtapi.NBTItem;
import fr.soraxdubbing.saostats.commands.*;
import fr.soraxdubbing.saostats.module.MoreModule;
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

            if(!item.getType().isAir()){
                NBTItem nbtItem = new NBTItem(item);
                if(nbtItem.hasKey("ItemInformations")){
                    ItemInformations itemInformations = nbtItem.getObject("ItemInformations",ItemInformations.class);

                    if(itemInformations.hasIntAttribute("durability.actual")){
                        int actuel = itemInformations.getIntAttribute("durability.actual");

                        if(actuel > 0){
                            actuel--;
                            itemInformations.addIntAttribute("durability.actual",actuel);
                            nbtItem.setObject("ItemInformations",itemInformations);
                            item = nbtItem.getItem();
                            DurabilityCommand.updateDurabilityLore(item);
                            livingEntity.getEquipment().setItemInMainHand(item);
                            if(actuel == 0){
                                Location location = livingEntity.getLocation();
                                livingEntity.getWorld().spawnParticle(org.bukkit.Particle.BLOCK_CRACK,location,10,0.5,0.5,0.5,0.5,location.getBlock().getBlockData());
                                livingEntity.getWorld().playSound(location,"minecraft:block.anvil.land",1,1);
                            }
                        }
                    }

                    if(itemInformations.hasDoubleAttribute("damage.max") && itemInformations.hasDoubleAttribute("damage.min")){
                        double max = itemInformations.getDoubleAttribute("damage.max");
                        double min = itemInformations.getDoubleAttribute("damage.min");

                        damage = Math.random() * (max - min) + min;
                    }
                    if (itemInformations.hasDoubleAttribute("damage.set")) {
                        double set = itemInformations.getDoubleAttribute("damage.set");
                        damage = set;
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
                }
            }
        }


        if(damaged instanceof LivingEntity){
            LivingEntity livingEntity = (LivingEntity) damaged;
            ItemStack[] armors = livingEntity.getEquipment().getArmorContents();

            if (armors != null){
                ItemStack[] newArmors = new ItemStack[armors.length];

                for (int i=0 ; i<armors.length ; i++){
                    ItemStack armor = armors[i];
                    if(armor == null) {
                        newArmors[i] = armors[i];
                        continue;
                    }
                    if(armor.getType().isAir()) {
                        newArmors[i] = armors[i];
                        continue;
                    }
                    NBTItem nbt = new NBTItem(armor);

                    if(nbt.hasKey("ItemInformations")) {
                        ItemInformations itemInformations = nbt.getObject("ItemInformations", ItemInformations.class);

                        if(itemInformations.hasIntAttribute("durability.actual")){
                            int actuel = itemInformations.getIntAttribute("durability.actual");

                            if(actuel > 0){
                                actuel--;
                                itemInformations.addIntAttribute("durability.actual",actuel);
                                nbt.setObject("ItemInformations",itemInformations);
                                armor = nbt.getItem();
                                DurabilityCommand.updateDurabilityLore(armor);
                                newArmors[i] = armor;
                                if(actuel == 0){
                                    Location location = livingEntity.getLocation();
                                    livingEntity.getWorld().spawnParticle(org.bukkit.Particle.BLOCK_CRACK,location,10,0.5,0.5,0.5,0.5,location.getBlock().getBlockData());
                                    livingEntity.getWorld().playSound(location,"minecraft:block.anvil.land",1,1);
                                    continue;
                                }
                            }
                            else {
                                newArmors[i] = armors[i];
                            }
                        }

                        if(itemInformations.hasDoubleAttribute("defense")){
                            double defense = itemInformations.getDoubleAttribute("defense");
                            damage -= (damage*defense);
                            if(damage < 0) damage = 0D;
                            System.out.println("Defense : " + defense);
                        }
                    }
                }
                livingEntity.getEquipment().setArmorContents(newArmors);
            }
        }
        event.setDamage(damage);
    }

    @EventHandler
    public void DamageHandlerEvent(EntityDamageEvent event){
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

                        if(itemInformations.hasIntAttribute("defense")){
                            int defense = itemInformations.getIntAttribute("defense");
                            damage -= defense;
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
        BasicBukkitCommandGraph cmdGraph = new BasicBukkitCommandGraph();

        cmdGraph.getBuilder().getInjector().install(new MoreModule());

        DispatcherNode cmd = cmdGraph.getRootDispatcherNode().registerNode("SAOStats");
        cmd.registerNode("damage").registerCommands(new DamageCommand());
        cmd.registerNode("critic").registerCommands(new CriticCommand());
        cmd.registerNode("durability").registerCommands(new DurabilityCommand());
        cmd.registerCommands(new DefenseCommand());
        cmd.registerNode("health").registerCommands(new HealthCommand());
        cmd.registerNode("name").registerCommands(new NameCommand());
        cmd.registerNode("lore").registerCommands(new LoreCommand());
        cmd.registerNode("potion").registerCommands(new PotionCommand());
        cmd.registerNode("enchant").registerCommands(new EnchantCommand());
        cmd.registerNode("attribute").registerCommands(new AttributesCommands());

        BukkitIntake bukkitIntake = new BukkitIntake(this, cmdGraph);
        bukkitIntake.register();
    }

    public static SAOStats getInstance() {
        return instance;
    }

}
