package com.oakleyio;

import java.awt.Color;

import net.runelite.client.config.Alpha;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;
import net.runelite.client.config.Range;


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
			keyName = "Interval",
			name = "Interval",
			description = "This setting customizes how often the Alch Indicator will flash when an alch spell is chosen."
	)
	default double flashingIndicatorInterval()
	{
		return 1;
	}

	@Alpha
	@ConfigItem(
			keyName = "indicatorColor",
			name = "Indicator Color",
			description = "The color of the overlay. The overlay is created after selecting an alch spell."
	)
	default Color indicatorColor() { return new Color(255, 255, 0, 50); }

	@ConfigSection(
		name = "DEBUG",
		description = "Debug options",
		position = 100,
		closedByDefault = true
	)
	String SECTION_DEBUG = "DEBUG";

	@ConfigItem(
			keyName = "chatboxLogger",
			name = "Chatbox Logger",
			description = "",
			section = SECTION_DEBUG,
			position = 100
	)
	default boolean chatboxLogger()
	{
		return false;
	}

	@Range(min = -1, max = 20)
	@ConfigItem(
			keyName = "borderThickness",
			name = "Border Thickness",
			description = "-1 is off, 0 is thin, higher is thicker",
			section = SECTION_DEBUG,
			position = 100
	)
	default int borderThickness()
	{
		return -1;
	}

	@Alpha
	@ConfigItem(
			keyName = "borderColor",
			name = "Border Color",
			description = ""
	)
	default Color borderColor() { return Color.YELLOW; }

	@Range(min = -1000, max = 1000)
	@ConfigItem(
			keyName = "offsetX",
			name = "Offset X",
			description = "",
			section = SECTION_DEBUG,
			position = 101
	)
	default int offsetX()
	{
		return 2;
	}

	@Range(min = -1000, max = 1000)
	@ConfigItem(
			keyName = "offsetY",
			name = "Offset Y",
			description = "",
			section = SECTION_DEBUG,
			position = 102
	)
	default int offsetY()
	{
		return 2;
	}

	@Range(min = -1000, max = 1000)
	@ConfigItem(
			keyName = "offsetHeight",
			name = "Offset Height",
			description = "",
			section = SECTION_DEBUG,
			position = 103
	)
	default int offsetHeight()
	{
		return -5;
	}

	@Range(min = -1000, max = 1000)
	@ConfigItem(
			keyName = "offsetWidth",
			name = "Offset Width",
			description = "",
			section = SECTION_DEBUG,
			position = 104
	)
	default int offsetWidth()
	{
		return -5;
	}
}
