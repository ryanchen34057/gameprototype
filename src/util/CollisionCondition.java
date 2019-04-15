package util;

import level.Tile;

import java.awt.*;

public interface CollisionCondition {
    Rectangle checkCollision(Tile t);
}
