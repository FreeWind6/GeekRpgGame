package com.geekbrains.rpg.game.logic;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.geekbrains.rpg.game.screens.utils.Assets;

public class Golem extends GameCharacter {
    private float attackTime;
    private boolean isActive;
    private float x = MathUtils.random(30, 1280 - 30);
    private float y = MathUtils.random(30, 720 - 30);
    private float time;

    public Golem(GameController gc) {
        super(gc, 40, 25.0f);
        this.texture = Assets.getInstance().getAtlas().findRegion("golem");
        this.changePosition(200.0f, 150.0f);
        this.isActive = true;
    }

    @Override
    public void onDeath() {
        this.hp = this.hpMax;
        this.isActive = false;
    }

    public boolean isActive() {
        return isActive;
    }

    @Override
    public void render(SpriteBatch batch, BitmapFont font) {
        //понимаю что не очень хороший подход, но не понял как сделать через контроллер, надеюсь обьясните на лекции
        if (isActive) {
            batch.draw(texture, position.x - 30, position.y - 30, 30, 30, 60, 60, 1, 1, 0);
            batch.draw(textureHp, position.x - 30, position.y + 30, 60 * ((float) hp / hpMax), 12);
        }
    }

    public void update(float dt) {
        if (position.dst(gc.getHero().getPosition()) < 300 && isActive) {
            super.update(dt);
            dst.set(gc.getHero().getPosition());
            if (this.position.dst(gc.getHero().getPosition()) < 40) {
                attackTime += dt;
                if (attackTime > 0.3f) {
                    attackTime = 0.0f;
                    gc.getHero().takeDamage(3);
                }
            }
        } else if (isActive) {
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
