package com.diegokrupitza.vm;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

/**
 * <code>FiFoHost</code> is a Host that priorities the Containers based on the order they got added.
 * Based on that every time a new phase gets triggert the first added containers starts
 * executing, then the second and so on
 *
 * @author Diego Krupitza
 * @version 1.0
 * @date 2019-05-27
 */
public class FiFoHost implements Host {

    private Queue<Container> containerQueue = new LinkedList<>();

    @Override
    public void addContainer(Container container) throws VirtualMaschineException {
        container.attachHost(this);
        containerQueue.add(container);
    }

    @Override
    public boolean removeContainerById(String id) throws VirtualMaschineException {
        return containerQueue.removeIf(item -> item.getContainerId().equals(id));
    }

    @Override
    public Optional<Container> getContainerById(String id) {
        return containerQueue.parallelStream()
                .filter(item -> item.getContainerId().equals(id))
                .findFirst();
    }

    @Override
    public boolean moveContainerById(String id, Host destination) throws VirtualMaschineException {
        Optional<Container> containerById = getContainerById(id);
        if (!containerById.isPresent()) {
            return false;
        }

        //TODO make sure everything goes right
        // in case not then rewind everything
        Container container = containerById.get();
        container.holdContainer();

        destination.addContainer(container);
        container.continueContainer();

        return true;
    }

    @Override
    public void execute() throws VirtualMaschineException {
        containerQueue
                .parallelStream()
                .forEach(Container::start);
    }
}
