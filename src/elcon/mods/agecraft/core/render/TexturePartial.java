package elcon.mods.agecraft.core.render;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.Resource;
import net.minecraft.client.resources.data.AnimationFrame;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.util.Icon;

import com.google.common.collect.Lists;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TexturePartial extends TextureAtlasSprite implements Icon {
	
	public int partMinX;
	public int partMaxX;
	public int partMinY;
	public int partMaxY;

	public TexturePartial(String iconName, int partMinX, int partMaxX, int partMinY, int partMaxY) {
		super(iconName);
		
		this.partMinX = partMinX;
		this.partMaxX = partMaxX;
		this.partMinY = partMinY;
		this.partMaxY = partMaxY;
	}

	public void loadSprite(Resource resource) throws IOException {
		resetSprite();
		InputStream inputStream = resource.getInputStream();
		AnimationMetadataSection animationMetadataSection = (AnimationMetadataSection) resource.getMetadata("animation");
		BufferedImage bufferedImage = ImageIO.read(inputStream);
		height = bufferedImage.getHeight();
		width = bufferedImage.getWidth();
		int[] textureData = new int[height * width];
		bufferedImage.getRGB(0, 0, width, height, textureData, 0, width);

		if(animationMetadataSection == null) {
			if(height != width) {
				throw new RuntimeException("broken aspect ratio and not an animation");
			}
			framesTextureData.add(getPartialFrameTextureData(textureData, width, height));
		} else {
			int ratio = height / width;
			int w = width;
			int h = width;
			height = width;
			int frameIndex;
			if(animationMetadataSection.getFrameCount() > 0) {
				Iterator iterator = animationMetadataSection.getFrameIndexSet().iterator();
				while(iterator.hasNext()) {
					frameIndex = ((Integer) iterator.next()).intValue();
					if(frameIndex >= ratio) {
						throw new RuntimeException("invalid frameindex " + frameIndex);
					}
					allocateFrameTextureData(frameIndex);
					framesTextureData.set(frameIndex, getPartialFrameTextureData(getFrameTextureData(textureData, w, h, frameIndex), w, h));
				}
				animationMetadata = animationMetadataSection;
			} else {
				ArrayList arraylist = Lists.newArrayList();
				for(frameIndex = 0; frameIndex < ratio; frameIndex++) {
					framesTextureData.add(getPartialFrameTextureData(getFrameTextureData(textureData, w, h, frameIndex), w, h));
					arraylist.add(new AnimationFrame(frameIndex, -1));
				}
				animationMetadata = new AnimationMetadataSection(arraylist, width, height, animationMetadataSection.getFrameTime());
			}
		}
	}

	public void allocateFrameTextureData(int size) {
		if(framesTextureData.size() <= size) {
			for(int j = framesTextureData.size(); j <= size; ++j) {
				framesTextureData.add((Object) null);
			}
		}
	}

	public int[] getFrameTextureData(int[] textureData, int width, int height, int frame) {
		int[] frameTextureData = new int[width * height];
		System.arraycopy(textureData, frame * frameTextureData.length, frameTextureData, 0, frameTextureData.length);
		return frameTextureData;
	}
	
	public int[] getPartialFrameTextureData(int[] textureData, int width, int height) {
		int[] partialTextureData = new int[width * height];
		//Arrays.fill(partialTextureData, 0x00FFFFFF);
		for(int i = partMinX; i <= partMaxX; i++) {
			for(int j = partMinY; j <= partMaxY; j++) {
				System.out.println(Integer.toString(textureData[i + j * width], 16));
				partialTextureData[i + j * width] = textureData[i + j * width];
			}
		}
		return partialTextureData;
	}
}
