package io.github.final_project.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import io.github.final_project.MemoActivity;
import io.github.final_project.R;
import io.github.final_project.Utils;
import io.github.final_project.data.DBHelper;
import io.github.final_project.data.Data;

public class RecyclerAdapter extends RecyclerView.Adapter<MyViewHolder>
{
    private BaseFragment parent;

    public RecyclerAdapter(BaseFragment parent)
    {
        this.parent = parent;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
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
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
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
                int pos = holder.getAdapterPosition();

                new DBHelper(parent.getContext()).deleteMemo(Data.getInstance().items.get(pos).getCreationDate());
                parent.updateList();
                notifyItemRemoved(pos);
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
}
