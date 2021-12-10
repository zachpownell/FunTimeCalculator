/*

Name: Zach Pownell
Professor: Cary Jardin
Class: CS311 Data Structures & Algorithms
Last Modified: 07 December, 2021

Description: Made this for CS311 as a final project. This program will use sorting algorithms to sort an array and show
             how many iterations (swaps) and seconds it took to sort (from nanoseconds). The array is of type int and
             is size 50,000 ranging from numbers between 0-1,000,000.
             When the program is first started, a new random array is generated and saved to our array.txt file. The
             user can choose which sorting algorithm to use to sort the array, and randomize the array again if desired.

             NOTE: There is a bug in which trying to sort an already sorted array using QuickSort results in a
                   StackOverFlow error. In the works of squashing it. >:( Removed for now.

             TODO: - Fix the QuickSort bug.
                   - Clean up the GUI setup.

 */

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

// Main class FunTimeInterface. Execute the main method to open up the interface and have a fun time sorting.
public class FunTimeInterface {

    public static void main(String[] args) {

        // Array manager used to manage our arrays.
        ArrayManager arrayManager = new ArrayManager();
        System.out.println("Starting...");

        // Creating frame and labels for the interface.
        // This looks a bit nasty - I'd like to clean this up, but it works fine for now...
        JFrame frame = new JFrame();
        JLabel displayLabel = new JLabel("-  FunTime Calculator  -", SwingConstants.CENTER);
        JLabel descriptionLabel = new JLabel("<html><center>This program will compare different sorting algorithms using Java.<br/>" +
                "We will use arrays of size 50,000 with integers ranging from 0-1,000,000.</center></html>", SwingConstants.CENTER);
        JLabel selectionLabel = new JLabel();
        JLabel randomizeArrayLabel = new JLabel();
        JLabel bigOLabel = new JLabel("<html><center>BubbleSort<br/>Best: O(n)  |  Avg: O(n^2)  |  Worst: O(n^2)</center></html>", SwingConstants.CENTER);
        JLabel runLabel = new JLabel();
        JLabel runtimeDisplayLabel = new JLabel("Array randomized. Saved to array.txt file.", SwingConstants.CENTER);

        // Set parameters for frame.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(0,1));
        frame.pack();
        frame.setSize(800,600);

        // Set fonts for the text labels.
        displayLabel.setFont(displayLabel.getFont().deriveFont(44.0f));
        descriptionLabel.setFont(descriptionLabel.getFont().deriveFont(16.0f));
        bigOLabel.setFont(bigOLabel.getFont().deriveFont(18.0f));
        runtimeDisplayLabel.setFont(runtimeDisplayLabel.getFont().deriveFont(18.0f));

        // Creating interactive buttons, selection box.
        String[] selections = {"BubbleSort", "SelectionSort", "InsertionSort"};
        JComboBox<String> selectionBox = new JComboBox<>(selections);
        JButton generateArrayButton = new JButton("Randomize Array");
        JButton runButton = new JButton("Sort Array!");

        // Add those interactive buttons and selection box to their respected labels.
        selectionLabel.add(selectionBox);
        randomizeArrayLabel.add(generateArrayButton);
        runLabel.add(runButton);

        // Set bounds and sizes for our interactive buttons and box.
        selectionBox.setBounds(340,0,120,30);
        generateArrayButton.setBounds(320,0,160,30);
        runButton.setBounds(290,0,220,50);

        // Add everything to the frame.
        frame.add(displayLabel);
        frame.add(descriptionLabel);
        frame.add(selectionLabel);
        frame.add(randomizeArrayLabel);
        frame.add(bigOLabel);
        frame.add(runLabel);
        frame.add(runtimeDisplayLabel);

        // Finally, make the frame visible.
        frame.setTitle("FunTime Calculator by Zach Pownell");
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        // Action event for when the sorting alrogithm selection box is changed. Used to display the different time
        // complexities of each sorting algorithm.
        selectionBox.addActionListener(e -> {

            // What does our selection box equal? Display corresponding time complexities.
            if (selectionBox.getSelectedItem() == "BubbleSort") {
                bigOLabel.setText("<html><center>BubbleSort<br/>Best: O(n)  |  Avg: O(n^2)  |  Worst: O(n^2)</center></html>");
            }
            /* ADD WHEN WORKING else if (selectionBox.getSelectedItem() == "QuickSort") {
                bigOLabel.setText("<html><center>QuickSort<br/>Best: O(n log(n))  |  Avg: O(n log(n))  |  Worst: O(n^2)</center></html>");
            }*/
            else if (selectionBox.getSelectedItem() == "SelectionSort") {
                bigOLabel.setText("<html><center>SelectionSort<br/>Best: O(n^2)  |  Avg: O(n^2)  |  Worst: O(n^2)</center></html>");
            }
            else if (selectionBox.getSelectedItem() == "InsertionSort") {
                bigOLabel.setText("<html><center>InsertionSort<br/>Best: O(n)  |  Avg: O(n^2)  |  Worst: O(n^2)</center></html>");
            }
            System.out.println(selectionBox.getSelectedItem() + " selected.");

        });

        // Another action event for when the sort array button is pressed. Determine which sorting algorithm and sort.
        runButton.addActionListener(e -> {

            runtimeDisplayLabel.setText("Calculating..."); // this doesn't show :(
            System.out.println("Calculating using " + selectionBox.getSelectedItem() + "...");

            // Assign sortedArray to the corresponding sorting algorithm's sort method.
            int[] sortedArray = {};
            int totalSwaps = 0;
            long startTime = System.nanoTime();

            // Each condition follows the same- create a new sort corresponding to the sorting algorithm selected.
            // Call the corresponding sorting algorithm's sort method and assign to sorted array. Once that's done,
            // assign total iterations to the corresponding sorting algorithm's getIterations method.
            if (selectionBox.getSelectedItem() == "BubbleSort") {

                BubbleSort bubbleSort = new BubbleSort();
                sortedArray = bubbleSort.sort(arrayManager.getArray());
                totalSwaps = bubbleSort.getSwaps();


            } /* ADD WHEN WORKING else if (selectionBox.getSelectedItem() == "QuickSort") {

                QuickSort quickSort = new QuickSort();
                sortedArray = quickSort.sort(arrayManager.getArray(), 0, arrayManager.getArray().length-1);
                totalIterations = quickSort.getIterations();


            }*/ else if (selectionBox.getSelectedItem() == "SelectionSort") {

                SelectionSort selectionSort = new SelectionSort();
                sortedArray = selectionSort.sort(arrayManager.getArray());
                totalSwaps = selectionSort.getSwaps();


            } else if (selectionBox.getSelectedItem() == "InsertionSort") {

                InsertionSort insertionSort = new InsertionSort();
                sortedArray = insertionSort.sort(arrayManager.getArray());
                totalSwaps = insertionSort.getSwaps();


            }

            // Use our arrayManager's writeArrayToFile method to write our sorted array to the array.txt file. Assign
            // totalTime to the endTime-startTime so we can display the total time to the user.
            arrayManager.writeArrayToFile(sortedArray);
            long totalTime = System.nanoTime() - startTime;
            runtimeDisplayLabel.setText("<html><center>Sorted using " + selectionBox.getSelectedItem() +
                    ". Saved to array.txt file.<br/>" + (double) totalTime/1000000000 + "" +
                    " seconds, " + totalSwaps + " swaps.</center></html>");
            System.out.println("Sorted using " + selectionBox.getSelectedItem() +  "" +
                    ". Saved to array.txt file.\n" + (double) totalTime/1000000000 +
                    " seconds, " + totalSwaps + " swaps.");

        });

        // Finally, an action event for when we want to randomize the array.
        generateArrayButton.addActionListener(e -> {

            arrayManager.randomizeArray();
            runtimeDisplayLabel.setText("Array randomized. Saved to array.txt file.");
            System.out.println("Array randomized. Saved to array.txt file.");

        });

    }


}


