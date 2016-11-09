import java.io.UncheckedIOException;

/**
 * Your implementation of a Min Heap
 *
 * @author YOUR Hao Zhang
 * @version 1.0
 */
public class MinHeap<T extends Comparable<? super T>>
    implements HeapInterface<T> {

    // Do NOT add or modify any of these instance variables
    private T[] backingArray;
    private int size;

    /**
     * Creates a Heap with an initial capacity of {@code INITIAL_CAPACITY}
     * Do NOT hardcode this value. Use the CONSTANT provided in the interface
     *
     * Should be O(1)
     */
    @SuppressWarnings("unchecked")
    public MinHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
    }

    /**
     * Creates a Heap with an initial capacity of initialCapacity
     *
     * @param initialCapacity capacity of the new array to initialize
     *                        This value should be the length of the array
     *                        regardless of our heap being 1-indexed
     *
     * Should be O(1)
     */
    @SuppressWarnings("unchecked")
    public MinHeap(int initialCapacity) {
        backingArray = (T[]) new Comparable[initialCapacity];
    }

    /**
     * Creates a Heap from an initial set of values
     * For this constructor, initialize the backing array to fit the passed in data exactly.
     * Example:
     *  If an 5 elements are passed in, your backing array should be of size 6 since the backing array is
     *  1 - indexed.
     * capacity of {@code INITIAL_CAPACITY}
     *
     * When this constructors returns, the backing array should satisfy all
     * the properties of a heap
     *
     * You should implement this the way it was mentioned in lecture
     * The BuildHeap algorithm visualized on the following page is how it should
     * be implemented and is the same method that was taught in class
     * https://www.cs.usfca.edu/~galles/visualization/Heap.html
     *
     * @param values values to initialize the heap with
     *               T... values is the same as T[] values
     *               You may assume that none of the arguments passed in will be null
     */
    @SafeVarargs
    @SuppressWarnings("unchecked")
    public MinHeap(T... values) {
        this(values.length + 1);
        for (int i = 0; i < values.length; i++) {
            backingArray[i+1] = values[i];
            size += 1;
        }
        buildHeap();
    }

    private void buildHeap() {
        for (int i = size/2; i > 0; i--) {
            bubbleDown(i);
        }
    }

    /**
     * Add an item to the heap
     * Double the size of the array if it is full and you are trying to add
     * another element
     *
     * Make sure that you start with your root element at index 1 NOT 0
     *
     * Should be O(log n)
     *
     * You may implement this method iteratively or recursively
     * However, if you do it recursively, you must adhere to the good, clean
     * recursion style discussed in recitation by using a private helper method
     * for the recursive calls
     *
     * @throws IllegalArgumentException if the item is null
     * @param data the item to be added to the heap
     */
    @SuppressWarnings("unchecked")
    @Override
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        }
        if (size < backingArray.length-1) {
            backingArray[size+1] = data;
            size += 1;
        } else {
            T[] backingArray2 = (T[]) new Comparable[(size+1)*2];
            for (int i = 1; i<size+1; i++) {
                backingArray2[i] = backingArray[i];
            }
            backingArray = backingArray2;
            backingArray[size+1] = data;
            size += 1;
        }
        bubbleUp(size);
    }

    private void swap(int child, int parent) {
        T temp = backingArray[child];
        backingArray[child] = backingArray[parent];
        backingArray[parent] = temp;
    }

    private void bubbleUp(int child) {
        int parent = child/2;
        while (child > 1 && backingArray[child].compareTo(backingArray[parent]) < 0) {
            swap(child, parent);
            child = parent;
            parent = child/2;
        }
    }

    /**
     * Remove and return the highest priority item from the heap
     * (in this case a min-heap)
     *
     * Should be O(log n)
     *
     * You may implement this method iteratively or recursively
     * However, if you do it recursively, you must adhere to the good, clean
     * recursion style discussed in recitation by using a private helper method
     * for the recursive calls
     *
     * @throws java.util.NoSuchElementException if the heap is empty
     * @return the removed item
     */
    @Override
    public T remove() {
        if (this.isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        T data = backingArray[1];
        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        size -= 1;
        bubbleDown(1);
        return data;
    }

    private void bubbleDown(int parent) {
        int child = parent * 2 + 1;
        if (child > size || backingArray[child].compareTo(backingArray[child-1]) >= 0) {
            child -= 1;
        }
        while (child < size+1 && backingArray[parent].compareTo(backingArray[child]) > 0) {
            swap(child, parent);
            parent = child;
            child = parent * 2 + 1;
            if (child > size || backingArray[child].compareTo(backingArray[child-1]) > 0) {
                child -= 1;
            }
        }

    }

    /**
     * Return if the heap is empty or not.
     *
     * Should be O(1)
     *
     * @return a boolean representing if the heap is empty
     */
    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Return the size of the heap.
     *
     * Should be O(1)
     *
     * @return the size of the heap
     */
    @Override
    public int size() {
        return size;
    }


    /**
     * Clear the heap AND return array to {@code INITIAL_CAPACITY}
     *
     * Should be O(1)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void clear() {
        size = 0;
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
    }

    // Do NOT edit or use this method in your code
    @Override
    public Comparable[] getBackingArray() {
        return backingArray;
    }
}
