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
import net.runelite.api.gameval.InterfaceID;


@Slf4j
public class AlchIndicatorOverlay extends Overlay {

    private final Client client;
    private final AlchIndicatorPlugin alchIndicatorPlugin;

    @Inject
    private AlchIndicatorOverlay(Client client, AlchIndicatorPlugin alchIndicatorPlugin)
    {
        this.client = client;
        this.alchIndicatorPlugin = alchIndicatorPlugin;

        // Allows the indicator to move - not needed technically, since you
        //  can't reposition widgets when casting a spell.
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
            // x and y are a point top left of the widget
            int xCord = anchorWidget.getCanvasLocation().getX();
            int yCord = anchorWidget.getCanvasLocation().getY();
            int widgetHeight = anchorWidget.getHeight();
            int widgetWidth = anchorWidget.getWidth();

            // Todo: Make this dynamic.
            graphics.setColor(new Color(255, 255, 0, 75));
            graphics.fillRect(xCord, yCord, widgetWidth, widgetHeight);
            graphics.drawRect(xCord, yCord, widgetWidth, widgetHeight);
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
                foundAnchor = checkWidget;
                break;
            }
        }

        return foundAnchor;
    }
}
