package ar.com.rp.rpcutils;

import java.rmi.RemoteException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class cacheManager {

	private static Cache<String, Object> cache = null;

	public static Cache<String, Object> getInstance() {
		if (cache == null) {
			cache = CacheBuilder.newBuilder().expireAfterWrite(12, TimeUnit.HOURS).build();
		}
		return cache;
	}

	public static Object getCache(String key) throws RemoteException {
		return getInstance().getIfPresent(key);
	}

	public static void setCache(String key, Object value) {
		getInstance().put(key, value);
	}

	public static void deleteCache(String key) {
		getInstance().invalidate(key);
	}

	public static void cleanCache() {
		getInstance().invalidateAll();
	}

}