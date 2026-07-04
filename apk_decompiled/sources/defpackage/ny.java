package defpackage;

import java.util.ConcurrentModificationException;
import java.util.ListIterator;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ny implements ListIterator, q30 {
    public final /* synthetic */ int e;
    public int f;
    public int g;
    public int h;
    public final Object i;

    public ny(mx0 mx0Var, int i) {
        this.e = 3;
        this.i = mx0Var;
        this.f = i - 1;
        this.g = -1;
        this.h = o4.J(mx0Var);
    }

    public void a() {
        if (ka0.c(((ja0) this.i).i) == this.h) {
        } else {
            throw new ConcurrentModificationException();
        }
    }

    @Override // java.util.ListIterator
    public final void add(Object obj) {
        int i = this.e;
        Object obj2 = this.i;
        switch (i) {
            case 0:
                throw new UnsupportedOperationException("Operation is not supported for read-only collection");
            case 1:
                a();
                ja0 ja0Var = (ja0) obj2;
                int i2 = this.f;
                this.f = i2 + 1;
                ja0Var.add(i2, obj);
                this.g = -1;
                this.h = ja0.c(ja0Var);
                return;
            case 2:
                b();
                ka0 ka0Var = (ka0) obj2;
                int i3 = this.f;
                this.f = i3 + 1;
                ka0Var.add(i3, obj);
                this.g = -1;
                this.h = ka0.c(ka0Var);
                return;
            default:
                c();
                mx0 mx0Var = (mx0) obj2;
                mx0Var.add(this.f + 1, obj);
                this.g = -1;
                this.f++;
                this.h = o4.J(mx0Var);
                return;
        }
    }

    public void b() {
        if (ka0.c((ka0) this.i) == this.h) {
        } else {
            throw new ConcurrentModificationException();
        }
    }

    public void c() {
        if (o4.J((mx0) this.i) == this.h) {
        } else {
            throw new ConcurrentModificationException();
        }
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public final boolean hasNext() {
        int i = this.e;
        Object obj = this.i;
        switch (i) {
            case 0:
                if (this.f >= this.h) {
                    return false;
                }
                return true;
            case 1:
                if (this.f >= ((ja0) obj).g) {
                    return false;
                }
                return true;
            case 2:
                if (this.f >= ((ka0) obj).f) {
                    return false;
                }
                return true;
            default:
                if (this.f >= ((mx0) obj).size() - 1) {
                    return false;
                }
                return true;
        }
    }

    @Override // java.util.ListIterator
    public final boolean hasPrevious() {
        switch (this.e) {
            case 0:
                if (this.f > this.g) {
                    return true;
                }
                return false;
            case 1:
                if (this.f > 0) {
                    return true;
                }
                return false;
            case 2:
                if (this.f > 0) {
                    return true;
                }
                return false;
            default:
                if (this.f >= 0) {
                    return true;
                }
                return false;
        }
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public final Object next() {
        int i = this.e;
        Object obj = this.i;
        switch (i) {
            case 0:
                pe0 pe0Var = ((py) obj).e;
                int i2 = this.f;
                this.f = i2 + 1;
                Object f = pe0Var.f(i2);
                f.getClass();
                return (bd0) f;
            case 1:
                a();
                int i3 = this.f;
                ja0 ja0Var = (ja0) obj;
                if (i3 < ja0Var.g) {
                    this.f = i3 + 1;
                    this.g = i3;
                    return ja0Var.e[ja0Var.f + i3];
                }
                v7.n();
                return null;
            case 2:
                b();
                int i4 = this.f;
                ka0 ka0Var = (ka0) obj;
                if (i4 < ka0Var.f) {
                    this.f = i4 + 1;
                    this.g = i4;
                    return ka0Var.e[i4];
                }
                v7.n();
                return null;
            default:
                c();
                int i5 = this.f + 1;
                this.g = i5;
                mx0 mx0Var = (mx0) obj;
                o4.i(i5, mx0Var.size());
                Object obj2 = mx0Var.get(i5);
                this.f = i5;
                return obj2;
        }
    }

    @Override // java.util.ListIterator
    public final int nextIndex() {
        switch (this.e) {
            case 0:
                return this.f - this.g;
            case 1:
                return this.f;
            case 2:
                return this.f;
            default:
                return this.f + 1;
        }
    }

    @Override // java.util.ListIterator
    public final Object previous() {
        int i = this.e;
        Object obj = this.i;
        switch (i) {
            case 0:
                pe0 pe0Var = ((py) obj).e;
                int i2 = this.f - 1;
                this.f = i2;
                Object f = pe0Var.f(i2);
                f.getClass();
                return (bd0) f;
            case 1:
                a();
                int i3 = this.f;
                if (i3 > 0) {
                    int i4 = i3 - 1;
                    this.f = i4;
                    this.g = i4;
                    ja0 ja0Var = (ja0) obj;
                    return ja0Var.e[ja0Var.f + i4];
                }
                v7.n();
                return null;
            case 2:
                b();
                int i5 = this.f;
                if (i5 > 0) {
                    int i6 = i5 - 1;
                    this.f = i6;
                    this.g = i6;
                    return ((ka0) obj).e[i6];
                }
                v7.n();
                return null;
            default:
                c();
                mx0 mx0Var = (mx0) obj;
                o4.i(this.f, mx0Var.size());
                int i7 = this.f;
                this.g = i7;
                this.f--;
                return mx0Var.get(i7);
        }
    }

    @Override // java.util.ListIterator
    public final int previousIndex() {
        int i;
        switch (this.e) {
            case 0:
                return (this.f - this.g) - 1;
            case 1:
                i = this.f;
                break;
            case 2:
                i = this.f;
                break;
            default:
                return this.f;
        }
        return i - 1;
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public final void remove() {
        int i = this.e;
        Object obj = this.i;
        switch (i) {
            case 0:
                throw new UnsupportedOperationException("Operation is not supported for read-only collection");
            case 1:
                ja0 ja0Var = (ja0) obj;
                a();
                int i2 = this.g;
                if (i2 != -1) {
                    ja0Var.b(i2);
                    this.f = this.g;
                    this.g = -1;
                    this.h = ja0.c(ja0Var);
                    return;
                }
                v7.o("Call next() or previous() before removing element from the iterator.");
                return;
            case 2:
                ka0 ka0Var = (ka0) obj;
                b();
                int i3 = this.g;
                if (i3 != -1) {
                    ka0Var.b(i3);
                    this.f = this.g;
                    this.g = -1;
                    this.h = ka0.c(ka0Var);
                    return;
                }
                v7.o("Call next() or previous() before removing element from the iterator.");
                return;
            default:
                c();
                mx0 mx0Var = (mx0) obj;
                mx0Var.remove(this.g);
                this.f--;
                this.g = -1;
                this.h = o4.J(mx0Var);
                return;
        }
    }

    @Override // java.util.ListIterator
    public final void set(Object obj) {
        int i = this.e;
        Object obj2 = this.i;
        switch (i) {
            case 0:
                throw new UnsupportedOperationException("Operation is not supported for read-only collection");
            case 1:
                a();
                int i2 = this.g;
                if (i2 != -1) {
                    ((ja0) obj2).set(i2, obj);
                    return;
                } else {
                    v7.o("Call next() or previous() before replacing element from the iterator.");
                    return;
                }
            case 2:
                b();
                int i3 = this.g;
                if (i3 != -1) {
                    ((ka0) obj2).set(i3, obj);
                    return;
                } else {
                    v7.o("Call next() or previous() before replacing element from the iterator.");
                    return;
                }
            default:
                mx0 mx0Var = (mx0) obj2;
                c();
                int i4 = this.g;
                if (i4 >= 0) {
                    mx0Var.set(i4, obj);
                    this.h = o4.J(mx0Var);
                    return;
                } else {
                    v7.o("Cannot call set before the first call to next() or previous() or immediately after a call to add() or remove()");
                    return;
                }
        }
    }

    public ny(ka0 ka0Var, int i) {
        this.e = 2;
        this.i = ka0Var;
        this.f = i;
        this.g = -1;
        this.h = ka0.c(ka0Var);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ny(py pyVar, int i, int i2) {
        this(pyVar, (i2 & 1) != 0 ? 0 : i, 0, pyVar.e.b);
        this.e = 0;
    }

    public ny(py pyVar, int i, int i2, int i3) {
        this.e = 0;
        this.i = pyVar;
        this.f = i;
        this.g = i2;
        this.h = i3;
    }

    public ny(ja0 ja0Var, int i) {
        this.e = 1;
        this.i = ja0Var;
        this.f = i;
        this.g = -1;
        this.h = ja0.c(ja0Var);
    }
}
