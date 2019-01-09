package com.zdy.baselibrary.base.mvp;

import android.os.Bundle;

import com.zdy.baselibrary.base.BaseCompatActivity;

public abstract class BaseActivityMvp<V extends Contract.ViewMvp, P extends BasePresenterMvp> extends BaseCompatActivity {

    public P present;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        present = createPresent();
        if (present != null) {
            present.onAttach((V) this);
        }
        initDataFromServer();
    }

    /**
     * 获取数据
     */
    protected abstract void initDataFromServer();

    /**
     * 创建Present
     *
     * @return P
     */
    protected abstract P createPresent();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (present != null) {
            present.disAttach();
        }
    }
}
