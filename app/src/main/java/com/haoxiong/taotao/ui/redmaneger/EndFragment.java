package com.haoxiong.taotao.ui.redmaneger;


import android.app.ProgressDialog;
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
import com.haoxiong.taotao.MyApp;
import com.haoxiong.taotao.R;
import com.haoxiong.taotao.ui.redmaneger.adapter.ManagerRecycleViewEndAdapter;
import com.haoxiong.taotao.ui.redpacket.RedPacket1Activity;
import com.haoxiong.taotao.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class EndFragment extends Fragment {


    @BindView(R.id.recycle_content)
    RecyclerView recycleContent;
    private ManagerRecycleViewEndAdapter adapter;
    List<RedManagerResponse.DataBean.RedsOffBean> data = new ArrayList<>();
    private ProgressDialog progressDialog;

    public EndFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_end, container, false);
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
        adapter = new ManagerRecycleViewEndAdapter(R.layout.manager_recycle_end_item, data,getActivity());
        recycleContent.setAdapter(adapter);
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                RedManagerResponse.DataBean.RedsOnBean redsOnBean = new RedManagerResponse.DataBean.RedsOnBean();
                redsOnBean.setRid(data.get(position).getRid());
                MyApp.TYPE = 4;
                RedPacket1Activity.luncher(getActivity(),redsOnBean);
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
                    data = response.getData().getReds_off();
                    adapter.setNewData(response.getData().getReds_off());
                } else {
                    ToastUtils.toTosat(getActivity(), response.getMsg());
                }
            }
        });
    }
    public void showProgressDialog(String msg) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        progressDialog.setMessage(msg);

    }
}
