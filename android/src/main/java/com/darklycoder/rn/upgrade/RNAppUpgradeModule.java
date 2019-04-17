package com.darklycoder.rn.upgrade;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import org.lzh.framework.updatepluginlib.UpdateBuilder;
import org.lzh.framework.updatepluginlib.UpdateConfig;
import org.lzh.framework.updatepluginlib.base.UpdateChecker;
import org.lzh.framework.updatepluginlib.base.UpdateParser;
import org.lzh.framework.updatepluginlib.model.Update;

public class RNAppUpgradeModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    public RNAppUpgradeModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;


        UpdateConfig.getConfig()
                .setUpdateChecker(new UpdateChecker() {
                    @Override
                    public boolean check(Update update) throws Exception {
                        return false;
                    }
                })
                .setUrl("")// 配置检查更新的API接口
                .setUpdateParser(new UpdateParser() {
                    @Override
                    public Update parse(String response) {

                        return null;
                    }

                });
    }

    @Override
    public String getName() {
        return "RNAppUpgrade";
    }

    /**
     * 升级接口
     */
    @ReactMethod
    public void upgrade(Callback callback) {

        UpdateBuilder.create().check();// 启动更新任务
    }

    /**
     * 打开appStore
     */
    @ReactMethod
    public void openAppStore(Callback callback) {

    }

}