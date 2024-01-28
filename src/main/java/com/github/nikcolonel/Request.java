package com.github.nikcolonel;

import com.github.nikcolonel.requesttype.HttpRequest;

public class Request {
    public HttpRequest.HttpRequestBuilder http() {
        return HttpRequest.builder();
    }

    public HttpRequest.HttpRequestBuilder https() {
        return HttpRequest.builder();
    }
}
