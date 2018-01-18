/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package micevisualization;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javax.imageio.ImageIO;
import singledecision.*;

/**
 *
 * @author parker
 * 
 * contains the necessary Canvas objects and logic for rendering all types of supported
 * visualizations. One grid class is instantiated as a global variable in the controller.
 * 
 */
public class Grid {
    ArrayList<GridSector> sectors; // the grid sectors
    Canvas background; // the background layer (white)
    ArrayList<Canvas> datalayers; // the layers for representing computationally intensive data
    Canvas gridlines; // the grid lines layer (black lines)
    Canvas gridnumbers; // the grid sector numbers (gray numbers)
    Canvas data; //the data on the map
    Canvas viewerPaneGridNumbers; //checks if user has gridnumbers active
    Canvas viewerPaneGridLines; //checks if user has gridlines active
    
    Boolean animationCancelled; // for storing if the current animation (if applicable) has been cancelled or not
    Boolean animationPaused; //Jacqueline: bool if the animation was paused
    Boolean isRewind; //Christian: bool if the animation is in rewind mode
    
    int overallfinalJ; //an int that saves the current number
    MouseLocTime overalllocTimeData; //a mouseloctime that saves every time stamp
    
    double updatedSpeed; //Used for actively changing the slider value
    public Tree tree;
    public Node rootNode;
    
    // (Parker 3/26/17): allocate the arrays during grid object construction:
    Grid() {
        this.sectors = new ArrayList<>();
        this.datalayers = new ArrayList<>();
        this.animationCancelled = true;
        this.animationPaused = false;
        this.overallfinalJ = 0;
        
        

        /*
        SingleMiceBehaviours root = new SingleMiceBehaviours("behaviour", 0, "null");
        SingleMiceBehaviours node1 = new SingleMiceBehaviours("inactive", 60000000, "greater");
        SingleMiceBehaviours node2 = new SingleMiceBehaviours("active", 60000000, "less");
        SingleMiceBehaviours node3 = new SingleMiceBehaviours("grooming", 180000000, "less");
        SingleMiceBehaviours node4 = new SingleMiceBehaviours("sleeping", 420000000, "greater");
        SingleMiceBehaviours node5 = new SingleMiceBehaviours("drinking", 24, "equal");
        SingleMiceBehaviours node6 = new SingleMiceBehaviours("eating", 25, "equal");
        SingleMiceBehaviours node7 = new SingleMiceBehaviours("socialBeh", 0, "null");
        SingleMiceBehaviours node8 = new SingleMiceBehaviours("Wheel", 31, "equal");

        rootNode = new Node(root);

        Node wheel = new Node(node8);
        rootNode.addChild(wheel);

        Node inactive = new Node(node1);
        rootNode.addChild(inactive); //add inactive to rootNode

        Node active = new Node(node2);
        rootNode.addChild(active); //add active to rootNode

        Node grooming = new Node(node3);
        inactive.addChild(grooming); //add grooming to inactive

        Node sleeping = new Node(node4);
        inactive.addChild(sleeping); //add sleeping to inactive

        Node drinking = new Node(node5);
        active.addChild(drinking); //add drinking to active

        Node eating = new Node(node6);
        active.addChild(eating); //add eating to active

        Node socialBeh = new Node(node7);
        active.addChild(socialBeh); //add eating to active
        */
        SingleMiceBehaviours root = new SingleMiceBehaviours("behaviour", 0, "null");
        SingleMiceBehaviours node1 = new SingleMiceBehaviours("inactive", 6000, "greater");
        SingleMiceBehaviours node2 = new SingleMiceBehaviours("active", 6000, "less");
        SingleMiceBehaviours node7 = new SingleMiceBehaviours("social", 0, "null");

        SingleMiceBehaviours node3 = new SingleMiceBehaviours("inactive", 1, "equal");
        SingleMiceBehaviours node4 = new SingleMiceBehaviours("inactive", 2, "equal");
        SingleMiceBehaviours node9 = new SingleMiceBehaviours("inactive", 5, "equal");
        SingleMiceBehaviours node10 = new SingleMiceBehaviours("inactive", 6, "equal");
        SingleMiceBehaviours node5 = new SingleMiceBehaviours("drinking", 24, "equal");
        SingleMiceBehaviours node6 = new SingleMiceBehaviours("eating", 25, "equal");
        SingleMiceBehaviours node8 = new SingleMiceBehaviours("wheel", 31, "equal");
        SingleMiceBehaviours node11 = new SingleMiceBehaviours("sorter", 29, "equal");
        SingleMiceBehaviours node12 = new SingleMiceBehaviours("sorter", 30, "equal");


        rootNode = new Node(root);

        Node inactive = new Node(node1);
        Node active  = new Node(node2);
        Node eating = new Node(node6);
        Node wheel = new Node(node8);
        Node sorter1 = new Node(node11);
        Node sorter2 = new Node(node12);

        Node inactive1 = new Node(node3);
        Node inactive2 = new Node(node4);
        Node inactive5 = new Node(node9);
        Node inactive6 = new Node(node10);
        Node drinking = new Node(node5);
        Node social = new Node(node7);

        rootNode.addChild(wheel); //add wheel to rootNode
        rootNode.addChild(sorter1); //add sorter1 to rootNode
        rootNode.addChild(sorter2); //add sorter2 to rootNode
        rootNode.addChild(eating); //add eating to rootNode
        rootNode.addChild(drinking); //add drinking to rootNode
        rootNode.addChild(inactive1); //add inactive1 to rootNode
        rootNode.addChild(inactive2); //add inactive2 to rootNode
        rootNode.addChild(inactive5); //add inactive5 to rootNode
        rootNode.addChild(inactive6); //add inactive6 to rootNode
        rootNode.addChild(inactive); //add inactive to rootNode
        rootNode.addChild(active); //add active to rootNode
        //active.addChild(social);

        tree = new Tree(rootNode);

        // Get the pre-order traversal
        List<Node> preOrder = tree.getPreOrderTraversal();
        for (Node i : preOrder) {
            //System.out.println(i.getData().behaviour);
        }

    }//end Grid
    
    // (Parker 3/26/17): add a GridSector object to the sectors array
    Boolean addSector(GridSector gs) {
        return this.sectors.add(gs);
    }//end addSector
    
    // (Parker 3/26/17): return the matching grid sector by its grid index. The grid is set up like this:
    // 1 5 9  13 17 21 
    // 2 6 10 14 18 22 25 (25 is displayed as vertically centered)
    // 3 7 11 15 19 23
    // 4 8 12 16 20 24
    GridSector getSectorByGridIndex(int index) {
        for (int i = 0; i < this.sectors.size(); ++i) {
            if (this.sectors.get(i).gridIndex != index) {
            } else {
                return this.sectors.get(i);
            }//end else
        }//end for
        return null;
    }//end getSectorByGridIndex
    public void updateSliderSpeed(double speed){
        updatedSpeed = speed;
    }
  
    // (Parker 3/26/17): draw a white background for each grid sector:
    void drawSectorsBackground(Canvas c) {
        GraphicsContext gc = c.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        for (int i = 0; i < this.sectors.size(); ++i)
            gc.fillRect(sectors.get(i).x, sectors.get(i).y, sectors.get(i).w, sectors.get(i).h);
    }//end drawSectorsBackground
    
    // (Parker 3/26/17): draw black gridlines around each grid sector:
    void drawSectorsGridlines(Canvas c) {
        GraphicsContext gc = c.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        for (int i = 0; i < this.sectors.size(); ++i)
            gc.strokeRect(sectors.get(i).x, sectors.get(i).y, sectors.get(i).w, sectors.get(i).h);
    }//end drawSectorsGridlines
    
    // (Parker 3/26/17): draw sector numbers in each grid sector:
    void drawSectorsNumbers(Canvas c) {
        GraphicsContext gc = c.getGraphicsContext2D();
        gc.setStroke(Color.GRAY);
        for (int i = 0; i < this.sectors.size(); ++i)
            gc.strokeText(String.valueOf(this.sectors.get(i).gridIndex), this.sectors.get(i).x + this.sectors.get(i).w*0.5, this.sectors.get(i).y + this.sectors.get(i).h*0.9);
    }//end drawSectorsNumbers
    
    
    // (Parker 3/26/17): create a new this.gridlines Canvas object. use setId for the purpose of selecting
    // it (the object) in the list of viewerPane's children at some point in the future. Call this.drawSectorsGridlines on 
    // the Canvas object and add it to viewerPane's children.
    void makeGridLinesCanvas(StackPane viewerPane, double width, double height) {
            this.gridlines = new Canvas(width, height);
            this.gridlines.setId("gridlines");
            this.drawSectorsGridlines(this.gridlines);
            viewerPane.getChildren().add(this.gridlines);   
    }//end makeGridLinesCanvas
    
    // (Parker 3/26/17): create a new this.gridnumbers Canvas object. Use setId for the purpose of selecting
    // it (the object) in the list of viewerPane's children at some point in the future. Call this.drawSectorsNumbers on 
    // the Canvas object and add it to viewerPane's children.
    void makeGridNumbersCanvas(StackPane viewerPane, double width, double height) {
            this.gridnumbers = new Canvas(width, height);
            this.gridnumbers.setId("gridnumbers");
            this.drawSectorsNumbers(this.gridnumbers);
            viewerPane.getChildren().add(this.gridnumbers);   
    }//end makeGridNumbersCanvas
    
    // (Parker 3/26/17): search viewerPane's children for the #gridlines Canvas object. If the gridlines Canvas
    // object is not a child of viewerPane, create it by calculating current width and height and using
    // the makeGridLinesCanvas function. If it is a child, remove it from viewerPane by its selector id.
    void toggleGridLines(StackPane viewerPane) {
        String id = "#gridlines";
        if ((Canvas)viewerPane.lookup(id) == null) {
            System.out.println("toggling grid lines on");
            double width = calculateDimensions(viewerPane).w;
            double height = calculateDimensions(viewerPane).h;
            makeGridLinesCanvas(viewerPane, width, height);
        }//end if
        else {
            System.out.println("toggling grid lines off");
            viewerPane.getChildren().remove(viewerPane.lookup(id));
        }//end else
    }//end toggleGridLines

    // (Parker 3/26/17): search viewerPane's children for the #gridnumbers Canvas object. If the gridnumbers Canvas
    // object is not a child of viewerPane, create it by calculating current width and height and using
    // the makeGridNumbersCanvas function. If it is a child, remove it from viewerPane by its selector id.    
    void toggleGridNumbers(StackPane viewerPane) {
        String id = "#gridnumbers";
        
        if ((Canvas)viewerPane.lookup(id) == null) {
            System.out.println("toggling grid numbers on");
            double width = calculateDimensions(viewerPane).w;
            double height = calculateDimensions(viewerPane).h;
            makeGridNumbersCanvas(viewerPane, width, height);
        }//end if
        else {
            System.out.println("toggling grid numbers off");
            viewerPane.getChildren().remove(viewerPane.lookup(id));
        }//end else
    }//end toggleGridNumbers
    
