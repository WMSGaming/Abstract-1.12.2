package com.wms.abstractclient.manager;

import java.util.ArrayList;
import java.util.List;

public class BetaManager {
    public static List<String> betaNames;
    public BetaManager(){
        betaNames = new ArrayList<>();
        //Adding beta users to the cape manager

        betaNames.add("_WMS");
        betaNames.add("Logging4J");
        betaNames.add("ClampWizard");
        betaNames.add("ARm8");
        betaNames.add("zyxswavy");
        betaNames.add("_NLE_CHOPPA");
        betaNames.add("Mr_Hilarious");
        betaNames.add("duck_with_a_hat");
        betaNames.add("4Ufo ");
        betaNames.add("Brotherismad");
        betaNames.add("endcrystalman");
        betaNames.add("PistonCrystal");

    }
    public static boolean isUserBeta(String ign){
        return betaNames.contains(ign);
    }
    // Checking if a username is a beta user, this is used in for capes and probs more stuff in the future
}
