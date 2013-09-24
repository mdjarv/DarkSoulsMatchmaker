package org.mdjarv.darksoulsmatchmaker;

public class LevelRange {
    public static final int MIN_LEVEL = 1;
    public static final int MAX_LEVEL = 713;

    private int min;
    private int max;
    private int imageId;

    public LevelRange(int imageId, int min, int max) {
        this.imageId = imageId;
        this.min = min;
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        if(min < MIN_LEVEL)
            this.min = MIN_LEVEL;
        else
            this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        if(max > MAX_LEVEL)
            this.max = MAX_LEVEL;
        else
            this.max = max;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

	public void copyRange(LevelRange range) {
		this.min = range.getMin();
		this.max = range.getMax();
	}
}
