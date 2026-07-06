package defpackage;

import java.nio.ByteBuffer;
import java.util.ConcurrentModificationException;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class dc0 {
    public int e;
    public int f;
    public int g;
    public Object h;

    public dc0() {
        if (ey0.f == null) {
            ey0.f = new ey0(10);
        }
    }

    public int a(int i) {
        if (i < this.g) {
            return ((ByteBuffer) this.h).getShort(this.f + i);
        }
        return 0;
    }

    public void b() {
        if (((ec0) this.h).l == this.g) {
        } else {
            throw new ConcurrentModificationException();
        }
    }

    public void c() {
        while (true) {
            int i = this.e;
            ec0 ec0Var = (ec0) this.h;
            if (i < ec0Var.j && ec0Var.g[i] < 0) {
                this.e = i + 1;
            } else {
                return;
            }
        }
    }

    public boolean hasNext() {
        if (this.e < ((ec0) this.h).j) {
            return true;
        }
        return false;
    }

    public void remove() {
        ec0 ec0Var = (ec0) this.h;
        b();
        if (this.f != -1) {
            ec0Var.b();
            ec0Var.j(this.f);
            this.f = -1;
            this.g = ec0Var.l;
            return;
        }
        v7.o("Call next() before removing element from the iterator.");
    }
}
