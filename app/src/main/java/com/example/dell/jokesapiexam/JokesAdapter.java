package com.example.dell.jokesapiexam;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class JokesAdapter extends RecyclerView.Adapter<JokesAdapter.MyViewHolder> {
    Context context;
    ArrayList<JokeModel> arrayList;

    public JokesAdapter(JokesActivity jokesActivity, ArrayList<JokeModel> arrayList) {
        this.context=jokesActivity;
        this.arrayList=arrayList;
    }

    @NonNull
    @Override
    public JokesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.design,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JokesAdapter.MyViewHolder holder, int position) {
        JokeModel model=arrayList.get(position);
        holder.textView.setText(model.getJokes());
    }

    @Override
    public int getItemCount() {
        return (arrayList==null)?0:arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.txt_jokes);
        }
    }
}
