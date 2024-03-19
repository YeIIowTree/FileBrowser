// Goal: To make a terminal based file browser 
// in java that can do basic operations
// Date: 3/6/24

import java.io.File; 
import java.io.IOException;
import java.util.Scanner;
import java.util.Arrays;

public class FileBrowser 
{
   public static void main(String[] args)
   {
      Scanner keyboard = new Scanner(System.in);
      
      // Grab's the user's name to start the program in the home directory"
      String username = System.getProperty("user.name");
     
      // Tries to start in the home directory, if not valid the program will
      // start in the root directory 
      File workingDirectory = new File("/home/" + username + "/");
      if (workingDirectory.exists() )
      {
         
      }
      else
      {
         workingDirectory = new File("/");
      }
      File currentDirectory;
      
      // Variable used to collect input
      String input;
      // Variable used to hold files when listing them
      
      // Main loop of the program, 
      while (true)
      {
         // Displays the working directory for ease of viewing
         System.out.print("Directory: " + workingDirectory + ": ");
         
         // Grabs input from the user
         input = keyboard.nextLine();   
         
         // Splits the input into the operator, filename, 
         // and directory (if a directory is given) in a
         // String array called operator
         String[] operation = new String[0];
         operation = splitInput(input, operation);
         
         
         // Switch statement 
         switch(operation[0])
         {
            case "list":
               
               switch (operation.length)
               {
                  case 1:
                     listDirectory(workingDirectory);
                     break;
                  case 2:
                     // Assigns directory given by the user to a variable which will be used
                     // by the listDirectory() method.
                     currentDirectory = new File(operation[1]);
                     
                     // Validates that the directory in the second index exists, if it
                     // exists files will be listed
                     if (currentDirectory.exists() )
                     {
                        listDirectory(currentDirectory);
                     }
                     else
                     {
                        System.out.println("Cannot list files, directory does not exist.");
                     }
                     break; 
                  default:
                     // Displays this error in the case that the list command is given with a
                     // third index
                     System.out.println("list command is not given in the form list, or list <directory>");
                  
               }
               
         }
          
      }
   }
   public static String[] splitInput(String input, String[] operation)
   {
      // Converts the user's input from a String to an array in the format of [operator, filename, directory]
      operation = input.trim().split(" ", 3);
      return operation;
      
      
   }
   public static void listDirectory(File Directory)
   {
      String[] files = Directory.list();
      for (int i = 0; i < files.length; i++) 
      { 
         System.out.println(files[i]); 
      }
   }
}