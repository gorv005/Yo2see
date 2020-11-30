package com.dartmic.yo2see.callbacks;


import com.dartmic.yo2see.network.RestResponse;

public interface APIResponseCallback<T> {

    void onResponse(RestResponse<T> response);
}
