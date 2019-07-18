/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {
        int k;
        RandomizedQueue<String> rq = new RandomizedQueue<>();

        if (args.length == 1) {
            k = Integer.parseInt(args[0]);
        }
        else {
            throw new IllegalArgumentException(
                    "Please specify an integer as a command-line argument");
        }


        // Option 1: You may use only a constant amount of memory plus either one Deque or RandomizedQueue object of maximum size at most n.
        // while (!StdIn.isEmpty()) {
        //     rq.enqueue(StdIn.readString());
        // }
        //


        if (k == 0) return;

        // Option 2: For an extra challenge and a small amount of extra credit, use only one Deque or RandomizedQueue object of maximum size at most k.
        for (int i = 0; !StdIn.isEmpty(); i++) {
            int r = StdRandom.uniform(i + 1);
            String s = StdIn.readString();
            if (r < k) {
                if (rq.size() == k) {
                    rq.dequeue();
                }
                rq.enqueue(s);
            }
        }

        while (k > 0) {
            StdOut.println(rq.dequeue());
            k--;
        }


    }

}
