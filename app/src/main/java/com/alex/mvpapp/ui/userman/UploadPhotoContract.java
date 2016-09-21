package com.alex.mvpapp.ui.userman;

import org.alex.mvp.BaseHttpContract;

import java.io.File;
import java.util.List;
/**
 * 作者：Alex
 * 时间：2016年09月03日    18:05
 * 简述：
 */
@SuppressWarnings("all")
public interface UploadPhotoContract {

    interface View extends BaseHttpContract.View {

    }

    interface Presenter extends BaseHttpContract.Presenter {
        /**
         * 上传图片文件
         */
        void upLoadFile(List<File> fileList, String phone, String pwd);
    }
}
