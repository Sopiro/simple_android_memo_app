package io.github.final_project.main;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import io.github.final_project.DBHelper;
import io.github.final_project.R;
import io.github.final_project.Utils;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>
{
    private FragMain parent;

    public RecyclerAdapter(FragMain parent)
    {
        this.parent = parent;
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
        holder.icon.setImageResource(R.drawable.ic_fiber_manual_record_24px);
        holder.title.setText(Data.getInstance().items.get(position).getTitle());
        holder.lastDate.setText(Data.getInstance().items.get(position).getLastDate());

        // 제목을 기준으로 해쉬컬러를 생성하여 아이콘과 배경을 정함
        holder.icon.setColorFilter(Utils.getHashColor(holder.title.getText().toString()));
        holder.outline.getBackground().setTint(Utils.getHashColor(holder.title.getText().toString()));
        holder.outline.getBackground().setTintMode(PorterDuff.Mode.SRC_IN);
        holder.outline.getBackground().setAlpha(0x55);

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(v ->
        {
            String name = holder.title.getText().toString();

            Intent intent = new Intent(parent.getContext(), MemoActivity.class);
            intent.putExtra("position", holder.getAdapterPosition());

            parent.startActivityForResult(intent, 1001);
        });

        holder.itemView.setOnLongClickListener(v ->
        {
            AlertDialog.Builder dlg = new AlertDialog.Builder(parent.getContext());
            dlg.setMessage(R.string.dlg_content);
            dlg.setPositiveButton(R.string.yes, (dialog, which) ->
            {
                int position1 = holder.getAdapterPosition();

                new DBHelper(parent.getContext()).deleteMemo(Data.getInstance().items.get(position1).getCreationDate());
                parent.updateList();
                notifyItemRemoved(position1);
            });

            dlg.setNegativeButton(R.string.no, null);
            dlg.show();

            return true;
        });
    }

    @Override
    public int getItemCount()
    {
        return Data.getInstance().items == null ? 0 : Data.getInstance().items.size();
    }

    public void removeItem(int pos)
    {
        try
        {
            Data.getInstance().items.remove(pos);
            notifyItemRemoved(pos);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        private LinearLayout outline;
        private ImageView icon;
        private TextView title;
        private TextView lastDate;

        MyViewHolder(@NonNull View itemView)
        {
            super(itemView);

            this.outline = itemView.findViewById(R.id.list_item_outline);
            this.icon = itemView.findViewById(R.id.list_item_icon);
            this.title = itemView.findViewById(R.id.list_item_title);
            this.lastDate = itemView.findViewById(R.id.list_item_date);
        }
    }
}
