package src;
public interface Room {
	public int getCost();
	public boolean book(); //The way I see it is that all the business with "booking, changing, and checking out" can be represented at a higher level than the room itself.
	public boolean unbook(); //Most likely we will have a helper class do all that for us. So, the room only knows when someone is or is not in it. Something something "information expert".
}
