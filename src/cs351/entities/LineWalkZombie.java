package cs351.entities;

import java.util.Random;

import cs351.core.Actor;
import cs351.core.Engine;
import cs351.core.GlobalConstants;
import cs351.core.Vector3;
import javafx.geometry.Point2D;


public class LineWalkZombie extends Zombie {
  //initialize to something we set
  private double elapsedSeconds=0;
  private Random rand = new Random();
  private double xDirection = 0.5;
  private double yDirection = 0.5;

  public LineWalkZombie(String textureFile, double x, double y, int width, int height, int depth)
  {
    super(textureFile, x, y, width, height, depth);
  }

  public LineWalkZombie(String textureFile, String modelFile, double x, double y, int width, int height, int depth)
  {
    super(textureFile, modelFile, x, y, width, height, depth);
  }

  public UpdateResult update(Engine engine, double deltaSeconds)
  { 

    //   System.out.println("--fps: "+1/deltaSeconds);
    double zombieSpeed = Double.parseDouble(engine.getSettings().getValue("zombie_speed"));
    // totalSpeed represents the movement speed offset in tiles per second
    elapsedSeconds += deltaSeconds;

    // every zombieDecisionRate seconds, switch direction
    if (elapsedSeconds > GlobalConstants.zombieDecisionRate)
    {


      elapsedSeconds = 0.0;
      if (!canSmellPlayer(engine)  && setNewDirection)
      {

        setNewDirection = false;
        // left or right random
        xDirection = 0.5-rand.nextInt(1000)/1000.0;
        yDirection = 0.5-rand.nextInt(1000)/1000.0;
   
      }
      else if (canSmellPlayer(engine))
      {
        setNewDirection = false;
        Point2D pt = super.PathfindToThePlayer(engine);
        xDirection = pt.getX();
        yDirection = pt.getY();
        if (yDirection == 0.0 && xDirection != 0.0) xDirection = xDirection < 0.0 ? -1.0 : 1.0;
        else if (xDirection != 0.0) xDirection = xDirection < 0.0 ? -0.5 : 0.5;
        if (xDirection == 0.0 && yDirection != 0.0) yDirection = yDirection < 0.0 ? -1.0 : 1.0;
        else if (yDirection != 0.0) yDirection = yDirection < 0.0 ? -0.5 : 0.5;
  
      }



    }
    //directionXY.set(.7, -0.7, 0.0);
    double totalSpeed = zombieSpeed * deltaSeconds;
    setLocation(getLocation().getX() + xDirection * totalSpeed,
                getLocation().getY() + yDirection * totalSpeed);

    checkPlaySound(engine, deltaSeconds);
    return UpdateResult.UPDATE_COMPLETED;

  }
}

