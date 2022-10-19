import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;


public class Main extends JFrame {

    static ArrayList<BufferedImage> Textures = new ArrayList<>();
    static ArrayList<Mushroom> Mushrooms = new ArrayList<>();
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
                if(keyEvent.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    Mushroom.Pickup = true;
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
                if(keyEvent.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    Mushroom.Pickup = false;
                }
            }
        });
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        //Init
        getTextures(32);
        getTextures(16);
        Map.init();
        MapObjects.init();

        //Main Loop
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

        Map.draw();
        Player.draw();
        MapObjects.draw();

        g.dispose();
        bs.show();
    }

    static void math()
    {
        Player.update();
        MapObjects.math();
        //Map.Calculate();
    }

    static void getTextures(int bit)
    {
        File dir = new File("MushroomGame/"+bit);
        File[] files = dir.listFiles();
        if(files != null) {
            Arrays.sort(files);

            for (File file : files) {
                try {
                    BufferedImage one = ImageIO.read(file);
                    for (int i = 0; i < one.getWidth() / bit; i++) {
                        Textures.add(ImageIO.read(file).getSubimage(i*bit, 0, bit, one.getHeight()));
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
    static Rectangle rect;

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
        rect = new Rectangle(Main.WIDTH/2,Main.HEIGHT/2,Map.Scale,Map.Scale);
    }

    static void draw()
    {
        Main.g.drawImage(Main.Textures.get(15),Main.WIDTH/2,Main.HEIGHT/2,Map.Scale,Map.Scale,null);
    }


}

class MapObjects
{
    static void init()
    {
        Main.Mushrooms.add(new Mushroom(50,50,4));
        Main.Mushrooms.add(new Mushroom(20,20,0));
    }

    static void draw()
    {
        for(Mushroom shroom : Main.Mushrooms)
        {
            shroom.draw();
        }
    }
    static void math()
    {
        for(Mushroom shroom : Main.Mushrooms)
        {
            shroom.math();
        }
    }
}

class Map
{
    static int Scale = 32;
    static ArrayList<int[]> MapTiles = new ArrayList<>();

    static void init()
    {
        for(int x = 0; x  < Main.WIDTH; x+=Scale)
        {
            for(int y = 0; y < Main.HEIGHT; y+=Scale)
            {
                MapTiles.add(new int[]{x,y,10});
            }
        }
    }

    static void Generator()
    {

    }

    static void Calculate()
    {
        MapTiles.sort(Comparator.comparingInt(a -> a[a.length - 1]));
    }
    static void draw()
    {
        /*for(int x = Player.PlayerCoords[0]; x-Player.PlayerCoords[0] < Main.WIDTH; x+=32)
        {
            for(int y = Player.PlayerCoords[1]; y-Player.PlayerCoords[1] < Main.HEIGHT; y+=32)
            {
                Main.g.drawImage(Main.Textures.get(10),x,y,32,32,null);
            }
        }*/
        for(int[] Tile : MapTiles)
        {
            Main.g.drawImage(Main.Textures.get(Tile[2]),Tile[0]-Player.PlayerCoords[0],Tile[1]-Player.PlayerCoords[1],Scale,Scale,null);
        }

    }
}
