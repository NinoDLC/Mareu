package com.openclassrooms.mareu.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.model.Meeting;

import java.util.List;

public class MainFragment extends Fragment {

    private final MainActivity mMainActivity;
    private final MainViewModel mViewModel;

    private final MeetingsRecyclerViewAdapter.Listener mListener = new MeetingsRecyclerViewAdapter.Listener() {
        @Override
        public void itemClicked(int id) {
            // todo : replace my mMainActivity with an interface with setDetailedViewContent ?
            mMainActivity.setDetailedViewContent(id);
        }

        @Override
        public void deleteButtonClicked(int id) {
            mViewModel.deleteButtonClicked(id);
        }
    };

    public MainFragment(MainActivity mainActivity, MainViewModel viewModel) {
        mMainActivity = mainActivity;
        mViewModel = viewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        view.findViewById(R.id.fab).setOnClickListener(v -> mMainActivity.setDetailedViewContent(0));

        RecyclerView recyclerView = view.findViewById(R.id.meeting_list);
        // todo: in XML: overkill?
        //  recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MeetingsRecyclerViewAdapter adapter = new MeetingsRecyclerViewAdapter(mListener, mViewModel.getMeetingRooms());
        recyclerView.setAdapter(adapter);

        mViewModel.getMeetingsLiveData().observe(requireActivity(), new Observer<List<Meeting>>() {
            @Override
            public void onChanged(List<Meeting> meetings) {
                Log.e("Livedata", "onChanged(), " + meetings.size());
                adapter.submitList(meetings);
            }
        });
        return view;
    }
}
