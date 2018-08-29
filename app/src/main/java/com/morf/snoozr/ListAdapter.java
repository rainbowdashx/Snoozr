package com.morf.snoozr;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    private List<ContentItem> mDataSet;
    private OnItemClickListener mClickListener;
    private boolean bInverse = false;


    public static class ListViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView mTextView;
        private TextView mNapDurationView;
        private TextView mCycleCountView;
        private ImageView mImage;


        public ListViewHolder(View v) {
            super(v);
            mTextView = v.findViewById(R.id.txtText);
            mNapDurationView = v.findViewById(R.id.txtDuration);
            mCycleCountView = v.findViewById(R.id.txtCycleCount);
            mImage = v.findViewById(R.id.imgAlarmClockAdd);

        }

        public void Bind(final ContentItem Item, final OnItemClickListener ClickListener, boolean Inverse) {
            mTextView.setText(Item.Text);
            mNapDurationView.setText(Item.Duration);
            mCycleCountView.setText(Item.Cycles);

            if (Inverse){
                mImage.setVisibility(View.GONE);
            }

            if (ClickListener != null) {

                itemView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        ClickListener.onItemClick(Item);
                    }
                });

            }

        }
    }

    public ListAdapter(List<ContentItem> myDataset, OnItemClickListener ClickListener) {
        mDataSet = myDataset;
        mClickListener = ClickListener;
    }

    public ListAdapter(List<ContentItem> myDataset, OnItemClickListener ClickListener, boolean Inverse) {
        mDataSet = myDataset;
        mClickListener = ClickListener;
        bInverse = Inverse;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_view_item, parent, false);

        ListViewHolder vh = new ListViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        holder.Bind(mDataSet.get(position), mClickListener, bInverse);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }


}
