package nl.tudelft.jpacman.board;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test suite to confirm that {@link Unit}s correctly (de)occupy squares.
 *
 * @author Jeroen Roosen 
 *
 */
class OccupantTest {

    /**
     * The unit under test.
     */
    private Unit unit;

    /**
     * Resets the unit under test.
     */
    @BeforeEach
    void setUp() {
        unit = new BasicUnit();
    }

    /**
     * Asserts that a unit has no square to start with.
     */
    @Test
    @DisplayName("测试方格")
    void noStartSquare() {
        // Remove the following placeholder:
        assertThat(unit).isNotNull();
        //判断是否被占据
        assertThat(unit.hasSquare()).isFalse();

    }

    /**
     * Tests that the unit indeed has the target square as its base after
     * occupation.
     */
    @Test
    @DisplayName("测试占据")
    void testOccupy() {
        // Remove the following placeholder:
        assertThat(unit).isNotNull();

        Square square = new BasicSquare();//定义
        unit.occupy(square); //产生关系
        assertThat(unit.getSquare()).isEqualTo(square);//判断占据的是不是游戏单元
        assertThat(square.getOccupants()).contains(unit);//判断这个单元是否被游戏单元包含
    }

    /**
     * Test that the unit indeed has the target square as its base after
     * double occupation.
     */
    @Test
    @DisplayName("测试再占据")
    void testReoccupy() {
        // Remove the following placeholder:
        assertThat(unit).isNotNull();

        Square square = new BasicSquare();
        unit.occupy(square);
        unit.occupy(square);
        assertThat(unit.getSquare()).isEqualTo(square);
        assertThat(square.getOccupants()).contains(unit).containsOnlyOnce(unit);
    }
}
