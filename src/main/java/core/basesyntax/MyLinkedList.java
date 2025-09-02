package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(T value, Node<T> prev, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    @Override
    public void add(T value) {
        if (size == 0) {
            head = new Node<>(value, null, null);
            tail = head;
        } else {
            link(value, tail, null);
            tail = tail.next;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if ((index < 0) || (index > size)) {
            throw new IndexOutOfBoundsException(index + " is out of bounds for size " + size);
        }
        if (index == size) {
            add(value);
            return;
        }
        if (index == 0) {
            link(value, null, head);
        } else {
            Node<T> prev = getNode(index - 1);
            link(value, prev, prev.next);
        }
        size++;
        checkHeadAndTail();
    }

    @Override
    public void addAll(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    @Override
    public T get(int index) {
        if (!checkIndex(index)) {
            throw new IndexOutOfBoundsException(index + " is out of bounds for size " + size);
        }
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        if (!checkIndex(index)) {
            throw new IndexOutOfBoundsException(index + " is out of bounds for size " + size);
        }
        Node<T> node = getNode(index);
        T oldValue = node.value;
        node.value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        if (!checkIndex(index)) {
            throw new IndexOutOfBoundsException(index + " is out of bounds for size " + size);
        }
        Node<T> temp = getNode(index);
        unlink(temp);
        size--;
        if (size == 0) {
            head = null;
            tail = null;
        } else {
            checkHeadAndTail();
        }
        return temp.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> temp = getNode(object);
        if (temp != null) {
            unlink(temp);
            size--;
            if (size == 0) {
                head = null;
                tail = null;
            } else {
                checkHeadAndTail();
            }
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private Node<T> getNode(int index) {
        int currentIndex;
        Node<T> currentNode;
        if (index == 0) {
            return head;
        }
        if (index == size - 1) {
            return tail;
        }
        if (index > size / 2) {
            currentNode = tail;
            currentIndex = size - 1;
            while (currentIndex != index) {
                currentNode = currentNode.prev;
                currentIndex--;
            }

        } else {
            currentNode = head;
            currentIndex = 0;
            while (currentIndex != index) {
                currentNode = currentNode.next;
                currentIndex++;
            }
        }
        return currentNode;
    }

    private Node<T> getNode(T object) {
        Node<T> temp = head;
        while (temp != null && !(temp.value == null ? object == null : temp.value.equals(object))) {
            temp = temp.next;
        }
        return temp;
    }

    private void link(T value, Node<T> prev, Node<T> next) {
        Node<T> newNode = new Node<>(value, prev, next);
        if (prev != null) {
            prev.next = newNode;
        }
        if (next != null) {
            next.prev = newNode;
        }
    }

    private boolean checkIndex(int i) {
        return 0 <= i && i < size;
    }

    private void unlink(Node<T> temp) {
        if (temp.prev != null) {
            temp.prev.next = temp.next;
        }
        if (temp.next != null) {
            temp.next.prev = temp.prev;
        }

        if (temp == head) {
            head = head.next;
        }

        if (temp == tail) {
            tail = tail.prev;
        }
    }

    private void checkHeadAndTail() {
        while (tail.next != null) {
            tail = tail.next;
        }

        while (head.prev != null) {
            head = head.prev;
        }
    }
}

