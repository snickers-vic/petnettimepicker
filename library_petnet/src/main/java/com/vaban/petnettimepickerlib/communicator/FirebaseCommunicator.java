package com.vaban.petnettimepickerlib.communicator;

import android.content.Context;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.vaban.petnettimepickerlib.listener.RequestTimeListener;
import com.vaban.petnettimepickerlib.listener.SendTimeListener;
import com.vaban.petnettimepickerlib.model.TimePickerData;

/**
 * Created by vaban on 2/21/2016.
 */
public class FirebaseCommunicator implements ICommunicator {

    private static final String LOG_TAG = FirebaseCommunicator.class.getSimpleName();
    Context mContext;

    Firebase mFirebaseRef;
    Firebase mTimePickerRef;
    Firebase.AuthResultHandler mHandler;

    String mUrl;
    String mToken;

    public FirebaseCommunicator(Context context, String url, String token, Firebase.AuthResultHandler handler){
        mContext = context;
        Firebase.setAndroidContext(mContext);
        mUrl = url;
        mToken = token;
        mFirebaseRef = new Firebase(url);
        mHandler = handler;

        requestHandShake();

        mTimePickerRef = mFirebaseRef.child("time_picker");
    }

    @Override
    public void sendTime(TimePickerData data, final SendTimeListener listener) {
        Log.d(LOG_TAG,"sendTime");
        mTimePickerRef.setValue(data, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                listener.onSendTimeCompleted();
            }
        });
    }

    @Override
    public void requestTime( final RequestTimeListener listener) {
        Log.d(LOG_TAG,"requestTime");

        mTimePickerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TimePickerData data = dataSnapshot.getValue(TimePickerData.class);
                listener.onRequestTimeCompleted(data);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d(LOG_TAG, firebaseError.getCode() + " " + firebaseError.getMessage() + firebaseError.getDetails());
                listener.onRequestTimeCancelled();
            }
        });

    }

    @Override
    public void requestHandShake() {
        mFirebaseRef.authWithCustomToken(mToken, mHandler);
    }
}
