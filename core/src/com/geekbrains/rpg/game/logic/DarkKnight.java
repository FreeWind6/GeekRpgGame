package com.geekbrains.rpg.game.logic;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.geekbrains.rpg.game.screens.utils.Assets;

public class DarkKnight extends GameCharacter {
    private float attackTime;
    private boolean isActive;

    public DarkKnight(GameController gc) {
        super(gc, 20, 100.0f);
        this.texture = Assets.getInstance().getAtlas().findRegion("knight");
        this.changePosition(800.0f, 300.0f);
        this.isActive = false;
    }

    @Override
    public void onDeath() {
        this.hp = this.hpMax;
        this.isActive = true;
    }

    public boolean isActive() {
        return isActive;
    }

    @Override
    public void render(SpriteBatch batch, BitmapFont font) {
        //понимаю что не очень хороший подход, но не понял как сделать через контроллер, надеюсь обьясните на лекции
        if (!isActive) {
            batch.setColor(0.5f, 0.5f, 0.5f, 0.7f);
            batch.draw(texture, position.x - 30, position.y - 30, 30, 30, 60, 60, 1, 1, 0);
            batch.setColor(1, 1, 1, 1);
            batch.draw(textureHp, position.x - 30, position.y + 30, 60 * ((float) hp / hpMax), 12);
        }
    }

    public void update(float dt) {
        if (position.dst(gc.getHero().getPosition()) < 300 && !isActive) {
            super.update(dt);
            dst.set(gc.getHero().getPosition());
            if (this.position.dst(gc.getHero().getPosition()) < 40) {
                attackTime += dt;
                if (attackTime > 0.3f) {
                    attackTime = 0.0f;
                    gc.getHero().takeDamage(1);
                }
            }
        }
    }
}
