package edu.bzu.group_assignment1_1170271_1172738.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import edu.bzu.group_assignment1_1170271_1172738.R;


public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void showCoursesClick(View view) {
        Intent intent = new Intent(this,ShowCourses.class);
        startActivity(intent);
    }
    public void serachCourseClick(View view) {
        Intent intent = new Intent(this,SearchCourse.class);
        startActivity(intent);
    }

    public void btnExitToLogin(View view) {
        Intent intent = new Intent(this,LoginAdmin.class);
        startActivity(intent);
    }

    public void addCoursesClick(View view) {
        Intent intent = new Intent(this,AddCourse.class);
        startActivity(intent);
    }

    public void updateCoursesClick(View view) {
        Intent intent = new Intent(this,UpdateCourse.class);
        startActivity(intent);
    }

    public void deleteCoursesClick(View view) {
        Intent intent = new Intent(this,DeleteCourse.class);
        startActivity(intent);
    }
}