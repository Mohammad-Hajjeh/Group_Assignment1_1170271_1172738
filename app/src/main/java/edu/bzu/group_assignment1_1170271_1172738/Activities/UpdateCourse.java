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
import android.text.TextUtils;
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

import edu.bzu.group_assignment1_1170271_1172738.Models.Course;
import edu.bzu.group_assignment1_1170271_1172738.R;

public class UpdateCourse extends AppCompatActivity {
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
        setContentView(R.layout.activity_update_course);
        initialize();
    }

    void initialize() {
        code = findViewById(R.id.etd_code);
        name = findViewById(R.id.etu_name);
        number = findViewById(R.id.etu_section);
        lecturer = findViewById(R.id.etu_lecturer);
        time = findViewById(R.id.etu_time);
        date = findViewById(R.id.etu_date);
        place = findViewById(R.id.etu_place);
    }

    public void onClickCancel(View view) {
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
        Toast.makeText(UpdateCourse.this, "UPDATE COURSE CANCELED", Toast.LENGTH_SHORT).show();

    }

    public void onClickLoad(View view) {
        String codeLoad = code.getText().toString();
        String codeNumber = number.getText().toString();

        if (codeLoad.isEmpty() || codeNumber.isEmpty() || !TextUtils.isDigitsOnly(number.getText())) {
            if (codeLoad.isEmpty()) {
                code.setError(getResources().getString(R.string.error_required_field));
                Toast.makeText(this, "Error: Empty Code Filed !", Toast.LENGTH_SHORT).show();
            }else if(!TextUtils.isDigitsOnly(number.getText()))
            {
                number.setError(getResources().getString(R.string.error_invalid_section));
            }
            else {
                number.setError(getResources().getString(R.string.error_required_field));
                Toast.makeText(this, "Error: Empty Section Filed !", Toast.LENGTH_SHORT).show();
            }
        }

    else {
            String url = "http://10.0.2.2:8080/rest/search2.php?cat=" + code.getText().toString() + "&cat1=" + number.getText().toString();
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.INTERNET)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.INTERNET},
                        123);

            } else {
                UpdateCourse.DownloadTextTask1 runner = new UpdateCourse.DownloadTextTask1();
                runner.execute(url);
            }
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

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




    private class DownloadTextTask1 extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return DownloadText(urls[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.equalsIgnoreCase("Wrong Code No Data Found !")) {
                Toast.makeText(UpdateCourse.this, result, Toast.LENGTH_SHORT).show();

            } else {
                String str = result;
                String[] course = str.split("@");
                Course courseObject = new Course();
                courseObject.setCode(course[0]);
                courseObject.setName(course[1]);
                courseObject.setNumber(course[2]);
                courseObject.setLecturer(course[3]);
                courseObject.setDay(course[4]);
                courseObject.setTime(course[5]);
                courseObject.setPlace(course[6].split("#")[0]);
                code.setText(courseObject.getCode());
                name.setText(courseObject.getName());
                number.setText(courseObject.getNumber());
                lecturer.setText(courseObject.getLecturer());
                date.setText(courseObject.getDay());
                time.setText(courseObject.getTime());
                place.setText(courseObject.getPlace());

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

            Toast.makeText(UpdateCourse.this, "UPDATE COURSE SUCCESSFULLY", Toast.LENGTH_SHORT).show();
        }
    }
    public void onClickUpdate(View view) {
        String restUrl = "http://10.0.2.2:8080/rest/update.php";

            if (checkEditTexts()) {
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.INTERNET)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.INTERNET},
                            123);

                } else {
                    UpdateCourse.SendPostRequest runner = new UpdateCourse.SendPostRequest();
                    runner.execute(restUrl);
                    Intent intent = new Intent(this, Menu.class);
                    startActivity(intent);
                }
            } else {
                Toast.makeText(UpdateCourse.this, "Please Fill All The Fields", Toast.LENGTH_LONG).show();
            }

    }

    boolean checkEditTexts() {
        if (code.getText().toString().equals(""))
        {
            code.setError(getResources().getString(R.string.error_required_field));
            return false;
        }
        if (name.getText().toString().equals("")){
            name.setError(getResources().getString(R.string.error_required_field));
            return false;}
        if (number.getText().toString().equals("")){
            number.setError(getResources().getString(R.string.error_required_field));
            return false;}
        boolean isNumeric=true;
        try {
            int x = Integer.parseInt(number.getText().toString());
        }catch (NumberFormatException nfe){
            isNumeric=false;
        }
        if (!isNumeric) {
            Toast.makeText(UpdateCourse.this, "Section Number Must be Integer", Toast.LENGTH_LONG).show();
            number.setBackgroundColor(Color.RED);
            return false;
        }
        if (lecturer.getText().toString().equals("")){
            lecturer.setError(getResources().getString(R.string.error_required_field));
            return false;}
        if (time.getText().toString().equals("")){
            time.setError(getResources().getString(R.string.error_required_field));
            return false;}
        if (date.getText().toString().equals("")){
            date.setError(getResources().getString(R.string.error_required_field));
            return false;}
        if(place.getText().toString().equals("")){
            place.setError(getResources().getString(R.string.error_required_field));
            return false;}
        return true;
    }


}



