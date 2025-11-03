package Ranking;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class HighScoreManager {
    private static final int numScores = 10;
    private static List<Integer> highScoresEasy = new ArrayList<>(numScores);
    private static List<Integer> highScoresMedium = new ArrayList<>(numScores);
    private static List<Integer> highScoresHard = new ArrayList<>(numScores);
//    public HighScoreManager() {
//        for (int i = 0; i < numScores; i++) highScoresEasy.add(0);
//    }


    public List<Integer> getHighScores() {
        return highScoresEasy;
    }

    public void readFromFile() {
        try {
            File in = new File("HighScores.txt");
            Scanner scanner = new Scanner(in);
            for (int i = 0; i < numScores; i++) {
                int score = scanner.nextInt();
                highScoresEasy.add(score);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addScore(int score) {
        int pos = -1;

        if (score > highScoresEasy.get(0)) pos = 0;
        for (int i = 0; i < highScoresEasy.size() - 1; i++) {
            if (highScoresEasy.get(i) >= score && score > highScoresEasy.get(i + 1)) pos = i;
        }

        if (pos == -1) return;

        for (int i = highScoresEasy.size() - 1; i > pos; i--) {
            highScoresEasy.set(i, highScoresEasy.get(i - 1));
        }

        highScoresEasy.set(pos, score);
    }

    public void writeToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("HighScores.txt"))) {
            for (int i = 0; i < numScores; i++) {
                bw.write(highScoresEasy.get(i) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
