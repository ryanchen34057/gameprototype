package states;

import character.Player;
import input.Input;

import java.util.List;

public class StandingJumping implements StateMachine {
    @Override
    public void handleKeyInput(Player player, List<Input.Key> keys) {
        if(keys.get(2).down) {
            player.setFacing(-1);
            player.setVelX(-1 * (player.getStep() / 1.5));
        }
        else if(keys.get(3).down) {
            player.setFacing(1);
            player.setVelX((player.getStep() / 1.5));
        }
        else if(keys.get(4).down && !PlayerState.dashing.isTired) {
            player.setVelY(0);
            player.currentState = PlayerState.dashingInTheAir;
            PlayerState.dashingInTheAir.isTired = true;
        }
    }

    @Override
    public void update(Player player) {
        player.setGravity(player.getGravity() - 0.3);
        player.setVelY((int) -player.getGravity());
        if (player.getGravity() <= 0.0) {
               player.currentState = PlayerState.falling;
        }
    }

    @Override
    public String toString() {
        return "StandingJumping";
    }
}
