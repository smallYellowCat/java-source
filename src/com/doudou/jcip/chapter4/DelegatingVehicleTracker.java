package com.doudou.jcip.chapter4;

import net.jcip.annotations.ThreadSafe;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 委托车辆跟踪器,基于委托的追踪器
 * @author 豆豆
 * @date 2019/5/16 11:26
 * @flag 以万物智能，化百千万亿身
 */
@ThreadSafe
public class DelegatingVehicleTracker {

    private final ConcurrentHashMap<String, Point> locations;
    private final Map<String, Point> unmodifiableMap;

    public DelegatingVehicleTracker(Map<String, Point> points){
        locations = new ConcurrentHashMap<>(points);
        unmodifiableMap = Collections.unmodifiableMap(locations);
    }

    public Map<String, Point> getLocations(){
        return unmodifiableMap;
    }

    public Point getLocation(String id){
        return locations.get(id);
    }

    public void setLocations(String id, int x, int y){
        if (locations.replace(id, new Point(x, y)) == null){
            throw new IllegalArgumentException("invalid vehicle name : " + id);
        }
    }
}
