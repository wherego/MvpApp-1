package com.alex.mvpapp.model.qianguan;

import java.util.List;
/**
 * 作者：Alex
 * 时间：2016年09月03日    18:05
 * 简述：
 */
public class Bean {

    /**
     * code : 1
     * data : {"msgList":[{"id":3827,"title":"温馨提示","msgDate":"2016-08-10 11:23:56","read":1,"notice":"公告通知","type":"sys"},{"id":3825,"title":"温馨提示","msgDate":"2016-08-10 11:23:00","read":1,"notice":"公告通知","type":"sys"},{"id":3813,"title":"温馨提示","msgDate":"2016-08-09 14:47:55","read":1,"notice":"公告通知","type":"sys"},{"id":3811,"title":"温馨提示","msgDate":"2016-08-09 14:40:34","read":1,"notice":"公告通知","type":"sys"},{"id":3797,"title":"温馨提示","msgDate":"2016-08-09 10:09:54","read":1,"notice":"公告通知","type":"sys"},{"id":3637,"title":"您有可领取的优惠券","msgDate":"2016-07-25 22:28:31","read":1,"notice":"公告通知","type":"sys"},{"id":3631,"title":"您已注册成功","msgDate":"2016-07-25 17:30:00","read":1,"notice":"公告通知","type":"sys"}]}
     * message : success
     */

    public int code;
    public DataBean data;
    public String message;

    public static class DataBean {
        /**
         * id : 3827
         * title : 温馨提示
         * msgDate : 2016-08-10 11:23:56
         * read : 1
         * notice : 公告通知
         * type : sys
         */

        public List<MsgListBean> msgList;

        public static class MsgListBean {
            public String id;
            public String title;
            public String msgDate;
            public String read;
            public String notice;
            public String type;
        }
    }
}
