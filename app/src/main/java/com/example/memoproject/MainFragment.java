package com.example.memoproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainFragment extends Fragment {
    MainActivity activity;
    RecyclerView recyclerView;
    MemoAdapter adapter = new MemoAdapter(getContext());

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_main,container,false);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        RecyclerDecoration spaceDecoration = new RecyclerDecoration(20);
        recyclerView.addItemDecoration(spaceDecoration);

        adapter.addItem(new MemoItem("하이하이","오후00:00"));
        recyclerView.setAdapter(adapter);


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
                        adapter.removeItem(position);
                    }
                });
                dialog.show();
            }
        });


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

        if(requestCode == 101){
            if(resultCode == 201) {
                String content = data.getStringExtra("content");
                String time = data.getStringExtra("time");
                adapter.addItem(new MemoItem(content, time));
                adapter.notifyDataSetChanged();

            }
        }

            if(resultCode == 202){
                String contentRewrite = data.getStringExtra("contentRewrite");
                String timeRewrite = data.getStringExtra("timeRewrite");
                int position = data.getIntExtra("position",0);
                Toast.makeText(getActivity(),"메모가 수정되었습니다.",Toast.LENGTH_SHORT).show();
                adapter.modifyItem(position,new MemoItem(contentRewrite,timeRewrite));
            }
        }
    }
