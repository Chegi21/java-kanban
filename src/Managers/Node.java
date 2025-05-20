package Managers;

public class Node<Task> {
    private Task node;

    private Node<Task> nextNode;

    private Node<Task> prevNode;

    public Node(Node<Task> prevNode, Task node, Node<Task> nextNode) {
        this.prevNode = prevNode;
        this.node = node;
        this.nextNode = nextNode;
    }

    public Task getNode() {
        return node;
    }

    public void setNode(Task node) {
        this.node = node;
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
