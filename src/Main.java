import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static void handle(List<String> events) {
        for (String event : events) {
            System.out.println(event);
        }
    }

    public static void main(String[] args) {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            String s = bufferedReader.readLine();
          //  bufferedReader.close();
            String[] s2 = s.split(" ");
            int n = Integer.parseInt(s2[0]); // Число дата-центров
            int m = Integer.parseInt(s2[1]); // Число серверов
            int q = Integer.parseInt(s2[2]); // Число событий
            List<String> events = new ArrayList<>(q);
            System.out.println(n + " " + m + " " + q);

            for (int i = 0; i < q; i++) {
                String e = bufferedReader.readLine();

                events.add(e);
            }
            bufferedReader.close();
            handle(events);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}