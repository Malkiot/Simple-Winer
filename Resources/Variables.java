package Resources;

import org.powerbot.script.Area;
import org.powerbot.script.Tile;
import org.powerbot.script.rt6.TilePath;

public class Variables {

    //IDs
    public static int z_wine_id = 245;
    public static int law_rune_id = 563;
    public static int law_rune_number = 54; //number of runes to withdraw

    //Define Telekinetic Area
    private static Tile tile1 = new Tile(2954, 3472, 0);
    private static Tile tile2 = new Tile(2950, 3477, 0);
    public static Area grabbing_area = new Area(tile1, tile2);

    //Define Banking Area
    private static Tile tile3 = new Tile(3010, 3353, 0);
    private static Tile tile4 = new Tile(3014, 3357, 0);
    public static Area banking_area = new Area(tile3, tile4);

    public static Tile[] path = {

            new Tile(2951, 3473, 0),
            new Tile(2952, 3474, 0), // grab locale
            new Tile(2953, 3468, 0),
            new Tile(2948, 3461, 0),
            new Tile(2946, 3453, 0),
            new Tile(2945, 3446, 0),
            new Tile(2947, 3437, 0),
            new Tile(2948, 3431, 0),
            new Tile(2952, 3422, 0),
            new Tile(2962, 3414, 0),
            new Tile(2964, 3402, 0),
            new Tile(2964, 3389, 0),
            new Tile(2967, 3381, 0),
            new Tile(2982, 3376, 0),
            new Tile(2990, 3372, 0),
            new Tile(2997, 3366, 0),
            new Tile(3005, 3364, 0),
            new Tile(3012, 3363, 0),
            new Tile(3012, 3355, 0), // bank
    };

    public static Tile[] path2 = {new Tile(3012, 3355, 0)};

    public static int[] winebounds = {-64, 64, -484, -308, -64, 64} ;

    public static int telekinesis_id = 518;

}
