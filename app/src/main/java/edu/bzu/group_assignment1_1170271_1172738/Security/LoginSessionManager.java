package edu.bzu.group_assignment1_1170271_1172738.Security;

import android.content.Context;
import android.content.SharedPreferences;
import edu.bzu.group_assignment1_1170271_1172738.Models.User;
public class LoginSessionManager {
    private static final String SHARED_PREF_FILE = "LoginSession";
    private static final String SHAREDPREF_KEY_ID = "ID";
    private static final String SHAREDPREF_KEY_FIRSTNAME = "Firstname";
    private static final String SHAREDPREF_KEY_LASTNAME = "Lastname";
    private static final String SHAREDPREF_KEY_EMAIL = "Email_Address";
    private static final String SHAREDPREF_KEY_ISLOGGEDIN = "isUserLoggedIn";

    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private boolean isLoggedIn;

    public LoginSessionManager(Context context){
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE );
        this.editor = this.sharedPreferences.edit();
        this.isLoggedIn = true;
    }

    public void saveUserLoginSession(User loggedInUser){
        this.editor.putInt(SHAREDPREF_KEY_ID, loggedInUser.getId());
        this.editor.putString(SHAREDPREF_KEY_FIRSTNAME, loggedInUser.getFirstName());
        this.editor.putString(SHAREDPREF_KEY_LASTNAME, loggedInUser.getLastName());
        this.editor.putString(SHAREDPREF_KEY_EMAIL, loggedInUser.getEmailAddress());
        this.editor.putBoolean(SHAREDPREF_KEY_ISLOGGEDIN, this.isLoggedIn);
        this.editor.commit();
    }

    public void updateCurrentLoginSession(User updatedUser){
        this.editor.putString(SHAREDPREF_KEY_FIRSTNAME, updatedUser.getFirstName());
        this.editor.putString(SHAREDPREF_KEY_LASTNAME, updatedUser.getLastName());
        this.editor.putString(SHAREDPREF_KEY_EMAIL, updatedUser.getEmailAddress());
        this.editor.commit();
    }

    public User getCurrentlyLoggedInUser(){
        User loggedInUser = new User();
        loggedInUser.setId(this.sharedPreferences.getInt(SHAREDPREF_KEY_ID, -1));
        loggedInUser.setFirstName(this.sharedPreferences.getString(SHAREDPREF_KEY_FIRSTNAME,null));
        loggedInUser.setLastName(this.sharedPreferences.getString(SHAREDPREF_KEY_LASTNAME,null));
        loggedInUser.setEmailAddress(this.sharedPreferences.getString(SHAREDPREF_KEY_EMAIL,null));
        return loggedInUser;
    }


    public void clearLoginSessionOnLogout(){
        this.editor.clear();
        this.editor.commit();
        this.isLoggedIn = false;
        this.editor.putBoolean(SHAREDPREF_KEY_ISLOGGEDIN, this.isLoggedIn);
        this.editor.commit();
    }
}
