package com.chriswarrick.android_flashlight;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.widget.Switch;
import android.widget.CompoundButton;
import android.widget.Toast;

public class MainActivity extends Activity {
	Camera c;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Switch toggle = (Switch) findViewById(R.id.lswitch);
		if (this.c == null) {
			this.c = Camera.open();
		}
		if (toggle.isChecked()) {
			this.start();
		}
		toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					MainActivity.this.start();
				} else {
					MainActivity.this.stop();
				}
			}
		});
	}

	public void close(View v) {
		this.finish();
	}

	public void minimize(View v) {
		Context context = getApplicationContext();
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, "Not implemented.", duration);
		toast.show();
	}

	@Override
	public void onPause() {
		super.onPause();
		if (this.c != null) {
			this.c.release();
			this.c = null;
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if (this.c == null) {
			this.c = Camera.open();
		}
	}

	public void start() {
		Parameters p = this.c.getParameters();
		p.setFlashMode(Parameters.FLASH_MODE_TORCH);
		this.c.setParameters(p);
		this.c.startPreview();
	}

	public void stop() {
		Parameters p = this.c.getParameters();
		p.setFlashMode(Parameters.FLASH_MODE_OFF);
		this.c.setParameters(p);
		this.c.stopPreview();
	}

	public void onToggled(View view) {
		boolean on = ((Switch) view).isChecked();

		if (on) {
			this.start();
		} else {
			this.stop();
		}
	}
}
