package testingstuff;

class IntToString implements Fun<Integer, String> {
    @Override
    public String apply(Integer x) {
        return Integer.toString(x);
    }
}

public class Map {

    private static class Worker<T, R> implements Runnable {
        private final Fun<T, R> f;
        private final T[] a;
        private final R[] b;
        private final int from;
        private final int to;

        Worker(Fun<T, R> f, T[] a, R[] b, int from, int to) {
            this.f = f;
            this.a = a;
            this.b = b;
            this.from = from;
            this.to = to;
        }

        @Override
        public void run() {
            for (int i = from; i < to; ++i)
                b[i] = f.apply(a[i]);
        }
    }

    static <T, R> void map(Fun<T, R> f, T[] a, R[] b, int n) throws InterruptedException {
        if (f == null || a == null || b == null || a.length > b.length || n < 1) throw new IllegalArgumentException();
        if (n > a.length) n = a.length;

        Thread[] worker = new Thread[n];

        final int more = a.length % n;
        final int step = a.length / n;
        final int step_more = step + 1;
        final int delta = more * step_more;

        for (int i = 0; i < more; ++i) {
            final int from = i * step_more;
            final int to = from + step_more;
            worker[i] = new Thread(new Worker<>(f, a, b, from, to));
            worker[i].start();
        }

        for (int i = 0; i < (n - more); ++i) {
            final int from = delta + i * step;
            final int to = from + step;
            worker[i + more] = new Thread(new Worker<>(f, a, b, from, to));
            worker[i + more].start();
        }

        for (int i = 0; i < n; ++i)
            worker[i].join();
    }
}
