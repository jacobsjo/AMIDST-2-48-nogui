package amidst.map.layers;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import amidst.Options;
import amidst.logging.Log;
import amidst.minecraft.Biome;
import amidst.minecraft.MinecraftUtil;

public class SpawnLayer {
	public static final ArrayList<Biome> validBiomes = new ArrayList<Biome>(Arrays.asList(
			Biome.forest, 
			Biome.plains, 
			Biome.taiga, 
			Biome.taigaHills, 
			Biome.forestHills, 
			Biome.jungle, 
			Biome.jungleHills
		));
	
	public SpawnLayer() {
	}
		
	private Point getSpawnPosition() {
		Random random = new Random(Options.instance.seed);
		Point location = MinecraftUtil.findValidLocation(0, 0, 256, validBiomes, random);
		int x = 0;
		int y = 0;
		if (location != null) {
			x = location.x;
			y = location.y;
		} else {
			Log.debug("Unable to find spawn biome.");
		}

		return new Point(x, y);
	}
	
}
