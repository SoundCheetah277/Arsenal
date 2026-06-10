package dev.doctor4t.arsenal.network;

import dev.doctor4t.arsenal.Arsenal;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record HoldWeaponPayload(boolean hold) implements CustomPayload {
    public static final Id<HoldWeaponPayload> ID =
            new Id<>(Arsenal.id("hold_weapon"));
    public static final PacketCodec<PacketByteBuf, HoldWeaponPayload> CODEC =
            PacketCodec.tuple(PacketCodecs.BOOL, HoldWeaponPayload::hold, HoldWeaponPayload::new);

    @Override
    public Id<HoldWeaponPayload> getId() {
        return ID;
    }
}
