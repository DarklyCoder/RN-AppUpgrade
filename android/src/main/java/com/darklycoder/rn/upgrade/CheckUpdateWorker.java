package com.darklycoder.rn.upgrade;

import org.lzh.framework.updatepluginlib.base.CheckWorker;
import org.lzh.framework.updatepluginlib.model.CheckEntity;

public class CheckUpdateWorker extends CheckWorker {

    /**
     * 外部传入的更新数据
     */
    static String response = "";

    @Override
    protected boolean useAsync() {
        return false;
    }


    @Override
    protected String check(CheckEntity entity) {
        return response;
    }

    @Override
    protected void asyncCheck(CheckEntity entity) {
        onResponse(response);
    }
}
