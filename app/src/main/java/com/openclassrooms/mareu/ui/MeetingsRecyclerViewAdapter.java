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

import com.google.android.material.snackbar.Snackbar;
import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.di.DependencyInjection;
import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.model.MeetingRoom;

import java.text.MessageFormat;
import java.time.format.DateTimeFormatter;

public class MeetingsRecyclerViewAdapter extends ListAdapter<Meeting, MeetingsRecyclerViewAdapter.ViewHolder> {

    public MeetingsRecyclerViewAdapter() {
        super(new MeetingsDiffCallback());
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
         */
        holder.bind(getItem(position));
        // todo : pourquoi peut-il écrire getItem(), et pas mMeetings.get()
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mText;
        private final TextView mParticipants;
        private final ImageView mImage;
        private final ImageButton mDelete;

        public ViewHolder(View view) {
            super(view);
            mText = view.findViewById(R.id.meeting_text);
            mParticipants = view.findViewById(R.id.meeting_participants);
            mImage = view.findViewById(R.id.meeting_image);
            mDelete = view.findViewById(R.id.meeting_delete_button);
        }

        public void bind(Meeting meeting){
            // TODO : this is ugly, but I hate eventbus even more
            MeetingRoom mr = DependencyInjection.getMeetingsRepository().getMeetingRoomById(meeting.getMeetingRoomId());
            String sep = " - ";
            if (mr != null) {
                mText.setText(
                        MessageFormat.format("{0}{1}{2}{3}{4}",
                                meeting.getStart().format(DateTimeFormatter.ofPattern("kk'h'mm")),
                                sep,
                                meeting.getSubject(),
                                sep,
                                mr.getName()
                        )
                );
                mParticipants.setText(meeting.getOwner());
                mImage.setImageResource(mr.getImageSrc());
                mDelete.setOnClickListener(
                        view -> Snackbar.make(
                                    view,
                                    "let's remove meeting " + meeting.getId(),
                                    Snackbar.LENGTH_SHORT).show()
                );
            }
        }
    }

    public static class MeetingsDiffCallback extends DiffUtil.ItemCallback<Meeting> {

        @Override
        public boolean areItemsTheSame(@NonNull Meeting oldItem, @NonNull Meeting newItem) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Meeting oldItem, @NonNull Meeting newItem) {
            return false;
        }
    }
}