import io.netty.channel.Channel;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public class Util {
    public static Object getNMSPlayer(Player player) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return player.getClass().getMethod("getHandle").invoke(player);
    }

    public static Object getPlayerConnection(Player player) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        Object nmsPlayer = getNMSPlayer(player);
        return nmsPlayer.getClass().getField("playerConnection").get(nmsPlayer);
    }

    public static Channel getPlayerChannel(Player player) throws InvocationTargetException, NoSuchMethodException, NoSuchFieldException, IllegalAccessException {
        Object playerConnection = getPlayerConnection(player);
        Object networkManager = playerConnection.getClass().getField("networkManager").get(playerConnection);
        return (Channel) networkManager.getClass().getField("channel").get(networkManager);
    }
}
