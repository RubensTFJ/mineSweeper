package pt.com.minesweep.vision;

import javax.swing.*;
import java.awt.event.MouseEvent;

public class EventHandler {

    public String  type = null;
    public JButton button = null;
    public Object  content = null;
    public int     mouseButton = 0;
    public EventHandler(MouseEvent e) {
        this.button = (e != null && e.getComponent() instanceof JButton) ?
                ((JButton) e.getComponent()) : (null);
        if (button == null)
            return ;
        this.mouseButton = e.getButton();
        this.type = (String) button.getClientProperty("type");
        this.content = button.getClientProperty("content");
    }
}
