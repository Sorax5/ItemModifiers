package fr.soraxdubbing.saostats.module;

import app.ashcon.intake.argument.ArgumentException;
import app.ashcon.intake.argument.CommandArgs;
import app.ashcon.intake.bukkit.parametric.provider.BukkitProvider;
import app.ashcon.intake.parametric.ProvisionException;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.annotation.Annotation;
import java.util.List;

public class HandedItemProvider implements BukkitProvider<ItemStack> {
    @Override
    public boolean isProvided() {
        return true;
    }
    @Override
    public ItemStack get(CommandSender commandSender, CommandArgs commandArgs, List<? extends Annotation> list) throws ArgumentException, ProvisionException {
        if(commandSender instanceof Player){
            Player player = (Player) commandSender;
            if(player.getInventory().getItemInMainHand().getType() != Material.AIR){
                return player.getInventory().getItemInMainHand();
            } else {
                throw new ArgumentException("Vous devez tenir un item en main !");
            }
        }
        throw new ArgumentException("Vous devez être un joueur pour utiliser cette commande !");
    }
}
