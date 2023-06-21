package core;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

import static core.utils.logging.LoggerConfig.initBaseLogger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import contrib.components.HealthComponent;
import contrib.components.SkillComponent;
import contrib.components.UpdateComponent;
import contrib.components.XPComponent;
import contrib.configuration.KeyboardConfig;
import contrib.entities.EntityFactory;
import contrib.entities.Ghost;
import contrib.entities.Gravestone;
import contrib.entities.monster.Chort;
import contrib.entities.monster.Imp;
import contrib.entities.monster.Rat;
import contrib.entities.monster.Skeleton;
import contrib.entities.traps.ArrowTrap;
import contrib.entities.traps.SpikeTrap;
import contrib.systems.*;

import core.components.PositionComponent;
import core.configuration.Configuration;
import core.hud.Inventory.InventoryGUI;
import core.hud.UITools;
import core.hud.heroUI.HeroUI;
import core.level.IOnLevelLoader;
import core.level.Tile;
import core.level.elements.ILevel;
import core.level.generator.IGenerator;
import core.level.generator.postGeneration.WallGenerator;
import core.level.generator.randomwalk.RandomWalkGenerator;
import core.level.utils.LevelSize;
import core.systems.DrawSystem;
import core.systems.PlayerSystem;
import core.systems.VelocitySystem;
import core.utils.Constants;
import core.utils.DelayedSet;
import core.utils.DungeonCamera;
import core.utils.Point;
import core.utils.SoundPlayer;
import core.utils.components.MissingComponentException;
import core.utils.components.draw.Painter;
import core.utils.components.draw.TextureHandler;
import core.utils.controller.AbstractController;
import core.utils.controller.SystemController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

/** The heart of the framework. From here all strings are pulled. */
public class Game extends ScreenAdapter implements IOnLevelLoader {
    /** Currently used level-size configuration for generating new level */
    public static LevelSize LEVELSIZE = LevelSize.SMALL;

    /**
     * The batch is necessary to draw ALL the stuff. Every object that uses draw need to know the
     * batch.
     */
    protected SpriteBatch batch;

    /** Contains all Controller of the Dungeon */
    public static List<AbstractController<?>> controller;

    public static DungeonCamera camera;
    /** Draws objects */
    protected Painter painter;

    protected LevelManager levelAPI;
    /** Generates the level */
    protected IGenerator generator;

    private boolean doSetup = true;

    /** A handler for managing asset paths */
    private static TextureHandler handler;

    /** All entities that are currently active in the dungeon */
    private static final DelayedSet<Entity> entities = new DelayedSet<>();

    /** List of all Systems in the ECS */
    public static SystemController systems;

    public static ILevel currentLevel;
    private static Entity hero;
    private Logger gameLogger;
    private static int depth;
    private static Sound backgroundMusic;
    private DebuggerSystem debugger;
    private static Game game;

    /**
     * Create a new Game instance if no instance currently exist.
     *
     * @return the (new) Game instance
     */
    public static Game newGame() {
        if (game == null) game = new Game();
        return game;
    }

    // for singleton
    private Game() {}

    /**
     * Main game loop. Redraws the dungeon and calls the own implementation (beginFrame, endFrame
     * and onLevelLoad).
     *
     * @param delta Time since last loop.
     */
    @Override
    public void render(float delta) {
        if (doSetup) setup();
        batch.setProjectionMatrix(camera.combined);
        frame();
        clearScreen();
        levelAPI.update();
        controller.forEach(AbstractController::update);
        camera.update();
    }

