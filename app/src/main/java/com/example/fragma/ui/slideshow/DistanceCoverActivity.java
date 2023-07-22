package com.example.fragma.ui.slideshow;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.fragma.R;

public class DistanceCoverActivity extends AppCompatActivity implements LocationListener, SensorEventListener {

    private TextView txtDistance;
    private TextView txtSteps;
    private TextView txtAvgSpeed;
    private Button btnStartWalk;
    private Button btnStartRun;
    private Button btnStart;
    private Button btnStop;
    private Button btnRequestLocationPermission;

    private LocationManager locationManager;
    private SensorManager sensorManager;
    private Location lastLocation;
    private long startTime;
    private float distanceCovered;
    private float totalSpeed;
    private int speedCount;
    private int stepsCount;
    private boolean isTracking;

    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final int SENSOR_DELAY_MICROS = 50;
    private static final float STEP_THRESHOLD = 10.0f; // Adjust this threshold based on device sensitivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance_cover);

        txtDistance = findViewById(R.id.txtDistance);
        txtSteps = findViewById(R.id.txtSteps);
        txtAvgSpeed = findViewById(R.id.txtAvgSpeed);
        btnStartWalk = findViewById(R.id.btnStartWalk);
        btnStartRun = findViewById(R.id.btnStartRun);
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);
        btnRequestLocationPermission = findViewById(R.id.btnRequestLocationPermission);

        btnStartWalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTracking();
            }
        });

        btnStartRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTracking();
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTracking();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTracking();
            }
        });

        btnRequestLocationPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestLocationPermission();
            }
        });

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    }

    private void startTracking() {
        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // Request location permission
                requestLocationPermission();
                return;
            }

            // Start tracking
            startTime = SystemClock.elapsedRealtime();
            distanceCovered = 0;
            totalSpeed = 0;
            speedCount = 0;
            stepsCount = 0;
            lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (lastLocation != null) {
                updateDistanceText(0);
            }

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            registerAccelerometerListener();
            isTracking = true;
        }
    }

    private void stopTracking() {
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
        unregisterAccelerometerListener();

        long elapsedTime = SystemClock.elapsedRealtime() - startTime;
        txtSteps.setText(getString(R.string.steps, stepsCount));
        txtAvgSpeed.setText(getString(R.string.avg_speed, 0.0f));

        // Calculate average speed if tracking was active
        if (isTracking) {
            float averageSpeed = (distanceCovered > 0 && elapsedTime > 0) ? (distanceCovered / elapsedTime) : 0;
            txtAvgSpeed.setText(getString(R.string.avg_speed, averageSpeed));
        }

        // Perform any additional calculations or actions based on the tracked data

        // Reset tracking state
        isTracking = false;
    }

    private void updateDistanceText(float distance) {
        if (isTracking) {
            distanceCovered = distance;
            txtDistance.setText(getString(R.string.distance, distanceCovered));
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (lastLocation != null && isTracking) {
            float distance = lastLocation.distanceTo(location);
            updateDistanceText(distance);

            // Calculate speed based on the elapsed time
            long elapsedTime = SystemClock.elapsedRealtime() - startTime;

            // Ensure that elapsedTime is not zero to avoid division by zero
            if (elapsedTime > 0) {
                float speed = distance / elapsedTime; // Calculate speed in meters per millisecond
                totalSpeed += speed;
                speedCount++;
            }

            // Perform any additional actions based on the tracked data
        }
        lastLocation = location;
        if (isTracking) {
            stepsCount++;
            txtSteps.setText(getString(R.string.steps, stepsCount));
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER && isTracking) {
            float[] values = event.values;
            float x = values[0];
            float y = values[1];
            float z = values[2];

            // Calculate magnitude of the acceleration vector
            double magnitude = Math.sqrt(x * x + y * y + z * z);

            // Check if the magnitude exceeds the step threshold
            if (magnitude > STEP_THRESHOLD) {
                stepsCount++;
                txtSteps.setText(getString(R.string.steps, stepsCount));
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // No implementation needed
    }

    private void registerAccelerometerListener() {
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SENSOR_DELAY_MICROS);
        } else {
            Toast.makeText(this, "Accelerometer sensor not available", Toast.LENGTH_SHORT).show();
        }
    }

    private void unregisterAccelerometerListener() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, start tracking
                startTracking();
            } else {
                // Permission denied, show a toast message
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_CODE);
    }

    // Implement the remaining LocationListener methods (onProviderEnabled, onProviderDisabled, onStatusChanged)
    // ...
}
