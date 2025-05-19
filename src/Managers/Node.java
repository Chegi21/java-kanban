package Managers;

public class Node<Task> {
    private Task task;

    private Node<Task> nextNode;

    private Node<Task> prevNode;

    public Node(Node<Task> prevNode, Task task , Node<Task> nextNode) {
        this.prevNode = prevNode;
        this.task = task;
        this.nextNode = nextNode;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Node<Task> getNextNode() {
        return nextNode;
    }

    public void setNextNode(Node<Task> nextNode) {
        this.nextNode = nextNode;
    }

    public Node<Task> getPrevNode() {
        return prevNode;
    }

    public void setPrevNode(Node<Task> prevNode) {
        this.prevNode = prevNode;
    }
}
