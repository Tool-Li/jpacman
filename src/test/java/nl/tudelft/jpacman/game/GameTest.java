package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * 用于测试在每个游戏关卡里的不同路径
 */
public class GameTest {
    private Launcher launcher;

    /**
     * 启动游戏
     * @param mapName 地图映射文件的名称
     */
    public void init(String[] mapName) {
        launcher = new Launcher().withMapFile(mapName[0]);
    }

    /**
     * Set a new launcher.
     *设置一个新的启动器
     * @param launcher 被设置好的启动器
     */
    public void setLauncher(Launcher launcher) {
        this.launcher = launcher;
    }

    /**
     * Return the launcher for the test.
     *返回测试的启动器
     * @return  测试的启动器the launcher of the test
     */
    public Launcher getLauncher() {
        return launcher;
    }


    @Test
    @DisplayName("1.测试游戏是否胜利")
    void testWin() {
        String[] map = {"/mapTest.txt"};
        init(map);
        Level.LevelObserver levelObserver = Mockito.mock(Level.LevelObserver.class);


        launcher.launch();
        Game game = launcher.getGame();
        game.getLevel().addObserver(levelObserver);
        assertThat(game.isInProgress()).isFalse();

        game.start();
        Player myPlayer = game.getPlayers().get(0);

        game.move(myPlayer, Direction.EAST);
        game.move(myPlayer, Direction.EAST);
        game.move(myPlayer, Direction.SOUTH);
        game.move(myPlayer, Direction.WEST);
        game.move(myPlayer, Direction.WEST);
        game.move(myPlayer, Direction.SOUTH);
        game.move(myPlayer, Direction.EAST);

        assertThat(myPlayer.isAlive()).isTrue();
        Mockito.verify(levelObserver, Mockito.times(1)).levelWon();
        assertThat(game.isInProgress()).isFalse();

        game.stop();
    }



    @Test
    @DisplayName("2.测试被消灭的小球")
    void testConsumePellet() {
        String[] map = {"/mapTest.txt"};
        init(map);


        launcher.launch();
        final int score = 20;

        Game game = launcher.getGame();
        assertThat(game.isInProgress()).isFalse();

        game.start();
        Player myPlayer = game.getPlayers().get(0);

        game.move(myPlayer, Direction.EAST);
        game.move(myPlayer, Direction.SOUTH);

        assertThat(myPlayer.getScore()).isEqualTo(score);
        assertThat(game.isInProgress()).isTrue();

        game.stop();
    }


    @Test
    @DisplayName("3.测试游戏失败之后魔鬼的碰撞")
    void testLose() {
        String[] map = {"/mapLose.txt"};
        init(map);
        Level.LevelObserver levelObserver = Mockito.mock(Level.LevelObserver.class);

        launcher.launch();
        Game game = launcher.getGame();
        game.getLevel().addObserver(levelObserver);
        assertThat(game.isInProgress()).isFalse();

        game.start();
        Player player = game.getPlayers().get(0);

        game.move(player, Direction.SOUTH);

        assertThat(player.isAlive()).isFalse();
        Mockito.verify(levelObserver, Mockito.times(1)).levelLost();
        assertThat(game.isInProgress()).isFalse();

        game.stop();
    }


    @Test
    @DisplayName("测试下一个方格为空时，游戏状态的改变")
    void testMoveEmpty() {
        String[] map = {"/moveEmpty.txt"};
        init(map);


        launcher.launch();
        Game game = launcher.getGame();
        assertThat(game.isInProgress()).isFalse();

        game.start();
        Player player = game.getPlayers().get(0);

        game.move(player, Direction.EAST);

        assertThat(player.isAlive()).isTrue();
        assertThat(player.getScore()).isEqualTo(0);
        assertThat(game.isInProgress()).isTrue();

        game.stop();
    }


    @Test
    @DisplayName("5.测试是否撞墙了")
    void testMoveWall() {
        String[] map = {"/moveWall.txt"};
        init(map);
        //launcher = new Launcher().withMapFile("/moveWall.txt");

        launcher.launch();
        Game game = launcher.getGame();
        assertThat(game.isInProgress()).isFalse();

        game.start();
        Player player = game.getPlayers().get(0);
        Square square = player.getSquare();

        game.move(player, Direction.NORTH);

        assertThat(player.getSquare()).isEqualTo(square);
        assertThat(player.isAlive()).isTrue();
        assertThat(player.getScore()).isEqualTo(0);
        assertThat(game.isInProgress()).isTrue();

        game.stop();
    }


    @Test
    @DisplayName("6.测试游戏停止之后重新启动游戏")
    void testStopStart() {
        String[] map = {"/sampleMap.txt"};
        init(map);
        //launcher = new Launcher().withMapFile("/sampleMap.txt");

        launcher.launch();
        Game game = launcher.getGame();
        assertThat(game.isInProgress()).isFalse();

        game.start();
        assertThat(game.isInProgress()).isTrue();

        game.stop();
        assertThat(game.isInProgress()).isFalse();

        game.start();
        assertThat(game.isInProgress()).isTrue();

        game.stop();
    }


