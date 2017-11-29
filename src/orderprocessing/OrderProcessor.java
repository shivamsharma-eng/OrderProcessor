package orderprocessing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;



public class OrderProcessor {
    
    private BufferedReader myReader = null;
    private PrintWriter myWriter = null;
    private final double shippingRate = .05;
    private final double taxRate = .02;
    private String inputs = "";
    private String outputFile;
    private String inputFile;

    public OrderProcessor() {
        this.inputFile = "orders/Orders.txt";
        this.outputFile = "orders/FinalData.txt";
    }
    
    public OrderProcessor(String input, String output) {
        this.inputFile = input;
        this.outputFile = output;
    }
    
    public void Open(){
        try
        {
            myReader = new BufferedReader(new FileReader(new File(inputFile)));
            myWriter = new PrintWriter(new BufferedWriter(new FileWriter(outputFile)));
        }
        catch (FileNotFoundException e)
        {   
            System.out.println(e.getClass().getName());
            System.out.println("Wrong file location");
        } 
        catch (IOException ex) {
            System.out.println(ex.getClass().getName());
            System.out.println("Wrong file location");
        }
    }
    
    public String Read(){
        
        try
        {
            inputs = myReader.readLine();
        }
        catch(IOException e)
        {
            System.out.println("Error in line reading");
        }
        return inputs;
    }
    
    public void Calculations_And_Output(){
        
        while(Read() != null){
           
            String[] allValues = inputs.split("\\|");
            Double[] Tax = new Double[6];
            Double[] Shipping = new Double[6];
            Double[] Total = new Double[6];
            
            for(int i = 0; i < allValues.length; i++)
            {
                if((i % 4 != 0) && (i % 3 != 0) && (i % 2 != 0))
                {
                    myWriter.println("OrderID:" + "    " + allValues[i]);
                }
                else if((i % 4 != 0) && (i % 3 != 0))
                {
                    for(int x = 0; x < Tax.length; x++)
                    {
                        Tax[x] = Double.parseDouble(allValues[i]) * taxRate;
                        Shipping[x] = Double.parseDouble(allValues[i]) * shippingRate;
                        Total[x] = Tax[x] + Shipping[x] + Double.parseDouble(allValues[i]);
                    }
                    
                    myWriter.println("Price:" + "    " + allValues[i]);
                }
                else if(i % 4 != 0)
                {
                    myWriter.println("PartNum:" + "    " + allValues[i]);
                }
                else
                {
                    myWriter.println("Quantity:" + "    " + allValues[i]);
                }   
                
            }
            
            myWriter.println("- - - - - - - - - -");
            
        }
    }
    
    public void Close(){
        try
        {
            myReader.close();
            myWriter.close();
        }
        catch (IOException e) {
            System.out.println(e.getClass().getName());
            System.out.println("Error in closing a file");
        }
    }
}
