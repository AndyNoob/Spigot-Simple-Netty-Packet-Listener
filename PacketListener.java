import io.netty.channel.*;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public class PacketListener extends ChannelDuplexHandler {

    private final Player player;

    public PacketListener(Player player) {
        this.player = player;
        inject();
    }

    /**
    Read PacketPlayIn... and Write PacketPlayOut...
     */

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        super.write(ctx, msg, promise);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }

    private void inject() {
        try {
            ChannelPipeline pipeline = Util.getPlayerChannel(player).pipeline();
            pipeline.addBefore("packet_handler", player.getUniqueId().toString(), this);
        } catch (InvocationTargetException | NoSuchMethodException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void eject() {
        try {
            Channel channel = Util.getPlayerChannel(player);
            channel.eventLoop().submit(() -> {
                channel.pipeline().remove(this);
            });
        } catch (InvocationTargetException | NoSuchMethodException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
