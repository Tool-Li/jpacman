package nl.tudelft.jpacman.board;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * A very simple (and not particularly useful)
 * test class to have a starting point where to put tests.
 *
 * @author Arie van Deursen
 */
public class DirectionTest {
    /**
     * Do we get the correct delta when moving north?
     */
    @Test
    @DisplayName("测试向北移动")
    void testNorth() {
        Direction north = Direction.valueOf("NORTH");

        assertThat(north.getDeltaX()).isEqualTo(0);
        assertThat(north.getDeltaY()).isEqualTo(-1);
    }

    @Test
    @DisplayName("向东移动测试")
    void testEast() {
        Direction east = Direction.valueOf("EAST");

        assertThat(east.getDeltaX()).isEqualTo(1);
        assertThat(east.getDeltaY()).isEqualTo(0);
    }

    @Test
    @DisplayName("测试向西移动")
    void testWest(){
        Direction west = Direction.valueOf("WEST");

        assertThat(west.getDeltaX()).isEqualTo(-1);
        assertThat(west.getDeltaY()).isEqualTo(0);
    }

    @Test
    @DisplayName("测试向南移动")
    void testSouth(){
        Direction south = Direction.valueOf("SOUTH");

        assertThat(south.getDeltaX()).isEqualTo(0);
        assertThat(south.getDeltaY()).isEqualTo(1);

    }

}
