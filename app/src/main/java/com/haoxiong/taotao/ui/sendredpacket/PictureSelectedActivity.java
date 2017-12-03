package com.haoxiong.taotao.ui.sendredpacket;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fan.service.OnRequestCompletedListener;
import com.fan.service.api.PersonServiceApi;
import com.fan.service.response.ChangePersonImgResponse;
import com.haoxiong.taotao.MyApp;
import com.haoxiong.taotao.R;
import com.haoxiong.taotao.base.BaseActivity;
import com.haoxiong.taotao.util.GlideUtil;
import com.haoxiong.taotao.util.SharePreferenceUtil;
import com.haoxiong.taotao.util.ToastUtils;
import com.haoxiong.taotao.util.WindowUtil;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class PictureSelectedActivity extends BaseActivity {

    @BindView(R.id.liner_picture_back)
    LinearLayout linerPictureBack;
    @BindView(R.id.liner_save_picture)
    LinearLayout linerSavePicture;
    @BindView(R.id.relativeLayout2)
    RelativeLayout relativeLayout2;
    @BindView(R.id.img_picture_one)
    ImageView imgPictureOne;
    @BindView(R.id.tv_update_picture_one)
    TextView tvUpdatePictureOne;
    @BindView(R.id.tv_delete_picture_one)
    TextView tvDeletePictureOne;
    @BindView(R.id.img_picture_two)
    ImageView imgPictureTwo;
    @BindView(R.id.tv_update_picture_two)
    TextView tvUpdatePictureTwo;
    @BindView(R.id.tv_delete_picture_two)
    TextView tvDeletePictureTwo;
    @BindView(R.id.img_picture_three)
    ImageView imgPictureThree;
    @BindView(R.id.tv_update_picture_three)
    TextView tvUpdatePictureThree;
    @BindView(R.id.tv_delete_picture_three)
    TextView tvDeletePictureThree;
    @BindView(R.id.img_picture_four)
    ImageView imgPictureFour;
    @BindView(R.id.tv_update_picture_four)
    TextView tvUpdatePictureFour;
    @BindView(R.id.tv_delete_picture_four)
    TextView tvDeletePictureFour;
    @BindView(R.id.img_picture_five)
    ImageView imgPictureFive;
    @BindView(R.id.tv_update_picture_five)
    TextView tvUpdatePictureFive;
    @BindView(R.id.tv_delete_picture_five)
    TextView tvDeletePictureFive;
    private int upDateTagPicture = 0;
    private int deleteTagPicture = 0;
    /**
     * 图片本地路径
     */
    private String pitcure1;
    private String pitcure2;
    private String pitcure3;
    private String pitcure4;
    private String pitcure5;
    /**
     * 网络图片
     */
    private String netPitcure1;
    private String netPitcure2;
    private String netPitcure3;
    private String netPitcure4;
    private String netPitcure5;
    private PopupWindow popupwindowShow;
    /**
     * 裁剪后
     */
    private String saveAdvicePicture;
    private String saveAdvicePicture1;
    private String saveAdvicePicture2;
    private String saveAdvicePicture3;
    private String saveAdvicePicture4;
    private String saveAdvicePicture5;
    private String adviceImgPath;
    private boolean tagEnable = true;
    /**
     * fileCode
     */
    private String fileCode1;
    private String fileCode2;
    private String fileCode3;
    private String fileCode4;
    private String fileCode5;

    public static void launch(BaseActivity context,int requestCode) {
        Intent intent = new Intent(context, PictureSelectedActivity.class);
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_selected);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_update_picture_one, R.id.tv_delete_picture_one
            , R.id.tv_update_picture_two, R.id.tv_delete_picture_two
            , R.id.tv_update_picture_three, R.id.tv_delete_picture_three
            , R.id.tv_update_picture_four, R.id.tv_delete_picture_four
            , R.id.liner_picture_back, R.id.liner_save_picture
            , R.id.tv_update_picture_five, R.id.tv_delete_picture_five})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_update_picture_one:
                if (tagEnable) {
                    upDateTagPicture = 1;
                    showAdviceImg();
                } else {
                    ToastUtils.toTosat(PictureSelectedActivity.this, "图片上传中...");
                }
                break;
            case R.id.tv_delete_picture_one:
                deleteTagPicture = 1;
                deletePicture();
                break;
            case R.id.tv_update_picture_two:
                if (tagEnable) {
                    upDateTagPicture = 2;
                    showAdviceImg();
                } else {
                    ToastUtils.toTosat(PictureSelectedActivity.this, "图片上传中...");
                }
                break;
            case R.id.tv_delete_picture_two:
                deleteTagPicture = 2;
                deletePicture();
                break;
            case R.id.tv_update_picture_three:
                if (tagEnable) {
                    upDateTagPicture = 3;
                    showAdviceImg();
                } else {
                    ToastUtils.toTosat(PictureSelectedActivity.this, "图片上传中...");
                }
                break;
            case R.id.tv_delete_picture_three:
                deleteTagPicture = 3;
                deletePicture();
                break;
            case R.id.tv_update_picture_four:
                if (tagEnable) {
                    upDateTagPicture = 4;
                    showAdviceImg();
                } else {
                    ToastUtils.toTosat(PictureSelectedActivity.this, "图片上传中...");
                }
                break;
            case R.id.tv_delete_picture_four:
                deleteTagPicture = 4;
                deletePicture();
                break;
            case R.id.tv_update_picture_five:
                if (tagEnable) {
                    upDateTagPicture = 5;
                    showAdviceImg();
                } else {
                    ToastUtils.toTosat(PictureSelectedActivity.this, "图片上传中...");
                }
                break;
            case R.id.tv_delete_picture_five:
                deleteTagPicture = 5;
                deletePicture();
                break;
            case R.id.liner_picture_back:
                onBackPressed();
                break;
            case R.id.liner_save_picture:
                save();
                break;
        }
    }

    private void save() {
        if (netPitcure1 == null || netPitcure2 == null) {
            ToastUtils.toTosat(PictureSelectedActivity.this, "请上传封面图片...");
        } else {
            Intent data = new Intent();
            StringBuilder netPicture = new StringBuilder(netPitcure1+"&"+netPitcure2);
            StringBuilder code = new StringBuilder(fileCode1+"&"+fileCode2);
            if (netPitcure3 != null) {
                netPicture.append("&" + netPitcure3);
                code.append("&" + fileCode3);
            }
            if (netPitcure4 != null) {
                netPicture.append("&" + netPitcure4);
                code.append("&" + fileCode4);
            }
            if (netPitcure5 != null) {
                netPicture.append("&" + netPitcure5);
                code.append("&" + fileCode5);
            }
            data.putExtra("EXTRA_RESULT", code + "%" + netPicture);
            setResult(99, data);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
       save();
    }

    private void deletePicture() {
        switch (deleteTagPicture) {
            case 1:
                netPitcure1 = null;
                fileCode1 = null;
                imgPictureOne.setImageResource(R.drawable.picture_bg);

                break;
            case 2:
                netPitcure2 = null;
                fileCode2 = null;
                imgPictureTwo.setImageResource(R.drawable.picture_bg);
                break;
            case 3:
                netPitcure3 = null;
                fileCode3 = null;
                imgPictureThree.setImageResource(R.drawable.picture_bg);
                break;
            case 4:
                netPitcure4 = null;
                fileCode4 = null;
                imgPictureFour.setImageResource(R.drawable.picture_bg);
                break;
            case 5:
                netPitcure5 = null;
                fileCode5 = null;
                imgPictureFive.setImageResource(R.drawable.picture_bg);
                break;
        }
    }

    private void showAdviceImg() {
        popupwindowShow = new PopupWindow(relativeLayout2, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.picture_popupwindow_layout, null);
        popupwindowShow.setContentView(view);
        popupwindowShow.setFocusable(true);
        popupwindowShow.setOutsideTouchable(true);
        popupwindowShow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        popupwindowShow.showAtLocation(relativeLayout2, Gravity.BOTTOM, 0, 0);
        TextView doPhoto = (TextView) view.findViewById(R.id.doPhoto);
        TextView choicePicture = (TextView) view.findViewById(R.id.choicePicture);
        TextView cansol = (TextView) view.findViewById(R.id.cansol);
        //拍照
        doPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doPhoto1();
                popupwindowShow.dismiss();
            }
        });
        //选择照片
        choicePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doPicture();
                popupwindowShow.dismiss();
            }
        });
        //取消
        cansol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupwindowShow.dismiss();
            }
        });

    }

    public void doPhoto1() {
        Uri uri;
        String saveDir = Environment.getExternalStorageDirectory() + "/redPacket";
        File dir = new File(saveDir);
        if (!dir.exists()) {
            dir.mkdir();
        }
        String filename = "redPacket" + System.currentTimeMillis() + ".PNG";

        adviceImgPath = saveDir + "/" + filename;
        switch (upDateTagPicture) {
            case 1:
                pitcure1 = adviceImgPath;
                break;
            case 2:
                pitcure2 = adviceImgPath;
                break;
            case 3:
                pitcure3 = adviceImgPath;
                break;
            case 4:
                pitcure4 = adviceImgPath;
                break;
            case 5:
                pitcure5 = adviceImgPath;
                break;
        }
        File file = new File(adviceImgPath);
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(PictureSelectedActivity.this, "com.haoxiong.taotao.fileprovider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, 20);
    }

    public void doPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 10);
    }

    /**
     * 裁剪原始的图片
     */
    public void cropRawPhoto(Uri uri) {
        tagEnable = false;
        String filename = "redPacket1" + System.currentTimeMillis() + ".PNG";
        switch (upDateTagPicture) {
            case 1:
                saveAdvicePicture1 = Environment.getExternalStorageDirectory() + "/1" + filename;
                saveAdvicePicture = saveAdvicePicture1;
                break;
            case 2:
                saveAdvicePicture2 = Environment.getExternalStorageDirectory() + "/2" + filename;
                saveAdvicePicture = saveAdvicePicture2;
                break;
            case 3:
                saveAdvicePicture3 = Environment.getExternalStorageDirectory() + "/3" + filename;
                saveAdvicePicture = saveAdvicePicture3;
                break;
            case 4:
                saveAdvicePicture4 = Environment.getExternalStorageDirectory() + "/4" + filename;
                saveAdvicePicture = saveAdvicePicture4;
                break;

            case 5:
                saveAdvicePicture5 = Environment.getExternalStorageDirectory() + "/5" + filename;
                saveAdvicePicture = saveAdvicePicture5;
                break;
        }

        File file = new File(saveAdvicePicture);
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Uri localUri = Uri.fromFile(file);
        Intent localIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, localUri);
        sendBroadcast(localIntent);
        UCrop.of(uri, Uri.fromFile(new File(saveAdvicePicture)))
                .withAspectRatio(16f, 16f)
                .withMaxResultSize(WindowUtil.getWidth(PictureSelectedActivity.this), (int) (WindowUtil.getWidth(PictureSelectedActivity.this) * 0.5))
                .start(PictureSelectedActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            luban();
        } else if (resultCode == UCrop.RESULT_ERROR) {
            Throwable error = UCrop.getError(data);
            ToastUtils.toTosat(PictureSelectedActivity.this, error.toString());
        } else {
            tagEnable = true;
        }
        //拍照
        if (requestCode == 20 && resultCode == RESULT_OK) {
            switch (upDateTagPicture) {
                case 1:
                    Log.e("pitcure1", pitcure1);
                    cropRawPhoto(Uri.fromFile(new File(pitcure1)));
                    break;
                case 2:
                    Log.e("pitcure2", pitcure2);
                    cropRawPhoto(Uri.fromFile(new File(pitcure2)));
                    break;
                case 3:
                    Log.e("pitcure3", pitcure3);
                    cropRawPhoto(Uri.fromFile(new File(pitcure3)));
                    break;
                case 4:
                    Log.e("pitcure4", pitcure4);
                    cropRawPhoto(Uri.fromFile(new File(pitcure4)));
                    break;
                case 5:
                    Log.e("pitcure5", pitcure5);
                    cropRawPhoto(Uri.fromFile(new File(pitcure5)));
                    break;
            }

        }
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case 10:
                //选择照片?
                if (resultCode == RESULT_OK && null != data) {
                    if (data != null) {
                        Uri uri = geturi(data);
                        String res = null;
                        String[] proj =
                                {MediaStore.Images.Media.DATA};
                        Cursor cursor = getApplicationContext().getContentResolver().query(uri, proj, null, null, null);
                        if (cursor != null) {
                            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                            cursor.moveToFirst();
                            res = cursor.getString(column_index);// 鍥剧墖鍦ㄧ殑璺緞
                        }
                        cursor.close();
                        adviceImgPath = res;
                        switch (upDateTagPicture) {
                            case 1:
                                pitcure1 = adviceImgPath;
                                cropRawPhoto(Uri.fromFile(new File(pitcure1)));
                                Log.e("pitcure1", pitcure1);
                                break;
                            case 2:
                                pitcure2 = adviceImgPath;
                                cropRawPhoto(Uri.fromFile(new File(pitcure2)));
                                Log.e("pitcure2", pitcure2);
                                break;
                            case 3:
                                pitcure3 = adviceImgPath;
                                cropRawPhoto(Uri.fromFile(new File(pitcure3)));
                                Log.e("pitcure3", pitcure3);
                                break;
                            case 4:
                                pitcure4 = adviceImgPath;
                                cropRawPhoto(Uri.fromFile(new File(pitcure4)));
                                Log.e("pitcure4", pitcure4);
                                break;
                            case 5:
                                pitcure5 = adviceImgPath;
                                cropRawPhoto(Uri.fromFile(new File(pitcure5)));
                                Log.e("pitcure5", pitcure5);
                                break;
                        }

                    } else {
                        ToastUtils.toTosat(PictureSelectedActivity.this, "请选择照片");
                        return;
                    }
                }
                break;

        }
    }

    private void luban() {
        File file = null;
        switch (upDateTagPicture) {
            case 1:
                file = new File(saveAdvicePicture1);
                break;
            case 2:
                file = new File(saveAdvicePicture2);
                break;
            case 3:
                file = new File(saveAdvicePicture3);
                break;
            case 4:
                file = new File(saveAdvicePicture4);
                break;
            case 5:
                file = new File(saveAdvicePicture5);
                break;
        }
        final File finalFile = file;
        Observable
                .create(new ObservableOnSubscribe<File>() {
                    @Override
                    public void subscribe(@NonNull final ObservableEmitter<File> e) throws Exception {
                        Luban.with(PictureSelectedActivity.this)
                                .load(finalFile)                     //传人要压缩的图片
                                .setCompressListener(new OnCompressListener() { //设置回调
                                    @Override
                                    public void onStart() {
                                    }

                                    @Override
                                    public void onSuccess(File file) {
                                        e.onNext(file);

                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                    }
                                }).launch();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<File>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull File file) {
                        upImg(file);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        ToastUtils.toTosat(PictureSelectedActivity.this, e.toString());
                        tagEnable = true;
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public Uri geturi(Intent intent) {
        Uri uri = intent.getData();
        String type = intent.getType();
        if (type != null && uri.getScheme().equals("file") && (type.contains("image/"))) {
            String path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = this.getContentResolver();
                StringBuffer buff = new StringBuffer();
                buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=")
                        .append("'" + path + "'").append(")");
                Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        new String[]{MediaStore.Images.ImageColumns._ID},
                        buff.toString(), null, null);
                int index = 0;
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                    // set _id value
                    index = cur.getInt(index);
                }
                if (index == 0) {
                    // do nothing
                } else {
                    Uri uri_temp = Uri
                            .parse("content://media/external/images/media/"
                                    + index);
                    if (uri_temp != null) {
                        uri = uri_temp;
                    }
                }
            }
        }
        return uri;
    }

    private void upImg(File file) {
        showProgressDialog("图片上传中...", false);
        PersonServiceApi.uploadMerchantPic(PictureSelectedActivity.this, MyApp.token, file, new OnRequestCompletedListener<ChangePersonImgResponse>() {
            @Override
            public void onCompleted(ChangePersonImgResponse response, String msg) {
                if (response.getErr() == 0) {
                    dismissProgressDialog();
                    SharePreferenceUtil.put(PictureSelectedActivity.this, "pic", response.getPreview_url());
                    SharePreferenceUtil.put(PictureSelectedActivity.this, "filecode", response.getFilecode());
                    switch (upDateTagPicture) {
                        case 1:
                            netPitcure1 = response.getPreview_url();
                            fileCode1 = response.getFilecode();
                            GlideUtil.loadImg(PictureSelectedActivity.this, netPitcure1, imgPictureOne);
                            break;
                        case 2:
                            netPitcure2 = response.getPreview_url();
                            fileCode2 = response.getFilecode();
                            GlideUtil.loadImg(PictureSelectedActivity.this, netPitcure2, imgPictureTwo);
                            break;
                        case 3:
                            netPitcure3 = response.getPreview_url();
                            fileCode3 = response.getFilecode();
                            GlideUtil.loadImg(PictureSelectedActivity.this, netPitcure3, imgPictureThree);
                            break;
                        case 4:
                            netPitcure4 = response.getPreview_url();
                            fileCode4 = response.getFilecode();
                            GlideUtil.loadImg(PictureSelectedActivity.this, netPitcure4, imgPictureFour);
                            break;
                        case 5:
                            netPitcure5 = response.getPreview_url();
                            fileCode5 = response.getFilecode();
                            GlideUtil.loadImg(PictureSelectedActivity.this, netPitcure5, imgPictureFive);
                            break;
                    }
                    ToastUtils.toTosat(PictureSelectedActivity.this, "图片上传成功");
                } else {
                    ToastUtils.toTosat(PictureSelectedActivity.this, response.getMsg());
                }
                tagEnable = true;
            }
        });
    }
}
