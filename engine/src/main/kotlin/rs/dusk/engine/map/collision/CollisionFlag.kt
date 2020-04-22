package rs.dusk.engine.map.collision

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since April 16, 2020
 */
object CollisionFlag {
    const val FLOOR = 0x200000
    const val FLOOR_DECO = 0x40000
    const val WALL = 0x80000

    const val LAND = 0x100
    const val SKY = LAND shl 9
    const val SEA = LAND shl 22

    const val BLOCKED = LAND or FLOOR or FLOOR_DECO

    const val NORTH_WEST = 0x1
    const val NORTH = 0x2
    const val NORTH_EAST = 0x4
    const val EAST = 0x8
    const val SOUTH_EAST = 0x10
    const val SOUTH = 0x20
    const val SOUTH_WEST = 0x40
    const val WEST = 0x80

    const val NORTH_OR_WEST = NORTH or WEST
    const val NORTH_OR_EAST = NORTH or EAST
    const val SOUTH_OR_EAST = SOUTH or EAST
    const val SOUTH_OR_WEST = SOUTH or WEST

    const val NORTH_AND_WEST = NORTH_OR_WEST or NORTH_WEST
    const val NORTH_AND_EAST = NORTH_OR_EAST or NORTH_EAST
    const val SOUTH_AND_EAST = SOUTH_OR_EAST or SOUTH_EAST
    const val SOUTH_AND_WEST = SOUTH_OR_WEST or SOUTH_WEST

    const val NOT_NORTH = SOUTH or EAST or WEST or SOUTH_EAST or SOUTH_WEST
    const val NOT_EAST = NORTH or SOUTH or WEST or NORTH_WEST or SOUTH_WEST
    const val NOT_SOUTH = NORTH or EAST or WEST or NORTH_EAST or NORTH_WEST
    const val NOT_WEST = NORTH or SOUTH or EAST or NORTH_EAST or SOUTH_EAST


    const val LAND_BLOCK_NORTH_WEST = NORTH_AND_WEST or BLOCKED
    const val LAND_BLOCK_NORTH = NORTH or BLOCKED
    const val LAND_BLOCK_NORTH_EAST = NORTH_AND_EAST or BLOCKED
    const val LAND_BLOCK_EAST = EAST or BLOCKED
    const val LAND_BLOCK_SOUTH_EAST = SOUTH_AND_EAST or BLOCKED
    const val LAND_BLOCK_SOUTH = SOUTH or BLOCKED
    const val LAND_BLOCK_SOUTH_WEST = SOUTH_AND_WEST or BLOCKED
    const val LAND_BLOCK_WEST = WEST or BLOCKED

    const val LAND_WALL_NORTH_WEST = NORTH_AND_WEST or WALL or BLOCKED
    const val LAND_WALL_NORTH = NORTH or WALL or BLOCKED
    const val LAND_WALL_NORTH_EAST = NORTH_AND_EAST or WALL or BLOCKED
    const val LAND_WALL_EAST = EAST or WALL or BLOCKED
    const val LAND_WALL_SOUTH_EAST = SOUTH_AND_EAST or WALL or BLOCKED
    const val LAND_WALL_SOUTH = SOUTH or WALL or BLOCKED
    const val LAND_WALL_SOUTH_WEST = SOUTH_AND_WEST or WALL or BLOCKED
    const val LAND_WALL_WEST = WEST or WALL or BLOCKED

    const val LAND_CLEAR_NORTH_WEST = SOUTH_AND_EAST or BLOCKED
    const val LAND_CLEAR_NORTH = NOT_NORTH or BLOCKED
    const val LAND_CLEAR_NORTH_EAST = SOUTH_AND_WEST or BLOCKED
    const val LAND_CLEAR_EAST = NOT_EAST or BLOCKED
    const val LAND_CLEAR_SOUTH_EAST = NORTH_AND_WEST or BLOCKED
    const val LAND_CLEAR_SOUTH = NOT_SOUTH or BLOCKED
    const val LAND_CLEAR_SOUTH_WEST = NORTH_AND_EAST or BLOCKED
    const val LAND_CLEAR_WEST = NOT_WEST or BLOCKED


    const val SKY_BLOCK_NORTH_WEST = NORTH_AND_WEST shl 9 or BLOCKED
    const val SKY_BLOCK_NORTH = NORTH shl 9 or BLOCKED
    const val SKY_BLOCK_NORTH_EAST = NORTH_AND_EAST shl 9 or BLOCKED
    const val SKY_BLOCK_EAST = EAST shl 9 or BLOCKED
    const val SKY_BLOCK_SOUTH_EAST = SOUTH_AND_EAST shl 9 or BLOCKED
    const val SKY_BLOCK_SOUTH = SOUTH shl 9 or BLOCKED
    const val SKY_BLOCK_SOUTH_WEST = SOUTH_AND_WEST shl 9 or BLOCKED
    const val SKY_BLOCK_WEST = WEST shl 9 or BLOCKED

