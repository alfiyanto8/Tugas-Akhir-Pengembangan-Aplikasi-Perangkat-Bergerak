package com.example.tugass.activities;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tugass.R;
import com.example.tugass.adapter.MainAdapter;
import com.example.tugass.model.ModelMain;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<ModelMain> modelMain = new ArrayList<>();
    MainAdapter mainAdapter;
    RecyclerView rvListMateri;
    SearchView searchMateri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set transparent statusbar
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        rvListMateri = findViewById(R.id.rvListMateri);
        searchMateri = findViewById(R.id.searchMateri);

        //transparent background searchview
        int searchPlateId = searchMateri.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        View searchPlate = searchMateri.findViewById(searchPlateId);
        if (searchPlate != null) {
            searchPlate.setBackgroundColor(Color.TRANSPARENT);
        }

        searchMateri.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchMateri.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mainAdapter.getFilter().filter(newText);
                return true;
            }
        });

        rvListMateri.setLayoutManager(new LinearLayoutManager(this));
        rvListMateri.setHasFixedSize(true);

        //get data json
        getNamaMateri();

    }

    private void getNamaMateri() {
        try {
            InputStream stream = getAssets().open("materiku.json");
            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            String strContent = new String(buffer, StandardCharsets.UTF_8);
            try {
                JSONObject jsonObject = new JSONObject(strContent);
                JSONArray jsonArray = jsonObject.getJSONArray("daftar_materi");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    ModelMain dataApi = new ModelMain();
                    dataApi.setNama(object.getString("nama"));
                    dataApi.setDeskripsi(object.getString("deskripsi"));
                    dataApi.setImage(object.getString("image_url"));
                    modelMain.add(dataApi);
                }
                mainAdapter = new MainAdapter(this, modelMain);
                rvListMateri.setAdapter(mainAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException ignored) {
            Toast.makeText(MainActivity.this, "Ups, ada yang tidak beres. " +
                    "Coba ulangi beberapa saat lagi.", Toast.LENGTH_SHORT).show();
        }
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