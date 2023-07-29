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

/** The program contains several methods for analysing the readings of the temperature levels over the course of a day.
 *  There are several things about the temperature levels that a user may be interested in: 
 *    The average temperature level.
 *    How the temperatures rose and fell over the day.
 *    The maximum and the minimum temperature levels during the day.
 */
public class TemperatureAnalyser{

    /* analyse reads a sequence of temperature levels from the user and prints out
     *    average, maximum, and minimum level and plots all the levels
     *    by calling appropriate methods
     */
    public void analyse(){
        UI.clearPanes();
        ArrayList<Double> listOfNumbers = UI.askNumbers("Enter levels, end with 'done': ");
        if (listOfNumbers.size() != 0) {
            this.printAverage(listOfNumbers);
            double maxValue = this.maximumOfList(listOfNumbers);
            double minValue = this.minimumOfList(listOfNumbers);
            this.plotLevels(listOfNumbers, maxValue, minValue);

            UI.printf("Maximum level was:  %f\n", this.maximumOfList(listOfNumbers));
            UI.printf("Minimum level was:  %f\n", this.minimumOfList(listOfNumbers));
            UI.printf("Median level was:  %f\n", this.medianOfList(listOfNumbers));
        }
        else {
            UI.println("No readings");
        }
    }

    /** Print the average level
     *   - There is guaranteed to be at least one level,
     *   - The method will need a variable to keep track of the sum, which 
     *     needs to be initialised to an appropiate value.
     *  CORE
     */
    public void printAverage(ArrayList<Double> listOfNumbers) {
        double tempSum = 0.0; 
        double length = listOfNumbers.size();  
        for (int i  = 0; i < listOfNumbers.size(); i++){
             tempSum = tempSum + listOfNumbers.get(i);
        }
        double averageTemp = tempSum / length; 
        UI.printf("Average Temperature was:  %f\n", averageTemp);
    }

