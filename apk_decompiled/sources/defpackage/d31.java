package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class d31 extends b31 {
    public final rl0 h;

    public d31(rl0 rl0Var) {
        this.h = rl0Var;
    }

    @Override // java.util.Iterator
    public final Object next() {
        int i = this.g;
        this.g = i + 2;
        Object[] objArr = this.e;
        return new ne0(this.h, objArr[i], objArr[i + 1]);
    }
}
