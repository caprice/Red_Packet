package com.haoxiong.taotao.ui.main.fragment;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.fan.service.response.ActiveResponse;
import com.haoxiong.taotao.MyApp;
import com.haoxiong.taotao.R;
import com.haoxiong.taotao.ui.login.LoginActivity;
import com.haoxiong.taotao.util.GlideUtil;
import com.haoxiong.taotao.util.ToastUtils;
import com.haoxiong.taotao.webview.WebViewActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：
 * 作者： YinJin
 * 创建时间：2017/11/16.21:23
 */

public class ActiveFragment extends DialogFragment {
    private com.bigkoo.convenientbanner.ConvenientBanner convenientBanner;
    private ArrayList<ActiveResponse.DataBean> data;
    private List<String> imgs = new ArrayList<>();
    private ImageView imgactiveclose;

    public ActiveFragment(ArrayList<ActiveResponse.DataBean> data) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("img", data);
        setArguments(bundle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.fragment_activity, container, false);
        this.imgactiveclose = (ImageView) view.findViewById(R.id.img_active_close);
        this.convenientBanner = (ConvenientBanner) view.findViewById(R.id.convenientBanner);
        Bundle bundle = getArguments();
        data = bundle.getParcelableArrayList("img");
        imgactiveclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().setCanceledOnTouchOutside(false);
        if (data != null && data.size() > 0) {
            for (int i = 0; i < data.size(); i++) {
                imgs.add(data.get(i).getHdtp());
//                imgs.add("https://ss2.baidu.com/-vo3dSag_xI4khGko9WTAnF6hhy/image/h%3D220/sign=04bcb05cc3177f3e0f34fb0f40ce3bb9/faedab64034f78f02242b81973310a55b2191c8a.jpg");
            }

            convenientBanner.setPages(
                    new CBViewHolderCreator<LocalImageHolderView>() {
                        @Override
                        public LocalImageHolderView createHolder() {
                            return new LocalImageHolderView();
                        }
                    }, imgs)
                    //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                    .setPageIndicator(new int[]{R.drawable.one, R.drawable.two})
                    //设置指示器的方向
                    .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                    .startTurning(2000)     //设置自动切换（同时设置了切换时间间隔）
                    .setManualPageable(true);  //设置手动影响（设置了该项无法手动切换）

            //设置翻页的效果，不需要翻页效果可用不设
            convenientBanner.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                 /*   ToastUtils.toTosat(getActivity(), position + "");
                    WebViewActivity.lunch(getActivity(), imgs.get(position));*/
                    if (MyApp.login_state == 1) {
                        WebViewActivity.lunch(getActivity(), data.get(position).getHdurl()+MyApp.token);
                    } else {
                        LoginActivity.luncher(getActivity());
                    }

                }
            });

        } else {
            dismiss();
        }
    }

    class LocalImageHolderView implements Holder<String> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, String data) {
            imageView.setImageResource(R.mipmap.item);
            GlideUtil.loadImg(context, data, imageView);
        }
    }
}
