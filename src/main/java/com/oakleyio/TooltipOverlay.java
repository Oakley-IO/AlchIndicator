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
        if (alchIndicatorConfig.tooltip() == false)
        {
            return null;
        }

        final MenuEntry[] menu = client.getMenuEntries();
        final int menuSize = menu.length;
        final MenuEntry entry = menu[menuSize - 1];

        String option = entry.getOption();
        String target = entry.getTarget();

        if (
                option.equals("Cast") && (target.contains("High Level Alchemy") || target.contains("Low Level Alchemy"))
        )
        {
            if (target.contains("->"))
            {
                int itemId = entry.getItemId();
                String tooltipText = "HA: " + itemManager.getItemComposition(itemId).getHaPrice();
                tooltipManager.add(new Tooltip(tooltipText));
            }
        }

        return null;
    }
}