    const val SKY_WALL_NORTH_WEST = NORTH_AND_WEST or WALL shl 9 or BLOCKED
    const val SKY_WALL_NORTH = NORTH or WALL shl 9 or BLOCKED
    const val SKY_WALL_NORTH_EAST = NORTH_AND_EAST or WALL shl 9 or BLOCKED
    const val SKY_WALL_EAST = EAST or WALL shl 9 or BLOCKED
    const val SKY_WALL_SOUTH_EAST = SOUTH_AND_EAST or WALL shl 9 or BLOCKED
    const val SKY_WALL_SOUTH = SOUTH or WALL shl 9 or BLOCKED
    const val SKY_WALL_SOUTH_WEST = SOUTH_AND_WEST or WALL shl 9 or BLOCKED
    const val SKY_WALL_WEST = WEST or WALL shl 9 or BLOCKED

    const val SKY_CLEAR_NORTH_WEST = SOUTH_AND_EAST shl 9 or BLOCKED
    const val SKY_CLEAR_NORTH = NOT_NORTH shl 9 or BLOCKED
    const val SKY_CLEAR_NORTH_EAST = SOUTH_AND_WEST shl 9 or BLOCKED
    const val SKY_CLEAR_EAST = NOT_EAST shl 9 or BLOCKED
    const val SKY_CLEAR_SOUTH_EAST = NORTH_AND_WEST shl 9 or BLOCKED
    const val SKY_CLEAR_SOUTH = NOT_SOUTH shl 9 or BLOCKED
    const val SKY_CLEAR_SOUTH_WEST = NORTH_AND_EAST shl 9 or BLOCKED
    const val SKY_CLEAR_WEST = NOT_WEST shl 9 or BLOCKED


    const val SEA_BLOCK_NORTH_WEST = NORTH_AND_WEST shl 22 or BLOCKED
    const val SEA_BLOCK_NORTH = NORTH shl 22 or BLOCKED
    const val SEA_BLOCK_NORTH_EAST = NORTH_AND_EAST shl 22 or BLOCKED
    const val SEA_BLOCK_EAST = EAST shl 22 or BLOCKED
    const val SEA_BLOCK_SOUTH_EAST = SOUTH_AND_EAST shl 22 or BLOCKED
    const val SEA_BLOCK_SOUTH = SOUTH shl 22 or BLOCKED
    const val SEA_BLOCK_SOUTH_WEST = SOUTH_AND_WEST shl 22 or BLOCKED
    const val SEA_BLOCK_WEST = WEST shl 22 or BLOCKED

    const val SEA_WALL_NORTH_WEST = NORTH_AND_WEST or WALL shl 22 or BLOCKED
    const val SEA_WALL_NORTH = NORTH or WALL shl 22 or BLOCKED
    const val SEA_WALL_NORTH_EAST = NORTH_AND_EAST or WALL shl 22 or BLOCKED
    const val SEA_WALL_EAST = EAST or WALL shl 22 or BLOCKED
    const val SEA_WALL_SOUTH_EAST = SOUTH_AND_EAST or WALL shl 22 or BLOCKED
    const val SEA_WALL_SOUTH = SOUTH or WALL shl 22 or BLOCKED
    const val SEA_WALL_SOUTH_WEST = SOUTH_AND_WEST or WALL shl 22 or BLOCKED
    const val SEA_WALL_WEST = WEST or WALL shl 22 or BLOCKED

    const val SEA_CLEAR_NORTH_WEST = SOUTH_AND_EAST shl 22 or BLOCKED
    const val SEA_CLEAR_NORTH = NOT_NORTH shl 22 or BLOCKED
    const val SEA_CLEAR_NORTH_EAST = SOUTH_AND_WEST shl 22 or BLOCKED
    const val SEA_CLEAR_EAST = NOT_EAST shl 22 or BLOCKED
    const val SEA_CLEAR_SOUTH_EAST = NORTH_AND_WEST shl 22 or BLOCKED
    const val SEA_CLEAR_SOUTH = NOT_SOUTH shl 22 or BLOCKED
    const val SEA_CLEAR_SOUTH_WEST = NORTH_AND_EAST shl 22 or BLOCKED
    const val SEA_CLEAR_WEST = NOT_WEST shl 22 or BLOCKED
}