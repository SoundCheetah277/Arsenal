package dev.doctor4t.arsenal.mixin;

import dev.doctor4t.arsenal.enchantment.UniqueEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.BookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.stream.Stream;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {
    @Inject(method = "getPossibleEntries", at = @At("RETURN"), cancellable = true)
    private static void arsenal$noFireAspectOrKnockbackOnScythe(int level, ItemStack stack, Stream<RegistryEntry<Enchantment>> possibleEnchantments, CallbackInfoReturnable<List<EnchantmentLevelEntry>> cir) {
        if (!(stack.getItem() instanceof BookItem)) {
            // remove unique enchantments from other weapons
            List<EnchantmentLevelEntry> possibleEntries = cir.getReturnValue();
            for (int i = possibleEntries.size() - 1; i >= 0; i--) {
                Enchantment enchantment = possibleEntries.get(i).enchantment.value();
                if (enchantment instanceof UniqueEnchantment) {
                    possibleEntries.remove(i);
                }
            }
            cir.setReturnValue(possibleEntries);
        }
    }
}
