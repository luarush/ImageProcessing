import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ImageProcessing {

    public static void imageProcessing( String inputFile, String outputFile, List<String> transformations ) {
       
        //Read the image file and process it according to the transformations specified
        //The image is read in chunks, and each chunk is processed based on the transformations
        try (FileInputStream inputStream = new FileInputStream ( inputFile );
             FileOutputStream outputStream = new FileOutputStream ( outputFile )) {

            //Variables to hold the header information and image data
            //The header contains information about the image type, dimensions, and maximum color value    
            byte data;
            int count = 0;
            StringBuilder header = new StringBuilder ();
            String fileType = null;
            int imageWidth = 0;
            int imageHeight = 0;
            int maxColorValue = 0;

            //Read the header information from the input file
            //The header is read until the end of the file or until all header information is collected
            //Byte by byte, the header is constructed
            while (( data = (byte) inputStream.read () ) != -1 && count < 4) {
                char character = (char) data;

                //Skip comments in the header
                //Comments start with '#' and continue until the end of the line
                if ( character == '#' ) {

                    //Read until the end of the line
                    //This is done to skip comments in the header
                    while (( data = (byte) inputStream.read () ) != -1 && data != '\n') {
                        // do nothing
                    }
                    continue;
                }

                //If the character is a space or newline, it indicates the end of a header field
                //The header fields are separated by spaces or newlines
                if ( ( character == ' ' || character == '\n' ) && ( !header.isEmpty () ) ) {
                    
                    //If the header field is complete, process it
                    //The first field is the file type, the second is the width, the third is the height, and the fourth is the maximum color value
                    //The count variable keeps track of which header field is being processed
                    if ( count == 0 ) {
                        fileType = header.toString ().trim ();
                    } else {
                        int imgHeaderInfo = Integer.parseInt ( header.toString ().trim () );
                    
                        if ( count == 1 ) {
                            imageWidth = imgHeaderInfo;
                        } else if ( count == 2 ) {
                            imageHeight = imgHeaderInfo;
                        } else if ( count == 3 ) {
                            maxColorValue = imgHeaderInfo;
                        }
                    }

                    //Reset the header StringBuilder for the next field
                    //This is done to prepare for the next header field
                    header = new StringBuilder ();
                    count++;

                    //If we have read all four header fields, break the loop
                    //'\n' indicates the end of the header
                    if ( character == '\n' && count == 4 ) {
                        break;
                    }
                //Append the character to the header StringBuilder
                } else {
                    header.append ( character );
                }
            }

            //headBuilder is used to construct the header for the output file
            //The header is written to the output file
            String headerBuilder = fileType + "\n" + imageWidth + " " + imageHeight + "\n" + maxColorValue + "\n";
            outputStream.write ( headerBuilder.getBytes () );

            //Process each row of the image
            //Each row is read as a byte array, and the transformations are applied to each row
            int chunkSize = imageWidth * 3;
            byte[] row = new byte[chunkSize];

            //Read each row of the image and apply the transformations
            //The row is read as a byte array, and each transformation is applied to the row
            for (int i = 0; i < imageHeight; i++) {

                if (inputStream.read(row) != -1) {
                    for (String transformation : transformations) {
                        imgTransformation(row, transformation, maxColorValue); // Apply the transformation to the row
                    }
                    //Write the transformed row to the output file
                    outputStream.write(row);
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace ();
        }

    }

    //Apply the specified transformations to the row of image data
    //This method takes a row of image data and applies the specified transformations to it
    private static void imgTransformation( byte[] row, String transformations, int maxColorValue ) {
        switch (transformations) {
            case "invertRed" -> Transformations.invertRed ( row, maxColorValue );
            case "invertGreen" -> Transformations.invertGreen ( row, maxColorValue );
            case "invertBlue" -> Transformations.invertBlue ( row, maxColorValue );
            case "convertToGrayScale" -> Transformations.convertToGrayScale ( row );
            case "flipHorizontal" -> Transformations.flipHorizontal ( row );
            case "removeGreen" -> Transformations.removeGreen ( row );
            case "removeBlue" -> Transformations.removeBlue ( row );
            case "removeRed" -> Transformations.removeRed ( row );
            case "blur" -> Transformations.blur ( row );
            case "contrast" -> Transformations.contrast ( row, maxColorValue );
            case "noise" -> Transformations.noise ( row, maxColorValue );
            default -> System.out.println ( "Invalid transformations choice." );
        }
    }
}

/*
 * https://copyprogramming.com/howto/reading-binary-file-byte-by-byte#reading-binary-file-byte-by-byte
 * http://josiahmanson.com/prose/optimize_ppm/
 */