    /**
     * Plot a bar graph of the sequence of levels,
     * using narrow rectangles whose heights are equal to the level.
     * [Core]
     *   - Plot the bars.
     * [Completion]
     *   - Draws a horizontal line for the x-axis (or baseline) without any labels.
     *   - Any level greater than 400 should be plotted as if it were just 400, putting an
     *         asterisk ("*") above it to show that it has been cut off.
     * [Challenge:] 
     *   - The graph should also have labels on the axes, roughly every 50 pixels.
     *   - The graph should also draw negative temperature levels correctly.
     *   - Scale the y-axis and the bars so that the largest numbers and the smallest just fit on the graph.
     *     The numbers on the y axis should reflect the scaling.
     *   - Scale the x-axis so that all the bars fit in the window.
     */
    public void plotLevels(ArrayList<Double> listOfNumbers, double maxValue, double minValue) {
        int amountBars = listOfNumbers.size();
        int screenWidth = 600; 
        int screenHeight = 400;
        int base = 420;   //base of the graph
        int left = 50;               //left of the graph
        int step = screenWidth / amountBars;               //distance between plotted points
        double range = maxValue - minValue; 

        double x = 0.0; // num to make bars fit on y axix 
        
        if (minValue < 0){
            x = screenHeight / range; 
        } else {
            x = screenHeight / maxValue;
        }
        
        boolean isNegative=false;
        for(int i=0;i<listOfNumbers.size();i++)
         {
            if(listOfNumbers.get(i)<0)
            {
              isNegative=true;
              break;
            } 
         }
        
        
        
        for (int i  = 0; i < listOfNumbers.size(); i++){ // y axis
            if (isNegative == true){
                int indexOfMax; 
                int numLabels; 
                if (maxValue < minValue * -1){ // finding the index of the max value so I can use it to scale the y axis for negative numbers. 
                    indexOfMax = listOfNumbers.indexOf(minValue); 
                    numLabels = (int)(minValue * -1)/50;
                    
                    for (int t = 0; t < numLabels + 1; t++){ // y axis labels
                        UI.drawLine(left, base - (t * listOfNumbers.get(indexOfMax) * x / numLabels), left - 5, base - (t * listOfNumbers.get(indexOfMax) * x / numLabels)); 
                        UI.drawString(String.valueOf(Math.round(t * minValue/ numLabels)), 10, base - (t * listOfNumbers.get(indexOfMax) * x / numLabels));
                    }
                    
                    for (int t = 0; t < numLabels + 1; t++){ // y axis labels
                        UI.drawLine(left, base + (t * listOfNumbers.get(indexOfMax) * x / numLabels), left - 5, base + (t * listOfNumbers.get(indexOfMax) * x / numLabels)); 
                        UI.drawString(String.valueOf(Math.round(t * minValue *-1/ numLabels)), 10, base + (t * listOfNumbers.get(indexOfMax) * x / numLabels));
                    }
                } else{
                    indexOfMax = listOfNumbers.indexOf(maxValue); 
                    numLabels = (int)maxValue/50; 
                    
                    for (int t = 0; t < numLabels + 1; t++){ // y axis labels
                        UI.drawLine(left, base - (t * listOfNumbers.get(indexOfMax) * x / numLabels), left - 5, base - (t * listOfNumbers.get(indexOfMax) * x / numLabels)); 
                        UI.drawString(String.valueOf(Math.round(t * maxValue / numLabels)), 10, base - (t * listOfNumbers.get(indexOfMax) * x / numLabels));
                    }
                    
                    for (int t = 0; t < numLabels + 1; t++){ // y axis labels
                        UI.drawLine(left, base + (t * listOfNumbers.get(indexOfMax) * x / numLabels), left - 5, base + (t * listOfNumbers.get(indexOfMax) * x / numLabels)); 
                        UI.drawString(String.valueOf(Math.round(t * maxValue * -1 / numLabels)), 10, base + (t * listOfNumbers.get(indexOfMax) * x / numLabels));
                    }
                }
                
                UI.drawLine(left, base - (listOfNumbers.get(indexOfMax) * x) ,left, base + (listOfNumbers.get(indexOfMax) * x));
                
                
                
            } else if (listOfNumbers.get(i) < 0){
                UI.drawLine(left, base - (listOfNumbers.get(i) * x) ,left, base - (maxValue * x)); 
                for (int t = 0; t < 9; t++){ // y axis labels
                    UI.drawLine(left, base - (t * 50), left - 5, base - (t * 50)); 
                    UI.drawString(String.valueOf(Math.round(t * maxValue / 8)), 10, base - (t * 50) + 5);
                }
            }else {
                UI.drawLine(left, base,left, base - (maxValue*x)); 
                for (int t = 0; t < 9; t++){ // y axis labels
                    UI.drawLine(left, base - (t * 50), left - 5, base - (t * 50)); 
                    UI.drawString(String.valueOf(Math.round(t * maxValue / 8)), 10, base - (t * 50) + 5);
                }
            }
            
        }
        
        UI.setColor(Color.red); 
        // COMPLETION
        // for (int i  = 0; i < listOfNumbers.size(); i++){
            // if (listOfNumbers.get(i) < 0){
                // UI.fillRect(left + (i* step) + 2 , base, step - 2, (listOfNumbers.get(i) * -1)); 
            // }else if (listOfNumbers.get(i) > 400){
                // UI.fillRect(left + (i* step) + 2 , base - 400, step - 2, 400); 
                // UI.drawString("*", l  eft + 9 + (i* step), base - 400);
            // }else{
                // UI.fillRect(left+ (i*step) + 2, base - listOfNumbers.get(i), step - 2, listOfNumbers.get(i)); 
            // }
        // }
        
        for (int i  = 0; i < listOfNumbers.size(); i++){
            if (listOfNumbers.get(i) < 0){
                UI.fillRect(left + (i* step) + 2 , base, step - 2, (listOfNumbers.get(i) * -1) * x); 
            }else{
                UI.fillRect(left+ (i*step) + 2, base - listOfNumbers.get(i) * x, step - 2, listOfNumbers.get(i) * x); 
                UI.println(listOfNumbers.get(i) * x); 
            }
        }
        
        
        UI.setColor(Color.black);
        UI.drawLine(left, base , left + screenWidth,base); // x axis
        
        // COMPLETION
        // for (int i = 0; i < (amountBars/2 + 1); i++){ // x axis labels
            // UI.drawLine(left +(i*50), base, left + (i*50), base + 5); 
            // UI.drawString(String.valueOf(i*2), left + (i*50), base + 20);
        // }
        
        for (int i = 0; i < amountBars + 1; i++){ // x axis labels
            UI.drawLine(left +(i*step), base, left + (i*step), base + 5); 
            UI.drawString(String.valueOf(i), left + (i*step), base + 20);
        }
        UI.println("Finished plotting");
    }

    /** Find and return the maximum level in the list
     *   - There is guaranteed to be at least one level,
     *   - The method will need a variable to keep track of the maximum, which
     *     needs to be initialised to an appropriate value.
     *  COMPLETION
     */
    public double maximumOfList(ArrayList<Double> listOfNumbers) {
        double max = 0.0; 
        max = Collections.max(listOfNumbers); 
        return max;
    }

    /** Find and return the minimum level in the list
     *   - There is guaranteed to be at least one level,
     *   - The method will need a variable to keep track of the minimum, which
     *     needs to be initialised to an appropriate value.
     *  COMPLETION
     */
    public double minimumOfList(ArrayList<Double> listOfNumbers) {
        double min = 0.0; 
        min = Collections.min(listOfNumbers); 
        return min;
    }

    
    public double medianOfList(ArrayList<Double> listOfNumbers){
        double median = 0.0; 
        Collections.sort(listOfNumbers); 
        int middle = listOfNumbers.size()/2; 
        median = listOfNumbers.get(middle); 
        
        return median; 
    }


    public void setupGUI() {
        UI.initialise();
        UI.addButton("Analyse", this::analyse );
        UI.addButton("Quit", UI::quit );
    }

    public static void main(String[] args) {
        TemperatureAnalyser ta = new TemperatureAnalyser();
        ta.setupGUI();
    }

}
