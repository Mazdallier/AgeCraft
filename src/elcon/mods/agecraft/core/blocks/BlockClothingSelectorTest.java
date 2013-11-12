package elcon.mods.agecraft.core.blocks;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import elcon.mods.agecraft.ACCreativeTabs;
import elcon.mods.agecraft.ACPacketHandler;
import elcon.mods.agecraft.core.clothing.ClothingRegistry;

public class BlockClothingSelectorTest extends Block {

	public BlockClothingSelectorTest(int id) {
		super(id, Material.iron);
		setCreativeTab(ACCreativeTabs.ageCraft);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(!world.isRemote) {
			ArrayList<String> changeable = new ArrayList<String>();
			changeable.addAll(ClothingRegistry.types.keySet());
			PacketDispatcher.sendPacketToPlayer(ACPacketHandler.getClothingSelectorOpenPacket(changeable), (Player) player);
		}
		return true;
	}
}
