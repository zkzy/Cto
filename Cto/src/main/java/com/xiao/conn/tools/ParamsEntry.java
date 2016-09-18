package com.xiao.conn.tools;


public final class ParamsEntry implements Comparable<ParamsEntry>{

    public String k;
    public String v;

    @Override
    public boolean equals(Object o) {
        if (o instanceof ParamsEntry) {
            return k.equals(((ParamsEntry) o).k);
        } else {
            return super.equals(o);
        }
    }

    @Override
    public int hashCode() {
        return k.hashCode();
    }

    public ParamsEntry(String key, String value) {
        k = key;
        v = value;
    }

    @Override
    public int compareTo(ParamsEntry another) {
        if (k == null) {
            return -1;
        } else {
            return k.compareTo(another.k);
        }
    }
}
