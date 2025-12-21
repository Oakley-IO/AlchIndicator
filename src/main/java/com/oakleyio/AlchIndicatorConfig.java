package com.oakleyio;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

// TODO: Enum is a simple idea, maybe other plugins do different color picking methods.
// What does Agility plugin do?
enum IndicatorColor
{
	RED,
	GREEN,
	YELLOW,
	BLUE
}

@ConfigGroup("example")
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
			keyName = "flashingIndicatorInterval",
			name = "Flashing Indicator Interval",
			description = "This setting customizes how often the Alch Indicator will flash when an alch spell is chosen."
	)
	default double flashingIndicatorInterval()
	{
		return 1;
	}

	@ConfigItem(
			keyName = "indicatorColor",
			name = "Indicator Color",
			description = "The color of the overlay. The overlay is created after selecting an alch spell."
	)
	default IndicatorColor indicatorColor()
	{
		return IndicatorColor.GREEN;
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
