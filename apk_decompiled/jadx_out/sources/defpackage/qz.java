package defpackage;

import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class qz extends w {
    public final b0 e;
    public final int f;
    public final int g;

    public qz(b0 b0Var, int i, int i2) {
        this.e = b0Var;
        this.f = i;
        m20.m(i, i2, b0Var.a());
        this.g = i2 - i;
    }

    @Override // defpackage.m
    public final int a() {
        return this.g;
    }

    @Override // java.util.List
    public final Object get(int i) {
        m20.j(i, this.g);
        return this.e.get(this.f + i);
    }

    @Override // defpackage.w, java.util.List
    public final List subList(int i, int i2) {
        m20.m(i, i2, this.g);
        int i3 = this.f;
        return new qz(this.e, i + i3, i3 + i2);
    }
}
