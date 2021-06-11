package com.openclassrooms.mareu;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.openclassrooms.mareu.repository.MasterDetailRepository;

public class MainActivity extends AppCompatActivity {

    private MasterDetailRepository mRepo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));

        FragmentManager fragmentManager = getSupportFragmentManager();
        boolean landscapeTabletLayout = findViewById(R.id.detail) != null;
        int viewMaster = R.id.master;
        int viewDetail = landscapeTabletLayout ? R.id.detail : viewMaster;

        mRepo = ViewModelFactory.getInstance().getMasterDetailRepositoryInstance();

        if (savedInstanceState == null) mRepo.setCurrentId(-1);

        mRepo.getMasterFragment().observe(this, fragment -> {
            fragmentManager.beginTransaction().replace(viewMaster, fragment, null).commit();
            if (landscapeTabletLayout)
                fragmentManager.beginTransaction().replace(viewDetail, new Fragment(R.layout.fragment_empty), null).commit();
        });

        mRepo.getDetailFragment().observe(this, fragment -> fragmentManager.beginTransaction().replace(viewDetail, fragment, null).commit());
    }

    @Override
    public void onBackPressed() {
        mRepo.setCurrentId(-1);
    }

    // todo: actually not so great design on big tablets in portrait mode
    // todo: play with addToBackstack in transactions.
    //  (when coming back from detailFragment, filters are discarded... meh.)
}

