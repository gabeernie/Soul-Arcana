package net.soularcana.common.event;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import net.soularcana.common.entity.SoulType;
import net.soularcana.common.setup.SoulArcanaItems;

public class EntityEvents
{
    public static void onEntityKill(World world, Entity killer, LivingEntity victim)
    {
        if (killer instanceof PlayerEntity player)
        {
            if (!player.getInventory().contains(SoulArcanaItems.TAG_SOUL_GEMS))
                return;

            var type = SoulType.getEntitySoulType(victim);

            if (type == null)
                return;

            var soulGemItem = SoulArcanaItems.getSoulGemForType(type);

            for (int slot = 0; slot < player.getInventory().size(); slot++)
            {
                var slotStack = player.getInventory().getStack(slot);

                if (slotStack.getItem() == soulGemItem && !slotStack.getOrCreateNbt().contains("filled"))
                {
                    slotStack.getNbt().putString("filled", Text.Serializer.toJson(victim.getDisplayName()));
                    break;
                }
            }
        }
    }
}
