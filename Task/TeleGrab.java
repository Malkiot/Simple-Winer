package Task;


import Resources.Variables;
import org.powerbot.script.rt6.*;

import java.awt.*;

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
        int i = 0;

        ctx.combatBar.actionAt(0).select(true);

        boolean grab = false;

        System.out.println("Grabbing");
        if(!ctx.client().isSpellSelected()) {
            ctx.combatBar.actionAt(0).select(true);
        }
        //ctx.camera.angle(149);
        //ctx.camera.pitch(84);

        //If Wine is there (to avoid incorrect loop)
        if (!ctx.groundItems.select().id(Resources.Variables.z_wine_id).isEmpty()) {

            //Keep Trying while Wine is there
            while(!ctx.groundItems.select().id(Resources.Variables.z_wine_id).isEmpty()) {

                //GroundItem target_item = ctx.groundItems.select().id(Resources.Variables.z_wine_id).nearest().poll();
                GroundItem target_item = ctx.groundItems.select().id(Variables.z_wine_id).each(Interactive.doSetBounds(Variables.winebounds)).nearest().poll();

                //if (target_item.inViewport()) {

                    //Placeholder until better method is found
                    //if (ctx.players.local().animation() == -1) {

                        //ctx.combatBar.actionAt(telekinetic_slot);
                        //if(ctx.players.local().animation() == -1) {

                        //ctx.combatBar.actionAt(0).select(true);
                        //target_item.click("Cast", "Wine of Zamorak");
                        //}

                        if (ctx.players.local().animation() == -1) {

                            //Point z_wine_centerpoint = target_item.centerPoint();
                            //ctx.input.move(z_wine_centerpoint);
                            //ctx.combatBar.actionAt(0).select(true);
                            //ctx.input.click(z_wine_centerpoint, true);

                            if(!ctx.client().isSpellSelected()) {
                                ctx.combatBar.actionAt(0).select(true);
                            }
                            target_item.interact(true, "Cast", "Wine of Zamorak");

                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if(!ctx.client().isSpellSelected()) {
                                ctx.combatBar.actionAt(0).select(true);
                            }
                            target_item.interact(true, "Cast", "Wine of Zamorak");
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            grab = true;

                        }

                    //}

                //} else {

                    //ctx.movement.step(target_item);
                    //ctx.camera.turnTo(target_item);
                    //ctx.camera.angle(149);
                    //ctx.camera.pitch(84);

                //}
            }

            if(grab) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            grab = false;
        }

    }


}