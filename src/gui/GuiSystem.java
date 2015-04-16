package gui;

import core.*;
import graphics.*;
import static graphics.SpriteContainer.loadSprite;
import java.awt.Font;
import java.io.IOException;
import static org.lwjgl.opengl.GL11.*;
import org.newdawn.slick.Color;
import units.Unit;
import static world.GridComponent.SQUARE_SIZE;

public class GuiSystem extends AbstractSystem {

    private ResourcesComponent rc;
    private InterfaceComponent ic;

    public GuiSystem(ResourcesComponent rc, InterfaceComponent ic) {
        this.rc = rc;
        this.ic = ic;
        FontContainer.add("GUI", "Calibri", Font.PLAIN, 30);
    }

    @Override
    public int getLayer() {
        return 2;
    }

    @Override
    public void update() {
        try {
            RenderManagerComponent rmc = Main.gameManager.rmc;

            //Draw grid
            if (ic.constructionMode && rmc.zoom < 10) {
                Color4d.BLACK.glColor();
                glDisable(GL_TEXTURE_2D);
                glBegin(GL_LINES);
                for (int i = SQUARE_SIZE * (int) Math.ceil(rmc.LL().x / SQUARE_SIZE); i < rmc.UR().x; i += SQUARE_SIZE) {
                    new Vec2(i, rmc.LL().y).glVertex();
                    new Vec2(i, rmc.UR().y).glVertex();
                    //System.out.println(i);
                }
                for (int i = SQUARE_SIZE * (int) Math.ceil(rmc.LL().y / SQUARE_SIZE); i < rmc.UR().y; i += SQUARE_SIZE) {
                    new Vec2(rmc.LL().x, i).glVertex();
                    new Vec2(rmc.UR().x, i).glVertex();
                }
                glEnd();
            }

            //Set view for easy drawing
            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
            glOrtho(0, rmc.displaySize.x, 0, rmc.displaySize.y, -1, 1);
            glMatrixMode(GL_MODELVIEW);

            //Top bar
            Graphics.fillRect(new Vec2(0, 1030), new Vec2(1920, 1080), new Color4d(.5, .5, 1));
            //Materials
            Graphics.drawSprite(loadSprite("material_icon"), new Vec2(40, 1055), new Vec2(1, 1), 0, Color4d.WHITE);
            Graphics.drawText("" + rc.materials, "GUI", new Vec2(80, 1070), Color.black);
            //Money
            Graphics.drawSprite(loadSprite("money_icon"), new Vec2(145, 1055), new Vec2(1, 1), 0, Color4d.WHITE);
            Graphics.drawText("" + rc.money, "GUI", new Vec2(170, 1070), Color.black);
            //Science
            Graphics.drawSprite(loadSprite("science_icon"), new Vec2(250, 1055), new Vec2(1, 1), 0, Color4d.WHITE);
            Graphics.drawText("" + rc.science, "GUI", new Vec2(275, 1070), Color.black);
            //Food
            Graphics.drawSprite(loadSprite("food_icon"), new Vec2(350, 1055), new Vec2(1, 1), 0, Color4d.WHITE);
            Graphics.drawText("" + rc.food, "GUI", new Vec2(375, 1070), Color.black);
            //Population
            Graphics.drawSprite(loadSprite("population_icon"), new Vec2(450, 1055), new Vec2(1, 1), 0, Color4d.WHITE);
            Graphics.drawText("" + Main.gameManager.elc.getList(Unit.class).size(), "GUI", new Vec2(475, 1070), Color.black);

            //Construction
            if (ic.constructionMode) {
                Graphics.drawSprite(loadSprite("construction_icon"), new Vec2(1750, 1055), new Vec2(1, 1), 0, new Color4d(.5, .5, .5));
            } else {
                Graphics.drawSprite(loadSprite("construction_icon"), new Vec2(1750, 1055), new Vec2(1, 1), 0, Color4d.WHITE);
            }
            //Pause
            if (Main.paused) {
                Graphics.drawSprite(loadSprite("pause"), new Vec2(1840, 1055), new Vec2(1, 1), Math.PI, Color4d.WHITE);
            } else {
                Graphics.drawSprite(loadSprite("pause"), new Vec2(1840, 1055), new Vec2(1, 1), 0, Color4d.WHITE);
            }
            //Settings
            Graphics.drawSprite(loadSprite("settings"), new Vec2(1890, 1055), new Vec2(1, 1), 0, Color4d.WHITE);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
