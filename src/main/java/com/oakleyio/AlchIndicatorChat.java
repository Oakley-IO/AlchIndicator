package com.oakleyio;

import com.google.inject.Inject;
import net.runelite.api.ChatMessageType;
import net.runelite.client.chat.ChatMessageBuilder;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.chat.QueuedMessage;

/**
 * Small helper for sending formatted debug/info messages
 * to the in-game chatbox (Game tab).
 */
public class AlchIndicatorChat
{
    private static final String PREFIX = "[Alch Indicator] ";

    private final ChatMessageManager chatMessageManager;

    @Inject
    public AlchIndicatorChat(ChatMessageManager chatMessageManager)
    {
        this.chatMessageManager = chatMessageManager;
    }

    /**
     * Sends a message to the Game chat tab.
     */
    public void send(String message)
    {
        chatMessageManager.queue(
                QueuedMessage.builder()
                        .type(ChatMessageType.GAMEMESSAGE)
                        .runeLiteFormattedMessage(
                                new ChatMessageBuilder()
                                        .append(PREFIX)
                                        .append(message)
                                        .build()
                        )
                        .build()
        );
    }
}
