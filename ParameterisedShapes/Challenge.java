// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP-102-112 - 2021T1, Assignment 3
 * Name: Ella Wipatene
 * Username: wipateella
 * ID: 300558005
 */

import ecs100.*;
import java.awt.Color;
import java.util.*;
import javax.swing.JColorChooser;
import java.util.Random; 

/** Paramterised Shapes: draw tricolour flags and game boards */
public class Challenge{
    
    //Constants for CORE  (three strip flags)
    public static final double FLAG_WIDTH = 200;
    public static final double FLAG_HEIGHT = 133;

    //Constants for COMPLETION
    public static final double BOARD_LEFT = 15;  // Left side of each row
    public static final double BOARD_TOP = 15;   // Top of the first row
    public static final double ROW_SIZE = 40;    // Height of each row.
    public static final double DISH_WIDTH = ROW_SIZE-4;      // Size of the dishes
    public static final double DISH_HEIGHT = DISH_WIDTH-10;  
    public static final double PEBBLE_DIAM = 10; // Size of the pebbles
    
    private static Random rng = new Random(); // Generating random number for Challenge
    
    /**   CORE
     * asks user for a position and three colours, then calls the
     * drawTriColorFlag method, passing the appropriate arguments
     */
    public void doCore(){
        double left = UI.askDouble("Left of flag");
        double top = UI.askDouble("Top of flag");
        boolean horiz = UI.askBoolean("Are the stripes horizontal?");
        UI.println("Now choose the colours");
        Color stripe1 = JColorChooser.showDialog(null, "First Stripe", Color.white);
        Color stripe2 = JColorChooser.showDialog(null, "Second Stripe", Color.white);
        Color stripe3 = JColorChooser.showDialog(null, "Third Stripe", Color.white);
        this.drawThreeStripeFlag(left, top, horiz, stripe1, stripe2, stripe3);
    }

    /**   CORE
     * draws a three colour flag at the given position consisting of
     * three equal size stripes of the given colors
     * The stripes are horizontal or vertical
     * The size of the flag is specified by the constants FLAG_WIDTH and FLAG_HEIGHT
     */
    public void drawThreeStripeFlag(double left, double top, boolean horiz, Color stripe1, Color stripe2, Color stripe3){
        UI.setColor(stripe1); 
        if (horiz == true){
            UI.fillRect(left, top, FLAG_WIDTH, FLAG_HEIGHT/3);
            UI.setColor(stripe2); 
            UI.fillRect(left, top + (FLAG_HEIGHT/3), FLAG_WIDTH, FLAG_HEIGHT/3);
            UI.setColor(stripe3); 
            UI.fillRect(left, top + (FLAG_HEIGHT*2/3), FLAG_WIDTH, FLAG_HEIGHT/3);
        } else{
            UI.fillRect(left, top, FLAG_WIDTH/3, FLAG_HEIGHT);
            UI.setColor(stripe2); 
            UI.fillRect(left + (FLAG_WIDTH/3), top, FLAG_WIDTH/3, FLAG_HEIGHT);
            UI.setColor(stripe3); 
            UI.fillRect(left + (FLAG_WIDTH*2/3), top, FLAG_WIDTH/3, FLAG_HEIGHT);
        }
    }

    /**   COMPLETION
     * Draws a pebble game board with five rows of increasing size
     *   The first row has 6 dishes, the second has 7 dishes, the third has 8, etc.
     *   The positions of the red and blue pebbles are shown in this table:
     *   (where the |'s separate the dishes)
     *     |   | r |   |   |   | b |
     *     |   | b | r |   |   |   |   |
     *     |   |   |   |   |   | r |   | b |
     *     | b |   |   | r |   |   |   |   |   |
     *     |   |   | b |   |   |   |   |   |   | r |
     *
     *  It uses the drawPebbleRow method which draws one row and the two pebbles in it.
     */
    public void doCompletion(){
        UI.clearGraphics();
        this.drawPebbleRow(1,6,1,5, true);
        this.drawPebbleRow(2,7,1,2, false);
        this.drawPebbleRow(3,8,5,7, true);
        this.drawPebbleRow(4,9,0,3, false);
        this.drawPebbleRow(5,10,2,9,false);
    }

