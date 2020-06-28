package io.github.final_project.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import io.github.final_project.R;

class MyViewHolder extends RecyclerView.ViewHolder
{
    LinearLayout outline;
    ImageView icon;
    TextView title;
    TextView lastDate;

    MyViewHolder(@NonNull View itemView)
    {
        super(itemView);

        outline = itemView.findViewById(R.id.list_item_outline);
        icon = itemView.findViewById(R.id.list_item_icon);
        title = itemView.findViewById(R.id.list_item_title);
        lastDate = itemView.findViewById(R.id.list_item_date);
    }
}
