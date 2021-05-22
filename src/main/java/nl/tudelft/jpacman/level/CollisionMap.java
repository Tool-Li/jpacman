package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.Unit;

/**
 * A table containing all (relevant) collisions between different types of
 * units.
 *
 * @author Jeroen Roosen 
 */
public interface CollisionMap {

    /**
     * Collides the two units and handles the result of the collision, which may
     * be nothing at all.
     *
     * @param <C1>
     *            The collider type.碰撞类型
     * @param <C2>
     *            The collidee (unit that was moved into) type.碰撞（被移动进去）类型
     *
     * @param collider
     *            The unit that causes the collision by occupying a square with
     *            another unit already on it.
     *            通过占用一个已经在上面的另一个单位而引起碰撞的单位。
     * @param collidee
     *            The unit that is already on the square that is being invaded.
     *            对象已经在被占领的方格上
     */
    <C1 extends Unit, C2 extends Unit> void collide(C1 collider, C2 collidee);

}
