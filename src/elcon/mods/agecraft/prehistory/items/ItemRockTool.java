package elcon.mods.agecraft.prehistory.items;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.AgeCraft;
import elcon.mods.agecraft.lang.LanguageManager;

public class ItemRockTool extends Item {

	public static Block[] blocksEffectiveAgainst = new Block[]{};
	
	private Icon icon;
	private Icon iconUp;
	private Icon iconDown;
	
	public ItemRockTool(int i) {
		super(i);
		setMaxDamage(32);
		setMaxStackSize(1);
		setDamage(new ItemStack(this), 1);
	}
	
	@Override
	public String getItemDisplayName(ItemStack stack) {
		return getItemStackDisplayName(stack);
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return LanguageManager.getLocalization(getUnlocalizedName(stack));
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "item.rockTool.name";
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int i) {
		return icon;
	}
	
	@Override
	public Icon getIcon(ItemStack stack, int pass) {
		if(stack.hasTagCompound() && stack.stackTagCompound.getInteger("Type") == 1) {
			return iconDown;
		}
		return iconUp;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if(!world.isRemote) {
			if(entityplayer.isSneaking()) {
				entityplayer.openGui(AgeCraft.instance, 1, world, (int) entityplayer.posX, (int) entityplayer.posY, (int) entityplayer.posZ);
				return itemstack;
			}			
			int i = 0;
			if(itemstack.hasTagCompound()) {
				i = itemstack.stackTagCompound.getInteger("Type");
				i = i == 0 ? 1 : 0;
				itemstack.stackTagCompound.setInteger("Type", i);
				icon = i == 0 ? iconUp : iconDown;
				return itemstack;
			}
			itemstack.setTagCompound(new NBTTagCompound());
			icon = iconDown;
			itemstack.stackTagCompound.setInteger("Type", 1);
			return itemstack;
		}
		return itemstack;
	}
	
	@Override
	public boolean canHarvestBlock(Block block) {
		for(int i = 0; i < blocksEffectiveAgainst.length; i++) {
			if(blocksEffectiveAgainst[i] == block) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public float getStrVsBlock(ItemStack stack, Block block) {
		for(int i = 0; i < blocksEffectiveAgainst.length; i++) {
			if(blocksEffectiveAgainst[i] == block) {
				return 2.0F;
			}
		}
		return 0.1F;
	}

	@Override
	public boolean hitEntity(ItemStack itemstack, EntityLivingBase entity1, EntityLivingBase entity2) {
		if(itemstack.hasTagCompound() && itemstack.stackTagCompound.getInteger("Type") == 1) {
			itemstack.damageItem(1, entity2);
		}
		itemstack.damageItem(2, entity2);
		return true;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack itemstack, World world, int id, int x, int y, int z, EntityLivingBase entiy) {
		if(itemstack.hasTagCompound() && itemstack.stackTagCompound.getInteger("Type") == 1) {
			itemstack.damageItem(2, entiy);
		} else {
			if((double) Block.blocksList[id].getBlockHardness(world, x, y, z) != 0.0D) {
				itemstack.damageItem(1, entiy);
			}
		}
		return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		iconUp = iconRegister.registerIcon("agecraft:ages/prehistory/rock_tool_0");
		iconDown = iconRegister.registerIcon("agecraft:ages/prehistory/rock_tool_1");
		
		icon = iconUp;
	}
}
