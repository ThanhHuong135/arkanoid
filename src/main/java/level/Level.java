package level;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import object.*;

public class Level {
    private static final int height = 5;
    private static final int width = 8;

    public static BrickType[][] load(String filename) {
        List<String> lines = new ArrayList<>();
        try (InputStream is = Level.class.getResourceAsStream("/" + filename)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line.trim());
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading level file: " + filename);
            e.printStackTrace();
            return null;
        }

        if (lines.isEmpty()) {
            return new BrickType[0][0];
        }

        BrickType[][] brickTypes = new BrickType[height][width];

        for (int row = 0; row < height; row++) {
            String[] cells = lines.get(row).split(",");
            for (int col = 0; col < width; col++) {
                char code = cells[col].charAt(0);
                //System.out.println(code);
                BrickType type = BrickType.fromCode(code);
                brickTypes[row][col] = type;
            }
        }
        return brickTypes;
    }
}
