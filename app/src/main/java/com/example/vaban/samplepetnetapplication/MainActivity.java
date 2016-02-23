package com.example.vaban.samplepetnetapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.vaban.samplepetnetapplication.util.Constants;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.vaban.petnettimepickerlib.communicator.FirebaseCommunicator;
import com.vaban.petnettimepickerlib.communicator.ICommunicator;
import com.vaban.petnettimepickerlib.listener.RequestTimeListener;
import com.vaban.petnettimepickerlib.listener.SendTimeListener;
import com.vaban.petnettimepickerlib.model.TimePickerData;
import com.vaban.petnettimepickerlib.view.PetnetTimePicker;
import com.vaban.petnettimepickerlib.wrapper.PetNetTimePickerWrapper;

import static com.example.vaban.samplepetnetapplication.R.*;

public class MainActivity extends Activity implements View.OnClickListener, Firebase.AuthResultHandler, RequestTimeListener, SendTimeListener {



    PetnetTimePicker mPetnetTimePicker;
    PetNetTimePickerWrapper mPetNetTimePickerWrapper;

    ICommunicator mCommunicator;

    Button mSaveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);

        mPetnetTimePicker = (PetnetTimePicker) findViewById(id.petnet_timepicker);
        mSaveButton = (Button) findViewById(id.saveBtn);

        // create CommunicatorFactory in the future
        FirebaseCommunicator firebaseCommunicator = new FirebaseCommunicator(this, Constants.URL, Constants.TOKEN, this);

        mCommunicator = firebaseCommunicator;

        mSaveButton.setOnClickListener(this);

        mPetNetTimePickerWrapper = new PetNetTimePickerWrapper(this, mPetnetTimePicker,mCommunicator);

        mPetNetTimePickerWrapper.setRequestTimeListener(this);
        mPetNetTimePickerWrapper.setSendTimeListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(MainActivity.this, "Requesting Authentication", Toast.LENGTH_SHORT).show();
        mSaveButton.setEnabled(false);
        mCommunicator.requestHandShake();
    }

    @Override
    public void onClick(View v) {

        mPetNetTimePickerWrapper.sendDisplayTime();
        Toast.makeText(MainActivity.this, "Sending Time", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAuthenticated(AuthData authData) {
        mPetNetTimePickerWrapper.requestDisplayTime();
        mSaveButton.setEnabled(true);
        Toast.makeText(MainActivity.this, "Authenticated!", Toast.LENGTH_SHORT).show();
//        authData.
    }

    @Override
    public void onAuthenticationError(FirebaseError firebaseError) {
        mSaveButton.setEnabled(false);
    }

    @Override
    public void onSendTimeCompleted() {
//        Toast.makeText(MainActivity.this, "Send Time Completed!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestTimeCompleted(TimePickerData data) {
        Toast.makeText(MainActivity.this, "Time Updated!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestTimeCancelled() {

    }
}
