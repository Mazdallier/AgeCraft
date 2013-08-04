package elcon.mods.agecraft.util;

import java.util.logging.Level;

import cpw.mods.fml.common.FMLCommonHandler;
import elcon.mods.agecraft.ACLog;

public class Vect {
	
	public int x;
	public int y;
	public int z;

	public Vect(int[] dim) {
		if(dim.length != 3) {
			ACLog.info("Cannot instantiate a vector with less or more than 3 points.");
		}
		this.x = dim[0];
		this.y = dim[1];
		this.z = dim[2];
	}

	public Vect(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vect add(Vect other) {
		Vect result = new Vect(this.x, this.y, this.z);
		result.x += other.x;
		result.y += other.y;
		result.z += other.z;
		return result;
	}

	public Vect multiply(float factor) {
		Vect result = new Vect(this.x, this.y, this.z);
		result.x = ((int) (result.x * factor));
		result.y = ((int) (result.y * factor));
		result.z = ((int) (result.z * factor));
		return result;
	}

	public String toString() {
		return String.format("%sx%sx%s;", new Object[]{Integer.valueOf(this.x), Integer.valueOf(this.y), Integer.valueOf(this.z)});
	}

	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = 31 * result + this.x;
		result = 31 * result + this.y;
		result = 31 * result + this.z;
		return result;
	}

	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		Vect other = (Vect) obj;
		if(this.x != other.x)
			return false;
		if(this.y != other.y)
			return false;
		if(this.z != other.z)
			return false;
		return true;
	}
}
