package elcon.mods.agecraft.asm;

import java.io.File;
import java.util.Map;

import cpw.mods.fml.relauncher.IFMLCallHook;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

public class ACCoreLoadingPlugin implements IFMLLoadingPlugin, IFMLCallHook {

	public static File minecraftDir;

	@Override
	public String[] getLibraryRequestClass() {
		return null;
	}

	@Override
	public String[] getASMTransformerClass() {
		return new String[]{
			"elcon.mods.agecraft.asm.ACCoreAccessTransformer"
		};
	}

	@Override
	public String getModContainerClass() {
		return "elcon.mods.agecraft.asm.ACCoreContainer";
	}

	@Override
	public String getSetupClass() {
		return "elcon.mods.agecraft.asm.ACCoreLoadingPlugin";
	}

	@Override
	public void injectData(Map<String, Object> data) {
		if(data.containsKey("mcLocation"))
			minecraftDir = (File) data.get("mcLocation");
	}

	@Override
	public Void call() throws Exception {
		ACCoreAccessTransformer.addTransformerMap("agecraft_at.cfg");
		return null;
	}
}
