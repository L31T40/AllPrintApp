
        package com.example.allprintapp.ui.utils;

        import android.app.Activity;
        import android.content.Context;
        import android.content.SharedPreferences;

        public class SharedPref
        {
            private static SharedPreferences mSharedPref;

            public static final String KEY_USER = "KEY_USER";
            public static final String KEY_NUMFUNC = "KEY_NUMFUNC";
            public static final String KEY_NAME = "KEY_NAME";
            public static final String KEY_EMAIL = "KEY_EMAIL";
            public static final String KEY_TEL = "KEY_TEL";
            public static final String KEY_LOCAL = "KEY_LOCAL";
            public static final String KEY_FOTO = "KEY_FOTO";
            public static final Boolean KEY_ISLOGGED = false;

            private SharedPref()
            {

            }

            public static void init(Context context)
            {
                if(mSharedPref == null)
                    mSharedPref = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
            }

            public static String readStr(String key, String defValue) {
                return mSharedPref.getString(key, defValue);
            }

            public static void writeStr(String key, String value) {
                SharedPreferences.Editor prefsEditor = mSharedPref.edit();
                prefsEditor.putString(key, value);
                prefsEditor.commit();
            }

            public static Boolean readBoolean(String key, boolean defValue) {
                return mSharedPref.getBoolean(key, defValue);
            }

            public static void writeBoolean(String key, boolean value) {
                SharedPreferences.Editor prefsEditor = mSharedPref.edit();
                prefsEditor.putBoolean(key, value);
                prefsEditor.commit();
            }

            public static Integer readInt(String key, int defValue) {
                return mSharedPref.getInt(key, defValue);
            }

            public static void writeInt(String key, Integer value) {
                SharedPreferences.Editor prefsEditor = mSharedPref.edit();
                prefsEditor.putInt(key, value).commit();
            }
        }

/**
 *Simply call SharedPref.init() on MainActivity once
 *
 * SharedPref.init(getApplicationContext());

 To Write data

 SharedPref.write(SharedPref.NAME, "XXXX");//save string in shared preference.
 SharedPref.write(SharedPref.AGE, 25);//save int in shared preference.
 SharedPref.write(SharedPref.IS_SELECT, true);//save boolean in shared preference.


 To Read Data

 String name = SharedPref.read(SharedPref.NAME, null);//read string in shared preference.
 int age = SharedPref.read(SharedPref.AGE, 0);//read int in shared preference.
 boolean isSelect = SharedPref.read(SharedPref.IS_SELECT, false);//read boolean in shared preference.
 **/