package com.example.tangrampuzzle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class LevelSelect extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select);

        Spinner level_spinner = findViewById(R.id.level_spinner);
        String[] str = getResources().getStringArray(R.array.Level);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1,str);
        level_spinner.setAdapter(adapter);
    }
    public void onAcceptLevel(View view){
        Spinner sounds_spinner = findViewById(R.id.level_spinner);
        int button_id = sounds_spinner.getSelectedItemPosition();
        if(button_id<0){
            return;
        }
        Intent result = new Intent();
        result.putExtra("LEVEL_ID", button_id);
        setResult(2,result);
        finish();
    }
    public void onCancelLevel(View view){
        finish();
    }
}