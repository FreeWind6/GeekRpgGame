package com.geekbrains.rpg.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Apple {
    private TextureRegion textureRegion;
    private Vector2 position;

    public Apple(TextureAtlas atlas) {
        this.textureRegion = atlas.findRegion("apple");
        this.position = new Vector2(0, 0);
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void render(SpriteBatch batch) {
        batch.draw(textureRegion, position.x - 30, position.y - 30, 30, 30, 60, 60, 0.5f, 0.5f, 0);
    }
}


