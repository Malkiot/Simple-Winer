package Task;


import org.powerbot.script.rt6.*;

public class TeleGrab extends Task<ClientContext> {

    public TeleGrab(ClientContext ctx) {
        super(ctx);
    }

    //Activation Parameters
    @Override
    public boolean condition() {
        return  (Resources.Variables.grabbing_area.contains(ctx.players.local())
                && ctx.backpack.select().count() < 28
                && !(ctx.backpack.select().id(Resources.Variables.law_rune_id).isEmpty()));

    }

    //Try to cast
    @Override
    public void run() {

        System.out.println("Grabbing");

        ctx.camera.angle(149);
        ctx.camera.pitch(84);

        //If Wine is there (to avoid incorrect loop)
        if (!ctx.groundItems.select().id(Resources.Variables.z_wine_id).isEmpty()) {

            //Keep Trying while Wine is there
            while(!ctx.groundItems.select().id(Resources.Variables.z_wine_id).isEmpty()) {

                GroundItem target_item = ctx.groundItems.select().id(Resources.Variables.z_wine_id).nearest().poll();

                if (target_item.inViewport()) {

                    //Placeholder until better method is found
                    if (ctx.players.local().animation() == -1) {

                        //ctx.combatBar.actionAt(telekinetic_slot);
                        //if(ctx.players.local().animation() == -1) {

                        ctx.combatBar.actionAt(0).select(true);
                        target_item.click("Cast", "Wine of Zamorak");
                        //}

                        if (ctx.players.local().animation() == -1) {

                            ctx.combatBar.actionAt(0).select(true);
                            target_item.interact("Cast", "Wine of Zamorak");

                        }

                        try {
                            Thread.sleep(250);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
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