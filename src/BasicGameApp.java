//Basic Game Application
//Version 2
// Basic Object, Image, Movement
// Astronaut moves to the right.
// Threaded

//K. Chun 8/2018

//*******************************************************************************
//Import Section
//Add Java libraries needed for the game
//import java.awt.Canvas;

//Graphics Libraries

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;
import java.text.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


//*******************************************************************************
// Class Definition Section

public class BasicGameApp implements Runnable, KeyListener, MouseInputListener {

    //Variable Definition Section
    //Declare the variables used in the program
    //You can set their initial values too

    //Sets the width and height of the program window
    final int WIDTH = 1000;
    final int HEIGHT = 700;

    public Astronaut [] characters;

    //Declare the variables needed for the graphics
    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;

    public BufferStrategy bufferStrategy;
    public Image ConorPic;
    public Image ringPic;
    public Image israelPic;
    public Image refereePic;
    public Image winPic;

    //Declare the objects used in the program
    //These are things that are made up of more than one variable type
    private Astronaut Conor;
    private Astronaut israel;
    //declare an array of controlledAstro

    private Astronaut referee;
    public int counter = 0;

    public boolean winning;


    // Main method definition
    // This is the code that runs first and automatically
    public static void main(String[] args) {
        BasicGameApp ex = new BasicGameApp();   //creates a new instance of the game
        new Thread(ex).start();                 //creates a threads & starts up the code in the run( ) method
    }


    // Constructor Method
    // This has the same name as the class
    // This section is the setup portion of the program
    // Initialize your variables and construct your program objects here.
    public BasicGameApp() {

        setUpGraphics();
        //sscanvas.addKeyListener(this);
        //variable and objects
        //create (construct) the objects needed for the game and load up
        characters = new Astronaut[3];
        ConorPic = Toolkit.getDefaultToolkit().getImage("Conor.jpeg");
        ringPic = Toolkit.getDefaultToolkit().getImage("ring.png");//load the picture
        israelPic = Toolkit.getDefaultToolkit().getImage("israel.jpeg");
        refereePic = Toolkit.getDefaultToolkit().getImage("referee.jpeg");
        winPic = Toolkit.getDefaultToolkit().getImage("win.jpeg");
        winning = false;

        Conor = new Astronaut(120, 240);
        israel = new Astronaut(200, 420);
        referee = new Astronaut(0, 0);

        Conor.pic = ConorPic;
        israel.pic= israelPic;
        referee.pic = refereePic;
        // construct the array to hold the astro, it is empty
        // fill each slot
//        israel.dy = -10;
//        israel.dx = 10;
        Conor.dy = -10;
        Conor.dx = 10;
        referee.dx = 1;


        characters [0] = referee;
        characters [1] = Conor;
        characters [2] = israel;


    }// BasicGameApp()


//*******************************************************************************
//User Method Section
//
// put your code to do things here.

    // main thread
    // this is the code that plays the game after you set things up
    public void run() {

        //for the moment we will loop things forever.
        while (true) {

            moveThings();  //move all the game objects
            render();  // paint the graphics
            pause(20); // sleep for 10 ms
        }
    }

    public void change() {
        if (Conor.rec.intersects(israel.rec)) ;

        //System.out.println("crash");
        Conor.dx = 1 * Conor.dx;
        Conor.dy = -Conor.dy;
        israel.dx = 1 * israel.dx;
        israel.dy = -israel.dy;
    }


    public void crash() {
        if (Conor.rec.intersects(israel.rec) && israel.isAlive == true && Conor.isAlive == true) {
            // System.out.println("crash");
            Conor.dx = 1 * Conor.dx;
            Conor.dy = -Conor.dy;
            israel.dx = 1 * israel.dx;
            israel.dy = -israel.dy;
            //israel.isAlive = false;
            counter++;
            Conor.lives--;
        }
//        if(counter==10){
//            israel.isAlive=false;
//        }
        if (Conor.rec.intersects(referee.rec) && referee.isAlive == true && Conor.isAlive == true) {
            System.out.println("crash");
            Conor.dx = 1 * Conor.dx;
            Conor.dy = -Conor.dy;
            referee.dx = 1 * referee.dx;
            referee.dy = -referee.dy;
            //  referee.isAlive = false;
            counter++;
            Conor.lives++;
        }
        if (Conor.lives == 0) {
            Conor.isAlive = false;
            winning = true;
        }

    }

    public void size() {
        if (Conor.rec.intersects(israel.rec)) {
            Conor.height = 3 * Conor.height;
            Conor.width = 3 * Conor.width;
            israel.height = 3 * israel.height;
            israel.width = 3 * israel.width;
        }
    }

