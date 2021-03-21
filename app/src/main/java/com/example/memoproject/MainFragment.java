package com.example.memoproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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


        Button button = rootView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(),MemoWriteActivity.class);
                startActivityForResult(intent,101);
            }
        });


        //아이템 클릭했을때, 아래 추가 버튼눌렀을때 이동하는 것 까지 옮겼음.
        //아이템 클릭해서 수정하는것, 길게 누르면 삭제 대화상자, 추가해서 아이템 추가되는것, 작성한 시간 기록되게하기.

       return rootView;
    }
}