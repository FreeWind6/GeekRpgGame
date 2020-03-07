package com.geekbrains.rpg.game.logic;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.geekbrains.rpg.game.screens.utils.Assets;

public class Witch extends GameCharacter {
    private float attackTime;
    private boolean isActive;
    private float x = MathUtils.random(30, 1280 - 30);
    private float y = MathUtils.random(30, 720 - 30);
    private float time;

    public Witch(GameController gc) {
        super(gc, 15, 120.0f);
        this.texture = Assets.getInstance().getAtlas().findRegion("witch");
        this.changePosition(500.0f, 400.0f);
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
            batch.draw(texture, position.x - 30, position.y - 30, 30, 30, 60, 60, 1, 1, 0);
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
        } else if (!isActive) {
            time += dt;
            if (time > 8.0f) {
                time = 0.0f;
                x = MathUtils.random(30, 1280 - 30);
                y = MathUtils.random(30, 720 - 30);
            }
            super.update(dt);
            dst.set(x, y);
        }
    }
}
