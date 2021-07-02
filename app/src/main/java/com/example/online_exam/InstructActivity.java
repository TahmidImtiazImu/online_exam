package com.example.online_exam;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class InstructActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instruction);
        this.setTitle("Instruction");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));

        textView = findViewById(R.id.instruction_show);

        InputStream input_instruction = this.getResources().openRawResource(R.raw.instruction);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input_instruction));

        StringBuffer stringBuffer = new StringBuffer();

        String instruction_data = "";

        if(input_instruction!=null){
            try {
                while((instruction_data = bufferedReader.readLine())!=null){

                    stringBuffer.append(instruction_data + "\n");
                }
                textView.setText(stringBuffer);

            }catch (Exception e){

                e.printStackTrace();
            }
        }

    }
}