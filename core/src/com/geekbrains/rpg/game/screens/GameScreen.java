package com.geekbrains.rpg.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.geekbrains.rpg.game.logic.GameController;
import com.geekbrains.rpg.game.logic.*;
import com.geekbrains.rpg.game.screens.utils.Assets;

public class GameScreen extends AbstractScreen {
    private Stage stage;
    private GameController gc;
    private WorldRenderer worldRenderer;
    private boolean isPause = false;

    public GameScreen(SpriteBatch batch) {
        super(batch);
    }

    @Override
    public void show() {
        gc = new GameController();
        worldRenderer = new WorldRenderer(gc, batch);
        createGui();
    }

    @Override
    public void render(float delta) {
        if (!isPause) {
            update(delta);
            gc.update(delta);
            worldRenderer.render();
        }
        stage.draw();
    }

    public void createGui() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        Skin skin = new Skin();
        skin.addRegions(Assets.getInstance().getAtlas());

        BitmapFont font14 = Assets.getInstance().getAssetManager().get("fonts/font14.ttf");
        TextButton.TextButtonStyle menuBtnStyle = new TextButton.TextButtonStyle(
                skin.getDrawable("smButton"), null, null, font14);

        TextButton btnMenu = new TextButton("Menu", menuBtnStyle);
        btnMenu.setPosition(1150, 650);
        TextButton btnPause = new TextButton("Pause", menuBtnStyle);
        btnPause.setPosition(1150, 600);

        btnMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.getInstance().changeScreen(ScreenManager.ScreenType.MENU);
            }
        });

        btnPause.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!isPause) {
                    isPause = true;
                } else {
                    isPause = false;
                }
            }
        });

        stage.addActor(btnMenu);
        stage.addActor(btnPause);
        skin.dispose();
    }

    public void update(float dt) {
        stage.act(dt);
    }
}