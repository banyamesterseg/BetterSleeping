package be.betterplugins.bettersleeping.sleepersneeded;

import be.betterplugins.bettersleeping.model.ConfigContainer;
import be.betterplugins.bettersleeping.model.sleeping.SleepWorld;
import be.betterplugins.core.messaging.logging.BPLogger;
import com.google.inject.Inject;

import java.util.logging.Level;

public class EitherNeeded implements ISleepersCalculator
{

    private final int percentage;
    private final int numNeeded;

    @Inject
    public EitherNeeded(ConfigContainer config, BPLogger logger)
    {
        this.percentage = config.getSleeping_settings().getInt("percentage_needed");
        this.numNeeded  = config.getSleeping_settings().getInt("absolute_needed");

        logger.log(Level.CONFIG, "Using 'either' as sleepers-needed calculator");
        logger.log(Level.CONFIG, "The trigger is set to " + this.percentage + "% of players OR at least " + this.numNeeded + " players sleeping, whichever comes first");
    }


    /**
     * Get the required amount of sleeping players in this world
     * Ignores bypassed (afk, vanished, gm/permission bypassed,...) players
     *
     * @return the absolute amount of required sleepers
     */
    @Override
    public int getNumNeeded(SleepWorld world)
    {
        int numPlayers = world.getValidPlayersInWorld().size();
        return Math.max(Math.round(percentage * numPlayers / 100f), 1);
        return Math.min(Math.round(percentage * numPlayers / 100f), Math.max(Math.min(numPlayers, numNeeded), 1));
    }

    @Override
    public int getSetting()
    {
        return -1;
    }
}
