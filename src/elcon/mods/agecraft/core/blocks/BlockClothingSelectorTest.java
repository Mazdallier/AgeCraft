package elcon.mods.agecraft.core.blocks;

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
			boolean[] changeable = new boolean[ClothingRegistry.types.length];
			for(int i = 0; i < ClothingRegistry.types.length; i++) {
				if(ClothingRegistry.types[i] != null) {
					changeable[i] = true;
				}
			}
			PacketDispatcher.sendPacketToPlayer(ACPacketHandler.getClothingSelectorOpenPacket(changeable), (Player) player);
		}
		return true;
	}
}
