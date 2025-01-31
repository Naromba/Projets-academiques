package org.example.tp2;

import javafx.geometry.Bounds;
import javafx.scene.shape.Rectangle;

public interface Entity {
    void update(double time);
   Bounds getHitbox();

}
