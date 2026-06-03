public class CO2 {

    static class SegTreeLazy {

        long[] tree;
        long[] lazy;
        int n;

        SegTreeLazy(int n) {
            this.n = n;
            tree = new long[4 * n];
            lazy = new long[4 * n];
        }

        void build(int node, int start, int end, long[] arr) {

            if (start == end) {
                tree[node] = arr[start];
                return;
            }

            int mid = (start + end) / 2;

            build(2 * node, start, mid, arr);
            build(2 * node + 1, mid + 1, end, arr);

            tree[node] = Math.max(tree[2 * node],
                                  tree[2 * node + 1]);
        }

        void pushDown(int node) {

            if (lazy[node] != 0) {

                tree[2 * node] += lazy[node];
                tree[2 * node + 1] += lazy[node];

                lazy[2 * node] += lazy[node];
                lazy[2 * node + 1] += lazy[node];

                lazy[node] = 0;
            }
        }

        void updateRange(int node,
                         int start,
                         int end,
                         int l,
                         int r,
                         long delta) {

            if (start > r || end < l)
                return;

            if (l <= start && end <= r) {

                tree[node] += delta;
                lazy[node] += delta;
                return;
            }

            pushDown(node);

            int mid = (start + end) / 2;

            updateRange(2 * node,
                    start,
                    mid,
                    l,
                    r,
                    delta);

            updateRange(2 * node + 1,
                    mid + 1,
                    end,
                    l,
                    r,
                    delta);

            tree[node] = Math.max(tree[2 * node],
                                  tree[2 * node + 1]);
        }

        long queryMax(int node,
                      int start,
                      int end,
                      int l,
                      int r) {

            if (start > r || end < l)
                return Long.MIN_VALUE;

            if (l <= start && end <= r)
                return tree[node];

            pushDown(node);

            int mid = (start + end) / 2;

            long left =
                    queryMax(2 * node,
                            start,
                            mid,
                            l,
                            r);

            long right =
                    queryMax(2 * node + 1,
                            mid + 1,
                            end,
                            l,
                            r);

            return Math.max(left, right);
        }

        void printTree() {

            System.out.println("\nSegment Tree Values:");

            for (int i = 1; i < 32; i++) {

                if (tree[i] != 0)
                    System.out.println(
                            "Node " + i +
                            " -> " + tree[i]);
            }
        }
    }

    public static void main(String[] args) {

        int n = 16;

        long[] attendance = new long[n];

        for (int i = 0; i < n; i++) {
            attendance[i] = 75;
        }

        SegTreeLazy seg =
                new SegTreeLazy(n);

        seg.build(1, 0, n - 1, attendance);

        System.out.println(
                "Initial Attendance = 75%"
        );

        // Operation 1
        System.out.println(
                "\nUpdate [3,9] += 5"
        );

        seg.updateRange(
                1, 0, n - 1,
                3, 9, 5
        );

        // Operation 2
        System.out.println(
                "Update [7,14] += 3"
        );

        seg.updateRange(
                1, 0, n - 1,
                7, 14, 3
        );

        // Operation 3
        long max1 =
                seg.queryMax(
                        1, 0, n - 1,
                        0, 15
                );

        System.out.println(
                "\nMax Attendance [0,15] = "
                        + max1 + "%"
        );

        // Operation 4
        System.out.println(
                "\nUpdate [2,6] += 4"
        );

        seg.updateRange(
                1, 0, n - 1,
                2, 6, 4
        );

        // Operation 5
        long max2 =
                seg.queryMax(
                        1, 0, n - 1,
                        4, 10
                );

        System.out.println(
                "\nMax Attendance [4,10] = "
                        + max2 + "%"
        );

        seg.printTree();

        System.out.println(
                "\nStudent Attendance Monitoring System Completed Successfully."
        );
    }
}