package com.yhxc.utils;

import java.util.UUID;

/**
 * uuid类
 */
public class UuidUtil {

    /**
     * 32 位uuid
     * @return
     */
    public static String get32UUID() {
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        return uuid;
    }
}
