package elcon.mods.agecraft.core.player;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.NetClientHandler;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.Session;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.core.Tools;
import elcon.mods.core.player.PlayerCoreClient;

@SideOnly(Side.CLIENT)
public class ACPlayerClient extends PlayerCoreClient {

	public ACPlayerClient(Minecraft mc, World world, Session session, NetClientHandler netClientHandler, int playerCoreIndex, PlayerCoreClient entityPlayerSP) {
		super(mc, world, session, netClientHandler, playerCoreIndex, entityPlayerSP);
		player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.maxHealth).setAttribute(100.0D);
		player.setHealth(100.0F);
		// player.inventoryContainer = new ContainerInventory(player.inventory, !world.isRemote, player);
		// player.dimension = 10;
		// ChunkCoordinates spawn = world.getSpawnPoint();
		// player.posX = spawn.posX;
		// player.posZ = spawn.posZ;
		// player.posY = getFirstUncoveredBlock(world, (int) player.posX, (int) player.posZ) + 1;
	}

	public int getFirstUncoveredBlock(World world, int x, int z) {
		int y;
		for(y = 63; !(world.isAirBlock(x, y, z) && world.isAirBlock(x, y + 1, z)); y++) {
		}
		return y;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getItemIcon(ItemStack stack, int renderPass) {
		Icon icon = stack.getIconIndex();
		if(stack.itemID == Item.fishingRod.itemID && player.fishEntity != null) {
			icon = Item.fishingRod.func_94597_g();
		} else {
			if(player.getItemInUse() != null && stack.itemID == Item.bow.itemID) {
				int j = stack.getMaxItemUseDuration() - player.getItemInUseCount();
				if(j >= 18) {
					return Item.bow.getItemIconForUseDuration(2);
				}
				if(j > 13) {
					return Item.bow.getItemIconForUseDuration(1);
				}
				if(j > 0) {
					return Item.bow.getItemIconForUseDuration(0);
				}
			}		
			icon = stack.getItem().getIcon(stack, renderPass, player, player.getItemInUse(), player.getItemInUseCount());
		}
		return icon;
	}

	@Override
	public float getFOVMultiplier() {
		float fov = 1.0F;
		if(player.capabilities.isFlying) {
			fov *= 1.1F;
		}
		AttributeInstance attributeinstance = player.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
		fov = (float) ((double) fov * ((attributeinstance.getAttributeValue() / (double) player.capabilities.getWalkSpeed() + 1.0D) / 2.0D));
		if(player.isUsingItem() && (player.getItemInUse().itemID == Item.bow.itemID || player.getItemInUse().itemID == Tools.bow.itemID || player.getItemInUse().itemID == Tools.crossbow.itemID)) {
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
