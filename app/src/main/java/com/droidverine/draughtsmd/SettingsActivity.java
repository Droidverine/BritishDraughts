package com.droidverine.draughtsmd;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {
    SeekBar seekBar, seekBar1;
    View view, player1bg, player2bg;
    ArrayList<String> colorale;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        // Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Settings ");
        sharedPreferences = getSharedPreferences("draughts", 0);

        colorale = new ArrayList<>();
        player1bg = findViewById(R.id.player1Vbg);
        player2bg = findViewById(R.id.player2Vbg);
        editor = sharedPreferences.edit();
        String temp1 = sharedPreferences.getString("player1", null);
        String temp2 = sharedPreferences.getString("player2", null);

        if (temp1 != null && temp2!=null) {


            player1bg.setBackgroundColor(Color.parseColor(temp1));
            player2bg.setBackgroundColor(Color.parseColor(temp2));

        }
        seekBar = findViewById(R.id.seekbar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                float[] hsvColor = {0, 1, 1};
                // generate only hue component in range [0, 360),
                // leaving saturation and brightness maximum possible
                hsvColor[0] = 244f * seekBar.getProgress() / seekBar.getMax();
                player1bg.setBackgroundColor(Color.HSVToColor(hsvColor));
                ColorDrawable buttonColor = (ColorDrawable) player1bg.getBackground();
                int colorId = buttonColor.getColor();
                String hexColorplayer1 = String.format("#%06X", (0xFFFFFF & colorId));
                editor.putString("player1", hexColorplayer1);
                editor.commit();
                Log.d("Kaai color", "" + hexColorplayer1);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBar1 = findViewById(R.id.seekbar1);
        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                float[] hsvColor = {0, 1, 1};
                // generate only hue component in range [0, 360),
                // leaving saturation and brightness maximum possible
                hsvColor[0] = 244f * seekBar1.getProgress() / seekBar1.getMax();
                player2bg.setBackgroundColor(Color.HSVToColor(hsvColor));
                ColorDrawable buttonColor = (ColorDrawable) player2bg.getBackground();
                int colorId = buttonColor.getColor();
                String hexColorplayer2 = String.format("#%06X", (0xFFFFFF & colorId));
                editor.putString("player2", hexColorplayer2);
                editor.commit();
                Log.d("Kaai color", "" + hexColorplayer2);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        //Spinner spinner =findViewById(R.id.player1score);

        /*
        final Spinner spinner = findViewById(R.id.spinnerplayer1);

        ArrayList<String> categories = new ArrayList<String>();
        categories.add("BLUE");
        categories.add("RED");
        categories.add("GREEN");


        ArrayAdapter aa = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, categories);
        //aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa);
        aa.notifyDataSetChanged();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });
        */
        // spinner.setSelection(0);

    }
}
