package com.geekbrains.rpg.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Random;

public class GameScreen extends AbstractScreen {
    private BitmapFont font32;
    private TextureRegion textureGrass;
    private ProjectilesController projectilesController;
    private Hero hero;
    private Monster monster;
    private Random random;
    private float attackTime;

    public Hero getHero() {
        return hero;
    }

    public ProjectilesController getProjectilesController() {
        return projectilesController;
    }

    public GameScreen(SpriteBatch batch) {
        super(batch);
    }

    @Override
    public void show() {
        this.random = new Random();
        this.projectilesController = new ProjectilesController();
        this.hero = new Hero(this);
        this.monster = new Monster(this);
        this.textureGrass = Assets.getInstance().getAtlas().findRegion("grass");
        this.font32 = Assets.getInstance().getAssetManager().get("fonts/font32.ttf");
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 9; j++) {
                batch.draw(textureGrass, i * 80, j * 80);
            }
        }
        hero.render(batch);
        monster.render(batch);
        projectilesController.render(batch);
        hero.renderGUI(batch, font32);
        batch.end();
    }

    public void update(float dt) {
        hero.update(dt);
        monster.update(dt);

        checkCollisions();
        hpDecreaseAndIncrease(dt);

        projectilesController.update(dt);
    }

    private void hpDecreaseAndIncrease(float dt) {
        if (monster.getPosition().dst(hero.getPosition()) < 30 && hero.getHp() != 0) {
            attackTime += dt;
            if (attackTime > 0.5f) {
                attackTime = 0.0f;
                hero.setHp(hero.getHp() - 1);
            }
            //восстановление здоровья
        } else if (hero.getHp() < hero.getHpMax()) {
            attackTime += dt;
            if (attackTime > 3.0f) {
                attackTime = 0.0f;
                hero.setHp(hero.getHp() + 1);
            }
        }
    }

    public void checkCollisions() {
        for (int i = 0; i < projectilesController.getActiveList().size(); i++) {
            Projectile p = projectilesController.getActiveList().get(i);
            if (p.getPosition().dst(monster.getPosition()) < 24) {
                p.deactivate();
                monster.takeDamage(1);
                if (monster.getHp() == 0) {
                    int x = random.nextInt(500) + 30;
                    int y = random.nextInt(700) + 30;
                    monster.getPosition().set(x, y);
                    monster.setHp(monster.getHpMax());
                    hero.setMoney(hero.getMoney() + 1);
                }
            }
        }
    }
}