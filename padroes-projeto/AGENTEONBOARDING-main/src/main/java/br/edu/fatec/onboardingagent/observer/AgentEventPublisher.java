package br.edu.fatec.onboardingagent.observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class AgentEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(AgentEventPublisher.class);

    private final List<AgentObserver> observers = new CopyOnWriteArrayList<>();

    public AgentEventPublisher(List<AgentObserver> descobertos) {
        observers.addAll(descobertos);
        log.info("Observadores inscritos: {}", observers.size());
    }

    public void subscribe(AgentObserver observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void unsubscribe(AgentObserver observer) {
        observers.remove(observer);
    }

    public int subscriberCount() {
        return observers.size();
    }

    public void publish(AgentEvent event) {
        for (AgentObserver observer : observers) {
            try {
                observer.onEvent(event);
            } catch (Exception e) {
                log.warn("Observador {} falhou ao tratar {}: {}",
                        observer.getClass().getSimpleName(), event.getClass().getSimpleName(), e.getMessage());
            }
        }
    }
}
