package com.openclassrooms.mareu;

import com.openclassrooms.mareu.di.DependencyInjection;
import com.openclassrooms.mareu.repository.MeetingsRepository;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class RepositoryUnitTests {

    private final MeetingsRepository repo = DependencyInjection.getNewMeetingsRepository();


    @Test
    public void printRepoContent() {
        System.out.println(repo.getMeetings());
        assertEquals(4, 2 + 2);
    }


}
