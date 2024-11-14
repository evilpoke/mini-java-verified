package testingstuff;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;




public class StringIterableClass<T> implements Iterator<Character> { //T is irrelevant
    private String str;
    private int count = 0;
    public StringIterableClass(String test) {
        str = test;
    }

    @Override
    public boolean hasNext() {
        return count < str.length();
    }

    @Override
    public Character next() {
        if (hasNext()) {

            return str.charAt(count++);
        } else {
            throw new NoSuchElementException();
        }

    }

    @Override
    public void remove() {

    }

    @Override
    public void forEachRemaining(Consumer<? super Character> action) {

    }
    public static void main(String[] args){
        Iterator<Character> iter = new StringIterableClass<Character>("Testing");
        while(iter.hasNext()){
            System.out.println(iter.next());
        }
    }

}
