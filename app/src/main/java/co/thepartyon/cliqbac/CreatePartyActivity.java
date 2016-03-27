package co.thepartyon.cliqbac;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.text.DateFormatSymbols;
import java.util.Calendar;

public class CreatePartyActivity extends AppCompatActivity implements View.OnClickListener,View.OnFocusChangeListener  {

    static final LatLng HAMBURG = new LatLng(53.558, 9.927);
    static final LatLng KIEL = new LatLng(53.551, 9.993);
    boolean showMap;
    private GoogleMap map;
    int PLACE_PICKER_REQUEST = 1;
    int PICK_CONTACT = 2016;
    EditText partyTitle;
    EditText startTime;
    EditText startDate;
    EditText location;
    EditText description;
    Button confirm;


    Drawable drawableLeft;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_party);

        //Instantiate Buttons + Set onClick Listeners
        partyTitle = (EditText) findViewById(R.id.partyTitle);
        partyTitle.setOnClickListener(this);

        startTime = (EditText) findViewById(R.id.startTimeEditText);
        startTime.setOnClickListener(this);

        startDate = (EditText) findViewById(R.id.startDateEditText);
        startDate.setOnClickListener(this);

        confirm = (Button) findViewById(R.id.inviteFriendsButton);
        confirm.setOnClickListener(this);

        location = (EditText) findViewById(R.id.locationEditText);
        location.setOnClickListener(this);

        description = (EditText) findViewById(R.id.descriptionEditText);
        description.setOnClickListener(this);



        //Set drawable icon transparency to be gray instead of black
        drawableLeft = getResources().getDrawable(R.drawable.ic_mode_edit);
        drawableLeft.setAlpha(100);


    }

    //On Click
    @Override
    public void onClick(View v) {


        if(v.getId() == R.id.startTimeEditText) //Click Time, bring up TimePickerDialog
        {
            //startTime.setCompoundDrawables(null,null,null,null); //Remove the edit icon

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
            //startDate.setCompoundDrawables(null,null,null,null); //Remove the edit icon
            //final EditText startDate = (EditText) v;
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
            //location.setCompoundDrawables(null,null,null,null); //Remove the edit icon
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

            try {
                startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
            } catch (GooglePlayServicesRepairableException e) {
                e.printStackTrace();
            } catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }

        }

        else if(v.getId() == R.id.inviteFriendsButton) //User clicks the confirm button, data is stored in DB
        {
            //Go to Invite Friends
            Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
            startActivityForResult(i, PICK_CONTACT);

            //Snackbar.make(v, "Confirmed.", Snackbar.LENGTH_SHORT).show();
        }



    }

    //On Focus Change (For Dynamic Drawable Icons on top of Views
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus)
        {
           // partyTitle.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }

        else
        {
           // partyTitle.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, null, null);
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //final EditText location = (EditText) findViewById(R.id.locationEditText);

        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();

                //TODO: Store that place as the party's location in SQLite
                location.setText(place.getName());

            }

        }

        if (requestCode == PICK_CONTACT && resultCode == RESULT_OK) {
            Uri contactUri = data.getData();
            Cursor cursor = getContentResolver().query(contactUri, null, null, null, null);
            cursor.moveToFirst();
            int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

            Toast.makeText(this, cursor.getString(column), Toast.LENGTH_SHORT).show();
        }


    }

    public String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month-1];
    }

}
