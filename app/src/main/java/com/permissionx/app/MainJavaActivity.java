package com.permissionx.app;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.ExplainReasonCallbackWithBeforeParam;
import com.permissionx.guolindev.callback.ForwardToSettingsCallback;
import com.permissionx.guolindev.callback.RequestCallback;
import com.permissionx.guolindev.request.ExplainScope;
import com.permissionx.guolindev.request.ForwardScope;

import java.util.List;

public class MainJavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_java);
        Button makeCallBtn = findViewById(R.id.makeCallBtn);
        makeCallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PermissionX.init(MainJavaActivity.this)
                        .permissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .onExplainRequestReason(new ExplainReasonCallbackWithBeforeParam() {
                            @Override
                            public void onExplainReason(ExplainScope scope, List<String> deniedList, boolean beforeRequest) {
                                scope.showRequestReasonDialog(deniedList, "为了保证程序正常工作，请您同意以下权限申请" + deniedList, "我已明白");
                            }
                        })
                        .onForwardToSettings(new ForwardToSettingsCallback() {
                            @Override
                            public void onForwardToSettings(ForwardScope scope, List<String> deniedList) {
                                scope.showForwardToSettingsDialog(deniedList, "您需要去应用程序设置当中手动开启权限" + deniedList, "我已明白");
                            }
                        })
                        .request(new RequestCallback() {
                            @Override
                            public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList) {
                                if (allGranted) {
                                    Toast.makeText(MainJavaActivity.this, "所有申请的权限都已通过", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(MainJavaActivity.this, "您拒绝了如下权限：" + deniedList, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}