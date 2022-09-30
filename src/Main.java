import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class Main extends JFrame {

    static ArrayList<BufferedImage> Textures = new ArrayList<>();
    static JFrame frame;static BufferStrategy bs;static Graphics g;
    static final int HEIGHT = 360;
    static final int WIDTH = 480;

    public static void main(String[] args) {
        frame = new JFrame("ShroomGatherings");
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {

            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if(keyEvent.getKeyCode() == KeyEvent.VK_D)
                {
                    Player.right = true;
                }else if(keyEvent.getKeyCode() == KeyEvent.VK_A)
                {
                    Player.left = true;
                }
                if(keyEvent.getKeyCode() == KeyEvent.VK_S)
                {
                    Player.down = true;
                }
                if(keyEvent.getKeyCode() == KeyEvent.VK_W)
                {
                    Player.up = true;
                }
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                if(keyEvent.getKeyCode() == KeyEvent.VK_D)
                {
                    Player.right = false;
                }else if(keyEvent.getKeyCode() == KeyEvent.VK_A)
                {
                    Player.left = false;
                }
                if(keyEvent.getKeyCode() == KeyEvent.VK_S)
                {
                    Player.down = false;
                }
                if(keyEvent.getKeyCode() == KeyEvent.VK_W)
                {
                    Player.up = false;
                }
            }
        });
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        getTextures();

        while(true) {
            math();
            try {Thread.sleep(5);} catch (InterruptedException e) {}
            render();
        }
    }

    static void render()
    {
        if (bs == null) {
            frame.createBufferStrategy(3);
            bs = frame.getBufferStrategy();
            frame.requestFocus();
            return;
        }
        g = bs.getDrawGraphics();

        g.setColor(Color.GREEN);
        g.fillRect(0, 0, WIDTH,HEIGHT);
        g.drawImage(Textures.get(0),50,50,32,32,null);

        Map.draw();

        g.dispose();
        bs.show();
    }

    static void math()
    {
        Player.update();
    }

    static void getTextures()
    {
        File dir = new File("MushroomGame");
        File[] files = dir.listFiles();
        if(files != null) {
            Arrays.sort(files);

            for (File file : files) {
                try {
                    BufferedImage one = ImageIO.read(file);
                    for (int i = 0; i < one.getWidth() / 32; i++) {
                        Textures.add(ImageIO.read(file).getSubimage(i, 0, 32, one.getHeight()));
                    }
                } catch (IOException e) {
                    System.out.print(e);
                }
            }
        }
    }
}

class Player
{
    static boolean up,down,left,right;
    static int[] PlayerCoords = new int[2];

    static void update()
    {
        if(up)
        {
            PlayerCoords[1]--;
        }
        if(down)
        {
            PlayerCoords[1]++;
        }
        if(left)
        {
            PlayerCoords[0]--;
        }
        if(right)
        {
            PlayerCoords[0]++;
        }
    }


}

class Map
{
    static void draw()
    {
        for(int x = Player.PlayerCoords[0]; x-Player.PlayerCoords[0] < Main.WIDTH; x+=32)
        {
            for(int y = Player.PlayerCoords[1]; y-Player.PlayerCoords[1] < Main.HEIGHT; y+=32)
            {
                Main.g.drawImage(Main.Textures.get(10),x,y,32,32,null);
            }
        }
    }
}