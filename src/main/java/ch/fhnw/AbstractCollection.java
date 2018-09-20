package ch.fhnw;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

// --------------------- My personal implementation ----------------------

public abstract class AbstractCollection<E> implements Collection<E> {

    /*  Idee:
    *   So viele Methoden wie möglich mit Hilfe derjenigen Methoden implementieren,
    *   die dann von der konkreten Klasse implementiert werden müssen.
    */

    /*  Which methods can't be implemented by using others?
    *   - iterator()
    *   - add()
    */
    public abstract Iterator<E> iterator();
    public abstract boolean add(E e);


    @Override
    public int size() {
        return toArray().length;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean contains(Object x) {
        Iterator<E> it = iterator();
        while ( it.hasNext() ) {
            E current = it.next();
            if (current == x && x.equals(current)) return true;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object e : c) {
            if ( !contains(e) ) return false;
        }
        return true;
    }

    @Override
    public boolean addAll( Collection<? extends E> c) {
        if (c == null ) throw new NullPointerException();
        if ( c.size() == 0) return false;
        for (E e : c) {
            add(e);
        }
        return true;
    }

    @Override
    public boolean remove( Object x) {
        Iterator<E> it = iterator();
        while ( it.hasNext() ) {
            E current = it.next();
            if (current == x && x.equals(current)) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeAll( Collection<?> c) {
        // When at least one element of c is contained within this collection, this will be changed => return true.
        boolean modified = false;
        for (Object e : c) {
            modified = remove( e );
        }
        return modified;
        // Note: Implementation in the solution is more efficient bcs the iterator
        // doesn't start from beginning again and again unlike by using 'remove()'
    }

    @Override
    public boolean retainAll( Collection<?> c) {
        Iterator<E> it = iterator();
        boolean modified = false;
        while ( it.hasNext() ) {
            if ( !c.contains( it.next() )) {
                it.remove();
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public void clear() {
        Iterator<E> it = iterator();
        while ( it.hasNext() ) {
            it.next();
            it.remove();
        }
    }

    @Override
    public Object[] toArray() {
        ArrayList<Object> arr = new ArrayList<>();
        Iterator<E> it = iterator();
        while ( it.hasNext() ) {
            arr.add( it.next() );
        }
        return arr.toArray();
    }

    @Override
    public <T> T[] toArray(T[] arg) {
        //Übernommen von Lösung, da nicht klar war, was per Spezifikation zu erwarten war.
        if (arg.length < size()) {
            arg = (T[]) Array.newInstance(arg.getClass().getComponentType(),
                    size());
        }
        int i = 0;
        for (E e : this) {
            arg[i++] = (T) e;
        }
        if (arg.length > size()) {
            arg[i] = null;
        }
        return arg;
    }
}
