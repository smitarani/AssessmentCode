package Assessment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.InputStreamReader;

class Test {
    // Read multiline input from console in Java by using BufferedReader class
    public static void main(String[] args) throws FileNotFoundException {

       
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    

        List<String> input = new ArrayList<String>();
        int emptyLineIndex = 0;
        String line;
        try {
            for (int i = 0; (line = br.readLine()) != null; i++) {
                if (line.equals("")) {
                    emptyLineIndex = i;
                }
                input.add(line);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // parse theater
        String[][] seats = new String[emptyLineIndex][];
        for (int i =0; i < emptyLineIndex ; i++) {
            seats[i] = input.get(i).split("\\s");
        }

        // parse orders
        String[][] orders = new String[input.size() - emptyLineIndex - 1][4];
        for (int j = 0, i = emptyLineIndex + 1; i < input.size(); i++, j++) {
            orders[j][0] = String.valueOf(i); // order sequence
            orders[j][1] = input.get(i).split("\\s")[1]; // order quantity
            orders[j][3] = input.get(i).split("\\s")[0]; // order name
            orders[j][2] = ""; // order status
        }
        
        // sort orders in descending order based on quantity
        Arrays.sort(orders, (a, b) -> Double.compare(Integer.valueOf(b[1]), Integer.valueOf(a[1])));

        int totalSeats = 0;

        // loop through all seats
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                int seatsInSection = Integer.parseInt(seats[i][j]);

                // loop through orders in descending order
                for (int k = 0; k < orders.length; k++) {

                    if (orders[k][2].equals("")) { // if the seats are not assigned yet
                        if (Integer.parseInt(orders[k][1]) <= seatsInSection) {
                            orders[k][2] = orders[k][3] + " Row " + (i + 1) + " Section " + (j + 1);
                            seatsInSection -= Integer.parseInt(orders[k][1]);
                        }
                    }
                }
                totalSeats += seatsInSection;

            }
        }

        // sort orders back to the sequence of arrival
        Arrays.sort(orders, (a, b) -> Double.compare(Integer.valueOf(a[0]), Integer.valueOf(b[0])));

        for (int k = 0; k < orders.length; k++) {

            if (orders[k][2].equals("")) {
                if (Integer.parseInt(orders[k][1]) <= totalSeats) {
                    orders[k][2] = orders[k][3] + " Call to split party";
                    totalSeats -= Integer.parseInt(orders[k][1]);
                } else {
                    orders[k][2] = orders[k][3] + " Sorry, we can't handle your party";
                }
            }

            System.out.println(orders[k][2]);
        }

    }
}
