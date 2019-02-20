package sample;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.canvas.GraphicsContext;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.PrintWriter;
import java.util.Random;
public class PrintInteger extends Task  {
    GraphicsContext gc;
    int numDots;
    int min = -8;
    int max = 8;
    Random randomG = new Random();

    public PrintInteger(GraphicsContext gc, int numDots) {
        this.gc = gc;
        this.numDots = numDots;
    }

    protected Object call() throws Exception {
        BufferedImage bi = new BufferedImage(600, 700, 1);
        int k = 0;

        for(int i = 0; i < this.numDots && !this.isCancelled(); ++i) {
            double x = (double)this.min + (double)(this.max - this.min) * this.randomG.nextDouble();
            double y = (double)this.min + (double)(this.max - this.min) * this.randomG.nextDouble();
            final double pixelX = this.gc.getCanvas().getWidth() * (x - (double)this.min) / (double)(this.max - this.min);
            final double pixelY = this.gc.getCanvas().getHeight() * (y - (double)this.min) / (double)(this.max - this.min);
            if (Equation.calc(x, y)) {
                ++k;
                bi.setRGB((int)pixelX, (int)(-pixelY + this.gc.getCanvas().getHeight()), Color.YELLOW.getRGB());
                if (i % 200 == 0) {
                    Platform.runLater(new Runnable() {
                        public void run() {
                            PrintInteger.this.gc.getPixelWriter().setColor((int)pixelX + 8, (int)PrintInteger.this.gc.getCanvas().getHeight() - (int)pixelY, javafx.scene.paint.Color.YELLOW);
                        }
                    });
                }
            } else {
                bi.setRGB((int)pixelX, (int)(-pixelY + this.gc.getCanvas().getHeight()), Color.BLUE.getRGB());
                if (i % 100 == 0) {
                    Platform.runLater(new Runnable() {
                        public void run() {
                            PrintInteger.this.gc.getPixelWriter().setColor((int)pixelX + 8, (int)PrintInteger.this.gc.getCanvas().getHeight() - (int)pixelY, javafx.scene.paint.Color.BLUE);
                        }
                    });
                }
            }

            this.updateProgress((long)i, (long)this.numDots);
        }

        return (float)k * 256.0F / (float)this.numDots * 1.0F;
    }
}
