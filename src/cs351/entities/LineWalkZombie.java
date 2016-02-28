package cs351.entities;

import java.util.Random;

import cs351.DijkstraAlgorithm.TestDijkstraAlgorithm;
import cs351.core.Actor;
import cs351.core.Engine;
import cs351.core.GlobalConstants;
import cs351.core.Actor.UpdateResult;
import javafx.geometry.Point2D;


public class LineWalkZombie extends Zombie {
  //initialize to something we set
  private double elapsedSeconds=0;
  private Random rand = new Random();
  private double xDirection = 0;
  private double yDirection = 0;
  private boolean setNewDirection = true;
  private double elapsedTime=0;
  private int timerCt = 0;
  @Override
  public void collided(Engine engine, Actor actor) {
    if (!actor.isPartOfFloor())
    {
      setNewDirection = true;
      elapsedTime = 0;
    }
  }

  public LineWalkZombie(String textureFile, double x, double y, int width, int height, int depth)
  {
    super(textureFile, x, y, width, height, depth);
  }

  public UpdateResult update(Engine engine, double deltaSeconds)
  { 

    // super.update(engine, deltaSeconds);

 //   System.out.println("--deltaSeconds--"+deltaSeconds);
 //   System.out.println("--one 60th--"+1.0/60.0);
       
    // totalSpeed represents the movement speed offset in tiles per second
    elapsedSeconds += deltaSeconds;
    // every zombieDecisionRate seconds, switch direction
    if (elapsedSeconds > GlobalConstants.zombieDecisionRate)
    {
      elapsedSeconds = 0.0;
      //timerCt++;
      if (canSmellPlayer(engine))
      {
        if (timerCt++ % 6==0) 
        {

       //   double worldWidth = engine.getWorld().getWorldPixelWidth() / engine.getWorld().getTilePixelWidth();
       //   double worldHeight = engine.getWorld().getWorldPixelHeight() / engine.getWorld().getTilePixelHeight();

          double currX = getLocation().getX();
          double currY = getLocation().getY();

          double targetX = engine.getWorld().getPlayer().getLocation().getX();
          double targetY = engine.getWorld().getPlayer().getLocation().getY();
/*
          System.out.println("----");
          System.out.println("start zombie x: "+currX);            
          System.out.println("----");
          System.out.println("start zombie y: "+currY);
          */
            
          if (currX<targetX)
          {
            currX = currX+1;
          } else
          {
            currX = currX-1; 
          }
          
          if (currY<targetY)
          {
            currY = currY+1;
          } else
          {
            currY = currY - 1;
          }
          
          
          
          Point2D pt = engine.getDijkstra().getNextLocation((int)currX,(int)currY,(int)targetX,(int)targetY);


          // if we have a path to player and can smell him
          if (pt!=null)
          {
            
            if ( pt.getX() > currX+0.02) 
            {
              xDirection = 0.02;
            } else if ( pt.getX() < currX-0.02) 
            {
              xDirection = -0.02; 
            }else
              xDirection = 0;
        

            
            if ( pt.getY() > currY+0.02) 
            {
              yDirection = 0.02;
            } else if ( pt.getY() < currY-0.02) 
            {
              yDirection = -0.02; 
            } else
              yDirection = 0;
            /*
            System.out.println("----");
            System.out.println("zombie x: "+currX);            
            System.out.println("player x: "+targetX);
            System.out.println("next step x: "+pt.getX());
            System.out.println("xDirection: "+xDirection);
            System.out.println("----");
            System.out.println("zombie y: "+currY);
            System.out.println("player y: "+targetY);
            System.out.println("next step y: "+pt.getY());
            System.out.println("yDirection: "+yDirection);
            */
            
          }
        } 
      }
      
      /*
      else if (setNewDirection)
      {
        setNewDirection = false;
        // left or right random
        xDirection = (100-rand.nextInt(200))/20000.0;
       // System.out.println("random xDirection: "+xDirection);

        // forward or back random
        yDirection = (100-rand.nextInt(200))/20000.0;
      }*/
      
    }
    setLocation(getLocation().getX()+xDirection, getLocation().getY() +yDirection);

    checkPlaySound(engine, deltaSeconds);
    return UpdateResult.UPDATE_COMPLETED;



    /*
     double currX = getLocation().getX();
     double currY = getLocation().getY();


    elapsedTime += deltaSeconds;
    if ((xDirection==-1000)||( ( elapsedTime > GlobalConstants.zombieDecisionRate)&&(setNewDirection == true)))
    {
      elapsedTime = 0;

      setNewDirection = false;
      // choose random X direction
      xDirection = (100-rand.nextInt(200))/20000.0;
      // choose random Y direction
      yDirection = (100-rand.nextInt(200))/20000.0;
    }

  Point2D pt = null;
    elapsedTime += deltaSeconds;

    if ( elapsedTime > GlobalConstants.zombieDecisionRate)
    {
      elapsedTime = 0;
      timerCt++;
      int targetX = (int)engine.getWorld().getPlayer().getLocation().getX();
      int targetY = (int)engine.getWorld().getPlayer().getLocation().getY();
      if (timerCt % 2==1)
      {
        xDirection = 0;
        yDirection = 0;

      }

      else if ((timerCt == 20)&&canSmellPlayer(engine))
      {           
        timerCt = 0;
        double worldWidth = engine.getWorld().getWorldPixelWidth() / engine.getWorld().getTilePixelWidth();
        double worldHeight = engine.getWorld().getWorldPixelHeight() / engine.getWorld().getTilePixelHeight();

        engine.getDijkstra().initGraph(engine.getPathingData(), (int)worldWidth, (int)worldHeight);
        pt = engine.getDijkstra().getNextLocation(currX,currY,targetX,targetY);


        // if we have a path to player and can smell him
        if (pt!=null)
        {
          xDirection = (currX - pt.getX())/2000000;
          yDirection = (currY - pt.getY())/2000000;
        }
      }

    }








    setLocation(currX+xDirection, currY+yDirection);

    checkPlaySound(engine, deltaSeconds);


    return UpdateResult.UPDATE_COMPLETED;
     */

  }
}
