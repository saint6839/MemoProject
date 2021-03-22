package com.example.memoproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MemoWriteActivity extends AppCompatActivity {
    TextView textView;
    EditText editText;

    long now = System.currentTimeMillis();
    Date date = new Date(now);
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    String strNow = simpleDateFormat.format(date);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_write);

        textView = findViewById(R.id.textView);
        editText = findViewById(R.id.editText);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("content",editText.getText().toString());
                intent.putExtra("time",strNow);
                setResult(201,intent);
                Toast.makeText(getApplicationContext(),"메모 추가",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}