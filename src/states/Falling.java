package states;

import character.Player;
import input.Input;

import java.util.List;

public class Falling implements StateMachine {
    @Override
    public void handleKeyInput(Player player, List<Input.Key> keys) {
        if(keys.get(2).down) {
            player.setFacing(-1);
            player.setVelX(-3);
        }
        else if(keys.get(3).down) {
            player.setFacing(1);
            player.setVelX(3);
        }
        else if(keys.get(4).down && !PlayerState.dashingInTheAir.isTired) {
            player.setVelY(0);
            player.currentState = PlayerState.dashingInTheAir;
            PlayerState.dashingInTheAir.isTired = true;
        }
        else {
            player.setVelX(0);
        }
    }

    @Override
    public void update(Player player) {
        player.setGravity(player.getGravity() + 0.3);
        player.setVelY((int) player.getGravity());
    }

    @Override
    public String toString() {
        return "Falling";
    }
}
