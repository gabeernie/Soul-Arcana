package net.soularcana.common.item;

import net.minecraft.block.Blocks;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.soularcana.GlobeType;
import net.soularcana.SoulArcana;
import net.soularcana.common.setup.SoulArcanaEntities;
import net.soularcana.common.setup.SoulArcanaItems;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SpellStaffItem extends Item
{
    private final GlobeType globeType;

    public SpellStaffItem(Settings settings, GlobeType globeType)
    {
        super(settings.maxDamage(100));
        this.globeType = globeType;
    }


    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        super.appendTooltip(stack, world, tooltip, context);

        tooltip.add(new TranslatableText(SoulArcana.MODID + ".spell_staff_detach.lore"));
        tooltip.add(new TranslatableText(SoulArcana.MODID + ".spell_staff_repair.lore"));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context)
    {
        var blockState = context.getWorld().getBlockState(context.getBlockPos());
        if (blockState.getBlock() == Blocks.SMITHING_TABLE)
        {
            context.getPlayer().setStackInHand(context.getHand(), new ItemStack(SoulArcanaItems.STAFF));

            var globeStack = SpellGlobeItem.getGlobeStack(globeType);
            globeStack.setDamage(context.getStack().getDamage());

            if (!context.getPlayer().giveItemStack(globeStack)) context.getPlayer().dropItem(globeStack, true);

            context.getStack().setCount(0);

            return ActionResult.SUCCESS;
        }

        return super.useOnBlock(context);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
    {
        ItemStack stack = user.getStackInHand(hand);

        if (!user.handSwinging)
        {
            if (!world.isClient() && stack.getDamage() < stack.getMaxDamage() - 1)
            {
                var pos = user.getPos().add(user.getRotationVector().multiply(0.5D)).add(0.5D * Math.sin(Math.toRadians(225.0F - user.getHeadYaw())), user.getHeight() * 2.0F / 3.0F, 0.5D * Math.cos(Math.toRadians(225.0F - user.getHeadYaw())));
                var velocity = user.getCameraPosVec(0.0F).add(user.getRotationVector().multiply(40)).subtract(pos).multiply(0.05D);

                world.spawnEntity(SoulArcanaEntities.getProjectileEntity(globeType).create(world).shoot(pos.x, pos.y, pos.z, velocity.x, velocity.y, velocity.z, user.getUuid()));
                world.playSound(null, pos.x, pos.y, pos.z, SoundEvents.ENTITY_BLAZE_SHOOT, SoundCategory.NEUTRAL, 0.75F, world.getRandom().nextFloat() * 0.2F + 0.9F);

                stack.damage(1, user, player -> player.sendToolBreakStatus(hand));
            }

            return TypedActionResult.success(stack, true);
        }
        else return TypedActionResult.pass(stack);
    }
}
