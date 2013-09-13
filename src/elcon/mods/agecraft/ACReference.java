package elcon.mods.agecraft;

public class ACReference {

	public static final boolean DEBUG_MODE = true;
	
	public static final String MOD_ID = "AgeCraft";
	public static final String NAME = "AgeCraft";
	public static final String VERSION = "@VERSION@ (build @BUILD_NUMBER@)";
	public static final String MC_VERSION = "[1.6.2]";
	public static final String DEPENDENCIES = "required-after:Forge@[9.10.0.799,);required-after:ElConCore";
	public static final String SERVER_PROXY_CLASS = "elcon.mods.agecraft.ACCommonProxy";
    public static final String CLIENT_PROXY_CLASS = "elcon.mods.agecraft.ACClientProxy";
    
    public static final String VERSION_URL = "https://raw.github.com/AgeCraft/AgeCraft/master/version.xml";
    
    public static final int SECOND_IN_TICKS = 20;
}
