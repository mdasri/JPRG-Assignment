package JPRG;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;
import java.util.Random;

public class GameFrame extends JFrame implements ActionListener {

    private JLabel gameTitle, playerName;
    private JPanel northPanel, centerPanel, southPanel, puzzlePanel1, puzzlePanel2, sp1, sp2, sp3;
    private JButton startGame, history, resetGame, exitGame;
    private JButton oriPicButton;
    public JTextField nameTxt;
    private JTextArea instructions, historytext;
    private ImageIcon oriPic, puzImg[];
    private JButton puzzlePiece[], completePiece[];
    private int a, c, i;
    private int b = -1;
//    private int[] pos1 = {6, 4, 1, 0, 2, 8, 7, 3, 5};
    private int[] pos = {0, 2, 8, 7, 3, 5, 6, 4, 1};
    private Game g;
    private String s;
    private JComboBox jcb;
    
    
    
    public GameFrame() {
        
        g = new Game();
        
        ShufflePuzzle();
        
        //the combobox besides title
        jcb = new JComboBox(new Object[]{"Easy", "Medium", "Difficult"});
        jcb.setForeground(Color.gray);
        jcb.setBackground(Color.white);
        jcb.setSelectedItem("Easy");
        
        
//        jcb.addItemListener(new ItemListener(){
//            public void itemStateChanged(ItemEvent e){
//                for(i = 0; i <puzzlePiece.length; i++){
//                    puzzlePanel1.remove(puzzlePiece[i]);
//                    puzzlePanel1.add(mediumGame());
//                }
//            }
//        });
        
        //history textfield
        historytext = new JTextArea("S/N\tPlayer's Name\t\tNo. Of Steps" + "\n");
        historytext.setEditable(false);
        historytext.setFont(new Font("Comic Sans", Font.BOLD, 12));

        //imageIcon array
        puzImg = new ImageIcon[9];
        for (a = 0; a < puzImg.length; a++) {
            puzImg[a] = new ImageIcon("Pictures/Picture" + (a + 1) + ".jpg");
        }

        //gametitle (north panel)
        gameTitle = new JLabel("My Puzzle Game");
        gameTitle.setForeground(Color.blue);
        gameTitle.setFont(new Font("Comic Sans", Font.BOLD, 18));

        //scrambled puzzle pieces(center panel)
        puzzlePiece = new JButton[9];
        for (i = 0; i < puzzlePiece.length; i++) {
            puzzlePiece[i] = new JButton((puzImg[pos[i]]));
            puzzlePiece[i].addActionListener(this);
            puzzlePiece[i].setEnabled(false);
        }

        //complete puzzle pieces(center panel)
        completePiece = new JButton[9];
        for (i = 0; i < completePiece.length; i++) {
            completePiece[i] = new JButton();
            completePiece[i].addActionListener(this);
            completePiece[i].setEnabled(false);
        }

        //accessibility buttons(south panel ~ west) 
        playerName = new JLabel("Enter player's name:", JLabel.CENTER);
        nameTxt = new JTextField();
        startGame = new JButton("Start Game");
        history = new JButton("Get History");
        resetGame = new JButton("Reset Game");
        exitGame = new JButton("Exit Game");

        //Action listener for buttons
        startGame.addActionListener(this);
        history.addActionListener(this);
        resetGame.addActionListener(this);
        exitGame.addActionListener(this);
        resetGame.setEnabled(false);

        //instructions (south panel ~ center)
        instructions = new JTextArea("Instructions: \n \nEnter your name and "
                + "click 'Start Game' button.");// + jscbVert);
        instructions.setEditable(false);
        instructions.setFont(new Font("Comic Sans", Font.BOLD, 12));
        
        //original picture(south panel ~ east)        
        oriPic = new ImageIcon("Pictures/Penguins3.jpg");
        oriPicButton = new JButton("Original Picture:", oriPic);
        oriPicButton.setHorizontalTextPosition(SwingConstants.LEFT);
        oriPicButton.setVerticalTextPosition(SwingConstants.TOP);

        colorButton();
        addComponents();
    }
        //whole frame layout
//        setLayout(new BorderLayout());

