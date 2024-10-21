// [Main.java]

// Import the required libraries needed for the Main.java file
import java.util.Arrays;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.InputMismatchException;

// Create a new class of Main to handle the input and output of our program
public class Main {
  // Create variables to store the Project and Scanner objects needed for the
  // program
  private final Project PROJECT;
  private final Scanner SCANNER;

  // Create the entrypoint for the program
  public static void main(String[] args) {
    // Create a new Main object which runs the constructor for the Main class
    new Main();
  }

  // Create the constructor for the Main class
  public Main() {
    // Initialize the Project and Scanner objects
    this.PROJECT = new Project();
    this.SCANNER = new Scanner(System.in);

    // Output the welcome message to the user
    System.out.println(
        "Welcome to Drywall Helper!\n\nThis program helps you determine how many pieces of drywall you will need for your project assuming you are utilizing as much scraps as possible. It starts by getting the dimensions of all the surface areas that need drywall. Then it gets all the surface areas that need to be removed like windows and doors. Finally, it gets the drywall size you are using and returns the minimum number of drywall sheets needed rounded up to the nearest whole number. To get started, complete the following:");

    // Initialize a new variable to store what part of the program the user is on
    int part = 0;

    // Create a loop to handle each part of the program
    while (part < 4) {
      // Increment the part variable
      part++;

      // Call requestInputForPart method while passing in the part variable
      this.requestInputForPart(part);

      // Exit the loop if the surface area is less than 1
      if (this.PROJECT.getSurfaceArea() < 1) {
        break;
      }
    }

    // Check the value of the part variable to see how far the user got in the program before it exited the loop
    if (part == 1) {
      // Output the message for not having any surface area for the project
      System.out.println("\nYou don’t have any surface area and therefore don’t need any drywall for your project.");

    } else if (part == 2) {
      // Output the message for removing too much surface area for the project
      System.out.println("\nYou removed too much surface area and no longer need drywall for your project.");

    } else {
      // Output the message indicating how much drywall is needed for the project
      System.out.println("\nYou will need at least " + this.PROJECT.getDrywallNeeded() + " sheet(s) of your chosen "
          + this.PROJECT.getSelectedDrywall() + "' drywall to complete your project.");
    }

    // Close the Scanner object as it will no longer be needed
    this.SCANNER.close();
  }

  // Create a method to handle the input for each of the 3 parts of the program
  private void requestInputForPart(int part) {

    // Check if the user is on part 1 or 2 of the program
    if ((part == 1) || (part == 2)) {

      // Create the needed variables for part 1 and 2 of the program
      final String TEXT_FOR_NUM_OF_SURFACES_REQUEST;
      final BiConsumer<Double, Double> PROJECT_RUN_METHOD;
      final String TEXT_FOR_ANY_MORE_SURFACES_REQUEST;

      // Initialize the variables for part 1 and 2 of the program based on the current
      // part
      if (part == 1) {
        TEXT_FOR_NUM_OF_SURFACES_REQUEST = "\nEnter the number of surfaces you would like to add to your project: ";
        PROJECT_RUN_METHOD = this.PROJECT::addSurfaceArea;
        TEXT_FOR_ANY_MORE_SURFACES_REQUEST = "\nThe surface area calculations made. Before we move on, would you like to add any additional surface areas? Please enter either ‘yes’ or ‘no’: ";
      } else {
        TEXT_FOR_NUM_OF_SURFACES_REQUEST = "\nEnter the number of surfaces you would like to remove from your project (eg. surfaces areas like doors or windows): ";
        PROJECT_RUN_METHOD = this.PROJECT::subtractSurfaceArea;
        TEXT_FOR_ANY_MORE_SURFACES_REQUEST = "\nThe surface area calculations made. Before we move on, would you like to remove any additional surface areas? Please enter either ‘yes’ or ‘no’: ";
      }

      // Create and initialize a new variable to indicate whether the part is completed or not
      Boolean completed = false;

      // Create a while loop that will run until the part is completed
      while (!completed) {

        // Output the message asking for the number of surfaces the user wants to add or remove
        System.out.print(TEXT_FOR_NUM_OF_SURFACES_REQUEST);

        // Create and initialize a new variable to store the number of surfaces the user wants to add or remove and set it to the result of calling the getIntForRequest method while passing in the error message
        final int NUMBER_OF_SURFACES = this
            .getIntForRequest("please enter a valid whole number for the number of surfaces");

        // Break out of the loop if the number of surfaces is less than 1
        if (NUMBER_OF_SURFACES < 1) {
          break;
        }

        // Loop through the number of surfaces the user wants to add or remove
        for (int i = 0; i < NUMBER_OF_SURFACES; i++) {
          // For each surface, output the message asking for the length and width of the surface
          System.out.print("\nEnter the length and width (in feet) of surface #" + (i + 1)
              + " while providing a single space between the two numbers: ");

          // Create and initialize a new variable to store the length and width of the surface and set it to the result of calling the getDoubleForRequest method while passing in the error message
          final double LENGTH = this.getDoubleForRequest("please enter a valid positive number for the length");
          final double WIDTH = this.getDoubleForRequest("please enter a valid positive number for the width");

          // Call the PROJECT_RUN_METHOD method while passing in the length and width variables
          PROJECT_RUN_METHOD.accept(LENGTH, WIDTH);
        }
        
        // Output the message asking for any more surface areas
        System.out.print(TEXT_FOR_ANY_MORE_SURFACES_REQUEST);

        // Move the scanner to the next line
        this.SCANNER.nextLine();

        // Set the completed variable to the result of the isPartCompleted method
        completed = this.isPartCompleted();
      }

    // If the user is not on part 1 or 2 of the program, check if they are on part 3
    } else if (part == 3) {

      // Create and initialize the variables for the drywall length options
      final Integer[] DRYWALL_LENGTH_OPTIONS = this.PROJECT.getDrywallLengthOptions();
      final String OPTIONS_STRING = Arrays.toString(DRYWALL_LENGTH_OPTIONS);

      // Output the message for selecting the drywall length
      System.out.print("\nThe final step is to enter the drywall length you will be using "
          + OPTIONS_STRING + " in feet: ");

      // Create and initialize the selected drywall length variable
      final int SELECTED_DRYWALL_LENGTH = this.getIntForRequest(DRYWALL_LENGTH_OPTIONS,
          "please enter one of the following length options, " + OPTIONS_STRING);

      // Set the selected drywall length of the project using the associated variable
      this.PROJECT.setSelectedDrywall(SELECTED_DRYWALL_LENGTH);
    }
  }

