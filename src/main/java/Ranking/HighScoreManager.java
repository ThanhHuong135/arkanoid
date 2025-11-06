package Ranking;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class HighScoreManager {
    public static final int numScores = 10;
    private static List<Integer> highScoresEasy = new ArrayList<>(numScores);
    private static List<Integer> highScoresMedium = new ArrayList<>(numScores);
    private static List<Integer> highScoresHard = new ArrayList<>(numScores);
    private static int chosenDifficulty;

    public int getChosenDifficulty() {
        return chosenDifficulty;
    }

    public void setChosenDifficulty(int difficulty) {
        chosenDifficulty = difficulty;
    }

    public List<Integer> getHighScoresEasy() {
        return highScoresEasy;
    }

    public List<Integer> getHighScoresMedium() {
        return highScoresMedium;
    }

    public List<Integer> getHighScoresHard() {
        return highScoresHard;
    }

    public void readFromFile() {
        try {
            File in = new File("HighScores.txt");
            Scanner scanner = new Scanner(in);
            for (int i = 0; i < numScores; i++) {
                int score = scanner.nextInt();
                highScoresEasy.add(score);
            }

            for (int i = 0; i < numScores; i++) {
                int score = scanner.nextInt();
                highScoresMedium.add(score);
            }

            for (int i = 0; i < numScores; i++) {
                int score = scanner.nextInt();
                highScoresHard.add(score);
            }

            highScoresEasy.sort(Comparator.reverseOrder());
            highScoresMedium.sort(Comparator.reverseOrder());
            highScoresHard.sort(Comparator.reverseOrder());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addScore(int score, int type) {
        if (type == 1) {
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
        } else if (type == 2) {
            int pos = -1;

            if (score > highScoresMedium.get(0)) pos = 0;
            for (int i = 0; i < highScoresMedium.size() - 1; i++) {
                if (highScoresMedium.get(i) >= score && score > highScoresMedium.get(i + 1)) pos = i;
            }

            if (pos == -1) return;

            for (int i = highScoresMedium.size() - 1; i > pos; i--) {
                highScoresMedium.set(i, highScoresMedium.get(i - 1));
            }

            highScoresMedium.set(pos, score);
        } else {
            int pos = -1;

            if (score > highScoresHard.get(0)) pos = 0;
            for (int i = 0; i < highScoresHard.size() - 1; i++) {
                if (highScoresHard.get(i) >= score && score > highScoresHard.get(i + 1)) pos = i;
            }

            if (pos == -1) return;

            for (int i = highScoresHard.size() - 1; i > pos; i--) {
                highScoresHard.set(i, highScoresHard.get(i - 1));
            }

            highScoresHard.set(pos, score);
        }
    }

    public void writeToFile() {
        highScoresEasy.sort(Comparator.reverseOrder());
        highScoresMedium.sort(Comparator.reverseOrder());
        highScoresHard.sort(Comparator.reverseOrder());
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("HighScores.txt"))) {
            for (int i = 0; i < numScores; i++) {
                bw.write(highScoresEasy.get(i) + "\n");
            }
            for (int i = 0; i < numScores; i++) {
                bw.write(highScoresMedium.get(i) + "\n");
            }
            for (int i = 0; i < numScores; i++) {
                bw.write(highScoresHard.get(i) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
