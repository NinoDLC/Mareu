package com.openclassrooms.mareu.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.mareu.R;

public class MainFragmentAdapter extends ListAdapter<MainFragmentViewState, MainFragmentAdapter.ViewHolder> {

    private final Listener mListener;

    public MainFragmentAdapter(@NonNull Listener listener) {
        super(new MeetingsDiffCallback());
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_meeting, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainFragmentAdapter.ViewHolder holder, int position) {
        // getItem() is defined in ListAdapter, with recyclerViewAdapter, we'd have mMeetings field and do mMeetings.get()
        MainFragmentViewState item = getItem(position);
        holder.bind(item, mListener);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mText;
        private final TextView mParticipants;
        private final TextView mRoomName;
        private final ImageButton mDelete;
        private final View mView;
        private final TextView mMore;

        public ViewHolder(@NonNull View view) {
            super(view);
            mView = view;
            mText = view.findViewById(R.id.meeting_text);
            mParticipants = view.findViewById(R.id.meeting_participants);
            mRoomName = view.findViewById(R.id.meeting_room_name);
            mDelete = view.findViewById(R.id.meeting_delete_button);
            mMore = view.findViewById(R.id.meeting_participants_more);
        }

        public void bind(@NonNull MainFragmentViewState item, @NonNull Listener listener) {
            mText.setText(item.getUpLine());
            mParticipants.setText(item.getOwner());
            mMore.setText(item.getParticipantsNumber());
            mRoomName.setText(item.getMeetingRoomName());
            mRoomName.setTextColor(ContextCompat.getColor(mView.getContext(), item.getMeetingRoomColor()));
            mDelete.setOnClickListener(view -> listener.deleteButtonClicked(item.getId()));
            mView.setOnClickListener(view -> listener.itemClicked(item.getId()));
        }
    }

    public static class MeetingsDiffCallback extends DiffUtil.ItemCallback<MainFragmentViewState> {
        @Override
        public boolean areItemsTheSame(@NonNull MainFragmentViewState oldItem, @NonNull MainFragmentViewState newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull MainFragmentViewState oldItem, @NonNull MainFragmentViewState newItem) {
            return oldItem.equals(newItem);
        }
    }

    public interface Listener {
        void itemClicked(int id);

        void deleteButtonClicked(int id);
    }


}