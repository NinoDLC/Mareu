package com.openclassrooms.mareu.ui;

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
import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.model.MeetingRoom;

import java.text.MessageFormat;
import java.util.HashMap;

public class MeetingsRecyclerViewAdapter extends ListAdapter<Meeting, MeetingsRecyclerViewAdapter.ViewHolder> {

    private final Listener mListener;
    private final HashMap<Integer, MeetingRoom> mMeetingRooms;

    public MeetingsRecyclerViewAdapter(Listener listener, HashMap<Integer, MeetingRoom> meetingRooms) {
        super(new MeetingsDiffCallback());
        mListener = listener;
        mMeetingRooms = meetingRooms;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_meeting, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingsRecyclerViewAdapter.ViewHolder holder, int position) {
        // getItem() is defined in ListAdapter, with recyclerViewAdapter, we'd have mMeetings field and do mMeetings.get()
        Meeting meeting = getItem(position);
        MeetingRoom mr = mMeetingRooms.get(meeting.getMeetingRoomId());

        if (mr == null)
            throw new NullPointerException("null MeetingRoom object");
        holder.bind(meeting, mListener, mr);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mText;
        private final TextView mParticipants;
        private final TextView mRoomName;
        private final ImageButton mDelete;
        private final View mView;
        private final TextView mMore;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mText = view.findViewById(R.id.meeting_text);
            mParticipants = view.findViewById(R.id.meeting_participants);
            mRoomName = view.findViewById(R.id.meeting_room_name);
            mDelete = view.findViewById(R.id.meeting_delete_button);
            mMore = view.findViewById(R.id.meeting_participants_more);
        }

        public void bind(Meeting meeting, Listener listener, MeetingRoom meetingRoom) {
            mText.setText(
                    MessageFormat.format("{0} - {1}",
                            utils.niceTimeFormat(meeting.getStart()),
                            meeting.getSubject()
                    )
            );
            mParticipants.setText(meeting.getOwner());
            mMore.setText(MessageFormat.format("+{0}", meeting.getParticipants().size()));
            mRoomName.setText(meetingRoom.getName());
            mRoomName.setTextColor(ContextCompat.getColor(mView.getContext(), meetingRoom.getImageSrc()));
            mDelete.setOnClickListener(view -> listener.deleteButtonClicked(meeting.getId()));
            mView.setOnClickListener(view -> listener.itemClicked(meeting.getId()));
        }
    }

    public static class MeetingsDiffCallback extends DiffUtil.ItemCallback<Meeting> {
        @Override
        public boolean areItemsTheSame(@NonNull Meeting oldItem, @NonNull Meeting newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Meeting oldItem, @NonNull Meeting newItem) {
            return oldItem.getId() == newItem.getId();
            // todo : if item subject changed, I don't update the screen, here...
        }
    }

    public interface Listener {
        void itemClicked(int id);

        void deleteButtonClicked(int id);
    }


}