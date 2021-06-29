package com.dartmic.yo2see.model.login



data class LoginRequest(val service: String = "",
                        val user_name: String = "",
                        val password: String = "",
                        val device_id: String = "",
                        val device_type: String = "",
                        val longitude: String = "",
                        val latitude: String = "",
                        val uid: String = ""

)


