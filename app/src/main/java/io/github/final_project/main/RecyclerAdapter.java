package io.github.final_project.main;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.github.final_project.R;
import io.github.final_project.Utils;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>
{
    private List<ListItem> items;

    public RecyclerAdapter(List<ListItem> items)
    {
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    /*
     * Ctrl + Q
     *
     *  Called by RecyclerView to display the data at the specified position.
     * This method should update the contents of the RecyclerView.ViewHolder.
     * itemView to reflect the item at the given position.
     * */
    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.MyViewHolder holder, int position)
    {
        holder.icon.setImageResource(items.get(position).getIcon());
        holder.title.setText(items.get(position).getTitle());
        holder.date.setText(items.get(position).getDate());

        // 제목을 기준으로 해쉬컬러를 생성하여 아이콘 색을 정함
        holder.icon.setColorFilter(Utils.getHashColor(holder.title.getText().toString()));

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(v ->
        {
            String name = holder.title.getText().toString();

            Toast.makeText(v.getContext(), name + ", " + holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount()
    {
        return items == null ? 0 : items.size();
    }

    public void removeItem(int pos)
    {
        try
        {
            items.remove(pos);
            notifyItemRemoved(pos);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView icon;
        private TextView title;
        private TextView date;

        MyViewHolder(@NonNull View itemView)
        {
            super(itemView);

            this.icon = itemView.findViewById(R.id.list_item_icon);
            this.title = itemView.findViewById(R.id.list_item_title);
            this.date = itemView.findViewById(R.id.list_item_date);
        }
    }
}
