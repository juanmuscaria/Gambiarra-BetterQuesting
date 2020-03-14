package dev.bq3_thermos_patch;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import net.minecraft.server.v1_7_R4.Container;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventoryCrafting;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventoryView;
import org.bukkit.entity.HumanEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


@Mixin(ContainerPlayer.class)
public abstract class MixinPatch{

    @Shadow
    @Final
    private EntityPlayer thePlayer;

    @Shadow
    private CraftInventoryView bukkitEntity = null;

    @Shadow
    private InventoryPlayer player;

    @Shadow public InventoryCrafting craftMatrix;

    @Inject(method = "onCraftMatrixChanged", at = @At("HEAD"), cancellable = true)
    public void onCraftMatrixChanged(IInventory p_75130_, CallbackInfo ci) throws NoSuchFieldException, IllegalAccessException {
        //System.out.println("Metodo do mixin chamado");
        Object a = (Object) this;
        ContainerPlayer self = (ContainerPlayer) a;
        Class containerclass = self.getClass().getSuperclass();
        Field craftersField = containerclass.getDeclaredField("field_75149_d");
        craftersField.setAccessible(true);
        List crafters = (List) craftersField.get(self);
        try {
            Field last = CraftingManager.getInstance().getClass().getDeclaredField("lastCraftView");
            last.setAccessible(true);
            last.set(CraftingManager.getInstance(), getBukkitView(self));
            self.craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(self.craftMatrix, this.thePlayer.worldObj));
            ItemStack craftResult = CraftingManager.getInstance().findMatchingRecipe(self.craftMatrix, this.thePlayer.worldObj);
            self.craftResult.setInventorySlotContents(0, craftResult);


            if (crafters.size() < 1)
            {
                return;
            }

            EntityPlayerMP player = (EntityPlayerMP) crafters.get(0);
            player.playerNetServerHandler.sendPacket(new S2FPacketSetSlot(player.openContainer.windowId, 0, craftResult));
        }
        catch (Exception e){
            self.craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(self.craftMatrix, this.thePlayer.worldObj));
            //e.printStackTrace();
        }
        ci.cancel();
    }

    public CraftInventoryView getBukkitView(ContainerPlayer self) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (bukkitEntity != null)
        {
            return bukkitEntity;
        }
        Object cm = self.craftMatrix;
        Object cr = self.craftResult;
        CraftInventoryCrafting inventory = new CraftInventoryCrafting((net.minecraft.server.v1_7_R4.InventoryCrafting) cm, (net.minecraft.server.v1_7_R4.IInventory) cr);
        Method getbukkitentity = player.player.getClass().getMethod("getBukkitEntity");
        Object a = self;
        bukkitEntity = new CraftInventoryView((HumanEntity) getbukkitentity.invoke(player.player), inventory, (Container) a);
        return bukkitEntity;
    }
}
