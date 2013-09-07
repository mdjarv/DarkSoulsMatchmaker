package org.mdjarv.darksoulsmatchmaking;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class DarkSoulsTextView extends TextView {

	public DarkSoulsTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		this.setTextColor(getResources().getColor(android.R.color.white));
		
		if(!isInEditMode())
			this.setTypeface(Typeface.createFromAsset(context.getAssets(), "OptimusPrincepsSemiBold.ttf"));
	}
}
