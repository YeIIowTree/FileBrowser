import java.io.File; 
import java.io.IOException;
import java.util.Scanner;
import java.util.Arrays;
import java.nio.file.StandardCopyOption;
import java.nio.file.Files;
import java.nio.file.Path;

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
      
      // These are used as temporary variables to make program functions easier to use
      File currentDirectory;
      File currentFile;
      
      // Variable used to collect input
      String input;
      
      // Variable used to exit the main loop
      boolean running = true;
      
      
      // Main loop of the program, 
      while (running)
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
               
               // Verifies that command has 1 or 2 arguments before executing
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
                     if (currentDirectory.exists() && currentDirectory.canRead() )
                     {
                        listDirectory(currentDirectory);
                     }
                     else
                     {
                        System.out.println("Cannot list files, directory does not exist or is not readable.");
                     }
                     break; 
                  default:
                     // Displays this error in the case that the list command is given with a
                     // third index
                     System.out.println("List command is not given in the form: list or list <directory>");
               }
               break;      
            
            case "navigate":
               // Verifies that input has the proper number of arguments, if not then give an error
               if (operation.length == 2)
               {
                  // Assigns the directory given to the currentDirectory variable
                  currentDirectory = new File(operation[1]);
                  
                  // If this directory is valid, and you have read access, then it
                  // is made the current directory. If not, give an error.
                  if (currentDirectory.exists() && currentDirectory.canRead() )
                  {
                     workingDirectory = currentDirectory;
                  }     
                  else
                  {
                     System.out.println("Failed to navigate to the specified directory. Directory does not exist or can not be read.");
                  }
               }
               else
               {
                  System.out.println("Navigate command is not given in the form: navigate <directory>");
               }
               break;
            
            case "make": 
            // Verifies that input is given with 2 arguments
            if (operation.length == 2)
            {
            currentFile = new File(workingDirectory, operation[1]);
            
               try
               {
                  if ( operation[1].contains("/") )
                  {
                     currentFile.mkdirs();
                  }
                  else
                  {
                  currentFile.createNewFile();
                  }  
               }
               catch(IOException e)
               {
                  System.out.println("Failed to create file");
               }
            }
            else
            {
               System.out.println("Make command is not given in the form: make <filename>");
            }
            break;
             
            
            case "copy":
               if (operation.length == 3) {
                  // Takes input into file variables so that file methods may be used on the input
                  currentFile = new File(workingDirectory, operation[1]);
                  currentDirectory = new File(operation[2]);

                  // Verifies that the destination directory exists and that you can write to it
                  if (currentDirectory.exists() && currentDirectory.canWrite()) {
                     // Verifies that the source file exists and can be read
                     if (currentFile.exists() && currentFile.canRead()) {
                        try {
                           // Resolving destination path to include the destination directory and source file name
                           File destinationFile = new File(currentDirectory, currentFile.getName());
                           Files.copy(currentFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                     
                        } 
                        catch (IOException e) 
                        {
                        e.printStackTrace();
                        }
                     }  
                     else 
                     {
                        System.out.println("Source file does not exist, is not readable, or is a directory.");
                     }
                  } 
                  else 
                  {
                     System.out.println("Destination directory does not exist or is not writable.");
                  }
               } 
               else 
               {
                  System.out.println("Copy command not given in the form: copy <filename> <directory>");
               }
               break;
            case "move":
            
            case "remove": 
            
            case "help":
            
            case "exit":
               running = false;
               break;
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
      // Gets the list of a directory's files
      String[] files = Directory.list();
      
      // Prints out each file in the list
      for (int i = 0; i < files.length; i++) 
      { 
         System.out.println(files[i]); 
      }
   }
}
