package com.example.memoproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MemoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    private ArrayList<MemoItem> items = new ArrayList<MemoItem>();

    OnItemClickListener clickListener;
    OnItemLongClickListener longClickListener;



    Context context;

    public MemoAdapter(Context context) {
        this.context = context;
    }

    public static interface OnItemClickListener{
        public void onItemClick(ViewHolder holder,View view, int position);
    }

    public static interface OnItemLongClickListener{
        public void onItemLongClick(ViewHolder holder,View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.clickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.longClickListener = listener;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.memo_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MemoItem item = items.get(position);
        ((ViewHolder)holder).setItem(item);
        ((ViewHolder)holder).setOnItemClickListener(clickListener);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public MemoItem getItem(int position){
        return items.get(position);
    }


    //리사이클러뷰 아이템 추가
    void addItem(MemoItem item){
        items.add(item);
    }

    // 리사이클러뷰 아이템 삭제
    void removeItem(int position){
        items.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    // 리사이클러뷰 아이템 수정
    void modifyItem(int position, MemoItem item){
        int count;
        count = getItemCount();
        if(count > 0){
            if(position > -1 && position < count){
                items.set(position,item);
                notifyDataSetChanged();
            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        TextView textView2;
        OnItemClickListener listener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            textView2 = itemView.findViewById(R.id.textView2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null){
                        listener.onItemClick(ViewHolder.this,v,position);
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        longClickListener.onItemLongClick(ViewHolder.this,v,position);
                    }
                    return true;
                }
            });

        }

        public void setOnItemClickListener(OnItemClickListener listener){
            this.listener = listener;
        }

        public void setItem(MemoItem item){
            textView.setText(item.getContent());
            textView2.setText(item.getTime());
        }


    }
}
