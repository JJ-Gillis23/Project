import javafx.scene.paint.*;
import javafx.scene.canvas.*;
public class Mine extends DrawableObject
{
   public Mine(float x, float y)
   {
      super(x,y);
   }
   double colorValue = Math.random();
   
   int way = 1;
   //Using advance color to shift between red and white
   public void advanceColor()
   {
      colorValue += 0.01f * way;
      
      if(colorValue > 1)
      {
         colorValue = 1;
         way = - 1;
      }
      if(colorValue < 0)
      {
         colorValue = 0;
         way = 1;
      }
   }

   //draws itself at the passed in x and y.
   public void drawMe(float x, float y, GraphicsContext gc)
   {
      advanceColor();
      //Using interpolate to activate the function
      gc.setFill(Color.WHITE.interpolate(Color.RED,colorValue));
      gc.fillOval(x,y,15,15);
      
   }
       
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
 }