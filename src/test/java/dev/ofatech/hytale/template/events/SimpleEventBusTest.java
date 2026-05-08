package dev.ofatech.hytale.template.events;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SimpleEventBusTest {

    @Test
    void publishesToSubscribersAndAllowsUnsubscribe() {
        SimpleEventBus bus = new SimpleEventBus(null);
        AtomicInteger counter = new AtomicInteger();

        EventSubscription subscription = bus.subscribe(String.class, value -> counter.incrementAndGet());
        bus.publish("one");
        subscription.unsubscribe();
        bus.publish("two");

        assertEquals(1, counter.get());
    }

    @Test
    void continuesWhenHandlerFails() {
        SimpleEventBus bus = new SimpleEventBus(null);
        AtomicInteger counter = new AtomicInteger();

        bus.subscribe(String.class, value -> {
            throw new IllegalStateException("boom");
        });
        bus.subscribe(String.class, value -> counter.incrementAndGet());

        bus.publish("test");

        assertEquals(1, counter.get());
    }
}

