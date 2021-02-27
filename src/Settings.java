
import java.io.UnsupportedEncodingException;
import java.util.Random;
import javax.microedition.rms.InvalidRecordIDException;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
/*
 * Settings.java
 *
 * Created on 24. kvìten 2007, 18:06
 */

/**
 *
 * @author Stepan
 */
public class Settings {
    
    // Singleton.
    private Settings() {
    }
    private static final Settings INSTANCE = new Settings();

    public static Settings getInstance() {
        return INSTANCE;
    }
    /** Colors. */
//    private int fgColor=0x00EEFF, bgColor=0x005555;
//    private boolean useBitmapFont=false;
    private byte settings = 0;
    private long cursorDelay = 1000;
    private byte mode = MODE_NORMAL;
    
    public static final byte MODE_NORMAL = 0;
    public static final byte MODE_FLASHCARD = 1;
    public static final byte MODE_T9 = 2;
    public static final byte MODE_T9_NOSPACE = 3;
    public static final byte MODE_T9_NOSPECIAL = 4;
    
    private int[] actions = new int[ACTIONS_COUNT];
    public static final byte DEL = 0;
    public static final byte CLEAR = 1;
    public static final byte ENTER = 2;
    public static final byte MENU = 3;
    public static final byte SKIP = 4;
    public static final byte LEFT = 5;
    public static final byte RIGHT = 6;
    public static final byte HELP = 7;
    public static final byte BACKSPACE = 8;
    public static final byte ACTIONS_COUNT = 9;
    
    private int[] colors = new int[]{
        0x00C8EE,
        0x005555,
        0x00EEFF,
        0xAA0000,
        0x00EEFF,
        0x00EEC8,
        0x004444,
        0xEFEFEF,
        0x009090,
        0xFF0000,
        0xEE0000
    };
    public static final byte COLOR_FG = 0;
    public static final byte COLOR_BG = 1;
    public static final byte COLOR_ANSWER = 2;
    public static final byte COLOR_CURSOR = 3;
    public static final byte COLOR_HELP = 4;
    public static final byte COLOR_ACTIVE_MAPPING = 5;
    public static final byte COLOR_ACTIVE_MAPPING_BG = 6;
    public static final byte COLOR_ACTIVE_MAPPING_FRAME = 7;
    public static final byte COLOR_INDICATOR_HELP = 8;
    public static final byte COLOR_INDICATOR_WRONG = 9;
    public static final byte COLOR_INDICATOR_WRONG_2 = 10;
    public static final byte COLORS_COUNT = 11;
    
    private boolean blend=true;
    
    public static final Random RND = new Random();

    public int getKeyMapping(int action) {
        return actions[action];
    }

    public void setKeyMapping(int action, int keyCode) {
        actions[action] = keyCode;
    }

    public int getColor(int index) {
        return colors[index];
    }

    public void setColor(int index, int value) {
        colors[index] = value;
    }

    public byte getMode() {
        return mode;
    }

    public void setMode(byte mode) {
        this.mode = mode;
    }
    private String encoding = DEFAULT_FILE_ENCODING;

    public long getCursorDelay() {
        return cursorDelay;
    }

//    public boolean getUseBitmapFont() {
//        return useBitmapFont;
//    }
    public void setEncoding(String encoding) throws UnsupportedEncodingException {
        if (encoding == null || encoding.equals("")) {
            encoding = DEFAULT_FILE_ENCODING;
        }

        if (this.encoding.equalsIgnoreCase(encoding)) {
            return;
        }

        new String(new byte[0], encoding); //check if encondig is supported

        this.encoding = encoding;
    }

    public String getEncoding() {
        return encoding;
    }

//    public void setUseBitmapFont(boolean useBitmapFont) {
//        this.useBitmapFont = useBitmapFont;
//    }
    public boolean getReverse() {
        return (settings & REVERSE_BITMASK) != 0;
    }

    public boolean getSmart() {
        return (settings & SMART_BITMASK) != 0;
    }

    public boolean getShuffle() {
        return (settings & SHUFFLE_BITMASK) != 0;
    }

    public boolean getSort() {
        return (settings & SORT_BITMASK) != 0;
    }
    
    public void setReverse(boolean reverse) {
        if (reverse) {
            settings |= REVERSE_BITMASK;
        } else {
            settings &= ~REVERSE_BITMASK;
        }
    }

    public void setSmart(boolean smart) {
        if (smart) {
            settings |= SMART_BITMASK;
        } else {
            settings &= ~SMART_BITMASK;
        }
    }

    public void setShuffle(boolean shuffle) {
        if (shuffle) {
            settings |= SHUFFLE_BITMASK;
        } else {
            settings &= ~SHUFFLE_BITMASK;
        }
    }
    
    public void setSort(boolean sort) {
        if (sort) {
            settings |= SORT_BITMASK;
        } else {
            settings &= ~SORT_BITMASK;
        }
    }    
    
