package defpackage;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class wl0 extends k0 implements Set, Collection, q30 {
    public static final wl0 h;
    public final Object e;
    public final Object f;
    public final ml0 g;

    static {
        x1 x1Var = x1.G;
        h = new wl0(x1Var, x1Var, ml0.g);
    }

    public wl0(Object obj, Object obj2, ml0 ml0Var) {
        this.e = obj;
        this.f = obj2;
        this.g = ml0Var;
    }

    @Override // defpackage.m
    public final int a() {
        return this.g.f;
    }

    @Override // defpackage.m, java.util.Collection, java.util.List
    public final boolean contains(Object obj) {
        return this.g.containsKey(obj);
    }

    @Override // java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator iterator() {
        return new iw(this.e, this.g);
    }
}
