package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class oi extends pi {
    public final wq0 e;
    public final wq0 f;
    public final float[] g;

    public oi(wq0 wq0Var, wq0 wq0Var2) {
        super(wq0Var2, wq0Var, wq0Var2, null);
        float[] N;
        this.e = wq0Var;
        this.f = wq0Var2;
        float[] fArr = (float[]) j2.g.f;
        c61 c61Var = wq0Var.d;
        float[] fArr2 = wq0Var.i;
        c61 c61Var2 = wq0Var2.d;
        float[] fArr3 = wq0Var2.j;
        if (o4.u(c61Var, c61Var2)) {
            N = o4.N(fArr3, fArr2);
        } else {
            float[] a = c61Var.a();
            float[] a2 = c61Var2.a();
            c61 c61Var3 = dl.m;
            N = o4.N(o4.u(c61Var2, c61Var3) ? fArr3 : o4.L(o4.N(o4.t(fArr, a2, new float[]{0.964212f, 1.0f, 0.825188f}), wq0Var2.i)), o4.u(c61Var, c61Var3) ? fArr2 : o4.N(o4.t(fArr, a, new float[]{0.964212f, 1.0f, 0.825188f}), fArr2));
        }
        this.g = N;
    }

    @Override // defpackage.pi
    public final long a(long j) {
        float h = se.h(j);
        float g = se.g(j);
        float e = se.e(j);
        float d = se.d(j);
        sq0 sq0Var = this.e.p;
        float c = (float) sq0Var.c(h);
        float c2 = (float) sq0Var.c(g);
        float c3 = (float) sq0Var.c(e);
        float[] fArr = this.g;
        float f = (fArr[6] * c3) + (fArr[3] * c2) + (fArr[0] * c);
        float f2 = (fArr[7] * c3) + (fArr[4] * c2) + (fArr[1] * c);
        float f3 = (fArr[8] * c3) + (fArr[5] * c2) + (fArr[2] * c);
        wq0 wq0Var = this.f;
        float c4 = (float) wq0Var.m.c(f);
        sq0 sq0Var2 = wq0Var.m;
        return f31.d(c4, (float) sq0Var2.c(f2), (float) sq0Var2.c(f3), d, wq0Var);
    }
}
