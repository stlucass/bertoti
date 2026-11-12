package br.edu.fatec.onboardingagent.domain;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class LearningJourney {

    private static final List<String> DEFAULT_MODULES = List.of(
            "Inspecionar o repositorio (git status)",
            "Trabalhar com branches (git branch, git checkout)",
            "Registrar alteracoes (git add, git commit)",
            "Publicar no remoto (git push)",
            "Pedir revisao (pull request)");

    private final Map<String, Boolean> modules = new LinkedHashMap<>();

    public LearningJourney() {
        this(DEFAULT_MODULES);
    }

    public LearningJourney(List<String> moduleNames) {
        Objects.requireNonNull(moduleNames, "moduleNames nao pode ser nulo");
        moduleNames.forEach(name -> modules.put(name, false));
    }

    public boolean complete(String moduleName) {
        if (!modules.containsKey(moduleName) || modules.get(moduleName)) {
            return false;
        }
        modules.put(moduleName, true);
        return true;
    }

    public boolean isCompleted(String moduleName) {
        return Boolean.TRUE.equals(modules.get(moduleName));
    }

    public Map<String, Boolean> modules() {
        return Collections.unmodifiableMap(modules);
    }

    public List<String> completedModules() {
        return modules.entrySet().stream().filter(Map.Entry::getValue).map(Map.Entry::getKey).toList();
    }

    public List<String> pendingModules() {
        return modules.entrySet().stream().filter(e -> !e.getValue()).map(Map.Entry::getKey).toList();
    }

    public double progress() {
        if (modules.isEmpty()) {
            return 0.0;
        }
        return (double) completedModules().size() / modules.size();
    }

    @Override
    public String toString() {
        return "Trilha{%d de %d modulos concluidos}".formatted(completedModules().size(), modules.size());
    }
}
