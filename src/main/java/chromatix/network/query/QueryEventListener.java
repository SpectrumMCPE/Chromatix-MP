package chromatix.network.query;

import chromatix.event.server.QueryRegenerateEvent;

import java.net.InetSocketAddress;

@FunctionalInterface
public interface QueryEventListener {
    QueryRegenerateEvent onQuery(InetSocketAddress address);
}