    /** Called once at the beginning of the game. */
    protected void setup() {
        doSetup = false;
        /*
         * THIS EXCEPTION HANDLING IS A TEMPORARY WORKAROUND !
         *
         * <p>The TextureHandler can throw an exception when it is first created. This exception
         * (IOException) must be handled somewhere. Normally we want to pass exceptions to the method
         * caller. This approach is (atm) not possible in the libgdx render method because Java does
         * not allow extending method signatures derived from a class. We should try to make clean
         * code out of this workaround later.
         *
         * <p>Please see also discussions at:<br>
         * - https://github.com/Programmiermethoden/Dungeon/pull/560<br>
         * - https://github.com/Programmiermethoden/Dungeon/issues/587<br>
         */
        try {
            handler = TextureHandler.getInstance();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        controller = new ArrayList<>();
        batch = new SpriteBatch();
        setupCameras();
        painter = new Painter(batch, camera);
        generator = new RandomWalkGenerator();
        levelAPI = new LevelManager(batch, painter, generator, this);
        initBaseLogger();
        gameLogger = Logger.getLogger(this.getClass().getName());
        systems = new SystemController();
        controller.add(systems);
        hero = EntityFactory.getHero();
        levelAPI =
                new LevelManager(
                        batch, painter, new WallGenerator(new RandomWalkGenerator()), this);
        levelAPI.loadLevel(LEVELSIZE);
        controller.add(HeroUI.getHeroUI());
        createSystems();
        controller.add(InventoryGUI.getInstance());
    }

    /** Called at the beginning of each frame. Before the controllers call <code>update</code>. */
    protected void frame() {
        setCameraFocus();
        entities.update();
        updateUpdateComponents();
        getHero().ifPresent(this::loadNextLevelIfEntityIsOnEndTile);
        HeroUI.getHeroUI().update();

        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            pause();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            // Dialogue for quiz questions (display of quiz questions and the answer area in test
            // mode)
            // DummyQuizQuestionList.getRandomQuestion().askQuizQuestionWithUI();
            HealthComponent hc =
                    (HealthComponent) hero.getComponent(HealthComponent.class).orElseThrow();
            SkillComponent sc =
                    (SkillComponent) hero.getComponent(SkillComponent.class).orElseThrow();
            XPComponent xpc = (XPComponent) hero.getComponent(XPComponent.class).orElseThrow();
            java.lang.System.out.println(
                    "Current health: "
                            + hc.getCurrentHealthpoints()
                            + ", Max health: "
                            + hc.getMaximalHealthpoints());
            java.lang.System.out.println(
                    "Current mana: " + sc.getCurrentMana() + ", Max mana: " + sc.getMaxMana());
            java.lang.System.out.println("Current level: " + xpc.getCurrentLevel());
            java.lang.System.out.println(
                    "Current xp: "
                            + xpc.getCurrentXP()
                            + ", XP needed for level up: "
                            + xpc.getXPToNextLevel());
        }
        if (Gdx.input.isKeyJustPressed(KeyboardConfig.DEBUG_TOGGLE_KEY.get())) {
            debugger.toggleRun();
            gameLogger.info("Debugger ist now " + debugger.isRunning());
        }
    }

    @Override
    public void onLevelLoad() {
        depth++;
        playSound();
        currentLevel = levelAPI.getCurrentLevel();
        entities.clear();
        getHero().ifPresent(this::placeOnLevelStart);
        spawnMonsters();
        entities.update();
        HeroUI.getHeroUI().createEnemyHealthBars();
        if (depth > 1) SoundPlayer.play("sounds/ladder/climb.mp3");
    }

    private void spawnMonsters() {
        // 16% chance to spawn
        if (Math.random() <= 0.16) {
            Ghost.getInstance().setNewPosition();
            entities.add(Ghost.getInstance());
            Gravestone.getInstance().setNewPosition();
            entities.add(Gravestone.getInstance());
        }

        for (int i = 0; i < (int) (Math.random() * 2 + 1); i++) {
            new Rat();
        }

        new SpikeTrap();
        new ArrowTrap();
        EntityFactory.getChest();

        if (depth >= 6) {
            for (int i = 0; i < (int) (Math.random() * 2 + 1); i++) {
                new Skeleton();
            }
            for (int i = 0; i < (int) (Math.random() * 2 + 1); i++) {
                new Imp();
            }
            if (depth >= 10) {
                for (int i = 0; i < (int) (Math.random() * 2 + 1); i++) {
                    new Imp();
                }
                for (int i = 0; i < (int) (Math.random() * 4 + 1); i++) {
                    new Chort();
                }
            }
        }
    }

    /** Updates all IUpdateFunctions in all UpdateComponents */
    private void updateUpdateComponents() {
        Game.entities.getSet().stream()
                .filter(en -> en.getComponent(UpdateComponent.class).isPresent())
                .forEach(
                        en ->
                                ((UpdateComponent)
                                                en.getComponent(UpdateComponent.class)
                                                        .orElseThrow())
                                        .update(en));
    }

    private void setCameraFocus() {
        if (getHero().isPresent()) {
            PositionComponent pc =
                    (PositionComponent)
                            getHero()
                                    .get()
                                    .getComponent(PositionComponent.class)
                                    .orElseThrow(
                                            () ->
                                                    new MissingComponentException(
                                                            "PositionComponent"));
            camera.setFocusPoint(pc.getPosition());

        } else camera.setFocusPoint(new Point(0, 0));
    }

    private void loadNextLevelIfEntityIsOnEndTile(Entity hero) {
        if (isOnEndTile(hero)) levelAPI.loadLevel(LEVELSIZE);
    }

