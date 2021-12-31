package net.soularcana.common.event;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
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

            for (int slot = 0; slot < player.getInventory().size(); slot++)
            {
                var slotStack = player.getInventory().getStack(slot);

                if (SoulArcanaItems.TAG_SOUL_GEMS.contains(slotStack.getItem()) && slotStack.getDamage() != 0)
                {
                    slotStack.setDamage(slotStack.getDamage() - type.getSoulValue());
                    break;
                }
            }
        }
    }
}
