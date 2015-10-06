package Task;

import Resources.Variables;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Interactive;

public class Banking extends Task<ClientContext>  {

    public Banking(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean condition() {

        return (!(ctx.backpack.select().count() < 28)
                || (ctx.backpack.select().id(Variables.law_rune_id).isEmpty())
                && Variables.banking_area.contains(ctx.players.local()));

    }

    //Try to Bank
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
            ctx.bank.withdraw(Variables.law_rune_id, Variables.law_rune_number);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ctx.bank.close();
        }



    }
}
