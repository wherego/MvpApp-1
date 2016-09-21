package com.alex.mvpapp.httpman;
/**
 * 作者：Alex
 * 时间：2016年09月03日    18:05
 * 简述：
 */
public class UrlMan {

    public static final class ZhiHu {
        public static final String baseUrl = "http://news-at.zhihu.com/";
        public static final String articleList = "api/4/theme/";
    }

    public static final class DouBan {
        public static final String baseUrl = "https://api.douban.com/v2/";
        public static final String movieList = "movie/top250";
    }

    public static final class QianGuan {
        public static final String baseUrl = "http://api.qianguan360.com/service/";
        public static final String login = "appBid/loginPhone/";
    }

    public static final class AlexApp {
        public static final String szBaseUrl = "http://192.168.5.55:8080/AlexApp/";
        public static final String hsBaseUrl = "http://172.27.23.7:8080/AlexApp/";
        public static final String login = "login/";
    }
}
