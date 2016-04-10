import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;

import java.util.Locale;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * CreatorLogic
 * 
 * @author William Chern
 * @version 1.0
 */
public class CreatorLogic
{
    // final (b/c they never change) integers for the 3 choices in text size
    private final int smallTextSize = 28;
    private final int mediumTextSize = 36;
    private final int largeTextSize = 40;

    // final (b/c they never change) Color object references for the choices in text color
    private final Color whiteColor = Color.WHITE;
    private final Color blueColor = new Color(0, 183, 255);
    private final Color redColor = new Color(255, 48, 48);
    private final Color greenColor = new Color(40, 237, 165);

    // final ImageIcon object references to display thumbnails in combo box
    private final ImageIcon gatsbyIcon = new ImageIcon("gatsby-100.jpg");
    private final ImageIcon vaderIcon = new ImageIcon("vader-100.jpg");
    private final ImageIcon hanIcon = new ImageIcon("han-100.jpg");
    private final ImageIcon obamaIcon = new ImageIcon("obama-100.jpg");
    private final ImageIcon putinIcon = new ImageIcon("putin-100.jpg");
    private final ImageIcon christieIcon = new ImageIcon("christie-100.jpg");
    // final array holding ImageIcon references, used to initialize combo box for selecting image
    private final ImageIcon[] iconList = {gatsbyIcon, vaderIcon, hanIcon, obamaIcon, putinIcon, christieIcon};

    // declare instance field variable object references
    private JComboBox fontComboBox; // combo box for user to select font
    private JComboBox imageComboBox; // combo box for user to select an image

    private String[] fontList; // String array to hold all the names of the available fonts

    private Font currentFont; // Font object reference to hold the reference for the currently selected font, including style and size
    private String currentFontFamily; // String to hold the name of the font currently selected in fontComboBox
    private int currentFontStyle; // int to hold value of styling options; values assigned later from static final variables of Font class
    private int currentFontSize; // int to hold value of the font size, determined by radio buttons

    private JLabel imageDescriptionLabel; // JLabel that provides a brief description of the currently selected image
    private JLabel mainImageLabel; // JLabel in the center of frame that holds the full version of the image
    private JLabel topCaptionLabel; // JLabel above the image that holds top caption text entered by user
    private JLabel bottomCaptionLabel; // JLabel below the image that holds bottom caption text entered by user

    private JTextField topCaptionTextField; // JTextField for user-inputted top caption
    private JTextField bottomCaptionTextField; // JTextField for user-inputted bottom caption

    private JCheckBox boldCheckBox; // JCheckBox for user preference of bold text
    private JCheckBox italicCheckBox; // JCheckBox for user preference of italicized text

    // JRadioButtons for user preference of font size
    private JRadioButton smallTextRadioButton;
    private JRadioButton mediumTextRadioButton;
    private JRadioButton largeTextRadioButton;

    // JRadioButtons for user preference of text color
    private JRadioButton whiteTextRadioButton;
    private JRadioButton blueTextRadioButton;
    private JRadioButton redTextRadioButton;
    private JRadioButton greenTextRadioButton;

    // BufferedImages to hold actual full-size images
    private BufferedImage gatsbyImage;
    private BufferedImage vaderImage;
    private BufferedImage hanImage;
    private BufferedImage obamaImage;
    private BufferedImage putinImage;
    private BufferedImage christieImage;