    /**   COMPLETION
     * Draws a row of a pebble game. Parameters must be sufficient to specify
     * the position and size of the row, and which dishes the red and blue pebbles are in.
     * Hint: use the drawRowOutline, drawDish and drawPebble methods!
     */
    public void drawPebbleRow(int rowNum, int numDishes, int pebbleOne, int pebbleTwo, boolean colour){
        drawRowOutline(rowNum, numDishes); 
        for (int i = 0; i < numDishes; i++){
            drawDish(rowNum, i); 
        }
        //whats the opposite of t
        if (colour == true){
            drawPebble(rowNum, pebbleOne, Color.red);  
            drawPebble(rowNum, pebbleTwo, Color.blue);  
        } else{
            drawPebble(rowNum, pebbleOne, Color.blue); 
            drawPebble(rowNum, pebbleTwo, Color.red); 
        }
    }

    /**
     * Draws the outline of the specified row with the specified number of dishes.
     * (rows numbered from 0)
     */
    public void drawRowOutline(int rowNum, int numDishes){
        UI.setColor(Color.black); 
        UI.drawRect(BOARD_LEFT, BOARD_TOP + (rowNum * ROW_SIZE), ROW_SIZE*numDishes, ROW_SIZE);
    }

    /**
     * Draw the specified dish in the specified row
     * (rows and dishes are numbered from 0)
     */
    public void drawDish(int rowNum, int dishNum){
        double dishLeft = BOARD_LEFT+dishNum*ROW_SIZE + ROW_SIZE/2 - DISH_WIDTH/2 ;
        double dishTop = BOARD_TOP + rowNum * ROW_SIZE + ROW_SIZE/2 - DISH_HEIGHT/2;
        UI.setColor(new Color(230, 230, 230));   // very light grey
        UI.fillOval(dishLeft, dishTop, DISH_WIDTH, DISH_HEIGHT);
        UI.setColor(Color.black);
        UI.drawOval(dishLeft, dishTop, DISH_WIDTH, DISH_HEIGHT);
    }

    /**
     * Draw a pebble in specified dish in the specified row with the specified color.
     * (rows and dishes are numbered from 0)
     */
    public void drawPebble(int rowNum, int dishNum, Color pebbleColor){
        double pebbleTop = BOARD_TOP + rowNum * ROW_SIZE + ROW_SIZE/2 - PEBBLE_DIAM/2;
        double pebbleLeft = BOARD_LEFT+dishNum*ROW_SIZE + ROW_SIZE/2 - PEBBLE_DIAM/2;
        UI.setColor(pebbleColor);
        UI.fillOval(pebbleLeft, pebbleTop, PEBBLE_DIAM, PEBBLE_DIAM);
    }
    
