package states;

import character.Player;
import input.Input;

import java.util.List;

public class DashJumping implements StateMachine {
    @Override
    public void handleKeyInput(Player player, List<Input.Key> keys) {

    }

    @Override
    public void update(Player player) {
        player.setVelX(player.getFacing() * (player.getStep() * 2));
        player.setGravity(player.getGravity() - 0.25);
        player.setVelY((int) -player.getGravity());
        if (player.getGravity() <= 0.0) {
            player.currentState = PlayerState.falling;
        }
    }

    @Override
    public String toString() {
        return "DashJumping";
    }
}
