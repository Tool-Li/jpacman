package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.points.DefaultPointCalculator;
import nl.tudelft.jpacman.points.PointCalculator;
import nl.tudelft.jpacman.sprite.PacManSprites;
import static org.assertj.core.api.Assertions.assertThat;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

/**
 * 测试Clyde类中的nextAiMove()方法的类。
 * 共实现了四种情况的测试：
 * 1. Clyde位于吃豆人附近(少于8个方格的距离)
 * 2 .Clyde距离吃豆人较远(大于8个方格的距离)
 * 3.没有玩家当前关卡。
 * 4. 在吃豆人和Clyde之间没有有效的路径
 */
public class ClydeTest {

    private PacManSprites pacManSprites;
    private BoardFactory boardFactory;
    private GhostFactory ghostFactory;
    private PointCalculator pointCalculator;
    private LevelFactory levelFactory;
    private GhostMapParser ghostMapParser;
    private PlayerFactory playerFactory;

    /**
     * 实例化GhostMapParser，方便进行测试。
     */
    @BeforeEach
    void init() {
        pacManSprites = new PacManSprites(); //用于角色显示
        boardFactory = new BoardFactory(pacManSprites);//棋盘场景显示
        ghostFactory = new GhostFactory(pacManSprites);//生成ghost对象。
        pointCalculator = new DefaultPointCalculator();//构造器
        levelFactory = new LevelFactory(pacManSprites, ghostFactory, pointCalculator);
        playerFactory = new PlayerFactory(pacManSprites);//生成Player对象。
        ghostMapParser = new GhostMapParser(levelFactory, boardFactory, ghostFactory);//对象实例化
    }


    /**
     * 在测试中，Clyde位于吃豆人附近(在8个方格的距离上)，所以它会试图逃跑。
     */
    @Test
    @DisplayName("1. Clyde位于吃豆人附近(少于8个方格的距离)")
    void testClose() {
        //解析地图，并产生对应种类的npc
        Level level = ghostMapParser.parseMap(
            Lists.newArrayList("############", "P       C###", "############")
        );
        Player player = playerFactory.createPacMan(); //创建player
        player.setDirection(Direction.WEST); //player的初始行走方向，西边。
        level.registerPlayer(player);//将player注册到地图中
        //调用Navigation函数中的方法找到clyde对象
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        //断言检测实际行为方向与预测方向是否相同
        assertThat(clyde.nextAiMove()).isEqualTo(Optional.of(Direction.EAST));
    }


    /**
     * 测试中Clyde距离吃豆人较远(大于8个方格的距离)，所以它会向吃豆人靠近。
     */
    @Test
    @DisplayName("2 .Clyde距离吃豆人较远(大于8个方格的距离)")
    void testFar() {
        //解析地图，并产生对应种类的npc
        Level level = ghostMapParser.parseMap(
            Lists.newArrayList("############", "P        C##", "############")
        );
        Player player = playerFactory.createPacMan();//创建player
        player.setDirection(Direction.EAST);//设置初始方向
        level.registerPlayer(player);//注册玩家到地图
        //寻找clyde对象
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        //断言检测
        assertThat(clyde.nextAiMove()).isEqualTo(Optional.of(Direction.WEST));
    }

    /**
     * 没有玩家在玩当前关卡。
     */
    @Test
    @DisplayName("3.没有玩家当前关卡。")
    void testNoPlayer() {
        //解析地图，产生npc
        Level level = ghostMapParser.parseMap(
            Lists.newArrayList("#####", "##C  ", "     ")
        );
        //在棋盘中找到clyde对象
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        //断言检测是否有行为。（行为应该为空）
        assertThat(clyde.nextAiMove()).isEqualTo(Optional.empty());
    }


    /**
     * 在吃豆人和Clyde之间没有有效的路径.
     */
    @Test
    @DisplayName("4. 在吃豆人和Clyde之间没有有效的路径.")
    void testNoPath() {
        //解析地图，产生npc,#阻挡了p和c之间的路径
        Level level = ghostMapParser.parseMap(
            Lists.newArrayList("######", "#P##C ", " ###  ")
        );
        Player player = playerFactory.createPacMan();//创建玩家
        player.setDirection(Direction.EAST);//设置初始行走方向
        level.registerPlayer(player);//注册
        //寻找clyde对象
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        //断言检测（行为应该是错的，没有路径）
        assertThat(clyde.nextAiMove()).isEqualTo(Optional.empty());
    }
}
