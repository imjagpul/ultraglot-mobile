/*
 * Browser.java
 *
 * Created on 9. kvìten 2007, 16:23
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Vector;
import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import javax.microedition.io.file.FileSystemRegistry;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

/**
 *
 * @author Stepan
 */
public class BrowserOld extends MIDlet implements CommandListener {
    
//    private static final String[] ELEMENTS=new String[] {
//        "30.txt",
//        "perfect_forms.txt",
//        "ja_L7.txt"};
    private static final String[] ELEMENTS=null;
    
    private static BrowserOld instance;
    public static BrowserOld getInstance() {
        return instance;
    }
    
    /** Creates a new instance of Browser */
    public BrowserOld() {
        instance=this;
        Settings.getInstance().load();
        
        initialize();
    }
    
    
//    private BTManager bt;
    public String currDirName=MEGA_ROOT;
    public String currentFile="";
    /* special string denotes upper directory */
    private final static String UP_DIRECTORY = "../";
    
    /* special string that denotes apper directory accessible by this browser.
     * this virtual directory contains all roots.
     */
    private final static String MEGA_ROOT = "/";
    
    /* separator string as defined by FC specification */
    private final static String SEP_STR = "/";
    
    /* separator character as defined by FC specification */
    private final static char   SEP = '/';
    public static final String SANDBOX = "[sandbox]/";
    public static final String EMBED = "/[embed]/";
    
    private List listFileBrowser;//GEN-BEGIN:MVDFields
    private Command exitCommand;
    private Command searchCommand;
    private Command readCommand;
    private Command backCommand;
    private Form formBookMenu;
    private Command settingsCommand;
    private TextField textFieldEncoding;
    private Command mapKeysCommand;
    private Command screenCommand1;
    private List listKeyboards;
    private Command selectCommand;
    private Command colorCommand;
    private ChoiceGroup choiceGroupSettings;
    private ChoiceGroup choiceGroupTestMode;//GEN-END:MVDFields
    private QuestionDisplay questionDisplay;
    private KeymappingForm keymappingForm;
    private ColorForm colorForm;
//GEN-LINE:MVDMethods
    
    
    /** Called by the system to indicate that a command has been invoked on a particular displayable.//GEN-BEGIN:MVDCABegin
     * @param command the Command that ws invoked
     * @param displayable the Displayable on which the command was invoked
     */
    public void commandAction(Command command, Displayable displayable) {//GEN-END:MVDCABegin
        
        int selectedIndex=get_listFileBrowser().getSelectedIndex();
        if(selectedIndex>=0)
            currentFile=get_listFileBrowser().getString(get_listFileBrowser().getSelectedIndex());
        
        if (displayable == listFileBrowser) {//GEN-BEGIN:MVDCABody
            if (command == exitCommand) {//GEN-END:MVDCABody
                // Insert pre-action code here
                exitMIDlet();//GEN-LINE:MVDCAAction12
                // Insert post-action code here
            } else if (command == readCommand) {//GEN-LINE:MVDCACase12
                if(readFile())
                    getDisplay().setCurrent(getBook());
                // Do nothing//GEN-LINE:MVDCAAction31
                
            } else if (command == settingsCommand) {//GEN-LINE:MVDCACase31
                initMenu();
                getDisplay().setCurrent(get_formBookMenu());//GEN-LINE:MVDCAAction57
                // Insert post-action code here
            } else if (command == mapKeysCommand) {//GEN-LINE:MVDCACase57
                getDisplay().setCurrent(get_keymappingForm());
                // Do nothing//GEN-LINE:MVDCAAction68
                // Insert post-action code here
            } else if (command == screenCommand1) {//GEN-LINE:MVDCACase68
                // Insert pre-action code here
                getDisplay().setCurrent(get_listKeyboards());//GEN-LINE:MVDCAAction71
                // Insert post-action code here
            } else if (command == colorCommand) {//GEN-LINE:MVDCACase71
                getDisplay().setCurrent(get_colorForm());
                // Do nothing//GEN-LINE:MVDCAAction88
                // Insert post-action code here
            }//GEN-BEGIN:MVDCACase88
        } else if (displayable == formBookMenu) {
            if (command == backCommand) {//GEN-END:MVDCACase88
                getBook().updateFromMenu();
                getDisplay().setCurrent(get_listFileBrowser());//GEN-LINE:MVDCAAction38
                // Insert post-action code here
            } else if (command == readCommand) {//GEN-LINE:MVDCACase38
                getBook().updateFromMenu();
                if(getBook().isTestLoaded()) {
                    getDisplay().setCurrent(getBook());
                } else {
                    getDisplay().setCurrent(get_listFileBrowser());
                }
                // Do nothing//GEN-LINE:MVDCAAction43
                // Insert post-action code here
            }//GEN-BEGIN:MVDCACase43
        } else if (displayable == listKeyboards) {
            if (command == listKeyboards.SELECT_COMMAND) {
                switch (get_listKeyboards().getSelectedIndex()) {
                    case 0://GEN-END:MVDCACase43
                        //cz
                        getBook().setKeyboard(new Keyboard());
                        getDisplay().setCurrent(get_listFileBrowser());
                        // Do nothing//GEN-LINE:MVDCAAction75
                        break;//GEN-BEGIN:MVDCACase75
                    case 1://GEN-END:MVDCACase75
                        //eo
                        getBook().setKeyboard(new EsperantoKeyboard());
                        getDisplay().setCurrent(get_listFileBrowser());
                        // Do nothing//GEN-LINE:MVDCAAction77
                        break;//GEN-BEGIN:MVDCACase77
                    case 2://GEN-END:MVDCACase77
                        //ru
                        getBook().setKeyboard(new RussianKeyboard());
                        getDisplay().setCurrent(get_listFileBrowser());
                        // Do nothing//GEN-LINE:MVDCAAction79
                        break;//GEN-BEGIN:MVDCACase79
                    case 3://GEN-END:MVDCACase79
                        //ja
                        getBook().setKeyboard(new JapaneseKeyboard());
                        getDisplay().setCurrent(get_listFileBrowser());
                        // Do nothing//GEN-LINE:MVDCAAction81
                        break;//GEN-BEGIN:MVDCACase81
                }
            } else if (command == backCommand) {//GEN-END:MVDCACase81
                // Insert pre-action code here
                getDisplay().setCurrent(get_listFileBrowser());//GEN-LINE:MVDCAAction84
                // Insert post-action code here
            }//GEN-BEGIN:MVDCACase84
        }//GEN-END:MVDCACase84
        // Insert global post-action code here
}//GEN-LINE:MVDCAEnd
    