    private boolean isOnEndTile(Entity entity) {
        PositionComponent pc =
                (PositionComponent)
                        entity.getComponent(PositionComponent.class)
                                .orElseThrow(
                                        () -> new MissingComponentException("PositionComponent"));
        Tile currentTile = currentLevel.getTileAt(pc.getPosition().toCoordinate());
        return currentTile.equals(currentLevel.getEndTile());
    }

    private void placeOnLevelStart(Entity hero) {
        entities.add(hero);
        PositionComponent pc =
                (PositionComponent)
                        hero.getComponent(PositionComponent.class)
                                .orElseThrow(
                                        () -> new MissingComponentException("PositionComponent"));
        pc.setPosition(currentLevel.getStartTile().getCoordinate().toPoint());
    }

    public void pause() {
        // Text Dialogue (output of information texts)
        UITools.showInfoText(Constants.DEFAULT_MESSAGE);
    }

    public static TextureHandler getHandler() {
        return handler;
    }

    /**
     * Given entity will be added to the game in the next frame
     *
     * @param entity will be added to the game next frame
     */
    public static void addEntity(Entity entity) {
        entities.add(entity);
    }

    /**
     * Given entity will be removed from the game in the next frame
     *
     * @param entity will be removed from the game next frame
     */
    public static void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    /**
     * @return Copy of the Set with all entities currently in game
     */
    public static Set<Entity> getEntities() {
        return entities.getSet();
    }

    /**
     * @return The {@link DelayedSet} to manage all the entities in the ecs
     */
    public static DelayedSet getDelayedEntitySet() {
        return entities;
    }

    /**
     * @return the player character, can be null if not initialized
     */
    public static Optional<Entity> getHero() {
        return Optional.ofNullable(hero);
    }

    /**
     * set the reference of the playable character careful: old hero will not be removed from the
     * game
     *
     * @param hero new reference of hero
     */
    public static void setHero(Entity hero) {
        Game.hero = hero;
    }

    /** Reset depth to 0 */
    public static void resetDepth() {
        depth = 0;
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }

    private void setupCameras() {
        camera = new DungeonCamera(null, Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        camera.zoom = Constants.DEFAULT_ZOOM_FACTOR;

        // See also:
        // https://stackoverflow.com/questions/52011592/libgdx-set-ortho-camera
    }

    private void createSystems() {
        new VelocitySystem();
        new DrawSystem(painter);
        new PlayerSystem();
        new AISystem();
        new CollisionSystem();
        new HealthSystem();
        new XPSystem();
        new SkillSystem();
        new ProjectileSystem();
        debugger = new DebuggerSystem();
    }

    /**
     * Load the configuration from the given path. If the configuration has already been loaded, the
     * cached version will be used.
     *
     * @param pathAsString Path to the config-file as String
     * @param klass Class where the ConfigKey field are located.
     * @throws IOException If the file could not be read
     */
    public static void loadConfig(String pathAsString, Class klass) throws IOException {
        Configuration.loadAndGetConfiguration(pathAsString, klass);
    }

    /** Starts the dungeon and needs a {@link Game}. */
    public static void run() {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowSizeLimits(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT, 9999, 9999);
        // The third and fourth parameters ("maxWidth" and "maxHeight") affect the resizing
        // behavior
        // of the window. If the window is enlarged or maximized, then it can assume these
        // dimensions at maximum. If you have a larger screen resolution than 9999x9999 pixels,
        // increase these parameters.
        config.setForegroundFPS(Constants.FRAME_RATE);
        config.setTitle(Constants.WINDOW_TITLE);
        config.setWindowIcon(Constants.LOGO_PATH);
        // config.disableAudio(true);
        // uncomment this if you wish no audio
        new Lwjgl3Application(
                new com.badlogic.gdx.Game() {
                    @Override
                    public void create() {
                        setScreen(Game.newGame());
                    }
                },
                config);
    }

    private static void playSound() {
        if (depth == 1) {
            if (backgroundMusic != null) backgroundMusic.stop();
            backgroundMusic = SoundPlayer.loop("sounds/music/depth_1.mp3");
        }
        if (depth == 5) {
            if (backgroundMusic != null) backgroundMusic.stop();
            backgroundMusic = SoundPlayer.loop("sounds/music/depth_2.mp3");
        }
        if (depth == 10) {
            if (backgroundMusic != null) backgroundMusic.stop();
            backgroundMusic = SoundPlayer.loop("sounds/music/depth_3.mp3");
        }
        if (depth == 15) {
            if (backgroundMusic != null) backgroundMusic.stop();
            backgroundMusic = SoundPlayer.loop("sounds/music/depth_4.mp3");
        }
    }
}
