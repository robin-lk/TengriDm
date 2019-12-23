package com.qdgdcm.apphome.fragment.homeitem;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.lk.robin.commonlibrary.app.AppFragment;
import com.lk.robin.commonlibrary.config.ConstantsRouter;
import com.lk.robin.commonlibrary.tools.Factory;
import com.lk.robin.langlibrary.JingPinDataHelper;
import com.lk.robin.langlibrary.QingGanDataHelper;
import com.lk.robin.langlibrary.XiangShengDataHelper;
import com.lk.robin.langlibrary.bean.ContentBean;
import com.qdgdcm.apphome.R;
import com.qdgdcm.apphome.R2;
import com.qdgdcm.apphome.fragment.BoutiqueListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
@Route(path = ConstantsRouter.Home.HomeMainProgramInfoFragment)
public class ProgramInfoFragment extends AppFragment {
    @BindView(R2.id.btn_back)
    View btnBack;
    @BindView(R2.id.btn_share)
    View btnShare;
    @BindView(R2.id.ic_head)
    ImageView imageHead;
    @BindView(R2.id.txt_title)
    TextView txtTitle;
    @BindView(R2.id.txt_jj)
    TextView txtJJ;
    @BindView(R2.id.root_jm)
    View rootJm;
    @BindView(R2.id.root_jj)
    View rootJj;
    @BindView(R2.id.root_tj)
    View rootTj;
    @BindView(R2.id.txt_info)
    TextView txtInfo;
    @BindView(R2.id.root_qbbf)
    View rootQbbf;
    @BindView(R2.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R2.id.txt_xgnr)
    TextView txtXgnr;

    public ProgramInfoFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_program_info;
    }

    @Override
    protected void initData() {
        super.initData();
        setBar(R.id.root_jm);
        Bundle arguments = getArguments();
        if (arguments!=null){
            String title = arguments.getString("title");
            String info = arguments.getString("info");
            int ic = arguments.getInt("ic");
            imageHead.setImageResource(ic);
            txtTitle.setText(title);
            txtJJ.setText(info);
            txtInfo.setText(info);
        }
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onHomeBack();
            }
        });

    }

    private void setBar(int id) {
        rootJm.setSelected(id == R.id.root_jm);
        rootJj.setSelected(id == R.id.root_jj);
        rootTj.setSelected(id == R.id.root_tj);

        setRootView(id);
    }

    private void setRootView(int id) {
        if (id == R.id.root_jm) {
            mRecyclerView.setVisibility(View.VISIBLE);
            rootQbbf.setVisibility(View.VISIBLE);
            txtInfo.setVisibility(View.GONE);
            txtXgnr.setVisibility(View.GONE);
            setJieMu();
        } else if (id == R.id.root_jj) {
            mRecyclerView.setVisibility(View.GONE);
            rootQbbf.setVisibility(View.GONE);
            txtInfo.setVisibility(View.VISIBLE);
            txtXgnr.setVisibility(View.GONE);
        } else if (id == R.id.root_tj) {
            setTuiJian();
            mRecyclerView.setVisibility(View.VISIBLE);
            rootQbbf.setVisibility(View.GONE);
            txtInfo.setVisibility(View.GONE);
            txtXgnr.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R2.id.root_jm, R2.id.root_jj, R2.id.root_tj})
    void onClicks(View view) {
        setBar(view.getId());
    }

    private void setJieMu(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        JieMuAdapter adapter=new JieMuAdapter();
        mRecyclerView.setAdapter(adapter);
        list.clear();
        list.addAll(QingGanDataHelper.getQgJybjm());
        adapter.notifyDataSetChanged();
    }

    private void setTuiJian(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        TuiJianAdapter adapter=new TuiJianAdapter();
        mRecyclerView.setAdapter(adapter);
        list.clear();
        list.addAll(XiangShengDataHelper.getXsYmds());
        list.addAll(XiangShengDataHelper.getXsJcxs());
        adapter.notifyDataSetChanged();

    }
    private List<ContentBean> list = new ArrayList<>();
    class TuiJianAdapter extends RecyclerView.Adapter<TuiJianAdapter.ViewHolder> {
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.common_item_album_layout, parent, false);
            return new ViewHolder(inflate);
        }

        @SuppressLint("ResourceType")
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.txtJi.setText(list.get(position).albumNum);
            holder.txtInfo.setText(list.get(position).info);
            holder.txtTitle.setText(list.get(position).title);
            holder.readCount.setText(list.get(position).countRead);
            Glide.with(Factory.app()).load(list.get(position).resId).into(holder.ic);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private TextView txtTitle, txtInfo, readCount, txtJi;
            private ImageView ic;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                txtTitle = itemView.findViewById(R.id.tv_album_title);
                txtInfo = itemView.findViewById(R.id.tv_album_info);
                readCount = itemView.findViewById(R.id.tv_album_listener);
                ic = itemView.findViewById(R.id.rv_album_cover);
                txtJi = itemView.findViewById(R.id.tv_album_num);
            }
        }
    }

    class JieMuAdapter extends RecyclerView.Adapter<JieMuAdapter.ViewHolder> {
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.frag_item_jiemu_list_layout, parent, false);
            return new ViewHolder(inflate);
        }

        @SuppressLint("ResourceType")
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.txtJi.setText(list.get(position).duration+"  12月29日");
            holder.txtInfo.setText(String.valueOf(position+1));
            holder.txtTitle.setText(list.get(position).title);
            holder.readCount.setText(list.get(position).countRead);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private TextView txtTitle, txtInfo, readCount, txtJi;
            private ImageView image;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                txtTitle = itemView.findViewById(R.id.txt_title);
                txtInfo = itemView.findViewById(R.id.txt_num);
                readCount = itemView.findViewById(R.id.txt_bofang_liang);
                txtJi = itemView.findViewById(R.id.txt_bofang_time);
                image = itemView.findViewById(R.id.menu);
            }
        }
    }

}
