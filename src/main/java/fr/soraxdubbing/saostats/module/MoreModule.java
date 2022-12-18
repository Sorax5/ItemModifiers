package fr.soraxdubbing.saostats.module;

import app.ashcon.intake.parametric.AbstractModule;
import app.ashcon.intake.parametric.provider.EnumProvider;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.potion.PotionType;

public class MoreModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(PotionType.class).toProvider(new EnumProvider<PotionType>(PotionType.class));
        bind(Enchantment.class).overridable().toProvider(new EnchantProvider());
    }
}
