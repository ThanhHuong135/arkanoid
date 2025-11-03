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
    private static List<Integer> highScores = new ArrayList<>(numScores);

//    public HighScoreManager() {
//        for (int i = 0; i < numScores; i++) highScores.add(0);
//    }

    public List<Integer> getHighScores() {
        return highScores;
    }

    public void readFromFile() {
        try {
            File in = new File("HighScores.txt");
            Scanner scanner = new Scanner(in);
            for (int i = 0; i < numScores; i++) {
                int score = scanner.nextInt();
                highScores.add(score);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addScore(int score) {
        int pos = -1;

        if (score > highScores.get(0)) pos = 0;
        for (int i = 0; i < highScores.size() - 1; i++) {
            if (highScores.get(i) >= score && score > highScores.get(i + 1)) pos = i;
        }

        if (pos == -1) return;

        for (int i = highScores.size() - 1; i > pos; i--) {
            highScores.set(i, highScores.get(i - 1));
        }

        highScores.set(pos, score);
    }

    public void writeToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("HighScores.txt"))) {
            for (int i = 0; i < numScores; i++) {
                bw.write(highScores.get(i) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
