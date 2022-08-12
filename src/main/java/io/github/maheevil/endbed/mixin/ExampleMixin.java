package io.github.maheevil.endbed.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerboundUseItemPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.apache.commons.lang3.mutable.MutableObject;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultiPlayerGameMode.class)
public class ExampleMixin {
	@Shadow @Final private Minecraft minecraft;

	@Inject(
			method = "method_41933(Lorg/apache/commons/lang3/mutable/MutableObject;Lnet/minecraft/client/player/LocalPlayer;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/phys/BlockHitResult;I)Lnet/minecraft/network/protocol/Packet;",
			at = @At("HEAD"),
			cancellable = true
	)
	public void injectOnAnonFunc(MutableObject mutableObject, LocalPlayer localPlayer, InteractionHand interactionHand, BlockHitResult blockHitResult, int i, CallbackInfoReturnable<Packet> cir){
		if(!localPlayer.getLevel().dimensionType().bedWorks()){
			BlockState state = localPlayer.level.getBlockState(blockHitResult.getBlockPos());
			if(state.getBlock() instanceof BedBlock){
				mutableObject.setValue(InteractionResult.CONSUME);
				cir.setReturnValue(new ServerboundUseItemPacket(interactionHand,i));
				cir.cancel();
			}
			//if(blockHitResult.)
		}
	}
}
