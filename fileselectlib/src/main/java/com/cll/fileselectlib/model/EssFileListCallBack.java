package com.cll.fileselectlib.model;

import java.util.List;

/**
 * EssFileListCallBack
 * Created by 李波 on 2018/3/5.
 */

public interface EssFileListCallBack {
    void onFindFileList(String queryPath, List<EssFile> essFileList);
}
