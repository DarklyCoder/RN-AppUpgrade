package com.darklycoder.rn.upgrade;

import org.lzh.framework.updatepluginlib.base.FileChecker;

public class FileCheckWorker extends FileChecker {
    @Override
    protected boolean onCheckBeforeDownload() throws Exception {
        return true;
    }

    @Override
    protected void onCheckBeforeInstall() throws Exception {

    }
}