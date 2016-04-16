package com.dash.study.commute_check.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dash.study.commute_check.R;
import com.dash.study.commute_check.bean.Student;
import com.dash.study.commute_check.connection.Conn;
import com.dash.study.commute_check.util.Pack;
import com.dash.study.commute_check.util.UnPack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private String uname;
    private String id;
    private int age;
    private String cla;
    private String gender;
    private String macAddr;
    private String ipAddr;
    private Student student;

    private EditText unameEt;
    private EditText idEt;
    private EditText ageEt;
    private EditText classEt;
    private EditText ipEt;
    private RadioButton manRB;
    private RadioButton womanRB;
    private List<String> errors = new ArrayList<String>();
    private RadioGroup genderRG;
    private TextView macTv;

    final private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String state = msg.getData().getString("state");
            //转向以一个CheckActivity
            Intent intent = new Intent();
            intent.putExtra("message", msg.getData().getString("message"));
            intent.putExtra("ip", ipAddr);
            intent.setClass(SignUpActivity.this, CheckActivity.class);
            startActivity(intent);
            finish();
        }
    };

    /**
     * 关闭此 activity
     *
     * @param view
     */
    public void doCancel(View view) {
        this.finish();
    }

    class SignUpRun implements Runnable {
        @Override
        public void run() {
            String url = "http://" + ipAddr + ":8080/ws/stu/signUp.do";
            try {
                HttpURLConnection connection = Conn.getConnection(url);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                String jsonStr = Pack.getJson(student);
                OutputStream os = connection.getOutputStream();
                os.write(jsonStr != null ? jsonStr.getBytes() : new byte[0]);
                int code = connection.getResponseCode();
                if (code == 200) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String str = br.readLine();
                    Message message = new Message();
                    Bundle bundle = new Bundle();
                    Map<String, String> state = UnPack.getMessage(str);
                    if (state.get("state").equals("ok")) {
                        bundle.putString("state", state.get("state"));
                        bundle.putString("message", state.get("message"));
                    } else if (state.get("state").equals("failed")) {
                        bundle.putString("state", state.get("state"));
                        bundle.putString("message", state.get("message"));
                    }
                    message.setData(bundle);
                    handler.sendMessage(message);
                } else {
                    Log.i("dash", code + "");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void doSignUp(View view) {

        fetch();
        boolean b = validate();
        if (!b) {
            render();
        } else {
            student = pack();
        }
        //创建线程，网络链接，向服务器进行注册
        new Thread(new SignUpRun()).start();
    }

    private void fetch() {
        ipAddr = ipEt.getText().toString().trim();
        uname = unameEt.getText().toString().trim();
        id = idEt.getText().toString().trim();
        if (manRB.isChecked()) {
            gender = manRB.getText().toString().trim();
        } else if (womanRB.isChecked()) {
            gender = womanRB.getText().toString().trim();
        }
        age = Integer.parseInt(ageEt.getText().toString().trim());
        cla = classEt.getText().toString().trim();
    }

    private boolean validate() {
        if (uname.trim().length() == 0) {
            errors.add("姓名不能为空！\n");
        }
        if (id.trim().length() == 0) {
            errors.add("班级不能为空！\n");
        }
        if (cla.trim().length() == 0) {
            errors.add("学号不能为空！\n");
        }
        if (age < 0 || age > 130) {
            errors.add("这不是人类的年龄！\n");
        }
        return (errors.isEmpty()) ? true : false;
    }

    private void render() {
        StringBuilder sb = new StringBuilder();
        for (String s : errors) {
            sb.append(s);
        }
        Toast.makeText(this, sb, Toast.LENGTH_LONG).show();
        Toast.makeText(this, sb, Toast.LENGTH_LONG).cancel();
    }

    private Student pack() {
        Student student = new Student(uname, gender, id, age, cla, macAddr);

        return student;
    }

    @Override
    @SuppressLint("WrongViewCast")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        unameEt = (EditText) findViewById(R.id.unameET);
        idEt = (EditText) findViewById(R.id.idET);
        genderRG = (RadioGroup) findViewById(R.id.genderRG);
        manRB = (RadioButton) findViewById(R.id.manRB);
        womanRB = (RadioButton) findViewById(R.id.womanRB);
        ageEt = (EditText) findViewById(R.id.ageET);
        classEt = (EditText) findViewById(R.id.classET);
        ipEt = (EditText) findViewById(R.id.signUpIp);
        macAddr = getIntent().getStringExtra("mac");
        macTv = (TextView) findViewById(R.id.macAddr);
        macTv.setText(macAddr);
    }
}
