package com.xiao.conn;

import android.os.Environment;

import com.xiao.conn.tools.CtoTools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

public class CtoDefaultCache {

    private static CtoDefaultCache instant = null;

    public static CtoDefaultCache getInstance(){
        if (null == instant){
            instant = new CtoDefaultCache();
        }
        return instant;
    }

    private String root = Cto.class.getSimpleName();

    private File createFileDir(String dir) {
        StringBuilder sb = new StringBuilder();
        if (CtoTools.isSDCardCanUse()) {
            sb.append(Environment.getExternalStorageDirectory().getAbsolutePath());
        } else {
            sb.append(RequestManager.getInstance().getAdmin().getContext().getCacheDir().getAbsolutePath());
        }
        sb.append(File.separator);
        sb.append(root);
        sb.append(File.separator);
        sb.append(dir);

        File file = new File(sb.toString());
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    private static String cacheName = null;

    private File getCacheDir() {
        if (null == cacheName){
            Admin admin = RequestManager.getInstance().getAdmin();
            cacheName = admin.getContext().getPackageName();
        }
        return createFileDir(cacheName);
    }

    public String get(String key) {
        BufferedReader bf = null;
        try {
            File cacheFile = new File(getCacheDir(), key);
            if (cacheFile.exists()) {
                bf = new BufferedReader(new FileReader(cacheFile));
                StringWriter sw = new StringWriter();
                String temp;
                while ((temp = bf.readLine()) != null) {
                    sw.write(temp);
                }
                return sw.toString();
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (bf != null)
                    bf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean delete(String key){
        try {
            File cacheFile = new File(getCacheDir(), key);
            if (cacheFile.exists()) {
                return cacheFile.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /** package */ boolean deleteAll(){
        return getCacheDir().delete();
    }

    public void save(String key, String content) {
        BufferedWriter bw = null;
        try {
            File cacheFile = new File(getCacheDir(), key);
            bw = new BufferedWriter(new FileWriter(cacheFile));
            bw.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null)
                    bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
