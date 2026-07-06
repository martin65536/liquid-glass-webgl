package defpackage;

import java.util.List;
import java.util.RandomAccess;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class v extends w implements RandomAccess {
    public final w e;
    public final int f;
    public final int g;

    public v(w wVar, int i, int i2) {
        this.e = wVar;
        this.f = i;
        k81.n(i, i2, wVar.a());
        this.g = i2 - i;
    }

    @Override // defpackage.m
    public final int a() {
        return this.g;
    }

    @Override // java.util.List
    public final Object get(int i) {
        int i2 = this.g;
        if (i >= 0 && i < i2) {
            return this.e.get(this.f + i);
        }
        v7.f(d3.u("index: ", i, ", size: ", i2));
        return null;
    }

    @Override // defpackage.w, java.util.List
    public final List subList(int i, int i2) {
        k81.n(i, i2, this.g);
        int i3 = this.f;
        return new v(this.e, i + i3, i3 + i2);
    }
}
