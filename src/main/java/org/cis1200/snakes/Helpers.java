package org.cis1200.snakes;

import org.cis1200.snakes.gameObjects.*;
import org.cis1200.snakes.panels.Main;

import java.io.*;
import java.nio.file.Paths;

public class Helpers {
    public static int getRandomInteger(int maximum, int minimum) {
        return ((int) (Math.random() * (maximum - minimum))) + minimum;
    }

    public static void writeStringToFile(String s, String filePath, boolean append) {
        File file = Paths.get(filePath).toFile();
        BufferedWriter bw;

        try {
            FileWriter fw = new FileWriter(file, append);
            bw = new BufferedWriter(fw);

            bw.write(s);

            bw.close();

        } catch (IOException e) {
            /*
             * If the process of writing the data triggers an IOException, you should
             * catch it and stop writing.
             */
            System.out.println("Error writing to file");
        }

    }

    public static int getHScoreFromFile() {
        try {
            FileReader fr = new FileReader(RunSnakes.PATH_TO_HIGUEST_SCORE);
            BufferedReader br = new BufferedReader(fr);
            String score = br.readLine();
            return Integer.parseInt(score);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static GameStateConditions getCSVData(String path, int hs) {

        try {

            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);

            String csvLine = br.readLine();
            if (csvLine == "") {
                System.out.println("empty");
                throw new RuntimeException();
            }

            String[] sarr = csvLine.split(",");

            // head pos
            String[] hdposstring = sarr[0].split("-");
            int[] hdpos = { Integer.parseInt(hdposstring[0]), Integer.parseInt(hdposstring[1]) };

            // h direction
            int hdir = Integer.parseInt(sarr[1]);

            // v direction
            int vdir = Integer.parseInt(sarr[2]);

            // body type and pos
            String[] bodyposstring = sarr[3].split("e");
            // ["0-5-5", "1-4-5"]
            int[][] bodycoords = new int[bodyposstring.length][3];

            for (int i = 0; i < bodyposstring.length; i++) {

                String[] strpos = bodyposstring[i].split("-");

                bodycoords[i][0] = Integer.parseInt(strpos[0]);
                bodycoords[i][1] = Integer.parseInt(strpos[1]);
                bodycoords[i][2] = Integer.parseInt(strpos[2]);
            }

            // fruit pos
            String[] frposstring = sarr[4].split("-");
            int[] frpos = { Integer.parseInt(frposstring[0]), Integer.parseInt(frposstring[1]) };

            // snake type
            int type = Integer.parseInt(sarr[5]);

            // portal pos
            int[] portalpos = { -1, -1 };
            String[] portalposstring = sarr[6].split("-");
            if (!portalposstring[0].equals("no")) {
                portalpos[0] = Integer.parseInt(portalposstring[0]);
                portalpos[1] = Integer.parseInt(portalposstring[1]);
            }

            return new GameStateConditions(
                    hs, hdpos,
                    hdir, vdir, bodycoords, frpos, type,
                    portalpos
            );

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static String stateToString(Snake snake, Fruit fruit) {
        String bodycoords = "";
        for (RunSnakes.SquareUnit s : snake.getBody()) {
            // 1-5-5 for Hole
            if (s instanceof Hole) {
                bodycoords += "1-" + s.getRow() + "-" + s.getCol() + "e";
            } else {
                // 0-5-5 for SquareUnit
                bodycoords += "0-" + s.getRow() + "-" + s.getCol() + "e";
            }
        }
        bodycoords = bodycoords.substring(0, bodycoords.length() - 1);

        String portalpos = "no";
        if (snake.getType() == Main.PORTAL_SNAKE) {
            assert snake instanceof PortalSnake;

            PortalSnake nsnake = (PortalSnake) snake;

            if (nsnake.getPortal() != null) {
                portalpos = nsnake.getPortal().getRow() + "-" +
                        nsnake.getPortal().getCol();
            }
        }

        String data = snake.getHead().getRow() + "-" + snake.getHead().getCol() + "," +
                snake.getHdir() + "," +
                snake.getVdir() + "," +
                bodycoords + "," +
                fruit.getRow() + "-" + fruit.getCol() + "," +
                snake.getType() + "," +
                portalpos;

        System.out.println(data);

        return data;
    }

}
