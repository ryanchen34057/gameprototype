package states;

import character.Player;
import input.Input;

import java.util.List;

public class Running implements StateMachine {
    @Override
    public void handleKeyInput(Player player, List<Input.Key> keys) {
        if(!keys.get(2).down && !keys.get(3).down) {
            player.currentState = PlayerState.standing;
        }
        if(keys.get(5).down) {
            player.setGravity(8.0);
            player.currentState = PlayerState.runningJumping;
        }
        if(keys.get(4).down) {
            if(!PlayerState.dashing.isTired) {
                player.currentState = PlayerState.dashing;
                PlayerState.dashing.isTired = true;
            }
        }
        else if(!keys.get(4).down) {
            PlayerState.dashing.isTired = false;
        }
    }

    @Override
    public void update(Player player) {
        player.setVelX(player.getFacing() * player.getStep());
    }

    @Override
    public String toString() {
        return "Running";
    }
}
