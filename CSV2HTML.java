import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.File;

public class CSV2HTML {

    public static void ConvertCSVtoHTML(Scanner sc, PrintWriter pw, String file) throws CSVAttributeMissing {
        // Save title line
        sc.skip("ï»¿");
        String title = sc.nextLine();

        // Print title
        System.out.println(title);

        // Save attributes
        String attributes = sc.nextLine();
        String[] attributeList = attributes.split(",");

        // Check for misisng attributes and throw exception if missing
        for (int i = 0; i < attributeList.length ; i++) {
            if (attributeList[i] == "")
                throw new CSVAttributeMissing();
        }

        // Print attributes
        for (int i = 0; i < attributeList.length ; i++) {
            System.out.println(attributeList[i]);
        }
        
        // While loop that handles saving and printing of data and note lines
        String note;
        int line = 3, missingIndex = 0;

        while(sc.hasNextLine()) {
            // Save Note (in savedata)
            if (sc.hasNextLine() == false) {
                note = sc.nextLine();

                // Write note
                System.out.println(note);
            }
             
            // Saving and Printing Data
            try {
                // Saving Data
                String data = sc.nextLine();
                String[] dataList = data.split(",");

                // Check for missing data and throw exception if missing
                for (int i = 0; i < dataList.length ; i++) {
                    if (dataList[i] == "") {
                        missingIndex = i;
                        throw new CSVDataMissing();
                    }
                }
                // Write data
                for (int i = 0; i < dataList.length ; i++) {
                    System.out.println(dataList[i]);
                }
            } catch (CSVDataMissing e) {
                //TODO: handle exception
                appendToLogs("WARNING: In file "+file+".csv line "+Integer.toString(line)+" is not converted to HTML: missing data: "+attributeList[missingIndex]);
            }
            line++;
        }
    }
    
    // function to delete subdirectories and files
    public static void deleteDirectory(File file) {
        // store all the paths of files and folders present
        // inside directory
        for (File subfile : file.listFiles()) {
            // if it is a subfolder
            // recursiley call function to empty subfolder
            if (subfile.isDirectory()) {
                deleteDirectory(subfile);
            }
            // delete files and empty subfolders
            subfile.delete();
        }
    }

    public static void appendToLogs(String aString) {
        // Opening Exceptions file
        PrintWriter logs = null;

        try {
            logs = new PrintWriter(new FileOutputStream("C:/A03Output/Exeptions.log"), true);
            logs.println(aString);
         } catch (FileNotFoundException e) {
             System.err.println("Could not open Exceptions file for logging.");
             System.err.println("This program will now terminate ...");
             System.exit(0);
            }
    }

    public static void main(String args[]) {
        Scanner sc = null;
        PrintWriter pw = null;
        Scanner keyboard = new Scanner(System.in);
        int fileCounter=1;

        // Welcome Message
        System.out.println("=======================================");
        System.out.println("Hello, Welcome to my CSV2HTML Program!");
        System.out.println("=======================================");
        System.out.println();

        // Ask for amount of files
        System.out.println("Please enter the amount of files you wish to convert");
        int fileNb = keyboard.nextInt();
        keyboard.nextLine();

        // Create output file directory
        String filepath = "C:/A03Output/";
        File outputFolder = new File(filepath);

        // Clear output directory from previous operations
        deleteDirectory(outputFolder);

        // For loop to repeat file creation for every file

        for (int i = 0; i < fileNb; i++) {

        // Ask for file names
        
        System.out.println("Please enter the name of file #"+Integer.toString(fileCounter));
        System.out.println("Do NOT include the file extension!");
        String filename = keyboard.nextLine();
        fileCounter++;

        // Opening Input files
        try {
            sc = new Scanner(new FileInputStream("C:/A03Input/"+filename+".csv"));
         } catch (FileNotFoundException e) {
             appendToLogs("Could not open file "+filename+" for reading.");
             appendToLogs("Please check that the file exists and is readable. This program will terminate after closing any opened files.");
             sc.close();
             System.exit(0);
         }

         // Opening Output files
        try {
            pw = new PrintWriter(new FileOutputStream("C:/A03Output/"+filename+".html"));
         } catch (FileNotFoundException e) {
             appendToLogs("Could not open file "+filename+" for reading.");
             appendToLogs("Please check that the file exists and is readable. This program will terminate after closing any opened files.");
             deleteDirectory(outputFolder);
             sc.close();
             System.exit(0);
            }

            // Requirement 4 - Calls ConvertCSVtoHTML method
            try {
                ConvertCSVtoHTML(sc, pw, filename);
                pw.close();
            } catch (CSVAttributeMissing e) {
                //TODO: handle exception
                appendToLogs("ERROR: In file "+filename+".csv. Missing attribute. File is not converted to HTML.");
            }
        }
        // Requirement 5 - Display the HTML files in output directory
    }
}
