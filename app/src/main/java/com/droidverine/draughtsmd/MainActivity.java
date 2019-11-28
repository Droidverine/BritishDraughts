package com.droidverine.draughtsmd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.audiofx.Equalizer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    int player1=0;
    int player2=0;
    TextView player1txt,player2txt;


    Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn=findViewById(R.id.resetbtn);

        player1txt=findViewById(R.id.player1score);
        player2txt=findViewById(R.id.player2score);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //To Reset whole game
                finish();
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });




        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float height = displayMetrics.heightPixels;
        float width = displayMetrics.widthPixels;
        if(height>width)
        {
            Log.d("ARERE","Phone");

        }
        else {
            Log.d("ARERE","Tablet");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuoptions,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menureset:
                Toast.makeText(getApplicationContext(),"Reset",Toast.LENGTH_LONG).show();
                finish();
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.menusettings:
                Toast.makeText(getApplicationContext(),"Settings",Toast.LENGTH_LONG).show();
                Intent intentm = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intentm);
                break;
            case R.id.menuexit:
                Toast.makeText(getApplicationContext(),"Exit",Toast.LENGTH_LONG).show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
