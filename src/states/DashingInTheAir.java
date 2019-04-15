package states;

import character.Player;
import input.Input;

import java.util.List;

public class DashingInTheAir implements StateMachine{
    private float dashTimer = Player.DASH_TIMER;
    public boolean isTired = false;
    @Override
    public void handleKeyInput(Player player, List<Input.Key> keys) {

    }

    @Override
    public void update(Player player) {
        player.setVelX(player.getDashSpeed() * player.getFacing());
        dashTimer -= (60.0f / 1000.0f);
        player.CURRENT_DASH_SPEED -= 0.5;
        if(dashTimer <= 0) {
            //Reset timer
            dashTimer = Player.DASH_TIMER;
            player.setGravity(0.8);
            player.currentState = PlayerState.falling;
            player.CURRENT_DASH_SPEED = Player.DASH_SPEED;
        }
    }

    @Override
    public String toString() {
        return "DashingInTheAir";
    }
}
