package com.jstyle.test2025.activity;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jstyle.blesdk2025.Util.BleSDK;
import com.jstyle.blesdk2025.constant.BleConst;
import com.jstyle.test2025.R;
import com.jstyle.test2025.adapter.GpsDataAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EcgDataActivity extends BaseActivity {

    @BindView(R.id.RecyclerView_exerciseHistory)
    RecyclerView ECGDATA;
    private GpsDataAdapter activityModeDataAdapter;
    int index=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecghisdata);
        ButterKnife.bind(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        ECGDATA.setLayoutManager(linearLayoutManager);
        activityModeDataAdapter = new GpsDataAdapter();
        ECGDATA.setAdapter(activityModeDataAdapter);
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        ECGDATA.addItemDecoration(dividerItemDecoration);
    }


    @OnClick({R.id.readECG,R.id.delete_ECG})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.readECG:
                index=0;
                sendValue(BleSDK.GetECGwaveform(true,0x00));
                break;
            case R.id.delete_ECG:
                sendValue(BleSDK.GetECGwaveform(false,0x00));
                activityModeDataAdapter.Clear();
                break;
        }
    }

    @Override
    public void dataCallback(Map<String, Object> maps) {
        super.dataCallback(maps);
        if(null!=maps){
            String dataType= getDataType(maps);
            Log.e("dataCallback",maps.toString());
            switch (dataType){
                case BleConst.ECGdata:
                    if(index!=9){
                        index++;
                        sendValue(BleSDK.GetECGwaveform(true,index));
                        activityModeDataAdapter.ADDData(maps.toString());
                    }else{
                        index=0;
                    }
                    break;
                case BleConst.DeleteECGdata:
                    activityModeDataAdapter.ADDData(maps.toString());
                    break;
            }
        }
      }

}
