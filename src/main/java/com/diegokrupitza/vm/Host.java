package com.diegokrupitza.vm;

import java.util.Optional;

/**
 * @author Diego Krupitza
 * @version 1.0
 * @date 2019-05-27
 */
public interface Host {

    /**
     * Adds a given container to the Host
     *
     * @param container the container to add
     */
    void addContainer(Container container) throws VirtualMaschineException;

    /**
     * Removes the container from the Host
     *
     * @param container the container to remove
     * @return true - if the container got removed
     * @throws VirtualMaschineException in case an error occurs
     */
    default boolean removeContainer(Container container) throws VirtualMaschineException {
        return removeContainerById(container.getContainerId());
    }

    /**
     * Removes a container from the host based on its id
     *
     * @param id the containers id
     * @return true - if the container got removed
     * @throws VirtualMaschineException in case an error occurs
     */
    boolean removeContainerById(String id) throws VirtualMaschineException;

    Optional<Container> getContainerById(String id);

    /**
     * Moves the container from one host over to another host
     *
     * @param container   the container to move
     * @param destination the host which the container land on
     * @return true - if the container got moved
     * @throws VirtualMaschineException in case an error occurs
     */
    default boolean moveContainer(Container container, Host destination) throws VirtualMaschineException {
        return moveContainerById(container.getContainerId(), destination);
    }

    /**
     * Moves the container from one host over to another host based on its id
     *
     * @param id          the id of the container
     * @param destination the host which the container land on
     * @return true - if the container got moved
     * @throws VirtualMaschineException in case an error occurs
     */
    boolean moveContainerById(String id, Host destination) throws VirtualMaschineException;

    /**
     * Executes all the Containers of the host
     * @throws VirtualMaschineException in case an error occurs while executing
     */
    void execute() throws VirtualMaschineException;

    default void printMessage(Container container, String hostMessage) {
        System.out.printf("[CONTAINER %s ]: %s%n", container.getContainerId(), hostMessage);
    }
}
