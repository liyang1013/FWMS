package com.example.flwms;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.flwms.utils.PrefsUtils;

import org.xwalk.core.XWalkNavigationHistory;
import org.xwalk.core.XWalkSettings;
import org.xwalk.core.XWalkView;

public class WebActivity extends AppCompatActivity {

    private long backPressedTime = 0;
    private XWalkView xWalkView;
    private static final String B5URL = "http://172.16.200.242:8080/webroot/decision/view/form?viewlet=立体库/看板报表/出库订单作业监控数据_液压平台.frm";
    private static final String INURL = "http://172.16.200.242:8080/webroot/decision/view/form?viewlet=立体库/看板报表/入库作业监控数据.frm&area=";
    private static final String OUTURL = "http://172.16.200.242:8080/webroot/decision/view/form?viewlet=立体库/看板报表/出库订单作业监控数据.frm&area=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        xWalkView = findViewById(R.id.xwalk_view);

        XWalkSettings settings = xWalkView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setUserAgentString("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3");

        xWalkView.setFocusable(true);
        xWalkView.setFocusableInTouchMode(true);
        String url = PrefsUtils.getLastUrl(this);
        if (url != null) {
            if (url.contains("IN")) {
                xWalkView.loadUrl(INURL + url, null);
            } else if (url.contains("OUT")) {
                xWalkView.loadUrl(OUTURL + url, null);
            } else {
                xWalkView.loadUrl(B5URL, null);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (xWalkView.getNavigationHistory().canGoBack()) {
            xWalkView.getNavigationHistory().navigate(XWalkNavigationHistory.Direction.BACKWARD, 1);
        } else {
            if (backPressedTime + 2000 > System.currentTimeMillis()) {
                super.onBackPressed();
                xWalkView.onDestroy();
                finishAffinity();
                System.exit(0);
            } else {
                Toast.makeText(this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
                backPressedTime = System.currentTimeMillis();
            }
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (xWalkView != null) {
            xWalkView.pauseTimers();
            xWalkView.onHide();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (xWalkView != null) {
            xWalkView.resumeTimers();
            xWalkView.onShow();
        }
    }

    @Override
    protected void onDestroy() {
        if (xWalkView != null) {
            xWalkView.onDestroy();
        }
        super.onDestroy();
    }
}
