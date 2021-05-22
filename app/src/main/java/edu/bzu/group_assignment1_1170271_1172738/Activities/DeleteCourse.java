package edu.bzu.group_assignment1_1170271_1172738.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;

import edu.bzu.group_assignment1_1170271_1172738.R;

public class DeleteCourse extends AppCompatActivity {
    public EditText code;
    public EditText name;
    public EditText number;
    private EditText lecturer;
    private EditText time;
    private EditText date;
    private EditText place;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_course);
        initialize();
    }

    void initialize() {
        code = findViewById(R.id.etd_code2);
        name = findViewById(R.id.etu_name2);
        number = findViewById(R.id.etu_section2);
        lecturer = findViewById(R.id.etu_lecturer2);
        time = findViewById(R.id.etu_time2);
        date = findViewById(R.id.etu_date2);
        place = findViewById(R.id.etu_place2);
    }

    public void onClickCancel2(View view) {
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }

    public void onClickLoad2(View view) {
        String url = "http://10.0.2.2:8080/rest/search2.php?cat=" + code.getText().toString() + "&cat1="+number.getText().toString();

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},
                    123);

        } else{
            DeleteCourse.DownloadTextTask1 runner = new DeleteCourse.DownloadTextTask1();
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

    public void onClickDelete(View view) {
        String url = "http://10.0.2.2:8080/rest/delete.php?cat=" + code.getText().toString() + "&cat1="+number.getText().toString();

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},
                    123);

        } else{
            DeleteCourse.SendPostRequest runner = new DeleteCourse.SendPostRequest();
            runner.execute(url);
        }
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }





    private class DownloadTextTask1 extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return DownloadText(urls[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.equalsIgnoreCase("Wrong Code No Data Found !")) {
                Toast.makeText(DeleteCourse.this, result, Toast.LENGTH_SHORT).show();

            } else {
                String str = result;
                Toast.makeText(DeleteCourse.this, result, Toast.LENGTH_SHORT).show();
                String[] course = str.split("@");
                code.setText(course[0]);
                name.setText(course[1]);
                number.setText(course[2]);
                lecturer.setText(course[3]);
                date.setText(course[4]);
                time.setText(course[5]);
                place.setText(course[6].split("#")[0]);

            }
        }
    }
    private String processRequest(String restUrl) throws UnsupportedEncodingException {


        String data = URLEncoder.encode("cod", "UTF-8")
                + "=" + URLEncoder.encode(code.getText().toString(), "UTF-8");

        data += "&" + URLEncoder.encode("name", "UTF-8") + "="
                + URLEncoder.encode(name.getText().toString(), "UTF-8");

        data += "&" + URLEncoder.encode("number", "UTF-8")
                + "=" + URLEncoder.encode(number.getText().toString(), "UTF-8");

        data += "&" + URLEncoder.encode("lecturer", "UTF-8")
                + "=" + URLEncoder.encode(lecturer.getText().toString(), "UTF-8");

        data += "&" + URLEncoder.encode("day", "UTF-8")
                + "=" + URLEncoder.encode(date.getText().toString(), "UTF-8");

        data += "&" + URLEncoder.encode("time", "UTF-8")
                + "=" + URLEncoder.encode(time.getText().toString(), "UTF-8");

        data += "&" + URLEncoder.encode("place", "UTF-8")
                + "=" + URLEncoder.encode(place.getText().toString(), "UTF-8");


        String text = "";
        BufferedReader reader = null;

        // Send data
        try {

            // Defined URL  where to send data
            URL url = new URL(restUrl);

            // Send POST data request

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = "";

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                // Append server response in string
                sb.append(line + "\n");
            }


            text = sb.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {

                reader.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        // Show response on activity
        return text;


    }

    private class SendPostRequest extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                return processRequest(urls[0]);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String result) {

            Toast.makeText(DeleteCourse.this, result, Toast.LENGTH_SHORT).show();
        }
    }


}



