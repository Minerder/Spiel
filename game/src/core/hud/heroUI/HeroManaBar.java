package core.hud.heroUI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import contrib.components.SkillComponent;

import core.hud.LabelStyleBuilder;
import core.hud.ScreenImage;
import core.hud.ScreenText;
import core.utils.Constants;
import core.utils.Point;

/** This class represents the ManaBar of the Hero */
public class HeroManaBar extends ScreenImage {

    /**
     * Creates a new ManaBar for the Hero
     *
     * @param texturePath the Path to the Texture
     * @param position the Position where the Image should be drawn
     * @param scale Determination of the scale
     */
    public HeroManaBar(String texturePath, Point position, float scale) {
        super(texturePath, position, scale);
    }

    protected void updateManaBar(SkillComponent sc) {
        float mpPercentage = (float) sc.getCurrentMana() / sc.getMaxMana() * 100;
        if (mpPercentage <= 0) {
            this.setTexture("hud/bar_empty.png");
        } else if (mpPercentage <= 20) {
            this.setTexture("hud/manaBar/manaBar_6.png");
        } else if (mpPercentage <= 36) {
            this.setTexture("hud/manaBar/manaBar_5.png");
        } else if (mpPercentage <= 52) {
            this.setTexture("hud/manaBar/manaBar_4.png");
        } else if (mpPercentage <= 68) {
            this.setTexture("hud/manaBar/manaBar_3.png");
        } else if (mpPercentage <= 84) {
            this.setTexture("hud/manaBar/manaBar_2.png");
        } else if (mpPercentage <= 100) {
            this.setTexture("hud/manaBar/manaBar_1.png");
        }
    }

    /**
     * Creates a popup on the screen with how much mana the hero lost or gained
     *
     * @param manaChange the amount of mana to display
     * @param font the font to use
     */
    protected void createManaPopup(int manaChange, BitmapFont font) {
        Color fontColor = manaChange > 0 ? Color.BLUE : Color.RED;
        ScreenText hpPopup =
                new ScreenText(
                        "%+d MP".formatted(manaChange),
                        new Point(Constants.WINDOW_WIDTH - 250, 9),
                        1,
                        new LabelStyleBuilder(font).setFontcolor(fontColor).build());
        hpPopup.addAction(Actions.sequence(Actions.fadeOut(1.7f), Actions.removeActor()));
        HeroUI.getHeroUI().add(hpPopup);
    }
}
