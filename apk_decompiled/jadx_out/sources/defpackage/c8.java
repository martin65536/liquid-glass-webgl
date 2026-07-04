package defpackage;

import java.util.Iterator;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class c8 implements Iterator, q30 {
    public int e;
    public int f;
    public boolean g;
    public final /* synthetic */ int h;
    public final /* synthetic */ Object i;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public c8(g8 g8Var, int i) {
        this(g8Var.g);
        this.h = i;
        switch (i) {
            case 1:
                this.i = g8Var;
                this(g8Var.g);
                return;
            default:
                this.i = g8Var;
                return;
        }
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        if (this.f < this.e) {
            return true;
        }
        return false;
    }

    @Override // java.util.Iterator
    public final Object next() {
        Object e;
        if (hasNext()) {
            int i = this.f;
            int i2 = this.h;
            Object obj = this.i;
            switch (i2) {
                case 0:
                    e = ((g8) obj).e(i);
                    break;
                case 1:
                    e = ((g8) obj).h(i);
                    break;
                default:
                    e = ((h8) obj).f[i];
                    break;
            }
            this.f++;
            this.g = true;
            return e;
        }
        v7.n();
        return null;
    }

    @Override // java.util.Iterator
    public final void remove() {
        if (this.g) {
            int i = this.f - 1;
            this.f = i;
            int i2 = this.h;
            Object obj = this.i;
            switch (i2) {
                case 0:
                    ((g8) obj).f(i);
                    break;
                case 1:
                    ((g8) obj).f(i);
                    break;
                default:
                    ((h8) obj).a(i);
                    break;
            }
            this.e--;
            this.g = false;
            return;
        }
        v7.o("Call next() before removing an element.");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public c8(h8 h8Var) {
        this(h8Var.g);
        this.h = 2;
        this.i = h8Var;
    }

    public c8(int i) {
        this.e = i;
    }
}