    public CreatorLogic () {
        fontList = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames(Locale.ENGLISH);
        // invoke getAvailableFontFamilyNames accessor method on GraphicsEnvironment object returned from getLocalGraphicsEnvironment static accessor method
        loadImages(); // uses try-catch statements to initialize new BufferedImage object references

        currentFontFamily = "Arial"; // assigns String "Arial" (as initial font) to String object reference that holds the current font
        currentFontStyle = Font.PLAIN; // assigns value of Font.PLAIN as initial font style
        currentFontSize = mediumTextSize; // by default, use the medium text size
        currentFont = new Font(currentFontFamily, currentFontStyle, currentFontSize); // invokes Font constructor to initialize currentFont object reference

        imageComboBox = new JComboBox<>(iconList); // initialize JComboBox which displays all icons for the user to select

        imageDescriptionLabel = new JLabel("Gatsby"); // invoke JLabel constructor and initialize the JLabel which provides a description of the currently selected image
        mainImageLabel = new JLabel(new ImageIcon(gatsbyImage)); // the main image in the center of window is initialized as JLabel with an icon

        topCaptionLabel = new JLabel("Top Caption"); // initialize JLabel which displays the user-entered Top Caption
        bottomCaptionLabel = new JLabel("Bottom Caption"); // initialize JLabel which displays the user-entered Bottom Caption

        boldCheckBox = new JCheckBox("Bold"); // invoke JCheckBox constructor and initialize JCheckBox which receives user preference for bold font
        italicCheckBox = new JCheckBox("Italic"); // initialize JCheckBox which receives user preference for italic font

        topCaptionTextField = new JTextField("Top Caption"); // invoke JTextField constructor and initialize JTextField which will take user input for the Top Caption
        bottomCaptionTextField = new JTextField("Bottom Caption"); // initialize JTextField which will take user input for the Bottom Caption

        smallTextRadioButton = new JRadioButton(Integer.toString(smallTextSize)); // invoke JRadioButton constructor and initialize JRadioButton that receives user preference for small text
        mediumTextRadioButton = new JRadioButton(Integer.toString(mediumTextSize), true); // initialize JRadioButton that receives user preference for medium text
        largeTextRadioButton = new JRadioButton(Integer.toString(largeTextSize)); // initialize JRadioButton that receives user preference for large text

        whiteTextRadioButton = new JRadioButton("White",true); // initialize JRadioButton that receives user preference for white text
        blueTextRadioButton = new JRadioButton("Blue"); // initialize JRadioButton that receives user preference for blue text
        redTextRadioButton = new JRadioButton("Red"); // initialize JRadioButton that receives user preference for red text
        greenTextRadioButton = new JRadioButton("Green"); // initialize JRadioButton that receives user preference for green text
    }

    public void displayInterface () {
        JFrame fr = new JFrame("Meme Creator — William Chern"); // declare and initialize JFrame, which will create the window to display the interface
        fr.setSize(1065, 465); // invoke setSize mutator method on JFrame object referenced by fr, sets size to 1065p x 465p
        fr.setResizable(false); // invoke setResizable mutator method on JFrame object referenced by fr
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // invoke setDefaultCloseOperation mutator method on JFrame object referenced by fr

        // draw main panel, a BorderLayout controlling all other panels
        JPanel mainPanel = new JPanel(); // declare and initialize JPanel object referenced by mainPanel
        mainPanel.setLayout(new BorderLayout(5,5)); // invoke setLayout mutator method on JPanel referenced by mainPanel, pass in newly constructed BorderLayout as explicit parameter

        // invoke mutator methods that will draw their respective panels
        drawLeftPanel(mainPanel);
        drawCenterPanel(mainPanel);
        drawRightPanel(mainPanel);

        // invoke mutator methods on JFrame object referenced by fr that will add and display the entire mainPanel (after other components added)
        fr.add(mainPanel);
        fr.setVisible(true);
    }

    private void drawLeftPanel (JPanel mainP) {
        JPanel leftPanel = new JPanel(); // declare and initialize new JPanel object referenced by leftPanel
        leftPanel.setLayout(new GridLayout(7,1)); // invoke setLayout mutator method, assign a 7x1 GridLayout to JPanel

        imageComboBox.setPreferredSize(new Dimension(150, 150)); // invoke setPreferredSize mutator method on JComboBox object referenced by imageComboBox
        imageComboBox.setEditable(false); // invoke setEditable mutator method on imageComboBox
        leftPanel.add(imageComboBox); // invoke add mutator method, adds imageComboBox to the JPanel

        imageDescriptionLabel.setHorizontalAlignment(JLabel.CENTER); // invoke setHorizontalAlignment mutator method on JLabel
        leftPanel.add(imageDescriptionLabel); // add JLabel to left panel

        class SelectedImageListener implements ActionListener {
            public void actionPerformed(ActionEvent e) { // implementation of abstract actionPerformed method defined in ActionListener interface
                switch (imageComboBox.getSelectedIndex()) { // switch statement based on the currently selected index
                    case 0:
                    imageDescriptionLabel.setText("Gatsby"); // invoke setText mutator method on JLabel object referenced by imageDescriptionLabel
                    mainImageLabel.setIcon(new ImageIcon(gatsbyImage)); // invoke setIcon mutator method on JLabel object referenced by mainImageLabel, changes the main image front and center to the one selected
                    break;

                    case 1:
                    imageDescriptionLabel.setText("Darth Vader");
                    mainImageLabel.setIcon(new ImageIcon(vaderImage)); // changes main and center image in JLabel to Darth Vader pic
                    break;

                    case 2:
                    imageDescriptionLabel.setText("Han Solo");
                    mainImageLabel.setIcon(new ImageIcon(hanImage)); // changes main and center image in JLabel to Han Solo pic
                    break;

                    case 3:
                    imageDescriptionLabel.setText("Barack Obama");
                    mainImageLabel.setIcon(new ImageIcon(obamaImage)); // changes main and center image in JLabel to Obama pic
                    break;

                    case 4:
                    imageDescriptionLabel.setText("Vladimir Putin");
                    mainImageLabel.setIcon(new ImageIcon(putinImage)); // changes main and center image in JLabel to Putin pic
                    break;

                    case 5:
                    imageDescriptionLabel.setText("Chris Christie");
                    mainImageLabel.setIcon(new ImageIcon(christieImage));  // changes main and center image in JLabel to Christie pic
                    break;

                    default:
                    imageDescriptionLabel.setText("[selected image]");
                    break;
                }
            }
        }

        imageComboBox.addActionListener(new SelectedImageListener()); // invoke addActionListener mutator method on imageComboBox, adds new instance of SelectedImageListener
        leftPanel.setBorder(new TitledBorder(new EtchedBorder(), "Image")); // set border of entire left panel, make it visible (etched), give it title "Image"
        mainP.add(leftPanel, BorderLayout.WEST); // add leftPanel back to the main panel, WEST side of BorderLayout
    }

