
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.List;
/*
 * KeymappingForm.java
 *
 * Created on 30. srpen 2007, 17:56
 */

/**
 *
 * @author Stepan
 */
public class KeymappingForm extends List implements CommandListener {

    private final int[] actions;
    private final String[] names = new String[]{"Del", "Clear", "Enter", "Menu",
        "Skip", "Left", "Right", "Help", "Backspace"
    };
    private Display display;
    private Displayable nextDisplayable;
    private KeyAccess keyAccess = new KeyAccess();
    private Command back,  edit,  unmap;

    /** Creates a new instance of KeymappingForm */
    public KeymappingForm(Display display, Displayable nextDisplayable) {
        super("Keymap", IMPLICIT);

        this.display = display;
        this.nextDisplayable = nextDisplayable;

        //add keys
        actions = new int[names.length];

        //load from settings
        for (int i = 0; i < actions.length; i++) {
            actions[i] = Settings.getInstance().getKeyMapping(i);
            append(createKeyName(i), null);
        }

        //add commands
        back = new Command("Back", Command.BACK, 0);
        edit = new Command("Edit", Command.ITEM, 0);
        unmap = new Command("Unmap", Command.ITEM, 1);
        setSelectCommand(edit);

        addCommand(back);
        addCommand(edit);
        addCommand(unmap);
        setCommandListener(this);

    }

    private String createKeyName(int i) {
        if (actions[i] == 0) {
            return names[i];
        } else {
            String keyname = "<unknown>";
            try {
                keyname = keyAccess.getKeyName(actions[i]);
            } catch (IllegalArgumentException exc) {
            }

            return names[i] + ":" + keyname + " (" + actions[i] + ")";
        }
    }

    public void commandAction(Command command, Displayable displayable) {
        if (command == back) {
            display.setCurrent(nextDisplayable);
            for (int i = 0; i < actions.length; i++) {
                Settings.getInstance().setKeyMapping(i, actions[i]);
            }
            return;
        }

        int sel = getSelectedIndex();

        if (command == unmap) {
            actions[sel] = 0;
            set(sel, names[sel], null);
        } else if (command == edit) {
            keyAccess.mapKey(sel);
        }

    }

    private class KeyAccess extends Canvas {

        private int mappedAction;
        private long startTime;

        public KeyAccess() {
            setFullScreenMode(true);
        }

        protected void paint(Graphics graphics) {
            graphics.setColor(0);
            graphics.fillRect(0, 0, getWidth(), getHeight());
            graphics.setColor(0xAA0000);
            graphics.drawArc(getWidth() / 2, getHeight() / 2, getWidth() / 4, getHeight() / 4, 0, 365);
            graphics.drawArc(getWidth() / 2, getHeight() / 2, getWidth() / 8, getHeight() / 8, 0, 365);
            graphics.drawArc(getWidth() / 2, getHeight() / 2, getWidth() / 16, getHeight() / 16, 0, 365);
        }

        public void mapKey(int action) {
            mappedAction = action;
            display.setCurrent(this);
            startTime = System.currentTimeMillis();
        }
        private static final long PAUSE_FOR_SELECT = 300L;

        protected void keyReleased(int keyCode) {
            if (keyCode == KEY_STAR || keyCode == KEY_POUND || keyCode >= KEY_NUM0 && keyCode <= KEY_NUM9) {
                return;
            //pauza
            }
            if ((System.currentTimeMillis() - startTime) < PAUSE_FOR_SELECT) {
                return;
            }

            actions[mappedAction] = keyCode;
            set(mappedAction, createKeyName(mappedAction), null);
            display.setCurrent(KeymappingForm.this);
        }
    }
}
