package com.example.notes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class noteAdapter extends RecyclerView.Adapter<noteAdapter.ViewHolder> {
    private Context context;
    private List<Note> noteList;
    public noteAdapter(Context context, List<Note> noteList) {
        this.context = context;
        this.noteList = noteList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.noterow,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Note note = noteList.get(position);
        holder.title.setText(note.getTitle());
        holder.content.setText(note.getContent());
        SimpleDateFormat formatter2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        holder.createdDate.setText(formatter2.format(note.getCreatedDate()));
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHandler db = new DatabaseHandler(context);
                db.delete(Integer.toString(note.getId()));
                noteList.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView title;
        private TextView content;
        private TextView createdDate;
        private ImageButton imageButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.imageButton = itemView.findViewById(R.id.deleteImage);
            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);
            createdDate = itemView.findViewById(R.id.createdDate);
        }

        @Override
        public void onClick(View view) {

            int position = getAdapterPosition();
            Note note = noteList.get(position);
            Intent intent = new Intent(context, editNote.class);
            intent.putExtra("title", note.getTitle());
            intent.putExtra("content", note.getContent());
            intent.putExtra("id", Integer.toString(note.getId()));
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            intent.putExtra("dueDate", dateFormat.format(note.getDueDate()));
            Log.d("tracking adapter: ", dateFormat.format(note.getDueDate()));
            context.startActivity(intent);
        }
    }
}
