package com.github.nikcolonel.requesttype;

import com.github.nikcolonel.model.HttpMethod;
import com.github.nikcolonel.model.HttpResponse;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

import javax.net.ssl.HttpsURLConnection;

@Builder
public class HttpRequest {
    @Builder.Default
    private Logger log = Logger.getLogger(HttpRequest.class.getName());

    @NonNull
    private String url;
    @Singular
    private Map<String, String> headers;
    @NonNull
    private HttpMethod httpMethod;
    private byte[] body;

    @Builder.Default
    private int connectTimeout = 15000;
    @Builder.Default
    private int readTimeout = 15000;

    @Builder.Default
    private boolean isLogging = true;

    public HttpResponse send() throws IOException {
        int requestId = new Random().nextInt(Integer.MAX_VALUE - 1) + 1;

        // logRequest(requestId);

        HttpsURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();
        connection.setRequestMethod(httpMethod.name());
        headers.forEach(connection::addRequestProperty);
        connection.setConnectTimeout(connectTimeout);
        connection.setReadTimeout(readTimeout);
        connection.setDoOutput(true);

        if (body != null) {
            try (OutputStream os = connection.getOutputStream()) {
                os.write(body);
                os.flush();
            }
        } else {
            connection.connect();
        }

        int responseCode = connection.getResponseCode();

        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setResponseCode(responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) {
            httpResponse.setBody(readResponse(connection.getInputStream()));
        } else if (responseCode != HttpURLConnection.HTTP_UNAUTHORIZED) {
            httpResponse.setBody(readResponse(connection.getErrorStream()));
        }

        // logResponse(requestId, httpResponse);

        return httpResponse;
    }

    private byte[] readResponse(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return null;
        }

        return inputStream.readAllBytes();
    }

    private void logRequest(int requestId) {
        if (!this.isLogging) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("Request").append("\n");
        sb.append("ID: ").append(requestId).append("\n");
        sb.append("Type: ").append(httpMethod.name()).append("\n");
        sb.append("URL: ").append(url).append("\n");

        if (!headers.isEmpty()) {
            sb.append("Headers: ");
            headers.forEach((k, v) -> sb.append(k).append(": ").append(v).append("\n"));
        }

        sb.append("Body: ").append(body.toString());

        log.warning(sb.toString());
    }

    private void logResponse(int requestId, HttpResponse httpResponse) {
        if (!this.isLogging) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("Response").append("\n");
        sb.append("ID: ").append(requestId).append("\n");
        sb.append("HTTP code: ").append(httpResponse.getResponseCode()).append("\n");
        sb.append("Body: ").append(httpResponse.getBody().toString());

        log.warning(sb.toString());
    }

}
