import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.File;

public class CSV2HTML {

    public static void ConvertCSVtoHTML(Scanner sc, PrintWriter pw) throws CSVAttributeMissing, CSVDataMissing {


        
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

    public static void main(String args[]) {
        Scanner sc = null;
        PrintWriter pw = null;
        Scanner keyboard = new Scanner(System.in);

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
        String filepath = "C:\\A03Output";
        File outputFolder = new File(filepath);

        // For loop to repeat file creation for every file

        for (int i = 0; i < fileNb; i++) {

        // Ask for file names
        int fileCounter=1;
        System.out.println("Please enter the name of file #"+Integer.toString(fileCounter));
        System.out.println("Do NOT include the file extension!");
        String filename = keyboard.nextLine();
        fileCounter++;

        // Opening Input files
        try {
            sc = new Scanner(new FileInputStream(filename+".csv"));
         } catch (FileNotFoundException e) {
             System.err.println("Could not open file "+filename+" for reading.");
             System.err.println("Please check that the file exists and is readable. This program will terminate after closing any opened files.");
             sc.close();
             System.exit(0);
         }

         // Opening Output files
        try {
            pw = new PrintWriter(new FileOutputStream("C:\\A03Output\\"+filename+".html"));
         } catch (FileNotFoundException e) {
             System.err.println("Could not open file "+filename+" for reading.");
             System.err.println("Please check that the file exists and is readable. This program will terminate after closing any opened files.");
             pw.close();
             deleteDirectory(outputFolder);
             sc.close();
             System.exit(0);
            }

            // Requirement 4 - Calls ConvertCSVtoHTML method
            try {
                ConvertCSVtoHTML(sc, pw);
            } catch (CSVAttributeMissing e) {
                //TODO: handle exception
            } catch (CSVDataMissing e) {
                //TODO: handle exception
            }
        }
        
        // Requirement 5 - Display the HTML files in output directory
    }
}
