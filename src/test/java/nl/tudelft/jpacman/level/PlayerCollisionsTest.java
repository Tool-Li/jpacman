package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.points.PointCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

/**
 * mockito验证不同单元之间的碰撞是否被正确处理。
 */
public class PlayerCollisionsTest extends CollisionMapTest {

    /**
     * 初始化玩家、小球、魔鬼用于测试。同时初始化玩家碰撞类型对象
     */
    @BeforeEach
    @Override
    void init() {
        //Mock对象用来验证测试中所依赖的对象的交互是否能达到预期。
        this.setPointCalculator(Mockito.mock(PointCalculator.class));
        this.setPlayer(Mockito.mock(Player.class));
        this.setPellet(Mockito.mock(Pellet.class));
        this.setGhost(Mockito.mock(Ghost.class));
        this.setPlayerCollisions(new PlayerCollisions(this.getPointCalculator()));
    }
}
