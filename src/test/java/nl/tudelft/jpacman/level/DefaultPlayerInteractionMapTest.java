package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.points.PointCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

/**
 * 使用mock方法测试来自DefaultPlayerInteractionMap类的collision方法。
 */
public class DefaultPlayerInteractionMapTest extends CollisionMapTest {

    /**
     * 初始化测试用例参数
     */
    @BeforeEach
    @Override
    void init() {
        this.setPointCalculator(Mockito.mock(PointCalculator.class));
        this.setPlayer(Mockito.mock(Player.class));
        this.setPellet(Mockito.mock(Pellet.class));
        this.setGhost(Mockito.mock(Ghost.class));
        this.setPlayerCollisions(new DefaultPlayerInteractionMap(this.getPointCalculator()));
    }
}
