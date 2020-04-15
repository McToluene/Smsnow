package com.initiative.smsnow;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
  private final static int REQUEST_CODE_ASK_PERMISSIONS = 1;
  private static String[] permissions = {Manifest.permission.RECEIVE_SMS,  Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS, Manifest.permission.READ_CONTACTS};

  @RequiresApi(api = Build.VERSION_CODES.M)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    checkPermissions();
    NavController controller = Navigation.findNavController(this, R.id.nav_host_fragment);
  }

  protected void checkPermissions() {
    final List<String> missingPermissions = new ArrayList<String>();
    // check all required dynamic permissions
    for (final String permission : permissions) {
      final int result = ContextCompat.checkSelfPermission(this, permission);
      if (result != PackageManager.PERMISSION_GRANTED) {
        missingPermissions.add(permission);
      }
    }
    if (!missingPermissions.isEmpty()) {
      // request all missing permissions
      final String[] permissions = missingPermissions.toArray(new String[missingPermissions.size()]);
      ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_ASK_PERMISSIONS);
    } else {
      final int[] grantResults = new int[permissions.length];
      Arrays.fill(grantResults, PackageManager.PERMISSION_GRANTED);
      onRequestPermissionsResult(REQUEST_CODE_ASK_PERMISSIONS, permissions, grantResults);
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (requestCode == REQUEST_CODE_ASK_PERMISSIONS) {
      for (int index = permissions.length - 1; index >= 0; --index) {
        if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
          // exit the app if one permission is not granted
          Toast.makeText(this, "Required permission '" + permissions[index]
                  + "' not granted, exiting", Toast.LENGTH_LONG).show();
          finish();
          return;
        }
      }
    }
  }
}