    public void moveThings() {
        //calls the move( ) code in the objects
        Conor.bounce();
        israel.bounce();
        referee.wrap();
        crash();


    }

    //Pauses or sleeps the computer for the amount specified in milliseconds
    public void pause(int time) {
        //sleep
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {

        }
    }

    //Graphics setup method
    private void setUpGraphics() {
        frame = new JFrame("Application Template");   //Create the program window or frame.  Names it.

        panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
        panel.setLayout(null);   //set the layout

        // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
        // and trap input events (Mouse and Keyboard events)
        canvas = new Canvas();
        canvas.addKeyListener(this);
        canvas.addMouseListener(this);
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);  // adds the canvas to the panel.

        // frame operations
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
        frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
        frame.setResizable(false);   //makes it so the frame cannot be resized
        frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!

        // sets up things so the screen displays images nicely.
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
        System.out.println("DONE graphic setup");

    }


    //paints things on the screen using bufferStrategy
    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);

        if (winning == false) {


            //draw the image of the astronaut
            g.drawImage(ringPic, 0, 0, WIDTH, HEIGHT, null);
            for (int z=0; z< characters.length; z++){
                if (characters[z].isAlive == true) {
                    g.drawImage(characters[z].pic, characters[z].xpos, characters[z].ypos, characters[z].width, characters[z].height, null);

                    g.draw(new Rectangle(characters[z].xpos, characters[z].ypos, characters[z].width, characters[z].height));
                }
            }


            g.setColor(Color.red);
            g.fillRect(Conor.xpos, Conor.ypos - 10, Conor.width, 10);
            g.setColor(Color.green);

            double calc = (Conor.lives / Conor.maxHealth);

         //   System.out.println(calc);
            // System.out.println(Conor.maxHealth);

            //   double calc = (Conor.lives/Conor.maxHealth);
            g.fillRect(Conor.xpos, Conor.ypos - 10, (int) (Conor.width * (Conor.lives / Conor.maxHealth)), 10);


        } else {
            g.drawImage(winPic, 0, 0, WIDTH, HEIGHT, null);
        }

        g.dispose();

        bufferStrategy.show();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        System.out.println(code);
        if (code == 83) {
            israel.dy=Math.abs(israel.dy);
            israel.dy = israel.dy + 1;
//            israel.dx = 0;
        }
        if (code == 87) {
            israel.dy = +israel.dy - 1;
//            israel.dx = 0;
        }
        if (code == 65) {
            israel.dx = israel.dx - 1;
        }
        if (code == 68) {
            israel.dx = israel.dx + 1;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
referee.dx=1;
    }

    @Override
    public void mouseExited(MouseEvent e) {
referee.dx=0;
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
    public  void pull() throws ParseException {
        String output = "abc";
        String totlaJson="";
        try {

            URL url = new URL("https://last-airbender-api.fly.dev/api/v1/characters");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {

                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));


            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                totlaJson+=output;
            }

            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONParser parser = new JSONParser();
        //System.out.println(str);
        jsonObject = (org.json.simple.JSONArray) parser.parse(totlaJson);
        System.out.println(jsonObject);
//System.out.println("hi");
        try {
            for(int j = 0; j<jsonObject.size(); j++){
                System.out.println(jsonObject.get(j));
                JSONObject secretTunnelGuy = (JSONObject) jsonObject.get(j);
                System.out.println(secretTunnelGuy.get("name"));
                JSONArray ally1 = (JSONArray) secretTunnelGuy.get("allies");
                System.out.println(ally1.get(0));
            }






            //   System.out.println(jsonObject.get(1));
            //   JSONObject guy2 = (JSONObject) jsonObject.get(1);
            //   System.out.println(guy2.get("name"));
            //   JSONArray ally2 = (JSONArray) guy2.get("allies");
            //   System.out.println(ally2.get(0));


//            for(jsonObject.size()){
//
//            }
//
//            String name = (String)jsonObject.get("name");
//
//            org.json.simple.JSONArray msg = (org.json.simple.JSONArray) jsonObject.get("starships");
//            int n =   msg.size(); //(msg).length();
//            for (int i = 0; i < n; ++i) {
//                String test =(String) msg.get(i);
//                System.out.println(test);
//                // System.out.println(person.getInt("key"));
//            }
//         //   String name= (String)jsonObject.get("eye_color");
//            System.out.println(name);
        }

        catch (Exception e) {
            e.printStackTrace();
        }




    }
}
