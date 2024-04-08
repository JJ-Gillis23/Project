import java.net.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.text.*;
import javafx.stage.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.*;
import javafx.scene.paint.*;
import javafx.geometry.*;
import javafx.scene.image.*;

import java.io.*;

import java.util.*;
import java.text.*;
import java.io.*;
import java.lang.*;
import javafx.application.*;
import javafx.event.*;
import javafx.stage.*;
import javafx.scene.canvas.*;
import javafx.scene.paint.*;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.animation.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import java.net.*;
import javafx.geometry.*;

public class Project extends Application
{
   FlowPane fp;
   int highscore;
   boolean up,down,left,right;
   boolean alive = true;
   Canvas theCanvas = new Canvas(600,600);
   thePlayer thePlayer = new thePlayer(300,300);
   thePlayer origin = new thePlayer(300,300);
   Random rand = new Random();
   ArrayList<Mine> m = new ArrayList<Mine>();
   Mine theMines = new Mine(200,200);
   float xForce = 0;
   float yForce = 0;
   int score = 0;
   float newX = 0;
   float newY = 0;
   int gx;
   int gy;
   int cgridx = ((int)thePlayer.getX())/100;
   int cgridy = ((int)thePlayer.getY())/100;  
   AnimationHandler ta = new AnimationHandler();
   public void start(Stage stage)
   {
      
      //Establishing the flowpane of the project
      fp = new FlowPane();
      fp.getChildren().add(theCanvas);
      gc = theCanvas.getGraphicsContext2D();
      drawBackground(300,300,gc);
      //Key lsiteners for moving the player
      fp.setOnKeyPressed(new KeyListenerDown());
      fp.setOnKeyReleased(new KeyListenerUp());
      
      
      Scene scene = new Scene(fp, 600, 600);
      stage.setScene(scene);
      stage.setTitle("Project :)");
      fp.requestFocus();
      stage.show();
      ta.start();
      
      
   }
   public Project()
   {
   //Here I read through a file so we can track the highscore through every game
      try
      {
         Scanner read = new Scanner(new File("Highscore.txt"));
         highscore = read.nextInt();
      }
      catch(FileNotFoundException fnfe)
      {
      }
   }
   
   GraphicsContext gc;
   
   
   Image background = new Image("stars.png");
   Image overlay = new Image("starsoverlay.png");
   Random backgroundRand = new Random();
   //this piece of code doesn't need to be modified
   public void drawBackground(float playerx, float playery, GraphicsContext gc)
   {
     //re-scale player position to make the background move slower. 
      playerx*=.1;
      playery*=.1;
   
   //figuring out the tile's position.
      float x = (playerx) / 400;
      float y = (playery) / 400;
      
      int xi = (int) x;
      int yi = (int) y;
      
     //draw a certain amount of the tiled images
      for(int i=xi-3;i<xi+3;i++)
      {
         for(int j=yi-3;j<yi+3;j++)
         {
            gc.drawImage(background,-playerx+i*400,-playery+j*400);
         }
      }
      
     //below repeats with an overlay image
      playerx*=2f;
      playery*=2f;
   
      x = (playerx) / 400;
      y = (playery) / 400;
      
      xi = (int) x;
      yi = (int) y;
      
      for(int i=xi-3;i<xi+3;i++)
      {
         for(int j=yi-3;j<yi+3;j++)
         {
            gc.drawImage(overlay,-playerx+i*400,-playery+j*400);
         }
      }
   }
   
