package com.haoxiong.taotao.ui.person;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.fan.service.OnRequestCompletedListener;
import com.fan.service.api.PersonServiceApi;
import com.fan.service.response.ChangePersonDetaResponse;
import com.fan.service.response.ChangePersonImgResponse;
import com.fan.service.response.PersonDateResponse;
import com.fan.service.rest.service.PersonService;
import com.haoxiong.taotao.MyApp;
import com.haoxiong.taotao.base.BaseActivity;
import com.haoxiong.taotao.ui.login.LoginActivity;
import com.haoxiong.taotao.ui.sendredpacket.ChildSendRedPacketActivity;
import com.haoxiong.taotao.ui.sendredpacket.SendRedPacketActivity;
import com.haoxiong.taotao.util.DensityUtil;
import com.haoxiong.taotao.util.GlideUtil;
import com.haoxiong.taotao.util.SharePreferenceUtil;
import com.haoxiong.taotao.util.WindowUtil;
import com.igexin.sdk.PushManager;
import com.pkmmte.view.CircularImageView;
import com.haoxiong.taotao.R;
import com.haoxiong.taotao.callback.Icallback;
import com.haoxiong.taotao.ui.main.MainActivity;
import com.haoxiong.taotao.util.ToastUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yalantis.ucrop.UCrop;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

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

public class PersonDataActivity extends BaseActivity {