// ArrayManager class. Initializing this will create a new array of type int with our set parameters. Has four methods.
// Constructor will create our array by calling randomizeArray method. writeArrayToFile method to write our array to
// the array.txt file, randomizeArray to destroy the universe (just kidding), and getArray to return the array when called.
public class ArrayManager extends FunTimeInterface {

    private static final int ARRAY_SIZE = 50000, RANDOM_NUMBERS_BETWEEN = 1000000;
    private static int[] array = new int[ARRAY_SIZE];

    public ArrayManager() { randomizeArray(); }

    public void writeArrayToFile(int[] arr) {

        array = arr;

        try {

            FileWriter writer = new java.io.FileWriter("array.txt");
            for (int j : array) {
                writer.write(j + " ");
            }

            writer.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void randomizeArray() {

        for (int i = 0 ; i < array.length ; i ++) {
            array[i] = new Random().nextInt(RANDOM_NUMBERS_BETWEEN);
        }

        writeArrayToFile(array);

    }

    public int[] getArray() { return array; }

}


// BubbleSort class.
public class BubbleSort extends FunTimeInterface {

    int swaps;

    public BubbleSort() { swaps = 0; }

    public int[] sort(int[] array) {

        for (int i=0 ; i< array.length-1 ; i++) {

            //
            for (int j=0 ; j < array.length-i-1 ; j++) {

                if (array[j] > array[j+1]) {

                    int temp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = temp;
                    swaps ++;

                }

            }

        }

        return array;

    }

    public int getSwaps() { return swaps; }

}


// InsertionSort class.
public class InsertionSort extends FunTimeInterface {

    int swaps;

    public InsertionSort() { swaps = 0; }

    public int[] sort(int[] array) {

        for (int i=1 ; i < array.length ; i++) {

            int key = array[i];
            int j = i - 1;

            while (j >=0 && array[j] > key) {

                array[j+1] = array[j];
                j --;
                swaps ++;

            }

            array[j+1] = key;

        }

        return array;

    }

    public int getSwaps() { return swaps; }

}


// SelectionSort class.
public class SelectionSort extends FunTimeInterface {

    int swaps;

    public SelectionSort() { swaps = 0; }

    public int[] sort(int[] array) {

        for (int i=0 ; i<array.length-1 ; i++) {

            int minimum = i;

            for (int j=i+1 ; j<array.length ; j++) {

                if (array[j] < array[minimum]) {
                    minimum = j;
                    swaps ++;
                }

                int temp = array[minimum];
                array[minimum] = array[i];
                array[i] = temp;

            }

        }

        return array;

    }

    public int getSwaps() { return swaps; }

}


// QuickSort class. Comment this out for now since its bugging.

/*
public class QuickSort extends FunTimeInterface {

    int iterations;

    public QuickSort() { iterations = 0; }

    private void swap(int[] arr, int i, int j) {

        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;

    }

    private int partition(int[] arr, int low, int high) {

        int pivot = arr[high];
        int i = (low - 1);

        for (int j=low ; j<=high ; j++) {

            if (arr[j] < pivot) {

                i ++;
                swap(arr, i, j);

            }

        }

        swap(arr, i + 1, high);
        return (i + 1);

    }

    public int[] sort(int[] arr, int low, int high) {

        if (low < high) {

            int partitionIndex = partition(arr, low, high);
            sort(arr, low, partitionIndex - 1);
            sort(arr, partitionIndex + 1, high);
            iterations ++;

        }

        return arr;

    }

    public int getIterations() { return iterations; }

}*/