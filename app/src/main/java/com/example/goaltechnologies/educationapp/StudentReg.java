package com.example.goaltechnologies.educationapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class StudentReg extends AppCompatActivity {
    EditText id1,name1,pass1,email1,class1,mble1,school1,add1;
    Button reg;
    TextView login;
    ArrayList<String> smsMessagesList = new ArrayList<String>();
    Spinner smsListView;
    ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_reg);
        smsListView = (Spinner) findViewById(R.id.spinner);
        smsMessagesList.add("PRE-MATRIC");
        smsMessagesList.add("POST-MATRIC");
        smsMessagesList.add("DISABILITIES");
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, smsMessagesList);
        smsListView.setAdapter(arrayAdapter);
        name1=(EditText)findViewById(R.id.editText12);
        pass1=(EditText)findViewById(R.id.editText13);
        email1=(EditText)findViewById(R.id.editText14);
        class1=(EditText)findViewById(R.id.editText15);
        mble1=(EditText)findViewById(R.id.editText8);
       // school1=(EditText)findViewById(R.id.editText9);
        add1=(EditText)findViewById(R.id.editText);
        reg=(Button)findViewById(R.id.button16);
        login=(TextView)findViewById(R.id.textView88);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = name1.getText().toString();
                String pass = pass1.getText().toString();
                String email = email1.getText().toString();
                String classs = class1.getText().toString();
                String mble=mble1.getText().toString();
                String caste=smsListView.getSelectedItem().toString();
                String add=add1.getText().toString();
                 Toast.makeText(getApplicationContext(),caste,Toast.LENGTH_SHORT).show();
              DownloadWebPageTask task = new DownloadWebPageTask();
               task.execute(new String[] {"http://www.healthmonitor.eu5.org/Eduapp/stureg.php?user="+name+"&pass="+pass+"&email="+email+"&classs="+classs+"&mble="+mble+"&caste="+caste+"&add="+add});

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
