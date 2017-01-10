package userInterface;

import core.Board;
import core.Die;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import javax.swing.*;

/**
 *
 * @author Jason Wheeler
 */
public class BoggleUi 
{
    // constants
    private final static int MAX_INDEX = 3;
    private final static int MIN_INDEX = 0;
    
    // member variables
    private Board board;
    private ArrayList<String> usersWords = new ArrayList<>();
    private ArrayList<String> compWords = new ArrayList<>();
    private JFrame frame;
    private JMenuBar menuBar;
    private JMenu game;
    private JMenuItem exit;
    private JMenuItem newGame;
    
    // Boggle board
    private JPanel bogglePanel;
    private  ArrayList<Die> dice;
    //private ArrayList<JButton> diceButtons = new ArrayList<JButton>();  
    private ArrayList<String> dictionaryWords = new ArrayList<String>();
    private JButton[][] diceButtons;

    // Enter found words
    private JPanel wordsPanel;
    private JScrollPane scrollPane;
    private JTextArea textArea;
    private JLabel timeLabel;
    private JButton shakeDice;
    
    // Enter current word
    private JPanel currentPanel;
    private JLabel currentLabel;
    private JButton currentSubmit;
    
    // player's score
    int score = 0;
    private JLabel scoreLabel;

    //Timer
    private Timer timer;
    private int minutes = 3;
    private int seconds = 0;
    
    // Action Listeners
    JButtonListener jButtonListener;
    ButtonListener buttonListener;
    
	// Constructor
    public BoggleUi(Board inBoard, ArrayList<String> dictionary)
    {
        board = inBoard;
        dictionaryWords = dictionary;
        initComponents();
    }
    
