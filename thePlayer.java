import javafx.scene.paint.*;
import javafx.scene.canvas.*;

//this is an example object
public class thePlayer extends DrawableObject
{
	//takes in its position
   public thePlayer(float x, float y)
   {
      super(x,y);
   }
   //draws itself at the passed in x and y.
   public void drawMe(float x, float y, GraphicsContext gc)
   {
   //Creating the ovals with black outline around them
      gc.setFill(Color.BLACK);
      gc.fillOval(x-14,y-14,27,27);
      gc.setFill(Color.GRAY);
      gc.fillOval(x-13,y-13,25,25);
      gc.setFill(Color.BLACK);
      gc.fillOval(x-8,y-8,15,15);
      gc.setFill(Color.GREEN);
      gc.fillOval(x-7,y-7,13,13);
   }
}
