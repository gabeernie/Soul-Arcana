package net.soularcana.common.setup;

import com.chocohead.mm.api.ClassTinkerers;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.soularcana.GlobeType;
import net.soularcana.SoulArcana;

public class SoulArcanaEnchantments
{
    public static EnchantmentTarget GLOBE_TYPE;

    public static Enchantment ARCANE;
    public static Enchantment CURSE;
    public static Enchantment FIRE;
    public static Enchantment FROST;
    public static Enchantment POISON;
    public static Enchantment STORM;

    public static void registerEnchantments()
    {
        GLOBE_TYPE = ClassTinkerers.getEnum(EnchantmentTarget.class, "ARCANE_GLOBE");

        registerEnchantment(ARCANE = new GlobeEnchantment(), "arcane");
        registerEnchantment(CURSE = new GlobeEnchantment(), "curse");
        registerEnchantment(FIRE = new GlobeEnchantment(), "fire");
        registerEnchantment(FROST = new GlobeEnchantment(), "frost");
        registerEnchantment(POISON = new GlobeEnchantment(), "poison");
        registerEnchantment(STORM = new GlobeEnchantment(), "storm");
    }

    private static void registerEnchantment(Enchantment enchantment, String name)
    {
        Registry.register(Registry.ENCHANTMENT, new Identifier(SoulArcana.MODID, name), enchantment);
    }

    public static Enchantment getEnchantment(GlobeType globeType)
    {
        return switch (globeType)
                {

                    case ARCANE -> ARCANE;
                    case FIRE -> FIRE;
                    case FROST -> FROST;
                    case STORM -> STORM;
                    case POISON -> POISON;
                    case CURSE -> CURSE;
                };
    }

    private static class GlobeEnchantment extends Enchantment
    {
        protected GlobeEnchantment()
        {
            super(Rarity.VERY_RARE, GLOBE_TYPE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
        }

        public int getMinPower(int level)
        {
            return 0;
        }

        public int getMaxPower(int level)
        {
            return super.getMinPower(level) + 50;
        }

        @Override
        protected boolean canAccept(Enchantment other)
        {
            return other.type == EnchantmentTarget.BREAKABLE;
        }
    }
}
