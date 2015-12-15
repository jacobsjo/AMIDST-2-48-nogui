package amidst;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import com.google.gson.Gson;

import amidst.logging.Log;
import amidst.map.layers.StrongholdLayer;
import amidst.minecraft.Minecraft;
import amidst.minecraft.MinecraftUtil;

public class Amidst {
	public final static int version_major = 3;
	public final static int version_minor = 7;
	public final static String versionOffset = "";
	public static final Gson gson = new Gson();

	public static void main(String args[]) {
		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread thread, Throwable e) {
				Log.crash(e, "Amidst has encounted an uncaught exception on thread: " + thread);
			}
		});
		CmdLineParser parser = new CmdLineParser(Options.instance);
		try {
			parser.parseArgument(args);
		} catch (CmdLineException e) {
			Log.w("There was an issue parsing command line options.");
			e.printStackTrace();
		}
		Util.setMinecraftDirectory();
		Util.setMinecraftLibraries();

		System.setProperty("sun.java2d.opengl", "True");
		System.setProperty("sun.java2d.accthreshold", "0");

		if (Options.instance.minecraftJar != null) {
			try {
				Util.setProfileDirectory(Options.instance.minecraftPath);
				new StrongholdLayer();
				MinecraftUtil
						.setBiomeInterface(new Minecraft(new File(Options.instance.minecraftJar)).createInterface());

				if (Options.instance.outputfile == null){
					Log.crash("A output file has to be specified in command line option -outputfile");
				}
				
				if (Options.instance.seedfile != null) {
					checkFile();
				} else {
					if (Options.instance.xCoord == null || Options.instance.zCoord == null
							|| Options.instance.seedConfig == null) {
						Log.crash(
								"Eather a seedfile (-seedfile <path>) or seed and coordinates (-seed <seed> -x <value> -z <value> have to be specified using commandline options.");
					} else {
						checkSeed();
					}
				}

			} catch (MalformedURLException e) {
				Log.crash(e, "MalformedURLException on Minecraft load.");
			}
		} else {
			Log.crash("Minecraft Jar has to be set using command line option -mcjar");
		}
	}

	public static void checkSeed() {
		FileOutputStream ostream = null;
		DataOutputStream out = null;
		BufferedWriter bw = null;
		try {
			ostream = new FileOutputStream(Options.instance.outputfile);
			out = new DataOutputStream(ostream);
			bw = new BufferedWriter(new OutputStreamWriter(out));

			List<Point> strongholdPositions = new ArrayList<Point>();
			strongholdPositions.add(
					new Point(Integer.parseInt(Options.instance.xCoord), Integer.parseInt(Options.instance.zCoord)));
			check(Long.parseLong(Options.instance.seedConfig), strongholdPositions, 1000000, true, bw, 0);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (out != null) {
				try {
					bw.close();
					out.close();
					ostream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void checkFile() {
		FileInputStream fstream = null;
		FileOutputStream ostream = null;
		DataInputStream in = null;
		DataOutputStream out = null;
		BufferedReader br = null;
		BufferedWriter bw = null;
		String strLine;
		long oldSeed;
		int oldCount;
		try {
			ostream = new FileOutputStream(Options.instance.outputfile);
			fstream = new FileInputStream(Options.instance.seedfile);
			out = new DataOutputStream(ostream);
			in = new DataInputStream(fstream);
			br = new BufferedReader(new InputStreamReader(in));
			bw = new BufferedWriter(new OutputStreamWriter(out));

			List<Point> strongholdPositions = new ArrayList<Point>();

			strLine = br.readLine();
			if (strLine == null)
				return;
			String[] tokens = strLine.split(" ");
			strongholdPositions.add(new Point(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3])));
			oldSeed = Long.parseLong(tokens[0]);
			oldCount = Integer.parseInt(tokens[1]);

			while ((strLine = br.readLine()) != null) {
				tokens = strLine.split(" ");

				if (oldSeed != Long.parseLong(tokens[0])) {
					check(oldSeed, strongholdPositions, 20000, false, bw, oldCount);
					strongholdPositions.clear();
					oldSeed = Long.parseLong(tokens[0]);
					oldCount = Integer.parseInt(tokens[1]);
				}
				strongholdPositions.add(new Point(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3])));
			}
			check(Long.parseLong(tokens[0]), strongholdPositions, 10000, false, bw, Integer.parseInt(tokens[1]));
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					br.close();
					in.close();
					fstream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					bw.close();
					out.close();
					ostream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void check(long baseseed, List<Point> strongholdPositions, int maxseeds, boolean checkAll,
			BufferedWriter out, int filledCount) throws IOException {
		for (long seed = baseseed; seed < baseseed + maxseeds * 281474976710656L; seed += 281474976710656L) {
			MinecraftUtil.createWorld(seed, "default");
			Point pos = StrongholdLayer.instance.findStronghold();
			if (pos != null) {
				Iterator<Point> point = strongholdPositions.iterator();

				while (point.hasNext()) {
					Point next = point.next();
					if (pos.x == next.x && pos.y == next.y) {
						if (!checkAll)
							point.remove();
						System.out.println(baseseed + " -> " + seed + " " + filledCount + " | " + pos.x + " " + pos.y + " -> " + (pos.x << 4) + " " + (pos.y << 4));
						out.write(baseseed + " -> " + seed + " " + filledCount + " | " + pos.x + " " + pos.y + " -> " + (pos.x << 4) + " " + (pos.y << 4));
						out.newLine();
					}
				}

				if (strongholdPositions.isEmpty())
					break;
			}
		}
	}

	public static boolean isOSX() {
		String osName = System.getProperty("os.name");
		return osName.contains("OS X");
	}

	public static String version() {
		return version_major + "." + version_minor + versionOffset;
	}

}
