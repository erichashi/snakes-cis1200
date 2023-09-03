package org.cis1200.snakes.gameObjects;

public class GameStateConditions {
    // data = [hs, snake head pos, snake dir, body as string of array, fruit pos]

    private int hs;
    private int[] hpos;
    private int hdir;
    private int vdir;
    private int[][] bodypos;
    private int[] fpos;
    private int type;
    private int[] portalpos;

    public GameStateConditions(
            int hs, int[] hpos,
            int hdir, int vdir,
            int[][] bodypos,
            int[] fpos, int type,
            int[] portalpos
    ) {
        this.hs = hs;
        this.hpos = hpos;
        this.vdir = vdir;
        this.hdir = hdir;

        // [0, 3, 3] first is type, second and third are pos
        this.bodypos = bodypos;
        this.fpos = fpos;
        this.type = type;
        this.portalpos = portalpos;

    }

    public int getHs() {
        return hs;
    }

    public int getHdir() {
        return hdir;
    }

    public int getVdir() {
        return vdir;
    }

    public int[] getFpos() {
        return fpos;
    }

    public int[] getHpos() {
        return hpos;
    }

    public int[][] getBodypos() {
        return bodypos;
    }

    public int getType() {
        return type;
    }

    public int[] getPortalpos() {
        return portalpos;
    }
}
