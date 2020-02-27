package com.initiative.smsnow;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
  private static final int SMS_PERMISSION_REQUEST_CODE = 200;
  private static final int CONTACT_PERMISSION_REQUEST_CODE = 300;
//  private static final String[] permissionArray = {Manifest.permission.RECEIVE_SMS, Manifest.permission.SEND_SMS, Manifest.permission.READ_CONTACTS};

  @RequiresApi(api = Build.VERSION_CODES.M)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    Objects.requireNonNull(getSupportActionBar()).setTitle("Messaging");

    checkPermission(Manifest.permission.RECEIVE_SMS, SMS_PERMISSION_REQUEST_CODE);
    checkPermission(Manifest.permission.READ_CONTACTS, CONTACT_PERMISSION_REQUEST_CODE );

    FloatingActionButton fab = findViewById(R.id.fab);
    fab.setColorFilter(getColor(R.color.colorPrimaryDark));
    fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show());

    NavController controller = Navigation.findNavController(this, R.id.nav_host_fragment);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  private void checkPermission(String permission, int requestCode) {
    if (ContextCompat.checkSelfPermission(MainActivity.this, permission) == PackageManager.PERMISSION_DENIED) {
      ActivityCompat.requestPermissions(MainActivity.this, new String[] {permission}, requestCode);
    } else {
      Toast.makeText(MainActivity.this, "Permission already granted", Toast.LENGTH_LONG).show();
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    if (requestCode == SMS_PERMISSION_REQUEST_CODE){
      if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        Toast.makeText(MainActivity.this, "SMS Permission Granted", Toast.LENGTH_SHORT).show();
      } else {
        Toast.makeText(MainActivity.this, "SMS Permission Denied", Toast.LENGTH_SHORT).show();
      }
    } else if(requestCode == CONTACT_PERMISSION_REQUEST_CODE){
      if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        Toast.makeText(this, "Contact Permission Granted", Toast.LENGTH_SHORT).show();
      } else {
        Toast.makeText(this, "Contact Permission Denied", Toast.LENGTH_SHORT).show();
      }

    }
  }
}
