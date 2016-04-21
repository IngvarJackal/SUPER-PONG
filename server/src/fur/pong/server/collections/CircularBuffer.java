package fur.pong.server.collections;

import java.lang.reflect.Array;

public class CircularBuffer<T> {
    private final T[] array;
    private final int arrLen;
    private int begin = 0;
    private int len = 0;

    @SuppressWarnings("unchecked")
    public CircularBuffer(Class<T> c, int arrLen) {
        array = (T[]) Array.newInstance(c, arrLen);
        this.arrLen = arrLen;
    }

    public T get(int pos) {
        assert pos < getLen();
        assert pos >= getAvalHist();
        return array[(pos + begin + arrLen) % arrLen];
    }

    public int getAvalHist() {
        return len - arrLen;
    }
    public int getLen() {
        return len;
    }

    public T getNearest(int pos) {
        T e = get(pos);
        if (e != null)
            return e;
        int i = pos+1; int j = pos-1;
        while (i < getLen() || j >= getAvalHist()) {
            if (i < getLen()) {
                e = get(i++);
                if (e != null)
                    return e;
            }
            if (j >= getAvalHist()) {
                e = get(j--);
                if (e != null)
                    return e;
            }
        }
        return null;
    }

    public T getEarlier(int pos) {
        T e = get(pos);
        if (e != null)
            return e;
        int j = pos-1;
        while (j >= getAvalHist()) {
            if (j >= getAvalHist()) {
                e = get(j--);
                if (e != null)
                    return e;
            }
        }
        return null;
    }

    /*
        returns amount of allocated memory
     */
    public int set(T obj, int index) {
        assert index >= getAvalHist();
        assert index < arrLen;
        int allocated = 0;
        if (index > len) { // allocate part between len and new index
            for (int i = len; i < index; i++) {
                array[(i + begin) % arrLen] = null;
                allocated++;
            }
        }
        len = Math.max(index + 1, len);
        array[(index + begin + arrLen) % arrLen] = obj;
        return allocated;
    }

    public void shift(int pos) {
        assert pos <= arrLen;
        assert pos >= getAvalHist();
        begin = (pos+begin) % arrLen;
        len = Math.max(0, len-pos);
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder(arrLen);
        b.append("l=" + getLen() + " h=" + getAvalHist() + " [ ");
        for (int i = getAvalHist(); i < 0; i++) {
            b.append(get(i));
            b.append(" ");
        }
        b.append("| ");
        for (int i = 0; i < len; i++) {
            b.append(get(i));
            b.append(" ");
        }
        b.append("]");
        return b.toString();
    }
}