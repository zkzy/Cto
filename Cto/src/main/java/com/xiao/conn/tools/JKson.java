package com.xiao.conn.tools;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;

/**
 * JSON 处理类
 * dependencies - jackson-all-1.9.10.jar
 * ObjectMapper 还有很多方法可以使用
 * Time：2016.07.18 pm
 */
public final class JKson {

    public ObjectMapper objectMapper = new ObjectMapper();

    private static final JKson jkson = new JKson();

    public static JKson Default(){
        return jkson;
    }

    /**
     * @return 格式化后的 JSON 字符串（多行）
     */
    public String toPretty(Object value){
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(value);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return 单行的 JSON 字符串
     */
    public String to(Object value){
        try {
            return objectMapper.writeValueAsString(value);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T from(String src, Class<T> valueType){
        try {
            return objectMapper.readValue(src,valueType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T from(Reader src, Class<T> valueType){
        try {
            return objectMapper.readValue(src,valueType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T from(InputStream src, Class<T> valueType){
        try {
            return objectMapper.readValue(src,valueType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T from(byte[] src, Class<T> valueType){
        try {
            return objectMapper.readValue(src,valueType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 这个方法默认 UI 线程中执行
     */
    public <T> T from(URL src, Class<T> valueType){
        try {
            return objectMapper.readValue(src,valueType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
