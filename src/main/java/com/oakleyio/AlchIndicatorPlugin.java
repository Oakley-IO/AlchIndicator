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

@Slf4j
@PluginDescriptor(
	name = "Example"
)
public class AlchIndicatorPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private AlchIndicatorConfig config;

	@Override
	protected void startUp() throws Exception
	{
		log.debug("Example started!");
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.debug("Example stopped!");
	}

	@Subscribe
	public void onMenuOptionClicked(MenuOptionClicked menuOptionClicked)
	{
		// log.debug("Menu Entry: {}", menuOptionClicked.getMenuEntry());
		String option = menuOptionClicked.getMenuEntry().getOption();
		String target = menuOptionClicked.getMenuEntry().getTarget();

		// The player selected one of the alchemy spells to cast.
		if (
				(option.equals("Cast") && target.equals("<col=00ff00>High Level Alchemy</col>"))
				|| (option.equals("Cast") && target.equals("<col=00ff00>Low Level Alchemy</col>"))
		) {
			log.debug("Alchemy spell selected!");
		}
		// The player cancelled the spell.
		else if (option.equals("Cancel") && target.equals(""))
		{
			log.debug("Spell cancelled.");
		}

		return;
	}

	@Provides
	AlchIndicatorConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(AlchIndicatorConfig.class);
	}
}
