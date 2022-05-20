import java.util.HashMap;

public class LRUCache {

    private HashMap<Integer, DLinkedNode>cache;
    private int capacity;
    private DLinkedNode head, tail;
    private int size;

    public LRUCache(int capacity) {
        this.size = 0;
        this.capacity = capacity;

        head = new DLinkedNode();
        // head.prev = null;

        tail = new DLinkedNode();
        // tail.next = null;

        head.next = tail;
        tail.prev = head;
    }

    public void addNode(DLinkedNode node) {
        node.prev = head;
        node.next = head.next;

        head.next.prev = node;
        head.next = node;
    }

    public void removeNode(DLinkedNode node) {
       DLinkedNode prev = node.prev;
       DLinkedNode next= node.next;

       prev.next = next;
       next.prev = prev;
    }

    public void moveToHead(DLinkedNode node) {
        removeNode(node);
        addNode(node);
    }

    public DLinkedNode popTail() {
        DLinkedNode res = tail.prev;
        removeNode(res);
        return res;
    }

    public int get(int key) {
        DLinkedNode node = cache.get(key);
        if (node == null) return -1;

        // move the accessed node to the head;
        moveToHead(node);

        return node.value;
    }

    public void put(int key, int value) {
        DLinkedNode node = cache.get(key);
        if(node == null){
            DLinkedNode node1 = new DLinkedNode();
            node1.key =key;
            node1.value = value;
            cache.put(key, node1);
            addNode(node1);
            size++;

            if(size == capacity) {
                DLinkedNode node2 = popTail();
                cache.remove(node2.key);
                size--;
            }
        } else {
            node.value = value;
            moveToHead(node);
        }
    }
}
