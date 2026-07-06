package defpackage;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class ol0 extends AbstractMap implements Map, r30 {
    public rt e;
    public a31 f;
    public Object g;
    public int h;
    public int i;

    public final void a(int i) {
        this.i = i;
        this.h++;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final void clear() {
        this.f = a31.e;
        a(0);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsKey(Object obj) {
        int i;
        a31 a31Var = this.f;
        if (obj != null) {
            i = obj.hashCode();
        } else {
            i = 0;
        }
        return a31Var.d(i, 0, obj);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Set entrySet() {
        return new ql0(0, this);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Object get(Object obj) {
        int i;
        a31 a31Var = this.f;
        if (obj != null) {
            i = obj.hashCode();
        } else {
            i = 0;
        }
        return a31Var.g(i, 0, obj);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Set keySet() {
        return new ql0(1, this);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Object put(Object obj, Object obj2) {
        int i;
        this.g = null;
        a31 a31Var = this.f;
        if (obj != null) {
            i = obj.hashCode();
        } else {
            i = 0;
        }
        this.f = a31Var.l(i, obj, obj2, 0, this);
        return this.g;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v15, types: [ml0] */
    /* JADX WARN: Type inference failed for: r3v0, types: [a31] */
    /* JADX WARN: Type inference failed for: r6v1, types: [lm, java.lang.Object] */
    @Override // java.util.AbstractMap, java.util.Map
    public final void putAll(Map map) {
        ll0 ll0Var;
        ol0 ol0Var;
        ll0 ll0Var2 = null;
        if (map instanceof ml0) {
            ll0Var = (ml0) map;
        } else {
            ll0Var = null;
        }
        if (ll0Var == null) {
            if (map instanceof ol0) {
                ol0Var = (ol0) map;
            } else {
                ol0Var = null;
            }
            if (ol0Var != null) {
                ll0Var2 = ((kl0) ol0Var).b();
            }
        } else {
            ll0Var2 = ll0Var;
        }
        if (ll0Var2 != null) {
            ?? obj = new Object();
            obj.a = 0;
            int i = this.i;
            ?? r3 = this.f;
            a31 a31Var = ll0Var2.e;
            a31Var.getClass();
            this.f = r3.m(a31Var, 0, obj, this);
            int i2 = (ll0Var2.f + i) - obj.a;
            if (i != i2) {
                a(i2);
                return;
            }
            return;
        }
        super.putAll(map);
    }

    @Override // java.util.Map
    public final boolean remove(Object obj, Object obj2) {
        int i;
        int i2 = this.i;
        a31 a31Var = this.f;
        if (obj != null) {
            i = obj.hashCode();
        } else {
            i = 0;
        }
        a31 o = a31Var.o(i, obj, obj2, 0, this);
        if (o == null) {
            o = a31.e;
        }
        this.f = o;
        if (i2 == this.i) {
            return false;
        }
        return true;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final int size() {
        return this.i;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Collection values() {
        return new gc0(1, this);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Object remove(Object obj) {
        this.g = null;
        a31 n = this.f.n(obj != null ? obj.hashCode() : 0, obj, 0, this);
        if (n == null) {
            n = a31.e;
        }
        this.f = n;
        return this.g;
    }
}
