package com.example.mac.loopprimenumber;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button bt;
    private EditText et_num;

    private final String UPER_NUM = "upernum";

    class MyThread extends Thread{
        private Handler mHandler;

        @Override
        public void run() {
            Looper.prepare();
            mHandler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    if(msg.what == 0x123){
                        int num = msg.getData().getInt(UPER_NUM);
                        List<Integer> numList = new ArrayList<>();

                        int flag = 0;
                        //计算从2开始的所有小于num的质数
                        outer:
                        for (int i=3; i<=num ; i++){
                            for (int j=2; j<=Math.sqrt(i) ; j++){
                                if (i%j== 0){
                                    continue outer;
                                }
                            }
                            numList.add(i);
                        }

                        //使用Toast显示结果
                        Toast.makeText(MainActivity.this,numList.toString(),Toast.LENGTH_LONG).show();
                    }
                }
            };
            Looper.loop();
        }
    }

    private MyThread myThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt = findViewById(R.id.button);
        bt.setOnClickListener(this);

        et_num = findViewById(R.id.et_num);

        myThread = new MyThread();
        myThread.start();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                Message msg = new Message();
                msg.what = 0x123;
                Bundle bundle = new Bundle();
                bundle.putInt(UPER_NUM,Integer.parseInt(et_num.getText().toString()));
                msg.setData(bundle);
                myThread.mHandler.sendMessage(msg);
                break;
        }
    }

}
