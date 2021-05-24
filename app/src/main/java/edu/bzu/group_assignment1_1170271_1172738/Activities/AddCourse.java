package edu.bzu.group_assignment1_1170271_1172738.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import edu.bzu.group_assignment1_1170271_1172738.R;

public class AddCourse extends AppCompatActivity {
    private EditText code;
    private EditText name;
    private EditText number;
    private EditText lecturer;
    private EditText time;
    private EditText date;
    private EditText place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        initialize();
    }

    void initialize() {
        code = findViewById(R.id.eT_cod);
        name = findViewById(R.id.et_title);
        number = findViewById(R.id.et_section);
        lecturer = findViewById(R.id.et_lecturer);
        time = findViewById(R.id.et_time);
        date = findViewById(R.id.et_date);
        place = findViewById(R.id.et_place);
    }

    public void onClickCancel(View view) {
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
        Toast.makeText(AddCourse.this, "ADDED COURSE CANCELED", Toast.LENGTH_SHORT).show();

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
            Toast.makeText(AddCourse.this, "ADDED COURSE SUCCESSFULLY", Toast.LENGTH_SHORT).show();

        }
    }

    public void onClickAdd(View view) {
        String restUrl = "http://10.0.2.2:8080/rest/add.php";
        if(checkEditTexts()){
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.INTERNET)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.INTERNET},
                        123);

            } else {
                SendPostRequest runner = new SendPostRequest();
                runner.execute(restUrl);
                Intent intent = new Intent(this, Menu.class);
                startActivity(intent);
            }
        }
        else{
            Toast.makeText(AddCourse.this, "Please Fill All The Fields", Toast.LENGTH_LONG).show();
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
            Toast.makeText(AddCourse.this, "Section Number Must be Integer", Toast.LENGTH_LONG).show();
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
