package com.oakleyio;

import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.client.ui.overlay.Overlay;


public class AlchIndicatorOverlay extends Overlay {

    private Client client;
    private AlchIndicatorPlugin alchIndicatorPlugin;

    @Inject
    private AlchIndicatorOverlay(Client client, AlchIndicatorPlugin alchIndicatorPlugin)
    {
        this.client = client;
        this.alchIndicatorPlugin = alchIndicatorPlugin;
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        return null;
    }
}
