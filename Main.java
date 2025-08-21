import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static Scanner kbInput = new Scanner ( System.in );
    public static List<String> transformations = new ArrayList<> ();

    public static void main( String[] args ) {

        System.out.print ( "--------- Image Editor ----------\n" );
        System.out.println ( "Enter name or path of the original image file: " );
        String inputFile = kbInput.nextLine ();

        System.out.println ( "Enter name of output file: " );
        String outputFile = kbInput.nextLine ();
        int choices;
 
        do {
            System.out.println ( """
                    Choose your transformation:
                    1. Invert Red\s
                    2. Invert Green\s
                    3. Invert Blue\s
                    4. GrayScale\s
                    5. Flip Horizontal\s
                    6. Remove Red\s
                    7. Remove Green\s
                    8. Remove Blue\s
                    9. More Options\s
                    10. Done\s
                                        
                    What is your choice?""" );

            choices = Integer.parseInt ( kbInput.nextLine () );
            mainmenu ( choices );

        } while (choices != 10);

        ImageProcessing.imageProcessing ( inputFile, outputFile, transformations );
    }

    public static boolean mainmenu( int choices ) {
        String transformation = "";

        if ( choices >= 1 && choices <= 8 ) {
            switch (choices) {
                case 1 -> transformation = "invertRed";
                case 2 -> transformation = "invertGreen";
                case 3 -> transformation = "invertBlue";
                case 4 -> transformation = "convertToGrayScale";
                case 5 -> transformation = "flipHorizontal";
                case 6 -> transformation = "removeRed";
                case 7 -> transformation = "removeGreen";
                case 8 -> transformation = "removeBlue";
            }
            transformations.add ( transformation );
            System.out.println ( "Applying transformation... \n" );

        } else if ( choices == 9 ) {
            while (submenu ()) {
            }
        } else if ( choices != 10 ) {
            System.out.println ( "Invalid choice." );
        }

        return true;
    }

    public static boolean submenu() {
        System.out.println ( "Choose a transformation: \n " );
        System.out.printf (
                "1. Add Blur \n" +
                        "2. Add Noise \n" +
                        "3. Add Contrast \n" +
                        "4. Main Menu \n" +
                        "%n" );
        System.out.println ( "What is your choice? " );
        String subMenu = kbInput.nextLine ();

        switch (subMenu) {
            case "1" -> {
                transformations.add ( "blur" );
                System.out.println ( "Applying transformation..." );
            }
            case "2" -> {
                transformations.add ( "noise" );
                System.out.println ( "Applying transformation..." );
            }
            case "3" -> {
                transformations.add ( "contrast" );
                System.out.println ( "Applying transformation..." );
            }
            case "4" -> {
                return false;
            }
            default -> System.out.println ( "Invalid choice." );
        }
        return true;
    }
}

/*
 *https://stackoverflow.com/questions/4455873/java-arraylist-to-store-user-input
 */