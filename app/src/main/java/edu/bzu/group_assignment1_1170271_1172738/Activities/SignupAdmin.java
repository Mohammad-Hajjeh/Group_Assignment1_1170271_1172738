package edu.bzu.group_assignment1_1170271_1172738.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.bzu.group_assignment1_1170271_1172738.Models.DataBaseHelper;
import edu.bzu.group_assignment1_1170271_1172738.Models.User;
import edu.bzu.group_assignment1_1170271_1172738.R;
import edu.bzu.group_assignment1_1170271_1172738.Security.SecurityUtils;
import edu.bzu.group_assignment1_1170271_1172738.Validation.Validator;

public class SignupAdmin extends AppCompatActivity {
    private boolean allInputsValidated = true;
    private String emailAddress, firstName, lastName, password, confirmPassword, phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_admin);
        final EditText emailAddressInputField = (EditText) findViewById(R.id.emailAddressInput);
        final EditText firstNameInputField = (EditText) findViewById(R.id.firstNameInput);
        final EditText lastNameInputField = (EditText) findViewById(R.id.lastNameInput);
        final EditText passwordInputField = (EditText) findViewById(R.id.passwordInput);
        final EditText confirmPasswordInputField = (EditText) findViewById(R.id.confirmPasswordInput);
        final DataBaseHelper databaseHelper = new DataBaseHelper(SignupAdmin.this);

        final AlertDialog.Builder registrationConfirmationAlertDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.dialog_title)
                .setMessage(R.string.dialog_message)
                .setPositiveButton(R.string.dialog_ok_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        User user = new User();
                        user.setEmailAddress(emailAddress);
                        user.setHashedPassword(SecurityUtils.generate_MD5_Secure_Password(password));
                        user.setFirstName(firstName);
                        user.setLastName(lastName);
                        boolean insertFlag;
                        if ((insertFlag = databaseHelper.addUser(user)))
                            Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();

                        else {
                            Toast.makeText(getApplicationContext(), "ERROR: Registeration Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton(R.string.dialog_cancel_button, null);


        final Button registerButton = (Button) findViewById(R.id.btnSignup);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailAddress = emailAddressInputField.getText().toString().trim();
                firstName = firstNameInputField.getText().toString().trim();
                lastName = lastNameInputField.getText().toString().trim();
                password = passwordInputField.getText().toString().trim();
                confirmPassword = confirmPasswordInputField.getText().toString().trim();
                allInputsValidated = true;

                if (Validator.checkRequiredFieldConstraint(emailAddress)) {
                    if (Validator.checkEmailAddressValidity(emailAddress)) {
                        if (!databaseHelper.userAlreadyExists(emailAddress)) {

                            emailAddressInputField.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_check, 0);
                        } else {
                            emailAddressInputField.setError(getResources().getString(R.string.error_email_exists));
                            allInputsValidated = false;
                        }
                    } else {
                        emailAddressInputField.setError(getResources().getString(R.string.error_invalid_email));
                        allInputsValidated = false;
                    }
                } else {
                    emailAddressInputField.setError(getResources().getString(R.string.error_required_field));
                    allInputsValidated = false;
                }

                if (Validator.checkRequiredFieldConstraint(firstName)) {
                    if (Validator.checkFirstNameValidity(firstName)) {
                        firstNameInputField.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_check,0);
                    } else {
                        firstNameInputField.setError(getResources().getString(R.string.error_invalid_firstname));

                        allInputsValidated = false;
                    }
                } else {
                    firstNameInputField.setError(getResources().getString(R.string.error_required_field));

                    allInputsValidated = false;
                }

                if (Validator.checkRequiredFieldConstraint(lastName)) {
                    if (Validator.checkLastNameValidity(lastName)) {
                        lastNameInputField.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_check,0);
                    }
                    else {
                        lastNameInputField.setError(getResources().getString(R.string.error_invalid_lastname));
                        allInputsValidated = false;
                    }
                }
                else {
                    lastNameInputField.setError(getResources().getString(R.string.error_required_field));
                    allInputsValidated = false;
                }

                if (Validator.checkRequiredFieldConstraint(password)) {
                    if (Validator.checkPasswordValidity(password)) {
                        passwordInputField.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_check, 0);
                    } else {
                        passwordInputField.setError(getResources().getString(R.string.error_invalid_password));
                        allInputsValidated = false;
                    }
                }
                else {
                    passwordInputField.setError(getResources().getString(R.string.error_required_field));
                    allInputsValidated = false;
                }


                if (Validator.checkRequiredFieldConstraint(confirmPassword)) {
                    if (Validator.checkIfPasswordsMatch(password, confirmPassword)) {
                        confirmPasswordInputField.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_check, 0);
                    } else {
                        confirmPasswordInputField.setError(getResources().getString(R.string.error_passwords_mismatch));
                        allInputsValidated = false;
                    }
                }
                else {
                    confirmPasswordInputField.setError(getResources().getString(R.string.error_required_field));
                    allInputsValidated = false;
                }

                if (allInputsValidated) {
                    registrationConfirmationAlertDialog.show();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.check_validation_errors, Toast.LENGTH_SHORT);
                }
            }
        });

    }
}