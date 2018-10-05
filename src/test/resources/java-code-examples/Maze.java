package maze;

public class Room extends MapSite
{
	
}

public class BrownDoor extends Door
{
	public BrownDoor(final Room r1, final Room r2)
	{
		super(r1, r2);
	}
	
	@Override
	public Color getColor()
	{
		return new Color(128, 64, 64);
	}
}

public class BlueMazeFactory extends MazeFactory
{
	@Override
	public Room makeRoom()
	{
		return new GreenRoom(nextRoomNumber());
	}
	
	@Override
	public Wall makeWall()
	{
		return new BlueWall();
	}
	
	@Override
	public Door makeDoor(final Room r1, final Room r2)
	{
		return new BrownDoor(r1, r2);
	}
}