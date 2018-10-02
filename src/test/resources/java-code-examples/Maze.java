public class MazeFactory
{
        private int count = 0;

        protected final int nextRoomNumber()
        {
                return count++;
        }

        public Maze makeMaze()
        {
                return new Maze();
        }

        public Room makeRoom()
        {
                return new Room(nextRoomNumber());
        }

        public Wall makeWall()
        {
                return new Wall();
        }

        public Door makeDoor(final Room r1, final Room r2)
        {
                return new Door(r1, r2);
        }
}
