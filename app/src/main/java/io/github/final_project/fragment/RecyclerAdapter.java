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
    // 이 리사이클러뷰를 소유하고있는 프래그먼트.
    private BaseFragment parent;

    private static final int LIST_ALPHA = 0x55;
    // 애니메이션을 위한 상수
    private static final float ANIM_SCALE = 0.9f;
    private static final float ANIM_TRANSLATION = -50f;
    private static final int ANIM_DURATION = 100;

    RecyclerAdapter(BaseFragment parent)
    {
        this.parent = parent;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        return new MyViewHolder(view);
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
        holder.title.setText(Data.getData().get(position).getTitle());
        holder.lastDate.setText(Utils.getDateString(Data.getData().get(position).getCreationDate()));

        // 제목을 기준으로 해쉬컬러를 생성하여 아이콘과 배경을 정함
        holder.icon.setColorFilter(Utils.getHashColor(holder.title.getText().toString()));
        holder.outline.getBackground().setTint(Utils.getHashColor(holder.title.getText().toString()));
        holder.outline.getBackground().setTintMode(PorterDuff.Mode.SRC_IN);
        holder.outline.getBackground().setAlpha(LIST_ALPHA);

        View view = holder.itemView;

        view.setTag(position);
        view.setOnClickListener(v ->
        {
            Intent intent = new Intent(parent.getContext(), MemoActivity.class);
            intent.putExtra("position", holder.getAdapterPosition());

            parent.startActivity(intent);
        });

        view.setOnLongClickListener(v ->
        {
            view.animate().scaleX(ANIM_SCALE).scaleY(ANIM_SCALE).translationX(ANIM_TRANSLATION).setDuration(ANIM_DURATION);

            AlertDialog.Builder dlg = new AlertDialog.Builder(parent.getContext());
            dlg.setMessage(R.string.dlg_content);

            dlg.setPositiveButton(R.string.yes, (dialog, which) ->
            {
                int pos = holder.getAdapterPosition();

                new DBHelper(parent.getContext()).deleteMemo(Data.getData().get(pos).getCreationDate());
                Data.getData().remove(pos);
                notifyItemRemoved(pos);

                if (Data.getData().size() == 0) parent.updateList();
                Utils.toast(parent.getContext(), R.string.deleted);
            });

            dlg.setNegativeButton(R.string.no, null);
            dlg.setOnDismissListener(dialog -> view.animate().scaleX(1.0f).scaleY(1.0f).translationX(0).setDuration(ANIM_DURATION));

            dlg.show();

            return true;
        });
    }

    @Override
    public int getItemCount()
    {
        return Data.getData() == null ? 0 : Data.getData().size();
    }
}
