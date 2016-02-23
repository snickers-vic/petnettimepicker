package com.vaban.petnettimepickerlib.wrapper;

import android.content.Context;
import android.util.Log;

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
    private PetnetTimePicker mTimePicker;
    private TimePickerData mTimePickerData;
    private ICommunicator mCommunicator;

    private RequestTimeListener mRequestTimeListener;
    private SendTimeListener mSendTimeListener;

    public PetNetTimePickerWrapper(Context context, PetnetTimePicker timePicker, ICommunicator communicator){
        mTimePicker = timePicker;
        mTimePickerData = new TimePickerData();
        mCommunicator = communicator;

        mRequestTimeListener = null;
        mSendTimeListener = null;
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

    public boolean requestDisplayTime(){
        if(mCommunicator != null){
            mCommunicator.requestTime(new RequestTimeListener() {
                @Override
                public void onRequestTimeCompleted(TimePickerData data) {
                    mTimePickerData = data;
                    mTimePicker.setTimerData(mTimePickerData);
                    Log.d(LOG_TAG, "RequestTime! " + data.getName() + " " + data.getTime());

                    mRequestTimeListener.onRequestTimeCompleted(data);
                }

                @Override
                public void onRequestTimeCancelled() {
                    mTimePickerData = new TimePickerData();
                    mRequestTimeListener.onRequestTimeCancelled();
                }
            });

            return true;
        } else {
            return false;
        }
    }

    public void sendDisplayTime(){
        if(mCommunicator != null) {
            mTimePickerData = mTimePicker.getTimerData();
            mCommunicator.sendTime(mTimePickerData, mSendTimeListener);
        }
    }

    public RequestTimeListener getmRequestTimeListener() {
        return mRequestTimeListener;
    }

    public void setRequestTimeListener(RequestTimeListener mRequestTimeListener) {
        this.mRequestTimeListener = mRequestTimeListener;
    }

    public SendTimeListener getmSendTimeListener() {
        return mSendTimeListener;
    }

    public void setSendTimeListener(SendTimeListener mSendTimeListener) {
        this.mSendTimeListener = mSendTimeListener;
    }
}
