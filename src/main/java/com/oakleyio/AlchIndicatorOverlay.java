package com.oakleyio;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics2D;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.widgets.Widget;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;


@Slf4j
public class AlchIndicatorOverlay extends Overlay {

    private Client client;
    private AlchIndicatorPlugin alchIndicatorPlugin;

    @Inject
    private AlchIndicatorOverlay(Client client, AlchIndicatorPlugin alchIndicatorPlugin)
    {
        this.client = client;
        this.alchIndicatorPlugin = alchIndicatorPlugin;

        // Allows the indicator to move - not needed technically, since you
        //  can't reposition widgets when casting a spell.
        setPosition(OverlayPosition.DYNAMIC);

        // Sets the indicator to be above the inventory.
        setLayer(OverlayLayer.ABOVE_SCENE);
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        // Find the "side panel" (inventory) widget. If found, draw an indicator
        //  above it.
        Widget currWidget = find_widget();
        if (currWidget != null)
        {
            int xCord = currWidget.getCanvasLocation().getX();
            int yCord = currWidget.getCanvasLocation().getY();
            int widgetHeight = currWidget.getHeight();
            int widgetWidth = currWidget.getWidth();

            // Todo: Make this dynamic.
            graphics.setColor(new Color(255, 255, 0, 75));
            graphics.fillRect(xCord, yCord, widgetWidth, widgetHeight);
            graphics.drawRect(xCord, yCord, widgetWidth, widgetHeight);
        }

        return null;
    }

    private Widget find_widget()
    {
        // Todo: Write the API to find the inventory widget.
        return null;
    }
}
