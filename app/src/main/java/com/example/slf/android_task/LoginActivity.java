package com.example.slf.android_task;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by draft on 2015/7/12.
 */
public class LoginActivity extends Activity {
    private  Button buttonLogin;
    private ImageView imageViewId;
    private ImageView imageViewHead;
    private EditText editId,editPassword;
    private String urlImagePath="http://1.smartprotecter.sinaapp.com/sm/icon_m.php";
    private String id,password;
    private Handler han;
    private Context context=LoginActivity.this;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //初始化
        init();


        //给按钮设置监听
        buttonLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {


                if (DoubleClickJuage.isFastDoubleClick()) {
                    return;
                }

                //获取两个所填Id和Password
                id=editId.getText().toString();
                password=editPassword.getText().toString();
                if(id.equals("")){
                    Toast.makeText(LoginActivity.this, "请输入你的用户名", Toast.LENGTH_SHORT).show();
                }else if(password.equals("")){
                    Toast.makeText(LoginActivity.this, "请输入你的密码", Toast.LENGTH_SHORT).show();
                }else{

                    Thread thread=new Thread(new Runnable()
                    {
                        @Override
                        public void run()
                        {

                            try {
                                //储存账号密码
                                Map<String, String> cookies = null;
                                Map<String, String> map = new HashMap<String, String>();
                                map.put("stuid", id);
                                map.put("pwd", password);

                                double time=((new Date().getTime()*9301+49297)%233280)/(233280.0);
                                double rand=Math.random();
                                //alert('rand is:'+rand+' time is:'+time);
                                time=(time+rand)*9301;
                                Log.d("test", "time"+time);
                                Log.d("test", "map" + map.toString());
                                Connection.Response res=Jsoup.connect("http://jwxt.sdu.edu.cn:7890/pls/wwwbks/bks_login2.login"+"?jym2005="+time).data(map)
                                        .method(Connection.Method.POST).timeout(20000).execute();
                                Document doc = res.parse();

                                cookies = res.cookies();

                                Document doc2 = Jsoup.connect("http://jwxt.sdu.edu.cn:7890/pls/wwwbks/xk.CourseView").cookies(cookies).timeout(30000).get();
                                Elements els = doc2.getAllElements();
                                int i=0;
                                Boolean needed=false;
                                for (Element el: els) {
                                    if (el.toString().substring(0,3).equals("<td")){
                                        Log.d("test",i+".........."+ el.text().toString());
                                        if (needed){
                                            SharedPreferences.Editor editor=context.getSharedPreferences(i+"", Context.MODE_PRIVATE).edit();
                                            editor.putString("nameStr",el.text().toString());
                                            editor.commit();
                                            if(i==41){
                                                needed=false;
                                            }

                                        }
                                        if (el.text().toString().equals("第一节")){
                                                 i=-1;
                                                 needed=true;
                                        }
                                        if (el.text().toString().equals("第二节")||el.text().toString().equals("第三节")||el.text().toString().equals("第四节")||el.text().toString().equals("第五节")||el.text().toString().equals("第六节")){
                                            i--;
                                        }

                                        i++;
                                    }


                                }


//

                            } catch (Exception e) {
                                Log.d("test","难道出错了");
                                e.printStackTrace();
                            }
                            Log.e("1111", "111111111");
                            // TODO Auto-generated method stub
                            Message message=new Message();
                            message.what=1;
                            han.sendMessage(message);
                        }
                    });
                    thread.start();
                    //登陆
                    Intent intent =new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });

    }

    private void init() {
        imageViewHead=(ImageView)findViewById(R.id.image_head);
        imageViewId=(ImageView)findViewById(R.id.image_id);
        imageViewId.setAlpha(210);
        buttonLogin=(Button)findViewById(R.id.button_login);
        editId=(EditText)findViewById(R.id.edit_id);
        editPassword=(EditText)findViewById(R.id.edit_password);
//        params = new ArrayList<NameValuePair>();

        han = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                Log.d("test",msg.toString());
            }
        };
    }


}
