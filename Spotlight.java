//34567890123456789012345678901234567890123456789
//Note: this is wide format for small fonts.
//=============================================//

/*File Spotlight.java.java
Copyright 2004, R.G.Baldwin

This program is designed to be driven by the
program named Framework01.java.

Enter the following on the command line to run
this program:

java Framework01 Spotlight FileName

This program illustrates an image special effect
involving the control of light intensity in the
image.  The effect is as though a spotlight is
shining on the object in the center of the image.
The center of the image is well lit while the
outer edges of the image are dark.

A small GUI with a text field is provided to
allow the user to control the radius of the
lighted area.  At startup, the text field
contains a 0.  To change the radius of the
lighted area, type an integer value into the
text field and press the Replot button at the
bottom of the image.  The larger the integer
value, the smaller will be the radius of the
lighted area.  For values of around 10 and
above, the lighted area will be reduced to
approximately the size of a single pixel.

Note that the pixel modification in this program
has no impact on transparent pixels.  If you
don't see what you expect, it may be because your
image contains large transparent areas.

Tested using SDK 1.4.2 and WinXP
************************************************/
import java.awt.*;

class Spotlight extends Frame
                           implements Framework01Intfc{

  int loops;//User input to control lighting
  String inputData;//Obtained via the TextField
  TextField input;//User input field

  Spotlight(){//constructor
    //Create a user input frame.
    setLayout(new FlowLayout());

    Label instructions = new Label(
                      "Type an int and replot.");
    add(instructions);

    input = new TextField("0",5);
    add(input);

    setTitle("Copyright 2004, Baldwin");
    setBounds(400,0,200,100);
    setVisible(true);

    //print certification
    System.out.println(
      "I certify that this program is my own work \n" +
      "and is not the work of others. I agree not \n" +
      "to share my solution with others.\n" +
      "Michael Brady\n");
  }//end constructor
  //-------------------------------------------//

  //This method must be defined to implement
  // the interface named ImgIntfc02.
  public int[][][] processImg(
                             int[][][] threeDPix,
                             int imgRows,
                             int imgCols){

    //Make a working copy of the 3D array to
    // avoid making permanent changes to the
    // image data.
    int[][][] temp3D =
                    new int[imgRows][imgCols][4];
    for(int row = 0;row < imgRows;row++){
      for(int col = 0;col < imgCols;col++){
        temp3D[row][col][0] =
                          threeDPix[row][col][0];
        temp3D[row][col][1] =
                          threeDPix[row][col][1];
        temp3D[row][col][2] =
                          threeDPix[row][col][2];
        temp3D[row][col][3] =
                          threeDPix[row][col][3];
      }//end inner loop
    }//end outer loop

    System.out.println("Width = " + imgCols);
    System.out.println("Height = " + imgRows);

    //Get the user input from the text field.
    // First time through, the input value is the
    // value set into the text field when it was
    // instantiated.  After that, the input value
    // is the value typed by the user.
    loops = Integer.parseInt(input.getText());

    //Calculate the center point of the image
    int centerWidth = imgCols/2;
    int centerHeight = imgRows/2;

    //Calculate the distance from a corner to the
    // center point of the image.  This will be
    // used as a base for calculating the
    // relative distance from any pixel to the
    // center.
    double maxDistance = Math.sqrt(
                      centerWidth*centerWidth +
                      centerHeight*centerHeight);

    //Decrease the intensity of every pixel
    // except the one in the center of the image.
    // Initially the intensity will decrease
    // linearly with the distance of a pixel from
    // the center pixel.  The user can modify
    // this through input at the text field
    // later.

    //Use a nested loop to operate on each pixel.
    for(int row = 0;row < imgRows;row++){
      for(int col = 0;col < imgCols;col++){
        //Get the horizontal and vertical
        // distances from the center as the two
        // sides of a right triangle.
        double horiz = centerWidth - col;
        double vert = centerHeight - row;

        //Get the distance to the center as the
        // hypotenuse of a right triangle.
        double distance = Math.sqrt(horiz*horiz
                                    + vert*vert);
        if((int)distance > 0){
          //Calculate the scale factor to apply
          // to the three color values for the
          // pixel in order to control the
          // intensity.
          double scale =
                      1.0 - distance/maxDistance;

          //Adjust the light intensity based on
          // user input.  For a user input value
          // of 0 (the default at startup), the
          // intensity decreases linearly with
          // the distance from the center.  For a
          // user input value of 1, the intensity
          // decreases as the square of the
          // distance.  For an input value of 2,
          // the intensity decreases as the cube
          // of the distance, etc.
          for(int cnt = 0;cnt < loops;cnt++){
            scale = scale*scale;
          }//end for loop

          //Change the blue and alpha values only to meet the program specifications
          temp3D[row][col][0] =
              (int)(scale * temp3D[row][col][0]);
          temp3D[row][col][3] =
              (int)(scale * temp3D[row][col][3]);
          
          
/*
          //As an interesting experiment, you can
          // filter out two colors by enabling
          // these two statements and setting the
          // value of the third dimension to 1,
          // 2, or 3.  As written here, green and
          // blue will be eliminated leaving only
          // red.
          temp3D[row][col][2] = 0;
          temp3D[row][col][3] = 0;
*/
        }//end if
      }//end for loop on col
    }//end for loop on row

    return temp3D;
  }//end processImg
  //-------------------------------------------//

}//end class Spotlight