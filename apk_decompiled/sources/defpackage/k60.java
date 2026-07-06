package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class k60 implements hy0 {
    public final ik0 e;
    public int f;

    public k60(int i) {
        int i2 = (i / 30) * 30;
        this.e = new ik0(n30.K(Math.max(i2 - 100, 0), i2 + 130), dt0.g);
        this.f = i;
    }

    @Override // defpackage.hy0
    public final Object getValue() {
        return (y10) this.e.getValue();
    }
}