    @BindView(R.id.liner_person_back)
    LinearLayout linerPersonBack;
    @BindView(R.id.liner_save_persondata)
    LinearLayout linerSavePersondata;
    @BindView(R.id.img_person_head)
    CircularImageView imgPersonHead;
    @BindView(R.id.et_person_nickname)
    EditText etPersonNickname;
    @BindView(R.id.et_person_sex)
    TextView etPersonSex;
    @BindView(R.id.et_person_birthday)
    TextView etPersonBirthday;
    @BindView(R.id.tv_person_logout)
    TextView tvPersonLogout;
    private String headImgPath;
    private PopupWindow popupwindowShow;
    private PersonDateResponse dateResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_data);
        ButterKnife.bind(this);
        assign();
        refreshData();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void refreshData() {
        PersonServiceApi.getUserInfor(PersonDataActivity.this, MyApp.token, new OnRequestCompletedListener<PersonDateResponse>() {
            @Override
            public void onCompleted(PersonDateResponse response, String msg) {
                if (response.getErr() == 0) {
                    dateResponse = response;
                    refreshView();
                } else {
                    ToastUtils.toTosat(PersonDataActivity.this, response.getMsg());

                }
            }
        });
    }

    private void refreshView() {
        GlideUtil.loadImg(PersonDataActivity.this, R.mipmap.head, MyApp.getInstance().user.getData().getUserinfo().getUserPic(), imgPersonHead);
        etPersonNickname.setText(dateResponse.getData().getUserinfo().getUsername() != null ? dateResponse.getData().getUserinfo().getUsername() : "");
        etPersonSex.setText(dateResponse.getData().getUserinfo().getGender() != null ? dateResponse.getData().getUserinfo().getGender() : "男");
        etPersonBirthday.setText(dateResponse.getData().getUserinfo().getBirthday() != null ? dateResponse.getData().getUserinfo().getBirthday() : "");

    }

    private void assign() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Boolean aBoolean) {
                Log.e("....", aBoolean.toString());
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }

    @OnClick({R.id.liner_person_back, R.id.liner_save_persondata, R.id.img_person_head, R.id.et_person_sex, R.id.et_person_birthday, R.id.tv_person_logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.liner_person_back:
                onBackPressed();
                break;
            case R.id.liner_save_persondata:
                savePersonData();
                break;
            case R.id.img_person_head:
                changeHeadImg();
                break;
            case R.id.et_person_sex:
                changeSex();
                break;
            case R.id.et_person_birthday:
                changeBirthday();
                break;
            case R.id.tv_person_logout:
                logout();
                break;
        }
    }

    private void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PersonDataActivity.this);
        builder.setTitle("提示")
                .setIcon(R.drawable.ic_logo)
                .setMessage("确定退出掏掏？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PushManager.getInstance().unBindAlias(PersonDataActivity.this,MyApp.getInstance().user.getData().getUserinfo().getMobile(),false);
                        MyApp.token = null;
                        SharePreferenceUtil.remove(PersonDataActivity.this,"token");
                        MyApp.login_state = 0;
                        SharePreferenceUtil.put(PersonDataActivity.this,"phone","");
                        Intent event = new Intent("refreshUserNothing");
                        EventBus.getDefault().post(event);
                        LoginActivity.luncher(PersonDataActivity.this,true);

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();

    }

    private void changeBirthday() {
// 最后一个false表示不显示日期，如果要显示日期，最后参数可以是true或者不用输入
        Calendar c = Calendar.getInstance();
        new DoubleDatePickerDialogWithouthhmms(PersonDataActivity.this, 0, new DoubleDatePickerDialogWithouthhmms.OnDateSetListener() {
            public void onDateSet(DatePicker startDatePicker,
                                  int startYear, int startMonthOfYear,
                                  int startDayOfMonth) {
                String text = String.format("%d年%d月%d日", startYear, startMonthOfYear + 1, startDayOfMonth);
                etPersonBirthday.setText(text);
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), true).show();
    }

    private void changeSex() {
        SexFragment sexFragment = new SexFragment(new Icallback() {
            @Override
            public void check(String contetn) {
                etPersonSex.setText(contetn);
            }
        }, etPersonSex.getText().toString());

        sexFragment.show(getSupportFragmentManager(), "1");

    }

    private void changeHeadImg() {
        popupwindowShow = new PopupWindow(imgPersonHead, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.picture_popupwindow_layout, null);
        popupwindowShow.setContentView(view);
        popupwindowShow.setFocusable(true);
        popupwindowShow.setOutsideTouchable(true);
        popupwindowShow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        popupwindowShow.showAtLocation(imgPersonHead, Gravity.BOTTOM, 0, 0);
        TextView doPhoto = (TextView) view.findViewById(R.id.doPhoto);
        TextView choicePicture = (TextView) view.findViewById(R.id.choicePicture);
        TextView cansol = (TextView) view.findViewById(R.id.cansol);
        //璋冪敤鎵嬫満鎷嶇収
        doPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doPhoto();
                popupwindowShow.dismiss();
            }
        });
        //鐩稿唽閫夋嫨鍥剧墖
        choicePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doPicture();
                popupwindowShow.dismiss();
            }
        });
        //鍙栨秷
        cansol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupwindowShow.dismiss();
            }
        });

    }

    private void savePersonData() {
        PersonServiceApi.changeUserInfor(PersonDataActivity.this, MyApp.token, etPersonNickname.getText().toString(), etPersonBirthday.getText().toString()
                , etPersonSex.getText().toString(), new OnRequestCompletedListener<ChangePersonDetaResponse>() {
                    @Override
                    public void onCompleted(ChangePersonDetaResponse response, String msg) {
                        if (response.getErr() == 0) {
                            MyApp.getInstance().user.getData().getUserinfo().setUsername(etPersonNickname.getText().toString());
                            MyApp.getInstance().user.getData().getUserinfo().setBirthday(etPersonBirthday.getText().toString());
                            MyApp.getInstance().user.getData().getUserinfo().setGender(etPersonSex.getText().toString());
                            Intent event = new Intent("refreshUser");
                            EventBus.getDefault().post(event);
                            finish();
                        } else {
                            ToastUtils.toTosat(PersonDataActivity.this, response.getMsg());
                        }
                    }
                });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MainActivity.luncher(PersonDataActivity.this);
    }

    public static void luncher(Context context) {
        context.startActivity(new Intent(context, PersonDataActivity.class));
    }

    public void doPhoto() {
        Uri uri = null;
        String saveDir = Environment.getExternalStorageDirectory() + "/redPacket";
        File dir = new File(saveDir);
        if (!dir.exists()) {
            dir.mkdir();
        }
        String filename = "redPacket" + System.currentTimeMillis() + ".PNG";
        headImgPath = saveDir + "/" + filename;
        File file = new File(headImgPath);
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(PersonDataActivity.this, "com.haoxiong.taotao.fileprovider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, 2);
    }

    public void doPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 3);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            imgPersonHead.setImageURI(resultUri);
            luban();
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
            Log.e("...", cropError.toString() + "");
        }
        switch (requestCode) {
            case 1:
                if (data == null) {
                    return;
                }
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap photo = extras.getParcelable("data");
                    imgPersonHead.setImageBitmap(photo);
                }
                luban();
                break;
            case 2:
                //拍照
                if (requestCode == 2 && resultCode == RESULT_OK) {
                    cropRawPhoto(Uri.fromFile(new File(headImgPath)));
                }
                break;
            case 3:
                //选择照片?
                if (requestCode == 3 && resultCode == RESULT_OK && null != data) {
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
                        headImgPath = res;
                        cropRawPhoto(Uri.fromFile(new File(headImgPath)));
                    } else {
                        ToastUtils.toTosat(PersonDataActivity.this, "请选择照片");
                        return;
                    }
                }
                break;
        }
    }

    private void luban() {
//        imgPersonHead.setImageBitmap(BitmapUtils.createImageThumbnail(headImgPath, 200, 200));
        final File file = new File(headImgPath);
        Observable
                .create(new ObservableOnSubscribe<File>() {
                    @Override
                    public void subscribe(@NonNull final ObservableEmitter<File> e) throws Exception {
                        Luban.with(PersonDataActivity.this)
                                .load(file)                     //传人要压缩的图片
                                .setCompressListener(new OnCompressListener() { //设置回调
                                    @Override
                                    public void onStart() {
                                        Log.e("....", file.length() / 1024 / 1024 + "");
                                    }

                                    @Override
                                    public void onSuccess(File file) {
                                        Log.e("....", file.length() / 1024 + "");
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

    /**
     * 裁剪原始的图片
     */
    public void cropRawPhoto(Uri uri) {

      /*  Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image*//*");

        // 设置裁剪
        intent.putExtra("crop", "true");

        // aspectX , aspectY :宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX , outputY : 裁剪图片宽高
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", true);

        startActivityForResult(intent, 1);*/
        UCrop.of(uri, uri)
                .withAspectRatio(9, 9)
                .withMaxResultSize(DensityUtil.dip2px(PersonDataActivity.this,60),DensityUtil.dip2px(PersonDataActivity.this,60))
                .start(PersonDataActivity.this);
    }

    private void upImg(File file) {
        showProgressDialog("图片上传中...");
        PersonServiceApi.changeUserHeadImg(PersonDataActivity.this, MyApp.token, file, new OnRequestCompletedListener<ChangePersonImgResponse>() {
            @Override
            public void onCompleted(ChangePersonImgResponse response, String msg) {
                if (response.getErr() == 0) {
                    dismissProgressDialog();
                    MyApp.getInstance().user.getData().getUserinfo().setUserPic(response.getPreview_url());
                    Intent event = new Intent();
                    event.setAction("refreshUserhead");
                    EventBus.getDefault().post(event);
                    ToastUtils.toTosat(PersonDataActivity.this, "修改头像成功");
                } else {
                    ToastUtils.toTosat(PersonDataActivity.this, response.getMsg());
                }
            }
        });
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (headImgPath != null) {
            outState.putString("headImgPath", headImgPath);
            Log.e("onSaveInstanceState", headImgPath);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        headImgPath = savedInstanceState.getString("headImgPath");
    }
}
