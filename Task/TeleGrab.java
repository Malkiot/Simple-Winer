package Task;

import org.powerbot.script.Area;
import org.powerbot.script.Filter;
import org.powerbot.script.Tile;
import org.powerbot.script.rt6.*;

public class TeleGrab extends Task<ClientContext> {

    public int telekinetic_id = 518;
    public int target_item_id = 245;
    //public IdQuery<Action> telekinetic_slot = ctx.combatBar.select().id(telekinetic_id);;
    public int law_rune_id = 563;

    //Define Telekinetic Area
    private final Tile tile1 = new Tile(2950, 3476, 0);
    private final Tile tile2 = new Tile(2954, 3472, 0);
    private final Area grabbing_area = new Area(tile1, tile2);

    public TeleGrab(ClientContext ctx) {
        super(ctx);
    }

    //Activation Parameters
    @Override
    public boolean condition() {
        return  (grabbing_area.contains(ctx.players.local())
                && ctx.backpack.select().count() < 28
                && !(ctx.backpack.select().id(law_rune_id).isEmpty()));

    }

    //Try to cast
    @Override
    public void run() {

        System.out.println("Grabbing");

        ctx.camera.angle(149);
        ctx.camera.pitch(84);

        //If Wine is there (to avoid incorrect loop)
        if (!ctx.groundItems.select().id(target_item_id).isEmpty()) {

            //Keep Trying while Wine is there
            while(!ctx.groundItems.select().id(target_item_id).isEmpty()) {

                GroundItem target_item = ctx.groundItems.select().id(target_item_id).nearest().poll();

                if (target_item.inViewport()) {

                    //Placeholder until better method is found
                    if (ctx.players.local().animation() == -1) {

                        //ctx.combatBar.actionAt(telekinetic_slot);
                        //if(ctx.players.local().animation() == -1) {

                        ctx.combatBar.actionAt(0).select(true);
                        target_item.click("Cast", "Wine of Zamorak");
                        //}

                        try {
                            Thread.sleep(250);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if (ctx.players.local().animation() == -1) {

                            ctx.combatBar.actionAt(0).select(true);
                            target_item.interact("Cast", "Wine of Zamorak");

                        }
                    }

                } else {

                    ctx.movement.step(target_item);
                    ctx.camera.turnTo(target_item);
                    ctx.camera.angle(149);
                    ctx.camera.pitch(84);

                }
            }
        }

    }
}
