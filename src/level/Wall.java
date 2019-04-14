package level;

import UI.Game;
import character.Id;

import java.awt.*;

public class Wall extends Tile {
    public Wall(int x, int y, int width, int height,boolean breakable,  Id id) {
        super(x, y, width, height,breakable, id);
    }

    @Override
    public void paint(Graphics g) {
            g.drawImage(Game.wall.getBufferedImage(), super.getX(), super.getY(),
                    super.getWidth(), super.getHeight(), null);
            g.setColor(Color.RED);
            g.drawRect(super.getX(), super.getY(), super.getWidth(), super.getHeight());
    }

    @Override
    public void update() {

    }
}
