package com.oakleyio;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;


@Slf4j
@PluginDescriptor(
	name = "Alch Indicator"
)
public class AlchIndicatorPlugin extends Plugin
{
	@Inject
	private AlchIndicatorConfig alchIndicatorConfig;

	@Inject
	private AlchIndicatorOverlay alchIndicatorOverlay;

	@Inject
	private Client client;

	@Inject
	private OverlayManager overlayManager;


	@Override
	protected void startUp() throws Exception
	{
		return;
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(alchIndicatorOverlay);

		return;
	}

	@Subscribe
	public void onMenuOptionClicked(MenuOptionClicked menuOptionClicked)
	{
		log.debug("Menu Entry: {}", menuOptionClicked.getMenuEntry());
		String option = menuOptionClicked.getMenuEntry().getOption();
		String target = menuOptionClicked.getMenuEntry().getTarget();

		// The player selected one of the alchemy spells to cast.
		if (
				option.equals("Cast") && (target.contains("High Level Alchemy") || target.contains("Low Level Alchemy"))
		) {
			if (target.contains("->"))
			{
				log.info("Alchemy spell casted.");
				overlayManager.remove(alchIndicatorOverlay);
			}
			else
			{
				log.info("Alchemy spell selected!");
				overlayManager.add(alchIndicatorOverlay);
			}
		}
		// The player canceled the spell.
		else if (option.equals("Cancel") && target.isEmpty())
		{
			log.info("Alchemy spell canceled.");
			overlayManager.remove(alchIndicatorOverlay);
		}
	}

	@Provides
	AlchIndicatorConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(AlchIndicatorConfig.class);
	}
}
