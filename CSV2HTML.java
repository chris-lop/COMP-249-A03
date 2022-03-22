import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.File;

public class CSV2HTML {

    public static void ConvertCSVtoHTML(Scanner sc, PrintWriter pw, String file) throws CSVAttributeMissing {
        // Printing HTML page Head Section
        pw.println("<html>");
        pw.println("<style>");
        pw.print("table {font-family: arial, sans-serif;border-collapse: collapse;}");
        pw.print("td, th {border: 1px solid #000000;text-align: left;padding: 8px;}");
        pw.print("tr:nth-child(even) {background-color: #dddddd;}");
        pw.print("span{font-size: small}");
        pw.println("</style>");
        
        pw.println("<body>");

        pw.println("<table>");
        
        // Save title line
        sc.skip("ï»¿");
        String title = sc.nextLine();
        String[] titleList = title.split(",");

        // Print title
        pw.println("<caption>"+titleList[0]+"</caption>");

        // Save attributes
        String attributes = sc.nextLine();
        String[] attributeList = attributes.split(",");

        // Check for misisng attributes and throw exception if missing
        for (int i = 0; i < attributeList.length ; i++) {
            if (attributeList[i] == "")
                throw new CSVAttributeMissing();
        }
        
        pw.println("<tr>");

        // Print attributes
        for (int i = 0; i < attributeList.length ; i++) {
            pw.println("<td>"+attributeList[i]+"</td>");
        }

        pw.println("</tr>");
        
        // While loop that handles saving and printing of data and note lines
        String note;
        int line = 3, missingIndex = 0;

        while(sc.hasNextLine()) {
            // Save Note (in savedata)
            // Saving and Printing Data
            try {
                // Saving Data
                String data = sc.nextLine();
                String[] dataList = data.split(",");

                if (sc.hasNextLine() == false) {
                    // Write note
                    pw.println("</table>");
                    pw.println("<span>"+dataList[0]+"</span>");
                    break;
                }

                // Check for missing data and throw exception if missing
                for (int i = 0; i < dataList.length ; i++) {
                    if (dataList[i] == "") {
                        missingIndex = i;
                        throw new CSVDataMissing();
                    }
                }

                pw.println("<tr>");

                // Write data
                for (int i = 0; i < dataList.length ; i++) {
                    pw.println("<td>"+dataList[i]+"</td>");
                }

                pw.println("</tr>");

            } catch (CSVDataMissing e) {
                //TODO: handle exception
                appendToLogs("WARNING: In file "+file+".csv line "+Integer.toString(line)+" is not converted to HTML: missing data: "+attributeList[missingIndex]);
            }
            line++;
        }
        pw.println("</body>");
        pw.println("</html>");
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
