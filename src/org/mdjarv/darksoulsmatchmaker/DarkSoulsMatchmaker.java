package org.mdjarv.darksoulsmatchmaker;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;

import java.util.ArrayList;

public class DarkSoulsMatchmaker extends ListActivity implements TextWatcher {
	private ArrayList<LevelRange> levelRanges = new ArrayList<LevelRange>();
	private LevelRangeAdapter m_adapter;
	EditText levelTextEdit;

	private int currentLevel;
	private LevelRange whiteSignSoapstone = new LevelRange(R.drawable.white_sign_soapstone, 1, 10);
	private LevelRange redSignSoapstone = new LevelRange(R.drawable.red_sign_soapstone, 1, 10);
	private LevelRange redEyeOrb = new LevelRange(R.drawable.redeyeorb, 1, 10);
	private LevelRange blueEyeOrb = new LevelRange(R.drawable.blue_eye_orb, 1, 10);

	private LevelRange eyeOfDeath = new LevelRange(R.drawable.eyeofdeath, 1, 10);
	private LevelRange dragonEye = new LevelRange(R.drawable.dragoneye, 1, 10);
	private LevelRange catCovenantRing = new LevelRange(R.drawable.cat_covenant_ring, 1, 10);
	private LevelRange crackedRedEyeOrb = new LevelRange(R.drawable.cracked_red_eye_orb, 1, 10);
	private LevelRange darkmoonBladeCovenantRing = new LevelRange(R.drawable.darkmoon_blade_covenant_ring, 1, 10);

	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		populateLevelRanges();

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			NumberPicker levelNumberPicker = (NumberPicker) findViewById(R.id.soullevelnumberpicker);
			levelNumberPicker.setMinValue(LevelRange.MIN_LEVEL);
			levelNumberPicker.setMaxValue(LevelRange.MAX_LEVEL);
			levelNumberPicker
					.setOnValueChangedListener(new OnValueChangeListener() {

						@Override
						public void onValueChange(NumberPicker picker,
								int oldVal, final int newVal) {
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									calculateLevelRanges(newVal);
									m_adapter.notifyDataSetChanged();
								}
							});
						}
					});
			calculateLevelRanges(levelNumberPicker.getValue());
		} else {
			levelTextEdit = (EditText) findViewById(R.id.levelEditText);
			levelTextEdit.addTextChangedListener(this);

			// ADD
			((Button) findViewById(R.id.addButton))
					.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							adjustLevelEditText(1);
						}
					});

			// SUBTRACT
			((Button) findViewById(R.id.subButton))
					.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							adjustLevelEditText(-1);
						}
					});
		}

		m_adapter = new LevelRangeAdapter(this, R.layout.level_range,
				levelRanges);

		this.getListView().addHeaderView(
				this.getLayoutInflater().inflate(R.layout.list_header, null));

		setListAdapter(m_adapter);
	}

	private void adjustLevelEditText(final int i) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				int newLevel = currentLevel + i;

				if (newLevel >= LevelRange.MIN_LEVEL
						&& newLevel <= LevelRange.MAX_LEVEL)
					levelTextEdit.setText(String.format("%d", newLevel));
			}
		});
	}

	private void populateLevelRanges() {
		levelRanges.add(whiteSignSoapstone);
		levelRanges.add(redSignSoapstone);
		levelRanges.add(redEyeOrb);
		levelRanges.add(blueEyeOrb);
		levelRanges.add(darkmoonBladeCovenantRing);
		levelRanges.add(catCovenantRing);
		levelRanges.add(crackedRedEyeOrb);
		levelRanges.add(eyeOfDeath);
		levelRanges.add(dragonEye);
	}

	private void calculateLevelRanges(int level) {
		currentLevel = level;
		int modifier = 0;

		// White Soapstone
		modifier = 10 + (int) (0.1 * level);
		whiteSignSoapstone.setMin(level - modifier);
		whiteSignSoapstone.setMax(level + modifier);

		// Red Soapstone
		modifier = 10 + (int) (0.1 * level);
		redSignSoapstone.setMin(level - modifier);
		redSignSoapstone.setMax(LevelRange.MAX_LEVEL);

		// Red Eye Orb
		modifier = (int) (0.1 * level);
		redEyeOrb.setMin(level - modifier);
		redEyeOrb.setMax(LevelRange.MAX_LEVEL);

		// Blue Eye Orb
		modifier = 50 + (int) (0.2 * level);
		blueEyeOrb.setMin(level - modifier);

		modifier = 10 + (int) (0.1 * level);
		blueEyeOrb.setMax(level + modifier);
		
		eyeOfDeath.copyRange(whiteSignSoapstone);
		dragonEye.copyRange(whiteSignSoapstone);
		
		catCovenantRing.copyRange(redSignSoapstone);
		
		crackedRedEyeOrb.copyRange(redEyeOrb);
		
		darkmoonBladeCovenantRing.copyRange(blueEyeOrb);
	}

	@Override
	public void afterTextChanged(Editable arg0) {

	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {

	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		final int level;
		try {
			level = Integer.parseInt(arg0.toString());
		} catch (NumberFormatException e) {
			return;
		}

		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				calculateLevelRanges(level);
				if (level > LevelRange.MAX_LEVEL)
					levelTextEdit.setText("" + LevelRange.MAX_LEVEL);
				else if (level < LevelRange.MIN_LEVEL)
					levelTextEdit.setText("" + LevelRange.MIN_LEVEL);

				m_adapter.notifyDataSetChanged();
			}
		});
	}
}
