package src;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.Brick;
import src.gameobjects.Heart;
import src.brick_strategies.BrickFactory;
import java.util.Random;
import java.awt.event.KeyEvent;


public class BrickerGameManager extends GameManager {
    private static final int BORDER_WIDTH = 10;
    private static final int PADDLE_HEIGHT = 20;
    private static final int PADDLE_WIDTH = 150;
    private static final int BALL_RADIUS = 35;
    private static final float BALL_SPEED = 250;

    private static final int ROW = 8;
    private static final int COL = 7;
    private static final int SPACE_FROM_BRICK = 1;
    private static final int SPACE_FROM_WALL = 15;
    private static final int HEIGHT_OF_BRICK = 15;
    private  int LIFE = 3;

    private float width_of_brick;

    private Heart[] hearts;
    private src.gameobjects.Ball ball;
    private Vector2 windowDimensions;
    private WindowController windowController;
    private Counter bricksCounter;
    private Counter lifeCounter;
    private SoundReader soundReader;
    private ImageReader imageReader;
    private GameObject numericLifeCounter;
    private src.gameobjects.GraphicLifeCounter[] heartsArr;
    private UserInputListener inputListener;
    private BrickFactory brickFactory;
    private boolean mockPaddleExist;

    private int numOflife;

    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
    }

    @Override
    public void initializeGame(ImageReader imageReader,
                               SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {
        this.windowController = windowController;
        this.soundReader = soundReader;
        this.imageReader = imageReader;
        this.inputListener = inputListener;

        // initialization
        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        this.hearts = new Heart[10];
        this.bricksCounter = new Counter(0);
        this.lifeCounter = new Counter(LIFE);
        this.numOflife = lifeCounter.value();
        this.mockPaddleExist = false;


        windowController.setTargetFramerate(80);
        windowDimensions = windowController.getWindowDimensions();
        this.brickFactory = new BrickFactory(gameObjects(), this, this.imageReader, this.soundReader,
                this.inputListener, this.windowController, this.windowDimensions, this.hearts, mockPaddleExist,
                this.lifeCounter);

        // create  ball
        createBall(imageReader, soundReader, windowController);

        // create background
        Renderable backgroundImage = imageReader.readImage("assets/DARK_BG2_small.jpeg", false);
        createBackground(backgroundImage);

        // create paddles
        windowDimensions = windowController.getWindowDimensions();

        // create grafic life
        createHearts();
        // create numeric life
        createNumericLifeCounter();



        Renderable paddleImage = imageReader.readImage(
                "assets/paddle.png", false);
        createPaddle(paddleImage, inputListener ,windowDimensions);
        createBorders(imageReader, windowDimensions);

        // create bricks
        Renderable brickImage = imageReader.readImage("assets/brick.png", false);

        createBricks(brickImage);
    }

    /**
     *  createBackground
     * @param backgroundInage
     */
    private void createBackground(Renderable backgroundInage){
        GameObject backGround = new GameObject(Vector2.ZERO, windowController.getWindowDimensions(), backgroundInage);
        gameObjects().addGameObject(backGround, Layer.BACKGROUND);

    }

    /**
     * createHearts
     * @param heartImage
     */
    private void createHearts(){
        Renderable heartImage = imageReader.readImage("assets/heart.png", false);
        this.heartsArr = new src.gameobjects.GraphicLifeCounter[this.lifeCounter.value()];
        for (int i = 0; i < lifeCounter.value(); i++) {
            src.gameobjects.GraphicLifeCounter graphicLifeCounter = new src.gameobjects.GraphicLifeCounter
                    (new Vector2(662, windowDimensions.y()/2+i*25),
                            new Vector2(20, 20), lifeCounter,
                            heartImage, gameObjects(), lifeCounter.value());
            heartsArr[i] = graphicLifeCounter;
            gameObjects().addGameObject(graphicLifeCounter, Layer.BACKGROUND);
        }
    }

    /**
     * createNumericCounter
     */
    private void createNumericLifeCounter(){
        GameObject numeric = new src.gameobjects.NumericLifeCounter(lifeCounter,
                new Vector2(15, windowDimensions.y() / 2),
                new Vector2(15,15),
                gameObjects());
        numericLifeCounter = numeric;
        gameObjects().addGameObject(numeric, Layer.BACKGROUND);
    }

    /**
     * createBorders
     * @param imageReader
     * @param windowDimensions
     */
    private void createBorders(ImageReader imageReader, Vector2 windowDimensions) {
        Renderable borderImage = imageReader.readImage(
                "assets/brick.png", false);
        gameObjects().addGameObject(new GameObject(Vector2.ZERO,
                        new Vector2(BORDER_WIDTH, windowDimensions.y()), borderImage));
        gameObjects().addGameObject(new GameObject(
                    new Vector2(windowDimensions.x()-BORDER_WIDTH, 0),
                    new Vector2(BORDER_WIDTH, windowDimensions.y()), borderImage));
        gameObjects().addGameObject(new GameObject(Vector2.ZERO,
                new Vector2(windowDimensions.x(), BORDER_WIDTH), borderImage));
    }

    /**
     * create bricks
     * @param brickImage
     */
    private void createBricks(Renderable brickImage) {
        this.width_of_brick = (windowDimensions.x() - (BORDER_WIDTH * 2 + (2 * SPACE_FROM_WALL) +
                (SPACE_FROM_BRICK * ROW)))/ COL;
        float rowLocation = BORDER_WIDTH + SPACE_FROM_WALL;
        float colLocation = BORDER_WIDTH;
        for(int i = 0; i < ROW; i++){
            for(int j = 0; j < COL; j++){
                Vector2 dimention = new Vector2(this.width_of_brick, HEIGHT_OF_BRICK);
                Vector2 location = new Vector2(rowLocation, colLocation);
                GameObject brick = new Brick(location, dimention, brickImage,
                        brickFactory.getStrategy(0), bricksCounter, 0);
                bricksCounter.increment();
                gameObjects().addGameObject(brick);
                rowLocation += this.width_of_brick + SPACE_FROM_BRICK;
            }
            rowLocation = BORDER_WIDTH + SPACE_FROM_WALL;;
            colLocation += HEIGHT_OF_BRICK + SPACE_FROM_BRICK;
        }
    }

    /**
     * create paddle
     * @param paddleImage
     * @param inputListener
     * @param windowDimensions
     */
    private void createPaddle(Renderable paddleImage, UserInputListener inputListener, Vector2 windowDimensions) {
        GameObject paddle = new src.gameobjects.Paddle(
                Vector2.ZERO,
                new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT),
                paddleImage,
                inputListener,
                windowDimensions,0);
        paddle.setCenter(new Vector2(windowDimensions.x()/2, (int)windowDimensions.y()-30));
        gameObjects().addGameObject(paddle);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if(numOflife < lifeCounter.value()){
            heartInit();
        }
        numOflife = lifeCounter.value();
        removeHeart();
        checkForGameEnd();
    }


    /**
     * remove hearts that get out of the game
     */
    private void removeHeart(){
        for (int i = 0; i < hearts.length; i++) {
            if (hearts[i] != null) {
                if(hearts[i].getCenter().y() > windowDimensions.y()){
                    gameObjects().removeGameObject(hearts[i]);
                    hearts[i] = null;
                }
            }
        }
    }

    /**
     * check if the game is over
     */
    private void checkForGameEnd() {
        double ballHeight = ball.getCenter().y();
        String prompt = "";
        if((bricksCounter.value() == 0) || (inputListener.isKeyPressed(KeyEvent.VK_W))){
            prompt = "you win!";
        }
        if(ballHeight > windowDimensions.y()){
            lifeCounter.decrement();
            if(lifeCounter.value() == 0){
                prompt = "you lose!";
            }
            else {
                gameObjects().removeGameObject(ball);
                createBall(imageReader, soundReader, windowController);
                heartInit();
            }
        }
        if(!prompt.isEmpty()){
            prompt += "\nPlay again?";
            if(windowController.openYesNoDialog(prompt)){
                windowController.resetGame();
            }
            else {
                windowController.closeWindow();
            }
        }
    }

    /**
     * create the numeric and the grafic counters
     */
    private void heartInit() {
        gameObjects().removeGameObject(numericLifeCounter, Layer.BACKGROUND);
        createNumericLifeCounter();
        int num = numOflife;
        for (int i = 0; i < num; i++) {
            gameObjects().removeGameObject(heartsArr[i], Layer.BACKGROUND);
        }
        createHearts();
    }

    /**
     * create ball
     * @param imageReader
     * @param soundReader
     * @param windowController
     */
    private void createBall(ImageReader imageReader, SoundReader soundReader, WindowController windowController){
        Renderable ballImage =
                imageReader.readImage("assets/ball.png", true);
        Sound collisionSound = soundReader.readSound("assets/blop_cut_silenced.wav");
        ball = new src.gameobjects.Ball(Vector2.ZERO, new Vector2(20,20), ballImage, collisionSound);
        ball.setVelocity(Vector2.DOWN.mult(300));
        Vector2 windowDimensions = windowController.getWindowDimensions();
        ball.setCenter(windowDimensions.mult(0.5F));
        gameObjects().addGameObject(ball);
        float ballVelX = BALL_SPEED;
        float ballVely = BALL_SPEED;
        Random rand = new Random();
        if(rand.nextBoolean()){
            ballVelX *= -1;
        }
        if(rand.nextBoolean()){
            ballVely *= -1;
        }
        ball.setVelocity(new Vector2(ballVelX, ballVely));
    }

    public boolean getMockExist(){
        return mockPaddleExist;
    }

    /**
     * main function
     * @param args
     */
    public static void main(String[] args) {

        new BrickerGameManager("Bricker", new Vector2(700, 500)).run();
    }
    public float getWidth_of_brick(){
        return width_of_brick;
    }
}
