package com.example.scbit.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;


import com.example.scbit.test.entity.Weather;
import com.xiao.conn.Cto;
import com.xiao.conn.CtoAdmin;
import com.xiao.conn.FailError;
import com.xiao.conn.CallBack;
import com.xiao.conn.tools.CtoTools;
import com.xiao.conn.tools.Params;

public class MainActivity extends AppCompatActivity {

    protected TextView txvLog;
    protected ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Params params = new Params();
        params.put("key","15ed71f5413c9");
        params.put("city","上海");

        txvLog = (TextView) findViewById(R.id.txvLog);
        scrollView = (ScrollView) findViewById(R.id.scrollView);

        findViewById(R.id.btnStart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i=0;
                for(;;){
                    if (++i > 200){
                        break;
                    }
                    Cto.params(params).call(callBack).create(Weather.class);
                }
            }
        });

        findViewById(R.id.butCancelAll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CtoAdmin.cancelAll();
                l("cancelAll","取消全部请求");
            }
        });
    }


    private CallBack callBack = new CallBack() {

        long time;

        @Override
        public void onBefore() {
            time = System.currentTimeMillis();
            l("onBefore","onBefore  "+getURL()+" "+getMethod()+" "+getWhat());
        }

        @Override
        public void onSuccess(String content) {
            l("onSuccess",content.substring(0,20)+"...");
        }

        @Override
        public void onFail(FailError error) {
            l("onFail",error.errorMessage);
        }

        @Override
        public void onCanceled() {
            l("onCanceled","onCanceled");
        }

        @Override
        public  void onSuccessBean(Object bean) {
            l("onSuccessBean",bean.getClass().getName());
        }

        @Override
        public void onAfter() {
            l("onAfter",""+(System.currentTimeMillis() - time));
        }
    };

    private void l(String tag,String msg){
        txvLog.append(tag+":"+msg+"\n");
        CtoTools.send2UI(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }


}
