package edu.bzu.group_assignment1_1170271_1172738;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.icu.text.SymbolTable;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class ShowCourses extends AppCompatActivity {
    ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
    private SimpleAdapter sa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_courses);
        HashMap<String,String> item;
        for(int i=0;i<StatesAndCapitals.length;i++){
            item = new HashMap<String,String>();
            item.put( "line1", StatesAndCapitals[i][0]);
            item.put( "line2", StatesAndCapitals[i][1]);
            list.add( item );
        }
        sa = new SimpleAdapter(this, list,
                R.layout.twolines,
                new String[] { "line1","line2" },
                new int[] {R.id.line_a, R.id.line_b});
        ((ListView)findViewById(R.id.mobile_list)).setAdapter(sa);
    }

    private String[][] StatesAndCapitals =
            {{"Alabama","Montgomery"},
                    {"Alaska","Juneau"},
                    {"Arizona","Phoenix"},
                    {"Arkansas","Little Rock"},
                    {"California","Sacramento"},
                    {"Colorado","Denver"},
                    {"Connecticut","Hartford"},
                    {"Delaware","Dover"},
                    {"Florida","Tallahassee"},
                    {"Georgia","Atlanta"},
                    {"Hawaii","Honolulu"},
                    {"Idaho","Boise"},
                    {"Illinois","Springfield"},
                    {"Indiana","Indianapolis"},
                    {"Iowa","Des Moines"},
                    {"Kansas","Topeka"},
                    {"Kentucky","Frankfort"},
                    {"Louisiana","Baton Rouge"},
                    {"Maine","Augusta"}
            ,{"Alaska","Juneau"},
                    {"Arizona","Phoenix"},
                    {"Arkansas","Little Rock"},
                    {"California","Sacramento"},
                    {"Colorado","Denver"},
                    {"Connecticut","Hartford"},
                    {"Delaware","Dover"},
                    {"Florida","Tallahassee"},
                    {"Georgia","Atlanta"},
                    {"Hawaii","Honolulu"},
                    {"Idaho","Boise"},
                    {"Illinois","Springfield"},
                    {"Indiana","Indianapolis"},
                    {"Iowa","Des Moines"},
                    {"Kansas","Topeka"},
                    {"Kentucky","Frankfort"},
                    {"Louisiana","Baton Rouge"},
                    {"Maine","Augusta"}};
}
/*
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class ShowCourses extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickbtn(View view) {
        String url = "http://127.0.0.1:8080/test/info.php";
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},
                    123);

        } else{
            DownloadTextTask runner = new DownloadTextTask();
            runner.execute(url);
        }



    }
    private InputStream OpenHttpConnection(String urlString) throws IOException
    {
        InputStream in = null;
        int response = -1;

        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();

        if (!(conn instanceof HttpURLConnection))
            throw new IOException("Not an HTTP connection");
        try{
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            response = httpConn.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
            }
        }
        catch (Exception ex)
        {
            Log.d("Networking", ex.getLocalizedMessage());
            throw new IOException("Error connecting");
        }
        return in;
    }
    private String DownloadText(String URL)
    {
        int BUFFER_SIZE = 2000;
        InputStream in = null;
        try {
            in = OpenHttpConnection(URL);
        } catch (IOException e) {
            Log.d("Networking", e.getLocalizedMessage());
            return "";
        }

        InputStreamReader isr = new InputStreamReader(in);
        int charRead;
        String str = "";
        char[] inputBuffer = new char[BUFFER_SIZE];
        try {
            while ((charRead = isr.read(inputBuffer))>0) {
                //---convert the chars to a String---
                String readString =
                        String.copyValueOf(inputBuffer, 0, charRead);
                str += readString;
                inputBuffer = new char[BUFFER_SIZE];
            }
            in.close();
        } catch (IOException e) {
            Log.d("Networking", e.getLocalizedMessage());
            return "";
        }
        return str;
    }

    private class DownloadTextTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return DownloadText(urls[0]);
        }
        @Override
        protected void onPostExecute(String result) {
            //Toast.makeText(getBaseContext(), result, Toast.LENGTH_LONG).show();
            //String[] books = result.split(",");
            //String str = "";
            //for(String s : books){
            //    str+= s + "\n";
            // }
            System.out.println(result);
        }
    }
}*/