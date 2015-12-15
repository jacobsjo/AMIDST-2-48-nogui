package amidst;

import java.io.File;
import java.util.prefs.Preferences;

import org.kohsuke.args4j.Option;

import amidst.preferences.FilePrefModel;

/** Currently selected options that change AMIDST’s behavior
 */
public enum Options {
	instance;
	
	//per-run preferences. TODO: store elsewhere?
	public long seed;
	public String seedText;
	
	//permanent preferences
	public final FilePrefModel jar;

	private Preferences preferences;
	
	@Option (name="-mcpath", usage="Sets the path to the .minecraft directory.", metaVar="<path>")
	public String minecraftPath;
	
	@Option (name="-mcjar", usage="Sets the path to the minecraft .jar", metaVar="<path>")
	public String minecraftJar;
	
	@Option (name="-mcjson", usage="Sets the path to the minecraft .json", metaVar="<path>")
	public String minecraftJson;

	@Option (name="-mclibs", usage="Sets the path to the libraries/ folder", metaVar="<path>")
	public String minecraftLibraries;
	
	@Option (name="-seed", usage="Sets the baseseed to check, overwritten if -seedfile is present", metaVar="<seed>")
	public String seedConfig;
	
	@Option (name="-x", usage="Sets the x-Coordinate of the Stronhhold to check", metaVar="<x>")
	public String xCoord;
	
	@Option (name="-z", usage="Sets the z-Coordinate of the Stronhhold to check", metaVar="<z>")
	public String zCoord;
	
	@Option (name="-seedfile", usage="Sets the file to reed paseseeds from", metaVar="<path>")
	public String seedfile;

	@Option (name="-outputfile", usage="Sets the file to reed paseseeds from", metaVar="<path>")
	public String outputfile;
	
	
	
	
	private Options() {
		seed = 0L;
		seedText = null;
		
		
		Preferences pref = Preferences.userNodeForPackage(Amidst.class);
		preferences = pref;
		jar				     = new FilePrefModel(   pref, "jar", new File(Util.minecraftDirectory, "bin/minecraft.jar"));
				
	}
	
	public Preferences getPreferences() {
		return preferences;
	}
	
	public File getJar() {
		return jar.get();
	}
	
	public String getSeedMessage() {
		if (seedText == null)
			return "Seed: " + seed;
		return "Seed: \"" + seedText + "\" (" + seed +  ")";
	}
}
