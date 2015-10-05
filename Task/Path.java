package Task;

import org.powerbot.script.Area;
import org.powerbot.script.Tile;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.TilePath;

public class Path extends Task<ClientContext>  {

    //Define Telekinetic Area
    private final Tile tile1 = new Tile(2951, 3475, 0);
    private final Tile tile2 = new Tile(2953, 3473, 0);
    private final Area grabbing_area = new Area(tile1, tile2);

    //Define Banking Area
    private final Tile tile3 = new Tile(3010, 3353, 0);
    private final Tile tile4 = new Tile(3014, 3357, 0);
    private final Area banking_area = new Area(tile3, tile4);

    public int law_rune_id = 563;

    boolean tp_bank_yes;

    public Path(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean condition() {
        return (
                (((ctx.backpack.select().count() == 28) || ctx.backpack.select().id(law_rune_id).isEmpty()) && grabbing_area.contains(ctx.players.local()))
                || ((ctx.backpack.select().count() < 28) && !(ctx.backpack.select().id(law_rune_id).isEmpty()) && banking_area.contains(ctx.players.local()))
                || ((ctx.backpack.select().count() == 28) || ctx.backpack.select().id(law_rune_id).isEmpty() && !grabbing_area.contains(ctx.players.local()) && !banking_area.contains(ctx.players.local()))
                || ((ctx.backpack.select().count() < 28) && !(ctx.backpack.select().id(law_rune_id).isEmpty()) && !banking_area.contains(ctx.players.local()) && !grabbing_area.contains(ctx.players.local()))
        );



    }

    //Try to cast
    @Override
    public void run() {
        System.out.println("");

        final Tile[] path = {

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

        final TilePath pathBToZ, pathZToB;

        pathBToZ = ctx.movement.newTilePath(path).reverse();
        pathZToB = ctx.movement.newTilePath(path);

        if((ctx.backpack.select().count() == 28) || ctx.backpack.select().id(law_rune_id).isEmpty() && grabbing_area.contains(ctx.players.local())
                || (!(ctx.backpack.select().count() < 28) || ctx.backpack.select().id(law_rune_id).isEmpty() && !grabbing_area.contains(ctx.players.local()))) {

            System.out.println("Trying to get to Bank");

            if(Lodestone.FALADOR.canUse(ctx) && tp_bank_yes){

                Lodestone.FALADOR.teleport(ctx);
                tp_bank_yes = false;

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            if ((ctx.players.local().animation() == -1)) {

                pathZToB.randomize(2, 2).traverse();

            }
        }

        if((ctx.backpack.select().count() < 28 && !(ctx.backpack.select().id(law_rune_id).isEmpty()) && banking_area.contains(ctx.players.local()))
                || ((ctx.backpack.select().count() < 28) && !(ctx.backpack.select().id(law_rune_id).isEmpty()) && !banking_area.contains(ctx.players.local()))){

            pathBToZ.randomize(2,2).traverse();
            System.out.println("Trying to get to Wine");
            final Tile[] path2 = {new Tile(3012, 3355, 0)};
            ctx.movement.newTilePath(path2).traverse();
            tp_bank_yes = true;

        }

    }
}
