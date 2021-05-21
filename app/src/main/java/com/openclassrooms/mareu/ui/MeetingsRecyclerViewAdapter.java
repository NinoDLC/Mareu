package com.openclassrooms.mareu.ui;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.model.Meeting;

import java.util.List;

public class MeetingsRecyclerViewAdapter extends RecyclerView.Adapter<MeetingsRecyclerViewAdapter.ViewHolder> {

    private final List<Meeting> mMeetings;

    public MeetingsRecyclerViewAdapter(List<Meeting> items) {
        mMeetings = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_meeting, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Meeting meeting = mMeetings.get(position);
        holder.mText.setText(meeting.getSubject());
        holder.mParticipants.setText(meeting.getOwner());
        //holder.mImage

    }

    @Override
    public int getItemCount() {
        return mMeetings.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mText;
        public final TextView mParticipants;
        public final ImageView mImage;
        public final ImageButton mDelete;

        public ViewHolder(View view) {
            super(view);
            mText = view.findViewById(R.id.meeting_text);
            mParticipants = view.findViewById(R.id.meeting_participants);
            mImage = view.findViewById(R.id.meeting_image);
            mDelete = view.findViewById(R.id.meeting_delete_button);
        }

    }
}