package edu.bzu.group_assignment1_1170271_1172738.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import edu.bzu.group_assignment1_1170271_1172738.R;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showCoursesClick(View view) {
        Intent intent = new Intent(this,ShowCourses.class);
        startActivity(intent);
    }
    public void serachCourseClick(View view) {
        Intent intent = new Intent(this,SearchCourse.class);
        startActivity(intent);
    }
}