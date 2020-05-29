package com.example.exercise;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.EntryViewHolder> {
    private List<Entry> entryList;
    public static class EntryViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public TextView date;
        public TextView description;
        public TextView place;
        public CheckBox isWork;
        public TextView coordinates;
        public EntryViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.titleOfEntry);
            date=itemView.findViewById(R.id.dateOfEntry);
            description=itemView.findViewById(R.id.descriptionOfEntry);
            place=itemView.findViewById(R.id.placeOfEntry);
            isWork=itemView.findViewById(R.id.ifWorkEntry);
            coordinates=itemView.findViewById(R.id.GPSCoordinateOfEntry);
        }
    }

    @NonNull
    @Override
    public EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_example,parent, false);
        EntryViewHolder entryViewHolder=new EntryViewHolder(view);
        return entryViewHolder;
    }
    public EntryAdapter(List<Entry> entryList){
        this.entryList=entryList;
    }
    @Override
    public void onBindViewHolder(@NonNull EntryViewHolder holder, int position) {
        Entry currentEntry= entryList.get(position);
        holder.title.setText(currentEntry.getTitle());
        holder.date.setText(currentEntry.getDate());
        holder.description.setText(currentEntry.getText());
        holder.place.setText(currentEntry.getPlace());
        holder.isWork.setChecked(currentEntry.getIsWork());
        holder.coordinates.setText(currentEntry.getCoordinates());
    }

    @Override
    public int getItemCount() {
        return entryList.size();
    }
}
