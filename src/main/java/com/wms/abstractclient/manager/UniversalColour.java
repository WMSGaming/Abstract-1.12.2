package com.wms.abstractclient.manager;

import java.awt.*;

public class UniversalColour {
    int red,green,blue;

    public UniversalColour(int red, int green, int blue){
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }
    public Color getColor(){
        return new Color(getRed(),getGreen(),getBlue());
    }
}
