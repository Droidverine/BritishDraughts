package com.droidverine.draughtsmd;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {
    SeekBar seekBar, seekBar1, seekBar2;
    int color;
    View bgcolor;
    ArrayList<String> colorale;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Button btnrstprf, btnset, btnresetall;
    int red, green, blue;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        sharedPreferences = getSharedPreferences("draughts", 0);
        btnrstprf = findViewById(R.id.btnresetpref);
        colorale = new ArrayList<>();
        bgcolor = findViewById(R.id.colorglimpse);
        editor = sharedPreferences.edit();
        btnresetall = findViewById(R.id.btnresetall);
        btnresetall.setOnClickListener(this);
        String temp1 = sharedPreferences.getString("player1", null);
        if (temp1 != null) {
            bgcolor.setBackgroundColor(Color.parseColor(temp1));

        }
        seekBar = findViewById(R.id.seekbarred);
        seekBar1 = findViewById(R.id.seekbarblue);
        seekBar2 = findViewById(R.id.seekbargreen);
        seekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
        seekBar1.setOnSeekBarChangeListener(onSeekBarChangeListener);
        seekBar2.setOnSeekBarChangeListener(onSeekBarChangeListener);
        spinner = findViewById(R.id.choicespinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (spinner.getSelectedItem().toString()) {
                    case "Player 1":
                        seekBar1.setProgress(0);
                        seekBar.setProgress(0);
                        seekBar2.setProgress(0);
                        if (sharedPreferences.getString("player1", null) != null) {
                            bgcolor.setBackgroundColor(Color.parseColor(sharedPreferences.getString("player1", null)));

                        }

                        break;
                    case "Player 2":
                        seekBar1.setProgress(0);
                        seekBar.setProgress(0);
                        seekBar2.setProgress(0);
                        bgcolor.setBackgroundColor(Color.parseColor(sharedPreferences.getString("player2", null)));

                        break;
                    case "Tile 1":
                        seekBar1.setProgress(0);
                        seekBar.setProgress(0);
                        seekBar2.setProgress(0);
                        bgcolor.setBackgroundColor(Color.parseColor(sharedPreferences.getString("tile1", null)));

                        break;
                    case "Tile 2":
                        seekBar1.setProgress(0);
                        seekBar.setProgress(0);
                        seekBar2.setProgress(0);
                        bgcolor.setBackgroundColor(Color.parseColor(sharedPreferences.getString("tile2", null)));

                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        List<String> categories = new ArrayList<String>();
        categories.add("Player 1");
        categories.add("Player 2");
        categories.add("Tile 1");
        categories.add("Tile 2");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        btnset = findViewById(R.id.btnset);
        btnset.setOnClickListener(this);
        btnrstprf.setOnClickListener(this);
    }

    private SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            switch (seekBar.getId()) {
                case R.id.seekbarred:
                    red = progress;
                    break;
                case R.id.seekbarblue:
                    green = progress;
                    break;
                case R.id.seekbargreen:
                    blue = progress;
                    break;
            }
            color = Color.rgb(red, green, blue);
            bgcolor.setBackgroundColor(color);
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnset:
                ColorDrawable buttonColor = (ColorDrawable) bgcolor.getBackground();
                int colorId = buttonColor.getColor();
                String hexColorplayer1 = String.format("#%06X", (0xFFFFFF & colorId));
                switch (spinner.getSelectedItem().toString()) {
                    case "Player 1":
                        editor.putString("player1", hexColorplayer1);
                        editor.commit();
                        Toast.makeText(getApplicationContext(), "Player 1 preferences updated", Toast.LENGTH_LONG).show();

                        break;
                    case "Player 2":
                        editor.putString("player2", hexColorplayer1);
                        editor.commit();
                        Toast.makeText(getApplicationContext(), "Player 2 preferences updated", Toast.LENGTH_LONG).show();

                        break;
                    case "Tile 1":
                        editor.putString("tile1", hexColorplayer1);
                        editor.commit();
                        Toast.makeText(getApplicationContext(), "Tile 1 preferences updated", Toast.LENGTH_LONG).show();

                        break;
                    case "Tile 2":
                        editor.putString("tile2", hexColorplayer1);
                        editor.commit();
                        Toast.makeText(getApplicationContext(), "Tile 2 preferences updated", Toast.LENGTH_LONG).show();

                        break;
                }
                break;
            case R.id.btnresetpref:
                switch (spinner.getSelectedItem().toString()) {
                    case "Player 1":
                        editor.putString("player1", "#00ff00");
                        editor.commit();
                        seekBar1.setProgress(0);
                        seekBar.setProgress(0);
                        seekBar2.setProgress(0);
                        bgcolor.setBackgroundColor(Color.parseColor("#00ff00"));
                        Toast.makeText(getApplicationContext(), "Player 1 preferences restored", Toast.LENGTH_LONG).show();

                        break;
                    case "Player 2":
                        editor.putString("player2", "#0000ff");
                        editor.commit();
                        seekBar1.setProgress(0);
                        seekBar.setProgress(0);
                        seekBar2.setProgress(0);
                        bgcolor.setBackgroundColor(Color.parseColor("#0000ff"));
                        Toast.makeText(getApplicationContext(), "Player 2 preferences restored", Toast.LENGTH_SHORT).show();

                        break;
                    case "Tile 1":
                        editor.putString("tile1", "#ffffff");
                        editor.commit();
                        seekBar1.setProgress(0);
                        seekBar.setProgress(0);
                        seekBar2.setProgress(0);
                        bgcolor.setBackgroundColor(Color.parseColor("#ffffff"));
                        Toast.makeText(getApplicationContext(), "Tile 1 preferences restored", Toast.LENGTH_SHORT).show();

                        break;
                    case "Tile 2":
                        editor.putString("tile2", "#000000");
                        editor.commit();
                        seekBar1.setProgress(0);
                        seekBar.setProgress(0);
                        seekBar2.setProgress(0);
                        bgcolor.setBackgroundColor(Color.parseColor("#000000"));
                        Toast.makeText(getApplicationContext(), "Tile 2 preferences restored", Toast.LENGTH_SHORT).show();

                        break;
                }
                break;

            case R.id.btnresetall:
                seekBar1.setProgress(0);
                seekBar.setProgress(0);
                seekBar2.setProgress(0);
                editor.putString("player1", "#00ff00");
                editor.putString("player2", "#0000ff");
                editor.putString("tile2", "#000000");
                editor.putString("tile1", "#ffffff");
                editor.commit();
                Toast.makeText(getApplicationContext(), "All preferences reseted to default", Toast.LENGTH_SHORT).show();


        }
    }
}
