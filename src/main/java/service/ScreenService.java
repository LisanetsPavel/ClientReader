package service;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

/**
 * Created by pc8 on 10.08.15.
 */
public class ScreenService {
    private final Rectangle2D SCREEN = Screen.getPrimary().getVisualBounds();

    private double getHeight() {
        return SCREEN.getMaxY();
    }

    private double getWidth() {
        return SCREEN.getMaxX();
    }

    public double getHeightForImageView() {
        return getHeight() * 85 / 100;
    }

    public double getWidthForImageView() {
        return getWidth() * 50 / 100;
    }


    public double getHeightForImageField() {
        return getHeight() * 95 / 100;
    }

    public double getWidthForImageField() {
        return getWidth() * 50 / 100;
    }

    public double getHeightForBookList() {
        return getHeight() * 65 / 100;
    }

    public double getWidthForBookList() {
        return getWidth() * 80 / 100;
    }

    public double getHeightForloginView() {
        return getHeight() * 40 / 100;
    }

    public double getWidthForloginView() {
        return getWidth() * 48 / 100;
    }
}
