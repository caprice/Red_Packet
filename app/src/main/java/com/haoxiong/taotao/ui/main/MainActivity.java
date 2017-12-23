package com.haoxiong.taotao.ui.main;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.fan.service.OnRequestCompletedListener;
import com.fan.service.api.MessageApi;
import com.fan.service.api.PersonServiceApi;
import com.fan.service.api.RedPacketListApi;
import com.fan.service.response.ActiveResponse;
import com.fan.service.response.LoginResponse;
import com.fan.service.response.PersonDateResponse;
import com.fan.service.response.RedPacketDetailResponse;
import com.fan.service.response.RedPacketListResponse;
import com.fan.service.response.UnreadResponse;
import com.fan.service.response.WalletResponse;
import com.haoxiong.taotao.MyApp;
import com.haoxiong.taotao.R;
import com.haoxiong.taotao.base.BaseActivity;
import com.haoxiong.taotao.ui.collect.CollectActivity;
import com.haoxiong.taotao.ui.login.LoginActivity;
import com.haoxiong.taotao.ui.main.adapter.HomeRecycleViewAdapter;
import com.haoxiong.taotao.ui.main.fragment.ActiveFragment;
import com.haoxiong.taotao.ui.message.MyMessageActivity;
import com.haoxiong.taotao.ui.person.PersonDataActivity;
import com.haoxiong.taotao.ui.redmaneger.RedMangerActivity;
import com.haoxiong.taotao.ui.redpacket.RedPacketActivity;
import com.haoxiong.taotao.ui.sendredpacket.SetMoneyActivity;
import com.haoxiong.taotao.ui.share.ShareActivity;
import com.haoxiong.taotao.ui.wallet.WalletActivity;
import com.haoxiong.taotao.util.DensityUtil;
import com.haoxiong.taotao.util.GlideUtil;
import com.haoxiong.taotao.util.ToastUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class MainActivity extends BaseActivity
        implements View.OnClickListener {
    @BindView(R.id.imageView)
    CircleImageView imageView;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_charge)
    TextView tvCharge;
    @BindView(R.id.tv_red)
    TextView tvRed;
    @BindView(R.id.tv_prize_invited)
    TextView tvPrizeInvited;
    @BindView(R.id.tv_red_maneger)
    TextView tvRedManeger;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.tv_contect_phone)
    TextView tvContectPhone;
    @BindView(R.id.mainactivity_person_liner)
    RelativeLayout mainactivityPersonLiner;
    @BindView(R.id.imageView3)
    ImageView imageView3;
    @BindView(R.id.mainactivity_select_liner)
    LinearLayout mainactivitySelectLiner;
    @BindView(R.id.main_recycle)
    RecyclerView mainRecycle;
    @BindView(R.id.content_main)
    RelativeLayout contentMain;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.main_swiperefreshlayout)
    SwipeRefreshLayout mainSwiperefreshlayout;
    @BindView(R.id.tv_active)
    TextView tvActive;
    @BindView(R.id.message_num)
    TextView messageNum;
    @BindView(R.id.tv_message)
    LinearLayout tvMessage;
    @BindView(R.id.message_num1)
    TextView messageNum1;

    private List<RedPacketListResponse.DataBean> data = new ArrayList<>();
    private LinearLayoutManager manager;
    private HomeRecycleViewAdapter adapter;
    private PopupWindow popupOrder;
    private int sort = 1;
    private int page = 1;
    private boolean isloadMore = false;
    public AMapLocationClient mLocationClient = null;
    public AMapLocationClientOption mLocationOption = null;
    private View footView;
    private ImageView fab1;
    private int isFresh = 0;
    private int clickPosition = -1;
    private int clickRid = -1;
    private RedPacketListResponse.DataBean clickDataBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        assinview();
        fab1 = (ImageView) findViewById(R.id.fab);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainRecycle.scrollToPosition(0);
                fab1.setVisibility(View.GONE);
            }
        });
        refreshAlways();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                getUnRead();
            }
        }, 10000, 10000);
    }

    private void getUnRead() {
        MessageApi.unReadNum(MainActivity.this, MyApp.token, new OnRequestCompletedListener<UnreadResponse>() {
            @Override
            public void onCompleted(UnreadResponse response, String msg) {
                if (response != null) {
                    if (response.getRet() == 200) {
                        if (response.getData() != null && response.getData().getCode() == 200) {
                            if (response.getData().getList().getUnread() != 0) {
                                messageNum.setVisibility(View.VISIBLE);
                                messageNum1.setVisibility(View.VISIBLE);
                                messageNum.setText(response.getData().getList().getUnread() + "");
                                messageNum1.setText(response.getData().getList().getUnread() + "");
                            } else {
                                messageNum.setVisibility(View.GONE);
                                messageNum1.setVisibility(View.GONE);
                            }
                        } else {
                            messageNum.setVisibility(View.GONE);
                            messageNum1.setVisibility(View.GONE);
                        }
                    } else {
                        messageNum.setVisibility(View.GONE);
                        messageNum1.setVisibility(View.GONE);
                    }
                } else {
                    messageNum.setVisibility(View.GONE);
                    messageNum1.setVisibility(View.GONE);
                }
            }
        });
    }

    private void refreshData() {
        if (MyApp.login_state == 1) {
            PersonServiceApi.getUserInfor(MainActivity.this, MyApp.token, new OnRequestCompletedListener<PersonDateResponse>() {
                @Override
                public void onCompleted(PersonDateResponse response, String msg) {
                    Log.e(">>.", MyApp.login_state + msg);
                    if (response.getErr() == 0) {
                        LoginResponse loginResponse = new LoginResponse();
                        loginResponse.setMsg(response.getMsg());
                        loginResponse.setErr(response.getErr());
                        LoginResponse.DataBean.UserinfoBean userinfoBean = new LoginResponse.DataBean.UserinfoBean();
                        userinfoBean.setBalance(response.getData().getUserinfo().getBalance());
                        userinfoBean.setGender(response.getData().getUserinfo().getGender());
                        userinfoBean.setUsername(response.getData().getUserinfo().getUsername());
                        userinfoBean.setBirthday(response.getData().getUserinfo().getBirthday());
                        userinfoBean.setUserPic(response.getData().getUserinfo().getUserPic());
                        userinfoBean.setMobile(response.getData().getUserinfo().getMobile());
                        userinfoBean.setInviteCode(response.getData().getUserinfo().getInviteCode());
                        LoginResponse.DataBean dataBean = new LoginResponse.DataBean();
                        dataBean.setUserinfo(userinfoBean);
                        loginResponse.setData(dataBean);
                        MyApp.getInstance().user = loginResponse;
                        try {
                            GlideUtil.loadImg(MainActivity.this, R.mipmap.head, loginResponse.getData().getUserinfo().getUserPic(), imageView);
                        } catch (Exception e) {
                            imageView.setImageResource(R.mipmap.head);
                        }
                        tvName.setText(loginResponse.getData().getUserinfo().getUsername() != null ? loginResponse.getData().getUserinfo().getUsername() : "");
                        tvCharge.setText(loginResponse.getData().getUserinfo().getBalance() + "元");
                    }
                }
            });
        }
    }


    @Override
    protected void onResume() {
        refreshAlways();
        switch (isFresh) {
            case 2:
                fab1.setVisibility(View.GONE);
                mainRecycle.scrollToPosition(0);
                page = 1;
                initData(false);
                break;
            case 1:
                if (clickPosition != -1 && clickRid != -1) {
                    RedPacketListApi.redPacketDetailUnLogin(MainActivity.this, clickRid, new OnRequestCompletedListener<RedPacketDetailResponse>() {
                        @Override
                        public void onCompleted(RedPacketDetailResponse response, String msg) {
                            dismissProgressDialog();
                            if (response != null) {
                                RedPacketDetailResponse.DataBean data = response.getData();
                                adapter.replaceItem(clickPosition, data);
                            } else {
                                ToastUtils.toTosat(MainActivity.this, msg);
                            }
                        }
                    });
                }
                break;
            default:
                break;
        }
        isFresh = 0;
        super.onResume();
    }

    private void initData(final boolean more) {
        showProgressDialog("数据加载中");
        if (MyApp.login_state == 0) {
            notLoginGetData(more);
        } else {
            RedPacketListApi.redPacketListLogined(MainActivity.this, MyApp.token
                    , MyApp.location == null ? "0" : MyApp.location.getLatitude() + ""
                    , MyApp.location == null ? "0" : MyApp.location.getLongitude() + "", 10, page, sort, new OnRequestCompletedListener<RedPacketListResponse>() {
                        @Override
                        public void onCompleted(RedPacketListResponse response, String msg) {
                            adapter.loadMoreComplete();
                            dismissProgressDialog();
                            if (response == null) {
                                mainSwiperefreshlayout.setRefreshing(false);
                                adapter.setEnableLoadMore(true);
                                ToastUtils.toTosat(MainActivity.this, msg);
                                return;
                            }
                            if (response.getErr() == 0) {
                                if (response.getData() == null) {
                                    adapter.setEnableLoadMore(false);
                                    adapter.loadMoreEnd();
                                    return;
                                }
                                if (more) {
                                    adapter.addData(response.getData());
                                } else {
                                    adapter.setNewData(response.getData());
                                    mainSwiperefreshlayout.setRefreshing(false);
                                }
                                if (response.getData() != null && response.getData().size() > 0) {
                                    adapter.setEnableLoadMore(true);
                                } else {
                                    adapter.setEnableLoadMore(false);
                                    adapter.loadMoreEnd();
                                }
                            } else {
                                MyApp.login_state = 0;
                                mainSwiperefreshlayout.setRefreshing(false);
                                adapter.setEnableLoadMore(true);
                                ToastUtils.toTosat(MainActivity.this, msg);
                                notLoginGetData(more);
                            }

                        }
                    });

         /*   RedPacketListApi.get_my_wallet(MainActivity.this, MyApp.token, new OnRequestCompletedListener<WalletResponse>() {
                @Override
                public void onCompleted(WalletResponse response, String msg) {
                    if (response.getErr() == 0) {
                        tvCharge.setText(response.getData().getBalance() + "元");
                    }
                }
            });*/

        }
    }

    private void refreshAlways() {
        RedPacketListApi.update_user_location(MainActivity.this, MyApp.token, MyApp.location == null ? "0" : MyApp.location.getLatitude() + ""
                , MyApp.location == null ? "0" : MyApp.location.getLongitude() + "", new OnRequestCompletedListener<WalletResponse>() {
                    @Override
                    public void onCompleted(WalletResponse response, String msg) {

                    }
                });
        if (!TextUtils.isEmpty(MyApp.clientid)) {
            RedPacketListApi.update_device(MainActivity.this, MyApp.token, MyApp.clientid, new OnRequestCompletedListener<WalletResponse>() {
                @Override
                public void onCompleted(WalletResponse response, String msg) {

                }
            });
        }

        refreshData();
    }

    private void notLoginGetData(final boolean more) {
        tvName.setText("未登录");
        imageView.setImageResource(R.mipmap.head);
        tvCharge.setText("");
        RedPacketListApi.redPacketListUnlogin(MainActivity.this
                , MyApp.location == null ? "0" : MyApp.location.getLatitude() + ""
                , MyApp.location == null ? "0" : MyApp.location.getLongitude() + "", 10, page, sort, new OnRequestCompletedListener<RedPacketListResponse>() {
                    @Override
                    public void onCompleted(RedPacketListResponse response, String msg) {
                        adapter.loadMoreComplete();
                        dismissProgressDialog();
                        if (response == null) {
                            mainSwiperefreshlayout.setRefreshing(false);
                            adapter.setEnableLoadMore(true);
                            ToastUtils.toTosat(MainActivity.this, msg);
                            return;
                        }
                        if (response.getErr() == 0) {
                            if (response.getData() == null) {
                                adapter.setEnableLoadMore(false);
                                adapter.loadMoreEnd();
                                return;
                            }
                            if (more) {
                                adapter.addData(response.getData());
                            } else {
                                adapter.setNewData(response.getData());
                                mainSwiperefreshlayout.setRefreshing(false);
                            }
                            if (response.getData() != null && response.getData().size() > 0) {
                                adapter.setEnableLoadMore(true);
                            } else {
                                adapter.setEnableLoadMore(false);
                                adapter.loadMoreEnd();
                            }
                        } else {
                            MyApp.login_state = 0;
                            mainSwiperefreshlayout.setRefreshing(false);
                            adapter.setEnableLoadMore(true);
                            ToastUtils.toTosat(MainActivity.this, msg);
                        }

                    }
                });
    }

    private void setAdapter() {
        manager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
//        footView = getLayoutInflater().inflate(R.layout.load_more_foot, null);
        mainRecycle.setLayoutManager(manager);
        adapter = new HomeRecycleViewAdapter(R.layout.home_recycle_item, MainActivity.this, data);
        mainRecycle.setAdapter(adapter);
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                clickDataBean = (RedPacketListResponse.DataBean) adapter.getData().get(position);
                clickPosition = position;
                clickRid = clickDataBean.getRid();
                switch (clickDataBean.getGot()) {
                    case 1:
                        MyApp.TYPE = 5;
                        RedPacketActivity.luncher(MainActivity.this, clickDataBean);
                        break;
                    case 2:
                        if (clickDataBean.getRemainCount() != 0) {
                            MyApp.TYPE = 2;
                            RedPacketActivity.luncher(MainActivity.this, clickDataBean);
                        } else {
                            MyApp.TYPE = 6;
                            RedPacketActivity.luncher(MainActivity.this, clickDataBean);
                        }
                        break;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        mLocationClient.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    private void assinview() {
        getActive(false);
//        startActivity(new Intent(MainActivity.this, GaodelocationService.class));
        mainSwiperefreshlayout.setEnabled(false);
        showProgressDialog("请稍后...");
        startLocal();
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE
                , Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.CAMERA
                , Manifest.permission.ACCESS_COARSE_LOCATION
                , Manifest.permission.ACCESS_FINE_LOCATION
                , Manifest.permission.READ_PHONE_STATE).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
            }

            @Override
            public void onNext(@NonNull Boolean aBoolean) {

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
        setAdapter();
        mainSwiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mainSwiperefreshlayout.setRefreshing(true);
                page = 1;
                initData(false);
            }
        });
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                initData(true);
            }
        }, mainRecycle);
        adapter.setLoadMoreView(new LoadMoreView() {
            @Override
            public int getLayoutId() {
                return R.layout.load_more;
            }

            @Override
            protected int getLoadingViewId() {
                return R.id.load_more_loading_view;
            }

            @Override
            protected int getLoadFailViewId() {
                return R.id.load_more_load_fail_view;
            }

            @Override
            protected int getLoadEndViewId() {
                return R.id.load_more_load_end_view;
            }

        });
        switch (MyApp.login_state) {
            case 0:
                tvName.setText("未登录");
                imageView.setImageResource(R.mipmap.head);
                tvCharge.setText("");
                break;
            case 1:
                tvCharge.setText("");
                tvName.setText("");
                break;
        }
        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                drawerView.setClickable(true);

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        mainRecycle.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    int position = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    Log.e("...", position + "");
                    if (position >= 6) {
                        fab1.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    int position = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    Log.e("...", position + "");
                    if (position >= 6) {
                        fab1.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    private void getActive(final boolean isShow) {
        RedPacketListApi.getActive(MainActivity.this, MyApp.token, new OnRequestCompletedListener<ActiveResponse>() {
            @Override
            public void onCompleted(ActiveResponse response, String msg) {
                if (response != null && response.getErr() == 0) {
                    List<ActiveResponse.DataBean> data = response.getData();
                    ArrayList<ActiveResponse.DataBean> data1 = new ArrayList<>();
                    if (data != null && data.size() > 0) {
                        for (int i = 0; i < data.size(); i++) {
                            data1.add(data.get(i));
                        }
                        ActiveFragment activeFragment = new ActiveFragment(data1);
                        activeFragment.show(getFragmentManager(), "2");
                    } else {
                        if (isShow) {
                            ToastUtils.toTosat(MainActivity.this, "不好意思，暂无活动");
                        }
                    }
                } else {
                    if (isShow) {
                        ToastUtils.toTosat(MainActivity.this, msg);
                    }
                }
            }
        });
    }

    AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            dismissProgressDialog();

            if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
                mainSwiperefreshlayout.setEnabled(true);
                MyApp.location = aMapLocation;
                if (!MyApp.local) {
                    page = 1;
                    initData(false);
                    MyApp.local = true;
                }
                Log.e("...", aMapLocation.getAddress());
            }
        }
    };

    private void startLocal() {

        mLocationClient = new AMapLocationClient(getApplicationContext());
        mLocationClient.setLocationListener(mLocationListener);
      /*  mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setOnceLocation(true);
//设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        mLocationOption.setNeedAddress(true);
        mLocationOption.setHttpTimeOut(20000);
        mLocationOption.setHttpTimeOut(20000);
        mLocationOption.setInterval(2000);
        mLocationOption.setLocationCacheEnable(true);*/

        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//        //设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
//        aMapLocationClientOption.setLocationMode(AMapLocationMode.Battery_Saving);
//        //设置定位模式为AMapLocationMode.Device_Sensors，仅设备模式。
//        aMapLocationClientOption.setLocationMode(AMapLocationMode.Device_Sensors);

        //获取一次定位结果：
        //该方法默认为false。
        //aMapLocationClientOption.setOnceLocation(true);
        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。
        // 如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        //aMapLocationClientOption.setOnceLocationLatest(true);

        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。 如果设置了上面的一次定位，则这里的连续定位无效，所以一次定位的方法是默认为false
        mLocationOption.setInterval(2000);

        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);

        //设置是否强制刷新WIFI，默认为true，强制刷新。
        mLocationOption.setWifiActiveScan(false);

        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);

        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(20000);

        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(true);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
