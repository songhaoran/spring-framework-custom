package com.song.spring.resource;

import java.io.InputStream;

/**
 * classpth 下resource加载类
 */
public class ClasspathResource implements Resource {

    private String location;

    public ClasspathResource(String location) {
        this.location = location;
    }

    @Override
    public InputStream getResource() {
        if (location.startsWith("classpath:")) {
            location = location.substring(10);
        }
        return this.getClass().getClassLoader().getResourceAsStream(location);
    }

    public static void main(String[] args) {
        String s = "classpath:springmvc.xml";
        s = s.substring(10);
        System.out.println(s);
    }
}
