package fr.soraxdubbing.saostats.module;

import app.ashcon.intake.argument.ArgumentException;
import app.ashcon.intake.argument.CommandArgs;
import app.ashcon.intake.bukkit.parametric.provider.BukkitProvider;
import app.ashcon.intake.parametric.ProvisionException;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;

import java.lang.annotation.Annotation;
import java.util.List;

public class EnchantProvider implements BukkitProvider<Enchantment> {

    @Override
    public boolean isProvided() {
        return true;
    }

    @Override
    public Enchantment get(CommandSender commandSender, CommandArgs commandArgs, List<? extends Annotation> list) throws ArgumentException, ProvisionException {
        String enchantName = commandArgs.next();
        try {
            return Enchantment.getByName(enchantName);
        } catch (Exception e) {
            throw new ArgumentException("Enchantement inconnu !");
        }
    }
}
