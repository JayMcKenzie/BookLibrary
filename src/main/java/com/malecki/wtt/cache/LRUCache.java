package com.malecki.wtt.cache;

import com.malecki.wtt.classes.BookExport;

import java.util.LinkedHashSet;
import java.util.Set;

public class LRUCache {
    private Set<BookExport> cache;
    private final int SIZE;

    private long lastClearTime = System.currentTimeMillis();

    private void clearCache(){
        long currentTime = System.currentTimeMillis();
        if(currentTime - lastClearTime >= 3600000){
            cache.clear();
            while(currentTime - lastClearTime >= 3600000)
                lastClearTime += 3600000;
        }
    }

    public Set<BookExport> getCache() {
        clearCache();
        return cache;
    }

    public void setCache(Set<BookExport> cache) {
        this.cache = cache;
    }

    public LRUCache(int size)
    {
        this.cache = new LinkedHashSet<>(size);
        this.SIZE = size;
    }

    public void put(BookExport key)
    {
        clearCache();
        if (cache.size() == SIZE) {
            BookExport firstKey = cache.iterator().next();
            cache.remove(firstKey);
        }
        cache.add(key);
    }
}