    private static final String SETTINGS = "Settings";
    private static final String DEFAULT_FILE_ENCODING = "UTF-8";
    private static final String RMS_ENCODING = DEFAULT_FILE_ENCODING;
    private static final byte SETTINGS_ID = 1;
    private static final byte PATH_ID = 2;
    private static final byte ENCODING_ID = 3;
    private static final byte ACTIONS_ID = 4;
    private static final byte COLORS_ID = 5;
    private static final byte MODE_ID = 6;
    private static final byte SHUFFLE_BITMASK = 1;
    private static final byte SMART_BITMASK = 2;
    private static final byte REVERSE_BITMASK = 4;
    private static final byte SORT_BITMASK = 8;
    

    public void load() {

        RecordStore rs = null;
        try {
            rs = RecordStore.openRecordStore(SETTINGS, true);


            if (rs.getNumRecords() == 0) {
                for (int i = 0; i <= 6; i++) {
                    rs.addRecord(null, 0, 0);
                }
            } else {
                byte[] data = rs.getRecord(SETTINGS_ID);
                if (data != null && data.length != 0) {
                    settings = data[0];

                    if (data.length > 1) {
                        mode = data[1];
                    }
//                    useBitmapFont= data[0]!=0;

                // note we have to convert unsigned byte to int

//                    fgColor=(((int) data[1] & 0xFF) << 16) +
//                            (((int) data[2] & 0xFF) << 8)  +
//                            ((int) data[3] & 0xFF);
//
//                    bgColor=(((int) data[4]& 0xFF) << 16) +
//                            (((int) data[5]& 0xFF) << 8 ) +
//                            ((int) data[6]& 0xFF);
                }

                data = rs.getRecord(PATH_ID);
                if (data != null) {
                    String path = new String(data, RMS_ENCODING);
                    if (!Browser.SANDBOX.equals(path)) {
                        Browser.getInstance().currDirName = path;
                    }
                }

                data = rs.getRecord(ENCODING_ID);
                if (data != null) {
                    try {
                        setEncoding(new String(data, RMS_ENCODING));
                    } catch (UnsupportedEncodingException ex) {
                    //ignore
                    }
                }

                data = rs.getRecord(ACTIONS_ID);
                if (data != null) {
                    try {
                        for (int i = 0; i < data.length; i++) {
                            actions[i] = data[i];
                        }
                    } catch (IndexOutOfBoundsException exc) {
                    //ignore
                    }
                }

                data = rs.getRecord(COLORS_ID);
                if (data != null) {
                    try {
                        for (int i = 0; i < data.length; i += 3) {
                            colors[i / 3] = (((int) data[i] & 0xFF) << 16) +
                                    (((int) data[i + 1] & 0xFF) << 8) +
                                    ((int) data[i + 2] & 0xFF);
                        }
                    } catch (IndexOutOfBoundsException exc) {
                    //ignore
                    }
                }

            }

            rs.closeRecordStore();
        } catch (InvalidRecordIDException exc) {
            try {
                //reset record store
                if (rs != null) {
                    rs.closeRecordStore();
                }
                RecordStore.deleteRecordStore(SETTINGS);
                load();
            } catch (RecordStoreException ex) {
                ex.printStackTrace();
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public void save() {
        try {
            RecordStore rs = RecordStore.openRecordStore(SETTINGS, false);

            byte[] data;
            data = new byte[2];
            data[0] = settings;
            data[1] = mode;
//            data[0]= (byte) (useBitmapFont ? 1 : 0);

//            data[1]= (byte)((fgColor >> 16) & 0xFF);
//            data[2]= (byte)((fgColor >> 8)  & 0xFF);
//            data[3]= (byte) (fgColor & 0xFF);
//
//            data[4]= (byte)((bgColor >> 16) & 0xFF);
//            data[5]= (byte)((bgColor >> 8)  & 0xFF);
//            data[6]= (byte) (bgColor & 0xFF);

            rs.setRecord(SETTINGS_ID, data, 0, data.length);

            data = Browser.getInstance().currDirName.getBytes(RMS_ENCODING);
            rs.setRecord(PATH_ID, data, 0, data.length);

            data = getEncoding().getBytes(RMS_ENCODING);
            rs.setRecord(ENCODING_ID, data, 0, data.length);

            data = new byte[actions.length];
            for (int i = 0; i < actions.length; i++) {
                data[i] = (byte) actions[i];
            }
            rs.setRecord(ACTIONS_ID, data, 0, data.length);

            data = new byte[colors.length * 3];
            for (int i = 0; i < data.length; i += 3) {
                data[i] = (byte) ((colors[i / 3] & 0xFF0000) >> 16);
                data[i + 1] = (byte) ((colors[i / 3] & 0x00FF00) >> 8);
                data[i + 2] = (byte) (colors[i / 3] & 0xFF);
            }

            rs.setRecord(COLORS_ID, data, 0, data.length);

            rs.closeRecordStore();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public boolean getBlend() {
        return blend;
    }
    
    public void setBlend(boolean blend) {
        this.blend=blend;
    }

    public boolean isT9() {
         return mode == Settings.MODE_T9 || mode == MODE_T9_NOSPACE || mode == MODE_T9_NOSPECIAL;
    }
}
