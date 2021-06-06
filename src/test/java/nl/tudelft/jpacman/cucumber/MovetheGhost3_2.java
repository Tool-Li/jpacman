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
import nl.tudelft.jpacman.level.Pellet;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.npc.ghost.Navigation;

import static org.assertj.core.api.Assertions.assertThat;

public class MovetheGhost3_2 {

    private Launcher launcher;
    private Level level;

    private Ghost ghost;
    private Pellet pellet;

    private Square square, nextsquare;

    private Game getGame() {
        return launcher.getGame();
    }

    @Given("the game has started ghost 02")
    public void the_game_has_started_ghost() {
        // Write code here that turns the phrase above into concrete actions
        // throw new io.cucumber.java.PendingException();
        launcher = new Launcher();
        launcher.withMapFile("/MovetheGhostMap/GhostMap02.txt");
        launcher.launch();

        getGame().start();

        assertThat(getGame().isInProgress()).isTrue();
    }

    @Given("a ghost is next to a cell containing a pellet 02")
    public void a_ghost_is_next_to_a_cell_containing_a_pellet() {
        // Write code here that turns the phrase above into concrete actions
        // throw new io.cucumber.java.PendingException();
        level = getGame().getLevel();
        ghost = Navigation.findUnitInBoard(Ghost.class, level.getBoard());

        assertThat(ghost).isNotNull();

        square = ghost.getSquare();
        nextsquare = square.getSquareAt(Direction.EAST);

        // 检查nextsquare上有没有pellet对象
        assertThat(nextsquare.getOccupants().size()).isEqualTo(1);
        assertThat(nextsquare.getOccupants().get(0)).isInstanceOf(Pellet.class);

        pellet = (Pellet) nextsquare.getOccupants().get(0);
    }

    @When("a tick event occurs 02")
    public void a_tick_event_occurs() {
        // Write code here that turns the phrase above into concrete actions
        // throw new io.cucumber.java.PendingException();
        level.move(ghost, Direction.EAST);
    }

    @Then("the ghost can move to the cell with the pellet 02")
    public void the_ghost_can_move_to_the_cell_with_the_pellet() {
        // Write code here that turns the phrase above into concrete actions
        // throw new io.cucumber.java.PendingException();
        // Ghost移动后square上没有对象
        assertThat(square.getOccupants().size()).isEqualTo(0);
        // Ghost移动后nextsquare上有两个对象
        assertThat(nextsquare.getOccupants().size()).isEqualTo(2);
        assertThat(nextsquare.getOccupants().get(0)).isInstanceOf(Pellet.class);
        assertThat(nextsquare.getOccupants().get(1)).isInstanceOf(Ghost.class);

    }

    @Then("the pellet on that cell is not visible anymore 02")
    public void the_pellet_on_that_cell_is_not_visible_anymore() {
        // Write code here that turns the phrase above into concrete actions
        // throw new io.cucumber.java.PendingException();
        assertThat(nextsquare.getOccupants().get(1).getSprite()).isEqualTo(ghost.getSprite());
    }

    @After("@framework")
    void teardownUI() {
        launcher.dispose();
    }
}