    public void drawCenterPanel (JPanel mainP) {
        JPanel centerPanel = new JPanel(); // declare and initialize new JPanel object referenced by centerPanel
        centerPanel.setLayout(new FlowLayout()); // set instance of FlowLayout as the layout manager of centerPanel
        centerPanel.setBackground(new Color(20, 20, 20)); // invoke setBackground mutator method on JPanel object referenced by centerPanel

        topCaptionLabel.setFont(currentFont); // invoke setFont mutator method on JLabel object referenced by topCaptionLabel
        topCaptionLabel.setForeground(whiteColor); // invoke setForeground (for text color) mutator method on JLabel object referenced by topCaptionLabel
        topCaptionLabel.setHorizontalAlignment(JLabel.CENTER); // invoke setHorizontalAlignment mutator method on JLabel object referenced by topCaptionLabel
        bottomCaptionLabel.setFont(currentFont);  // invoke setFont mutator method on JLabel object referenced by bottomCaptionLabel
        bottomCaptionLabel.setForeground(whiteColor);  // invoke setForeground (for text color) mutator method on JLabel object referenced by bottomCaptionLabel
        bottomCaptionLabel.setHorizontalAlignment(JLabel.CENTER);  // invoke setHorizontalAlignment mutator method on JLabel object referenced by bottomCaptionLabel

        centerPanel.add(topCaptionLabel); // add JLabel for top caption to centerPanel
        centerPanel.add(mainImageLabel); // add JLabel holding the main image to centerPanel
        centerPanel.add(bottomCaptionLabel); // add JLabel for bottom caption to centerPanel

        mainP.add(centerPanel, BorderLayout.CENTER); // add centerPanel back to the main panel, CENTER of BorderLayout
    }

    private void drawRightPanel (JPanel mainP) {
        JPanel rightPanel = new JPanel(); // declare and initialize new JPanel object referenced by rightPanel
        rightPanel.setLayout(new GridLayout(7,1)); // invoke setLayout mutator method, assign a 7x1 GridLayout to JPanel

        displayCaptionControls(rightPanel); // invoke displayCaptionControls method, implemented below

        mainP.add(rightPanel, BorderLayout.EAST); // add rightPanel back to the main panel, EAST side of BorderLayout
    }

