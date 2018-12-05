package com.ccit.enums;

import java.util.Hashtable;
import java.util.Map;


public enum PerformanceInterval {

    DAILY(300, "Daily"),
    TRIPLE_DAY(900, "TripleDay"),
    WEEKLY(1800, "Weekly"),
    MONTHLY(7200, "Monthly"),
    QUARTERLY(21600, "Quarterly"),
    SEMIANNUAL(43200, "Semiannual"),
    ANNUAL(86400, "Annual");

    private Integer code;
    private String name;

    private static Map<Integer, PerformanceInterval> codeRelMap = new Hashtable<>();
    private static Map<String, PerformanceInterval> nameRelMap = new Hashtable<>();

    static {
        for (PerformanceInterval performanceInterval : PerformanceInterval.values()) {
            codeRelMap.put(performanceInterval.getCode(), performanceInterval); //根据code关联对应的枚举项
            nameRelMap.put(performanceInterval.getName(), performanceInterval); //根据name关联对应的枚举项
        }
    }

    PerformanceInterval(Integer code, String name) {
        this.code = code;
        this.name = name;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public static PerformanceInterval getByCode(Integer code) {
        return codeRelMap.get(code);
    }

    public static PerformanceInterval getByName(String name){
        return nameRelMap.get(name);
    }

}
