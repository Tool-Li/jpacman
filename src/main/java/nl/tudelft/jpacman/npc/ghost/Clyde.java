package nl.tudelft.jpacman.npc.ghost;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.sprite.Sprite;

/**
 * <p>
 * An implementation of the classic Pac-Man ghost Clyde.
 * 经典的《吃豆人》幽灵Clyde的实现。
 * </p>
 * <p>
 * Pokey needs a new nickname because out of all the ghosts,
 *  Pokey需要一个新绰号，因为在所有的鬼魂中，
 * Clyde is the least likely to "C'lyde" with Pac-Man.
 * Clyde是《吃豆人》中最不可能出现的“C’lyde”。
 *
 * Clyde is always the last
 * ghost out of the regenerator, and the loner of the gang, usually off doing
 * his own thing when not patrolling the bottom-left corner of the maze. His
 * behavior is very random, so while he's not likely to be following you in hot
 * pursuit with the other ghosts, he is a little less predictable, and still a
 * danger.
 * 克莱德总是扎斯特鬼出再生器，以及团伙中的孤独者，
 * 通常在不巡逻迷宫左下角的时候做自己的事情。他的行为是非常随机的，
 * 所以虽然他不太可能跟在你后面跟其他鬼，但他有点难以预测，仍然是一个危险。
 * </p>
 * <p>
 * <b>AI:</b> Clyde has two basic AIs, one for when he's far from Pac-Man, and
 * one for when he is near to Pac-Man. 一个远离豆子一个接近豆子
 * When Clyde is far away from Pac-Man (beyond eight grid spaces),
 * Clyde behaves very much like Blinky, trying to move to Pac-Man's exact
 * location. However, when Clyde gets within eight grid spaces of Pac-Man, he
 * automatically changes his behavior and runs away.
 * </p>
 * <p>
 * Source: http://strategywiki.org/wiki/Pac-Man/Getting_Started
 * </p>
 *
 * @author Jeroen Roosen
 */
public class Clyde extends Ghost {

    /**
     * The amount of cells Clyde wants to stay away from Pac Man.
     */
    private static final int SHYNESS = 8;

    /**
     * The variation in intervals, this makes the ghosts look more dynamic and
     * less predictable.
     */
    private static final int INTERVAL_VARIATION = 50;

    /**
     * The base movement interval.
     */
    private static final int MOVE_INTERVAL = 250;

    /**
     * A map of opposite directions.
     */
    private static final Map<Direction, Direction> OPPOSITES = new EnumMap<>(Direction.class);
    static {
        OPPOSITES.put(Direction.NORTH, Direction.SOUTH);
        OPPOSITES.put(Direction.SOUTH, Direction.NORTH);
        OPPOSITES.put(Direction.WEST, Direction.EAST);
        OPPOSITES.put(Direction.EAST, Direction.WEST);
    }

    /**
     * Creates a new "Clyde", a.k.a. "Pokey".
     *
     * @param spriteMap The sprites for this ghost.
     */
    public Clyde(Map<Direction, Sprite> spriteMap) {
        super(spriteMap, MOVE_INTERVAL, INTERVAL_VARIATION);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * Clyde has two basic AIs, one for when he's far from Pac-Man, and one for
     * when he is near to Pac-Man. 
     * When Clyde is far away from Pac-Man (beyond eight grid spaces),
     * Clyde behaves very much like Blinky, trying to move to Pac-Man's exact
     * location. However, when Clyde gets within eight grid spaces of Pac-Man,
     * he automatically changes his behavior and runs away
     * </p>
     */
    @Override

    //nextAiMove()方法，考虑测试用例以确保Clyde按预期行为工作。
    public Optional<Direction> nextAiMove() {
        assert hasSquare();

        Unit nearest = Navigation.findNearest(Player.class, getSquare());
        if (nearest == null) {
            return Optional.empty(); //无player对象
        }
        assert nearest.hasSquare();
        Square target = nearest.getSquare();

        List<Direction> path = Navigation.shortestPath(getSquare(), target, this);
        if (path != null && !path.isEmpty()) {
            Direction direction = path.get(0);
            if (path.size() <= SHYNESS) {
                return Optional.ofNullable(OPPOSITES.get(direction));
            }
            return Optional.of(direction); //距离大于8
        }
        return Optional.empty();//没有路径
    }
}