    private void displayCaptionControls (JPanel p) {
        displayFontComboBox(p); // invoke displayFontComboBox method, implemented below

        // add bold and italic check box controls to panel
        p.add(boldCheckBox);
        p.add(italicCheckBox);

        // create separate JPanel and ButtonGroup (mutually exclusive) grouping for radio buttons controlling font size
        JPanel textSizePanel = new JPanel(); // declare and initialize JPanel for holding font size radio buttons
        ButtonGroup textSizeButtonGroup = new ButtonGroup(); // declare and initialize ButtonGroup for font size, makes radio buttons mutually exclusive
        textSizeButtonGroup.add(smallTextRadioButton); // add text size radio buttons to button group
        textSizeButtonGroup.add(mediumTextRadioButton);
        textSizeButtonGroup.add(largeTextRadioButton);
        textSizePanel.setLayout(new GridLayout (1,3)); // set 1x3 GridLayout manager for JPanel holding textSize radio buttons
        textSizePanel.add(smallTextRadioButton);
        textSizePanel.add(mediumTextRadioButton);
        textSizePanel.add(largeTextRadioButton);
        textSizePanel.setBorder(BorderFactory.createTitledBorder("Font Size")); // create border around radio buttons to visually group them (for clarity)
        p.add(textSizePanel);

        // create separate JPanel and ButtonGroup (mutually exclusive) grouping for radio buttons controlling text color
        JPanel textColorPanel = new JPanel(); // declare and initialize JPanel for holding text color radio buttons
        ButtonGroup textColorButtonGroup = new ButtonGroup(); // declare and initialize ButtonGroup for text color, makes radio buttons mutually exclusive
        textColorButtonGroup.add(whiteTextRadioButton); // add text color radio buttons to button group
        textColorButtonGroup.add(blueTextRadioButton);
        textColorButtonGroup.add(redTextRadioButton);
        textColorButtonGroup.add(greenTextRadioButton);
        textColorPanel.setLayout(new GridLayout(2,2)); // set 2x2 GridLayout manager for JPanel holding text color radio buttons
        textColorPanel.add(whiteTextRadioButton);
        textColorPanel.add(blueTextRadioButton);
        textColorPanel.add(redTextRadioButton);
        textColorPanel.add(greenTextRadioButton);
        textColorPanel.setBorder(BorderFactory.createTitledBorder("Color")); // create border around radio buttons to group them visually
        p.add(textColorPanel);

        topCaptionTextField.setHorizontalAlignment(JTextField.CENTER); // invoke setHorizontalAlignment mutator method on topCaptionTextField, center align text in text field
        bottomCaptionTextField.setHorizontalAlignment(JTextField.CENTER);
        p.add(topCaptionTextField); // add caption text fields to panel
        p.add(bottomCaptionTextField);

        p.setBorder(new TitledBorder(new EtchedBorder(), "Text Style")); // set border for entire panel, make it visible (etched), use group name "Text Style"

        addTextStyleEventListeners(); // invokes mutator method (implemented below) that initializes listeners for text style panel
    }

    private void displayFontComboBox (JPanel p) {
        fontComboBox = new JComboBox<>(fontList); // initialize JComboBox containing list of fonts for user to select
        fontComboBox.setEditable(false); // invoke setEditable mutator method on JComboBox object referenced by fontComboBox
        fontComboBox.setSelectedItem(currentFontFamily); // invoke setSelectedItem mutator method on JComboBox object referenced by fontComboBox
        p.add(fontComboBox); // add fontComboBox to JPanel p (explicit parameter)
    }

    private void loadImages () {
        try {
            gatsbyImage = ImageIO.read(new File("gatsby.jpg")); // read gatsby.jpg image file, assigns returned BufferedImage object ref to gatsbyImage
        }
        catch (IOException e) {
            System.out.println("404: Gatsby not found"); // prevents exception by catching it, if the file is not there
        }
        try {
            vaderImage = ImageIO.read(new File("vader.jpg")); // read vader.jpg image file, assigns returned BufferedImage object ref to vaderImage
        }
        catch (IOException e) {
            System.out.println("404: Vader not found");
        }
        try {
            hanImage = ImageIO.read(new File("han.jpg")); // read han.jpg image file, assigns returned BufferedImage obj ref to hanImage
        }
        catch (IOException e) {
            System.out.println("404: Han Solo not found");
        }
        try {
            obamaImage = ImageIO.read(new File("obama.jpg")); // read obama.jpg image file, assigns returned BufferedImage obj ref to obamaImage
        }
        catch (IOException e) {
            System.out.println("404: Obama not found");
        }
        try {
            putinImage = ImageIO.read(new File("putin.jpg")); // read putin.jpg image file, assigns returned BufferedImage obj ref to putinImage
        }
        catch (IOException e) {
            System.out.println("404: Putin not found");
        }
        try {
            christieImage = ImageIO.read(new File("christie.jpg")); // read christie.jpg image file, assigns returned BufferedImage obj ref to christieImage
        }
        catch (IOException e) {
            System.out.println("404: Christie not found");
        }
    }

