package com.haoxiong.taotao.ui.sendredpacket;

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
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.fan.service.OnRequestCompletedListener;
import com.fan.service.api.PersonServiceApi;
import com.fan.service.api.RedPacketListApi;
import com.fan.service.request.SendRedPacketRequest;
import com.fan.service.response.ChangePersonImgResponse;
import com.fan.service.response.SendRedPacketResponse;
import com.haoxiong.taotao.MyApp;
import com.haoxiong.taotao.R;
import com.haoxiong.taotao.base.BaseActivity;
import com.haoxiong.taotao.eventbean.MessageEvent;
import com.haoxiong.taotao.ui.map.ListMapActivity;
import com.haoxiong.taotao.ui.redpacket.RedPacketActivity;
import com.haoxiong.taotao.util.SharePreferenceUtil;
import com.haoxiong.taotao.util.ToastUtils;
import com.haoxiong.taotao.util.WindowUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yalantis.ucrop.UCrop;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

public class SendRedPacketActivity extends BaseActivity {
    public static final String EXTRA_TITLE = "EXTRA_TITLE";
    public static final String EXTRA_DISTANCE = "EXTRA_DISTANCE";
    @BindView(R.id.liner_send_red_packet_back)
    LinearLayout linerSendRedPacketBack;
    @BindView(R.id.tv_send_red_packet_title)
    TextView tvSendRedPacketTitle;
    @BindView(R.id.liner_send_red_packet_title)
    LinearLayout linerSendRedPacketTitle;
    @BindView(R.id.tv_send_red_packet_content)
    TextView tvSendRedPacketContent;
    @BindView(R.id.liner_send_red_packet_content)
    LinearLayout linerSendRedPacketContent;
    @BindView(R.id.tv_send_red_packet_advice)
    TextView tvSendRedPacketAdvice;
    @BindView(R.id.liner_send_red_packet_advice)
    LinearLayout linerSendRedPacketAdvice;
    @BindView(R.id.tv_send_red_packet_contact)
    TextView tvSendRedPacketContact;
    @BindView(R.id.liner_send_red_packet_contact)
    LinearLayout linerSendRedPacketContact;
    @BindView(R.id.tv_send_red_packet_question)
    TextView tvSendRedPacketQuestion;
    @BindView(R.id.liner_send_red_packet_question)
    LinearLayout linerSendRedPacketQuestion;
    @BindView(R.id.tv_send_red_packet_right)
    TextView tvSendRedPacketRight;
    @BindView(R.id.liner_send_red_packet_right)
    LinearLayout linerSendRedPacketRight;
    @BindView(R.id.tv_send_red_packet_wrong)
    TextView tvSendRedPacketWrong;
    @BindView(R.id.liner_send_red_packet_wrong)
    LinearLayout linerSendRedPacketWrong;
    @BindView(R.id.tv_send_red_packet_diatance)
    TextView tvSendRedPacketDiatance;
    @BindView(R.id.liner_send_red_packet_distance)
    LinearLayout linerSendRedPacketDistance;
    @BindView(R.id.tv_send_red_packet_local)
    TextView tvSendRedPacketLocal;
    @BindView(R.id.liner_send_red_packet_local)
    LinearLayout linerSendRedPacketLocal;
    @BindView(R.id.imageView4)
    ImageView imageView4;
    @BindView(R.id.tv_send_red_packet_scan)
    TextView tvSendRedPacketScan;
    private String title;
    private String content;
    private String contact;
    private String question;
    private String right;
    private String wrong;
    private String distance;
    private PopupWindow popupwindowShow;
    private String adviceImgPath;
    private String adviceImgPath1;
    private String netAdviceImgPath;
    private String address;
    private MessageEvent message;
    private String money;
    private String num;
    private String lng;
    private String lat;
    private String mapaddress;
    private SendRedPacketRequest redPacketRequest;
    private String fileCode;

