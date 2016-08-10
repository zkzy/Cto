# AndroidClientConn
基于 注解 和 链式调用 简化 HttpURLConnection

**一行代码调用**
```Java
Conn.create(Ascription.class).params(params).call(mCall).Go();
```
<br>
##如何使用
<br>
* 第一步【添加权限】
```
    <uses-permission android:name="android.permission.INTERNET"/>
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
```
* 第二步【添加默认队列的管理者】
```
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Queue.defaultQueue().addAdmin(admin);
    }
}
```
* 第三步【创建json模型并使用注解绑定】
```
@ConnModel(url = R.string.url_ascription,what = "获得归属地")
public class Ascription {

    public String msg;
    public ResultBean result;
    public String retCode;

    public static class ResultBean {
        public String city;
        public String cityCode;
        public String mobileNumber;
        public String operator;
        public String province;
        public String zipCode;
    }
}
```
* 第四步【调用】
```
    public void getAscri(View view) {
        HttpParams params = new HttpParams();
        params.put("key","15ed71f5413c9");
        params.put("phone","18888888888");
        
        Conn.create(Ascription.class).params(params).call(mCall).Go();
    }

    private Conn.Call mCall = new Conn.Call() {
        @Override
        public void call(Object bean, HTTPExtra extra) {
            switch (extra.what) {
                case "获得归属地":
                    if (extra.status == Status.CONN_OK) {
                      Log.d("Conn-Object2String", ((Ascription)bean).toString());
                    } else {
                        Toast.makeText(MainActivity.this, extra.status.name(), Toast.LENGTH_SHORT).show();
                    }
                    Log.d("Conn-连接耗时", extra.time + "");
                    Log.d("Conn-连接状态", extra.status + "");
                    Log.d("Conn-bean", String.valueOf(bean == null) + "");
                    Log.d("Conn-返回数据", extra.res + "");
                    Log.d("Conn-数据来自", extra.from + "");
                    break;
            }
        }
    };
```
