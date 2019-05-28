package com.diegokrupitza.vm;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Diego Krupitza
 * @version 1.0
 * @date 2019-05-27
 */
@Getter
@Setter
public abstract class Container extends Thread {

    private String containerId;
    private ContainerStatus containerStatus;
    private Host host;

    public Container() {
        super();
        this.containerId = generateRandomContainerId();
    }

    /**
     * Executes the instruction of that container
     *
     * @throws ContainerException in case any error occurs
     */
    public abstract void execute() throws ContainerException;

    /**
     * Instantly kills the container and deletes all the data stored into the registries
     */
    public abstract void killContainer();

    /**
     * Continues the execution of the instructions where it stopped
     */
    public abstract boolean continueContainer();

    /**
     * Holds all executions from the container
     *
     * @return true if the container changes into the state <code>ContainerStatus.HOLD</code>
     */
    public abstract boolean holdContainer();

    /**
     * Resets all the registries in the container and restarts the instructions
     *
     * @return true if the container changes into the state <code>ContainerStatus.RUNNING</code>
     */
    public void restartContainer() {
        if (isRunning()) {
            setContainerStatus(ContainerStatus.RUNNING);
        }
        //kill and the call start()
        killContainer();
        start();
        this.containerStatus = ContainerStatus.RUNNING;
    }

    /**
     * Starts the execution of the instruction
     *
     * @return true if the container changes into the state <code>ContainerStatus.RUNNING</code>
     */
    private boolean startContainer() {
        try {
            this.containerStatus = ContainerStatus.RUNNING;
            execute();
        } catch (ContainerException e) {
            this.containerStatus = ContainerStatus.STOPPED;
            return false;
        }
        return true;
    }

    /**
     * Generates a Random Base64 continer id
     *
     * @return the random id
     */
    private String generateRandomContainerId() {
        Random random = ThreadLocalRandom.current();
        byte[] r = new byte[256]; //Means 2048 bit
        random.nextBytes(r);
        byte[] encode = Base64.getEncoder().encode(r);
        return new String(encode).substring(4,16);
    }

    public void sendMessageToHost(String hostMessage) {
        host.printMessage(this, hostMessage);
    }

    @Override
    public synchronized void start() {
        super.start();
        if (!startContainer()) {
            // container could not start
            // kill container and send message to host
            killContainer();
            sendMessageToHost("Container could not start!");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContainerImpl)) return false;
        ContainerImpl container = (ContainerImpl) o;
        return getContainerId().equals(container.getContainerId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getContainerId());
    }

    /**
     * Attaches the host instace to the container, so its possible
     * to communicate inbetween host and container
     *
     * @param host
     */
    public void attachHost(Host host) {
        this.host = host;
    }

    public boolean isRunning() {
        return containerStatus == ContainerStatus.RUNNING;
    }

    public boolean isStopped() {
        return containerStatus == ContainerStatus.STOPPED;
    }

    public boolean isHold() {
        return containerStatus == ContainerStatus.HOLD;
    }
}
