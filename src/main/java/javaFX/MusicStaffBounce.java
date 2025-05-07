package javaFX;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.*;
import javafx.stage.Stage;

import java.util.LinkedList;

public class MusicStaffBounce extends Application {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int STAFF_LINE_COUNT = 5;
    private static final int STAFF_SPACING = 20;
    private static final int STAFF_Y_OFFSET = 300;
    private static final int TRAIL_LENGTH = 40;
    private static final double BALL_RADIUS = 6;
    private static final double SPEED_X = 3.0;
    private static final int GLOW_RADIUS = 100;

    private GraphicsContext gc;
    private double posX = 0;
    private LinkedList<Double> trailX = new LinkedList<>();
    private LinkedList<Double> trailY = new LinkedList<>();

    // 五线谱相关参数
    private final int[] staffLinesY = new int[STAFF_LINE_COUNT];

    @Override
    public void start(Stage primaryStage) {
        initializeStaff();

        Pane root = new Pane();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // 光晕效果设置
        DropShadow glow = new DropShadow();
        glow.setRadius(25);
        glow.setColor(Color.GOLD.deriveColor(1, 1, 1, 0.1));
        canvas.setEffect(glow);

        root.getChildren().add(canvas);
        Scene scene = new Scene(root, WIDTH, HEIGHT, Color.BLACK);

        primaryStage.setTitle("五线谱节奏球");
        primaryStage.setScene(scene);
        primaryStage.show();

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                render();
            }
        }.start();
    }

    private void initializeStaff() {
        // 计算五线谱各线Y坐标
        int startY = STAFF_Y_OFFSET - (STAFF_LINE_COUNT / 2) * STAFF_SPACING;
        for (int i = 0; i < STAFF_LINE_COUNT; i++) {
            staffLinesY[i] = startY + i * STAFF_SPACING;
        }
    }

    private void update() {
        posX += SPEED_X;


        // 循环重置
        if (posX - BALL_RADIUS > WIDTH) {
            posX = -BALL_RADIUS;
            trailX.clear();
            trailY.clear();
        }

        // 记录轨迹
        trailX.addFirst(posX);
        trailY.addFirst(getCurrentNoteY());
        if (trailX.size() > TRAIL_LENGTH) {
            trailX.removeLast();
            trailY.removeLast();
        }
    }

    private double getCurrentNoteY() {
        // 根据当前位置获取对应五线谱Y坐标
        double phase = (posX % 200) / 200.0;
        return staffLinesY[2] + Math.sin(phase * Math.PI * 2) * STAFF_SPACING * 1.5;
    }

    private void render() {
        gc.clearRect(0, 0, WIDTH, HEIGHT);
        drawStaff();
        drawLightEffect();
        drawTrail();
        drawBall();
    }

    private void drawStaff() {
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(1.5);
        for (int y : staffLinesY) {
            gc.strokeLine(0, y, WIDTH, y);
        }
    }

    private void drawLightEffect() {
        RadialGradient gradient = new RadialGradient(
                0, 0,
                posX, getCurrentNoteY(),
                GLOW_RADIUS,
                false,
                CycleMethod.NO_CYCLE,
                new Stop(0, Color.GOLD.deriveColor(1, 1, 1, 0.4)),
                new Stop(1, Color.TRANSPARENT)
        );

        gc.setGlobalBlendMode(BlendMode.SOFT_LIGHT);
        gc.setFill(gradient);
        gc.fillRect(0, 0, WIDTH, HEIGHT);
        gc.setGlobalBlendMode(BlendMode.SRC_OVER);
    }

    private void drawTrail() {
        for (int i = 1; i < trailX.size() - 1; i++) {
            double alpha = 1.0 - (i / (double)TRAIL_LENGTH);
            gc.setStroke(Color.GOLD.deriveColor(1, 1, 1, alpha));
            gc.setLineWidth(BALL_RADIUS * alpha * 1.5);
            gc.strokeLine(
                    trailX.get(i), trailY.get(i),
                    trailX.get(i+1), trailY.get(i+1)
            );
        }
    }

    private void drawBall() {
        gc.setFill(Color.GOLD);
        gc.fillOval(
                posX - BALL_RADIUS / 2,
                getCurrentNoteY() - BALL_RADIUS / 2,
                BALL_RADIUS,
                BALL_RADIUS
        );
    }

    public static void main(String[] args) {
        launch(args);
    }
}