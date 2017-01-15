package main;

import messaging.HSBCJabberClientInstance;
import ml.HSBCSparkInstance;
import ml.Intent;


public final class Main {

    public static void main(String[] args) throws Exception {

        HSBCSparkInstance sparkInstance = new HSBCSparkInstance();
        sparkInstance.addIntent(new Intent("files/carte.txt"));
        sparkInstance.addIntent(new Intent("files/conges.txt"));
        sparkInstance.addIntent(new Intent("files/stagiaire.txt"));

        sparkInstance.learnAndCreateModel();
        HSBCJabberClientInstance jabberInstance = new HSBCJabberClientInstance(sparkInstance,"bot", "1234");
    }
}