    // (Parker 3/26/17): calculate the width and height of the canvas layers, maintaining a 7:4 aspect ratio,
    // and return the width and height as a Dimensions object
    Dimensions calculateDimensions(StackPane viewerPane) {
        // (Parker 3/25/17): calculate values to create a margin around the canvas:
        double width = viewerPane.getWidth() - 30;
        double height = viewerPane.getHeight() - 30;
        
        // (Parker 3/25/17): maintain a 7:4 aspect ratio in the canvas dimensions:
        if (width > height)
            width = height;
        
        else if (width < height)
            height = width;
        
        height = (height/7)*4;
        
        Dimensions d = new Dimensions(width, height);
        return d;
    }//end calculateDimensions
    
    
    // (Parker 3/26/17): reset the rendered visualization. Redraw() draws all of the non-computationally intensive layers
    // of the visualization, including the background, gridlines, and gridnumbers Canvas objects.
    // The children of viewerPane, the visualization's parent object, are cleared, and so are the
    // sectors and datalayers. The newly created Canvas objects are then attached as children nodes
    // to viewerPane.
    /**
     * 
     * @param viewerPane the parent object that will contain the various Canvas layers
     * @param showGridNumbers the Boolean value taken from the showGridLinesCheckBox control
     * @param showGridLines the Boolean value taken from the showGridNumbersCheckBox control
     * @param isGenerated unused 
     */
    void redraw(StackPane viewerPane, Boolean showGridNumbers, Boolean showGridLines, Boolean isGenerated) {

        // (Parker 3/25/17): clear out the old content in the viewerPane object and reset the Grid data members:
        viewerPane.getChildren().clear();
        this.sectors.clear();
        this.background = null;
        this.datalayers.clear();
        this.gridlines = null;
        this.gridnumbers = null;
        
        // (Parker 3/26/17): calculate the dimensions of the Canvas layers, based on viewerPane
        double width = calculateDimensions(viewerPane).w;
        double height = calculateDimensions(viewerPane).h;
        
        /* (Parker 3/26/17): the rows and columns of the physical grid:
        1 5 9  13 17 21 
        2 6 10 14 18 22 25 (25 is displayed as vertically centered)
        3 7 11 15 19 23
        4 8 12 16 20 24 */
        int ROWS = 4;
        // 6 columns containing 4 sectors each, plus one column containing the feeding station sector
        int COLS = 7;
        
        /* (Parker 3/25/17): create a 7x4 grid representing the experiment enclosure;
        grid sectors in the first 6x4 spaces are regular sectors, while column 7x4
        contains one grid sector, offset from the others, which represents the feeding station.
        Add the gridSectors to the grid's gridSectors array with addSector(). */
        double currentY;
        double currentX;
        
        for (int i = 0; i < ROWS; ++i) {
            currentY = (height / ROWS) * i;
            for (int j = 0; j < COLS-1; ++j) { // handle the first 24 grid sectors (#s 0 - 23)
                currentX = (width / COLS) * j;
                GridSector gs = new GridSector(currentX, currentY, width/COLS, height/ROWS, (j*4 + i) + 1); // unitLabel (the gridSector id# from the data set) ranges from 1 - 25, hence (j*4 + i) + 1
                this.addSector(gs);
            }//end for
        }//end for
        
        // handle the last grid sector, the feeding station (# 24):
        GridSector gs = new GridSector((width / COLS) * 6, height/2 - ((height/ROWS)/2), width/COLS, height/ROWS, 25);  
        this.addSector(gs);
        
        /* (Parker 3/25/17): setup the visualization with several layers of canvas objects:
        layer 0: background
        layer 1, 2, 3 ... : drawing layers for computationally intensive work
        layer n-1: grid sector numbers overlay
        layer n: gridlines overlay */
        this.background = new Canvas(width, height);
        this.background.setId("background");
        this.drawSectorsBackground(this.background);
        boolean add = viewerPane.getChildren().add(this.background);
        
        if (showGridNumbers)
            makeGridNumbersCanvas(viewerPane, width, height);
        
        if (showGridLines)
            makeGridLinesCanvas(viewerPane, width, height);
    }//end redraw
    
    /**
     * 
     * @author: Parker / Alex
     * 
     * This function consolidates the Canvas layers in the grid object into one visible layer via a BufferedImage object
     * and saves its data to an image file. The save file location is passed as a parameter,
     * as well as the file extension (which represents the type of image format).
     * 
     * @param viewerPane
     * @param file
     * @param ext
     * @throws AWTException 
     */
    void exportFrame(StackPane viewerPane, File file, String ext) throws AWTException {
        // Image capture starting x and y coordintes (default coordinates)
        int x = 0;
        int y = 0;
        
        // Image Dimensions (default resolution)
        int IMG_W = 455;
        int IMG_H = 260;
        
        if (this.background != null) {
            //  Retrieves actual width and height for image
            IMG_W = (int) this.background.getWidth();
            IMG_H = (int) this.background.getHeight();
            
            // Retrieves x and y coordinates of the rectangular area containing the visualization to capture.
            Bounds bounds = this.background.getBoundsInLocal();
            Bounds screenBoundaries = this.background.localToScreen(bounds);
            x = (int) screenBoundaries.getMinX();
            y = (int) screenBoundaries.getMinY();
        }//end if
       
        // Capture screen to store as an image of users choosing
        BufferedImage screencapture = new Robot().createScreenCapture(new java.awt.Rectangle(x, y, IMG_W, IMG_H));

        try {
            // Write to file path selected
            ImageIO.write(screencapture, ext, file);
        }//end try
        
        catch (IOException | NullPointerException ex) {
            Logger.getLogger(AppStageController.class.getName()).log(Level.SEVERE, null, ex);
        }//end catch
    }//end exportFrame
    
    /**
     * 
     * @author Parker
     * 
     * the purpose of this function is to reset any data contained within GridSector objects
     * of the GridSector array of the Grid class. This is NOT for resetting map-independent parameters
     * such as x,y,w,h, and gridIndex, but instead for reseting map-specific parameters such as
     * finalTotalDuration 
     * 
     */
    void resetGridSectorsInfo() {
        for (int i = 0; i < this.sectors.size(); ++i) {
            this.sectors.get(i).finalTotalDuration = 0;
            this.sectors.get(i).currentTotalDuration = 0;
        }//end for
    }//end resetGridSectorsInfo
    
    /**
     * 
     * Since the unitLabel string refers to which grid sector a mouse is in for the current data row,
     * isolate the numeric portion of this string and return it as an integer. This allows for the 
     * unitLabel to be either an integer value or a string containing an integer value.
     * 
     * @param str
     * @return 
     */
    int getGridSectorIndexFromUnitLabel(String str) {
        return Integer.parseInt(str.replaceAll("[^0-9]", ""));
    }
    
    /**
     * 
     * @author Parker
     * 
     * Calculate each individual GridSector's finalTotalDuration, write each finalTotalDuration to
     * the GridSector's finalTotalDuration parameter, and return the maxDuration.
     * 
     * @param viewerPane
     * @param mice
     * @param start
     * @param stop 
     * @return maxDuration
     */
    double calculateGridSectorHeatMapInfo(StackPane viewerPane, ArrayList<Mouse> mice, Date start, Date stop) {
        Date mouseDate;
        // (Parker 3/26/17): store the cumulative event durations of the most active
        // grid sector, for the purposes of calibrating the heat map colors:
        double maxDuration = 0;
        // (Parker 3/26/17): loop through the selected mice and process their 
        // timestamp, event duration, and grid sector data. Ensure that only data within
        // the range between the start and stop Date parameters are processed. 
        for (int i = 0; i < mice.size(); ++i) {
            for (int j = 0; j < mice.get(i).locTimeData.size() && mice.get(i).locTimeData.get(j).timestamp.compareTo(stop) <= 0; ++j) {
                mouseDate = mice.get(i).locTimeData.get(j).timestamp;
                
                if (mouseDate.compareTo(start) >= 0) {
                    
                    /* Extract the numeric value to get the integer gridIndex from the unitLabel locTimeData parameter: */
                    int gridSectorIndex = getGridSectorIndexFromUnitLabel(mice.get(i).locTimeData.get(j).unitLabel);
                    
                    /* use the gridIndex to retrieve the matching gridSector object from the grid's gridSector array: */
                    GridSector gs = getSectorByGridIndex(gridSectorIndex);
                    
                    /* add the current record's event duration to the selected GridSector's finalTotalDuration parameter */
                    gs.finalTotalDuration += mice.get(i).locTimeData.get(j).eventDuration;
                    
                    /* calculate the largest gridSector finalTotalDuration, for the purpose of calibrating the heatmap colors: */
                    if (gs.finalTotalDuration > maxDuration)
                        maxDuration = gs.finalTotalDuration;
                }//end if
            }//end for
        }//end for
        return maxDuration;
    }//end calculateGridSectorHeatMapInfo
    
    /**
     * 
     * @author: Parker
     * 
     * create a heat map based on the selected mice and starting and stopping timestamp indices.
     * 
     * 
     * @param viewerPane the parent object that will contain the Canvas objects as its children
     * @param mice the mice to visualize
     * @param start a starting index from the range of data in the dataset
     * @param stop an ending index from the range of data in the dataset
     */
    void staticHeatMap(StackPane viewerPane, ArrayList<Mouse> mice, Date start, Date stop) {
        // (Parker 4/3/17): calculate the maximumDuration of mouse activity in the most active GridSector,
        // and calculate each GridSector's finalTotalDuration:
        double maxDuration = calculateGridSectorHeatMapInfo(viewerPane, mice, start, stop);
        
        // calculate width and height of the Canvas object:
        double width = calculateDimensions(viewerPane).w;
        double height = calculateDimensions(viewerPane).h;
        
        data = new Canvas(width, height);
        
        // get the graphics context of the data Canvas in order to perform drawing:
        GraphicsContext dataCanvasContext = data.getGraphicsContext2D();
        
        // loop through the grid sectors and draw its finalTotalDuration as a shade of color: 
        for (int i = 0; i < this.sectors.size(); ++i) {
            double opacity = 0.0000000;
            /* calculate the opacity of the gridSector's color: */
            if (this.sectors.get(i).finalTotalDuration != 0) {
                /* the gridSector with the maxDuration will have the darkest shade, so 
                gridSectors with less activity will have lighter shades: */
                opacity = this.sectors.get(i).finalTotalDuration / maxDuration;
            }//end if
            //Increase gradient by 20 percent: Christian
            if(opacity > 0.83){
                opacity = 1.0;
            }
            else{
                opacity *= 1.2;
            }
            /* perform the drawing of the shade onto the Canvas for this gridSector */
            if(mice.size()>1) dataCanvasContext.setFill(Color.web("#FFA500", opacity));
            else{
                double r, g, b;
                r = mice.get(0).getColor().getRed();
                g = mice.get(0).getColor().getGreen();
                b = mice.get(0).getColor().getBlue();
                dataCanvasContext.setFill(Color.color(r,g,b, opacity));
            }//end else
            dataCanvasContext.fillRect(sectors.get(i).x, sectors.get(i).y, sectors.get(i).w, sectors.get(i).h);
        }//end for
        
        // check for and remove any pre-existing heat map layer:
        String heatMapId = "heatmap";
        Canvas viewerPaneHeatMapLayer = (Canvas)viewerPane.lookup("#" + heatMapId);
        
        if (viewerPaneHeatMapLayer != null)
            viewerPane.getChildren().remove(viewerPane.lookup("#" + heatMapId));
        
        // add the Canvas layer containing the heatmap to the grid object itself, and then to the viewerPane:
        data.setId(heatMapId);
        this.datalayers.add(data);
        viewerPane.getChildren().add(data);
        
        //Checks grid line/numbers if they are active or not; if they are active, move them to the front of the Canvas layers
        moveInfoLayersToFront(viewerPane);
    }//end staticHeatMap
    /**
     * author Jacqueline
     * Paused the animation it changes the text and the image icon.
     * @param button 
     */
    void pauseAnimation(Button button){
        button.setText("Animation Paused");
        this.animationPaused = true;
        this.animationCancelled = true;
        Image buttonIcon = new Image("resources/pause.png", 16,16,true,true);
        button.setGraphic(new ImageView(buttonIcon));
       
        
    }
    /**
     * 
     * author Parker
     * 
     * Reset the state of relevant GUI items when an animation is cancelled or 
     * naturally stops. Currently only the playback controls are reset when this happens
     * (not the visualization itself).
     * 
     * @param button 
     */
    void stopAnimation(Button button) {
        button.setText("Play Animation");
        this.animationCancelled = true;
        Image buttonIcon = new Image("resources/play.png", 16, 16, true, true);
        button.setGraphic(new ImageView(buttonIcon));
        overallfinalJ = 0;
        isRewind = false;

    }//end stopAnimation
    
    
    
