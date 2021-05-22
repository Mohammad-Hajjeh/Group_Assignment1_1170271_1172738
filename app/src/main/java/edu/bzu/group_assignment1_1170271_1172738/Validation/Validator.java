package edu.bzu.group_assignment1_1170271_1172738.Validation;

public class Validator {
    public static boolean checkRequiredFieldConstraint(String field){
        if(!field.isEmpty())
            return true;
        else
            return false;
    }
    public static boolean checkFirstNameValidity(String firstName){
        if(firstName.length()< 2)
            return false;
        else
            return true;
    }

    public static boolean checkLastNameValidity(String lastName){
        if(lastName.length() < 2)
            return false;
        else
            return true;
    }

    public static boolean checkPasswordValidity(String password){
        String PASSWORD_REGEX = "^(?=.{5,})(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[~@#$%-._]).*$";
        if(password.matches(PASSWORD_REGEX))
            return true;
        else
            return false;
    }

    public static boolean checkIfPasswordsMatch (String password, String confirmPassword){
        if(password.equals(confirmPassword))
            return true;
        else
            return false;
    }
    public static boolean checkPhoneNumberValidity(String phoneNumber){
        String PHONE_NUMBER_REGEX = "^(?=.{9,})[0-9]+";
        if(phoneNumber.matches(PHONE_NUMBER_REGEX))
            return true;
        else
            return false;
    }
    public static boolean checkEmailAddressValidity(String emailAddress){
        String EMAIL_ADDRESS_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
        if(emailAddress.matches(EMAIL_ADDRESS_REGEX))
            return true;
        else
            return false;
    }
}