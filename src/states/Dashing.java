package states;

import character.Player;
import input.Input;

import java.util.List;

public class Dashing implements StateMachine {
    private static final int DASH_SPEED = 20;
    private float dashTimer = 0.8f;
    public boolean isTired = false;
    @Override
    public void handleKeyInput(Player player, List<Input.Key> keys) {
        if(keys.get(2).down) {
            player.setFacing(-1);
            if(keys.get(5).down) {
                player.setGravity(8.0);
                player.currentState = PlayerState.dashJumping;
            }
        }
        if(keys.get(3).down) {
            player.setFacing(1);
            if(keys.get(5).down) {
                player.setGravity(8.0);
                player.currentState = PlayerState.dashJumping;
            }
        }
        if(keys.get(5).down) {
            player.setGravity(8.0);
            player.currentState = PlayerState.dashJumping;
        }
    }

    @Override
    public void update(Player player) {
        player.setVelX(DASH_SPEED * player.getFacing());
        dashTimer -= (60.0f / 1000.0f);
        if(dashTimer <= 0) {
            //Reset timer
            dashTimer = 0.7f;
            player.currentState = PlayerState.standing;
        }
    }

    @Override
    public String toString() {
        return "Dashing";
    }
}
