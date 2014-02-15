package org.agecraft.core.party;

import java.util.HashMap;

public class PartyManager {

	public HashMap<String, Party> parties = new HashMap<String, Party>();
	
	public Party getParty(String name) {
		if(parties.containsKey(name)) {
			return parties.get(name);
		}
		return null;
	}
	
	public void addParty(String name, Party party) {
		parties.put(name, party);
	}
	
	public void removeParty(String name) {
		parties.remove(name);
	}
}
