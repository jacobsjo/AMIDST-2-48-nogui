package amidst.map.layers;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import amidst.Options;
import amidst.minecraft.Biome;
import amidst.minecraft.MinecraftUtil;

public class StrongholdLayer{
	public static StrongholdLayer instance;

	public StrongholdLayer() {
		instance = this;
	}

	public Point findStronghold() {
		Random random = new Random();
		random.setSeed(Options.instance.seed);

		List<Biome> biomeArrayList = new ArrayList<Biome>();
		for (int i = 0; i < Biome.biomes.length; i++) {
			if ((Biome.biomes[i] != null) && (Biome.biomes[i].type.value1 > 0f)) {
				biomeArrayList.add(Biome.biomes[i]);
			}
		}

		double angle = random.nextDouble() * 3.141592653589793D * 2.0D;
		for (int i = 0; i < 3; i++) {
			double distance = (1.25D + random.nextDouble()) * 32.0D;
			int x = (int) Math.round(Math.cos(angle) * distance);
			int y = (int) Math.round(Math.sin(angle) * distance);

			Point strongholdLocation = MinecraftUtil.findValidLocation((x << 4) + 8, (y << 4) + 8, 112, biomeArrayList,
					random);
			if (strongholdLocation != null) {
				x = strongholdLocation.x >> 4;
				y = strongholdLocation.y >> 4;

				return new Point(x,y);
			}
			angle += 6.283185307179586D / 3.0D;
		}
		return null;
	}
}
