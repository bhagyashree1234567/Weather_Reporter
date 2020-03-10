package com.bk.weatherreporter;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText etLocation;
    Button btnFind;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etLocation=findViewById(R.id.etLocation);
        btnFind=findViewById(R.id.btnFind);
        tvResult=findViewById(R.id.tvResult);

        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loc=etLocation.getText().toString();
                if (loc.length() == 0) {

                    etLocation.setError("location is Empty");
                    etLocation.requestFocus();
                    return;
                }
                MeraTask t1=new MeraTask();
                String w1="http://api.openweathermap.org/data/2.5/weather?units=metric";
                String w2="&q="+loc;
                String w3="&appid=c6e315d09197cec231495138183954bd";
                String w=w1+w2+w3;
                t1.execute(w);
            }
        });
    }

    class MeraTask extends AsyncTask<String, Void, Double>
    {
        double temp;

        @Override
        protected Double doInBackground(String... strings) {
            String json="",line="";
            try{
                URL url=new URL(strings[0]);
                HttpURLConnection con=(HttpURLConnection) url.openConnection();
                con.connect();


                InputStreamReader isr=new InputStreamReader(con.getInputStream());
                BufferedReader br= new BufferedReader(isr);

                while ((line=br.readLine())!=null){
                    json=json+line+"\n";
                }
                JSONObject o= new JSONObject(json);
                JSONObject p=o.getJSONObject("main");
                temp=p.getDouble("temp");

            }
            catch (Exception e){
                e.printStackTrace();
            }
            return  temp;
        }

        @Override
        protected void onPostExecute(Double aDouble) {
            super.onPostExecute(aDouble);
            tvResult.setText(" Temp " + aDouble);
        }
    }
}
