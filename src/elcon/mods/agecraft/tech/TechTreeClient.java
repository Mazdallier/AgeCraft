package elcon.mods.agecraft.tech;

import java.util.ArrayList;

import elcon.mods.agecraft.ACTickHandlerClient;

public class TechTreeClient {
	
	public static ArrayList<String> unlockedTechComponents = new ArrayList<String>();
	
	public static void unlockComponentStartup(String key) {
		if((unlockedTechComponents == null) || (!unlockedTechComponents.contains(key))) {
			if(unlockedTechComponents == null) {
				unlockedTechComponents = new ArrayList();
			}
			unlockedTechComponents.add(key);
		}
	}
	
	public static void unlockComponent(String key) {
		if((unlockedTechComponents == null) || (!unlockedTechComponents.contains(key))) {
			if(unlockedTechComponents == null) {
				unlockedTechComponents = new ArrayList();
			}
			unlockedTechComponents.add(key);
			ACTickHandlerClient.techTreePopup.queueTakenTechTreeComponent(TechTree.getTechTreeComponent(key));
		}
	}
	
	public static void lockComponent(String key) {
		if((unlockedTechComponents == null) || (unlockedTechComponents.contains(key))) {
			if(unlockedTechComponents == null) {
				unlockedTechComponents = new ArrayList();
				return;
			}
			unlockedTechComponents.remove(key);
		}
	}
}
