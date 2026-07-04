package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class pb0 extends dm0 {
    public final /* synthetic */ int f;
    public final Object g;

    public /* synthetic */ pb0(int i, Object obj) {
        this.f = i;
        this.g = obj;
    }

    @Override // defpackage.mm
    public final float B() {
        int i = this.f;
        Object obj = this.g;
        switch (i) {
            case 0:
                return ((ob0) obj).B();
            default:
                return ((b4) obj).getDensity().B();
        }
    }

    @Override // defpackage.dm0
    public float u(ty tyVar) {
        float f;
        float intBitsToFloat;
        int U;
        switch (this.f) {
            case 0:
                kv kvVar = tyVar.a;
                if (kvVar != null) {
                    return ((Number) kvVar.d(this, Float.valueOf(Float.NaN))).floatValue();
                }
                ob0 ob0Var = (ob0) this.g;
                if (ob0Var.o) {
                    return Float.NaN;
                }
                ob0 ob0Var2 = ob0Var;
                while (true) {
                    rr0 rr0Var = ob0Var2.q;
                    if (rr0Var != null && (U = i8.U(rr0Var.b, tyVar)) >= 0) {
                        f = rr0Var.c[U];
                    } else {
                        f = Float.NaN;
                    }
                    if (!Float.isNaN(f)) {
                        ob0Var2.m0(ob0Var.v0(), tyVar);
                        l40 t0 = ob0Var2.t0();
                        l40 t02 = ob0Var.t0();
                        switch (tyVar.b) {
                            case 0:
                                intBitsToFloat = Float.intBitsToFloat((int) (t02.P(t0, (Float.floatToRawIntBits(f) & 4294967295L) | (Float.floatToRawIntBits(((int) (t0.X() >> 32)) / 2.0f) << 32)) & 4294967295L));
                                break;
                            default:
                                intBitsToFloat = Float.intBitsToFloat((int) (t02.P(t0, (Float.floatToRawIntBits(f) << 32) | (4294967295L & Float.floatToRawIntBits(((int) (t0.X() & 4294967295L)) / 2.0f))) >> 32));
                                break;
                        }
                        return intBitsToFloat;
                    }
                    ob0 x0 = ob0Var2.x0();
                    if (x0 == null) {
                        ob0Var2.m0(ob0Var.v0(), tyVar);
                        return Float.NaN;
                    }
                    ob0Var2 = x0;
                }
                break;
            default:
                return super.u(tyVar);
        }
    }

    @Override // defpackage.dm0
    public final m40 v() {
        int i = this.f;
        Object obj = this.g;
        switch (i) {
            case 0:
                return ((ob0) obj).getLayoutDirection();
            default:
                return ((b4) obj).getLayoutDirection();
        }
    }

    @Override // defpackage.dm0
    public final int x() {
        int i = this.f;
        Object obj = this.g;
        switch (i) {
            case 0:
                return ((ob0) obj).g0();
            default:
                return ((b4) obj).getRoot().I.p.e;
        }
    }

    @Override // defpackage.mm
    public final float y() {
        int i = this.f;
        Object obj = this.g;
        switch (i) {
            case 0:
                return ((ob0) obj).y();
            default:
                return ((b4) obj).getDensity().y();
        }
    }
}
