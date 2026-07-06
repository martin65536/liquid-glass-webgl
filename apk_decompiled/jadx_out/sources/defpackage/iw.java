package defpackage;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class iw implements Iterator, q30 {
    public final /* synthetic */ int e;
    public int f;
    public Object g;
    public final Object h;

    public iw(ye0 ye0Var) {
        this.e = 2;
        this.h = ye0Var;
        this.f = -1;
        this.g = g30.y(new xe0(ye0Var, this, null));
    }

    public void a() {
        Object e;
        int i;
        int i2 = this.f;
        cs csVar = (cs) this.h;
        if (i2 == -2) {
            e = ((vu) csVar.c).a();
        } else {
            gv gvVar = csVar.b;
            Object obj = this.g;
            obj.getClass();
            e = gvVar.e(obj);
        }
        this.g = e;
        if (e == null) {
            i = 0;
        } else {
            i = 1;
        }
        this.f = i;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        switch (this.e) {
            case 0:
                if (this.f < 0) {
                    a();
                }
                if (this.f != 1) {
                    return false;
                }
                return true;
            case 1:
                return ((mv0) this.g).hasNext();
            case 2:
                return ((mv0) this.g).hasNext();
            default:
                if (this.f >= ((Map) this.h).size()) {
                    return false;
                }
                return true;
        }
    }

    @Override // java.util.Iterator
    public final Object next() {
        Object obj = null;
        switch (this.e) {
            case 0:
                if (this.f < 0) {
                    a();
                }
                if (this.f != 0) {
                    Object obj2 = this.g;
                    obj2.getClass();
                    this.f = -1;
                    return obj2;
                }
                v7.n();
                return null;
            case 1:
                return ((mv0) this.g).next();
            case 2:
                return ((mv0) this.g).next();
            default:
                if (hasNext()) {
                    obj = this.g;
                    this.f++;
                    Object obj3 = ((Map) this.h).get(obj);
                    if (obj3 != null) {
                        this.g = ((f90) obj3).b;
                    } else {
                        throw new ConcurrentModificationException("Hash code of an element (" + obj + ") has changed after it was added to the persistent set.");
                    }
                } else {
                    v7.n();
                }
                return obj;
        }
    }

    @Override // java.util.Iterator
    public final void remove() {
        int i = this.e;
        Object obj = this.h;
        switch (i) {
            case 0:
                throw new UnsupportedOperationException("Operation is not supported for read-only collection");
            case 1:
                int i2 = this.f;
                if (i2 != -1) {
                    ((se0) obj).f.h(i2);
                    this.f = -1;
                    return;
                }
                return;
            case 2:
                int i3 = this.f;
                if (i3 != -1) {
                    ((ye0) obj).f.m(i3);
                    this.f = -1;
                    return;
                }
                return;
            default:
                throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }
    }

    public iw(cs csVar) {
        this.e = 0;
        this.h = csVar;
        this.f = -2;
    }

    public iw(Object obj, Map map) {
        this.e = 3;
        this.g = obj;
        this.h = map;
    }

    public iw(se0 se0Var) {
        this.e = 1;
        this.h = se0Var;
        this.f = -1;
        this.g = g30.y(new re0(se0Var, this, null));
    }
}
