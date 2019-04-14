package states;

import character.Player;
import input.Input;

import java.util.List;

public class Bouncing implements StateMachine {
    @Override
    public void handleKeyInput(Player player, List<Input.Key> keys) {

    }

    @Override
    public void update(Player player) {
        player.setVelX(player.getFacing() * -1 * 3);
        player.setGravity(player.getGravity() - 0.3);
        player.setVelY((int) -player.getGravity());
        if (player.getGravity() <= 0.0) {
            player.currentState = PlayerState.falling;
            System.out.println("!");
        }
    }

    @Override
    public String toString() {
        return "Bouncing";
    }
}
