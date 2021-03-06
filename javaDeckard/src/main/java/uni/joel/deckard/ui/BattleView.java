package uni.joel.deckard.ui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import uni.joel.deckard.logic.Battle;
import uni.joel.deckard.logic.Player;
import uni.joel.deckard.logic.cards.AttackCard;
import uni.joel.deckard.logic.cards.Card;

public class BattleView implements Runnable {

    //Player 1 = upper cards and values.
    //Player 2 = lower ones.
    private JFrame frame;
    private Battle battle;
    Player p1;
    Player p2;
    static final int TURNWAITTIME = 1000;

    JPanel upperStatusScreen;
    JPanel upperCardRow;
    JPanel middleField;
    JPanel lowerCardRow;
    JPanel lowerStatusScreen;

    JLabel player1Name;
    JLabel player1Armor;
    JLabel player1HP;
    JLabel player1Mana;
    JLabel player1ManaProduction;

    JLabel player2Name;
    JLabel player2Armor;
    JLabel player2HP;
    JLabel player2Mana;
    JLabel player2ManaProduction;

    /**
     * Creates a new Battle window for the given Battle.
     *
     * @param battle The battle the window is created for.
     */
    public BattleView(Battle battle) {
        this.frame = new JFrame();
        this.battle = battle;
        p1 = battle.getP1();
        p2 = battle.getP2();
    }

    @Override
    public void run() {
        frame.setPreferredSize(new Dimension(1100, 810));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        createComponents(frame.getContentPane());

        frame.pack();
        frame.setVisible(true);
    }

    private void createComponents(Container container) {
        container.setLayout(new GridLayout(5, 1));
        upperStatusScreen = new JPanel(new GridLayout());
        upperCardRow = new JPanel(new GridLayout());
        middleField = new JPanel(new GridLayout());
        lowerCardRow = new JPanel(new GridLayout());
        lowerStatusScreen = new JPanel(new GridLayout());

        // Create resource info screens for player 1
        player1Name = new JLabel();
        player1Name.setHorizontalAlignment(SwingConstants.CENTER);
        player1Armor = new JLabel();
        player1Armor.setHorizontalAlignment(SwingConstants.CENTER);
        player1HP = new JLabel();
        player1HP.setHorizontalAlignment(SwingConstants.CENTER);
        player1Mana = new JLabel();
        player1Mana.setHorizontalAlignment(SwingConstants.CENTER);
        player1ManaProduction = new JLabel();
        player1ManaProduction.setHorizontalAlignment(SwingConstants.CENTER);

        // Create resource info screens for player 2
        player2Name = new JLabel();
        player2Name.setHorizontalAlignment(SwingConstants.CENTER);
        player2Armor = new JLabel();
        player2Armor.setHorizontalAlignment(SwingConstants.CENTER);
        player2HP = new JLabel();
        player2HP.setHorizontalAlignment(SwingConstants.CENTER);
        player2Mana = new JLabel();
        player2Mana.setHorizontalAlignment(SwingConstants.CENTER);
        player2ManaProduction = new JLabel();
        player2ManaProduction.setHorizontalAlignment(SwingConstants.CENTER);

        // Update the info screens
        updatePlayerStatus(p1);
        updatePlayerStatus(p2);

        // Add player 1 info screens to the upper status screen
        upperStatusScreen.add(player1Name);
        upperStatusScreen.add(player1Armor);
        upperStatusScreen.add(player1HP);
        upperStatusScreen.add(player1Mana);
        upperStatusScreen.add(player1ManaProduction);

        // Add player 2 info screens to the lower status screen
        lowerStatusScreen.add(player2Name);
        lowerStatusScreen.add(player2Armor);
        lowerStatusScreen.add(player2HP);
        lowerStatusScreen.add(player2Mana);
        lowerStatusScreen.add(player2ManaProduction);

        // Add card buttons for player 1
        upperCardRow.add(new JButton());
        upperCardRow.add(new JButton());
        upperCardRow.add(new JButton());
        upperCardRow.add(new JButton());
        upperCardRow.add(new JButton());
        upperCardRow.add(new JButton());
        upperCardRow.add(new JButton());
        upperCardRow.add(new JButton());
        upperCardRow.add(new JButton());
        upperCardRow.add(new JButton());

        // Add the deck button for player 1
        JButton deckButton1 = new JButton("<html><big>DECK</big></html>");
        deckButton1.addActionListener(new DeckListener(p1));
        upperCardRow.add(deckButton1);

        //Add the middleField components, skip turn -button
        JButton skipTurnButton = new JButton("<html><big>Skip turn</big></html>");
        skipTurnButton.addActionListener(new SkipTurnListener());
        middleField.add(skipTurnButton);

        // Add card buttons for player 2
        lowerCardRow.add(new JButton());
        lowerCardRow.add(new JButton());
        lowerCardRow.add(new JButton());
        lowerCardRow.add(new JButton());
        lowerCardRow.add(new JButton());
        lowerCardRow.add(new JButton());
        lowerCardRow.add(new JButton());
        lowerCardRow.add(new JButton());
        lowerCardRow.add(new JButton());
        lowerCardRow.add(new JButton());

        // Add the deck button for player 2
        JButton deckButton2 = new JButton("<html><big>DECK</big></html>");
        deckButton2.addActionListener(new DeckListener(p2));
        lowerCardRow.add(deckButton2);

        // Add all the components to the main container
        container.add(upperStatusScreen);
        container.add(upperCardRow);
        container.add(middleField);
        container.add(lowerCardRow);
        container.add(lowerStatusScreen);

        // Hide the cards of the player that is not starting.
        hidePlayerCards(battle.drawStartingPlayer().getOpponent());

    }

