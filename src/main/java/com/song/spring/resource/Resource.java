package com.song.spring.resource;

import java.io.InputStream;

/**
 * 提供对资源的操作 (资源来源: 磁盘/网络/classpath下)
 */
public interface Resource {

    InputStream getResource();
}
