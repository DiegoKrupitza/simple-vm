package com.diegokrupitza.vm;

import com.diegokrupitza.command.Instruction;
import lombok.AllArgsConstructor;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author Diego Krupitza
 * @version 1.0
 * @date 2019-05-27
 */
@AllArgsConstructor
public class ContainerImpl extends Container {

    private ContainerRegistry registry;
    private LinkedList<Instruction> instructions;

    public ContainerImpl(String nickname) {
        this();
        setContainerId(nickname);
    }

    public ContainerImpl() {
        super();
        this.registry = new ContainerRegistry(10);
        this.instructions = new LinkedList<>();

        // TODO change: this is just for testing porpuse
        Instruction instruction = new Instruction(0, "print Hello World!");
        this.instructions.add(instruction);
    }

    @Override
    public boolean holdContainer() {
        if (isStopped() || isHold()) {
            setContainerStatus(ContainerStatus.HOLD);
            return true;
        }
        //TODO
        return false;
    }

    @Override
    public void killContainer() {
        //TODO
    }

    @Override
    public boolean continueContainer() {
        //TODO
        return false;
    }

    @Override
    public void execute() throws ContainerException {
        //TODO select better parser
        Iterator<Instruction> iterator = instructions.iterator();
        while (iterator.hasNext()) {
            Instruction next = iterator.next();
            if (next.getCommand().startsWith("print")) {
                String substring = next.getCommand().substring(next.getCommand().indexOf(" ") + 1);
                sendMessageToHost(substring);
            }
        }
    }
}
