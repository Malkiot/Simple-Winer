package Task;

import Resources.Lodestone;
import Resources.Variables;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.TilePath;

public class Path extends Task<ClientContext>  {

    public Path(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean condition() {
        return (

                //BackPackFull OR NoLawRunesFound AND PlayerNotAtBank
                (((ctx.backpack.select().count() == 28) || ctx.backpack.select().id(Variables.law_rune_id).isEmpty()) && !Variables.banking_area.contains(ctx.players.local()))
                //BackNotFull AND LawRunesFound AND PlayerNotAtTemple
                || ((ctx.backpack.select().count() < 28) && !(ctx.backpack.select().id(Variables.law_rune_id).isEmpty()) && !Variables.grabbing_area.contains(ctx.players.local())));
    }

    @Override
    public void run() {

        final TilePath pathBToZ, pathZToB;

        pathBToZ = ctx.movement.newTilePath(Variables.path).reverse();
        pathZToB = ctx.movement.newTilePath(Variables.path);

        //Walk to Bank if ((BackPackFull OR NoLawRunesFound) && PlayerNotAtBank)
        if((ctx.backpack.select().count() == 28) || ctx.backpack.select().id(Variables.law_rune_id).isEmpty() && !Variables.banking_area.contains(ctx.players.local())) {

            //Use Lodestone only if at Temple
            if(Lodestone.FALADOR.canUse(ctx) && Variables.grabbing_area.contains(ctx.players.local())){

                Lodestone.FALADOR.teleport(ctx);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            //Do not walk if attempting teleport
            if ((ctx.players.local().animation() == -1) && !Variables.grabbing_area.contains(ctx.players.local())) {

                pathZToB.randomize(2, 2).traverse();

            }
        }

        //Walk to Temple if BackPackNotFull && LawRunesFound && PlayerNotAtTemple)
        if((ctx.backpack.select().count() < 28 && !(ctx.backpack.select().id(Variables.law_rune_id).isEmpty()) && !Variables.grabbing_area.contains(ctx.players.local()))){

            pathBToZ.randomize(2,2).traverse();
            System.out.println("Trying to get to Wine");
            if (Variables.grabbing_area.contains(ctx.players.local())) {
                ctx.movement.newTilePath(Variables.path2).traverse();
            }

        }

    }
}
