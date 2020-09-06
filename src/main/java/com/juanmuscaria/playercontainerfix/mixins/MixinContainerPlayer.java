package com.juanmuscaria.playercontainerfix.mixins;


import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(ContainerPlayer.class)
public abstract class MixinContainerPlayer extends Container {

    @Shadow
    public IInventory craftResult;

    //Way better knowing how and where to inject code.
    @Inject(method = "onCraftMatrixChanged", at = @At(value = "INVOKE", target = "Ljava/util/List;get(I)Ljava/lang/Object;"), cancellable = true)
    private void onCraftMatrixChangedProxy(CallbackInfo ci) {
        Object player = crafters.get(0);
        if (player instanceof EntityPlayerMP) {
            EntityPlayerMP playerMP = (EntityPlayerMP) player;
            ItemStack result = craftResult.getStackInSlot(0);
            playerMP.playerNetServerHandler.sendPacket(new S2FPacketSetSlot(playerMP.openContainer.windowId, 0, result));
        }
        ci.cancel();
    }
}
