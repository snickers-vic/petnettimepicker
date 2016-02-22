package com.vaban.petnettimepickerlib.wrapper;

import android.util.Log;
import android.widget.Toast;

import com.vaban.petnettimepickerlib.communicator.ICommunicator;
import com.vaban.petnettimepickerlib.listener.RequestTimeListener;
import com.vaban.petnettimepickerlib.listener.SendTimeListener;
import com.vaban.petnettimepickerlib.model.TimePickerData;
import com.vaban.petnettimepickerlib.view.PetnetTimePicker;

/**
 * Created by vaban on 2/21/2016.
 */
public class PetNetTimePickerWrapper {

    private static final String LOG_TAG = PetNetTimePickerWrapper.class.getSimpleName();
    PetnetTimePicker mTimePicker;
    TimePickerData mTimePickerData;
    ICommunicator mCommunicator;

    public PetNetTimePickerWrapper(PetnetTimePicker timePicker, ICommunicator communicator){
        mTimePicker = timePicker;
        mTimePickerData = new TimePickerData();
        mCommunicator = communicator;


    }

    public PetnetTimePicker getTimePicker() {
        return mTimePicker;
    }

    public void setTimePicker(PetnetTimePicker mTimePicker) {
        this.mTimePicker = mTimePicker;
    }

    public TimePickerData getTimePickerData() {
        return mTimePickerData;
    }

    public void setTimePickerData(TimePickerData mTimePickerData) {
        this.mTimePickerData = mTimePickerData;
    }

    public ICommunicator getCommunicator() {
        return mCommunicator;
    }

    public void setCommunicator(ICommunicator mCommunicator) {
        this.mCommunicator = mCommunicator;
    }

    public boolean updateDisplayTime(){
        if(mCommunicator != null){
            mCommunicator.requestTime(new RequestTimeListener() {
                @Override
                public void onCompleted(TimePickerData data) {
                    mTimePickerData = data;
                    mTimePicker.setTimerData(mTimePickerData);
                    Log.d(LOG_TAG, "RequestTime! " + data.getName());
                }

                @Override
                public void onCancelled() {
                    mTimePickerData = new TimePickerData();
                }
            });

            return true;
        } else {
            return false;
        }
    }

    public void save(SendTimeListener sendTimeListener){
        if(mCommunicator != null) {
            mTimePickerData = mTimePicker.getTimerData();
            mCommunicator.sendTime(mTimePickerData, sendTimeListener);
        }
    }
}
