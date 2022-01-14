package com.github.raveninthedark.rsfreezefix.mixin;

import com.refinedmods.refinedstorage.api.network.INetwork;
import com.refinedmods.refinedstorage.apiimpl.storage.cache.ItemStorageCache;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.world.World;

@Mixin(ItemStorageCache.class)
public class ItemStorageCacheMixin {

    @Shadow
    private INetwork network;

    private int ticks;

    @Inject(method = "invalidate", cancellable = true, at = @At("HEAD"), remap = false)
    public void onInvalidate (CallbackInfo ci) {
        World world = network.getWorld();
        if (world.getServer().getTickCount() != ticks) {
            ticks = world.getServer().getTickCount();
            return;
        } 
        else {
            ci.cancel();
        }
    }
}
