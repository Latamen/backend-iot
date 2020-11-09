package org.example.models;

public class Light extends Thing {

    public interface OnLightChangedListener {
        void onLightChanged(Light sender, boolean isLightOn);
    }

    private boolean isLightOn = false;
    private OnLightChangedListener lightChangedListener;

    @Override
    public String getTypeName() {
        return "Light";
    }

    @Override
    public String getDescription() {
        return "light is on=" + isLightOn;
    }

    public boolean isLightOn() {
        return isLightOn;
    }

    public void setLightOn(boolean lightOn) {
        if (state != State.REACHABLE) {
            return;
        }
        isLightOn = lightOn;

        if (lightChangedListener != null) {
            lightChangedListener.onLightChanged(this, isLightOn);
        }
    }

    public void setOnLightChangedListener(OnLightChangedListener lightChangedListener) {
        this.lightChangedListener = lightChangedListener;
    }
}