    public void erasePebble(int rowNum, int dishNum, Color pebbleColor){
        double pebbleTop = BOARD_TOP + rowNum * ROW_SIZE + ROW_SIZE/2 - PEBBLE_DIAM/2;
        double pebbleLeft = BOARD_LEFT+dishNum*ROW_SIZE + ROW_SIZE/2 - PEBBLE_DIAM/2;
        UI.eraseOval(pebbleLeft, pebbleTop, PEBBLE_DIAM, PEBBLE_DIAM);
        UI.setColor(new Color(230, 230, 230));
        UI.fillOval(pebbleLeft, pebbleTop, PEBBLE_DIAM, PEBBLE_DIAM);
    }
    
    
    public void doChallengeHarder(){
        int numDishes = getDishes(); 
        int numRows = getRows(); 
        String playerMove = "L"; 
        int playerRow = 1; 
        boolean win = false; 
        
        // Draw the board 
        for (int row = 0; row < numRows; row++){
            drawRowOutline(row, numDishes); 
            for (int dish = 0; dish < numDishes; dish++){
                drawDish(row, dish); 
            }
        }
        
        // Set up and draw pebbles 
        ArrayList<Integer> redPosition = new ArrayList<Integer>(); 
        ArrayList<Integer> bluePosition = new ArrayList<Integer>(); 
        for (int i = 0; i < numRows; i++){
            redPosition.add(0); 
            bluePosition.add(numDishes - 1); 
            
            drawPebble(i, redPosition.get(i), Color.red); 
            drawPebble(i, bluePosition.get(i), Color.blue); 
        }
        UI.sleep(500);
        
        for (int i = 0; i < 100000000; i++){
            if (i % 2 == 0){ // red turn 
                playerRow = rowInput(numRows) - 1; 
                UI.println(playerRow); 
                playerMove = userInput();    
                win = redTurn(redPosition, bluePosition, playerRow, playerMove, numDishes ); 
                if (win == true){
                    UI.println("Red is the winner!"); 
                    break; 
                }
            
            } else { //blue turn 
                int blueRow = 1 +  rng.nextInt(numRows); // random row  
                int blueMove = (int)Math.round(Math.random()); // 0 = right, 1 = left
            
            
            
            
            }
        
        
        }
        

        //for (int i = 0; i < 100000000; i++){
            // if (i % 2 == 0){ // red turn 
                // player = userInput(); 
                // if (redPosition == 0 && player.equals("L")){ // first move 
                    // UI.println("Sorry, you can only move right right now.");
                    // if (bluePosition == 1){
                        // redPosition = redMove(2, redPosition); 
                        // bluePosition = blueHome(bluePosition, numDishes); 
                    // }
                    // redPosition = redMove(1, redPosition); 
                // } else if(redPosition == numDishes - 3 && player.equals("R") && bluePosition == numDishes -2){ // jump over blue but dont return blue home
                    // redPosition = redMove(2, redPosition); 
                    
                    // UI.println("Red wins the game!");
                    // break; 
                // }else if(redPosition == bluePosition - 1 && player.equals("R")){ // red jump right & send blue home
                    // redPosition = redMove(2, redPosition); 
                    // bluePosition = blueHome(bluePosition, numDishes);  
                // }else if(redPosition == bluePosition + 1 && player.equals("L")){ // red jump left & sends blue home
                    // redPosition = redMove(-2, redPosition); 
                    // bluePosition = blueHome(bluePosition, numDishes); 
                // } else{
                    // if (player.equals("R")){ // red move right
                        // redPosition = redMove(1,redPosition); 
                        // if (redPosition == numDishes - 1){
                            // UI.println("Red wins the game!");
                            // break; 
                        // }
                    // } else { // red move left 
                        // redPosition = redMove(-1, redPosition); 
                    // }
                // }
            // } else{ // blues turn
                // int blueMove = (int)Math.round(Math.random()); // 0 = right, 1 = left
                // if (bluePosition == numDishes - 1){
                    // if (redPosition == numDishes -2){ //blue at home and jumps over red  
                        // bluePosition = blueMove(-2, bluePosition); 
                        // redPosition = redHome(redPosition); 
                    // }else{
                        // bluePosition = blueMove(-1, bluePosition); 
                    // }
                // } else if(bluePosition == redPosition + 1 && blueMove == 1){ // blue jump left & send red home
                    // bluePosition = blueMove(-2, bluePosition); 
                    // if (bluePosition == 0){
                        // UI.println("Blue wins!");
                        // break; 
                    // }else{
                        // redPosition = redHome(redPosition); 
                    // }
                // }else if(bluePosition == redPosition - 1 && blueMove == 0){ //blue jump right & send red home
                    // bluePosition = blueMove(2, bluePosition); 
                    // redPosition = redHome(redPosition);  
                // }else{ 
                    // if (blueMove == 1){ // blue move left 
                        // bluePosition = blueMove(-1, bluePosition); 
                        // if (bluePosition == 0){
                            // UI.println("Blue wins!");
                            // break; 
                        // }
                    // } else{ //blue move right
                        // bluePosition = blueMove(1, bluePosition); 
                    // }
                // }
            //}
        //}
        }    
    