    public void addTextStyleEventListeners () {
        class FontListener implements ActionListener { // inner class FontListener implements ActionListener interface
            public void actionPerformed (ActionEvent e) { // implementation of actionPerformed method listed in ActionListener interface
                currentFontFamily = (String) fontComboBox.getSelectedItem();
                // gets the currently selected font family in fontComboBox, must be type casted as String b/c getSelectedItem method returns generic Object (the Java cosmic superclass)

                if (boldCheckBox.isSelected() && italicCheckBox.isSelected()) currentFontStyle = Font.BOLD + Font.ITALIC; // if both bold and italic are checked, sum the values of BOLD and ITALIC static vars of Font class
                else if (boldCheckBox.isSelected()) currentFontStyle = Font.BOLD; // only bold is checked, only assign BOLD static value of Font class
                else if (italicCheckBox.isSelected()) currentFontStyle = Font.ITALIC; // only italic is checked, only assign ITALIC static value of Font class
                else currentFontStyle = Font.PLAIN; // if neither are checked, assign PLAIN static int of Font class

                // give currentFontSize a value based on which radio button is currently selected
                if (smallTextRadioButton.isSelected()) currentFontSize = smallTextSize;
                else if (mediumTextRadioButton.isSelected()) currentFontSize = mediumTextSize;
                else currentFontSize = largeTextSize;

                // assign top and bottom captions a text color based on selection of which text color radio button
                if (whiteTextRadioButton.isSelected()) {
                    topCaptionLabel.setForeground(whiteColor); // invoke setForeground mutator method on JLabel object referenced by topCaptionLabel
                    bottomCaptionLabel.setForeground(whiteColor);
                }
                else if (blueTextRadioButton.isSelected()) {
                    topCaptionLabel.setForeground(blueColor);
                    bottomCaptionLabel.setForeground(blueColor);
                }
                else if (redTextRadioButton.isSelected()) {
                    topCaptionLabel.setForeground(redColor);
                    bottomCaptionLabel.setForeground(redColor);
                }
                else {
                    topCaptionLabel.setForeground(greenColor);
                    bottomCaptionLabel.setForeground(greenColor);
                }

                currentFont = new Font(currentFontFamily, currentFontStyle, currentFontSize); // construct new Font obj ref from values determined above, assign that new memory location to existing currentFont
                topCaptionLabel.setFont(currentFont); // invoke setFont mutator method on JLabel object referenced by topCaptionLabel
                bottomCaptionLabel.setFont(currentFont);
            }
        }

        class CaptionTextListener implements KeyListener { // inner class CaptionTextListener implements KeyListener interface
            public void keyPressed (KeyEvent e) { // implementation of keyPressed method listed in KeyListener interface
                if (e.getKeyCode() == KeyEvent.VK_ENTER) { // if the Enter/Return key on keyboard is pressed, update the top and bottom caption labels from text in text field
                    topCaptionLabel.setText(topCaptionTextField.getText()); // invoke setText mutator method on JLabel obj referenced by topCaptionLabel, explicit parameter from invoking getText accessor method on topCaptionTextField
                    bottomCaptionLabel.setText(bottomCaptionTextField.getText());
                }
            }

            public void keyReleased (KeyEvent e) {} // implementation for keyReleased method listed in KeyListener interface
            public void keyTyped (KeyEvent e) {} // implementation for keyTyped method listed in KeyListener interface
        }

        FontListener fL = new FontListener(); // declare and initialize new instance of FontListener class, assign object reference memory location to fL
        // invoke addActionListener mutator method on all the text style controls, adds fL as the ActionListener (implemented by FontListener)
        fontComboBox.addActionListener(fL);
        boldCheckBox.addActionListener(fL);
        italicCheckBox.addActionListener(fL);
        smallTextRadioButton.addActionListener(fL);
        mediumTextRadioButton.addActionListener(fL);
        largeTextRadioButton.addActionListener(fL);
        whiteTextRadioButton.addActionListener(fL);
        blueTextRadioButton.addActionListener(fL);
        redTextRadioButton.addActionListener(fL);
        greenTextRadioButton.addActionListener(fL);

        CaptionTextListener cTL = new CaptionTextListener(); // declare and initialize new instance of CaptionTextListener, assign memory location to cTL
        // invoke addKeyListener mutator method on caption text fields, adds cTL as the KeyListener (implemented by CaptionTextListener)
        topCaptionTextField.addKeyListener(cTL);
        bottomCaptionTextField.addKeyListener(cTL);
    }
}