   //draws itself at the passed in x and y.
   
   
   
   
   

  
   public class AnimationHandler extends AnimationTimer
   {
      public void handle(long currentTimeInNanoSeconds) 
      {
         gc.clearRect(0,0,600,600);
         
         //USE THIS CALL ONCE YOU HAVE A PLAYER
         drawBackground(thePlayer.getX(),thePlayer.getY(),gc); 
      
         //example calls of draw - this should be the player's call for draw
          //all other objects will use false in the parameter.
          //Drawing the player if it hasnt hit the mine yet
         if(alive == true)
         {
         thePlayer.draw(300,300,gc,true);
         }
         //Creating the slowing down feature
         if(!left && !right)
         {
            if(xForce > -.25 && xForce < .25)
            {
               xForce = 0;
            }
            else if(xForce <= -.25)
            {
               xForce+= .025;
            }
            else if(xForce >= -.25)
            {
               xForce -= .025;
            }
         }
         if(!up && !down)
         {
            if(yForce > -.25 && yForce < .25)
            {
               yForce = 0;
            }
            else if(yForce <= -.25)
            {
               yForce+= .025;
            }
            else if(yForce >= -.25)
            {
               yForce -= .025;
            }
         }         
         
            
         //Changing the force wherever the player moves
         if(left)
         {
            if(xForce > 5)
            {
               xForce = 5;
            }
            else 
            {
               xForce -= .1;
            }         
         }
         if(right)
         {
            if(xForce < -5)
            {
               xForce = -5;
            }
            else if (xForce >= -5)
            {
               xForce += .1;
            } 
         }
         if(down)
         {
            if(yForce < -5)
            {
               yForce = -5;
            }
            else if (yForce >= -5)
            {
               yForce += .1;
            } 
         }
         if(up)
         {
            if(yForce > 5)
            {
               yForce = 5;
            }
            else 
            {
               yForce -= .1;
            }  
         }
         //Moves the player by the force thats applied
         thePlayer.setX(thePlayer.getX() + xForce);
         thePlayer.setY(thePlayer.getY() + yForce);
         //Setting up the grid where the mines are gonna go
         if(((int)thePlayer.getX())/100 != cgridx || ((int)thePlayer.getY())/100 != cgridy)
         {
         
            cgridx = ((int)thePlayer.getX())/100;
            cgridy = ((int)thePlayer.getY())/100;   
            //Using four for loops to go from the corners and make a border of mines
            for(int i = 0; i<9; i++)
            {
               gx = cgridx - 5 + i;
               gy = cgridy - 5;
               createMines(gx,gy);
            }
            for(int i = 0; i<9; i++)
            {
               gx = cgridx - 5 + i;
               gy = cgridy + 5;
               createMines(gx,gy);
            }
            for(int i = 0; i<9; i++)
            {
               gx = cgridx - 5;
               gy = cgridy - 5 + i;
               createMines(gx,gy);
            }
            for(int i = 0; i<9; i++)
            {
               gx = cgridx + 5;
               gy = cgridy - 5 + i;
               createMines(gx,gy);
            }         
         }
         
         
         //Calculating the score by the distance from the player to the origin
         score = (int)thePlayer.distance(origin);
         
         gc.setFill(Color.WHITE);
         //Creating the score and highscore features
         gc.fillText("Score" + score, 15,15);
         gc.fillText("Highscore" + highscore,15,40);
         //If the score exceeds the highscore set it as the new highscore
         if(score > highscore)
         {
            highscore = score;
            gc.fillText("Highscore" + highscore,15,40);
            try
            {
            //Creating a file to hold the highscore so we can repeat the game with the highscore
               FileOutputStream fos = new FileOutputStream("Highscore.txt", false); //false means new file; true means append
                     
               PrintWriter pw = new PrintWriter(fos);
                     
               pw.println(highscore);
                     
               pw.close(); //closes the open file. Sometimes if you don't close a file you won't see the output in the file
                     
            }
            catch(FileNotFoundException fnfe)
            {
               System.out.println("File does not exist! (if appending or creating the file fails)");
            }              
         }
      
      
         
         //example call of a draw where m is a non-player object. Note that you are passing the player's position in and not m's position.
         //m.draw(thePlayer.getX(),thePlayer.getY(),gc,false);
         //When the player hits the mine stops the animation
         if(alive == false)
         {
         ta.stop();
         }
         
         for(int i = 0; i<m.size(); i++)
         {
         //Drawing all the mines that are currently made
            m.get(i).draw(thePlayer.getX(),thePlayer.getY(),gc,false);
            //When the player gets 800 units away from the mine I remove the mine
            if(m.get(i).distance(thePlayer) >= 800)
            {
               m.remove(i);
            }
            //If the player touches the mine it dies along with the mine which is removed
            if(m.get(i).distance(thePlayer) <= 25)
            {
               m.remove(i);
               i--;
               alive = false;
               
            }
           
         }
         
        
         
      }
   }
   public class KeyListenerDown implements EventHandler<KeyEvent>  
   {
      public void handle(KeyEvent event) 
      { 
      //Key listener for moving
         if (event.getCode() == KeyCode.A) 
         {
            left = true;
         }
         if (event.getCode() == KeyCode.W)  
         {
            up = true;
         }
         if (event.getCode() == KeyCode.S)  
         {
            down = true;
         }
         if (event.getCode() == KeyCode.D)  
         {
            right = true;
         }
         
      
      }
   }
   
   public class KeyListenerUp implements EventHandler<KeyEvent>  
   {
      public void handle(KeyEvent event) 
      { 
      //Key listener to counter act when the action is released
         if (event.getCode() == KeyCode.A) 
         {
            left = false;
         }
         if (event.getCode() == KeyCode.W)  
         {
            up = false;
         }
         if (event.getCode() == KeyCode.S)  
         {
            down = false;
         }
         if (event.getCode() == KeyCode.D)  
         {
            right = false;
         }
      }
   }
   public void createMines(int gx, int gy)
   {
   //N is the calculation from the mine to the origin
      int n = score/1000;
      while(n != 0)
      {
         int probability = rand.nextInt(100);
         //30% chance of cretaing a mine
         if(probability <= 30)
         {
            int xrealcord = gx * 100;
            int yrealcord = gy * 100;
            Mine theMines = new Mine(xrealcord+rand.nextInt(100),yrealcord+rand.nextInt(100));
            //Adding all of the mines I created into one array list
            m.add(theMines);
         }
      //Repeats depending on how far it is from the player
         n--;
      }
      
   
   }
      
   


   public static void main(String[] args)
   {
      launch(args);
   }
}

