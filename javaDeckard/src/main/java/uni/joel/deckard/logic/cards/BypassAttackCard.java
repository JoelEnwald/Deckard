package uni.joel.deckard.logic.cards;

import uni.joel.deckard.logic.Player;

public class BypassAttackCard extends Card {

    /**
     * A card that directly reduces the opponent's health, bypassing armor.
     */
    public static final int DAMAGE = 9;
    public static final int MANACOST = 9;

    /**
     * Main constructor for BypassAttackCard.
     */
    public BypassAttackCard() {
        super("Rapier", MANACOST, "A rapier used to stab the enemy in between the armor.");
    }

    /**
     * Alternative constructor for BypassAttackCard.
     *
     * @param name name
     * @param manaCost mana
     * @param desc description
     */
    public BypassAttackCard(String name, int manaCost, String desc) {
        super(name, manaCost, desc);
    }

    /**
     * BypassAttackCard reduces the opponent's health directly, without taking
     * into account the armor.
     *
     * @param user The user of the card.
     */
    @Override
    public void invoke(Player user) {
        int opponentHitpoints = user.getOpponent().getHitpoints();
        user.getOpponent().changeHitpointsBy(-DAMAGE);
    }

    @Override
    public String toString() {
        return super.toString() + "<br/>" + "Damage: " + DAMAGE + "</html>";
    }

}
