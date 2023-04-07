import java.awt.*;

/**
 * Created by chales on 11/6/2017.
 */
public class Astronaut {

    //VARIABLE DECLARATION SECTION
    //Here's where you state which variables you are going to use.
    public String name;                //holds the name of the hero
    public int xpos;                //the x position
    public int ypos;                //the y position
    public int dx;                    //the speed of the hero in the x direction
    public int dy;                    //the speed of the hero in the y direction
    public int width;
    public int height;
    public boolean isAlive;            //a boolean to denote if the hero is alive or dead.
    public Rectangle rec;
    public double lives;
    public double maxHealth;

    public boolean north;
    public boolean south;
    public boolean east;
    public boolean west;


    // METHOD DEFINITION SECTION

    // Constructor Definition
    // A constructor builds the object when called and sets variable values.


    //This is a SECOND constructor that takes 3 parameters.  This allows us to specify the hero's name and position when we build it.
    // if you put in a String, an int and an int the program will use this constructor instead of the one above.
    public Astronaut(int pXpos, int pYpos) {
        xpos = pXpos;
        ypos = pYpos;
        dx = 0;
        dy = 0;
        width = 110;
        height = 110;
        isAlive = true;
        rec = new Rectangle (xpos, ypos, height, width);
        lives= 10;
        maxHealth=20;

    } // constructor

    //The move method.  Everytime this is run (or "called") the hero's x position and y position change by dx and dy
    public void move() {
        xpos = xpos + dx;
        ypos = ypos + dy;
        rec = new Rectangle (xpos, ypos, height, width);
//        if (north){
//            dy = 3;
//            dx = 0;
//        }
    }
//    public Astronaut(int pXpos, int pYpos) {
//        xpos = pXpos;
//        ypos = pYpos;
//        dx =10;
//        dy =10;
//        width = 60;
//        height = 60;
//        isAlive = true;

    public void wrap() {
        if (xpos > 1000) {
            xpos = 0;
        }
        if (ypos > 1000) {
            ypos = 0;
        }
        xpos = xpos + dx;
        ypos = ypos + dy;
        rec = new Rectangle (xpos, ypos, height, width);
    }


    public void bounce() {
        if (xpos > 1000) {
            dx = -dx;
        }
        if (ypos > 700) {
            dy = -dy;
        }
        if (xpos < 0) {
            dx = -dx;
        }
        if (ypos < 0) {
            dy = -dy;
        }

        xpos = xpos + dx;
        ypos = ypos + dy;
        rec = new Rectangle (xpos, ypos, height, width);

    }
    public void step(){
        xpos = xpos + dx;
        ypos = ypos + dy;
        rec = new Rectangle (xpos, ypos, height, width);
    }
}

