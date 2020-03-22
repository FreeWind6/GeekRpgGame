package com.geekbrains.rpg.game.logic;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.geekbrains.rpg.game.logic.utils.ObjectPool;

public class SpecialEffectsHPController extends ObjectPool<SpecialEffectHP> {

    @Override
    protected SpecialEffectHP newObject() {
        return new SpecialEffectHP();
    }

    public void setup(float x, float y, int amount) {
        getActiveElement().setup(x, y, amount);
    }

    public void render(SpriteBatch batch, BitmapFont font) {
        for (int i = 0; i < getActiveList().size(); i++) {
            getActiveList().get(i).render(batch, font);
        }
    }

    public void update(float dt) {
        for (int i = 0; i < getActiveList().size(); i++) {
            getActiveList().get(i).update(dt);
        }
        checkPool();
    }
}
