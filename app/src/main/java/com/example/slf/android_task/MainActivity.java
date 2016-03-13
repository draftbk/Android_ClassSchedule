package com.example.slf.android_task;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tv1_1,tv1_2,tv1_3,tv1_4,tv1_5,tv1_6,tv1_7,tv2_1,tv2_2,tv2_3,tv2_4,tv2_5,tv2_6,tv2_7
            ,tv3_1,tv3_2,tv3_3,tv3_4,tv3_5,tv3_6,tv3_7,tv4_1,tv4_2,tv4_3,tv4_4,tv4_5,tv4_6,tv4_7
            ,tv5_1,tv5_2,tv5_3,tv5_4,tv5_5,tv5_6,tv5_7,tv6_1,tv6_2,tv6_3,tv6_4,tv6_5,tv6_6,tv6_7;

    TextView[] textViews = {tv1_1,tv1_2,tv1_3,tv1_4,tv1_5,tv1_6,tv1_7,tv2_1,tv2_2,tv2_3,tv2_4,tv2_5,tv2_6,tv2_7
            ,tv3_1,tv3_2,tv3_3,tv3_4,tv3_5,tv3_6,tv3_7,tv4_1,tv4_2,tv4_3,tv4_4,tv4_5,tv4_6,tv4_7
            ,tv5_1,tv5_2,tv5_3,tv5_4,tv5_5,tv5_6,tv5_7,tv6_1,tv6_2,tv6_3,tv6_4,tv6_5,tv6_6,tv6_7};
    int [] textid={R.id.class_1_1,R.id.class_1_2,R.id.class_1_3,R.id.class_1_4,R.id.class_1_5,R.id.class_1_6,R.id.class_1_7,
            R.id.class_2_1,R.id.class_2_2,R.id.class_2_3,R.id.class_2_4,R.id.class_2_5,R.id.class_2_6,R.id.class_2_7,
            R.id.class_3_1,R.id.class_3_2,R.id.class_3_3,R.id.class_3_4,R.id.class_3_5,R.id.class_3_6,R.id.class_3_7,
            R.id.class_4_1,R.id.class_4_2,R.id.class_4_3,R.id.class_4_4,R.id.class_4_5,R.id.class_4_6,R.id.class_4_7,
            R.id.class_5_1,R.id.class_5_2,R.id.class_5_3,R.id.class_5_4,R.id.class_5_5,R.id.class_5_6,R.id.class_5_7,
            R.id.class_6_1,R.id.class_6_2,R.id.class_6_3,R.id.class_6_4,R.id.class_6_5,R.id.class_6_6,R.id.class_6_7};

    Context context=MainActivity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {


        for(int i = 0;i<42;i++){
            textViews[i]= (TextView) findViewById(textid[i]);
            textViews[i].setOnClickListener(this);
            textViews[i].setTag(i);
            SharedPreferences pref=getSharedPreferences(""+i, MODE_PRIVATE);
            textViews[i].setText(pref.getString("nameStr","课程"));
            Log.d("test",i+"......"+pref.getString("nameStr","课程"));
        }

    }

    @Override
    public void onClick(View v) {
        final TextView t=(TextView)v;
        //弹出框
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        //    通过LayoutInflater来加载一个xml的布局文件作为一个View对象
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog, null);
        //    设置我们自己定义的布局文件作为弹出框的Content
        builder.setView(view);
        SharedPreferences pref=getSharedPreferences(""+t.getTag(), MODE_PRIVATE);
        final EditText editName = (EditText)view.findViewById(R.id.name);
        final EditText editTime = (EditText)view.findViewById(R.id.time);
        final EditText editLocation = (EditText)view.findViewById(R.id.location);
        editName.setText(pref.getString("nameStr",""));
        editTime.setText(pref.getString("timeStr",""));
        editLocation.setText(pref.getString("locationStr",""));

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                String nameStr = editName.getText().toString();
                String timeStr = editTime.getText().toString();
                String locationStr = editLocation.getText().toString();
                SharedPreferences.Editor editor=context.getSharedPreferences(t.getTag().toString(), Context.MODE_PRIVATE).edit();
                editor.putString("nameStr",nameStr);
                editor.putString("timeStr",timeStr);
                editor.putString("locationStr", locationStr);
                editor.commit();
                t.setText(nameStr);

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });
        builder.show();
    }
}
