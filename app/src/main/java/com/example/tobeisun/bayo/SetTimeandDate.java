package com.example.tobeisun.bayo;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.firebase.geofire.core.GeoHashQuery;

import java.util.Calendar;


public class SetTimeandDate extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private String TAG = getClass().getSimpleName();
    Spinner spinner ;
    Spinner spinnergmt ;
    Spinner spinnerdate;
    Button proceed, setDateTimeBtn;
    TextView displaydate, displaytime;

    //To receive broadcast that ride has been ordered
    private BroadcastReceiver onRideOrdered = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

//            Toast.makeText(context, "Ride Ordered", Toast.LENGTH_SHORT).show();
            finish();
        }
    };


    private Calendar mCalendar;
    private DatePickerDialog mStartDatePickerDialog;
    private TimePickerDialog mStartTimePickerDialog;
    private long mStartDateTimeInMillis = 0l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_time);

        spinner = (Spinner) findViewById(R.id.spinnertime);
        spinnergmt =(Spinner) findViewById(R.id.spinnergmt);
        spinnerdate=(Spinner) findViewById(R.id.spinnerdate);
        proceed=(Button) findViewById(R.id.buttonproceed);
        displaytime= (TextView) findViewById(R.id.textViewshowtime);
        displaydate= (TextView) findViewById(R.id.textViewshowdate);

        setDateTimeBtn = (Button) findViewById(R.id.set_date_time_btn);


        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SetTimeandDate.this, InputdetailsCustomer.class));
            }
        });

        setDateTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpStartDateAndTimePickerDialog();
                mStartDatePickerDialog.show();
            }
        });


        //dropdown for the time

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.time, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        //dropdown for the gmt (am,pm)


        ArrayAdapter<CharSequence> adaptergmt = ArrayAdapter.createFromResource(this, R.array.gmt, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnergmt.setAdapter(adaptergmt);
        spinnergmt.setOnItemSelectedListener(this);



        //dropdown for the date


        ArrayAdapter<CharSequence> adapterdate = ArrayAdapter.createFromResource(this, R.array.date, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerdate.setAdapter(adapterdate);
        spinnerdate.setOnItemSelectedListener(this);

        LocalBroadcastManager.getInstance(this).registerReceiver(onRideOrdered,
                new IntentFilter(Constants.BROADCAST_RIDE_ORDERED));

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String selected = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(),selected, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public void setUpStartDateAndTimePickerDialog(){

        final int cYear, cMonth, cDay;
        Log.d(TAG, "onClick: stardate fab clicked!!!!!");
        final Calendar c = Calendar.getInstance();
        cYear = c.get(Calendar.YEAR);
        cMonth = c.get(Calendar.MONTH);
        cDay = c.get(Calendar.DAY_OF_MONTH);

        mStartDatePickerDialog = new DatePickerDialog(this,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, final int year, final int month, final int dayOfMonth) {


                int cHour, cMinute;
                mCalendar = Calendar.getInstance();
                cHour = mCalendar.get(Calendar.HOUR_OF_DAY);
                cMinute = mCalendar.get(Calendar.MINUTE);

                mStartTimePickerDialog = new TimePickerDialog(SetTimeandDate.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

//                        mStartTimePicker =  view;
                        mStartDateTimeInMillis = UtilsClass.getDateTimeInMilliseconds(year,
                                month, dayOfMonth, hourOfDay, minute);

                        displaydate.setText(UtilsClass.getDateTimeString(mStartDateTimeInMillis));

                    }
                }, cHour, cMinute, false);




                mStartTimePickerDialog.show();
            }
        }, cYear, cMonth, cDay);


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(onRideOrdered);
    }
}
