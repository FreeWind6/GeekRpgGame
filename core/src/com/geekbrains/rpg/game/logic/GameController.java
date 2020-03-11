package com.geekbrains.rpg.game.logic;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class GameController {
    private ProjectilesController projectilesController;
    private Map map;
    private Hero hero;
    private DarkKnight darkKnight;
    private Witch witch;
    private Golem golem;
    private Vector2 tmp, tmp2;

    public Hero getHero() {
        return hero;
    }

    public DarkKnight getDarkKnight() {
        return darkKnight;
    }

    public Witch getWitch() {
        return witch;
    }

    public Golem getGolem() {
        return golem;
    }

    public Map getMap() {
        return map;
    }

    public ProjectilesController getProjectilesController() {
        return projectilesController;
    }

    public GameController() {
        this.projectilesController = new ProjectilesController();
        this.hero = new Hero(this);
        this.darkKnight = new DarkKnight(this);
        this.witch = new Witch(this);
        this.golem = new Golem(this);
        this.map = new Map();
        this.tmp = new Vector2(0, 0);
        this.tmp2 = new Vector2(0, 0);
    }

    public void update(float dt) {
        hero.update(dt);
        darkKnight.update(dt);
        witch.update(dt);
        golem.update(dt);

        checkCollisions();
        collideUnits(hero, darkKnight, darkKnight.isActive());
        collideUnits(hero, witch, witch.isActive());
        collideUnits(hero, golem, golem.isActive());
        projectilesController.update(dt);
    }

    public void collideUnits(GameCharacter u1, GameCharacter u2, boolean isActive) {
        if (u1.getArea().overlaps(u2.getArea()) && isActive) {
            tmp.set(u1.getArea().x, u1.getArea().y);
            tmp.sub(u2.getArea().x, u2.getArea().y);
            float halfInterLen = ((u1.getArea().radius + u2.getArea().radius) - tmp.len()) / 2.0f;
            tmp.nor();

            tmp2.set(u1.getPosition()).mulAdd(tmp, halfInterLen);
            if (map.isGroundPassable(tmp2)) {
                u1.changePosition(tmp2);
            }

            tmp2.set(u2.getPosition()).mulAdd(tmp, -halfInterLen);
            if (map.isGroundPassable(tmp2)) {
                u2.changePosition(tmp2);
            }
        }
    }

    public void checkCollisions() {
        for (int i = 0; i < projectilesController.getActiveList().size(); i++) {
            Projectile p = projectilesController.getActiveList().get(i);
            if (!map.isAirPassable(p.getCellX(), p.getCellY())) {
                p.deactivate();
                continue;
            }
            if (p.getPosition().dst(darkKnight.getPosition()) < 24 && darkKnight.isActive()) {
                p.deactivate();
                if (darkKnight.takeDamage(1)) {
                    hero.addCoins(MathUtils.random(1, 10));
                }
            }

            if (p.getPosition().dst(witch.getPosition()) < 24 && witch.isActive()) {
                p.deactivate();
                if (witch.takeDamage(1)) {
                    hero.addCoins(MathUtils.random(1, 10));
                }
            }

            if (p.getPosition().dst(golem.getPosition()) < 24 && golem.isActive()) {
                p.deactivate();
                if (golem.takeDamage(1)) {
                    hero.addCoins(MathUtils.random(1, 10));
                }
            }
        }
    }
}
