package amidst.minecraft;

/**
 * Acts as an additional layer of abstraction for interfacing with Minecraft.<br>
 * This allows for other sources of data other than direct reflection against a loaded jar of Minecraft.
 */
public interface IMinecraftInterface {
	/**
	 * @param useQuarterResolutionMap
	 * Minecraft calculates biomes at quarter-resolution, then noisily interpolates the biome-map up to
	 * 1:1 resolution when needed, set useQuarterResolutionMap to true to read from the quarter-resolution
	 * map, or false to read values that have been interpolated up to full resolution.
	 * 
	 * When useQuarterResolutionMap is true, the x, y, width, and height paramaters must all 
	 * correspond to a quarter of the Minecraft block coordinates/sizes you wish to obtain the 
	 * biome data for.
	 *  
	 * AMIDST displays the quarter-resolution biome map, however full resolution is required to
	 * determine the position and nature of structures, as the noisy interpolation can change
	 * which biome a structure is located in (if the structure is located on a biome boundary).
	 */
	public int[] getBiomeData(int x, int y, int width, int height, boolean useQuarterResolutionMap);
	public void createWorld(long seed, String type, String generatorOptions);
}
