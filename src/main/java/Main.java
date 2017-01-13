import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public final class Main {

    public static void main(String[] args) throws InterruptedException {

        HSBCSparkInstance sparkInstance =  new HSBCSparkInstance();

        sparkInstance.addIntent(new Intent("files/carte.txt"));
        sparkInstance.addIntent(new Intent("files/conges.txt"));
        sparkInstance.addIntent(new Intent("files/stagiaire.txt"));

        sparkInstance.learnAndCreateModel();

        BufferedReader br;

        try {

            br = new BufferedReader(new InputStreamReader(System.in));

            while (true) {

                System.out.print("Quelle est votre question : ");
                String question = br.readLine();

                if ("q".equals(question)) {
                    System.out.println("Exit!");
                    System.exit(0);
                }

                System.out.println(sparkInstance.findResponse(question));
                System.out.println("-----------\n");
            }
        }catch (IOException e) {
                e.printStackTrace();

        }
    }
}