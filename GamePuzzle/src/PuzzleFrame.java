/**
 * Brian Aguirre
 * CSC 201
 * FINAL PROJECT
 */

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PuzzleFrame extends JFrame {
	
	//DIMENSIONS OF SQUARES AND ALSO WINDOW:
	private static final long serialVersionUID = 1L;
	private static final int DEFAULT_HEIGHT = 600;
	private static final int DEFAULT_WIDTH = 600;
	private static final int SIZE = 4;
	private static final int TOTAL_BUTTONS = 15;
	JPanel[][] panelArray = new JPanel[SIZE][SIZE];
	ArrayList<Integer> neighbours = new ArrayList<Integer>();

	
	//GAME TITLE AND METHODS BROUGHT TO FRONT:
    public PuzzleFrame(){
        this.setTitle("Fifteen puzzle game");
        this.add(getGamePanel());
        this.setSize(DEFAULT_HEIGHT, DEFAULT_WIDTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.neighboursVisible();
        this.processKeys();
        this.winningCombination();
        
    }
    
    //RANDOMIZES THE BOXES AT THE BEGINNING OF THE GAME:
    private ArrayList<JComponent> createRandomizedList() {
    	ArrayList<JComponent> componentList = new ArrayList<JComponent>();
    	for (int i = 0; i < TOTAL_BUTTONS; i++) { // add 15 buttons
    		JButton button = new JButton("" + (i + 1));
    		button.setName("" + i); // used in winningCompination() method
    		componentList.add(button);
    	}
    	
    	componentList.add(new JPanel()); // add empty panel 
    	Collections.shuffle(componentList);
        return componentList;
    }
    
    //CREATED A GRID LAYOUR, WHICH MAKES IT EASIER TO MAKE THE BOXES:
    private JPanel getGamePanel() {
    	JPanel panel = new JPanel();
    	panel.setLayout(new GridLayout(SIZE, SIZE));
    	
    	//ARRAY OF JComponent OBJECTS:
    	ArrayList<JComponent> componentList= createRandomizedList();
    	int index = 0;
    	for (int i = 0; i < SIZE; i++) {
    		panelArray[i] = new JPanel[SIZE];
    		for (int j = 0; j < SIZE; j++) {
    			panelArray[i][j] = new JPanel();
    			panelArray[i][j].setLayout(new GridLayout(1, 1));
    			panelArray[i][j].add(componentList.get(index));
    			index++;
    			panel.add(panelArray[i][j]);
    		}
    	}
		return panel ;
    }
    
    //ALLOWS THE MOVEMENT FROM ONE BOX TO ENOTHER:
    private void doSwap(int x, int y, int x1, int y1) {
        Component temp1, temp2;
        temp1 = panelArray[x][y].getComponent(0);
        temp2 = panelArray[x1][y1].getComponent(0);
        panelArray[x][y].remove(0);
        panelArray[x1][y1].remove(0);
        panelArray[x1][y1].add(temp1);
        panelArray[x][y].add(temp2);
        temp2.requestFocus();
        neighboursVisible();
        this.repaint();
    }
    
    //HANDLES WHICH KEYS CAN BE PRESSED IN ORDER TO MOVE THE BOXES:
    private void handleKeyPress(int keyCode) {
        int emptyIndex = findEmptyIndex();        
        neighboursVisible();
        int x = emptyIndex / SIZE;
        int y = emptyIndex % SIZE;
         
        switch (keyCode) {
        case 39:
            if(y == SIZE - 1) return;
            doSwap(x, y, x, y + 1);
            break;
        case 40:
            if(x == SIZE - 1) return;
            doSwap(x, y, x + 1, y);
            break;
        case 37:
            if(y == 0) return;
            doSwap(x, y, x, y - 1);
            break;
        case 38:
            if(x == 0) return;
            doSwap(x, y, x - 1, y);
            break;
        }
    }
    
    //ALLOWS THE RECOGNITION OF UP, LEFT, DOWN, AND RIGHT ARROWS TO MOVE THE BOX:
    private void processKeys() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(
        	new KeyEventDispatcher()  { 
                public boolean dispatchKeyEvent(KeyEvent e){
                    if(e.getID() == KeyEvent.KEY_PRESSED){
                        handleKeyPress(e.getKeyCode());
                    }
                    return false;
                } 
        });
    }
    
    //ALLOWS THE PLAYER TO ALWAYS BE ABLE TO MOVE THE EMPTY SPOT
    //RECOGNIZES WHICH IS THE EMPTY BOX AND THE SPOTS AROUND IT:
    private int findEmptyIndex() {
    	int emptyIndex = 0;
    	for (int i = 0; i < SIZE; i++) {
    		for (int j = 0; j < SIZE; j++) {
    			if (!(panelArray[i][j].getComponent(0) instanceof JButton)) {
    				emptyIndex = i * SIZE + j;
    			}
    		}
    	}
    	return emptyIndex;
    }
    
    //SEES WHICH BUTTONS IN THE CORRECT PLACES:
    //PRINTS TO CONSOLE
    private boolean winningCombination() {
    	for (int i = 0; i < SIZE * SIZE - 1; i++) {
    		String name = panelArray[i / SIZE][i % SIZE].getComponent(0).getName();
    		if (name != null && name.equals(("" + Integer.toString(i)))) {
    			System.out.println("Buttons are in correct places");
    			return true;
    		}
    	}
    	System.out.println("Buttons are NOT in correct places");
    	return false;
    }
    
    //BASED ON  WHERE THE EMPTY INDEX IS, IT SETS UP THE BOXES AROUND IT:
    private void findNeighbours() {
    	int emptyIndex = findEmptyIndex();
    	
    	if(emptyIndex == 0) {
    		neighbours.add(1);
    		neighbours.add(4);
    	}
    	else if(emptyIndex == 1) {
    		neighbours.add(0);
    		neighbours.add(2);
    		neighbours.add(5);
    	}
    	else if(emptyIndex == 2) {
    		neighbours.add(1);
    		neighbours.add(3);
    		neighbours.add(6);
    	}
    	else if(emptyIndex == 3) {
    		neighbours.add(2);
    		neighbours.add(7);
    	}
    	else if(emptyIndex == 4) {
    		neighbours.add(8);
    		neighbours.add(5);
    		neighbours.add(0);
    	}
    	else if(emptyIndex == 5) {
    		neighbours.add(4);
    		neighbours.add(6);
    		neighbours.add(9);
    		neighbours.add(1);
    	}
    	else if(emptyIndex == 6) {
    		neighbours.add(5);
    		neighbours.add(7);
    		neighbours.add(10);
    		neighbours.add(2);
    	}
    	else if(emptyIndex == 7) {
    		neighbours.add(3);
    		neighbours.add(6);
    		neighbours.add(11);
    	}
    	else if(emptyIndex == 8) {
    		neighbours.add(4);
    		neighbours.add(9);
    		neighbours.add(12);
    	}
    	else if(emptyIndex == 9) {
    		neighbours.add(10);
    		neighbours.add(8);
    		neighbours.add(13);
    		neighbours.add(5);
    	}
    	else if(emptyIndex == 10) {
    		neighbours.add(11);
    		neighbours.add(9);
    		neighbours.add(14);
    		neighbours.add(6);
    	}
    	else if(emptyIndex == 11) {
    		neighbours.add(15);
    		neighbours.add(7);
    		neighbours.add(10);
    	}
    	else if(emptyIndex == 12) {
    		neighbours.add(13);
    		neighbours.add(8);
    	}
    	else if(emptyIndex == 13) {
    		neighbours.add(14);
    		neighbours.add(9);
    		neighbours.add(12);
    	}
    	else if(emptyIndex == 14) {
    		neighbours.add(15);
    		neighbours.add(10);
    		neighbours.add(13);
    	}
    	else {
    		neighbours.add(14);
    		neighbours.add(11);
    	}
    }
    
    //CREATES THE NEIGHBORING BOXES TO VISIBLE ONCE MOVED:
    private void neighboursVisible() {
    	findNeighbours();
    	for (int i = 0; i < SIZE * SIZE ; i++) {
    		panelArray[i / SIZE][i % SIZE].getComponent(0).setEnabled(false);
    	}
    	for (int i = 0; i < neighbours.size(); i++) {
    		panelArray[neighbours.get(i) / SIZE][neighbours.get(i) % SIZE].getComponent(0).setEnabled(true);
    	}
    	neighbours.clear();
    }
    
}
