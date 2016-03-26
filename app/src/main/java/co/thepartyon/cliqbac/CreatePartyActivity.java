package co.thepartyon.cliqbac;

import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DateFormatSymbols;
import java.util.Calendar;

public class CreatePartyActivity extends AppCompatActivity implements View.OnClickListener {

    static final LatLng HAMBURG = new LatLng(53.558, 9.927);
    static final LatLng KIEL = new LatLng(53.551, 9.993);
    boolean showMap;
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_party);

        //Instantiate Buttons + Set onClick Listeners
        View startTime = (EditText) findViewById(R.id.startTimeEditText);
        startTime.setOnClickListener(this);

        View startDate = (EditText) findViewById(R.id.startDateEditText);
        startDate.setOnClickListener(this);

        View confirm = (Button) findViewById(R.id.confirmButton);
        confirm.setOnClickListener(this);

        View location = (EditText) findViewById(R.id.locationEditText);
        location.setOnClickListener(this);

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
                    startTime.setText((Math.abs(selectedHour - 12)) + ":" + selectedMinute + " " + AM_PM);
                }
            }, hour, minute, false);
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();

        }

        else if(v.getId() == R.id.startDateEditText) //Click Date, bring up DatePickerDialog
        {
            final EditText startDate = (EditText) v;
            Calendar mcurrentDate = Calendar.getInstance();
            int day = mcurrentDate.get(Calendar.DAY_OF_MONTH);
            int month = mcurrentDate.get(Calendar.MONTH);
            int year = mcurrentDate.get(Calendar.YEAR);

            DatePickerDialog mDatePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


                    startDate.setText(getMonth(monthOfYear) + " " + dayOfMonth + ", " + year);
                }
            }, year, month, day);
            mDatePicker.setTitle("Select Date");
            mDatePicker.show();

        }

        else if(v.getId() == R.id.locationEditText)
        {
            Intent i = new Intent(this, MapsActivity.class);
            startActivity(i);

            /*

            //Move the camera instantly to hamburg with a zoom of 15.
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(HAMBURG, 15));

            // Zoom in, animating the camera.
            map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

            */





        }

        else if(v.getId() == R.id.confirmButton) //User clicks the confirm button, data is stored in DB
        {
            //Store info in DB and move to next activity
            Snackbar.make(v, "Confirmed.", Snackbar.LENGTH_SHORT).show();
        }



    }

    public String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month-1];
    }

}
