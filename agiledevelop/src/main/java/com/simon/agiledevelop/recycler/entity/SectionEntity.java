package com.simon.agiledevelop.recycler.entity;

/**
 * describe:
 *
 * @author Simon Han
 * @date 2015.09.18
 * @email hanzx1024@gmail.com
 * @param <T>
 */

public abstract class SectionEntity<T> {
    public boolean isHeader;
    public T t;
    public String header;

    public SectionEntity(boolean isHeader, String header) {
        this.isHeader = isHeader;
        this.header = header;
        this.t = null;
    }

    public SectionEntity(T t) {
        this.isHeader = false;
        this.header = null;
        this.t = t;
    }
}
