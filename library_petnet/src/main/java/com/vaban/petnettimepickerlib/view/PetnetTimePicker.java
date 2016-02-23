package com.vaban.petnettimepickerlib.view;

import android.content.Context;
import android.text.format.Time;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.vaban.petnettimepickerlib.R;
import com.vaban.petnettimepickerlib.model.TimePickerData;
import com.wdullaer.materialdatetimepicker.time.TimePickerView;
import com.wdullaer.materialdatetimepicker.time.Timepoint;

import java.util.Calendar;

/**
 * Created by vaban on 2/21/2016.
 */
public class PetnetTimePicker extends RelativeLayout {

    private static final String LOG_TAG = PetnetTimePicker.class.getSimpleName();
    EditText mEditText;
    TimePickerView mTimePickerView;

    public PetnetTimePicker(Context context) {
        super(context);
        initView(context);
    }

    public PetnetTimePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public PetnetTimePicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    private void initView(Context context) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.timepicker_petnet, this);

        mTimePickerView = (TimePickerView)findViewById (R.id.timePicker);
        mEditText = (EditText) findViewById(R.id.nameEditText);

    }

    public TimePickerData getTimerData(){
        TimePickerData data = new TimePickerData();

        data.setName(mEditText.getText().toString());

        Calendar now = Calendar.getInstance();
        now.set(Calendar.HOUR_OF_DAY, mTimePickerView.getHour());
        now.set(Calendar.MINUTE, mTimePickerView.getMinute());

        data.setTime(now.getTimeInMillis());
        Log.d(LOG_TAG, "getTimerData " + data.getName() + " " + data.getTime());
        return data;
    }
    public void setTimerData(TimePickerData mTimePickerData) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(mTimePickerData.getTime());

        Timepoint timePoint = new Timepoint(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
        /*mTimePickerView.onValueSelected(timePoint);

        mTimePickerView.initialize(null, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND), false);
        */
        mTimePickerView.changeTimeValue(timePoint);

        mEditText.setText(mTimePickerData.getName());
    }

}
