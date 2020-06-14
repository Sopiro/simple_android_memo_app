package io.github.final_project;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder
{
    public LinearLayout outline;
    public ImageView icon;
    public TextView title;
    public TextView lastDate;

    public MyViewHolder(@NonNull View itemView)
    {
        super(itemView);

        this.outline = itemView.findViewById(R.id.list_item_outline);
        this.icon = itemView.findViewById(R.id.list_item_icon);
        this.title = itemView.findViewById(R.id.list_item_title);
        this.lastDate = itemView.findViewById(R.id.list_item_date);
    }
}
