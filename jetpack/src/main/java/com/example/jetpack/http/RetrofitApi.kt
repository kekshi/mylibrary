package com.example.jetpack.http

import com.zdy.baselibrary.retrofit.RetrofitCreateHelper


object RetrofitApi {

    private  var api: Api? = null

    val instence: Api
        get() {
            if (api == null) {
                synchronized(RetrofitApi::class.java) {
                    if (api == null) {
                        api = RetrofitCreateHelper.createApi(Api::class.java, BASE_URL)
                    }
                }
            }
            return api!!
        }
}
