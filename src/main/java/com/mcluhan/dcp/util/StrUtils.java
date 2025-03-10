package com.mcluhan.dcp.util;

public class StrUtils {

    public static boolean containIgnoreCase(String str, String search) {
        return str.toUpperCase().contains(search);
    }
}
