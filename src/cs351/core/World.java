package cs351.core;

/**
 * A World object maintains the current state of all active
 * objects that an Engine is working with. It does not need to
 * deal with collision detection and the Engine will let it know
 * when an object has moved from one location to another.
 */
public interface World
{
  /**
   * Checks to see if the World currently has the given Actor object.
   *
   * @param actor Actor object to check for
   * @return true if it exists in the World and false if not
   */
  boolean contains(Actor actor);

  /**
   * Takes a reference to an Actor object and tries to remove it from
   * the World.
   *
   * @param actor Actor object to remove
   * @throws RuntimeException if the given Actor does not exist in the World
   */
  void remove(Actor actor) throws RuntimeException;

  /**
   * Tries to add an Actor object to the existing World. This should only
   * be used on Actors that can move.
   *
   * @param actor Actor object to add
   */
  void add(Actor actor);

  /**
   * Tries to add a Block (Actor) to the existing World. This is a separate
   * function so that moving objects can be separated from things like walls
   * or other static objects that can collide with the player.
   *
   * @param block Block object to add
   */
  void add(Block block);

  /**
   * Tries to add a Tile (Actor) to the existing World. This is used by a Level
   * to tell the World where all of the floor/ceiling tiles are so that the
   * Engine will know not to check for collisions against them and the Renderer
   * will know to drawn them on the top/bottom of the environment.
   *
   * @param tile Tile object to add
   */
  void add(Tile tile);

  /**
   * Adds a level to the end of the current list of levels.
   *
   * @param level Level to add
   */
  void add(Level level);

  /**
   * This should return the width of the world in terms of pixels.
   *
   * @return pixel world width
   */
  int getWorldPixelWidth();

  /**
   * This should return the width of the world in terms of pixels.
   *
   * @return pixel world height
   */
  int getWorldPixelHeight();

  /**
   * Sets the width and height of the world in terms of pixels. This will probably only
   * ever be called by a Level object.
   *
   * @param pixelWidth width of the world in pixels
   * @param pixelHeight height of the world in pixels
   */
  void setPixelWidthHeight(int pixelWidth, int pixelHeight);

  /**
   * This should return the width of the tiles in terms of pixels.
   *
   * @return width of each tile in pixels
   */
  int getTilePixelWidth();

  /**
   * This should return the height of the tiles in terms of pixels.
   *
   * @return height of each tile in pixels
   */
  int getTilePixelHeight();

  /**
   * Sets the default size of the world tiles in terms of pixels.
   *
   * @param pixelWidth tile width in pixels
   * @param pixelHeight tile height in pixels
   */
  void setTilePixelWidthHeight(int pixelWidth, int pixelHeight);

  /**
   * This is used to figure out which Actor object is the player. The current player object
   * should be set by a Level.
   *
   * @return reference to player
   */
  Actor getPlayer();

  /**
   * Lets the World know which object is serving as the player.
   *
   * @throws RuntimeException if the given Actor does not exist in the World
   */
  void setPlayer(Actor player) throws RuntimeException;

  /**
   * Checks to see if there is another Level that can be loaded. The Engine
   * will call this whenever the previous Level has ended.
   *
   * @return true if there is and false if not
   */
  boolean hasNextLevel();

  /**
   * This function is called when the Engine is first started up and it wants
   * to begin the game as well as each time the previous level has ended.
   *
   * If there is another Level the World should load it and then tell the
   * next Level to initialize the starting state of the game.
   *
   * @param engine reference to the Engine object which is calling this function -
   *               can be used for callbacks, especially to tell the Engine about
   *               each new Actor that is being added so it can start building
   *               separate lists to improve its performance/the renderer's performance
   */
  void nextLevel(Engine engine);
}