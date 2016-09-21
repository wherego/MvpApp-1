package org.alex.retrofit.interceptor;

import org.alex.retrofit.RequestParams;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
/**
 * 作者：Alex
 * 时间：2016年09月03日    21:29
 * 简述：
 */
@SuppressWarnings("all")
public final class RequestInterceptor implements Interceptor {
    private RequestParams requestParams;
    private Charset charSet;

    public RequestInterceptor(RequestParams requestParams) {
        this.requestParams = requestParams;
        charSet = Charset.forName("UTF-8");
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();

        Map<String, String> stringQueryMap = null;
        if (requestParams != null) {
            stringQueryMap = requestParams.getQueryMap();
        }
        StringBuilder queryStringBuilder = new StringBuilder();

        queryStringBuilder.append(request.url().toString().contains("?") ? "" : "?");

            /*解析文本请求行*/
        if ((stringQueryMap != null) && (stringQueryMap.size() > 0)) {
            Iterator<?> iterator = stringQueryMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                queryStringBuilder.append("&" + entry.getKey() + "=" + entry.getValue() + "");
            }
        }
         /*TODO: 暂时去掉这个*/
        /** .addHeader("Accept-Encoding", "gzip, deflate")*/
        builder.addHeader("Content-Type", "application/x-www-form-urlencoded;  charset=UTF-8")
                .addHeader("Accept", "*/*")
                .addHeader("Connection", "keep-alive");
        Request.Builder requestBuilder = builder.url(request.url().toString() + queryStringBuilder);


        Map<String, String> stringHeadMap = null;
        if (requestParams != null) {
            stringHeadMap = requestParams.getHeadMap();
        }
             /*解析文本请求头*/
        if ((stringHeadMap != null) && (stringHeadMap.size() > 0)) {
            Iterator<?> iterator = stringHeadMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                requestBuilder.addHeader(entry.getKey() + "", entry.getValue() + "");
            }
        }
        RequestBody requestBody = null;
        if (requestParams != null) {
            requestBody = getStringRequestBody(requestParams.getBodyMap());
        }
        if (requestBody != null) {
            requestBody.contentType().charset(charSet);
            requestBuilder.method("POST", requestBody);
        }
        return chain.proceed(requestBuilder.build());
    }

    private RequestBody getStringRequestBody(Map<?, ?> stringBodyMap) {
        FormBody.Builder stringBodyBuilder = new FormBody.Builder();
        if ((stringBodyMap == null) || (stringBodyMap.size() <= 0)) {
            return null;
        }
        if ((stringBodyMap == null) || (stringBodyMap.size() <= 0)) {
            return null;
        }
            /*解析文本请求体*/
        if ((stringBodyMap != null) && (stringBodyMap.size() > 0)) {
            Iterator iterator = stringBodyMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                stringBodyBuilder.add(entry.getKey() + "", entry.getValue() + "");
            }
        }
        return stringBodyBuilder.build();
    }
}