    public void addComponents(){
        
        setLayout(new BorderLayout());
        
        //northpanel (game title)
        northPanel = new JPanel();
        northPanel.add(gameTitle);
        northPanel.add(jcb);

        //center panel (both left and right puzzle pieces)
        puzzlePanel1 = new JPanel(new GridLayout(3, 3));
        puzzlePanel1.setBorder(BorderFactory.createTitledBorder("Puzzle Pieces"));
        for (int x = 0; x < 9; x++) {
            puzzlePanel1.add(puzzlePiece[x]);
        }

        puzzlePanel2 = new JPanel(new GridLayout(3, 3));
        puzzlePanel2.setBorder(BorderFactory.createTitledBorder("Complete the Puzzle"));
        for (int x = 0; x < 9; x++) {
            puzzlePanel2.add(completePiece[x]);
        }

        //adding components to center panel
        centerPanel = new JPanel(new GridLayout(1, 2));
        centerPanel.add(puzzlePanel1);
        centerPanel.add(puzzlePanel2);

        //southpanel (accessibility buttons, instructions, original picture)
        sp1 = new JPanel(new GridLayout(3,2));
        sp1.add(playerName);
        sp1.add(nameTxt);
        sp1.add(startGame);
        sp1.add(history);
        sp1.add(resetGame);
        sp1.add(exitGame);

        sp2 = new JPanel(new BorderLayout());
        sp2.add(instructions);
        
        sp3 = new JPanel(new BorderLayout());
        sp3.add(oriPicButton);

        southPanel = new JPanel(new GridLayout());
        southPanel.setBorder(BorderFactory.createTitledBorder("Puzzle Game"));
        southPanel.add(sp1);
        southPanel.add(sp2);
        southPanel.add(sp3);

        // adding in the whole layout
        add(northPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
    }
    
    public void colorButton(){
        startGame.setForeground(Color.white);
        startGame.setBackground(Color.blue);
        history.setForeground(Color.white);
        history.setBackground(Color.blue);
        resetGame.setForeground(Color.white);
        resetGame.setBackground(Color.blue);
        exitGame.setForeground(Color.white);
        exitGame.setBackground(Color.blue);
    }
    
    public void ShufflePuzzle(){
        Random rnd = new Random();
        for (int i = 0; i < pos.length; i ++){
            pos[i] = i;
        }
        
        for (int i = 0; i < pos.length; i ++) {
            int u = i + rnd.nextInt(pos.length - i ) ;
            int temp = pos[i];
            pos[i]= pos[u];
            pos[u]=temp;
        }
    }  
    
    public void randomPuzzleSound(){
        try 
        {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("File/button-16.wav"));
            Clip clip = AudioSystem.getClip( );
            clip.open(audioInputStream);
            clip.start( );
        }
        catch(Exception e)
        {
            System.out.println("Error with playing sound.");
        }
    }
    
    public void completePuzzleSound(){
        try 
        {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("File/crowdcheer.wav"));
            Clip clip = AudioSystem.getClip( );
            clip.open(audioInputStream);
            clip.start( );
        }
        catch(Exception e)
        {
            System.out.println("Error with playing sound.");
        }
    }
    
    public void wrongMove(){
        try 
        {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("File/Bang.wav"));
            Clip clip = AudioSystem.getClip( );
            clip.open(audioInputStream);
            clip.start( );
        }
        catch(Exception e)
        {
            System.out.println("Error with playing sound.");
        }
    }
    
//    public void keyPressed(KeyEvent e) {
//        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
//            startGame.doClick();
//        }
//    }

    public void actionPerformed(ActionEvent e) {

        for (i = 0; i < puzzlePiece.length; i++) {
            if (e.getSource() == puzzlePiece[i]) {
                b = pos[i];
                c = i;
                instructions.setText("Intructions:\n\n Puzzle Piece " + (c + 1) + " selected, "
                        + "\nplease place into the correct position"
                        + "\n within the 'Complete the Puzzle' panel.");
                randomPuzzleSound();
            }
        }

        for (i = 0; i < completePiece.length; i++) {
            if (e.getSource() == completePiece[i]) {
                //if game not ended do
                if (g.getEndGame() != 9) {
                    if (i == b) {
                        completePiece[i].setIcon(puzImg[i]);
                        puzzlePiece[c].setEnabled(false);
                        instructions.setText("Intructions:\n\n Puzzle Piece " + (c + 1) 
                                + " Done!" + "\nSelect a puzzle piece from the 'Puzzle Pieces' panel.");
                        g.setEndGame();
                        g.setCountSteps();
                        if (g.getEndGame() == 9) {
                            completePuzzleSound();
                            JOptionPane.showMessageDialog(null, "Congratulations! You did it in " + g.getSteps() + " steps.");
                            instructions.setText("Instructions: \n\nCompleted! You did it in " 
                                    + g.getSteps() + " steps.\nClick 'Reset Game' to play again.");
                            g.setName(nameTxt.getText());
                            historytext.append(g.getHistoryMessage(s));
                            g.setSN();
                        }
                    } else {
                        wrongMove();
                        JOptionPane.showMessageDialog(null, "Wrong try again",
                                "You're Wrong!",
                                JOptionPane.ERROR_MESSAGE);
                        g.setCountSteps();
                    }
                } else {}
            }
            //end if
        }

        if (e.getSource() == startGame) {
            if (nameTxt.getText() == null || (nameTxt.getText().trim().length() == 0)) {
                JOptionPane.showMessageDialog(null, "Please enter your name");
            } else {
                for (i = 0; i < 9; i++) {
                    puzzlePiece[i].setEnabled(true);
                    completePiece[i].setEnabled(true);
                }
                startGame.setEnabled(false);
                instructions.setText("Instructions: \n \nSelect a piece of the puzzle from the 'Puzzle Pieces' panel. ");
                resetGame.setEnabled(true);
            }
        }
        

        if (e.getSource() == resetGame) {
            instructions.setText("Instructions: \n \nEnter your name and click 'Start Game' button. ");
            resetGame.setEnabled(false);
            startGame.setEnabled(true);
            nameTxt.setText("");

            for (i = 0; i < 9; i++) {
//                puzzlePiece[i].setEnabled(false);
                completePiece[i].setEnabled(false);
                completePiece[i].setIcon(null);
            }
            g.resetEndGame();
            g.resetSteps();
            ShufflePuzzle();
            for (i = 0; i < puzzlePiece.length; i++){
                    puzzlePiece[i].addActionListener(this);
                    puzzlePiece[i].setEnabled(false);
                    puzzlePiece[i].setIcon(puzImg[pos[i]]);
            }
        }

        if (e.getSource() == history) {
            JOptionPane.showMessageDialog(null, historytext);
        }

        if (e.getSource() == exitGame) {
            this.dispose();
            JOptionPane.showMessageDialog(null, "Thank you for playing!");
        }
    }
}