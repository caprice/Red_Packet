package com.haoxiong.taotao.ui.redmaneger;


import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fan.service.OnRequestCompletedListener;
import com.fan.service.api.RedManagerServiceApi;
import com.fan.service.response.RedManagerResponse;
import com.fan.service.response.StopOrNotResponse;
import com.haoxiong.taotao.MyApp;
import com.haoxiong.taotao.R;
import com.haoxiong.taotao.ui.redmaneger.adapter.ManagerRecycleViewRunAdapter;
import com.haoxiong.taotao.ui.redpacket.RedPacket1Activity;
import com.haoxiong.taotao.ui.redpacket.RedPacketActivity;
import com.haoxiong.taotao.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.haoxiong.taotao.R.id.item_state;


public class RunFragment extends Fragment {


    @BindView(R.id.recycle_content)
    RecyclerView recycleContent;
    List<RedManagerResponse.DataBean.RedsOnBean> data = new ArrayList<>();
    private ManagerRecycleViewRunAdapter adapter;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_run, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        assign();
        refreshData();
    }

    private void assign() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recycleContent.setLayoutManager(manager);
        adapter = new ManagerRecycleViewRunAdapter(R.layout.manager_recycle_item, getActivity(),data);
        recycleContent.setAdapter(adapter);
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(final BaseQuickAdapter adapter, View view, final int position) {
                final List<RedManagerResponse.DataBean.RedsOnBean> redsOnBeen=  adapter.getData();
                final RedManagerResponse.DataBean.RedsOnBean bean = redsOnBeen.get(position);
                switch (bean.getStatus()) {
                    case 1:
                        RedManagerServiceApi.stopOrNot(getActivity(), MyApp.token, bean.getRid(), false, new OnRequestCompletedListener<StopOrNotResponse>() {
                            @Override
                            public void onCompleted(StopOrNotResponse response, String msg) {
                                if (response.getErr() == 0) {
                                    bean.setStatus(2);
                                    adapter.notifyItemChanged(position,bean);
                                } else {
                                    ToastUtils.toTosat(getActivity(),response.getMsg());
                                }
                            }
                        });
                        break;
                    case 2:
                        RedManagerServiceApi.stopOrNot(getActivity(), MyApp.token, bean.getRid(), true, new OnRequestCompletedListener<StopOrNotResponse>() {
                            @Override
                            public void onCompleted(StopOrNotResponse response, String msg) {
                                if (response.getErr() == 0) {
                                    bean.setStatus(1);
                                    adapter.notifyItemChanged(position,bean);
                                } else {
                                    ToastUtils.toTosat(getActivity(),response.getMsg());
                                }
                            }
                        });
                        break;
                }
            }
        });
       adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
           @Override
           public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
               MyApp.TYPE = 4;
               RedPacket1Activity.luncher(getActivity(),data.get(position));
           }
       });
    }
    private void refreshData() {
        showProgressDialog("数据加载中...");
        RedManagerServiceApi.redManager(getActivity(), MyApp.token, new OnRequestCompletedListener<RedManagerResponse>() {
            @Override
            public void onCompleted(RedManagerResponse response, String msg) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                if (response.getErr() == 0) {
                    data.clear();
                    data.addAll(response.getData().getReds_on());
                    adapter.setNewData(response.getData().getReds_on());
                } else {
                    ToastUtils.toTosat(getActivity(),response.getMsg());
                }
            }
        });
    }

    /**
     * show a progress dialog with the given message.
     */
    public void showProgressDialog(String msg) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        progressDialog.setMessage(msg);

    }
}
