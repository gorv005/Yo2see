package com.dartmic.yo2see.utils

class Config {

    object AdapterClickViewTypes {


        const val CLICK_VIEW_CATEGORY = 99
        const val CLICK_VIEW_PRODUCT = 100


    }

    object ConstantsAnswer {
        const val ONE = 1
        const val TWO = 2
        const val THREE = 3
        const val FOUR = 4
    }

    object AdapterLayouts {
        const val HEADER = 1
        const val FOOTER = 2

    }

    object Constants {
        const val HEADER_AUTHORISATION = "Authorization"
    }

    object Endpoints {
        /* BASE */
        const val BASE_PATH = "/app/admin/api/"

        /* AUTH RELATED */
        const val SIGN_UP_API = BASE_PATH + "users/sign_up"
        const val LOGIN_API = BASE_PATH + "users/authenticate"
        const val RESET_PASSWORD_API = BASE_PATH + "users/password_resets"
        const val CATEGORIES_API = BASE_PATH + "common/categorylist"
        const val FEATURE_API = BASE_PATH + "common/isfeaturelist"


    }

    object SharedPreferences {
        // shared preferences name
        const val PROPERTY_PREF = "PREFERENCE_DEFAULT"

        // keys
        // login related
        const val PROPERTY_LOGIN_PREF = "PROPERTY_LOGIN_PREF" // is user logged in
        const val PROPERTY_JWT_TOKEN = "PROPERTY_JWT_TOKEN" // auth token
        const val PROPERTY_USER_ID = "PROPERTY_USER_ID" // user id
        const val PROPERTY_USER_NAME = "PROPERTY_USER_NAME" // user name
        const val PROPERTY_USER_EMAIL = "PROPERTY_USER_EMAIL" // user email
        const val PROPERTY_USER_TYPE = "PROPERTY_USER_TYPE" // user email

        // notification
        const val PROPERTY_FCM_REGISTRATION_TOKEN = "PROPERTY_FCM_REGISTRATION_TOKEN"

    }
}