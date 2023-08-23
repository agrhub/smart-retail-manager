package com.agrhub.app.smart_retail.utils;

import okhttp3.*;
import org.apache.commons.io.IOUtils;

import javax.net.ssl.*;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class URLUtil {
    private final static String USER_AGENT = "Mozilla/5.0; GAE FLEX";
    public final static int DEFAULT_TIMEOUT = 60000;
    private static OkHttpClient client = new OkHttpClient();
    private static X509TrustManager trustManager = null;
    private static SSLSocketFactory sslSocketFactory = null;

    private static void initSSL(){
        LogUtil.instance.debug(URLUtil.class, "initSSL");
        try {
            trustManager = new DefaultTrustManager();
            SSLContext sslContext = SSLContext.getInstance("TLS");

            // Install the all-trusting trust manager
            sslContext.init(null, new TrustManager[] {trustManager}, new SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (GeneralSecurityException e) {
            LogUtil.instance.debug(URLUtil.class, "initSSL: error " + e.getMessage());
        }
    }

    public static String GET(String url, int timeout){
        String content = GET(url,null, timeout);
        return content;
    }

    public static String GET(String url, Map<String, String> headers, int timeout){
        String content = "";
        try{
            InputStream is = URLUtil.get(url, headers, timeout);
            content = IOUtils.toString(is, "UTF-8");
            LogUtil.instance.debug(URLUtil.class, "GET - content: " + content);
        }catch(Exception e){
            LogUtil.instance.debug(URLUtil.class, "GET: " + e.getMessage());
        }
        return content;
    }

    public static String POST(String url, String params, MediaType type, Map<String, String> headers, int timeout){
        String content = "";
        try{
            InputStream is = URLUtil.post(url, params, type, headers, timeout);
            content = IOUtils.toString(is, "UTF-8");
            LogUtil.instance.debug(URLUtil.class, "POST - content: " + content);
        }catch(Exception e){
            LogUtil.instance.debug(URLUtil.class, "POST: " + e.getMessage());
        }
        return content;
    }

    public static String POST(String url, InputStream is, MediaType type, Map<String, String> headers, int timeout){
        String content = "";
        try{
            InputStream result = URLUtil.post(url, is, type, headers, timeout);
            content = IOUtils.toString(result, "UTF-8");
            LogUtil.instance.debug(URLUtil.class, "POST - content: " + content);
        }catch(Exception e){
            LogUtil.instance.debug(URLUtil.class, "POST: " + e.getMessage());
        }
        return content;
    }

    public static InputStream get(String url, Map<String, String> headers, int timeout) throws IOException{
        InputStream stream = null;
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        builder.header("User-Agent", USER_AGENT);
        if(headers != null){
            for(Map.Entry<String, String> entity : headers.entrySet()){
                builder.header(entity.getKey(), entity.getValue());
            }
        }

        Request request = builder.build();
        // Copy to customize OkHttp for this request.
        OkHttpClient getClient = getOkHttpClient(url, timeout);
        LogUtil.instance.info(URLUtil.class, "get: Sending 'GET' request to URL : " + url);
        try (Response response = getClient.newCall(request).execute()) {
            //return response.body().string();
            int responseCode = response.code();
            LogUtil.instance.info(URLUtil.class, "get: Response Code : " + responseCode);
            byte[] bytes = response.body().bytes();
            stream = new ByteArrayInputStream(bytes);
            if(responseCode == HttpServletResponse.SC_UNAUTHORIZED){
                String resString = "Unauthorized";
                stream = IOUtils.toInputStream(resString, "UTF-8");
            }
            response.body().close();
            //LogUtil.instance.debug(URLUtil.class, "get - content: " + IOUtils.toString(stream, "UTF-8") + " size: " + bytes.length);
        }
        catch (Exception e){
            LogUtil.instance.debug(URLUtil.class, "get - error: " + e.getMessage());
        }
        return stream;
    }

    public static InputStream post(String url, String params, MediaType type, Map<String, String> headers, int timeout) throws IOException{
        InputStream stream = null;
        //MediaType type = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
        RequestBody body = RequestBody.create(type, params);
        stream = post(url, body, headers, timeout);
        return stream;
    }

    public static InputStream post(String url, InputStream is, MediaType type, Map<String, String> headers, int timeout) throws IOException{
        InputStream stream = null;
        byte[] bytes = IOUtils.toByteArray(is);
        RequestBody body = RequestBody.create(type, bytes);
        stream = post(url, body, headers, timeout);
        return stream;
    }

    public static InputStream post(String url, RequestBody body, Map<String, String> headers, int timeout) throws IOException{
        InputStream stream = null;
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        builder.post(body);
        builder.header("User-Agent", USER_AGENT);
        if(headers != null){
            for(Map.Entry<String, String> entity : headers.entrySet()){
                builder.header(entity.getKey(), entity.getValue());
            }
        }

        Request request = builder.build();
        // Copy to customize OkHttp for this request.
        OkHttpClient postClient = getOkHttpClient(url, timeout);
        LogUtil.instance.info(URLUtil.class, "post: Sending 'POST' request to URL : " + url);
        try (Response response = postClient.newCall(request).execute()) {
            int responseCode = response.code();
            LogUtil.instance.info(URLUtil.class, "post: Response Code : " + responseCode);
            stream = new ByteArrayInputStream(response.body().bytes());

            if(responseCode == HttpServletResponse.SC_UNAUTHORIZED){
                String resString = "Unauthorized";
                stream = IOUtils.toInputStream(resString, "UTF-8");
            }
            response.body().close();
            //LogUtil.instance.debug(URLUtil.class, "post - content: " + IOUtils.toString(stream, "UTF-8") + " size: " + bytes.length);
        }catch (Exception e){
            LogUtil.instance.debug(URLUtil.class, "post - error: " + e.getMessage());
        }

        return stream;
    }

    private static OkHttpClient getOkHttpClient(String url, int timeout){
        LogUtil.instance.info(URLUtil.class, "getOkHttpClient");
        OkHttpClient okHttpClient = null;
        try {
            if (url.indexOf("https://") == 0) {
                if (sslSocketFactory == null) {
                    initSSL();
                }
                okHttpClient = client.newBuilder()
                        .readTimeout(timeout, TimeUnit.MILLISECONDS)
                        .sslSocketFactory(sslSocketFactory, trustManager)
                        .hostnameVerifier(new HostnameVerifier() {
                            @Override
                            public boolean verify(String hostname, SSLSession session) {
                                return true;
                            }
                        })
                        .build();
            } else {
                okHttpClient = client.newBuilder()
                        .readTimeout(timeout, TimeUnit.MILLISECONDS)
                        .build();
            }
        }catch (Exception e){
            LogUtil.instance.info(URLUtil.class, "getOkHttpClient - error: " + e.getMessage());
        }
        return okHttpClient;
    }

    private static class DefaultTrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}

        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }
}