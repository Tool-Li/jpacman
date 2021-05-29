package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.points.PointCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * 用于实现CollisionMap接口的类的测试。
 * 定义了抽象类CollisionMapTest，它的子类是PlayerCollisionsTest。
 * 和DefaultPlayerInteractionMapTest。方便两个子类调用。
 */
@SuppressWarnings("checkstyle:Indentation")
public abstract class CollisionMapTest {
    private PointCalculator pointCalculator; //分数计算器
    private Player player;
    private Pellet pellet;
    private Ghost ghost;
    private CollisionMap playerCollisions; //collsionMap是一个接口


    /**
     *分数计算器构造函数。
     */
    public void setPointCalculator(PointCalculator pointCalculator) {
        this.pointCalculator = pointCalculator;
    }


    /**
     * 设置玩家构造函数
     */
    public void setPlayer(Player player) {
        this.player = player;
    }


    /**
     * 设置小球构造函数
     */
    public void setPellet(Pellet pellet) {
        this.pellet = pellet;
    }


    /**
     * 设置魔鬼构造函数
     */
    public void setGhost(Ghost ghost) {
        this.ghost = ghost;
    }


    /**
     * 设置玩家类型碰撞构造函数
     */
    public void setPlayerCollisions(CollisionMap playerCollisions) {
        this.playerCollisions = playerCollisions;
    }


    /**
     * 得到对象的计算器分数
     */
    public PointCalculator getPointCalculator() {
        return pointCalculator;
    }


    /**
     * 测试用例参数数据初始化
     */
    @BeforeEach
    abstract void init();



    /**
     *测试中碰撞方法的第一个参数是一个玩家，第二个也是一个玩家。
     */
    @Test
    @DisplayName("1.测试中碰撞方法的第一个参数是一个玩家，第二个也是一个玩家。结果什么都没发生")
    void testPlayerPlayer() {
        Player player1 = Mockito.mock(Player.class);
        playerCollisions.collide(player, player1);
       // Mockito中verify(…).methodXxx(…)验证methodXxx方法是否按预期进行调用
        Mockito.verifyZeroInteractions(player, player1);
    }



    /**
     * 测试碰撞方法中第一个参数是玩家，第二个是小球
     */
    @Test
    @DisplayName("2.测试碰撞方法中第一个参数是玩家，第二个是小球。")
    void testPlayerPellet() {
        playerCollisions.collide(player, pellet);

        Mockito.verify(pointCalculator, Mockito.times(1)).consumedAPellet(
            Mockito.eq(player),
            Mockito.eq(pellet)
        );

        Mockito.verify(pellet, Mockito.times(1)).leaveSquare();

        Mockito.verifyNoMoreInteractions(player, pellet);
    }


    /**
     * 测试碰撞方法中第一个参数是玩家，第二个是魔鬼
     */
    @Test
    @DisplayName("3.测试碰撞方法中第一个参数是玩家，第二个是魔鬼。")
    void testPlayerGhost() {
        playerCollisions.collide(player, ghost);

        Mockito.verify(pointCalculator, Mockito.times(1)).collidedWithAGhost(
            Mockito.eq(player),
            Mockito.eq(ghost)
        );

        Mockito.verify(player, Mockito.times(1)).setAlive(false);

        Mockito.verify(player, Mockito.times(1)).setKiller(Mockito.eq(ghost));

        Mockito.verifyNoMoreInteractions(player, ghost);
    }


    /**
     * 测试碰撞方法中第一个参数是魔鬼，第二个参数是玩家
     */
    @Test
    @DisplayName("4.测试碰撞方法中第一个参数是魔鬼，第二个参数是玩家。")
    void testGhostPlayer() {
        playerCollisions.collide(ghost, player);

        Mockito.verify(pointCalculator, Mockito.times(1)).collidedWithAGhost(
            Mockito.eq(player),
            Mockito.eq(ghost)
        );

        Mockito.verify(player, Mockito.times(1)).setAlive(false);

        Mockito.verify(player, Mockito.times(1)).setKiller(Mockito.eq(ghost));

        Mockito.verifyNoMoreInteractions(player, ghost);
    }


    /**
     * 测试碰撞方法中第一个参数是魔鬼，第二个参数是小球
     */
    @Test
    @DisplayName("5.测试碰撞方法中第一个参数是魔鬼，第二个参数是小球.")
    void testGhostPellet() {
        playerCollisions.collide(ghost, pellet);

        Mockito.verifyZeroInteractions(ghost, pellet);
    }


    /**
     * 测试碰撞方法中第一个参数是魔鬼，第二个参数也是魔鬼，结果什么都没发生
     */
    @Test
    @DisplayName("6.测试碰撞方法中第一个参数是魔鬼，第二个参数也是魔鬼")
    void testGhostGhost() {
        Ghost ghost1 = Mockito.mock(Ghost.class);
        playerCollisions.collide(ghost, ghost1);
        //查询没有交互的mock对象 用来确认mock2对象没有进行任何交互，
        // 但mock2执行了get(0)方法，所以这里测试会报错。
        // 由于它和verifyNoMoreInteractions()方法实现的源码都一样，
        // 因此如果在verifyZeroInteractions(mock2)执行之前对mock.get(0)进行了验证那么测试将会通过。
        Mockito.verifyZeroInteractions(ghost, ghost1);
    }


    /**
     * 测试碰撞方法中第一个参数是小球，第二参数是玩家。
     */
    @Test
    @DisplayName("7.测试碰撞方法中第一个参数是小球，第二参数是玩家")
    void testPelletPlayer() {
        playerCollisions.collide(pellet, player);

        Mockito.verify(pointCalculator, Mockito.times(1)).consumedAPellet(
            Mockito.eq(player),
            Mockito.eq(pellet)
        );

        Mockito.verify(pellet, Mockito.times(1)).leaveSquare();

        Mockito.verifyNoMoreInteractions(pellet, player);
    }


    /**
     * 测试碰撞方法中第一个参数是小球，第二个参数是魔鬼
     */
    @Test
    @DisplayName("8. 测试碰撞方法中第一个参数是小球，第二个参数是魔鬼")
    void testPelletGhost() {
        playerCollisions.collide(pellet, ghost);

        Mockito.verifyZeroInteractions(pellet, ghost);
    }


    /**
     * 测试碰撞方法中第一个参数是小球，第二个参数也是小球
     */
    @Test
    @DisplayName("9.测试碰撞方法中第一个参数是小球，第二个参数也是小球，结果什么都没发生")
    void testPelletPellet() {
        Pellet pellet1 = Mockito.mock(Pellet.class);
        playerCollisions.collide(pellet, pellet1);

        Mockito.verifyZeroInteractions(pellet, pellet1);
    }
}
