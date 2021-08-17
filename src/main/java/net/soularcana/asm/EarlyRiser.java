package net.soularcana.asm;

import com.chocohead.mm.api.ClassTinkerers;
import net.fabricmc.loader.api.FabricLoader;

public class EarlyRiser implements Runnable {
	@Override
	public void run() {
		addTargets();
	}

	private void addTargets() {
		var mapResolver = FabricLoader.getInstance().getMappingResolver();
		String enchantmentTarget = mapResolver.mapClassName("intermediary", "net.minecraft.class_1886");

		ClassTinkerers.enumBuilder(enchantmentTarget)
				.addEnumSubclass("ARCANE_GLOBE", "net.soularcana.asm.ArcaneGlobeEnchantmentTarget").build();
	}
}