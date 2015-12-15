package amidst;

import amidst.logging.Log;

public enum Type {
	DEFAULT("Default", "default"), FLAT("Flat", "flat"), LARGE_BIOMES("Large Biomes",
			"largeBiomes"), AMPLIFIED("Amplified", "amplified"), CUSTOMIZED("Customized", "customized");
	private final String name;
	private final String value;

	Type(String name, String value) {
		this.name = name;
		this.value = value;
	}

	@Override
	public String toString() {
		return name;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	public static Type fromMixedCase(String name) {
		name = name.toLowerCase();
		for (Type t : values())
			if (t.name.toLowerCase().equals(name) || t.value.toLowerCase().equals(name))
				return t;
		Log.crash("Unable to find World Type: " + name);
		return null;
	}

}
