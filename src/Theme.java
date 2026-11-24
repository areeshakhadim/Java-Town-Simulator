import java.awt.Color;
import javax.swing.UIManager;

/**
 * One place for the app's look: the colour palette.
 * Every screen calls Theme.apply() before it opens, so all windows match.
 */
public class Theme {

    // Colour palette
    public static final Color PRIMARY    = new Color(0x3B5BDB); // blue for buttons and highlights
    public static final Color ACCENT     = new Color(0xE8590C); // orange for the shortest path
    public static final Color BACKGROUND = new Color(0xF8F9FC); // soft off-white
    public static final Color TEXT       = new Color(0x212529); // dark grey text
    public static final Color NODE       = new Color(0xDBE4FF); // light blue for map places

    private static boolean applied = false;

    public static void apply() {
        if (applied) return;
        applied = true;
        try {
            // Colours for Java's built-in "Nimbus" style
            UIManager.put("nimbusBase", PRIMARY);
            UIManager.put("nimbusBlueGrey", new Color(0xADB5BD));
            UIManager.put("control", BACKGROUND);
            UIManager.put("nimbusFocus", PRIMARY);
            UIManager.put("nimbusSelectionBackground", PRIMARY);
            UIManager.put("text", TEXT);

            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            // If anything fails, the app still runs with Java's default look
        }
    }
}
