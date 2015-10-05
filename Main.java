/**
 * Created by Malk on 01.10.2015.
 */

import org.powerbot.script.*;
import org.powerbot.script.rt6.*;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.IdQuery;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Script.Manifest(name = "Simple Wine", description = "Grabs Z Wine", properties = "client=6; topic=0;")

public class Main extends PollingScript<ClientContext> implements PaintListener {

    private List<Task.Task> taskList = new ArrayList<Task.Task>();

    //Define Telekinetic Area
    private final Tile tile1 = new Tile(2951, 3473, 0);
    private final Tile tile2 = new Tile(2952, 3474, 0);
    private final Area grabbing_area = new Area(tile1, tile2);

    //Define Banking Area
    private final Tile tile3 = new Tile(3010, 3353, 0);
    private final Tile tile4 = new Tile(3014, 3357, 0);
    private final Area banking_area = new Area(tile3, tile4);

    //State Status Flags
    private boolean is_full;
    private boolean has_law_runes;
    private boolean within_banking_area;
    private boolean within_grabbing_area;

    //Create Variables
    public int telekinetic_id = 518; //Telekinetic Grab ID
    public int target_item_id = 245; //Zammy Wine ID
    public int law_rune_id = 563; //place holder Law Rune ID
    public int no_law_runes = 54; //No of runes to withdraw
    IdQuery<Action> telekinetic_slot; //Ability Slot Number

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
    int profit;
    int profithourly;
    int winecounthourly;
    String Time;

    //Detect Location, LvL, Inventory. Launch GUI
    @Override
    public void start() {
        startTime = System.currentTimeMillis();

        System.out.println("Script Started!");

        //Check if Telekinetic Grab in ActionBar and find slot
        /*if (ctx.combatBar.select().id(telekinetic_id).peek().type() == Action.Type.ABILITY) {
            telekinetic_slot = ctx.combatBar.select().id(telekinetic_id);
        } else {
            System.out.println("Telekinetic Grab not found. Exiting.");
            stop();
        }*/

        //Create Task List
        taskList.addAll(Arrays.asList(
                new Task.TeleGrab(ctx),
                new Task.Path(ctx),
                new Task.Banking(ctx))
        );

        wineprice = GeItem.price(target_item_id);

    }

    @Override
    public void poll() {

        wine1 = ctx.backpack.id(target_item_id).select().count();

        for (Task.Task task : taskList) {
            if (task.condition()) {
                task.run();
            }
        }

        wine2 = ctx.backpack.id(target_item_id).select().count();

        if(wine2 > wine1){
            winecount = winecount + (wine2 - wine1);
        }

    }

    public void repaint(Graphics g) {

        //Timer
        endTime   = System.currentTimeMillis();
        totalTime = ((endTime - startTime + 1)/1000);
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
