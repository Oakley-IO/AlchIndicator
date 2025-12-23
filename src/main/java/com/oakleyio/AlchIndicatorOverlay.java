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
    private final AlchIndicatorConfig alchIndicatorConfig;
    private Color lastIndicatorColor;
    private final AlchIndicatorPlugin alchIndicatorPlugin;

    private boolean hasGameTicked = false;
    private int gameTicks = 0;

    @Inject
    private AlchIndicatorOverlay(Client client, AlchIndicatorConfig alchIndicatorConfig, AlchIndicatorPlugin alchIndicatorPlugin)
    {
        this.client = client;
        this.alchIndicatorConfig = alchIndicatorConfig;
        this.alchIndicatorPlugin = alchIndicatorPlugin;

        // Allows the indicator to move - not needed technically, since you
        //  can't reposition widgets when casting a spell.
        setPosition(OverlayPosition.DYNAMIC);

        // Sets the indicator to be above the inventory.
        setLayer(OverlayLayer.ABOVE_WIDGETS);

        this.lastIndicatorColor = null;
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        // If flashing is enabled, alternate indicator visibility based on game ticks.
        if (alchIndicatorConfig.flashingIndicator())
        {
            // Rendering occurs more frequently than game ticks.
            // Only update flashing state when a new game tick has occurred.
            if (hasGameTicked)
            {
                // Check if the number of game ticks meets the interval threshold
                //  configured by the player.
                if (gameTicks >= alchIndicatorConfig.flashingIndicatorInterval())
                {
                    // Color value is irrelevant here; opacity controls the visibility.
                    lastIndicatorColor = new Color(255, 255, 255, 0);
                    gameTicks = 0;
                }
                else
                {
                    lastIndicatorColor = alchIndicatorConfig.indicatorColor();
                    gameTicks++;
                }

                // Prevent further flashing updates until the next game tick.
                hasGameTicked = false;
            }

          renderIndicator(lastIndicatorColor, graphics);
        }
        else
        {
            // Flashing is disabled, always render the indicator normally.
            renderIndicator(alchIndicatorConfig.indicatorColor(), graphics);
        }

        return null;
    }

    private Widget findWidget()
    {
        // List of possible UI anchor widgets corresponding to the different
        //  game client layout configurations a player may be using.
        // Each entry represents the inventory, or "side panel", widget
        //  for a specific layout mode.
        int[] possibleAnchors =
        {
            InterfaceID.ToplevelOsrsStretch.SIDE_CONTAINER, // Resizable - Classic layout
            InterfaceID.ToplevelPreEoc.SIDE_BACKGROUND,	    // Resizable - Modern layout
            InterfaceID.Toplevel.SIDE_PANELS                // Fixed - Classic layout
        };

        // Iterate through known layout anchors and return the first
        //  widget that is present and visible, which indicates the
        //  player's active layout.
        Widget foundAnchor = null;
        for (int widgetInterfaceID : possibleAnchors)
        {
            Widget checkWidget = client.getWidget(widgetInterfaceID);

            // A non-null, visible widget indicates the active layout.
            if (checkWidget != null && !checkWidget.isHidden())
            {
                foundAnchor = checkWidget;
                break;
            }
        }

        return foundAnchor;
    }

    private void renderIndicator(Color indicatorColor, Graphics2D graphics)
    {
        // Locate the inventory UI widget based on the players game client layout
        //  (i.e., fixed or resizeable).
        Widget anchorWidget = findWidget();

        if (anchorWidget != null)
        {
            // The x and y coordinates reference the top left of the widget.
            int xCord = anchorWidget.getCanvasLocation().getX();
            int yCord = anchorWidget.getCanvasLocation().getY();

            // Use the widget's dimensions so the indicator fully overlays it.
            int widgetHeight = anchorWidget.getHeight();
            int widgetWidth = anchorWidget.getWidth();

            graphics.setColor(indicatorColor);
            graphics.fillRect(xCord, yCord, widgetWidth, widgetHeight);
            graphics.drawRect(xCord, yCord, widgetWidth, widgetHeight);
        }
    }

    public void setHasGameTicked(boolean hasGameTicked)
    {
        this.hasGameTicked = hasGameTicked;
    }
}