	// initialize the GUI
    private void initComponents()
    {
        // Initialize the JFrame
        frame = new JFrame("Boggle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 700);
        
        // Initialize the JMenuBar and add to the JFrame
        createMenu();
        // Initialize the JPane for the current word
        setupCurrentPanel();
        // Initialize the JPanel for the word entry
        setupWordPanel();
        // Initialize the JPanel for the Boggle dice
        setupBogglePanel();
        // initialize the Timer
        setupTimer();
        
        // Add everything to the JFrame
        frame.setJMenuBar(menuBar);
        frame.add(bogglePanel, BorderLayout.WEST);
        frame.add(wordsPanel, BorderLayout.CENTER);
        frame.add(currentPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
    
    private void createMenu()
    {
        menuBar = new JMenuBar();
        game = new JMenu("Boggle");
        game.setMnemonic('B');
        
        newGame = new JMenuItem("New Game");
        newGame.setMnemonic('N');
        newGame.addActionListener(new ResetGameListener());

        exit = new JMenuItem("Exit");
        exit.setMnemonic('E');
        exit.addActionListener(new ExitListener());
        
        game.add(newGame);    
        game.add(exit);    
        
        menuBar.add(game);
    }
    
    private void setupBogglePanel()
    {   
        bogglePanel = new JPanel(new GridLayout(4, 4));
        bogglePanel.setMinimumSize(new Dimension(400, 400));
        bogglePanel.setPreferredSize(new Dimension(400, 400));
        bogglePanel.setBorder(BorderFactory.createTitledBorder("Boggle Board"));

        dice = board.shakeDice();
       
        Random rnd = new Random(20);
        Collections.shuffle(dice, rnd);
        
        int counter = 0;
        diceButtons = new JButton[4][4];
        
        for(int row = 0; row < 4; row++)
            for(int col = 0; col < 4; col++)
        {
            diceButtons[row][col] = new JButton(dice.get(counter).getLetter());
            //Collections.shuffle(dice); 
            jButtonListener = new JButtonListener();
            buttonListener = new ButtonListener();
            diceButtons[row][col].addActionListener(buttonListener);
            diceButtons[row][col].addActionListener(jButtonListener);
            
            bogglePanel.add(diceButtons[row][col]);
            counter++;
        }
    }

    private void setupWordPanel()
    {
        wordsPanel = new JPanel();
        wordsPanel.setLayout(new BoxLayout(wordsPanel, BoxLayout.Y_AXIS));
        wordsPanel.setBorder(BorderFactory.createTitledBorder("Enter Words Found"));
        
        textArea = new JTextArea();
        textArea.setLineWrap(false);
        scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(180, 330));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        timeLabel = new JLabel("3:00");
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timeLabel.setFont(new Font("Serif", Font.PLAIN, 48));
        timeLabel.setPreferredSize(new Dimension(240, 100));
        timeLabel.setMinimumSize(new Dimension(240, 100));
        timeLabel.setMaximumSize(new Dimension(240, 100));
        timeLabel.setBorder(BorderFactory.createTitledBorder("Time Left"));
        
        shakeDice = new JButton("Shake Dice");
        shakeDice.setPreferredSize(new Dimension(240, 100));
        shakeDice.setMinimumSize(new Dimension(240, 100));
        shakeDice.setMaximumSize(new Dimension(240, 100));
        shakeDice.addActionListener(new ResetGameListener());
        
        wordsPanel.add(scrollPane);
        wordsPanel.add(timeLabel);
        wordsPanel.add(shakeDice);
    }
    
    private void setupCurrentPanel()
    {
        currentPanel = new JPanel();
        currentPanel.setBorder(BorderFactory.createTitledBorder("Current Word"));

        currentLabel = new JLabel();
        currentLabel.setBorder(BorderFactory.createTitledBorder("Current Word"));
        currentLabel.setMinimumSize(new Dimension(300, 50));
        currentLabel.setPreferredSize(new Dimension(300,50));
        currentLabel.setHorizontalAlignment(SwingConstants.LEFT);
        
        currentSubmit = new JButton("Submit Word");
        currentSubmit.setMinimumSize(new Dimension(200, 100));
        currentSubmit.setPreferredSize(new Dimension(200, 50));
        currentSubmit.addActionListener(new SubmitWordListener());
        
        scoreLabel = new JLabel();
        scoreLabel.setBorder(BorderFactory.createTitledBorder("Score"));
        scoreLabel.setMinimumSize(new Dimension(100, 50));
        scoreLabel.setPreferredSize(new Dimension(100,50));
        scoreLabel.setText("0");
        
        currentPanel.add(currentLabel);
        currentPanel.add(currentSubmit);
        currentPanel.add(scoreLabel);
    }

    private void setupTimer()
    {
        timer = new Timer(1000, new TimerListener());
        timer.start();
    }
    
    private void updateTextArea(String data)
    {
        textArea.append(data + "\n");
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    // Listens for when a letter is selected
    private class ButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            //to add first letter to currentLabel
            if(currentSubmit.isEnabled() == false)
            {
                String temp; //temp is letter corresponding to button pressed
                temp = e.getActionCommand(); //gets letter clicked on
                currentLabel.setText(temp);
                //currentLabel is only the first letter pressed
            }
            //when there is text in currentLabel
            else
            {
                String temp2; //temp2 is letter corresponding to button pressed
                temp2 = e.getActionCommand(); //gets letter clicked on
                //adds letter stored in temp2 to currentLabel to make a word
                currentLabel.setText(currentLabel.getText() + temp2);
            }   
        }     
    }
    
	// Listens for when the user wants to submit a selected word
    private class SubmitWordListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            // compare the word to the words in the dictionary
            if(dictionaryWords.contains(currentLabel.getText().toLowerCase()) == true) /*if word is in dictionary*/
            {
                
                if(usersWords.contains(currentLabel.getText().toLowerCase()))
                {
                    updateTextArea("You've already submitted that word!");
                }
                else 
                {  
                    usersWords.add(currentLabel.getText().toLowerCase()); //add the user's word to the arraylist holding all their valid words
                    updateTextArea(currentLabel.getText());
                    getWordScore();
                    scoreLabel.setText(String.valueOf(score));
                }
                
                currentLabel.setText(""); //clears currentWord JLabel 
                
                
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Not a valid word");
                currentLabel.setText("");
            }

            //re-enable all buttons
            int tempRow = -1;
            int tempCol = -1;
            for(int row = 0; row <= MAX_INDEX; row++)
            {
                for(int col = 0; col <= MAX_INDEX; col++)
                {
                    diceButtons[row][col].setEnabled(true);

                    if(e.getSource().equals(diceButtons[row][col]))
                    {
                        tempRow = row;
                        tempCol = col;
                    }
                }   
            }
        }
        
