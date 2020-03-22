package com.geekbrains.rpg.game.logic;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.StringBuilder;
import com.geekbrains.rpg.game.logic.utils.MapElement;
import com.geekbrains.rpg.game.logic.utils.Poolable;

public class SpecialEffectHP implements Poolable, MapElement {

    private Vector2 position;
    private boolean active;

    private StringBuilder strBuilder;
    private float lifeStrBuilder;
    private float up;
    private int amount;

    @Override
    public int getCellX() {
        return (int) position.x / Map.CELL_WIDTH;
    }

    @Override
    public int getCellY() {
        return (int) position.y / Map.CELL_HEIGHT;
    }

    @Override
    public float getY() {
        return position.y;
    }

    public Vector2 getPosition() {
        return position;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    public SpecialEffectHP() {
        this.position = new Vector2(0.0f, 0.0f);
        this.active = false;
    }

    public void setup(float x, float y, int amount) {
        this.position.set(x, y);
        this.active = true;
        this.amount = amount;

        strBuilder = new StringBuilder();
        this.up = 0.0f;
        this.lifeStrBuilder = 0.0f;
    }

    public void deactivate() {
        active = false;
    }

    @Override
    public void render(SpriteBatch batch, BitmapFont font) {
        up += 1.0f;
        strBuilder.setLength(0);
        strBuilder.append("-" + amount).append("\n");
        font.draw(batch, strBuilder, position.x - 10, position.y + 60 + up);
    }

    public void update(float dt) {
        lifeStrBuilder += dt;
        if (lifeStrBuilder > 1.0f) {
            lifeStrBuilder = 0.0f;
            active = false;
        }
    }
}
