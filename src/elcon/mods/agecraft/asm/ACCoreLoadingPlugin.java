package elcon.mods.agecraft.asm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.jar.JarFile;

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
		ACAccessTransformer.addTransformerMap("agecraft_at.cfg");
		return null;
	}

	private File extractTemp(JarFile jar, String mapFile) throws IOException {
		File temp = new File("temp.dat");
		if(!temp.exists()) {
			temp.createNewFile();
		}
		FileOutputStream fout = new FileOutputStream(temp);
		byte[] data = new byte[4096];
		int read = 0;
		InputStream fin = jar.getInputStream(jar.getEntry(mapFile));
		while((read = fin.read(data)) > 0) {
			fout.write(data, 0, read);
		}
		fin.close();
		fout.close();
		return temp;
	}
}
