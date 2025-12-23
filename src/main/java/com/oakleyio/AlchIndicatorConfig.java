package com.oakleyio;

import java.awt.Color;

import net.runelite.client.config.Alpha;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;


@ConfigGroup("Alch Indicator")
public interface AlchIndicatorConfig extends Config
{
	@ConfigItem(
			keyName = "flashingIndicator",
			name = "Flashing Indicator",
			description = "This setting enables the Alch Indicator to flash when an alch spell is chosen."
	)
	default boolean flashingIndicator()
	{
		return false;
	}

	@ConfigItem(
			keyName = "interval",
			name = "Interval",
			description = "This setting customizes how often the Alch Indicator will flash when an alch spell is chosen."
	)
	default int flashingIndicatorInterval() { return 1; }

	@Alpha
	@ConfigItem(
			keyName = "indicatorColor",
			name = "Indicator Color",
			description = "The color of the overlay. The overlay is created after selecting an alch spell."
	)
	default Color indicatorColor()
	{
		return new Color(75, 0, 255, 50);
	}

	@ConfigItem(
		keyName = "tooltip",
		name = "Alch Price Tooltip",
		description = "A tooltip that displays the alch price of an item when hovering over it."
	)
	default boolean tooltip()
	{
		return false;
	}
}
