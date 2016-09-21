package com.alex.mvpapp.ui.userman;

import android.support.annotation.NonNull;

import com.alex.mvpapp.httpman.HttpMan;
import com.alex.mvpapp.httpman.UrlMan;
import com.alex.mvpapp.model.qianguan.LoginBean;

import org.alex.mvp.CancelablePresenter;
import org.alex.retrofit.RetrofitClient;
import org.alex.rxjava.BaseSubscriber;
import org.alex.rxjava.RxUtil;
import org.alex.util.LogUtil;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
/**
 * 作者：Alex
 * 时间：2016年09月03日    18:05
 * 简述：
 */
@SuppressWarnings("all")
public class UploadPhotoPresenter extends CancelablePresenter<UploadPhotoContract.View> implements UploadPhotoContract.Presenter {

    public UploadPhotoPresenter(@NonNull UploadPhotoContract.View view) {
        super(view);
    }

    @Override
    public void upLoadFile(List<File> fileList, String phone, String pwd) {

        if ((fileList == null) || (fileList.size() <= 0)) {
            LogUtil.e("文件为空");
            return;
        }


        Map<String, RequestBody> paramsMap = new HashMap<>();
        for (int i = 0; i < fileList.size(); i++) {
            File file = fileList.get(i);
            RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), fileList.get(i));
            paramsMap.put("userLogo\"; filename=\"" + file.getName() + ".png", fileBody);
        }
        paramsMap.put("phone", RequestBody.create(null, phone));
        paramsMap.put("pwd", RequestBody.create(null, pwd));

        new RetrofitClient.Builder()
                .baseUrl(UrlMan.AlexApp.szBaseUrl)
                .build()
                .create(HttpMan.class)
                .upLoad2(paramsMap)
                .compose(RxUtil.<LoginBean>rxHttp())
                .lift(new ReplaceSubscriberOperator())
                .subscribe(new MyHttpSubscriber());

    }

    private final class MyHttpSubscriber extends BaseSubscriber<LoginBean> {
        @Override
        public void onStart() {
            view.showLoadingDialog();
        }

        @Override
        public void onError(String message) {
            LogUtil.e(message);
            view.setFailMessage(message);
            view.dismissLoadingDialog();
        }

        @Override
        public void onSuccess(LoginBean result) {

            view.dismissLoadingDialog();
            view.toast("上传成功");
        }
    }

}
