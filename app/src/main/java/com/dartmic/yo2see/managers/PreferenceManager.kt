package com.dartmic.yo2see.managers

//import com.appiness.yo2see.models.signUp.UserRequestEmail
import android.content.Context

class PreferenceManager(context: Context) /*: BasePrefManager(context)*/ {

   /* @SuppressLint("ApplySharedPref")
    fun logOut() {
        val fcmToken = getStringPreference(PROPERTY_FCM_REGISTRATION_TOKEN)

        val editor = sharedPreferences.edit()
        editor.clear()
        commitChangesInEditor(editor)

        savePreference(PROPERTY_FCM_REGISTRATION_TOKEN, fcmToken)
    }*/


  /*  fun loginUser(token: String?, user: User?,isRemember :Boolean) {
        savePreference(Config.SharedPreferences.PROPERTY_JWT_TOKEN, token)
        user?.let { saveUserData(it) }
        savePreference(Config.SharedPreferences.PROPERTY_LOGIN_PREF, isRemember)
    }

    fun saveUserData(user: User?) {
        savePreference(Config.SharedPreferences.PROPERTY_USER_ID, user?.id)
        savePreference(Config.SharedPreferences.PROPERTY_USER_NAME, user?.firstName)
        savePreference(Config.SharedPreferences.PROPERTY_USER_EMAIL, user?.email)
        savePreference(Config.SharedPreferences.PROPERTY_USER_ERN_NUMBER, user?.ernNumber)
        savePreference(Config.SharedPreferences.PROPERTY_USER_TYPE, user?.user_type)

        saveUserImage(user?.profilePicture?.url)

    }



    fun saveUserImage(userImage: String?) {
        savePreference(Config.SharedPreferences.PROPERTY_USER_IMAGE, userImage)
    }

    fun isUserFBLoggedIn() = getBooleanPreference(Config.SharedPreferences.PROPERTY_USER_IS_FB_LOGIN)
    fun isUserLoggedIn() = getBooleanPreference(Config.SharedPreferences.PROPERTY_LOGIN_PREF)
    fun getLoggedInUserName() = getStringPreference(Config.SharedPreferences.PROPERTY_USER_NAME)
    fun getLoggedInUserEmail() = getStringPreference(Config.SharedPreferences.PROPERTY_USER_EMAIL)
    fun getLoggedInUserImage() = getStringPreference(Config.SharedPreferences.PROPERTY_USER_IMAGE)
    fun getLoggedInUserImageThumb() = getStringPreference(Config.SharedPreferences.PROPERTY_USER_IMAGE_THUMB)
    fun getLoggedInUserId() = getIntPreference(Config.SharedPreferences.PROPERTY_USER_ID)


    val sharedPreferences = context.getSharedPreferences(
        Config.SharedPreferences.PROPERTY_PREF,
        Context.MODE_PRIVATE
    )

    fun savePreference(key: String, value: String?) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        commitChangesInEditor(editor)
    }

    fun savePreference(key: String, value: Int?, defaultValue: Int = 0) {
        val editor = sharedPreferences.edit()
        editor.putInt(key, value ?: defaultValue)
        commitChangesInEditor(editor)
    }

    fun savePreference(key: String, value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        commitChangesInEditor(editor)
    }

    fun getStringPreference(prefName: String, defaultValue: String? = null): String? =
        sharedPreferences.getString(prefName, defaultValue)

    fun getIntPreference(prefName: String, defaultValue: Int = 0) = sharedPreferences.getInt(prefName, defaultValue)

    fun getBooleanPreference(prefName: String, defaultValue: Boolean = false) =
        sharedPreferences.getBoolean(prefName, defaultValue)

    private fun clearPreference(key: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, null)
        commitChangesInEditor(editor)
    }

    private fun commitChangesInEditor(editor: SharedPreferences.Editor) {
        editor.commit()
    }

    fun getDeviceTokenForFCM(): String? {

        return getStringPreference(Config.SharedPreferences.PROPERTY_FCM_REGISTRATION_TOKEN,null)
    }
     fun getBearerToken(): String {
        val token = sharedPreferences.getString(Config.SharedPreferences.PROPERTY_JWT_TOKEN,"")
        return "Bearer $token"
    }*/
}