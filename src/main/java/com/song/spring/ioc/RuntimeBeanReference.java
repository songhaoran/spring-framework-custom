package com.song.spring.ioc;

/**
 * Created by Song on 2020/09/14.
 */
public class RuntimeBeanReference {
    private String ref;

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public RuntimeBeanReference(String ref) {
        super();
        this.ref = ref;
    }
}
