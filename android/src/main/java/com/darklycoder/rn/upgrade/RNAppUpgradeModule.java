package com.darklycoder.rn.upgrade;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import org.json.JSONObject;
import org.lzh.framework.updatepluginlib.UpdateBuilder;
import org.lzh.framework.updatepluginlib.UpdateConfig;
import org.lzh.framework.updatepluginlib.base.UpdateParser;
import org.lzh.framework.updatepluginlib.model.CheckEntity;
import org.lzh.framework.updatepluginlib.model.Update;

public class RNAppUpgradeModule extends ReactContextBaseJavaModule {

    private static final String TAG = "RNAppUpgradeModule-log";

    public RNAppUpgradeModule(ReactApplicationContext reactContext) {
        super(reactContext);

        //初始设置
        UpdateConfig.getConfig()
                .setCheckEntity(new CheckEntity().setUrl("http://test.com"))//防止报错
                .setCheckWorker(CheckUpdateWorker.class)//自定义更新检查
                .setUpdateChecker(new UpdateCheckWorker())
                .setFileChecker(new FileCheckWorker())
                .setUpdateParser(new UpdateParser() {
                    @Override
                    public Update parse(String response) {
                        Update update = new Update();

                        try {
                            JSONObject json = new JSONObject(response);

                            update.setUpdateUrl(json.optString("url"));
                            update.setForced(json.optBoolean("force", true));
                            update.setVersionCode(json.optInt("versionCode"));
                            update.setVersionName(json.optString("versionName"));
                            update.setUpdateContent(json.optString("updateContent"));

                        } catch (Exception e) {
                            Log.e(TAG, "", e);
                        }

                        return update;
                    }
                });
    }

    @Override
    public String getName() {
        return "RNAppUpgrade";
    }

    /**
     * 升级接口
     *
     * @param response json数据：
     *                 {
     *                 "url":"http://www.baidu.com",
     *                 "force":true,
     *                 "versionCode":2,
     *                 "versionName":"1.0.1",
     *                 "updateContent":"fix bugs!"
     *                 }
     */
    @ReactMethod
    public void upgrade(String response) {
        ReactApplicationContext context = getReactApplicationContext();
        if (null == context) {
            return;
        }

        CheckUpdateWorker.response = response;

        Log.d(TAG, "upgrade->" + CheckUpdateWorker.response);

        context.runOnUiQueueThread(new Runnable() {
            @Override
            public void run() {
                // 启动更新任务
                UpdateBuilder.create().check();
            }
        });
    }

    /**
     * 打开appStore
     */
    @ReactMethod
    public void openAppStore(final String pkgName) {
        final ReactApplicationContext context = getReactApplicationContext();
        if (null == context) {
            return;
        }

        context.runOnUiQueueThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    String name = TextUtils.isEmpty(pkgName) ? context.getPackageName() : pkgName;
                    intent.setData(Uri.parse("market://details?id=" + name));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                } catch (Exception e) {
                    Log.e(TAG, "您的手机上没有安装Android应用市场", e);
                }
            }
        });
    }

}