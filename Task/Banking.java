package Task;

import org.powerbot.script.Area;
import org.powerbot.script.Tile;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Interactive;

public class Banking extends Task<ClientContext>  {

    //Define Banking Area
    private final Tile tile3 = new Tile(3010, 3353, 0);
    private final Tile tile4 = new Tile(3014, 3357, 0);
    private final Area banking_area = new Area(tile3, tile4);

    public int law_rune_id = 563;
    public int no_law_runes = 54;

    public Banking(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean condition() {
        final Tile tile1 = new Tile(3010, 3353, 0);
        final Tile tile2 = new Tile(3014, 3357, 0);
        final Area area = new Area(tile1, tile2);

        return (!(ctx.backpack.select().count() < 28)
                || (ctx.backpack.select().id(law_rune_id).isEmpty())
                && banking_area.contains(ctx.players.local()));

    }

    //Try to cast
    @Override
    public void run() {

        System.out.println("Banking");
        if(!ctx.bank.opened()) {
            //ctx.bank.open();
            System.out.println("Opening Bank");
            Interactive banker = ctx.npcs.select().id(6200).nearest().poll();
            banker.interact("Bank");
        } else {
            System.out.println("Banking");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ctx.bank.depositInventory();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ctx.bank.withdraw(law_rune_id, no_law_runes);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ctx.bank.close();
        }



    }
}
