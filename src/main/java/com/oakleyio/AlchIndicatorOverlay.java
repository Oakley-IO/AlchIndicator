package com.oakleyio;

import java.awt.BasicStroke;
import java.awt.Stroke;
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
import net.runelite.api.gameval.InterfaceID;


@Slf4j
public class AlchIndicatorOverlay extends Overlay {

    private final Client client;
    private final AlchIndicatorPlugin alchIndicatorPlugin;
    private final AlchIndicatorConfig config;

    @Inject
    private AlchIndicatorOverlay(Client client, AlchIndicatorPlugin alchIndicatorPlugin, AlchIndicatorConfig config)
    {
        this.client = client;
        this.alchIndicatorPlugin = alchIndicatorPlugin;
        this.config = config;

        // Allows the overlay to follow the anchor point
        setPosition(OverlayPosition.DYNAMIC);

        // Sets the indicator to be above the inventory.
        setLayer(OverlayLayer.ABOVE_WIDGETS);
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        // Find the "side panel" (inventory) widget. If found, draw an indicator
        //  above it.
        Widget anchorWidget = findWidget();
        if (anchorWidget != null)
        {
            // Align the indicator with the inventory widget
            int xCord = anchorWidget.getCanvasLocation().getX() + config.offsetX();
            int yCord = anchorWidget.getCanvasLocation().getY() + config.offsetY();
            int widgetHeight = anchorWidget.getHeight() + config.offsetHeight();
            int widgetWidth = anchorWidget.getWidth() + config.offsetWidth();

            // Indicator Fill
            graphics.setColor(config.indicatorColor());
            graphics.fillRect(xCord, yCord, widgetWidth, widgetHeight);

            int configThickness = config.borderThickness();

            // -1 means OFF (do nothing)
            if (configThickness >= 0)
            {
                Stroke oldStroke = graphics.getStroke();
                graphics.setStroke(new BasicStroke(configThickness));

                int inset = configThickness / 2;

                graphics.setColor(config.borderColor());
                graphics.drawRect(
                        xCord + inset,
                        yCord + inset,
                        widgetWidth - 1 - inset * 2,
                        widgetHeight - 1 - inset * 2
                );

                // reset the stroke width
                graphics.setStroke(oldStroke);
            }
            // else (-1): border intentionally not drawn


            // text warning
            String text = "Alchemy spell activated!";
            graphics.setColor(Color.BLACK);
            graphics.drawString(text, xCord + 1, yCord + 1);
            graphics.setColor(Color.YELLOW);
            graphics.drawString(text, xCord, yCord);
        }

        return null;
    }

    private Widget findWidget()
    {
        // todo rewrite these notes, but they are good to know.
        // using widget inspector find the name of the widget and search for the map in:
        // runelite\runelite-api\src\main\java\net\runelite\api\gameval\InterfaceID.java
        // list all possible interface ID depending on user's display mode
        // check if the widget id exists with runelite atm
        // return the hit, else return null

        // possible anchors to attach the overlay to
        int[] possibleAnchors =
        {
            // array of widgets that are assigned to an int InterfaceID
            InterfaceID.ToplevelOsrsStretch.SIDE_CONTAINER, 	// Resizable - Classic layout
            InterfaceID.ToplevelPreEoc.SIDE_BACKGROUND,			// Resizable - Modern layout
            InterfaceID.Toplevel.SIDE_PANELS					// Fixed - Classic layout
        };

        Widget foundAnchor = null;

        for (int widgetInterfaceID : possibleAnchors)
        {
            Widget checkWidget = client.getWidget(widgetInterfaceID);

            // if the widget exists at the moment
            if (checkWidget != null && !checkWidget.isHidden())
            {
                //log.info("ALCH DEBUG CHECK WIDGET TYPE: {}", checkWidget.getType());
                //log.info("ALCH DEBUG CHECK WIDGET: {}", checkWidget);
                foundAnchor = checkWidget;
                break;
            }
        }

        return foundAnchor;
    }
}
