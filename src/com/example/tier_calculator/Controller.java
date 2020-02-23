package com.example.tier_calculator;

import com.example.tier_calculator.json.JSONObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URL;

public class Controller {
    // FXML Consistents
    @FXML private Label title;
    @FXML private TextArea loadText;
    @FXML private Button loadTextFile;
    @FXML private TextArea systemMessage;
    @FXML private Label tierName;
    @FXML private Label totalExp;
    @FXML private Label currExp;
    @FXML private WebView tierImage;
    @FXML private ProgressBar expBar;
    private File file;

    // Loading Text File
    @FXML
    public void loadTextFile(ActionEvent event) {
        loadText.setText("");

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        file = fileChooser.showOpenDialog(null);

        if (file != null){  // File Loaded
            loadText.setText(file.getAbsolutePath());
        }
        else {
            systemMessage.setText("Error: File Not Found. Did you get the right 'TXT' format file?");
        }
    }

    // Calculating
    @FXML
    public void launchTextAction(ActionEvent event) {
        systemMessage.setText("");
        DiffwithTier.init();
        BufferedReader br = null;

        try {  // Try File Reading
            br = new BufferedReader(new FileReader(file));
        } catch (Exception var11) {
            var11.printStackTrace();
            systemMessage.setText("Error: File Not Found. Did you get the right 'TXT' format file?");
        }

        try {  // Try Calculating
            int T = Integer.parseInt(br.readLine());
            long exp = 0L;
            for (int i = 1; i <= T; ++i) {
                try {
                    Problem prob = getProblemDiff(br.readLine());
                    exp += (long) DiffwithTier.diffs[prob.getLevel()].getExpProblem();
                } catch (Exception var10) {
                    var10.printStackTrace();
                    systemMessage.setText("Error: Getting Tier Info Failed.");
                }
                //System.out.printf("%,d / %,d Completed.\n", i, T);
            }
            System.out.println();
            printTierInfo(exp);

        } catch (Exception var12) {
            var12.printStackTrace();
            systemMessage.setText("Error: Getting Tier Info Failed.");
        }
        try {
            br.close();
        } catch (Exception var9) {
            var9.printStackTrace();
            systemMessage.setText("Error: File Reading Failed.");
        }
    }

    // Printing Tier
    private void printTierInfo(long exp) {
        UserTier uTier = new UserTier(exp);
        String color = "#000000";
        systemMessage.setText(String.format("Tier : %s, [EXP : %,d / %,d (%.2f%%)]",
                uTier.getTier().getTierName(), uTier.getUserTier().getCurEXP(), uTier.getUserTier().getMEXP(),
                uTier.getUserTier().getPer()));

        tierName.setText(uTier.getTier().getTierName());
        totalExp.setText(String.format("%,d", exp));
        currExp.setText(String.format("%.2f%% (%,d / %,d)", uTier.getUserTier().getPer(),
                uTier.getUserTier().getCurEXP(), uTier.getUserTier().getMEXP()));

        //setTextFill(Color.web("#0076a3"));
        switch ((uTier.getTier().getTierLevel() - 1) / 5) {
            case 0: color = "#ad5600"; break;
            case 1: color = "#435f7a"; break;
            case 2: color = "#ec9a00"; break;
            case 3: color = "#27e2a4"; break;
            case 4: color = "#00b4fc"; break;
            case 5: color = "#ff0062"; break;
            default: break;
        }
        tierName.setTextFill(Color.web(color));
        currExp.setTextFill(Color.web(color));
        expBar.setProgress(uTier.getUserTier().getPer() / 100);

        System.out.printf("Tier : %s\n", uTier.getTier().getTierName());
        System.out.printf("EXP : %,d / %,d (%.2f%%)\n", uTier.getUserTier().getCurEXP(),
                uTier.getUserTier().getMEXP(), uTier.getUserTier().getPer());
        System.out.printf("Total EXP : %,d\n", exp);

        tierImage.getEngine().load("https://solved.ac/res/tier-small/" + uTier.getTier().getTierLevel() + ".svg");
}

    // Requesting API
    public Problem getProblemDiff(String pID) {
        Problem prob = null;

        try {
            URL url = new URL(String.format("https://api.solved.ac/problem_level.php?id=%s", pID));
            HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("User-Agent", "Mozilla/4.0");
            conn.connect();
            StringBuilder response = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line;
            while((line = br.readLine()) != null) {
                response.append(line);
                response.append('\n');
            }

            br.close();
            if (conn != null) {
                conn.disconnect();
            }

            JSONObject json = new JSONObject(response.toString());
            prob = new Problem(pID, json.getInt("level"), json.getInt("kudeki_level"));

        } catch (Exception var8) {
            var8.printStackTrace();
            systemMessage.setText("Error: Getting Problem Info Failed.");
        }
        return prob;
    }

}
