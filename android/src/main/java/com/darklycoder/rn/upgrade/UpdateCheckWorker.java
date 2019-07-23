package com.darklycoder.rn.upgrade;

import org.lzh.framework.updatepluginlib.base.UpdateChecker;
import org.lzh.framework.updatepluginlib.model.Update;

public class UpdateCheckWorker extends UpdateChecker {
    @Override
    public boolean check(Update update) throws Exception {
        return true;
    }
}