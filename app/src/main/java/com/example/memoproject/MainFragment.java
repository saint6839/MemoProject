package com.example.memoproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.security.acl.Owner;
import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {
    MainActivity activity;
    RecyclerView recyclerView;
    MemoAdapter adapter = new MemoAdapter(getContext());
    AppDatabase db;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();


    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        //데이터 변화 감지 후에 실행 메모 추가 즉시 추가되지 않고 한번 종료후에 실행했을때 추가 되는 문제, 삭제, 수정까지 반영합시다.
        db.memoDao().getAll().observe(this, new Observer<List<MemoItem>>() {
            @Override
            public void onChanged(List<MemoItem> memoItems) {     // ****insert에서 넘겨준 값이 onChanged 파라미터로 그대로 넘어온다.
                Log.d("데이터확인", memoItems.toString());

                adapter.setItems(memoItems);      // memoItems는 변화된 값들을 전부 그대로 가지고 있음, 이 변화된 값을 그대로 어댑터 객체에 넘겨서 바꿔주면 삭제, 수정, 업데이트가 그대로 반영이 됨.
                                                  // insert,delete,update 등 들어온 값을 굳이 반복문을 통해서 일일히 불러줄 필요가 없다.
                                                  // 변화된 값을 가지고 있는 객체를 그대로 넘겨주는 메서드를 어댑터에서 작성해서 넘겨주면 된다.
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_main,container,false);

        db = Room.databaseBuilder(getContext(),AppDatabase.class,"memo-db")
                .allowMainThreadQueries()
                .build();




        recyclerView = rootView.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        RecyclerDecoration spaceDecoration = new RecyclerDecoration(20);
        recyclerView.addItemDecoration(spaceDecoration);

        adapter.addItem(new MemoItem("test default memo","time value"));

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        //리사이클러뷰 아이템 터치 했을때 메모 수정 액티비티로 이동
        adapter.setOnItemClickListener(new MemoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MemoAdapter.ViewHolder holder, View view, int position) {
                MemoItem item = adapter.getItem(position);
                Toast.makeText(getContext(),"아이템 선택: " + position,Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(requireActivity(),MemoViewActivity.class);
                if(item != null){
                    intent.putExtra("position",position);  // 인텐트로 포지션 값을 담아서 보내준다.
                    Toast.makeText(requireActivity(),"포지션 : " + position,Toast.LENGTH_SHORT).show();
                    intent.putExtra("contentView", item);
                }
                startActivityForResult(intent,102);
            }
        });

        //리사이클러뷰 아이템 길게 터치했을때 삭제여부 물어보는 대화상자 띄움
        adapter.setOnItemLongClickListener(new MemoAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(MemoAdapter.ViewHolder holder, View view, int position) {
                MemoItem item = adapter.getItem(position);
                Toast.makeText(getActivity(),"길게 선택 성공: " + position,Toast.LENGTH_SHORT).show();

                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("삭제");
                dialog.setMessage("삭제하시겠습니까?");
                dialog.setIcon(R.drawable.trashcan);
                dialog.setPositiveButton("삭제", new DialogInterface.OnClickListener() {

                    //대화 상자에서 삭제버튼 누르면 실제로 리사이클러뷰 아이템이 삭제되게 만들기.
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "삭제", Toast.LENGTH_SHORT).show();

                        // 메모 데이터베이스에서 삭제
                        db.memoDao().delete(adapter.getItems().get(position)); // update와 마찬가지로 adapter의 getItems()와 get() 메소드로 position에 해당하는 List<MemoItem> 객체를 반환받는다.
                        Log.d("삭제", item.toString());
                       // adapter.removeItem(position);
                    }
                });
                dialog.show();
            }
        });
        

        //하단의 메모 추가(+) 버튼
        FloatingActionButton button = rootView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(),MemoWriteActivity.class);
                startActivityForResult(intent,101);
            }
        });

       return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        AppDatabase db = Room.databaseBuilder(getContext(),AppDatabase.class,"memo-db")
                .allowMainThreadQueries()
                .build();

        // 메모 추가 버튼을 눌렀을때(메모를 신규로 작성하였을때) 데이터를 받아옴
        if(requestCode == 101){
            if(resultCode == 201) {
                String content = data.getStringExtra("content");
                String time = data.getStringExtra("time");

                //메모 데이터베이스에 추가
                db.memoDao().insert(new MemoItem(content,time));

            }
        }
        // 아이템을 클릭하여 기존 메모를 수정하고 데이터를 업데이트 함
            if(resultCode == 202){

                String contentRewrite = data.getStringExtra("contentRewrite");
                String timeRewrite = data.getStringExtra("timeRewrite");
                int position = data.getIntExtra("position",0);
                Toast.makeText(getActivity(),"메모가 수정되었습니다.",Toast.LENGTH_SHORT).show();

                adapter.getItem(position).setContent(contentRewrite);
                adapter.getItem(position).setTime(timeRewrite);

                // 메모 데이터베이스에서 업데이트
                db.memoDao().update(adapter.getItems().get(position)); // getItems().get(position)으로 수정하는 위치에 해당하는 List<MemoItem> 객체를 반환받아옴

            }
        }
    }
