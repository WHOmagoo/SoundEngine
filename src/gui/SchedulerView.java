package gui;

import composition.Scheduler;

import javax.swing.*;
import java.awt.*;

public class SchedulerView extends JLabel implements Updateable {
    Scheduler scheduler;

    public SchedulerView(Scheduler s){
        scheduler = s;
        update(scheduler);
        setPreferredSize(new Dimension(100,125));
    }

    @Override
    public void update(Object sender) {
        setText(String.valueOf(scheduler.getMeasure() % 4));
    }
}
