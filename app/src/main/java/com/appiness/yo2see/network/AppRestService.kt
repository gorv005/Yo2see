package com.appiness.yo2see.network



import com.appiness.yo2see.managers.PreferenceManager
import com.appiness.yo2see.utils.Config
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response


/**
 * Signature of all the rest services will be here.
 */
class AppRestService(
    internal val appRestApiFast: AppRestApiFast,
    internal val preferenceManager: PreferenceManager
) {

/*    private fun getBearerToken(): String {
        val token = preferenceManager.getStringPreference(Config.SharedPreferences.PROPERTY_JWT_TOKEN)
        return "Bearer $token"
    }

    private fun getToken(): String {
        return preferenceManager.getStringPreference(Config.SharedPreferences.PROPERTY_JWT_TOKEN) ?: ""
    }*/


    /*Authentication*/

   /* fun signIn(loginRequest: LoginRequestPayload): Observable<Response<AuthenticationResponse>> {
        val apiCall = appRestApiFast.signUp(loginRequest)
        return apiCall
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getProducts(token :String, uuid: String?,with_subcategory:String): Observable<Response<ProductsWithCategoryResponse>> {
        val apiCall = appRestApiFast.getProducts(token,uuid,with_subcategory)
        return apiCall
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }*/
}
