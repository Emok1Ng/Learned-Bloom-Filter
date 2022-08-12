import java.util.BitSet;

public class MyBloomFilter {

    private static final int DEFAULT_SIZE = 2 << 24;

    private static final int[] SEEDS = new int[]{3, 13, 46, 71, 91, 134};

    private BitSet bits = new BitSet(DEFAULT_SIZE);

    private SimpleHash[] func = new SimpleHash[SEEDS.length];

    public MyBloomFilter() {
        for(int i = 0; i < SEEDS.length; i++){
            func[i] = new SimpleHash(SEEDS[i], DEFAULT_SIZE);
        }
    }

    public void add(Object object){
        for(SimpleHash f: func){
            bits.set(f.hash(object), true);
        }
    }

    public boolean contains(Object object){
        boolean ret = true;
        for(SimpleHash f: func){
            ret = ret & bits.get(f.hash(object));
        }
        return ret;
    }

    public static class SimpleHash{

        private int seed;
        private int capacity;

        public SimpleHash(int seed, int capacity){
            this.seed = seed;
            this.capacity = capacity;
        }

        public int hash(Object object){
            int h;
            return object == null ? 0 : Math.abs(seed * (capacity - 1) & ((h = object.hashCode()) ^ (h >>> 16)));
        }
    }
}
