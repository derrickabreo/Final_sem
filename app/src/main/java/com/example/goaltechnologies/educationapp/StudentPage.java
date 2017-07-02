package com.example.goaltechnologies.educationapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class StudentPage extends AppCompatActivity {
Button logout, sch;
    TextView namee;
    ArrayList<String> smsMessagesList = new ArrayList<String>();
    ArrayList<String> smsMessagesListt = new ArrayList<String>();
    ListView smsListView;
    Spinner sal;
    ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_page);
        String name=getIntent().getStringExtra("sname");
        Toast.makeText(getApplicationContext(),"Welcome "+name,Toast.LENGTH_LONG).show();
        sal = (Spinner) findViewById(R.id.spinner2);
        smsMessagesListt.add("Select Category");
        smsMessagesListt.add("PRE-MATRIC");
        smsMessagesListt.add("POST-MATRIC");
        smsMessagesListt.add("DISABILITIES");
        smsMessagesListt.add("SC/ST");
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, smsMessagesListt);
        sal.setAdapter(arrayAdapter);
        namee=(TextView)findViewById(R.id.textView3);
        namee.setText(name);
        smsListView = (ListView) findViewById(R.id.SMSList);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, smsMessagesList);
        smsListView.setAdapter(arrayAdapter);
       // smsListView.setOnItemClickListener(this);

        logout=(Button)findViewById(R.id.button8);
        DownloadWebPageTask task = new DownloadWebPageTask();
        task.execute(new String[]{"http://www.healthmonitor.eu5.org/Eduapp/data.php"});
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),LoginPage.class);
                startActivity(i);
            }
        });
sal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String items = sal.getSelectedItem().toString();
        if(items.equals("Select Category"))
        {

        }
        else {
            Intent i = new Intent(getApplicationContext(), ScholarAct.class);
            i.putExtra("sch", items);
            startActivity(i);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



});
        smsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              //  arrayAdapter = (ArrayAdapter) smsListView.getAdapter();

                String url = "http://healthmonitor.eu5.org/education/job/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
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

            try {
                JSONArray jArray = new JSONArray(result);


                for (int i = 0; i < jArray.length(); i++) {

                    JSONObject json_data = jArray.getJSONObject(i);
String str=json_data.getString("notification");

                    arrayAdapter.add(str);
                }


            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
    }



}
