package nl.tudelft.jpacman.cucumber;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.npc.ghost.Blinky;
import nl.tudelft.jpacman.npc.ghost.Navigation;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MovetheGhost3_1 {

    private Launcher launcher;
    private Level level;
    private Ghost ghost;
    private Game game;

    private Square square, nextsquare;

    private Game getGame() {
        return launcher.getGame();
    }


    @Given("the game has started ghost 01")
    public void the_game_has_started_ghost() {
        // Write code here that turns the phrase above into concrete actions
        // throw new io.cucumber.java.PendingException();
        launcher = new Launcher();
        launcher.withMapFile("/MovetheGhostMap/GhostMap01.txt");
        launcher.launch();

        game = getGame();
        game.start();

        assertThat(getGame().isInProgress()).isTrue();
    }

    @Given("a ghost is next to an empty cell 01")
    public void a_ghost_is_next_to_an_empty_cell() {
        // Write code here that turns the phrase above into concrete actions
        // throw new io.cucumber.java.PendingException();

        // 获取level对象
        level = launcher.makeLevel();
        // 获取Ghost对象
        ghost = Navigation.findUnitInBoard(Ghost.class, level.getBoard());
        // 判断是否有blinky对象
        assertThat(ghost).isNotNull();

        square = ghost.getSquare();
        nextsquare = square.getSquareAt(Direction.EAST);
        List<Unit> units = nextsquare.getOccupants();
        // 判断旁边的方块上是否为空
        assertThat(units.size()).isEqualTo(0);
    }

    @When("a tick event occurs 01")
    public void a_tick_event_occurs() {
        // Write code here that turns the phrase above into concrete actions
        // throw new io.cucumber.java.PendingException();
        ghost.occupy(nextsquare);
    }

    @Then("the ghost can move to that cell 01")
    public void the_ghost_can_move_to_that_cell() {
        // Write code here that turns the phrase above into concrete actions
        // throw new io.cucumber.java.PendingException();
        // 判断blinky移动后square上有没有对象
        assertThat(square.getOccupants().size()).isEqualTo(0);
        // 判断blinky移动后nextsquare上有没有对象
        assertThat(nextsquare.getOccupants().size()).isEqualTo(1);
        // 判断nextsquare上的对象是不是blinky
        assertThat(nextsquare.getOccupants().get(0)).isInstanceOf(Blinky.class);
    }

    @After("@framework")
    void teardownUI() {
        launcher.dispose();
    }

}
