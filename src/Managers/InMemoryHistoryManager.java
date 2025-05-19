package Managers;

import Task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final HashMap<Integer, Node<Task>> nodeHashMap = new HashMap<>();
    private Node<Task> headNode;
    private Node<Task> tailNode;

    @Override
    public void add(Task task) {
        if(task != null) {
            remove(task.getId());
            overwrite(task);
        }
    }

    private void overwrite(Task task) {
        Node<Task> oldTail = tailNode;
        Node<Task> newNode = new Node<Task>(oldTail, task, null);
        tailNode = newNode;

        if (oldTail == null) {
            headNode = newNode;
        } else {
            oldTail.setNextNode(newNode);
        }

        nodeHashMap.put(task.getId(), newNode);
    }

    @Override
    public void remove(int id) {
        Node<Task> taskNode = nodeHashMap.get(id);

        if (!(taskNode == null)) {
            if (headNode == taskNode && tailNode == taskNode) {
                headNode = null;
                tailNode = null;
            } else if (headNode == taskNode) {
                headNode = taskNode.getNextNode();
                headNode.setPrevNode(null);
            } else if (tailNode == taskNode) {
                tailNode = taskNode.getPrevNode();
                tailNode.setNextNode(null);
            } else {
                taskNode.getPrevNode().setNextNode(taskNode.getNextNode());
                taskNode.getNextNode().setPrevNode(taskNode.getPrevNode());
            }


            taskNode.setTask(null);
            taskNode.setPrevNode(null);
            taskNode.setNextNode(null);
            nodeHashMap.remove(id);
        }
    }

    @Override
    public List<Task> getHistory() {
        List<Task> tasks = new ArrayList<>();
        Node<Task> node = headNode;
        while (!(node == null)) {
            tasks.add(node.getTask());
            node = node.getNextNode();
        }
        return tasks;
    }
}
