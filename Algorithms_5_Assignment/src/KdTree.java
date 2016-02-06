import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Queue;

public class KdTree {
    private static final boolean SP_V = true;
    private static final boolean SP_H = false;
    private Node root;
    private int N = 0;

    private static class Node {
        private Point2D p;
        private RectHV rect;
        private Node lb;
        private Node rt;

        public Node(Point2D p, RectHV rect, Node lb, Node rt) {
            this.p = p;
            this.rect = rect;
            this.lb = lb;
            this.rt = rt;
        }
    }

    public KdTree() {
        root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return N;
    }

    public boolean contains(Point2D p) {
        if (p == null)
            throw new NullPointerException("Argument cannot be null!");
        if (isEmpty())
            return false;
        return contains(root, p, SP_V);
    }

    private boolean contains(Node x, Point2D p, boolean sp) {
        if (x == null) {
            return false;
        }
        if (sp == SP_V) {
            if (p.x() < x.p.x())
                return contains(x.lb, p, SP_H);
            else {
                if (p.equals(x.p))
                    return true;
                else
                    return contains(x.rt, p, SP_H);
            }
        } else {
            if (p.y() < x.p.y())
                return contains(x.lb, p, SP_V);
            else {
                if (p.equals(x.p))
                    return true;
                else
                    return contains(x.rt, p, SP_V);
            }
        }
    }

    public void insert(Point2D p) {
        if (p == null)
            throw new NullPointerException("Argument cannot be null!");
        if (isEmpty()) {
            root = new Node(p, new RectHV(0, 0, 1, 1), null, null);
            this.N++;
            return;
        }
        if (contains(p))
            return;
        root = insert(root, p, root.p.x(), root.rect, SP_V);
    }

    private Node insert(Node x, Point2D p, double side, RectHV rect, boolean sp) {
        if (x == null) {
            this.N++;
            if (sp == SP_H) {
                if (p.x() < side)
                    return new Node(p, new RectHV(rect.xmin(), rect.ymin(), side, rect.ymax()), null, null);
                else
                    return new Node(p, new RectHV(side, rect.ymin(), rect.xmax(), rect.ymax()), null, null);
            } else if (sp == SP_V) {
                if (p.y() < side)
                    return new Node(p, new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), side), null, null);
                else
                    return new Node(p, new RectHV(rect.xmin(), side, rect.xmax(), rect.ymax()), null, null);
            }
        }
        if (sp == SP_V) {
            if (p.x() < x.p.x())
                x.lb = insert(x.lb, p, x.p.x(), x.rect, SP_H);
            else
                x.rt = insert(x.rt, p, x.p.x(), x.rect, SP_H);
        } else {
            if (p.y() < x.p.y())
                x.lb = insert(x.lb, p, x.p.y(), x.rect, SP_V);
            else
                x.rt = insert(x.rt, p, x.p.y(), x.rect, SP_V);
        }
        return x;
    }

    public void draw() {
        draw(root, SP_V);
    }

    private void draw(Node x, boolean sp) {
        if (x == null)
            return;
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        x.p.draw();
        if (sp == SP_V) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            x.p.drawTo(new Point2D(x.p.x(), x.rect.ymax()));
            x.p.drawTo(new Point2D(x.p.x(), x.rect.ymin()));
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            x.p.drawTo(new Point2D(x.rect.xmin(), x.p.y()));
            x.p.drawTo(new Point2D(x.rect.xmax(), x.p.y()));
        }
        draw(x.lb, !sp);
        draw(x.rt, !sp);
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new NullPointerException("Argument cannot be null!");
        Queue<Point2D> queue = new Queue<Point2D>();
        if (isEmpty())
            return queue;
        return range(root, rect, queue);
    }

    private Iterable<Point2D> range(Node x, RectHV rect, Queue<Point2D> q) {
        if (rect.contains(x.p))
            q.enqueue(x.p);
        if (x.lb != null && rect.intersects(x.lb.rect))
            range(x.lb, rect, q);
        if (x.rt != null && rect.intersects(x.rt.rect))
            range(x.rt, rect, q);
        return q;
    }

    private final RectHV CONTAINER = new RectHV(0, 0, 1, 1);

    public Point2D nearest(Point2D p) { // kdtree找nearest Point2D模块化:
        if (p == null)
            throw new NullPointerException("Argument cannot be null!");
        if (isEmpty())
            return null;
        return nearest(root, p, root.p, SP_V);  // 此时定有root，才可以用root.p
    }

    private Point2D nearest(Node x, Point2D p, Point2D candidate, boolean sp) {
        final double min = p.distanceSquaredTo(candidate);
        Point2D temp = candidate;
        if (x == null || x.rect.distanceSquaredTo(p) >= min) // rules在这里判断了，若矩形过远，可直接pass；或者到了基底条件x == null也pass
            return candidate;
        if (x.p.distanceSquaredTo(p) < min) {
            temp = x.p;  // 判断，若更近，则更新candidate
        }
        // judge whether we should keep searching each subtree
        // 注意了，必须这样分情况讨论！！！！！
        if (sp == SP_V) {
            if (p.x() < x.p.x())
                temp = nearest(x.rt, p, nearest(x.lb, p, temp, SP_H), SP_H); // 简洁的写法!此情况下一定先走左边，这样右边可能根本不用走了
            else
                temp = nearest(x.lb, p, nearest(x.rt, p, temp, SP_H), SP_H); // 简洁的写法!此情况下一定先走右边，这样左边可能根本不用走了
        } else {
            if (p.y() < x.p.y())
                temp = nearest(x.rt, p, nearest(x.lb, p, temp, SP_V), SP_V);
            else
                temp = nearest(x.lb, p, nearest(x.rt, p, temp, SP_V), SP_V);
        }
        return temp;
    }

    public static void main(String[] args) {
        KdTree test = new KdTree();
        test.insert(new Point2D(0.7, 0.2));
        test.insert(new Point2D(0.5, 0.4));
        test.insert(new Point2D(0.2, 0.3));
        test.insert(new Point2D(0.4, 0.7));
        test.insert(new Point2D(0.9, 0.6));
        System.out.println(test.nearest(new Point2D(0.8, 0.2)));
    }
}
