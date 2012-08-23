/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import framboos.OutPin;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Rick
 */
@ManagedBean
@SessionScoped
public class MainSessionBean implements Serializable {

    private static boolean DEBUGGING = true;
    private static boolean PRINTTRACE = false;
    private static boolean PRINTSTEPPER = true;
    private static long FAST=200000;
    private static long MEDIUM=700000;
    private static long SLOW=1200000;
    private OutPin pin7Out = null;
    private OutPin pin0Out = null;
    private OutPin pin2Out = null;
    private OutPin pin3Out = null;
    private OutPin pin4Out = null;

    /**
     * Creates a new instance of MainSessionBean
     */
    public MainSessionBean() {
        init();
    }

    private void init() {
        printMessage("\n\nEntering Stepper1\n");
//        pwmInitialized = false;
//        pwmInit();
    }

    public void moveSteps() { // Blink gpio pin 7 a set number of times.
        // Print text between quotes to screen. The \n is a "new line" character,
        // It prints an extra return line to the screen.
        printStepper("\n\nEntering method moveSteps\n");
        
        long delay=FAST;
        
        if (stepSpeed.equals("fast")) {
            delay = FAST;
        } else if (stepSpeed.equals("medium")) {
            delay = MEDIUM;
        } else if (stepSpeed.equals("slow")) {
            delay = SLOW;
        }

        // Set pin 7 to be output.
        // Pin 7 is connected to the step input of the stepper motor controller.
        if (pin7Out == null) {
            pin7Out = new OutPin(7, DEBUGGING, PRINTTRACE);
        }
        
        // This uses pin 0 to set the rotation direction.
        if (rotateClockwise) {
            pin0On();
        } else {
            pin0Off();
        }

        for (int i = 0; i < numberOfSteps; i++) {
            // Print out a message with the loop number after every 1000 steps.
            
            // The "%" is the modulus or "remainder" operator, so 
            // this only prints when i is a multiple of 1000.
            if ((i%1000)==0) {
               printStepper("Step loop, iteration: " + i);
            }


            // Turn off pin 7.
            pin7Out.setValue(false);

            // Delay for number of milliseconds in parentheses.
            // this leaves LED on for a time period.
            sleepNanos(delay);

            // turn on gpio pin 7
            // his provides the rising edge that the stepper controller 
            // sees as a signal to step
            pin7Out.setValue(true);

        }

        // Print "End of method moveSteps" to the screen
        printStepper("End of method moveSteps");
    }
    

    public void pin0On() {
        if (pin0Out == null) {
            pin0Out = new OutPin(0, DEBUGGING, PRINTTRACE);
        }

        // turn off gpio pin 0
        pin0Out.setValue(true);

    }

    public void pin0Off() {
        if (pin0Out == null) {
            pin0Out = new OutPin(0, DEBUGGING, PRINTTRACE);
        }

        // turn off gpio pin 0
        pin0Out.setValue(false);

    }

    private void delayMilliSec(int mS) {  // Wait the number of milliSeconds in the integer variable mS.

        // The "try" statement is paired with the "catch" statement below to catch errors.
        // Errors are called "Exceptions" in Java.
        // If the code between the curly braces after "try" generates the errors listed
        // in the "catch" part, the program immediately jumps down to the code in the catch 
        // part and runs it.  In this case a message just gets printed.
        try {
            printMessage("Delaying for " + mS + " milliSeconds\n");

            Thread.sleep(mS);
        } catch (InterruptedException ie) {
            System.err.println("Setting gpio failed because of an interrupt error.");
        }
    }

    private void printMessage(String s) { // Print the String in s to the screen.
        if (PRINTTRACE) {
            System.out.println(s);
        }
    }
    
    private void printStepper(String s) { // Print the String in s to the screen.
        if (PRINTSTEPPER) {
            System.out.println(s);
        }
    }

    @Override
    @SuppressWarnings("FinalizeDeclaration")
    protected void finalize() throws Throwable {
        if (pin7Out != null) {
            pin7Out.close();
        }
        
        super.finalize();
    }
    
        private boolean rotateClockwise = true;

    /**
     * Get the value of rotateClockwise
     *
     * @return the value of rotateClockwise
     */
    public boolean isRotateClockwise() {
        return rotateClockwise;
    }

    /**
     * Set the value of rotateClockwise
     *
     * @param rotateClockwise new value of rotateClockwise
     */
    public void setRotateClockwise(boolean rotateClockwise) {
        this.rotateClockwise = rotateClockwise;
    }
    
    public static void sleepNanos (long nanoDuration) {
        final long end = System.nanoTime() + nanoDuration;
        long timeLeft = nanoDuration;
        do {
            if (timeLeft > 10000L) {
                    Thread.yield();
            }

            timeLeft = end - System.nanoTime();
        } while (timeLeft > 0);
    }
    private int steps = 0;

    /**
     * Get the value of steps
     *
     * @return the value of steps
     */
    public int getSteps() {
        return steps;
    }

    /**
     * Set the value of steps
     *
     * @param steps new value of steps
     */
    public void setSteps(int steps) {
        this.steps = steps;
    }

    
    
    private String stepSpeed = "fast";

    /**
     * Get the value of stepSpeed
     *
     * @return the value of stepSpeed
     */
    public String getStepSpeed() {
        return stepSpeed;
    }

    /**
     * Set the value of stepSpeed
     *
     * @param stepSpeed new value of stepSpeed
     */
    public void setStepSpeed(String stepSpeed) {
        this.stepSpeed = stepSpeed;
    }

        private Integer numberOfSteps = 0;

    /**
     * Get the value of numberOfSteps
     *
     * @return the value of numberOfSteps
     */
    public Integer getNumberOfSteps() {
        return numberOfSteps;
    }

    /**
     * Set the value of numberOfSteps
     *
     * @param numberOfSteps new value of numberOfSteps
     */
    public void setNumberOfSteps(Integer numberOfSteps) {
        this.numberOfSteps = numberOfSteps;
    }

}
