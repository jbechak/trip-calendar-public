package com.example.springboot.utility;

import java.util.UUID;

public class General {

    public static String getGuid() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }
}