//启动定位
        mLocationClient.startLocation();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @OnClick({R.id.tv_red, R.id.tv_prize_invited, R.id.tv_red_maneger
            , R.id.tv_save, R.id.tv_contect_phone, R.id.mainactivity_person_liner
            , R.id.mainactivity_select_liner, R.id.send_red_packet_img
            , R.id.imageView, R.id.tv_active, R.id.tv_message})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_red:
                switch (MyApp.login_state) {
                    case 0:
                        LoginActivity.luncher(MainActivity.this);
                        break;
                    case 1:
                        WalletActivity.luncher(MainActivity.this);
                        break;
                }
                break;
            case R.id.tv_prize_invited:
                switch (MyApp.login_state) {
                    case 0:
                        LoginActivity.luncher(MainActivity.this);
                        break;
                    case 1:
                        ShareActivity.luncher(MainActivity.this);
                        break;
                }
                break;
            case R.id.tv_red_maneger:
                switch (MyApp.login_state) {
                    case 0:
                        LoginActivity.luncher(MainActivity.this);
                        break;
                    case 1:
                        RedMangerActivity.luncher(MainActivity.this);
                        break;
                }
                break;
            case R.id.tv_save:
                switch (MyApp.login_state) {
                    case 0:
                        LoginActivity.luncher(MainActivity.this);
                        break;
                    case 1:
                        CollectActivity.luncher(MainActivity.this);
                        break;
                }

                break;
            case R.id.tv_contect_phone:
                playPhone();
                break;
            case R.id.mainactivity_person_liner:
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
            case R.id.mainactivity_select_liner:
                showOrder();
                break;
            case R.id.send_red_packet_img:
                switch (MyApp.login_state) {
                    case 0:
                        LoginActivity.luncher(MainActivity.this);
                        break;
                    case 1:
                        SetMoneyActivity.luncher(MainActivity.this);
                        break;
                }
                break;
            case R.id.imageView:
                switch (MyApp.login_state) {
                    case 0:
                        LoginActivity.luncher(MainActivity.this);
                        break;
                    case 1:
                        PersonDataActivity.luncher(MainActivity.this);
                        break;
                }
                break;
            case R.id.tv_active:
                getActive(true);
                break;
            case R.id.tv_message:
                switch (MyApp.login_state) {
                    case 0:
                        LoginActivity.luncher(MainActivity.this);
                        break;
                    case 1:
                        MyMessageActivity.launch(MainActivity.this);
                        break;
                }

                break;
        }
    }

    private void showOrder() {
        popupOrder = new PopupWindow(mainactivitySelectLiner, DensityUtil.dip2px(MainActivity.this, 150), DensityUtil.dip2px(MainActivity.this, 140));
        View view = getLayoutInflater().inflate(R.layout.popup_order_layout, null);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.alpha = 0.5f;
        getWindow().setAttributes(attributes);
        popupOrder.setContentView(view);
        popupOrder.setFocusable(true);
        popupOrder.setOutsideTouchable(true);
        popupOrder.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        popupOrder.showAsDropDown(mainactivitySelectLiner, 10, 0);
        TextView tv_main_person = (TextView) view.findViewById(R.id.tv_main_person);
        TextView tv_main_left = (TextView) view.findViewById(R.id.tv_main_left);
        TextView tv_main_time = (TextView) view.findViewById(R.id.tv_main_time);
        TextView tv_main_count = (TextView) view.findViewById(R.id.tv_main_count);
        tv_main_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sort = 1;
                page = 1;
                mainRecycle.scrollToPosition(0);
                initData(false);
                popupOrder.dismiss();
            }
        });
        tv_main_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sort = 3;
                page = 1;
                mainRecycle.scrollToPosition(0);
                initData(false);
                popupOrder.dismiss();
            }
        });
        tv_main_person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sort = 2;
                page = 1;
                mainRecycle.scrollToPosition(0);
                initData(false);
                popupOrder.dismiss();
            }
        });
        tv_main_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sort = 4;
                page = 1;
                mainRecycle.scrollToPosition(0);
                initData(false);
                popupOrder.dismiss();
            }
        });
        popupOrder.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams attributes = getWindow().getAttributes();
                attributes.alpha = 1f;
                getWindow().setAttributes(attributes);
            }
        });
    }

    private void playPhone() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("询问")
                .setIcon(R.drawable.logo_s)
                .setMessage("是否联系客服：028-64095902")
                .setPositiveButton("拨打", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "028-64095902"));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("取消", null)
                .show();

    }


    public static void luncher(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Intent intent) {
        switch (intent.getAction()) {
            case "refreshUser":
                LoginResponse user = MyApp.getInstance().user;
                try {
                    GlideUtil.loadImg(MainActivity.this, R.mipmap.head, user.getData().getUserinfo().getUserPic(), imageView);
                } catch (Exception e) {
                    imageView.setImageResource(R.mipmap.head);
                }

                tvName.setText(user.getData().getUserinfo().getUsername() != null ? user.getData().getUserinfo().getUsername() : "");
                tvCharge.setText(user.getData().getUserinfo().getBalance() + "元");
                break;
            case "refreshUserhead":
                try {
                    GlideUtil.loadImg(MainActivity.this, R.mipmap.head, MyApp.getInstance().user.getData().getUserinfo().getUserPic(), imageView);
                } catch (Exception e) {
                    imageView.setImageResource(R.mipmap.head);
                }
                break;
            case "refreshUserNothing":
                tvName.setText("未登录");
                imageView.setImageResource(R.mipmap.head);
                tvCharge.setText("");
                break;
            case "REFRESH":
                isFresh = 2;
                break;
            case "REFRESH_POSITION":
                isFresh = 1;
                break;
        }
    }


}
