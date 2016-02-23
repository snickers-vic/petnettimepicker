package com.vaban.petnettimepickerlib.communicator;

import com.vaban.petnettimepickerlib.listener.RequestTimeListener;
import com.vaban.petnettimepickerlib.listener.SendTimeListener;
import com.vaban.petnettimepickerlib.model.TimePickerData;

/**
 * Created by vaban on 2/21/2016.
 */
public interface ICommunicator {

    public void sendTime(TimePickerData data, SendTimeListener listener);
    public void requestTime( RequestTimeListener listener);

    public void requestHandShake();
}
