package com.example.slf.android_task;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by draft on 2015/7/23.
 */
public class NetThread extends Thread {
    private Map map;
    private String url;
    private Handler han;
    String result="";

    public NetThread(Handler han, String url, Map map){
        this.han=han;
        this.url=url;
        this.map = map;
    }



    @Override
    public void run() {
        // TODO Auto-generated method stub

        try {


            Log.d("test", "result" + "................." + result);

                if(result.equals("1")){
                    Message mess=new Message();
                    mess.what=1;
                    mess.obj =result;
                    han.sendMessage(mess);

                }else {
                    Message mess=new Message();
                    mess.what=0;
                    mess.obj = result;
                    han.sendMessage(mess);
                }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {

        }

    }

}