package com.example.memoproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MemoViewActivity extends AppCompatActivity {
    EditText editText;

    long now = System.currentTimeMillis();
    Date date = new Date(now);
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    String strNow = simpleDateFormat.format(date);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_view);

        editText = findViewById(R.id.editText);
        Button button = findViewById(R.id.button);


        // 102번 코드로 MemoItem객체를 넘겨받음. 넘겨 받은 MemoItem에서 content를 editText에 뿌려줌
        MemoItem item = (MemoItem) getIntent().getSerializableExtra("contentView");
        editText.setText(item.getContent());

        // 수정버튼을 누르면 수정된 내용이 다시 MainActivity로 이동됨. 내용 수정과, 수정한 시간정보 이동.
        button.setOnClickListener(new View.OnClickListener() {
            Intent intent = getIntent();
            @Override
            public void onClick(View v) {
                intent.putExtra("contentRewrite",editText.getText().toString());
                intent.putExtra("timeRewrite",strNow);
                int itemPosition = intent.getIntExtra("position",0);

                setResult(202,intent);
                finish();
            }
        });
    }
}