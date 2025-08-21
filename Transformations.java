import java.util.Random;

public class Transformations {
    //Invert the red color of the image to the maximum color value
    //This is done by subtracting the red value from the maximum color value
    public static void invertRed( byte[] row, int maxColorValue ) {
        for(int i = 0; i < row.length; i += 3){ //Each pixel has 3 bytes (R, G, B)
            int red = row[ i ] & 0xff; //Get the red value as an unsigned byte
            int redInverted = maxColorValue - red; //Subtract the red value from the maximum color value
            row[ i ] = (byte) (redInverted); //Store the inverted red value as byte
        }
    }

    //Invert the green color of the image to the maximum color value
    public static void invertGreen( byte[] row, int maxColorValue ) {
        for(int i = 0; i < row.length; i += 3){ 
            int green = row[ i + 1 ] & 0xff; //Get the green value as an unsigned byte
            int greenInverted = maxColorValue - green; //Subtract the green value from the maximum color value
            row[ i + 1 ] = (byte) ( greenInverted ); //Store the inverted green value as byte
        }
    }

    //Invert the blue color of the image to the maximum color value
    public static void invertBlue( byte[] row, int maxColorValue ) {
        for(int i = 0; i < row.length; i += 3){
            row[ i + 2 ] = (byte) ( maxColorValue - row[ i + 2 ] & 0xff );


        }
    }

    //Convert the image to grayscale by averaging the RGB values
    //This is done by calculating the average of the red, green, and blue values
    public static void convertToGrayScale( byte[] row ) {
        for(int i = 0; i < row.length; i += 3){
            int r = row[ i ] & 0xff;
            int g = row[ i + 1 ] & 0xff;
            int b = row[ i + 2 ] & 0xff;

            int avg = ( r + g + b ) / 3;

            //Set the red, green, and blue values to the average value
            //This will make the pixel grayscale
            row[ i ] = (byte) avg;
            row[ i + 1 ] = (byte) avg;
            row[ i + 2 ] = (byte) avg;
        }

    }

    //Flip the image horizontally by swapping the pixels in each row
    //This is done by swapping the pixels from the left and right sides of the row
    public static void flipHorizontal( byte[] row ) {
        //Each pixel has 3 bytes (R, G, B)
        //The width of the image is the length of the row divided by 3
        int dimWidth = row.length / 3;

        //Swap the pixels from the left and right sides of the row
        //We only need to swap half of the row, since the other half will be swapped
        for(int x = 0; x < dimWidth / 2; x++){
            for(int y = 0; y < 3; y++){ // Loop through the RGB values
                //Calculate the index of the left and right pixels
                int leftIndex = x * 3 + y; 
                int rightIndex = ( dimWidth - x - 1 ) * 3 + y; 

                //Swap the left and right pixels
                //Use a temporary variable to hold the value of the left pixel
                byte temp = row[ leftIndex ];
                row[ leftIndex ] = row[ rightIndex ];
                row[ rightIndex ] = temp;


            }
        }
    }


    //Remove the red color from the image by setting the red value to 0
    public static void removeRed( byte[] row ) {
        for(int i = 0; i < row.length; i += 3){
            row[ i ] = 0;
        }
    }

    //Remove the green color from the image by setting the green value to 0
    public static void removeGreen( byte[] row ) {
        for(int i = 0; i < row.length; i += 3){
            row[ i + 1 ] = 0;
        }
    }


    //Remove the blue color from the image by setting the blue value to 0
    public static void removeBlue( byte[] row ) {
        for(int i = 0; i < row.length; i += 3){
            row[ i + 2 ] = 0;
        }
    }

    // Apply contrast to the image by setting the color values to 0
    public static void contrast( byte[] row, int maxColorValue ) {
        for(int i = 0; i < row.length; i += 3){
            int r = row[ i ] & 0xff;
            int g = row[ i + 1 ] & 0xff;
            int b = row[ i + 2 ] & 0xff;

            int midpoint = maxColorValue / 2; // Calculate the midpoint of the color range

            //If the color value is greater than the midpoint, set it to the maximum color value
            //Set to 0 if less than or equal to midpoint
            if ( r > midpoint ) {
                row[ i ] = (byte) maxColorValue;
            } else {
                row[ i ] = 0; 
            }

            
            if ( g > midpoint ) {
                row[ i + 1 ] = (byte) maxColorValue;
            } else {
                row[ i + 1 ] = 0;
            }

            if ( b > midpoint ) {
                row[ i + 2 ] = (byte) maxColorValue;
            } else {
                row[ i + 2 ] = 0;
            }
        }
    }

    //Add noise to the image by randomly adjusting the color values
    //This will create a grainy effect on the image
    //This is done by adding a random value to the red, green, and blue values
    public static void noise( byte[] row, int maxColorValue ) {
        Random rand = new Random ();

        //Iterate through each pixel in the row
        for(int i = 0; i < row.length; i += 3){
            int r = row[i] & 0xff;
            int g = row[i + 1] & 0xff;
            int b = row[i + 2] & 0xff;

            //Generate a random noise value between 0 and 50
            //Ensure that the new color values do not exceed the maximum color value
            int noise = rand.nextInt (50);
            if ( !(r + noise > maxColorValue)  && !(g + noise > maxColorValue) && !(b + noise > maxColorValue)){
                r += noise;
                g += noise;
                b += noise;
            }

            //Set the new color values
            row[i] = (byte) r;
            row[i + 1] = (byte) g;
            row[i + 2] = (byte) b;
        }
    }

    //Apply a blur effect to the image by averaging the color values of neighboring pixels
    //This is done by calculating the average color value of the pixel and its neighbors
    public static void blur(byte[] row) {
        int halfBlur = 5; //Half the blur size, can be adjusted for more or less blur
        
        //Initialize variables to hold the sum of the color values and the count of pixels
        int r = 0;
        int g = 0;
        int b = 0;

        //Count of pixels to average
        int count = 0;

        //Iterate through each pixel in the row
        for (int i = 0; i < row.length; i += 3) {

            //Reset the sum and count for each pixel
            for(int  j = i - 3 * halfBlur; j <= i + 3 * halfBlur; j += 3){
                if ( j >= 0 && j < row.length ){
                    r += row[ j ] & 0xFF;
                    g += row[j + 1] & 0xFF;
                    b += row[ j + 2 ] & 0xFF;

                    count++;
                }
            }
            //Calculate the average color values
            //Ensure that the average is within the byte range (0-255)
            int avgR = r / count;
            int avgG = g / count;
            int avgB = b / count;

            //Set the new color values for the pixel
            //This will create a blur effect by averaging the color values of neighboring pixels
            row[i] = (byte) avgR;
            row[i + 1] = (byte) avgG;
            row[i + 2] = (byte) avgB;

            // reset
            r = 0;
            g = 0;
            b = 0;
            count = 0;
        }

    }

}




/*
 * https://www.delftstack.com/howto/cpp/read-ppm-file-cpp/
 * https://www.gbengasesan.com/fyp/69/ch12.htm
 * https://stackoverflow.com/questions/39684820/java-implementation-of-gaussian-blur
 * https://stackoverflow.com/questions/41051119/java-image-byte-array-to-grayscale-back-to-image
 */