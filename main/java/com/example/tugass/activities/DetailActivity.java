package com.example.tugass.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tugass.R;
import com.example.tugass.model.ModelMain;
import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    public static final String DETAIL_MATERI = "DETAIL_MATERI";
    String strNamaMateri, strPenjelasanMateri;
    ModelMain modelMain;
    Toolbar toolbar;
    ImageView imageMateri;
    TextView tvNamaMateri, tvPenjelasanMateri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //set transparent statusbar
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        toolbar = findViewById(R.id.toolbar);
        imageMateri = findViewById(R.id.imageMateri);
        tvNamaMateri = findViewById(R.id.tvNamaMateri);
        tvPenjelasanMateri = findViewById(R.id.tvPenjelasanMateri);

        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //get data intent
        modelMain = (ModelMain) getIntent().getSerializableExtra(DETAIL_MATERI);
        if (modelMain != null) {
            strNamaMateri = modelMain.getNama();
            strPenjelasanMateri = modelMain.getDeskripsi();

            Glide.with(this)
                    .load(modelMain.getImage())
                    .into(imageMateri);

            tvNamaMateri.setText(strNamaMateri);
            tvPenjelasanMateri.setText(strPenjelasanMateri);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        if (on) {
            layoutParams.flags |= bits;
        } else {
            layoutParams.flags &= ~bits;
        }
        window.setAttributes(layoutParams);
    }

}