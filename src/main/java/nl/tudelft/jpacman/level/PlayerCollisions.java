package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.points.PointCalculator;

/**
 * A simple implementation of a collision map for the JPacman player.
 *•*为JPacman玩家实现了一个碰撞地图的简单实现。
 * <p>
 * It uses a number of instanceof checks to implement the multiple dispatch for the 
 * collisionmap. For more realistic collision maps, this approach will not scale,
 * and the recommended approach is to use a {@link CollisionInteractionMap}.
 *它使用大量的instanceof检查来实现碰撞映射的多分派。对于更真实的碰撞地图，这种方法不会缩放，推荐的方法是使用{link CollisionInteractionMap}。
 * @author Arie van Deursen, 2014
 *
 */
//继承碰撞类
public class PlayerCollisions implements CollisionMap {

    private PointCalculator pointCalculator;

    /**
     * Create a simple player-based collision map, informing the
     * point calculator about points to be added.
     * 创建一个简单的基于玩家的碰撞地图，告知分数计算器需要添加的分数。
     *
     * @param pointCalculator
     *             Strategy for calculating points.
     *             分数计算策略
     */
    public PlayerCollisions(PointCalculator pointCalculator) {
        this.pointCalculator = pointCalculator;
    }

    @Override
    public void collide(Unit mover, Unit collidedOn) {
        //instanceof判断移动的对象是什么
        if (mover instanceof Player) {
            playerColliding((Player) mover, collidedOn);
        }
        else if (mover instanceof Ghost) {
            ghostColliding((Ghost) mover, collidedOn);
        }
        //小球
        else if (mover instanceof Pellet) {
            pelletColliding((Pellet) mover, collidedOn);
        }
    }
    //玩家碰撞
    private void playerColliding(Player player, Unit collidedOn) {
        if (collidedOn instanceof Ghost) {
            playerVersusGhost(player, (Ghost) collidedOn);
        }
        if (collidedOn instanceof Pellet) {
            playerVersusPellet(player, (Pellet) collidedOn);
        }
    }
    //魔鬼碰撞
    private void ghostColliding(Ghost ghost, Unit collidedOn) {
        if (collidedOn instanceof Player) {
            playerVersusGhost((Player) collidedOn, ghost);
        }
    }
    //小球碰撞
    private void pelletColliding(Pellet pellet, Unit collidedOn) {
        if (collidedOn instanceof Player) {
            playerVersusPellet((Player) collidedOn, pellet);
        }
    }


    /**
     * Actual case of player bumping into ghost or vice versa.
     *玩家撞上幽灵或反之亦然。
     * @param player
     *          The player involved in the collision.卷入碰撞的玩家
     * @param ghost
     *          The ghost involved in the collision.卷入碰撞的魔鬼
     */
    public void playerVersusGhost(Player player, Ghost ghost) {
        pointCalculator.collidedWithAGhost(player, ghost);
        player.setAlive(false);
        player.setKiller(ghost);
    }

    /**
     * Actual case of player consuming a pellet.
     * 玩家消灭小球的真实用例。
     *
     * @param player
     *           The player involved in the collision.包含在碰撞中的玩家
     * @param pellet
     *           The pellet involved in the collision.包含在碰撞中的小球
     */
    public void playerVersusPellet(Player player, Pellet pellet) {
        pointCalculator.consumedAPellet(player, pellet);
        pellet.leaveSquare();
    }

}
