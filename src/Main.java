import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static class DataCentre implements Comparable<DataCentre> {
        private final int number;
        private final int totalServers;
        private int countReset;
        private int countWorkServer;
        private int[] servers;

        public DataCentre(int number, int totalServers) {
            this.number = number;
            this.totalServers = totalServers;
            countReset = 0;
            this.countWorkServer = totalServers;
            servers = new int[totalServers];
            for (int i = 0; i < servers.length; i++) {
                servers[i] = 1;
            }
        }

        public void reset() {
            countReset++;
        }

        public void disable(int numberServer) {
            servers[numberServer] = 0;
            countWorkServer = Arrays.stream(servers).sum();
        }

        public int getRA() {
            return countReset * countWorkServer;
        }

        public int getNumber() {
            return number;
        }


        @Override
        public int compareTo(DataCentre o) {
            return this.number - o.getNumber();
        }
    }

    private static DataCentre[] init(int countDatacentres, int countServers) {
        DataCentre[] datacentres = new DataCentre[countDatacentres];

        for (int i = 0; i < countDatacentres; i++) {
            datacentres[i] = new DataCentre(i, countServers);
        }
        return datacentres;
    }

    private static void handle(int countDatacentres, int countServers, List<String> events) {
        TreeSet<DataCentre> minDatacentres = new TreeSet<>();
        TreeSet<DataCentre> maxDatacentres = new TreeSet<>();
        //Map<Integer, Integer[]> datacentres = new HashMap<>(countDatacentres);
        DataCentre[] dataCentres = init(countDatacentres, countServers);
        boolean currentMinMustChanged = true;
        boolean currentMaxMustChanged = true;
        int currentMax = -1;
        int currentMin = -1;

        for (String event : events) {
            if (event.startsWith("RESET")) {
                String[] s = event.split(" ");
                int i = Integer.parseInt(s[1]);
                dataCentres[i - 1].reset();
            } else if (event.startsWith("DISABLE")) {
                String[] s = event.split(" ");
                int i = Integer.parseInt(s[1]);
                int j = Integer.parseInt(s[2]);
                dataCentres[i - 1].disable(j - 1);
            } else if (event.startsWith("GETMAX")) {
                if (currentMaxMustChanged) {
                    currentMax = dataCentres[0].getRA();
                    currentMaxMustChanged = false;
                }
                int max = currentMax;

                for (DataCentre dataCentre : dataCentres) {
                    if (dataCentre.getRA() > max) {
                        max = dataCentre.getRA();
                        if (currentMax < max) {
                            currentMax = max;
                            if (!maxDatacentres.isEmpty()) {
                                maxDatacentres.clear();
                                maxDatacentres.add(dataCentre);
                            }
                        } else if (currentMax == max) {
                            maxDatacentres.add(dataCentre);
                        }
                    }
                    if (!maxDatacentres.isEmpty()) {
                        System.out.println(maxDatacentres.last().getNumber() + 1);
                    }

                }

            } else if (event.startsWith("GETMIN")) {
                if (currentMinMustChanged) {
                    currentMin = dataCentres[0].getRA();
                    currentMinMustChanged = false;
                }
                int min = currentMin;

                for (DataCentre dataCentre : dataCentres) {
                    minDatacentres.add(dataCentre);
                    if (dataCentre.getRA() < min) {
                        min = dataCentre.getRA();
                        if (currentMin > min) {
                            currentMin = min;

                            minDatacentres.clear();
                            minDatacentres.add(dataCentre);

                        } else if (currentMin == min) {
                            minDatacentres.add(dataCentre);
                        }
                    }
                    if (!minDatacentres.isEmpty()) {
                        System.out.println(minDatacentres.first().getNumber() + 1);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            String s = bufferedReader.readLine();

            String[] s2 = s.split(" ");
            int n = Integer.parseInt(s2[0]); // Число дата-центров
            int m = Integer.parseInt(s2[1]); // Число серверов
            int q = Integer.parseInt(s2[2]); // Число событий
            List<String> events = new ArrayList<>(q);


            for (int i = 0; i < q; i++) {
                String e = bufferedReader.readLine();
                events.add(e);
            }
            bufferedReader.close();
            handle(n, m, events);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}