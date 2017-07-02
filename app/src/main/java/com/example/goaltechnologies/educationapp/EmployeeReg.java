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
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
public class EmployeeReg extends AppCompatActivity {
    EditText name1,pass1,email1,quali1,mble1,exp1;
    Button reg,login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_reg);
        name1=(EditText)findViewById(R.id.editText);
        pass1=(EditText)findViewById(R.id.editText2);
        email1=(EditText)findViewById(R.id.editText4);
        quali1=(EditText)findViewById(R.id.editText7);
        mble1=(EditText)findViewById(R.id.editText3);
        exp1 =(EditText)findViewById(R.id.editText10);
        reg=(Button)findViewById(R.id.button5);
        login=(Button)findViewById(R.id.button6);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = name1.getText().toString();
                String pass = pass1.getText().toString();
                String email = email1.getText().toString();
                String quali = quali1.getText().toString();
                String mble=mble1.getText().toString();
                String exp=exp1.getText().toString();
                DownloadWebPageTask task = new DownloadWebPageTask();
                task.execute(new String[] {"http://www.healthmonitor.eu5.org/Eduapp/empreg.php?user="+name+"&pass="+pass+"&email="+email+"&quali="+quali+"&email="+email+"&mble="+mble+"&exp="+exp});


            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),LoginPage.class);
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
                Toast.makeText(getApplicationContext(), "  registration successful", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(getApplicationContext(),LoginPage.class);
                startActivity(i);
            } else {
                Toast.makeText(getApplicationContext(), "you are not registered", Toast.LENGTH_SHORT).show();

            }

        }
    }
}
