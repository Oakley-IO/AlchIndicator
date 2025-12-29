package com.oakleyio;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.MenuEntry;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.tooltip.Tooltip;
import net.runelite.client.ui.overlay.tooltip.TooltipManager;

import javax.inject.Inject;
import java.awt.*;

@Slf4j
public class TooltipOverlay extends Overlay
{
    @Inject
    private AlchIndicatorConfig alchIndicatorConfig;

    @Inject
    private Client client;

    @Inject
    private ItemManager itemManager;

    @Inject
    private TooltipManager tooltipManager;

    @Inject
    private TooltipOverlay(AlchIndicatorConfig alchIndicatorConfig, Client client, ItemManager itemManager, TooltipManager tooltipManager)
    {
        this.alchIndicatorConfig = alchIndicatorConfig;
        this.client = client;
        this.itemManager = itemManager;
        this.tooltipManager = tooltipManager;
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        // Exit early if tooltips are disabled in the plugin configuration.
        if (alchIndicatorConfig.tooltip() == false)
        {
            return null;
        }

        // Retrieve the current menu entry that represents the action the
        //  player is currently hovering over.
        MenuEntry[] menu = client.getMenuEntries();
        int menuSize = menu.length;
        MenuEntry entry = menu[menuSize - 1];

        // Extract the option (action) and target (spell -> item) from the menu
        //  entry.
        String option = entry.getOption();
        String target = entry.getTarget();

        // Only show a tooltip when the player has an alchemy spell selected and
        //  is hovering over an item that can be cast on.
        if (
                option.equals("Cast") && (target.contains("High Level Alchemy") || target.contains("Low Level Alchemy"))
        )
        {
            // The presence of "->" indicates the spell is being targeted at an item.
            if (target.contains("->"))
            {
                // Look up the item's High Alchemy value and display it as a tooltip.
                int itemId = entry.getItemId();
                int haPrice = itemManager.getItemComposition(itemId).getHaPrice();
                String tooltipText = "HA: " + haPrice;
                tooltipManager.add(new Tooltip(tooltipText));
            }
        }

        return null;
    }
}
