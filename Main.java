/****************************************************************
 * Thanks to Chris, Artificial, and Mookyman for their advice.  *
 *                                                              *
 * Additional thanks to Artificial for his Lodestone API        *
 *                                                              *
 *TODO: Antipattern and include LawRunes in Profit Calc         *
 ***************************************************************/

import Resources.Variables;
import org.powerbot.script.*;
import org.powerbot.script.rt6.*;
import org.powerbot.script.rt6.ClientContext;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Script.Manifest(name = "Simple Wine", description = "Grabs Z Wine", properties = "client=6; topic=0;")

public class Main extends PollingScript<ClientContext> implements PaintListener {

    private List<Task.Task> taskList = new ArrayList<Task.Task>();

    //Detect Location, LvL, Inventory. Launch GUI
    @Override
    public void start() {

        startTime = System.currentTimeMillis();

        System.out.println("Script Started!");

        //Create Task List
        taskList.addAll(Arrays.asList(
                new Task.TeleGrab(ctx),
                new Task.Path(ctx),
                new Task.Banking(ctx))
        );

        wineprice = GeItem.price(Resources.Variables.z_wine_id);
        lawruneprice = GeItem.price(Variables.law_rune_id);

    }

    @Override
    public void poll() {

        wine1 = ctx.backpack.id(Resources.Variables.z_wine_id).select().count();

        for (Task.Task task : taskList) {
            if (task.condition()) {
                task.run();
            }
        }

        wine2 = ctx.backpack.id(Resources.Variables.z_wine_id).select().count();

        if(wine2 > wine1){
            winecount = winecount + (wine2 - wine1);
        }

    }


    //Paint Variables
    int hours;
    int minutes;
    int seconds;

    long startTime;
    long endTime;
    long totalTime;

    int wine1;
    int wine2;
    int winecount;
    int wineprice;
    int lawruneprice;
    int profit;
    int profithourly;
    int winecounthourly;

    public void repaint(Graphics g) {

        //Timer
        endTime   = System.currentTimeMillis();
        totalTime = ((endTime - startTime)/1000);
        hours = (int) Math.floor(totalTime / 3600);
        minutes = (int) Math.floor((totalTime - hours*3600)/60);
        seconds = (int) Math.floor((totalTime - minutes*60));

        profit = winecount*wineprice;
        profithourly = (int) Math.floor(profit/totalTime*3600);
        winecounthourly = (int) Math.round(winecount*3600/totalTime);

        g.setColor(new Color(0, 0, 0, 146));
        g.fillRect(5, 5, 115, 60);
        g.setColor(Color.GREEN);
        g.drawString("Runtime: " +String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":"
                + String.format("%02d", seconds), 8, 20);
        g.drawString("Grabbed: " + winecount + " (" + winecounthourly + ")", 8, 60);
        g.drawString("Profit: " + profit + " (" + profithourly + ")", 8, 40);
        g.drawRect(5, 5, 115, 60);

    }

    @Override
    public void stop()
    {
        System.out.println("Script Stopped!");
    }

}