    public static void luncher(Context context, @NonNull String money, @NonNull String num) {
        Intent intent = new Intent(context, SendRedPacketActivity.class);
        intent.putExtra("money", money);
        intent.putExtra("num", num);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_red_packet);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        assignView();
    }

    private void assignView() {
        money = getIntent().getStringExtra("money");
        num = getIntent().getStringExtra("num");
        netAdviceImgPath = SharePreferenceUtil.get(SendRedPacketActivity.this, "pic");
        fileCode = SharePreferenceUtil.get(SendRedPacketActivity.this, "filecode");
        title = SharePreferenceUtil.get(SendRedPacketActivity.this, "merchant");
        content = SharePreferenceUtil.get(SendRedPacketActivity.this, "merchant_des");
        contact = SharePreferenceUtil.get(SendRedPacketActivity.this, "tel") + "--" + SharePreferenceUtil.get(SendRedPacketActivity.this, "address");
        question = SharePreferenceUtil.get(SendRedPacketActivity.this, "question");
        right = SharePreferenceUtil.get(SendRedPacketActivity.this, "first_answer");
        wrong = SharePreferenceUtil.get(SendRedPacketActivity.this, "second_answer") + "--" + SharePreferenceUtil.get(SendRedPacketActivity.this, "third_answer");
        distance = SharePreferenceUtil.get(SendRedPacketActivity.this, "distance");
        address = SharePreferenceUtil.get(SendRedPacketActivity.this, "address");
        mapaddress = SharePreferenceUtil.get(SendRedPacketActivity.this, "mapaddress");
        lng = SharePreferenceUtil.get(SendRedPacketActivity.this, "lng");
        lat = SharePreferenceUtil.get(SendRedPacketActivity.this, "lat");
        Boolean aBoolean = SharePreferenceUtil.getBoolean(SendRedPacketActivity.this, "type", false);
        if (aBoolean) {
            distance = "";
            tvSendRedPacketDiatance.setText("全国发放");
        } else {
            if (!distance.equals("")) {
                tvSendRedPacketDiatance.setText(distance + "km");
            } else {
                distance = null;
            }
        }
        tvSendRedPacketTitle.setText(title);
        tvSendRedPacketContent.setText(content);
        tvSendRedPacketContact.setText(contact);
        tvSendRedPacketQuestion.setText(question);
        tvSendRedPacketRight.setText(right);
        tvSendRedPacketWrong.setText(wrong);
        tvSendRedPacketLocal.setText(mapaddress);
        if (TextUtils.isEmpty(netAdviceImgPath)) {
            tvSendRedPacketAdvice.setText("未设置");
        } else {
            tvSendRedPacketAdvice.setText("已设置");
        }
    }

    @OnClick({R.id.liner_send_red_packet_back, R.id.liner_send_red_packet_title
            , R.id.liner_send_red_packet_content, R.id.liner_send_red_packet_advice
            , R.id.liner_send_red_packet_contact, R.id.liner_send_red_packet_question
            , R.id.liner_send_red_packet_right, R.id.liner_send_red_packet_wrong
            , R.id.liner_send_red_packet_distance, R.id.liner_send_red_packet_local})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.liner_send_red_packet_back:
                onBackPressed();
                break;
            case R.id.liner_send_red_packet_title:
                ChildSendRedPacketActivity.luncher(SendRedPacketActivity.this, 0, 0);
                break;
            case R.id.liner_send_red_packet_content:
                ChildSendRedPacketActivity.luncher(SendRedPacketActivity.this, 1, 1);
                break;
            case R.id.liner_send_red_packet_advice:
                RxPermissions rxPermissions = new RxPermissions(this);
                rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA).subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Boolean aBoolean) {
                        if (!aBoolean) {
                            new AlertDialog.Builder(SendRedPacketActivity.this).setTitle("提示")
                                    .setIcon(R.drawable.ic_logo)
                                    .setMessage("需要开启相机权限和读写才能拍照？")
                                    .setPositiveButton("去开启", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            getAppDetailSettingIntent(SendRedPacketActivity.this);

                                        }
                                    })
                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    })
                                    .show();
                        } else {
                            showAdviceImg();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

                break;
            case R.id.liner_send_red_packet_contact:
                ChildSendRedPacketActivity.luncher(SendRedPacketActivity.this, 2, 2);
                break;
            case R.id.liner_send_red_packet_question:
                ChildSendRedPacketActivity.luncher(SendRedPacketActivity.this, 3, 3);
                break;
            case R.id.liner_send_red_packet_right:
                ChildSendRedPacketActivity.luncher(SendRedPacketActivity.this, 4, 4);
                break;
            case R.id.liner_send_red_packet_wrong:
                ChildSendRedPacketActivity.luncher(SendRedPacketActivity.this, 5, 5);
                break;
            case R.id.liner_send_red_packet_distance:
                ChildSendRedPacketActivity.luncher(SendRedPacketActivity.this, 6, 6);
                break;
            case R.id.liner_send_red_packet_local:
                if (distance == null) {
                    ToastUtils.toTosat(SendRedPacketActivity.this, "请确定提醒范围");
                } else {
                    if (distance.equals("")) {
                        ToastUtils.toTosat(SendRedPacketActivity.this, "全国红包不设置定位");
                    } else {
                        if (distance != null && distance.length() > 0) {
                            try {
                                int integer = Integer.decode(distance) * 1000;
                                ListMapActivity.luncher(SendRedPacketActivity.this, integer, 7);
                            } catch (Exception e) {
                                ToastUtils.toTosat(SendRedPacketActivity.this, "请输入正确的提醒范围");
                            }
                        }
                    }
                }

                break;
        }
    }

    @OnClick(R.id.tv_send_red_packet_scan)
    public void onClick() {

        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(content) && !TextUtils.isEmpty(question) && !TextUtils.isEmpty(right) && !TextUtils.isEmpty(wrong)) {
            redPacketRequest = new SendRedPacketRequest();
            Boolean aBoolean = SharePreferenceUtil.getBoolean(SendRedPacketActivity.this, "type", false);
            if (aBoolean) {//全国红包
                redPacketRequest.setMoney(Integer.parseInt(money));
                redPacketRequest.setPcount(Integer.parseInt(num));
//                redPacketRequest.setDistance(Integer.parseInt(distance));
                redPacketRequest.setFirst_answer(right);
//                redPacketRequest.setLat(message.getLatLng().latitude+"");
//                redPacketRequest.setLng(message.getLatLng().longitude+"");
                redPacketRequest.setAddress(address);
                redPacketRequest.setMerchant(title);
                redPacketRequest.setMerchant_des(content);
                redPacketRequest.setPic1_filecode(adviceImgPath1);
                redPacketRequest.setQuestion(question);
                redPacketRequest.setSecond_answer(wrong.split("--")[0]);
                redPacketRequest.setThird_answer(wrong.split("--")[1]);
                try {
                    redPacketRequest.setTel(contact != null ? contact.split("--")[0] : "");
                } catch (Exception e) {
                    redPacketRequest.setTel("");
                }

                redPacketRequest.setPic1_filecode(netAdviceImgPath);
                redPacketRequest.setFilecode(fileCode);
                sendPacket();
            } else {//本地红包
                if (!TextUtils.isEmpty(lat)) {
                    redPacketRequest.setMoney(Integer.parseInt(money));
                    redPacketRequest.setPcount(Integer.parseInt(num));
                    redPacketRequest.setDistance(Integer.parseInt(distance));
                    redPacketRequest.setFirst_answer(right);
                    redPacketRequest.setLat(lat);
                    redPacketRequest.setLng(lng);
                    redPacketRequest.setMerchant(title);
                    redPacketRequest.setAddress(address);
                    redPacketRequest.setMerchant_des(content);
                    redPacketRequest.setPic1_filecode(adviceImgPath1);
                    redPacketRequest.setQuestion(question);
                    redPacketRequest.setSecond_answer(wrong.split("--")[0]);
                    redPacketRequest.setThird_answer(wrong.split("--")[1]);
                    try {
                        redPacketRequest.setTel(contact != null ? contact.split("--")[0] : "");
                    } catch (Exception e) {
                        redPacketRequest.setTel("");
                    }

                    redPacketRequest.setPic1_filecode(netAdviceImgPath);
                    redPacketRequest.setFilecode(fileCode);
                    sendLocal();
                } else {
                    ToastUtils.toTosat(SendRedPacketActivity.this, "请选择定位");
                }
            }
        } else {
            ToastUtils.toTosat(SendRedPacketActivity.this, "请完善信息");
        }
    }

    private void sendPacket() {
        RedPacketListApi.sendRedPacket(SendRedPacketActivity.this, MyApp.token,
                redPacketRequest.getMoney(), redPacketRequest.getPcount(),
                1, redPacketRequest.getMerchant(), redPacketRequest.getMerchant_des(),
                redPacketRequest.getQuestion(), redPacketRequest.getFirst_answer(), redPacketRequest.getSecond_answer(), redPacketRequest.getThird_answer()
                , redPacketRequest.getAddress(), redPacketRequest.getTel(), redPacketRequest.getFilecode()
                , new OnRequestCompletedListener<SendRedPacketResponse>() {
                    @Override
                    public void onCompleted(SendRedPacketResponse response, String msg) {
                        if (response == null) {
                            ToastUtils.toTosat(SendRedPacketActivity.this, "预览失败");
                            return;
                        }
                        if (response.getErr() == 0) {
                            MyApp.TYPE = 1;
                            redPacketRequest.setRid(response.getData());
                            RedPacketActivity.luncher(SendRedPacketActivity.this, redPacketRequest);
                        } else {
                            ToastUtils.toTosat(SendRedPacketActivity.this, response.getMsg());
                        }
                    }
                });
    }

    private void sendLocal() {
        RedPacketListApi.sendLocalRedPacket(SendRedPacketActivity.this, MyApp.token,
                redPacketRequest.getMoney(), redPacketRequest.getPcount(),
                2, redPacketRequest.getDistance(), redPacketRequest.getMerchant(), redPacketRequest.getMerchant_des(),
                redPacketRequest.getQuestion(), redPacketRequest.getFirst_answer(), redPacketRequest.getSecond_answer(), redPacketRequest.getThird_answer()
                , redPacketRequest.getAddress(), redPacketRequest.getTel(), redPacketRequest.getLng(), redPacketRequest.getLat(), redPacketRequest.getFilecode()
                , new OnRequestCompletedListener<SendRedPacketResponse>() {
                    @Override
                    public void onCompleted(SendRedPacketResponse response, String msg) {
                        if (response == null) {
                            ToastUtils.toTosat(SendRedPacketActivity.this, "预览失败");
                            return;
                        }
                        if (response.getErr() == 0) {
                            redPacketRequest.setRid(response.getData());
                            MyApp.TYPE = 1;
                            RedPacketActivity.luncher(SendRedPacketActivity.this, redPacketRequest);
                        } else {
                            ToastUtils.toTosat(SendRedPacketActivity.this, response.getMsg());
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            luban();
        } else if (resultCode == UCrop.RESULT_ERROR) {
            Throwable error = UCrop.getError(data);
            ToastUtils.toTosat(SendRedPacketActivity.this, error.toString());
        }
        //拍照
        if (requestCode == 20 && resultCode == RESULT_OK) {
            cropRawPhoto(Uri.fromFile(new File(adviceImgPath)));
        }
        if (data == null) {
            return;
        }
        String result = data.getStringExtra("EXTRA_RESULT");
        switch (requestCode) {
            case 0:
                title = result;
                tvSendRedPacketTitle.setText(title);
                break;
            case 1:
                content = result;
                tvSendRedPacketContent.setText(content);
                break;
            case 2:
                contact = result;
                try {
                    address = contact != null ? contact.split("--")[1] : "";
                } catch (Exception e) {
                    address = "";
                }
                tvSendRedPacketContact.setText(contact);
                break;
            case 3:
                question = result;
                tvSendRedPacketQuestion.setText(question);
                break;
            case 4:
                right = result;
                tvSendRedPacketRight.setText(right);
                break;
            case 5:
                wrong = result;
                tvSendRedPacketWrong.setText(wrong);
                break;
            case 6:
                distance = result;
                if (!result.equals("")) {
                    tvSendRedPacketDiatance.setText(distance + "km");
                } else {
                    tvSendRedPacketDiatance.setText("全国发放");
                }
                break;
            case 7:

                break;
            case 30:
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap photo = extras.getParcelable("data");
                }

                break;

            case 10:
                //选择照片?
                if (requestCode == 10 && resultCode == RESULT_OK && null != data) {
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
                        cropRawPhoto(Uri.fromFile(new File(adviceImgPath)));
                    } else {
                        ToastUtils.toTosat(SendRedPacketActivity.this, "请选择照片");
                        return;
                    }
                }
                break;

        }
    }

    private void luban() {
        final File file = new File(adviceImgPath1);
        Observable
                .create(new ObservableOnSubscribe<File>() {
                    @Override
                    public void subscribe(@NonNull final ObservableEmitter<File> e) throws Exception {
                        Luban.with(SendRedPacketActivity.this)
                                .load(file)                     //传人要压缩的图片
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
                        ToastUtils.toTosat(SendRedPacketActivity.this, e.toString());
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

    private void showAdviceImg() {
        popupwindowShow = new PopupWindow(linerSendRedPacketAdvice, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.picture_popupwindow_layout, null);
        popupwindowShow.setContentView(view);
        popupwindowShow.setFocusable(true);
        popupwindowShow.setOutsideTouchable(true);
        popupwindowShow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        popupwindowShow.showAtLocation(linerSendRedPacketAdvice, Gravity.BOTTOM, 0, 0);
        TextView doPhoto = (TextView) view.findViewById(R.id.doPhoto);
        TextView choicePicture = (TextView) view.findViewById(R.id.choicePicture);
        TextView cansol = (TextView) view.findViewById(R.id.cansol);
        //拍照
        doPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doPhoto();
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

    public void doPhoto() {
        Uri uri = null;
        String saveDir = Environment.getExternalStorageDirectory() + "/redPacket";
        File dir = new File(saveDir);
        if (!dir.exists()) {
            dir.mkdir();
        }
        String filename = "redPacket" + System.currentTimeMillis() + ".PNG";
        adviceImgPath = saveDir + "/" + filename;
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
            uri = FileProvider.getUriForFile(SendRedPacketActivity.this, "com.haoxiong.taotao.fileprovider", file);
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
        String filename = "redPacket1" + System.currentTimeMillis() + ".PNG";
        adviceImgPath1 = Environment.getExternalStorageDirectory() + "/" + filename;
        File file = new File(adviceImgPath1);
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
       /* Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image*//*");

        // 设置裁剪
        intent.putExtra("crop", "true");

        // aspectX , aspectY :宽高的比例
        intent.putExtra("aspectX", 4);
        intent.putExtra("aspectY", 3);

        // outputX , outputY : 裁剪图片宽高
        intent.putExtra("outputX", WindowUtil.getWidth(SendRedPacketActivity.this));
        intent.putExtra("outputY", 400);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 30);*/
        UCrop.of(uri, Uri.fromFile(new File(adviceImgPath1)))
                .withAspectRatio(16f, 9f)
                .withMaxResultSize(WindowUtil.getWidth(SendRedPacketActivity.this), (int) (WindowUtil.getWidth(SendRedPacketActivity.this) * 0.5))
                .start(SendRedPacketActivity.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent messageEvent) {
        message = messageEvent;
        lat = message.getLatLng().getLatitude() + "";
        lng = message.getLatLng().getLongitude() + "";
        tvSendRedPacketLocal.setText(messageEvent.getAddress());
    }

    private void upImg(File file) {
        showProgressDialog("图片上传中...", false);
        PersonServiceApi.uploadMerchantPic(SendRedPacketActivity.this, MyApp.token, file, new OnRequestCompletedListener<ChangePersonImgResponse>() {
            @Override
            public void onCompleted(ChangePersonImgResponse response, String msg) {
                if (response.getErr() == 0) {
                    dismissProgressDialog();
                    fileCode = response.getFilecode();
                    tvSendRedPacketAdvice.setText("已设置");
                    SharePreferenceUtil.put(SendRedPacketActivity.this, "pic", response.getPreview_url());
                    SharePreferenceUtil.put(SendRedPacketActivity.this, "filecode", response.getFilecode());
                    netAdviceImgPath = response.getPreview_url();
                    ToastUtils.toTosat(SendRedPacketActivity.this, "图片上传成功");
                } else {
                    ToastUtils.toTosat(SendRedPacketActivity.this, response.getMsg());
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (adviceImgPath1 != null) {
            outState.putString("tmpPhotoFile1", adviceImgPath1);
        }
        if (adviceImgPath != null) {
            outState.putString("tmpPhotoFile", adviceImgPath);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        adviceImgPath1 = savedInstanceState.getString("tmpPhotoFile1");
        adviceImgPath = savedInstanceState.getString("tmpPhotoFile");
    }

    /**
     * 跳转到权限设置界面
     */
    private void getAppDetailSettingIntent(Context context) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            intent.setAction(Intent.ACTION_VIEW);
            intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            intent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
        }
        startActivity(intent);
    }
}
