package edu.bzu.group_assignment1_1170271_1172738.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import edu.bzu.group_assignment1_1170271_1172738.Models.DataBaseHelper;
import edu.bzu.group_assignment1_1170271_1172738.Models.User;
import edu.bzu.group_assignment1_1170271_1172738.R;
import edu.bzu.group_assignment1_1170271_1172738.Security.LoginSessionManager;
import edu.bzu.group_assignment1_1170271_1172738.Security.SecurityUtils;

public class LoginAdmin extends AppCompatActivity {

    private EditText emailAddressEditText;
    private EditText passwordEditText;
    private Button openRegistrationForm;
    private Button loginButton;
    private CheckBox rememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);
        emailAddressEditText = findViewById(R.id.loginEmailAddress);
        passwordEditText = findViewById(R.id.loginPassword);
        rememberMe = findViewById(R.id.rememberMeCheckbox);
        //Check Shared Preferences for Saved User's Email Address
        final SharedPreferences sharedPreferences = getSharedPreferences("UserData",MODE_PRIVATE);
        String savedEmailAddress = sharedPreferences.getString("EmailAddress","NONE");
        String savedPassword = sharedPreferences.getString("Password", "");
        if(!savedEmailAddress.equals("NONE") && ! savedPassword.isEmpty()){
            emailAddressEditText.setText(savedEmailAddress);
            passwordEditText.setText(savedPassword);
        }

        openRegistrationForm = (Button) findViewById(R.id.signup);
        openRegistrationForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toRegistrationActivityIntent = new Intent(LoginAdmin.this, SignupAdmin.class);
                LoginAdmin.this.startActivity(toRegistrationActivityIntent);

            }
        });

        loginButton = (Button) findViewById(R.id.login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailAddress = emailAddressEditText.getText().toString().trim();
                final DataBaseHelper databaseHelper = new DataBaseHelper(LoginAdmin.this);
                User user = databaseHelper.getUserByEmailAddress(emailAddress);
                if(user != null){
                    //Compare Password to Stored Password Hash to Perform User Login
                    String password = passwordEditText.getText().toString().trim();
                    if(SecurityUtils.verifyPasswordsMatch(password, user.getHashedPassword())){
                        //Check if Remember Me Checkbox is Checked
                        if(rememberMe.isChecked()) {
                            //Save Email Address to Shared Preferences
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("EmailAddress", emailAddress);
                            editor.putString("Password", password);
                            editor.commit();
                        }

                        //Save Logged-In User in a Login Session Using Shared Preferences
                        LoginSessionManager loginSession = new LoginSessionManager(getApplicationContext());
                        loginSession.saveUserLoginSession(user);

                        Intent toHomePageIntent = new Intent(LoginAdmin.this, MainActivity.class);
                        LoginAdmin.this.startActivity(toHomePageIntent);
                        LoginAdmin.this.finish();
                    }
                    else{
                        passwordEditText.setError(getResources().getString(R.string.error_wrong_password));
                    }
                }
                else {
                    emailAddressEditText.setError(getResources().getString(R.string.error_email_doesnot_exist));
                }
            }
        });
    }
}