  // Create the getIntForRequest method that requires only the error text and returns the result of the similarly named method that also requires an array of options. This makes it so that the getIntForRequest method can be called with only one parameter instead of two, therefore making one of them optional.
  private int getIntForRequest(String invalidText) {
    // return the result of the getIntForRequest method while passing in the invalid text and an empty list of options
    return this.getIntForRequest(new Integer[0], invalidText);
  }

  // Create the getIntForRequest method that requires both the error text and an array of options and returns
  private int getIntForRequest(Integer[] options, String invalidText) {
    // Loop until the user enters a valid integer
    while (true) {

      // Try too return an integer from the user input
      try {

        // Create and initialize a new variable to store the user input as an Integer
        final int INPUT = this.SCANNER.nextInt();
        
        // If the options array is empty or if the input is in the options array then return the input
        if (options.length == 0 || Arrays.asList(options).contains(INPUT)) {
          return INPUT;
          
        // If the input is not in the options array then output the invalid text and continue the loop
        } else {
          throw new InputMismatchException("Input not in contained in options");
        }
      // If the scanner cannot return an integer or if the integer is not contained inside the options array, catch the exception, move the scanner to the next line, and output the error message
      } catch (InputMismatchException e) {
        this.SCANNER.nextLine();
        System.out.print("\nInput invalid, " + invalidText + " : ");
      }
    }
  }

  private double getDoubleForRequest(String invalidText) {
    // Loop until the user enters a valid double
    while (true) {
      // Try to return a double from the user input
      try {
        return this.SCANNER.nextDouble();

      // If the scanner cannot return a double, catch the exception, move the scanner to the next line, and output the error message
      } catch (InputMismatchException e) {
        this.SCANNER.nextLine();
        System.out.print("\nInput invalid, " + invalidText + " : ");
      }
    }
  }

  // Create a method that returns if the user is done with a part of the program based on the input from the user
  private Boolean isPartCompleted() {

    // Create a while loop that loops until the method's return statement is called
    while (true) {
      // create and initialize a variable to hold the user's input into the program
      final String INPUT = this.SCANNER.nextLine().toLowerCase();

      // Check if the user's input is either "yes" or "no" and return the appropriate boolean, or output an error message
      if (INPUT.equals("no")) {
        return true;
      } else if (INPUT.equals("yes")) {
        return false;
      } else {
        System.out.print("\nInput invalid, please enter either 'yes' or 'no': ");
      }
    }
  }
}