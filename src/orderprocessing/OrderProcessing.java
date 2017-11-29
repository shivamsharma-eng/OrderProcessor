package orderprocessing;

public class OrderProcessing {

    public static void main(String[] args) {
        
        OrderProcessor myProcessor = new OrderProcessor();
        myProcessor.Open();
        myProcessor.Read();
        myProcessor.Calculations_And_Output();
        myProcessor.Close();
    }
    
}
