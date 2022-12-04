package fr.soraxdubbing.saostats.module;

import app.ashcon.intake.parametric.AbstractModule;
import app.ashcon.intake.parametric.provider.EnumProvider;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.potion.PotionType;

public class SpigotModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Enchantment.class).overridable().toProvider(new EnchantProvider());
        bind(PotionType.class).overridable().toProvider(new EnumProvider<>(PotionType.class));
    }
}
