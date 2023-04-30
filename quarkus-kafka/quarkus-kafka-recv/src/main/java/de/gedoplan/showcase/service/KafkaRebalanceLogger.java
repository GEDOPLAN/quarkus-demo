package de.gedoplan.showcase.service;

import java.util.Collection;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.TopicPartition;
import org.jboss.logging.Logger;

import io.smallrye.common.annotation.Identifier;
import io.smallrye.reactive.messaging.kafka.KafkaConsumerRebalanceListener;

@ApplicationScoped
@Identifier("rebalance.logger")
public class KafkaRebalanceLogger implements KafkaConsumerRebalanceListener  {
    @Inject
    Logger logger;

    @Override
    public void onPartitionsAssigned(Consumer<?, ?> consumer, Collection<TopicPartition> partitions) {
        partitions.forEach(p -> logger.debugf("Assigned %s at position %d", p, consumer.position(p)));
    }

    @Override
    public void onPartitionsLost(Consumer<?, ?> consumer, Collection<TopicPartition> partitions) {
        partitions.forEach(p -> logger.debugf("Lost %s", p));
    }

    @Override
    public void onPartitionsRevoked(Consumer<?, ?> consumer, Collection<TopicPartition> partitions) {
        partitions.forEach(p -> logger.debugf("Revoked %s", p));
    }
}
