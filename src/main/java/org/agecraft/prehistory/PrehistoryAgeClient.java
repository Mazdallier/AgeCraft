package org.agecraft.prehistory;

import org.agecraft.Age;
import org.agecraft.AgeClient;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class PrehistoryAgeClient extends AgeClient {

	public PrehistoryAgeClient(Age age) {
		super(age);
	}
}
