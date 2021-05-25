package com.openclassrooms.mareu.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.model.MeetingRoom;

import java.text.MessageFormat;
import java.time.format.DateTimeFormatter;
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
        /* todo : remember : this bind method allows for all ViewHolder fields to be private !
            generally, I guess we want private fields and (some) public methods...
            getItem() is defined in ListAdapter,
            with recyclerViewAdapter, we'd have mMeetings field and do mMeetings.get() */
        Meeting meeting = getItem(position);
        MeetingRoom mr = mMeetingRooms.get(meeting.getMeetingRoomId());

        if (mr == null)
            throw new NullPointerException("null MeetingRoom object");
        holder.bind(meeting, mListener, mr);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mText;
        private final TextView mParticipants;
        private final ImageView mImage;
        private final ImageButton mDelete;
        private final View mView;

        public ViewHolder(View view) {
            super(view);
            mText = view.findViewById(R.id.meeting_text);
            mParticipants = view.findViewById(R.id.meeting_participants);
            mImage = view.findViewById(R.id.meeting_image);
            mDelete = view.findViewById(R.id.meeting_delete_button);
            mView = view;
        }

        public void bind(Meeting meeting, Listener listener, MeetingRoom meetingRoom) {

            final String sep = " - ";
            mText.setText(
                    MessageFormat.format("{0}{1}{2}{3}{4}",
                            meeting.getStart().format(DateTimeFormatter.ofPattern("kk'h'mm")),
                            sep,
                            meeting.getSubject(),
                            sep,
                            meetingRoom.getName()
                    )
            );
            mParticipants.setText(meeting.getOwner());
            mImage.setImageResource(meetingRoom.getImageSrc());
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
            // if item subject changed, I don't update the screen, here...
        }
    }

    public interface Listener {
        void itemClicked(int id);

        void deleteButtonClicked(int id);
    }


}