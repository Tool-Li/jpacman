package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.npc.ghost.Navigation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GameTest {
    private Launcher launcher = new Launcher();

    @Test
    @DisplayName("游戏开始")
    void startGame() {
        launcher.withMapFile("/board.txt");
        launcher.launch();
        launcher.getGame().start();
        assertThat(launcher.getGame().isInProgress()).isTrue();
    }

    @Test
    @DisplayName("暂停游戏")
    void pauseGame() {
        launcher.withMapFile("/board.txt");
        launcher.launch();
        launcher.getGame().start();
        assertThat(launcher.getGame().isInProgress()).isTrue();
        //模拟点击暂停
        launcher.getGame().stop();
        assertThat(launcher.getGame().isInProgress()).isFalse();
    }

    @Test
    @DisplayName("继续游戏")
    void continueGame() {
        launcher.withMapFile("/board.txt");
        launcher.launch();
        launcher.getGame().start();
        assertThat(launcher.getGame().isInProgress()).isTrue();
        launcher.getGame().stop();
        assertThat(launcher.getGame().isInProgress()).isFalse();
        launcher.getGame().start();
        assertThat(launcher.getGame().isInProgress()).isTrue();
    }

    @Test
    @DisplayName("游戏胜利")
    void gameWin(){
        launcher.withMapFile("/win.txt");
        launcher.launch();
        launcher.getGame().start();
        assertThat(launcher.getGame().isInProgress()).isTrue();
        Player p = Navigation.findUnitInBoard(Player.class,launcher.getGame().getLevel().getBoard());
        launcher.getGame().getLevel().move(p, Direction.WEST);
        assertThat(launcher.getGame().isInProgress()).isFalse();
    }

    @Test
    @DisplayName("游戏失败")
    void gameLose(){
        launcher.withMapFile("/mapLose.txt");
        launcher.launch();
        //模拟点击开始
        launcher.getGame().start();
        assertThat(launcher.getGame().isInProgress()).isTrue();
        Player p = Navigation.findUnitInBoard(Player.class,launcher.getGame().getLevel().getBoard());
        launcher.getGame().getLevel().move(p, Direction.WEST);
        assertThat(launcher.getGame().isInProgress()).isFalse();
    }

}
