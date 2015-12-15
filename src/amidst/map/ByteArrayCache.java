package amidst.map;

import java.util.Vector;

public class ByteArrayCache extends CacheManager {
	public static final int CACHE_SIZE = 32, CACHE_SHIFT = 5;
	public static final int HEADER_SIZE = (CACHE_SIZE*CACHE_SIZE) >> 3;
	public static final int CACHE_MAX_SIZE = CACHE_SIZE*CACHE_SIZE; // TODO : Change name to CACHE_LENGTH ?
	
	private int maxUnits;
	private byte unitSize;
	
	private Vector<ByteArrayHub> cacheMap;
	
	private byte[] byteCache; 
	
	public ByteArrayCache(byte unitSize, int maxUnits) {
		cacheMap = new Vector<ByteArrayHub>();
		this.unitSize = unitSize;
		this.maxUnits = maxUnits;
		byteCache = new byte[unitSize*maxUnits];
	}
	
	private ByteArrayHub getHub(long key) {
		for (ByteArrayHub hub : cacheMap) {
			if (hub.getKey() == key) 
				return hub;
		}
		return null;
	}

	// JS Shortcut
	// TODO : Add more?
	
	public byte[] intToCachedBytes(int[] data) {
		for (int i = 0; i < byteCache.length; i++) {
			byteCache[i] = (byte) data[i];
		}
		return byteCache;
	}
	
}
