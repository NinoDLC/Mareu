package com.openclassrooms.mareu;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.openclassrooms.mareu.repository.CurrentIdRepository;
import com.openclassrooms.mareu.testUtils.LiveDataTestUtils;
import com.openclassrooms.mareu.ui.main.MainActivityViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class MainActivityViewModelUnitTests {

    private MainActivityViewModel viewModel;

    private MutableLiveData<Integer> myMutableLiveData;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    CurrentIdRepository currentIdRepository;

    @Before
    public void setUp() {
        given(currentIdRepository.getCurrentIdLiveData()).willReturn(myMutableLiveData);
        viewModel = new MainActivityViewModel(currentIdRepository);
    }

    @Test
    public void nominalCase() throws InterruptedException {
        /*
        todo this is about accounting for verifyNoMoreInterractions()...
        BDDMockito.ignoreStubs(currentIdRepository.getCurrentIdLiveData());

        todo unrelated and frowned upon
        BDDMockito.clearInvocations(currentIdRepository);
        // BDDMockito.mockConstruction()

        todo relevant, but code smell
        BDDMockito.reset(currentIdRepository);
        BDDMockito.BDDMyOngoingStubbing.class.getClasses()[0].
         */

        // when
        myMutableLiveData.setValue(1);

        // then
        assertTrue(LiveDataTestUtils.getOrAwaitValue(viewModel.eventIsShowMeeting()));
    }

    @Test
    public void nominalFalseCase() throws InterruptedException {
        // when
        myMutableLiveData.setValue(0);

        // then
        assertFalse(LiveDataTestUtils.getOrAwaitValue(viewModel.eventIsShowMeeting()));
    }
}
