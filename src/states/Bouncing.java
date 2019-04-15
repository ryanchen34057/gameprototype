package states;

import character.Player;
import input.Input;

import java.util.List;

public class Bouncing implements StateMachine {
    @Override
    public void handleKeyInput(Player player, List<Input.Key> keys) {
        if(keys.get(2).down) {
            player.setFacing(-1);
        }
        else if(keys.get(3).down) {
            player.setFacing(1);
        }
    }

    @Override
    public void update(Player player) {
        player.accumulateFatigue();
        player.setVelX(player.getFacing() * -1 * 2.5);
        player.setGravity(player.getGravity() - 0.3);
        player.setVelY((int) -player.getGravity());
        if (player.getGravity() <= 0.0 || player.getFatigue() >= player.getSTAMINA()) {
            player.currentState = PlayerState.falling;
        }
    }

    @Override
    public String toString() {
        return "Bouncing";
    }
}
