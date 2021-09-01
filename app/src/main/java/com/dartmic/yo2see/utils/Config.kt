package com.dartmic.yo2see.utils

class Config {

    object AdapterClickViewTypes {


        const val CLICK_VIEW_CATEGORY = 99
        const val CLICK_VIEW_PRODUCT = 100
        const val CLICK_VIEW_FAV = 101
        const val CLICK_ADD_IMAGE = 102
        const val CLICK_REMOVE_IMAGE = 103
        const val CLICK_SHARE= 104
        const val CLICK_VIEW_DELETE = 108
        const val CLICK_COMMENT= 109

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
        const val SELL = 1
        const val RENT = 2
        const val BARTER = 3
        const val POST = 4
        const val POST_AN_ADD = 5
        const val PRODUCT = 6
        const val TYPE_SELL = "Sell"
        const val TYPE_RENT = "Rent"
        const val TYPE_POST = "Post"
        const val TYPE_BARTER = "Barter"
        const val ADDRESS = "ADDRESS"
        const val LATITUDE = "LATITUDE"
        const val LONGITUDE = "LONGITUDE"

    }

    object Endpoints {
        /* BASE */
        const val BASE_PATH = "/app/admin/api/"

        /* AUTH RELATED */
        const val SIGN_UP_API = BASE_PATH + "user/signup"
        const val LOGIN_API = BASE_PATH + "user/login"
        const val SOCIAL_LOGIN_API = BASE_PATH + "user/socialmedia"

        const val RESET_PASSWORD_API = BASE_PATH + "users/password_resets"
        const val CATEGORIES_API = BASE_PATH + "master/categorylist"
        const val ALL_TYPE_API = BASE_PATH + "master/alltypelist"

        const val FEATURE_API = BASE_PATH + "common/isfeaturelist"
        const val CATEGORIES_DATA_API = BASE_PATH + "common/alltypelist"
        const val PRODUCT_DATA_API = BASE_PATH + "product/listing"
        const val PRODUCT_JOB_DATA_API = BASE_PATH + "event/listing"

        const val UPLOAD_IMAGE_API = BASE_PATH + "common/upload_image"
        const val PRODUCT_ADD_API = BASE_PATH + "product/add_product"
        const val PRODUCT_DETAILS_DATA_API = BASE_PATH + "common/listing_detail"
        const val GET_USER_DATA_API = BASE_PATH + "user/getUser"
        const val GET_OTP = BASE_PATH + "user/generateotp"
        const val VERIFY_OTP = BASE_PATH + "user/validate_otp"
        const val GET_USER = BASE_PATH + "user/getUser"
        const val UPDATE_USER_API = BASE_PATH + "user/update_profile"
        const val REMOVE_ACCOUNT = BASE_PATH + "user/remove_account"
        const val ADD_TO_FAVORITES = BASE_PATH + "common/addtofav"
        const val PRODUCT_FAV_LIST_API = BASE_PATH + "common/userfavlist"
        const val CHANGE_PASSWORD = BASE_PATH + "user/changepassword"
        const val RESEND_EMAIL = BASE_PATH + "user/resend_email"
        const val FORGOT_PASSWORD = BASE_PATH + "user/forgetpwd"
        const val FEEDBACK = BASE_PATH + "user/user_rating"
        const val GET_GEN_DATA_API = BASE_PATH + "common/generallist"
        const val PRODUCT_ADD_JOB_API = BASE_PATH + "event/add_job"
        const val PRODUCT_ADD_EVENT_API = BASE_PATH + "event/add_event"
        const val PRODUCT_ADD_BUSINESS = BASE_PATH + "event/add_business"
        const val PRODUCT_ADD_BLOG = BASE_PATH + "event/add_blog"
        const val PRODUCT_ADD_POEM = BASE_PATH + "event/add_poem"
        const val PRODUCT_ADD_STORY = BASE_PATH + "event/add_story"
        const val PRODUCT_ADD_FREELANCE = BASE_PATH + "event/add_freelancing"
        const val PRODUCT_ADD_LOCAL_SERVICE = BASE_PATH + "event/add_local_service"
        const val PRODUCT_DETAILS_EVENT_DATA_API = BASE_PATH + "event/listing_detail"
        const val PRODUCT_ADD_BUSINESS_FOR_SALE = BASE_PATH + "event/add_business_for_sale"
        const val PRODUCT_ADD_UNCATEGORIZED = BASE_PATH + "event/add_uncategorized"
        const val PRODUCT_ADD_VOLUNTERRING= BASE_PATH + "event/add_valunteering"
        const val PRODUCT_ADD_FORUM = BASE_PATH + "event/add_forum"
        const val POST_HISTORY = BASE_PATH + "common/mylist"
        const val DELETE_HISTORY = BASE_PATH + "common/deletelist"
        const val COMMENT_LIST = BASE_PATH + "event/commentlist"
        const val ADD_COMMENT = BASE_PATH + "event/add_comment"


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
        const val PROPERTY_USER_PHONE = "PROPERTY_USER_PHONE" // user email
        const val PROPERTY_IS_USER_LOGIN = "PROPERTY_IS_USER_LOGIN" // user email
        const val PROPERTY_USER_IMAGE = "PROPERTY_USER_IMAGE" // user id

        const val PROPERTY_USER_PASSWORD = "PROPERTY_USER_PASSWORD" // PASSWORD

        // notification
        const val PROPERTY_FCM_REGISTRATION_TOKEN = "PROPERTY_FCM_REGISTRATION_TOKEN"

    }
}