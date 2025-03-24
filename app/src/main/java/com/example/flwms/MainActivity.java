package com.example.flwms;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.GridLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.flwms.utils.PrefsUtils;

import java.util.LinkedHashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String lastUrl = PrefsUtils.getLastUrl(this);
        if (lastUrl != null) {

            startWebActivity(lastUrl);
            finish();
            return;
        }
        setContentView(R.layout.activity_main);
        initButtons();
    }

    private void initButtons() {
        GridLayout container = findViewById(R.id.button_container);
        Map<String, String> buttonMap = new LinkedHashMap<>();
        {
            buttonMap.put("A1入库", "A1_IN");
            buttonMap.put("A2入库", "A2_IN");
            buttonMap.put("A3入库", "A3_IN");
            buttonMap.put("A4入库", "A4_IN");
            buttonMap.put("B1入库", "B1_IN");
            buttonMap.put("B2入库", "B2_IN");
            buttonMap.put("B3入库", "B3_IN");
            buttonMap.put("B4入库", "B4_IN");
            buttonMap.put("A1出库", "A1_OUT");
            buttonMap.put("A2出库", "A2_OUT");
            buttonMap.put("A3出库", "A3_OUT");
            buttonMap.put("A4出库", "A4_OUT");
            buttonMap.put("B1出库", "B1_OUT");
            buttonMap.put("B2出库", "B2_OUT");
            buttonMap.put("B3出库", "B3_OUT");
            buttonMap.put("B4出库", "B4_OUT");
            buttonMap.put("液压平台", "B5");
        }

        int buttonWidth = dpToPx(200);
        int buttonHeight = dpToPx(80);

        for (Map.Entry<String, String> entry : buttonMap.entrySet()) {

            Button btn = new Button(this);
            btn.setText(entry.getKey());

            btn.setBackgroundResource(R.drawable.button_focus_border);
            btn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
            btn.setTextColor(Color.WHITE);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = buttonWidth;
            params.height = buttonHeight;
            params.setMargins(dpToPx(10), dpToPx(10), dpToPx(10), dpToPx(10));


            btn.setOnFocusChangeListener((v, hasFocus) -> {
                if (hasFocus) {
                    v.animate().scaleX(1.1f).scaleY(1.1f).setDuration(150).start();
                } else {
                    v.animate().scaleX(1.0f).scaleY(1.0f).setDuration(150).start();
                }
            });

            btn.setOnClickListener(v -> {
                PrefsUtils.saveLastUrl(this, entry.getValue());
                startWebActivity(entry.getValue());
                finish();
            });

            container.addView(btn, params);
        }
        if (!buttonMap.isEmpty()) {
            container.getChildAt(0).requestFocus();
        }
    }

    private void startWebActivity(String url) {
        Intent intent = new Intent(this, WebActivity.class);
        startActivity(intent);
    }

    private int dpToPx(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }

}