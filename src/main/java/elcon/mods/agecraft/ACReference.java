package elcon.mods.agecraft;

public class ACReference {

	public static final boolean DEBUG_MODE = true;
	
	public static final String MOD_ID = "AgeCraft";
	public static final String NAME = "AgeCraft";
	public static final String VERSION = "${version} (build ${buildnumber})";
	public static final String MC_VERSION = "[${mcversion}]";
	public static final String DEPENDENCIES = "required-after:Forge@[10.12.0.967,);required-after:ElConCore";
	public static final String SERVER_PROXY_CLASS = "elcon.mods.agecraft.ACCommonProxy";
    public static final String CLIENT_PROXY_CLASS = "elcon.mods.agecraft.ACClientProxy";
    
    public static final String VERSION_URL = "https://raw.github.com/AgeCraft/AgeCraft/master/version.xml";
}
