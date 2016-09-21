package org.alex.retrofit;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
/**
 * 作者：Alex
 * 时间：2016年09月03日    21:29
 * 简述：
 */
@SuppressWarnings("all")
public class JavaNetCookiejar implements CookieJar {
    private List<Cookie> cookieList;

    public JavaNetCookiejar() {
        cookieList = new ArrayList<Cookie>();
    }

    @Override
    public synchronized void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
        if((list!=null) && (cookieList!=null)){
            cookieList.addAll(list);
        }
    }

    @Override
    public synchronized List<Cookie> loadForRequest(HttpUrl httpUrl) {
        List<Cookie> resultCookieList = new ArrayList<Cookie>();
        for (int i = 0; (cookieList!=null) && (i<cookieList.size()); i++) {
            Cookie cookie = cookieList.get(i);
            if(cookie.matches(httpUrl)){
                resultCookieList.add(cookie);
            }
        }
        return resultCookieList;
    }
}
