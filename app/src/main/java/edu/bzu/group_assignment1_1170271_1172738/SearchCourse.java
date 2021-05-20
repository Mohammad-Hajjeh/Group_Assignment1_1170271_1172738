package edu.bzu.group_assignment1_1170271_1172738;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class SearchCourse extends AppCompatActivity {
    ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
    private SimpleAdapter sa;
    private SimpleAdapter sa2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_course);
        sa = new SimpleAdapter(this, list,
                R.layout.twolines,
                new String[] { "line1","line2","line3","line4","line5","line6","line7" },
                new int[] {R.id.line_a, R.id.line_b,R.id.line_c,R.id.line_d,R.id.line_e,R.id.line_f,R.id.line_g});
        sa2 = new SimpleAdapter(this, list,
                R.layout.oneline,
                new String[] { "line1" },
                new int[] {R.id.text1});
    }
    public void searchClick(View view) {
        list.clear();
        EditText edtCat = (EditText)findViewById(R.id.edtSerarch);
        String url = "http://10.0.2.2:8080/rest/search.php?cat=" + edtCat.getText();
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
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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
            if (result.equalsIgnoreCase("Wrong Code No Data Found !")) {
                Toast.makeText(SearchCourse.this, result, Toast.LENGTH_SHORT).show();
                HashMap<String, String> item;
                String StatesAndCapitals = "            Error: "+result;
                    item = new HashMap<String, String>();
                    item.put("line1", StatesAndCapitals);
                    list.add(item);
                ((ListView) findViewById(R.id.searchList)).setAdapter(sa2);

            } else {
                String str = result;
                Toast.makeText(SearchCourse.this, result, Toast.LENGTH_SHORT).show();
                String[] courses = str.split("#");
                String[][] StatesAndCapitals = {{"", "", "", "", "", "", ""},{"", "", "", "", "", "", ""}
                ,{"", "", "", "", "", "", ""},{"", "", "", "", "", "", ""}
                ,{"", "", "", "", "", "", ""},{"", "", "", "", "", "", ""}
                        ,{"", "", "", "", "", "", ""},{"", "", "", "", "", "", ""}
                        ,{"", "", "", "", "", "", ""},{"", "", "", "", "", "", ""}};
                for (int i = 0; i <courses.length; i++) {
                    String[] course = courses[i].split("@",7);
                    StatesAndCapitals[i][0] = "  Code: " + course[0];
                    StatesAndCapitals[i][1] = "  Name: " + course[1];
                    StatesAndCapitals[i][2] = "  Number: " + course[2];
                    StatesAndCapitals[i][3] = "  Lecturer: " + course[3];
                    StatesAndCapitals[i][4] = "  Day: " + course[4];
                    StatesAndCapitals[i][5] = "  Time: " + course[5];
                    StatesAndCapitals[i][6] = "  Place: " + course[6];
                }
                HashMap<String, String> item;
                for (int i = 0; i < StatesAndCapitals.length; i++) {
                    if(StatesAndCapitals[i][0]==""&&StatesAndCapitals[i][1]==""&&StatesAndCapitals[i][2]==""
                            &&StatesAndCapitals[i][3]==""&&StatesAndCapitals[i][4]==""&&StatesAndCapitals[i][5]==""
                            &&StatesAndCapitals[i][6]=="")
                        break;
                    item = new HashMap<String, String>();
                    item.put("line1", StatesAndCapitals[i][0]);
                    item.put("line2", StatesAndCapitals[i][1]);
                    item.put("line3", StatesAndCapitals[i][2]);
                    item.put("line4", StatesAndCapitals[i][3]);
                    item.put("line5", StatesAndCapitals[i][4]);
                    item.put("line6", StatesAndCapitals[i][5]);
                    item.put("line7", StatesAndCapitals[i][6].replaceAll("#",""));
                    list.add(item);
                }
                ((ListView) findViewById(R.id.searchList)).setAdapter(sa);
            }
        }
    }

}