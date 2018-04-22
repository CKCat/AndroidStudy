package com.cat.httpclient.Utils;

public interface HttpCallBackListener {

    void onFinish(String response);
    void onError(Exception e);
}
