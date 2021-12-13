//package com.example.driverapp;
//
//import android.Manifest;
//import android.content.pm.PackageManager;
//import android.location.Location;
//import android.widget.Toast;
//
//import androidx.core.app.ActivityCompat;
//
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//
//public class Sample {
//    Location currentLocation;
//    FusedLocationProviderClient fusedLocationProviderClient;
//    private static final int REQUEST_CODE = 101;
//
//
//    private void fetchLastLocation(GoogleMap googleMap) {
////        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
////            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
////
////            return;
////        }
////        Task<Location> task = fusedLocationProviderClient.getLastLocation();
////        task.addOnSuccessListener(new OnSuccessListener<Location>() {
////            @Override
////            public void onSuccess(Location location) {
////                if(location != null)
////                {
////                    currentLocation = location;
////                    Toast.makeText(getApplicationContext(),currentLocation.getLatitude()+" "+currentLocation.getLongitude() ,Toast.LENGTH_SHORT).show();
////
////
////
////
////                }
////            }
////        })
////        LatLng latlng = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
////        MarkerOptions mo = new MarkerOptions().position(latlng).title("I am Here");
////        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latlng));
////        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng,7));
////        googleMap.addMarker(mo);
//
//        switch (REQUEST_CODE) {
//            case REQUEST_CODE:
//                if(REQUEST_CODE > 0)
//                {
//
//                }
//                break;
//        }
//
//    }
//
//
//
//
//}
