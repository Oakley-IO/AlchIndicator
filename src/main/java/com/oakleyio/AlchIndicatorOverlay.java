package com.oakleyio;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.client.ui.overlay.Overlay;


@Slf4j
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
        graphics.drawRect(0, 0, 100, 200);

        return null;
    }
}
