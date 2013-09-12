package org.mdjarv.darksoulsmatchmaker;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.NumberPicker;

import java.util.ArrayList;

public class DarkSoulsMatchmaker extends ListActivity implements NumberPicker.OnValueChangeListener {
    private ArrayList<LevelRange> levelRanges = new ArrayList<LevelRange>();
    private LevelRangeAdapter m_adapter;
    private LevelRange whiteSoapstone = new LevelRange(R.drawable.white_sign_soapstone, 1, 10);
    private LevelRange redSoapstone = new LevelRange(R.drawable.red_sign_soapstone, 1, 10);
    private LevelRange redEyeOrb = new LevelRange(R.drawable.redeyeorb, 1, 10);
    private LevelRange blueEyeOrb = new LevelRange(R.drawable.blue_eye_orb, 1, 10);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateLevelRanges();

        NumberPicker levelNumberPicker = (NumberPicker) findViewById(R.id.soullevelnumberpicker);
        levelNumberPicker.setMinValue(LevelRange.MIN_LEVEL);
        levelNumberPicker.setMaxValue(LevelRange.MAX_LEVEL);
        levelNumberPicker.setOnValueChangedListener(this);

        calculateLevelRanges(levelNumberPicker.getValue());

        m_adapter = new LevelRangeAdapter(this, R.layout.level_range, levelRanges);

        this.getListView().addHeaderView(this.getLayoutInflater().inflate(R.layout.list_header, null));

        setListAdapter(m_adapter);
    }

    private void populateLevelRanges() {
        levelRanges.add(whiteSoapstone);
        levelRanges.add(redSoapstone);
        levelRanges.add(redEyeOrb);
        levelRanges.add(blueEyeOrb);
    }

    @Override
    public void onValueChange(NumberPicker numberPicker, final int oldVal, final int newVal) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                calculateLevelRanges(newVal);
                m_adapter.notifyDataSetChanged();
            }
        });
    }

    private void calculateLevelRanges(int level) {
        int modifier = 0;

        // White Soapstone
        modifier = 10 + (int) (0.1 * level);
        whiteSoapstone.setMin(level - modifier);
        whiteSoapstone.setMax(level + modifier);

        // Red Soapstone
        modifier = 10 + (int) (0.1 * level);
        redSoapstone.setMin(level - modifier);
        redSoapstone.setMax(LevelRange.MAX_LEVEL);

        // Red Eye Orb
        modifier = (int) (0.1 * level);
        redEyeOrb.setMin(level - modifier);
        redEyeOrb.setMax(LevelRange.MAX_LEVEL);

        // Blue Eye Orb
        modifier = 50 + (int) (0.2 * level);
        blueEyeOrb.setMin(level - modifier);

        modifier = 10 + (int) (0.1 * level);
        blueEyeOrb.setMax(level + modifier);

    }
}
