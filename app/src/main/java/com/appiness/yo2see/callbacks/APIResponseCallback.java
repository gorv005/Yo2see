package com.appiness.yo2see.callbacks;


import com.appiness.yo2see.network.RestResponse;

public interface APIResponseCallback<T> {

    void onResponse(RestResponse<T> response);
}