    @Test
    @DisplayName("7.测试没有启动游戏时，玩家移动方向箭头")
    void testNotStartedMoveWall() {
        String[] map = {"/moveWall.txt"};
        init(map);
        //launcher = new Launcher().withMapFile("/moveWall.txt");
        launcher.launch();

        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);
        Square square = player.getSquare();

        assertThat(game.isInProgress()).isFalse();

        game.move(player, Direction.NORTH);
        assertThat(player.getSquare()).isEqualTo(square);

        assertThat(game.isInProgress()).isFalse();
    }

    @Test
    @DisplayName("测试当游戏未开始时，玩家移动到空的方格")
    void testNotStartedMoveEmpty() {
        String[] map = {"/moveEmpty.txt"};
        init(map);
        //launcher = new Launcher().withMapFile("/moveEmpty.txt");
        launcher.launch();

        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);

        assertThat(game.isInProgress()).isFalse();

        game.move(player, Direction.EAST);
        assertThat(game.isInProgress()).isFalse();
    }

    @Test
    @DisplayName("9.测试当游戏未开始时，玩家朝小球移动")
    void testNotStartedMovePellet() {
        String[] map = {"/mapTest.txt"};
        init(map);
        Level.LevelObserver levelObserver = Mockito.mock(Level.LevelObserver.class);
        //launcher = new Launcher().withMapFile("/mapTest.txt");
        launcher.launch();

        Game game = launcher.getGame();
        game.getLevel().addObserver(levelObserver);
        Player player = game.getPlayers().get(0);

        assertThat(game.isInProgress()).isFalse();

        game.move(player, Direction.EAST);

        assertThat(game.isInProgress()).isFalse();
        assertThat(player.getScore()).isEqualTo(0);
    }

    @Test
    @DisplayName("10.测试当游戏停止后，玩家朝着墙移动")
    void testSuspendMoveWall() {
        String[] map = {"/moveWall.txt"};
        init(map);
        //launcher = new Launcher().withMapFile("/moveWall.txt");
        launcher.launch();

        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);

        game.start();
        assertThat(game.isInProgress()).isTrue();

        game.stop();
        assertThat(game.isInProgress()).isFalse();

        game.move(player, Direction.NORTH);

        assertThat(game.isInProgress()).isFalse();
    }



    @Test
    @DisplayName("11.测试当游戏停止的时候，玩家想移动到空格子处")
    void testSuspendMoveEmpty() {
        String[] map = {"/moveEmpty.txt"};
        init(map);
        //launcher = new Launcher().withMapFile("/moveEmpty.txt");
        launcher.launch();

        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);
        Square square = player.getSquare();

        game.start();
        assertThat(game.isInProgress()).isTrue();

        game.stop();
        assertThat(game.isInProgress()).isFalse();

        game.move(player, Direction.EAST);
        assertThat(player.getSquare()).isEqualTo(square);

        assertThat(game.isInProgress()).isFalse();
    }



    @Test
    @DisplayName("12.测试当游戏开始后玩家又再一次按下start")
    void testInGameStart() {
        String[] map = {"/mapTest.txt"};
        init(map);
        //launcher = new Launcher().withMapFile("/mapTest.txt");
        launcher.launch();

        Game game = launcher.getGame();
        game.start();
        assertThat(game.isInProgress()).isTrue();

        game.start();
        assertThat(game.isInProgress()).isTrue();
    }


    @Test
    @DisplayName("13.测试游戏胜利之后玩家再次按下开始")
    void testWinStart() {
        String[] map = {"/mapLose.txt"};
        init(map);
        Level.LevelObserver levelObserver = Mockito.mock(Level.LevelObserver.class);
        //launcher = new Launcher().withMapFile("/mapLose.txt");
        launcher.launch();

        Game game = launcher.getGame();
        game.getLevel().addObserver(levelObserver);
        game.start();
        Player player = game.getPlayers().get(0);

        game.move(player, Direction.EAST);
        Mockito.verify(levelObserver, Mockito.times(1)).levelWon();

        game.start();
        assertThat(game.isInProgress()).isFalse();
    }

    @Test
    @DisplayName("14.测试当游戏失败后，玩家再次按下开始")
    void testLoseStart() {
        String[] map = {"/mapLose.txt"};
        init(map);
        Level.LevelObserver levelObserver = Mockito.mock(Level.LevelObserver.class);
        //launcher = new Launcher().withMapFile("/mapLose.txt");
        launcher.launch();

        Game game = launcher.getGame();
        game.getLevel().addObserver(levelObserver);
        game.start();
        Player player = game.getPlayers().get(0);

        game.move(player, Direction.SOUTH);
        Mockito.verify(levelObserver, Mockito.times(1)).levelLost();

        game.start();
        assertThat(game.isInProgress()).isFalse();
    }
}
