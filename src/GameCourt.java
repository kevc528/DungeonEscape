
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;

import java.util.*;
import java.util.List;

// This class is used to implement game logic and interactions between objects
@SuppressWarnings("serial")
public class GameCourt extends JPanel {
	
	// game state booleans
	private boolean playing = false;
	private boolean won = false;
	private JLabel status;
	
	// game basic numbers
    public static final int COURT_WIDTH = 690;
    public static final int COURT_HEIGHT = 720;
    public static final int PLAYER_VELOCITY = 4;
    public static final int NUMBER_KEYS = 5;
    public static final int NUMBER_OF_GOBLINS = 3;
    public static final int NUMBER_OF_DRAGONS = 3;
    
    // determine time between monster attacks 
    private int lastAttack = 0;
    
    // data structures to keep track of what is displayed on screen
    private List<Path> pathList;
    private List<Wall> wallList;
    private List<Key> keyList;
    private List<Monster> monsterList;
    private Player player;
    
    // start menu
    private Menu menu;
    
    // high score page
    private HighScores hs;
    private String name;
    
    private Help help;
    
    // booleans for which keys are pressed currently
    private boolean LPressed = false;
    private boolean RPressed = false;
    private boolean DPressed = false;
    private boolean UPressed = false;
    
    public static final int INTERVAL = 35;
    
    // enum to keep track of the different modes/screens
    public static enum Screen {
    	START, HELP, SCORE, GAME
    };
    
    private Screen state = Screen.START;
    
    // counts number of ticks, used for the timer
    private static int tickCount = 0;
                    