    /** This method initializes UI of the application.//GEN-BEGIN:MVDInitBegin
     */
    private void initialize() {//GEN-END:MVDInitBegin
        // Insert pre-init code here
        getDisplay().setCurrent(get_listFileBrowser());//GEN-LINE:MVDInitInit
        // Insert post-init code here
    }//GEN-LINE:MVDInitEnd
    
    /**
     * This method should return an instance of the display.
     */
    public Display getDisplay() {//GEN-FIRST:MVDGetDisplay
        return Display.getDisplay(this);
    }//GEN-LAST:MVDGetDisplay
    
    /**
     * This method should exit the midlet.
     */
    public void exitMIDlet() {//GEN-FIRST:MVDExitMidlet
        getDisplay().setCurrent(null);
        destroyApp(true);
        notifyDestroyed();
    }//GEN-LAST:MVDExitMidlet
    
    /** This method returns instance for listFileBrowser component and should be called instead of accessing listFileBrowser field directly.//GEN-BEGIN:MVDGetBegin2
     * @return Instance for listFileBrowser component
     */
    public List get_listFileBrowser() {
        if (listFileBrowser == null) {//GEN-END:MVDGetBegin2
            // Insert pre-init code here
            listFileBrowser = new List(null, Choice.IMPLICIT, new String[0], new Image[0]);//GEN-BEGIN:MVDGetInit2
            listFileBrowser.addCommand(get_exitCommand());
            listFileBrowser.addCommand(get_readCommand());
            listFileBrowser.addCommand(get_settingsCommand());
            listFileBrowser.addCommand(get_mapKeysCommand());
            listFileBrowser.addCommand(get_screenCommand1());
            listFileBrowser.addCommand(get_colorCommand());
            listFileBrowser.setCommandListener(this);
            listFileBrowser.setSelectedFlags(new boolean[0]);
            listFileBrowser.setSelectCommand(get_readCommand());//GEN-END:MVDGetInit2
            try {
                showCurrDir();
            } catch(SecurityException exc) {
                currDirName=SANDBOX;
                showCurrDir();
            }
        }//GEN-BEGIN:MVDGetEnd2
        return listFileBrowser;
    }//GEN-END:MVDGetEnd2
    
    
    /** This method returns instance for exitCommand component and should be called instead of accessing exitCommand field directly.//GEN-BEGIN:MVDGetBegin11
     * @return Instance for exitCommand component
     */
    public Command get_exitCommand() {
        if (exitCommand == null) {//GEN-END:MVDGetBegin11
            // Insert pre-init code here
            exitCommand = new Command("Exit", Command.EXIT, 1);//GEN-LINE:MVDGetInit11
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd11
        return exitCommand;
    }//GEN-END:MVDGetEnd11
    
    
    /** This method returns instance for searchCommand component and should be called instead of accessing searchCommand field directly.//GEN-BEGIN:MVDGetBegin17
     * @return Instance for searchCommand component
     */
    public Command get_searchCommand() {
        if (searchCommand == null) {//GEN-END:MVDGetBegin17
            // Insert pre-init code here
            searchCommand = new Command("Search", Command.ITEM, 1);//GEN-LINE:MVDGetInit17
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd17
        return searchCommand;
    }//GEN-END:MVDGetEnd17
    
    
    
    /** This method returns instance for readCommand component and should be called instead of accessing readCommand field directly.//GEN-BEGIN:MVDGetBegin30
     * @return Instance for readCommand component
     */
    public Command get_readCommand() {
        if (readCommand == null) {//GEN-END:MVDGetBegin30
            // Insert pre-init code here
            readCommand = new Command("Read", Command.OK, 1);//GEN-LINE:MVDGetInit30
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd30
        return readCommand;
    }//GEN-END:MVDGetEnd30
    
    
    /** This method returns instance for backCommand component and should be called instead of accessing backCommand field directly.//GEN-BEGIN:MVDGetBegin33
     * @return Instance for backCommand component
     */
    public Command get_backCommand() {
        if (backCommand == null) {//GEN-END:MVDGetBegin33
            // Insert pre-init code here
            backCommand = new Command("Back", Command.BACK, 1);//GEN-LINE:MVDGetInit33
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd33
        return backCommand;
    }//GEN-END:MVDGetEnd33
    
    /** This method returns instance for formBookMenu component and should be called instead of accessing formBookMenu field directly.//GEN-BEGIN:MVDGetBegin37
     * @return Instance for formBookMenu component
     */
    public Form get_formBookMenu() {
        if (formBookMenu == null) {//GEN-END:MVDGetBegin37
            // Insert pre-init code here
            formBookMenu = new Form(null, new Item[] {//GEN-BEGIN:MVDGetInit37
                get_textFieldEncoding(),
                get_choiceGroupSettings(),
                get_choiceGroupTestMode()
            });
            formBookMenu.addCommand(get_backCommand());
            formBookMenu.addCommand(get_readCommand());
            formBookMenu.setCommandListener(this);//GEN-END:MVDGetInit37
            formBookMenu.setItemStateListener(new ItemStateListener() {
                public void itemStateChanged(Item item) {
                    
                    Settings.getInstance().setSmart(get_choiceGroupSettings().isSelected(0)); //smart
                    Settings.getInstance().setReverse(get_choiceGroupSettings().isSelected(1)); //reverse
                    Settings.getInstance().setShuffle(get_choiceGroupSettings().isSelected(2)); //shuffle
                    
                    Settings.getInstance().setMode((byte)get_choiceGroupTestMode().getSelectedIndex());
                    
                    try {
                        Settings.getInstance().setEncoding(get_textFieldEncoding().getString());
                    } catch (UnsupportedEncodingException ex) {
                        String old=get_textFieldEncoding().getString();
                        get_textFieldEncoding().setString(Settings.getInstance().getEncoding());
                        getDisplay().setCurrent(new Alert("Unsupported encoding '"+old+"'"),
                                get_formBookMenu());
                    }
                }
            });
            
            
        }//GEN-BEGIN:MVDGetEnd37
        return formBookMenu;
    }//GEN-END:MVDGetEnd37
        
    
    /** This method returns instance for settingsCommand component and should be called instead of accessing settingsCommand field directly.//GEN-BEGIN:MVDGetBegin55
     * @return Instance for settingsCommand component
     */
    public Command get_settingsCommand() {
        if (settingsCommand == null) {//GEN-END:MVDGetBegin55
            // Insert pre-init code here
            settingsCommand = new Command("Settings", Command.OK, 1);//GEN-LINE:MVDGetInit55
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd55
        return settingsCommand;
    }//GEN-END:MVDGetEnd55
    
    /** This method returns instance for textFieldEncoding component and should be called instead of accessing textFieldEncoding field directly.//GEN-BEGIN:MVDGetBegin66
     * @return Instance for textFieldEncoding component
     */
    public TextField get_textFieldEncoding() {
        if (textFieldEncoding == null) {//GEN-END:MVDGetBegin66
            // Insert pre-init code here
            textFieldEncoding = new TextField("Encoding", "", 120, TextField.ANY);//GEN-LINE:MVDGetInit66
            
        }//GEN-BEGIN:MVDGetEnd66
        return textFieldEncoding;
    }//GEN-END:MVDGetEnd66
    
    /** This method returns instance for mapKeysCommand component and should be called instead of accessing mapKeysCommand field directly.//GEN-BEGIN:MVDGetBegin67
     * @return Instance for mapKeysCommand component
     */
    public Command get_mapKeysCommand() {
        if (mapKeysCommand == null) {//GEN-END:MVDGetBegin67
            // Insert pre-init code here
            mapKeysCommand = new Command("Map keys", Command.SCREEN, 1);//GEN-LINE:MVDGetInit67
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd67
        return mapKeysCommand;
    }//GEN-END:MVDGetEnd67
    
    /** This method returns instance for screenCommand1 component and should be called instead of accessing screenCommand1 field directly.//GEN-BEGIN:MVDGetBegin70
     * @return Instance for screenCommand1 component
     */
    public Command get_screenCommand1() {
        if (screenCommand1 == null) {//GEN-END:MVDGetBegin70
            // Insert pre-init code here
            screenCommand1 = new Command("Select keyboard", Command.SCREEN, 1);//GEN-LINE:MVDGetInit70
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd70
        return screenCommand1;
    }//GEN-END:MVDGetEnd70
    
    /** This method returns instance for listKeyboards component and should be called instead of accessing listKeyboards field directly.//GEN-BEGIN:MVDGetBegin72
     * @return Instance for listKeyboards component
     */
    public List get_listKeyboards() {
        if (listKeyboards == null) {//GEN-END:MVDGetBegin72
            // Insert pre-init code here
            listKeyboards = new List(null, Choice.IMPLICIT, new String[] {//GEN-BEGIN:MVDGetInit72
                "\u010Cesk\u00E1",
                "Esperanto",
                "Rusk\u00E1",
                "Japonsk\u00E1"
            }, new Image[] {
                null,
                null,
                null,
                null
            });
            listKeyboards.addCommand(get_backCommand());
            listKeyboards.setCommandListener(this);
            listKeyboards.setSelectedFlags(new boolean[] {
                false,
                false,
                false,
                false
            });//GEN-END:MVDGetInit72
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd72
        return listKeyboards;
    }//GEN-END:MVDGetEnd72
    
    /** This method returns instance for selectCommand component and should be called instead of accessing selectCommand field directly.//GEN-BEGIN:MVDGetBegin85
     * @return Instance for selectCommand component
     */
    public Command get_selectCommand() {
        if (selectCommand == null) {//GEN-END:MVDGetBegin85
            // Insert pre-init code here
            selectCommand = new Command("Item", Command.ITEM, 1);//GEN-LINE:MVDGetInit85
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd85
        return selectCommand;
    }//GEN-END:MVDGetEnd85
    
    /** This method returns instance for colorCommand component and should be called instead of accessing colorCommand field directly.//GEN-BEGIN:MVDGetBegin87
     * @return Instance for colorCommand component
     */
    public Command get_colorCommand() {
        if (colorCommand == null) {//GEN-END:MVDGetBegin87
            // Insert pre-init code here
            colorCommand = new Command("Setup colors", Command.SCREEN, 1);//GEN-LINE:MVDGetInit87
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd87
        return colorCommand;
    }//GEN-END:MVDGetEnd87
    
    /** This method returns instance for choiceGroupSettings component and should be called instead of accessing choiceGroupSettings field directly.//GEN-BEGIN:MVDGetBegin89
     * @return Instance for choiceGroupSettings component
     */
    public ChoiceGroup get_choiceGroupSettings() {
        if (choiceGroupSettings == null) {//GEN-END:MVDGetBegin89
            // Insert pre-init code here
            choiceGroupSettings = new ChoiceGroup("Settings", Choice.MULTIPLE, new String[] {//GEN-BEGIN:MVDGetInit89
                "Smart",
                "Reverse",
                "Shuffle"
            }, new Image[] {
                null,
                null,
                null
            });
            choiceGroupSettings.setSelectedFlags(new boolean[] {
                false,
                false,
                false
            });//GEN-END:MVDGetInit89
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd89
        return choiceGroupSettings;
    }//GEN-END:MVDGetEnd89

    /** This method returns instance for choiceGroupTestMode component and should be called instead of accessing choiceGroupTestMode field directly.//GEN-BEGIN:MVDGetBegin93
     * @return Instance for choiceGroupTestMode component
     */
    public ChoiceGroup get_choiceGroupTestMode() {
        if (choiceGroupTestMode == null) {//GEN-END:MVDGetBegin93
            // Insert pre-init code here
            choiceGroupTestMode = new ChoiceGroup("Testing mode", Choice.EXCLUSIVE, new String[] {//GEN-BEGIN:MVDGetInit93
                "Normal",
                "Flashcard",
                "T9"
            }, new Image[] {
                null,
                null,
                null
            });
            choiceGroupTestMode.setSelectedFlags(new boolean[] {
                false,
                false,
                false
            });//GEN-END:MVDGetInit93
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd93
        return choiceGroupTestMode;
    }//GEN-END:MVDGetEnd93
    
    public Displayable get_keymappingForm() {
        if(keymappingForm==null)
            keymappingForm=new KeymappingForm(getDisplay(), get_listFileBrowser());
        
        return keymappingForm;
    }
    
    
    
    public void startApp() {
    }
    
    public void pauseApp() {
        Settings.getInstance().save();
    }
    
    public void destroyApp(boolean unconditional) {
        Settings.getInstance().save();
        
    }
    
    public QuestionDisplay getBook() {
        if(questionDisplay==null) {
            questionDisplay=new QuestionDisplay();
        }
        
        return questionDisplay;
    }
    
    private boolean readFile() {
        try { //todo fix errmsg
            
            if(currentFile.endsWith(SEP_STR)) {
                if(currentFile.equals(UP_DIRECTORY)) {
                    currDirName=currDirName.substring(0, currDirName.length()-1);
                    currDirName=currDirName.substring(0, currDirName.lastIndexOf(SEP)+1);
                } else if(currentFile.equals(EMBED)) {
                    currDirName=EMBED;
                } else {
                    currDirName+=currentFile;
                }
                showCurrDir();
                return false;
            } else if(currentFile.endsWith(".font")) {
//                getBook().showErrorMsg("Cannot read jars.");
                return false;
            } else {
                getBook().loadFile(new BufferedReader(openFile()));
                return true;
            }
        } catch(Exception exc) {
            exc.printStackTrace();
//            getBook().showErrorMsg(exc.getMessage());
            return false;
        }
    }
    
//    public Book getBook() {
//        if(book==null) {
//            book=new Book(getDisplay().getCurrent().getWidth(), getDisplay().getCurrent().getHeight(), this);
//        }
//
//        return book;
//    }
    
/*
    public int getBgColor() {
        int r=0,g=0,b=0;
 
        if(get_textFieldBgColorR().size() > 0)
            r=(Integer.parseInt(get_textFieldBgColorR().getString()) << 16 );
 
        if(get_textFieldBgColorG().size() > 0)
            g=(Integer.parseInt(get_textFieldBgColorG().getString()) << 8 );
 
        if(get_textFieldBgColorB().size() > 0)
            b=(Integer.parseInt(get_textFieldBgColorB().getString()));
 
        return r+g+b;
    }
 
    public void setBgColor(int color) {
        get_textFieldBgColorR().setString(Integer.toString((color & 0x00FF0000) >> 16));
        get_textFieldBgColorG().setString(Integer.toString((color & 0x0000FF00) >> 8));
        get_textFieldBgColorB().setString(Integer.toString(color & 0x000000FF));
    }
 */
    
    public void initMenu() {
        get_textFieldEncoding().setString(Settings.getInstance().getEncoding());
        
        get_choiceGroupSettings().setSelectedIndex(0, Settings.getInstance().getSmart()); //smart
        get_choiceGroupSettings().setSelectedIndex(1, Settings.getInstance().getReverse()); //reverse
        get_choiceGroupSettings().setSelectedIndex(2, Settings.getInstance().getShuffle()); //shuffle
    }
    
    /**
     * Show file list in the current directory .
     */
    void showCurrDir() {
        Enumeration e;
        FileConnection currDir = null;
        
        try {
            get_listFileBrowser().setTitle(currDirName);
            
            get_listFileBrowser().deleteAll();
            
            if(SANDBOX.equals(currDirName) || EMBED.equals(currDirName)) {
                //TODO : e = embedded
                if(EMBED.equals(currDirName)) {
                    // not root - draw UP_DIRECTORY
                    get_listFileBrowser().append(UP_DIRECTORY, null);
                }
                
                if(ELEMENTS!=null) {
                    for (int i = 0; i < ELEMENTS.length; i++) {
                        get_listFileBrowser().append(ELEMENTS[i], null);
                    }
                }
                return;
            }
            
            if (MEGA_ROOT.equals(currDirName)) {
                e = FileSystemRegistry.listRoots();
                
                //root - draw special folder
                if(ELEMENTS!=null)
                    get_listFileBrowser().append(EMBED, null);
            } else {
                currDir = (FileConnection)Connector.open("file://localhost" +
                        currDirName);
                
                if(!currDir.exists()) {
                    currDirName=MEGA_ROOT;
                    showCurrDir();
                    return;
                }
                
                
//                try { Thread.sleep(100); } catch (InterruptedException ex) {} //emulator hack
                
                e = currDir.list(); // BUG : the WTK emulator often dies at this point, unless ^ hack is present
                // (a threading bug?)
                
                // not root - draw UP_DIRECTORY
                get_listFileBrowser().append(UP_DIRECTORY, null);
            }
            
            Vector dirs=new Vector();
            Vector filenames=new Vector();
            while (e.hasMoreElements()) {
                String fileName = (String)e.nextElement();
                if (fileName.charAt(fileName.length()-1) == SEP) {
                    // This is directory
                    //get_listFileBrowser().append(fileName, null);
                    dirs.addElement(fileName);
                } else {
                    // this is regular file
                    //get_listFileBrowser().append(fileName, null);
                    filenames.addElement(fileName);                    
                }
            }
            
            //TODO: sort by name
            for (int i = 0; i < dirs.size(); i++) {
                get_listFileBrowser().append((String) dirs.elementAt(i), null);
            }

            for (int i = 0; i < filenames.size(); i++) {
                get_listFileBrowser().append((String) filenames.elementAt(i), null);
            }
            
            
//            get_listFileBrowser().setSelectCommand(view);
//            get_listFileBrowser().addCommand(prop);
            
//            get_listFileBrowser().addCommand(exit);
            
//            get_listFileBrowser().setCommandListener(this);
            
            if (currDir != null) {
                currDir.close();
            }
            
//            Display.getDisplay(this).setCurrent(browser);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
    public InputStream openFile() throws IOException {
        if(ELEMENTS!=null && (MEGA_ROOT+EMBED).equals(currDirName)) {
            return BrowserOld.class.getResourceAsStream(currentFile);
        } else {
            return Connector.openInputStream("file://localhost" +currDirName + currentFile);
        }
    }
    
    private Displayable get_colorForm() {
        if(colorForm==null)
            colorForm=new ColorForm(getDisplay(), get_listFileBrowser());
        
        return colorForm;
    }
    
    
}

