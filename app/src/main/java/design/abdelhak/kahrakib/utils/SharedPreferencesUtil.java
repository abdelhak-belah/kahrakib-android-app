package design.abdelhak.kahrakib.utils;

import android.content.Context;
import android.content.SharedPreferences;

import design.abdelhak.kahrakib.keys.SharedPreferencesKeys;
import design.abdelhak.kahrakib.networks.responses.AuthResponseModel;

public class SharedPreferencesUtil {

    /*-----------------------------variables-----------------------------*/
    private static SharedPreferences sp;
    private static SharedPreferences.Editor spe;
    /*-------------------------------------------------------------------*/

    public static void createUserSession(Context context, AuthResponseModel authResponseModel){
        sp = context.getSharedPreferences(SharedPreferencesKeys.USER_KEY,Context.MODE_PRIVATE);
        spe = sp.edit();
        spe.putString(SharedPreferencesKeys.USER_TOKEN_KEY, authResponseModel.getJwt());
        spe.putInt(SharedPreferencesKeys.USER_ID_KEY, authResponseModel.getId());
        spe.putString(SharedPreferencesKeys.USER_EMAIL_KEY, authResponseModel.getEmail());
        spe.putString(SharedPreferencesKeys.USER_ROLE_KEY, authResponseModel.getRole());
        spe.commit();
    }

    public static Boolean isUserLoggedIn(Context context){
        String token = getUserToken(context);

        if (token == null){
            return false;
        }else {
            return true;
        }
    }

    public static String getUserToken(Context context){
        sp = context.getSharedPreferences(SharedPreferencesKeys.USER_KEY,Context.MODE_PRIVATE);
        String token = sp.getString(SharedPreferencesKeys.USER_TOKEN_KEY,null);
        return token;
    }

    public static String getUserRole(Context context){
        sp = context.getSharedPreferences(SharedPreferencesKeys.USER_KEY,Context.MODE_PRIVATE);
        String role = sp.getString(SharedPreferencesKeys.USER_ROLE_KEY,null);
        return role;
    }

    public static String getUserEmail(Context context){
        sp = context.getSharedPreferences(SharedPreferencesKeys.USER_KEY,Context.MODE_PRIVATE);
        String email = sp.getString(SharedPreferencesKeys.USER_EMAIL_KEY,null);
        return email;
    }


    public static int getUserId(Context context){
        sp = context.getSharedPreferences(SharedPreferencesKeys.USER_KEY,Context.MODE_PRIVATE);
        int id = sp.getInt(SharedPreferencesKeys.USER_ID_KEY,0);
        return id;
    }

    public static void logoutUser(Context context){
        sp = context.getSharedPreferences(SharedPreferencesKeys.USER_KEY,Context.MODE_PRIVATE);
        spe = sp.edit();

        spe.clear();
        spe.apply();
        spe.commit();
    }


    public static void saveParametreSession(Context context,Boolean saveSession){
        sp = context.getSharedPreferences(SharedPreferencesKeys.PARAMETRE_KEY,Context.MODE_PRIVATE);
        spe = sp.edit();
        spe.putBoolean(SharedPreferencesKeys.PARAMETRE_SESSION_KEY, saveSession);
        spe.commit();
    }

    public static Boolean getParametreSession(Context context){
        sp = context.getSharedPreferences(SharedPreferencesKeys.PARAMETRE_KEY,Context.MODE_PRIVATE);
        Boolean session = sp.getBoolean(SharedPreferencesKeys.PARAMETRE_SESSION_KEY,true);
        return session;
    }


}
