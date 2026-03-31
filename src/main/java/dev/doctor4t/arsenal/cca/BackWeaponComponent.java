package dev.doctor4t.arsenal.cca;

import dev.doctor4t.arsenal.Arsenal;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.RegistryWrapper;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

public class BackWeaponComponent implements AutoSyncedComponent {
    private final PlayerEntity player;
    private final SimpleInventory backWeapon = new SimpleInventory(1);
    private boolean holdingBackWeapon = false;

    public BackWeaponComponent(PlayerEntity player) {
        this.player = player;
    }

    public ItemStack getBackWeapon() {
        return this.backWeapon.getStack(0);
    }

    public static ItemStack getBackWeapon(PlayerEntity player) {
        return ArsenalComponents.BACK_WEAPON_COMPONENT.get(player).getBackWeapon();
    }

    public boolean setBackWeapon(ItemStack backWeapon) {
        this.backWeapon.setStack(0, backWeapon);
        ArsenalComponents.BACK_WEAPON_COMPONENT.sync(this.player);
        return true;
    }

    public static boolean setBackWeapon(PlayerEntity player, ItemStack backWeapon) {
        return ArsenalComponents.BACK_WEAPON_COMPONENT.get(player).setBackWeapon(backWeapon);
    }

    public SimpleInventory getBackWeaponInventory() {
        return this.backWeapon;
    }

    public static SimpleInventory getBackWeaponInventory(PlayerEntity player) {
        return ArsenalComponents.BACK_WEAPON_COMPONENT.get(player).getBackWeaponInventory();
    }

    public boolean isHoldingBackWeapon() {
        return this.holdingBackWeapon;
    }

    public static boolean isHoldingBackWeapon(PlayerEntity player) {
        return ArsenalComponents.BACK_WEAPON_COMPONENT.get(player).isHoldingBackWeapon();
    }

    public void setHoldingBackWeapon(boolean holdingBackWeapon) {
        this.holdingBackWeapon = holdingBackWeapon;
        ArsenalComponents.BACK_WEAPON_COMPONENT.sync(this.player);
    }

    public static void setHoldingBackWeapon(PlayerEntity player, boolean holdingBackWeapon) {
        if (player.getWorld().isClient()) {
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeBoolean(holdingBackWeapon);
            ClientPlayNetworking.send(Arsenal.SERVERBOUND_HOLD_WEAPON_PACKET, buf);
            return;
        }
        ArsenalComponents.BACK_WEAPON_COMPONENT.get(player).setHoldingBackWeapon(holdingBackWeapon);
    }

    @Override
    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        this.backWeapon.setStack(0, ItemStack.fromNbt(nbtCompound.getCompound("backWeapon")));
        this.holdingBackWeapon = nbtCompound.getBoolean("holdingBackWeapon");
    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbtCompound.put("backWeapon", this.backWeapon.getStack(0).writeToNbt(new NbtCompound()));
        nbtCompound.putBoolean("holdingBackWeapon", this.holdingBackWeapon);
    }
}
