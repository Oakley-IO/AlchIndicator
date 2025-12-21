package com.oakleyio;

import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.inject.Inject;
import net.runelite.client.ui.overlay.Overlay;


public class AlchIndicatorOverlay extends Overlay {
    @Inject
    private AlchIndicatorOverlay(AlchIndicatorPlugin plugin)
    {

    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        return null;
    }
}
