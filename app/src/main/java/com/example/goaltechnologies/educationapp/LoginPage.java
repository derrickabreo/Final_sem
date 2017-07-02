package com.example.goaltechnologies.educationapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class LoginPage extends AppCompatActivity {
    EditText user1,pass1;
    Button login,reset;
    TextView newuser;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        user1 = (EditText) findViewById(R.id.editText5);
        pass1 = (EditText) findViewById(R.id.editText6);
        login = (Button) findViewById(R.id.button2);
        reset = (Button) findViewById(R.id.button3);
        newuser=(TextView)findViewById(R.id.textView2);
        radioGroup = (RadioGroup) findViewById(R.id.radiotype);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = user1.getText().toString();
                String pass = pass1.getText().toString();
                int selectedId = radioGroup.getCheckedRadioButtonId();
                // find the radiobutton by returned id
                radioButton = (RadioButton) findViewById(selectedId);
                String role=radioButton.getText().toString();
//                if (!isValidPassword(pass)) {
//                    pass1.setError("Invalid Password");
//                }
                DownloadWebPageTask task = new DownloadWebPageTask();
                task.execute(new String[] {"http://www.healthmonitor.eu5.org/Eduapp/login.php?user="+user+"&pass="+pass+"&role="+role});


            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user1.setText("");
                pass1.setText("");
            }
        });

        newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),NewUserPage.class);
                startActivity(i);
            }
        });
    }

    private class DownloadWebPageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            for (String url : urls) {
                DefaultHttpClient client = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);
                try {
                    HttpResponse execute = client.execute(httpGet);
                    InputStream content = execute.getEntity().getContent();

                    BufferedReader buffer = new BufferedReader(
                            new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            //msg.setText(result);
            if (result.contains("success")) {
                Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(getApplicationContext(),StudentPage.class);
                i.putExtra("sname",user1.getText().toString());
                startActivity(i);
            }
            else  if (result.contains("true")) {
                Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(getApplicationContext(),EmpPage.class);
                i.putExtra("ename",user1.getText().toString());
                startActivity(i);
            } else {
                Toast.makeText(getApplicationContext(), "Invalid User", Toast.LENGTH_SHORT).show();

            }

        }
    }


    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() > 6) {
            return true;
        }
        return false;
    }
}
