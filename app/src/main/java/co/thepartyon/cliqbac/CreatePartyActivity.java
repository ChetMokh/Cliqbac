package co.thepartyon.cliqbac;

import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

public class CreatePartyActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_party);


        //Instantiate Buttons + Set onClick Listeners
        final View startTime = (EditText) findViewById(R.id.startTimeEditText);
        startTime.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.startTimeEditText) //Click Time, bring up TimePickerDialog
        {
            final EditText startTime = (EditText) v;
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    String AM_PM ;
                    if(selectedHour < 12) {
                        AM_PM = "AM";
                    } else {
                        AM_PM = "PM";
                    }
                    startTime.setText( "" + (Math.abs(selectedHour - 12)) + ":" + selectedMinute + " " + AM_PM);
                }
            }, hour, minute, false);
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();

        }

        //else if(v.getId() == R.id.date)

    }
}
