package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class uj0 {
    public r5 a;
    public te b;
    public float c = 1.0f;
    public m40 d = m40.e;

    public abstract void a(float f);

    public abstract void b(te teVar);

    public final void c(b50 b50Var, long j, float f, te teVar) {
        yc ycVar = b50Var.e;
        if (this.c != f) {
            a(f);
            this.c = f;
        }
        if (!o20.e(this.b, teVar)) {
            b(teVar);
            this.b = teVar;
        }
        m40 layoutDirection = b50Var.getLayoutDirection();
        if (this.d != layoutDirection) {
            this.d = layoutDirection;
        }
        int i = (int) (j >> 32);
        float intBitsToFloat = Float.intBitsToFloat((int) (b50Var.j() >> 32)) - Float.intBitsToFloat(i);
        int i2 = (int) (j & 4294967295L);
        float intBitsToFloat2 = Float.intBitsToFloat((int) (b50Var.j() & 4294967295L)) - Float.intBitsToFloat(i2);
        ((j2) ycVar.f.f).j(0.0f, 0.0f, intBitsToFloat, intBitsToFloat2);
        if (f > 0.0f) {
            try {
                if (Float.intBitsToFloat(i) > 0.0f && Float.intBitsToFloat(i2) > 0.0f) {
                    e(b50Var);
                }
            } finally {
                ((j2) ycVar.f.f).j(-0.0f, -0.0f, -intBitsToFloat, -intBitsToFloat2);
            }
        }
    }

    public abstract long d();

    public abstract void e(b50 b50Var);
}
