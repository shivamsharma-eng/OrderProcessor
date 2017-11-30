package orderprocessing;

public class OrderProcessing {
    /**
     * Main method that calls on the OrderProcessor class to run all of it's methods
     * @param args
     */
    public static void main(String[] args) {
        
        //makes a new processor object
        OrderProcessor myProcessor = new OrderProcessor("orders/Orders.txt", "orders/OrdersProcessed.txt");
        //opens file
        myProcessor.Open();
        //Reads the first line so it'll be skipped later on
        myProcessor.Read();
        //Goes through all the lines after the first one and gets them ready to be outputed
        myProcessor.Calculations_And_Output();
        //closes file
        myProcessor.Close();
    }
    
}
