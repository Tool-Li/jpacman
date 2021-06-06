package nl.tudelft.jpacman.cucumber;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.npc.ghost.Navigation;

import static org.assertj.core.api.Assertions.assertThat;

public class MovetheGhost3_4 {

    private Launcher launcher;
    private Level level;

    private Ghost ghost;
    private Player player;

    private Square square, nextsquare;

    private Game getGame() {
        return launcher.getGame();
    }

    @Given("the game has started ghost 04")
    public void the_game_has_started_ghost() {
        // Write code here that turns the phrase above into concrete actions
        // throw new io.cucumber.java.PendingException();
        launcher = new Launcher();
        launcher.withMapFile("/MovetheGhostMap/GhostMap04.txt");
        launcher.launch();
        getGame().start();
        assertThat(getGame().isInProgress()).isTrue();
    }

    @Given("a ghost is next to a cell containing the player 04")
    public void a_ghost_is_next_to_a_cell_containing_the_player() {
        // Write code here that turns the phrase above into concrete actions
        // throw new io.cucumber.java.PendingException();
        level = getGame().getLevel();
        ghost = Navigation.findUnitInBoard(Ghost.class, level.getBoard());
        assertThat(ghost).isNotNull();
        square = ghost.getSquare();

        nextsquare = square.getSquareAt(Direction.EAST);

        assertThat(nextsquare.getOccupants().size()).isEqualTo(1);
        assertThat(nextsquare.getOccupants().get(0)).isInstanceOf(Player.class);
        player = (Player) nextsquare.getOccupants().get(0);
    }

    @When("a tick event occurs 04")
    public void a_tick_event_occurs() {
        // Write code here that turns the phrase above into concrete actions
        // throw new io.cucumber.java.PendingException();
        level.move(ghost, Direction.EAST);
    }

    @Then("the ghost can move to the player 04")
    public void the_ghost_can_move_to_the_player() {
        // Write code here that turns the phrase above into concrete actions
        // throw new io.cucumber.java.PendingException();
        assertThat(square.getOccupants().size()).isEqualTo(0);
        // 此时两者相碰撞，player死亡，游戏结束，但是不从square上移除
        assertThat(nextsquare.getOccupants().size()).isEqualTo(2);
        assertThat(nextsquare.getOccupants().get(1)).isInstanceOf(Ghost.class);
    }

    @Then("the game is over ghost 04")
    public void the_game_is_over_ghost() {
        // Write code here that turns the phrase above into concrete actions
        // throw new io.cucumber.java.PendingException();
        assertThat(player.isAlive()).isFalse();
        assertThat(player.getKiller()).isEqualTo(ghost);
        assertThat(getGame().isInProgress()).isFalse();
    }

    @After("@framework")
    void teardownUI() {
        launcher.dispose();
    }
}
