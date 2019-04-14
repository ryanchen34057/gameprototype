package states;

import character.Player;
import input.Input;

import java.util.List;

public class Sliding implements StateMachine {
    private float friction = 0;
    @Override
    public void handleKeyInput(Player player, List<Input.Key> keys) {
        if(keys.get(5).down) {
            player.setGravity(7);
            player.currentState = PlayerState.bouncing;
        }
        if(player.getFacing() == -1) {
            if(!keys.get(2).down) {
                player.setGravity(0.8);
                player.currentState = PlayerState.falling;
            }
        }
        else {
            if(!keys.get(3).down) {
                player.setGravity(0.8);
                player.currentState = PlayerState.falling;
            }
        }

    }

    @Override
    public void update(Player player) {
        friction += 0.1;
        player.setVelY(friction);
        if(friction >= 5) {
            player.currentState = PlayerState.falling;
            friction = 0;
        }
    }

    @Override
    public String toString() {
        return "Sliding";
    }
}
