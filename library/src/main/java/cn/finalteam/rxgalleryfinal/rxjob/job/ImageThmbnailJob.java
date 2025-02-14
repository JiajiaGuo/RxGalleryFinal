package cn.finalteam.rxgalleryfinal.rxjob.job;

import android.content.ContentUris;
import android.content.Context;
import android.provider.MediaStore;

import java.io.File;

import cn.finalteam.rxgalleryfinal.bean.MediaBean;
import cn.finalteam.rxgalleryfinal.rxjob.Job;
import cn.finalteam.rxgalleryfinal.utils.BitmapUtils;
import cn.finalteam.rxgalleryfinal.utils.MediaUtils;

/**
 * Desction:
 * Author:pengjianbo  Dujinyang
 * Date:16/7/31 上午11:46
 */
public class ImageThmbnailJob implements Job {

    private final MediaBean mediaBean;
    private final Context context;

    public ImageThmbnailJob(Context context, Params params) {
        this.context = context;
        this.mediaBean = (MediaBean) params.getRequestData();
    }

    @Override
    public Result onRunJob() {
        String originalPath = mediaBean.getOriginalPath();
        File bigThumFile = MediaUtils.createThumbnailBigFileName(context, originalPath);
        File smallThumFile = MediaUtils.createThumbnailSmallFileName(context, originalPath);
        if (!bigThumFile.exists()) {
//            BitmapUtils.createThumbnailBig(bigThumFile, originalPath);
            BitmapUtils.createThumbnailBig(context, bigThumFile, originalPath,
                    ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, mediaBean.getId()));
        }
        if (!smallThumFile.exists()) {
//            BitmapUtils.createThumbnailSmall(smallThumFile, originalPath);
            BitmapUtils.createThumbnailSmall(context, bigThumFile, originalPath,
                    ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, mediaBean.getId()));
        }
        Result result = Result.SUCCESS;
        result.setResultData(mediaBean);
        return result;
    }
}
