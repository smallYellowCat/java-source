package com.doudou.jcip.chapter4;

import net.jcip.annotations.ThreadSafe;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 安全发布底层状态的机动车追踪器
 * @author 豆豆
 * @date 2019/5/16 14:22
 * @flag 以万物智能，化百千万亿身
 */
@ThreadSafe
public class PublishingVehicleTracker {

    private final Map<String, SafePoint> locations;
    private final Map<String, SafePoint> unmodifiableMap;

    public PublishingVehicleTracker(Map<String, SafePoint> locations){
        this.locations = new ConcurrentHashMap<>(locations);
        this.unmodifiableMap = new ConcurrentHashMap<>(this.locations);
    }

    public Map<String, SafePoint> getLocations(){
        return unmodifiableMap;
    }

    public SafePoint getLocation(String id){
        return locations.get(id);
    }

    public void setLocation(String id, int x, int y){
        if (!locations.containsKey(id)){
            throw new IllegalArgumentException("invalid vehicle name: " + id);
        }

        locations.get(id).set(x, y);
    }
}
