package orderprocessing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class OrderProcessor {
    
    //Formats my decimals to output with 4 decimals
    NumberFormat formatter = new DecimalFormat("#0.0000");
    //Reader and Writer for the files
    private BufferedReader myReader = null;
    private PrintWriter myWriter = null;
    //final tax and shipping rates
    private final double shippingRate = .05;
    private final double taxRate = .02;
    //Strings to hold file places
    private String outputFile;
    private String inputFile;
    //String that will hold the text that's in the input file
    private String inputs = "";

    /**
     * Basic constructor with no parameters
     */
    public OrderProcessor() {
        this.inputFile = "orders/Orders.txt";
        this.outputFile = "orders/OrdersProcessed.txt";
    }
    
    /**
     * Constructor that takes to strings for the input and output files
     * @param input
     * @param output 
     */
    public OrderProcessor(String input, String output) {
        this.inputFile = input;
        this.outputFile = output;
    }
    
    /**
     * Opens the files that are read and written too
     */
    public void Open(){
        try
        {
            //Creates reader and writer
            myReader = new BufferedReader(new FileReader(new File(inputFile)));
            myWriter = new PrintWriter(new BufferedWriter(new FileWriter(outputFile)));
            System.out.println("File Opened...");
        }
        //exception for a not found file
        catch (FileNotFoundException e)
        {   
            System.out.println(e.getClass().getName());
            System.out.println("Cannot find your file");
        } 
        //eception for an extra error
        catch (IOException ex) {
            System.out.println(ex.getClass().getName());
            System.out.println("Other Error");
        }
    }
    
    /**
     * Reads the next line and returns the value
     * @return 
     */
    public String Read(){

        try
        {
            //reads a line
            inputs = myReader.readLine();
        }
        //no more lines too read
        catch(EOFException eofe)
        {
            System.out.println("Out of lines to read");
            System.exit(0);
        }
        //another line reading error
        catch(IOException e)
        {
            System.out.println("Error in line reading");
        }
        
        return inputs;
    }
    
    /**
     * Uses the read method to run through the file and organize the data so it 
     * can be used to do a few calculations
     */
    public void Calculations_And_Output(){
        
        while(Read() != null){
           
            //array that holds all of the values from the file
            String[] allValues = inputs.split("\\|");
            //ArrayLists that hold the price and quantity
            ArrayList<Double> quantity = new ArrayList<>();
            ArrayList<Double> price = new ArrayList<>();
            
            for(int i = 0; i < allValues.length; i++)
            {
                //if the length values cannot be divided evenly by 4,3,or 2 it becomes the PartNum
                if((i % 4 != 0) && (i % 3 != 0) && (i % 2 != 0))
                {
                    myWriter.println("PartNum:" + "    " + allValues[i]);
                }
                //if the length values cannot be divided evenly by 4 or 3 it becomes the Price
                else if((i % 4 != 0) && (i % 3 != 0))
                {
                    price.add(Double.parseDouble(allValues[i]));
                    myWriter.println("Price:" + "      " + allValues[i]);
                }
                //if the length values cannot be divided evenly by 4 it becomes the Quantity
                else if(i % 4 != 0)
                {
                    quantity.add(Double.parseDouble(allValues[i]));
                    myWriter.println("Quantity:" + "   " + allValues[i]);
                }
                else
                {
                    if(allValues[i].equals(""))
                    {  
                    }
                    else
                    {
                        myWriter.println("OrderID:" + "    " + allValues[i]);  
                    }
                    
                }  
            }
            
            for(int x = 0; x < price.size(); x++)
            {
                //variables for output values
                Double startingPrice = (price.get(x) * quantity.get(x));
                Double tax =  startingPrice * taxRate;
                Double shipping = startingPrice * shippingRate;
                Double totalPrice = startingPrice + tax + shipping;
                
                //outputs the remainding values
                myWriter.println("Tax:" + "        " + formatter.format(tax));
                myWriter.println("Shipping:" + "   " + formatter.format(shipping));
                myWriter.println("Total:" + "      " + (formatter.format(totalPrice)));
                myWriter.println("- - - - - - - - - -");
            }
        }
    }
    
    /**
     * Closes the files
     */
    public void Close(){
        try
        {
            //closes the reader and writer
            myReader.close();
            myWriter.close();
            System.out.println("File Closed...");
        }
        //error incase file cannot be closed
        catch (IOException e) {
            System.out.println(e.getClass().getName());
            System.out.println("Error in closing a file");
        }
    }
}
