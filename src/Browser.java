/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
 * @author Stepan
 */
public class Browser extends MIDlet implements CommandListener {

// HINT - Uncomment for accessing new MIDlet Started/Resumed logic.
//    private boolean midletPaused = false;

    //<editor-fold defaultstate="collapsed" desc=" Generated Fields ">//GEN-BEGIN:|fields|0|
    private List listFileBrowser;
    private Form formBookMenu;
    private TextField textFieldEncoding;
    private ChoiceGroup choiceGroupSettings;
    private ChoiceGroup choiceGroupTestMode;
    private List listKeyboards;
    private Command exitCommand;
    private Command readCommand;
    private Command settingsCommand;
    private Command backCommand;
    private Command screenCommand1;
    private Command mapKeysCommand;
    private Command colorCommand;
    private Command markCommand;
    private Command selectCommand;
    private Command excludeAllCommand;
    private Command upCommand;
    private Command itemCommand;
    //</editor-fold>//GEN-END:|fields|0|
    private Image includedIcon = null;

    private Image getIncludedIcon() {
        if (includedIcon == null) {
            try {
                includedIcon = Image.createImage(Browser.class.getResourceAsStream("/icon.png"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return includedIcon;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc=" Generated Methods ">//GEN-BEGIN:|methods|0|
    //</editor-fold>//GEN-END:|methods|0|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: initialize ">//GEN-BEGIN:|0-initialize|0|0-preInitialize
    /**
     * Initilizes the application.
     * It is called only once when the MIDlet is started. The method is called before the <code>startMIDlet</code> method.
     */
    private void initialize() {//GEN-END:|0-initialize|0|0-preInitialize
        // write pre-initialize user code here
//GEN-LINE:|0-initialize|1|0-postInitialize
        getDisplay().setCurrent(getListFileBrowser());
    }//GEN-BEGIN:|0-initialize|2|
    //</editor-fold>//GEN-END:|0-initialize|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: startMIDlet ">//GEN-BEGIN:|3-startMIDlet|0|3-preAction
    /**
     * Performs an action assigned to the Mobile Device - MIDlet Started point.
     */
    public void startMIDlet() {//GEN-END:|3-startMIDlet|0|3-preAction
        // write pre-action user code here
        switchDisplayable(null, getListFileBrowser());//GEN-LINE:|3-startMIDlet|1|3-postAction
    // write post-action user code here
    }//GEN-BEGIN:|3-startMIDlet|2|
    //</editor-fold>//GEN-END:|3-startMIDlet|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: resumeMIDlet ">//GEN-BEGIN:|4-resumeMIDlet|0|4-preAction
    /**
     * Performs an action assigned to the Mobile Device - MIDlet Resumed point.
     */
    public void resumeMIDlet() {//GEN-END:|4-resumeMIDlet|0|4-preAction
        // write pre-action user code here
//GEN-LINE:|4-resumeMIDlet|1|4-postAction
        // write post-action user code here
    }//GEN-BEGIN:|4-resumeMIDlet|2|
    //</editor-fold>//GEN-END:|4-resumeMIDlet|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: switchDisplayable ">//GEN-BEGIN:|5-switchDisplayable|0|5-preSwitch
    /**
     * Switches a current displayable in a display. The <code>display</code> instance is taken from <code>getDisplay</code> method. This method is used by all actions in the design for switching displayable.
     * @param alert the Alert which is temporarily set to the display; if <code>null</code>, then <code>nextDisplayable</code> is set immediately
     * @param nextDisplayable the Displayable to be set
     */
    public void switchDisplayable(Alert alert, Displayable nextDisplayable) {//GEN-END:|5-switchDisplayable|0|5-preSwitch
        // write pre-switch user code here
        Display display = getDisplay();//GEN-BEGIN:|5-switchDisplayable|1|5-postSwitch
        if (alert == null) {
            display.setCurrent(nextDisplayable);
        } else {
            display.setCurrent(alert, nextDisplayable);
        }//GEN-END:|5-switchDisplayable|1|5-postSwitch
    // write post-switch user code here
    }//GEN-BEGIN:|5-switchDisplayable|2|
    //</editor-fold>//GEN-END:|5-switchDisplayable|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: commandAction for Displayables ">//GEN-BEGIN:|7-commandAction|0|7-preCommandAction
    /**
     * Called by a system to indicated that a command has been invoked on a particular displayable.
     * @param command the Command that was invoked
     * @param displayable the Displayable where the command was invoked
     */
    public void commandAction(Command command, Displayable displayable) {//GEN-END:|7-commandAction|0|7-preCommandAction

        int selectedIndex = getListFileBrowser().getSelectedIndex();
        if (selectedIndex >= 0) {
            currentFile = getListFileBrowser().getString(getListFileBrowser().getSelectedIndex());
        }

        if (displayable == formBookMenu) {//GEN-BEGIN:|7-commandAction|1|25-preAction
            if (command == backCommand) {//GEN-END:|7-commandAction|1|25-preAction
                getBook().updateFromMenu();
                switchDisplayable(null, getListFileBrowser());//GEN-LINE:|7-commandAction|2|25-postAction
            // Insert post-action code here
            } else if (command == readCommand) {//GEN-LINE:|7-commandAction|3|28-preAction
                getBook().updateFromMenu();
                if (getBook().isTestLoaded()) {
                    getDisplay().setCurrent(getBook());
                } else {
                    getDisplay().setCurrent(getListFileBrowser());
                }
//GEN-LINE:|7-commandAction|4|28-postAction
            // Insert post-action code here
            }//GEN-BEGIN:|7-commandAction|5|15-preAction
        } else if (displayable == listFileBrowser) {
            if (command == List.SELECT_COMMAND) {//GEN-END:|7-commandAction|5|15-preAction
                // write pre-action user code here
                listFileBrowserAction();//GEN-LINE:|7-commandAction|6|15-postAction
            // write post-action user code here
            } else if (command == colorCommand) {//GEN-LINE:|7-commandAction|7|53-preAction
                getDisplay().setCurrent(get_colorForm());
//GEN-LINE:|7-commandAction|8|53-postAction
            } else if (command == excludeAllCommand) {//GEN-LINE:|7-commandAction|9|63-preAction
                includedTests.removeAllElements();
                int index = listFileBrowser.getSelectedIndex();
                showCurrDir();
                try {
                    listFileBrowser.setSelectedIndex(index, true);
                } catch (IndexOutOfBoundsException exc) {
                    //ignore - filesystem changed during include/exclude
                }
//GEN-LINE:|7-commandAction|10|63-postAction
            } else if (command == exitCommand) {//GEN-LINE:|7-commandAction|11|17-preAction
                // Insert pre-action code here
                exitMIDlet();//GEN-LINE:|7-commandAction|12|17-postAction
            // Insert post-action code here
            } else if (command == mapKeysCommand) {//GEN-LINE:|7-commandAction|13|39-preAction
                getDisplay().setCurrent(get_keymappingForm());
//GEN-LINE:|7-commandAction|14|39-postAction
            // Insert post-action code here
            } else if (command == markCommand) {//GEN-LINE:|7-commandAction|15|59-preAction
                //Integer index = new Integer(getListFileBrowser().getSelectedIndex());
                String current = currDirName + currentFile;
                if (includedTests.contains(current)) {
                    includedTests.removeElement(current);
                } else {
                    includedTests.addElement(current);
                }

                int index = listFileBrowser.getSelectedIndex();
                showCurrDir();
                try {
                    listFileBrowser.setSelectedIndex(index, true);
                } catch (IndexOutOfBoundsException exc) {
                    //ignore - filesystem changed during include/exclude
                }
//GEN-LINE:|7-commandAction|16|59-postAction
            // write post-action user code here
            } else if (command == readCommand) {//GEN-LINE:|7-commandAction|17|20-preAction
//                try {
//                boolean result=readFile();
//                  getDisplay().setCurrent(new Alert("Alert"), getListFileBrowser());
//                }catch(Exception e) {
//                  getDisplay().setCurrent(new Alert(e.toString()), getListFileBrowser());
//
//                }
                if (readFile()) {
//                    getDisplay().setCurrent(get_keymappingForm());
                    getDisplay().setCurrent(getBook());
                }
//GEN-LINE:|7-commandAction|18|20-postAction

            } else if (command == screenCommand1) {//GEN-LINE:|7-commandAction|19|41-preAction
                // Insert pre-action code here
                switchDisplayable(null, getListKeyboards());//GEN-LINE:|7-commandAction|20|41-postAction
            // Insert post-action code here
            } else if (command == settingsCommand) {//GEN-LINE:|7-commandAction|21|22-preAction
                initMenu();
                switchDisplayable(null, getFormBookMenu());//GEN-LINE:|7-commandAction|22|22-postAction
            // Insert post-action code here
            } else if (command == upCommand) {//GEN-LINE:|7-commandAction|23|61-preAction
                currentFile = UP_DIRECTORY;
                readFile();
//GEN-LINE:|7-commandAction|24|61-postAction
            // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|25|44-preAction
        } else if (displayable == listKeyboards) {
            if (command == List.SELECT_COMMAND) {//GEN-END:|7-commandAction|25|44-preAction
                // write pre-action user code here
                listKeyboardsAction();//GEN-LINE:|7-commandAction|26|44-postAction
            // write post-action user code here
            } else if (command == backCommand) {//GEN-LINE:|7-commandAction|27|46-preAction
                // Insert pre-action code here
                switchDisplayable(null, getListFileBrowser());//GEN-LINE:|7-commandAction|28|46-postAction
            // Insert post-action code here
            } else if (command == selectCommand) {//GEN-LINE:|7-commandAction|29|57-preAction
                switch (getListKeyboards().getSelectedIndex()) {
                    case 0:
                        //cz
                        getBook().setKeyboard(new Keyboard());
                        break;
                    case 1:
                        //eo
                        getBook().setKeyboard(new EsperantoKeyboard());
                        break;
                    case 2:
                        //ru
                        getBook().setKeyboard(new RussianKeyboard());
                        break;
                    case 3:
                        //ja
                        getBook().setKeyboard(new JapaneseKeyboard());
                        break;
                    case 4:
                        //fr
                        getBook().setKeyboard(new FrenchKeyboard());
                        break;
                    case 5:
                        //qwerty universal
                        getBook().setKeyboard(new QwertyKeyboard());
                        break;
                }
                getDisplay().setCurrent(getListFileBrowser());
//GEN-LINE:|7-commandAction|30|57-postAction
            // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|31|7-postCommandAction
        }//GEN-END:|7-commandAction|31|7-postCommandAction
    // Insert global post-action code here
    }//GEN-BEGIN:|7-commandAction|32|
    //</editor-fold>//GEN-END:|7-commandAction|32|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: listFileBrowser ">//GEN-BEGIN:|13-getter|0|13-preInit
    /**
     * Returns an initiliazed instance of listFileBrowser component.
     * @return the initialized component instance
     */
    public List getListFileBrowser() {
        if (listFileBrowser == null) {//GEN-END:|13-getter|0|13-preInit
            // Insert pre-init code here
            listFileBrowser = new List(null, Choice.IMPLICIT);//GEN-BEGIN:|13-getter|1|13-postInit
            listFileBrowser.addCommand(getExitCommand());
            listFileBrowser.addCommand(getReadCommand());
            listFileBrowser.addCommand(getSettingsCommand());
            listFileBrowser.addCommand(getMapKeysCommand());
            listFileBrowser.addCommand(getScreenCommand1());
            listFileBrowser.addCommand(getColorCommand());
            listFileBrowser.addCommand(getMarkCommand());
            listFileBrowser.addCommand(getUpCommand());
            listFileBrowser.addCommand(getExcludeAllCommand());
            listFileBrowser.setCommandListener(this);
            listFileBrowser.setSelectCommand(getReadCommand());//GEN-END:|13-getter|1|13-postInit
            try {
                showCurrDir();
            } catch (SecurityException exc) {
                currDirName = SANDBOX;
                showCurrDir();
            }
        }//GEN-BEGIN:|13-getter|2|
        return listFileBrowser;
    }
    //</editor-fold>//GEN-END:|13-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: listFileBrowserAction ">//GEN-BEGIN:|13-action|0|13-preAction
    /**
     * Performs an action assigned to the selected list element in the listFileBrowser component.
     */
    public void listFileBrowserAction() {//GEN-END:|13-action|0|13-preAction
        // enter pre-action user code here
//GEN-LINE:|13-action|1|13-postAction
        // enter post-action user code here
    }//GEN-BEGIN:|13-action|2|
    //</editor-fold>//GEN-END:|13-action|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: formBookMenu ">//GEN-BEGIN:|24-getter|0|24-preInit
    /**
     * Returns an initiliazed instance of formBookMenu component.
     * @return the initialized component instance
     */
    public Form getFormBookMenu() {
        if (formBookMenu == null) {//GEN-END:|24-getter|0|24-preInit
            // Insert pre-init code here
            formBookMenu = new Form("form", new Item[] { getTextFieldEncoding(), getChoiceGroupSettings(), getChoiceGroupTestMode() });//GEN-BEGIN:|24-getter|1|24-postInit
            formBookMenu.addCommand(getBackCommand());
            formBookMenu.addCommand(getReadCommand());
            formBookMenu.setCommandListener(this);//GEN-END:|24-getter|1|24-postInit
            formBookMenu.setItemStateListener(new ItemStateListener() {

                public void itemStateChanged(Item item) {

                    Settings.getInstance().setSmart(getChoiceGroupSettings().isSelected(0)); //smart

                    Settings.getInstance().setReverse(getChoiceGroupSettings().isSelected(1)); //reverse

                    Settings.getInstance().setShuffle(getChoiceGroupSettings().isSelected(2)); //shuffle

                    Settings.getInstance().setSort(getChoiceGroupSettings().isSelected(3)); //sort

                    Settings.getInstance().setMode((byte) getChoiceGroupTestMode().getSelectedIndex());

                    try {
                        Settings.getInstance().setEncoding(getTextFieldEncoding().getString());
                    } catch (UnsupportedEncodingException ex) {
                        String old = getTextFieldEncoding().getString();
                        getTextFieldEncoding().setString(Settings.getInstance().getEncoding());
                        getDisplay().setCurrent(new Alert("Unsupported encoding '" + old + "'"),
                                getFormBookMenu());
                    }
                }
            });


        }//GEN-BEGIN:|24-getter|2|
        return formBookMenu;
    }
    //</editor-fold>//GEN-END:|24-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: textFieldEncoding ">//GEN-BEGIN:|29-getter|0|29-preInit
    /**
     * Returns an initiliazed instance of textFieldEncoding component.
     * @return the initialized component instance
     */
    public TextField getTextFieldEncoding() {
        if (textFieldEncoding == null) {//GEN-END:|29-getter|0|29-preInit
            // Insert pre-init code here
            textFieldEncoding = new TextField("Encoding", null, 32, TextField.ANY);//GEN-LINE:|29-getter|1|29-postInit

        }//GEN-BEGIN:|29-getter|2|
        return textFieldEncoding;
    }
    //</editor-fold>//GEN-END:|29-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: choiceGroupSettings ">//GEN-BEGIN:|30-getter|0|30-preInit
    /**
     * Returns an initiliazed instance of choiceGroupSettings component.
     * @return the initialized component instance
     */
    public ChoiceGroup getChoiceGroupSettings() {
        if (choiceGroupSettings == null) {//GEN-END:|30-getter|0|30-preInit
            // Insert pre-init code here
            choiceGroupSettings = new ChoiceGroup("Settings", Choice.MULTIPLE);//GEN-BEGIN:|30-getter|1|30-postInit
            choiceGroupSettings.append("Smart", null);
            choiceGroupSettings.append("Reverse", null);
            choiceGroupSettings.append("Shuffle", null);
            choiceGroupSettings.append("Sort filelist", null);
            choiceGroupSettings.setSelectedFlags(new boolean[] { false, false, false, false });
            choiceGroupSettings.setFont(0, null);
            choiceGroupSettings.setFont(1, null);
            choiceGroupSettings.setFont(2, null);
            choiceGroupSettings.setFont(3, null);//GEN-END:|30-getter|1|30-postInit
        // Insert post-init code here
        }//GEN-BEGIN:|30-getter|2|
        return choiceGroupSettings;
    }
    //</editor-fold>//GEN-END:|30-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: choiceGroupTestMode ">//GEN-BEGIN:|34-getter|0|34-preInit
    /**
     * Returns an initiliazed instance of choiceGroupTestMode component.
     * @return the initialized component instance
     */
    public ChoiceGroup getChoiceGroupTestMode() {
        if (choiceGroupTestMode == null) {//GEN-END:|34-getter|0|34-preInit
            // Insert pre-init code here
            choiceGroupTestMode = new ChoiceGroup("Testing mode", Choice.EXCLUSIVE);//GEN-BEGIN:|34-getter|1|34-postInit
            choiceGroupTestMode.append("Normal", null);
            choiceGroupTestMode.append("Flashcard", null);
            choiceGroupTestMode.append("T9", null);
            choiceGroupTestMode.append("T9 NoSpace", null);
            choiceGroupTestMode.append("T9 NoSpecial", null);
            choiceGroupTestMode.setSelectedFlags(new boolean[] { false, false, false, false, false });
            choiceGroupTestMode.setFont(0, null);
            choiceGroupTestMode.setFont(1, null);
            choiceGroupTestMode.setFont(2, null);
            choiceGroupTestMode.setFont(3, null);
            choiceGroupTestMode.setFont(4, null);//GEN-END:|34-getter|1|34-postInit
        // Insert post-init code here
        }//GEN-BEGIN:|34-getter|2|
        return choiceGroupTestMode;
    }
    //</editor-fold>//GEN-END:|34-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: listKeyboards ">//GEN-BEGIN:|43-getter|0|43-preInit
    /**
     * Returns an initiliazed instance of listKeyboards component.
     * @return the initialized component instance
     */
    public List getListKeyboards() {
        if (listKeyboards == null) {//GEN-END:|43-getter|0|43-preInit
            // Insert pre-init code here
            listKeyboards = new List("list", Choice.IMPLICIT);//GEN-BEGIN:|43-getter|1|43-postInit
            listKeyboards.append("\u010Cesk\u00E1", null);
            listKeyboards.append("Esperanto", null);
            listKeyboards.append("Rusk\u00E1", null);
            listKeyboards.append("Japonsk\u00E1", null);
            listKeyboards.append("Francouzsk\u00E1", null);
            listKeyboards.append("QWERTY", null);
            listKeyboards.addCommand(getBackCommand());
            listKeyboards.addCommand(getSelectCommand());
            listKeyboards.setCommandListener(this);
            listKeyboards.setSelectCommand(getSelectCommand());
            listKeyboards.setSelectedFlags(new boolean[] { false, false, false, false, false, false });//GEN-END:|43-getter|1|43-postInit
        // Insert post-init code here
        }//GEN-BEGIN:|43-getter|2|
        return listKeyboards;
    }
    //</editor-fold>//GEN-END:|43-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: listKeyboardsAction ">//GEN-BEGIN:|43-action|0|43-preAction
    /**
     * Performs an action assigned to the selected list element in the listKeyboards component.
     */
    public void listKeyboardsAction() {//GEN-END:|43-action|0|43-preAction
        // enter pre-action user code here
        switch (getListKeyboards().getSelectedIndex()) {//GEN-BEGIN:|43-action|1|48-preAction
            case 0://GEN-END:|43-action|1|48-preAction
                // write pre-action user code here
//GEN-LINE:|43-action|2|48-postAction
                // write post-action user code here
                break;//GEN-BEGIN:|43-action|3|49-preAction
            case 1://GEN-END:|43-action|3|49-preAction
                // write pre-action user code here
//GEN-LINE:|43-action|4|49-postAction
                // write post-action user code here
                break;//GEN-BEGIN:|43-action|5|50-preAction
            case 2://GEN-END:|43-action|5|50-preAction
                // write pre-action user code here
//GEN-LINE:|43-action|6|50-postAction
                // write post-action user code here
                break;//GEN-BEGIN:|43-action|7|51-preAction
            case 3://GEN-END:|43-action|7|51-preAction
                // write pre-action user code here
//GEN-LINE:|43-action|8|51-postAction
                // write post-action user code here
                break;//GEN-BEGIN:|43-action|9|67-preAction
            case 4://GEN-END:|43-action|9|67-preAction
                // write pre-action user code here
//GEN-LINE:|43-action|10|67-postAction
                // write post-action user code here
                break;//GEN-BEGIN:|43-action|11|71-preAction
            case 5://GEN-END:|43-action|11|71-preAction
                // write pre-action user code here
//GEN-LINE:|43-action|12|71-postAction
                // write post-action user code here
                break;//GEN-BEGIN:|43-action|13|43-postAction
        }//GEN-END:|43-action|13|43-postAction
    // enter post-action user code here
    }//GEN-BEGIN:|43-action|14|
    //</editor-fold>//GEN-END:|43-action|14|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: exitCommand ">//GEN-BEGIN:|18-getter|0|18-preInit
    /**
     * Returns an initiliazed instance of exitCommand component.
     * @return the initialized component instance
     */
    public Command getExitCommand() {
        if (exitCommand == null) {//GEN-END:|18-getter|0|18-preInit
            // Insert pre-init code here
            exitCommand = new Command("Exit", Command.EXIT, 1);//GEN-LINE:|18-getter|1|18-postInit
        // Insert post-init code here
        }//GEN-BEGIN:|18-getter|2|
        return exitCommand;
    }
    //</editor-fold>//GEN-END:|18-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: readCommand ">//GEN-BEGIN:|21-getter|0|21-preInit
    /**
     * Returns an initiliazed instance of readCommand component.
     * @return the initialized component instance
     */
    public Command getReadCommand() {
        if (readCommand == null) {//GEN-END:|21-getter|0|21-preInit
            // Insert pre-init code here
            readCommand = new Command("Read", Command.OK, 1);//GEN-LINE:|21-getter|1|21-postInit
        // Insert post-init code here
        }//GEN-BEGIN:|21-getter|2|
        return readCommand;
    }
    //</editor-fold>//GEN-END:|21-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: settingsCommand ">//GEN-BEGIN:|23-getter|0|23-preInit
    /**
     * Returns an initiliazed instance of settingsCommand component.
     * @return the initialized component instance
     */
    public Command getSettingsCommand() {
        if (settingsCommand == null) {//GEN-END:|23-getter|0|23-preInit
            // Insert pre-init code here
            settingsCommand = new Command("Settings", Command.OK, 1);//GEN-LINE:|23-getter|1|23-postInit
        // Insert post-init code here
        }//GEN-BEGIN:|23-getter|2|
        return settingsCommand;
    }
    //</editor-fold>//GEN-END:|23-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: backCommand ">//GEN-BEGIN:|26-getter|0|26-preInit
    /**
     * Returns an initiliazed instance of backCommand component.
     * @return the initialized component instance
     */
    public Command getBackCommand() {
        if (backCommand == null) {//GEN-END:|26-getter|0|26-preInit
            // Insert pre-init code here
            backCommand = new Command("Back", Command.BACK, 1);//GEN-LINE:|26-getter|1|26-postInit
        // Insert post-init code here
        }//GEN-BEGIN:|26-getter|2|
        return backCommand;
    }
    //</editor-fold>//GEN-END:|26-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: mapKeysCommand ">//GEN-BEGIN:|40-getter|0|40-preInit
    /**
     * Returns an initiliazed instance of mapKeysCommand component.
     * @return the initialized component instance
     */
    public Command getMapKeysCommand() {
        if (mapKeysCommand == null) {//GEN-END:|40-getter|0|40-preInit
            // Insert pre-init code here
            mapKeysCommand = new Command("Map keys", Command.SCREEN, 1);//GEN-LINE:|40-getter|1|40-postInit
        // Insert post-init code here
        }//GEN-BEGIN:|40-getter|2|
        return mapKeysCommand;
    }
    //</editor-fold>//GEN-END:|40-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: screenCommand1 ">//GEN-BEGIN:|42-getter|0|42-preInit
/**
 * Returns an initiliazed instance of screenCommand1 component.
 * @return the initialized component instance
 */
public Command getScreenCommand1() {
    if (screenCommand1 == null) {//GEN-END:|42-getter|0|42-preInit
            // Insert pre-init code here
        screenCommand1 = new Command("Select keyboard", Command.SCREEN, 1);//GEN-LINE:|42-getter|1|42-postInit
        // Insert post-init code here
    }//GEN-BEGIN:|42-getter|2|
    return screenCommand1;
}
//</editor-fold>//GEN-END:|42-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: colorCommand ">//GEN-BEGIN:|54-getter|0|54-preInit
/**
 * Returns an initiliazed instance of colorCommand component.
 * @return the initialized component instance
 */
public Command getColorCommand() {
    if (colorCommand == null) {//GEN-END:|54-getter|0|54-preInit
            // Insert pre-init code here
        colorCommand = new Command("Setup colors", Command.SCREEN, 1);//GEN-LINE:|54-getter|1|54-postInit
        // Insert post-init code here
    }//GEN-BEGIN:|54-getter|2|
    return colorCommand;
}
//</editor-fold>//GEN-END:|54-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: selectCommand ">//GEN-BEGIN:|56-getter|0|56-preInit
/**
 * Returns an initiliazed instance of selectCommand component.
 * @return the initialized component instance
 */
public Command getSelectCommand() {
    if (selectCommand == null) {//GEN-END:|56-getter|0|56-preInit
            // write pre-init user code here
        selectCommand = new Command("Select", Command.ITEM, 0);//GEN-LINE:|56-getter|1|56-postInit
        // write post-init user code here
    }//GEN-BEGIN:|56-getter|2|
    return selectCommand;
}
//</editor-fold>//GEN-END:|56-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: markCommand ">//GEN-BEGIN:|58-getter|0|58-preInit
/**
 * Returns an initiliazed instance of markCommand component.
 * @return the initialized component instance
 */
public Command getMarkCommand() {
    if (markCommand == null) {//GEN-END:|58-getter|0|58-preInit
            // write pre-init user code here
        markCommand = new Command("Include", Command.ITEM, 0);//GEN-LINE:|58-getter|1|58-postInit
        // write post-init user code here
    }//GEN-BEGIN:|58-getter|2|
    return markCommand;
}
//</editor-fold>//GEN-END:|58-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: upCommand ">//GEN-BEGIN:|60-getter|0|60-preInit
/**
 * Returns an initiliazed instance of upCommand component.
 * @return the initialized component instance
 */
public Command getUpCommand() {
    if (upCommand == null) {//GEN-END:|60-getter|0|60-preInit
            // write pre-init user code here
        upCommand = new Command("..", Command.ITEM, 1);//GEN-LINE:|60-getter|1|60-postInit
        // write post-init user code here
    }//GEN-BEGIN:|60-getter|2|
    return upCommand;
}
//</editor-fold>//GEN-END:|60-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: excludeAllCommand ">//GEN-BEGIN:|62-getter|0|62-preInit
/**
 * Returns an initiliazed instance of excludeAllCommand component.
 * @return the initialized component instance
 */
public Command getExcludeAllCommand() {
    if (excludeAllCommand == null) {//GEN-END:|62-getter|0|62-preInit
            // write pre-init user code here
        excludeAllCommand = new Command("Exclude all", Command.ITEM, 0);//GEN-LINE:|62-getter|1|62-postInit
        // write post-init user code here
    }//GEN-BEGIN:|62-getter|2|
    return excludeAllCommand;
}
//</editor-fold>//GEN-END:|62-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: itemCommand ">//GEN-BEGIN:|65-getter|0|65-preInit
/**
 * Returns an initiliazed instance of itemCommand component.
 * @return the initialized component instance
 */
public Command getItemCommand() {
    if (itemCommand == null) {//GEN-END:|65-getter|0|65-preInit
            // write pre-init user code here
        itemCommand = new Command("Item", Command.ITEM, 0);//GEN-LINE:|65-getter|1|65-postInit
            // write post-init user code here
    }//GEN-BEGIN:|65-getter|2|
    return itemCommand;
}
//</editor-fold>//GEN-END:|65-getter|2|
    private QuestionDisplay questionDisplay;
    private KeymappingForm keymappingForm;
    private ColorForm colorForm;
    private Vector includedTests = new Vector();

    /**
     * This method should return an instance of the display.
     */
    /**
     * Returns a display instance.
     * @return the display instance.
     */
    public Display getDisplay() {

        return Display.getDisplay(this);
    // return Display.getDisplay (this);
    }

    /**
     * This method should exit the midlet.
     */
    /**
     * Exits MIDlet.
     */
    public void exitMIDlet() {

        getDisplay().setCurrent(null);
        destroyApp(true);
        notifyDestroyed();
    // switchDisplayable (null, null);
    // destroyApp(true);
    // notifyDestroyed();
    }
//    private static final String[] ELEMENTS=new String[] {
//        "30.txt",
//        "perfect_forms.txt",
//        "ja_L7.txt"};
    private static final String[] ELEMENTS = null;
    private static Browser instance;

    public static Browser getInstance() {
        return instance;
    }

    /** Creates a new instance of ConvertedBrowser */
    public Browser() {

        instance = this;

        Settings.getInstance().load();

        initialize();

    }
//    private BTManager bt;
    public String currDirName = MEGA_ROOT;
    public String currentFile = "";
    /* special string denotes upper directory */
    private final static String UP_DIRECTORY = "../";
    /* special string that denotes apper directory accessible by this browser.
     * this virtual directory contains all roots.
     */
    private final static String MEGA_ROOT = "/";
    /* separator string as defined by FC specification */
    private final static String SEP_STR = "/";
    /* separator character as defined by FC specification */
    private final static char SEP = '/';
    public static final String SANDBOX = "[sandbox]/";
    public static final String EMBED = "/[embed]/";

    public Displayable get_keymappingForm() {
        if (keymappingForm == null) {
            keymappingForm = new KeymappingForm(getDisplay(), getListFileBrowser());
        }

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
        if (questionDisplay == null) {
            questionDisplay = new QuestionDisplay();
        }

        return questionDisplay;
    }

    private void expandDirectory(String fileName, Vector filenames) throws IOException {
        FileConnection currDir = (FileConnection) Connector.open("file://localhost" +
                fileName);

        Enumeration files = currDir.list();
        while (files.hasMoreElements()) {
            String inName = fileName + (String) files.nextElement();
            if (!filenames.contains(inName)) {
                if (inName.endsWith(SEP_STR)) {
                    expandDirectory(inName, filenames);
                } else {
                    filenames.addElement(inName);
                }
            }
        }

    }

    private void expandDirectories(Vector filenames) throws IOException {
        Vector forRemoval = new Vector();
        Enumeration e = filenames.elements();
        while (e.hasMoreElements()) {
            String fileName = (String) e.nextElement();

            if (fileName.endsWith(SEP_STR)) {
                expandDirectory(fileName, filenames);
                forRemoval.addElement(fileName);
            }
        }

        //remove dirs
        e = forRemoval.elements();
        while (e.hasMoreElements()) {
            String fileName = (String) e.nextElement();
            filenames.removeElement(fileName);
        }
    }

    private boolean readFile() {
        try { //todo fix errmsg
            if(currentFile==null) {
                getDisplay().setCurrent(new Alert("currentFile==null"), getListFileBrowser());
                return false;
            }

            if (currentFile.endsWith(SEP_STR)) {
                if (currentFile.equals(UP_DIRECTORY)) {
                    currDirName = currDirName.substring(0, currDirName.length() - 1);
                    currDirName = currDirName.substring(0, currDirName.lastIndexOf(SEP) + 1);
                } else if (currentFile.equals(EMBED)) {
                    currDirName = EMBED;
                } else {
                    currDirName += currentFile;
                }
                showCurrDir();
                return false;
            } else if (currentFile.endsWith(".font")) {

                FontUtilWrapping font = FontUtilWrapping.initialize(openFile());
                if (font != null) {
                    getBook().setBitmapFont(font);
                }

                return false;
            } else {
                if(includedTests==null) {
                    getDisplay().setCurrent(new Alert("includedTests==null"), getListFileBrowser());
                    return false;
                }

                if (includedTests.isEmpty()) {
                    InputStream is=openFile();

                    if(is==null) {
                        getDisplay().setCurrent(new Alert("openFile() returned null"), getListFileBrowser());
                        return false;
                    }



                    BufferedReader reader = new BufferedReader(is);
/*                    try {
                        //String firstLine=reader.readLine();
                        
                        //int firstLine=reader.
                        getDisplay().setCurrent(new Alert("firstByte="+is.read()), getListFileBrowser());
                    } catch(NullPointerException exc) {
                        getDisplay().setCurrent(new Alert("npexc:"+exc.getMessage()), getListFileBrowser());
                    }

                    if(true) return false;*/
                    //getDisplay().setCurrent(new Alert("reader is open"), getListFileBrowser());
//                    if(getBook()==null) {
//                        getDisplay().setCurrent(new Alert("getBook()==null"), getListFileBrowser());
//                        return false;
//                    }
//                    if(reader==null) {
//                        getDisplay().setCurrent(new Alert("reader==null !!!"), getListFileBrowser());
//                        return false;
//                    }

                    getBook().loadFile(reader);
                    //getDisplay().setCurrent(new Alert("loadfile passed"), getListFileBrowser());
                    reader.close();
                    //getDisplay().setCurrent(new Alert("readerclosed passed"), getListFileBrowser());
                } else {
                    String current = currDirName + currentFile;
                    boolean cont = includedTests.contains(current);

                    if (!cont) {
                        includedTests.addElement(current);
                    }

                    //expand directories to files
                    expandDirectories(includedTests);

                    getBook().loadFiles(includedTests, this);
                    if (!cont) {
                        includedTests.removeElement(current);
                    }
                }
                return true;
            }
        } catch (Exception exc) {
            exc.printStackTrace();
//            getBook().showErrorMsg(exc.getMessage());
            getDisplay().setCurrent(new Alert("readfile failed", exc.toString(), null, AlertType.ERROR), getListFileBrowser());
//            getDisplay().setCurrent(new Alert("readfile 2", exc.toString(), null, AlertType.ERROR), getListFileBrowser());
            return false;
        }
    }

    public void initMenu() {
        getTextFieldEncoding().setString(Settings.getInstance().getEncoding());

        getChoiceGroupSettings().setSelectedIndex(0, Settings.getInstance().getSmart()); //smart

        getChoiceGroupSettings().setSelectedIndex(1, Settings.getInstance().getReverse()); //reverse

        getChoiceGroupSettings().setSelectedIndex(2, Settings.getInstance().getShuffle()); //shuffle

        getChoiceGroupSettings().setSelectedIndex(3, Settings.getInstance().getSort()); //sort

        //vybrat spravne mode v menu
        getChoiceGroupTestMode().setSelectedIndex(Settings.getInstance().getMode(), true);
    }


    //<editor-fold defaultstate="collapsed" desc=" heapSort(Vector vector) ">
    private static void heapSort(Vector vector) {
        /* This method performs an in-place heapsort. Starting
         * from the beginning of the array, the array is swapped
         * into a binary max heap.  Then elements are removed
         * from the heap, and added to the front of the sorted
         * section of the array. */

        int len = vector.size();

        /* Insertion onto heap */
        for (int heapsize = 0; heapsize < len; heapsize++) {
            /* Step one in adding an element to the heap in the
             * place that element at the end of the heap array-
             * in this case, the element is already there. */
            int n = heapsize; // the index of the inserted int

            while (n > 0) { // until we reach the root of the heap

                int p = (n - 1) / 2; // the index of the parent of n

                if (((String) vector.elementAt(n)).compareTo(((String) vector.elementAt(p))) > 0) { // child is larger than parent

                    vectorSwap(vector, n, p); // swap child with parent

                    n = p; // check parent

                } else // parent is larger than child
                {
                    break; // all is good in the heap

                }
            }
        }

        /* Removal from heap */
        for (int heapsize = len; heapsize > 0;) {
            vectorSwap(vector, 0, --heapsize); // swap root with the last heap element

            int n = 0; // index of the element being moved down the tree

            while (true) {
                int left = (n * 2) + 1;
                if (left >= heapsize) // node has no left child
                {
                    break; // reached the bottom; heap is heapified

                }
                int right = left + 1;
                if (right >= heapsize) { // node has a left child, but no right child

                    if (((String) vector.elementAt(left)).compareTo(((String) vector.elementAt(n))) > 0) // if left child is greater than node
                    {
                        vectorSwap(vector, left, n); // swap left child with node

                    }
                    break; // heap is heapified

                }
                if (((String) vector.elementAt(left)).compareTo(((String) vector.elementAt(n))) > 0) { // (left > n)

                    if (((String) vector.elementAt(left)).compareTo((String) vector.elementAt(right)) > 0) { // (left > right) & (left > n)

                        vectorSwap(vector, left, n);
                        n = left;
                        continue; // continue recursion on left child

                    } else { // (right > left > n)

                        vectorSwap(vector, right, n);
                        n = right;
                        continue; // continue recursion on right child

                    }
                } else { // (n > left)

                    if (((String) vector.elementAt(right)).compareTo(((String) vector.elementAt(n))) > 0) { // (right > n > left)

                        vectorSwap(vector, right, n);
                        n = right;
                        continue; // continue recursion on right child

                    } else { // (n > left) & (n > right)

                        break; // node is greater than both children, so it's heapified

                    }
                }
            }
        }
    }

    private static void vectorSwap(Vector vector, int a, int b) {
        Object temp = vector.elementAt(a);
        vector.setElementAt(vector.elementAt(b), a);
        vector.setElementAt(temp, b);
    }

    //</editor-fold>
    /**
     * Show file list in the current directory .
     */
    void showCurrDir() {
        Enumeration e;
        FileConnection currDir = null;

        Vector dirs = new Vector();
        Vector filenames = new Vector();

        try {
            getListFileBrowser().setTitle(currDirName);

            getListFileBrowser().deleteAll();

            if (SANDBOX.equals(currDirName) || EMBED.equals(currDirName)) {
                //TODO : e = embedded
                if (EMBED.equals(currDirName)) {
                    // not root - draw UP_DIRECTORY
                    getListFileBrowser().append(UP_DIRECTORY, null);
                }

                if (ELEMENTS != null) {
                    for (int i = 0; i < ELEMENTS.length; i++) {
                        filenames.addElement(ELEMENTS[i]);
                    //getListFileBrowser().append(ELEMENTS[i], null);
                    }
                }
                return;
            }

            if (MEGA_ROOT.equals(currDirName)) {
                e = FileSystemRegistry.listRoots();

                //root - draw special folder
                if (ELEMENTS != null) {
                    getListFileBrowser().append(EMBED, null);
                }
            } else {
                currDir = (FileConnection) Connector.open("file://localhost" +
                        currDirName, Connector.READ);

                if (!currDir.exists()) {
                    currDirName = MEGA_ROOT;
                    showCurrDir();
                    return;
                }


                //try { Thread.sleep(100); } catch (InterruptedException ex) {} //emulator hack

                e = currDir.list(); // BUG : the WTK emulator often dies at this point, unless ^ hack is present
                // (a threading bug?)

                // not root - draw UP_DIRECTORY
                getListFileBrowser().append(UP_DIRECTORY, null);
            }

            while (e.hasMoreElements()) {
                String fileName = (String) e.nextElement();
                if (fileName.charAt(fileName.length() - 1) == SEP) {
                    // This is directory
                    dirs.addElement(fileName);
                } else {
                    // this is regular file
                    filenames.addElement(fileName);
                }
            }



            if (Settings.getInstance().getSort()) {
                heapSort(dirs);
            }
            for (int i = 0; i < dirs.size(); i++) {
                String f = (String) dirs.elementAt(i);

                Image icon = null;
                if (includedTests.contains(currDirName + f)) {
                    icon = getIncludedIcon();
                }

                getListFileBrowser().append((String) dirs.elementAt(i), icon);
            }

            if (Settings.getInstance().getSort()) {
                heapSort(filenames);
            }
            for (int i = 0; i < filenames.size(); i++) {
                String f = (String) filenames.elementAt(i);

                Image icon = null;
                if (includedTests.contains(currDirName + f)) {
                    icon = getIncludedIcon();
                }

                getListFileBrowser().append(f, icon);
            }


//            getListFileBrowser().setSelectCommand(view);
//            getListFileBrowser().addCommand(prop);

//            getListFileBrowser().addCommand(exit);

//            getListFileBrowser().setCommandListener(this);

            if (currDir != null) {
                currDir.close();
            }

//            Display.getDisplay(this).setCurrent(browser);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public InputStream openFile(String path) throws IOException {
        if (ELEMENTS != null && path.startsWith(MEGA_ROOT + EMBED)) {
            return Browser.class.getResourceAsStream(path.substring(MEGA_ROOT.length() + EMBED.length()));
        } else {
//            InputStream input=Connector.openInputStream("file://localhost" + path);
            FileConnection inputConnection = (FileConnection) Connector.open("file://localhost" + path, Connector.READ);
            if(!inputConnection.exists()) {
                getDisplay().setCurrent(new Alert("file doesn't exist"), getListFileBrowser());
                return null;
            }

            InputStream input=inputConnection.openInputStream();

            if(input==null)
                getDisplay().setCurrent(new Alert("open inpustream failed", "file://localhost" + path, null, AlertType.ERROR), getListFileBrowser());

            return input;
        }
    }

    /**
     * Opens selected file.
     */
    public InputStream openFile() throws IOException {
        return openFile(currDirName + currentFile);
    }

    private Displayable get_colorForm() {
        if (colorForm == null) {
            colorForm = new ColorForm(getDisplay(), getListFileBrowser());
        }

        return colorForm;
    }
// HINT - Uncomment for accessing new MIDlet Started/Resumed logic.
// NOTE - Be aware of resolving conflicts of following methods.
//    /**
//     * Called when MIDlet is started.
//     * Checks whether the MIDlet have been already started and initialize/starts or resumes the MIDlet.
//     */
//    public void startApp() {
//        if (midletPaused) {
//            resumeMIDlet ();
//        } else {
//            initialize ();
//            startMIDlet ();
//        }
//        midletPaused = false;
//    }
//
//    /**
//     * Called when MIDlet is paused.
//     */
//    public void pauseApp() {
//        midletPaused = true;
//    }
//
//    /**
//     * Called to signal the MIDlet to terminate.
//     * @param unconditional if true, then the MIDlet has to be unconditionally terminated and all resources has to be released.
//     */
//    public void destroyApp(boolean unconditional) {
//    }
}
