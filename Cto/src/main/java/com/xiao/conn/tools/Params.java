package com.xiao.conn.tools;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;

public final class Params {

    public Params() {}

    public Params(final String key, final String value) {
        put(key,value);
    }

    public Params(final String key, final int value) {
        put(key,value);
    }

    public Params(final String key, final double value) {
        put(key,value);
    }

    public Params(final String key, final float value) {
        put(key,value);
    }

    public Params(final String key, final char value) {
        put(key,value);
    }

    public Params(final String key, final long value) {
        put(key,value);
    }

    public Params(final String key, final boolean value) {
        put(key,value);
    }

    private final ArrayList<ParamsEntry> params = new ArrayList<>(8);
    private final ArrayList<ParamsEntry> headers = new ArrayList<>(4);

    public void putHeaders(final String key, final String value) {
        headers.add(new ParamsEntry(key, value));
    }

    public void put(final String key, final String value) {
        params.add(new ParamsEntry(key, value));
    }

    public void put(final String key, final int value) {
        put(key,String.valueOf(value));
    }

    public void put(final String key, final double value) {
        put(key,String.valueOf(value));
    }

    public void put(final String key, final float value) {
        put(key,String.valueOf(value));
    }

    public void put(final String key, final char value) {
        put(key,String.valueOf(value));
    }

    public void put(final String key, final long value) {
        put(key,String.valueOf(value));
    }

    public void put(final String key, final boolean value) {
        put(key,String.valueOf(value));
    }

    public StringBuilder getUrlParams(boolean encode) {
        StringBuilder result = new StringBuilder();
        boolean isFirst = true;
        Collections.sort(params);
        try {
            for (ParamsEntry entry : params) {
                if (!isFirst) {
                    result.append("&");
                } else {
                    result.append("?");
                    isFirst = false;
                }
                result.append(entry.k).append("=");
                if (encode) {
                    result.append(URLEncoder.encode(entry.v, "UTF-8"));
                } else {
                    result.append(entry.v);
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<ParamsEntry> getHeaders() {
        return headers;
    }
}