    public boolean redTurn(ArrayList<Integer> redPosition, ArrayList<Integer> bluePosition, int playerRow, String playerMove, int numDishes){
        boolean win = false; 
        int newPosition = 0; 
        if (redPosition.get(playerRow) == 0 && playerMove.equals("L")){ // first move 
                    UI.println("Sorry, you can only move right right now.");
                    if (bluePosition.get(playerRow) == 1){ // jump over blue and send blue home
                        newPosition = redMove(2, redPosition, playerRow); 
                        redPosition.set(playerRow, newPosition); 
                        
                        newPosition = blueHome(bluePosition.get(playerRow), numDishes); 
                        bluePosition.set(playerRow, newPosition); 
                    }
                    newPosition = redMove(1, redPosition, playerRow); 
                    redPosition.set(playerRow, newPosition); 
                } else if(redPosition.get(playerRow) == numDishes - 3 && playerMove.equals("R") && bluePosition.get(playerRow) == numDishes -2){ // jump over blue but dont return blue home
                    newPosition = redMove(2, redPosition, playerRow); 
                    redPosition.set(playerRow, newPosition); 
                    win = true;  
                }else if(redPosition.get(playerRow) == bluePosition.get(playerRow) - 1 && playerMove.equals("R")){ // red jump right & send blue home
                    newPosition = redMove(2, redPosition, playerRow); 
                    redPosition.set(playerRow, newPosition); 
                        
                    newPosition = blueHome(bluePosition.get(playerRow), numDishes); 
                    bluePosition.set(playerRow, newPosition);  
                }else if(redPosition.get(playerRow) == bluePosition.get(playerRow) + 1 && playerMove.equals("L")){ // red jump left & sends blue home
                    newPosition = redMove(-2, redPosition, playerRow); 
                    redPosition.set(playerRow, newPosition); 
                        
                    newPosition = blueHome(bluePosition.get(playerRow), numDishes); 
                    bluePosition.set(playerRow, newPosition);   
                } else{
                    if (playerMove.equals("R")){ // red move right
                        newPosition = redMove(1, redPosition, playerRow); 
                        redPosition.set(playerRow, newPosition); 
                        if (redPosition.get(playerRow) == numDishes - 1){
                            win = true;
                        }
                    } else { // red move left 
                        newPosition = redMove(-1, redPosition, playerRow); 
                        redPosition.set(playerRow, newPosition);  
                    }
                }

        return win; 
    }
    
    public int redMove(int spaces, ArrayList<Integer> redPosition,int playerRow){
        erasePebble(1, redPosition.get(playerRow), Color.red); 
        int newPosition = redPosition.get(playerRow) + spaces; 
        drawPebble(1, newPosition, Color.red);
        UI.sleep(500);
        
        return newPosition; 
    }
    
    public int redHome(int redPosition){
        erasePebble(1, redPosition, Color.red); 
        redPosition = 0; 
        drawPebble(1, redPosition, Color.red);
        UI.sleep(500);
        
        return redPosition; 
    }
    
    public int blueMove(int spaces, int bluePosition){
        erasePebble(1, bluePosition, Color.blue); 
        bluePosition = bluePosition + spaces; 
        drawPebble(1, bluePosition, Color.blue);
        UI.sleep(500);
        
        return bluePosition; 
    }
    
    public int blueHome(int bluePosition, int numDishes){
        erasePebble(1, bluePosition, Color.blue); 
        bluePosition = numDishes - 1; 
        drawPebble(1, bluePosition, Color.blue);
        UI.sleep(500);
        
        return bluePosition; 
    }
    
    public int getDishes(){
        int numDishes = 0; 
        boolean valid = false;
        
        while (valid == false){
            numDishes = UI.askInt("How many dishes?");
            if (numDishes > 3 && numDishes < 11){
                valid = true;  
            } else{
                UI.println("Please enter a number between 3 and 11.");
                continue; 
            }
        }
        return numDishes; 
    }
    
    public int getRows(){
        int numRows = 0; 
        boolean valid = false;
        
        while (valid == false){
            numRows = UI.askInt("How many Rows?");
            if (numRows > 0 && numRows < 11){
                valid = true;  
            } else{
                UI.println("Please enter a number between 0 and 11.");
                continue; 
            }
        }
        return numRows; 
    }
    
    public int rowInput(int numRows){
        boolean valid = false;
        int row = 1; 
        
        while (valid == false){
            row = UI.askInt("Which row do you want to move your pebble in?)");
            if (row > 0 && row < numRows + 1){
                valid = true;  
            } else{
                UI.println("Please enter a number between 0 and " + numRows);
                continue; 
            }
        }
        return row;  
    } 
 
    
    public String userInput(){
        boolean valid = false;
        String player = "L"; 
        
        while (valid == false){
            player = UI.askString("Left or Right? (L or R)");
            if (player.equals("L") || player.equals("R")){
                valid = true;  
            } else{
                UI.println("Please enter either L or R");
                continue; 
            }
        }
        return player;  
    } 

    public void setUpGame(int numDishes){
        drawRowOutline(1, numDishes);
        for (int i = 0; i < numDishes; i++){
            drawDish(1, i); 
        }
    }

    public void setupGUI(){
        UI.initialise();
        UI.addButton("Clear", UI::clearPanes );
        UI.addButton("Core", this::doCore );
        UI.addButton("Completion", this::doCompletion );
        UI.addButton("Challenge Harder", this::doChallengeHarder );
        UI.addButton("Quit", UI::quit );
    }

    public static void main(String[] args){
        ParameterisedShapes ps = new ParameterisedShapes ();
        ps.setupGUI();
    }

}
