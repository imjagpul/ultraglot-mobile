import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.List;
/*
 * ColorForm.java
 *
 * Created on 3. zברם 2007, 10:03
 */

/**
 *
 * @author Stepan
 */
public class ColorForm extends List implements CommandListener {
    private final int[] colors;
    private static final String[] names=new String[] {"Foreground", "Background",
    "Answer", "Cursor", "Help", "Active mapping FG", "Active mapping BG",
    "Active mapping frame", "Help indicator", "Wrong indicator"};
    
    private Display display;
    private Displayable nextDisplayable;
    
    private ColorAccess colorAccess=new ColorAccess();
    private Command back, edit;
    
    /** Creates a new instance of KeymappingForm */
    public ColorForm(Display display, Displayable nextDisplayable) {
        super("Colormap", IMPLICIT);
        
        this.display=display;
        this.nextDisplayable=nextDisplayable;
        
        //add keys
        colors=new int[names.length];
        
        //load from settings
        for (int i = 0; i < colors.length; i++) {
            append("", null);
            setColor(i, Settings.getInstance().getColor(i));
        }
        
        //add commands
        back=new Command("Back", Command.BACK, 0);
        edit=new Command("Change", Command.ITEM, 0);
        setSelectCommand(edit);
        
        addCommand(back);
        addCommand(edit);
        setCommandListener(this);
        
    }
    
    private String toHexString(int number) {
        String r=Integer.toHexString(number);
        while(r.length()<6)
            r="0"+r;
        return r;
    }
    
    private void setColor(int i, int value) {
        colors[i]=value;
        
        Settings.getInstance().setColor(i, value);
        set(i, names[i]+":"+toHexString(value), null);
    }
    
    public void commandAction(Command command, Displayable displayable) {
        if(command==back) {
            display.setCurrent(nextDisplayable);
            for (int i = 0; i < colors.length; i++) {
                Settings.getInstance().setColor(i, colors[i]);
            }
        } else //if(command==edit) {
            colorAccess.mapColor(getSelectedIndex());
    }
    
    private class ColorAccess extends Canvas {
        private int mappedIndex;
        
        public ColorAccess() {
            setFullScreenMode(true);
        }
        
        private static final String R="R:";
        private static final String G="G:";
        private static final String B="B:";
        
        private int active=0;
        
        private static final int ACTIVE=0xFFFFFF;
        private static final int R_COLOR=0xAA0000;
        private static final int G_COLOR=0x00AA00;
        private static final int B_COLOR=0x0000AA;
        private static final int STEP=10;
        
        protected void paint(Graphics graphics) {
            graphics.setColor(0);
            graphics.fillRect(0,0, getWidth(), getHeight());
            
            int h=graphics.getFont().getHeight();
            int fontW=graphics.getFont().stringWidth(R);
            fontW=Math.max(fontW, graphics.getFont().stringWidth(G));
            fontW=Math.max(fontW, graphics.getFont().stringWidth(B));
            
            graphics.setColor(colors[mappedIndex]);
            graphics.fillRect(fontW, 0, h, h);
            graphics.setColor(ACTIVE);
            graphics.drawRect(fontW, 0, h, h);
            
            graphics.setColor(0xAAAAAA);
            graphics.drawString(toHexString(colors[mappedIndex]), getWidth()/2, 0, Graphics.TOP | Graphics.HCENTER);
            
            //red
            int y=h*2;            
            int w= getWidth() - fontW - 2;
            int part=((0xFF0000 & colors[mappedIndex]) >> 16)*w / 0xFF;
            
            if(active==0)
                graphics.setColor(ACTIVE);
            else
                graphics.setColor(R_COLOR);
            graphics.drawString(R, 0, y, 0);
            graphics.setColor(R_COLOR);
            graphics.drawRect(fontW, y, w, h);
            graphics.fillRect(fontW, y, part, h);
            
            //green
            y+=h+h/2;
            part=((0x00FF00 & colors[mappedIndex]) >> 8)*w / 0xFF;
            
            if(active==1)
                graphics.setColor(ACTIVE);
            else
                graphics.setColor(G_COLOR);
            
            graphics.drawString(G, 0, y, 0);
            graphics.setColor(G_COLOR);
            graphics.drawRect(fontW, y, w, h);
            graphics.fillRect(fontW, y, part, h);
            
            //blue
            y+=h+h/2;
            
            part=(0x0000FF & colors[mappedIndex])*w / 0xFF;
            
            if(active==2)
                graphics.setColor(ACTIVE);
            else
                graphics.setColor(B_COLOR);
            
            graphics.drawString(B, 0, y, 0);
            graphics.setColor(B_COLOR);
            graphics.drawRect(fontW, y, w, h);
            graphics.fillRect(fontW, y, part, h);
        }
        
        public void mapColor(int index) {
            mappedIndex=index;
            display.setCurrent(this);
        }
        
        
        protected void keyPressed(int keyCode) {
            if(getGameAction(keyCode)==DOWN) {
                if(active<2) active++;
            }
            
            if(getGameAction(keyCode)==UP) {
                if(active>0) active--;
            }
            
            if(getGameAction(keyCode)==LEFT || getGameAction(keyCode)==RIGHT) {
                int shift=8* (2-active);
                
                //extract value
                int val=(colors[mappedIndex] >> shift) & 0xFF;
                
                //increase or decrease value
                if(getGameAction(keyCode)==LEFT) {
                    if(val>0)
                        val-=STEP;
                    if(val<0) val=0;
                } else if(val<0xFF) {
                    val+=STEP;
                    if(val>0xFF)
                        val=0xFF;
                }
                
                
                //combine value back into the color
                val = val << shift;
                setColor(mappedIndex, val | colors[mappedIndex] & ~(0xFF << shift));
            }
            
            if(getGameAction(keyCode)==FIRE) {
                display.setCurrent(ColorForm.this);
            } 
            
            repaint();
        }

        protected void keyRepeated(int keyCode) {
            keyPressed(keyCode);
        }
    }
}
