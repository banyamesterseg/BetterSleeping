package be.betterplugins.bettersleeping.sleepersneeded;

import be.betterplugins.bettersleeping.model.ConfigContainer;
import be.betterplugins.bettersleeping.model.sleeping.SleepWorld;
import be.betterplugins.core.messaging.logging.BPLogger;
import com.google.inject.Inject;

import java.util.logging.Level;

public class AbsoluteNeeded implements ISleepersCalculator
{

    // Constants
    private final int numNeeded;

    @Inject
    public AbsoluteNeeded(ConfigContainer config, BPLogger logger)
    {
        this.numNeeded = config.getSleeping_settings().getInt("absolute_needed");

        logger.log(Level.CONFIG, "Using 'absolute' as sleepers-needed calculator");
        logger.log(Level.CONFIG, "The trigger is set to " + numNeeded + " players sleeping");
    }

    /**
     * Get the required amount of sleeping players in this world
     * @return the absolute amount of required sleepers
     */
    @Override
    public int getNumNeeded(SleepWorld world)
    {
        // Calculate the lowest requirement: the fixed number, or all players in the world
        int numPlayers = world.getValidPlayersInWorld().size();
        int num = Math.min(numPlayers, numNeeded);

        // Require at least one sleeper
        return Math.max(num, 1);
    }

    @Override
    public int getSetting()
    {
        return numNeeded;
    }

}
