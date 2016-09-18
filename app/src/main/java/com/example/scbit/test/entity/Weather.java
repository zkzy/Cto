package com.example.scbit.test.entity;

import com.example.scbit.test.R;
import com.xiao.conn.annotation.CtoModel;
import com.xiao.conn.enu.Expired;
import com.xiao.conn.enu.Method;
import com.xiao.conn.enu.Pattern;

import java.util.ArrayList;
import java.util.List;

@CtoModel(
        url = R.string.url_weather,
        //method = Method.GET,
        cacheExpired = 1000 * 20,
        what = Weather.TAG,
        pattern = Pattern.BEFORE_RETURN_CACHE | Pattern.FAIL_RETURN_CACHE,
        classes = {APIError.class}
)
public class Weather {

    public static final String TAG = "天气";

    public String msg;
    public String retCode;
    public List<ResultBean> result;

    public static class ResultBean {
        public String airCondition;
        public String city;
        public String coldIndex;
        public String date;
        public String distrct;
        public String dressingIndex;
        public String exerciseIndex;
        public String humidity;
        public String pollutionIndex;
        public String province;
        public String sunrise;
        public String sunset;
        public String temperature;
        public String time;
        public String updateTime;
        public String washIndex;
        public String weather;
        public String week;
        public String wind;
        public List<FutureBean> future;

        public static class FutureBean {
            public String date;
            public String dayTime;
            public String night;
            public String temperature;
            public String week;
            public String wind;
        }
    }
}
