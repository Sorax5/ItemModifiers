package fr.soraxdubbing.saostats.module;

import app.ashcon.intake.parametric.AbstractModule;
import app.ashcon.intake.parametric.provider.EnumProvider;
import fr.soraxdubbing.saostats.module.annoted.Hand;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;

public class MoreModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(PotionType.class).toProvider(new EnumProvider<PotionType>(PotionType.class));
        bind(Enchantment.class).toProvider(new EnchantProvider());
        bind(Attribute.class).toProvider(new EnumProvider<Attribute>(Attribute.class));
        bind(ItemStack.class).annotatedWith(Hand.class).toProvider(new HandedItemProvider());
    }
}