    public GameCourt(JLabel status) {
    	
    	menu = new Menu();
    	hs = new HighScores();
    	help = new Help();
        
    	setBorder(BorderFactory.createLineBorder(Color.BLACK));
    	
        Timer timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });
        timer.start();
        
        setFocusable(true);
        
        // mouse listener to see what is clicked on the menu/start screen
        addMouseListener(new MouseListener() {
        	public void mousePressed(MouseEvent e) {
	        	if (state == Screen.START) {
	        		
	        		// restart button, used exclusively in GAME mode
		            final JButton restart = new JButton("Restart");
		            restart.addActionListener(new ActionListener() {
		                public void actionPerformed(ActionEvent e) {
		                    reset();
		                }
		            });
		            
		            // main menu button, will be found on all pages but main menu
		            final JButton back = new JButton("Main Menu");
		            back.addActionListener(new ActionListener() {
		                public void actionPerformed(ActionEvent e) {
		                    state = Screen.START;
		                    if (Arrays.asList(Game.status_panel.getComponents()).contains(restart)) 
		                    {
	        					Game.status_panel.remove(restart);
	        				}
		                    Game.status_panel.remove(back);
		                    reset();
	        	            Game.status_panel.revalidate();
	        	            Game.status_panel.repaint();               
		                }
		            });
		            
	        		int x = e.getX();
	        		int y = e.getY();
	        		
	        		int buttonX = COURT_WIDTH/2 - 100;
	        		
	        		// checking click locations for the buttons
	        		if (x >= buttonX && x<= buttonX + 200) {
	        			if (y >= 200 && y <= 250) {
	        				state = Screen.GAME;
	
	
	        	            Game.status_panel.add(restart);
	        	            Game.status_panel.revalidate();
	        	            Game.status_panel.repaint();
	        	            
	        	            Game.status_panel.add(back);
	        	            Game.status_panel.revalidate();
	        	            Game.status_panel.repaint();
	        	            
	        				reset();
	        			}
	        			
	        			if (y >= 350 && y <= 400) {
	        				state = Screen.SCORE;
	        				if (Arrays.asList(Game.status_panel.getComponents()).contains(restart)) 
	        				{
	        					Game.status_panel.remove(restart);
	        				}
	        				
	        	            Game.status_panel.add(back);
	        	            Game.status_panel.revalidate();
	        	            Game.status_panel.repaint();
	        	            
	        				reset();
	        			}
	        			
	        			if (y >= 500 && y <= 550) {
	        				state = Screen.HELP;
	        				if (Arrays.asList(Game.status_panel.getComponents()).contains(restart)) 
	        				{
	        					Game.status_panel.remove(restart);
	        				}
	        				
	        	            Game.status_panel.add(back);
	        	            Game.status_panel.revalidate();
	        	            Game.status_panel.repaint();
	        	            
	        				reset();
	        			}
	        		}
        		}
        	}

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
        });
        
        // key press listeners for GAME mode to controller the player
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
            	if (state == Screen.GAME) {
	                if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
	                    player.setVy(PLAYER_VELOCITY);
	                    DPressed = true;
	                }
	                if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
	                    player.setVy(-PLAYER_VELOCITY);
	                    UPressed = true;
	                }
	                if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
	                    player.setVx(-PLAYER_VELOCITY);
	                    LPressed = true;
	                }
	                if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
	                    player.setVx(PLAYER_VELOCITY);
	                    RPressed = true;
	                }
            	}
            }
            
            // key released keeps track of other keys held down to ensure smooth movement
            public void keyReleased(KeyEvent e) {
            	if (state == Screen.GAME) {
	            	if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
	            		LPressed = false;
	            		if (!RPressed) {
	                        player.setVx(0);
	            		}
	            		else {
	            			player.setVx(PLAYER_VELOCITY);
	            		}
	                } 
	            	if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
	                	RPressed = false;
	            		if (!LPressed) {
	                        player.setVx(0);
	            		}
	            		else {
	            			player.setVx(-PLAYER_VELOCITY);
	            		}
	                } 
	            	if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
	                	DPressed = false;
	            		if (!UPressed) {
	                        player.setVy(0);
	            		}
	            		else {
	            			player.setVy(-PLAYER_VELOCITY);
	            		}
	                } 
	            	if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
	                	UPressed = false;
	            		if (!DPressed) {
	                        player.setVy(0);
	            		}
	            		else {
	            			player.setVy(PLAYER_VELOCITY);
	            		}                
	            	}
            	}
            }
        });
    
    this.status = status;
    	
    }
    
    public void reset() {
    	
    	// in game mode, this will create a new game, setting up the game
    	if (state == Screen.GAME) {
    		pathList = new LinkedList<Path>();
        	wallList = new LinkedList<Wall>();
        	keyList = new LinkedList<Key>();
        	monsterList = new LinkedList<Monster>();
        	player = new Player();
        	won = false;
        	tickCount = 0;
        	lastAttack = 0;
        	
            Maze template = new Maze(COURT_WIDTH/Path.getSize(), COURT_HEIGHT/Path.getSize());
            
            int[][] board = template.getGeneratedBoard();
            
            // replacing the numbers in the 2D array for the maze with walls and paths
            for (int i = 0; i < board.length; i++) {
            	for (int j = 0; j < board.length; j++) {
            		if (board[i][j] == 1) {
                		wallList.add(new Wall(i * 30, j * 30));
            		}
            		else {
                		pathList.add(new Path(i * 30, j * 30));
            		}
            	}
            }
            
        	Random random = new Random();
        	
            for (int i = 0; i < NUMBER_KEYS; i++) {
            	Path pathForKey = pathList.get(random.nextInt(pathList.size()-1));
            	
            	Key keyToAdd = new Key(pathForKey.getPx(), pathForKey.getPy());
            	
            	keyList.add(keyToAdd);
            }
            
            for (int i = 0; i < NUMBER_OF_GOBLINS; i++) {
            	Path pathForG = pathList.get(random.nextInt(pathList.size()-1));
            	
            	Monster goblin = new Goblin(pathForG.getPx()+1, pathForG.getPy()+1);
            	
            	monsterList.add(goblin);
            }
            
            for (int i = 0; i < NUMBER_OF_DRAGONS; i++) {
            	Path pathForG = pathList.get(random.nextInt(pathList.size()-1));
            	            	
            	Monster dragon = new Dragon(pathForG.getPx()+1, pathForG.getPy()+1);
            	
            	monsterList.add(dragon);
            }
            
            playing = true;
            status.setText("Running...");
    	}
    	
        
        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
        
        repaint();
    }
    
    // helper function to get number of keys for determining win or not
    private int getNumberKeys() {
    	if (keyList == null) {
    		return NUMBER_KEYS;
    	}
    	else {
    		return keyList.size();
    	}
    }
    
    // function that will automate movement of a game object throughout the maze, used for monsters
    private void moveInMaze(GameObj o) {
    	boolean intersectionX = false;
    	boolean intersectionY = false;
    	
    	Random random = new Random();
    	
    	for (Wall w : wallList) {

    		if (o.willIntersectX(w) && !intersectionX) {
    			intersectionX = true;
    		}
    		if (o.willIntersectY(w) && !intersectionY) {
    			intersectionY = true;
    		}
    		if (intersectionX && intersectionY) {
    			int rand = random.nextInt(4);
    			
    			if (rand == 0) {
    				o.setVx(2);
    				o.setVy(2);
    			}
    			if (rand == 1) {
    				o.setVx(2);
    				o.setVy(-2);
    			}
    			if (rand == 2) {
    				o.setVx(-2);
    				o.setVy(2);
    			}
    			if (rand == 3) {
    				o.setVx(-2);
    				o.setVy(-2);
    			}
    			
    			break;
    		}
    	}
    	      	        	
    	if (!intersectionX) {
            o.moveX();
    	}
    	
    	if (!intersectionY) {
            o.moveY();
    	}
    }
    

    
    // tick function, performed every interval of time
    void tick() {
    	
    	repaint();
    	
    	// managing the game when in game mode
    	if (state == Screen.GAME) {
	        if (playing) {
	        	
	        	boolean intersectionX = false;
	        	boolean intersectionY = false;
	        	
	        	for (Wall w : wallList) {
	        		if (player.willIntersectX(w) && !intersectionX) {
	        			intersectionX = true;
	        		}
	        		if (player.willIntersectY(w) && !intersectionY) {
	        			intersectionY = true;
	        		}
	        		if (intersectionX && intersectionY) {
	        			break;
	        		}
	        	}
	        	        	        	
	        	if (!intersectionX) {
	                player.moveX();
	        	}
	        	
	        	if (!intersectionY) {
	                player.moveY();
	        	}
	        	
	        	for (Key k : keyList) {
	        		if (player.intersects(k)){
	        			keyList.remove(k);
	        			break;
	        		}
	        	}
	        	
	        	// monsters attack after 5 seconds
	        	if (tickCount > 143) {
		        	for (Monster m : monsterList) {
		        		if (player.intersects(m)) {
		        			if (tickCount > (lastAttack + 60)) {
			        			m.attack(player);
			        			lastAttack = tickCount;
		        			}
		        			break;
		        		}
		        	}
	        	}

	        	
	        	// check for winning
	        	if (keyList != null) {
	        		if(keyList.size() == 0) {
	            		playing = false;
	            		won = true;
	            		status.setText("You Win!");
	            		name = (String) JOptionPane.showInputDialog(null, 
	            				"You Win! Enter your name", "", JOptionPane.INFORMATION_MESSAGE, 
	            				new ImageIcon("files/trophy.png"), null, "");
	            		if (!(name == null)) {
		            		hs.updateScore(Math.round(((tickCount * INTERVAL)/1000.0)*100.0)/100.0, 
		            				name);
	            		}
	        		}
	        		
	        	}
	        	
	        	// check for dying
	        	if (player.getHealth() == 0) {
	        		playing = false;
	        		status.setText("You died!");
	        		JOptionPane.showMessageDialog(null, "You Died", "", 
	        				JOptionPane.INFORMATION_MESSAGE, new ImageIcon("files/skull.png"));
	        	}
	        	
	        	for (Monster m : monsterList) {
	        		moveInMaze(m);
	        	}
	        	
	        	tickCount++;
	        }
	        repaint();
    	}
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // paint menu screen 
        if (state == Screen.START) {
        	menu.draw(g);
        }
        
        if (state == Screen.SCORE) {
        	hs.draw(g);
        }
        
        if (state == Screen.HELP) {
        	help.draw(g);
        }
        
        // paint game
        if (state == Screen.GAME) {
        	 double roundedTime = Math.round(((tickCount * INTERVAL)/1000.0)*100.0)/100.0;
             
             if (!won) {
             	g.fillRect(0, 0, COURT_WIDTH, COURT_HEIGHT);
             	  
                 for (Path p : pathList) {
                 	if (Math.hypot(p.getPx()-player.getPx(), p.getPy()-player.getPy()) < 120) {
                     	p.draw(g);
                 	}
                 }
                 for (Wall w : wallList) {
                 	if (Math.hypot(w.getPx()-player.getPx(), w.getPy()-player.getPy()) < 120) {
                     	w.draw(g);
                 	}
                 }
                 for (Key k : keyList) {
                 	if (Math.hypot(k.getPx()-player.getPx(), k.getPy()-player.getPy()) < 120) {
                     	k.draw(g);
                 	}
                 }
                 
                 if (tickCount > 143) {
	                 for (Monster m : monsterList) {
	                  	if (Math.hypot(m.getPx()-player.getPx(), m.getPy()-player.getPy()) < 120) {
	                      	m.draw(g);
	                  	}
	                  }
                 }
             }
             else {            
                 for (Path p : pathList) {
                 	p.draw(g);
                 }
                 for (Wall w : wallList) {
                 	w.draw(g);
                 }
                 for (Monster m : monsterList) {
                	 m.draw(g);
                 }
             }
             
             if (tickCount > 143 && tickCount < 228) {
                 g.setColor(Color.WHITE);
                 Font f = new Font("Courier New", Font.BOLD, 30);
                 FontMetrics metrics = g.getFontMetrics(f);
                 g.setFont(f);
                 g.drawString("Monsters Released", GameCourt.COURT_WIDTH/2 - 
                		 metrics.stringWidth("Monsters Released")/2, 
                		 200 + metrics.getHeight() - metrics.getHeight()/4);        
             }
             
             player.draw(g);
             g.setColor(Color.WHITE);
             g.fillRect(0, 680, COURT_WIDTH, 40);
             g.setFont(new Font("Courier New", Font.BOLD, 14));
             g.setColor(Color.BLACK);
             g.drawString("You have collected " + (-(getNumberKeys()-NUMBER_KEYS)) + " out of " + 
             NUMBER_KEYS + " keys", COURT_WIDTH-300, COURT_HEIGHT-20);
             g.drawString("Time: " + roundedTime+ " seconds", COURT_WIDTH-500, COURT_HEIGHT-20);
             g.drawString("Health: " + player.getHealth(), COURT_WIDTH-600, COURT_HEIGHT-20);
         }
        }
       
    
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }  
}
