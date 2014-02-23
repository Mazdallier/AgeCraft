package org.agecraft.core.player;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.init.Items;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.util.Session;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;

import org.agecraft.core.Tools;

import elcon.mods.elconqore.player.PlayerCoreClient;

public class ACPlayerClient extends PlayerCoreClient {

	public ACPlayerClient(Minecraft mc, World world, Session session, NetHandlerPlayClient netHandlerClient, StatFileWriter statFileWriter, int playerCoreIndex, PlayerCoreClient entityPlayerSP) {
		super(mc, world, session, netHandlerClient, statFileWriter, playerCoreIndex, entityPlayerSP);
		player.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0D);
		player.setHealth(100.0F);
	}
	
	@Override
	public float getFOVMultiplier() {
		float fov = 1.0F;
		if(player.capabilities.isFlying) {
			fov *= 1.1F;
		}
		IAttributeInstance attributeinstance = player.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
		fov = (float) ((double) fov * ((attributeinstance.getAttributeValue() / (double) player.capabilities.getWalkSpeed() + 1.0D) / 2.0D));
		if(player.isUsingItem() && (player.getItemInUse().getItem() == Items.bow || player.getItemInUse().getItem() == Tools.bow || player.getItemInUse().getItem() == Tools.crossbow)) {
			int duration = player.getItemInUseDuration();
			float multiplier = (float) duration / 20.0F;
			if(multiplier > 1.0F) {
				multiplier = 1.0F;
			} else {
				multiplier *= multiplier;
			}
			fov *= 1.0F - multiplier * 0.15F;
		}
		return ForgeHooksClient.getOffsetFOV(player, fov);
	}
}