    public void updatePlayerStatus(Player px) {
        if (px == p1) {
            player1Name.setText("<html>Player: <big>" + px.getName() + "</big></html>");
            player1Armor.setText("<html>Armor: <big>" + Integer.toString(px.getArmor()) + "</big>/" + Player.MAXARMOR + "</html>");
            player1HP.setText("<html>Hitpoints: <big>" + Integer.toString(px.getHitpoints()) + "</big>/" + Player.MAXHITPOINTS + "</html>");
            player1Mana.setText("<html>Mana: <big>" + Integer.toString(px.getMana()) + "</big></html>");
            player1ManaProduction.setText("<html>Mana production: <big>" + Integer.toString(px.getManaRecovery()) + "</big><html>");
        }
        if (px == p2) {
            player2Name.setText("<html>Player: <big>" + px.getName() + "</big><html>");
            player2Armor.setText("<html>Armor: <big>" + Integer.toString(px.getArmor()) + "</big>/" + Player.MAXARMOR + "</html>");
            player2HP.setText("<html>Hitpoints: <big>" + Integer.toString(px.getHitpoints()) + "</big>/" + Player.MAXHITPOINTS + "</html>");
            player2Mana.setText("<html>Mana: <big>" + Integer.toString(px.getMana()) + "</big></html>");
            player2ManaProduction.setText("<html>Mana production: <big>" + Integer.toString(px.getManaRecovery()) + "</big></html>");
        }
    }

    public void updatePlayerCards(Player player) {
        initializePlayerCards(player);
        ArrayList<Card> cards = player.getHand().getCards();
        for (int i = 0; i < cards.size(); i++) {
            if (player == p1) {
                JButton cardButton = (JButton) upperCardRow.getComponent(i);
                cardButton.setText(cards.get(i).toString());
                cardButton.addActionListener(new CardListener(player, cards.get(i)));
            } else {
                JButton cardButton = (JButton) lowerCardRow.getComponent(i);
                cardButton.setText(cards.get(i).toString());
                cardButton.addActionListener(new CardListener(player, cards.get(i)));
            }
        }
    }

    public void initializePlayerCards(Player player) {
        if (player == p1) {
            for (int i = 0; i < 10; i++) {
                JButton cardButton = (JButton) upperCardRow.getComponent(i);
                cardButton.setText("empty");
                ActionListener[] actionListeners = cardButton.getActionListeners();
                for (ActionListener actionListener : actionListeners) {
                    cardButton.removeActionListener(actionListener);
                }
            }
        } else {
            for (int i = 0; i < 10; i++) {
                JButton cardButton = (JButton) lowerCardRow.getComponent(i);
                cardButton.setText("empty");
                ActionListener[] actionListeners = cardButton.getActionListeners();
                for (ActionListener actionListener : actionListeners) {
                    cardButton.removeActionListener(actionListener);
                }
            }
        }
    }

    public void updateAll() {
        updatePlayerStatus(p1);
        updatePlayerStatus(p2);
        updatePlayerCards(p1);
        updatePlayerCards(p2);
    }

    /**
     * Hides the cards of the given player from view and also makes the cards of
     * the opponent visible.
     *
     * @param player The player whose cards will be hidden.
     */
    public void hidePlayerCards(Player player) {
        if (player == p1) {
            upperCardRow.setVisible(false);
            try {
                java.lang.Thread.sleep(TURNWAITTIME);
            } catch (Exception e) {
                System.out.println(e);
            }
            lowerCardRow.setVisible(true);
        } else {
            upperCardRow.setVisible(true);
            try {
                java.lang.Thread.sleep(TURNWAITTIME);
            } catch (Exception e) {
                System.out.println(e);
            }
            lowerCardRow.setVisible(false);
        }
    }

    public class DeckListener implements ActionListener {

        private Player player;

        public DeckListener(Player player) {
            this.player = player;
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (player.drawCard()) {
                battle.endTurn();
            }
        }
    }

    public class CardListener implements ActionListener {

        private Player player;
        private Card card;

        public CardListener(Player player, Card card) {
            this.player = player;
            this.card = card;
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (player.useCard(card)) {
                battle.endTurn();
            }
        }
    }

    public class SkipTurnListener implements ActionListener {

        public SkipTurnListener() {

        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            battle.endTurn();
        }
    }

}
