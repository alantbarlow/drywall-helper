// [Project.java]

// Import the required libraries needed for the Project.java file
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

// Create a new class of Project to handle the calculations needed for the project
public class Project {

  // Create the variables needed for the Project class
  private int surfaceArea = 0;
  private HashMap<Integer, Integer> drywallSizes = new HashMap<>(); // Initialize as a new HashMap and set the keys and
                                                                    // values upon class intialization
  private int selectedDrywall;

  // Create a constructor for the Project class
  public Project() {
    // Create the HashMap of drywall size options where the key is the length and
    // the value is the square footage. The width of every drywall length option is
    // 4 feet.
    drywallSizes.put(8, 8 * 4);
    drywallSizes.put(10, 10 * 4);
    drywallSizes.put(12, 12 * 4);
    drywallSizes.put(14, 14 * 4);
    drywallSizes.put(16, 16 * 4);
  }

  // Create a method to calculate the surface area of the given doubles and add it
  // to the surfaceArea variable of the project
  public void addSurfaceArea(double length, double width) {
    this.surfaceArea += length * width;
  }

  // Create a method to calculate the surface area of the given doubles and remove
  // it to the surfaceArea variable of the project
  public void subtractSurfaceArea(double length, double width) {
    this.surfaceArea -= length * width;
  }

  // Create a method that requests an integer and sets it as the selected drywall
  // size
  public void setSelectedDrywall(int selectedDrywall) {
    this.selectedDrywall = selectedDrywall;
  }

  // Create a method that gets the selected drywall size of the project and
  // returns it, thereby allowing the private variable to be read
  public int getSelectedDrywall() {
    return this.selectedDrywall;
  }

  // Create a method for returning the drywall length options
  public Integer[] getDrywallLengthOptions() {
    // Create and initialize a variable that will hold the length options of the
    // drywall
    final Set<Integer> KEY_SET = drywallSizes.keySet();

    // Convert the keys of the HashMap into an array
    Integer[] optionsArray = KEY_SET.toArray(new Integer[0]);

    // Sort the array to be in ascending order
    Arrays.sort(optionsArray);

    // return the sorted array
    return optionsArray;
  }

  // Create a method that gets the surface area of the project and returns it,
  // thereby allowing the private variable to be read
  public int getSurfaceArea() {
    return this.surfaceArea;
  }

  // Create a method that returns the drywall needed for the project
  public int getDrywallNeeded() {

    // Create a variable that will hold the drywall needed that is calculated by
    // dividing the surface area by the selected drywall size
    final double DRYWALL_NEEDED = (double) this.surfaceArea / this.drywallSizes.get(this.selectedDrywall);

    // Convert the drywall needed to an integer rounded up to the nearest whole
    // number and return it
    return (int) Math.ceil(DRYWALL_NEEDED);
  }
}