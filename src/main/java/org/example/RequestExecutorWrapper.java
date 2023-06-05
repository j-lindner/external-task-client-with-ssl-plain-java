package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.camunda.bpm.client.impl.RequestExecutor;
import org.camunda.bpm.client.interceptor.impl.RequestInterceptorHandler;

import javax.net.ssl.SSLContext;

public class RequestExecutorWrapper extends RequestExecutor {

    protected RequestExecutorWrapper(RequestInterceptorHandler requestInterceptorHandler, ObjectMapper objectMapper) {
        super(requestInterceptorHandler, objectMapper);
    }

    @Override
    protected void initHttpClient(RequestInterceptorHandler requestInterceptorHandler) {
        CloseableHttpClient build = HttpClients.custom()
                // The NO_OP HostnameVerifier essentially turns hostname verification off. This implementation is a no-op, and never throws the SSLException.
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
//                .setSSLContext(null); TODO <<< here a custom SSL Context could be used
                .build();

        System.out.println("Initialized httpClient with NoopHostnameVerifier");

        this.httpClient = build;
    }
}
