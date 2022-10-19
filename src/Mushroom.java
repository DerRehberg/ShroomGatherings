import java.awt.*;

public class Mushroom {

    static boolean Pickup = false;
    boolean PickedUp = false;
    int X;
    int Y;
    int Scale = Map.Scale;
    int Type= 0;
    Rectangle rect;

    public Mushroom(int x, int y,int type)
    {
        int scale = 1;
        if(Map.Scale > 32)
        {
            scale = Map.Scale/32;
        }
        X = x*scale;
        Y = y*scale;
        rect = new Rectangle(x,y,Scale,Scale);
        Type = type;
    }

    public void math()
    {
        rect = new Rectangle(X - Player.PlayerCoords[0], Y - Player.PlayerCoords[1], Scale, Scale);

        if(PickedUp)
        {
            X = Main.WIDTH/2+ Player.PlayerCoords[0];
            Y = Main.HEIGHT/2+ Player.PlayerCoords[1];
        }
        if(Pickup)
        {
            if(rect.intersects(Player.rect))
            {
                PickedUp = true;
            }
        }
    }

    public void draw()
    {
        Main.g.drawImage(Main.Textures.get(23+Type),rect.x,rect.y,Scale,Scale,null);
    }

}