        // The score for each submitted word depends on the word's length
        private void getWordScore()
        {
            int wordLength = currentLabel.getText().length();
            
            switch(wordLength)
            {
                case 0:
                case 1:
                case 2:
                    JOptionPane.showMessageDialog(null, "Word too short");
                    break;
                case 3:
                case 4:
                    score += 1;
                    break;
                case 5:
                    score += 2;
                    break;
                case 6:
                    score += 3;
                    break;
                case 7:
                    score += 5;
                    break;
                default:
                    score += 11;
            }
        }
    }
        
	// Keep track of the timer and update the ending results when time runs out
    private class TimerListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            int newScore = score;
            
            if(seconds == 0)
            {
                seconds = 59;
                minutes--;
            }
            else
            {
                seconds--;
            }
            
            timeLabel.setText(String.valueOf(minutes) + ":" + String.valueOf(seconds));
            
            if((minutes == 0) && (seconds == 0))
            {
                timer.stop();
                currentSubmit.setEnabled(false);
                shakeDice.setEnabled(true);
                for(int r = 0; r <= MAX_INDEX; r++)
                {
                    for(int c = 0; c <= MAX_INDEX; c++)
                    {
                        diceButtons[r][c].setEnabled(false);

                    }
                }
                
                
                //add random words to compWords list
                if(!timer.isRunning())
                {
                   if(!usersWords.isEmpty())
                   {
                        JOptionPane.showMessageDialog(null, "Time's Up! Now comparing your score with the computer's..." + "\n" + "Press OK and check the 'Words Found' Text Area for your results."); 


                        Random rand = new Random();

                        for(int i = 0; i < ((rand.nextInt(usersWords.size())) + 1); i++)
                        {
                            compWords.add(usersWords.get(i));

                        }

                        Collections.shuffle(compWords);

                        updateTextArea("\n");
                        updateTextArea("You found " + usersWords.size() + " words.");
                        updateTextArea("The computer found " + compWords.size() + " words.");
                        updateTextArea("They are listed as follows: ");

                        for(int z = 0; z<(compWords.size()); z++)
                        {
                            updateTextArea(compWords.get(z));
                            updateTextArea("\n");

                            //Calculates new score based on computer's words
                            switch(compWords.get(z).toString().trim().length())
                            {
                                case 0:
                                case 1:
                                case 2:
                                case 3:
                                case 4:
                                    newScore -= 1;
                                    break;
                                case 5:
                                    newScore -= 2;
                                    break;
                                case 6:
                                    newScore -= 3;
                                    break;
                                case 7:
                                    newScore -= 5;
                                    break;
                                default:
                                    newScore -= 11;
                            }    
                        }    
                        // Show results of the game
                        updateTextArea("All of the words found by the computer");
                        updateTextArea("were also words that you found.");
                        updateTextArea("\n");
                        updateTextArea("Therefore, the points for those words");
                        updateTextArea("will be subtracted from your score");
                        updateTextArea("and your final score will be given.");
                        updateTextArea("\n");
                        updateTextArea("Your final score is " + newScore + ".");
                        updateTextArea("Game over." + " Select the new game menu option to start again.");

                   }
                  
                    else
                    {
                        // results of the game if user didn't enter any words
                        updateTextArea("\n\n");
                        updateTextArea("You didn't find any words!");
                        updateTextArea("Your final score is 0.");
                        updateTextArea("The computer didn't find any words either!");
                        updateTextArea("Select the new game menu option to try again.");
                    }
                }    
                
            } 
        }
    }
    
	// Starts a new game when the reset button is pushed
    private class ResetGameListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            //this code resets the bogglePanel with new letters
            bogglePanel.removeAll();
            setupBogglePanel();
            frame.add(bogglePanel, BorderLayout.WEST);
            bogglePanel.revalidate();
            bogglePanel.repaint();
          
            //resets text areas to be ready for a new game
            textArea.setText(""); //Words found panel is cleared
            scoreLabel.setText("0"); //score is reset
            currentLabel.setText(""); //currentLabel is reset
            timeLabel.setText("3:00"); //timer is 'reset'
            seconds = 60;
            minutes = 2;
            shakeDice.setEnabled(false); //shakeDice button is diabled
            
            //restarts timer
            timer.start();
        }
    }
    
	// Whenever a letter is selected, the user may only subsequently select adjacent letters
	// to make a valid word
    private class JButtonListener implements ActionListener
    {
        int tempRow = -1;
        int tempCol = -1;
        
        public void actionPerformed(ActionEvent e)
        {
            //grid
            for(int row = 0; row <= MAX_INDEX; row++)
            {    
                for(int col = 0; col <= MAX_INDEX; col++)
                {
                    //disable
                    diceButtons[row][col].setEnabled(false); 
                    if(e.getSource().equals(diceButtons[row][col]))
                    {
                        tempRow = row;
                        tempCol = col;
                    }
                }
            }
            //for the button to the left    
            if(tempRow - 1 >= MIN_INDEX)
            {
                diceButtons[tempRow - 1][tempCol].setEnabled(true);
                if(tempCol - 1 >= MIN_INDEX)
                {
                    diceButtons[tempRow - 1][tempCol - 1].setEnabled(true);
                }
                if(tempCol + 1 <= MAX_INDEX)
                {
                    diceButtons[tempRow - 1][tempCol + 1].setEnabled(true);
                }
            }
            //for the button to the right
            if(tempRow + 1 <= MAX_INDEX)
            {
                diceButtons[tempRow + 1][tempCol].setEnabled(true);
                if(tempCol - 1 >= MIN_INDEX)
                {
                    diceButtons[tempRow + 1][tempCol - 1].setEnabled(true);
                }
                if(tempCol + 1 <= MAX_INDEX)
                {
                    diceButtons[tempRow+1][tempCol+1].setEnabled(true);
                }
            }
            //for the buttons above
            if(tempCol - 1 >= MIN_INDEX)
            {
                diceButtons[tempRow][tempCol - 1].setEnabled(true);
            }
            if(tempCol + 1 <= MAX_INDEX)
            {
                diceButtons[tempRow][tempCol + 1].setEnabled(true);
            }           
        }
    }
    
    // Listen for when an exit menu item is selected
    private class ExitListener implements ActionListener
    {
        // Exits the application
        @Override
        public void actionPerformed(ActionEvent e)
        {
            int response = JOptionPane.showConfirmDialog(null, "Are you sure you wish to exit?", 
                    "Exit", JOptionPane.YES_NO_OPTION);
            
            if (response == JOptionPane.YES_OPTION)
                System.exit(0);	
        }	
    }
    
    
    
    
    
    
}