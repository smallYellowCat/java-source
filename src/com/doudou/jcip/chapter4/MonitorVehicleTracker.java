package com.doudou.jcip.chapter4;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 基于监视器的车辆追踪器
 * @author 豆豆
 * @date 2019/5/16 11:34
 * @flag 以万物智能，化百千万亿身
 */
@ThreadSafe
public class MonitorVehicleTracker {

    @GuardedBy("this")
    private final Map<String, MutablePoint> locations;

    public MonitorVehicleTracker(Map<String, MutablePoint> locations){
        this.locations = deepCopy(locations);
    }

    public synchronized  Map<String, MutablePoint> getLocations(){
        return deepCopy(locations);
    }

    public synchronized MutablePoint getLocation(String id){
        MutablePoint loca = locations.get(id);
        return loca == null ? null : new MutablePoint(loca);
    }

    public synchronized void setLocation(String id, int x, int y){
        MutablePoint loca = locations.get(id);
        if (loca == null){
            throw  new IllegalArgumentException("NO SUCH ID:" + id);
        }

        loca.x = x;
        loca.y = y;
    }

    /**
     *
     * @param m
     * @return
     */
    private static Map<String, MutablePoint> deepCopy(Map<String, MutablePoint> m){
        Map<String, MutablePoint> result = new HashMap<>();
        for (String id : m.keySet()){
            result.put(id, new MutablePoint(m.get(id)));
        }
        //返回一个不可修改的Map视图，只读，尝试去对该map进行写操作会抛出UnsupportedOperationException异常
        return Collections.unmodifiableMap(result);
    }
}
