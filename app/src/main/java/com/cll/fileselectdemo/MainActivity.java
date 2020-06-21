package com.cll.fileselectdemo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cll.fileselectlib.FilePicker;
import com.cll.fileselectlib.model.EssFile;
import com.cll.fileselectlib.util.Const;
import com.cll.fileselectlib.util.DialogUtil;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_CHOOSE = 23;
    private Button bt;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AndPermission
                .with(this)
                .permission(Permission.READ_EXTERNAL_STORAGE,Permission.WRITE_EXTERNAL_STORAGE)
                .onDenied(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        //拒绝权限
                        DialogUtil.showPermissionDialog(MainActivity.this,Permission.transformText(MainActivity.this, permissions).get(0));
                    }
                })
                .start();
        bt = findViewById(R.id.bt_btn);
        textView = findViewById(R.id.tv_info);

        bt.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 FilePicker.from(MainActivity.this)
                         .chooseForBrowser()
                         .isSingle()
                         .requestCode(REQUEST_CODE_CHOOSE)
                         .start();
             }
         });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CODE_CHOOSE) {
            ArrayList<EssFile> essFileList = data.getParcelableArrayListExtra(Const.EXTRA_RESULT_SELECTION);
            Log.d("CLLSTUDY", "size: "+essFileList.size());
            StringBuilder builder = new StringBuilder();
            for (EssFile file :
                    essFileList) {
                builder.append(file.getMimeType()).append(" | ").append(file.getName()).append("\n\n");
                Log.d("CLLSTUDY", "onActivityResult: "+file.getName());
            }
            textView.setText(builder.toString());
        }
    }
}