package defpackage;

import java.util.Iterator;
import java.util.Map;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class e8 implements Iterator, Map.Entry {
    public int e;
    public int f = -1;
    public boolean g;
    public final /* synthetic */ g8 h;

    public e8(g8 g8Var) {
        this.h = g8Var;
        this.e = g8Var.g - 1;
    }

    @Override // java.util.Map.Entry
    public final boolean equals(Object obj) {
        if (this.g) {
            if (obj instanceof Map.Entry) {
                Map.Entry entry = (Map.Entry) obj;
                Object key = entry.getKey();
                int i = this.f;
                g8 g8Var = this.h;
                if (o20.e(key, g8Var.e(i)) && o20.e(entry.getValue(), g8Var.h(this.f))) {
                    return true;
                }
            }
            return false;
        }
        v7.o("This container does not support retaining Map.Entry objects");
        return false;
    }

    @Override // java.util.Map.Entry
    public final Object getKey() {
        if (this.g) {
            return this.h.e(this.f);
        }
        v7.o("This container does not support retaining Map.Entry objects");
        return null;
    }

    @Override // java.util.Map.Entry
    public final Object getValue() {
        if (this.g) {
            return this.h.h(this.f);
        }
        v7.o("This container does not support retaining Map.Entry objects");
        return null;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        if (this.f < this.e) {
            return true;
        }
        return false;
    }

    @Override // java.util.Map.Entry
    public final int hashCode() {
        int hashCode;
        int i = 0;
        if (this.g) {
            int i2 = this.f;
            g8 g8Var = this.h;
            Object e = g8Var.e(i2);
            Object h = g8Var.h(this.f);
            if (e == null) {
                hashCode = 0;
            } else {
                hashCode = e.hashCode();
            }
            if (h != null) {
                i = h.hashCode();
            }
            return hashCode ^ i;
        }
        v7.o("This container does not support retaining Map.Entry objects");
        return 0;
    }

    @Override // java.util.Iterator
    public final Object next() {
        if (hasNext()) {
            this.f++;
            this.g = true;
            return this;
        }
        v7.n();
        return null;
    }

    @Override // java.util.Iterator
    public final void remove() {
        if (this.g) {
            this.h.f(this.f);
            this.f--;
            this.e--;
            this.g = false;
            return;
        }
        throw new IllegalStateException();
    }

    @Override // java.util.Map.Entry
    public final Object setValue(Object obj) {
        if (this.g) {
            return this.h.g(this.f, obj);
        }
        v7.o("This container does not support retaining Map.Entry objects");
        return null;
    }

    public final String toString() {
        return getKey() + "=" + getValue();
    }
}
