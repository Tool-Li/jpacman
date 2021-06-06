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
import nl.tudelft.jpacman.npc.ghost.Blinky;
import nl.tudelft.jpacman.npc.ghost.Navigation;

import static org.assertj.core.api.Assertions.assertThat;

public class MovetheGhost3_3 {

    private Launcher launcher;
    private Level level;

    private Pellet pellet;
    private Ghost ghost;
    private Square square, nextsquare, nextnextsquare;

    private Game getGame() {
        return launcher.getGame();
    }

    @Given("a ghost is on a cell with a pellet \\(see S3.2) 03")
    public void a_ghost_is_on_a_cell_with_a_pellet_see_S3() {
        // Write code here that turns the phrase above into concrete actions
        // throw new io.cucumber.java.PendingException();
        launcher = new Launcher();
        launcher.withMapFile("/MovetheGhostMap/GhostMap03.txt");
        launcher.launch();

        getGame().start();
        assertThat(getGame().isInProgress()).isTrue();

        level = getGame().getLevel();
        ghost = Navigation.findUnitInBoard(Blinky.class, level.getBoard());

        square = ghost.getSquare();


        nextsquare = square.getSquareAt(Direction.EAST);
        assertThat(nextsquare.getOccupants().size()).isEqualTo(1);
        assertThat(nextsquare.getOccupants().get(0)).isInstanceOf(Pellet.class);
        pellet =(Pellet) nextsquare.getOccupants().get(0);

        level.move(ghost, Direction.EAST);

        assertThat(square.getOccupants().size()).isEqualTo(0);
        assertThat(nextsquare.getOccupants().size()).isEqualTo(2);
        assertThat(nextsquare.getOccupants().get(1)).isInstanceOf(Ghost.class);

        assertThat(nextsquare.getOccupants().get(1).getSprite()).isEqualTo(ghost.getSprite());
    }

    @When("a tick even occurs 03")
    public void a_tick_even_occurs() {
        // Write code here that turns the phrase above into concrete actions
        // throw new io.cucumber.java.PendingException();
        nextnextsquare = nextsquare.getSquareAt(Direction.EAST);
        level.move(ghost, Direction.EAST);
    }

    @Then("the ghost can move away from the cell with the pellet 03")
    public void the_ghost_can_move_away_from_the_cell_with_the_pellet() {
        // Write code here that turns the phrase above into concrete actions
        // throw new io.cucumber.java.PendingException();
        assertThat(nextnextsquare.getOccupants().size()).isEqualTo(1);
        assertThat(nextnextsquare.getOccupants().get(0)).isInstanceOf(Ghost.class);

        assertThat(nextsquare.getOccupants().size()).isEqualTo(1);
        assertThat(nextsquare.getOccupants().get(0)).isInstanceOf(Pellet.class);
    }

    @Then("the pellet on that cell is is visible again 03")
    public void the_pellet_on_that_cell_is_is_visible_again() {
        // Write code here that turns the phrase above into concrete actions
        // throw new io.cucumber.java.PendingException();
        assertThat(nextsquare.getOccupants().get(0).getSprite()).isEqualTo(pellet.getSprite());
    }

    @After("@framework")
    void teardownUI() {
        launcher.dispose();
    }
}
