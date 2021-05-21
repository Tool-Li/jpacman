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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * 测试Inky类中nextAiMove() 方法，
 * 测试了五种情况：
 *1.当Blinky不存在时，
 *2.Inky与player之间没有路径。
 *3.玩家不存在棋盘上，BI
 *4.inky和binky对象在吃豆人附近，PBI
 *5.当 BPI三个对象都存在，inky对象的移动
 */
public class InkyTest {

    private static final PacManSprites SPRITES = new PacManSprites();
    private LevelFactory levelFactory;
    private GhostFactory ghostFactory;
    private PlayerFactory playerFactory;
    private BoardFactory boardFactory;
    private GhostMapParser ghostMapParser;
    private PointCalculator calculator;

    /**
     * 初始化游戏
     */
    @BeforeEach
    void init() {
        calculator = new DefaultPointCalculator();
        playerFactory = new PlayerFactory(SPRITES);
        boardFactory = new BoardFactory(SPRITES);
        ghostFactory = new GhostFactory(SPRITES);
        levelFactory = new LevelFactory(SPRITES, ghostFactory, calculator);
        ghostMapParser = new GhostMapParser(levelFactory, boardFactory, ghostFactory);
    }


    /**
     * 没有Blinky对象
     */
    @Test
    @DisplayName("1.当Blinky不存在时，")
    void testNoBlinky() {
        //解析地图，产生npc，B不存在
        Level level = ghostMapParser.parseMap(
            Lists.newArrayList("PI ", "   ", "   ")
        );
        Player player = playerFactory.createPacMan(); //产生对象
        player.setDirection(Direction.WEST);//设置初始化方向
        level.registerPlayer(player);//将玩家注册到地图
        //在棋盘中寻找inky对象
        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        //断言检测，空行为
        assertThat(inky.nextAiMove()).isEqualTo(Optional.empty());
    }

    /**
     * Inky与player之间没有路径。
     */
    @Test
    @DisplayName("2.Inky与player之间没有路径。")
    void testNoPath() {
        //地图解析，I，P之间无路径
        List<String> grid = new ArrayList<>();
        grid.add("#######################");
        grid.add("#.........P#......I..B#");
        grid.add("#######################");

        Level level = ghostMapParser.parseMap(grid);//在地图上显示前面定义添加的路径格子
        Player player = playerFactory.createPacMan();//构造玩家
        player.setDirection(Direction.WEST);//设置初始方向
        level.registerPlayer(player); //注册玩家到地图
        //寻找inky对象
        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        //断言检测，两者之间没有路径，应匹配空行为
        assertThat(inky.nextAiMove()).isEqualTo(Optional.empty());

    }


    /**
     * 棋盘上不存在player
     */
    @Test
    @DisplayName("3.玩家不存在棋盘上，BI")
    void testNoPlayer() {
        //解析地图，不存在p
        Level level = ghostMapParser.parseMap(
            Lists.newArrayList("####", "B  I", "####")
        );
        //在棋盘中找到inky对象，并判断棋盘中是否有玩家
        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        //断言检测，无玩家，此时空行为
        assertThat(inky.nextAiMove()).isEqualTo(Optional.empty());
    }


    /**
     * Inky跟着吃豆人，Blinky在吃豆人后面。
     */
    @Test
    @DisplayName("4.inky和binky对象在吃豆人附近，PBI")
    void testPlayerClose() {
        //解析地图，产生地图,npc,PBI
        List<String> grid = new ArrayList<>();
        grid.add("#######################");
        grid.add("#          P    B   I #");
        grid.add("#######################");

        Level level = ghostMapParser.parseMap(grid);//画格子
        Player player = playerFactory.createPacMan();//构造吃豆人
        player.setDirection(Direction.EAST);//初始化方向
        level.registerPlayer(player);//将玩家注册到地图
        //定义inky对象并判断
        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        //断言检测inky对象和预期方向是否相同。
        assertThat(inky.nextAiMove()).isEqualTo(Optional.of(Direction.WEST));
    }


    /**
     * 当Inky对象在吃豆人面前时，它会远离。
     */
    @Test
    @DisplayName("5.当BPI三个对象都存在,inky对象的移动")
    void testInkyMovesAway() {
        //解析地图，此时BPI三个对象都在。
        List<String> grid = new ArrayList<>();
        grid.add("#######################");
        grid.add("#      B P  I         #");
        grid.add("#######################");

        Level level = ghostMapParser.parseMap(grid);
        Player player = playerFactory.createPacMan();
        player.setDirection(Direction.EAST);//初始方向
        level.registerPlayer(player);
        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard()); //传送数据回去判断
        //断言检测，用例预期是否和实际方向相同
        assertThat(inky.nextAiMove()).isEqualTo(Optional.of(Direction.EAST));

    }
}