    /**
     * 
     * @author Parker
     * 
     * Generate an animated Heat map. Use the coordination of several GUI controls
     * in the Visualization options, the Grid class, and Mouse objects to achieve an
     * animation. The drawing of the animation must occur in a separate thread in order
     * to show constant progress updates in the GUI, so Service and Task objects are
     * used to achieve concurrency. The speed of the animation is controlled by
     * delay introduced between frames, ranging from 1 - 1000 milliseconds.
     * 
     * The exportFolder parameter is optional (may contain a File object or null value).
     * If it is a File object, the function will export each frame of the animation
     * to the directory specified by exportFolder.
     * 
     * @param viewerPane
     * @param generateButton
     * @param currentAnimationFrame
     * @param leftStatus
     * @param mice
     * @param start
     * @param stop
     * @param speed
     * @param exportFolder
     * @throws InterruptedException 
     */
    void animatedHeatMap(StackPane viewerPane, Button generateButton, TextArea currentAnimationFrame, Label leftStatus, ArrayList<Mouse> mice, Date start, Date stop, double speed, File exportFolder, boolean rewind) throws InterruptedException {
        // (Parker 4/3/17): calculate the maximumDuration of mouse activity in the most active GridSector,
        // and calculate each GridSector's finalTotalDuration:
        isRewind = rewind;
        double maxDuration = calculateGridSectorHeatMapInfo(viewerPane, mice, start, stop);
        System.out.println("MAX DURATION: " + String.valueOf(maxDuration));
        
        //Checks grid line/numbers if they are active or not; if they are active, move them to the front of the Canvas layers
        moveInfoLayersToFront(viewerPane);

        // Establish a new service:
        Service<Void> service;
        service = new Service<Void>() {
            long elapsedTs = 0; // Create a timestamp based timer to measure the duration of the animation
            
            long frameCount = 0; // Count how many frames the animation has rendered
            
            @Override
            // create a new task that will comprise the new thread of the animation:
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        /* PUT LOOPING LOGIC HERE */
                        
                        long startTs = System.currentTimeMillis(); // begin a timer to record the amount of time the generation takes
                        
                        /* (Parker): in order to display the data rows in order, extract all the MouseLocTime data from
                        the Mouse objects in the mice function parameter and store the collective MouseLocTime data in
                        a new ArrayList. Only data rows that are within the start and stop indices are included. */
                        ArrayList<MouseLocTime> locTimeData = Mice.getMiceDataRowsFromRange(mice, start, stop);
                        
                        // (Parker): sort the collective MouseLocTime data so that it is in order from earliest Date timestamp
                        // to latest Date timestamp:
                        Collections.sort(locTimeData);
                        
                        // (Parker): begin looping through the sorted locTimeData (the data row entries). Ensure the loop
                        // stops at the stopping index:
                    if(isRewind){
                        for (int j = overallfinalJ; j > 0; --j) {
                            // if the animation was cancelled (by setting the Grid class' animationCancelled property),
                            // then cancel the animation thread:
                            if (animationCancelled == true)
                                this.cancel(true);
                           
                            if (isCancelled())
                                break;
                            // since we need to reference the 'j' incrementor inside the Platform.runlater code,
                            // we need to make a new final variable that is a copy of j's current value:
                            final int finalJ = j;
                            overallfinalJ = j;
                            // Create a Platform to run code in the background on the new thread;
                            // this is where the data layer of the Grid's Canvas objects gets updated:
                            Platform.runLater(() -> {
                                /* PUT GUI UPDATE LOGIC HERE */
                                
                                /* (Parker 4/3/17): display to the user the current frame being rendered: */
                                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SSS");
                                currentAnimationFrame.setText(formatter.format(locTimeData.get(finalJ).timestamp));
                                
                                /* perform string manipulation to get the integer gridIndex from the unitLabel locTimeData parameter: */
                                int gridSectorIndex = getGridSectorIndexFromUnitLabel(locTimeData.get(finalJ).unitLabel);
                                
                                /* use the gridIndex to retrieve the matching gridSector object from the grid's gridSector array: */
                                GridSector gs = getSectorByGridIndex(gridSectorIndex);
                                
                                /* add the current record's event duration to the selected GridSector's currentTotalDuration parameter */
                                gs.currentTotalDuration += locTimeData.get(finalJ).eventDuration;
                                
                                /* get the maximum opacity for this grid sector (AKA this GridSector's value in the static heat map) */
                                double baseOpacity = gs.finalTotalDuration / maxDuration;
                                
                                /* now create a shade of the baseOpactiy by applying the ratio of the finalTotalDuration vs currentTotalDuration */
                                double currentOpacity = ((double)gs.currentTotalDuration / (double)gs.finalTotalDuration) * baseOpacity;
                                
                                /* attempt to reference the heatmap data layer of the viewerPane's children;
                                if this is not possible, create a new heatmap data layer and add it to the viewerPane: */
                                String heatMapId = "heatmap";
                                Canvas viewerPaneHeatMapLayer = (Canvas)viewerPane.lookup("#" + heatMapId);
                                
                                if (viewerPaneHeatMapLayer == null) {
                                    // calculate width and height of the Canvas object:
                                    double width = calculateDimensions(viewerPane).w;
                                    double height = calculateDimensions(viewerPane).h;
                                    data = new Canvas(width, height);
                                    data.setId(heatMapId);
                                    //this.datalayers.add(data);
                                    viewerPane.getChildren().add(data);
                                    //Checks grid line/numbers if they are active or not; if they are active, move them to the front of the Canvas layers
                                    moveInfoLayersToFront(viewerPane);
                                }//end if
                                
                                viewerPaneHeatMapLayer = (Canvas)viewerPane.lookup("#" + heatMapId);
                                
                                // get the graphics context of the heat map data layer for the purpose of drawing:
                                GraphicsContext dataCanvasContext = viewerPaneHeatMapLayer.getGraphicsContext2D();
                                
                                /* perform the drawing of the shade onto the Canvas for this gridSector: */
                                // clear out the current GridSector's old shade:
                                dataCanvasContext.clearRect(gs.x, gs.y, gs.w, gs.h);
                                // fill the current GridSector with the calculated shade:
                                if(mice.size()>1) dataCanvasContext.setFill(Color.web("#FFA500", currentOpacity));
                                else{
                                    double r, g, b;
                                    r = mice.get(0).getColor().getRed();
                                    g = mice.get(0).getColor().getGreen();
                                    b = mice.get(0).getColor().getBlue();
                                    dataCanvasContext.setFill(Color.color(r,g,b, currentOpacity));
                                }//end else
                                dataCanvasContext.fillRect(gs.x, gs.y, gs.w, gs.h);
                                
                                // If the exportFolder parameter contains a File object, create a new File.
                                // Using the path of the exportFolder appeneded to the number of the current frame, create a filename
                                // unique to the current frame.
                                if (exportFolder != null) {
                                    try {
                                        File exportFile = new File(exportFolder.getPath() + "\\" + frameCount + ".png");
                                        System.out.println("Frame filename: " + exportFile.getPath());
                                        exportFrame(viewerPane, exportFile, "png");
                                    } catch (AWTException ex) {
                                        Logger.getLogger(Grid.class.getName()).log(Level.SEVERE, null, ex);
                                    }//end catch
                                }//end if
                                
                                // increment the number of frames rendered in the animation:
                                frameCount++;
                            });
                            // delay the animation thread by "speed" number of milliseconds;
                            // this value should come directly from the Frame Delay GUI slider control:
                         
                                if(updatedSpeed > 0){
                                    Thread.sleep((int)updatedSpeed);
                                }
                                else{
                                    Thread.sleep((int)speed);
                                }
                        }//end for
                    }
                    else{
                        for (int j = overallfinalJ; j < locTimeData.size(); ++j) {
                            // if the animation was cancelled (by setting the Grid class' animationCancelled property),
                            // then cancel the animation thread:
                            if (animationCancelled == true)
                                this.cancel(true);
                           
                            if (isCancelled())
                                break;
                            // since we need to reference the 'j' incrementor inside the Platform.runlater code,
                            // we need to make a new final variable that is a copy of j's current value:
                            final int finalJ = j;
                            overallfinalJ = j;
                            // Create a Platform to run code in the background on the new thread;
                            // this is where the data layer of the Grid's Canvas objects gets updated:
                            Platform.runLater(() -> {
                                /* PUT GUI UPDATE LOGIC HERE */
                                
                                /* (Parker 4/3/17): display to the user the current frame being rendered: */
                                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SSS");
                                currentAnimationFrame.setText(formatter.format(locTimeData.get(finalJ).timestamp));
                                
                                /* perform string manipulation to get the integer gridIndex from the unitLabel locTimeData parameter: */
                                int gridSectorIndex = getGridSectorIndexFromUnitLabel(locTimeData.get(finalJ).unitLabel);
                                
                                /* use the gridIndex to retrieve the matching gridSector object from the grid's gridSector array: */
                                GridSector gs = getSectorByGridIndex(gridSectorIndex);
                                
                                /* add the current record's event duration to the selected GridSector's currentTotalDuration parameter */
                                gs.currentTotalDuration += locTimeData.get(finalJ).eventDuration;
                                
                                /* get the maximum opacity for this grid sector (AKA this GridSector's value in the static heat map) */
                                double baseOpacity = gs.finalTotalDuration / maxDuration;
                                
                                /* now create a shade of the baseOpactiy by applying the ratio of the finalTotalDuration vs currentTotalDuration */
                                double currentOpacity = ((double)gs.currentTotalDuration / (double)gs.finalTotalDuration) * baseOpacity;
                                
                                /* attempt to reference the heatmap data layer of the viewerPane's children;
                                if this is not possible, create a new heatmap data layer and add it to the viewerPane: */
                                String heatMapId = "heatmap";
                                Canvas viewerPaneHeatMapLayer = (Canvas)viewerPane.lookup("#" + heatMapId);
                                
                                if (viewerPaneHeatMapLayer == null) {
                                    // calculate width and height of the Canvas object:
                                    double width = calculateDimensions(viewerPane).w;
                                    double height = calculateDimensions(viewerPane).h;
                                    data = new Canvas(width, height);
                                    data.setId(heatMapId);
                                    //this.datalayers.add(data);
                                    viewerPane.getChildren().add(data);
                                    //Checks grid line/numbers if they are active or not; if they are active, move them to the front of the Canvas layers
                                    moveInfoLayersToFront(viewerPane);
                                }//end if
                                
                                viewerPaneHeatMapLayer = (Canvas)viewerPane.lookup("#" + heatMapId);
                                
                                // get the graphics context of the heat map data layer for the purpose of drawing:
                                GraphicsContext dataCanvasContext = viewerPaneHeatMapLayer.getGraphicsContext2D();
                                
                                /* perform the drawing of the shade onto the Canvas for this gridSector: */
                                // clear out the current GridSector's old shade:
                                dataCanvasContext.clearRect(gs.x, gs.y, gs.w, gs.h);
                                // fill the current GridSector with the calculated shade:
                                if(mice.size()>1) dataCanvasContext.setFill(Color.web("#FFA500", currentOpacity));
                                else{
                                    double r, g, b;
                                    r = mice.get(0).getColor().getRed();
                                    g = mice.get(0).getColor().getGreen();
                                    b = mice.get(0).getColor().getBlue();
                                    dataCanvasContext.setFill(Color.color(r,g,b, currentOpacity));
                                }//end else
                                dataCanvasContext.fillRect(gs.x, gs.y, gs.w, gs.h);
                                
                                // If the exportFolder parameter contains a File object, create a new File.
                                // Using the path of the exportFolder appeneded to the number of the current frame, create a filename
                                // unique to the current frame.
                                if (exportFolder != null) {
                                    try {
                                        File exportFile = new File(exportFolder.getPath() + "\\" + frameCount + ".png");
                                        System.out.println("Frame filename: " + exportFile.getPath());
                                        exportFrame(viewerPane, exportFile, "png");
                                    } catch (AWTException ex) {
                                        Logger.getLogger(Grid.class.getName()).log(Level.SEVERE, null, ex);
                                    }//end catch
                                }//end if
                                
                                // increment the number of frames rendered in the animation:
                                frameCount++;
                            });
                            // delay the animation thread by "speed" number of milliseconds;
                            // this value should come directly from the Frame Delay GUI slider control:
                         
                                if(updatedSpeed > 0){
                                    Thread.sleep((int)updatedSpeed);
                                }
                                else{
                                    Thread.sleep((int)speed);
                                }
                        }//end for
                    }
                        long endTs = System.currentTimeMillis(); // stop the timer
                        elapsedTs = endTs - startTs; // get the elapsed time of the generation duration
                        return null; // we aren't returning a specific value from the task, so return null
                    }//end call
                    
                    // if the animation was cancelled by the user, respond gracefully:
                    @Override protected void cancelled() {
                        super.cancelled();
                        updateMessage("Cancelled!");
                    }//end cancelled
                    
                    // if the animation succeeded, respond gracefully:
                    @Override protected void succeeded() {
                        stopAnimation(generateButton);
                        
                        // output a text status update to the user containing the duration of the animation:
                        String describeMice = (mice.size() > 1) ? "mice" : "mouse";
                        leftStatus.setText("Finished generating an animated heat map of " + mice.size() + " " + describeMice + " in " + elapsedTs + " milliseconds. " + frameCount + " frames rendered.");
                    }//end succeeded
                };
            }
        };
        // start the animation thread:
        service.start();
    }//end animatedHeatMap
    
       /**
     * 
     * @author Parker
     * 
     * Generate an animated Heat map. Use the coordination of several GUI controls
     * in the Visualization options, the Grid class, and Mouse objects to achieve an
     * animation. The drawing of the animation must occur in a separate thread in order
     * to show constant progress updates in the GUI, so Service and Task objects are
     * used to achieve concurrency. The speed of the animation is controlled by
     * delay introduced between frames, ranging from 1 - 1000 milliseconds.
     * 
     * The exportFolder parameter is optional (may contain a File object or null value).
     * If it is a File object, the function will export each frame of the animation
     * to the directory specified by exportFolder.
     * 
     * @param viewerPane
     * @param generateButton
     * @param currentAnimationFrame
     * @param leftStatus
     * @param mice
     * @param start
     * @param stop
     * @param speed
     * @param exportFolder
     * @throws InterruptedException 
     */
    void animatedOverlayMap(StackPane viewerPane, Button generateButton, TextArea currentAnimationFrame, Label leftStatus, ArrayList<Mouse> mice, Date start, Date stop, double speed, File exportFolder, boolean rewind) throws InterruptedException {
        // (Parker 4/3/17): calculate the maximumDuration of mouse activity in the most active GridSector,
        // and calculate each GridSector's finalTotalDuration:
        isRewind = rewind;
        double maxDuration = calculateGridSectorHeatMapInfo(viewerPane, mice, start, stop);
        System.out.println("MAX DURATION: " + String.valueOf(maxDuration));
        
        //Checks grid line/numbers if they are active or not; if they are active, move them to the front of the Canvas layers
        moveInfoLayersToFront(viewerPane);

        // Establish a new service:
        Service<Void> service = new Service<Void>() {
            long elapsedTs = 0; // Create a timestamp based timer to measure the duration of the animation
            
            long frameCount = 0; // Count how many frames the animation has rendered
            
            @Override
            // create a new task that will comprise the new thread of the animation:
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        /* PUT LOOPING LOGIC HERE */
                        
                        long startTs = System.currentTimeMillis(); // begin a timer to record the amount of time the generation takes
                        
                        Date mouseDate;
                        
                        /* (Parker): in order to display the data rows in order, extract all the MouseLocTime data from 
                        the Mouse objects in the selected mice function parameter and store the collective MouseLocTime data in
                        a new ArrayList. */
                        HashMap<Date, Mouse> mouse_color = new HashMap<>();
                        ArrayList<MouseLocTime> locTimeData = new ArrayList<>();
                        
                        for (int i = 0; i < mice.size(); ++i) {
                            for (int j = 0; j < mice.get(i).locTimeData.size(); ++j) {
                                locTimeData.add(mice.get(i).locTimeData.get(j));
                                mouse_color.put(mice.get(i).locTimeData.get(j).timestamp,mice.get(i));
                            }//end for
                        }//end for
                        
                        
                        //(Joshua) Hashmap to store the current position on the grid for each mouse as the animation iterates
                        HashMap<Mouse, GridSector> current_mouse_positions = new HashMap<>();
                        mouse_color.entrySet().forEach((entry) -> {
                            current_mouse_positions.put(entry.getValue(), null);
                        });
                        
                        // (Parker): sort the collective MouseLocTime data so that it is in order from earliest Date timestamp
                        // to latest Date timestamp:
                        Collections.sort(locTimeData);
                        
                        // arraylist to store the vectorframe objects
                        ArrayList<VectorFrame> line_frames = new ArrayList<>();
                        
                    if(isRewind){
                        for (int j = overallfinalJ; j > 0; --j) {
                            // if the animation was cancelled (by setting the Grid class' animationCancelled property),
                            // then cancel the animation thread:
                            if (animationCancelled == true)
                                this.cancel(true);
                           if (isCancelled())
                                break;
                           // since we need to reference the 'j' incrementor inside the Platform.runlater code, 
                           // we need to make a new final variable that is a copy of j's current value:
                            final int finalJ = j;
                            overallfinalJ = j;
                            // determine if the current data row (MouseLocTime object) falls within the range of the Start and Stop indicies:
                            mouseDate = locTimeData.get(j).timestamp;
                            // if the current data row is within the Start and Stop indicies, perform the
                            // color shade calculations and update the GUI inside the Platform.runLater() function:
                            
                            //increment the frame count for each mouse position
                            for (int position = 0; position < line_frames.size(); position ++)
                                line_frames.get(position).increment();
                            
                            if (mouseDate.compareTo(start) >= 0) {
                                
                                // Create a Platform to run code in the background on the new thread;
                                // this is where the data layer of the Grid's Canvas objects gets updated:
                                Platform.runLater(() -> {
                                    /* PUT GUI UPDATE LOGIC HERE */
                                    
                                    /* (Parker 4/3/17): display to the user the current frame being rendered: */
                                    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SSS");
                                    currentAnimationFrame.setText(formatter.format(locTimeData.get(finalJ).timestamp));
                                    overalllocTimeData = locTimeData.get(finalJ);
                                    
                                    /* GENERATE HEAT MAP: *************************************************** */
                                    
                                    /* perform string manipulation to get the integer gridIndex from the unitLabel locTimeData parameter: */
                                    int gridSectorIndex = getGridSectorIndexFromUnitLabel(locTimeData.get(finalJ).unitLabel);
                                    
                                    /* use the gridIndex to retrieve the matching gridSector object from the grid's gridSector array: */
                                    GridSector gs = getSectorByGridIndex(gridSectorIndex);
                                    
                                    /* add the current record's event duration to the selected GridSector's currentTotalDuration parameter */
                                    gs.currentTotalDuration += locTimeData.get(finalJ).eventDuration;
                                    
                                    /* get the maximum opacity for this grid sector (AKA this GridSector's value in the static heat map) */
                                    double baseOpacity = gs.finalTotalDuration / maxDuration;
                                    
                                    /* now create a shade of the baseOpactiy by applying the ratio of the finalTotalDuration vs currentTotalDuration */
                                    double currentOpacity = ((double)gs.currentTotalDuration / (double)gs.finalTotalDuration) * baseOpacity;
                                    
                                    /* attempt to reference the heatmap data layer of the viewerPane's children;
                                    if this is not possible, create a new heatmap data layer and add it to the viewerPane: */
                                    String heatMapId = "heatmap";
                                    Canvas viewerPaneHeatMapLayer = (Canvas)viewerPane.lookup("#" + heatMapId);
                                    
                                    if (viewerPaneHeatMapLayer == null) {
                                        // calculate width and height of the Canvas object:
                                        double width = calculateDimensions(viewerPane).w;
                                        double height = calculateDimensions(viewerPane).h;
                                        data = new Canvas(width, height);
                                        data.setId(heatMapId);
                                        //this.datalayers.add(data);
                                        viewerPane.getChildren().add(data);
                                    }//end if
                                    
                                    viewerPaneHeatMapLayer = (Canvas)viewerPane.lookup("#" + heatMapId);
                                    
                                    // get the graphics context of the heat map data layer for the purpose of drawing:
                                    GraphicsContext heatCanvasContext = viewerPaneHeatMapLayer.getGraphicsContext2D();
                                    
                                    /* perform the drawing of the shade onto the Canvas for this gridSector: */
                                    // clear out the current GridSector's old shade:
                                    heatCanvasContext.clearRect(gs.x, gs.y, gs.w, gs.h);
                                    // fill the current GridSector with the calculated shade:
                                    if(mice.size()>1) heatCanvasContext.setFill(Color.web("#FFA500", currentOpacity));
                                    else{
                                        double r, g, b;
                                        r = mice.get(0).getColor().getRed();
                                        g = mice.get(0).getColor().getGreen();
                                        b = mice.get(0).getColor().getBlue();
                                        heatCanvasContext.setFill(Color.color(r,g,b, currentOpacity));
                                    }//end else
                                    heatCanvasContext.fillRect(gs.x, gs.y, gs.w, gs.h);
                                    
                                    /* GENERATE VECTOR MAP: *************************************************** */
                                    
                                    /* perform string manipulation to get the integer gridIndex from the unitLabel locTimeData parameter: */
                                    int gridSectorIndex_previous = getGridSectorIndexFromUnitLabel(locTimeData.get(finalJ).unitLabel);
                                    //getGridSectorIndexFromUnitLabel
                                    
                                    int nextIndex = finalJ+1;
                                    // (Parker): prevent an index out of bounds error:
                                    if (nextIndex >= locTimeData.size()) nextIndex = finalJ;
                                    int gridSectorIndex_current = getGridSectorIndexFromUnitLabel(locTimeData.get(nextIndex).unitLabel);
                                    /* use the gridIndex to retrieve the matching gridSector object from the grid's gridSector array: */
                                    
                                    GridSector previous_grid = getSectorByGridIndex(gridSectorIndex_previous);
                                    GridSector current_grid = getSectorByGridIndex(gridSectorIndex_current);
                                    
                                    /* attempt to reference the heatmap data layer of the viewerPane's children;
                                    if this is not possible, create a new heatmap data layer and add it to the viewerPane: */
                                    String vectorMapId = "vectormap";
                                    Canvas viewerPaneVectorMapLayer = (Canvas)viewerPane.lookup("#" + vectorMapId);
                                    
                                    if (viewerPaneVectorMapLayer == null) {
                                        // calculate width and height of the Canvas object:
                                        double width = calculateDimensions(viewerPane).w;
                                        double height = calculateDimensions(viewerPane).h;
                                        data = new Canvas(width, height);
                                        data.setId(vectorMapId);
                                        //this.datalayers.add(data);
                                        viewerPane.getChildren().add(data);
                                    }//end if
                                    
                                    viewerPaneVectorMapLayer = (Canvas)viewerPane.lookup("#" + vectorMapId);
                                    
                                    // get the graphics context of the heat map data layer for the purpose of drawing:
                                    GraphicsContext vectorCanvasContext = viewerPaneVectorMapLayer.getGraphicsContext2D();
                                    
                                    vectorCanvasContext.save();
                                    
                                    // Use the identity matrix while clearing the canvas
                                    vectorCanvasContext.setTransform(1, 0, 0, 1, 0, 0);
                                    vectorCanvasContext.clearRect(0, 0, viewerPaneVectorMapLayer.getWidth(), viewerPaneVectorMapLayer.getHeight());
                                    
                                    // Restore the transform
                                    vectorCanvasContext.restore();
                                    
                                    vectorCanvasContext.setLineWidth(5);
                                    
                                    int frame_number = 1;
                                    VectorFrame vectorFrame = new VectorFrame(previous_grid, current_grid, mouse_color.get(locTimeData.get(finalJ).timestamp), frame_number);
                                    line_frames.add(vectorFrame);
                                    
                                     //update mouse position before drawing lines
                                        current_mouse_positions.put(mouse_color.get(locTimeData.get(finalJ).timestamp), current_grid);
                                    
                                    //Draw on the mouse positions
                                    current_mouse_positions.entrySet().stream().filter((entry) -> (entry.getValue()!=null)).map((entry) -> {
                                        vectorCanvasContext.setFill(entry.getKey().mouse_color);
                                        return entry;
                                    }).forEachOrdered((entry) -> {
                                        vectorCanvasContext.fillOval((entry.getKey().x * (entry.getValue().center_x - entry.getValue().x)) + entry.getValue().x, (entry.getKey().y * (entry.getValue().center_y - entry.getValue().y)) + entry.getValue().y, 15, 15);
                                    });
                                    
                                    for (int position = 0; position < line_frames.size(); position ++){
                                        
                                        if(line_frames.get(position).get_animation_state() == 1){
                                            
                                            vectorCanvasContext.setStroke(line_frames.get(position).color);
                                            vectorCanvasContext.setLineDashes(0d);  
                                        }else if(line_frames.get(position).get_animation_state() == 2){
                                            
                                            vectorCanvasContext.setStroke(line_frames.get(position).color);
                                            vectorCanvasContext.setLineDashes(10d);
                                        }else if(line_frames.get(position).get_animation_state() >= 3){
                                            
                                            vectorCanvasContext.setStroke(Color.TRANSPARENT);
                                            vectorCanvasContext.setLineDashes(0d);
                                        }//end else if
                                        //dataCanvasContext.fillOval((entry.getKey().x * (entry.getValue().center_x - entry.getValue().x)) + entry.getValue().x, (entry.getKey().y * (entry.getValue().center_y - entry.getValue().y)) + entry.getValue().y, 9, 9);
                                        vectorCanvasContext.strokeLine(
                                                (line_frames.get(position).mouse.x * (line_frames.get(position).first.center_x - line_frames.get(position).first.x)) + line_frames.get(position).first.x + 8,
 +                                                (line_frames.get(position).mouse.y * (line_frames.get(position).first.center_y - line_frames.get(position).first.y)) + line_frames.get(position).first.y +8,
 +                                                (line_frames.get(position).mouse.x * (line_frames.get(position).second.center_x - line_frames.get(position).second.x)) + line_frames.get(position).second.x + 8,
 +                                                (line_frames.get(position).mouse.y * (line_frames.get(position).second.center_y - line_frames.get(position).second.y)) + line_frames.get(position).second.y + 8);
                                        
                                      /*  vectorCanvasContext.strokeLine(
                                                line_frames.get(position).x,
                                                line_frames.get(position).first.center_y,
                                                line_frames.get(position).second.center_x,
                                                line_frames.get(position).second.center_y);*/
                                    }//end for
                                    
                                    //Checks grid line/numbers if they are active or not; if they are active, move them to the front of the Canvas layers
                                    moveInfoLayersToFront(viewerPane);
                                    
                                    // If the exportFolder parameter contains a File object, create a new File.
                                    // Using the path of the exportFolder appeneded to the number of the current frame, create a filename
                                    // unique to the current frame.
                                    if (exportFolder != null) {
                                        try {
                                            File exportFile = new File(exportFolder.getPath() + "\\" + frameCount + ".png");
                                            System.out.println("Frame filename: " + exportFile.getPath());
                                            exportFrame(viewerPane, exportFile, "png");
                                        } catch (AWTException ex) {
                                            Logger.getLogger(Grid.class.getName()).log(Level.SEVERE, null, ex);
                                        }//end catch
                                    }//end if
                                    
                                    // increment the number of frames rendered in the animation:
                                    frameCount++;
                                });
                                // delay the animation thread by "speed" number of milliseconds;
                                // this value should come directly from the Frame Delay GUI slider control:
                              
                               if(updatedSpeed > 0){
                                    Thread.sleep((int)updatedSpeed);
                                }
                                else{
                                    Thread.sleep((int)speed);
                                }
                            }//end if
                        }//end for
                    }
                        // (Parker): begin looping through the sorted locTimeData (the data row entries). Ensure the loop
                        // stops at the stopping index:
                    else{
                        for (int j = overallfinalJ; j < locTimeData.size() && locTimeData.get(j).timestamp.compareTo(stop) <= 0; ++j) {
                            // if the animation was cancelled (by setting the Grid class' animationCancelled property),
                            // then cancel the animation thread:
                            if (animationCancelled == true)
                                this.cancel(true);
                           if (isCancelled())
                                break;
                           // since we need to reference the 'j' incrementor inside the Platform.runlater code, 
                           // we need to make a new final variable that is a copy of j's current value:
                            final int finalJ = j;
                            overallfinalJ = j;
                            // determine if the current data row (MouseLocTime object) falls within the range of the Start and Stop indicies:
                            mouseDate = locTimeData.get(j).timestamp;
                            // if the current data row is within the Start and Stop indicies, perform the
                            // color shade calculations and update the GUI inside the Platform.runLater() function:
                            
                            //increment the frame count for each mouse position
                            for (int position = 0; position < line_frames.size(); position ++)
                                line_frames.get(position).increment();
                            
                            if (mouseDate.compareTo(start) >= 0) {
                                
                                // Create a Platform to run code in the background on the new thread;
                                // this is where the data layer of the Grid's Canvas objects gets updated:
                                Platform.runLater(() -> {
                                    /* PUT GUI UPDATE LOGIC HERE */
                                    
                                    /* (Parker 4/3/17): display to the user the current frame being rendered: */
                                    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SSS");
                                    currentAnimationFrame.setText(formatter.format(locTimeData.get(finalJ).timestamp));
                                    overalllocTimeData = locTimeData.get(finalJ);
                                    
                                    /* GENERATE HEAT MAP: *************************************************** */
                                    
                                    /* perform string manipulation to get the integer gridIndex from the unitLabel locTimeData parameter: */
                                    int gridSectorIndex = getGridSectorIndexFromUnitLabel(locTimeData.get(finalJ).unitLabel);
                                    
                                    /* use the gridIndex to retrieve the matching gridSector object from the grid's gridSector array: */
                                    GridSector gs = getSectorByGridIndex(gridSectorIndex);
                                    
                                    /* add the current record's event duration to the selected GridSector's currentTotalDuration parameter */
                                    gs.currentTotalDuration += locTimeData.get(finalJ).eventDuration;
                                    
                                    /* get the maximum opacity for this grid sector (AKA this GridSector's value in the static heat map) */
                                    double baseOpacity = gs.finalTotalDuration / maxDuration;
                                    
                                    /* now create a shade of the baseOpactiy by applying the ratio of the finalTotalDuration vs currentTotalDuration */
                                    double currentOpacity = ((double)gs.currentTotalDuration / (double)gs.finalTotalDuration) * baseOpacity;
                                    
                                    /* attempt to reference the heatmap data layer of the viewerPane's children;
                                    if this is not possible, create a new heatmap data layer and add it to the viewerPane: */
                                    String heatMapId = "heatmap";
                                    Canvas viewerPaneHeatMapLayer = (Canvas)viewerPane.lookup("#" + heatMapId);
                                    
                                    if (viewerPaneHeatMapLayer == null) {
                                        // calculate width and height of the Canvas object:
                                        double width = calculateDimensions(viewerPane).w;
                                        double height = calculateDimensions(viewerPane).h;
                                        data = new Canvas(width, height);
                                        data.setId(heatMapId);
                                        //this.datalayers.add(data);
                                        viewerPane.getChildren().add(data);
                                    }//end if
                                    
                                    viewerPaneHeatMapLayer = (Canvas)viewerPane.lookup("#" + heatMapId);
                                    
                                    // get the graphics context of the heat map data layer for the purpose of drawing:
                                    GraphicsContext heatCanvasContext = viewerPaneHeatMapLayer.getGraphicsContext2D();
                                    
                                    /* perform the drawing of the shade onto the Canvas for this gridSector: */
                                    // clear out the current GridSector's old shade:
                                    heatCanvasContext.clearRect(gs.x, gs.y, gs.w, gs.h);
                                    // fill the current GridSector with the calculated shade:
                                    if(mice.size()>1) heatCanvasContext.setFill(Color.web("#FFA500", currentOpacity));
                                    else{
                                        double r, g, b;
                                        r = mice.get(0).getColor().getRed();
                                        g = mice.get(0).getColor().getGreen();
                                        b = mice.get(0).getColor().getBlue();
                                        heatCanvasContext.setFill(Color.color(r,g,b, currentOpacity));
                                    }//end else
                                    heatCanvasContext.fillRect(gs.x, gs.y, gs.w, gs.h);
                                    
                                    /* GENERATE VECTOR MAP: *************************************************** */
                                    
                                    /* perform string manipulation to get the integer gridIndex from the unitLabel locTimeData parameter: */
                                    int gridSectorIndex_previous = getGridSectorIndexFromUnitLabel(locTimeData.get(finalJ).unitLabel);
                                    //getGridSectorIndexFromUnitLabel
                                    
                                    int nextIndex = finalJ+1;
                                    // (Parker): prevent an index out of bounds error:
                                    if (nextIndex >= locTimeData.size()) nextIndex = finalJ;
                                    int gridSectorIndex_current = getGridSectorIndexFromUnitLabel(locTimeData.get(nextIndex).unitLabel);
                                    /* use the gridIndex to retrieve the matching gridSector object from the grid's gridSector array: */
                                    
                                    GridSector previous_grid = getSectorByGridIndex(gridSectorIndex_previous);
                                    GridSector current_grid = getSectorByGridIndex(gridSectorIndex_current);
                                    
                                    /* attempt to reference the heatmap data layer of the viewerPane's children;
                                    if this is not possible, create a new heatmap data layer and add it to the viewerPane: */
                                    String vectorMapId = "vectormap";
                                    Canvas viewerPaneVectorMapLayer = (Canvas)viewerPane.lookup("#" + vectorMapId);
                                    
                                    if (viewerPaneVectorMapLayer == null) {
                                        // calculate width and height of the Canvas object:
                                        double width = calculateDimensions(viewerPane).w;
                                        double height = calculateDimensions(viewerPane).h;
                                        data = new Canvas(width, height);
                                        data.setId(vectorMapId);
                                        //this.datalayers.add(data);
                                        viewerPane.getChildren().add(data);
                                    }//end if
                                    
                                    viewerPaneVectorMapLayer = (Canvas)viewerPane.lookup("#" + vectorMapId);
                                    
                                    // get the graphics context of the heat map data layer for the purpose of drawing:
                                    GraphicsContext vectorCanvasContext = viewerPaneVectorMapLayer.getGraphicsContext2D();
                                    
                                    vectorCanvasContext.save();
                                    
                                    // Use the identity matrix while clearing the canvas
                                    vectorCanvasContext.setTransform(1, 0, 0, 1, 0, 0);
                                    vectorCanvasContext.clearRect(0, 0, viewerPaneVectorMapLayer.getWidth(), viewerPaneVectorMapLayer.getHeight());
                                    
                                    // Restore the transform
                                    vectorCanvasContext.restore();
                                    
                                    vectorCanvasContext.setLineWidth(5);
                                    
                                    int frame_number = 1;
                                    
                                    VectorFrame vectorFrame = new VectorFrame(previous_grid, current_grid, mouse_color.get(locTimeData.get(finalJ).timestamp), frame_number);
                                    line_frames.add(vectorFrame);
                                     //update mouse position before drawing lines
                                        current_mouse_positions.put(mouse_color.get(locTimeData.get(finalJ).timestamp), current_grid);
                                    
                                    //Draw on the mouse positions
                                    current_mouse_positions.entrySet().stream().filter((entry) -> (entry.getValue()!=null)).map((entry) -> {
                                        vectorCanvasContext.setFill(entry.getKey().mouse_color);
                                        return entry;
                                    }).forEachOrdered((entry) -> {
                                           vectorCanvasContext.fillOval((entry.getKey().x * (entry.getValue().center_x - entry.getValue().x)) + entry.getValue().x, (entry.getKey().y * (entry.getValue().center_y - entry.getValue().y)) + entry.getValue().y, 15, 15);
                                    });
                                    
                                    for (int position = 0; position < line_frames.size(); position ++){
                                        
                                        if(line_frames.get(position).get_animation_state() == 1){
                                            
                                            vectorCanvasContext.setStroke(line_frames.get(position).color);
                                            vectorCanvasContext.setLineDashes(0d);  
                                        }else if(line_frames.get(position).get_animation_state() == 2){
                                            
                                            vectorCanvasContext.setStroke(line_frames.get(position).color);
                                            vectorCanvasContext.setLineDashes(10d);
                                        }else if(line_frames.get(position).get_animation_state() >= 3){
                                            
                                            vectorCanvasContext.setStroke(Color.TRANSPARENT);
                                            vectorCanvasContext.setLineDashes(0d);
                                        }//end else if
                                        //dataCanvasContext.fillOval((entry.getKey().x * (entry.getValue().center_x - entry.getValue().x)) + entry.getValue().x, (entry.getKey().y * (entry.getValue().center_y - entry.getValue().y)) + entry.getValue().y, 9, 9);
                                        vectorCanvasContext.strokeLine(
                                                (line_frames.get(position).mouse.x * (line_frames.get(position).first.center_x - line_frames.get(position).first.x)) + line_frames.get(position).first.x + 8,
 +                                                (line_frames.get(position).mouse.y * (line_frames.get(position).first.center_y - line_frames.get(position).first.y)) + line_frames.get(position).first.y +8,
 +                                                (line_frames.get(position).mouse.x * (line_frames.get(position).second.center_x - line_frames.get(position).second.x)) + line_frames.get(position).second.x + 8,
 +                                                (line_frames.get(position).mouse.y * (line_frames.get(position).second.center_y - line_frames.get(position).second.y)) + line_frames.get(position).second.y + 8);
                                        
                                      /*  vectorCanvasContext.strokeLine(
                                                line_frames.get(position).x,
                                                line_frames.get(position).first.center_y,
                                                line_frames.get(position).second.center_x,
                                                line_frames.get(position).second.center_y);*/
                                    }//end for
                                    
                                    //Checks grid line/numbers if they are active or not; if they are active, move them to the front of the Canvas layers
                                    moveInfoLayersToFront(viewerPane);
                                    
                                    // If the exportFolder parameter contains a File object, create a new File.
                                    // Using the path of the exportFolder appeneded to the number of the current frame, create a filename
                                    // unique to the current frame.
                                    if (exportFolder != null) {
                                        try {
                                            File exportFile = new File(exportFolder.getPath() + "\\" + frameCount + ".png");
                                            System.out.println("Frame filename: " + exportFile.getPath());
                                            exportFrame(viewerPane, exportFile, "png");
                                        } catch (AWTException ex) {
                                            Logger.getLogger(Grid.class.getName()).log(Level.SEVERE, null, ex);
                                        }//end catch
                                    }//end if
                                    
                                    // increment the number of frames rendered in the animation:
                                    frameCount++;
                                });
                                // delay the animation thread by "speed" number of milliseconds;
                                // this value should come directly from the Frame Delay GUI slider control:
                              
                               if(updatedSpeed > 0){
                                    Thread.sleep((int)updatedSpeed);
                                }
                                else{
                                    Thread.sleep((int)speed);
                                }
                            }//end if
                        }//end for
                    }
                        long endTs = System.currentTimeMillis(); // stop the timer
                        elapsedTs = endTs - startTs; // get the elapsed time of the generation duration
                        return null; // we aren't returning a specific value from the task, so return null
                    }//end call
                    
                    // if the animation was cancelled by the user, respond gracefully:
                    @Override protected void cancelled() {
                        super.cancelled();
                        updateMessage("Cancelled!");
                    }//end cancelled
                    
                    // if the animation succeeded, respond gracefully:
                    @Override protected void succeeded() {
                        stopAnimation(generateButton);
                        // output a text status update to the user containing the duration of the animation:
                        String describeMice = (mice.size() > 1) ? "mice" : "mouse";
                        leftStatus.setText("Finished generating an animated overlay map of " + mice.size() + " " + describeMice + " in " + elapsedTs + " milliseconds. " + frameCount + " frames rendered.");
                    }//end succeeded
                };
            }//end createTask
        };
        // start the animation thread:
        service.start();
    }//end animatedOverlayMap
    
    /**
     * @author Joshua
     * 
     * Creates a vector map based on the selected mice and the indicated starting and stopping times
     * 
     * @param viewerPane the parent object that will contain the Canvas objects as its children
     * @param mice the mice to visualize
     * @param start a starting index from the range of data in the dataset
     * @param stop an ending index from the range of data in the dataset 
     */
    
    void staticVectorMap(StackPane viewerPane, ArrayList<Mouse> mice, Date start, Date stop) {
        Date mouseDate;
        HashMap<Mouse, ArrayList<Integer>> Mouse_positions = new HashMap<>(); 
        /*Creates a hashmap that stores mouse labels with that paticular mouse's grid positions stored as the value */
        
        for (int i = 0; i < mice.size(); ++i) {
            ArrayList<Integer> UnitLabels = new ArrayList<>();
            //creates an arraylist to store all the mouse postions for a particulars mouse 
            
            for (int j = 0; j < mice.get(i).locTimeData.size() && mice.get(i).locTimeData.get(j).timestamp.compareTo(stop) <= 0; ++j) {
                //System.out.println("currentDate.compareTo(stop) <= 0 ?: " + new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SSS").format(mouseDate) + " compareTo " + new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SSS").format(stop) + " = " + String.valueOf(mouseDate.compareTo(stop)));
                mouseDate = mice.get(i).locTimeData.get(j).timestamp;
                if (mouseDate.compareTo(start) >= 0) {
                    /* perform string manipulation to get the integer gridIndex from the unitLabel locTimeData parameter: */
                    int gridSectorIndex = getGridSectorIndexFromUnitLabel(mice.get(i).locTimeData.get(j).unitLabel);
                    
                    UnitLabels.add(gridSectorIndex);
                    }//end if
                }//end for
            Mouse_positions.put(mice.get(i), UnitLabels);
        }//end for
        
        double width = calculateDimensions(viewerPane).w;
        double height = calculateDimensions(viewerPane).h;
        this.data = new Canvas(width, height);
            
    
        GraphicsContext dataCanvasContext = data.getGraphicsContext2D();
        
        
        Mouse_positions.entrySet().stream().map((entry) -> {
            dataCanvasContext.setLineWidth(5);
            dataCanvasContext.setStroke(entry.getKey().mouse_color);
            return entry;            
        }).forEachOrdered((entry) -> {
            for (int i =1; i< entry.getValue().size(); i++){
                
                
                GridSector GS1 = getSectorByGridIndex(entry.getValue().get(i-1));
                GridSector GS2 = getSectorByGridIndex(entry.getValue().get(i));
                dataCanvasContext.strokeLine(
                        GS1.center_x ,
                        GS1.center_y,
                        GS2.center_x,
                        GS2.center_y);
                //Draws the line segments connecting all the sectors that the a mouse traveled
            }
        });
        
        
        // check for and remove any pre-existing heat map layer:
        String vectorMapId = "vectormap";
        Canvas viewerPaneHeatMapLayer = (Canvas)viewerPane.lookup("#" + vectorMapId);
        if (viewerPaneHeatMapLayer != null)
            viewerPane.getChildren().remove(viewerPane.lookup("#" + vectorMapId));
        
        // add the Canvas layer containing the heatmap to the grid object itself, and then to the viewerPane:
        data.setId(vectorMapId);
        this.datalayers.add(data);
        viewerPane.getChildren().add(data);
        
        //Checks grid line/numbers if they are active or not; if they are active, move them to the front of the Canvas layers
        moveInfoLayersToFront(viewerPane);
    }//end staticVectorMap
    
    /**
     * 
     * @author Joshua
     * 
     * Generate an animated vector map. Use the coordination of several GUI controls
     * in the Visualization options, the Grid class, and Mouse objects to achieve an
     * animation. The drawing of the animation must occur in a separate thread in order
     * to show constant progress updates in the GUI, so Service and Task objects are
     * used to achieve concurrency. The speed of the animation is controlled by
     * delay introduced between frames, ranging from 1 - 1000 milliseconds.
     * 
     * The exportFolder parameter is optional (may contain a File object or null value).
     * If it is a File object, the function will export each frame of the animation
     * to the directory specified by exportFolder.
     * 
     * @param viewerPane
     * @param generateButton
     * @param currentAnimationFrame
     * @param leftStatus
     * @param mice
     * @param start
     * @param stop
     * @param speed
     * @param exportFolder
     * @throws InterruptedException 
     */
    void animatedVectorMap(StackPane viewerPane, Button generateButton, TextArea currentAnimationFrame, Label leftStatus, ArrayList<Mouse> mice, Date start, Date stop, double speed,
             Color[] ColorList, AppStageController controller, File exportFolder, boolean rewind) throws InterruptedException {
         
        isRewind = rewind;
       
        //Checks grid line/numbers if they are active or not; if they are active, move them to the front of the Canvas layers
        moveInfoLayersToFront(viewerPane);

        // Establish a new service:
        Service<Void> service;
        service = new Service<Void>() {
            long elapsedTs = 0; // Create a timestamp based timer to measure the duration of the animation
            
            long frameCount = 0; // Count how many frames the animation has rendered
            
            @Override
            // create a new task that will comprise the new thread of the animation:
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        /* PUT LOOPING LOGIC HERE */
                        
                        long startTs = System.currentTimeMillis(); // begin a timer to record the amount of time the generation takes
                        
                        Date mouseDate;
                        
                        /* (Parker): in order to display the data rows in order, extract all the MouseLocTime data from
                        the Mouse objects in the selected mice function parameter and store the collective MouseLocTime data in
                        a new ArrayList. */
                        
                        HashMap<Date, Mouse> mouse_color = new HashMap<>();
                        ArrayList<MouseLocTime> locTimeData = new ArrayList<>();
                        for (int i = 0; i < mice.size(); ++i) {
                            for (int j = 0; j < mice.get(i).locTimeData.size(); ++j) {
                                locTimeData.add(mice.get(i).locTimeData.get(j));
                                mouse_color.put(mice.get(i).locTimeData.get(j).timestamp,mice.get(i));

                            }
                        }
                        
                        //Hashmap to store the current position on the grid for each mouse as the animation iterates
                        HashMap<Mouse, GridSector> current_mouse_positions = new HashMap<>();
                        mouse_color.entrySet().forEach((entry) -> {
                            current_mouse_positions.put(entry.getValue(), null);
                        });

                        
                        // (Parker): sort the collective MouseLocTime data so that it is in order from earliest Date timestamp
                        // to latest Date timestamp:
                        Collections.sort(locTimeData);
                        
                      
                        
                        //A list stores lines and the animatation frame state of each line
                        ArrayList<VectorFrame> line_frames = new ArrayList<>();
                        
                        // (Parker): begin looping through the sorted locTimeData (the data row entries). Ensure the loop
                        // stops at the stopping index:
                    if(isRewind){
                        for (int j = overallfinalJ; j > 0; --j) {
                            // if the animation was cancelled (by setting the Grid class' animationCancelled property),
                            // then cancel the animation thread:
                            if (animationCancelled == true)
                                this.cancel(true);
                            if (isCancelled())
                                break;
                            
                            // since we need to reference the 'j' incrementor inside the Platform.runlater code,
                            // we need to make a new final variable that is a copy of j's current value:
                            final int finalJ = j;
                            overallfinalJ = finalJ;
                            // determine if the current data row (MouseLocTime object) falls within the range of the Start and Stop indicies:
                            mouseDate = locTimeData.get(j).timestamp;
                            // if the current data row is within the Start and Stop indicies, perform the
                            // color shade calculations and update the GUI inside the Platform.runLater() function
                            for (int position = 0; position < line_frames.size(); position ++)
                                line_frames.get(position).increment();
                            
                            
                            if (mouseDate.compareTo(start) >= 0) {
                                
                                // Create a Platform to run code in the background on the new thread;
                                // this is where the data layer of the Grid's Canvas objects gets updated:
                                Platform.runLater(() -> {
                                    /* PUT GUI UPDATE LOGIC HERE */
                                    
                                    //Draws the line segments connecting all the sectors that the a mouse traveled
                                    
                                    /* (Parker 4/3/17): display to the user the current frame being rendered: */
                                    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SSS");
                                    currentAnimationFrame.setText(formatter.format(locTimeData.get(finalJ).timestamp));
                                    overalllocTimeData = locTimeData.get(finalJ);
                                    
                                    /* perform string manipulation to get the integer gridIndex from the unitLabel locTimeData parameter: */
                                    int gridSectorIndex_previous = getGridSectorIndexFromUnitLabel(locTimeData.get(finalJ).unitLabel);
                                    int nextIndex = finalJ+1;
                                    // (Parker): prevent an index out of bounds error:
                                    if (nextIndex >= locTimeData.size()) nextIndex = finalJ;
                                    int gridSectorIndex_current = getGridSectorIndexFromUnitLabel(locTimeData.get(nextIndex).unitLabel);
                                    /* use the gridIndex to retrieve the matching gridSector object from the grid's gridSector array: */
                                    
                                    GridSector previous_grid = getSectorByGridIndex(gridSectorIndex_previous);
                                    GridSector current_grid = getSectorByGridIndex(gridSectorIndex_current);
                                    
                                    /* attempt to reference the heatmap data layer of the viewerPane's children;
                                    if this is not possible, create a new heatmap data layer and add it to the viewerPane: */
                                    String vectorMapId = "vectormap";
                                    Canvas viewerPaneVectorMapLayer = (Canvas)viewerPane.lookup("#" + vectorMapId);
                                    
                                    if (viewerPaneVectorMapLayer == null) {
                                        // calculate width and height of the Canvas object:
                                        double width = calculateDimensions(viewerPane).w;
                                        double height = calculateDimensions(viewerPane).h;
                                        data = new Canvas(width, height);
                                        data.setId(vectorMapId);
                                        //this.datalayers.add(data);
                                        viewerPane.getChildren().add(data);
                                        //Checks grid line/numbers if they are active or not; if they are active, move them to the front of the Canvas layers
                                        moveInfoLayersToFront(viewerPane);
                                    }//end if
                                    
                                    viewerPaneVectorMapLayer = (Canvas)viewerPane.lookup("#" + vectorMapId);
                                    
                                    // get the graphics context of the heat map data layer for the purpose of drawing:
                                    GraphicsContext dataCanvasContext = viewerPaneVectorMapLayer.getGraphicsContext2D();
                                    
                                    // Store the current transformation matrix
                                    dataCanvasContext.save();
                                    
                                    // Use the identity matrix while clearing the canvas
                                    dataCanvasContext.setTransform(1, 0, 0, 1, 0, 0);
                                    dataCanvasContext.clearRect(0, 0, viewerPaneVectorMapLayer.getWidth(), viewerPaneVectorMapLayer.getHeight());
                                    
                                    // Restore the transform
                                    dataCanvasContext.restore();
                                    
                                    dataCanvasContext.setLineWidth(5);
                                    
                                    int frame_number = 1;
                                    VectorFrame vectorFrame = new VectorFrame(previous_grid, current_grid, mouse_color.get(locTimeData.get(finalJ).timestamp), frame_number);
                                    line_frames.add(vectorFrame);
                                    
                                    //update mouse position before drawing lines
                                    current_mouse_positions.put(mouse_color.get(locTimeData.get(finalJ).timestamp), current_grid);
                                    
                                    
                                    //Draw on the mouse positions
                                    
                                    
                                    current_mouse_positions.entrySet().stream().filter((entry) -> (entry.getValue()!=null)).map((entry) -> {
                                        dataCanvasContext.setFill(entry.getKey().mouse_color);
                                        return entry;
                                    }).forEachOrdered((entry) -> {
                                        //Whitney Post 6/5/17: Modified the method so that it draws each mouse in unique location within grid sector
                                        dataCanvasContext.fillOval((entry.getKey().x * (entry.getValue().center_x - entry.getValue().x)) + entry.getValue().x, (entry.getKey().y * (entry.getValue().center_y - entry.getValue().y)) + entry.getValue().y, 15, 15);
                                        //dataCanvasContext.fillOval(entry.getValue().center_x -5, entry.getValue().center_y -5, 9, 9);
                                        
                                  
                                    });
                                    
                                    for (int position = 0; position < line_frames.size(); position ++){
                                        
                                        if(line_frames.get(position).get_animation_state() == 1){
                                            
                                            dataCanvasContext.setStroke(line_frames.get(position).color);
                                            dataCanvasContext.setLineDashes(0d);
                                            
                                            
                                        }else if(line_frames.get(position).get_animation_state() == 2){
                                            
                                            dataCanvasContext.setStroke(line_frames.get(position).color);
                                            dataCanvasContext.setLineDashes(10d);
                                            
                                            
                                            
                                        }else if(line_frames.get(position).get_animation_state() >= 3){
                                            
                                            dataCanvasContext.setStroke(Color.TRANSPARENT);
                                            dataCanvasContext.setLineDashes(0d);
                                            
                                        }//end else if
                                        //(entry.getKey().x * (entry.getValue().center_x - entry.getValue().x)) + entry.getValue().x, (entry.getKey().y * (entry.getValue().center_y - entry.getValue().y)) + entry.getValue().y
                                        //Whitney Post 6/6/2017: Modified the way the vector lines are drawn by matching the unique mouse position in the grids to the position of the vectors start and stop point
                                        dataCanvasContext.strokeLine(
                                                (line_frames.get(position).mouse.x * (line_frames.get(position).first.center_x - line_frames.get(position).first.x)) + line_frames.get(position).first.x + 8,
 +                                                (line_frames.get(position).mouse.y * (line_frames.get(position).first.center_y - line_frames.get(position).first.y)) + line_frames.get(position).first.y +8,
 +                                                (line_frames.get(position).mouse.x * (line_frames.get(position).second.center_x - line_frames.get(position).second.x)) + line_frames.get(position).second.x + 8,
 +                                                (line_frames.get(position).mouse.y * (line_frames.get(position).second.center_y - line_frames.get(position).second.y)) + line_frames.get(position).second.y + 8);
                                        
                                        /*dataCanvasContext.strokeLine(
                                                line_frames.get(position).first.center_x,
                                                line_frames.get(position).first.center_y,
                                                line_frames.get(position).second.center_x,
                                                line_frames.get(position).second.center_y);
                                        */
                                    }//end for
                                    // If the exportFolder parameter contains a File object, create a new File.
                                    // Using the path of the exportFolder appeneded to the number of the current frame, create a filename
                                    // unique to the current frame.
                                    if (exportFolder != null) {
                                        try {
                                            File exportFile = new File(exportFolder.getPath() + "\\" + frameCount + ".png");
                                            System.out.println("Frame filename: " + exportFile.getPath());
                                            exportFrame(viewerPane, exportFile, "png");
                                        } catch (AWTException ex) {
                                            Logger.getLogger(Grid.class.getName()).log(Level.SEVERE, null, ex);
                                        }//end catch
                                    }//end if
                                    
                                    ++frameCount;
                                } //end run
                                );
                                // delay the animation thread by "speed" number of milliseconds;
                                // this value should come directly from the Frame Delay GUI slider control:
                                if(updatedSpeed > 0){
                                    Thread.sleep((int)updatedSpeed);
                                }
                                else{
                                    Thread.sleep((int)speed);
                                }
                            }//end if
                        }//end for
                        
                    }
                    else{
                        for (int j = overallfinalJ; j+1 < locTimeData.size() && locTimeData.get(j).timestamp.compareTo(stop) <= 0; ++j) {
                            // if the animation was cancelled (by setting the Grid class' animationCancelled property),
                            // then cancel the animation thread:
                            if (animationCancelled == true)
                                this.cancel(true);
                            if (isCancelled())
                                break;
                            
                            // since we need to reference the 'j' incrementor inside the Platform.runlater code,
                            // we need to make a new final variable that is a copy of j's current value:
                            final int finalJ = j;
                            overallfinalJ = finalJ;
                            // determine if the current data row (MouseLocTime object) falls within the range of the Start and Stop indicies:
                            mouseDate = locTimeData.get(j).timestamp;
                            // if the current data row is within the Start and Stop indicies, perform the
                            // color shade calculations and update the GUI inside the Platform.runLater() function:
                            
                            for (int position = 0; position < line_frames.size(); position ++)
                                line_frames.get(position).increment();
                            
                            
                            if (mouseDate.compareTo(start) >= 0) {
                                
                                // Create a Platform to run code in the background on the new thread;
                                // this is where the data layer of the Grid's Canvas objects gets updated:
                                Platform.runLater(() -> {
                                    /* PUT GUI UPDATE LOGIC HERE */
                                    Color currentColor = mouse_color.get(locTimeData.get(finalJ).timestamp).mouse_color;
                                    for (int i = 0; i < ColorList.length; i++) {
                                    if (currentColor.equals(ColorList[i])) {
                                        miceData currentData;
                                        int duration = locTimeData.get(finalJ).eventDuration;
                                        int location = Integer.parseInt(locTimeData.get(finalJ).unitLabel.substring(4));
                                        currentData = new miceData(duration, location, mouse_color.get(locTimeData.get(finalJ).timestamp).IdLabel);
                                        String behaviour = tree.search(currentData);
                                        controller.updateBehaviour(behaviour, i);
                                        
                                        
                                        break;

                                    }
                                }
                                    
                                    
                                    //Draws the line segments connecting all the sectors that the a mouse traveled
                                   
                                    /* (Parker 4/3/17): display to the user the current frame being rendered: */
                                    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SSS");
                                    currentAnimationFrame.setText(formatter.format(locTimeData.get(finalJ).timestamp));
                                    overalllocTimeData = locTimeData.get(finalJ);
                                    
                                    /* perform string manipulation to get the integer gridIndex from the unitLabel locTimeData parameter: */
                                    int gridSectorIndex_previous = getGridSectorIndexFromUnitLabel(locTimeData.get(finalJ).unitLabel);
                                    int nextIndex = finalJ+1;
                                    // (Parker): prevent an index out of bounds error:
                                    if (nextIndex >= locTimeData.size()) nextIndex = finalJ;
                                    int gridSectorIndex_current = getGridSectorIndexFromUnitLabel(locTimeData.get(nextIndex).unitLabel);
                                    /* use the gridIndex to retrieve the matching gridSector object from the grid's gridSector array: */
                                    
                                    GridSector previous_grid = getSectorByGridIndex(gridSectorIndex_previous);
                                    GridSector current_grid = getSectorByGridIndex(gridSectorIndex_current);
                                    
                                    /* attempt to reference the heatmap data layer of the viewerPane's children;
                                    if this is not possible, create a new heatmap data layer and add it to the viewerPane: */
                                    String vectorMapId = "vectormap";
                                    Canvas viewerPaneVectorMapLayer = (Canvas)viewerPane.lookup("#" + vectorMapId);
                                    
                                    if (viewerPaneVectorMapLayer == null) {
                                        // calculate width and height of the Canvas object:
                                        double width = calculateDimensions(viewerPane).w;
                                        double height = calculateDimensions(viewerPane).h;
                                        data = new Canvas(width, height);
                                        data.setId(vectorMapId);
                                        //this.datalayers.add(data);
                                        viewerPane.getChildren().add(data);
                                        //Checks grid line/numbers if they are active or not; if they are active, move them to the front of the Canvas layers
                                        moveInfoLayersToFront(viewerPane);
                                    }//end if
                                    
                                    viewerPaneVectorMapLayer = (Canvas)viewerPane.lookup("#" + vectorMapId);
                                    
                                    // get the graphics context of the heat map data layer for the purpose of drawing:
                                    GraphicsContext dataCanvasContext = viewerPaneVectorMapLayer.getGraphicsContext2D();
                                    
                                    // Store the current transformation matrix
                                    dataCanvasContext.save();
                                    
                                    // Use the identity matrix while clearing the canvas
                                    dataCanvasContext.setTransform(1, 0, 0, 1, 0, 0);
                                    dataCanvasContext.clearRect(0, 0, viewerPaneVectorMapLayer.getWidth(), viewerPaneVectorMapLayer.getHeight());
                                    
                                    // Restore the transform
                                    dataCanvasContext.restore();
                                    
                                    dataCanvasContext.setLineWidth(5);
                                    
                                    int frame_number = 1;
                                    VectorFrame vectorFrame = new VectorFrame(previous_grid, current_grid, mouse_color.get(locTimeData.get(finalJ).timestamp), frame_number);
                                    line_frames.add(vectorFrame);
                                    
                                    //update mouse position before drawing lines
                                    current_mouse_positions.put(mouse_color.get(locTimeData.get(finalJ).timestamp), current_grid);
                                    
                                    //Draw on the mouse positions
                                    
                                    
                                    current_mouse_positions.entrySet().stream().filter((entry) -> (entry.getValue()!=null)).map((entry) -> {
                                        dataCanvasContext.setFill(entry.getKey().mouse_color);
                                        return entry;
                                    }).forEachOrdered((entry) -> {
                                        //Whitney Post 6/5/17: Modified the method so that it draws each mouse in unique location within grid sector
                                        dataCanvasContext.fillOval((entry.getKey().x * (entry.getValue().center_x - entry.getValue().x)) + entry.getValue().x, (entry.getKey().y * (entry.getValue().center_y - entry.getValue().y)) + entry.getValue().y, 15, 15);
                                        //dataCanvasContext.fillOval(entry.getValue().center_x -5, entry.getValue().center_y -5, 9, 9);
                                        
                                  
                                    });
                                    
                                    for (int position = 0; position < line_frames.size(); position ++){
                                        
                                        if(line_frames.get(position).get_animation_state() == 1){
                                            
                                            dataCanvasContext.setStroke(line_frames.get(position).color);
                                            dataCanvasContext.setLineDashes(0d);
                                            
                                            
                                        }else if(line_frames.get(position).get_animation_state() == 2){
                                            
                                            dataCanvasContext.setStroke(line_frames.get(position).color);
                                            dataCanvasContext.setLineDashes(10d);
                                            
                                            
                                            
                                        }else if(line_frames.get(position).get_animation_state() >= 3){
                                            
                                            dataCanvasContext.setStroke(Color.TRANSPARENT);
                                            dataCanvasContext.setLineDashes(0d);
                                            
                                        }//end else if
                                        //(entry.getKey().x * (entry.getValue().center_x - entry.getValue().x)) + entry.getValue().x, (entry.getKey().y * (entry.getValue().center_y - entry.getValue().y)) + entry.getValue().y
                                        //Whitney Post 6/6/2017: Modified the way the vector lines are drawn by matching the unique mouse position in the grids to the position of the vectors start and stop point
                                        dataCanvasContext.strokeLine(
                                                (line_frames.get(position).mouse.x * (line_frames.get(position).first.center_x - line_frames.get(position).first.x)) + line_frames.get(position).first.x + 8,
 +                                                (line_frames.get(position).mouse.y * (line_frames.get(position).first.center_y - line_frames.get(position).first.y)) + line_frames.get(position).first.y +8,
 +                                                (line_frames.get(position).mouse.x * (line_frames.get(position).second.center_x - line_frames.get(position).second.x)) + line_frames.get(position).second.x + 8,
 +                                                (line_frames.get(position).mouse.y * (line_frames.get(position).second.center_y - line_frames.get(position).second.y)) + line_frames.get(position).second.y + 8);
                                        
                                        /*dataCanvasContext.strokeLine(
                                                line_frames.get(position).first.center_x,
                                                line_frames.get(position).first.center_y,
                                                line_frames.get(position).second.center_x,
                                                line_frames.get(position).second.center_y);
                                        */
                                    }//end for
                                    // If the exportFolder parameter contains a File object, create a new File.
                                    // Using the path of the exportFolder appeneded to the number of the current frame, create a filename
                                    // unique to the current frame.
                                    if (exportFolder != null) {
                                        try {
                                            File exportFile = new File(exportFolder.getPath() + "\\" + frameCount + ".png");
                                            System.out.println("Frame filename: " + exportFile.getPath());
                                            exportFrame(viewerPane, exportFile, "png");
                                        } catch (AWTException ex) {
                                            Logger.getLogger(Grid.class.getName()).log(Level.SEVERE, null, ex);
                                        }//end catch
                                    }//end if
                                    
                                    ++frameCount;
                                } //end run
                                );
                        

                                 
                                // delay the animation thread by "speed" number of milliseconds;
                                // this value should come directly from the Frame Delay GUI slider control:
                                if(updatedSpeed > 0){
                                    Thread.sleep((int)updatedSpeed);
                                }
                                else{
                                    Thread.sleep((int)speed);
                                }
                            }//end if
                        }//end for
                    }
                        long endTs = System.currentTimeMillis(); // stop the timer
                        elapsedTs = endTs - startTs; // get the elapsed time of the generation duration
                        return null; // we aren't returning a specific value from the task, so return null
                    }//end  call
                    
                    // if the animation was cancelled by the user, respond gracefully:
                    @Override protected void cancelled() {
                        super.cancelled();
                        updateMessage("Cancelled!");
                    }//end cancelled
                    
                    // if the animation succeeded, respond gracefully:
                    @Override protected void succeeded() {
                        stopAnimation(generateButton);

                        // output a text status update to the user containing the duration of the animation:
                        String describeMice = (mice.size() > 1) ? "mice" : "mouse";
                        leftStatus.setText("Finished generating an animated vector map of " + mice.size() + " " + describeMice + " in " + elapsedTs + " milliseconds. " + frameCount + " frames rendered.");
                    }//end succeeded
                };
            }//end createTask
        };
        // start the animation thread:
        service.start();
    }//end animatedVectorMap
    
    /*
    Alex (3/29/17):
        This is parkers code, I just put it in a function since it was repeated in both static and vector functions.
            Current problem with exporting -> it'll erase the screen after exporting, but won't reset checkboxes
                for grid lines and numbers, so unchecking them enables them instead. Need to fix that.
    */
    /**
     * 
     * @author: Parker, Alex
     * 
     * check if gridnumbers and/or gridlines Canvas objects exist in the viewerPane
     * parent object. If they do, move them to the front of the Grid's Canvas layers.
     * This is useful for ensuring that the grid overlay and grid sector numbers
     * will appear on top of any generated map layers.
     * 
     * @param viewerPane 
     */
    void moveInfoLayersToFront(StackPane viewerPane) {
        
        // (Parker 3/27/17): Get the child gridnumbers and gridlines Canvas layers that have been added to the viewerPane object as children:
        viewerPaneGridNumbers = (Canvas)viewerPane.lookup("#gridnumbers");
        viewerPaneGridLines = (Canvas)viewerPane.lookup("#gridlines");
        
        // (Parker 3/27/17): if they exist (meaning if the user has chosen to display them), shift the layers to the front on top of the datalayers:
        if (viewerPaneGridNumbers != null)
            viewerPaneGridNumbers.toFront();
        
        if (viewerPaneGridLines != null)
            viewerPaneGridLines.toFront();
    }//end moveInfoLayersToFront
}//end class Grid